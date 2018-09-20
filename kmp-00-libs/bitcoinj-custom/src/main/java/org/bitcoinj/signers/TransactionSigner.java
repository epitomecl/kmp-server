/*
 * Decompiled with CFR 0_132.
 */
package org.bitcoinj.signers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.bitcoinj.core.Transaction;
import org.bitcoinj.crypto.ChildNumber;
import org.bitcoinj.script.Script;
import org.bitcoinj.wallet.KeyBag;

public interface TransactionSigner {
    public boolean isReady();

    public byte[] serialize();

    public void deserialize(byte[] var1);

    public boolean signInputs(ProposedTransaction var1, KeyBag var2);

    public static class MissingSignatureException
    extends RuntimeException {
    }

    public static class ProposedTransaction {
        public final Transaction partialTx;
        public final Map<Script, List<ChildNumber>> keyPaths;
        boolean useForkId = false;

        public ProposedTransaction(Transaction partialTx) {
            this.partialTx = partialTx;
            this.keyPaths = new HashMap<Script, List<ChildNumber>>();
        }

        public ProposedTransaction(Transaction partialTx, boolean useForkId) {
            this.partialTx = partialTx;
            this.keyPaths = new HashMap<Script, List<ChildNumber>>();
            this.useForkId = useForkId;
        }
    }

}

