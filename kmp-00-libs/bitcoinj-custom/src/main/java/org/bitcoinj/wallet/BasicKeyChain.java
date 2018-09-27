/*
 * Decompiled with CFR 0_132.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Preconditions
 *  com.google.common.collect.ImmutableList
 *  com.google.common.collect.Lists
 *  com.google.protobuf.ByteString
 *  javax.annotation.Nullable
 */
package org.bitcoinj.wallet;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.protobuf.ByteString;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;
import javax.annotation.Nullable;
import org.bitcoinj.core.ECKey;
import org.bitcoinj.utils.Threading;
import org.bitcoinj.wallet.KeyChain;

public class BasicKeyChain
implements KeyChain {
    private final ReentrantLock lock = Threading.lock("BasicKeyChain");
    private final LinkedHashMap<ByteString, ECKey> hashToKeys = new LinkedHashMap();
    private final LinkedHashMap<ByteString, ECKey> pubkeyToKeys = new LinkedHashMap();
    private boolean isWatching;

    @Override
    public ECKey getKey(@Nullable KeyChain.KeyPurpose ignored) {
        this.lock.lock();
        try {
            ECKey key;
            if (this.hashToKeys.isEmpty()) {
                key = new ECKey();
                this.importKeyLocked(key);
            }
            key = this.hashToKeys.values().iterator().next();
            return key;
        }
        finally {
            this.lock.unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public List<ECKey> getKeys(@Nullable KeyChain.KeyPurpose purpose, int numberOfKeys) {
        Preconditions.checkArgument((boolean)(numberOfKeys > 0));
        this.lock.lock();
        try {
            if (this.hashToKeys.size() < numberOfKeys) {
                ArrayList<ECKey> keys = new ArrayList<ECKey>();
                for (int i = 0; i < numberOfKeys - this.hashToKeys.size(); ++i) {
                    keys.add(new ECKey());
                }
                ImmutableList immutableKeys = ImmutableList.copyOf(keys);
                this.importKeysLocked((List<ECKey>)immutableKeys);
            }
            ArrayList<ECKey> keysToReturn = new ArrayList<ECKey>();
            for (int count = 0; this.hashToKeys.values().iterator().hasNext() && numberOfKeys != count; ++count) {
                keysToReturn.add(this.hashToKeys.values().iterator().next());
            }
            ArrayList<ECKey> arrayList = keysToReturn;
            return arrayList;
        }
        finally {
            this.lock.unlock();
        }
    }

    public List<ECKey> getKeys() {
        this.lock.lock();
        try {
            ArrayList<ECKey> arrayList = new ArrayList<ECKey>(this.hashToKeys.values());
            return arrayList;
        }
        finally {
            this.lock.unlock();
        }
    }

    public /* varargs */ int importKeys(ECKey ... keys) {
        return this.importKeys((List<? extends ECKey>)ImmutableList.copyOf(keys));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public int importKeys(List<? extends ECKey> keys) {
        this.lock.lock();
        try {
            ArrayList<ECKey> actuallyAdded = new ArrayList<ECKey>(keys.size());
            for (ECKey key : keys) {
                if (this.hasKey(key)) continue;
                actuallyAdded.add(key);
                this.importKeyLocked(key);
            }
            int n = actuallyAdded.size();
            return n;
        }
        finally {
            this.lock.unlock();
        }
    }

    private void importKeyLocked(ECKey key) {
        if (this.hashToKeys.isEmpty()) {
            this.isWatching = key.isWatching();
        } else {
            if (key.isWatching() && !this.isWatching) {
                throw new IllegalArgumentException("Key is watching but chain is not");
            }
            if (!key.isWatching() && this.isWatching) {
                throw new IllegalArgumentException("Key is not watching but chain is");
            }
        }
        ECKey previousKey = this.pubkeyToKeys.put(ByteString.copyFrom((byte[])key.getPubKey()), key);
        this.hashToKeys.put(ByteString.copyFrom((byte[])key.getPubKeyHash()), key);
        Preconditions.checkState((boolean)(previousKey == null));
    }

    private void importKeysLocked(List<ECKey> keys) {
        for (ECKey key : keys) {
            this.importKeyLocked(key);
        }
    }

    public void importKey(ECKey key) {
        this.lock.lock();
        try {
            if (this.hasKey(key)) {
                return;
            }
            this.importKeyLocked(key);
        }
        finally {
            this.lock.unlock();
        }
    }

    public ECKey findKeyFromPubHash(byte[] pubkeyHash) {
        this.lock.lock();
        try {
            ECKey eCKey = this.hashToKeys.get((Object)ByteString.copyFrom((byte[])pubkeyHash));
            return eCKey;
        }
        finally {
            this.lock.unlock();
        }
    }

    public ECKey findKeyFromPubKey(byte[] pubkey) {
        this.lock.lock();
        try {
            ECKey eCKey = this.pubkeyToKeys.get((Object)ByteString.copyFrom((byte[])pubkey));
            return eCKey;
        }
        finally {
            this.lock.unlock();
        }
    }

    @Override
    public boolean hasKey(ECKey key) {
        return this.findKeyFromPubKey(key.getPubKey()) != null;
    }

    @Override
    public int numKeys() {
        return this.pubkeyToKeys.size();
    }

    public State isWatching() {
        this.lock.lock();
        try {
            if (this.hashToKeys.isEmpty()) {
                State state = State.EMPTY;
                return state;
            }
            State state = this.isWatching ? State.WATCHING : State.REGULAR;
            return state;
        }
        finally {
            this.lock.unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public boolean removeKey(ECKey key) {
        this.lock.lock();
        try {
            boolean a = this.hashToKeys.remove((Object)ByteString.copyFrom((byte[])key.getPubKeyHash())) != null;
            boolean b = this.pubkeyToKeys.remove((Object)ByteString.copyFrom((byte[])key.getPubKey())) != null;
            Preconditions.checkState((boolean)(a == b));
            boolean bl = a;
            return bl;
        }
        finally {
            this.lock.unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public long getEarliestKeyCreationTime() {
        this.lock.lock();
        try {
            long time = Long.MAX_VALUE;
            for (ECKey key : this.hashToKeys.values()) {
                time = Math.min(key.getCreationTimeSeconds(), time);
            }
            long l = time;
            return l;
        }
        finally {
            this.lock.unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Nullable
    public ECKey findOldestKeyAfter(long timeSecs) {
        this.lock.lock();
        try {
            ECKey oldest = null;
            for (ECKey key : this.hashToKeys.values()) {
                long keyTime = key.getCreationTimeSeconds();
                if (keyTime <= timeSecs || oldest != null && oldest.getCreationTimeSeconds() <= keyTime) continue;
                oldest = key;
            }
            //Iterator<ECKey> iterator = oldest;
            //return iterator;
            return oldest;
        }
        finally {
            this.lock.unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public List<ECKey> findKeysBefore(long timeSecs) {
        this.lock.lock();
        try {
            LinkedList results = Lists.newLinkedList();
            for (ECKey key : this.hashToKeys.values()) {
                long keyTime = key.getCreationTimeSeconds();
                if (keyTime >= timeSecs) continue;
                results.add(key);
            }
            LinkedList linkedList = results;
            return linkedList;
        }
        finally {
            this.lock.unlock();
        }
    }

    public static enum State {
        EMPTY,
        WATCHING,
        REGULAR;
        

        private State() {
        }
    }

}

