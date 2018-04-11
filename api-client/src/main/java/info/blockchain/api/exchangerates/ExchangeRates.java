package info.blockchain.api.exchangerates;

import info.blockchain.api.BaseApiClient;
import info.blockchain.api.data.Ticker;
import info.blockchain.api.data.TickerItem;
import retrofit2.Call;
import retrofit2.Retrofit;

import java.math.BigDecimal;
import java.util.TreeMap;

/**
 * Created by riaanvos on 26/01/2017.
 */
public class ExchangeRates extends BaseApiClient {

    private static final int BTC_TO_SATOSHI = 100_000_000;
    private static final BigDecimal ETH_TO_WEI = BigDecimal.valueOf(1_000_000_000_000_000_000L);
    private static final String ETH = "eth";

    /**
     * Default constructor for production environment. Uses default OkHttpClient with
     * blockchain.info certificate pinning for secure host verification.
     */
    public ExchangeRates() {
        super();
    }

    /**
     * Default constructor for production environment with an API key. Uses default OkHttpClient
     * with blockchain.info certificate pinning for secure host verification.
     */
    public ExchangeRates(String apiCode) {
        super(apiCode);
    }

    /**
     * Constructor that allows you to pass in retrofit instances. Ideal if you have existing
     * retrofit instances for other blockchain api purposes.
     *
     * @param apiCode Nullable - API code for platform Android, iOS, Web statistics
     */
    public ExchangeRates(Retrofit retrofitExplorerRoot, Retrofit retrofitApiRoot, String apiCode) {
        super(retrofitExplorerRoot, retrofitApiRoot, apiCode);
    }

    ///////////////////////////////////////////////////////////////////////////
    // BTC PRICES
    ///////////////////////////////////////////////////////////////////////////

    /**
     * Returns {@link Ticker} object which contains {@link info.blockchain.api.data.TickerItem}.
     * "15m" is the 15 minutes delayed market price, "last" is the most recent market price,
     * "symbol" is the currency symbol.
     *
     * @return {@link Call} object which can be executed synchronously or asynchronously to return a
     * response object
     * @deprecated use @link getBtcTickerMap instead.
     */
    @Deprecated
    public Call<Ticker> getBtcTicker() {
        return getApiInterface().getBtcTicker(apiCode);
    }

    /**
     * Returns {@link Ticker} object which contains {@link info.blockchain.api.data.TickerItem}.
     * "15m" is the 15 minutes delayed market price, "last" is the most recent BTC market price,
     * "symbol" is the currency symbol.
     *
     * @return {@link Call} object which can be executed synchronously or asynchronously to return a
     * response object
     * @deprecated See <a href=https://api.blockchain.info/price/specs>Blockchain Price API specs</a>
     */
    @Deprecated
    public Call<TreeMap<String, TickerItem>> getBtcTickerMap() {
        return getApiInterface().getBtcTickerMap(apiCode);
    }

    /**
     * Convert x value in the provided currency to BTC.
     *
     * @param currency a String in 3 letters to represent a currency
     * @param value    a double value to represent the currency value to convert from
     * @return {@link Call} object which can be executed synchronously or asynchronously to return a
     * response object
     * @deprecated See <a href=https://api.blockchain.info/price/specs>Blockchain Price API specs</a>
     */
    @Deprecated
    public Call<Double> toBTC(String currency, double value) {
        return getApiInterface().toBTC(currency, value, apiCode);
    }

    /**
     * Convert x value in the provided currency to BTC. Note that the unit is BTC, not some smaller
     * denomination.
     *
     * @param currency a String in 3 letters to represent a currency
     * @param value    a double value to represent the currency value to convert
     * @return {@link Call} object which can be executed synchronously or asynchronously to return a
     * response object
     * @deprecated See <a href=https://api.blockchain.info/price/specs>Blockchain Price API specs</a>
     */
    @Deprecated
    public Call<Double> fromBTC(String currency, double value) {
        return getApiInterface().fromBTC(currency, (long) value * BTC_TO_SATOSHI, apiCode);
    }

    ///////////////////////////////////////////////////////////////////////////
    // ETH PRICES
    ///////////////////////////////////////////////////////////////////////////

    /**
     * Returns {@link Ticker} object which contains {@link info.blockchain.api.data.TickerItem}.
     * "15m" is the 15 minutes delayed market price, "last" is the most recent ETH market price,
     * "symbol" is the currency symbol.
     *
     * @return {@link Call} object which can be executed synchronously or asynchronously to return a
     * response object
     * @deprecated See <a href=https://api.blockchain.info/price/specs>Blockchain Price API specs</a>
     */
    @Deprecated
    public Call<TreeMap<String, TickerItem>> getEthTickerMap() {
        return getApiInterface().getEthTickerMap(ETH, apiCode);
    }

}
