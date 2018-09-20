/*
 * Decompiled with CFR 0_132.
 * 
 * Could not load the following classes:
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package org.bitcoinj.core;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.bitcoinj.core.BitcoinSerializer;
import org.bitcoinj.core.Block;
import org.bitcoinj.core.Message;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.core.ProtocolException;
import org.bitcoinj.core.VarInt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HeadersMessage
extends Message {
    private static final Logger log = LoggerFactory.getLogger(HeadersMessage.class);
    public static final int MAX_HEADERS = 2000;
    private List<Block> blockHeaders;

    public HeadersMessage(NetworkParameters params, byte[] payload) throws ProtocolException {
        super(params, payload, 0);
    }

    public /* varargs */ HeadersMessage(NetworkParameters params, Block ... headers) throws ProtocolException {
        super(params);
        this.blockHeaders = Arrays.asList(headers);
    }

    public HeadersMessage(NetworkParameters params, List<Block> headers) throws ProtocolException {
        super(params);
        this.blockHeaders = headers;
    }

    @Override
    public void bitcoinSerializeToStream(OutputStream stream) throws IOException {
        stream.write(new VarInt(this.blockHeaders.size()).encode());
        for (Block header : this.blockHeaders) {
            header.cloneAsHeader().bitcoinSerializeToStream(stream);
            stream.write(0);
        }
    }

    @Override
    protected void parse() throws ProtocolException {
        long numHeaders = this.readVarInt();
        if (numHeaders > 2000L) {
            throw new ProtocolException("Too many headers: got " + numHeaders + " which is larger than " + 2000);
        }
        this.blockHeaders = new ArrayList<Block>();
        BitcoinSerializer serializer = this.params.getSerializer(true);
        int i = 0;
        while ((long)i < numHeaders) {
            Block newBlockHeader = serializer.makeBlock(this.payload, this.cursor, Integer.MIN_VALUE);
            if (newBlockHeader.hasTransactions()) {
                throw new ProtocolException("Block header does not end with a null byte");
            }
            this.cursor += newBlockHeader.optimalEncodingMessageSize;
            this.blockHeaders.add(newBlockHeader);
            ++i;
        }
        if (this.length == Integer.MIN_VALUE) {
            this.length = this.cursor - this.offset;
        }
        if (log.isDebugEnabled()) {
            i = 0;
            while ((long)i < numHeaders) {
                log.debug(this.blockHeaders.get(i).toString());
                ++i;
            }
        }
    }

    public List<Block> getBlockHeaders() {
        return this.blockHeaders;
    }
}

