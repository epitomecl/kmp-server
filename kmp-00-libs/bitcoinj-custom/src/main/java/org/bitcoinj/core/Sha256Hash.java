/*
 * Decompiled with CFR 0_132.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Preconditions
 *  com.google.common.io.BaseEncoding
 *  com.google.common.io.ByteStreams
 *  com.google.common.primitives.Ints
 */
package org.bitcoinj.core;

import com.google.common.base.Preconditions;
import com.google.common.io.BaseEncoding;
import com.google.common.io.ByteStreams;
import com.google.common.primitives.Ints;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import org.bitcoinj.core.Utils;

public class Sha256Hash
implements Serializable,
Comparable<Sha256Hash> {
    public static final int LENGTH = 32;
    public static final Sha256Hash ZERO_HASH = Sha256Hash.wrap(new byte[32]);
    private final byte[] bytes;

    @Deprecated
    public Sha256Hash(byte[] rawHashBytes) {
        Preconditions.checkArgument((boolean)(rawHashBytes.length == 32));
        this.bytes = rawHashBytes;
    }

    @Deprecated
    public Sha256Hash(String hexString) {
        Preconditions.checkArgument((boolean)(hexString.length() == 64));
        this.bytes = Utils.HEX.decode((CharSequence)hexString);
    }

    public static Sha256Hash wrap(byte[] rawHashBytes) {
        return new Sha256Hash(rawHashBytes);
    }

    public static Sha256Hash wrap(String hexString) {
        return Sha256Hash.wrap(Utils.HEX.decode((CharSequence)hexString));
    }

    public static Sha256Hash wrapReversed(byte[] rawHashBytes) {
        return Sha256Hash.wrap(Utils.reverseBytes(rawHashBytes));
    }

    @Deprecated
    public static Sha256Hash create(byte[] contents) {
        return Sha256Hash.of(contents);
    }

    public static Sha256Hash of(byte[] contents) {
        return Sha256Hash.wrap(Sha256Hash.hash(contents));
    }

    @Deprecated
    public static Sha256Hash createDouble(byte[] contents) {
        return Sha256Hash.twiceOf(contents);
    }

    public static Sha256Hash twiceOf(byte[] contents) {
        return Sha256Hash.wrap(Sha256Hash.hashTwice(contents));
    }

    public static Sha256Hash of(File file) throws IOException {
        FileInputStream in = new FileInputStream(file);
        try {
            Sha256Hash sha256Hash = Sha256Hash.of(ByteStreams.toByteArray((InputStream)in));
            return sha256Hash;
        }
        finally {
            in.close();
        }
    }

    public static MessageDigest newDigest() {
        try {
            return MessageDigest.getInstance("SHA-256");
        }
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static byte[] hash(byte[] input) {
        return Sha256Hash.hash(input, 0, input.length);
    }

    public static byte[] hash(byte[] input, int offset, int length) {
        MessageDigest digest = Sha256Hash.newDigest();
        digest.update(input, offset, length);
        return digest.digest();
    }

    public static byte[] hashTwice(byte[] input) {
        return Sha256Hash.hashTwice(input, 0, input.length);
    }

    public static byte[] hashTwice(byte[] input, int offset, int length) {
        MessageDigest digest = Sha256Hash.newDigest();
        digest.update(input, offset, length);
        return digest.digest(digest.digest());
    }

    public static byte[] hashTwice(byte[] input1, int offset1, int length1, byte[] input2, int offset2, int length2) {
        MessageDigest digest = Sha256Hash.newDigest();
        digest.update(input1, offset1, length1);
        digest.update(input2, offset2, length2);
        return digest.digest(digest.digest());
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        return Arrays.equals(this.bytes, ((Sha256Hash)o).bytes);
    }

    public int hashCode() {
        return Ints.fromBytes((byte)this.bytes[28], (byte)this.bytes[29], (byte)this.bytes[30], (byte)this.bytes[31]);
    }

    public String toString() {
        return Utils.HEX.encode(this.bytes);
    }

    public BigInteger toBigInteger() {
        return new BigInteger(1, this.bytes);
    }

    public byte[] getBytes() {
        return this.bytes;
    }

    public byte[] getReversedBytes() {
        return Utils.reverseBytes(this.bytes);
    }

    @Override
    public int compareTo(Sha256Hash other) {
        for (int i = 31; i >= 0; --i) {
            int thisByte = this.bytes[i] & 255;
            int otherByte = other.bytes[i] & 255;
            if (thisByte > otherByte) {
                return 1;
            }
            if (thisByte >= otherByte) continue;
            return -1;
        }
        return 0;
    }
}

