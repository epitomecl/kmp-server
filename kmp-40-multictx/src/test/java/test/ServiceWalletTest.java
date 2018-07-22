package test;

import com.epitomecl.kmp.cc.main.KmpApp;
import com.fasterxml.jackson.core.JsonProcessingException;
import info.blockchain.api.data.*;
import info.blockchain.api.wallet.Wallet;
import okhttp3.ResponseBody;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.math.BigInteger;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ServiceWalletTest {
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private static final String _serviceURL = "http://localhost:8080/";
    private static final String _password = "YOUR_SUPER_SECURE_PASSWORD";
    private static final String _apiCode = "YOUR_API_CODE";
    private static final String _label = "label";
    private static final String _priv = "PRIV";
    private static final String _email = "aaa@aaa.com";

    private static KmpApp kmpApp;

    private static String _guid = null;//"d0899765-baf2-4c60-80c4-154a59c2e622";  //created Wallet identifier
    private static String _address = null;//"1Mi4eKxgdNR5PCPfWZit2jVx7RFN59ULVY"; //created Wallet default bitcoin wallet address
    private static Wallet wallet;
    private static long _balance = 0;

    /**
     * Execute a REST call and block until the response is received.
     */
    private <T> T executeSync(Call<T> call) throws Exception {
        try {
            Response<T> response = call.execute();
            if (response.isSuccessful()) {
                return response.body();
            } else {
                ResponseBody errorBody = response.errorBody();
                logger.error("[errorBody]" + (errorBody == null ? "" : errorBody.string()));
                throw new Exception(response.message());
            }
        } catch (IOException e) {
            throw new Exception(e);
        }
    }

    @BeforeClass
    public static void beforeClass() {
        kmpApp = new KmpApp();
        kmpApp.start(new String[0]);
    }

    @AfterClass
    public static void afterClass() {
        kmpApp.stop();
    }

    @Test
    public void test1_createWallet() throws Exception {
        //if there are no Wallet guid and address
        if ((_guid == null) && (_address == null)) {
            CreateWalletResponse created = executeSync(Wallet.createWallet(
                    _serviceURL,
                    _password,
                    _apiCode,
                    _priv,
                    _label,
                    _email));

            logger.info("[wallet-identifier]" + created.getGuid());

            _guid = created.getGuid();
            _address = created.getAddress();
            // when Wallet created, it has default bitcoin wallet address.
        }
    }

    @Test
    public void test2_getAddress() throws Exception {
        //create wallet client object to request wallet data
        wallet = new Wallet(
                _serviceURL,
                _apiCode,
                _guid,
                _password);

        // request one wallet-address data if we know the address
        WalletAddress addressData = executeSync(wallet.getAddress(_address));
        reqAddressData(wallet, addressData);
    }

    @Test
    public void test3_listAddresses() throws Exception {

        // list all addresses and their balances
        WalletAddressList addressList = executeSync(wallet.listAddresses());
        reqWalletAddressList(addressList);
    }

    @Test
    public void test4_getBalance() throws Exception {

        // Fetches the wallet balance. Includes unconfirmed transactions and
        WalletBalance totalBalance = executeSync(wallet.getBalance());
        reqWalletTotalBalance(totalBalance);
    }

    @Test
    public void test5_archiveAddress() throws Exception {

        // archive an old address. this call remove address in Wallet. write address on some kind of paper.
        WalletAddressArchiveResponse archiveAddress = executeSync(wallet.archiveAddress(_address));
        reqArchiveAddress(archiveAddress);
    }

    @Test
    public void test6_unarchiveAddress() throws Exception {

        // attache address.
        WalletAddressUnarchiveResponse unarchiveAddress = executeSync(wallet.unarchiveAddress(_address));
        reqUnarchiveAddress(unarchiveAddress);
    }

    @Test
    public void test7_newAddress() throws Exception {

        // create a new address and attach a label to it
        WalletAddress newAddress = executeSync(wallet.newAddress("new test label 123"));
        reqNewAddress(newAddress);
    }

    private void reqAddressData(Wallet wallet, WalletAddress addressData) throws Exception {
        logger.info(String.format("The balance is %s", addressData.getBalance()));

        // if address has balance
        if (addressData.getBalance().compareTo(new BigInteger("0")) == 1) {
            //send test wallet payment
            try {
                sendTestWalletPayment(wallet,
                        "1dice6YgEVBf88erBFra9BHf6ZMoyvG88",
                        20000000L,
                        null,
                        1000000L,
                        "note");
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
    }

    private void reqWalletAddressList(WalletAddressList addressList) throws Exception {
        for (WalletAddress a : addressList.getAddressList()) {
            logger.info(String.format("The address %s has a balance of %s satoshi",
                    a.getAddress(), a.getBalance()));
        }
    }

    private void sendTestWalletPayment(Wallet wallet, String toAddress,
                                       long amount,
                                       String fromAddress,
                                       Long fee,
                                       String note) throws Exception {
        // send 0.2 bitcoins with a custom fee of 0.01 BTC and a note
        // public notes require a minimum transaction size of 0.005 BTC
        WalletPaymentResponse payment = executeSync(wallet.send(
                "1dice6YgEVBf88erBFra9BHf6ZMoyvG88",
                20000000L,
                null,
                1000000L,
                "note"));

        logger.info(String.format("The payment TX hash is %s", payment.getTxHash()));
    }

    private void reqWalletTotalBalance(WalletBalance balance) throws Exception {
        BigInteger totalBalance = balance.getBalance();
        logger.info(String.format("The total wallet balance is %s", totalBalance));
    }

    private void reqArchiveAddress(WalletAddressArchiveResponse balance) throws Exception {
        String addressArchived = balance.getArchived();
        logger.info(String.format("Archived address is %s, keep it safe. dont't forget this.", addressArchived));
    }

    private void reqUnarchiveAddress(WalletAddressUnarchiveResponse balance) throws Exception {
        String addressActive = balance.getActive();
        logger.info(String.format("Unarchived address is %s, it's active address in wallet.", addressActive));
    }

    private void reqNewAddress(WalletAddress address) throws Exception {
        logger.info(String.format("The new address is %s", address.getAddress()));
    }

}
