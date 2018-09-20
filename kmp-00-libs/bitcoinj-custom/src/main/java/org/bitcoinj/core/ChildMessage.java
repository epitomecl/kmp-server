/*
 * Decompiled with CFR 0_132.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package org.bitcoinj.core;

import javax.annotation.Nullable;
import org.bitcoinj.core.Message;
import org.bitcoinj.core.MessageSerializer;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.core.ProtocolException;

public abstract class ChildMessage
extends Message {
    @Nullable
    protected Message parent;

    @Deprecated
    protected ChildMessage() {
    }

    public ChildMessage(NetworkParameters params) {
        super(params);
    }

    public ChildMessage(NetworkParameters params, byte[] payload, int offset, int protocolVersion) throws ProtocolException {
        super(params, payload, offset, protocolVersion);
    }

    public ChildMessage(NetworkParameters params, byte[] payload, int offset, int protocolVersion, Message parent, MessageSerializer setSerializer, int length) throws ProtocolException {
        super(params, payload, offset, protocolVersion, setSerializer, length);
        this.parent = parent;
    }

    public ChildMessage(NetworkParameters params, byte[] payload, int offset) throws ProtocolException {
        super(params, payload, offset);
    }

    public ChildMessage(NetworkParameters params, byte[] payload, int offset, @Nullable Message parent, MessageSerializer setSerializer, int length) throws ProtocolException {
        super(params, payload, offset, setSerializer, length);
        this.parent = parent;
    }

    public final void setParent(@Nullable Message parent) {
        if (this.parent != null && this.parent != parent && parent != null) {
            this.parent.unCache();
        }
        this.parent = parent;
    }

    @Override
    protected void unCache() {
        super.unCache();
        if (this.parent != null) {
            this.parent.unCache();
        }
    }

    protected void adjustLength(int adjustment) {
        this.adjustLength(0, adjustment);
    }

    @Override
    protected void adjustLength(int newArraySize, int adjustment) {
        super.adjustLength(newArraySize, adjustment);
        if (this.parent != null) {
            this.parent.adjustLength(newArraySize, adjustment);
        }
    }
}

