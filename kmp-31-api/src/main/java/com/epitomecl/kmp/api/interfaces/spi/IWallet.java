package com.epitomecl.kmp.api.interfaces.spi;

import info.blockchain.wallet.api.data.Status;
import info.blockchain.wallet.api.data.WalletOptions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * source from - info.blockchain.wallet.api.WalletEndpoints
 */
public interface IWallet {

    @PostMapping("/wallet")
    String walletCall(
            //            @HeaderMap Map<String, String> headers,
            @RequestParam("method") String method,
            @RequestParam("guid") String guid,
            @RequestParam(value = "sharedKey", required = false) String sharedKey,
            @RequestParam(value = "payload", required = false) String payload,
            @RequestParam(value = "length", required = false) int length,
            @RequestParam(value = "checksum", required = false) String checksum,
            @RequestParam(value = "active", required = false) String active,
            @RequestParam(value = "email", required = false) String email,
            @RequestParam(value = "device", required = false) String device,
            @RequestParam(value = "old_checksum", required = false) String old_checksum,
            @RequestParam(value = "format", required = false) String format,        //updateSettings, submitTwoFactorCode
            @RequestParam("api_code") String apiCode);

    @GetMapping("/wallet/{guid}?format=json&resend_code=false")
    String getSessionId(
            @PathVariable("guid") String guid);

    @GetMapping("/wallet/{guid}")
    String fetchEncryptedPayload(
            @PathVariable("guid") String guid,
//            @Header("cookie") String sessionId,
            @RequestParam("format") String format,
            @RequestParam("resend_code") boolean resendCode,
            @RequestParam("api_code") String apiCode);

    @GetMapping("/v2/randombytes")
    String getRandomBytes(
            @RequestParam("bytes") int bytes,
            @RequestParam("format") String format);

    @PostMapping("/pin-store")
    Status pinStore(
            @RequestParam("key") String key,
            @RequestParam("pin") String pin,
            @RequestParam("value") String value,
            @RequestParam("method") String method,
            @RequestParam("api_code") String apiCode);

    @GetMapping("/event")
    Status logEvent(
            @RequestParam("name") String name,
            @RequestParam("api_code") String apiCode);

    @GetMapping("/Resources/wallet-options.json")
    WalletOptions getWalletOptions(@RequestParam("api_code") String apiCode);
}