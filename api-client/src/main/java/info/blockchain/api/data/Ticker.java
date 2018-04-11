package info.blockchain.api.data;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonAutoDetect(fieldVisibility = Visibility.NONE,
        getterVisibility = Visibility.NONE,
        setterVisibility = Visibility.NONE,
        creatorVisibility = Visibility.NONE,
        isGetterVisibility = Visibility.NONE)
public class Ticker {

    @JsonProperty("USD")
    private TickerItem USD;
    @JsonProperty("ISK")
    private TickerItem ISK;
    @JsonProperty("HKD")
    private TickerItem HKD;
    @JsonProperty("TWD")
    private TickerItem TWD;
    @JsonProperty("CHF")
    private TickerItem CHF;
    @JsonProperty("EUR")
    private TickerItem EUR;
    @JsonProperty("DKK")
    private TickerItem DKK;
    @JsonProperty("CLP")
    private TickerItem CLP;
    @JsonProperty("CAD")
    private TickerItem CAD;
    @JsonProperty("CNY")
    private TickerItem CNY;
    @JsonProperty("THB")
    private TickerItem THB;
    @JsonProperty("AUD")
    private TickerItem AUD;
    @JsonProperty("SGD")
    private TickerItem SGD;
    @JsonProperty("KRW")
    private TickerItem KRW;
    @JsonProperty("JPY")
    private TickerItem JPY;
    @JsonProperty("PLN")
    private TickerItem PLN;
    @JsonProperty("GBP")
    private TickerItem GBP;
    @JsonProperty("SEK")
    private TickerItem SEK;
    @JsonProperty("NZD")
    private TickerItem NZD;
    @JsonProperty("BRL")
    private TickerItem BRL;
    @JsonProperty("RUB")
    private TickerItem RUB;

    @JsonIgnore
    public TickerItem getUSD() {
        return USD;
    }

    @JsonIgnore
    public TickerItem getISK() {
        return ISK;
    }

    @JsonIgnore
    public TickerItem getHKD() {
        return HKD;
    }

    @JsonIgnore
    public TickerItem getTWD() {
        return TWD;
    }

    @JsonIgnore
    public TickerItem getCHF() {
        return CHF;
    }

    @JsonIgnore
    public TickerItem getEUR() {
        return EUR;
    }

    @JsonIgnore
    public TickerItem getDKK() {
        return DKK;
    }

    @JsonIgnore
    public TickerItem getCLP() {
        return CLP;
    }

    @JsonIgnore
    public TickerItem getCAD() {
        return CAD;
    }

    @JsonIgnore
    public TickerItem getCNY() {
        return CNY;
    }

    @JsonIgnore
    public TickerItem getTHB() {
        return THB;
    }

    @JsonIgnore
    public TickerItem getAUD() {
        return AUD;
    }

    @JsonIgnore
    public TickerItem getSGD() {
        return SGD;
    }

    @JsonIgnore
    public TickerItem getKRW() {
        return KRW;
    }

    @JsonIgnore
    public TickerItem getJPY() {
        return JPY;
    }

    @JsonIgnore
    public TickerItem getPLN() {
        return PLN;
    }

    @JsonIgnore
    public TickerItem getGBP() {
        return GBP;
    }

    @JsonIgnore
    public TickerItem getSEK() {
        return SEK;
    }

    @JsonIgnore
    public TickerItem getNZD() {
        return NZD;
    }

    @JsonIgnore
    public TickerItem getBRL() {
        return BRL;
    }

    @JsonIgnore
    public TickerItem getRUB() {
        return RUB;
    }

    public void setUSD(TickerItem USD) {
        this.USD = USD;
    }

    public void setISK(TickerItem ISK) {
        this.ISK = ISK;
    }

    public void setHKD(TickerItem HKD) {
        this.HKD = HKD;
    }

    public void setTWD(TickerItem TWD) {
        this.TWD = TWD;
    }

    public void setCHF(TickerItem CHF) {
        this.CHF = CHF;
    }

    public void setEUR(TickerItem EUR) {
        this.EUR = EUR;
    }

    public void setDKK(TickerItem DKK) {
        this.DKK = DKK;
    }

    public void setCLP(TickerItem CLP) {
        this.CLP = CLP;
    }

    public void setCAD(TickerItem CAD) {
        this.CAD = CAD;
    }

    public void setCNY(TickerItem CNY) {
        this.CNY = CNY;
    }

    public void setTHB(TickerItem THB) {
        this.THB = THB;
    }

    public void setAUD(TickerItem AUD) {
        this.AUD = AUD;
    }

    public void setSGD(TickerItem SGD) {
        this.SGD = SGD;
    }

    public void setKRW(TickerItem KRW) {
        this.KRW = KRW;
    }

    public void setJPY(TickerItem JPY) {
        this.JPY = JPY;
    }

    public void setPLN(TickerItem PLN) {
        this.PLN = PLN;
    }

    public void setGBP(TickerItem GBP) {
        this.GBP = GBP;
    }

    public void setSEK(TickerItem SEK) {
        this.SEK = SEK;
    }

    public void setNZD(TickerItem NZD) {
        this.NZD = NZD;
    }

    public void setBRL(TickerItem BRL) {
        this.BRL = BRL;
    }

    public void setRUB(TickerItem RUB) {
        this.RUB = RUB;
    }

    @JsonIgnore
    public static Ticker fromJson(String json) throws IOException {
        return new ObjectMapper().readValue(json, Ticker.class);
    }

    @JsonIgnore
    public String toJson() throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(this);
    }
}
