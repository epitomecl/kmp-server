/*
 * Decompiled with CFR 0_132.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Objects
 */
package org.bitcoinj.crypto;

import com.google.common.base.Objects;
import java.util.Arrays;

public final class EncryptedData {
    public final byte[] initialisationVector;
    public final byte[] encryptedBytes;

    public EncryptedData(byte[] initialisationVector, byte[] encryptedBytes) {
        this.initialisationVector = Arrays.copyOf(initialisationVector, initialisationVector.length);
        this.encryptedBytes = Arrays.copyOf(encryptedBytes, encryptedBytes.length);
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        EncryptedData other = (EncryptedData)o;
        return Arrays.equals(this.encryptedBytes, other.encryptedBytes) && Arrays.equals(this.initialisationVector, other.initialisationVector);
    }

    public int hashCode() {
        return Objects.hashCode((Object[])new Object[]{Arrays.hashCode(this.encryptedBytes), Arrays.hashCode(this.initialisationVector)});
    }

    public String toString() {
        return "EncryptedData [initialisationVector=" + Arrays.toString(this.initialisationVector) + ", encryptedPrivateKey=" + Arrays.toString(this.encryptedBytes) + "]";
    }
}

