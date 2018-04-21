package info.blockchain.api.statistics;

import info.blockchain.api.BaseApiClient;
import info.blockchain.api.data.Chart;
import info.blockchain.api.data.Stats;
import retrofit2.Call;
import retrofit2.Retrofit;

import java.io.IOException;
import java.util.HashMap;

/**
 * Created by riaanvos on 26/01/2017.
 */
public class Statistics extends BaseApiClient {

    /**
     * Default constructor for production environment.
     * Uses default OkHttpClient with blockchain.info certificate pinning for secure host verification.
     */
    public Statistics() {
        super();
    }


    /**
     * Default constructor for production environment with an API key.
     * Uses default OkHttpClient with blockchain.info certificate pinning for secure host verification.
     */
    public Statistics(String apiCode) {
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
    public Statistics(Retrofit retrofitApiRoot, String apiCode) {
        super(null, retrofitApiRoot, apiCode);
    }

    /**
     * This method can be used to get and manipulate data behind all Blockchain.info's charts.
     *
     * @param type           of chart (Example: transactions-per-second, total-bitcoins)
     * @param timeSpan       (Example: 5weeks)
     * @param rollingAverage (Example: 8hours)
     * @return {@link Call} object which can be executed synchronously or asynchronously to return a
     * response object
     * @deprecated See <a href=https://api.blockchain.info/price/specs>Blockchain Price API
     * specs</a>
     */
    @Deprecated
    public Call<Chart> getChart(String type, String timeSpan, String rollingAverage) {
        return getApiInterface().getChart(type, timeSpan, rollingAverage, "json", apiCode);
    }

    /**
     * This method can be used to get the data behind Blockchain.info's stats.
     *
     * @return
     */
    public Call<Stats> getStats() {
        return getApiInterface().getStats(apiCode);
    }

    /**
     * This method can be used to get the data behind Blockchain.info's pools information.
     *
     * @param timeSpan (Example: 5weeks)
     * @return {@link Call} object which can be executed synchronously or asynchronously to return a
     * response object
     */
    public Call<HashMap<String, Integer>> getPools(String timeSpan) {
        return getApiInterface().getPools(timeSpan, apiCode);
    }

}
