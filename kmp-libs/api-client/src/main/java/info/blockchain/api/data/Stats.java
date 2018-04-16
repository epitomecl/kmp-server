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
public class Stats {

    @JsonProperty("market_price_usd")
    private float marketPriceUsd;

    @JsonProperty("hash_rate")
    private float hashRate;

    @JsonProperty("total_fees_btc")
    private long totalFeesBtc;

    @JsonProperty("n_btc_mined")
    private long btcMinedCount;

    @JsonProperty("n_tx")
    private long txCount;

    @JsonProperty("n_blocks_mined")
    private int blocksMinedCount;

    @JsonProperty("minutes_between_blocks")
    private double minutesBetweenBlocks;

    @JsonProperty("totalbc")
    private long totalbc;

    @JsonProperty("n_blocks_total")
    private int blocksTotal;

    @JsonProperty("estimated_transaction_volume_usd")
    private float estimatedTransactionVolumeUsd;

    @JsonProperty("blocks_size")
    private long blocksSize;

    @JsonProperty("miners_revenue_usd")
    private double minersRevenueUsd;

    @JsonProperty("nextretarget")
    private int nextRetarget;

    @JsonProperty("difficulty")
    private long difficulty;

    @JsonProperty("estimated_btc_sent")
    private long estimatedBtcSent;

    @JsonProperty("miners_revenue_btc")
    private int minersRevenueBtc;

    @JsonProperty("total_btc_sent")
    private long totalBtcSent;

    @JsonProperty("trade_volume_btc")
    private double tradeVolumeBtc;

    @JsonProperty("trade_volume_usd")
    private double tradeVolumeUsd;

    @JsonProperty("timestamp")
    private long timestamp;

    public float getMarketPriceUsd() {
        return marketPriceUsd;
    }

    public float getHashRate() {
        return hashRate;
    }

    public long getTotalFeesBtc() {
        return totalFeesBtc;
    }

    public double getMinutesBetweenBlocks() {
        return minutesBetweenBlocks;
    }

    public long getTotalbc() {
        return totalbc;
    }

    public float getEstimatedTransactionVolumeUsd() {
        return estimatedTransactionVolumeUsd;
    }

    public long getBlocksSize() {
        return blocksSize;
    }

    public double getMinersRevenueUsd() {
        return minersRevenueUsd;
    }

    public int getNextRetarget() {
        return nextRetarget;
    }

    public long getDifficulty() {
        return difficulty;
    }

    public long getEstimatedBtcSent() {
        return estimatedBtcSent;
    }

    public int getMinersRevenueBtc() {
        return minersRevenueBtc;
    }

    public long getTotalBtcSent() {
        return totalBtcSent;
    }

    public double getTradeVolumeBtc() {
        return tradeVolumeBtc;
    }

    public double getTradeVolumeUsd() {
        return tradeVolumeUsd;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setMarketPriceUsd(float market_price_usd) {
        this.marketPriceUsd = market_price_usd;
    }

    public void setHashRate(float hash_rate) {
        this.hashRate = hash_rate;
    }

    public void setTotalFeesBtc(long total_fees_btc) {
        this.totalFeesBtc = total_fees_btc;
    }

    public void setMinutesBetweenBlocks(double minutes_between_blocks) {
        this.minutesBetweenBlocks = minutes_between_blocks;
    }

    public void setTotalbc(long totalbc) {
        this.totalbc = totalbc;
    }

    public void setNBlocksTotal(int n_blocks_total) {
        this.blocksTotal = n_blocks_total;
    }

    public void setEstimatedTransactionVolumeUsd(float estimated_transaction_volume_usd) {
        this.estimatedTransactionVolumeUsd = estimated_transaction_volume_usd;
    }

    public void setBlocksSize(long blocks_size) {
        this.blocksSize = blocks_size;
    }

    public void setMinersRevenueUsd(double miners_revenue_usd) {
        this.minersRevenueUsd = miners_revenue_usd;
    }

    public void setNextRetarget(int nextRetarget) {
        this.nextRetarget = nextRetarget;
    }

    public void setDifficulty(long difficulty) {
        this.difficulty = difficulty;
    }

    public void setEstimatedBtcSent(long estimated_btc_sent) {
        this.estimatedBtcSent = estimated_btc_sent;
    }

    public void setMinersRevenueBtc(int miners_revenue_btc) {
        this.minersRevenueBtc = miners_revenue_btc;
    }

    public void setTotalBtcSent(long total_btc_sent) {
        this.totalBtcSent = total_btc_sent;
    }

    public void setTradeVolumeBtc(double trade_volume_btc) {
        this.tradeVolumeBtc = trade_volume_btc;
    }

    public void setTradeVolumeUsd(double trade_volume_usd) {
        this.tradeVolumeUsd = trade_volume_usd;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public long getBtcMinedCount() {
        return btcMinedCount;
    }

    public void setBtcMinedCount(long btcMinedCount) {
        this.btcMinedCount = btcMinedCount;
    }

    public long getTxCount() {
        return txCount;
    }

    public void setTxCount(long txCount) {
        this.txCount = txCount;
    }

    public int getBlocksMinedCount() {
        return blocksMinedCount;
    }

    public void setBlocksMinedCount(int blocksMinedCount) {
        this.blocksMinedCount = blocksMinedCount;
    }

    public int getBlocksTotal() {
        return blocksTotal;
    }

    public void setBlocksTotal(int blocksTotal) {
        this.blocksTotal = blocksTotal;
    }

    @JsonIgnore
    public static Stats fromJson(String json) throws IOException {
        return new ObjectMapper().readValue(json, Stats.class);
    }

    @JsonIgnore
    public String toJson() throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(this);
    }
}
