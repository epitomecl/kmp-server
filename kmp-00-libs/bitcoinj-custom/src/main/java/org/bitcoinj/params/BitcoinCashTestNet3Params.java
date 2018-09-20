/*
 * Decompiled with CFR 0_132.
 * 
 * Could not load the following classes:
 *  com.google.common.io.BaseEncoding
 */
package org.bitcoinj.params;

import com.google.common.io.BaseEncoding;
import java.math.BigInteger;
import java.util.Date;
import org.bitcoinj.core.Utils;
import org.bitcoinj.params.AbstractNetParams;

public class BitcoinCashTestNet3Params
extends AbstractNetParams {
    private static BitcoinCashTestNet3Params instance;
    private static final Date testnetDiffDate;

    public BitcoinCashTestNet3Params() {
        this.id = "org.bitcoincash.test";
        this.packetMagic = 4108710900L;
        this.maxTarget = Utils.decodeCompactBits(486604799L);
        this.addressHeader = 111;
        this.p2shHeader = 196;
        this.acceptableAddressCodes = new int[]{this.addressHeader, this.p2shHeader};
        this.dumpedPrivateKeyHeader = 239;
        this.spendableCoinbaseDepth = 100;
        this.alertSigningKey = Utils.HEX.decode((CharSequence)"04302390343f91cc401d56d68b123028bf52e5fca1939df127f63c6467cdf9c8e2c14b61104cf817d0b780da337893ecc4aaff1309e536162dabbdb45200ca2b0a");
        this.uriScheme = "bitcoincash";
        this.bip32HeaderPub = 70617039;
        this.bip32HeaderPriv = 70615956;
        this.bech32Prefix = "bchtest";
        this.bech32Separator = (byte)58;
        this.majorityEnforceBlockUpgrade = 51;
        this.majorityRejectBlockOutdated = 75;
        this.majorityWindow = 100;
    }

    public static synchronized BitcoinCashTestNet3Params get() {
        if (instance == null) {
            instance = new BitcoinCashTestNet3Params();
        }
        return instance;
    }

    @Override
    public String getPaymentProtocolId() {
        return "test";
    }

    static {
        testnetDiffDate = new Date(1329264000000L);
    }
}

