package com.waferthin.bitcoinj;

import com.epitomecl.kmp.core.common.HomeConfigurator;
import info.blockchain.wallet.bip44.HDChain;
import org.bitcoinj.core.*;
import org.bitcoinj.crypto.DeterministicKey;
import org.bitcoinj.crypto.HDKeyDerivation;
import org.bitcoinj.net.discovery.DnsDiscovery;
import org.bitcoinj.store.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.lang.invoke.MethodHandles;
import java.math.BigInteger;
import java.util.concurrent.ExecutionException;

public class BlockChainTest2 {
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public static void main(String[] args) {
        HomeConfigurator.changeLogConfiguration();

        // we get the following from the command line ...
        // (this is not secure - needs validation)
        String network = "test";                // "test" or "prod"
        String recipient = "mrY35stqZEtUoym2UQpYsHowNHc3Krzudr";    // Bitcoin address(testnet)

        // the Bitcoin network to use
        final NetworkParameters netParams;

        // check for production Bitcoin network ...
        if (network.equalsIgnoreCase("prod")) {
            netParams = NetworkParameters.prodNet();
            // ... otherwise use the testnet
        } else {
            netParams = NetworkParameters.testNet();
        }

        // data structure for block chain storage
        FullPrunedBlockStore blockStore = null;
        try {
            //blockStore = new MySQLFullPrunedBlockStore(netParams, 100000, "127.0.0.1:3306", "bitcoin_core_testnet", "root", "##free@2018..");

            //blockStore = new PostgresFullPrunedBlockStore(netParams, 100000,"13.125.250.135:5432", "bitcoin_core_testnet", "postgres", "##free@2018..");
            //blockStore = new PostgresFullPrunedBlockStore(netParams, 100000,"localhost:5432", "bitcoin_core_testnet", "postgres", "##free@2018..");
            blockStore = new KmpPostgresFullPrunedBlockStore(netParams, 100000,"localhost:5432", "bitcoin_core_testnet", "postgres", "##free@2018..");
        } catch (BlockStoreException e) {
            e.printStackTrace();
        }

        // declare object to store and understand block chain
        FullPrunedBlockChain chain;

        try {
            //prefare receive key and address
            String xpriv = "tprv8gXNt4Nsnp9LHW6zWCxV1Gp53iNquhETHhmVzQeKuhJD8aaFJdXcbNyjbfXneX73sVLtMGk3zJyqx9vap4eeLyB32v8wjRfq8J5uU7YzZsJ";
            DeterministicKey btc_key = DeterministicKey.deserializeB58(xpriv, NetworkParameters.testNet());
            DeterministicKey btc_receive_key = HDKeyDerivation.deriveChildKey(btc_key, HDChain.RECEIVE_CHAIN);
            String receive_address = "n14GGH3QU4bG66S7ptKpixmLUjbtM4woBY";//btc_receive_key.toAddress(NetworkParameters.testNet()).toBase58();

            // initialize BlockChain object
            chain = new FullPrunedBlockChain(netParams, blockStore);

            //copy code from ==> https://www.programcreek.com/java-api-examples/?code=lvaccaro/BitcoinBlockExplorer/BitcoinBlockExplorer-master/app/src/main/java/com/vaccarostudio/bitcoinblockexplorer/Bitcoin.java
            System.out.printf("BITCOINJ", "Create PeerGroup");
            PeerGroup peerGroup = new PeerGroup(netParams, chain);
            peerGroup.setUserAgent("PeerMonitor", "1.0");
            peerGroup.setMaxConnections(16);
            peerGroup.addPeerDiscovery(new DnsDiscovery(netParams));

            System.out.printf("BITCOINJ", "Start Asynchronous PeerGroup");

            peerGroup.start();
            peerGroup.waitForPeers(16).get();

            peerGroup.downloadBlockChain();

            org.bitcoinj.core.Address address = Address.fromBase58(netParams, receive_address);
            BigInteger balance = ((PostgresFullPrunedBlockStore) blockStore).calculateBalanceForAddress(address);
            logger.info("DB Coin myBalance: " + Coin.valueOf(balance.longValue()).toFriendlyString());

            peerGroup.stop();
            blockStore.close();

            // handle the various exceptions; this needs more work
        } catch (BlockStoreException e) {
            e.printStackTrace();
        } catch (AddressFormatException e) {
            e.printStackTrace();
//        } catch (UnknownHostException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (InsufficientMoneyException e) {
//            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
