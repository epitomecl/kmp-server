/*
 * Decompiled with CFR 0_132.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Preconditions
 *  javax.annotation.Nullable
 */
package org.bitcoinj.core;

import com.google.common.base.Preconditions;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;
import javax.annotation.Nullable;
import org.bitcoinj.core.PeerAddress;
import org.bitcoinj.core.Sha256Hash;
import org.bitcoinj.core.TransactionConfidence;
import org.bitcoinj.utils.Threading;

public class TxConfidenceTable {
    protected ReentrantLock lock = Threading.lock("txconfidencetable");
    private LinkedHashMap<Sha256Hash, WeakConfidenceReference> table;
    private ReferenceQueue<TransactionConfidence> referenceQueue;
    public static final int MAX_SIZE = 1000;

    public TxConfidenceTable(final int size) {
        this.table = new LinkedHashMap<Sha256Hash, WeakConfidenceReference>(){

            @Override
            protected boolean removeEldestEntry(Map.Entry<Sha256Hash, WeakConfidenceReference> entry) {
                return this.size() > size;
            }
        };
        this.referenceQueue = new ReferenceQueue();
    }

    public TxConfidenceTable() {
        this(1000);
    }

    private void cleanTable() {
        this.lock.lock();
        try {
            //Reference<TransactionConfidence> ref;
            Reference<?> ref;
            while ((ref = this.referenceQueue.poll()) != null) {
                WeakConfidenceReference txRef = (WeakConfidenceReference)ref;
                this.table.remove(txRef.hash);
            }
        }
        finally {
            this.lock.unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public int numBroadcastPeers(Sha256Hash txHash) {
        this.lock.lock();
        try {
            this.cleanTable();
            WeakConfidenceReference entry = this.table.get(txHash);
            if (entry == null) {
                int n = 0;
                return n;
            }
            TransactionConfidence confidence = (TransactionConfidence)entry.get();
            if (confidence == null) {
                this.table.remove(txHash);
                int n = 0;
                return n;
            }
            int n = confidence.numBroadcastPeers();
            return n;
        }
        finally {
            this.lock.unlock();
        }
    }

    public TransactionConfidence seen(Sha256Hash hash, PeerAddress byPeer) {
        boolean fresh = false;
        this.lock.lock();
        this.cleanTable();
        TransactionConfidence confidence = this.getOrCreate(hash);
        fresh = confidence.markBroadcastBy(byPeer);
        this.lock.unlock();
        if (fresh) {
            confidence.queueListeners(TransactionConfidence.Listener.ChangeReason.SEEN_PEERS);
        }
        return confidence;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public TransactionConfidence getOrCreate(Sha256Hash hash) {
        Preconditions.checkNotNull((Object)hash);
        this.lock.lock();
        try {
            TransactionConfidence confidence;
            WeakConfidenceReference reference = this.table.get(hash);
            if (reference != null && (confidence = (TransactionConfidence)reference.get()) != null) {
                TransactionConfidence transactionConfidence = confidence;
                return transactionConfidence;
            }
            TransactionConfidence newConfidence = new TransactionConfidence(hash);
            this.table.put(hash, new WeakConfidenceReference(newConfidence, this.referenceQueue));
            TransactionConfidence transactionConfidence = newConfidence;
            return transactionConfidence;
        }
        finally {
            this.lock.unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Nullable
    public TransactionConfidence get(Sha256Hash hash) {
        this.lock.lock();
        try {
            WeakConfidenceReference ref = this.table.get(hash);
            if (ref == null) {
                TransactionConfidence transactionConfidence = null;
                return transactionConfidence;
            }
            TransactionConfidence confidence = (TransactionConfidence)ref.get();
            if (confidence != null) {
                TransactionConfidence transactionConfidence = confidence;
                return transactionConfidence;
            }
            TransactionConfidence transactionConfidence = null;
            return transactionConfidence;
        }
        finally {
            this.lock.unlock();
        }
    }

    private static class WeakConfidenceReference
    extends WeakReference<TransactionConfidence> {
        public Sha256Hash hash;

        public WeakConfidenceReference(TransactionConfidence confidence, ReferenceQueue<TransactionConfidence> queue) {
            super(confidence, queue);
            this.hash = confidence.getTransactionHash();
        }
    }

}

