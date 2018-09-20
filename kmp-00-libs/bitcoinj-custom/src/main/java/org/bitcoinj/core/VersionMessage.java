/*
 * Decompiled with CFR 0_132.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Objects
 *  javax.annotation.Nullable
 */
package org.bitcoinj.core;

import com.google.common.base.Objects;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;
import java.net.UnknownHostException;
import java.util.Locale;
import javax.annotation.Nullable;
import org.bitcoinj.core.Message;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.core.PeerAddress;
import org.bitcoinj.core.ProtocolException;
import org.bitcoinj.core.Utils;
import org.bitcoinj.core.VarInt;

public class VersionMessage
extends Message {
    public static final String BITCOINJ_VERSION = "0.15-SNAPSHOT";
    public static final String LIBRARY_SUBVER = "/bitcoinj:0.15-SNAPSHOT/";
    public static final int NODE_NETWORK = 1;
    public static final int NODE_GETUTXOS = 2;
    public static final int NODE_BITCOIN_CASH = 32;
    public int clientVersion;
    public long localServices;
    public long time;
    public PeerAddress myAddr;
    public PeerAddress theirAddr;
    public String subVer;
    public long bestHeight;
    public boolean relayTxesBeforeFilter;

    public VersionMessage(NetworkParameters params, byte[] payload) throws ProtocolException {
        super(params, payload, 0);
    }

    @Override
    protected void parse() throws ProtocolException {
        this.clientVersion = (int)this.readUint32();
        this.localServices = this.readUint64().longValue();
        this.time = this.readUint64().longValue();
        this.myAddr = new PeerAddress(this.params, this.payload, this.cursor, 0);
        this.cursor += this.myAddr.getMessageSize();
        this.theirAddr = new PeerAddress(this.params, this.payload, this.cursor, 0);
        this.cursor += this.theirAddr.getMessageSize();
        this.readUint64();
        try {
            this.subVer = "";
            this.bestHeight = 0L;
            this.relayTxesBeforeFilter = true;
            if (!this.hasMoreBytes()) {
                return;
            }
            this.subVer = this.readStr();
            if (!this.hasMoreBytes()) {
                return;
            }
            this.bestHeight = this.readUint32();
            if (!this.hasMoreBytes()) {
                return;
            }
            this.relayTxesBeforeFilter = this.readBytes(1)[0] != 0;
        }
        finally {
            this.length = this.cursor - this.offset;
        }
    }

    @Override
    public void bitcoinSerializeToStream(OutputStream buf) throws IOException {
        Utils.uint32ToByteStreamLE(this.clientVersion, buf);
        Utils.uint32ToByteStreamLE(this.localServices, buf);
        Utils.uint32ToByteStreamLE(this.localServices >> 32, buf);
        Utils.uint32ToByteStreamLE(this.time, buf);
        Utils.uint32ToByteStreamLE(this.time >> 32, buf);
        try {
            this.myAddr.bitcoinSerialize(buf);
            this.theirAddr.bitcoinSerialize(buf);
        }
        catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
        Utils.uint32ToByteStreamLE(0L, buf);
        Utils.uint32ToByteStreamLE(0L, buf);
        byte[] subVerBytes = this.subVer.getBytes("UTF-8");
        buf.write(new VarInt(subVerBytes.length).encode());
        buf.write(subVerBytes);
        Utils.uint32ToByteStreamLE(this.bestHeight, buf);
        buf.write(this.relayTxesBeforeFilter ? 1 : 0);
    }

    public boolean hasBlockChain() {
        return (this.localServices & 1L) == 1L;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        VersionMessage other = (VersionMessage)o;
        return other.bestHeight == this.bestHeight && other.clientVersion == this.clientVersion && other.localServices == this.localServices && other.time == this.time && other.subVer.equals(this.subVer) && other.myAddr.equals(this.myAddr) && other.theirAddr.equals(this.theirAddr) && other.relayTxesBeforeFilter == this.relayTxesBeforeFilter;
    }

    public int hashCode() {
        return Objects.hashCode((Object[])new Object[]{this.bestHeight, this.clientVersion, this.localServices, this.time, this.subVer, this.myAddr, this.theirAddr, this.relayTxesBeforeFilter});
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\n");
        stringBuilder.append("client version: ").append(this.clientVersion).append("\n");
        stringBuilder.append("local services: ").append(this.localServices).append("\n");
        stringBuilder.append("time:           ").append(this.time).append("\n");
        stringBuilder.append("my addr:        ").append(this.myAddr).append("\n");
        stringBuilder.append("their addr:     ").append(this.theirAddr).append("\n");
        stringBuilder.append("sub version:    ").append(this.subVer).append("\n");
        stringBuilder.append("best height:    ").append(this.bestHeight).append("\n");
        stringBuilder.append("delay tx relay: ").append(!this.relayTxesBeforeFilter).append("\n");
        return stringBuilder.toString();
    }

    public void appendToSubVer(String name, String version, @Nullable String comments) {
        VersionMessage.checkSubVerComponent(name);
        VersionMessage.checkSubVerComponent(version);
        if (comments != null) {
            VersionMessage.checkSubVerComponent(comments);
            this.subVer = this.subVer.concat(String.format(Locale.US, "%s:%s(%s)/", name, version, comments));
        } else {
            this.subVer = this.subVer.concat(String.format(Locale.US, "%s:%s/", name, version));
        }
    }

    private static void checkSubVerComponent(String component) {
        if (component.contains("/") || component.contains("(") || component.contains(")")) {
            throw new IllegalArgumentException("name contains invalid characters");
        }
    }

    public boolean isPingPongSupported() {
        return this.clientVersion >= this.params.getProtocolVersionNum(NetworkParameters.ProtocolVersion.PONG);
    }

    public boolean isBloomFilteringSupported() {
        return this.clientVersion >= this.params.getProtocolVersionNum(NetworkParameters.ProtocolVersion.BLOOM_FILTER);
    }

    public boolean isGetUTXOsSupported() {
        return this.clientVersion >= 70002 && (this.localServices & 2L) == 2L;
    }
}

