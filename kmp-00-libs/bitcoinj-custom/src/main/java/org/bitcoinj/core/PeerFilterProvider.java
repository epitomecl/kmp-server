/*
 * Decompiled with CFR 0_132.
 */
package org.bitcoinj.core;

import org.bitcoinj.core.BloomFilter;

public interface PeerFilterProvider {
    public long getEarliestKeyCreationTime();

    public void beginBloomFilterCalculation();

    public int getBloomFilterElementCount();

    public BloomFilter getBloomFilter(int var1, double var2, long var4);

    public boolean isRequiringUpdateAllBloomFilter();

    public void endBloomFilterCalculation();
}

