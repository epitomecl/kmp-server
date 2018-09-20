/*
 * Decompiled with CFR 0_132.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Objects
 */
package org.bitcoinj.core;

import com.google.common.base.Objects;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.bitcoinj.core.Message;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.core.ProtocolException;
import org.bitcoinj.core.Sha256Hash;
import org.bitcoinj.core.Utils;
import org.bitcoinj.core.VarInt;
import org.bitcoinj.core.VerificationException;

public class PartialMerkleTree
extends Message {
    private int transactionCount;
    private byte[] matchedChildBits;
    private List<Sha256Hash> hashes;

    public PartialMerkleTree(NetworkParameters params, byte[] payloadBytes, int offset) throws ProtocolException {
        super(params, payloadBytes, offset);
    }

    public PartialMerkleTree(NetworkParameters params, byte[] bits, List<Sha256Hash> hashes, int origTxCount) {
        super(params);
        this.matchedChildBits = bits;
        this.hashes = hashes;
        this.transactionCount = origTxCount;
    }

    public static PartialMerkleTree buildFromLeaves(NetworkParameters params, byte[] includeBits, List<Sha256Hash> allLeafHashes) {
        int height = 0;
        while (PartialMerkleTree.getTreeWidth(allLeafHashes.size(), height) > 1) {
            ++height;
        }
        ArrayList<Boolean> bitList = new ArrayList<Boolean>();
        ArrayList<Sha256Hash> hashes = new ArrayList<Sha256Hash>();
        PartialMerkleTree.traverseAndBuild(height, 0, allLeafHashes, includeBits, bitList, hashes);
        byte[] bits = new byte[(int)Math.ceil((double)bitList.size() / 8.0)];
        for (int i = 0; i < bitList.size(); ++i) {
            if (!bitList.get(i).booleanValue()) continue;
            Utils.setBitLE(bits, i);
        }
        return new PartialMerkleTree(params, bits, hashes, allLeafHashes.size());
    }

    @Override
    public void bitcoinSerializeToStream(OutputStream stream) throws IOException {
        Utils.uint32ToByteStreamLE(this.transactionCount, stream);
        stream.write(new VarInt(this.hashes.size()).encode());
        for (Sha256Hash hash : this.hashes) {
            stream.write(hash.getReversedBytes());
        }
        stream.write(new VarInt(this.matchedChildBits.length).encode());
        stream.write(this.matchedChildBits);
    }

    @Override
    protected void parse() throws ProtocolException {
        this.transactionCount = (int)this.readUint32();
        int nHashes = (int)this.readVarInt();
        this.hashes = new ArrayList<Sha256Hash>(nHashes);
        for (int i = 0; i < nHashes; ++i) {
            this.hashes.add(this.readHash());
        }
        int nFlagBytes = (int)this.readVarInt();
        this.matchedChildBits = this.readBytes(nFlagBytes);
        this.length = this.cursor - this.offset;
    }

    private static void traverseAndBuild(int height, int pos, List<Sha256Hash> allLeafHashes, byte[] includeBits, List<Boolean> matchedChildBits, List<Sha256Hash> resultHashes) {
        boolean parentOfMatch = false;
        for (int p = pos << height; p < pos + 1 << height && p < allLeafHashes.size(); ++p) {
            if (!Utils.checkBitLE(includeBits, p)) continue;
            parentOfMatch = true;
            break;
        }
        matchedChildBits.add(parentOfMatch);
        if (height == 0 || !parentOfMatch) {
            resultHashes.add(PartialMerkleTree.calcHash(height, pos, allLeafHashes));
        } else {
            int h = height - 1;
            int p = pos * 2;
            PartialMerkleTree.traverseAndBuild(h, p, allLeafHashes, includeBits, matchedChildBits, resultHashes);
            if (p + 1 < PartialMerkleTree.getTreeWidth(allLeafHashes.size(), h)) {
                PartialMerkleTree.traverseAndBuild(h, p + 1, allLeafHashes, includeBits, matchedChildBits, resultHashes);
            }
        }
    }

    private static Sha256Hash calcHash(int height, int pos, List<Sha256Hash> hashes) {
        if (height == 0) {
            return hashes.get(pos);
        }
        int h = height - 1;
        int p = pos * 2;
        Sha256Hash left = PartialMerkleTree.calcHash(h, p, hashes);
        Sha256Hash right = p + 1 < PartialMerkleTree.getTreeWidth(hashes.size(), h) ? PartialMerkleTree.calcHash(h, p + 1, hashes) : left;
        return PartialMerkleTree.combineLeftRight(left.getBytes(), right.getBytes());
    }

    private static int getTreeWidth(int transactionCount, int height) {
        return transactionCount + (1 << height) - 1 >> height;
    }

    private Sha256Hash recursiveExtractHashes(int height, int pos, ValuesUsed used, List<Sha256Hash> matchedHashes) throws VerificationException {
        byte[] right;
        if (used.bitsUsed >= this.matchedChildBits.length * 8) {
            throw new VerificationException("PartialMerkleTree overflowed its bits array");
        }
        boolean parentOfMatch = Utils.checkBitLE(this.matchedChildBits, used.bitsUsed++);
        if (height == 0 || !parentOfMatch) {
            if (used.hashesUsed >= this.hashes.size()) {
                throw new VerificationException("PartialMerkleTree overflowed its hash array");
            }
            Sha256Hash hash = this.hashes.get(used.hashesUsed++);
            if (height == 0 && parentOfMatch) {
                matchedHashes.add(hash);
            }
            return hash;
        }
        byte[] left = this.recursiveExtractHashes(height - 1, pos * 2, used, matchedHashes).getBytes();
        if (pos * 2 + 1 < PartialMerkleTree.getTreeWidth(this.transactionCount, height - 1)) {
            right = this.recursiveExtractHashes(height - 1, pos * 2 + 1, used, matchedHashes).getBytes();
            if (Arrays.equals(right, left)) {
                throw new VerificationException("Invalid merkle tree with duplicated left/right branches");
            }
        } else {
            right = left;
        }
        return PartialMerkleTree.combineLeftRight(left, right);
    }

    private static Sha256Hash combineLeftRight(byte[] left, byte[] right) {
        return Sha256Hash.wrapReversed(Sha256Hash.hashTwice(Utils.reverseBytes(left), 0, 32, Utils.reverseBytes(right), 0, 32));
    }

    public Sha256Hash getTxnHashAndMerkleRoot(List<Sha256Hash> matchedHashesOut) throws VerificationException {
        matchedHashesOut.clear();
        if (this.transactionCount == 0) {
            throw new VerificationException("Got a CPartialMerkleTree with 0 transactions");
        }
        if (this.transactionCount > 16666) {
            throw new VerificationException("Got a CPartialMerkleTree with more transactions than is possible");
        }
        if (this.hashes.size() > this.transactionCount) {
            throw new VerificationException("Got a CPartialMerkleTree with more hashes than transactions");
        }
        if (this.matchedChildBits.length * 8 < this.hashes.size()) {
            throw new VerificationException("Got a CPartialMerkleTree with fewer matched bits than hashes");
        }
        int height = 0;
        while (PartialMerkleTree.getTreeWidth(this.transactionCount, height) > 1) {
            ++height;
        }
        ValuesUsed used = new ValuesUsed();
        Sha256Hash merkleRoot = this.recursiveExtractHashes(height, 0, used, matchedHashesOut);
        if ((used.bitsUsed + 7) / 8 != this.matchedChildBits.length || used.hashesUsed != this.hashes.size()) {
            throw new VerificationException("Got a CPartialMerkleTree that didn't need all the data it provided");
        }
        return merkleRoot;
    }

    public int getTransactionCount() {
        return this.transactionCount;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        PartialMerkleTree other = (PartialMerkleTree)o;
        return this.transactionCount == other.transactionCount && this.hashes.equals(other.hashes) && Arrays.equals(this.matchedChildBits, other.matchedChildBits);
    }

    public int hashCode() {
        return Objects.hashCode((Object[])new Object[]{this.transactionCount, this.hashes, Arrays.hashCode(this.matchedChildBits)});
    }

    public String toString() {
        return "PartialMerkleTree{transactionCount=" + this.transactionCount + ", matchedChildBits=" + Arrays.toString(this.matchedChildBits) + ", hashes=" + this.hashes + '}';
    }

    private static class ValuesUsed {
        public int bitsUsed = 0;
        public int hashesUsed = 0;

        private ValuesUsed() {
        }
    }

}

