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
public class WalletAddress {

    @JsonProperty("address")
    private String address;

    @JsonProperty("label")
    private String label;

    @JsonProperty("balance")
    private BigInteger balance;

    @JsonProperty("total_received")
    private BigInteger totalReceived;

    public String getAddress() {
        return address;
    }

    public String getLabel() {
        return label;
    }

    public BigInteger getBalance() {
        return balance;
    }

    public BigInteger getTotalReceived() {
        return totalReceived;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setBalance(BigInteger balance) {
        this.balance = balance;
    }

    public void setTotalReceived(BigInteger totalReceived) {
        this.totalReceived = totalReceived;
    }

    @JsonIgnore
    public static WalletAddress fromJson(String json) throws IOException {
        return new ObjectMapper().readValue(json, WalletAddress.class);
    }

    @JsonIgnore
    public String toJson() throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(this);
    }
}
