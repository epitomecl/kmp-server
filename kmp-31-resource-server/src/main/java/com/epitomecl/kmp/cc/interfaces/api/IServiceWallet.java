package com.epitomecl.kmp.cc.interfaces.api;

import info.blockchain.api.data.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * source from - info.blockchain.api.interfaces.ServiceWalletInterface
 */
public interface IServiceWallet {

    @GetMapping("/api/v2/create")
    CreateWalletResponse createWallet(
            @RequestParam("password") String password,
            @RequestParam("api_code") String api_code,
            @RequestParam("priv") String priv,
            @RequestParam("label") String label,
            @RequestParam("email") String email);

    @GetMapping("/merchant/{guid}/balance")
    WalletBalance getBalance(
            @PathVariable("guid") String guid,
            @RequestParam("password") String password,
            @RequestParam("api_code") String apiCode);

    @GetMapping("/merchant/{guid}/{method}")
    WalletPaymentResponse send(
            @PathVariable("guid") String guid,
            @PathVariable("method") String method,
            @RequestParam("password") String password,
            @RequestParam("second_password") String secondPassword,
            @RequestParam("api_code") String apiCode,
            @RequestParam("to") String to,
            @RequestParam("amount") String amount,
            @RequestParam("recipients") String recipients,
            @RequestParam("from") String from,
            @RequestParam("fee") long fee,
            @RequestParam("note") String note);

    @GetMapping("/merchant/{guid}/list")
    WalletAddressList listAddresses(
            @PathVariable("guid") String guid,
            @RequestParam("password") String password,
            @RequestParam("api_code") String apiCode);

    @GetMapping("/merchant/{guid}/address_balance")
    WalletAddress getAddress(
            @PathVariable("guid") String guid,
            @RequestParam("password") String password,
            @RequestParam("api_code") String apiCode,
            @RequestParam("address") String address);

    @GetMapping("/merchant/{guid}/new_address")
    WalletAddress newAddress(
            @PathVariable("guid") String guid,
            @RequestParam("password") String password,
            @RequestParam("second_password") String secondPassword,
            @RequestParam("api_code") String apiCode,
            @RequestParam("label") String label);

    @GetMapping("/merchant/{guid}/archive_address")
    WalletAddressArchiveResponse archiveAddress(
            @PathVariable("guid") String guid,
            @RequestParam("password") String password,
            @RequestParam("api_code") String apiCode,
            @RequestParam("address") String address);

    @GetMapping("/merchant/{guid}/unarchive_address")
    WalletAddressUnarchiveResponse unarchiveAddress(
            @PathVariable("guid") String guid,
            @RequestParam("password") String password,
            @RequestParam("api_code") String apiCode,
            @RequestParam("address") String address);
}
