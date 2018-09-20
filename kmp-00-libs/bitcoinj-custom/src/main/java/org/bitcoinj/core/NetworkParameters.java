/*
 * Decompiled with CFR 0_132.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Objects
 *  com.google.common.io.BaseEncoding
 *  javax.annotation.Nullable
 */
package org.bitcoinj.core;

import com.google.common.base.Objects;
import com.google.common.io.BaseEncoding;
import java.math.BigInteger;
import java.util.EnumSet;
import javax.annotation.Nullable;
import org.bitcoinj.core.BitcoinSerializer;
import org.bitcoinj.core.Block;
import org.bitcoinj.core.Coin;
import org.bitcoinj.core.MessageSerializer;
import org.bitcoinj.core.Transaction;
import org.bitcoinj.core.Utils;
import org.bitcoinj.params.BitcoinCashMainNetParams;
import org.bitcoinj.params.BitcoinCashTestNet3Params;
import org.bitcoinj.params.BitcoinMainNetParams;
import org.bitcoinj.params.BitcoinRegBitcoinTestParams;
import org.bitcoinj.params.BitcoinTestNet2Params;
import org.bitcoinj.params.BitcoinTestNet3Params;
import org.bitcoinj.params.BitcoinUnitTestParams;
import org.bitcoinj.script.Script;
import org.bitcoinj.utils.MonetaryFormat;
import org.bitcoinj.utils.VersionTally;

public abstract class NetworkParameters {
    public static final byte[] SATOSHI_KEY = Utils.HEX.decode((CharSequence)"04fc9702847840aaf195de8442ebecedf5b095cdbb9bc716bda9110971b28a49e0ead8564ff0db22209e0374782c093bb899692d524e9d6a6956e7c5ecbcd68284");
    public static final String ID_BTC_MAINNET = "org.bitcoin.production";
    public static final String ID_BTC_TESTNET = "org.bitcoin.test";
    public static final String ID_BTC_REGTEST = "org.bitcoin.regtest";
    public static final String ID_BTC_UNITTESTNET = "org.bitcoinj.unittest";
    public static final String ID_BCH_MAINNET = "org.bitcoincash.production";
    public static final String ID_BCH_TESTNET = "org.bitcoincash.test";
    public static final String PAYMENT_PROTOCOL_ID_MAINNET = "main";
    public static final String PAYMENT_PROTOCOL_ID_TESTNET = "test";
    public static final String PAYMENT_PROTOCOL_ID_UNIT_TESTS = "unittest";
    public static final String PAYMENT_PROTOCOL_ID_REGTEST = "regtest";
    protected String uriScheme;
    protected BigInteger maxTarget;
    protected long packetMagic;
    protected int addressHeader;
    protected int p2shHeader;
    protected int dumpedPrivateKeyHeader;
    protected byte[] alertSigningKey = SATOSHI_KEY;
    protected int bip32HeaderPub;
    protected int bip32HeaderPriv;
    protected String bech32Prefix;
    protected byte bech32Separator;
    protected int majorityEnforceBlockUpgrade;
    protected int majorityRejectBlockOutdated;
    protected int majorityWindow;
    protected String id;
    protected int spendableCoinbaseDepth;
    protected int[] acceptableAddressCodes;
    protected transient MessageSerializer defaultSerializer = null;
    public static final int BIP16_ENFORCE_TIME = 1333238400;
    public static final long MAX_COINS = 21000000L;
    public static final Coin MAX_MONEY = Coin.COIN.multiply(21000000L);

    protected NetworkParameters() {
    }

    @Deprecated
    public static NetworkParameters testNet() {
        return BitcoinTestNet3Params.get();
    }

    @Deprecated
    public static NetworkParameters testNet2() {
        return BitcoinTestNet2Params.get();
    }

    @Deprecated
    public static NetworkParameters testNet3() {
        return BitcoinTestNet3Params.get();
    }

    @Deprecated
    public static NetworkParameters prodNet() {
        return BitcoinMainNetParams.get();
    }

    @Deprecated
    public static NetworkParameters unitTests() {
        return BitcoinUnitTestParams.get();
    }

    @Deprecated
    public static NetworkParameters regTests() {
        return BitcoinRegBitcoinTestParams.get();
    }

    public String getId() {
        return this.id;
    }

