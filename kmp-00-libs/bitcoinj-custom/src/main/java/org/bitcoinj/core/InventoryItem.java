/*
 * Decompiled with CFR 0_132.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Objects
 */
package org.bitcoinj.core;

import com.google.common.base.Objects;
import org.bitcoinj.core.Sha256Hash;

public class InventoryItem {
    static final int MESSAGE_LENGTH = 36;
    public final Type type;
    public final Sha256Hash hash;

    public InventoryItem(Type type, Sha256Hash hash) {
        this.type = type;
        this.hash = hash;
    }

    public String toString() {
        return (Object)((Object)this.type) + ": " + this.hash;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        InventoryItem other = (InventoryItem)o;
        return this.type == other.type && this.hash.equals(other.hash);
    }

    public int hashCode() {
        return Objects.hashCode((Object[])new Object[]{this.type, this.hash});
    }

    public static enum Type {
        Error,
        Transaction,
        Block,
        FilteredBlock;
        

        private Type() {
        }
    }

}

