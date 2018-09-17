package com.epitomecl.kmp.blockexplorer.interfaces.api;

import com.epitomecl.kmp.blockexplorer.domain.EncryptedResult;
import com.epitomecl.kmp.blockexplorer.domain.UserVO;
import info.blockchain.api.data.*;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;

/**
 * source from - info.blockchain.api.interfaces.ApiInterface
 */
public interface IApi {

    @GetMapping("/charts/{chart-type}")
    Chart getChart(@PathVariable("chart-type") String chartType,
                   @RequestParam("timespan") String timespan,
                   @RequestParam("rollingAverage") String rollingAverage,
                   @RequestParam("format") String format,
                   @RequestParam("api_code") String apiCode);

    @GetMapping("/stats")
    Stats getStats(@RequestParam("api_code") String apiCode);

    @GetMapping("/pools")
    HashMap<String, Integer> getPools(
            @RequestParam("timespan") String timespan,
            @RequestParam("api_code") String apiCode);

    @GetMapping("/v2/receive")
    ReceivePayment receive(
            @RequestParam("xpub") String xpub,
            @RequestParam("callback") String callbackUrl,
            @RequestParam("gap_limit") Integer gapLimit,
            @RequestParam("index") Integer index,
            @RequestParam("key") String key);

    @GetMapping("/v2/receive/checkgap")
    XpubGap checkGap(
            @RequestParam("xpub") String xpub,
            @RequestParam("key") String key);

    @GetMapping("/v2/receive/callback_log")
    List<CallbackLog> callbackLog(
            @RequestParam("callback") String callbackUrl,
            @RequestParam("key") String key);

    @GetMapping("/ticker")
    Ticker getBtcTicker(
            @RequestParam("api_code") String apiCode);

//    @GetMapping("/ticker")
//    TreeMap<String, TickerItem> getBtcTickerMap(
//            @RequestParam("api_code") String apiCode);
//
//    @GetMapping("/ticker")
//    TreeMap<String, TickerItem> getEthTickerMap(
//            @RequestParam("base") String base,
//            @RequestParam("api_code") String apiCode);

    @GetMapping("/tobtc")
    Double toBTC(
            @RequestParam("currency") String currency,
            @RequestParam("value") Double value,
            @RequestParam("api_code") String apiCode);

    @GetMapping("/frombtc")
    Double fromBTC(
            @RequestParam("currency") String currency,
            @RequestParam("value") Long value,
            @RequestParam("api_code") String apiCode);

    @GetMapping("/toeth")
    Double toETH(
            @RequestParam("currency") String currency,
            @RequestParam("value") Double value,
            @RequestParam("api_code") String apiCode);

    @GetMapping("/frometh")
    Double fromETH(
            @RequestParam("currency") String currency,
            @RequestParam("value") String value,
            @RequestParam("api_code") String apiCode);

    @PostMapping("/{coin}/multiaddr")
    MultiAddress getMultiAddress(
            @PathVariable("coin") String coin,
            @RequestParam("active") String active,
            @RequestParam("n") Integer limit,
            @RequestParam("offset") Integer offset,
            @RequestParam("filter") Integer filter,
            @RequestParam("onlyShow") String context,
            @RequestParam("api_code") String apiCode);

    @GetMapping("/{coin}/unspent")
    UnspentOutputs getUnspent(
            @PathVariable("coin") String coin,
            @RequestParam("active") String active,
            @RequestParam("confirmations") Integer confirmations,
            @RequestParam("limit") Integer limit,
            @RequestParam("api_code") String apiCode);

    @PostMapping("/{coin}/balance")
    HashMap<String, Balance> getBalance(
            @PathVariable("coin") String coin,
            @RequestParam("active") String active,
            @RequestParam("filter") Integer filter,
            @RequestParam("api_code") String apiCode);

    @PostMapping("/{coin}/pushtx")
    String pushTx(
            @PathVariable("coin") String coin,
            @RequestParam("tx") String hash,
            @RequestParam("api_code") String apiCode);

    @PostMapping("/login")
    UserVO postLogin(
            @RequestParam("id") String id,
            @RequestParam("pw") String pw,
            HttpSession session);

    @PostMapping("/regist")
    UserVO postRegist(
            @RequestParam("id") String id,
            @RequestParam("pw") String pw,
            HttpSession session);

    @PostMapping("/setencrypted")
    EncryptedResult setEncrypted(
            @RequestParam("index") int index,
            @RequestParam("label") String label,
            @RequestParam("encrypted") String encrypted,
            HttpSession session);

    @PostMapping("/getencrypted")
    EncryptedResult getEncrypted(
            @RequestParam("index") int index,
            @RequestParam("label") String label,
            HttpSession session);

}
