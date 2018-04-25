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
public class Block {

    @JsonProperty("hash")
    private String hash;

    @JsonProperty("time")
    private long time;

    @JsonProperty("block_index")
    private long blockIndex;

    @JsonProperty("height")
    private long height;

    @JsonProperty("txIndexes")
    private ArrayList<Long> txIndexes;

    public String getHash() {
        return hash;
    }

    public long getTime() {
        return time;
    }

    public long getBlockIndex() {
        return blockIndex;
    }

    public long getHeight() {
        return height;
    }

    public ArrayList<Long> getTxIndexes() {
        return txIndexes;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public void setBlockIndex(long block_index) {
        this.blockIndex = block_index;
    }

    public void setHeight(long height) {
        this.height = height;
    }

    public void setTxIndexes(ArrayList<Long> txIndexes) {
        this.txIndexes = txIndexes;
    }

    @JsonIgnore
    public static Block fromJson(String json) throws IOException {
        return new ObjectMapper().readValue(json, Block.class);
    }

    @JsonIgnore
    public String toJson() throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(this);
    }
}
