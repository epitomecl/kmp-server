/*
 * Decompiled with CFR 0_132.
 */
package org.bitcoinj.core;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import org.bitcoinj.core.Utils;

public class UnsafeByteArrayOutputStream
extends ByteArrayOutputStream {
    public UnsafeByteArrayOutputStream() {
        super(32);
    }

    public UnsafeByteArrayOutputStream(int size) {
        super(size);
    }

    @Override
    public void write(int b) {
        int newcount = this.count + 1;
        if (newcount > this.buf.length) {
            this.buf = Utils.copyOf(this.buf, Math.max(this.buf.length << 1, newcount));
        }
        this.buf[this.count] = (byte)b;
        this.count = newcount;
    }

    @Override
    public void write(byte[] b, int off, int len) {
        if (off < 0 || off > b.length || len < 0 || off + len > b.length || off + len < 0) {
            throw new IndexOutOfBoundsException();
        }
        if (len == 0) {
            return;
        }
        int newcount = this.count + len;
        if (newcount > this.buf.length) {
            this.buf = Utils.copyOf(this.buf, Math.max(this.buf.length << 1, newcount));
        }
        System.arraycopy(b, off, this.buf, this.count, len);
        this.count = newcount;
    }

    @Override
    public void writeTo(OutputStream out) throws IOException {
        out.write(this.buf, 0, this.count);
    }

    @Override
    public void reset() {
        this.count = 0;
    }

    @Override
    public byte[] toByteArray() {
        return this.count == this.buf.length ? this.buf : Utils.copyOf(this.buf, this.count);
    }

    @Override
    public int size() {
        return this.count;
    }
}

