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
public class ExportHistory {

    @JsonProperty("date")
    private String date;

    @JsonProperty("time")
    private String time;

    @JsonProperty("type")
    private String type;

    @JsonProperty("amount_btc")
    private double amountBtc;

    @JsonProperty("value_then")
    private String valueThen;

    @JsonProperty("value_now")
    private String valueNow;

    @JsonProperty("exchange_rate_then")
    private String exchangeRateThen;

    @JsonProperty("tx")
    private String tx;

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getType() {
        return type;
    }

    public double getAmountBtc() {
        return amountBtc;
    }

    public String getValueThen() {
        return valueThen;
    }

    public String getValueNow() {
        return valueNow;
    }

    public String getExchangeRateThen() {
        return exchangeRateThen;
    }

    public String getTx() {
        return tx;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setAmountBtc(double amount_btc) {
        this.amountBtc = amount_btc;
    }

    public void setValueThen(String value_then) {
        this.valueThen = value_then;
    }

    public void setValueNow(String value_now) {
        this.valueNow = value_now;
    }

    public void setExchangeRateThen(String exchange_rate_then) {
        this.exchangeRateThen = exchange_rate_then;
    }

    public void setTx(String tx) {
        this.tx = tx;
    }

    @JsonIgnore
    public static ExportHistory fromJson(String json) throws IOException {
        return new ObjectMapper().readValue(json, ExportHistory.class);
    }

    @JsonIgnore
    public String toJson() throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(this);
    }
}
