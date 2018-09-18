package com.epitomecl.kmp.api.domain;

public class SendTXResult {

    private String hashtx;
    private String error;

    public String getHashtx() {
        return hashtx;
    }

    public void setHashtx(String hashtx) {
        this.hashtx = hashtx;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    @Override
    public String toString() {
        return "SendTXResult [" +
                "hashtx=" + hashtx +
                "error=" + error +
                "]";
    }
}
