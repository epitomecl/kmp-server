/*
 * Decompiled with CFR 0_132.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Charsets
 *  com.google.common.base.Joiner
 *  com.google.common.base.Objects
 *  com.google.common.base.Preconditions
 *  com.google.common.base.Splitter
 *  com.google.common.io.BaseEncoding
 *  javax.annotation.Nullable
 */
package org.bitcoinj.wallet;

import com.google.common.base.Charsets;
import com.google.common.base.Joiner;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.base.Splitter;
import com.google.common.io.BaseEncoding;
import java.nio.charset.Charset;
import java.security.SecureRandom;
import java.util.List;
import javax.annotation.Nullable;
import org.bitcoinj.core.Utils;
import org.bitcoinj.crypto.MnemonicCode;
import org.bitcoinj.crypto.MnemonicException;
import org.bitcoinj.wallet.UnreadableWalletException;

public class DeterministicSeed {
    public static final int DEFAULT_SEED_ENTROPY_BITS = 128;
    public static final int MAX_SEED_ENTROPY_BITS = 512;
    @Nullable
    private final byte[] seed;
    @Nullable
    private final List<String> mnemonicCode;
    private long creationTimeSeconds;

    public DeterministicSeed(String mnemonicCode, byte[] seed, String passphrase, long creationTimeSeconds) throws UnreadableWalletException {
        this(DeterministicSeed.decodeMnemonicCode(mnemonicCode), seed, passphrase, creationTimeSeconds);
    }

    public DeterministicSeed(byte[] seed, List<String> mnemonic, long creationTimeSeconds) {
        this.seed = (byte[])Preconditions.checkNotNull((Object)seed);
        this.mnemonicCode = (List)Preconditions.checkNotNull(mnemonic);
        this.creationTimeSeconds = creationTimeSeconds;
    }

    public DeterministicSeed(List<String> mnemonicCode, @Nullable byte[] seed, String passphrase, long creationTimeSeconds) {
        this(seed != null ? seed : MnemonicCode.toSeed(mnemonicCode, (String)Preconditions.checkNotNull((Object)passphrase)), mnemonicCode, creationTimeSeconds);
    }

    public DeterministicSeed(SecureRandom random, int bits, String passphrase, long creationTimeSeconds) {
        this(DeterministicSeed.getEntropy(random, bits), (String)Preconditions.checkNotNull((Object)passphrase), creationTimeSeconds);
    }

    public DeterministicSeed(byte[] entropy, String passphrase, long creationTimeSeconds) {
        Preconditions.checkArgument((boolean)(entropy.length % 4 == 0), (Object)"entropy size in bits not divisible by 32");
        Preconditions.checkArgument((boolean)(entropy.length * 8 >= 128), (Object)"entropy size too small");
        Preconditions.checkNotNull((Object)passphrase);
        try {
            this.mnemonicCode = MnemonicCode.INSTANCE.toMnemonic(entropy);
        }
        catch (MnemonicException.MnemonicLengthException e) {
            throw new RuntimeException(e);
        }
        this.seed = MnemonicCode.toSeed(this.mnemonicCode, passphrase);
        this.creationTimeSeconds = creationTimeSeconds;
    }

    private static byte[] getEntropy(SecureRandom random, int bits) {
        Preconditions.checkArgument((boolean)(bits <= 512), (Object)"requested entropy size too large");
        byte[] seed = new byte[bits / 8];
        random.nextBytes(seed);
        return seed;
    }

    public String toString() {
        return "DeterministicSeed " + this.toHexString() + " " + Utils.SPACE_JOINER.join(this.mnemonicCode);
    }

    @Nullable
    public String toHexString() {
        return this.seed != null ? Utils.HEX.encode(this.seed) : null;
    }

    @Nullable
    public byte[] getSecretBytes() {
        return this.getMnemonicAsBytes();
    }

    @Nullable
    public byte[] getSeedBytes() {
        return this.seed;
    }

    public long getCreationTimeSeconds() {
        return this.creationTimeSeconds;
    }

    public void setCreationTimeSeconds(long creationTimeSeconds) {
        this.creationTimeSeconds = creationTimeSeconds;
    }

    private byte[] getMnemonicAsBytes() {
        return Utils.SPACE_JOINER.join(this.mnemonicCode).getBytes(Charsets.UTF_8);
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        DeterministicSeed other = (DeterministicSeed)o;
        return this.creationTimeSeconds == other.creationTimeSeconds && Objects.equal(this.mnemonicCode, other.mnemonicCode);
    }

    public int hashCode() {
        return Objects.hashCode((Object[])new Object[]{this.creationTimeSeconds, this.mnemonicCode});
    }

    public void check() throws MnemonicException {
        if (this.mnemonicCode != null) {
            MnemonicCode.INSTANCE.check(this.mnemonicCode);
        }
    }

    byte[] getEntropyBytes() throws MnemonicException {
        return MnemonicCode.INSTANCE.toEntropy(this.mnemonicCode);
    }

    @Nullable
    public List<String> getMnemonicCode() {
        return this.mnemonicCode;
    }

    private static List<String> decodeMnemonicCode(byte[] mnemonicCode) {
        return DeterministicSeed.decodeMnemonicCode(Utils.toString(mnemonicCode, "UTF-8"));
    }

    private static List<String> decodeMnemonicCode(String mnemonicCode) {
        return Splitter.on((String)" ").splitToList((CharSequence)mnemonicCode);
    }
}

