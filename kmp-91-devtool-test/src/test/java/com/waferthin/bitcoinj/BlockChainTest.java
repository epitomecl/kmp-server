package com.waferthin.bitcoinj;


import com.epitomecl.kmp.core.common.HomeConfigurator;
import info.blockchain.wallet.bip44.HDChain;
import org.bitcoinj.core.*;
import org.bitcoinj.crypto.DeterministicKey;
import org.bitcoinj.crypto.HDKeyDerivation;
import org.bitcoinj.net.discovery.DnsDiscovery;
import org.bitcoinj.store.BlockStore;
import org.bitcoinj.store.BlockStoreException;
import org.bitcoinj.store.MemoryBlockStore;
import org.bitcoinj.wallet.Wallet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.util.concurrent.ExecutionException;

public class BlockChainTest {
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public static void main(String[] args) {
        HomeConfigurator.changeLogConfiguration();

        // we get the following from the command line ...
        // (this is not secure - needs validation)
        String network = "test";                // "test" or "prod"
        String walletFileName = "test.wallet"; // wallet file name
        String amountToSend = "10";             // milli-BTC. satoshis
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
        BlockStore blockStore = new MemoryBlockStore(netParams);

        // declare object to store and understand block chain
        BlockChain chain;

        // declare wallet
        Wallet wallet;

        try {

            //prefare receive key and address
            String xpriv = "tprv8gXNt4Nsnp9LHW6zWCxV1Gp53iNquhETHhmVzQeKuhJD8aaFJdXcbNyjbfXneX73sVLtMGk3zJyqx9vap4eeLyB32v8wjRfq8J5uU7YzZsJ";
            DeterministicKey btc_key = DeterministicKey.deserializeB58(xpriv, NetworkParameters.testNet());
            DeterministicKey btc_receive_key = HDKeyDerivation.deriveChildKey(btc_key, HDChain.RECEIVE_CHAIN);
            String receive_address = btc_receive_key.toAddress(NetworkParameters.testNet()).toBase58();

            // load wallet from file
            wallet = new Wallet(netParams);//Wallet.loadFromFile(walletFile);
            DumpedPrivateKey dumpedPrivateKey = btc_receive_key.getPrivateKeyEncoded(NetworkParameters.testNet());
            ECKey key = dumpedPrivateKey.getKey();
            wallet.addKey(key);

            // how man milli-Bitcoins(satoshis) to send
            Coin coin = Coin.valueOf(Long.valueOf(amountToSend));

            // initialize BlockChain object
            chain = new BlockChain(netParams, wallet, blockStore);

            //copy code from ==> https://www.programcreek.com/java-api-examples/?code=lvaccaro/BitcoinBlockExplorer/BitcoinBlockExplorer-master/app/src/main/java/com/vaccarostudio/bitcoinblockexplorer/Bitcoin.java
            System.out.printf("BITCOINJ", "Create PeerGroup");
            PeerGroup peerGroup = new PeerGroup(netParams, chain);
            peerGroup.setUserAgent("PeerMonitor", "1.0");
            peerGroup.setMaxConnections(16);
            peerGroup.addPeerDiscovery(new DnsDiscovery(netParams));

            System.out.printf("BITCOINJ", "Start Asynchronous PeerGroup");

            peerGroup.start();
            peerGroup.waitForPeers(4).get();

            peerGroup.downloadBlockChain();

            Coin myBalance = wallet.getBalance();
            logger.info("Coin myBalance: " + myBalance.toFriendlyString());

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
