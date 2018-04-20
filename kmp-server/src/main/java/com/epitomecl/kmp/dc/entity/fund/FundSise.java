package com.epitomecl.kmp.dc.entity.fund;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import java.util.Date;

@Entity
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"}, ignoreUnknown = true)
public class FundSise {

    public static String[] 필드명배열() {
        String[] 필드명배열 = {
                "종목코드",
                "날짜",
                "기준가",
                "전일대비",
                "등락율",
                "설정액",
                "순자산액",
                "과표기준가"
        };
        return 필드명배열;
    }

    @EmbeddedId
    private FundSisePk pk;

    private double 기준가;
    private double 전일대비;
    private double 등락율; // (%)
    private double 설정액; // (억)
    private double 순자산액; // (억)
    private double 과표기준가;

    public FundSise() {
    }

    public String get종목코드() {
        return pk.get종목코드();
    }

    public Date get날짜() {
        return pk.get날짜();
    }

    public FundSisePk getPk() {
        return pk;
    }

    public void setPk(FundSisePk pk) {
        this.pk = pk;
    }

    public double get기준가() {
        return 기준가;
    }

    public void set기준가(double 기준가) {
        this.기준가 = 기준가;
    }

    public double get전일대비() {
        return 전일대비;
    }

    public void set전일대비(double 전일대비) {
        this.전일대비 = 전일대비;
    }

    public double get등락율() {
        return 등락율;
    }

    public void set등락율(double 등락율) {
        this.등락율 = 등락율;
    }

    public double get설정액() {
        return 설정액;
    }

    public void set설정액(double 설정액) {
        this.설정액 = 설정액;
    }

    public double get순자산액() {
        return 순자산액;
    }

    public void set순자산액(double 순자산액) {
        this.순자산액 = 순자산액;
    }

    public double get과표기준가() {
        return 과표기준가;
    }

    public void set과표기준가(double 과표기준가) {
        this.과표기준가 = 과표기준가;
    }
}
