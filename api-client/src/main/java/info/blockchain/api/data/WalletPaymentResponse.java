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
public class WalletPaymentResponse {

    @JsonProperty("to")
    private ArrayList<String> toList;

    @JsonProperty("amounts")
    private ArrayList<BigInteger> amountList;

    @JsonProperty("from")
    private ArrayList<String> fromList;

    @JsonProperty("fee")
    private BigInteger fee;

    @JsonProperty("txid")
    private String txId;

    @JsonProperty("tx_hash")
    private String txHash;

    @JsonProperty("message")
    private String message;

    @JsonProperty("success")
    private boolean success;

    public ArrayList<String> getToList() {
        return toList;
    }

    public ArrayList<BigInteger> getAmountList() {
        return amountList;
    }

    public ArrayList<String> getFromList() {
        return fromList;
    }

    public BigInteger getFee() {
        return fee;
    }

    public String getTxId() {
        return txId;
    }

    public String getTxHash() {
        return txHash;
    }

    public String getMessage() {
        return message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setToList(ArrayList<String> toList) {
        this.toList = toList;
    }

    public void setAmountList(ArrayList<BigInteger> amountList) {
        this.amountList = amountList;
    }

    public void setFromList(ArrayList<String> fromList) {
        this.fromList = fromList;
    }

    public void setFee(BigInteger fee) {
        this.fee = fee;
    }

    public void setTxId(String txId) {
        this.txId = txId;
    }

    public void setTxHash(String tx_hash) {
        this.txHash = tx_hash;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    @JsonIgnore
    public static WalletPaymentResponse fromJson(String json) throws IOException {
        return new ObjectMapper().readValue(json, WalletPaymentResponse.class);
    }

    @JsonIgnore
    public String toJson() throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(this);
    }
}
