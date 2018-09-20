/*
 * Decompiled with CFR 0_132.
 */
package org.bitcoinj.core;

import org.bitcoinj.core.Utils;

public class VarInt {
    public final long value;
    private final int originallyEncodedSize;

    public VarInt(long value) {
        this.value = value;
        this.originallyEncodedSize = this.getSizeInBytes();
    }

    public VarInt(byte[] buf, int offset) {
        int first = 255 & buf[offset];
        if (first < 253) {
            this.value = first;
            this.originallyEncodedSize = 1;
        } else if (first == 253) {
            this.value = 255 & buf[offset + 1] | (255 & buf[offset + 2]) << 8;
            this.originallyEncodedSize = 3;
        } else if (first == 254) {
            this.value = Utils.readUint32(buf, offset + 1);
            this.originallyEncodedSize = 5;
        } else {
            this.value = Utils.readInt64(buf, offset + 1);
            this.originallyEncodedSize = 9;
        }
    }

    public int getOriginalSizeInBytes() {
        return this.originallyEncodedSize;
    }

    public final int getSizeInBytes() {
        return VarInt.sizeOf(this.value);
    }

    public static int sizeOf(long value) {
        if (value < 0L) {
            return 9;
        }
        if (value < 253L) {
            return 1;
        }
        if (value <= 65535L) {
            return 3;
        }
        if (value <= 0xFFFFFFFFL) {
            return 5;
        }
        return 9;
    }

    public byte[] encode() {
        switch (VarInt.sizeOf(this.value)) {
            case 1: {
                return new byte[]{(byte)this.value};
            }
            case 3: {
                return new byte[]{-3, (byte)this.value, (byte)(this.value >> 8)};
            }
            case 5: {
                byte[] bytes = new byte[5];
                bytes[0] = -2;
                Utils.uint32ToByteArrayLE(this.value, bytes, 1);
                return bytes;
            }
        }
        byte[] bytes = new byte[9];
        bytes[0] = -1;
        Utils.uint64ToByteArrayLE(this.value, bytes, 1);
        return bytes;
    }
}

