/*
 * Decompiled with CFR 0_132.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package org.bitcoinj.signers;

import com.google.common.collect.ImmutableList;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.bitcoinj.core.Coin;
import org.bitcoinj.core.ECKey;
import org.bitcoinj.core.Transaction;
import org.bitcoinj.core.TransactionInput;
import org.bitcoinj.core.TransactionOutput;
import org.bitcoinj.crypto.ChildNumber;
import org.bitcoinj.crypto.DeterministicKey;
import org.bitcoinj.crypto.TransactionSignature;
import org.bitcoinj.script.Script;
import org.bitcoinj.script.ScriptException;
import org.bitcoinj.signers.StatelessTransactionSigner;
import org.bitcoinj.signers.TransactionSigner;
import org.bitcoinj.wallet.KeyBag;
import org.bitcoinj.wallet.RedeemData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LocalTransactionSigner
extends StatelessTransactionSigner {
    private static final Logger log = LoggerFactory.getLogger(LocalTransactionSigner.class);
    private static final EnumSet<Script.VerifyFlag> MINIMUM_VERIFY_FLAGS = EnumSet.of(Script.VerifyFlag.P2SH, Script.VerifyFlag.NULLDUMMY);

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
            if (txIn.getConnectedOutput() == null) {
                log.warn("Missing connected output, assuming input {} is already signed.", (Object)i);
                continue;
            }
            try {
                txIn.getScriptSig().correctlySpends(tx, i, txIn.getConnectedOutput().getScriptPubKey(), txIn.getConnectedOutput().getValue(), MINIMUM_VERIFY_FLAGS);
                log.warn("Input {} already correctly spends output, assuming SIGHASH type used will be safe and skipping signing.", (Object)i);
                continue;
            }
            catch (ScriptException scriptException) {
                ECKey key;
                RedeemData redeemData = txIn.getConnectedRedeemData(keyBag);
                Script scriptPubKey = txIn.getConnectedOutput().getScriptPubKey();
                ECKey pubKey = redeemData.keys.get(0);
                if (pubKey instanceof DeterministicKey) {
                    propTx.keyPaths.put(scriptPubKey, (List<ChildNumber>)((DeterministicKey)pubKey).getPath());
                }
                if ((key = redeemData.getFullKey()) == null) {
                    log.warn("No local key found for input {}", (Object)i);
                    continue;
                }
                Script inputScript = txIn.getScriptSig();
                byte[] script = redeemData.redeemScript.getProgram();
                try {
                    TransactionSignature signature = propTx.useForkId ? tx.calculateWitnessSignature(i, key, script, tx.getInput(i).getConnectedOutput().getValue(), Transaction.SigHash.ALL, false) : tx.calculateSignature(i, key, script, Transaction.SigHash.ALL, false);
                    int sigIndex = 0;
                    inputScript = scriptPubKey.getScriptSigWithSignature(inputScript, signature.encodeToBitcoin(), sigIndex);
                    txIn.setScriptSig(inputScript);
                    continue;
                }
                catch (ECKey.KeyIsEncryptedException e) {
                    throw e;
                }
                catch (ECKey.MissingPrivateKeyException e) {
                    log.warn("No private key in keypair for input {}", (Object)i);
                }
            }
        }
        return true;
    }
}

