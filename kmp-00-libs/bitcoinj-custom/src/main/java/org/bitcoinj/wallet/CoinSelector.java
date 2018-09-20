/*
 * Decompiled with CFR 0_132.
 */
package org.bitcoinj.wallet;

import java.util.List;
import org.bitcoinj.core.Coin;
import org.bitcoinj.core.TransactionOutput;
import org.bitcoinj.wallet.CoinSelection;

public interface CoinSelector {
    public CoinSelection select(Coin var1, List<TransactionOutput> var2);
}

