/*
 * Decompiled with CFR 0_132.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Objects
 *  com.google.common.base.Preconditions
 *  com.google.common.math.LongMath
 *  com.google.common.primitives.Longs
 */
package org.bitcoinj.utils;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.math.LongMath;
import com.google.common.primitives.Longs;
import java.io.Serializable;
import java.math.BigDecimal;
import org.bitcoinj.core.Monetary;
import org.bitcoinj.utils.MonetaryFormat;

public final class Fiat
implements Monetary,
Comparable<Fiat>,
Serializable {
    public static final int SMALLEST_UNIT_EXPONENT = 4;
    public final long value;
    public final String currencyCode;
    private static final MonetaryFormat FRIENDLY_FORMAT = MonetaryFormat.FIAT.postfixCode();
    private static final MonetaryFormat PLAIN_FORMAT = MonetaryFormat.FIAT.minDecimals(0).repeatOptionalDecimals(1, 4).noCode();

    private Fiat(String currencyCode, long value) {
        this.value = value;
        this.currencyCode = currencyCode;
    }

    public static Fiat valueOf(String currencyCode, long value) {
        return new Fiat(currencyCode, value);
    }

    @Override
    public int smallestUnitExponent() {
        return 4;
    }

    @Override
    public long getValue() {
        return this.value;
    }

    public String getCurrencyCode() {
        return this.currencyCode;
    }

    public static Fiat parseFiat(String currencyCode, String str) {
        try {
            long val = new BigDecimal(str).movePointRight(4).longValueExact();
            return Fiat.valueOf(currencyCode, val);
        }
        catch (ArithmeticException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static Fiat parseFiatInexact(String currencyCode, String str) {
        try {
            long val = new BigDecimal(str).movePointRight(4).longValue();
            return Fiat.valueOf(currencyCode, val);
        }
        catch (ArithmeticException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public Fiat add(Fiat value) {
        Preconditions.checkArgument((boolean)value.currencyCode.equals(this.currencyCode));
        return new Fiat(this.currencyCode, LongMath.checkedAdd((long)this.value, (long)value.value));
    }

    public Fiat subtract(Fiat value) {
        Preconditions.checkArgument((boolean)value.currencyCode.equals(this.currencyCode));
        return new Fiat(this.currencyCode, LongMath.checkedSubtract((long)this.value, (long)value.value));
    }

    public Fiat multiply(long factor) {
        return new Fiat(this.currencyCode, LongMath.checkedMultiply((long)this.value, (long)factor));
    }

    public Fiat divide(long divisor) {
        return new Fiat(this.currencyCode, this.value / divisor);
    }

    public Fiat[] divideAndRemainder(long divisor) {
        return new Fiat[]{new Fiat(this.currencyCode, this.value / divisor), new Fiat(this.currencyCode, this.value % divisor)};
    }

    public long divide(Fiat divisor) {
        Preconditions.checkArgument((boolean)divisor.currencyCode.equals(this.currencyCode));
        return this.value / divisor.value;
    }

    public boolean isPositive() {
        return this.signum() == 1;
    }

    public boolean isNegative() {
        return this.signum() == -1;
    }

    public boolean isZero() {
        return this.signum() == 0;
    }

    public boolean isGreaterThan(Fiat other) {
        return this.compareTo(other) > 0;
    }

    public boolean isLessThan(Fiat other) {
        return this.compareTo(other) < 0;
    }

    @Override
    public int signum() {
        if (this.value == 0L) {
            return 0;
        }
        return this.value < 0L ? -1 : 1;
    }

    public Fiat negate() {
        return new Fiat(this.currencyCode, - this.value);
    }

    public long longValue() {
        return this.value;
    }

    public String toFriendlyString() {
        return FRIENDLY_FORMAT.code(0, this.currencyCode).format(this).toString();
    }

    public String toPlainString() {
        return PLAIN_FORMAT.format(this).toString();
    }

    public String toString() {
        return Long.toString(this.value);
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o == null || o.getClass() != this.getClass()) {
            return false;
        }
        Fiat other = (Fiat)o;
        return this.value == other.value && this.currencyCode.equals(other.currencyCode);
    }

    public int hashCode() {
        return Objects.hashCode((Object[])new Object[]{this.value, this.currencyCode});
    }

    @Override
    public int compareTo(Fiat other) {
        if (!this.currencyCode.equals(other.currencyCode)) {
            return this.currencyCode.compareTo(other.currencyCode);
        }
        return Longs.compare((long)this.value, (long)other.value);
    }
}

