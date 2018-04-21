package com.waferthin.bitcoinj;

import com.epitomecl.kmp.cc.common.HomeConfigurator;
import org.bitcoinj.core.Address;
import org.bitcoinj.core.Coin;
import org.bitcoinj.core.ECKey;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.wallet.DeterministicSeed;
import org.bitcoinj.wallet.Wallet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.lang.invoke.MethodHandles;

public class GetinfoWallet {
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public static void main(String[] args) {
        HomeConfigurator.changeLogConfiguration();

        final NetworkParameters netParams = NetworkParameters.testNet();

        Wallet wallet = null;
        final File walletFile = new File("test.wallet");

        try{
            wallet = Wallet.loadFromFile(walletFile, null);

            /* test 1 : check equals if convert eckey to address */
            Address address = wallet.currentReceiveAddress();
            ECKey ecKey = wallet.currentReceiveKey();
            Address fAddress = wallet.freshReceiveAddress();

            logger.info("result:"+ecKey.toAddress(wallet.getParams()).equals(address));
            logger.info("result:"+!fAddress.equals(address));

            /* test 2 : create seed word */
            DeterministicSeed seed = wallet.getKeyChainSeed();
            logger.info("Seed word : " + seed.getMnemonicCode());
            logger.info("Seed created_at : " + seed.getCreationTimeSeconds());

            /* test 3 : restore wallet via seedcode */
            String seedCode = "Epitomecl willpark BB Gene Hun john Maru Orwell Sharpe Anthony";
            long creationTime = 1444444444L;
            DeterministicSeed newSeed = new DeterministicSeed(seedCode, null, "", creationTime);
            Wallet restoredWallet = Wallet.fromSeed(netParams, seed);
            logger.info("Balance : "+ restoredWallet.getBalance());

        }catch (Exception e){
            e.printStackTrace();
        }


    }
}
