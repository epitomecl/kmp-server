package com.epitomecl.kmp.blockexplorer.domain;

public class SecretSharingVO {
    private int userIndex;
    private String label;
    private String sharedData;

    public int getUserIndex() {
        return userIndex;
    }

    public void setUserIndex(int user_index) {
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

    public void setSharedData(String shared_data) {
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
