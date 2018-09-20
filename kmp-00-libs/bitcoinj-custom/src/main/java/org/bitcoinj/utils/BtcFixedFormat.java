/*
 * Decompiled with CFR 0_132.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Objects
 *  com.google.common.base.Preconditions
 */
package org.bitcoinj.utils;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import org.bitcoinj.utils.BtcFormat;

public final class BtcFixedFormat
extends BtcFormat {
    public static final int[] REPEATING_PLACES = new int[]{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1};
    public static final int[] REPEATING_DOUBLETS = new int[]{2, 2, 2, 2, 2, 2, 2};
    public static final int[] REPEATING_TRIPLETS = new int[]{3, 3, 3, 3, 3};
    private final int scale;

    protected BtcFixedFormat(Locale locale, int scale, int minDecimals, List<Integer> groups) {
        super((DecimalFormat)NumberFormat.getInstance(locale), minDecimals, groups);
        Preconditions.checkArgument((boolean)(scale <= 8), (Object)("decimal cannot be shifted " + String.valueOf(scale) + " places"));
        this.scale = scale;
    }

    @Override
    protected int scale(BigInteger satoshis, int fractionPlaces) {
        BtcFixedFormat.prefixUnitsIndicator(this.numberFormat, this.scale);
        return this.scale;
    }

    @Override
    public int scale() {
        return this.scale;
    }

    public String code() {
        return BtcFixedFormat.prefixCode(this.coinCode(), this.scale);
    }

    public String symbol() {
        return BtcFixedFormat.prefixSymbol(this.coinSymbol(), this.scale);
    }

    public int[] fractionPlaceGroups() {
        Object[] boxedArray = this.decimalGroups.toArray();
        int len = boxedArray.length + 1;
        int[] array = new int[len];
        array[0] = this.minimumFractionDigits;
        for (int i = 1; i < len; ++i) {
            array[i] = (Integer)boxedArray[i - 1];
        }
        return array;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof BtcFixedFormat)) {
            return false;
        }
        BtcFixedFormat other = (BtcFixedFormat)o;
        return super.equals(other) && other.scale() == this.scale() && other.decimalGroups.equals(this.decimalGroups);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode((Object[])new Object[]{super.hashCode(), this.scale});
    }

    private static String prefixLabel(int scale) {
        switch (scale) {
            case 0: {
                return "Coin-";
            }
            case 1: {
                return "Decicoin-";
            }
            case 2: {
                return "Centicoin-";
            }
            case 3: {
                return "Millicoin-";
            }
            case 6: {
                return "Microcoin-";
            }
            case -1: {
                return "Dekacoin-";
            }
            case -2: {
                return "Hectocoin-";
            }
            case -3: {
                return "Kilocoin-";
            }
            case -6: {
                return "Megacoin-";
            }
        }
        return "Fixed (" + String.valueOf(scale) + ") ";
    }

    public String toString() {
        return BtcFixedFormat.prefixLabel(this.scale) + "format " + this.pattern();
    }
}

