package com.epitomecl.kmp.api.interfaces.spi;

import info.blockchain.wallet.shapeshift.ShapeShiftUrls;
import info.blockchain.wallet.shapeshift.data.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface IShapeShift {

    @GetMapping(ShapeShiftUrls.MARKET_INFO + "/{pair}")
    MarketInfo getMarketInfo(@PathVariable("pair") String pair);

    @PostMapping(ShapeShiftUrls.SENDAMOUNT)
    SendAmountResponseWrapper getQuote(@RequestBody QuoteRequest request);

    @PostMapping(ShapeShiftUrls.SENDAMOUNT)
    QuoteResponseWrapper getApproximateQuote(@RequestBody QuoteRequest request);

    @GetMapping(ShapeShiftUrls.TX_STATS + "/{address}")
    TradeStatusResponse getTradeStatus(@PathVariable("address") String address);

    @GetMapping(ShapeShiftUrls.TIME_REMAINING + "/{address}")
    TimeRemaining getTimeRemaining(@PathVariable("address") String address);
}
