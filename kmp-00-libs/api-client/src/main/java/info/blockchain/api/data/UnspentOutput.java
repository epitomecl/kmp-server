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
public class UnspentOutput {

    @JsonProperty("tx_age")
    private long txAge;

    @JsonProperty("tx_hash")
    private String txHash;

    @JsonProperty("tx_hash_big_endian")
    private String txHashBigEndian;

    @JsonProperty("tx_index")
    private long txIndex;

    @JsonProperty("tx_output_n")
    private int txOutputCount;

    @JsonProperty("script")
    private String script;

    @JsonProperty("value")
    private BigInteger value;

    @JsonProperty("value_hex")
    private String valueHex;

    @JsonProperty("confirmations")
    private long confirmations;

    @JsonProperty("xpub")
    private Xpub xpub;

    @JsonProperty("replayable")
    private boolean replayable = true;

    public long getTxAge() {
        return txAge;
    }

    public String getTxHash() {
        return txHash;
    }

    public String getTxHashBigEndian() {
        return txHashBigEndian;
    }

    public long getTxIndex() {
        return txIndex;
    }

    public int getTxOutputCount() {
        return txOutputCount;
    }

    public String getScript() {
        return script;
    }

    public BigInteger getValue() {
        return value;
    }

    public String getValueHex() {
        return valueHex;
    }

    public long getConfirmations() {
        return confirmations;
    }

    public Xpub getXpub() {
        return xpub;
    }

    public void setTxAge(long tx_age) {
        this.txAge = tx_age;
    }

    public void setTxHash(String tx_hash) {
        this.txHash = tx_hash;
    }

    public void setTxHashBigEndian(String tx_hash_big_endian) {
        this.txHashBigEndian = tx_hash_big_endian;
    }

    public void setTxIndex(long tx_index) {
        this.txIndex = tx_index;
    }

    public void setTxOutputCount(int tx_output_n) {
        this.txOutputCount = tx_output_n;
    }

    public void setScript(String script) {
        this.script = script;
    }

    public void setValue(BigInteger value) {
        this.value = value;
    }

    public void setValueHex(String value_hex) {
        this.valueHex = value_hex;
    }

    public void setConfirmations(long confirmations) {
        this.confirmations = confirmations;
    }

    public void setXpub(Xpub xpub) {
        this.xpub = xpub;
    }

    public boolean isReplayable() {
        return replayable;
    }

    public void setReplayable(boolean replayable) {
        this.replayable = replayable;
    }

    @JsonIgnore
    public static UnspentOutput fromJson(String json) throws IOException {
        return new ObjectMapper().readValue(json, UnspentOutput.class);
    }

    @JsonIgnore
    public String toJson() throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(this);
    }
}
