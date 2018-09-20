/*
 * Decompiled with CFR 0_132.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Objects
 *  com.google.common.base.Preconditions
 *  com.google.common.primitives.Ints
 *  com.google.common.primitives.UnsignedBytes
 */
package org.bitcoinj.core;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.primitives.Ints;
import com.google.common.primitives.UnsignedBytes;
import java.io.Serializable;
import java.util.Arrays;
import org.bitcoinj.core.AddressFormatException;
import org.bitcoinj.core.Base58;
import org.bitcoinj.core.Sha256Hash;

public class VersionedChecksummedBytes
implements Serializable,
Cloneable,
Comparable<VersionedChecksummedBytes> {
    protected final int version;
    protected byte[] bytes;

    protected VersionedChecksummedBytes(String encoded) throws AddressFormatException {
        byte[] versionAndDataBytes = Base58.decodeChecked(encoded);
        byte versionByte = versionAndDataBytes[0];
        this.version = versionByte & 255;
        this.bytes = new byte[versionAndDataBytes.length - 1];
        System.arraycopy(versionAndDataBytes, 1, this.bytes, 0, versionAndDataBytes.length - 1);
    }

    protected VersionedChecksummedBytes(int version, byte[] bytes) {
        Preconditions.checkArgument((boolean)(version >= 0 && version < 256));
        this.version = version;
        this.bytes = bytes;
    }

    public final String toBase58() {
        byte[] addressBytes = new byte[1 + this.bytes.length + 4];
        addressBytes[0] = (byte)this.version;
        System.arraycopy(this.bytes, 0, addressBytes, 1, this.bytes.length);
        byte[] checksum = Sha256Hash.hashTwice(addressBytes, 0, this.bytes.length + 1);
        System.arraycopy(checksum, 0, addressBytes, this.bytes.length + 1, 4);
        return Base58.encode(addressBytes);
    }

    public String toString() {
        return this.toBase58();
    }

    public int hashCode() {
        return Objects.hashCode((Object[])new Object[]{this.version, Arrays.hashCode(this.bytes)});
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        VersionedChecksummedBytes other = (VersionedChecksummedBytes)o;
        return this.version == other.version && Arrays.equals(this.bytes, other.bytes);
    }

    public VersionedChecksummedBytes clone() throws CloneNotSupportedException {
        return (VersionedChecksummedBytes)super.clone();
    }

    @Override
    public int compareTo(VersionedChecksummedBytes o) {
        int result = Ints.compare((int)this.version, (int)o.version);
        return result != 0 ? result : UnsignedBytes.lexicographicalComparator().compare(this.bytes, o.bytes);
    }

    public int getVersion() {
        return this.version;
    }
}

