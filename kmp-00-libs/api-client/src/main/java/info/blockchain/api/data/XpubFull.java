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
public class XpubFull {

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

    @JsonProperty("change_index")
    private int changeIndex;

    @JsonProperty("account_index")
    private int accountIndex;

    @JsonProperty("gap_limit")
    private int gapLimit;

    @JsonProperty("txs")
    private ArrayList<Transaction> txs;

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

    public int getChangeIndex() {
        return changeIndex;
    }

    public int getAccountIndex() {
        return accountIndex;
    }

    public void setFinalBalance(BigInteger final_balance) {
        this.finalBalance = final_balance;
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

    public void setChangeIndex(int change_index) {
        this.changeIndex = change_index;
    }

    public void setAccountIndex(int account_index) {
        this.accountIndex = account_index;
    }

    public int getGapLimit() {
        return gapLimit;
    }

    public void setGapLimit(int gapLimit) {
        this.gapLimit = gapLimit;
    }

    public ArrayList<Transaction> getTxs() {
        return txs;
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

    public XpubFull(AddressSummary addressSummary, ArrayList<Transaction> txs) {
        this.address = addressSummary.getAddress();
        this.txCount = addressSummary.getTxCount();
        this.accountIndex = addressSummary.getAccountIndex();
        this.changeIndex = addressSummary.getChangeIndex();
        this.finalBalance = addressSummary.getFinalBalance();
        this.gapLimit = addressSummary.getGapLimit();
        this.totalReceived = addressSummary.getTotalReceived();
        this.totalSent = addressSummary.getTotalSent();
        this.txs = txs;
    }
}
