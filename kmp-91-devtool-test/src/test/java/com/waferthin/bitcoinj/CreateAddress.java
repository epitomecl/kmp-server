package com.waferthin.bitcoinj;

import com.epitomecl.kmp.core.common.HomeConfigurator;
import org.bitcoinj.core.Address;
import org.bitcoinj.core.ECKey;
import org.bitcoinj.core.NetworkParameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;

public class CreateAddress {
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public static void main(String[] args) throws Exception {
        HomeConfigurator.changeLogConfiguration();

        // use test net by default
        String net = "test";

        if (args.length >= 1 && (args[0].equals("test") || args[0].equals("prod"))) {
            net = args[0];
            logger.info("Using " + net + " network.");
        }

        // create a new EC Key ...
        ECKey key = new ECKey();

        // ... and look at the key pair
        logger.info("We created key:\n" + key);

        // either test or production net are possible
        final NetworkParameters netParams;

        if (net.equals("prod")) {
            netParams = NetworkParameters.prodNet();
        } else {
            netParams = NetworkParameters.testNet();
        }

        // get valid Bitcoin address from public key
        Address addressFromKey = key.toAddress(netParams);

        logger.info("On the " + net + " network, we can use this address:\n" + addressFromKey);
    }
}