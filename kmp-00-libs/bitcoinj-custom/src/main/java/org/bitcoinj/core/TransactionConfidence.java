/*
 * Decompiled with CFR 0_132.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Preconditions
 *  com.google.common.collect.Sets
 *  com.google.common.util.concurrent.ListenableFuture
 *  com.google.common.util.concurrent.SettableFuture
 *  javax.annotation.Nullable
 */
package org.bitcoinj.core;

import com.google.common.base.Preconditions;
import com.google.common.collect.Sets;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.SettableFuture;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.ListIterator;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executor;
import javax.annotation.Nullable;
import org.bitcoinj.core.PeerAddress;
import org.bitcoinj.core.Sha256Hash;
import org.bitcoinj.core.Transaction;
import org.bitcoinj.core.Utils;
import org.bitcoinj.utils.ListenerRegistration;
import org.bitcoinj.utils.Threading;

public class TransactionConfidence {
    private CopyOnWriteArrayList<PeerAddress> broadcastBy = new CopyOnWriteArrayList();
    private Date lastBroadcastedAt;
    private final Sha256Hash hash;
    private CopyOnWriteArrayList<ListenerRegistration<Listener>> listeners = new CopyOnWriteArrayList();
    private int depth;
    private ConfidenceType confidenceType = ConfidenceType.UNKNOWN;
    private int appearedAtChainHeight = -1;
    private Transaction overridingTransaction;
    private Source source = Source.UNKNOWN;
    private static final Set<TransactionConfidence> pinnedConfidenceObjects = Collections.synchronizedSet(new HashSet());

    public TransactionConfidence(Sha256Hash hash) {
        this.hash = hash;
    }

    public void addEventListener(Executor executor, Listener listener) {
        Preconditions.checkNotNull((Object)listener);
        this.listeners.addIfAbsent(new ListenerRegistration<Listener>(listener, executor));
        pinnedConfidenceObjects.add(this);
    }

    public void addEventListener(Listener listener) {
        this.addEventListener(Threading.USER_THREAD, listener);
    }

    public boolean removeEventListener(Listener listener) {
        Preconditions.checkNotNull((Object)listener);
        boolean removed = ListenerRegistration.removeFromList(listener, this.listeners);
        if (this.listeners.isEmpty()) {
            pinnedConfidenceObjects.remove(this);
        }
        return removed;
    }

    public synchronized int getAppearedAtChainHeight() {
        if (this.getConfidenceType() != ConfidenceType.BUILDING) {
            throw new IllegalStateException("Confidence type is " + (Object)((Object)this.getConfidenceType()) + ", not BUILDING");
        }
        return this.appearedAtChainHeight;
    }

    public synchronized void setAppearedAtChainHeight(int appearedAtChainHeight) {
        if (appearedAtChainHeight < 0) {
            throw new IllegalArgumentException("appearedAtChainHeight out of range");
        }
        this.appearedAtChainHeight = appearedAtChainHeight;
        this.depth = 1;
        this.setConfidenceType(ConfidenceType.BUILDING);
    }

    public synchronized ConfidenceType getConfidenceType() {
        return this.confidenceType;
    }

