/*
 * Decompiled with CFR 0_132.
 */
package org.bitcoinj.core;

public class PeerException
extends Exception {
    public PeerException(String msg) {
        super(msg);
    }

    public PeerException(Exception e) {
        super(e);
    }

    public PeerException(String msg, Exception e) {
        super(msg, e);
    }
}

