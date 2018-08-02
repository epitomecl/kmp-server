package com.epitomecl.kmp.blockexplorer.domain;

public class SendTXResult {

    private String hashtx;

    public String getHashtx() {
        return hashtx;
    }

    public void setHashtx(String hashtx) {
        this.hashtx = hashtx;
    }

    @Override
    public String toString() {
        return "SendTXResult [" +
                "hashtx=" + hashtx +
                "]";
    }
}
