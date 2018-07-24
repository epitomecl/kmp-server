package com.epitomecl.kmp.blockexplorer.domain;

import java.math.BigInteger;

public class UTXORaw {

    private byte[] hash;
    private int index;
    private BigInteger value;
    private byte[] scriptBytes;

    public byte[] getHash() {
        return hash;
    }

    public void setHash(byte[] hash) {
        this.hash = hash;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public BigInteger getValue() {
        return value;
    }

    public void setValue(BigInteger value) {
        this.value = value;
    }

    public byte[] getScriptBytes() {
        return scriptBytes;
    }

    public void setScriptBytes(byte[] scriptBytes) {
        this.scriptBytes = scriptBytes;
    }

    @Override
    public String toString() {
        return "UTXO [" +
                "hash=" + hash +
                ", index=" + index +
                ", value=" + value +
                ", scriptBytes=" + scriptBytes +
                "]";
    }
}
