/*
 * Decompiled with CFR 0_132.
 */
package org.bitcoinj.core;

import java.io.Serializable;

public interface Monetary
extends Serializable {
    public int smallestUnitExponent();

    public long getValue();

    public int signum();
}

