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
public class RawBlock {

    @JsonProperty("block_index")
    private long blockIndex;

    @JsonProperty("hash")
    private String hash;

    @JsonProperty("height")
    private long height;

    @JsonProperty("time")
    private long time;

    @JsonProperty("ver")
    private int ver;

    @JsonProperty("prev_block")
    private String prevBlock;

    @JsonProperty("mrkl_root")
    private String merkleRoot;

    @JsonProperty("bits")
    private long bits;

    @JsonProperty("fee")
    private long fee;

    @JsonProperty("nonce")
    private long nonce;

    @JsonProperty("n_tx")
    private int txCount;

    @JsonProperty("size")
    private int size;

    @JsonProperty("main_chain")
    private boolean mainChain;

    @JsonProperty("received_time")
    private long receivedTime;

    @JsonProperty("relayed_by")
    private String relayedBy;

    @JsonProperty("tx")
    private ArrayList<Transaction> tx;

    public long getBlockIndex() {
        return blockIndex;
    }

    public String getHash() {
        return hash;
    }

    public long getHeight() {
        return height;
    }

    public long getTime() {
        return time;
    }

    public int getVer() {
        return ver;
    }

    public String getPrevBlock() {
        return prevBlock;
    }

    public String getMerkleRoot() {
        return merkleRoot;
    }

    public long getBits() {
        return bits;
    }

    public long getFee() {
        return fee;
    }

    public long getNonce() {
        return nonce;
    }

    public int getTxCount() {
        return txCount;
    }

    public int getSize() {
        return size;
    }

    public boolean isMainChain() {
        return mainChain;
    }

    public long getReceivedTime() {
        return receivedTime;
    }

    public String getRelayedBy() {
        return relayedBy;
    }

    public ArrayList<Transaction> getTx() {
        return tx;
    }

    public void setBlockIndex(long block_index) {
        this.blockIndex = block_index;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public void setHeight(long height) {
        this.height = height;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public void setVer(int ver) {
        this.ver = ver;
    }

    public void setPrevBlock(String prev_block) {
        this.prevBlock = prev_block;
    }

    public void setMerkleRoot(String mrkl_root) {
        this.merkleRoot = mrkl_root;
    }

    public void setBits(long bits) {
        this.bits = bits;
    }

    public void setFee(long fee) {
        this.fee = fee;
    }

    public void setNonce(long nonce) {
        this.nonce = nonce;
    }

    public void setTxCount(int txCount) {
        this.txCount = txCount;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setMainChain(boolean main_chain) {
        this.mainChain = main_chain;
    }

    public void setReceivedTime(long received_time) {
        this.receivedTime = received_time;
    }

    public void setRelayedBy(String relayed_by) {
        this.relayedBy = relayed_by;
    }

    public void setTx(ArrayList<Transaction> tx) {
        this.tx = tx;
    }

    @JsonIgnore
    public static RawBlock fromJson(String json) throws IOException {
        return new ObjectMapper().readValue(json, RawBlock.class);
    }

    @JsonIgnore
    public String toJson() throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(this);
    }
}
