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
public class CreateWalletResponse {

    @JsonProperty("guid")
    private String guid;

    @JsonProperty("address")
    private String address;

    @JsonProperty("label")
    private String label;

    public String getGuid() {
        return guid;
    }

    public String getAddress() {
        return address;
    }

    public String getLabel() {
        return label;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @JsonIgnore
    public static CreateWalletResponse fromJson(String json) throws IOException {
        return new ObjectMapper().readValue(json, CreateWalletResponse.class);
    }

    @JsonIgnore
    public String toJson() throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(this);
    }
}
