/*
 * Decompiled with CFR 0_132.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Objects
 */
package org.bitcoinj.core;

import com.google.common.base.Objects;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Locale;
import org.bitcoinj.core.Message;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.core.ProtocolException;
import org.bitcoinj.core.Sha256Hash;
import org.bitcoinj.core.VarInt;

public class RejectMessage
extends Message {
    private String message;
    private String reason;
    private RejectCode code;
    private Sha256Hash messageHash;

    public RejectMessage(NetworkParameters params, byte[] payload) throws ProtocolException {
        super(params, payload, 0);
    }

    public RejectMessage(NetworkParameters params, RejectCode code, Sha256Hash hash, String message, String reason) throws ProtocolException {
        super(params);
        this.code = code;
        this.messageHash = hash;
        this.message = message;
        this.reason = reason;
    }

    @Override
    protected void parse() throws ProtocolException {
        this.message = this.readStr();
        this.code = RejectCode.fromCode(this.readBytes(1)[0]);
        this.reason = this.readStr();
        if (this.message.equals("block") || this.message.equals("tx")) {
            this.messageHash = this.readHash();
        }
        this.length = this.cursor - this.offset;
    }

    @Override
    public void bitcoinSerializeToStream(OutputStream stream) throws IOException {
        byte[] messageBytes = this.message.getBytes("UTF-8");
        stream.write(new VarInt(messageBytes.length).encode());
        stream.write(messageBytes);
        stream.write(this.code.code);
        byte[] reasonBytes = this.reason.getBytes("UTF-8");
        stream.write(new VarInt(reasonBytes.length).encode());
        stream.write(reasonBytes);
        if ("block".equals(this.message) || "tx".equals(this.message)) {
            stream.write(this.messageHash.getReversedBytes());
        }
    }

    public String getRejectedMessage() {
        return this.message;
    }

    public Sha256Hash getRejectedObjectHash() {
        return this.messageHash;
    }

    public RejectCode getReasonCode() {
        return this.code;
    }

    public String getReasonString() {
        return this.reason;
    }

    public String toString() {
        Sha256Hash hash = this.getRejectedObjectHash();
        Object[] arrobject = new Object[4];
        arrobject[0] = this.getRejectedMessage();
        arrobject[1] = hash != null ? hash : "";
        arrobject[2] = this.getReasonString();
        arrobject[3] = this.getReasonCode().code;
        return String.format(Locale.US, "Reject: %s %s for reason '%s' (%d)", arrobject);
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        RejectMessage other = (RejectMessage)o;
        return this.message.equals(other.message) && this.code.equals((Object)other.code) && this.reason.equals(other.reason) && this.messageHash.equals(other.messageHash);
    }

    public int hashCode() {
        return Objects.hashCode((Object[])new Object[]{this.message, this.code, this.reason, this.messageHash});
    }

    public static enum RejectCode {
        MALFORMED((byte)1),
        INVALID((byte)16),
        OBSOLETE((byte)17),
        DUPLICATE((byte)18),
        NONSTANDARD((byte)64),
        DUST((byte)65),
        INSUFFICIENTFEE((byte)66),
        CHECKPOINT((byte)67),
        OTHER((byte)-1);
        
        byte code;

        private RejectCode(byte code) {
            this.code = code;
        }

        static RejectCode fromCode(byte code) {
            for (RejectCode rejectCode : RejectCode.values()) {
                if (rejectCode.code != code) continue;
                return rejectCode;
            }
            return OTHER;
        }
    }

}

