/*
 * Decompiled with CFR 0_132.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Objects
 */
package org.bitcoinj.core;

import com.google.common.base.Objects;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.Locale;
import org.bitcoinj.core.Coin;
import org.bitcoinj.core.Sha256Hash;
import org.bitcoinj.core.Utils;
import org.bitcoinj.script.Script;

public class UTXO
implements Serializable {
    private static final long serialVersionUID = 4736241649298988166L;
    private Coin value;
    private Script script;
    private Sha256Hash hash;
    private long index;
    private int height;
    private boolean coinbase;
    private String address;

    public UTXO(Sha256Hash hash, long index, Coin value, int height, boolean coinbase, Script script) {
        this.hash = hash;
        this.index = index;
        this.value = value;
        this.height = height;
        this.script = script;
        this.coinbase = coinbase;
        this.address = "";
    }

    public UTXO(Sha256Hash hash, long index, Coin value, int height, boolean coinbase, Script script, String address) {
        this(hash, index, value, height, coinbase, script);
        this.address = address;
    }

    public UTXO(InputStream in) throws IOException {
        this.deserializeFromStream(in);
    }

    public Coin getValue() {
        return this.value;
    }

    public Script getScript() {
        return this.script;
    }

    public Sha256Hash getHash() {
        return this.hash;
    }

    public long getIndex() {
        return this.index;
    }

    public int getHeight() {
        return this.height;
    }

    public boolean isCoinbase() {
        return this.coinbase;
    }

    public String getAddress() {
        return this.address;
    }

    public String toString() {
        return String.format(Locale.US, "Stored TxOut of %s (%s:%d)", this.value.toFriendlyString(), this.hash, this.index);
    }

    public int hashCode() {
        return Objects.hashCode((Object[])new Object[]{this.getIndex(), this.getHash()});
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        UTXO other = (UTXO)o;
        return this.getIndex() == other.getIndex() && this.getHash().equals(other.getHash());
    }

    public void serializeToStream(OutputStream bos) throws IOException {
        Utils.uint64ToByteStreamLE(BigInteger.valueOf(this.value.value), bos);
        byte[] scriptBytes = this.script.getProgram();
        bos.write(255 & scriptBytes.length);
        bos.write(255 & scriptBytes.length >> 8);
        bos.write(255 & scriptBytes.length >> 16);
        bos.write(255 & scriptBytes.length >> 24);
        bos.write(scriptBytes);
        bos.write(this.hash.getBytes());
        Utils.uint32ToByteStreamLE(this.index, bos);
        bos.write(255 & this.height);
        bos.write(255 & this.height >> 8);
        bos.write(255 & this.height >> 16);
        bos.write(255 & this.height >> 24);
        byte[] arrby = new byte[1];
        arrby[0] = (byte)(this.coinbase ? 1 : 0);
        bos.write(arrby);
    }

    public void deserializeFromStream(InputStream in) throws IOException {
        byte[] valueBytes = new byte[8];
        if (in.read(valueBytes, 0, 8) != 8) {
            throw new EOFException();
        }
        this.value = Coin.valueOf(Utils.readInt64(valueBytes, 0));
        int scriptBytesLength = in.read() & 255 | (in.read() & 255) << 8 | (in.read() & 255) << 16 | (in.read() & 255) << 24;
        byte[] scriptBytes = new byte[scriptBytesLength];
        if (in.read(scriptBytes) != scriptBytesLength) {
            throw new EOFException();
        }
        this.script = new Script(scriptBytes);
        byte[] hashBytes = new byte[32];
        if (in.read(hashBytes) != 32) {
            throw new EOFException();
        }
        this.hash = Sha256Hash.wrap(hashBytes);
        byte[] indexBytes = new byte[4];
        if (in.read(indexBytes) != 4) {
            throw new EOFException();
        }
        this.index = Utils.readUint32(indexBytes, 0);
        this.height = in.read() & 255 | (in.read() & 255) << 8 | (in.read() & 255) << 16 | (in.read() & 255) << 24;
        byte[] coinbaseByte = new byte[1];
        in.read(coinbaseByte);
        this.coinbase = coinbaseByte[0] == 1;
    }

    private void writeObject(ObjectOutputStream o) throws IOException {
        this.serializeToStream(o);
    }

    private void readObject(ObjectInputStream o) throws IOException, ClassNotFoundException {
        this.deserializeFromStream(o);
    }
}

