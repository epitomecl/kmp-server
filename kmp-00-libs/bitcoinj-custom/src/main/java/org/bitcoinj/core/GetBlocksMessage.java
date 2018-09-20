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
import java.util.Collection;
import java.util.List;
import org.bitcoinj.core.Message;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.core.ProtocolException;
import org.bitcoinj.core.Sha256Hash;
import org.bitcoinj.core.Utils;
import org.bitcoinj.core.VarInt;

public class GetBlocksMessage
extends Message {
    protected long version;
    protected List<Sha256Hash> locator;
    protected Sha256Hash stopHash;

    public GetBlocksMessage(NetworkParameters params, List<Sha256Hash> locator, Sha256Hash stopHash) {
        super(params);
        this.version = this.protocolVersion;
        this.locator = locator;
        this.stopHash = stopHash;
    }

    public GetBlocksMessage(NetworkParameters params, byte[] payload) throws ProtocolException {
        super(params, payload, 0);
    }

    @Override
    protected void parse() throws ProtocolException {
        this.cursor = this.offset;
        this.version = this.readUint32();
        int startCount = (int)this.readVarInt();
        if (startCount > 500) {
            throw new ProtocolException("Number of locators cannot be > 500, received: " + startCount);
        }
        this.length = this.cursor - this.offset + (startCount + 1) * 32;
        this.locator = new ArrayList<Sha256Hash>(startCount);
        for (int i = 0; i < startCount; ++i) {
            this.locator.add(this.readHash());
        }
        this.stopHash = this.readHash();
    }

    public List<Sha256Hash> getLocator() {
        return this.locator;
    }

    public Sha256Hash getStopHash() {
        return this.stopHash;
    }

    public String toString() {
        return "getblocks: " + Utils.SPACE_JOINER.join(this.locator);
    }

    @Override
    protected void bitcoinSerializeToStream(OutputStream stream) throws IOException {
        Utils.uint32ToByteStreamLE(this.params.getProtocolVersionNum(NetworkParameters.ProtocolVersion.CURRENT), stream);
        stream.write(new VarInt(this.locator.size()).encode());
        for (Sha256Hash hash : this.locator) {
            stream.write(hash.getReversedBytes());
        }
        stream.write(this.stopHash.getReversedBytes());
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        GetBlocksMessage other = (GetBlocksMessage)o;
        return this.version == other.version && this.stopHash.equals(other.stopHash) && this.locator.size() == other.locator.size() && this.locator.containsAll(other.locator);
    }

    public int hashCode() {
        int hashCode = (int)this.version ^ "getblocks".hashCode() ^ this.stopHash.hashCode();
        for (Sha256Hash aLocator : this.locator) {
            hashCode ^= aLocator.hashCode();
        }
        return hashCode;
    }
}

