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
public class WalletAddressConsolidated {

    @JsonProperty("consolidated")
    private ArrayList<String> consolidated;

    public ArrayList<String> getConsolidated() {
        return consolidated;
    }

    public void setConsolidated(ArrayList<String> consolidated) {
        this.consolidated = consolidated;
    }

    @JsonIgnore
    public static WalletAddressConsolidated fromJson(String json) throws IOException {
        return new ObjectMapper().readValue(json, WalletAddressConsolidated.class);
    }

    @JsonIgnore
    public String toJson() throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(this);
    }
}
