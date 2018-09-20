/*
 * Decompiled with CFR 0_132.
 */
package org.bitcoinj.core;

import org.bitcoinj.core.RejectMessage;
import org.bitcoinj.core.Transaction;

public class RejectedTransactionException
extends Exception {
    private Transaction tx;
    private RejectMessage rejectMessage;

    public RejectedTransactionException(Transaction tx, RejectMessage rejectMessage) {
        super(rejectMessage.toString());
        this.tx = tx;
        this.rejectMessage = rejectMessage;
    }

    public Transaction getTransaction() {
        return this.tx;
    }

    public RejectMessage getRejectMessage() {
        return this.rejectMessage;
    }
}

