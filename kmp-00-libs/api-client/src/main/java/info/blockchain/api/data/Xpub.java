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
public class Xpub {

    @JsonProperty("m")
    private String m;

    @JsonProperty("path")
    private String path;

    public String getM() {
        return m;
    }

    public String getPath() {
        return path;
    }

    public void setM(String m) {
        this.m = m;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @JsonIgnore
    public static Xpub fromJson(String json) throws IOException {
        return new ObjectMapper().readValue(json, Xpub.class);
    }

    @JsonIgnore
    public String toJson() throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(this);
    }
}
