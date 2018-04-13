//package com.waferthin.bitcoinj;
//
//import com.epitomecl.kmp.common.HomeConfigurator;
//import org.bitcoinj.core.*;
//import org.bitcoinj.store.BlockStore;
//import org.bitcoinj.store.BlockStoreException;
//import org.bitcoinj.store.MemoryBlockStore;
//import org.bitcoinj.wallet.Wallet;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.io.File;
//import java.io.IOException;
//import java.lang.invoke.MethodHandles;
//import java.math.BigInteger;
//import java.net.InetAddress;
//import java.net.UnknownHostException;
//
//public class SendCoins {
//    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
//
//    public static void main(String[] args) {
//        HomeConfigurator.changeLogConfiguration();
//
//        if (args.length != 4) {
//            logger.info("Usage: java SendCoins prod|test wallet amount recipient");
//            System.exit(1);
//        }
//
//        // we get the following from the command line ...
//        // (this is not secure - needs validation)
//        String network = args[0];  // "test" or "prod"
//        String walletFileName = args[1];  // wallet file name
//        String amountToSend = args[2];  // milli-BTC
//        String recipient = args[3];  // Bitcoin address
//
//        // the Bitcoin network to use
//        final NetworkParameters netParams;
//
//        // check for production Bitcoin network ...
//        if (network.equalsIgnoreCase("prod")) {
//            netParams = NetworkParameters.prodNet();
//            // ... otherwise use the testnet
//        } else {
//            netParams = NetworkParameters.testNet();
//        }
//
//        // data structure for block chain storage
//        BlockStore blockStore = new MemoryBlockStore(netParams);
//
//        // declare object to store and understand block chain
//        BlockChain chain;
//
//        // declare wallet
//        Wallet wallet;
//
//        try {
//
//            // wallet file that contains Bitcoins we can send
//            final File walletFile = new File(walletFileName);
//
//            // load wallet from file
//            wallet = Wallet.loadFromFile(walletFile);
//
//            // how man milli-Bitcoins to send
//            BigInteger btcToSend = new BigInteger(amountToSend);
//
//            // initialize BlockChain object
//            chain = new BlockChain(netParams, wallet, blockStore);
//
//            // instantiate Peer object to handle connections
//            final Peer peer = new Peer(netParams, new PeerAddress(InetAddress.getLocalHost()), chain);
//
//            // connect to peer node on localhost
//            peer.connect();
//
//            // recipient address provided by official Bitcoin client
//            Address recipientAddress = new Address(netParams, recipient);
//
//            // tell peer to send amountToSend to recipientAddress
//            Transaction sendTxn = wallet.sendCoins(peer, recipientAddress, btcToSend);
//
//            // null means we didn't have enough Bitcoins in our wallet for the transaction
//            if (sendTxn == null) {
//                logger.info("Cannot send requested amount of " + Utils.bitcoinValueToFriendlyString(btcToSend)
//                        + " BTC; wallet only contains " + Utils.bitcoinValueToFriendlyString(wallet.getBalance()) + " BTC.");
//            } else {
//
//                // once communicated to the network (via our local peer),
//                // the transaction will appear on Bitcoin explorer sooner or later
//                logger.info(Utils.bitcoinValueToFriendlyString(btcToSend) + " BTC sent. You can monitor the transaction here:\n"
//                        + "http://blockexplorer.com/tx/" + sendTxn.getHashAsString());
//            }
//
//            // save wallet with new transaction(s)
//            wallet.saveToFile(walletFile);
//
//            // handle the various exceptions; this needs more work
//        } catch (BlockStoreException e) {
//            e.printStackTrace();
//        } catch (UnknownHostException e) {
//            e.printStackTrace();
//        } catch (PeerException e) {
//            e.printStackTrace();
//        } catch (AddressFormatException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//}