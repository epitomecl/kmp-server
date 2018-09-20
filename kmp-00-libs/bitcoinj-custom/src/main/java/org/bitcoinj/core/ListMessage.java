/*
 * Decompiled with CFR 0_132.
 */
package org.bitcoinj.core;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.bitcoinj.core.InventoryItem;
import org.bitcoinj.core.Message;
import org.bitcoinj.core.MessageSerializer;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.core.ProtocolException;
import org.bitcoinj.core.Sha256Hash;
import org.bitcoinj.core.Utils;
import org.bitcoinj.core.VarInt;

public abstract class ListMessage
extends Message {
    private long arrayLen;
    protected List<InventoryItem> items;
    public static final long MAX_INVENTORY_ITEMS = 50000L;

    public ListMessage(NetworkParameters params, byte[] bytes) throws ProtocolException {
        super(params, bytes, 0);
    }

    public ListMessage(NetworkParameters params, byte[] payload, MessageSerializer serializer, int length) throws ProtocolException {
        super(params, payload, 0, serializer, length);
    }

    public ListMessage(NetworkParameters params) {
        super(params);
        this.items = new ArrayList<InventoryItem>();
        this.length = 1;
    }

    public List<InventoryItem> getItems() {
        return Collections.unmodifiableList(this.items);
    }

    public void addItem(InventoryItem item) {
        this.unCache();
        this.length -= VarInt.sizeOf(this.items.size());
        this.items.add(item);
        this.length += VarInt.sizeOf(this.items.size()) + 36;
    }

    public void removeItem(int index) {
        this.unCache();
        this.length -= VarInt.sizeOf(this.items.size());
        this.items.remove(index);
        this.length += VarInt.sizeOf(this.items.size()) - 36;
    }

    @Override
    protected void parse() throws ProtocolException {
        this.arrayLen = this.readVarInt();
        if (this.arrayLen > 50000L) {
            throw new ProtocolException("Too many items in INV message: " + this.arrayLen);
        }
        this.length = (int)((long)(this.cursor - this.offset) + this.arrayLen * 36L);
        this.items = new ArrayList<InventoryItem>((int)this.arrayLen);
        int i = 0;
        while ((long)i < this.arrayLen) {
            InventoryItem.Type type;
            if (this.cursor + 36 > this.payload.length) {
                throw new ProtocolException("Ran off the end of the INV");
            }
            int typeCode = (int)this.readUint32();
            switch (typeCode) {
                case 0: {
                    type = InventoryItem.Type.Error;
                    break;
                }
                case 1: {
                    type = InventoryItem.Type.Transaction;
                    break;
                }
                case 2: {
                    type = InventoryItem.Type.Block;
                    break;
                }
                case 3: {
                    type = InventoryItem.Type.FilteredBlock;
                    break;
                }
                default: {
                    throw new ProtocolException("Unknown CInv type: " + typeCode);
                }
            }
            InventoryItem item = new InventoryItem(type, this.readHash());
            this.items.add(item);
            ++i;
        }
        this.payload = null;
    }

    @Override
    public void bitcoinSerializeToStream(OutputStream stream) throws IOException {
        stream.write(new VarInt(this.items.size()).encode());
        for (InventoryItem i : this.items) {
            Utils.uint32ToByteStreamLE(i.type.ordinal(), stream);
            stream.write(i.hash.getReversedBytes());
        }
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        return this.items.equals(((ListMessage)o).items);
    }

    public int hashCode() {
        return this.items.hashCode();
    }
}

