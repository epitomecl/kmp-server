package com.epitomecl.kmp.api.domain;

public class UserVO {
    private String session;
    private int index;
    private String id;

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "UserVO [" +
                "session=" + session +
                "index=" + index +
                "id=" + id +
                "]";
    }
}
