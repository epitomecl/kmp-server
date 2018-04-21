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
public class WalletBalance {

    @JsonProperty("balance")
    private BigInteger balance;

    public BigInteger getBalance() {
        return balance;
    }

    public void setBalance(BigInteger balance) {
        this.balance = balance;
    }

    @JsonIgnore
    public static WalletBalance fromJson(String json) throws IOException {
        return new ObjectMapper().readValue(json, WalletBalance.class);
    }

    @JsonIgnore
    public String toJson() throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(this);
    }
}
