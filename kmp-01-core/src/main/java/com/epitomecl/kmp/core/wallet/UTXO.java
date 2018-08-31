package com.epitomecl.kmp.core.wallet;

import java.math.BigInteger;

public class UTXO {

    private String hash;
    private int index;
    private BigInteger value;
    private String scriptBytes;
    private String toaddress;

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
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

    public String getScriptBytes() {
        return scriptBytes;
    }

    public void setScriptBytes(String scriptBytes) {
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
        return "UTXO [" +
                "hash=" + hash +
                ", index=" + index +
                ", value=" + value +
                ", scriptBytes=" + scriptBytes +
                ", toaddress=" + toaddress +
                "]";
    }
}
