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
import java.io.Serializable;
import java.math.BigInteger;
import org.bitcoinj.core.Coin;
import org.bitcoinj.utils.Fiat;

public class ExchangeRate
implements Serializable {
    public final Coin coin;
    public final Fiat fiat;

    public ExchangeRate(Coin coin, Fiat fiat) {
        Preconditions.checkArgument((boolean)coin.isPositive());
        Preconditions.checkArgument((boolean)fiat.isPositive());
        Preconditions.checkArgument((boolean)(fiat.currencyCode != null), (Object)"currency code required");
        this.coin = coin;
        this.fiat = fiat;
    }

    public ExchangeRate(Fiat fiat) {
        this(Coin.COIN, fiat);
    }

    public Fiat coinToFiat(Coin convertCoin) {
        BigInteger converted = BigInteger.valueOf(convertCoin.value).multiply(BigInteger.valueOf(this.fiat.value)).divide(BigInteger.valueOf(this.coin.value));
        if (converted.compareTo(BigInteger.valueOf(Long.MAX_VALUE)) > 0 || converted.compareTo(BigInteger.valueOf(Long.MIN_VALUE)) < 0) {
            throw new ArithmeticException("Overflow");
        }
        return Fiat.valueOf(this.fiat.currencyCode, converted.longValue());
    }

    public Coin fiatToCoin(Fiat convertFiat) {
        Preconditions.checkArgument((boolean)convertFiat.currencyCode.equals(this.fiat.currencyCode), (String)"Currency mismatch: %s vs %s", (Object)convertFiat.currencyCode, (Object)this.fiat.currencyCode);
        BigInteger converted = BigInteger.valueOf(convertFiat.value).multiply(BigInteger.valueOf(this.coin.value)).divide(BigInteger.valueOf(this.fiat.value));
        if (converted.compareTo(BigInteger.valueOf(Long.MAX_VALUE)) > 0 || converted.compareTo(BigInteger.valueOf(Long.MIN_VALUE)) < 0) {
            throw new ArithmeticException("Overflow");
        }
        try {
            return Coin.valueOf(converted.longValue());
        }
        catch (IllegalArgumentException x) {
            throw new ArithmeticException("Overflow: " + x.getMessage());
        }
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        ExchangeRate other = (ExchangeRate)o;
        return Objects.equal((Object)this.coin, (Object)other.coin) && Objects.equal((Object)this.fiat, (Object)other.fiat);
    }

    public int hashCode() {
        return Objects.hashCode((Object[])new Object[]{this.coin, this.fiat});
    }
}

