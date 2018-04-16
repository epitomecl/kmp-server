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
public class UnspentOutputs {

    @JsonProperty("notice")
    private String notice;

    @JsonProperty("unspent_outputs")
    private ArrayList<UnspentOutput> unspentOutputs;

    public String getNotice() {
        return notice;
    }

    public ArrayList<UnspentOutput> getUnspentOutputs() {
        return unspentOutputs;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

    public void setUnspentOutputs(
            ArrayList<UnspentOutput> unspent_outputs) {
        this.unspentOutputs = unspent_outputs;
    }

    @JsonIgnore
    public static UnspentOutputs fromJson(String json) throws IOException {
        return new ObjectMapper().readValue(json, UnspentOutputs.class);
    }

    @JsonIgnore
    public String toJson() throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(this);
    }
}
