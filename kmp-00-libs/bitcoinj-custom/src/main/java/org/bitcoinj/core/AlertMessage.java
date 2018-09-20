/*
 * Decompiled with CFR 0_132.
 */
package org.bitcoinj.core;

import java.math.BigInteger;
import java.util.Date;
import java.util.HashSet;
import org.bitcoinj.core.ECKey;
import org.bitcoinj.core.Message;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.core.ProtocolException;
import org.bitcoinj.core.Sha256Hash;

public class AlertMessage
extends Message {
    private byte[] content;
    private byte[] signature;
    private long version = 1L;
    private Date relayUntil;
    private Date expiration;
    private long id;
    private long cancel;
    private long minVer;
    private long maxVer;
    private long priority;
    private String comment;
    private String statusBar;
    private String reserved;
    private static final long MAX_SET_SIZE = 100L;

    public AlertMessage(NetworkParameters params, byte[] payloadBytes) throws ProtocolException {
        super(params, payloadBytes, 0);
    }

    public String toString() {
        return "ALERT: " + this.getStatusBar();
    }

    @Override
    protected void parse() throws ProtocolException {
        int startPos = this.cursor;
        this.content = this.readByteArray();
        this.signature = this.readByteArray();
        this.cursor = startPos;
        this.readVarInt();
        this.version = this.readUint32();
        this.relayUntil = new Date(this.readUint64().longValue() * 1000L);
        this.expiration = new Date(this.readUint64().longValue() * 1000L);
        this.id = this.readUint32();
        this.cancel = this.readUint32();
        long cancelSetSize = this.readVarInt();
        if (cancelSetSize < 0L || cancelSetSize > 100L) {
            throw new ProtocolException("Bad cancel set size: " + cancelSetSize);
        }
        HashSet<Long> cancelSet = new HashSet<Long>((int)cancelSetSize);
        for (long i = 0L; i < cancelSetSize; ++i) {
            cancelSet.add(this.readUint32());
        }
        this.minVer = this.readUint32();
        this.maxVer = this.readUint32();
        long subverSetSize = this.readVarInt();
        if (subverSetSize < 0L || subverSetSize > 100L) {
            throw new ProtocolException("Bad subver set size: " + subverSetSize);
        }
        HashSet<String> matchingSubVers = new HashSet<String>((int)subverSetSize);
        for (long i = 0L; i < subverSetSize; ++i) {
            matchingSubVers.add(this.readStr());
        }
        this.priority = this.readUint32();
        this.comment = this.readStr();
        this.statusBar = this.readStr();
        this.reserved = this.readStr();
        this.length = this.cursor - this.offset;
    }

    public boolean isSignatureValid() {
        return ECKey.verify(Sha256Hash.hashTwice(this.content), this.signature, this.params.getAlertSigningKey());
    }

    public Date getRelayUntil() {
        return this.relayUntil;
    }

    public void setRelayUntil(Date relayUntil) {
        this.relayUntil = relayUntil;
    }

    public Date getExpiration() {
        return this.expiration;
    }

    public void setExpiration(Date expiration) {
        this.expiration = expiration;
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCancel() {
        return this.cancel;
    }

    public void setCancel(long cancel) {
        this.cancel = cancel;
    }

    public long getMinVer() {
        return this.minVer;
    }

    public void setMinVer(long minVer) {
        this.minVer = minVer;
    }

    public long getMaxVer() {
        return this.maxVer;
    }

    public void setMaxVer(long maxVer) {
        this.maxVer = maxVer;
    }

    public long getPriority() {
        return this.priority;
    }

    public void setPriority(long priority) {
        this.priority = priority;
    }

    public String getComment() {
        return this.comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getStatusBar() {
        return this.statusBar;
    }

    public void setStatusBar(String statusBar) {
        this.statusBar = statusBar;
    }

    public String getReserved() {
        return this.reserved;
    }

    public void setReserved(String reserved) {
        this.reserved = reserved;
    }

    public long getVersion() {
        return this.version;
    }
}

