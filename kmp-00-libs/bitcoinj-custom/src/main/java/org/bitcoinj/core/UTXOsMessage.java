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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import org.bitcoinj.core.Message;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.core.ProtocolException;
import org.bitcoinj.core.Sha256Hash;
import org.bitcoinj.core.Transaction;
import org.bitcoinj.core.TransactionOutput;
import org.bitcoinj.core.Utils;
import org.bitcoinj.core.VarInt;

public class UTXOsMessage
extends Message {
    private long height;
    private Sha256Hash chainHead;
    private byte[] hits;
    private List<TransactionOutput> outputs;
    private long[] heights;
    public static long MEMPOOL_HEIGHT = Integer.MAX_VALUE;

    public UTXOsMessage(NetworkParameters params, byte[] payloadBytes) {
        super(params, payloadBytes, 0);
    }

    public UTXOsMessage(NetworkParameters params, List<TransactionOutput> outputs, long[] heights, Sha256Hash chainHead, long height) {
        super(params);
        this.hits = new byte[(int)Math.ceil((double)outputs.size() / 8.0)];
        for (int i = 0; i < outputs.size(); ++i) {
            if (outputs.get(i) == null) continue;
            Utils.setBitLE(this.hits, i);
        }
        this.outputs = new ArrayList<TransactionOutput>(outputs.size());
        for (TransactionOutput output : outputs) {
            if (output == null) continue;
            this.outputs.add(output);
        }
        this.chainHead = chainHead;
        this.height = height;
        this.heights = Arrays.copyOf(heights, heights.length);
    }

    @Override
    protected void bitcoinSerializeToStream(OutputStream stream) throws IOException {
        Utils.uint32ToByteStreamLE(this.height, stream);
        stream.write(this.chainHead.getBytes());
        stream.write(new VarInt(this.hits.length).encode());
        stream.write(this.hits);
        stream.write(new VarInt(this.outputs.size()).encode());
        for (int i = 0; i < this.outputs.size(); ++i) {
            TransactionOutput output = this.outputs.get(i);
            Transaction tx = output.getParentTransaction();
            Utils.uint32ToByteStreamLE(tx != null ? tx.getVersion() : 0L, stream);
            Utils.uint32ToByteStreamLE(this.heights[i], stream);
            output.bitcoinSerializeToStream(stream);
        }
    }

    @Override
    protected void parse() throws ProtocolException {
        this.height = this.readUint32();
        this.chainHead = this.readHash();
        int numBytes = (int)this.readVarInt();
        if (numBytes < 0 || (long)numBytes > 6250L) {
            throw new ProtocolException("hitsBitmap out of range: " + numBytes);
        }
        this.hits = this.readBytes(numBytes);
        int numOuts = (int)this.readVarInt();
        if (numOuts < 0 || (long)numOuts > 50000L) {
            throw new ProtocolException("numOuts out of range: " + numOuts);
        }
        this.outputs = new ArrayList<TransactionOutput>(numOuts);
        this.heights = new long[numOuts];
        for (int i = 0; i < numOuts; ++i) {
            long version = this.readUint32();
            long height = this.readUint32();
            if (version > 1L) {
                throw new ProtocolException("Unknown tx version in getutxo output: " + version);
            }
            TransactionOutput output = new TransactionOutput(this.params, null, this.payload, this.cursor);
            this.outputs.add(output);
            this.heights[i] = height;
            this.cursor += output.length;
        }
        this.length = this.cursor;
    }

    public byte[] getHitMap() {
        return Arrays.copyOf(this.hits, this.hits.length);
    }

    public List<TransactionOutput> getOutputs() {
        return new ArrayList<TransactionOutput>(this.outputs);
    }

    public long[] getHeights() {
        return Arrays.copyOf(this.heights, this.heights.length);
    }

    public String toString() {
        return "UTXOsMessage{height=" + this.height + ", chainHead=" + this.chainHead + ", hitMap=" + Arrays.toString(this.hits) + ", outputs=" + this.outputs + ", heights=" + Arrays.toString(this.heights) + '}';
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        UTXOsMessage other = (UTXOsMessage)o;
        return this.height == other.height && this.chainHead.equals(other.chainHead) && Arrays.equals(this.heights, other.heights) && Arrays.equals(this.hits, other.hits) && this.outputs.equals(other.outputs);
    }

    public int hashCode() {
        return Objects.hashCode((Object[])new Object[]{this.height, this.chainHead, Arrays.hashCode(this.heights), Arrays.hashCode(this.hits), this.outputs});
    }
}

