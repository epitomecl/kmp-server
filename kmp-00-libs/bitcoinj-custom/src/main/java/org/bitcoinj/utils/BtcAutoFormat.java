/*
 * Decompiled with CFR 0_132.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 */
package org.bitcoinj.utils;

import com.google.common.collect.ImmutableList;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import org.bitcoinj.utils.BtcFormat;

public final class BtcAutoFormat
extends BtcFormat {
    protected BtcAutoFormat(Locale locale, Style style, int fractionPlaces) {
        super((DecimalFormat)NumberFormat.getCurrencyInstance(locale), fractionPlaces, (List<Integer>)ImmutableList.of());
        style.apply(this.numberFormat);
    }

    @Override
    protected int scale(BigInteger satoshis, int fractionPlaces) {
        int places;
        int coinOffset = Math.max(8 - fractionPlaces, 0);
        BigDecimal inCoins = new BigDecimal(satoshis).movePointLeft(coinOffset);
        if (inCoins.remainder(BigDecimal.ONE).compareTo(BigDecimal.ZERO) == 0) {
            places = 0;
        } else {
            BigDecimal inMillis = inCoins.movePointRight(3);
            if (inMillis.remainder(BigDecimal.ONE).compareTo(BigDecimal.ZERO) == 0) {
                places = 3;
            } else {
                BigDecimal inMicros = inCoins.movePointRight(6);
                if (inMicros.remainder(BigDecimal.ONE).compareTo(BigDecimal.ZERO) == 0) {
                    places = 6;
                } else {
                    BigDecimal a = inCoins.subtract(inCoins.setScale(0, RoundingMode.HALF_UP)).movePointRight(coinOffset).abs();
                    BigDecimal b = inMillis.subtract(inMillis.setScale(0, RoundingMode.HALF_UP)).movePointRight(coinOffset - 3).abs();
                    BigDecimal c = inMicros.subtract(inMicros.setScale(0, RoundingMode.HALF_UP)).movePointRight(coinOffset - 6).abs();
                    places = a.compareTo(b) < 0 ? (a.compareTo(c) < 0 ? 0 : 6) : (b.compareTo(c) < 0 ? 3 : 6);
                }
            }
        }
        BtcAutoFormat.prefixUnitsIndicator(this.numberFormat, places);
        return places;
    }

    @Override
    protected int scale() {
        return 0;
    }

    public int fractionPlaces() {
        return this.minimumFractionDigits;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BtcAutoFormat)) {
            return false;
        }
        return super.equals(o);
    }

    public String toString() {
        return "Auto-format " + this.pattern();
    }

    public static enum Style {
        CODE{

            @Override
            void apply(DecimalFormat decimalFormat) {
                decimalFormat.applyPattern(BtcFormat.negify(decimalFormat.toPattern()).replaceAll("\u00a4", "\u00a4\u00a4").replaceAll("([#0.,E-])\u00a4\u00a4", "$1 \u00a4\u00a4").replaceAll("\u00a4\u00a4([0#.,E-])", "\u00a4\u00a4 $1"));
            }
        }
        ,
        SYMBOL{

            @Override
            void apply(DecimalFormat decimalFormat) {
                decimalFormat.applyPattern(BtcFormat.negify(decimalFormat.toPattern()).replaceAll("\u00a4\u00a4", "\u00a4"));
            }
        };
        

        private Style() {
        }

        abstract void apply(DecimalFormat var1);

    }

}

