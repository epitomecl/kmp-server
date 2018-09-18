package com.epitomecl.kmp.api.domain;

public class EncryptedResult {
    private String result;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "EncryptedResult [" +
                "result=" + result +
                "]";
    }
}
