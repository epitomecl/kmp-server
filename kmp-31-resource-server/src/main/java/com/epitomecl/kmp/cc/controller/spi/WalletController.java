package com.epitomecl.kmp.cc.controller.spi;

import com.epitomecl.kmp.cc.interfaces.spi.IWallet;
import com.epitomecl.kmp.dc.repository.SharedBackupRepository;
import com.epitomecl.kmp.dc.repository.ThirdPartyBackupRepository;
import info.blockchain.wallet.api.data.Settings;
import info.blockchain.wallet.api.data.Status;
import info.blockchain.wallet.api.data.WalletOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.lang.invoke.MethodHandles;

@RestController
public class WalletController implements IWallet {
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    //@Autowired
    //SharedBackupRepository sharedBackupRepository;

    //@Autowired
    //ThirdPartyBackupRepository thirdPartyBackupRepository;

    public WalletController() {

    }

    @Override
    public String sharingBackup(
            @RequestParam("method") String method,
            @RequestParam("guid") String guid,
            @RequestParam("payload") String payload,
            @RequestParam("api_code") String apiCode) {
        String result = "ok";

        return result;
    }

    @Override
    public String thirdPartyBackup(
            @RequestParam("encrypt") String encrypt) {
        String result = "ok";

        return result;
    }

    @Override
    public String walletCall(
            //            @HeaderMap Map<String, String> headers,
            @RequestParam("method") String method,
            @RequestParam("guid") String guid,
            String sharedKey,
            String payload,
            int length,
            String checksum,
            String active,
            String email,
            String device,
            String old_checksum,
            String format,        //updateSettings, submitTwoFactorCode
            @RequestParam("api_code") String apiCode) {
        String result = "ok";

        return result;
    }

    private String postToWallet(
            String method,
            String guid,
            String sharedKey,
            String payload,
            int length,
            String apiCode) {
        return null;
    }

    private Settings fetchSettings(
            String method,
            String guid,
            String sharedKey,
            String format,
            String apiCode) {
        return null;
    }

    private String updateSettings(
            String method,
            String guid,
            String sharedKey,
            String payload,
            int length,
            String format,
            String apiCode) {
        return null;
    }

    private String fetchWalletData(
            String method,
            String guid,
            String sharedKey,
            String format,
            String apiCode) {
        return null;
    }

    private String submitTwoFactorCode(
            //            @HeaderMap Map<String, String> headers,
            String method,
            String guid,
            String twoFactorCode,
            int length,
            String format,
            String apiCode) {
        return null;
    }

    private String syncWalletCall(
            String method,
            String guid,
            String sharedKey,
            String payload,
            int length,
            String checksum,
            String active,
            String email,
            String device,
            String old_checksum,
            String apiCode) {
        return null;
    }

    private String fetchPairingEncryptionPasswordCall(
            String method,
            String guid,
            String apiCode) {
        return null;
    }

    private String fetchPairingEncryptionPassword(
            String method,
            String guid,
            String apiCode) {
        return null;
    }

    @Override
    public String getSessionId(
            @PathVariable("guid") String guid) {
        String result = "ok";

        return result;
    }

    @Override
    public String fetchEncryptedPayload(
            @PathVariable("guid") String guid,
//            @Header("cookie") String sessionId,
            @RequestParam("format") String format,
            @RequestParam("resend_code") boolean resendCode,
            @RequestParam("api_code") String apiCode) {
        String result = "ok";

        return result;
    }

    @Override
    public String getRandomBytes(
            @RequestParam("bytes") int bytes,
            @RequestParam("format") String format) {
        return null;
    }

    @Override
    public Status pinStore(
            @RequestParam("key") String key,
            @RequestParam("pin") String pin,
            @RequestParam("value") String value,
            @RequestParam("method") String method,
            @RequestParam("api_code") String apiCode) {
        Status result = new Status();

        return result;
    }

    @Override
    public Status logEvent(
            @RequestParam("name") String name,
            @RequestParam("api_code") String apiCode) {
        Status result = new Status();

        return result;
    }

    @Override
    public WalletOptions getWalletOptions(@RequestParam("api_code") String apiCode) {
        WalletOptions result = new WalletOptions();

        return result;
    }
}
