/*
 * Decompiled with CFR 0_132.
 */
package org.bitcoinj.core;

import java.io.IOException;
import java.io.OutputStream;
import org.bitcoinj.core.Message;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.core.ProtocolException;

public abstract class EmptyMessage
extends Message {
    public EmptyMessage() {
        this.length = 0;
    }

    public EmptyMessage(NetworkParameters params) {
        super(params);
        this.length = 0;
    }

    public EmptyMessage(NetworkParameters params, byte[] payload, int offset) throws ProtocolException {
        super(params, payload, offset);
        this.length = 0;
    }

    @Override
    protected final void bitcoinSerializeToStream(OutputStream stream) throws IOException {
    }

    @Override
    protected void parse() throws ProtocolException {
    }

    @Override
    public byte[] bitcoinSerialize() {
        return new byte[0];
    }
}

