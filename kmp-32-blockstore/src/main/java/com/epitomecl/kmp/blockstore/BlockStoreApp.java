package com.epitomecl.kmp.blockstore;

import org.bitcoinj.core.*;
import org.bitcoinj.net.discovery.DnsDiscovery;
import org.bitcoinj.store.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ExecutionException;

public class BlockStoreApp {
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public void waitExcute(long time) throws InterruptedException {
        synchronized (this) {
            this.wait(time);
        }
    }

    public static void main(String[] args) {

        BlockStoreApp bs = new BlockStoreApp();

        Properties prop = new Properties();
        try {
            prop.load(BlockStoreApp.class.getClassLoader().getResourceAsStream("secrets.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        String url = prop.getProperty("URL");
        String db = prop.getProperty("DB");
        String user = prop.getProperty("USER");
        String pass = prop.getProperty("PASS");

        String network = "test";                // "test" or "prod"

        //the Bitcoin network to use
        final NetworkParameters netParams;

        //check for production Bitcoin network ...
        if (network.equalsIgnoreCase("prod")) {
            netParams = NetworkParameters.prodNet();
            // ... otherwise use the testnet
        } else {
            netParams = NetworkParameters.testNet();
        }

        //data structure for block chain storage
        FullPrunedBlockStore blockStore = null;
        try {
            blockStore = new KmpPostgresFullPrunedBlockStore(netParams, 100000, url, db, user, pass);
        } catch (BlockStoreException e) {
            e.printStackTrace();
        }

        //declare object to store and understand block chain
        FullPrunedBlockChain chain;

        try {
            //initialize BlockChain object
            chain = new FullPrunedBlockChain(netParams, blockStore);

            System.out.printf("BITCOINJ", "Create PeerGroup");
            PeerGroup peerGroup = new PeerGroup(netParams, chain);
            peerGroup.setUserAgent("PeerMonitor", "1.0");
            peerGroup.setMaxConnections(16);
            peerGroup.addPeerDiscovery(new DnsDiscovery(netParams));

            System.out.printf("BITCOINJ", "Start Asynchronous PeerGroup");

            peerGroup.start();
            peerGroup.waitForPeers(16).get();

            //download all blocks. if db does not have block data, it takes a lot of time.
            peerGroup.downloadBlockChain();

            //after download, holding blockchain network.
            int i = 1;
            while(i > 0){
                bs.waitExcute(480000);
            }

            peerGroup.stop();

            blockStore.close();

        } catch (BlockStoreException e) {
            e.printStackTrace();
        } catch (AddressFormatException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
