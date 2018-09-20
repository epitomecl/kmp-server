/*
 * Decompiled with CFR 0_132.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Preconditions
 *  com.google.common.math.LongMath
 */
package org.bitcoinj.utils;

import com.google.common.base.Preconditions;
import com.google.common.math.LongMath;
import java.math.RoundingMode;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import org.bitcoinj.core.Coin;
import org.bitcoinj.core.Monetary;
import org.bitcoinj.utils.Fiat;

public final class MonetaryFormat {
    public static final MonetaryFormat BTC = new MonetaryFormat().shift(0).minDecimals(2).repeatOptionalDecimals(2, 3);
    public static final MonetaryFormat MBTC = new MonetaryFormat().shift(3).minDecimals(2).optionalDecimals(2);
    public static final MonetaryFormat UBTC = new MonetaryFormat().shift(6).minDecimals(0).optionalDecimals(2);
    public static final MonetaryFormat FIAT = new MonetaryFormat().shift(0).minDecimals(2).repeatOptionalDecimals(2, 1);
    public static final String CODE_BTC = "BTC";
    public static final String CODE_MBTC = "mBTC";
    public static final String CODE_UBTC = "\u00b5BTC";
    public static final int MAX_DECIMALS = 8;
    private final char negativeSign;
    private final char positiveSign;
    private final char zeroDigit;
    private final char decimalMark;
    private final int minDecimals;
    private final List<Integer> decimalGroups;
    private final int shift;
    private final RoundingMode roundingMode;
    private final String[] codes;
    private final char codeSeparator;
    private final boolean codePrefixed;
    private static final String DECIMALS_PADDING = "0000000000000000";

    public MonetaryFormat negativeSign(char negativeSign) {
        Preconditions.checkArgument((boolean)(!Character.isDigit(negativeSign)));
        Preconditions.checkArgument((boolean)(negativeSign > '\u0000'));
        if (negativeSign == this.negativeSign) {
            return this;
        }
        return new MonetaryFormat(negativeSign, this.positiveSign, this.zeroDigit, this.decimalMark, this.minDecimals, this.decimalGroups, this.shift, this.roundingMode, this.codes, this.codeSeparator, this.codePrefixed);
    }

    public MonetaryFormat positiveSign(char positiveSign) {
        Preconditions.checkArgument((boolean)(!Character.isDigit(positiveSign)));
        if (positiveSign == this.positiveSign) {
            return this;
        }
        return new MonetaryFormat(this.negativeSign, positiveSign, this.zeroDigit, this.decimalMark, this.minDecimals, this.decimalGroups, this.shift, this.roundingMode, this.codes, this.codeSeparator, this.codePrefixed);
    }

    public MonetaryFormat digits(char zeroDigit) {
        if (zeroDigit == this.zeroDigit) {
            return this;
        }
        return new MonetaryFormat(this.negativeSign, this.positiveSign, zeroDigit, this.decimalMark, this.minDecimals, this.decimalGroups, this.shift, this.roundingMode, this.codes, this.codeSeparator, this.codePrefixed);
    }

    public MonetaryFormat decimalMark(char decimalMark) {
        Preconditions.checkArgument((boolean)(!Character.isDigit(decimalMark)));
        Preconditions.checkArgument((boolean)(decimalMark > '\u0000'));
        if (decimalMark == this.decimalMark) {
            return this;
        }
        return new MonetaryFormat(this.negativeSign, this.positiveSign, this.zeroDigit, decimalMark, this.minDecimals, this.decimalGroups, this.shift, this.roundingMode, this.codes, this.codeSeparator, this.codePrefixed);
    }

    public MonetaryFormat minDecimals(int minDecimals) {
        if (minDecimals == this.minDecimals) {
            return this;
        }
        return new MonetaryFormat(this.negativeSign, this.positiveSign, this.zeroDigit, this.decimalMark, minDecimals, this.decimalGroups, this.shift, this.roundingMode, this.codes, this.codeSeparator, this.codePrefixed);
    }

    public /* varargs */ MonetaryFormat optionalDecimals(int ... groups) {
        ArrayList<Integer> decimalGroups = new ArrayList<Integer>(groups.length);
        for (int group : groups) {
            decimalGroups.add(group);
        }
        return new MonetaryFormat(this.negativeSign, this.positiveSign, this.zeroDigit, this.decimalMark, this.minDecimals, decimalGroups, this.shift, this.roundingMode, this.codes, this.codeSeparator, this.codePrefixed);
    }

    public MonetaryFormat repeatOptionalDecimals(int decimals, int repetitions) {
        Preconditions.checkArgument((boolean)(repetitions >= 0));
        ArrayList<Integer> decimalGroups = new ArrayList<Integer>(repetitions);
        for (int i = 0; i < repetitions; ++i) {
            decimalGroups.add(decimals);
        }
        return new MonetaryFormat(this.negativeSign, this.positiveSign, this.zeroDigit, this.decimalMark, this.minDecimals, decimalGroups, this.shift, this.roundingMode, this.codes, this.codeSeparator, this.codePrefixed);
    }

