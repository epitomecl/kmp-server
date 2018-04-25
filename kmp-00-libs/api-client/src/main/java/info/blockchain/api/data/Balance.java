package info.blockchain.api.data;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.math.BigInteger;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonAutoDetect(fieldVisibility = Visibility.NONE,
        getterVisibility = Visibility.NONE,
        setterVisibility = Visibility.NONE,
        creatorVisibility = Visibility.NONE,
        isGetterVisibility = Visibility.NONE)
public class Balance {

    @JsonProperty("final_balance")
    private BigInteger finalBalance;

    @JsonProperty("n_tx")
    private long txCount;

    @JsonProperty("total_received")
    private BigInteger totalReceived;

    public BigInteger getFinalBalance() {
        return finalBalance;
    }

    public long getTxCount() {
        return txCount;
    }

    public BigInteger getTotalReceived() {
        return totalReceived;
    }

    public void setFinalBalance(BigInteger final_balance) {
        this.finalBalance = final_balance;
    }

    public void setTxCount(long n_tx) {
        this.txCount = n_tx;
    }

    public void setTotalReceived(BigInteger total_received) {
        this.totalReceived = total_received;
    }

    @JsonIgnore
    public static Balance fromJson(String json) throws IOException {
        return new ObjectMapper().readValue(json, Balance.class);
    }

    @JsonIgnore
    public String toJson() throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(this);
    }
}
