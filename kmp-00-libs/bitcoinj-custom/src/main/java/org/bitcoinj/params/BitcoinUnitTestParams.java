/*
 * Decompiled with CFR 0_132.
 */
package org.bitcoinj.params;

import java.math.BigInteger;
import org.bitcoinj.params.AbstractNetParams;

public class BitcoinUnitTestParams
extends AbstractNetParams {
    public static final int UNITNET_MAJORITY_WINDOW = 8;
    public static final int TESTNET_MAJORITY_REJECT_BLOCK_OUTDATED = 6;
    public static final int TESTNET_MAJORITY_ENFORCE_BLOCK_UPGRADE = 4;
    private static BitcoinUnitTestParams instance;

    public BitcoinUnitTestParams() {
        this.id = "org.bitcoinj.unittest";
        this.packetMagic = 185665799L;
        this.addressHeader = 111;
        this.p2shHeader = 196;
        this.acceptableAddressCodes = new int[]{this.addressHeader, this.p2shHeader};
        this.maxTarget = new BigInteger("00ffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff", 16);
        this.dumpedPrivateKeyHeader = 239;
        this.spendableCoinbaseDepth = 5;
        this.bip32HeaderPub = 70617039;
        this.bip32HeaderPriv = 70615956;
        this.majorityEnforceBlockUpgrade = 3;
        this.majorityRejectBlockOutdated = 4;
        this.majorityWindow = 7;
    }

    public static synchronized BitcoinUnitTestParams get() {
        if (instance == null) {
            instance = new BitcoinUnitTestParams();
        }
        return instance;
    }

    @Override
    public String getPaymentProtocolId() {
        return "unittest";
    }
}

