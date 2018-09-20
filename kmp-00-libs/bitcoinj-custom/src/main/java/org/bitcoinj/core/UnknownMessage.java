/*
 * Decompiled with CFR 0_132.
 * 
 * Could not load the following classes:
 *  com.google.common.io.BaseEncoding
 */
package org.bitcoinj.core;

import com.google.common.io.BaseEncoding;
import org.bitcoinj.core.EmptyMessage;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.core.ProtocolException;
import org.bitcoinj.core.Utils;

public class UnknownMessage
extends EmptyMessage {
    private String name;

    public UnknownMessage(NetworkParameters params, String name, byte[] payloadBytes) throws ProtocolException {
        super(params, payloadBytes, 0);
        this.name = name;
    }

    public String toString() {
        return "Unknown message [" + this.name + "]: " + (this.payload == null ? "" : Utils.HEX.encode(this.payload));
    }
}

