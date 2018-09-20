/*
 * Decompiled with CFR 0_132.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Objects
 *  com.google.common.base.Preconditions
 */
package org.bitcoinj.core;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import org.bitcoinj.core.ChildMessage;
import org.bitcoinj.core.Message;
import org.bitcoinj.core.MessageSerializer;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.core.ProtocolException;
import org.bitcoinj.core.Utils;

public class PeerAddress
extends ChildMessage {
    static final int MESSAGE_SIZE = 30;
    private InetAddress addr;
    private String hostname;
    private int port;
    private BigInteger services;
    private long time;

    public PeerAddress(NetworkParameters params, byte[] payload, int offset, int protocolVersion) throws ProtocolException {
        super(params, payload, offset, protocolVersion);
    }

    public PeerAddress(NetworkParameters params, byte[] payload, int offset, int protocolVersion, Message parent, MessageSerializer serializer) throws ProtocolException {
        super(params, payload, offset, protocolVersion, parent, serializer, Integer.MIN_VALUE);
    }

    public PeerAddress(NetworkParameters params, InetAddress addr, int port, int protocolVersion, BigInteger services) {
        super(params);
        this.addr = (InetAddress)Preconditions.checkNotNull((Object)addr);
        this.port = port;
        this.protocolVersion = protocolVersion;
        this.services = services;
        this.length = protocolVersion > 31402 ? 30 : 26;
    }

    public PeerAddress(NetworkParameters params, InetAddress addr, int port) {
        this(params, addr, port, params.getProtocolVersionNum(NetworkParameters.ProtocolVersion.CURRENT), BigInteger.ZERO);
    }

    public PeerAddress(NetworkParameters params, InetSocketAddress addr) {
        this(params, addr.getAddress(), addr.getPort());
    }

    public PeerAddress(NetworkParameters params, String hostname, int port) {
        super(params);
        this.hostname = hostname;
        this.port = port;
        this.protocolVersion = params.getProtocolVersionNum(NetworkParameters.ProtocolVersion.CURRENT);
        this.services = BigInteger.ZERO;
    }

    @Override
    protected void bitcoinSerializeToStream(OutputStream stream) throws IOException {
        if (this.protocolVersion >= 31402) {
            int secs = (int)Utils.currentTimeSeconds();
            Utils.uint32ToByteStreamLE(secs, stream);
        }
        Utils.uint64ToByteStreamLE(this.services, stream);
        byte[] ipBytes = this.addr.getAddress();
        if (ipBytes.length == 4) {
            byte[] v6addr = new byte[16];
            System.arraycopy(ipBytes, 0, v6addr, 12, 4);
            v6addr[10] = -1;
            v6addr[11] = -1;
            ipBytes = v6addr;
        }
        stream.write(ipBytes);
        stream.write((byte)(255 & this.port >> 8));
        stream.write((byte)(255 & this.port));
    }

    @Override
    protected void parse() throws ProtocolException {
        this.time = this.protocolVersion > 31402 ? this.readUint32() : -1L;
        this.services = this.readUint64();
        byte[] addrBytes = this.readBytes(16);
        try {
            this.addr = InetAddress.getByAddress(addrBytes);
        }
        catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
        this.port = (255 & this.payload[this.cursor++]) << 8 | 255 & this.payload[this.cursor++];
        this.length = this.protocolVersion > 31402 ? 30 : 26;
    }

    public String getHostname() {
        return this.hostname;
    }

    public InetAddress getAddr() {
        return this.addr;
    }

    public InetSocketAddress getSocketAddress() {
        return new InetSocketAddress(this.getAddr(), this.getPort());
    }

    public int getPort() {
        return this.port;
    }

    public BigInteger getServices() {
        return this.services;
    }

    public long getTime() {
        return this.time;
    }

    public String toString() {
        if (this.hostname != null) {
            return "[" + this.hostname + "]:" + this.port;
        }
        return "[" + this.addr.getHostAddress() + "]:" + this.port;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        PeerAddress other = (PeerAddress)o;
        return other.addr.equals(this.addr) && other.port == this.port && other.time == this.time && other.services.equals(this.services);
    }

    public int hashCode() {
        return Objects.hashCode((Object[])new Object[]{this.addr, this.port, this.time, this.services});
    }

    public InetSocketAddress toSocketAddress() {
        if (this.hostname != null) {
            return InetSocketAddress.createUnresolved(this.hostname, this.port);
        }
        return new InetSocketAddress(this.addr, this.port);
    }
}

