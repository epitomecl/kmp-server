/*
 * Decompiled with CFR 0_132.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Preconditions
 *  com.google.common.math.LongMath
 *  com.google.common.primitives.Longs
 */
package org.bitcoinj.core;

import com.google.common.base.Preconditions;
import com.google.common.math.LongMath;
import com.google.common.primitives.Longs;
import java.io.Serializable;
import java.math.BigDecimal;
import org.bitcoinj.core.Monetary;
import org.bitcoinj.utils.MonetaryFormat;

public final class Coin
implements Monetary,
Comparable<Coin>,
Serializable {
    public static final int SMALLEST_UNIT_EXPONENT = 8;
    private static final long COIN_VALUE = LongMath.pow((long)10L, (int)8);
    public static final Coin ZERO = Coin.valueOf(0L);
    public static final Coin COIN = Coin.valueOf(COIN_VALUE);
    public static final Coin CENT = COIN.divide(100L);
    public static final Coin MILLICOIN = COIN.divide(1000L);
    public static final Coin MICROCOIN = MILLICOIN.divide(1000L);
    public static final Coin SATOSHI = Coin.valueOf(1L);
    public static final Coin FIFTY_COINS = COIN.multiply(50L);
    public static final Coin NEGATIVE_SATOSHI = Coin.valueOf(-1L);
    public final long value;
    private static final MonetaryFormat FRIENDLY_FORMAT = MonetaryFormat.BTC.minDecimals(2).repeatOptionalDecimals(1, 6).postfixCode();
    private static final MonetaryFormat PLAIN_FORMAT = MonetaryFormat.BTC.minDecimals(0).repeatOptionalDecimals(1, 8).noCode();

    private Coin(long satoshis) {
        this.value = satoshis;
    }

    public static Coin valueOf(long satoshis) {
        return new Coin(satoshis);
    }

    @Override
    public int smallestUnitExponent() {
        return 8;
    }

    @Override
    public long getValue() {
        return this.value;
    }

    public static Coin valueOf(int coins, int cents) {
        Preconditions.checkArgument((boolean)(cents < 100));
        Preconditions.checkArgument((boolean)(cents >= 0));
        Preconditions.checkArgument((boolean)(coins >= 0));
        Coin coin = COIN.multiply(coins).add(CENT.multiply(cents));
        return coin;
    }

    public static Coin parseCoin(String str) {
        try {
            long satoshis = new BigDecimal(str).movePointRight(8).longValueExact();
            return Coin.valueOf(satoshis);
        }
        catch (ArithmeticException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static Coin parseCoinInexact(String str) {
        try {
            long satoshis = new BigDecimal(str).movePointRight(8).longValue();
            return Coin.valueOf(satoshis);
        }
        catch (ArithmeticException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public Coin add(Coin value) {
        return new Coin(LongMath.checkedAdd((long)this.value, (long)value.value));
    }

    public Coin plus(Coin value) {
        return this.add(value);
    }

    public Coin subtract(Coin value) {
        return new Coin(LongMath.checkedSubtract((long)this.value, (long)value.value));
    }

    public Coin minus(Coin value) {
        return this.subtract(value);
    }

    public Coin multiply(long factor) {
        return new Coin(LongMath.checkedMultiply((long)this.value, (long)factor));
    }

    public Coin times(long factor) {
        return this.multiply(factor);
    }

    public Coin times(int factor) {
        return this.multiply(factor);
    }

    public Coin divide(long divisor) {
        return new Coin(this.value / divisor);
    }

    public Coin div(long divisor) {
        return this.divide(divisor);
    }

    public Coin div(int divisor) {
        return this.divide(divisor);
    }

    public Coin[] divideAndRemainder(long divisor) {
        return new Coin[]{new Coin(this.value / divisor), new Coin(this.value % divisor)};
    }

    public long divide(Coin divisor) {
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

    public boolean isGreaterThan(Coin other) {
        return this.compareTo(other) > 0;
    }

    public boolean isLessThan(Coin other) {
        return this.compareTo(other) < 0;
    }

    public Coin shiftLeft(int n) {
        return new Coin(this.value << n);
    }

    public Coin shiftRight(int n) {
        return new Coin(this.value >> n);
    }

    @Override
    public int signum() {
        if (this.value == 0L) {
            return 0;
        }
        return this.value < 0L ? -1 : 1;
    }

    public Coin negate() {
        return new Coin(- this.value);
    }

    public long longValue() {
        return this.value;
    }

    public String toFriendlyString() {
        return FRIENDLY_FORMAT.format(this).toString();
    }

    public String toPlainString() {
        return PLAIN_FORMAT.format(this).toString();
    }

    public String toString() {
        return Long.toString(this.value);
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        return this.value == ((Coin)o).value;
    }

    public int hashCode() {
        return (int)this.value;
    }

    @Override
    public int compareTo(Coin other) {
        return Longs.compare((long)this.value, (long)other.value);
    }
}

