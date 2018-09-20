/*
 * Decompiled with CFR 0_132.
 * 
 * Could not load the following classes:
 *  com.google.common.annotations.VisibleForTesting
 */
package org.bitcoinj.wallet;

import com.google.common.annotations.VisibleForTesting;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.bitcoinj.core.Coin;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.core.Sha256Hash;
import org.bitcoinj.core.Transaction;
import org.bitcoinj.core.TransactionConfidence;
import org.bitcoinj.core.TransactionOutput;
import org.bitcoinj.wallet.CoinSelection;
import org.bitcoinj.wallet.CoinSelector;

public class DefaultCoinSelector
implements CoinSelector {
    @Override
    public CoinSelection select(Coin target, List<TransactionOutput> candidates) {
        ArrayList<TransactionOutput> selected = new ArrayList<TransactionOutput>();
        ArrayList<TransactionOutput> sortedOutputs = new ArrayList<TransactionOutput>(candidates);
        if (!target.equals(NetworkParameters.MAX_MONEY)) {
            DefaultCoinSelector.sortOutputs(sortedOutputs);
        }
        long total = 0L;
        for (TransactionOutput output : sortedOutputs) {
            if (total >= target.value) break;
            if (!this.shouldSelect(output.getParentTransaction())) continue;
            selected.add(output);
            total += output.getValue().value;
        }
        return new CoinSelection(Coin.valueOf(total), selected);
    }

    @VisibleForTesting
    static void sortOutputs(ArrayList<TransactionOutput> outputs) {
        Collections.sort(outputs, new Comparator<TransactionOutput>(){

            @Override
            public int compare(TransactionOutput a, TransactionOutput b) {
                int depth1 = a.getParentTransactionDepthInBlocks();
                int depth2 = b.getParentTransactionDepthInBlocks();
                Coin aValue = a.getValue();
                Coin bValue = b.getValue();
                BigInteger aCoinDepth = BigInteger.valueOf(aValue.value).multiply(BigInteger.valueOf(depth1));
                BigInteger bCoinDepth = BigInteger.valueOf(bValue.value).multiply(BigInteger.valueOf(depth2));
                int c1 = bCoinDepth.compareTo(aCoinDepth);
                if (c1 != 0) {
                    return c1;
                }
                int c2 = bValue.compareTo(aValue);
                if (c2 != 0) {
                    return c2;
                }
                BigInteger aHash = a.getParentTransactionHash().toBigInteger();
                BigInteger bHash = b.getParentTransactionHash().toBigInteger();
                return aHash.compareTo(bHash);
            }
        });
    }

    protected boolean shouldSelect(Transaction tx) {
        if (tx != null) {
            return DefaultCoinSelector.isSelectable(tx);
        }
        return true;
    }

    public static boolean isSelectable(Transaction tx) {
        TransactionConfidence confidence = tx.getConfidence();
        TransactionConfidence.ConfidenceType type = confidence.getConfidenceType();
        return type.equals((Object)TransactionConfidence.ConfidenceType.BUILDING) || type.equals((Object)TransactionConfidence.ConfidenceType.PENDING) && confidence.getSource().equals((Object)TransactionConfidence.Source.SELF) && (confidence.numBroadcastPeers() > 1 || tx.getParams().getId().equals("org.bitcoin.regtest"));
    }

}

