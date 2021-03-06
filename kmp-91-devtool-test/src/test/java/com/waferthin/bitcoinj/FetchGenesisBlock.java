package com.waferthin.bitcoinj;

import com.epitomecl.kmp.core.common.HomeConfigurator;
import org.bitcoinj.core.*;
import org.bitcoinj.net.discovery.DnsDiscovery;
import org.bitcoinj.store.BlockStore;
import org.bitcoinj.store.BlockStoreException;
import org.bitcoinj.store.MemoryBlockStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class FetchGenesisBlock {
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public static void main(String[] args) {
        HomeConfigurator.changeLogConfiguration();

        // work with testnet
        final NetworkParameters netParams = NetworkParameters.testNet();

        // data structure for block chain storage
        BlockStore blockStore = new MemoryBlockStore(netParams);

        // declare object to store and understand block chain
        BlockChain chain;

        try {

            // initialize BlockChain object
            chain = new BlockChain(netParams, blockStore);

            //copy code from ==> https://www.programcreek.com/java-api-examples/?code=lvaccaro/BitcoinBlockExplorer/BitcoinBlockExplorer-master/app/src/main/java/com/vaccarostudio/bitcoinblockexplorer/Bitcoin.java
            System.out.printf("BITCOINJ", "Create PeerGroup");
            PeerGroup peerGroup = new PeerGroup(netParams, chain);
            peerGroup.setUserAgent("PeerMonitor", "1.0");
            peerGroup.setMaxConnections(8);
            peerGroup.addPeerDiscovery(new DnsDiscovery(netParams));

            System.out.printf("BITCOINJ", "Start Asynchronous PeerGroup");
            peerGroup.start();

            peerGroup.waitForPeers(4).get();

            //testnet genesisblock info from
            //https://testnet.blockchain.info/ko/block-height/0
            //hash: 000000000933ea01ad0ee984209779baaec3ced90fa3f408719526f8d77f4943

            // we found the hash of the genesis block on Bitcoin Block Explorer
            //Sha256Hash blockHash = new Sha256Hash("00000007199508e34a9ff81e6ec0c477a4cccff2a4767a8eee39c11db367b008"); //mainnet
            Sha256Hash blockHash = new Sha256Hash("000000000933ea01ad0ee984209779baaec3ced90fa3f408719526f8d77f4943"); //testnet3

            // ask the node to which we're connected for the block
            // and wait for a response
            Future<Block> future = peerGroup.getDownloadPeer().getBlock(blockHash);
            System.out.println("Waiting for node to send us the requested block: " + blockHash);

            // get and use the Block's toString() to output the genesis block
            Block block = future.get();
            System.out.println("Here is the genesis block:\n" + block);

            // we're done; disconnect from the peer node
            peerGroup.stop();

            blockStore.close();

            // handle the various exceptions; this needs more work
        } catch (BlockStoreException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}