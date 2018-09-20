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

public class Ping
extends Message {
    private long nonce;
    private boolean hasNonce;

    public Ping(NetworkParameters params, byte[] payloadBytes) throws ProtocolException {
        super(params, payloadBytes, 0);
    }

    public Ping(long nonce) {
        this.nonce = nonce;
        this.hasNonce = true;
    }

    public Ping() {
        this.hasNonce = false;
    }

    @Override
    public void bitcoinSerializeToStream(OutputStream stream) throws IOException {
        if (this.hasNonce) {
            Utils.int64ToByteStreamLE(this.nonce, stream);
        }
    }

    @Override
    protected void parse() throws ProtocolException {
        try {
            this.nonce = this.readInt64();
            this.hasNonce = true;
        }
        catch (ProtocolException e) {
            this.hasNonce = false;
        }
        this.length = this.hasNonce ? 8 : 0;
    }

    public boolean hasNonce() {
        return this.hasNonce;
    }

    public long getNonce() {
        return this.nonce;
    }
}

