/*
 * Decompiled with CFR 0_132.
 * 
 * Could not load the following classes:
 *  com.google.common.io.BaseEncoding
 */
package org.bitcoinj.params;

import com.google.common.io.BaseEncoding;
import java.math.BigInteger;
import org.bitcoinj.core.Utils;
import org.bitcoinj.params.AbstractNetParams;

public class BitcoinTestNet3Params
extends AbstractNetParams {
    private static BitcoinTestNet3Params instance;

    public BitcoinTestNet3Params() {
        this.id = "org.bitcoin.test";
        this.packetMagic = 185665799L;
        this.maxTarget = Utils.decodeCompactBits(486604799L);
        this.addressHeader = 111;
        this.p2shHeader = 196;
        this.acceptableAddressCodes = new int[]{this.addressHeader, this.p2shHeader};
        this.dumpedPrivateKeyHeader = 239;
        this.spendableCoinbaseDepth = 100;
        this.alertSigningKey = Utils.HEX.decode((CharSequence)"04302390343f91cc401d56d68b123028bf52e5fca1939df127f63c6467cdf9c8e2c14b61104cf817d0b780da337893ecc4aaff1309e536162dabbdb45200ca2b0a");
        this.uriScheme = "bitcoin";
        this.bip32HeaderPub = 70617039;
        this.bip32HeaderPriv = 70615956;
        this.bech32Prefix = "tb";
        this.bech32Separator = (byte)49;
        this.majorityEnforceBlockUpgrade = 51;
        this.majorityRejectBlockOutdated = 75;
        this.majorityWindow = 100;
    }

    public static synchronized BitcoinTestNet3Params get() {
        if (instance == null) {
            instance = new BitcoinTestNet3Params();
        }
        return instance;
    }

    @Override
    public String getPaymentProtocolId() {
        return "test";
    }
}

