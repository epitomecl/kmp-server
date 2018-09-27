/*
 * Decompiled with CFR 0_132.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Preconditions
 *  com.google.common.base.Strings
 *  com.google.common.collect.ImmutableMap
 *  com.google.common.io.BaseEncoding
 *  com.google.common.primitives.Ints
 *  com.google.common.primitives.Longs
 *  javax.annotation.Nullable
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.spongycastle.crypto.params.KeyParameter
 */
package org.bitcoinj.core;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableMap;
import com.google.common.io.BaseEncoding;
import com.google.common.primitives.Ints;
import com.google.common.primitives.Longs;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import javax.annotation.Nullable;
import org.bitcoinj.core.Address;
import org.bitcoinj.core.ChildMessage;
import org.bitcoinj.core.Coin;
import org.bitcoinj.core.Context;
import org.bitcoinj.core.ECKey;
import org.bitcoinj.core.Message;
import org.bitcoinj.core.MessageSerializer;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.core.ProtocolException;
import org.bitcoinj.core.Sha256Hash;
import org.bitcoinj.core.TransactionBag;
import org.bitcoinj.core.TransactionConfidence;
import org.bitcoinj.core.TransactionInput;
import org.bitcoinj.core.TransactionOutPoint;
import org.bitcoinj.core.TransactionOutput;
import org.bitcoinj.core.TxConfidenceTable;
import org.bitcoinj.core.UnsafeByteArrayOutputStream;
import org.bitcoinj.core.Utils;
import org.bitcoinj.core.VarInt;
import org.bitcoinj.core.VerificationException;
import org.bitcoinj.crypto.TransactionSignature;
import org.bitcoinj.script.Script;
import org.bitcoinj.script.ScriptBuilder;
import org.bitcoinj.script.ScriptError;
import org.bitcoinj.script.ScriptException;
import org.bitcoinj.utils.ExchangeRate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongycastle.crypto.params.KeyParameter;

