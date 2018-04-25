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
public class TickerItem {

    @JsonProperty("15m")
    private double min15;

    @JsonProperty("last")
    private double last;

    @JsonProperty("buy")
    private double buy;

    @JsonProperty("sell")
    private double sell;

    @JsonProperty("symbol")
    private String symbol;

    public double getMin15() {
        return min15;
    }

    public void setMin15(double min15) {
        this.min15 = min15;
    }

    public void setLast(double last) {
        this.last = last;
    }

    public void setBuy(double buy) {
        this.buy = buy;
    }

    public void setSell(double sell) {
        this.sell = sell;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public double getLast() {
        return last;
    }

    public double getBuy() {
        return buy;
    }

    public double getSell() {
        return sell;
    }

    public String getSymbol() {
        return symbol;
    }

    @JsonIgnore
    public static TickerItem fromJson(String json) throws IOException {
        return new ObjectMapper().readValue(json, TickerItem.class);
    }

    @JsonIgnore
    public String toJson() throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(this);
    }
}