    public synchronized void setConfidenceType(ConfidenceType confidenceType) {
        if (confidenceType == this.confidenceType) {
            return;
        }
        this.confidenceType = confidenceType;
        if (confidenceType != ConfidenceType.DEAD) {
            this.overridingTransaction = null;
        }
        if (confidenceType == ConfidenceType.PENDING || confidenceType == ConfidenceType.IN_CONFLICT) {
            this.depth = 0;
            this.appearedAtChainHeight = -1;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public boolean markBroadcastBy(PeerAddress address) {
        this.lastBroadcastedAt = Utils.now();
        if (!this.broadcastBy.addIfAbsent(address)) {
            return false;
        }
        TransactionConfidence transactionConfidence = this;
        synchronized (transactionConfidence) {
            if (this.getConfidenceType() == ConfidenceType.UNKNOWN) {
                this.confidenceType = ConfidenceType.PENDING;
            }
        }
        return true;
    }

    public int numBroadcastPeers() {
        return this.broadcastBy.size();
    }

    public Set<PeerAddress> getBroadcastBy() {
        ListIterator<PeerAddress> iterator = this.broadcastBy.listIterator();
        return Sets.newHashSet(iterator);
    }

    public boolean wasBroadcastBy(PeerAddress address) {
        return this.broadcastBy.contains(address);
    }

    public Date getLastBroadcastedAt() {
        return this.lastBroadcastedAt;
    }

    public void setLastBroadcastedAt(Date lastBroadcastedAt) {
        this.lastBroadcastedAt = lastBroadcastedAt;
    }

    public synchronized String toString() {
        StringBuilder builder = new StringBuilder();
        int peers = this.numBroadcastPeers();
        if (peers > 0) {
            builder.append("Seen by ").append(peers).append(peers > 1 ? " peers" : " peer");
            if (this.lastBroadcastedAt != null) {
                builder.append(" (most recently: ").append(Utils.dateTimeFormat(this.lastBroadcastedAt)).append(")");
            }
            builder.append(". ");
        }
        switch (this.getConfidenceType()) {
            case UNKNOWN: {
                builder.append("Unknown confidence level.");
                break;
            }
            case DEAD: {
                builder.append("Dead: overridden by double spend and will not confirm.");
                break;
            }
            case PENDING: {
                builder.append("Pending/unconfirmed.");
                break;
            }
            case IN_CONFLICT: {
                builder.append("In conflict.");
                break;
            }
            case BUILDING: {
                builder.append(String.format(Locale.US, "Appeared in best chain at height %d, depth %d.", this.getAppearedAtChainHeight(), this.getDepthInBlocks()));
            }
        }
        if (this.source != Source.UNKNOWN) {
            builder.append(" Source: ").append((Object)this.source);
        }
        return builder.toString();
    }

    public synchronized int incrementDepthInBlocks() {
        return ++this.depth;
    }

    public synchronized int getDepthInBlocks() {
        return this.depth;
    }

    public synchronized void setDepthInBlocks(int depth) {
        this.depth = depth;
    }

    public void clearBroadcastBy() {
        Preconditions.checkState((boolean)(this.getConfidenceType() != ConfidenceType.PENDING));
        this.broadcastBy.clear();
        this.lastBroadcastedAt = null;
    }

    public synchronized Transaction getOverridingTransaction() {
        if (this.getConfidenceType() != ConfidenceType.DEAD) {
            throw new IllegalStateException("Confidence type is " + (Object)((Object)this.getConfidenceType()) + ", not DEAD");
        }
        return this.overridingTransaction;
    }

    public synchronized void setOverridingTransaction(@Nullable Transaction overridingTransaction) {
        this.overridingTransaction = overridingTransaction;
        this.setConfidenceType(ConfidenceType.DEAD);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public TransactionConfidence duplicate() {
        TransactionConfidence c = new TransactionConfidence(this.hash);
        c.broadcastBy.addAll(this.broadcastBy);
        c.lastBroadcastedAt = this.lastBroadcastedAt;
        TransactionConfidence transactionConfidence = this;
        synchronized (transactionConfidence) {
            c.confidenceType = this.confidenceType;
            c.overridingTransaction = this.overridingTransaction;
            c.appearedAtChainHeight = this.appearedAtChainHeight;
        }
        return c;
    }

    public void queueListeners(final Listener.ChangeReason reason) {
        for (final ListenerRegistration<Listener> registration : this.listeners) {
            registration.executor.execute(new Runnable(){

                @Override
                public void run() {
                    ((Listener)registration.listener).onConfidenceChanged(TransactionConfidence.this, reason);
                }
            });
        }
    }

    public synchronized Source getSource() {
        return this.source;
    }

    public synchronized void setSource(Source source) {
        this.source = source;
    }

    public synchronized ListenableFuture<TransactionConfidence> getDepthFuture(final int depth, Executor executor) {
        final SettableFuture result = SettableFuture.create();
        if (this.getDepthInBlocks() >= depth) {
            result.set((Object)this);
        }
        this.addEventListener(executor, new Listener(){

            @Override
            public void onConfidenceChanged(TransactionConfidence confidence, Listener.ChangeReason reason) {
                if (TransactionConfidence.this.getDepthInBlocks() >= depth) {
                    TransactionConfidence.this.removeEventListener(this);
                    result.set((Object)confidence);
                }
            }
        });
        return result;
    }

    public synchronized ListenableFuture<TransactionConfidence> getDepthFuture(int depth) {
        return this.getDepthFuture(depth, Threading.USER_THREAD);
    }

    public Sha256Hash getTransactionHash() {
        return this.hash;
    }

    public static interface Listener {
        public void onConfidenceChanged(TransactionConfidence var1, ChangeReason var2);

        public static enum ChangeReason {
            TYPE,
            DEPTH,
            SEEN_PEERS;
            

            private ChangeReason() {
            }
        }

    }

    public static enum Source {
        UNKNOWN,
        NETWORK,
        SELF;
        

        private Source() {
        }
    }

    public static enum ConfidenceType {
        BUILDING(1),
        PENDING(2),
        DEAD(4),
        IN_CONFLICT(5),
        UNKNOWN(0);
        
        private int value;

        private ConfidenceType(int value) {
            this.value = value;
        }

        public int getValue() {
            return this.value;
        }
    }

}

