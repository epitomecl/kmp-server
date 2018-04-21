package info.blockchain.api.data;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.math.BigInteger;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonAutoDetect(fieldVisibility = Visibility.NONE,
        getterVisibility = Visibility.NONE,
        setterVisibility = Visibility.NONE,
        creatorVisibility = Visibility.NONE,
        isGetterVisibility = Visibility.NONE)
public class Output {

    @JsonProperty("spent")
    private boolean spent;

    @JsonProperty("tx_index")
    private long txIndex;

    @JsonProperty("type")
    private int type;

    @JsonProperty("addr")
    private String addr;

    @JsonProperty("value")
    private BigInteger value;

    @JsonProperty("n")
    private int count;

    @JsonProperty("script")
    private String script;

    @JsonProperty("xpub")
    private Xpub xpub;

    public boolean isSpent() {
        return spent;
    }

    public long getTxIndex() {
        return txIndex;
    }

    public int getType() {
        return type;
    }

    public String getAddr() {
        return addr;
    }

    public BigInteger getValue() {
        return value;
    }

    public int getCount() {
        return count;
    }

    public String getScript() {
        return script;
    }

    public Xpub getXpub() {
        return xpub;
    }

    public void setSpent(boolean spent) {
        this.spent = spent;
    }

    public void setTxIndex(long tx_index) {
        this.txIndex = tx_index;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public void setValue(BigInteger value) {
        this.value = value;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setScript(String script) {
        this.script = script;
    }

    public void setXpub(Xpub xpub) {
        this.xpub = xpub;
    }

    @JsonIgnore
    public static Output fromJson(String json) throws IOException {
        return new ObjectMapper().readValue(json, Output.class);
    }

    @JsonIgnore
    public String toJson() throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(this);
    }
}
