package test;

import com.epitomecl.kmp.core.common.HomeConfigurator;
import com.epitomecl.kmp.core.wallet.AccountData;
import com.epitomecl.kmp.core.wallet.CryptoType;
import com.epitomecl.kmp.core.wallet.HDWalletData;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.epitomecl.kmp.core.wallet.bip44.HDWallet;
import com.epitomecl.kmp.core.wallet.bip44.HDWalletFactory;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.io.FileUtils;
import org.bitcoinj.crypto.MnemonicException;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    private static final String faucetWalletBtcFileName = HomeConfigurator.getWalletDir() + "/faucetWalletBtc.json";
    private static final String faucetWalletBchFileName = HomeConfigurator.getWalletDir() + "/faucetWalletBch.json";
    private static final String faucetWalletEthFileName = HomeConfigurator.getWalletDir() + "/faucetWalletEth.json";

    //region private/public static part

    private static Map<CryptoType, HDWalletData> faucetWallets = new HashMap<>();

    private static Map<CryptoType, WalletKey> keys = new HashMap<>();

    @BeforeClass
    public static void beforeClass() {
        loadFaucetWallts();
    }

    private static void loadFaucetWallts() {
        //load faucet wallets
        String json = null;
        try {
            json = String.join("", Files.readAllLines(Paths.get(faucetWalletBtcFileName)));
            faucetWallets.put(CryptoType.BITCOIN_TESTNET, HDWalletData.fromJson(json));

            json = String.join("", Files.readAllLines(Paths.get(faucetWalletBchFileName)));
            faucetWallets.put(CryptoType.BITCOIN_CASH_TESTNET, HDWalletData.fromJson(json));

            json = String.join("", Files.readAllLines(Paths.get(faucetWalletEthFileName)));
            faucetWallets.put(CryptoType.ETHEREUM_TESTNET, HDWalletData.fromJson(json));

        } catch (IOException | MnemonicException.MnemonicWordException | DecoderException | MnemonicException.MnemonicLengthException | MnemonicException.MnemonicChecksumException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
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

            WalletKey key = new WalletKey();
            key.seed = seedHex;
            key.xpriv = xpriv;
            key.xpub = xpub;
            keys.put(cryptoType, key);
        }

        try {
            FileUtils.forceMkdir(new File(HomeConfigurator.getTmpDir()));
            String json = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(walletData);
            Files.write(Paths.get(filePath), json.getBytes(StandardCharsets.UTF_8), StandardOpenOption.CREATE);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private HDWalletData doRecovery(String filePath) {
        HDWalletData walletData = null;
        HDWallet hdWallet = null;

        try {
            String json = String.join("", Files.readAllLines(Paths.get(filePath)));
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

            WalletKey key = keys.get(dataHDWallet.getCryptoType());
            Assert.assertEquals(key.seed, seedHex);
            Assert.assertEquals(key.xpriv, xpriv);
            Assert.assertEquals(key.xpub, xpub);

        } catch (IOException | MnemonicException.MnemonicChecksumException | MnemonicException.MnemonicWordException | MnemonicException.MnemonicLengthException | DecoderException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return walletData;
    }
    //endregion
}
