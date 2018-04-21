package com.epitomecl.kmp.cc.common;

import info.blockchain.wallet.FrameworkInterface;
import info.blockchain.wallet.api.Environment;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.params.BitcoinCashTestNet3Params;
import org.bitcoinj.params.BitcoinTestNet3Params;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class FrameworkImpl implements FrameworkInterface {

    private Retrofit retrofit;

    public FrameworkImpl() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        this.retrofit = new Retrofit.Builder()
                //.baseUrl("https://api.blockchain.info/")
                .baseUrl("http://localhost:8080/")
                .client(client)
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
    }

    @Override
    public Retrofit getRetrofitApiInstance() {
        return this.retrofit;
    }

    @Override
    public Retrofit getRetrofitExplorerInstance() {
        return this.retrofit;
    }

    @Override
    public Retrofit getRetrofitShapeShiftInstance() {
        return this.retrofit;
    }

    @Override
    public Environment getEnvironment() {
        return Environment.fromString("env_dev");
    }

    @Override
    public NetworkParameters getBitcoinParams() {
        // for TEST
        NetworkParameters params = BitcoinTestNet3Params.get();

        // for REAL
//        NetworkParameters params = BitcoinMainNetParams.get();

        return params;
    }

    @Override
    public NetworkParameters getBitcoinCashParams() {
        // for TEST
        NetworkParameters params = BitcoinCashTestNet3Params.get();

        // for REAL
//        NetworkParameters params =    BitcoinCashMainNetParams.get();

        return params;
    }

    @Override
    public String getApiCode() {
        return "25a6ad13-1633-4dfb-b6ee-9b91cdf0b5c3";
    }

    @Override
    public String getDevice() {
        return "android";
    }

    @Override
    public String getAppVersion() {
        return "0";
    }

}
