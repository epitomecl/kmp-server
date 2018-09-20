/*
 * Decompiled with CFR 0_132.
 */
package org.bitcoinj.params;

import java.math.BigInteger;
import org.bitcoinj.core.Utils;
import org.bitcoinj.params.AbstractNetParams;

public class BitcoinTestNet2Params
extends AbstractNetParams {
    public static final int TESTNET_MAJORITY_WINDOW = 100;
    public static final int TESTNET_MAJORITY_REJECT_BLOCK_OUTDATED = 75;
    public static final int TESTNET_MAJORITY_ENFORCE_BLOCK_UPGRADE = 51;
    private static BitcoinTestNet2Params instance;

    public BitcoinTestNet2Params() {
        this.init();
    }

    private void init() {
        this.id = "org.bitcoin.test";
        this.packetMagic = 4206867930L;
        this.addressHeader = 111;
        this.p2shHeader = 196;
        this.acceptableAddressCodes = new int[]{this.addressHeader, this.p2shHeader};
        this.maxTarget = Utils.decodeCompactBits(487587839L);
        this.dumpedPrivateKeyHeader = 239;
        this.spendableCoinbaseDepth = 100;
        this.uriScheme = "bitcoin";
        this.bip32HeaderPub = 70617039;
        this.bip32HeaderPriv = 70615956;
        this.bech32Prefix = "tb";
        this.bech32Separator = (byte)49;
        this.majorityEnforceBlockUpgrade = 51;
        this.majorityRejectBlockOutdated = 75;
        this.majorityWindow = 100;
    }

    public static synchronized BitcoinTestNet2Params get() {
        if (instance == null) {
            instance = new BitcoinTestNet2Params();
        }
        return instance;
    }

    @Override
    public String getPaymentProtocolId() {
        return null;
    }
}