    public abstract String getPaymentProtocolId();

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        return this.getId().equals(((NetworkParameters)o).getId());
    }

    public int hashCode() {
        return Objects.hashCode((Object[])new Object[]{this.getId()});
    }

    @Nullable
    public static NetworkParameters fromID(String id) {
        if (id.equals("org.bitcoin.production")) {
            return BitcoinMainNetParams.get();
        }
        if (id.equals("org.bitcoin.test")) {
            return BitcoinTestNet3Params.get();
        }
        if (id.equals("org.bitcoinj.unittest")) {
            return BitcoinUnitTestParams.get();
        }
        if (id.equals("org.bitcoin.regtest")) {
            return BitcoinRegBitcoinTestParams.get();
        }
        if (id.equals("org.bitcoincash.production")) {
            return BitcoinCashMainNetParams.get();
        }
        if (id.equals("org.bitcoincash.test")) {
            return BitcoinCashTestNet3Params.get();
        }
        return null;
    }

    @Nullable
    public static NetworkParameters fromPmtProtocolID(String pmtProtocolId) {
        if (pmtProtocolId.equals("main")) {
            return BitcoinMainNetParams.get();
        }
        if (pmtProtocolId.equals("test")) {
            return BitcoinTestNet3Params.get();
        }
        if (pmtProtocolId.equals("unittest")) {
            return BitcoinUnitTestParams.get();
        }
        if (pmtProtocolId.equals("regtest")) {
            return BitcoinRegBitcoinTestParams.get();
        }
        return null;
    }

    public int getSpendableCoinbaseDepth() {
        return this.spendableCoinbaseDepth;
    }

    public long getPacketMagic() {
        return this.packetMagic;
    }

    public int getAddressHeader() {
        return this.addressHeader;
    }

    public int getP2SHHeader() {
        return this.p2shHeader;
    }

    public int getDumpedPrivateKeyHeader() {
        return this.dumpedPrivateKeyHeader;
    }

    public int[] getAcceptableAddressCodes() {
        return this.acceptableAddressCodes;
    }

    public boolean allowEmptyPeerChain() {
        return true;
    }

    public BigInteger getMaxTarget() {
        return this.maxTarget;
    }

    public byte[] getAlertSigningKey() {
        return this.alertSigningKey;
    }

    public int getBip32HeaderPub() {
        return this.bip32HeaderPub;
    }

    public int getBip32HeaderPriv() {
        return this.bip32HeaderPriv;
    }

    public abstract Coin getMaxMoney();

    public abstract Coin getMinNonDustOutput();

    public abstract MonetaryFormat getMonetaryFormat();

    public String getUriScheme() {
        return this.uriScheme;
    }

    public abstract boolean hasMaxMoney();

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final MessageSerializer getDefaultSerializer() {
        if (null == this.defaultSerializer) {
            NetworkParameters networkParameters = this;
            synchronized (networkParameters) {
                if (null == this.defaultSerializer) {
                    this.defaultSerializer = this.getSerializer(false);
                }
            }
        }
        return this.defaultSerializer;
    }

    public abstract BitcoinSerializer getSerializer(boolean var1);

    public int getMajorityEnforceBlockUpgrade() {
        return this.majorityEnforceBlockUpgrade;
    }

    public int getMajorityRejectBlockOutdated() {
        return this.majorityRejectBlockOutdated;
    }

    public int getMajorityWindow() {
        return this.majorityWindow;
    }

    public EnumSet<Block.VerifyFlag> getBlockVerificationFlags(Block block, VersionTally tally, Integer height) {
        Integer count;
        EnumSet<Block.VerifyFlag> flags = EnumSet.noneOf(Block.VerifyFlag.class);
        if (block.isBIP34() && null != (count = tally.getCountAtOrAbove(2L)) && count >= this.getMajorityEnforceBlockUpgrade()) {
            flags.add(Block.VerifyFlag.HEIGHT_IN_COINBASE);
        }
        return flags;
    }

    public EnumSet<Script.VerifyFlag> getTransactionVerificationFlags(Block block, Transaction transaction, VersionTally tally, Integer height) {
        EnumSet<Script.VerifyFlag> verifyFlags = EnumSet.noneOf(Script.VerifyFlag.class);
        if (block.getTimeSeconds() >= 1333238400L) {
            verifyFlags.add(Script.VerifyFlag.P2SH);
        }
        if (block.getVersion() >= 4L && tally.getCountAtOrAbove(4L) > this.getMajorityEnforceBlockUpgrade()) {
            verifyFlags.add(Script.VerifyFlag.CHECKLOCKTIMEVERIFY);
        }
        return verifyFlags;
    }

    public String getBech32AddressPrefix() {
        return this.bech32Prefix;
    }

    public byte getBech32AddressSeparator() {
        return this.bech32Separator;
    }

    public abstract int getProtocolVersionNum(ProtocolVersion var1);

    public static enum ProtocolVersion {
        MINIMUM(70000),
        PONG(60001),
        BLOOM_FILTER(70000),
        CURRENT(70012);
        
        private final int bitcoinProtocol;

        private ProtocolVersion(int bitcoinProtocol) {
            this.bitcoinProtocol = bitcoinProtocol;
        }

        public int getBitcoinProtocolVersion() {
            return this.bitcoinProtocol;
        }
    }

}

