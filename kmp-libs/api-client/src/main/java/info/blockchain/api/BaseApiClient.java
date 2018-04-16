package info.blockchain.api;

import info.blockchain.api.interfaces.ApiInterface;
import info.blockchain.api.interfaces.ExplorerInterface;
import okhttp3.CertificatePinner;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.Retrofit.Builder;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.util.Properties;
import java.util.concurrent.TimeUnit;

/**
 * Created by riaanvos on 26/01/2017.
 */
public abstract class BaseApiClient {

    //Certificate Pinning
    public static final String HOSTNAME_1 = "blockchain.info";
    public static final String CERTIFICATE_1 = "Z87j23nY+/WSTtsgE/O4ZcDVhevBohFPgPMU6rV2iSw=";//base64

    public String apiCode;

    public static String EXPLORER_URL = "https://blockchain.info";
    public static String API_URL = "https://api.blockchain.info";

    private static final Properties urls = new Properties();

    private ExplorerInterface explorerInterface;
    private ApiInterface apiInterface;

    /**
     * Provides {@link CertificatePinner} for host blockchain.info.
     *
     * @return
     */
    public static CertificatePinner getCertificatePinner() {
        return new CertificatePinner.Builder()
                .add(HOSTNAME_1, "sha256/" + CERTIFICATE_1)
                .build();
    }

    /**
     * Default constructor for production environment.
     * Uses default OkHttpClient with blockchain.info certificate pinning for secure host verification.
     */
    public BaseApiClient() {
        this.apiCode = null;

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .retryOnConnectionFailure(false)
                .certificatePinner(getCertificatePinner())
//                .addInterceptor(getVerboseInterceptor())
                .build();

        setExplorerInterface(okHttpClient);
        setApiInterface(okHttpClient);
    }

    /**
     * Default constructor for production environment.
     * Uses default OkHttpClient with blockchain.info certificate pinning for secure host verification.
     */
    public BaseApiClient(String apiCode) {
        this();
        this.apiCode = apiCode;
    }

    /*
    Some api interfaces might rely on both explorer-root-url and api-root-url, so we'll allow both.
     */
    public BaseApiClient(Retrofit retrofitExplorerRoot, Retrofit retrofitApiRoot, String apiCode) {
        this.apiCode = apiCode;

        if (retrofitExplorerRoot != null) {
            explorerInterface = retrofitExplorerRoot.create(ExplorerInterface.class);
        }

        if (retrofitApiRoot != null) {
            apiInterface = retrofitApiRoot.create(ApiInterface.class);
        }
    }

    private void setExplorerInterface(OkHttpClient okHttpClient) {
        explorerInterface = new Builder()
                .baseUrl(EXPLORER_URL)
                .client(okHttpClient)
                .addConverterFactory(JacksonConverterFactory.create())
                .build()
                .create(ExplorerInterface.class);
    }

    private void setApiInterface(OkHttpClient okHttpClient) {
        apiInterface = new Builder()
                .baseUrl(API_URL)
                .client(okHttpClient)
                .addConverterFactory(JacksonConverterFactory.create())
                .build()
                .create(ApiInterface.class);
    }

    public ExplorerInterface getExplorerInterface() {
        return explorerInterface;
    }

    public ApiInterface getApiInterface() {
        return apiInterface;
    }

    private HttpLoggingInterceptor getVerboseInterceptor() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        return logging;
    }

}
