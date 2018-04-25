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
public class Input {

    @JsonProperty("sequence")
    private long sequence;

    @JsonProperty("prev_out")
    private Output prevOut;

    @JsonProperty("script")
    private String script;

    public long getSequence() {
        return sequence;
    }

    public Output getPrevOut() {
        return prevOut;
    }

    public String getScript() {
        return script;
    }

    public void setSequence(long sequence) {
        this.sequence = sequence;
    }

    public void setPrevOut(Output prev_out) {
        this.prevOut = prev_out;
    }

    public void setScript(String script) {
        this.script = script;
    }

    @JsonIgnore
    public static Input fromJson(String json) throws IOException {
        return new ObjectMapper().readValue(json, Input.class);
    }

    @JsonIgnore
    public String toJson() throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(this);
    }
}
