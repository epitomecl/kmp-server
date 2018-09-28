package com.epitomecl.kmp.api.interfaces.api;

import com.epitomecl.kmp.api.domain.ActiveAddress;
import com.epitomecl.kmp.api.domain.SecretSharingResult;
import com.epitomecl.kmp.api.domain.SecretSharingVO;
import com.epitomecl.kmp.api.domain.SendTXResult;
import info.blockchain.api.data.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * source from - info.blockchain.api.interfaces.ServiceWalletInterface
 */
public interface IServiceWallet {

//    @GetMapping("/api/v2/create")
//    CreateWalletResponse createWallet(
//            @RequestParam("password") String password,
//            @RequestParam("api_code") String api_code,
//            @RequestParam("priv") String priv,
//            @RequestParam("label") String label,
//            @RequestParam("email") String email);
//
//    @GetMapping("/merchant/{guid}/balance")
//    WalletBalance getBalance(
//            @PathVariable("guid") String guid,
//            @RequestParam("password") String password,
//            @RequestParam("api_code") String apiCode);
//
//    @GetMapping("/merchant/{guid}/{method}")
//    WalletPaymentResponse send(
//            @PathVariable("guid") String guid,
//            @PathVariable("method") String method,
//            @RequestParam("password") String password,
//            @RequestParam("second_password") String secondPassword,
//            @RequestParam("api_code") String apiCode,
//            @RequestParam("to") String to,
//            @RequestParam("amount") String amount,
//            @RequestParam("recipients") String recipients,
//            @RequestParam("from") String from,
//            @RequestParam("fee") long fee,
//            @RequestParam("note") String note);
//
//    @GetMapping("/merchant/{guid}/list")
//    WalletAddressList listAddresses(
//            @PathVariable("guid") String guid,
//            @RequestParam("password") String password,
//            @RequestParam("api_code") String apiCode);
//
//    @GetMapping("/merchant/{guid}/address_balance")
//    WalletAddress getAddress(
//            @PathVariable("guid") String guid,
//            @RequestParam("password") String password,
//            @RequestParam("api_code") String apiCode,
//            @RequestParam("address") String address);
//
//    @GetMapping("/merchant/{guid}/new_address")
//    WalletAddress newAddress(
//            @PathVariable("guid") String guid,
//            @RequestParam("password") String password,
//            @RequestParam("second_password") String secondPassword,
//            @RequestParam("api_code") String apiCode,
//            @RequestParam("label") String label);
//
//    @GetMapping("/merchant/{guid}/archive_address")
//    WalletAddressArchiveResponse archiveAddress(
//            @PathVariable("guid") String guid,
//            @RequestParam("password") String password,
//            @RequestParam("api_code") String apiCode,
//            @RequestParam("address") String address);
//
//    @GetMapping("/merchant/{guid}/unarchive_address")
//    WalletAddressUnarchiveResponse unarchiveAddress(
//            @PathVariable("guid") String guid,
//            @RequestParam("password") String password,
//            @RequestParam("api_code") String apiCode,
//            @RequestParam("address") String address);

    //region custom extension
    @PostMapping("/send")
    SendTXResult send(
            @RequestParam("hashtx") String hashtx,
            @RequestParam("api_code") String api_code,
            HttpSession session);

    @PostMapping("/sharingdatalist")
    List<String> getSharingDataList(
            @RequestParam("index") int index,
            @RequestParam("api_code") String api_code,
            HttpSession session);

    @PostMapping("/sharingdataone")
    SecretSharingVO getSharingDataOne(@RequestParam("index") int index,
                                      @RequestParam("label") String label,
                                      @RequestParam("api_code") String api_code,
                                      HttpSession session);

    @PostMapping("/sharingdatatwo")
    SecretSharingVO getSharingDataTwo(@RequestParam("index") int index,
                                      @RequestParam("label") String label,
                                      @RequestParam("api_code") String api_code,
                                      HttpSession session);

    @PostMapping("/backupsharingdataone")
    SecretSharingResult backupSharingDataOne(@RequestParam("index") int index,
                                             @RequestParam("label") String label,
                                             @RequestParam("shareddata") String shareddata,
                                             @RequestParam("api_code") String api_code,
                                             HttpSession session);

    @PostMapping("/backupsharingdatatwo")
    SecretSharingResult backupSharingDataTwo(@RequestParam("index") int index,
                                             @RequestParam("label") String label,
                                             @RequestParam("shareddata") String shareddata,
                                             @RequestParam("api_code") String api_code,
                                             HttpSession session);
    //endregion

    //region support for integration test
    @PostMapping("/coinfromfaucet")
    SendTXResult coinFromFaucet(
            @RequestParam("address") String address,
            @RequestParam("api_code") String api_code,
            HttpSession session);

    @PostMapping("/addressfaucet")
    ActiveAddress addressFaucet(
            @RequestParam("api_code") String api_code,
            HttpSession session);
    //endregion
}
