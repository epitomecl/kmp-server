/*
 * Decompiled with CFR 0_132.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Objects
 *  com.google.common.base.Preconditions
 *  com.google.common.collect.Lists
 */
package org.bitcoinj.core;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.bitcoinj.core.Block;
import org.bitcoinj.core.ECKey;
import org.bitcoinj.core.FilteredBlock;
import org.bitcoinj.core.Message;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.core.PartialMerkleTree;
import org.bitcoinj.core.ProtocolException;
import org.bitcoinj.core.Sha256Hash;
import org.bitcoinj.core.Transaction;
import org.bitcoinj.core.TransactionInput;
import org.bitcoinj.core.TransactionOutPoint;
import org.bitcoinj.core.TransactionOutput;
import org.bitcoinj.core.Utils;
import org.bitcoinj.core.VarInt;
import org.bitcoinj.script.Script;
import org.bitcoinj.script.ScriptChunk;

public class BloomFilter
extends Message {
    private byte[] data;
    private long hashFuncs;
    private long nTweak;
    private byte nFlags;
    private static final long MAX_FILTER_SIZE = 36000L;
    private static final int MAX_HASH_FUNCS = 50;

    public BloomFilter(NetworkParameters params, byte[] payloadBytes) throws ProtocolException {
        super(params, payloadBytes, 0);
    }

    public BloomFilter(int elements, double falsePositiveRate, long randomNonce) {
        this(elements, falsePositiveRate, randomNonce, BloomUpdate.UPDATE_P2PUBKEY_ONLY);
    }

    public BloomFilter(int elements, double falsePositiveRate, long randomNonce, BloomUpdate updateFlag) {
        int size = (int)(-1.0 / Math.pow(Math.log(2.0), 2.0) * (double)elements * Math.log(falsePositiveRate));
        size = Math.max(1, Math.min(size, 288000) / 8);
        this.data = new byte[size];
        this.hashFuncs = (int)((double)(this.data.length * 8) / (double)elements * Math.log(2.0));
        this.hashFuncs = Math.max(1L, Math.min(this.hashFuncs, 50L));
        this.nTweak = randomNonce;
        this.nFlags = (byte)(255 & updateFlag.ordinal());
    }

    public double getFalsePositiveRate(int elements) {
        return Math.pow(1.0 - Math.pow(2.718281828459045, -1.0 * (double)(this.hashFuncs * (long)elements) / (double)(this.data.length * 8)), this.hashFuncs);
    }

    public String toString() {
        return "Bloom Filter of size " + this.data.length + " with " + this.hashFuncs + " hash functions.";
    }

    @Override
    protected void parse() throws ProtocolException {
        this.data = this.readByteArray();
        if ((long)this.data.length > 36000L) {
            throw new ProtocolException("Bloom filter out of size range.");
        }
        this.hashFuncs = this.readUint32();
        if (this.hashFuncs > 50L) {
            throw new ProtocolException("Bloom filter hash function count out of range");
        }
        this.nTweak = this.readUint32();
        this.nFlags = this.readBytes(1)[0];
        this.length = this.cursor - this.offset;
    }

    @Override
    protected void bitcoinSerializeToStream(OutputStream stream) throws IOException {
        stream.write(new VarInt(this.data.length).encode());
        stream.write(this.data);
        Utils.uint32ToByteStreamLE(this.hashFuncs, stream);
        Utils.uint32ToByteStreamLE(this.nTweak, stream);
        stream.write(this.nFlags);
    }

    private static int rotateLeft32(int x, int r) {
        return x << r | x >>> 32 - r;
    }

    public static int murmurHash3(byte[] data, long nTweak, int hashNum, byte[] object) {
        int h1 = (int)((long)hashNum * 4221880213L + nTweak);
        int c1 = -862048943;
        int c2 = 461845907;
        int numBlocks = object.length / 4 * 4;
        for (int i = 0; i < numBlocks; i += 4) {
            int k1 = object[i] & 255 | (object[i + 1] & 255) << 8 | (object[i + 2] & 255) << 16 | (object[i + 3] & 255) << 24;
            k1 *= -862048943;
            k1 = BloomFilter.rotateLeft32(k1, 15);
            h1 ^= (k1 *= 461845907);
            h1 = BloomFilter.rotateLeft32(h1, 13);
            h1 = h1 * 5 + -430675100;
        }
        int k1 = 0;
        switch (object.length & 3) {
            case 3: {
                k1 ^= (object[numBlocks + 2] & 255) << 16;
            }
            case 2: {
                k1 ^= (object[numBlocks + 1] & 255) << 8;
            }
            case 1: {
                k1 ^= object[numBlocks] & 255;
                k1 *= -862048943;
                k1 = BloomFilter.rotateLeft32(k1, 15);
                h1 ^= (k1 *= 461845907);
            }
        }
        h1 ^= object.length;
        h1 ^= h1 >>> 16;
        h1 *= -2048144789;
        h1 ^= h1 >>> 13;
        h1 *= -1028477387;
        h1 ^= h1 >>> 16;
        return (int)(((long)h1 & 0xFFFFFFFFL) % (long)(data.length * 8));
    }

    public synchronized boolean contains(byte[] object) {
        int i = 0;
        while ((long)i < this.hashFuncs) {
            if (!Utils.checkBitLE(this.data, BloomFilter.murmurHash3(this.data, this.nTweak, i, object))) {
                return false;
            }
            ++i;
        }
        return true;
    }

    public synchronized void insert(byte[] object) {
        int i = 0;
        while ((long)i < this.hashFuncs) {
            Utils.setBitLE(this.data, BloomFilter.murmurHash3(this.data, this.nTweak, i, object));
            ++i;
        }
    }

    public synchronized void insert(ECKey key) {
        this.insert(key.getPubKey());
        this.insert(key.getPubKeyHash());
    }

    public synchronized void setMatchAll() {
        this.data = new byte[]{-1};
    }

    public synchronized void merge(BloomFilter filter) {
        if (!this.matchesAll() && !filter.matchesAll()) {
            Preconditions.checkArgument((boolean)(filter.data.length == this.data.length && filter.hashFuncs == this.hashFuncs && filter.nTweak == this.nTweak));
            for (int i = 0; i < this.data.length; ++i) {
                byte[] arrby = this.data;
                int n = i;
                arrby[n] = (byte)(arrby[n] | filter.data[i]);
            }
        } else {
            this.data = new byte[]{-1};
        }
    }

    public synchronized boolean matchesAll() {
        for (byte b : this.data) {
            if (b == -1) continue;
            return false;
        }
        return true;
    }

    public synchronized BloomUpdate getUpdateFlag() {
        if (this.nFlags == 0) {
            return BloomUpdate.UPDATE_NONE;
        }
        if (this.nFlags == 1) {
            return BloomUpdate.UPDATE_ALL;
        }
        if (this.nFlags == 2) {
            return BloomUpdate.UPDATE_P2PUBKEY_ONLY;
        }
        throw new IllegalStateException("Unknown flag combination");
    }

    public synchronized FilteredBlock applyAndUpdate(Block block) {
        List<Transaction> txns = block.getTransactions();
        ArrayList<Sha256Hash> txHashes = new ArrayList<Sha256Hash>(txns.size());
        ArrayList<Transaction> matched = Lists.newArrayList();
        byte[] bits = new byte[(int)Math.ceil((double)txns.size() / 8.0)];
        for (int i = 0; i < txns.size(); ++i) {
            Transaction tx = txns.get(i);
            txHashes.add(tx.getHash());
            if (!this.applyAndUpdate(tx)) continue;
            Utils.setBitLE(bits, i);
            matched.add(tx);
        }
        PartialMerkleTree pmt = PartialMerkleTree.buildFromLeaves(block.getParams(), bits, txHashes);
        FilteredBlock filteredBlock = new FilteredBlock(block.getParams(), block.cloneAsHeader(), pmt);
        for (Transaction transaction : matched) {
            filteredBlock.provideTransaction(transaction);
        }
        return filteredBlock;
    }

    public synchronized boolean applyAndUpdate(Transaction tx) {
        if (this.contains(tx.getHash().getBytes())) {
            return true;
        }
        boolean found = false;
        BloomUpdate flag = this.getUpdateFlag();
        for (TransactionOutput output : tx.getOutputs()) {
            Script script = output.getScriptPubKey();
            for (ScriptChunk chunk : script.getChunks()) {
                boolean isSendingToPubKeys;
                if (!chunk.isPushData() || !this.contains(chunk.data)) continue;
                boolean bl = isSendingToPubKeys = script.isSentToRawPubKey() || script.isSentToMultiSig();
                if (flag == BloomUpdate.UPDATE_ALL || flag == BloomUpdate.UPDATE_P2PUBKEY_ONLY && isSendingToPubKeys) {
                    this.insert(output.getOutPointFor().unsafeBitcoinSerialize());
                }
                found = true;
            }
        }
        if (found) {
            return true;
        }
        for (TransactionInput input : tx.getInputs()) {
            if (this.contains(input.getOutpoint().unsafeBitcoinSerialize())) {
                return true;
            }
            for (ScriptChunk chunk : input.getScriptSig().getChunks()) {
                if (!chunk.isPushData() || !this.contains(chunk.data)) continue;
                return true;
            }
        }
        return false;
    }

    public synchronized boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        BloomFilter other = (BloomFilter)o;
        return this.hashFuncs == other.hashFuncs && this.nTweak == other.nTweak && Arrays.equals(this.data, other.data);
    }

    public synchronized int hashCode() {
        return Objects.hashCode((Object[])new Object[]{this.hashFuncs, this.nTweak, Arrays.hashCode(this.data)});
    }

    public static enum BloomUpdate {
        UPDATE_NONE,
        UPDATE_ALL,
        UPDATE_P2PUBKEY_ONLY;
        

        private BloomUpdate() {
        }
    }

}

