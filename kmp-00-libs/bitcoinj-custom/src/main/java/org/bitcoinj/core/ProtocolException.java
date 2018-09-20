/*
 * Decompiled with CFR 0_132.
 */
package org.bitcoinj.core;

import org.bitcoinj.core.VerificationException;

public class ProtocolException
extends VerificationException {
    public ProtocolException(String msg) {
        super(msg);
    }

    public ProtocolException(Exception e) {
        super(e);
    }

    public ProtocolException(String msg, Exception e) {
        super(msg, e);
    }
}

