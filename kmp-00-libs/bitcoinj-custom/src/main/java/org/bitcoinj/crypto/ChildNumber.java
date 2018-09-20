/*
 * Decompiled with CFR 0_132.
 * 
 * Could not load the following classes:
 *  com.google.common.primitives.Ints
 */
package org.bitcoinj.crypto;

import com.google.common.primitives.Ints;
import java.util.Locale;

public class ChildNumber
implements Comparable<ChildNumber> {
    public static final int HARDENED_BIT = Integer.MIN_VALUE;
    public static final ChildNumber ZERO = new ChildNumber(0);
    public static final ChildNumber ONE = new ChildNumber(1);
    public static final ChildNumber ZERO_HARDENED = new ChildNumber(0, true);
    private final int i;

    public ChildNumber(int childNumber, boolean isHardened) {
        if (ChildNumber.hasHardenedBit(childNumber)) {
            throw new IllegalArgumentException("Most significant bit is reserved and shouldn't be set: " + childNumber);
        }
        this.i = isHardened ? childNumber | Integer.MIN_VALUE : childNumber;
    }

    public ChildNumber(int i) {
        this.i = i;
    }

    public int getI() {
        return this.i;
    }

    public int i() {
        return this.i;
    }

    public boolean isHardened() {
        return ChildNumber.hasHardenedBit(this.i);
    }

    private static boolean hasHardenedBit(int a) {
        return (a & Integer.MIN_VALUE) != 0;
    }

    public int num() {
        return this.i & Integer.MAX_VALUE;
    }

    public String toString() {
        Object[] arrobject = new Object[2];
        arrobject[0] = this.num();
        arrobject[1] = this.isHardened() ? "H" : "";
        return String.format(Locale.US, "%d%s", arrobject);
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        return this.i == ((ChildNumber)o).i;
    }

    public int hashCode() {
        return this.i;
    }

    @Override
    public int compareTo(ChildNumber other) {
        return Ints.compare((int)this.num(), (int)other.num());
    }
}

