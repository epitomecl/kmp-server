package com.epitomecl.kmp.interfaces.spi;

import info.blockchain.wallet.prices.PriceUrls;
import info.blockchain.wallet.prices.data.PriceDatum;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

interface IPrice {

    @GetMapping("/" + PriceUrls.PRICE_SERIES)
    List<PriceDatum> getHistoricPriceSeries(@RequestParam("base") String base,
                                            @RequestParam("quote") String quote,
                                            @RequestParam("start") long start,
                                            @RequestParam("scale") int scale,
                                            @RequestParam("api_key") String apiKey);

    @GetMapping("/" + PriceUrls.SINGLE_PRICE)
    PriceDatum getCurrentPrice(@RequestParam("base") String base,
                               @RequestParam("quote") String quote,
                               @RequestParam("api_key") String apiKey);

    @GetMapping("/" + PriceUrls.SINGLE_PRICE)
    PriceDatum getHistoricPrice(@RequestParam("base") String base,
                                @RequestParam("quote") String quote,
                                @RequestParam("time") long time,
                                @RequestParam("api_key") String apiKey);

    @GetMapping("/" + PriceUrls.PRICE_INDEXES)
    Map<String, PriceDatum> getPriceIndexes(@RequestParam("base") String base,
                                            @RequestParam("api_key") String apiKey);

}
