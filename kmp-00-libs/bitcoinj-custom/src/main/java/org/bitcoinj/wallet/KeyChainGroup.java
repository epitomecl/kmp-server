/*
 * Decompiled with CFR 0_132.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Preconditions
 *  com.google.common.collect.ImmutableList
 *  com.google.protobuf.ByteString
 *  javax.annotation.Nullable
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package org.bitcoinj.wallet;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.protobuf.ByteString;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import javax.annotation.Nullable;
import org.bitcoinj.core.Address;
import org.bitcoinj.core.ECKey;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.core.Utils;
import org.bitcoinj.crypto.ChildNumber;
import org.bitcoinj.crypto.DeterministicKey;
import org.bitcoinj.crypto.HDUtils;
import org.bitcoinj.crypto.LinuxSecureRandom;
import org.bitcoinj.wallet.BasicKeyChain;
import org.bitcoinj.wallet.DeterministicKeyChain;
import org.bitcoinj.wallet.DeterministicSeed;
import org.bitcoinj.wallet.KeyBag;
import org.bitcoinj.wallet.KeyChain;
import org.bitcoinj.wallet.RedeemData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KeyChainGroup
implements KeyBag {
    private static final Logger log;
    private BasicKeyChain basic;
    private NetworkParameters params;
    protected final LinkedList<DeterministicKeyChain> chains;
    private final EnumMap<KeyChain.KeyPurpose, DeterministicKey> currentKeys;
    private final EnumMap<KeyChain.KeyPurpose, Address> currentAddresses;

    public KeyChainGroup(NetworkParameters params) {
        this(params, (BasicKeyChain)null, new ArrayList(1), (EnumMap)null);
    }

    public KeyChainGroup(NetworkParameters params, DeterministicSeed seed) {
        this(params, (BasicKeyChain)null, ImmutableList.of(new DeterministicKeyChain(seed)), (EnumMap)null);
    }

    public KeyChainGroup(NetworkParameters params, DeterministicSeed seed, ImmutableList<ChildNumber> accountPath) {
        this(params, (BasicKeyChain)null, ImmutableList.of(new DeterministicKeyChain(seed, accountPath)), (EnumMap)null);
    }

    public KeyChainGroup(NetworkParameters params, DeterministicKey watchKey) {
        this(params, (BasicKeyChain)null, ImmutableList.of(DeterministicKeyChain.watch(watchKey)), (EnumMap)null);
    }

    public KeyChainGroup(NetworkParameters params, DeterministicKey watchKey, ImmutableList<ChildNumber> accountPath) {
        this(params, (BasicKeyChain)null, ImmutableList.of(DeterministicKeyChain.watch(watchKey, accountPath)), (EnumMap)null);
    }

    private KeyChainGroup(NetworkParameters params, @Nullable BasicKeyChain basicKeyChain, List<DeterministicKeyChain> chains, @Nullable EnumMap<KeyChain.KeyPurpose, DeterministicKey> currentKeys) {
        this.params = params;
        this.basic = basicKeyChain == null ? new BasicKeyChain() : basicKeyChain;
        this.chains = new LinkedList((Collection)Preconditions.checkNotNull(chains));
        this.currentKeys = currentKeys == null ? new EnumMap(KeyChain.KeyPurpose.class) : currentKeys;
        this.currentAddresses = new EnumMap(KeyChain.KeyPurpose.class);
        this.maybeLookaheadScripts();
    }

    private void maybeLookaheadScripts() {
        for (DeterministicKeyChain chain : this.chains) {
            chain.maybeLookAheadScripts();
        }
    }

    public int importKeys(List<ECKey> keys) {
        return this.basic.importKeys(keys);
    }

    public int importKeys(ECKey... keys) {
        return this.importKeys((List)ImmutableList.copyOf(keys));
    }

    @Nullable
    @Override
    public RedeemData findRedeemDataFromScriptHash(byte[] scriptHash) {
        Iterator<DeterministicKeyChain> iter = this.chains.descendingIterator();
        while (iter.hasNext()) {
            DeterministicKeyChain chain = iter.next();
            RedeemData redeemData = chain.findRedeemDataByScriptHash(ByteString.copyFrom((byte[])scriptHash));
            if (redeemData == null) continue;
            return redeemData;
        }
        return null;
    }

    @Nullable
    @Override
    public ECKey findKeyFromPubHash(byte[] pubkeyHash) {
        ECKey result = this.basic.findKeyFromPubHash(pubkeyHash);
        if (result != null) {
            return result;
        }
        for (DeterministicKeyChain chain : this.chains) {
            result = chain.findKeyFromPubHash(pubkeyHash);
            if (result == null) continue;
            return result;
        }
        return null;
    }

    @Nullable
    @Override
    public ECKey findKeyFromPubKey(byte[] pubkey) {
        ECKey result = this.basic.findKeyFromPubKey(pubkey);
        if (result != null) {
            return result;
        }
        for (DeterministicKeyChain chain : this.chains) {
            result = chain.findKeyFromPubKey(pubkey);
            if (result == null) continue;
            return result;
        }
        return null;
    }

    private static EnumMap<KeyChain.KeyPurpose, DeterministicKey> createCurrentKeysMap(List<DeterministicKeyChain> chains) {
        DeterministicKeyChain activeChain = chains.get(chains.size() - 1);
        EnumMap<KeyChain.KeyPurpose, DeterministicKey> currentKeys = new EnumMap<KeyChain.KeyPurpose, DeterministicKey>(KeyChain.KeyPurpose.class);
        if (activeChain.getIssuedExternalKeys() > 0) {
            DeterministicKey currentExternalKey = activeChain.getKeyByPath((List<ChildNumber>)HDUtils.append(HDUtils.concat(activeChain.getAccountPath(), DeterministicKeyChain.EXTERNAL_SUBPATH), new ChildNumber(activeChain.getIssuedExternalKeys() - 1)));
            currentKeys.put(KeyChain.KeyPurpose.RECEIVE_FUNDS, currentExternalKey);
        }
        if (activeChain.getIssuedInternalKeys() > 0) {
            DeterministicKey currentInternalKey = activeChain.getKeyByPath((List<ChildNumber>)HDUtils.append(HDUtils.concat(activeChain.getAccountPath(), DeterministicKeyChain.INTERNAL_SUBPATH), new ChildNumber(activeChain.getIssuedInternalKeys() - 1)));
            currentKeys.put(KeyChain.KeyPurpose.CHANGE, currentInternalKey);
        }
        return currentKeys;
    }

    public String toString(boolean includePrivateKeys) {
        StringBuilder builder = new StringBuilder();
        if (this.basic != null) {
            List<ECKey> keys = this.basic.getKeys();
            Collections.sort(keys, ECKey.AGE_COMPARATOR);
            Iterator iterator = keys.iterator();
            while (iterator.hasNext()) {
                ECKey key = (ECKey)iterator.next();
                key.formatKeyWithAddress(builder, this.params);
            }
        }
        for (DeterministicKeyChain chain : this.chains) {
            builder.append(chain.toString(includePrivateKeys, this.params)).append('\n');
        }
        return builder.toString();
    }

    static {
        if (Utils.isAndroidRuntime()) {
            new LinuxSecureRandom();
        }
        log = LoggerFactory.getLogger(KeyChainGroup.class);
    }
}

