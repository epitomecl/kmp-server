package test;

import com.epitomecl.kmp.common.HomeConfigurator;
import com.epitomecl.kmp.main.KmpApp;
import com.epitomecl.kmp.wallet.CryptoType;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import info.blockchain.wallet.bip44.HDAccount;
import info.blockchain.wallet.bip44.HDWallet;
import info.blockchain.wallet.bip44.HDWalletFactory;
import info.blockchain.wallet.ethereum.EthereumWallet;
import info.blockchain.wallet.exceptions.HDWalletException;
import info.blockchain.wallet.payload.data.Account;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.io.FileUtils;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.crypto.MnemonicException;
import org.bitcoinj.params.BitcoinCashMainNetParams;
import org.bitcoinj.params.BitcoinMainNetParams;
import org.junit.*;
import org.junit.runners.MethodSorters;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class WalletTest {

    class walletkey {
        String seed;
        String xpriv;
        String xpub;
    }

    private static KmpApp kmpApp;

    private static final int DEFAULT_MNEMONIC_LENGTH = 12;
    private static final int DEFAULT_NEW_WALLET_SIZE = 1;
    private static final String DEFAULT_PASSPHRASE = "";

    private static final String defaultAccountName = "My wallet";

    private final String hdWalletBtcFileName = HomeConfigurator.getTmpDir() + "/hdWalletBtc.json";
    private final String hdWalletBchFileName = HomeConfigurator.getTmpDir() + "/hdWalletBch.json";
    private final String hdWalletEthFileName = HomeConfigurator.getTmpDir() + "/hdWalletEth.json";

    private static walletkey keyBtc;
    private static walletkey keyBch;
    private static walletkey keyEth;

    @BeforeClass
    public static void beforeClass() {
        kmpApp = new KmpApp();
        kmpApp.start(new String[0]);
    }

    @AfterClass
    public static void afterClass() {
        kmpApp.stop();
    }

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
        CryptoType cryptoType = CryptoType.ETHEREUM;
        HDWallet hdWallet_ethereum = create(cryptoType);
        String label = "";
        getInfo(label, cryptoType, hdWallet_ethereum);

        cryptoType = CryptoType.BITCOIN;
        HDWallet hdWallet_bitcoin = create(CryptoType.BITCOIN);
        getInfo(label, cryptoType, hdWallet_bitcoin);

        cryptoType = CryptoType.BITCOIN_CASH;
        HDWallet hdWallet_bitcoin_cash = create(CryptoType.BITCOIN_CASH);
        getInfo(label, cryptoType, hdWallet_bitcoin_cash);
    }

    private NetworkParameters getParams(CryptoType cryptoType) {
        NetworkParameters param = null;
        switch (cryptoType) {
            case ETHEREUM:
                //Create etherium wallet code from <== EthereumWalletTest.java
                param = BitcoinMainNetParams.get();
                break;
            case BITCOIN:
                param = BitcoinMainNetParams.get();
                break;
            case BITCOIN_CASH:
                param = BitcoinCashMainNetParams.get();
                break;
        }

        return param;
    }

    private HDWallet create(CryptoType cryptoType) {
        NetworkParameters param = getParams(cryptoType);

        HDWallet wallet = null;
        try {
            wallet = HDWalletFactory
                    .createWallet(param, HDWalletFactory.Language.US,
                            DEFAULT_MNEMONIC_LENGTH, DEFAULT_PASSPHRASE, DEFAULT_NEW_WALLET_SIZE);
        } catch (IOException | MnemonicException.MnemonicLengthException e) {
            e.printStackTrace();
        }

        return wallet;
    }

    public void getInfo(String label, CryptoType coinType, HDWallet wallet) {
        String receiveAddr = "";
        String changeAddr = "";
        switch (coinType) {
            case BITCOIN:
            case BITCOIN_CASH:
                receiveAddr = wallet.getAccounts().get(0).getReceive().getAddressAt(0).getAddressBase58();
                changeAddr = wallet.getAccounts().get(0).getChange().getAddressAt(0).getAddressBase58();

                //address length check
                Assert.assertEquals(receiveAddr.length(), 34);
                Assert.assertEquals(changeAddr.length(), 34);
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

    @Test
    public void test_02_backup() {
        CryptoType cryptoType = CryptoType.ETHEREUM;
        HDWallet hdWallet_ethereum = create(cryptoType);
        doBackup(cryptoType, hdWallet_ethereum, hdWalletEthFileName);

        cryptoType = CryptoType.BITCOIN;
        HDWallet hdWallet_bitcoin = create(CryptoType.BITCOIN);
        doBackup(cryptoType, hdWallet_bitcoin, hdWalletBtcFileName);

        cryptoType = CryptoType.BITCOIN_CASH;
        HDWallet hdWallet_bitcoin_cash = create(CryptoType.BITCOIN_CASH);
        doBackup(cryptoType, hdWallet_bitcoin_cash, hdWalletBchFileName);
    }

    private void doBackup(CryptoType cryptoType, HDWallet wallet, String filePath) {
        info.blockchain.wallet.payload.data.HDWallet dataHDWallet = new info.blockchain.wallet.payload.data.HDWallet();

        String seedHex = wallet.getSeedHex();

        List<HDAccount> hdAccounts = wallet.getAccounts();
        List<Account> accountBodyList = new ArrayList<>();
        int accountNumber = 1;
        for (int i = 0; i < hdAccounts.size(); i++) {

            String label = defaultAccountName;
            if (accountNumber > 1) {
                label = defaultAccountName + " " + accountNumber;
            }

            String xpriv = wallet.getAccount(0).getXPriv();
            String xpub = wallet.getAccount(0).getXpub();

            Account accountBody = new Account();
            accountBody.setLabel(label);
            accountBody.setXpriv(xpriv);
            accountBody.setXpub(xpub);
            accountBodyList.add(accountBody);

            accountNumber++;

            switch (cryptoType) {
                case ETHEREUM:
                    keyEth = new walletkey();
                    keyEth.seed = seedHex;
                    keyEth.xpriv = xpriv;
                    keyEth.xpub = xpub;
                    break;

                case BITCOIN:
                    keyBtc = new walletkey();
                    keyBtc.seed = seedHex;
                    keyBtc.xpriv = xpriv;
                    keyBtc.xpub = xpub;
                    break;

                case BITCOIN_CASH:
                    keyBch = new walletkey();
                    keyBch.seed = seedHex;
                    keyBch.xpriv = xpriv;
                    keyBch.xpub = xpub;
                    break;
            }
        }

        dataHDWallet.setSeedHex(seedHex);
        dataHDWallet.setDefaultAccountIdx(0);
        dataHDWallet.setMnemonicVerified(false);
        dataHDWallet.setPassphrase(DEFAULT_PASSPHRASE);
        dataHDWallet.setAccounts(accountBodyList);

        try {
            FileUtils.forceMkdir(new File(HomeConfigurator.getTmpDir()));
            String json = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(dataHDWallet);
            Files.write(Paths.get(filePath), json.getBytes(StandardCharsets.UTF_8), StandardOpenOption.CREATE);

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test_03_recovery() {
        CryptoType cryptoType = CryptoType.ETHEREUM;
        HDWallet hdWallet_ethereum = doRecovery(cryptoType, hdWalletEthFileName);

        cryptoType = CryptoType.BITCOIN;
        HDWallet hdWallet_bitcoin = doRecovery(cryptoType, hdWalletBtcFileName);

        cryptoType = CryptoType.BITCOIN_CASH;
        HDWallet hdWallet_bitcoin_cash = doRecovery(cryptoType, hdWalletBchFileName);
    }

    private HDWallet doRecovery(CryptoType cryptoType, String filePath) {
        NetworkParameters param = getParams(cryptoType);
        HDWallet wallet = null;

        try {
            String json = String.join(" ", Files.readAllLines(Paths.get(filePath)));
            info.blockchain.wallet.payload.data.HDWallet dataHDWallet
                    = info.blockchain.wallet.payload.data.HDWallet.fromJson(json);

            //from code <== instantiateBip44Wallet()
            try {
                int walletSize = DEFAULT_NEW_WALLET_SIZE;
                if (dataHDWallet.getAccounts() != null) walletSize = dataHDWallet.getAccounts().size();
                wallet = HDWalletFactory
                        .restoreWallet(param, HDWalletFactory.Language.US,
                                dataHDWallet.getSeedHex(), dataHDWallet.getPassphrase(), walletSize);
            } catch (Exception e) {

                ArrayList<String> xpubList = new ArrayList<>();
                for (Account account : dataHDWallet.getAccounts()) {
                    xpubList.add(account.getXpub());
                }

                wallet = HDWalletFactory
                        .restoreWatchOnlyWallet(param, xpubList);
            }

            String seedHex = wallet.getSeedHex();
            String xpriv = wallet.getAccounts().get(0).getXPriv();
            String xpub = wallet.getAccounts().get(0).getXpub();

            switch (cryptoType) {
                case ETHEREUM:
                    Assert.assertEquals(keyEth.seed, seedHex);
                    Assert.assertEquals(keyEth.xpriv, xpriv);
                    Assert.assertEquals(keyEth.xpub, xpub);
                    break;

                case BITCOIN:
                    Assert.assertEquals(keyBtc.seed, seedHex);
                    Assert.assertEquals(keyBtc.xpriv, xpriv);
                    Assert.assertEquals(keyBtc.xpub, xpub);
                    break;

                case BITCOIN_CASH:
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

        return wallet;
    }
}
