package info.blockchain.api.interfaces;

import info.blockchain.api.data.*;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.HashMap;
import java.util.List;

/**
 * Created by riaanvos on 21/01/2017.
 */
public interface ExplorerInterface {

    @GET("address/{addr}")
    Call<AddressFull> getAddress(
            @Path("addr") String address,
            @Query("limit") Integer limit,
            @Query("offset") Integer offset,
            @Query("filter") Integer filter,
            @Query("format") String format,
            @Query("api_code") String apiCode);

    @Deprecated
    @FormUrlEncoded
    @POST("balance")
    Call<HashMap<String, Balance>> getBalance(
            @Field("active") String active,
            @Field("filter") Integer filter,
            @Field("api_code") String apiCode);

    @Deprecated
    @FormUrlEncoded
    @POST("multiaddr")
    Call<MultiAddress> getMultiAddress(
            @Field("active") String active,
            @Field("n") Integer limit,
            @Field("offset") Integer offset,
            @Field("filter") Integer filter,
            @Field("onlyShow") String context,
            @Field("api_code") String apiCode);

    @Deprecated
    @FormUrlEncoded
    @POST("pushtx")
    Call<ResponseBody> pushTx(
            @Field("tx") String hash,
            @Field("api_code") String apiCode);

    @FormUrlEncoded
    @POST("pushtx")
    Call<ResponseBody> pushTxWithSecret(
            @Field("tx") String hash,
            @Field("lock_secret") String lockSecret,
            @Field("api_code") String apiCode);

    @GET("tx/{hash}")
    Call<Transaction> getTransaction(
            @Path("hash") String hash,
            @Query("format") String format,
            @Query("api_code") String apiCode);

    @Deprecated
    @GET("unspent")
    Call<UnspentOutputs> getUnspent(
            @Query("active") String active,
            @Query("confirmations") Integer confirmations,
            @Query("limit") Integer limit,
            @Query("api_code") String apiCode);

    @GET("rawblock/{block_hash}")
    Call<RawBlock> getBlockDetails(
            @Path("block_hash") String blockHash,
            @Query("api_code") String apiCode);

    @GET("rawblock/{block_index}")
    Call<RawBlock> getBlockDetails(
            @Path("block_index") long blockIndex,
            @Query("api_code") String apiCode);

    @GET("rawtx/{tx_index}")
    Call<Transaction> getTransactionDetails(
            @Path("tx_index") long txIndex,
            @Query("api_code") String apiCode);

    @GET("rawtx/{tx_hash}")
    Call<Transaction> getTransactionDetails(
            @Path("tx_hash") String txHash,
            @Query("api_code") String apiCode);

    @GET("block-height/{block-height}")
    Call<RawBlocks> getBlocksAtHeight(
            @Path("block-height") long blockHeight,
            @Query("format") String format,
            @Query("api_code") String apiCode);

    @GET("latestblock")
    Call<Block> getLatestBlock(@Query("api_code") String apiCode);

    @GET("unconfirmed-transactions")
    Call<Transactions> getUnconfirmedTransactions(
            @Query("format") String format,
            @Query("api_code") String apiCode);

    @GET("blocks/{time_in_milliseconds}")
    Call<RawBlocks> getBlocksAtTime(
            @Path("time_in_milliseconds") long time,
            @Query("format") String format,
            @Query("api_code") String apiCode);

    @GET("blocks/{pool_name}")
    Call<RawBlocks> getBlocksFromPool(
            @Path("pool_name") String pool,
            @Query("format") String format,
            @Query("api_code") String apiCode);

    @GET("v2/export-history")
    Call<List<ExportHistory>> getExportHistory(
            @Query("active") String active,
            @Query("start") String start,
            @Query("end") String end,
            @Query("currency") String currency,
            @Query("api_code") String apiCode);
}
