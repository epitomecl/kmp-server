/*
 * Decompiled with CFR 0_132.
 */
package org.bitcoinj.utils;

import org.bitcoinj.core.NetworkParameters;

public class VersionTally {
    private final long[] versionWindow;
    private int versionWriteHead = 0;
    private int versionsStored = 0;

    public VersionTally(NetworkParameters params) {
        this.versionWindow = new long[params.getMajorityWindow()];
    }

    public void add(long version) {
        this.versionWindow[this.versionWriteHead++] = version;
        if (this.versionWriteHead == this.versionWindow.length) {
            this.versionWriteHead = 0;
        }
        ++this.versionsStored;
    }

    public Integer getCountAtOrAbove(long version) {
        if (this.versionsStored < this.versionWindow.length) {
            return null;
        }
        int count = 0;
        for (int versionIdx = 0; versionIdx < this.versionWindow.length; ++versionIdx) {
            if (this.versionWindow[versionIdx] < version) continue;
            ++count;
        }
        return count;
    }

    public int size() {
        return this.versionWindow.length;
    }
}

