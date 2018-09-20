/*
 * Decompiled with CFR 0_132.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Joiner
 *  com.google.common.base.Preconditions
 *  com.google.common.base.Stopwatch
 *  com.google.common.collect.ImmutableList
 *  com.google.common.collect.ImmutableList$Builder
 *  com.google.protobuf.ByteString
 *  javax.annotation.Nullable
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package org.bitcoinj.wallet;

import com.google.common.base.Joiner;
import com.google.common.base.Preconditions;
import com.google.common.base.Stopwatch;
import com.google.common.collect.ImmutableList;
import com.google.protobuf.ByteString;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.locks.ReentrantLock;
import javax.annotation.Nullable;
import org.bitcoinj.core.ECKey;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.core.Utils;
import org.bitcoinj.crypto.ChildNumber;
import org.bitcoinj.crypto.DeterministicHierarchy;
import org.bitcoinj.crypto.DeterministicKey;
import org.bitcoinj.crypto.HDKeyDerivation;
import org.bitcoinj.crypto.HDUtils;
import org.bitcoinj.script.Script;
import org.bitcoinj.utils.Threading;
import org.bitcoinj.wallet.BasicKeyChain;
import org.bitcoinj.wallet.DeterministicSeed;
import org.bitcoinj.wallet.KeyChain;
import org.bitcoinj.wallet.RedeemData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DeterministicKeyChain
implements KeyChain {
    private static final Logger log = LoggerFactory.getLogger(DeterministicKeyChain.class);
    public static final String DEFAULT_PASSPHRASE_FOR_MNEMONIC = "";
    protected final ReentrantLock lock = Threading.lock("DeterministicKeyChain");
    private DeterministicHierarchy hierarchy;
    @Nullable
    private DeterministicKey rootKey;
    @Nullable
    private DeterministicSeed seed;
    @Nullable
    private ImmutableList<ChildNumber> accountPath;
    public static final ImmutableList<ChildNumber> ACCOUNT_ZERO_PATH = ImmutableList.of((Object)ChildNumber.ZERO_HARDENED);
    public static final ImmutableList<ChildNumber> BIP44_ACCOUNT_ZERO_PATH = ImmutableList.of((Object)new ChildNumber(44, true), (Object)ChildNumber.ZERO_HARDENED, (Object)ChildNumber.ZERO_HARDENED);
    public static final ImmutableList<ChildNumber> EXTERNAL_SUBPATH = ImmutableList.of((Object)ChildNumber.ZERO);
    public static final ImmutableList<ChildNumber> INTERNAL_SUBPATH = ImmutableList.of((Object)ChildNumber.ONE);
    private static final int LAZY_CALCULATE_LOOKAHEAD = -1;
    protected int lookaheadSize = 100;
    protected int lookaheadThreshold = this.calcDefaultLookaheadThreshold();
    private DeterministicKey externalParentKey;
    private DeterministicKey internalParentKey;
    private int issuedExternalKeys;
    private int issuedInternalKeys;
    private int keyLookaheadEpoch;
    private final BasicKeyChain basicKeyChain;
    private boolean isFollowing;
    protected int sigsRequiredToSpend = 1;

    private int calcDefaultLookaheadThreshold() {
        return this.lookaheadSize / 3;
    }

    public static Builder<?> builder() {
        return new Builder();
    }

    public DeterministicKeyChain(SecureRandom random) {
        this(random, 128, DEFAULT_PASSPHRASE_FOR_MNEMONIC, Utils.currentTimeSeconds());
    }

    public DeterministicKeyChain(SecureRandom random, int bits) {
        this(random, bits, DEFAULT_PASSPHRASE_FOR_MNEMONIC, Utils.currentTimeSeconds());
    }

    public DeterministicKeyChain(SecureRandom random, int bits, String passphrase, long seedCreationTimeSecs) {
        this(new DeterministicSeed(random, bits, passphrase, seedCreationTimeSecs));
    }

    public DeterministicKeyChain(byte[] entropy, String passphrase, long seedCreationTimeSecs) {
        this(new DeterministicSeed(entropy, passphrase, seedCreationTimeSecs));
    }

    protected DeterministicKeyChain(DeterministicSeed seed) {
        this(seed, ACCOUNT_ZERO_PATH);
    }

    public DeterministicKeyChain(DeterministicKey watchingKey) {
        this(watchingKey, ACCOUNT_ZERO_PATH);
    }

    public DeterministicKeyChain(DeterministicKey watchingKey, ImmutableList<ChildNumber> accountPath) {
        Preconditions.checkArgument((boolean)watchingKey.isPubKeyOnly(), (Object)"Private subtrees not currently supported: if you got this key from DKC.getWatchingKey() then use .dropPrivate().dropParent() on it first.");
        Preconditions.checkArgument((boolean)(watchingKey.getPath().size() == accountPath.size()), (Object)"You can only watch an account key currently");
        this.setAccountPath(accountPath);
        this.basicKeyChain = new BasicKeyChain();
        this.seed = null;
        this.rootKey = null;
        this.basicKeyChain.importKey(watchingKey);
        this.hierarchy = new DeterministicHierarchy(watchingKey);
        this.initializeHierarchyUnencrypted(watchingKey);
    }

    protected DeterministicKeyChain(DeterministicKey watchKey, boolean isFollowing) {
        this(watchKey, isFollowing, ACCOUNT_ZERO_PATH);
    }

    protected DeterministicKeyChain(DeterministicKey watchKey, boolean isFollowing, ImmutableList<ChildNumber> accountPath) {
        this(watchKey, accountPath);
        this.isFollowing = isFollowing;
    }

    public static DeterministicKeyChain watchAndFollow(DeterministicKey watchKey) {
        return new DeterministicKeyChain(watchKey, true);
    }

    public static DeterministicKeyChain watch(DeterministicKey accountKey) {
        return DeterministicKeyChain.watch(accountKey, ACCOUNT_ZERO_PATH);
    }

    public static DeterministicKeyChain watch(DeterministicKey accountKey, ImmutableList<ChildNumber> accountPath) {
        return new DeterministicKeyChain(accountKey, accountPath);
    }

    public DeterministicKeyChain(DeterministicSeed seed, ImmutableList<ChildNumber> accountPath) {
        this.setAccountPath(accountPath);
        this.seed = seed;
        this.basicKeyChain = new BasicKeyChain();
        this.rootKey = HDKeyDerivation.createMasterPrivateKey((byte[])Preconditions.checkNotNull((Object)seed.getSeedBytes()));
        this.rootKey.setCreationTimeSeconds(seed.getCreationTimeSeconds());
        this.basicKeyChain.importKey(this.rootKey);
        this.hierarchy = new DeterministicHierarchy(this.rootKey);
        for (int i = 1; i <= this.getAccountPath().size(); ++i) {
            this.basicKeyChain.importKey(this.hierarchy.get((List<ChildNumber>)this.getAccountPath().subList(0, i), false, true));
        }
        this.initializeHierarchyUnencrypted(this.rootKey);
    }

    protected ImmutableList<ChildNumber> getAccountPath() {
        if (this.accountPath != null) {
            return this.accountPath;
        }
        return ACCOUNT_ZERO_PATH;
    }

    protected void setAccountPath(ImmutableList<ChildNumber> accountPath) {
        this.accountPath = accountPath;
    }

    private void initializeHierarchyUnencrypted(DeterministicKey baseKey) {
        this.externalParentKey = this.hierarchy.deriveChild((List<ChildNumber>)this.getAccountPath(), false, false, ChildNumber.ZERO);
        this.internalParentKey = this.hierarchy.deriveChild((List<ChildNumber>)this.getAccountPath(), false, false, ChildNumber.ONE);
        this.basicKeyChain.importKey(this.externalParentKey);
        this.basicKeyChain.importKey(this.internalParentKey);
    }

    @Override
    public DeterministicKey getKey(KeyChain.KeyPurpose purpose) {
        return this.getKeys(purpose, 1).get(0);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public List<DeterministicKey> getKeys(KeyChain.KeyPurpose purpose, int numberOfKeys) {
        Preconditions.checkArgument((boolean)(numberOfKeys > 0));
        this.lock.lock();
        try {
            DeterministicKey parentKey;
            int index;
            switch (purpose) {
                case RECEIVE_FUNDS: 
                case REFUND: {
                    this.issuedExternalKeys += numberOfKeys;
                    index = this.issuedExternalKeys;
                    parentKey = this.externalParentKey;
                    break;
                }
                case AUTHENTICATION: 
                case CHANGE: {
                    this.issuedInternalKeys += numberOfKeys;
                    index = this.issuedInternalKeys;
                    parentKey = this.internalParentKey;
                    break;
                }
                default: {
                    throw new UnsupportedOperationException();
                }
            }
            List<DeterministicKey> lookahead = this.maybeLookAhead(parentKey, index, 0, 0);
            this.basicKeyChain.importKeys(lookahead);
            ArrayList<DeterministicKey> keys = new ArrayList<DeterministicKey>(numberOfKeys);
            for (int i = 0; i < numberOfKeys; ++i) {
                ImmutableList<ChildNumber> path = HDUtils.append(parentKey.getPath(), new ChildNumber(index - numberOfKeys + i, false));
                DeterministicKey k = this.hierarchy.get((List<ChildNumber>)path, false, false);
                this.checkForBitFlip(k);
                keys.add(k);
            }
            ArrayList<DeterministicKey> i = keys;
            return i;
        }
        finally {
            this.lock.unlock();
        }
    }

    private void checkForBitFlip(DeterministicKey k) {
        DeterministicKey parent = (DeterministicKey)Preconditions.checkNotNull((Object)k.getParent());
        byte[] rederived = HDKeyDerivation.deriveChildKeyBytesFromPublic((DeterministicKey)parent, (ChildNumber)k.getChildNumber(), (HDKeyDerivation.PublicDeriveMode)HDKeyDerivation.PublicDeriveMode.WITH_INVERSION).keyBytes;
        byte[] actual = k.getPubKey();
        if (!Arrays.equals(rederived, actual)) {
            throw new IllegalStateException(String.format(Locale.US, "Bit-flip check failed: %s vs %s", Arrays.toString(rederived), Arrays.toString(actual)));
        }
    }

    public DeterministicKey markKeyAsUsed(DeterministicKey k) {
        int numChildren = k.getChildNumber().i() + 1;
        if (k.getParent() == this.internalParentKey) {
            if (this.issuedInternalKeys < numChildren) {
                this.issuedInternalKeys = numChildren;
                this.maybeLookAhead();
            }
        } else if (k.getParent() == this.externalParentKey && this.issuedExternalKeys < numChildren) {
            this.issuedExternalKeys = numChildren;
            this.maybeLookAhead();
        }
        return k;
    }

    public DeterministicKey findKeyFromPubHash(byte[] pubkeyHash) {
        this.lock.lock();
        try {
            DeterministicKey deterministicKey = (DeterministicKey)this.basicKeyChain.findKeyFromPubHash(pubkeyHash);
            return deterministicKey;
        }
        finally {
            this.lock.unlock();
        }
    }

    public DeterministicKey findKeyFromPubKey(byte[] pubkey) {
        this.lock.lock();
        try {
            DeterministicKey deterministicKey = (DeterministicKey)this.basicKeyChain.findKeyFromPubKey(pubkey);
            return deterministicKey;
        }
        finally {
            this.lock.unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Nullable
    public DeterministicKey markPubHashAsUsed(byte[] pubkeyHash) {
        this.lock.lock();
        try {
            DeterministicKey k = (DeterministicKey)this.basicKeyChain.findKeyFromPubHash(pubkeyHash);
            if (k != null) {
                this.markKeyAsUsed(k);
            }
            DeterministicKey deterministicKey = k;
            return deterministicKey;
        }
        finally {
            this.lock.unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Nullable
    public DeterministicKey markPubKeyAsUsed(byte[] pubkey) {
        this.lock.lock();
        try {
            DeterministicKey k = (DeterministicKey)this.basicKeyChain.findKeyFromPubKey(pubkey);
            if (k != null) {
                this.markKeyAsUsed(k);
            }
            DeterministicKey deterministicKey = k;
            return deterministicKey;
        }
        finally {
            this.lock.unlock();
        }
    }

    @Override
    public boolean hasKey(ECKey key) {
        this.lock.lock();
        try {
            boolean bl = this.basicKeyChain.hasKey(key);
            return bl;
        }
        finally {
            this.lock.unlock();
        }
    }

    protected /* varargs */ DeterministicKey getKeyByPath(ChildNumber ... path) {
        return this.getKeyByPath((List<ChildNumber>)ImmutableList.copyOf((Object[])path));
    }

    protected DeterministicKey getKeyByPath(List<ChildNumber> path) {
        return this.getKeyByPath(path, false);
    }

    public DeterministicKey getKeyByPath(List<ChildNumber> path, boolean create) {
        return this.hierarchy.get(path, false, create);
    }

    public DeterministicKey getWatchingKey() {
        return this.getKeyByPath((List<ChildNumber>)this.getAccountPath());
    }

    public boolean isWatching() {
        return this.getWatchingKey().isWatching();
    }

    @Override
    public int numKeys() {
        this.lock.lock();
        try {
            this.maybeLookAhead();
            int n = this.basicKeyChain.numKeys();
            return n;
        }
        finally {
            this.lock.unlock();
        }
    }

    public int numLeafKeysIssued() {
        this.lock.lock();
        try {
            int n = this.issuedExternalKeys + this.issuedInternalKeys;
            return n;
        }
        finally {
            this.lock.unlock();
        }
    }

    public long getEarliestKeyCreationTime() {
        if (this.seed != null) {
            return this.seed.getCreationTimeSeconds();
        }
        return this.getWatchingKey().getCreationTimeSeconds();
    }

    @Nullable
    public List<String> getMnemonicCode() {
        if (this.seed == null) {
            return null;
        }
        this.lock.lock();
        try {
            List<String> list = this.seed.getMnemonicCode();
            return list;
        }
        finally {
            this.lock.unlock();
        }
    }

    public boolean isFollowing() {
        return this.isFollowing;
    }

    protected DeterministicKeyChain makeKeyChainFromSeed(DeterministicSeed seed) {
        return new DeterministicKeyChain(seed);
    }

    public int getLookaheadSize() {
        this.lock.lock();
        try {
            int n = this.lookaheadSize;
            return n;
        }
        finally {
            this.lock.unlock();
        }
    }

    public void setLookaheadSize(int lookaheadSize) {
        this.lock.lock();
        try {
            boolean readjustThreshold = this.lookaheadThreshold == this.calcDefaultLookaheadThreshold();
            this.lookaheadSize = lookaheadSize;
            if (readjustThreshold) {
                this.lookaheadThreshold = this.calcDefaultLookaheadThreshold();
            }
        }
        finally {
            this.lock.unlock();
        }
    }

    public void setLookaheadThreshold(int num) {
        this.lock.lock();
        try {
            if (num >= this.lookaheadSize) {
                throw new IllegalArgumentException("Threshold larger or equal to the lookaheadSize");
            }
            this.lookaheadThreshold = num;
        }
        finally {
            this.lock.unlock();
        }
    }

    public int getLookaheadThreshold() {
        this.lock.lock();
        try {
            if (this.lookaheadThreshold >= this.lookaheadSize) {
                int n = 0;
                return n;
            }
            int n = this.lookaheadThreshold;
            return n;
        }
        finally {
            this.lock.unlock();
        }
    }

    public void maybeLookAhead() {
        this.lock.lock();
        try {
            List<DeterministicKey> keys = this.maybeLookAhead(this.externalParentKey, this.issuedExternalKeys);
            keys.addAll(this.maybeLookAhead(this.internalParentKey, this.issuedInternalKeys));
            if (keys.isEmpty()) {
                return;
            }
            ++this.keyLookaheadEpoch;
            this.basicKeyChain.importKeys(keys);
        }
        finally {
            this.lock.unlock();
        }
    }

    private List<DeterministicKey> maybeLookAhead(DeterministicKey parent, int issued) {
        Preconditions.checkState((boolean)this.lock.isHeldByCurrentThread());
        return this.maybeLookAhead(parent, issued, this.getLookaheadSize(), this.getLookaheadThreshold());
    }

    private List<DeterministicKey> maybeLookAhead(DeterministicKey parent, int issued, int lookaheadSize, int lookaheadThreshold) {
        Preconditions.checkState((boolean)this.lock.isHeldByCurrentThread());
        int numChildren = this.hierarchy.getNumChildren(parent.getPath());
        int needed = issued + lookaheadSize + lookaheadThreshold - numChildren;
        if (needed <= lookaheadThreshold) {
            return new ArrayList<DeterministicKey>();
        }
        log.info("{} keys needed for {} = {} issued + {} lookahead size + {} lookahead threshold - {} num children", new Object[]{needed, parent.getPathAsString(), issued, lookaheadSize, lookaheadThreshold, numChildren});
        ArrayList<DeterministicKey> result = new ArrayList<DeterministicKey>(needed);
        Stopwatch watch = Stopwatch.createStarted();
        int nextChild = numChildren;
        for (int i = 0; i < needed; ++i) {
            DeterministicKey key = HDKeyDerivation.deriveThisOrNextChildKey(parent, nextChild);
            key = key.dropPrivateBytes();
            this.hierarchy.putKey(key);
            result.add(key);
            nextChild = key.getChildNumber().num() + 1;
        }
        watch.stop();
        log.info("Took {}", (Object)watch);
        return result;
    }

    public void maybeLookAheadScripts() {
    }

    public int getIssuedExternalKeys() {
        this.lock.lock();
        try {
            int n = this.issuedExternalKeys;
            return n;
        }
        finally {
            this.lock.unlock();
        }
    }

    public int getIssuedInternalKeys() {
        this.lock.lock();
        try {
            int n = this.issuedInternalKeys;
            return n;
        }
        finally {
            this.lock.unlock();
        }
    }

    @Nullable
    public DeterministicSeed getSeed() {
        this.lock.lock();
        try {
            DeterministicSeed deterministicSeed = this.seed;
            return deterministicSeed;
        }
        finally {
            this.lock.unlock();
        }
    }

    List<ECKey> getKeys(boolean includeLookahead, boolean includeParents) {
        List<ECKey> keys = this.basicKeyChain.getKeys();
        if (!includeLookahead) {
            int treeSize = this.internalParentKey.getPath().size();
            LinkedList<ECKey> issuedKeys = new LinkedList<ECKey>();
            for (ECKey key : keys) {
                DeterministicKey detkey = (DeterministicKey)key;
                DeterministicKey parent = detkey.getParent();
                if (!includeParents && parent == null || !includeParents && detkey.getPath().size() <= treeSize || this.internalParentKey.equals(parent) && detkey.getChildNumber().i() >= this.issuedInternalKeys || this.externalParentKey.equals(parent) && detkey.getChildNumber().i() >= this.issuedExternalKeys) continue;
                issuedKeys.add(detkey);
            }
            return issuedKeys;
        }
        return keys;
    }

    public List<ECKey> getIssuedReceiveKeys() {
        ArrayList<ECKey> keys = new ArrayList<ECKey>(this.getKeys(false, false));
        Iterator<ECKey> i = keys.iterator();
        while (i.hasNext()) {
            DeterministicKey parent = ((DeterministicKey)i.next()).getParent();
            if (parent != null && this.externalParentKey.equals(parent)) continue;
            i.remove();
        }
        return keys;
    }

    public List<DeterministicKey> getLeafKeys() {
        ImmutableList.Builder keys = ImmutableList.builder();
        for (ECKey key : this.getKeys(true, false)) {
            DeterministicKey dKey = (DeterministicKey)key;
            if (dKey.getPath().size() != this.getAccountPath().size() + 2) continue;
            keys.add((Object)dKey);
        }
        return keys.build();
    }

    public int getKeyLookaheadEpoch() {
        this.lock.lock();
        try {
            int n = this.keyLookaheadEpoch;
            return n;
        }
        finally {
            this.lock.unlock();
        }
    }

    public RedeemData getRedeemData(DeterministicKey followedKey) {
        throw new UnsupportedOperationException();
    }

    public Script freshOutputScript(KeyChain.KeyPurpose purpose) {
        throw new UnsupportedOperationException();
    }

    public String toString(boolean includePrivateKeys, NetworkParameters params) {
        DeterministicKey watchingKey = this.getWatchingKey();
        StringBuilder builder = new StringBuilder();
        if (this.seed != null) {
            if (includePrivateKeys) {
                DeterministicSeed decryptedSeed = this.seed;
                List<String> words = decryptedSeed.getMnemonicCode();
                builder.append("Seed as words: ").append(Utils.SPACE_JOINER.join(words)).append('\n');
                builder.append("Seed as hex:   ").append(decryptedSeed.toHexString()).append('\n');
            }
            builder.append("Seed birthday: ").append(this.seed.getCreationTimeSeconds()).append("  [").append(Utils.dateTimeFormat(this.seed.getCreationTimeSeconds() * 1000L)).append("]\n");
        } else {
            builder.append("Key birthday:  ").append(watchingKey.getCreationTimeSeconds()).append("  [").append(Utils.dateTimeFormat(watchingKey.getCreationTimeSeconds() * 1000L)).append("]\n");
        }
        builder.append("Key to watch:  ").append(watchingKey.serializePubB58(params)).append('\n');
        return builder.toString();
    }

    protected void formatAddresses(NetworkParameters params, StringBuilder builder) {
        for (ECKey key : this.getKeys(false, true)) {
            key.formatKeyWithAddress(builder, params);
        }
    }

    public void setSigsRequiredToSpend(int sigsRequiredToSpend) {
        this.sigsRequiredToSpend = sigsRequiredToSpend;
    }

    public int getSigsRequiredToSpend() {
        return this.sigsRequiredToSpend;
    }

    @Nullable
    public RedeemData findRedeemDataByScriptHash(ByteString bytes) {
        return null;
    }

    public static class Builder<T extends Builder<T>> {
        protected SecureRandom random;
        protected int bits = 128;
        protected String passphrase;
        protected long seedCreationTimeSecs;
        protected byte[] entropy;
        protected DeterministicSeed seed;
        protected DeterministicKey watchingKey;

        protected Builder() {
        }

        protected T self() {
            return (T)this;
        }

        public T entropy(byte[] entropy) {
            this.entropy = entropy;
            return this.self();
        }

        public T seed(DeterministicSeed seed) {
            this.seed = seed;
            return this.self();
        }

        public T random(SecureRandom random, int bits) {
            this.random = random;
            this.bits = bits;
            return this.self();
        }

        public T random(SecureRandom random) {
            this.random = random;
            return this.self();
        }

        public T watchingKey(DeterministicKey watchingKey) {
            this.watchingKey = watchingKey;
            return this.self();
        }

        public T seedCreationTimeSecs(long seedCreationTimeSecs) {
            this.seedCreationTimeSecs = seedCreationTimeSecs;
            return this.self();
        }

        public T passphrase(String passphrase) {
            this.passphrase = passphrase;
            return this.self();
        }

        public DeterministicKeyChain build() {
            DeterministicKeyChain chain;
            Preconditions.checkState((boolean)(this.random != null || this.entropy != null || this.seed != null || this.watchingKey != null), (Object)"Must provide either entropy or random or seed or watchingKey");
            Preconditions.checkState((boolean)(this.passphrase == null || this.seed == null), (Object)"Passphrase must not be specified with seed");
            if (this.random != null) {
                chain = new DeterministicKeyChain(this.random, this.bits, this.getPassphrase(), this.seedCreationTimeSecs);
            } else if (this.entropy != null) {
                chain = new DeterministicKeyChain(this.entropy, this.getPassphrase(), this.seedCreationTimeSecs);
            } else if (this.seed != null) {
                this.seed.setCreationTimeSeconds(this.seedCreationTimeSecs);
                chain = new DeterministicKeyChain(this.seed);
            } else {
                this.watchingKey.setCreationTimeSeconds(this.seedCreationTimeSecs);
                chain = new DeterministicKeyChain(this.watchingKey);
            }
            return chain;
        }

        protected String getPassphrase() {
            return this.passphrase != null ? this.passphrase : DeterministicKeyChain.DEFAULT_PASSPHRASE_FOR_MNEMONIC;
        }
    }

}

