/*
 * Decompiled with CFR 0_132.
 */
package org.bitcoinj.core;

import org.bitcoinj.core.EmptyMessage;
import org.bitcoinj.core.NetworkParameters;

public class GetAddrMessage
extends EmptyMessage {
    public GetAddrMessage(NetworkParameters params) {
        super(params);
    }
}

