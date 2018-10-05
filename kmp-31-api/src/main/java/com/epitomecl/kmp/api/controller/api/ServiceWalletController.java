package com.epitomecl.kmp.api.controller.api;

import com.epitomecl.kmp.api.domain.ActiveAddress;
import com.epitomecl.kmp.api.domain.SecretSharingResult;
import com.epitomecl.kmp.api.domain.SecretSharingVO;
import com.epitomecl.kmp.api.domain.SendTXResult;
import com.epitomecl.kmp.api.service.SecretShareServiceImpl;
import com.epitomecl.kmp.core.wallet.*;
import com.epitomecl.kmp.api.interfaces.api.IServiceWallet;
import com.epitomecl.kmp.core.wallet.UTXO;
import com.epitomecl.kmp.core.wallet.TXBuilder;
import info.blockchain.api.data.*;
import info.blockchain.wallet.exceptions.HDWalletException;
import info.blockchain.wallet.exceptions.ServerConnectionException;
import info.blockchain.wallet.payload.PayloadManager;
import info.blockchain.wallet.payload.data.Account;
import info.blockchain.wallet.payload.data.Wallet;
import org.apache.commons.codec.DecoderException;
import org.bitcoinj.core.BlockChain;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.core.PeerGroup;
import org.bitcoinj.core.Transaction;
import org.bitcoinj.crypto.MnemonicException;
import org.bitcoinj.net.discovery.DnsDiscovery;
import org.bitcoinj.params.TestNet3Params;
import org.bitcoinj.store.BlockStore;
import org.bitcoinj.store.BlockStoreException;
import org.bitcoinj.store.MemoryBlockStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongycastle.util.encoders.Hex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;


@RestController
public class ServiceWalletController implements IServiceWallet {
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Autowired
    private SecretShareServiceImpl service;

    //todo. for test. fix or remove later
    //private Wallet wallet;
    private PeerGroup peerGroup;

    private HDWalletData faucet;

    @Autowired
    private ExplorerController explorerController;

