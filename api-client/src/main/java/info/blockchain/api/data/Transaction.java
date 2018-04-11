package info.blockchain.api.data;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonAutoDetect(fieldVisibility = Visibility.NONE,
        getterVisibility = Visibility.NONE,
        setterVisibility = Visibility.NONE,
        creatorVisibility = Visibility.NONE,
        isGetterVisibility = Visibility.NONE)
public class Transaction {

    @JsonProperty("hash")
    private String hash;

    @JsonProperty("ver")
    private int ver;

    @JsonProperty("lock_time")
    private long lockTime;

    @JsonProperty("block_height")
    private long blockHeight;

    @JsonProperty("relayed_by")
    private String relayedBy;

    @JsonProperty("result")
    private BigInteger result;

    @JsonProperty("fee")
    private BigInteger fee;

    @JsonProperty("size")
    private int size;

    @JsonProperty("time")
    private long time;

    @JsonProperty("tx_index")
    private long txIndex;

    @JsonProperty("vin_sz")
    private int vinSz;

    @JsonProperty("vout_sz")
    private int voutSz;

    @JsonProperty("double_spend")
    private boolean doubleSpend;

    @JsonProperty("inputs")
    private ArrayList<Input> inputs;

    @JsonProperty("out")
    private ArrayList<Output> out;

    public String getHash() {
        return hash;
    }

    public int getVer() {
        return ver;
    }

    public long getLockTime() {
        return lockTime;
    }

    public long getBlockHeight() {
        return blockHeight;
    }

    public String getRelayedBy() {
        return relayedBy;
    }

    public BigInteger getResult() {
        return result;
    }

    public BigInteger getFee() {
        return fee;
    }

    public int getSize() {
        return size;
    }

    public long getTime() {
        return time;
    }

    public long getTxIndex() {
        return txIndex;
    }

    public int getVinSz() {
        return vinSz;
    }

    public int getVoutSz() {
        return voutSz;
    }

    public boolean isDoubleSpend() {
        return doubleSpend;
    }

    public ArrayList<Input> getInputs() {
        return inputs;
    }

    public ArrayList<Output> getOut() {
        return out;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public void setVer(int ver) {
        this.ver = ver;
    }

    public void setLockTime(long lock_time) {
        this.lockTime = lock_time;
    }

    public void setBlockHeight(long block_height) {
        this.blockHeight = block_height;
    }

    public void setRelayedBy(String relayed_by) {
        this.relayedBy = relayed_by;
    }

    public void setResult(BigInteger result) {
        this.result = result;
    }

    public void setFee(BigInteger fee) {
        this.fee = fee;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public void setTxIndex(long tx_index) {
        this.txIndex = tx_index;
    }

    public void setVinSz(int vin_sz) {
        this.vinSz = vin_sz;
    }

    public void setVoutSz(int vout_sz) {
        this.voutSz = vout_sz;
    }

    public void setDoubleSpend(boolean double_spend) {
        this.doubleSpend = double_spend;
    }

    public void setInputs(ArrayList<Input> inputs) {
        this.inputs = inputs;
    }

    public void setOut(ArrayList<Output> out) {
        this.out = out;
    }

    @JsonIgnore
    public static Transaction fromJson(String json) throws IOException {
        return new ObjectMapper().readValue(json, Transaction.class);
    }

    @JsonIgnore
    public String toJson() throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(this);
    }
}
