/*
 * Decompiled with CFR 0_132.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Objects
 *  com.google.common.collect.ImmutableList
 *  com.google.common.collect.ImmutableList$Builder
 */
package org.bitcoinj.core;

import com.google.common.base.Objects;
import com.google.common.collect.ImmutableList;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import org.bitcoinj.core.Message;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.core.ProtocolException;
import org.bitcoinj.core.TransactionOutPoint;
import org.bitcoinj.core.VarInt;

public class GetUTXOsMessage
extends Message {
    public static final int MIN_PROTOCOL_VERSION = 70002;
    public static final long SERVICE_FLAGS_REQUIRED = 3L;
    private boolean includeMempool;
    private ImmutableList<TransactionOutPoint> outPoints;

    public GetUTXOsMessage(NetworkParameters params, List<TransactionOutPoint> outPoints, boolean includeMempool) {
        super(params);
        this.outPoints = ImmutableList.copyOf(outPoints);
        this.includeMempool = includeMempool;
    }

    public GetUTXOsMessage(NetworkParameters params, byte[] payloadBytes) {
        super(params, payloadBytes, 0);
    }

    @Override
    protected void parse() throws ProtocolException {
        this.includeMempool = this.readBytes(1)[0] == 1;
        long numOutpoints = this.readVarInt();
        ImmutableList.Builder list = ImmutableList.builder();
        int i = 0;
        while ((long)i < numOutpoints) {
            TransactionOutPoint outPoint = new TransactionOutPoint(this.params, this.payload, this.cursor);
            list.add((Object)outPoint);
            this.cursor += outPoint.getMessageSize();
            ++i;
        }
        this.outPoints = list.build();
        this.length = this.cursor;
    }

    public boolean getIncludeMempool() {
        return this.includeMempool;
    }

    public ImmutableList<TransactionOutPoint> getOutPoints() {
        return this.outPoints;
    }

    @Override
    protected void bitcoinSerializeToStream(OutputStream stream) throws IOException {
        byte[] arrby = new byte[1];
        arrby[0] = this.includeMempool ? (byte)1 : 0;
        stream.write(arrby);
        stream.write(new VarInt(this.outPoints.size()).encode());
        for (TransactionOutPoint outPoint : this.outPoints) {
            outPoint.bitcoinSerializeToStream(stream);
        }
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        GetUTXOsMessage other = (GetUTXOsMessage)o;
        return this.includeMempool == other.includeMempool && this.outPoints.equals(other.outPoints);
    }

    public int hashCode() {
        return Objects.hashCode((Object[])new Object[]{this.includeMempool, this.outPoints});
    }
}

