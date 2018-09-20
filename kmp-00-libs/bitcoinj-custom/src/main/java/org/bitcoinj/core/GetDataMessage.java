/*
 * Decompiled with CFR 0_132.
 */
package org.bitcoinj.core;

import java.util.List;
import org.bitcoinj.core.InventoryItem;
import org.bitcoinj.core.ListMessage;
import org.bitcoinj.core.MessageSerializer;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.core.ProtocolException;
import org.bitcoinj.core.Sha256Hash;

public class GetDataMessage
extends ListMessage {
    public GetDataMessage(NetworkParameters params, byte[] payloadBytes) throws ProtocolException {
        super(params, payloadBytes);
    }

    public GetDataMessage(NetworkParameters params, byte[] payload, MessageSerializer serializer, int length) throws ProtocolException {
        super(params, payload, serializer, length);
    }

    public GetDataMessage(NetworkParameters params) {
        super(params);
    }

    public void addTransaction(Sha256Hash hash) {
        this.addItem(new InventoryItem(InventoryItem.Type.Transaction, hash));
    }

    public void addBlock(Sha256Hash hash) {
        this.addItem(new InventoryItem(InventoryItem.Type.Block, hash));
    }

    public void addFilteredBlock(Sha256Hash hash) {
        this.addItem(new InventoryItem(InventoryItem.Type.FilteredBlock, hash));
    }

    public Sha256Hash getHashOf(int i) {
        return this.getItems().get((int)i).hash;
    }
}

