/*
 * Decompiled with CFR 0_132.
 */
package org.bitcoinj.wallet;

import java.util.Collection;
import org.bitcoinj.core.Coin;
import org.bitcoinj.core.TransactionOutput;

public class CoinSelection {
    public Coin valueGathered;
    public Collection<TransactionOutput> gathered;

    public CoinSelection(Coin valueGathered, Collection<TransactionOutput> gathered) {
        this.valueGathered = valueGathered;
        this.gathered = gathered;
    }
}

