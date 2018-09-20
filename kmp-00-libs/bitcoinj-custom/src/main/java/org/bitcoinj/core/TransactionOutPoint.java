/*
 * Decompiled with CFR 0_132.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Objects
 *  com.google.common.base.Preconditions
 *  javax.annotation.Nullable
 */
package org.bitcoinj.core;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import javax.annotation.Nullable;
import org.bitcoinj.core.ChildMessage;
import org.bitcoinj.core.ECKey;
import org.bitcoinj.core.Message;
import org.bitcoinj.core.MessageSerializer;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.core.ProtocolException;
import org.bitcoinj.core.Sha256Hash;
import org.bitcoinj.core.Transaction;
import org.bitcoinj.core.TransactionOutput;
import org.bitcoinj.core.Utils;
import org.bitcoinj.script.Script;
import org.bitcoinj.script.ScriptError;
import org.bitcoinj.script.ScriptException;
import org.bitcoinj.wallet.KeyBag;
import org.bitcoinj.wallet.RedeemData;

public class TransactionOutPoint
extends ChildMessage {
    static final int MESSAGE_LENGTH = 36;
    private Sha256Hash hash;
    private long index;
    Transaction fromTx;
    TransactionOutput connectedOutput;

    public TransactionOutPoint(NetworkParameters params, long index, @Nullable Transaction fromTx) {
        super(params);
        this.index = index;
        if (fromTx != null) {
            this.hash = fromTx.getHash();
            this.fromTx = fromTx;
        } else {
            this.hash = Sha256Hash.ZERO_HASH;
        }
        this.length = 36;
    }

    public TransactionOutPoint(NetworkParameters params, long index, Sha256Hash hash) {
        super(params);
        this.index = index;
        this.hash = hash;
        this.length = 36;
    }

    public TransactionOutPoint(NetworkParameters params, TransactionOutput connectedOutput) {
        this(params, (long)connectedOutput.getIndex(), connectedOutput.getParentTransactionHash());
        this.connectedOutput = connectedOutput;
    }

    public TransactionOutPoint(NetworkParameters params, byte[] payload, int offset) throws ProtocolException {
        super(params, payload, offset);
    }

    public TransactionOutPoint(NetworkParameters params, byte[] payload, int offset, Message parent, MessageSerializer serializer) throws ProtocolException {
        super(params, payload, offset, parent, serializer, 36);
    }

    @Override
    protected void parse() throws ProtocolException {
        this.length = 36;
        this.hash = this.readHash();
        this.index = this.readUint32();
    }

    @Override
    protected void bitcoinSerializeToStream(OutputStream stream) throws IOException {
        stream.write(this.hash.getReversedBytes());
        Utils.uint32ToByteStreamLE(this.index, stream);
    }

    @Nullable
    public TransactionOutput getConnectedOutput() {
        if (this.fromTx != null) {
            return this.fromTx.getOutputs().get((int)this.index);
        }
        if (this.connectedOutput != null) {
            return this.connectedOutput;
        }
        return null;
    }

    public byte[] getConnectedPubKeyScript() {
        byte[] result = ((TransactionOutput)Preconditions.checkNotNull((Object)this.getConnectedOutput())).getScriptBytes();
        Preconditions.checkState((boolean)(result.length > 0));
        return result;
    }

    @Nullable
    public ECKey getConnectedKey(KeyBag keyBag) throws ScriptException {
        TransactionOutput connectedOutput = this.getConnectedOutput();
        Preconditions.checkNotNull((Object)connectedOutput, (Object)"Input is not connected so cannot retrieve key");
        Script connectedScript = connectedOutput.getScriptPubKey();
        if (connectedScript.isSentToAddress()) {
            byte[] addressBytes = connectedScript.getPubKeyHash();
            return keyBag.findKeyFromPubHash(addressBytes);
        }
        if (connectedScript.isSentToRawPubKey()) {
            byte[] pubkeyBytes = connectedScript.getPubKey();
            return keyBag.findKeyFromPubKey(pubkeyBytes);
        }
        throw new ScriptException(ScriptError.SCRIPT_ERR_UNKNOWN_ERROR, "Could not understand form of connected output script: " + connectedScript);
    }

    @Nullable
    public RedeemData getConnectedRedeemData(KeyBag keyBag) throws ScriptException {
        TransactionOutput connectedOutput = this.getConnectedOutput();
        Preconditions.checkNotNull((Object)connectedOutput, (Object)"Input is not connected so cannot retrieve key");
        Script connectedScript = connectedOutput.getScriptPubKey();
        if (connectedScript.isSentToAddress()) {
            byte[] addressBytes = connectedScript.getPubKeyHash();
            return RedeemData.of(keyBag.findKeyFromPubHash(addressBytes), connectedScript);
        }
        if (connectedScript.isSentToRawPubKey()) {
            byte[] pubkeyBytes = connectedScript.getPubKey();
            return RedeemData.of(keyBag.findKeyFromPubKey(pubkeyBytes), connectedScript);
        }
        if (connectedScript.isPayToScriptHash()) {
            byte[] scriptHash = connectedScript.getPubKeyHash();
            return keyBag.findRedeemDataFromScriptHash(scriptHash);
        }
        throw new ScriptException(ScriptError.SCRIPT_ERR_UNKNOWN_ERROR, "Could not understand form of connected output script: " + connectedScript);
    }

    public String toString() {
        return this.hash + ":" + this.index;
    }

    @Override
    public Sha256Hash getHash() {
        return this.hash;
    }

    void setHash(Sha256Hash hash) {
        this.hash = hash;
    }

    public long getIndex() {
        return this.index;
    }

    public void setIndex(long index) {
        this.index = index;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        TransactionOutPoint other = (TransactionOutPoint)o;
        return this.getIndex() == other.getIndex() && this.getHash().equals(other.getHash());
    }

    public int hashCode() {
        return Objects.hashCode((Object[])new Object[]{this.getIndex(), this.getHash()});
    }
}

