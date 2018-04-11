package info.blockchain.api.data;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonAutoDetect(fieldVisibility = Visibility.NONE,
        getterVisibility = Visibility.NONE,
        setterVisibility = Visibility.NONE,
        creatorVisibility = Visibility.NONE,
        isGetterVisibility = Visibility.NONE)
public class MultiAddress {

    @JsonProperty("wallet")
    private MultiAddressBalance multiAddressBalance;

    @JsonProperty("addresses")
    private ArrayList<AddressSummary> addresses;

    @JsonProperty("txs")
    private ArrayList<Transaction> txs;

    @JsonProperty("info")
    private Info info;

    public MultiAddressBalance getMultiAddressBalance() {
        return multiAddressBalance;
    }

    public ArrayList<AddressSummary> getAddresses() {
        return addresses;
    }

    public ArrayList<Transaction> getTxs() {
        return txs;
    }

    public Info getInfo() {
        return info;
    }

    public void setMultiAddressBalance(MultiAddressBalance multiAddressBalance) {
        this.multiAddressBalance = multiAddressBalance;
    }

    public void setAddresses(ArrayList<AddressSummary> addresses) {
        this.addresses = addresses;
    }

    public void setTxs(ArrayList<Transaction> txs) {
        this.txs = txs;
    }

    public void setInfo(Info info) {
        this.info = info;
    }

    @JsonIgnore
    public static MultiAddress fromJson(String json) throws IOException {
        return new ObjectMapper().readValue(json, MultiAddress.class);
    }

    @JsonIgnore
    public String toJson() throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(this);
    }
}
