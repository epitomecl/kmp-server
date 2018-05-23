package com.epitomecl.kmp.cc.controller.api;

import com.epitomecl.kmp.cc.common.FrameworkImpl;
import com.epitomecl.kmp.cc.interfaces.api.IServiceWallet;
import com.epitomecl.kmp.core.common.HomeConfigurator;
import com.fasterxml.jackson.databind.ObjectMapper;
import info.blockchain.api.data.*;
import info.blockchain.wallet.BlockchainFramework;
import info.blockchain.wallet.exceptions.HDWalletException;
import info.blockchain.wallet.exceptions.ServerConnectionException;
import info.blockchain.wallet.payload.PayloadManager;
import info.blockchain.wallet.payload.data.Account;
import info.blockchain.wallet.payload.data.Wallet;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;


@RestController
public class ServiceWalletController implements IServiceWallet {
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    //todo. for test. fix or remove later
    private Wallet wallet;
    private final String hdWalletFileName = HomeConfigurator.getTmpDir() + "/hdWallet.json";

    public ServiceWalletController() {
        BlockchainFramework.init(new FrameworkImpl());
    }

    private boolean verifyGuid(String guid, String password) {
        //todo. need to check password
        if (this.wallet.getGuid().equals(guid)) {
            return true;
        }
        return false;
    }

    private info.blockchain.wallet.payload.data.HDWallet getHdWallet() {
        return wallet.getHdWallets().get(0);
    }

