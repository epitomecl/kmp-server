package com.epitomecl.kmp.dc.entity.fund;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Date;

@Embeddable
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"}, ignoreUnknown = true)
public class FundSisePk implements Serializable {

    @Column(name = "종목코드", nullable = false, updatable = false)
    private String 종목코드;

    @Column(name = "날짜", nullable = false, updatable = false)
    private Date 날짜;

    protected FundSisePk() {
    }

    public FundSisePk(String 종목코드, Date 날짜) {
        this.종목코드 = 종목코드;
        this.날짜 = 날짜;
    }

    public String get종목코드() {
        return 종목코드;
    }

    public void set종목코드(String 종목코드) {
        this.종목코드 = 종목코드;
    }

    public Date get날짜() {
        return 날짜;
    }

    public void set날짜(Date 날짜) {
        this.날짜 = 날짜;
    }

}