    public MonetaryFormat shift(int shift) {
        if (shift == this.shift) {
            return this;
        }
        return new MonetaryFormat(this.negativeSign, this.positiveSign, this.zeroDigit, this.decimalMark, this.minDecimals, this.decimalGroups, shift, this.roundingMode, this.codes, this.codeSeparator, this.codePrefixed);
    }

    public MonetaryFormat roundingMode(RoundingMode roundingMode) {
        if (roundingMode == this.roundingMode) {
            return this;
        }
        return new MonetaryFormat(this.negativeSign, this.positiveSign, this.zeroDigit, this.decimalMark, this.minDecimals, this.decimalGroups, this.shift, roundingMode, this.codes, this.codeSeparator, this.codePrefixed);
    }

    public MonetaryFormat noCode() {
        if (this.codes == null) {
            return this;
        }
        return new MonetaryFormat(this.negativeSign, this.positiveSign, this.zeroDigit, this.decimalMark, this.minDecimals, this.decimalGroups, this.shift, this.roundingMode, null, this.codeSeparator, this.codePrefixed);
    }

    public MonetaryFormat code(int codeShift, String code) {
        Preconditions.checkArgument((boolean)(codeShift >= 0));
        String[] codes = null == this.codes ? new String[8] : Arrays.copyOf(this.codes, this.codes.length);
        codes[codeShift] = code;
        return new MonetaryFormat(this.negativeSign, this.positiveSign, this.zeroDigit, this.decimalMark, this.minDecimals, this.decimalGroups, this.shift, this.roundingMode, codes, this.codeSeparator, this.codePrefixed);
    }

    public MonetaryFormat codeSeparator(char codeSeparator) {
        Preconditions.checkArgument((boolean)(!Character.isDigit(codeSeparator)));
        Preconditions.checkArgument((boolean)(codeSeparator > '\u0000'));
        if (codeSeparator == this.codeSeparator) {
            return this;
        }
        return new MonetaryFormat(this.negativeSign, this.positiveSign, this.zeroDigit, this.decimalMark, this.minDecimals, this.decimalGroups, this.shift, this.roundingMode, this.codes, codeSeparator, this.codePrefixed);
    }

    public MonetaryFormat prefixCode() {
        if (this.codePrefixed) {
            return this;
        }
        return new MonetaryFormat(this.negativeSign, this.positiveSign, this.zeroDigit, this.decimalMark, this.minDecimals, this.decimalGroups, this.shift, this.roundingMode, this.codes, this.codeSeparator, true);
    }

    public MonetaryFormat postfixCode() {
        if (!this.codePrefixed) {
            return this;
        }
        return new MonetaryFormat(this.negativeSign, this.positiveSign, this.zeroDigit, this.decimalMark, this.minDecimals, this.decimalGroups, this.shift, this.roundingMode, this.codes, this.codeSeparator, false);
    }

    public MonetaryFormat withLocale(Locale locale) {
        DecimalFormatSymbols dfs = new DecimalFormatSymbols(locale);
        char negativeSign = dfs.getMinusSign();
        char zeroDigit = dfs.getZeroDigit();
        char decimalMark = dfs.getMonetaryDecimalSeparator();
        return new MonetaryFormat(negativeSign, this.positiveSign, zeroDigit, decimalMark, this.minDecimals, this.decimalGroups, this.shift, this.roundingMode, this.codes, this.codeSeparator, this.codePrefixed);
    }

    public MonetaryFormat() {
        this.negativeSign = (char)45;
        this.positiveSign = '\u0000';
        this.zeroDigit = (char)48;
        this.decimalMark = (char)46;
        this.minDecimals = 2;
        this.decimalGroups = null;
        this.shift = 0;
        this.roundingMode = RoundingMode.HALF_UP;
        this.codes = new String[8];
        this.codes[0] = CODE_BTC;
        this.codes[3] = CODE_MBTC;
        this.codes[6] = CODE_UBTC;
        this.codeSeparator = (char)32;
        this.codePrefixed = true;
    }

    private MonetaryFormat(char negativeSign, char positiveSign, char zeroDigit, char decimalMark, int minDecimals, List<Integer> decimalGroups, int shift, RoundingMode roundingMode, String[] codes, char codeSeparator, boolean codePrefixed) {
        this.negativeSign = negativeSign;
        this.positiveSign = positiveSign;
        this.zeroDigit = zeroDigit;
        this.decimalMark = decimalMark;
        this.minDecimals = minDecimals;
        this.decimalGroups = decimalGroups;
        this.shift = shift;
        this.roundingMode = roundingMode;
        this.codes = codes;
        this.codeSeparator = codeSeparator;
        this.codePrefixed = codePrefixed;
    }

