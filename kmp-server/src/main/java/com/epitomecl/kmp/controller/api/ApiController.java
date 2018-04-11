package com.epitomecl.kmp.controller.api;

import com.epitomecl.kmp.interfaces.api.IApi;
import info.blockchain.api.data.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

@RestController
public class ApiController implements IApi {
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public ApiController() {

    }

    public Chart getChart(@PathVariable("chart-type") String chartType,
                          @RequestParam("timespan") String timespan,
                          @RequestParam("rollingAverage") String rollingAverage,
                          @RequestParam("format") String format,
                          @RequestParam("api_code") String apiCode) {
        return new Chart();
    }

    public Stats getStats(@RequestParam("api_code") String apiCode) {
        return new Stats();
    }

    public HashMap<String, Integer> getPools(
            @RequestParam("timespan") String timespan,
            @RequestParam("api_code") String apiCode) {
        return new HashMap<String, Integer>();
    }

    public ReceivePayment receive(
            @RequestParam("xpub") String xpub,
            @RequestParam("callback") String callbackUrl,
            @RequestParam("gap_limit") Integer gapLimit,
            @RequestParam("index") Integer index,
            @RequestParam("key") String key) {
        return new ReceivePayment();
    }

    public XpubGap checkGap(
            @RequestParam("xpub") String xpub,
            @RequestParam("key") String key) {
        return new XpubGap();
    }

    public List<CallbackLog> callbackLog(
            @RequestParam("callback") String callbackUrl,
            @RequestParam("key") String key) {
        List<CallbackLog> result = new ArrayList<CallbackLog>();
        return result;
    }

    public Ticker getBtcTicker(
            @RequestParam("api_code") String apiCode) {
        return new Ticker();
    }

    public TreeMap<String, TickerItem> getBtcTickerMap(
            @RequestParam("api_code") String apiCode) {
        return new TreeMap<String, TickerItem>();
    }

    public TreeMap<String, TickerItem> getEthTickerMap(
            @RequestParam("base") String base,
            @RequestParam("api_code") String apiCode) {
        return new TreeMap<String, TickerItem>();
    }

    public Double toBTC(
            @RequestParam("currency") String currency,
            @RequestParam("value") Double value,
            @RequestParam("api_code") String apiCode) {
        return 0.0;
    }

    public Double fromBTC(
            @RequestParam("currency") String currency,
            @RequestParam("value") Long value,
            @RequestParam("api_code") String apiCode) {
        return 0.0;
    }

    public Double toETH(
            @RequestParam("currency") String currency,
            @RequestParam("value") Double value,
            @RequestParam("api_code") String apiCode) {
        return 0.0;
    }

    public Double fromETH(
            @RequestParam("currency") String currency,
            @RequestParam("value") String value,
            @RequestParam("api_code") String apiCode) {
        return 0.0;
    }

    public MultiAddress getMultiAddress(
            @PathVariable("coin") String coin,
            @RequestParam("active") String active,
            @RequestParam("n") Integer limit,
            @RequestParam("offset") Integer offset,
            @RequestParam("filter") Integer filter,
            @RequestParam("onlyShow") String context,
            @RequestParam("api_code") String apiCode) {
        return new MultiAddress();
    }

    public UnspentOutputs getUnspent(
            @PathVariable("coin") String coin,
            @RequestParam("active") String active,
            @RequestParam("confirmations") Integer confirmations,
            @RequestParam("limit") Integer limit,
            @RequestParam("api_code") String apiCode) {
        return new UnspentOutputs();
    }

    public HashMap<String, Balance> getBalance(
            @PathVariable("coin") String coin,
            @RequestParam("active") String active,
            @RequestParam("filter") Integer filter,
            @RequestParam("api_code") String apiCode) {
        return new HashMap<String, Balance>();
    }

    public String pushTx(
            @PathVariable("coin") String coin,
            @RequestParam("tx") String hash,
            @RequestParam("api_code") String apiCode) {
        return "ok";
    }
}
