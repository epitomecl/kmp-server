/*
 * Decompiled with CFR 0_132.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Preconditions
 *  com.google.common.collect.ImmutableList
 *  com.google.common.collect.ImmutableList$Builder
 *  com.google.common.collect.Maps
 */
package org.bitcoinj.crypto;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.bitcoinj.crypto.ChildNumber;
import org.bitcoinj.crypto.DeterministicKey;
import org.bitcoinj.crypto.HDDerivationException;
import org.bitcoinj.crypto.HDKeyDerivation;
import org.bitcoinj.crypto.HDUtils;

public class DeterministicHierarchy {
    private final Map<ImmutableList<ChildNumber>, DeterministicKey> keys = Maps.newHashMap();
    private final ImmutableList<ChildNumber> rootPath;
    private final Map<ImmutableList<ChildNumber>, ChildNumber> lastChildNumbers = Maps.newHashMap();
    public static final int BIP32_STANDARDISATION_TIME_SECS = 1369267200;

    public DeterministicHierarchy(DeterministicKey rootKey) {
        this.putKey(rootKey);
        this.rootPath = rootKey.getPath();
    }

    public final void putKey(DeterministicKey key) {
        ImmutableList<ChildNumber> path = key.getPath();
        DeterministicKey parent = key.getParent();
        if (parent != null) {
            this.lastChildNumbers.put(parent.getPath(), key.getChildNumber());
        }
        this.keys.put(path, key);
    }

    public DeterministicKey get(List<ChildNumber> path, boolean relativePath, boolean create) {
        ImmutableList absolutePath;
        ImmutableList immutableList = absolutePath = relativePath ? ImmutableList.builder().addAll(this.rootPath).addAll(path).build() : ImmutableList.copyOf(path);
        if (!this.keys.containsKey((Object)absolutePath)) {
            if (!create) {
                Object[] arrobject = new Object[2];
                arrobject[0] = relativePath ? "relative" : "absolute";
                arrobject[1] = HDUtils.formatPath(path);
                throw new IllegalArgumentException(String.format(Locale.US, "No key found for %s path %s.", arrobject));
            }
            Preconditions.checkArgument((boolean)(absolutePath.size() > 0), (Object)"Can't derive the master key: nothing to derive from.");
            DeterministicKey parent = this.get((List<ChildNumber>)absolutePath.subList(0, absolutePath.size() - 1), false, true);
            this.putKey(HDKeyDerivation.deriveChildKey(parent, (ChildNumber)absolutePath.get(absolutePath.size() - 1)));
        }
        return this.keys.get((Object)absolutePath);
    }

    public DeterministicKey deriveNextChild(ImmutableList<ChildNumber> parentPath, boolean relative, boolean createParent, boolean privateDerivation) {
        DeterministicKey parent = this.get((List<ChildNumber>)parentPath, relative, createParent);
        int nAttempts = 0;
        while (nAttempts++ < 100) {
            try {
                ChildNumber createChildNumber = this.getNextChildNumberToDerive(parent.getPath(), privateDerivation);
                return this.deriveChild(parent, createChildNumber);
            }
            catch (HDDerivationException createChildNumber) {
            }
        }
        throw new HDDerivationException("Maximum number of child derivation attempts reached, this is probably an indication of a bug.");
    }

    private ChildNumber getNextChildNumberToDerive(ImmutableList<ChildNumber> path, boolean privateDerivation) {
        ChildNumber lastChildNumber = this.lastChildNumbers.get(path);
        ChildNumber nextChildNumber = new ChildNumber(lastChildNumber != null ? lastChildNumber.num() + 1 : 0, privateDerivation);
        this.lastChildNumbers.put(path, nextChildNumber);
        return nextChildNumber;
    }

    public int getNumChildren(ImmutableList<ChildNumber> path) {
        ChildNumber cn = this.lastChildNumbers.get(path);
        if (cn == null) {
            return 0;
        }
        return cn.num() + 1;
    }

    public DeterministicKey deriveChild(List<ChildNumber> parentPath, boolean relative, boolean createParent, ChildNumber createChildNumber) {
        return this.deriveChild(this.get(parentPath, relative, createParent), createChildNumber);
    }

    private DeterministicKey deriveChild(DeterministicKey parent, ChildNumber createChildNumber) {
        DeterministicKey childKey = HDKeyDerivation.deriveChildKey(parent, createChildNumber);
        this.putKey(childKey);
        return childKey;
    }

    public DeterministicKey getRootKey() {
        return this.get((List<ChildNumber>)this.rootPath, false, false);
    }
}

