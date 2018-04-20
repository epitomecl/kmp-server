package com.waferthin.bitcoinj;

import com.epitomecl.kmp.cc.common.HomeConfigurator;
import org.bitcoinj.core.*;
import org.bitcoinj.net.discovery.DnsDiscovery;
import org.bitcoinj.store.BlockStore;
import org.bitcoinj.store.BlockStoreException;
import org.bitcoinj.store.MemoryBlockStore;
import org.bitcoinj.wallet.UnreadableWalletException;
import org.bitcoinj.wallet.Wallet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutionException;

public class SendCoins {
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public static void main(String[] args) {
        HomeConfigurator.changeLogConfiguration();

        //if (args.length != 4) {
        //    logger.info("Usage: java SendCoins prod|test wallet amount recipient");
        //    System.exit(1);
        //}

        // we get the following from the command line ...
        // (this is not secure - needs validation)
        String network = "test";//args[0];  // "test" or "prod"
        String walletFileName = "test.wallet";//args[1];  // wallet file name
        String amountToSend = "10";//args[2];  // milli-BTC. satoshis
        String recipient = "mrY35stqZEtUoym2UQpYsHowNHc3Krzudr";//args[3];  // Bitcoin address(testnet)

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

            // wallet file that contains Bitcoins we can send
            final File walletFile = new File(walletFileName);

            // load wallet from file
            wallet = Wallet.loadFromFile(walletFile);

            // how man milli-Bitcoins(satoshis) to send
            Coin coin = Coin.valueOf(Long.valueOf(amountToSend));

            // initialize BlockChain object
            chain = new BlockChain(netParams, wallet, blockStore);

            //copy code from ==> https://www.programcreek.com/java-api-examples/?code=lvaccaro/BitcoinBlockExplorer/BitcoinBlockExplorer-master/app/src/main/java/com/vaccarostudio/bitcoinblockexplorer/Bitcoin.java
            System.out.printf("BITCOINJ", "Create PeerGroup");
            PeerGroup peerGroup = new PeerGroup(netParams, chain);
            peerGroup.setUserAgent("PeerMonitor", "1.0");
            peerGroup.setMaxConnections(8);
            peerGroup.addPeerDiscovery(new DnsDiscovery(netParams));

            System.out.printf("BITCOINJ", "Start Asynchronous PeerGroup");
            peerGroup.start();

            peerGroup.waitForPeers(4).get();

            // recipient address provided by official Bitcoin client
            Address recipientAddress = new Address(netParams, recipient);

            //reference from => https://bitcoinj.github.io/working-with-the-wallet
            // tell peer to send amountToSend to recipientAddress. send test coin amount
            Wallet.SendResult sendTxn = wallet.sendCoins(peerGroup, recipientAddress, coin);

            // null means we didn't have enough Bitcoins in our wallet for the transaction
            if (sendTxn == null) {
                logger.info("Cannot send requested amount of " + coin.toFriendlyString()
                        + " BTC; wallet only contains " + wallet.getBalance().toFriendlyString() + " BTC.");
            } else {

                // once communicated to the network (via our local peer),
                // the transaction will appear on Bitcoin explorer sooner or later
                logger.info(coin.toFriendlyString() + " BTC sent. You can monitor the transaction here:\n"
                        + "http://blockexplorer.com/tx/" + sendTxn.tx.getHashAsString());
            }

            // save wallet with new transaction(s)
            wallet.saveToFile(walletFile);

            // handle the various exceptions; this needs more work
        } catch (BlockStoreException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (AddressFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InsufficientMoneyException e) {
            e.printStackTrace();
        } catch (UnreadableWalletException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}