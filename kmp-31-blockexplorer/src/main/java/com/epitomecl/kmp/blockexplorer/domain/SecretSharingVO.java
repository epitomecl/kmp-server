package com.epitomecl.kmp.blockexplorer.domain;

public class SecretSharingVO {
    private int userIndex;
    private String label;
    private String sharedData;

    public int getUserIndex() {
        return userIndex;
    }

    public void setUserIndex(int userIndex) {
        this.userIndex = userIndex;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getSharedData() {
        return sharedData;
    }

    public void setSharedData(String sharedData) {
        this.sharedData = sharedData;
    }

    @Override
    public String toString() {
        return "SecretSharingVO [" +
                "userIndex=" + userIndex +
                "label=" + label +
                "sharedData=" + sharedData +
                "]";
    }
}
