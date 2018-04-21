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
public class ReceivePayment {

    @JsonProperty("address")
    private String address;

    @JsonProperty("index")
    private int index;

    @JsonProperty("callback")
    private String callback;

    public String getAddress() {
        return address;
    }

    public int getIndex() {
        return index;
    }

    public String getCallback() {
        return callback;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public void setCallback(String callback) {
        this.callback = callback;
    }

    @JsonIgnore
    public static ReceivePayment fromJson(String json) throws IOException {
        return new ObjectMapper().readValue(json, ReceivePayment.class);
    }

    @JsonIgnore
    public String toJson() throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(this);
    }
}