    private boolean archiveAddress(info.blockchain.wallet.payload.data.HDWallet hdWallet, String address, boolean archived) {
        try {
            PayloadManager payloadManager = PayloadManager.getInstance();

            List<Account> accounts = hdWallet.getAccounts();
            for (Account m : accounts) {
                if (payloadManager.getNextReceiveAddress(m).equals(address)) {
                    m.setArchived(archived);
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * source from - piuk.blockchain.android.data.payload.PayloadService#createHdWallet
     *
     * @param password
     * @param api_code
     * @param priv
     * @param label
     * @param email
     * @return
     */
    @Override
    public CreateWalletResponse createWallet(
            @RequestParam("password") String password,
            @RequestParam("api_code") String api_code,
            @RequestParam("priv") String priv,
            @RequestParam("label") String label,
            @RequestParam("email") String email
    ) {
        PayloadManager payloadManager = PayloadManager.getInstance();
        String defaultAccountName = "My Bitcoin Wallet";
        try {
            wallet = payloadManager.create(defaultAccountName, email, password);

            //wallet data write to json format
            FileUtils.forceMkdir(new File(HomeConfigurator.getTmpDir()));
            String json = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(wallet);
            Files.write(Paths.get(hdWalletFileName), json.getBytes(StandardCharsets.UTF_8), StandardOpenOption.CREATE);

            CreateWalletResponse createWalletResponse = new CreateWalletResponse();

            info.blockchain.wallet.payload.data.HDWallet hdWallet = getHdWallet();
            int defaultAccountIdx = hdWallet.getDefaultAccountIdx();
            Account account = hdWallet.getAccount(defaultAccountIdx);
            String address = payloadManager.getNextReceiveAddress(account);

            createWalletResponse.setGuid(wallet.getGuid());
            createWalletResponse.setLabel(label);
            createWalletResponse.setAddress(address);

            return createWalletResponse;
        } catch (Exception e) {
            throw new IllegalStateException(ExceptionUtils.getStackTrace(e));
        }
    }

    @Override
    public WalletBalance getBalance(
            @PathVariable("guid") String guid,
            @RequestParam("password") String password,
            @RequestParam("api_code") String apiCode
    ) {
        WalletBalance walletBalance = new WalletBalance();

        if (verifyGuid(guid, password)) {
            PayloadManager payloadManager = PayloadManager.getInstance();

            try {
                payloadManager.updateAllBalances();

                BigInteger balance = payloadManager.getWalletBalance();
                walletBalance.setBalance(balance);
            } catch (ServerConnectionException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return walletBalance;
    }

    @Override
    public WalletPaymentResponse send(
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
            @RequestParam("note") String note
    ) {
        return null;
    }

    @Override
    public WalletAddressList listAddresses(
            @PathVariable("guid") String guid,
            @RequestParam("password") String password,
            @RequestParam("api_code") String apiCode
    ) {
        //todo. for test. fix or remove later
        WalletAddressList walletAddressList = new WalletAddressList();

        if (verifyGuid(guid, password)) {
            PayloadManager payloadManager = PayloadManager.getInstance();

            ArrayList<WalletAddress> addressList = new ArrayList<WalletAddress>();

            //get default created HdWallet
            info.blockchain.wallet.payload.data.HDWallet hdWallet = getHdWallet();

            List<Account> list = hdWallet.getAccounts();
            for (Account m : list) {
                try {
                    WalletAddress walletAddress = new WalletAddress();
                    walletAddress.setAddress(payloadManager.getNextReceiveAddress(m));
                    walletAddress.setLabel(m.getLabel());
                    walletAddress.setBalance(BigInteger.valueOf(0));        //?
                    walletAddress.setTotalReceived(BigInteger.valueOf(0));  //?
                    addressList.add(walletAddress);
                } catch (HDWalletException e) {
                    e.printStackTrace();
                }
            }

            walletAddressList.setAddressList(addressList);
        }

        return walletAddressList;
    }

    @Override
    public WalletAddress getAddress(
            @PathVariable("guid") String guid,
            @RequestParam("password") String password,
            @RequestParam("api_code") String apiCode,
            @RequestParam("address") String address
    ) {
        //todo. for test. fix or remove later
        WalletAddress walletAddress = new WalletAddress();

        if (verifyGuid(guid, password)) {
            PayloadManager payloadManager = PayloadManager.getInstance();

            //need to find out address is in my accounts in hdwallets
            info.blockchain.wallet.payload.data.HDWallet hdWallet = getHdWallet();

            List<Account> list = hdWallet.getAccounts();
            for (Account m : list) {
                try {
                    if (payloadManager.getNextReceiveAddress(m).equals(address)) {
                        walletAddress.setAddress(address);
                        walletAddress.setLabel(m.getLabel());
                        walletAddress.setBalance(BigInteger.valueOf(0));        //?
                        walletAddress.setTotalReceived(BigInteger.valueOf(0));  //?

                        break;
                    }
                } catch (HDWalletException e) {
                    e.printStackTrace();
                }
            }
        }

        return walletAddress;
    }

    @Override
    public WalletAddress newAddress(
            @PathVariable("guid") String guid,
            @RequestParam("password") String password,
            @RequestParam(value = "second_password", required = false) String secondPassword,
            @RequestParam("api_code") String apiCode,
            @RequestParam("label") String label
    ) {
        WalletAddress newAddress = new WalletAddress();

        if (verifyGuid(guid, password)) {
            try {
                PayloadManager payloadManager = PayloadManager.getInstance();

                //if wallet is double encrypted then secondPassword is valid
                //this case wallet is not double encrypted so secondPassword is null
                Account newAccount = payloadManager.addAccount(label, secondPassword);
                newAddress.setLabel(newAccount.getLabel());
                newAddress.setAddress(payloadManager.getNextReceiveAddress(newAccount));
                newAddress.setBalance(new BigInteger("0"));         //?
                newAddress.setTotalReceived(new BigInteger("0"));   //?
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return newAddress;
    }

    @Override
    public WalletAddressArchiveResponse archiveAddress(
            @PathVariable("guid") String guid,
            @RequestParam("password") String password,
            @RequestParam("api_code") String apiCode,
            @RequestParam("address") String address
    ) {
        //todo. for test. fix or remove later
        WalletAddressArchiveResponse archiveResponse = new WalletAddressArchiveResponse();

        if (verifyGuid(guid, password)) {
            info.blockchain.wallet.payload.data.HDWallet hdWallet = getHdWallet();

            if (archiveAddress(hdWallet, address, true)) {
                archiveResponse.setArchived(address);   //?
            }
        }

        return archiveResponse;
    }

    @Override
    public WalletAddressUnarchiveResponse unarchiveAddress(
            @PathVariable("guid") String guid,
            @RequestParam("password") String password,
            @RequestParam("api_code") String apiCode,
            @RequestParam("address") String address
    ) {
        //todo. for test. fix or remove later
        WalletAddressUnarchiveResponse unArchiveResponse = new WalletAddressUnarchiveResponse();

        if (verifyGuid(guid, password)) {
            info.blockchain.wallet.payload.data.HDWallet hdWallet = getHdWallet();

            if (archiveAddress(hdWallet, address, false)) {
                unArchiveResponse.setActive(address);   //?
            }
        }

        return unArchiveResponse;
    }
}
