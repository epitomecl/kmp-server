/*
 * Decompiled with CFR 0_132.
 */
package org.bitcoinj.params;

import java.math.BigInteger;
import org.bitcoinj.params.BitcoinTestNet2Params;

public class BitcoinRegBitcoinTestParams
extends BitcoinTestNet2Params {
    private static final BigInteger MAX_TARGET = new BigInteger("7fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff", 16);
    private static BitcoinRegBitcoinTestParams instance;

    public BitcoinRegBitcoinTestParams() {
        this.maxTarget = MAX_TARGET;
        this.id = "org.bitcoin.regtest";
        this.majorityEnforceBlockUpgrade = 750;
        this.majorityRejectBlockOutdated = 950;
        this.majorityWindow = 1000;
    }

    @Override
    public boolean allowEmptyPeerChain() {
        return true;
    }

    public static synchronized BitcoinRegBitcoinTestParams get() {
        if (instance == null) {
            instance = new BitcoinRegBitcoinTestParams();
        }
        return instance;
    }

    @Override
    public String getPaymentProtocolId() {
        return "regtest";
    }
}