    public CharSequence format(Monetary monetary) {
        int smallestUnitExponent;
        int maxDecimals = this.minDecimals;
        if (this.decimalGroups != null) {
            Iterator<Integer> iterator = this.decimalGroups.iterator();
            while (iterator.hasNext()) {
                int group = iterator.next();
                maxDecimals += group;
            }
        }
        Preconditions.checkState((boolean)(maxDecimals <= (smallestUnitExponent = monetary.smallestUnitExponent())), (String)"The maximum possible number of decimals (%s) cannot exceed %s.", (int)maxDecimals, (int)smallestUnitExponent);
        long satoshis = Math.abs(monetary.getValue());
        long precisionDivisor = LongMath.checkedPow((long)10L, (int)(smallestUnitExponent - this.shift - maxDecimals));
        satoshis = LongMath.checkedMultiply((long)LongMath.divide((long)satoshis, (long)precisionDivisor, (RoundingMode)this.roundingMode), (long)precisionDivisor);
        long shiftDivisor = LongMath.checkedPow((long)10L, (int)(smallestUnitExponent - this.shift));
        long numbers = satoshis / shiftDivisor;
        long decimals = satoshis % shiftDivisor;
        String decimalsStr = String.format(Locale.US, "%0" + (smallestUnitExponent - this.shift) + "d", decimals);
        StringBuilder str = new StringBuilder(decimalsStr);
        while (str.length() > this.minDecimals && str.charAt(str.length() - 1) == '0') {
            str.setLength(str.length() - 1);
        }
        int i = this.minDecimals;
        if (this.decimalGroups != null) {
            Iterator<Integer> iterator = this.decimalGroups.iterator();
            while (iterator.hasNext()) {
                int group = iterator.next();
                if (str.length() > i && str.length() < i + group) {
                    while (str.length() < i + group) {
                        str.append('0');
                    }
                    break;
                }
                i += group;
            }
        }
        if (str.length() > 0) {
            str.insert(0, this.decimalMark);
        }
        str.insert(0, numbers);
        if (monetary.getValue() < 0L) {
            str.insert(0, this.negativeSign);
        } else if (this.positiveSign != '\u0000') {
            str.insert(0, this.positiveSign);
        }
        if (this.codes != null) {
            if (this.codePrefixed) {
                str.insert(0, this.codeSeparator);
                str.insert(0, this.code());
            } else {
                str.append(this.codeSeparator);
                str.append(this.code());
            }
        }
        if (this.zeroDigit != '0') {
            int offset = this.zeroDigit - 48;
            for (int d = 0; d < str.length(); ++d) {
                char c = str.charAt(d);
                if (!Character.isDigit(c)) continue;
                str.setCharAt(d, (char)(c + offset));
            }
        }
        return str;
    }

    public Coin parse(String str) throws NumberFormatException {
        return Coin.valueOf(this.parseValue(str, 8));
    }

    public Fiat parseFiat(String currencyCode, String str) throws NumberFormatException {
        return Fiat.valueOf(currencyCode, this.parseValue(str, 4));
    }

    private long parseValue(String str, int smallestUnitExponent) {
        String decimals;
        int decimalMarkIndex;
        String numbers;
        Preconditions.checkState((boolean)(DECIMALS_PADDING.length() >= smallestUnitExponent));
        if (str.isEmpty()) {
            throw new NumberFormatException("empty string");
        }
        char first = str.charAt(0);
        if (first == this.negativeSign || first == this.positiveSign) {
            str = str.substring(1);
        }
        if ((decimalMarkIndex = str.indexOf(this.decimalMark)) != -1) {
            numbers = str.substring(0, decimalMarkIndex);
            decimals = (str + DECIMALS_PADDING).substring(decimalMarkIndex + 1);
            if (decimals.indexOf(this.decimalMark) != -1) {
                throw new NumberFormatException("more than one decimal mark");
            }
        } else {
            numbers = str;
            decimals = DECIMALS_PADDING;
        }
        String satoshis = numbers + decimals.substring(0, smallestUnitExponent - this.shift);
        for (char c : satoshis.toCharArray()) {
            if (Character.isDigit(c)) continue;
            throw new NumberFormatException("illegal character: " + c);
        }
        long value = Long.parseLong(satoshis);
        if (first == this.negativeSign) {
            value = - value;
        }
        return value;
    }

    public String code() {
        if (this.codes == null) {
            return null;
        }
        if (this.codes[this.shift] == null) {
            throw new NumberFormatException("missing code for shift: " + this.shift);
        }
        return this.codes[this.shift];
    }
}

