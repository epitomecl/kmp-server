package test;

import com.epitomecl.kmp.common.HomeConfigurator;
import com.epitomecl.kmp.wallet.AccountData;
import com.epitomecl.kmp.wallet.CryptoType;
import com.epitomecl.kmp.wallet.HDWalletData;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import info.blockchain.wallet.bip44.HDWallet;
import info.blockchain.wallet.bip44.HDWalletFactory;
import info.blockchain.wallet.ethereum.EthereumWallet;
import info.blockchain.wallet.exceptions.HDWalletException;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.io.FileUtils;
import org.bitcoinj.crypto.MnemonicException;
import org.junit.*;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class WalletTest {

    //region private static final part
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private static final class WalletKey {
        String seed;
        String xpriv;
        String xpub;
    }

    private static final int DEFAULT_NEW_WALLET_SIZE = 1;

    private static final String defaultAccountName = "My wallet";

    private static final String hdWalletBtcFileName = HomeConfigurator.getTmpDir() + "/hdWalletBtc.json";
    private static final String hdWalletBchFileName = HomeConfigurator.getTmpDir() + "/hdWalletBch.json";
    private static final String hdWalletEthFileName = HomeConfigurator.getTmpDir() + "/hdWalletEth.json";

    //region private/public static part

    private static WalletKey keyBtc;
    private static WalletKey keyBch;
    private static WalletKey keyEth;

    @BeforeClass
    public static void beforeClass() {
        loadFaucetWallts();
    }

    private static void loadFaucetWallts()
    {
        //todo. load faucet wallets
    }
    //endregion

    //region public part
    /**
     * 1 테스트 준비(사전에 1회 수동 생성후 json으로 저장. 테스트시 로드)
     * 1.1 테스트넷 비트코인 지갑A 생성 / 백업 (최초 1회, 수동)
     * 1.2 테스트용 지갑A에 코인 전송 (수시로, 틈날때)
     * <p>
     * 2. 테스팅 - 자동 (매번 실행)
     * 2.0 지갑A json 으로부터 로드.
     * 2.1 지갑B 생성
     * 2.2 지갑B 백업
     * 2.3 지갑A → 지갑B에 코인 전송 (
     * 2.4 지갑B 밸런스 확인
     * 2.5 지갑B 삭제
     * 2.6 지갑B 복구
     * 2.7 지갑B → 지갑A에 코인 전송
     * 2.8 지갑B 삭제
     */
    public void test() {
        test_01_create();

        test_02_backup();

        test_03_recovery();

//        test_balance();
//
//        test_transfer();
    }

    @Test
    public void test_01_create() {
        CryptoType cryptoType = CryptoType.ETHEREUM_TESTNET;
        HDWalletData hdWallet_ethereum = create(cryptoType);
        String label = "";
        getInfo(label, cryptoType, hdWallet_ethereum.getHDWallet());

        cryptoType = CryptoType.BITCOIN_TESTNET;
        HDWalletData hdWallet_bitcoin = create(cryptoType);
        getInfo(label, cryptoType, hdWallet_bitcoin.getHDWallet());

        cryptoType = CryptoType.BITCOIN_CASH_TESTNET;
        HDWalletData hdWallet_bitcoin_cash = create(cryptoType);
        getInfo(label, cryptoType, hdWallet_bitcoin_cash.getHDWallet());
    }

    @Test
    public void test_02_backup() {
        CryptoType cryptoType = CryptoType.ETHEREUM_TESTNET;
        HDWalletData hdWallet_ethereum = create(cryptoType);
        doBackup(cryptoType, hdWallet_ethereum, hdWalletEthFileName);

        cryptoType = CryptoType.BITCOIN_TESTNET;
        HDWalletData hdWallet_bitcoin = create(cryptoType);
        doBackup(cryptoType, hdWallet_bitcoin, hdWalletBtcFileName);

        cryptoType = CryptoType.BITCOIN_CASH_TESTNET;
        HDWalletData hdWallet_bitcoin_cash = create(cryptoType);
        doBackup(cryptoType, hdWallet_bitcoin_cash, hdWalletBchFileName);
    }

    @Test
    public void test_03_recovery() {
        CryptoType cryptoType = CryptoType.ETHEREUM;
        HDWalletData hdWallet_ethereum = doRecovery(hdWalletEthFileName);

        cryptoType = CryptoType.BITCOIN;
        HDWalletData hdWallet_bitcoin = doRecovery(hdWalletBtcFileName);

        cryptoType = CryptoType.BITCOIN_CASH;
        HDWalletData hdWallet_bitcoin_cash = doRecovery(hdWalletBchFileName);
    }
    //endregion

    //region private part
    private HDWalletData create(CryptoType cryptoType) {
        HDWalletData hdWalletData = null;
        try {
            hdWalletData = new HDWalletData(cryptoType, String.format("My %s wallet", cryptoType.toString()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return hdWalletData;
    }

    private void getInfo(String label, CryptoType coinType, HDWallet wallet) {
        String receiveAddr = "";
        String changeAddr = "";
        switch (coinType) {
            case BITCOIN:
            case BITCOIN_CASH:
                receiveAddr = wallet.getAccounts().get(0).getReceive().getAddressAt(0).getAddressBase58();
                changeAddr = wallet.getAccounts().get(0).getChange().getAddressAt(0).getAddressBase58();

                //address length check
                Assert.assertEquals(receiveAddr, receiveAddr.length(), 34);
                Assert.assertEquals(changeAddr, changeAddr.length(), 34);

                break;
            case ETHEREUM:
                //Create etherium wallet code from <== EthereumWalletTest.java
                EthereumWallet subject = new EthereumWallet(wallet.getMasterKey(), label);

                //etherium wallet has single account and one address
                receiveAddr = subject.getAccount().getAddress();
                Assert.assertEquals(receiveAddr.length(), 42);
                break;
        }
    }

    private void doBackup(CryptoType cryptoType, HDWalletData walletData, String filePath) {

        String seedHex = walletData.getSeedHex();

        List<AccountData> AccountsData = walletData.getAccounts();
        int accountNumber = 1;
        for (int i = 0; i < AccountsData.size(); i++) {

            String label = defaultAccountName;
            if (accountNumber > 1) {
                label = defaultAccountName + " " + accountNumber;
            }

            String xpriv = AccountsData.get(0).getXpriv();
            String xpub = AccountsData.get(0).getXpub();

            accountNumber++;

            switch (cryptoType) {
                case ETHEREUM:
                case ETHEREUM_TESTNET:
                    keyEth = new WalletKey();
                    keyEth.seed = seedHex;
                    keyEth.xpriv = xpriv;
                    keyEth.xpub = xpub;
                    break;

                case BITCOIN:
                case BITCOIN_TESTNET:
                    keyBtc = new WalletKey();
                    keyBtc.seed = seedHex;
                    keyBtc.xpriv = xpriv;
                    keyBtc.xpub = xpub;
                    break;

                case BITCOIN_CASH:
                case BITCOIN_CASH_TESTNET:
                    keyBch = new WalletKey();
                    keyBch.seed = seedHex;
                    keyBch.xpriv = xpriv;
                    keyBch.xpub = xpub;
                    break;
            }
        }

        try {
            FileUtils.forceMkdir(new File(HomeConfigurator.getTmpDir()));
            String json = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(walletData);
            Files.write(Paths.get(filePath), json.getBytes(StandardCharsets.UTF_8), StandardOpenOption.CREATE);

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private HDWalletData doRecovery(String filePath) {

        HDWalletData walletData = null;

        HDWallet hdWallet = null;

        try {
            String json = String.join(" ", Files.readAllLines(Paths.get(filePath)));
            HDWalletData dataHDWallet = HDWalletData.fromJson(json);

            //from code <== instantiateBip44Wallet()
            try {
                int walletSize = DEFAULT_NEW_WALLET_SIZE;
                if (dataHDWallet.getAccounts() != null) walletSize = dataHDWallet.getAccounts().size();
                hdWallet = HDWalletFactory
                        .restoreWallet(dataHDWallet.getNetworkParameters(), HDWalletFactory.Language.US,
                                dataHDWallet.getSeedHex(), dataHDWallet.getPassphrase(), walletSize);
            } catch (Exception e) {

                ArrayList<String> xpubList = new ArrayList<>();
                for (AccountData account : dataHDWallet.getAccounts()) {
                    xpubList.add(account.getXpub());
                }

                hdWallet = HDWalletFactory
                        .restoreWatchOnlyWallet(dataHDWallet.getNetworkParameters(), xpubList);
            }

            String seedHex = hdWallet.getSeedHex();
            String xpriv = hdWallet.getAccounts().get(0).getXPriv();
            String xpub = hdWallet.getAccounts().get(0).getXpub();

            switch (dataHDWallet.getCryptoType()) {
                case ETHEREUM:
                case ETHEREUM_TESTNET:
                    Assert.assertEquals(keyEth.seed, seedHex);
                    Assert.assertEquals(keyEth.xpriv, xpriv);
                    Assert.assertEquals(keyEth.xpub, xpub);
                    break;

                case BITCOIN:
                case BITCOIN_TESTNET:
                    Assert.assertEquals(keyBtc.seed, seedHex);
                    Assert.assertEquals(keyBtc.xpriv, xpriv);
                    Assert.assertEquals(keyBtc.xpub, xpub);
                    break;

                case BITCOIN_CASH:
                case BITCOIN_CASH_TESTNET:
                    Assert.assertEquals(keyBch.seed, seedHex);
                    Assert.assertEquals(keyBch.xpriv, xpriv);
                    Assert.assertEquals(keyBch.xpub, xpub);
                    break;
            }

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

        return walletData;
    }
    //endregion
}