public class Transaction
extends ChildMessage {
    public static final Comparator<Transaction> SORT_TX_BY_UPDATE_TIME = new Comparator<Transaction>(){

        @Override
        public int compare(Transaction tx1, Transaction tx2) {
            long time2;
            long time1 = tx1.getUpdateTime().getTime();
            int updateTimeComparison = - Longs.compare((long)time1, (long)(time2 = tx2.getUpdateTime().getTime()));
            return updateTimeComparison != 0 ? updateTimeComparison : tx1.getHash().compareTo(tx2.getHash());
        }
    };
    public static final Comparator<Transaction> SORT_TX_BY_HEIGHT = new Comparator<Transaction>(){

        @Override
        public int compare(Transaction tx1, Transaction tx2) {
            TransactionConfidence confidence1 = tx1.getConfidence();
            int height1 = confidence1.getConfidenceType() == TransactionConfidence.ConfidenceType.BUILDING ? confidence1.getAppearedAtChainHeight() : -1;
            TransactionConfidence confidence2 = tx2.getConfidence();
            int height2 = confidence2.getConfidenceType() == TransactionConfidence.ConfidenceType.BUILDING ? confidence2.getAppearedAtChainHeight() : -1;
            int heightComparison = - Ints.compare((int)height1, (int)height2);
            return heightComparison != 0 ? heightComparison : tx1.getHash().compareTo(tx2.getHash());
        }
    };
    private static final Logger log = LoggerFactory.getLogger(Transaction.class);
    public static final int LOCKTIME_THRESHOLD = 500000000;
    public static final BigInteger LOCKTIME_THRESHOLD_BIG = BigInteger.valueOf(500000000L);
    public static final int MAX_STANDARD_TX_SIZE = 100000;
    public static final Coin REFERENCE_DEFAULT_MIN_TX_FEE = Coin.valueOf(5000L);
    public static final Coin DEFAULT_TX_FEE = Coin.valueOf(100000L);
    public static final Coin MIN_NONDUST_OUTPUT = Coin.valueOf(546L);
    public static final int CURRENT_VERSION = 2;
    public static final int MAX_STANDARD_VERSION = 2;
    public static final int FORKID_VERSION = 2;
    private long version;
    private ArrayList<TransactionInput> inputs;
    private ArrayList<TransactionOutput> outputs;
    private long lockTime;
    private Date updatedAt;
    private Sha256Hash hash;
    @Nullable
    private TransactionConfidence confidence;
    private Map<Sha256Hash, Integer> appearsInHashes;
    private int optimalEncodingMessageSize;
    private Purpose purpose = Purpose.UNKNOWN;
    @Nullable
    private ExchangeRate exchangeRate;
    @Nullable
    private String memo;
    @Nullable
    private Coin cachedValue;
    @Nullable
    private TransactionBag cachedForBag;
    public static final byte SIGHASH_ANYONECANPAY_VALUE = -128;

    public Transaction(NetworkParameters params) {
        super(params);
        this.version = 1L;
        this.inputs = new ArrayList();
        this.outputs = new ArrayList();
        this.length = 8;
    }

    public Transaction(NetworkParameters params, byte[] payloadBytes) throws ProtocolException {
        super(params, payloadBytes, 0);
    }

    public Transaction(NetworkParameters params, byte[] payload, int offset) throws ProtocolException {
        super(params, payload, offset);
    }

    public Transaction(NetworkParameters params, byte[] payload, int offset, @Nullable Message parent, MessageSerializer setSerializer, int length) throws ProtocolException {
        super(params, payload, offset, parent, setSerializer, length);
    }

    public Transaction(NetworkParameters params, byte[] payload, @Nullable Message parent, MessageSerializer setSerializer, int length) throws ProtocolException {
        super(params, payload, 0, parent, setSerializer, length);
    }

    @Override
    public Sha256Hash getHash() {
        if (this.hash == null) {
            this.hash = Sha256Hash.wrapReversed(Sha256Hash.hashTwice(this.unsafeBitcoinSerialize()));
        }
        return this.hash;
    }

    void setHash(Sha256Hash hash) {
        this.hash = hash;
    }

    public String getHashAsString() {
        return this.getHash().toString();
    }

    public Coin getInputSum() {
        Coin inputTotal = Coin.ZERO;
        for (TransactionInput input : this.inputs) {
            Coin inputValue = input.getValue();
            if (inputValue == null) continue;
            inputTotal = inputTotal.add(inputValue);
        }
        return inputTotal;
    }

    public Coin getValueSentToMe(TransactionBag transactionBag) {
        Coin v = Coin.ZERO;
        for (TransactionOutput o : this.outputs) {
            if (!o.isMineOrWatched(transactionBag)) continue;
            v = v.add(o.getValue());
        }
        return v;
    }

    @Nullable
    public Map<Sha256Hash, Integer> getAppearsInHashes() {
        return this.appearsInHashes != null ? ImmutableMap.copyOf(this.appearsInHashes) : null;
    }

    public boolean isPending() {
        return this.getConfidence().getConfidenceType() == TransactionConfidence.ConfidenceType.PENDING;
    }

    public void addBlockAppearance(Sha256Hash blockHash, int relativityOffset) {
        if (this.appearsInHashes == null) {
            this.appearsInHashes = new TreeMap<Sha256Hash, Integer>();
        }
        this.appearsInHashes.put(blockHash, relativityOffset);
    }

    public Coin getOutputSum() {
        Coin totalOut = Coin.ZERO;
        for (TransactionOutput output : this.outputs) {
            totalOut = totalOut.add(output.getValue());
        }
        return totalOut;
    }

    public Coin getFee() {
        Coin fee = Coin.ZERO;
        if (this.inputs.isEmpty() || this.outputs.isEmpty()) {
            return null;
        }
        for (TransactionInput input : this.inputs) {
            if (input.getValue() == null) {
                return null;
            }
            fee = fee.add(input.getValue());
        }
        for (TransactionOutput output : this.outputs) {
            fee = fee.subtract(output.getValue());
        }
        return fee;
    }

    public boolean isAnyOutputSpent() {
        for (TransactionOutput output : this.outputs) {
            if (output.isAvailableForSpending()) continue;
            return true;
        }
        return false;
    }

    public boolean isEveryOwnedOutputSpent(TransactionBag transactionBag) {
        for (TransactionOutput output : this.outputs) {
            if (!output.isAvailableForSpending() || !output.isMineOrWatched(transactionBag)) continue;
            return false;
        }
        return true;
    }

    public Date getUpdateTime() {
        if (this.updatedAt == null) {
            this.updatedAt = new Date(0L);
        }
        return this.updatedAt;
    }

    public void setUpdateTime(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    protected void unCache() {
        super.unCache();
        this.hash = null;
    }

    protected static int calcLength(byte[] buf, int offset) {
        long scriptLen;
        int cursor = offset + 4;
        VarInt varint = new VarInt(buf, cursor);
        long txInCount = varint.value;
        cursor += varint.getOriginalSizeInBytes();
        int i = 0;
        while ((long)i < txInCount) {
            varint = new VarInt(buf, cursor += 36);
            scriptLen = varint.value;
            cursor = (int)((long)cursor + (scriptLen + 4L + (long)varint.getOriginalSizeInBytes()));
            ++i;
        }
        varint = new VarInt(buf, cursor);
        long txOutCount = varint.value;
        cursor += varint.getOriginalSizeInBytes();
        i = 0;
        while ((long)i < txOutCount) {
            varint = new VarInt(buf, cursor += 8);
            scriptLen = varint.value;
            cursor = (int)((long)cursor + (scriptLen + (long)varint.getOriginalSizeInBytes()));
            ++i;
        }
        return cursor - offset + 4;
    }

    @Override
    protected void parse() throws ProtocolException {
        this.cursor = this.offset;
        this.version = this.readUint32();
        this.optimalEncodingMessageSize = 4;
        long numInputs = this.readVarInt();
        this.optimalEncodingMessageSize += VarInt.sizeOf(numInputs);
        this.inputs = new ArrayList((int)numInputs);
        for (long i = 0L; i < numInputs; ++i) {
            TransactionInput input = new TransactionInput(this.params, this, this.payload, this.cursor, this.serializer);
            this.inputs.add(input);
            long scriptLen = this.readVarInt(36);
            this.optimalEncodingMessageSize = (int)((long)this.optimalEncodingMessageSize + ((long)(36 + VarInt.sizeOf(scriptLen)) + scriptLen + 4L));
            this.cursor = (int)((long)this.cursor + (scriptLen + 4L));
        }
        long numOutputs = this.readVarInt();
        this.optimalEncodingMessageSize += VarInt.sizeOf(numOutputs);
        this.outputs = new ArrayList((int)numOutputs);
        for (long i = 0L; i < numOutputs; ++i) {
            TransactionOutput output = new TransactionOutput(this.params, this, this.payload, this.cursor, this.serializer);
            this.outputs.add(output);
            long scriptLen = this.readVarInt(8);
            this.optimalEncodingMessageSize = (int)((long)this.optimalEncodingMessageSize + ((long)(8 + VarInt.sizeOf(scriptLen)) + scriptLen));
            this.cursor = (int)((long)this.cursor + scriptLen);
        }
        this.lockTime = this.readUint32();
        this.optimalEncodingMessageSize += 4;
        this.length = this.cursor - this.offset;
    }

    public int getOptimalEncodingMessageSize() {
        if (this.optimalEncodingMessageSize != 0) {
            return this.optimalEncodingMessageSize;
        }
        this.optimalEncodingMessageSize = this.getMessageSize();
        return this.optimalEncodingMessageSize;
    }

    public int getMessageSizeForPriorityCalc() {
        int size = this.getMessageSize();
        for (TransactionInput input : this.inputs) {
            int benefit = 41 + Math.min(110, input.getScriptSig().getProgram().length);
            if (size <= benefit) continue;
            size -= benefit;
        }
        return size;
    }

    public boolean isCoinBase() {
        return this.inputs.size() == 1 && this.inputs.get(0).isCoinBase();
    }

    public boolean isMature() {
        if (!this.isCoinBase()) {
            return true;
        }
        if (this.getConfidence().getConfidenceType() != TransactionConfidence.ConfidenceType.BUILDING) {
            return false;
        }
        return this.getConfidence().getDepthInBlocks() >= this.params.getSpendableCoinbaseDepth();
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append("  ").append(this.getHashAsString()).append('\n');
        if (this.updatedAt != null) {
            s.append("  updated: ").append(Utils.dateTimeFormat(this.updatedAt)).append('\n');
        }
        if (this.version != 1L) {
            s.append("  version ").append(this.version).append('\n');
        }
        if (this.isTimeLocked()) {
            s.append("  time locked until ");
            if (this.lockTime < 500000000L) {
                s.append("block ").append(this.lockTime);
            } else {
                s.append(Utils.dateTimeFormat(this.lockTime * 1000L));
            }
            s.append('\n');
        }
        if (this.hasRelativeLockTime()) {
            s.append("  has relative lock time\n");
        }
        if (this.isOptInFullRBF()) {
            s.append("  opts into full replace-by-fee\n");
        }
        if (this.isCoinBase()) {
            String script2;
            String script;
            try {
                script = this.inputs.get(0).getScriptSig().toString();
                script2 = this.outputs.get(0).getScriptPubKey().toString();
            }
            catch (ScriptException e) {
                script = "???";
                script2 = "???";
            }
            s.append("     == COINBASE TXN (scriptSig ").append(script).append(")  (scriptPubKey ").append(script2).append(")\n");
            return s.toString();
        }
        if (!this.inputs.isEmpty()) {
            for (TransactionInput in : this.inputs) {
                s.append("     ");
                s.append("in   ");
                try {
                    Script scriptPubKey;
                    String scriptSigStr = in.getScriptSig().toString();
                    s.append(!Strings.isNullOrEmpty((String)scriptSigStr) ? scriptSigStr : "<no scriptSig>");
                    Coin value = in.getValue();
                    if (value != null) {
                        s.append(" ").append(value.toFriendlyString());
                    }
                    s.append("\n          ");
                    s.append("outpoint:");
                    TransactionOutPoint outpoint = in.getOutpoint();
                    s.append(outpoint.toString());
                    TransactionOutput connectedOutput = outpoint.getConnectedOutput();
                    if (connectedOutput != null && ((scriptPubKey = connectedOutput.getScriptPubKey()).isSentToAddress() || scriptPubKey.isPayToScriptHash())) {
                        s.append(" hash160:");
                        s.append(Utils.HEX.encode(scriptPubKey.getPubKeyHash()));
                    }
                    if (in.hasSequence()) {
                        s.append("\n          sequence:").append(Long.toHexString(in.getSequenceNumber()));
                        if (in.isOptInFullRBF()) {
                            s.append(", opts into full RBF");
                        }
                        if (this.version >= 2L && in.hasRelativeLockTime()) {
                            s.append(", has RLT");
                        }
                    }
                }
                catch (Exception e) {
                    s.append("[exception: ").append(e.getMessage()).append("]");
                }
                s.append('\n');
            }
        } else {
            s.append("     ");
            s.append("INCOMPLETE: No inputs!\n");
        }
        for (TransactionOutput out : this.outputs) {
            s.append("     ");
            s.append("out  ");
            try {
                TransactionInput spentBy;
                String scriptPubKeyStr = out.getScriptPubKey().toString();
                s.append(!Strings.isNullOrEmpty((String)scriptPubKeyStr) ? scriptPubKeyStr : "<no scriptPubKey>");
                s.append(" ");
                s.append(out.getValue().toFriendlyString());
                if (!out.isAvailableForSpending()) {
                    s.append(" Spent");
                }
                if ((spentBy = out.getSpentBy()) != null) {
                    s.append(" by ");
                    s.append(spentBy.getParentTransaction().getHashAsString());
                }
            }
            catch (Exception e) {
                s.append("[exception: ").append(e.getMessage()).append("]");
            }
            s.append('\n');
        }
        Coin fee = this.getFee();
        if (fee != null) {
            int size = this.unsafeBitcoinSerialize().length;
            s.append("     fee  ").append(fee.multiply(1000L).divide(size).toFriendlyString()).append("/kB, ").append(fee.toFriendlyString()).append(" for ").append(size).append(" bytes\n");
        }
        if (this.purpose != null) {
            s.append("     prps ").append((Object)this.purpose).append('\n');
        }
        return s.toString();
    }

    public void clearInputs() {
        this.unCache();
        for (TransactionInput input : this.inputs) {
            input.setParent(null);
        }
        this.inputs.clear();
        this.length = this.unsafeBitcoinSerialize().length;
    }

    public TransactionInput addInput(TransactionOutput from) {
        return this.addInput(new TransactionInput(this.params, this, from));
    }

    public TransactionInput addInput(TransactionInput input) {
        this.unCache();
        input.setParent(this);
        this.inputs.add(input);
        this.adjustLength(this.inputs.size(), input.length);
        return input;
    }

    public TransactionInput addInput(Sha256Hash spendTxHash, long outputIndex, Script script) {
        return this.addInput(new TransactionInput(this.params, this, script.getProgram(), new TransactionOutPoint(this.params, outputIndex, spendTxHash)));
    }

    public TransactionInput addSignedInput(TransactionOutPoint prevOut, Script scriptPubKey, ECKey sigKey, SigHash sigHash, boolean anyoneCanPay) throws ScriptException {
        Preconditions.checkState((boolean)(!this.outputs.isEmpty()), (Object)"Attempting to sign tx without outputs.");
        TransactionInput input = new TransactionInput(this.params, this, new byte[0], prevOut);
        this.addInput(input);
        Sha256Hash hash = this.hashForSignature(this.inputs.size() - 1, scriptPubKey, sigHash, anyoneCanPay);
        ECKey.ECDSASignature ecSig = sigKey.sign(hash);
        TransactionSignature txSig = new TransactionSignature(ecSig, sigHash, anyoneCanPay, false);
        if (scriptPubKey.isSentToRawPubKey()) {
            input.setScriptSig(ScriptBuilder.createInputScript(txSig));
        } else if (scriptPubKey.isSentToAddress()) {
            input.setScriptSig(ScriptBuilder.createInputScript(txSig, sigKey));
        } else {
            throw new ScriptException(ScriptError.SCRIPT_ERR_UNKNOWN_ERROR, "Don't know how to sign for this kind of scriptPubKey: " + scriptPubKey);
        }
        return input;
    }

    public TransactionInput addSignedInput(TransactionOutPoint prevOut, Script scriptPubKey, ECKey sigKey, SigHash sigHash, boolean anyoneCanPay, boolean forkId) throws ScriptException {
        Preconditions.checkState((boolean)(!this.outputs.isEmpty()), (Object)"Attempting to sign tx without outputs.");
        TransactionInput input = new TransactionInput(this.params, this, new byte[0], prevOut);
        this.addInput(input);
        Sha256Hash hash = forkId ? this.hashForSignatureWitness(this.inputs.size() - 1, scriptPubKey, prevOut.getConnectedOutput().getValue(), sigHash, anyoneCanPay) : this.hashForSignature(this.inputs.size() - 1, scriptPubKey, sigHash, anyoneCanPay);
        ECKey.ECDSASignature ecSig = sigKey.sign(hash);
        TransactionSignature txSig = new TransactionSignature(ecSig, sigHash, anyoneCanPay, forkId);
        if (scriptPubKey.isSentToRawPubKey()) {
            input.setScriptSig(ScriptBuilder.createInputScript(txSig));
        } else if (scriptPubKey.isSentToAddress()) {
            input.setScriptSig(ScriptBuilder.createInputScript(txSig, sigKey));
        } else {
            throw new ScriptException(ScriptError.SCRIPT_ERR_UNKNOWN_ERROR, "Don't know how to sign for this kind of scriptPubKey: " + scriptPubKey);
        }
        return input;
    }

    public TransactionInput addSignedInput(TransactionOutPoint prevOut, Script scriptPubKey, ECKey sigKey) throws ScriptException {
        return this.addSignedInput(prevOut, scriptPubKey, sigKey, SigHash.ALL, false);
    }

    public TransactionInput addSignedInput(TransactionOutput output, ECKey signingKey) {
        return this.addSignedInput(output.getOutPointFor(), output.getScriptPubKey(), signingKey);
    }

    public TransactionInput addSignedInput(TransactionOutput output, ECKey signingKey, SigHash sigHash, boolean anyoneCanPay) {
        return this.addSignedInput(output.getOutPointFor(), output.getScriptPubKey(), signingKey, sigHash, anyoneCanPay);
    }

    public void clearOutputs() {
        this.unCache();
        for (TransactionOutput output : this.outputs) {
            output.setParent(null);
        }
        this.outputs.clear();
        this.length = this.unsafeBitcoinSerialize().length;
    }

    public TransactionOutput addOutput(TransactionOutput to) {
        this.unCache();
        to.setParent(this);
        this.outputs.add(to);
        this.adjustLength(this.outputs.size(), to.length);
        return to;
    }

    public TransactionOutput addOutput(Coin value, Address address) {
        return this.addOutput(new TransactionOutput(this.params, this, value, address));
    }

    public TransactionOutput addOutput(Coin value, ECKey pubkey) {
        return this.addOutput(new TransactionOutput(this.params, this, value, pubkey));
    }

    public TransactionOutput addOutput(Coin value, Script script) {
        return this.addOutput(new TransactionOutput(this.params, this, value, script.getProgram()));
    }

    public TransactionSignature calculateSignature(int inputIndex, ECKey key, byte[] redeemScript, SigHash hashType, boolean anyoneCanPay) {
        Sha256Hash hash = this.hashForSignature(inputIndex, redeemScript, hashType, anyoneCanPay);
        return new TransactionSignature(key.sign(hash), hashType, anyoneCanPay);
    }

    public TransactionSignature calculateWitnessSignature(int inputIndex, ECKey key, byte[] redeemScript, Coin value, SigHash hashType, boolean anyoneCanPay) {
        Sha256Hash hash = this.hashForSignatureWitness(inputIndex, redeemScript, value, hashType, anyoneCanPay);
        return new TransactionSignature(key.sign(hash), hashType, anyoneCanPay, true);
    }

    public TransactionSignature calculateSignature(int inputIndex, ECKey key, Script redeemScript, SigHash hashType, boolean anyoneCanPay) {
        Sha256Hash hash = this.hashForSignature(inputIndex, redeemScript.getProgram(), hashType, anyoneCanPay);
        return new TransactionSignature(key.sign(hash), hashType, anyoneCanPay);
    }

    public TransactionSignature calculateWitnessSignature(int inputIndex, ECKey key, Script redeemScript, Coin value, SigHash hashType, boolean anyoneCanPay) {
        Sha256Hash hash = this.hashForSignatureWitness(inputIndex, redeemScript.getProgram(), value, hashType, anyoneCanPay);
        return new TransactionSignature(key.sign(hash), hashType, anyoneCanPay, true);
    }

    public TransactionSignature calculateSignature(int inputIndex, ECKey key, byte[] redeemScript, SigHash hashType, boolean anyoneCanPay, boolean forkId) {
        Sha256Hash hash = this.hashForSignature(inputIndex, redeemScript, hashType, anyoneCanPay);
        return new TransactionSignature(key.sign(hash), hashType, anyoneCanPay);
    }

    public TransactionSignature calculateSignature(int inputIndex, ECKey key, @Nullable KeyParameter aesKey, Script redeemScript, SigHash hashType, boolean anyoneCanPay) {
        Sha256Hash hash = this.hashForSignature(inputIndex, redeemScript.getProgram(), hashType, anyoneCanPay);
        return new TransactionSignature(key.sign(hash), hashType, anyoneCanPay, false);
    }

    public Sha256Hash hashForSignature(int inputIndex, byte[] redeemScript, SigHash type, boolean anyoneCanPay) {
        byte sigHashType = (byte)TransactionSignature.calcSigHashValue(type, anyoneCanPay);
        return this.hashForSignature(inputIndex, redeemScript, sigHashType);
    }

    public Sha256Hash hashForSignature(int inputIndex, Script redeemScript, SigHash type, boolean anyoneCanPay) {
        int sigHash = TransactionSignature.calcSigHashValue(type, anyoneCanPay);
        return this.hashForSignature(inputIndex, redeemScript.getProgram(), (byte)sigHash);
    }

    public Sha256Hash hashForSignature(int inputIndex, byte[] connectedScript, byte sigHashType) {
        try {
            int i;
            Transaction tx = this.params.getDefaultSerializer().makeTransaction(this.bitcoinSerialize());
            for (int i2 = 0; i2 < tx.inputs.size(); ++i2) {
                tx.inputs.get(i2).clearScriptBytes();
            }
            connectedScript = Script.removeAllInstancesOfOp(connectedScript, 171);
            TransactionInput input = tx.inputs.get(inputIndex);
            input.setScriptBytes(connectedScript);
            if ((sigHashType & 31) == SigHash.NONE.value) {
                tx.outputs = new ArrayList(0);
                for (i = 0; i < tx.inputs.size(); ++i) {
                    if (i == inputIndex) continue;
                    tx.inputs.get(i).setSequenceNumber(0L);
                }
            } else if ((sigHashType & 31) == SigHash.SINGLE.value) {
                if (inputIndex >= tx.outputs.size()) {
                    return Sha256Hash.wrap("0100000000000000000000000000000000000000000000000000000000000000");
                }
                tx.outputs = new ArrayList<TransactionOutput>(tx.outputs.subList(0, inputIndex + 1));
                for (i = 0; i < inputIndex; ++i) {
                    tx.outputs.set(i, new TransactionOutput(tx.params, tx, Coin.NEGATIVE_SATOSHI, new byte[0]));
                }
                for (i = 0; i < tx.inputs.size(); ++i) {
                    if (i == inputIndex) continue;
                    tx.inputs.get(i).setSequenceNumber(0L);
                }
            }
            if ((sigHashType & SigHash.ANYONECANPAY.value) == SigHash.ANYONECANPAY.value) {
                tx.inputs = new ArrayList();
                tx.inputs.add(input);
            }
            UnsafeByteArrayOutputStream bos = new UnsafeByteArrayOutputStream(tx.length == Integer.MIN_VALUE ? 256 : tx.length + 4);
            tx.bitcoinSerialize(bos);
            Utils.uint32ToByteStreamLE(255 & sigHashType, bos);
            Sha256Hash hash = Sha256Hash.twiceOf(bos.toByteArray());
            bos.close();
            return hash;
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public synchronized Sha256Hash hashForSignatureWitness(int inputIndex, Script scriptCode, Coin prevValue, SigHash type, boolean anyoneCanPay) {
        byte[] connectedScript = scriptCode.getProgram();
        return this.hashForSignatureWitness(inputIndex, connectedScript, prevValue, type, anyoneCanPay);
    }

    public synchronized Sha256Hash hashForSignatureWitness(int inputIndex, byte[] connectedScript, Coin prevValue, SigHash type, boolean anyoneCanPay) {
        byte sigHashType = (byte)TransactionSignature.calcSigHashValue(type, anyoneCanPay, true);
        UnsafeByteArrayOutputStream bos = new UnsafeByteArrayOutputStream(this.length == Integer.MIN_VALUE ? 256 : this.length + 4);
        try {
            UnsafeByteArrayOutputStream bosHashOutputs;
            int i;
            byte[] hashPrevouts = new byte[32];
            byte[] hashSequence = new byte[32];
            byte[] hashOutputs = new byte[32];
            boolean bl = anyoneCanPay = (sigHashType & -128) == -128;
            if (!anyoneCanPay) {
                UnsafeByteArrayOutputStream bosHashPrevouts = new UnsafeByteArrayOutputStream(256);
                for (i = 0; i < this.inputs.size(); ++i) {
                    bosHashPrevouts.write(this.inputs.get(i).getOutpoint().getHash().getReversedBytes());
                    Utils.uint32ToByteStreamLE(this.inputs.get(i).getOutpoint().getIndex(), bosHashPrevouts);
                }
                hashPrevouts = Sha256Hash.hashTwice(bosHashPrevouts.toByteArray());
            }
            if (!anyoneCanPay && type != SigHash.SINGLE && type != SigHash.NONE) {
                UnsafeByteArrayOutputStream bosSequence = new UnsafeByteArrayOutputStream(256);
                for (i = 0; i < this.inputs.size(); ++i) {
                    Utils.uint32ToByteStreamLE(this.inputs.get(i).getSequenceNumber(), bosSequence);
                }
                hashSequence = Sha256Hash.hashTwice(bosSequence.toByteArray());
            }
            if (type != SigHash.SINGLE && type != SigHash.NONE) {
                bosHashOutputs = new UnsafeByteArrayOutputStream(256);
                for (i = 0; i < this.outputs.size(); ++i) {
                    Utils.uint64ToByteStreamLE(BigInteger.valueOf(this.outputs.get(i).getValue().getValue()), bosHashOutputs);
                    bosHashOutputs.write(new VarInt(this.outputs.get(i).getScriptBytes().length).encode());
                    bosHashOutputs.write(this.outputs.get(i).getScriptBytes());
                }
                hashOutputs = Sha256Hash.hashTwice(bosHashOutputs.toByteArray());
            } else if (type == SigHash.SINGLE && inputIndex < this.outputs.size()) {
                bosHashOutputs = new UnsafeByteArrayOutputStream(256);
                Utils.uint64ToByteStreamLE(BigInteger.valueOf(this.outputs.get(inputIndex).getValue().getValue()), bosHashOutputs);
                bosHashOutputs.write(new VarInt(this.outputs.get(inputIndex).getScriptBytes().length).encode());
                bosHashOutputs.write(this.outputs.get(inputIndex).getScriptBytes());
                hashOutputs = Sha256Hash.hashTwice(bosHashOutputs.toByteArray());
            }
            Utils.uint32ToByteStreamLE(this.version, bos);
            bos.write(hashPrevouts);
            bos.write(hashSequence);
            bos.write(this.inputs.get(inputIndex).getOutpoint().getHash().getReversedBytes());
            Utils.uint32ToByteStreamLE(this.inputs.get(inputIndex).getOutpoint().getIndex(), bos);
            bos.write(new VarInt(connectedScript.length).encode());
            bos.write(connectedScript);
            Utils.uint64ToByteStreamLE(BigInteger.valueOf(prevValue.getValue()), bos);
            Utils.uint32ToByteStreamLE(this.inputs.get(inputIndex).getSequenceNumber(), bos);
            bos.write(hashOutputs);
            Utils.uint32ToByteStreamLE(this.lockTime, bos);
            Utils.uint32ToByteStreamLE(255 & sigHashType, bos);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
        return Sha256Hash.twiceOf(bos.toByteArray());
    }

    @Override
    protected void bitcoinSerializeToStream(OutputStream stream) throws IOException {
        Utils.uint32ToByteStreamLE(this.version, stream);
        stream.write(new VarInt(this.inputs.size()).encode());
        for (TransactionInput in : this.inputs) {
            in.bitcoinSerialize(stream);
        }
        stream.write(new VarInt(this.outputs.size()).encode());
        for (TransactionOutput out : this.outputs) {
            out.bitcoinSerialize(stream);
        }
        Utils.uint32ToByteStreamLE(this.lockTime, stream);
    }

    public long getLockTime() {
        return this.lockTime;
    }

    public void setLockTime(long lockTime) {
        this.unCache();
        boolean seqNumSet = false;
        for (TransactionInput input : this.inputs) {
            if (input.getSequenceNumber() == 0xFFFFFFFFL) continue;
            seqNumSet = true;
            break;
        }
        if (lockTime != 0L && (!seqNumSet || this.inputs.isEmpty())) {
            log.warn("You are setting the lock time on a transaction but none of the inputs have non-default sequence numbers. This will not do what you expect!");
        }
        this.lockTime = lockTime;
    }

    public long getVersion() {
        return this.version;
    }

    public void setVersion(int version) {
        this.version = version;
        this.unCache();
    }

    public List<TransactionInput> getInputs() {
        return Collections.unmodifiableList(this.inputs);
    }

    public List<TransactionOutput> getOutputs() {
        return Collections.unmodifiableList(this.outputs);
    }

    public List<TransactionOutput> getWalletOutputs(TransactionBag transactionBag) {
        LinkedList<TransactionOutput> walletOutputs = new LinkedList<TransactionOutput>();
        for (TransactionOutput o : this.outputs) {
            if (!o.isMineOrWatched(transactionBag)) continue;
            walletOutputs.add(o);
        }
        return walletOutputs;
    }

    public void shuffleOutputs() {
        Collections.shuffle(this.outputs);
    }

    public TransactionInput getInput(long index) {
        return this.inputs.get((int)index);
    }

    public TransactionOutput getOutput(long index) {
        return this.outputs.get((int)index);
    }

    public TransactionConfidence getConfidence() {
        return this.getConfidence(Context.get());
    }

    public TransactionConfidence getConfidence(Context context) {
        return this.getConfidence(context.getConfidenceTable());
    }

    public TransactionConfidence getConfidence(TxConfidenceTable table) {
        if (this.confidence == null) {
            this.confidence = table.getOrCreate(this.getHash());
        }
        return this.confidence;
    }

    public boolean hasConfidence() {
        return this.getConfidence().getConfidenceType() != TransactionConfidence.ConfidenceType.UNKNOWN;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        return this.getHash().equals(((Transaction)o).getHash());
    }

    public int hashCode() {
        return this.getHash().hashCode();
    }

    public int getSigOpCount() throws ScriptException {
        int sigOps = 0;
        for (TransactionInput input : this.inputs) {
            sigOps += Script.getSigOpCount(input.getScriptBytes());
        }
        for (TransactionOutput output : this.outputs) {
            sigOps += Script.getSigOpCount(output.getScriptBytes());
        }
        return sigOps;
    }

    public void checkCoinBaseHeight(int height) throws VerificationException {
        Preconditions.checkArgument((boolean)(height >= 0));
        Preconditions.checkState((boolean)this.isCoinBase());
        TransactionInput in = this.getInputs().get(0);
        ScriptBuilder builder = new ScriptBuilder();
        builder.number(height);
        byte[] expected = builder.build().getProgram();
        byte[] actual = in.getScriptBytes();
        if (actual.length < expected.length) {
            throw new VerificationException.CoinbaseHeightMismatch("Block height mismatch in coinbase.");
        }
        for (int scriptIdx = 0; scriptIdx < expected.length; ++scriptIdx) {
            if (actual[scriptIdx] == expected[scriptIdx]) continue;
            throw new VerificationException.CoinbaseHeightMismatch("Block height mismatch in coinbase.");
        }
    }

    public void verify() throws VerificationException {
        if (this.inputs.size() == 0 || this.outputs.size() == 0) {
            throw new VerificationException.EmptyInputsOrOutputs();
        }
        if (this.getMessageSize() > 1000000) {
            throw new VerificationException.LargerThanMaxBlockSize();
        }
        Coin valueOut = Coin.ZERO;
        HashSet<TransactionOutPoint> outpoints = new HashSet<TransactionOutPoint>();
        for (TransactionInput input : this.inputs) {
            if (outpoints.contains(input.getOutpoint())) {
                throw new VerificationException.DuplicatedOutPoint();
            }
            outpoints.add(input.getOutpoint());
        }
        try {
            for (TransactionOutput output : this.outputs) {
                if (output.getValue().signum() < 0) {
                    throw new VerificationException.NegativeValueOutput();
                }
                valueOut = valueOut.add(output.getValue());
                if (!this.params.hasMaxMoney() || valueOut.compareTo(this.params.getMaxMoney()) <= 0) continue;
                throw new IllegalArgumentException();
            }
        }
        catch (IllegalStateException e) {
            throw new VerificationException.ExcessiveValue();
        }
        catch (IllegalArgumentException e) {
            throw new VerificationException.ExcessiveValue();
        }
        if (this.isCoinBase()) {
            if (this.inputs.get(0).getScriptBytes().length < 2 || this.inputs.get(0).getScriptBytes().length > 100) {
                throw new VerificationException.CoinbaseScriptSizeOutOfRange();
            }
        } else {
            for (TransactionInput input : this.inputs) {
                if (!input.isCoinBase()) continue;
                throw new VerificationException.UnexpectedCoinbaseInput();
            }
        }
    }

    public boolean isTimeLocked() {
        if (this.getLockTime() == 0L) {
            return false;
        }
        for (TransactionInput input : this.getInputs()) {
            if (!input.hasSequence()) continue;
            return true;
        }
        return false;
    }

    public boolean hasRelativeLockTime() {
        if (this.version < 2L) {
            return false;
        }
        for (TransactionInput input : this.getInputs()) {
            if (!input.hasRelativeLockTime()) continue;
            return true;
        }
        return false;
    }

    public boolean isOptInFullRBF() {
        for (TransactionInput input : this.getInputs()) {
            if (!input.isOptInFullRBF()) continue;
            return true;
        }
        return false;
    }

    public boolean isFinal(int height, long blockTimeSeconds) {
        long time = 0;
        return time < ((time = this.getLockTime()) < 500000000L ? (long)height : blockTimeSeconds) || !this.isTimeLocked();
    }

    public Purpose getPurpose() {
        return this.purpose;
    }

    public void setPurpose(Purpose purpose) {
        this.purpose = purpose;
    }

    @Nullable
    public ExchangeRate getExchangeRate() {
        return this.exchangeRate;
    }

    public void setExchangeRate(ExchangeRate exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public String getMemo() {
        return this.memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public static enum SigHash {
        ALL(1),
        NONE(2),
        SINGLE(3),
        FORKID(64),
        ANYONECANPAY(128),
        ANYONECANPAY_ALL(129),
        ANYONECANPAY_NONE(130),
        ANYONECANPAY_SINGLE(131),
        UNSET(0);
        
        public final int value;

        private SigHash(int value) {
            this.value = value;
        }

        public byte byteValue() {
            return (byte)this.value;
        }
    }

    public static enum Purpose {
        UNKNOWN,
        USER_PAYMENT,
        KEY_ROTATION,
        ASSURANCE_CONTRACT_CLAIM,
        ASSURANCE_CONTRACT_PLEDGE,
        ASSURANCE_CONTRACT_STUB,
        RAISE_FEE;
        

        private Purpose() {
        }
    }

}

