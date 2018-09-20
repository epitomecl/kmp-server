/*
 * Decompiled with CFR 0_132.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Joiner
 *  com.google.common.base.Stopwatch
 *  com.google.common.io.BaseEncoding
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package org.bitcoinj.crypto;

import com.google.common.base.Joiner;
import com.google.common.base.Stopwatch;
import com.google.common.io.BaseEncoding;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.bitcoinj.core.Sha256Hash;
import org.bitcoinj.core.Utils;
import org.bitcoinj.crypto.MnemonicException;
import org.bitcoinj.crypto.PBKDF2SHA512;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MnemonicCode {
    private static final Logger log = LoggerFactory.getLogger(MnemonicCode.class);
    private ArrayList<String> wordList;
    private static final String BIP39_ENGLISH_RESOURCE_NAME = "mnemonic/wordlist/english.txt";
    private static final String BIP39_ENGLISH_SHA256 = "ad90bf3beb7b0eb7e5acd74727dc0da96e0a280a258354e7293fb7e211ac03db";
    public static long BIP39_STANDARDISATION_TIME_SECS = 1381276800L;
    private static final int PBKDF2_ROUNDS = 2048;
    public static MnemonicCode INSTANCE;

    public MnemonicCode() throws IOException {
        this(MnemonicCode.openDefaultWords(), BIP39_ENGLISH_SHA256);
    }

    private static InputStream openDefaultWords() throws IOException {
        InputStream stream = MnemonicCode.class.getResourceAsStream(BIP39_ENGLISH_RESOURCE_NAME);
        if (stream == null) {
            throw new FileNotFoundException(BIP39_ENGLISH_RESOURCE_NAME);
        }
        return stream;
    }

    public MnemonicCode(InputStream wordstream, String wordListDigest) throws IOException, IllegalArgumentException {
        String hexdigest;
        String word;
        byte[] digest;
        BufferedReader br = new BufferedReader(new InputStreamReader(wordstream, "UTF-8"));
        this.wordList = new ArrayList(2048);
        MessageDigest md = Sha256Hash.newDigest();
        while ((word = br.readLine()) != null) {
            md.update(word.getBytes());
            this.wordList.add(word);
        }
        br.close();
        if (this.wordList.size() != 2048) {
            throw new IllegalArgumentException("input stream did not contain 2048 words");
        }
        if (wordListDigest != null && !(hexdigest = Utils.HEX.encode(digest = md.digest())).equals(wordListDigest)) {
            throw new IllegalArgumentException("wordlist digest mismatch");
        }
    }

    public List<String> getWordList() {
        return this.wordList;
    }

    public static byte[] toSeed(List<String> words, String passphrase) {
        String pass = Utils.SPACE_JOINER.join(words);
        String salt = "mnemonic" + passphrase;
        Stopwatch watch = Stopwatch.createStarted();
        byte[] seed = PBKDF2SHA512.derive(pass, salt, 2048, 64);
        watch.stop();
        log.info("PBKDF2 took {}", (Object)watch);
        return seed;
    }

    public byte[] toEntropy(List<String> words) throws MnemonicException.MnemonicLengthException, MnemonicException.MnemonicWordException, MnemonicException.MnemonicChecksumException {
        int ii;
        if (words.size() % 3 > 0) {
            throw new MnemonicException.MnemonicLengthException("Word list size must be multiple of three words.");
        }
        if (words.size() == 0) {
            throw new MnemonicException.MnemonicLengthException("Word list is empty.");
        }
        int concatLenBits = words.size() * 11;
        boolean[] concatBits = new boolean[concatLenBits];
        int wordindex = 0;
        for (String word : words) {
            int ndx = Collections.binarySearch(this.wordList, word);
            if (ndx < 0) {
                throw new MnemonicException.MnemonicWordException(word);
            }
            for (ii = 0; ii < 11; ++ii) {
                concatBits[wordindex * 11 + ii] = (ndx & 1 << 10 - ii) != 0;
            }
            ++wordindex;
        }
        int checksumLengthBits = concatLenBits / 33;
        int entropyLengthBits = concatLenBits - checksumLengthBits;
        byte[] entropy = new byte[entropyLengthBits / 8];
        for (ii = 0; ii < entropy.length; ++ii) {
            for (int jj = 0; jj < 8; ++jj) {
                if (!concatBits[ii * 8 + jj]) continue;
                byte[] arrby = entropy;
                int n = ii;
                arrby[n] = (byte)(arrby[n] | 1 << 7 - jj);
            }
        }
        byte[] hash = Sha256Hash.hash(entropy);
        boolean[] hashBits = MnemonicCode.bytesToBits(hash);
        for (int i = 0; i < checksumLengthBits; ++i) {
            if (concatBits[entropyLengthBits + i] == hashBits[i]) continue;
            throw new MnemonicException.MnemonicChecksumException();
        }
        return entropy;
    }

    public List<String> toMnemonic(byte[] entropy) throws MnemonicException.MnemonicLengthException {
        if (entropy.length % 4 > 0) {
            throw new MnemonicException.MnemonicLengthException("Entropy length not multiple of 32 bits.");
        }
        if (entropy.length == 0) {
            throw new MnemonicException.MnemonicLengthException("Entropy is empty.");
        }
        byte[] hash = Sha256Hash.hash(entropy);
        boolean[] hashBits = MnemonicCode.bytesToBits(hash);
        boolean[] entropyBits = MnemonicCode.bytesToBits(entropy);
        int checksumLengthBits = entropyBits.length / 32;
        boolean[] concatBits = new boolean[entropyBits.length + checksumLengthBits];
        System.arraycopy(entropyBits, 0, concatBits, 0, entropyBits.length);
        System.arraycopy(hashBits, 0, concatBits, entropyBits.length, checksumLengthBits);
        ArrayList<String> words = new ArrayList<String>();
        int nwords = concatBits.length / 11;
        for (int i = 0; i < nwords; ++i) {
            int index = 0;
            for (int j = 0; j < 11; ++j) {
                index <<= 1;
                if (!concatBits[i * 11 + j]) continue;
                index |= 1;
            }
            words.add(this.wordList.get(index));
        }
        return words;
    }

    public void check(List<String> words) throws MnemonicException {
        this.toEntropy(words);
    }

    private static boolean[] bytesToBits(byte[] data) {
        boolean[] bits = new boolean[data.length * 8];
        for (int i = 0; i < data.length; ++i) {
            for (int j = 0; j < 8; ++j) {
                bits[i * 8 + j] = (data[i] & 1 << 7 - j) != 0;
            }
        }
        return bits;
    }

    static {
        try {
            INSTANCE = new MnemonicCode();
        }
        catch (FileNotFoundException e) {
            if (!Utils.isAndroidRuntime()) {
                log.error("Could not find word list", (Throwable)e);
            }
        }
        catch (IOException e) {
            log.error("Failed to load word list", (Throwable)e);
        }
    }
}

