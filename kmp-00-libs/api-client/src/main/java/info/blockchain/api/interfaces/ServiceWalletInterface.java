package info.blockchain.api.interfaces;

import info.blockchain.api.data.*;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by riaanvos on 26/01/2017.
 */
public interface ServiceWalletInterface {

    @GET("api/v2/create")
    Call<CreateWalletResponse> createWallet(
            @Query("password") String password,
            @Query("api_code") String api_code,
            @Query("priv") String priv,
            @Query("label") String label,
            @Query("email") String email);

    @GET("merchant/{guid}/balance")
    Call<WalletBalance> getBalance(
            @Path("guid") String guid,
            @Query("password") String password,
            @Query("api_code") String apiCode);

    @GET("merchant/{guid}/{method}")
    Call<WalletPaymentResponse> send(
            @Path("guid") String guid,
            @Path("method") String method,
            @Query("password") String password,
            @Query("second_password") String secondPassword,
            @Query("api_code") String apiCode,
            @Query("to") String to,
            @Query("amount") String amount,
            @Query("recipients") String recipients,
            @Query("from") String from,
            @Query("fee") long fee,
            @Query("note") String note);

    @GET("merchant/{guid}/list")
    Call<WalletAddressList> listAddresses(
            @Path("guid") String guid,
            @Query("password") String password,
            @Query("api_code") String apiCode);

    @GET("merchant/{guid}/address_balance")
    Call<WalletAddress> getAddress(
            @Path("guid") String guid,
            @Query("password") String password,
            @Query("api_code") String apiCode,
            @Query("address") String address);

    @GET("merchant/{guid}/new_address")
    Call<WalletAddress> newAddress(
            @Path("guid") String guid,
            @Query("password") String password,
            @Query("second_password") String secondPassword,
            @Query("api_code") String apiCode,
            @Query("label") String label);

    @GET("merchant/{guid}/archive_address")
    Call<WalletAddressArchiveResponse> archiveAddress(
            @Path("guid") String guid,
            @Query("password") String password,
            @Query("api_code") String apiCode,
            @Query("address") String address);

    @GET("merchant/{guid}/unarchive_address")
    Call<WalletAddressUnarchiveResponse> unarchiveAddress(
            @Path("guid") String guid,
            @Query("password") String password,
            @Query("api_code") String apiCode,
            @Query("address") String address);
}
