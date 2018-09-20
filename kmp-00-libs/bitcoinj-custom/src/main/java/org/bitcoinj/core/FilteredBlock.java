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
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.bitcoinj.core.Block;
import org.bitcoinj.core.Message;
import org.bitcoinj.core.MessageSerializer;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.core.PartialMerkleTree;
import org.bitcoinj.core.ProtocolException;
import org.bitcoinj.core.Sha256Hash;
import org.bitcoinj.core.Transaction;
import org.bitcoinj.core.VerificationException;

public class FilteredBlock
extends Message {
    private Block header;
    private PartialMerkleTree merkleTree;
    private List<Sha256Hash> cachedTransactionHashes = null;
    private Map<Sha256Hash, Transaction> associatedTransactions = new HashMap<Sha256Hash, Transaction>();

    public FilteredBlock(NetworkParameters params, byte[] payloadBytes) throws ProtocolException {
        super(params, payloadBytes, 0);
    }

    public FilteredBlock(NetworkParameters params, Block header, PartialMerkleTree pmt) {
        super(params);
        this.header = header;
        this.merkleTree = pmt;
    }

    @Override
    public void bitcoinSerializeToStream(OutputStream stream) throws IOException {
        if (this.header.transactions == null) {
            this.header.bitcoinSerializeToStream(stream);
        } else {
            this.header.cloneAsHeader().bitcoinSerializeToStream(stream);
        }
        this.merkleTree.bitcoinSerializeToStream(stream);
    }

    @Override
    protected void parse() throws ProtocolException {
        byte[] headerBytes = new byte[80];
        System.arraycopy(this.payload, 0, headerBytes, 0, 80);
        this.header = this.params.getDefaultSerializer().makeBlock(headerBytes);
        this.merkleTree = new PartialMerkleTree(this.params, this.payload, 80);
        this.length = 80 + this.merkleTree.getMessageSize();
    }

    public List<Sha256Hash> getTransactionHashes() throws VerificationException {
        if (this.cachedTransactionHashes != null) {
            return Collections.unmodifiableList(this.cachedTransactionHashes);
        }
        LinkedList<Sha256Hash> hashesMatched = new LinkedList<Sha256Hash>();
        if (this.header.getMerkleRoot().equals(this.merkleTree.getTxnHashAndMerkleRoot(hashesMatched))) {
            this.cachedTransactionHashes = hashesMatched;
            return Collections.unmodifiableList(this.cachedTransactionHashes);
        }
        throw new VerificationException("Merkle root of block header does not match merkle root of partial merkle tree.");
    }

    public Block getBlockHeader() {
        return this.header.cloneAsHeader();
    }

    @Override
    public Sha256Hash getHash() {
        return this.header.getHash();
    }

    public boolean provideTransaction(Transaction tx) throws VerificationException {
        Sha256Hash hash = tx.getHash();
        if (this.getTransactionHashes().contains(hash)) {
            this.associatedTransactions.put(hash, tx);
            return true;
        }
        return false;
    }

    public PartialMerkleTree getPartialMerkleTree() {
        return this.merkleTree;
    }

    public Map<Sha256Hash, Transaction> getAssociatedTransactions() {
        return Collections.unmodifiableMap(this.associatedTransactions);
    }

    public int getTransactionCount() {
        return this.merkleTree.getTransactionCount();
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        FilteredBlock other = (FilteredBlock)o;
        return this.associatedTransactions.equals(other.associatedTransactions) && this.header.equals(other.header) && this.merkleTree.equals(other.merkleTree);
    }

    public int hashCode() {
        return Objects.hashCode((Object[])new Object[]{this.associatedTransactions, this.header, this.merkleTree});
    }

    public String toString() {
        return "FilteredBlock{merkleTree=" + this.merkleTree + ", header=" + this.header + '}';
    }
}

