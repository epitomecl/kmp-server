/*
 * Decompiled with CFR 0_132.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Objects
 *  com.google.common.base.Preconditions
 *  javax.annotation.Nullable
 */
package org.bitcoinj.core;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import java.util.Arrays;
import javax.annotation.Nullable;
import org.bitcoinj.core.AddressFormatException;
import org.bitcoinj.core.ECKey;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.core.VersionedChecksummedBytes;
import org.bitcoinj.core.WrongNetworkException;

public class DumpedPrivateKey
extends VersionedChecksummedBytes {
    public static DumpedPrivateKey fromBase58(@Nullable NetworkParameters params, String base58) throws AddressFormatException {
        return new DumpedPrivateKey(params, base58);
    }

    DumpedPrivateKey(NetworkParameters params, byte[] keyBytes, boolean compressed) {
        super(params.getDumpedPrivateKeyHeader(), DumpedPrivateKey.encode(keyBytes, compressed));
    }

    private static byte[] encode(byte[] keyBytes, boolean compressed) {
        Preconditions.checkArgument((boolean)(keyBytes.length == 32), (Object)"Private keys must be 32 bytes");
        if (!compressed) {
            return keyBytes;
        }
        byte[] bytes = new byte[33];
        System.arraycopy(keyBytes, 0, bytes, 0, 32);
        bytes[32] = 1;
        return bytes;
    }

    @Deprecated
    public DumpedPrivateKey(@Nullable NetworkParameters params, String encoded) throws AddressFormatException {
        super(encoded);
        if (params != null && this.version != params.getDumpedPrivateKeyHeader()) {
            throw new WrongNetworkException(this.version, new int[]{params.getDumpedPrivateKeyHeader()});
        }
        if (this.bytes.length != 32 && this.bytes.length != 33) {
            throw new AddressFormatException("Wrong number of bytes for a private key, not 32 or 33");
        }
    }

    public ECKey getKey() {
        return ECKey.fromPrivate(Arrays.copyOf(this.bytes, 32), this.isPubKeyCompressed());
    }

    public boolean isPubKeyCompressed() {
        return this.bytes.length == 33 && this.bytes[32] == 1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        DumpedPrivateKey other = (DumpedPrivateKey)o;
        return this.version == other.version && Arrays.equals(this.bytes, other.bytes);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode((Object[])new Object[]{this.version, Arrays.hashCode(this.bytes)});
    }
}

