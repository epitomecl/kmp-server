/*
 * Decompiled with CFR 0_132.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Joiner
 */
package org.bitcoinj.core;

import com.google.common.base.Joiner;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.bitcoinj.core.Message;
import org.bitcoinj.core.MessageSerializer;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.core.PeerAddress;
import org.bitcoinj.core.ProtocolException;
import org.bitcoinj.core.Utils;
import org.bitcoinj.core.VarInt;

public class AddressMessage
extends Message {
    private static final long MAX_ADDRESSES = 1024L;
    private List<PeerAddress> addresses;

    AddressMessage(NetworkParameters params, byte[] payload, int offset, MessageSerializer setSerializer, int length) throws ProtocolException {
        super(params, payload, offset, setSerializer, length);
    }

    AddressMessage(NetworkParameters params, byte[] payload, MessageSerializer serializer, int length) throws ProtocolException {
        super(params, payload, 0, serializer, length);
    }

    AddressMessage(NetworkParameters params, byte[] payload, int offset) throws ProtocolException {
        super(params, payload, offset, params.getDefaultSerializer(), Integer.MIN_VALUE);
    }

    AddressMessage(NetworkParameters params, byte[] payload) throws ProtocolException {
        super(params, payload, 0, params.getDefaultSerializer(), Integer.MIN_VALUE);
    }

    @Override
    protected void parse() throws ProtocolException {
        long numAddresses = this.readVarInt();
        if (numAddresses > 1024L) {
            throw new ProtocolException("Address message too large.");
        }
        this.addresses = new ArrayList<PeerAddress>((int)numAddresses);
        int i = 0;
        while ((long)i < numAddresses) {
            PeerAddress addr = new PeerAddress(this.params, this.payload, this.cursor, this.protocolVersion, this, this.serializer);
            this.addresses.add(addr);
            this.cursor += addr.getMessageSize();
            ++i;
        }
        this.length = new VarInt(this.addresses.size()).getSizeInBytes();
        this.length += this.addresses.size() * (this.protocolVersion > 31402 ? 30 : 26);
    }

    @Override
    protected void bitcoinSerializeToStream(OutputStream stream) throws IOException {
        if (this.addresses == null) {
            return;
        }
        stream.write(new VarInt(this.addresses.size()).encode());
        for (PeerAddress addr : this.addresses) {
            addr.bitcoinSerialize(stream);
        }
    }

    public List<PeerAddress> getAddresses() {
        return Collections.unmodifiableList(this.addresses);
    }

    public void addAddress(PeerAddress address) {
        this.unCache();
        address.setParent(this);
        this.addresses.add(address);
        if (this.length == Integer.MIN_VALUE) {
            this.getMessageSize();
        } else {
            this.length += address.getMessageSize();
        }
    }

    public void removeAddress(int index) {
        this.unCache();
        PeerAddress address = this.addresses.remove(index);
        address.setParent(null);
        if (this.length == Integer.MIN_VALUE) {
            this.getMessageSize();
        } else {
            this.length -= address.getMessageSize();
        }
    }

    public String toString() {
        return "addr: " + Utils.SPACE_JOINER.join(this.addresses);
    }
}

