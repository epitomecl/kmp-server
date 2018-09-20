/*
 * Decompiled with CFR 0_132.
 */
package org.bitcoinj.signers;

import org.bitcoinj.signers.TransactionSigner;

public abstract class StatelessTransactionSigner
implements TransactionSigner {
    @Override
    public void deserialize(byte[] data) {
    }

    @Override
    public byte[] serialize() {
        return new byte[0];
    }
}

