/*
 * Decompiled with CFR 0_132.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Joiner
 */
package org.bitcoinj.core;

import com.google.common.base.Joiner;
import java.util.Collection;
import java.util.List;
import org.bitcoinj.core.GetBlocksMessage;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.core.ProtocolException;
import org.bitcoinj.core.Sha256Hash;
import org.bitcoinj.core.Utils;

public class GetHeadersMessage
extends GetBlocksMessage {
    public GetHeadersMessage(NetworkParameters params, List<Sha256Hash> locator, Sha256Hash stopHash) {
        super(params, locator, stopHash);
    }

    public GetHeadersMessage(NetworkParameters params, byte[] payload) throws ProtocolException {
        super(params, payload);
    }

    @Override
    public String toString() {
        return "getheaders: " + Utils.SPACE_JOINER.join((Iterable)this.locator);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        GetHeadersMessage other = (GetHeadersMessage)o;
        return this.version == other.version && this.stopHash.equals(other.stopHash) && this.locator.size() == other.locator.size() && this.locator.containsAll(other.locator);
    }

    @Override
    public int hashCode() {
        int hashCode = (int)this.version ^ "getheaders".hashCode() ^ this.stopHash.hashCode();
        for (Sha256Hash aLocator : this.locator) {
            hashCode ^= aLocator.hashCode();
        }
        return hashCode;
    }
}

