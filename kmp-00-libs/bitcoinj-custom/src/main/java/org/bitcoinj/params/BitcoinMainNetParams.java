/*
 * Decompiled with CFR 0_132.
 */
package org.bitcoinj.params;

import java.math.BigInteger;
import org.bitcoinj.core.Utils;
import org.bitcoinj.params.AbstractNetParams;

public class BitcoinMainNetParams
extends AbstractNetParams {
    public static final int MAINNET_MAJORITY_WINDOW = 1000;
    public static final int MAINNET_MAJORITY_REJECT_BLOCK_OUTDATED = 950;
    public static final int MAINNET_MAJORITY_ENFORCE_BLOCK_UPGRADE = 750;
    public static final String URI_SCHEME = "bitcoin";
    private static BitcoinMainNetParams instance;

    public BitcoinMainNetParams() {
        this.maxTarget = Utils.decodeCompactBits(486604799L);
        this.dumpedPrivateKeyHeader = 128;
        this.addressHeader = 0;
        this.p2shHeader = 5;
        this.acceptableAddressCodes = new int[]{this.addressHeader, this.p2shHeader};
        this.packetMagic = 4190024921L;
        this.uriScheme = URI_SCHEME;
        this.bip32HeaderPub = 76067358;
        this.bip32HeaderPriv = 76066276;
        this.bech32Prefix = "bc";
        this.bech32Separator = (byte)49;
        this.majorityEnforceBlockUpgrade = 750;
        this.majorityRejectBlockOutdated = 950;
        this.majorityWindow = 1000;
        this.id = "org.bitcoin.production";
        this.spendableCoinbaseDepth = 100;
    }

    public static synchronized BitcoinMainNetParams get() {
        if (instance == null) {
            instance = new BitcoinMainNetParams();
        }
        return instance;
    }

    @Override
    public String getPaymentProtocolId() {
        return "main";
    }
}

