package info.blockchain.api.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.math.BigInteger;

public class MultiAddressBalance {

    @JsonProperty("n_tx")
    private int txCount;

    @JsonProperty("n_tx_filtered")
    private int txCountFiltered;

    @JsonProperty("total_received")
    private BigInteger totalReceived;

    @JsonProperty("total_sent")
    private BigInteger totalSent;

    @JsonProperty("final_balance")
    private BigInteger finalBalance;

    public int getTxCount() {
        return txCount;
    }

    public void setTxCount(int txCount) {
        this.txCount = txCount;
    }

    public BigInteger getTotalReceived() {
        return totalReceived;
    }

    public BigInteger getTotalSent() {
        return totalSent;
    }

    public BigInteger getFinalBalance() {
        return finalBalance;
    }

    public void setFinalBalance(BigInteger final_balance) {
        this.finalBalance = final_balance;
    }

    public void setTotalReceived(BigInteger total_received) {
        this.totalReceived = total_received;
    }

    public void setTotalSent(BigInteger total_sent) {
        this.totalSent = total_sent;
    }

    public int getTxCountFiltered() {
        return txCountFiltered;
    }

    public void setTxCountFiltered(int txCountFiltered) {
        this.txCountFiltered = txCountFiltered;
    }

    @JsonIgnore
    public static MultiAddressBalance fromJson(String json) throws IOException {
        return new ObjectMapper().readValue(json, MultiAddressBalance.class);
    }

    @JsonIgnore
    public String toJson() throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(this);
    }
}
