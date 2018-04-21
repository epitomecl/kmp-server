package info.blockchain.api.interfaces;

import info.blockchain.api.data.*;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

/**
 * Created by riaanvos on 24/01/2017.
 */
public interface ApiInterface {

    @GET("charts/{chart-type}")
    Call<Chart> getChart(@Path("chart-type") String chartType,
                         @Query("timespan") String timespan,
                         @Query("rollingAverage") String rollingAverage,
                         @Query("format") String format,
                         @Query("api_code") String apiCode);

    @GET("stats")
    Call<Stats> getStats(@Query("api_code") String apiCode);

    @GET("pools")
    Call<HashMap<String, Integer>> getPools(
            @Query("timespan") String timespan,
            @Query("api_code") String apiCode);

    @GET("v2/receive")
    Call<ReceivePayment> receive(
            @Query("xpub") String xpub,
            @Query("callback") String callbackUrl,
            @Query("gap_limit") Integer gapLimit,
            @Query("index") Integer index,
            @Query("key") String key);

    @GET("v2/receive/checkgap")
    Call<XpubGap> checkGap(
            @Query("xpub") String xpub,
            @Query("key") String key);

    @GET("v2/receive/callback_log")
    Call<List<CallbackLog>> callbackLog(
            @Query("callback") String callbackUrl,
            @Query("key") String key);

    @GET("ticker")
    Call<Ticker> getBtcTicker(
            @Query("api_code") String apiCode);

    @GET("ticker")
    Call<TreeMap<String, TickerItem>> getBtcTickerMap(
            @Query("api_code") String apiCode);

    @GET("ticker")
    Call<TreeMap<String, TickerItem>> getEthTickerMap(
            @Query("base") String base,
            @Query("api_code") String apiCode);

    @GET("tobtc")
    Call<Double> toBTC(
            @Query("currency") String currency,
            @Query("value") Double value,
            @Query("api_code") String apiCode);

    @GET("frombtc")
    Call<Double> fromBTC(
            @Query("currency") String currency,
            @Query("value") Long value,
            @Query("api_code") String apiCode);

    @GET("toeth")
    Call<Double> toETH(
            @Query("currency") String currency,
            @Query("value") Double value,
            @Query("api_code") String apiCode);

    @GET("frometh")
    Call<Double> fromETH(
            @Query("currency") String currency,
            @Query("value") String value,
            @Query("api_code") String apiCode);

    @FormUrlEncoded
    @POST("{coin}/multiaddr")
    Call<MultiAddress> getMultiAddress(
            @Path("coin") String coin,
            @Field("active") String active,
            @Field("n") Integer limit,
            @Field("offset") Integer offset,
            @Field("filter") Integer filter,
            @Field("onlyShow") String context,
            @Field("api_code") String apiCode);

    @GET("{coin}/unspent")
    Call<UnspentOutputs> getUnspent(
            @Path("coin") String coin,
            @Query("active") String active,
            @Query("confirmations") Integer confirmations,
            @Query("limit") Integer limit,
            @Query("api_code") String apiCode);

    @FormUrlEncoded
    @POST("{coin}/balance")
    Call<HashMap<String, Balance>> getBalance(
            @Path("coin") String coin,
            @Field("active") String active,
            @Field("filter") Integer filter,
            @Field("api_code") String apiCode);

    @FormUrlEncoded
    @POST("{coin}/pushtx")
    Call<ResponseBody> pushTx(
            @Path("coin") String coin,
            @Field("tx") String hash,
            @Field("api_code") String apiCode);

}
