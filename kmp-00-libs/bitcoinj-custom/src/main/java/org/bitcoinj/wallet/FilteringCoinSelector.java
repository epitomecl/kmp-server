/*
 * Decompiled with CFR 0_132.
 */
package org.bitcoinj.wallet;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import org.bitcoinj.core.Coin;
import org.bitcoinj.core.Transaction;
import org.bitcoinj.core.TransactionInput;
import org.bitcoinj.core.TransactionOutPoint;
import org.bitcoinj.core.TransactionOutput;
import org.bitcoinj.wallet.CoinSelection;
import org.bitcoinj.wallet.CoinSelector;

public class FilteringCoinSelector
implements CoinSelector {
    protected CoinSelector delegate;
    protected HashSet<TransactionOutPoint> spent = new HashSet();

    public FilteringCoinSelector(CoinSelector delegate) {
        this.delegate = delegate;
    }

    public void excludeOutputsSpentBy(Transaction tx) {
        for (TransactionInput input : tx.getInputs()) {
            this.spent.add(input.getOutpoint());
        }
    }

    @Override
    public CoinSelection select(Coin target, List<TransactionOutput> candidates) {
        Iterator<TransactionOutput> iter = candidates.iterator();
        while (iter.hasNext()) {
            TransactionOutput output = iter.next();
            if (!this.spent.contains(output.getOutPointFor())) continue;
            iter.remove();
        }
        return this.delegate.select(target, candidates);
    }
}

