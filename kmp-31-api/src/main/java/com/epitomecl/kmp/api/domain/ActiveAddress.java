package com.epitomecl.kmp.api.domain;

public class ActiveAddress {
    private String address;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "ActiveAddress [" +
                "address=" + address +
                "]";
    }
}
