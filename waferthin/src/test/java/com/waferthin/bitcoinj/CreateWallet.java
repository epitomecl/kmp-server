package com.waferthin.bitcoinj;

import com.epitomecl.kmp.cc.common.HomeConfigurator;
import org.bitcoinj.core.ECKey;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.wallet.KeyChain;
import org.bitcoinj.wallet.Wallet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.lang.invoke.MethodHandles;

public class CreateWallet {
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public static void main(String[] args) {
        HomeConfigurator.changeLogConfiguration();

        // work with testnet
        final NetworkParameters netParams = NetworkParameters.testNet();

        // Try to read the wallet from storage, create a new one if not possible.
        Wallet wallet = null;
        final File walletFile = new File("test.wallet");

        try {
            wallet = new Wallet(netParams);

            // 5 times
            for (int i = 0; i < 5; i++) {

                // create a key and add it to the wallet
                wallet.addKey(new ECKey());
            }

            // save wallet contents to disk
            wallet.saveToFile(walletFile);

        } catch (IOException e) {
            logger.info("Unable to create wallet file.");
        }

        // fetch the first key in the wallet directly from the keychain ArrayList
        ECKey firstKey = wallet.getActiveKeyChain().getKey(KeyChain.KeyPurpose.AUTHENTICATION);

        // output key
        logger.info("First key in the wallet:\n" + firstKey);

        // and here is the whole wallet
        logger.info("Complete content of the wallet:\n" + wallet);

        // we can use the hash of the public key
        // to check whether the key pair is in this wallet
        if (wallet.isPubKeyHashMine(firstKey.getPubKeyHash())) {
            logger.info("Yep, that's my key.");
        } else {
            logger.info("Nope, that key didn't come from this wallet.");
        }
    }
}