    public ServiceWalletController() {
        //peer start
        NetworkParameters netParams = TestNet3Params.get();
        BlockStore bs;

        try {
            faucet = (HDWalletData) HDWalletData.restoreFromSeed(CryptoType.BITCOIN_TESTNET, "e1f3906a6f161428fe600f9e646ebc2f", "", "faucet wallet", 5);

            bs = new MemoryBlockStore(netParams);
            BlockChain chain = new BlockChain(netParams, bs);

            peerGroup = new PeerGroup(netParams, chain);
            peerGroup.setUserAgent("PeerMonitor", "1.0");
            peerGroup.setMaxConnections(16);
            peerGroup.addPeerDiscovery(new DnsDiscovery(netParams));

            peerGroup.start();
            peerGroup.waitForPeers(16).get();

        } catch (BlockStoreException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (HDWalletException e) {
            e.printStackTrace();
        } catch (MnemonicException.MnemonicChecksumException e) {
            e.printStackTrace();
        } catch (MnemonicException.MnemonicWordException e) {
            e.printStackTrace();
        } catch (MnemonicException.MnemonicLengthException e) {
            e.printStackTrace();
        } catch (DecoderException e) {
            e.printStackTrace();
        }
    }

//    private boolean verifyGuid(String guid, String password) {
//        //todo. need to check password
//        if (this.wallet.getGuid().equals(guid)) {
//            return true;
//        }
//        return false;
//    }
//
//    private info.blockchain.wallet.payload.data.HDWallet getHdWallet() {
//        return wallet.getHdWallets().get(0);
//    }
//
//    private boolean archiveAddress(info.blockchain.wallet.payload.data.HDWallet hdWallet, String address, boolean archived) {
//        try {
//            PayloadManager payloadManager = PayloadManager.getInstance();
//
//            List<Account> accounts = hdWallet.getAccounts();
//            for (Account m : accounts) {
//                if (payloadManager.getNextReceiveAddress(m).equals(address)) {
//                    m.setArchived(archived);
//                    return true;
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return false;
//    }
//
//    /**
//     * source from - piuk.blockchain.android.data.payload.PayloadService#createHdWallet
//     *
//     * @param password
//     * @param api_code
//     * @param priv
//     * @param label
//     * @param email
//     * @return
//     */
//    @Override
//    public CreateWalletResponse createWallet(
//            @RequestParam("password") String password,
//            @RequestParam("api_code") String api_code,
//            @RequestParam("priv") String priv,
//            @RequestParam("label") String label,
//            @RequestParam("email") String email
//    ) {
//        PayloadManager payloadManager = PayloadManager.getInstance();
//        String defaultAccountName = "My Bitcoin Wallet";
//        try {
//            wallet = payloadManager.create(defaultAccountName, email, password);
//
//            //wallet data write to json format
////            FileUtils.forceMkdir(new File(HomeConfigurator.getTmpDir()));
////            String json = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(wallet);
////            Files.write(Paths.get(hdWalletFileName), json.getBytes(StandardCharsets.UTF_8), StandardOpenOption.CREATE);
//
//            CreateWalletResponse createWalletResponse = new CreateWalletResponse();
//
//            info.blockchain.wallet.payload.data.HDWallet hdWallet = getHdWallet();
//            int defaultAccountIdx = hdWallet.getDefaultAccountIdx();
//            Account account = hdWallet.getAccount(defaultAccountIdx);
//            String address = payloadManager.getNextReceiveAddress(account);
//
//            createWalletResponse.setGuid(wallet.getGuid());
//            createWalletResponse.setLabel(label);
//            createWalletResponse.setAddress(address);
//
//            return createWalletResponse;
//        } catch (Exception e) {
//            throw new IllegalStateException(e.getMessage());
//        }
//    }
//
//    @Override
//    public WalletBalance getBalance(
//            @PathVariable("guid") String guid,
//            @RequestParam("password") String password,
//            @RequestParam("api_code") String apiCode
//    ) {
//        WalletBalance walletBalance = new WalletBalance();
//
//        if (verifyGuid(guid, password)) {
//            PayloadManager payloadManager = PayloadManager.getInstance();
//
//            try {
//                payloadManager.updateAllBalances();
//
//                BigInteger balance = payloadManager.getWalletBalance();
//                walletBalance.setBalance(balance);
//            } catch (ServerConnectionException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//
//        return walletBalance;
//    }
//
//    @Override
//    public WalletPaymentResponse send(
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
//            @RequestParam("note") String note
//    ) {
//        return null;
//    }
//
//    @Override
//    public WalletAddressList listAddresses(
//            @PathVariable("guid") String guid,
//            @RequestParam("password") String password,
//            @RequestParam("api_code") String apiCode
//    ) {
//        //todo. for test. fix or remove later
//        WalletAddressList walletAddressList = new WalletAddressList();
//
//        if (verifyGuid(guid, password)) {
//            PayloadManager payloadManager = PayloadManager.getInstance();
//
//            ArrayList<WalletAddress> addressList = new ArrayList<WalletAddress>();
//
//            //get default created HdWallet
//            info.blockchain.wallet.payload.data.HDWallet hdWallet = getHdWallet();
//
//            List<Account> list = hdWallet.getAccounts();
//            for (Account m : list) {
//                try {
//                    WalletAddress walletAddress = new WalletAddress();
//                    walletAddress.setAddress(payloadManager.getNextReceiveAddress(m));
//                    walletAddress.setLabel(m.getLabel());
//                    walletAddress.setBalance(BigInteger.valueOf(0));        //?
//                    walletAddress.setTotalReceived(BigInteger.valueOf(0));  //?
//                    addressList.add(walletAddress);
//                } catch (HDWalletException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            walletAddressList.setAddressList(addressList);
//        }
//
//        return walletAddressList;
//    }
//
//    @Override
//    public WalletAddress getAddress(
//            @PathVariable("guid") String guid,
//            @RequestParam("password") String password,
//            @RequestParam("api_code") String apiCode,
//            @RequestParam("address") String address
//    ) {
//        //todo. for test. fix or remove later
//        WalletAddress walletAddress = new WalletAddress();
//
//        if (verifyGuid(guid, password)) {
//            PayloadManager payloadManager = PayloadManager.getInstance();
//
//            //need to find out address is in my accounts in hdwallets
//            info.blockchain.wallet.payload.data.HDWallet hdWallet = getHdWallet();
//
//            List<Account> list = hdWallet.getAccounts();
//            for (Account m : list) {
//                try {
//                    if (payloadManager.getNextReceiveAddress(m).equals(address)) {
//                        walletAddress.setAddress(address);
//                        walletAddress.setLabel(m.getLabel());
//                        walletAddress.setBalance(BigInteger.valueOf(0));        //?
//                        walletAddress.setTotalReceived(BigInteger.valueOf(0));  //?
//
//                        break;
//                    }
//                } catch (HDWalletException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//
//        return walletAddress;
//    }
//
//    @Override
//    public WalletAddress newAddress(
//            @PathVariable("guid") String guid,
//            @RequestParam("password") String password,
//            @RequestParam(value = "second_password", required = false) String secondPassword,
//            @RequestParam("api_code") String apiCode,
//            @RequestParam("label") String label
//    ) {
//        WalletAddress newAddress = new WalletAddress();
//
//        if (verifyGuid(guid, password)) {
//            try {
//                PayloadManager payloadManager = PayloadManager.getInstance();
//
//                //if wallet is double encrypted then secondPassword is valid
//                //this case wallet is not double encrypted so secondPassword is null
//                Account newAccount = payloadManager.addAccount(label, secondPassword);
//                newAddress.setLabel(newAccount.getLabel());
//                newAddress.setAddress(payloadManager.getNextReceiveAddress(newAccount));
//                newAddress.setBalance(new BigInteger("0"));         //?
//                newAddress.setTotalReceived(new BigInteger("0"));   //?
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//
//        return newAddress;
//    }
//
//    @Override
//    public WalletAddressArchiveResponse archiveAddress(
//            @PathVariable("guid") String guid,
//            @RequestParam("password") String password,
//            @RequestParam("api_code") String apiCode,
//            @RequestParam("address") String address
//    ) {
//        //todo. for test. fix or remove later
//        WalletAddressArchiveResponse archiveResponse = new WalletAddressArchiveResponse();
//
//        if (verifyGuid(guid, password)) {
//            info.blockchain.wallet.payload.data.HDWallet hdWallet = getHdWallet();
//
//            if (archiveAddress(hdWallet, address, true)) {
//                archiveResponse.setArchived(address);   //?
//            }
//        }
//
//        return archiveResponse;
//    }
//
//    @Override
//    public WalletAddressUnarchiveResponse unarchiveAddress(
//            @PathVariable("guid") String guid,
//            @RequestParam("password") String password,
//            @RequestParam("api_code") String apiCode,
//            @RequestParam("address") String address
//    ) {
//        //todo. for test. fix or remove later
//        WalletAddressUnarchiveResponse unArchiveResponse = new WalletAddressUnarchiveResponse();
//
//        if (verifyGuid(guid, password)) {
//            info.blockchain.wallet.payload.data.HDWallet hdWallet = getHdWallet();
//
//            if (archiveAddress(hdWallet, address, false)) {
//                unArchiveResponse.setActive(address);   //?
//            }
//        }
//
//        return unArchiveResponse;
//    }

    @Override
    public SendTXResult send(
            @RequestParam("hashtx") String hashtx,
            @RequestParam("api_code") String api_code,
            HttpSession session) {
        SendTXResult result = new SendTXResult();
        result.setHashtx(hashtx);
        result.setError("ok");

        byte[] payloadBytes = Hex.decode(hashtx);
        Transaction tx = new Transaction(TestNet3Params.get(), payloadBytes);

        try {
            peerGroup.broadcastTransaction(tx).broadcast().get();
            result.setHashtx(Hex.toHexString(tx.bitcoinSerialize()));
        } catch (ExecutionException e) {
            logger.error(e.getMessage());
            result.setError(e.getMessage());

        } catch (InterruptedException e) {
            logger.error(e.getMessage());
            result.setError(e.getMessage());
        }

        return result;
    }

    @Override
    public List<String> getSharingDataList(
            @RequestParam("index") int index,
            @RequestParam("api_code") String api_code,
            HttpSession session) {
        List<String> result = service.getSharingDataList(index);
        return result;
    }

    @Override
    public SecretSharingVO getSharingDataOne(@RequestParam("index") int index,
                                      @RequestParam("label") String label,
                                      @RequestParam("api_code") String api_code,
                                      HttpSession session) {
        SecretSharingVO result = new SecretSharingVO();

        SecretSharingVO shartingData = service.getSharingDataOne(index, label);
        if(shartingData != null) {
            result = shartingData;
        }

        return result;
    }

    @Override
    public SecretSharingVO getSharingDataTwo(@RequestParam("index") int index,
                                      @RequestParam("label") String label,
                                      @RequestParam("api_code") String api_code,
                                      HttpSession session) {
        SecretSharingVO result = new SecretSharingVO();

        SecretSharingVO shartingData = service.getSharingDataTwo(index, label);
        if(shartingData != null) {
            result = shartingData;
        }

        return result;
    }

    @Override
    public SecretSharingResult backupSharingDataOne(@RequestParam("index") int index,
                                             @RequestParam("label") String label,
                                             @RequestParam("shareddata") String shareddata,
                                             @RequestParam("api_code") String api_code,
                                             HttpSession session) {
        SecretSharingResult result = new SecretSharingResult();
        try {
            service.backupSharingDataOne(index, label, shareddata);
            result.setResult("ok");
        } catch (Exception e) {
            result.setResult("fail");
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public SecretSharingResult backupSharingDataTwo(@RequestParam("index") int index,
                                             @RequestParam("label") String label,
                                             @RequestParam("shareddata") String shareddata,
                                             @RequestParam("api_code") String api_code,
                                             HttpSession session) {
        SecretSharingResult result = new SecretSharingResult();
        try {
            service.backupSharingDataTwo(index, label, shareddata);
            result.setResult("ok");
        } catch (Exception e) {
            result.setResult("fail");
            e.printStackTrace();
        }
        return result;
    }

    //region support for integration test
    @Override
    public SendTXResult coinFromFaucet(
            @RequestParam("address") String address,
            @RequestParam("api_code") String api_code,
            HttpSession session) {
        SendTXResult result = new SendTXResult();

        try {
            AccountData accountData = faucet.getAccount(0);

            String toAddress = address;
            Long sendSatoshi = 1000000L;
            ActiveAddress changeAddress = explorerController.getActiveChangeAddress(accountData.getXpub(), api_code, session);

            //get faucet balance
            List<UTXO> utxos = explorerController.getBalanceEx(accountData.getXpub(), api_code, session);

            //check balance compare to send amount
            BigInteger balance = BigInteger.ZERO;
            for(UTXO i : utxos) {
                balance = balance.add(i.getValue());
            }

            TXBuilder txBuilder = new TXBuilder(explorerController);

            String hashtx = txBuilder.makeTx(accountData.getXpriv(), accountData.getXpub(), toAddress, changeAddress.getAddress(), sendSatoshi, utxos);

            byte[] payloadBytes = Hex.decode(hashtx);
            Transaction tx = new Transaction(TestNet3Params.get(), payloadBytes);
            //tx = peerGroup.broadcastTransaction(tx).broadcast().get();
            //result.setHashtx(Hex.toHexString(tx.bitcoinSerialize()));
            peerGroup.broadcastTransaction(tx).broadcast();
            result.setHashtx(hashtx);
        }
        catch (Exception e) {
            result.setError(e.getMessage());
            logger.error(e.getMessage());
        }

        return result;
    }

    @Override
    public ActiveAddress addressFaucet(
            @RequestParam("api_code") String api_code,
            HttpSession session) {
        ActiveAddress addressFaucet = new ActiveAddress();

        try {
            AccountData accountData = faucet.getAccount(0);
            ActiveAddress changeAddress = explorerController.getActiveReceiveAddress(accountData.getXpub(), api_code, session);
            addressFaucet.setAddress(changeAddress.getAddress());
        }
        catch (Exception e) {
            logger.error(e.getMessage());
        }

        return addressFaucet;
    }

    //endregion
}
