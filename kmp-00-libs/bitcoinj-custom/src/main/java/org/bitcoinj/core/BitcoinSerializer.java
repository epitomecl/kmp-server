/*
 * Decompiled with CFR 0_132.
 * 
 * Could not load the following classes:
 *  com.google.common.io.BaseEncoding
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package org.bitcoinj.core;

import com.google.common.io.BaseEncoding;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;
import org.bitcoinj.core.AddressMessage;
import org.bitcoinj.core.AlertMessage;
import org.bitcoinj.core.Block;
import org.bitcoinj.core.BloomFilter;
import org.bitcoinj.core.FilteredBlock;
import org.bitcoinj.core.GetAddrMessage;
import org.bitcoinj.core.GetBlocksMessage;
import org.bitcoinj.core.GetDataMessage;
import org.bitcoinj.core.GetHeadersMessage;
import org.bitcoinj.core.GetUTXOsMessage;
import org.bitcoinj.core.HeadersMessage;
import org.bitcoinj.core.InventoryMessage;
import org.bitcoinj.core.MemoryPoolMessage;
import org.bitcoinj.core.Message;
import org.bitcoinj.core.MessageSerializer;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.core.NotFoundMessage;
import org.bitcoinj.core.Ping;
import org.bitcoinj.core.Pong;
import org.bitcoinj.core.ProtocolException;
import org.bitcoinj.core.RejectMessage;
import org.bitcoinj.core.SendHeadersMessage;
import org.bitcoinj.core.Sha256Hash;
import org.bitcoinj.core.Transaction;
import org.bitcoinj.core.UTXOsMessage;
import org.bitcoinj.core.UnknownMessage;
import org.bitcoinj.core.Utils;
import org.bitcoinj.core.VersionAck;
import org.bitcoinj.core.VersionMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BitcoinSerializer
extends MessageSerializer {
    private static final Logger log = LoggerFactory.getLogger(BitcoinSerializer.class);
    private static final int COMMAND_LEN = 12;
    private final NetworkParameters params;
    private final boolean parseRetain;
    private static final Map<Class<? extends Message>, String> names = new HashMap<Class<? extends Message>, String>();

    public BitcoinSerializer(NetworkParameters params, boolean parseRetain) {
        this.params = params;
        this.parseRetain = parseRetain;
    }

    @Override
    public void serialize(String name, byte[] message, OutputStream out) throws IOException {
        byte[] header = new byte[24];
        Utils.uint32ToByteArrayBE(this.params.getPacketMagic(), header, 0);
        for (int i = 0; i < name.length() && i < 12; ++i) {
            header[4 + i] = (byte)(name.codePointAt(i) & 255);
        }
        Utils.uint32ToByteArrayLE(message.length, header, 16);
        byte[] hash = Sha256Hash.hashTwice(message);
        System.arraycopy(hash, 0, header, 20, 4);
        out.write(header);
        out.write(message);
        if (log.isDebugEnabled()) {
            log.debug("Sending {} message: {}", (Object)name, (Object)(Utils.HEX.encode(header) + Utils.HEX.encode(message)));
        }
    }

    @Override
    public void serialize(Message message, OutputStream out) throws IOException {
        String name = names.get(message.getClass());
        if (name == null) {
            throw new Error("BitcoinSerializer doesn't currently know how to serialize " + message.getClass());
        }
        this.serialize(name, message.bitcoinSerialize(), out);
    }

    @Override
    public Message deserialize(ByteBuffer in) throws ProtocolException, IOException {
        this.seekPastMagicBytes(in);
        BitcoinPacketHeader header = new BitcoinPacketHeader(in);
        return this.deserializePayload(header, in);
    }

    @Override
    public BitcoinPacketHeader deserializeHeader(ByteBuffer in) throws ProtocolException, IOException {
        return new BitcoinPacketHeader(in);
    }

    @Override
    public Message deserializePayload(BitcoinPacketHeader header, ByteBuffer in) throws ProtocolException, BufferUnderflowException {
        byte[] payloadBytes = new byte[header.size];
        in.get(payloadBytes, 0, header.size);
        byte[] hash = Sha256Hash.hashTwice(payloadBytes);
        if (header.checksum[0] != hash[0] || header.checksum[1] != hash[1] || header.checksum[2] != hash[2] || header.checksum[3] != hash[3]) {
            throw new ProtocolException("Checksum failed to verify, actual " + Utils.HEX.encode(hash) + " vs " + Utils.HEX.encode(header.checksum));
        }
        if (log.isDebugEnabled()) {
            log.debug("Received {} byte '{}' message: {}", new Object[]{header.size, header.command, Utils.HEX.encode(payloadBytes)});
        }
        try {
            return this.makeMessage(header.command, header.size, payloadBytes, hash, header.checksum);
        }
        catch (Exception e) {
            throw new ProtocolException("Error deserializing message " + Utils.HEX.encode(payloadBytes) + "\n", e);
        }
    }

    private Message makeMessage(String command, int length, byte[] payloadBytes, byte[] hash, byte[] checksum) throws ProtocolException {
        Message message;
        if (command.equals("version")) {
            return new VersionMessage(this.params, payloadBytes);
        }
        if (command.equals("inv")) {
            message = this.makeInventoryMessage(payloadBytes, length);
        } else if (command.equals("block")) {
            message = this.makeBlock(payloadBytes, length);
        } else if (command.equals("merkleblock")) {
            message = this.makeFilteredBlock(payloadBytes);
        } else if (command.equals("getdata")) {
            message = new GetDataMessage(this.params, payloadBytes, this, length);
        } else if (command.equals("getblocks")) {
            message = new GetBlocksMessage(this.params, payloadBytes);
        } else if (command.equals("getheaders")) {
            message = new GetHeadersMessage(this.params, payloadBytes);
        } else if (command.equals("tx")) {
            message = this.makeTransaction(payloadBytes, 0, length, hash);
        } else if (command.equals("addr")) {
            message = this.makeAddressMessage(payloadBytes, length);
        } else if (command.equals("ping")) {
            message = new Ping(this.params, payloadBytes);
        } else if (command.equals("pong")) {
            message = new Pong(this.params, payloadBytes);
        } else {
            if (command.equals("verack")) {
                return new VersionAck(this.params, payloadBytes);
            }
            if (command.equals("headers")) {
                return new HeadersMessage(this.params, payloadBytes);
            }
            if (command.equals("alert")) {
                return this.makeAlertMessage(payloadBytes);
            }
            if (command.equals("filterload")) {
                return this.makeBloomFilter(payloadBytes);
            }
            if (command.equals("notfound")) {
                return new NotFoundMessage(this.params, payloadBytes);
            }
            if (command.equals("mempool")) {
                return new MemoryPoolMessage();
            }
            if (command.equals("reject")) {
                return new RejectMessage(this.params, payloadBytes);
            }
            if (command.equals("utxos")) {
                return new UTXOsMessage(this.params, payloadBytes);
            }
            if (command.equals("getutxos")) {
                return new GetUTXOsMessage(this.params, payloadBytes);
            }
            if (command.equals("sendheaders")) {
                return new SendHeadersMessage(this.params, payloadBytes);
            }
            log.warn("No support for deserializing message with name {}", (Object)command);
            return new UnknownMessage(this.params, command, payloadBytes);
        }
        return message;
    }

    public NetworkParameters getParameters() {
        return this.params;
    }

    @Override
    public AddressMessage makeAddressMessage(byte[] payloadBytes, int length) throws ProtocolException {
        return new AddressMessage(this.params, payloadBytes, this, length);
    }

    @Override
    public Message makeAlertMessage(byte[] payloadBytes) throws ProtocolException {
        return new AlertMessage(this.params, payloadBytes);
    }

    @Override
    public Block makeBlock(byte[] payloadBytes, int offset, int length) throws ProtocolException {
        return new Block(this.params, payloadBytes, offset, this, length);
    }

    @Override
    public Message makeBloomFilter(byte[] payloadBytes) throws ProtocolException {
        return new BloomFilter(this.params, payloadBytes);
    }

    @Override
    public FilteredBlock makeFilteredBlock(byte[] payloadBytes) throws ProtocolException {
        return new FilteredBlock(this.params, payloadBytes);
    }

    @Override
    public InventoryMessage makeInventoryMessage(byte[] payloadBytes, int length) throws ProtocolException {
        return new InventoryMessage(this.params, payloadBytes, this, length);
    }

    @Override
    public Transaction makeTransaction(byte[] payloadBytes, int offset, int length, byte[] hash) throws ProtocolException {
        Transaction tx = new Transaction(this.params, payloadBytes, offset, null, this, length);
        if (hash != null) {
            tx.setHash(Sha256Hash.wrapReversed(hash));
        }
        return tx;
    }

    @Override
    public void seekPastMagicBytes(ByteBuffer in) throws BufferUnderflowException {
        int magicCursor = 3;
        do {
            byte expectedByte;
            byte b;
            if ((b = in.get()) == (expectedByte = (byte)(255L & this.params.getPacketMagic() >>> magicCursor * 8))) {
                if (--magicCursor >= 0) continue;
                return;
            }
            magicCursor = 3;
        } while (true);
    }

    @Override
    public boolean isParseRetainMode() {
        return this.parseRetain;
    }

    static {
        names.put(VersionMessage.class, "version");
        names.put(InventoryMessage.class, "inv");
        names.put(Block.class, "block");
        names.put(GetDataMessage.class, "getdata");
        names.put(Transaction.class, "tx");
        names.put(AddressMessage.class, "addr");
        names.put(Ping.class, "ping");
        names.put(Pong.class, "pong");
        names.put(VersionAck.class, "verack");
        names.put(GetBlocksMessage.class, "getblocks");
        names.put(GetHeadersMessage.class, "getheaders");
        names.put(GetAddrMessage.class, "getaddr");
        names.put(HeadersMessage.class, "headers");
        names.put(BloomFilter.class, "filterload");
        names.put(FilteredBlock.class, "merkleblock");
        names.put(NotFoundMessage.class, "notfound");
        names.put(MemoryPoolMessage.class, "mempool");
        names.put(RejectMessage.class, "reject");
        names.put(GetUTXOsMessage.class, "getutxos");
        names.put(UTXOsMessage.class, "utxos");
        names.put(SendHeadersMessage.class, "sendheaders");
    }

    public static class BitcoinPacketHeader {
        public static final int HEADER_LENGTH = 20;
        public final byte[] header = new byte[20];
        public final String command;
        public final int size;
        public final byte[] checksum;

        public BitcoinPacketHeader(ByteBuffer in) throws ProtocolException, BufferUnderflowException {
            int cursor;
            in.get(this.header, 0, this.header.length);
            for (cursor = 0; this.header[cursor] != 0 && cursor < 12; ++cursor) {
            }
            byte[] commandBytes = new byte[cursor];
            System.arraycopy(this.header, 0, commandBytes, 0, cursor);
            this.command = Utils.toString(commandBytes, "US-ASCII");
            cursor = 12;
            this.size = (int)Utils.readUint32(this.header, cursor);
            cursor += 4;
            if (this.size > 33554432 || this.size < 0) {
                throw new ProtocolException("Message size too large: " + this.size);
            }
            this.checksum = new byte[4];
            System.arraycopy(this.header, cursor, this.checksum, 0, 4);
            cursor += 4;
        }
    }

}

