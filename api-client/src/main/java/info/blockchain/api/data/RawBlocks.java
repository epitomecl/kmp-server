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
public class RawBlocks {

    @JsonProperty("blocks")
    private ArrayList<RawBlock> blocks;

    public ArrayList<RawBlock> getBlocks() {
        return blocks;
    }

    public void setBlocks(ArrayList<RawBlock> blocks) {
        this.blocks = blocks;
    }

    @JsonIgnore
    public static RawBlocks fromJson(String json) throws IOException {
        return new ObjectMapper().readValue(json, RawBlocks.class);
    }

    @JsonIgnore
    public String toJson() throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(this);
    }
}
