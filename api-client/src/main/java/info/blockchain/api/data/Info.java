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
public class Info {

    @JsonProperty("nconnected")
    private int connectedCount;

    @JsonProperty("conversion")
    private double conversion;

    @JsonProperty("latest_block")
    private RawBlock latestBlock;

    public int getConnectedCount() {
        return connectedCount;
    }

    public double getConversion() {
        return conversion;
    }

    public RawBlock getLatestBlock() {
        return latestBlock;
    }

    public void setConnectedCount(int connectedCount) {
        this.connectedCount = connectedCount;
    }

    public void setConversion(double conversion) {
        this.conversion = conversion;
    }

    public void setLatestBlock(RawBlock latest_block) {
        this.latestBlock = latest_block;
    }

    @JsonIgnore
    public static Info fromJson(String json) throws IOException {
        return new ObjectMapper().readValue(json, Info.class);
    }

    @JsonIgnore
    public String toJson() throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(this);
    }
}