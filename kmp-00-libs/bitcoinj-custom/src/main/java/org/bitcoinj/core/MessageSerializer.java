/*
 * Decompiled with CFR 0_132.
 */
package org.bitcoinj.core;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import org.bitcoinj.core.AddressMessage;
import org.bitcoinj.core.BitcoinSerializer;
import org.bitcoinj.core.Block;
import org.bitcoinj.core.FilteredBlock;
import org.bitcoinj.core.InventoryMessage;
import org.bitcoinj.core.Message;
import org.bitcoinj.core.ProtocolException;
import org.bitcoinj.core.Transaction;

public abstract class MessageSerializer {
    public abstract Message deserialize(ByteBuffer var1) throws ProtocolException, IOException, UnsupportedOperationException;

    public abstract BitcoinSerializer.BitcoinPacketHeader deserializeHeader(ByteBuffer var1) throws ProtocolException, IOException, UnsupportedOperationException;

    public abstract Message deserializePayload(BitcoinSerializer.BitcoinPacketHeader var1, ByteBuffer var2) throws ProtocolException, BufferUnderflowException, UnsupportedOperationException;

    public abstract boolean isParseRetainMode();

    public abstract AddressMessage makeAddressMessage(byte[] var1, int var2) throws ProtocolException, UnsupportedOperationException;

    public abstract Message makeAlertMessage(byte[] var1) throws ProtocolException, UnsupportedOperationException;

    public final Block makeBlock(byte[] payloadBytes) throws ProtocolException {
        return this.makeBlock(payloadBytes, 0, payloadBytes.length);
    }

    public final Block makeBlock(byte[] payloadBytes, int length) throws ProtocolException {
        return this.makeBlock(payloadBytes, 0, length);
    }

    public abstract Block makeBlock(byte[] var1, int var2, int var3) throws ProtocolException, UnsupportedOperationException;

    public abstract Message makeBloomFilter(byte[] var1) throws ProtocolException, UnsupportedOperationException;

    public abstract FilteredBlock makeFilteredBlock(byte[] var1) throws ProtocolException, UnsupportedOperationException;

    public abstract InventoryMessage makeInventoryMessage(byte[] var1, int var2) throws ProtocolException, UnsupportedOperationException;

    public abstract Transaction makeTransaction(byte[] var1, int var2, int var3, byte[] var4) throws ProtocolException, UnsupportedOperationException;

    public final Transaction makeTransaction(byte[] payloadBytes) throws ProtocolException, UnsupportedOperationException {
        return this.makeTransaction(payloadBytes, 0);
    }

    public final Transaction makeTransaction(byte[] payloadBytes, int offset) throws ProtocolException {
        return this.makeTransaction(payloadBytes, offset, payloadBytes.length, null);
    }

    public abstract void seekPastMagicBytes(ByteBuffer var1) throws BufferUnderflowException;

    public abstract void serialize(String var1, byte[] var2, OutputStream var3) throws IOException, UnsupportedOperationException;

    public abstract void serialize(Message var1, OutputStream var2) throws IOException, UnsupportedOperationException;
}

