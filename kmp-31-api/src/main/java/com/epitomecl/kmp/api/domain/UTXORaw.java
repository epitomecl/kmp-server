package com.epitomecl.kmp.api.domain;

import java.math.BigInteger;

public class UTXORaw {

    private byte[] hash;
    private int index;
    private BigInteger value;
    private byte[] scriptBytes;
    private String toaddress;

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

    public String getToAddress() {
        return toaddress;
    }

    public void setToAddress(String toaddress) {
        this.toaddress = toaddress;
    }

    @Override
    public String toString() {
        return "UTXORaw [" +
                "hash=" + hash +
                ", index=" + index +
                ", value=" + value +
                ", scriptBytes=" + scriptBytes +
                ", toaddress=" + toaddress +
                "]";
    }
}
