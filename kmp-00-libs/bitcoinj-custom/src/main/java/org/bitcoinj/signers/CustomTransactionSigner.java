/*
 * Decompiled with CFR 0_132.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Preconditions
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package org.bitcoinj.signers;

import com.google.common.base.Preconditions;
import java.util.List;
import java.util.Map;
import org.bitcoinj.core.Coin;
import org.bitcoinj.core.ECKey;
import org.bitcoinj.core.Sha256Hash;
import org.bitcoinj.core.Transaction;
import org.bitcoinj.core.TransactionInput;
import org.bitcoinj.core.TransactionOutput;
import org.bitcoinj.crypto.ChildNumber;
import org.bitcoinj.crypto.TransactionSignature;
import org.bitcoinj.script.Script;
import org.bitcoinj.script.ScriptException;
import org.bitcoinj.signers.StatelessTransactionSigner;
import org.bitcoinj.signers.TransactionSigner;
import org.bitcoinj.wallet.KeyBag;
import org.bitcoinj.wallet.RedeemData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class CustomTransactionSigner
extends StatelessTransactionSigner {
    private static final Logger log = LoggerFactory.getLogger(CustomTransactionSigner.class);

    @Override
    public boolean isReady() {
        return true;
    }

    @Override
    public boolean signInputs(TransactionSigner.ProposedTransaction propTx, KeyBag keyBag) {
        Transaction tx = propTx.partialTx;
        int numInputs = tx.getInputs().size();
        for (int i = 0; i < numInputs; ++i) {
            TransactionInput txIn = tx.getInput(i);
            TransactionOutput txOut = txIn.getConnectedOutput();
            if (txOut == null) continue;
            Script scriptPubKey = txOut.getScriptPubKey();
            if (!scriptPubKey.isPayToScriptHash()) {
                log.warn("CustomTransactionSigner works only with P2SH transactions");
                return false;
            }
            Script inputScript = (Script)Preconditions.checkNotNull((Object)txIn.getScriptSig());
            try {
                txIn.getScriptSig().correctlySpends(tx, i, txIn.getConnectedOutput().getScriptPubKey());
                log.warn("Input {} already correctly spends output, assuming SIGHASH type used will be safe and skipping signing.", (Object)i);
                continue;
            }
            catch (ScriptException scriptException) {
                RedeemData redeemData = txIn.getConnectedRedeemData(keyBag);
                if (redeemData == null) {
                    log.warn("No redeem data found for input {}", (Object)i);
                    continue;
                }
                Sha256Hash sighash = propTx.useForkId ? tx.hashForSignatureWitness(i, redeemData.redeemScript, tx.getInput(i).getConnectedOutput().getValue(), Transaction.SigHash.ALL, false) : tx.hashForSignature(i, redeemData.redeemScript, Transaction.SigHash.ALL, false);
                SignatureAndKey sigKey = this.getSignature(sighash, propTx.keyPaths.get(scriptPubKey));
                TransactionSignature txSig = new TransactionSignature(sigKey.sig, Transaction.SigHash.ALL, false, propTx.useForkId);
                int sigIndex = inputScript.getSigInsertionIndex(sighash, sigKey.pubKey);
                inputScript = scriptPubKey.getScriptSigWithSignature(inputScript, txSig.encodeToBitcoin(), sigIndex);
                txIn.setScriptSig(inputScript);
            }
        }
        return true;
    }

    protected abstract SignatureAndKey getSignature(Sha256Hash var1, List<ChildNumber> var2);

    public class SignatureAndKey {
        public final ECKey.ECDSASignature sig;
        public final ECKey pubKey;

        public SignatureAndKey(ECKey.ECDSASignature sig, ECKey pubKey) {
            this.sig = sig;
            this.pubKey = pubKey;
        }
    }

}

