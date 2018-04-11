package info.blockchain.api.pushtx;

import info.blockchain.api.BaseApiClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;

import java.io.IOException;

/**
 * Created by riaanvos on 26/01/2017.
 */
public class PushTx extends BaseApiClient {

    /**
     * Default constructor for production environment.
     * Uses default OkHttpClient with blockchain.info certificate pinning for secure host verification.
     */
    public PushTx() {
        super();
    }

    /**
     * Default constructor for production environment with an API key.
     * Uses default OkHttpClient with blockchain.info certificate pinning for secure host verification.
     */
    public PushTx(String apiCode) {
        super(apiCode);
    }

    /**
     * Constructor that allows you to pass in retrofit instances. Ideal if you have existing retrofit
     * instances for other blockchain api purposes.
     *
     * @param retrofitExplorerRoot
     * @param apiCode              Nullable - API code for platform Android, iOS, Web statistics
     * @throws IOException
     */
    public PushTx(Retrofit retrofitExplorerRoot, String apiCode) {
        super(retrofitExplorerRoot, null, apiCode);
    }

    // FIXME - added by jdlee manually
    public PushTx(Retrofit retrofitExplorerRoot, Retrofit retrofitApiRoot, String apiCode) {
        super(retrofitExplorerRoot, retrofitApiRoot, apiCode);
    }

    /**
     * Push a Bitcoin (BTC) transaction to network.
     *
     * @param hash Transaction hash
     * @return {@link Call} object which can be executed synchronously or asynchronously to return a
     * response object
     * @deprecated This is hardcoded to BTC, use {@link #pushTx(String, String)} instead. Also
     * uses the old Explorer endpoints
     */
    @Deprecated
    public Call<ResponseBody> pushTx(String hash) {
        return getExplorerInterface().pushTx(hash, apiCode);
    }

    /**
     * Push a Bitcoin or Bitcoin Cash transaction to network.
     *
     * @param coin The coin type, either BTC or BCH
     * @param hash Transaction hash
     * @return {@link Call} object which can be executed synchronously or asynchronously to return a
     * response object
     */
    public Call<ResponseBody> pushTx(String coin, String hash) {
        return getApiInterface().pushTx(coin, hash, apiCode);
    }

    /**
     * Push transaction to network with lock secret.
     *
     * @param hash       Transaction hash
     * @param lockSecret Secret used server side
     * @return {@link Call} object which can be executed synchronously or asynchronously to return a
     * response object
     */
    public Call<ResponseBody> pushTxWithSecret(String hash, String lockSecret) {
        return getExplorerInterface().pushTxWithSecret(hash, lockSecret, apiCode);
    }
}
