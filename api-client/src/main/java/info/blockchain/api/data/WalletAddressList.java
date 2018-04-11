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
public class WalletAddressList {

    @JsonProperty("addresses")
    private ArrayList<WalletAddress> addressList;

    public ArrayList<WalletAddress> getAddressList() {
        return addressList;
    }

    public void setAddressList(ArrayList<WalletAddress> addressList) {
        this.addressList = addressList;
    }

    @JsonIgnore
    public static WalletAddressList fromJson(String json) throws IOException {
        return new ObjectMapper().readValue(json, WalletAddressList.class);
    }

    @JsonIgnore
    public String toJson() throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(this);
    }
}
