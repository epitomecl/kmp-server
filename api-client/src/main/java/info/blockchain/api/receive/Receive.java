package info.blockchain.api.receive;

import info.blockchain.api.BaseApiClient;
import info.blockchain.api.data.CallbackLog;
import info.blockchain.api.data.ReceivePayment;
import info.blockchain.api.data.XpubGap;
import retrofit2.Call;
import retrofit2.Retrofit;

import java.io.IOException;
import java.util.List;

/**
 * Created by riaanvos on 26/01/2017.
 */
public class Receive extends BaseApiClient {

    /**
     * Default constructor for production environment.
     * Uses default OkHttpClient with blockchain.info certificate pinning for secure host verification.
     */
    public Receive() {
        super();
    }

    /**
     * Default constructor for production environment with an API key.
     * Uses default OkHttpClient with blockchain.info certificate pinning for secure host verification.
     */
    public Receive(String apiCode) {
        super(apiCode);
    }

    /**
     * Constructor that allows you to pass in retrofit instances. Ideal if you have existing retrofit
     * instances for other blockchain api purposes.
     *
     * @param retrofitApiRoot
     * @param apiCode         Nullable - API code for platform Android, iOS, Web statistics
     * @throws IOException
     */
    public Receive(Retrofit retrofitApiRoot, String apiCode) {
        super(null, retrofitApiRoot, apiCode);
    }

    /**
     * Calls the receive-payments-api v2 and returns an address for the payment.
     * See <a href="https://blockchain.info/api/api_receive">Receive Payments API V2</a>
     *
     * @param xpub        Destination address where the payment should be sent
     * @param callbackUrl Callback URI that will be called upon payment
     * @param key         Blockchain.info API code for the receive-payments v2 API (different from normal API key)
     * @return {@link Call} object which can be executed synchronously or asynchronously to return a
     * response object
     */
    public Call<ReceivePayment> receive(String xpub, String callbackUrl, String key) {
        return getApiInterface().receive(xpub, callbackUrl, null, null, key);
    }

    /**
     * Calls the receive-payments-api v2 and returns an address for the payment.
     * See <a href="https://blockchain.info/api/api_receive">Receive Payments API V2</a>
     *
     * @param xpub        Destination address where the payment should be sent
     * @param callbackUrl Callback URI that will be called upon payment
     * @param key         Blockchain.info API code for the receive-payments v2 API (different from normal API key)
     * @param gapLimit    The number of unused addresses to invalidate the xpub subscription
     * @return {@link Call} object which can be executed synchronously or asynchronously to return a
     * response object
     */
    public Call<ReceivePayment> receiveWithGapLimit(String xpub, String callbackUrl, String key, Integer gapLimit) {
        return getApiInterface().receive(xpub, callbackUrl, gapLimit, null, key);
    }

    /**
     * Calls the receive-payments-api v2 and returns an address for the payment.
     * See <a href="https://blockchain.info/api/api_receive">Receive Payments API V2</a>
     *
     * @param xpub        Destination address where the payment should be sent
     * @param callbackUrl Callback URI that will be called upon payment
     * @param key         Blockchain.info API code for the receive-payments v2 API (different from normal API key)
     * @param index       The child index of the xpub key
     * @return {@link Call} object which can be executed synchronously or asynchronously to return a
     * response object
     */
    public Call<ReceivePayment> receiveWithIndex(String xpub, String callbackUrl, String key, Integer index) {
        return getApiInterface().receive(xpub, callbackUrl, null, index, key);
    }

    /**
     * Checks the current xpub gap size.
     *
     * @param xpub Destination address where the payment should be sent
     * @param key  Blockchain.info API code for the receive-payments v2 API (different from normal API key)
     * @return {@link Call} object which can be executed synchronously or asynchronously to return a
     * response object
     */
    public Call<XpubGap> checkGap(String xpub, String key) {
        return getApiInterface().checkGap(xpub, key);
    }

    /**
     * Checks the callback log for a callback URL
     *
     * @param callbackUrl Callback URI that will be called upon payment
     * @param key         Blockchain.info API code for the receive-payments v2 API (different from normal API key)
     * @return {@link Call} object which can be executed synchronously or asynchronously to return a
     * response object
     */
    public Call<List<CallbackLog>> callbackLog(String callbackUrl, String key) {
        return getApiInterface().callbackLog(callbackUrl, key);
    }

}
