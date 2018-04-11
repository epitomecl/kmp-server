package test;

import com.epitomecl.kmp.wallet.CryptoType;
import info.blockchain.wallet.bip44.HDWallet;
import info.blockchain.wallet.bip44.HDWalletFactory;
import info.blockchain.wallet.ethereum.EthereumWallet;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.crypto.MnemonicException;
import org.bitcoinj.params.BitcoinCashMainNetParams;
import org.bitcoinj.params.BitcoinMainNetParams;
import org.junit.Test;

import java.io.IOException;

public class CreateWalletTest {
    private static final int DEFAULT_MNEMONIC_LENGTH = 12;
    private static final int DEFAULT_NEW_WALLET_SIZE = 1;
    private static final String DEFAULT_PASSPHRASE = "";

    @Test
    public void test() {
        test_create();

//        test_balance();
//
//        test_transfer();

//        test_backup();
//
//        test_recovery();
    }

    private void test_create() {
        CryptoType cryptoType = CryptoType.ETHEREUM;
        HDWallet hdWallet_ethereum =  create(cryptoType);
        String label = "";
        getInfo(label, cryptoType, hdWallet_ethereum);

        cryptoType = CryptoType.BITCOIN;
        HDWallet hdWallet_bitcoin =  create(CryptoType.BITCOIN);
        getInfo(label, cryptoType, hdWallet_ethereum);

        cryptoType = CryptoType.BITCOIN_CASH;
        HDWallet hdWallet_bitcoin_cash =  create(CryptoType.BITCOIN_CASH);
        getInfo(label, cryptoType, hdWallet_ethereum);
    }

    private HDWallet create(CryptoType coinType) {
        NetworkParameters param = null;
        switch (coinType) {
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
                receiveAddr = wallet.getAccounts().get(0).getReceive().getAddressAt(0).getAddressBase58();
                changeAddr = wallet.getAccounts().get(0).getChange().getAddressAt(0).getAddressBase58();
                break;
            case BITCOIN_CASH:
                receiveAddr = wallet.getAccounts().get(0).getReceive().getAddressAt(0).getAddressBase58();
                changeAddr = wallet.getAccounts().get(0).getChange().getAddressAt(0).getAddressBase58();
                break;
            case ETHEREUM:
                //Create etherium wallet code from <== EthereumWalletTest.java
                EthereumWallet subject = new EthereumWallet(wallet.getMasterKey(), label);

                //etherium wallet has single account and one address
                receiveAddr = subject.getAccount().getAddress();
                changeAddr = subject.getAccount().getAddress();
                break;
        }
    }

}
