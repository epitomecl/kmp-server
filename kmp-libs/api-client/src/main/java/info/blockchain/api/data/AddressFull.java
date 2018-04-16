package info.blockchain.api.data;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonAutoDetect(fieldVisibility = Visibility.NONE,
        getterVisibility = Visibility.NONE,
        setterVisibility = Visibility.NONE,
        creatorVisibility = Visibility.NONE,
        isGetterVisibility = Visibility.NONE)
public class AddressFull {

    @JsonProperty("hash160")
    private String hash160;

    @JsonProperty("address")
    private String address;

    @JsonProperty("n_tx")
    private int txCount;

    @JsonProperty("total_received")
    private BigInteger totalReceived;

    @JsonProperty("total_sent")
    private BigInteger totalSent;

    @JsonProperty("final_balance")
    private BigInteger finalBalance;

    @JsonProperty("txs")
    private ArrayList<Transaction> txs;

    public String getHash160() {
        return hash160;
    }

    public String getAddress() {
        return address;
    }

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

    public ArrayList<Transaction> getTxs() {
        return txs;
    }

    public void setFinalBalance(BigInteger final_balance) {
        this.finalBalance = final_balance;
    }

    public void setHash160(String hash160) {
        this.hash160 = hash160;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setTotalReceived(BigInteger total_received) {
        this.totalReceived = total_received;
    }

    public void setTotalSent(BigInteger total_sent) {
        this.totalSent = total_sent;
    }

    public void setTxs(ArrayList<Transaction> txs) {
        this.txs = txs;
    }

    @JsonIgnore
    public static AddressFull fromJson(String json) throws IOException {
        return new ObjectMapper().readValue(json, AddressFull.class);
    }

    @JsonIgnore
    public String toJson() throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(this);
    }
}
