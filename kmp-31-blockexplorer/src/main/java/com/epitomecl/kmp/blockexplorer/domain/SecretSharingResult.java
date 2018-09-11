package com.epitomecl.kmp.blockexplorer.domain;

public class SecretSharingResult {
    private String result;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "SecretSharingResult [" +
                "result=" + result +
                "]";
    }
}
