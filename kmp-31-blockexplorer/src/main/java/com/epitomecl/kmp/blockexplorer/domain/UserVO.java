package com.epitomecl.kmp.blockexplorer.domain;

public class UserVO {
    private String session;

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    @Override
    public String toString() {
        return "UserVO [" +
                "session=" + session +
                "]";
    }
}
