/*
 * Decompiled with CFR 0_132.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Preconditions
 */
package org.bitcoinj.core;

import com.google.common.base.Preconditions;
import org.bitcoinj.core.Block;
import org.bitcoinj.core.InventoryItem;
import org.bitcoinj.core.ListMessage;
import org.bitcoinj.core.MessageSerializer;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.core.ProtocolException;
import org.bitcoinj.core.Sha256Hash;
import org.bitcoinj.core.Transaction;

public class InventoryMessage
extends ListMessage {
    public static final int MAX_INV_SIZE = 50000;

    public InventoryMessage(NetworkParameters params, byte[] bytes) throws ProtocolException {
        super(params, bytes);
    }

    public InventoryMessage(NetworkParameters params, byte[] payload, MessageSerializer serializer, int length) throws ProtocolException {
        super(params, payload, serializer, length);
    }

    public InventoryMessage(NetworkParameters params) {
        super(params);
    }

    public void addBlock(Block block) {
        this.addItem(new InventoryItem(InventoryItem.Type.Block, block.getHash()));
    }

    public void addTransaction(Transaction tx) {
        this.addItem(new InventoryItem(InventoryItem.Type.Transaction, tx.getHash()));
    }

    public static /* varargs */ InventoryMessage with(Transaction ... txns) {
        Preconditions.checkArgument((boolean)(txns.length > 0));
        InventoryMessage result = new InventoryMessage(txns[0].getParams());
        for (Transaction tx : txns) {
            result.addTransaction(tx);
        }
        return result;
    }
}

