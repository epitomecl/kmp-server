/*
 * Decompiled with CFR 0_132.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Objects
 *  com.google.common.base.Preconditions
 *  com.google.common.io.BaseEncoding
 *  javax.annotation.Nullable
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.spongycastle.util.Arrays
 */
package org.bitcoinj.core;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.io.BaseEncoding;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import javax.annotation.Nullable;
import org.bitcoinj.core.Address;
import org.bitcoinj.core.ChildMessage;
import org.bitcoinj.core.Coin;
import org.bitcoinj.core.ECKey;
import org.bitcoinj.core.Message;
import org.bitcoinj.core.MessageSerializer;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.core.ProtocolException;
import org.bitcoinj.core.Sha256Hash;
import org.bitcoinj.core.Transaction;
import org.bitcoinj.core.TransactionBag;
import org.bitcoinj.core.TransactionConfidence;
import org.bitcoinj.core.TransactionInput;
import org.bitcoinj.core.TransactionOutPoint;
import org.bitcoinj.core.Utils;
import org.bitcoinj.core.VarInt;
import org.bitcoinj.script.Script;
import org.bitcoinj.script.ScriptBuilder;
import org.bitcoinj.script.ScriptException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongycastle.util.Arrays;

public class TransactionOutput
extends ChildMessage {
    private static final Logger log = LoggerFactory.getLogger(TransactionOutput.class);
    private long value;
    private byte[] scriptBytes;
    private Script scriptPubKey;
    private boolean availableForSpending;
    @Nullable
    private TransactionInput spentBy;
    private int scriptLen;

    public TransactionOutput(NetworkParameters params, @Nullable Transaction parent, byte[] payload, int offset) throws ProtocolException {
        super(params, payload, offset);
        this.setParent(parent);
        this.availableForSpending = true;
    }

    public TransactionOutput(NetworkParameters params, @Nullable Transaction parent, byte[] payload, int offset, MessageSerializer serializer) throws ProtocolException {
        super(params, payload, offset, parent, serializer, Integer.MIN_VALUE);
        this.availableForSpending = true;
    }

    public TransactionOutput(NetworkParameters params, @Nullable Transaction parent, Coin value, Address to) {
        this(params, parent, value, ScriptBuilder.createOutputScript(to).getProgram());
    }

    public TransactionOutput(NetworkParameters params, @Nullable Transaction parent, Coin value, ECKey to) {
        this(params, parent, value, ScriptBuilder.createOutputScript(to).getProgram());
    }

    public TransactionOutput(NetworkParameters params, @Nullable Transaction parent, Coin value, byte[] scriptBytes) {
        super(params);
        Preconditions.checkArgument((boolean)(value.signum() >= 0 || value.equals(Coin.NEGATIVE_SATOSHI)), (Object)"Negative values not allowed");
        Preconditions.checkArgument((boolean)(!params.hasMaxMoney() || value.compareTo(params.getMaxMoney()) <= 0), (Object)"Values larger than MAX_MONEY not allowed");
        this.value = value.value;
        this.scriptBytes = scriptBytes;
        this.setParent(parent);
        this.availableForSpending = true;
        this.length = 8 + VarInt.sizeOf(scriptBytes.length) + scriptBytes.length;
    }

    public Script getScriptPubKey() throws ScriptException {
        if (this.scriptPubKey == null) {
            this.scriptPubKey = new Script(this.scriptBytes);
        }
        return this.scriptPubKey;
    }

    @Nullable
    public Address getAddressFromP2PKHScript(NetworkParameters networkParameters) throws ScriptException {
        if (this.getScriptPubKey().isSentToAddress()) {
            return this.getScriptPubKey().getToAddress(networkParameters);
        }
        return null;
    }

    @Nullable
    public Address getAddressFromP2SH(NetworkParameters networkParameters) throws ScriptException {
        if (this.getScriptPubKey().isPayToScriptHash()) {
            return this.getScriptPubKey().getToAddress(networkParameters);
        }
        return null;
    }

    @Override
    protected void parse() throws ProtocolException {
        this.value = this.readInt64();
        this.scriptLen = (int)this.readVarInt();
        this.length = this.cursor - this.offset + this.scriptLen;
        this.scriptBytes = this.readBytes(this.scriptLen);
    }

    @Override
    protected void bitcoinSerializeToStream(OutputStream stream) throws IOException {
        Preconditions.checkNotNull((Object)this.scriptBytes);
        Utils.int64ToByteStreamLE(this.value, stream);
        stream.write(new VarInt(this.scriptBytes.length).encode());
        stream.write(this.scriptBytes);
    }

    public Coin getValue() {
        try {
            return Coin.valueOf(this.value);
        }
        catch (IllegalArgumentException e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }

    public void setValue(Coin value) {
        Preconditions.checkNotNull((Object)value);
        this.unCache();
        this.value = value.value;
    }

    public int getIndex() {
        List<TransactionOutput> outputs = this.getParentTransaction().getOutputs();
        for (int i = 0; i < outputs.size(); ++i) {
            if (outputs.get(i) != this) continue;
            return i;
        }
        throw new IllegalStateException("Output linked to wrong parent transaction?");
    }

    public boolean isDust() {
        if (this.getScriptPubKey().isOpReturn()) {
            return false;
        }
        return this.getValue().isLessThan(this.getMinNonDustValue());
    }

    public Coin getMinNonDustValue(Coin feePerKb) {
        long size = this.unsafeBitcoinSerialize().length + 148;
        return feePerKb.multiply(size).divide(1000L);
    }

    public Coin getMinNonDustValue() {
        return this.getMinNonDustValue(Transaction.REFERENCE_DEFAULT_MIN_TX_FEE.multiply(3L));
    }

    public void markAsSpent(TransactionInput input) {
        Preconditions.checkState((boolean)this.availableForSpending);
        this.availableForSpending = false;
        this.spentBy = input;
        if (this.parent != null) {
            if (log.isDebugEnabled()) {
                log.debug("Marked {}:{} as spent by {}", new Object[]{this.getParentTransactionHash(), this.getIndex(), input});
            } else if (log.isDebugEnabled()) {
                log.debug("Marked floating output as spent by {}", (Object)input);
            }
        }
    }

    public void markAsUnspent() {
        if (this.parent != null) {
            if (log.isDebugEnabled()) {
                log.debug("Un-marked {}:{} as spent by {}", new Object[]{this.getParentTransactionHash(), this.getIndex(), this.spentBy});
            } else if (log.isDebugEnabled()) {
                log.debug("Un-marked floating output as spent by {}", (Object)this.spentBy);
            }
        }
        this.availableForSpending = true;
        this.spentBy = null;
    }

    public boolean isAvailableForSpending() {
        return this.availableForSpending;
    }

    public byte[] getScriptBytes() {
        return this.scriptBytes;
    }

    public boolean isMineOrWatched(TransactionBag transactionBag) {
        return this.isMine(transactionBag) || this.isWatched(transactionBag);
    }

    public boolean isWatched(TransactionBag transactionBag) {
        try {
            Script script = this.getScriptPubKey();
            return transactionBag.isWatchedScript(script);
        }
        catch (ScriptException e) {
            log.debug("Could not parse tx output script: {}", (Object)e.toString());
            return false;
        }
    }

    public boolean isMine(TransactionBag transactionBag) {
        try {
            Script script = this.getScriptPubKey();
            if (script.isSentToRawPubKey()) {
                byte[] pubkey = script.getPubKey();
                return transactionBag.isPubKeyMine(pubkey);
            }
            if (script.isPayToScriptHash()) {
                return transactionBag.isPayToScriptHashMine(script.getPubKeyHash());
            }
            byte[] pubkeyHash = script.getPubKeyHash();
            return transactionBag.isPubKeyHashMine(pubkeyHash);
        }
        catch (ScriptException e) {
            log.debug("Could not parse tx {} output script: {}", this.parent != null ? this.parent.getHash() : "(no parent)", (Object)e.toString());
            return false;
        }
    }

    public String toString() {
        try {
            Script script = this.getScriptPubKey();
            StringBuilder buf = new StringBuilder("TxOut of ");
            buf.append(Coin.valueOf(this.value).toFriendlyString());
            if (script.isSentToAddress() || script.isPayToScriptHash()) {
                buf.append(" to ").append(script.getToAddress(this.params));
            } else if (script.isSentToRawPubKey()) {
                buf.append(" to pubkey ").append(Utils.HEX.encode(script.getPubKey()));
            } else if (script.isSentToMultiSig()) {
                buf.append(" to multisig");
            } else {
                buf.append(" (unknown type)");
            }
            buf.append(" script:").append(script);
            return buf.toString();
        }
        catch (ScriptException e) {
            throw new RuntimeException(e);
        }
    }

    @Nullable
    public TransactionInput getSpentBy() {
        return this.spentBy;
    }

    @Nullable
    public Transaction getParentTransaction() {
        return (Transaction)this.parent;
    }

    @Nullable
    public Sha256Hash getParentTransactionHash() {
        return this.parent == null ? null : this.parent.getHash();
    }

    public int getParentTransactionDepthInBlocks() {
        TransactionConfidence confidence;
        if (this.getParentTransaction() != null && (confidence = this.getParentTransaction().getConfidence()).getConfidenceType() == TransactionConfidence.ConfidenceType.BUILDING) {
            return confidence.getDepthInBlocks();
        }
        return -1;
    }

    public TransactionOutPoint getOutPointFor() {
        return new TransactionOutPoint(this.params, (long)this.getIndex(), this.getParentTransaction());
    }

    public TransactionOutput duplicateDetached() {
        return new TransactionOutput(this.params, null, Coin.valueOf(this.value), Arrays.clone((byte[])this.scriptBytes));
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        TransactionOutput other = (TransactionOutput)o;
        return this.value == other.value && (this.parent == null || this.parent == other.parent && this.getIndex() == other.getIndex()) && java.util.Arrays.equals(this.scriptBytes, other.scriptBytes);
    }

    public int hashCode() {
        return Objects.hashCode((Object[])new Object[]{this.value, this.parent, java.util.Arrays.hashCode(this.scriptBytes)});
    }
}

