/*
 * Decompiled with CFR 0_132.
 */
package org.bitcoinj.core;

import java.io.IOException;
import java.io.OutputStream;
import org.bitcoinj.core.Message;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.core.ProtocolException;
import org.bitcoinj.core.Utils;

public class Pong
extends Message {
    private long nonce;

    public Pong(NetworkParameters params, byte[] payloadBytes) throws ProtocolException {
        super(params, payloadBytes, 0);
    }

    public Pong(long nonce) {
        this.nonce = nonce;
    }

    @Override
    protected void parse() throws ProtocolException {
        this.nonce = this.readInt64();
        this.length = 8;
    }

    @Override
    public void bitcoinSerializeToStream(OutputStream stream) throws IOException {
        Utils.int64ToByteStreamLE(this.nonce, stream);
    }

    public long getNonce() {
        return this.nonce;
    }
}

