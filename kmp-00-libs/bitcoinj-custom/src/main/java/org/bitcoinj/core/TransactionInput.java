/*
 * Decompiled with CFR 0_132.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Joiner
 *  com.google.common.base.Objects
 *  com.google.common.base.Preconditions
 *  javax.annotation.Nullable
 */
package org.bitcoinj.core;

import com.google.common.base.Joiner;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.ref.WeakReference;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;
import org.bitcoinj.core.Address;
import org.bitcoinj.core.ChildMessage;
import org.bitcoinj.core.Coin;
import org.bitcoinj.core.Message;
import org.bitcoinj.core.MessageSerializer;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.core.ProtocolException;
import org.bitcoinj.core.Sha256Hash;
import org.bitcoinj.core.Transaction;
import org.bitcoinj.core.TransactionOutPoint;
import org.bitcoinj.core.TransactionOutput;
import org.bitcoinj.core.Utils;
import org.bitcoinj.core.VarInt;
import org.bitcoinj.core.VerificationException;
import org.bitcoinj.script.Script;
import org.bitcoinj.script.ScriptError;
import org.bitcoinj.script.ScriptException;
import org.bitcoinj.wallet.KeyBag;
import org.bitcoinj.wallet.RedeemData;

public class TransactionInput
extends ChildMessage {
    public static final long NO_SEQUENCE = 0xFFFFFFFFL;
    public static final long SEQUENCE_LOCKTIME_DISABLE_FLAG = 0x80000000L;
    public static final long SEQUENCE_LOCKTIME_TYPE_FLAG = 0x400000L;
    public static final long SEQUENCE_LOCKTIME_MASK = 65535L;
    private static final byte[] EMPTY_ARRAY = new byte[0];
    private static final long UNCONNECTED = 0xFFFFFFFFL;
    private long sequence;
    private TransactionOutPoint outpoint;
    private byte[] scriptBytes;
    private WeakReference<Script> scriptSig;
    @Nullable
    private Coin value;

    public TransactionInput(NetworkParameters params, @Nullable Transaction parentTransaction, byte[] scriptBytes) {
        this(params, parentTransaction, scriptBytes, new TransactionOutPoint(params, 0xFFFFFFFFL, (Transaction)null));
    }

    public TransactionInput(NetworkParameters params, @Nullable Transaction parentTransaction, byte[] scriptBytes, TransactionOutPoint outpoint) {
        this(params, parentTransaction, scriptBytes, outpoint, null);
    }

    public TransactionInput(NetworkParameters params, @Nullable Transaction parentTransaction, byte[] scriptBytes, TransactionOutPoint outpoint, @Nullable Coin value) {
        super(params);
        this.scriptBytes = scriptBytes;
        this.outpoint = outpoint;
        this.sequence = 0xFFFFFFFFL;
        this.value = value;
        this.setParent(parentTransaction);
        this.length = 40 + (scriptBytes == null ? 1 : VarInt.sizeOf(scriptBytes.length) + scriptBytes.length);
    }

    TransactionInput(NetworkParameters params, Transaction parentTransaction, TransactionOutput output) {
        super(params);
        long outputIndex = output.getIndex();
        this.outpoint = output.getParentTransaction() != null ? new TransactionOutPoint(params, outputIndex, output.getParentTransaction()) : new TransactionOutPoint(params, output);
        this.scriptBytes = EMPTY_ARRAY;
        this.sequence = 0xFFFFFFFFL;
        this.setParent(parentTransaction);
        this.value = output.getValue();
        this.length = 41;
    }

    public TransactionInput(NetworkParameters params, @Nullable Transaction parentTransaction, byte[] payload, int offset) throws ProtocolException {
        super(params, payload, offset);
        this.setParent(parentTransaction);
        this.value = null;
    }

    public TransactionInput(NetworkParameters params, Transaction parentTransaction, byte[] payload, int offset, MessageSerializer serializer) throws ProtocolException {
        super(params, payload, offset, parentTransaction, serializer, Integer.MIN_VALUE);
        this.value = null;
    }

    @Override
    protected void parse() throws ProtocolException {
        this.outpoint = new TransactionOutPoint(this.params, this.payload, this.cursor, this, this.serializer);
        this.cursor += this.outpoint.getMessageSize();
        int scriptLen = (int)this.readVarInt();
        this.length = this.cursor - this.offset + scriptLen + 4;
        this.scriptBytes = this.readBytes(scriptLen);
        this.sequence = this.readUint32();
    }

    @Override
    protected void bitcoinSerializeToStream(OutputStream stream) throws IOException {
        this.outpoint.bitcoinSerialize(stream);
        stream.write(new VarInt(this.scriptBytes.length).encode());
        stream.write(this.scriptBytes);
        Utils.uint32ToByteStreamLE(this.sequence, stream);
    }

    public boolean isCoinBase() {
        return this.outpoint.getHash().equals(Sha256Hash.ZERO_HASH) && (this.outpoint.getIndex() & 0xFFFFFFFFL) == 0xFFFFFFFFL;
    }

    public Script getScriptSig() throws ScriptException {
        Script script;
        Script script2 = script = this.scriptSig == null ? null : this.scriptSig.get();
        if (script == null) {
            script = new Script(this.scriptBytes);
            this.scriptSig = new WeakReference<Script>(script);
        }
        return script;
    }

    public void setScriptSig(Script scriptSig) {
        this.scriptSig = new WeakReference<>(Preconditions.checkNotNull(scriptSig));
        this.setScriptBytes(scriptSig.getProgram());
    }

    @Deprecated
    public Address getFromAddress() throws ScriptException {
        if (this.isCoinBase()) {
            throw new ScriptException(ScriptError.SCRIPT_ERR_UNKNOWN_ERROR, "This is a coinbase transaction which generates new coins. It does not have a from address.");
        }
        return this.getScriptSig().getFromAddress(this.params);
    }

    public long getSequenceNumber() {
        return this.sequence;
    }

    public void setSequenceNumber(long sequence) {
        this.unCache();
        this.sequence = sequence;
    }

    public TransactionOutPoint getOutpoint() {
        return this.outpoint;
    }

    public byte[] getScriptBytes() {
        return this.scriptBytes;
    }

    public void clearScriptBytes() {
        this.setScriptBytes(EMPTY_ARRAY);
    }

    void setScriptBytes(byte[] scriptBytes) {
        this.unCache();
        this.scriptSig = null;
        int oldLength = this.length;
        this.scriptBytes = scriptBytes;
        int newLength = 40 + (scriptBytes == null ? 1 : VarInt.sizeOf(scriptBytes.length) + scriptBytes.length);
        this.adjustLength(newLength - oldLength);
    }

    public Transaction getParentTransaction() {
        return (Transaction)this.parent;
    }

    @Nullable
    public Coin getValue() {
        return this.value;
    }

    @Nullable
    TransactionOutput getConnectedOutput(Map<Sha256Hash, Transaction> transactions) {
        Transaction tx = transactions.get(this.outpoint.getHash());
        if (tx == null) {
            return null;
        }
        return tx.getOutputs().get((int)this.outpoint.getIndex());
    }

    @Nullable
    public RedeemData getConnectedRedeemData(KeyBag keyBag) throws ScriptException {
        return this.getOutpoint().getConnectedRedeemData(keyBag);
    }

    public ConnectionResult connect(Map<Sha256Hash, Transaction> transactions, ConnectMode mode) {
        Transaction tx = transactions.get(this.outpoint.getHash());
        if (tx == null) {
            return ConnectionResult.NO_SUCH_TX;
        }
        return this.connect(tx, mode);
    }

    public ConnectionResult connect(Transaction transaction, ConnectMode mode) {
        if (!transaction.getHash().equals(this.outpoint.getHash())) {
            return ConnectionResult.NO_SUCH_TX;
        }
        Preconditions.checkElementIndex((int)((int)this.outpoint.getIndex()), (int)transaction.getOutputs().size(), (String)"Corrupt transaction");
        TransactionOutput out = transaction.getOutput((int)this.outpoint.getIndex());
        if (!out.isAvailableForSpending()) {
            if (this.getParentTransaction().equals(this.outpoint.fromTx)) {
                return ConnectionResult.SUCCESS;
            }
            if (mode == ConnectMode.DISCONNECT_ON_CONFLICT) {
                out.markAsUnspent();
            } else if (mode == ConnectMode.ABORT_ON_CONFLICT) {
                this.outpoint.fromTx = out.getParentTransaction();
                return ConnectionResult.ALREADY_SPENT;
            }
        }
        this.connect(out);
        return ConnectionResult.SUCCESS;
    }

    public void connect(TransactionOutput out) {
        this.outpoint.fromTx = out.getParentTransaction();
        out.markAsSpent(this);
        this.value = out.getValue();
    }

    public boolean disconnect() {
        TransactionOutput connectedOutput;
        if (this.outpoint.fromTx != null) {
            connectedOutput = this.outpoint.fromTx.getOutput((int)this.outpoint.getIndex());
            this.outpoint.fromTx = null;
        } else if (this.outpoint.connectedOutput != null) {
            connectedOutput = this.outpoint.connectedOutput;
            this.outpoint.connectedOutput = null;
        } else {
            return false;
        }
        if (connectedOutput != null && connectedOutput.getSpentBy() == this) {
            connectedOutput.markAsUnspent();
            return true;
        }
        return false;
    }

    public boolean hasSequence() {
        return this.sequence != 0xFFFFFFFFL;
    }

    public boolean isOptInFullRBF() {
        return this.sequence < 0xFFFFFFFEL;
    }

    public boolean hasRelativeLockTime() {
        return (this.sequence & 0x80000000L) == 0L;
    }

    public void verify() throws VerificationException {
        Transaction fromTx = this.getOutpoint().fromTx;
        long spendingIndex = this.getOutpoint().getIndex();
        Preconditions.checkNotNull((Object)fromTx, (Object)"Not connected");
        TransactionOutput output = fromTx.getOutput((int)spendingIndex);
        this.verify(output);
    }

    public void verify(TransactionOutput output) throws VerificationException {
        if (output.parent != null) {
            if (!this.getOutpoint().getHash().equals(output.getParentTransaction().getHash())) {
                throw new VerificationException("This input does not refer to the tx containing the output.");
            }
            if (this.getOutpoint().getIndex() != (long)output.getIndex()) {
                throw new VerificationException("This input refers to a different output on the given tx.");
            }
        }
        Script pubKey = output.getScriptPubKey();
        int myIndex = this.getParentTransaction().getInputs().indexOf(this);
        this.getScriptSig().correctlySpends(this.getParentTransaction(), myIndex, pubKey);
    }

    @Nullable
    public TransactionOutput getConnectedOutput() {
        return this.getOutpoint().getConnectedOutput();
    }

    @Nullable
    public Transaction getConnectedTransaction() {
        return this.getOutpoint().fromTx;
    }

    public TransactionInput duplicateDetached() {
        return new TransactionInput(this.params, null, this.bitcoinSerialize(), 0);
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        TransactionInput other = (TransactionInput)o;
        return this.sequence == other.sequence && this.parent == other.parent && this.outpoint.equals(other.outpoint) && Arrays.equals(this.scriptBytes, other.scriptBytes);
    }

    public int hashCode() {
        return Objects.hashCode((Object[])new Object[]{this.sequence, this.outpoint, Arrays.hashCode(this.scriptBytes)});
    }

    public String toString() {
        StringBuilder s = new StringBuilder("TxIn");
        try {
            if (this.isCoinBase()) {
                s.append(": COINBASE");
            } else {
                s.append(" for [").append(this.outpoint).append("]: ").append(this.getScriptSig());
                String flags = Joiner.on((String)", ").skipNulls().join(this.hasSequence() ? "sequence: " + Long.toHexString(this.sequence) : null, (Object)(this.isOptInFullRBF() ? "opts into full RBF" : null), new Object[0]);
                if (!flags.isEmpty()) {
                    s.append(" (").append(flags).append(')');
                }
            }
            return s.toString();
        }
        catch (ScriptException e) {
            throw new RuntimeException(e);
        }
    }

    public static enum ConnectMode {
        DISCONNECT_ON_CONFLICT,
        ABORT_ON_CONFLICT;
        

        private ConnectMode() {
        }
    }

    public static enum ConnectionResult {
        NO_SUCH_TX,
        ALREADY_SPENT,
        SUCCESS;
        

        private ConnectionResult() {
        }
    }

}

