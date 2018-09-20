/*
 * Decompiled with CFR 0_132.
 */
package org.bitcoinj.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.bitcoinj.core.InventoryItem;
import org.bitcoinj.core.InventoryMessage;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.core.ProtocolException;

public class NotFoundMessage
extends InventoryMessage {
    public static int MIN_PROTOCOL_VERSION = 70001;

    public NotFoundMessage(NetworkParameters params) {
        super(params);
    }

    public NotFoundMessage(NetworkParameters params, byte[] payloadBytes) throws ProtocolException {
        super(params, payloadBytes);
    }

    public NotFoundMessage(NetworkParameters params, List<InventoryItem> items) {
        super(params);
        this.items = new ArrayList<InventoryItem>(items);
    }
}

