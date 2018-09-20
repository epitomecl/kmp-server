/*
 * Decompiled with CFR 0_132.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Objects
 *  com.google.common.base.Preconditions
 *  com.google.common.base.Strings
 *  com.google.common.collect.ImmutableList
 */
package org.bitcoinj.utils;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableList;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.text.AttributedCharacterIterator;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.FieldPosition;
import java.text.Format;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.bitcoinj.core.Coin;
import org.bitcoinj.utils.BtcAutoFormat;
import org.bitcoinj.utils.BtcFixedFormat;

public abstract class BtcFormat
extends Format {
    private static final String COIN_CODE = "BTC";
    private static final String COIN_SYMBOL = "\u0e3f";
    protected static final String COIN_SYMBOL_ALT = "\u0243";
    protected final DecimalFormat numberFormat;
    protected final int minimumFractionDigits;
    protected final List<Integer> decimalGroups;
    public static final int COIN_SCALE = 0;
    public static final int MILLICOIN_SCALE = 3;
    public static final int MICROCOIN_SCALE = 6;
    private volatile String ci = "(\u0e3f|\u0243|B\u20e6|BTC|XBT)";
    private Pattern coinPattern;
    private volatile ScaleMatcher[] denoms;

    private static int offSatoshis(int scale) {
        return 8 - scale;
    }

    private static Locale defaultLocale() {
        return Locale.getDefault();
    }

    public static Builder builder() {
        return new Builder();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected BtcFormat(DecimalFormat numberFormat, int minDecimals, List<Integer> groups) {
        Preconditions.checkArgument((boolean)(minDecimals >= 0), (Object)"There can be no fewer than zero fractional decimal places");
        this.numberFormat = numberFormat;
        this.numberFormat.setParseBigDecimal(true);
        this.numberFormat.setRoundingMode(RoundingMode.HALF_UP);
        this.minimumFractionDigits = minDecimals;
        this.numberFormat.setMinimumFractionDigits(this.minimumFractionDigits);
        this.numberFormat.setMaximumFractionDigits(this.minimumFractionDigits);
        this.decimalGroups = groups;
        DecimalFormat decimalFormat = this.numberFormat;
        synchronized (decimalFormat) {
            BtcFormat.setSymbolAndCode(this.numberFormat, this.numberFormat.getDecimalFormatSymbols().getCurrencySymbol().contains(COIN_SYMBOL) ? COIN_SYMBOL_ALT : COIN_SYMBOL, COIN_CODE);
        }
    }

    public static BtcFormat getInstance() {
        return BtcFormat.getInstance(BtcFormat.defaultLocale());
    }

    public static BtcFormat getSymbolInstance() {
        return BtcFormat.getSymbolInstance(BtcFormat.defaultLocale());
    }

    public static BtcFormat getCodeInstance() {
        return BtcFormat.getCodeInstance(BtcFormat.defaultLocale());
    }

    public static BtcFormat getSymbolInstance(int fractionPlaces) {
        return BtcFormat.getSymbolInstance(BtcFormat.defaultLocale(), fractionPlaces);
    }

    public static BtcFormat getCodeInstance(int minDecimals) {
        return BtcFormat.getCodeInstance(BtcFormat.defaultLocale(), minDecimals);
    }

    public static BtcFormat getInstance(Locale locale) {
        return BtcFormat.getCodeInstance(locale);
    }

    public static BtcFormat getCodeInstance(Locale locale) {
        return BtcFormat.getInstance(BtcAutoFormat.Style.CODE, locale);
    }

    public static BtcFormat getInstance(Locale locale, int minDecimals) {
        return BtcFormat.getCodeInstance(locale, minDecimals);
    }

    public static BtcFormat getCodeInstance(Locale locale, int minDecimals) {
        return BtcFormat.getInstance(BtcAutoFormat.Style.CODE, locale, minDecimals);
    }

    public static BtcFormat getSymbolInstance(Locale locale) {
        return BtcFormat.getInstance(BtcAutoFormat.Style.SYMBOL, locale);
    }

    public static BtcFormat getSymbolInstance(Locale locale, int fractionPlaces) {
        return BtcFormat.getInstance(BtcAutoFormat.Style.SYMBOL, locale, fractionPlaces);
    }

    public static BtcFormat getInstance(BtcAutoFormat.Style style) {
        return BtcFormat.getInstance(style, BtcFormat.defaultLocale());
    }

    public static BtcFormat getInstance(BtcAutoFormat.Style style, int fractionPlaces) {
        return BtcFormat.getInstance(style, BtcFormat.defaultLocale(), fractionPlaces);
    }

    public static BtcFormat getInstance(BtcAutoFormat.Style style, Locale locale) {
        return BtcFormat.getInstance(style, locale, 2);
    }

    public static BtcFormat getInstance(BtcAutoFormat.Style style, Locale locale, int fractionPlaces) {
        return new BtcAutoFormat(locale, style, fractionPlaces);
    }

    public static BtcFormat getCoinInstance() {
        return BtcFormat.getCoinInstance(BtcFormat.defaultLocale());
    }

    private static List<Integer> boxAsList(int[] elements) throws IllegalArgumentException {
        ArrayList<Integer> list = new ArrayList<Integer>(elements.length);
        for (int e : elements) {
            Preconditions.checkArgument((boolean)(e > 0), (Object)"Size of decimal group must be at least one.");
            list.add(e);
        }
        return list;
    }

    public static /* varargs */ BtcFormat getCoinInstance(int minFractionPlaces, int ... groups) {
        return BtcFormat.getInstance(0, BtcFormat.defaultLocale(), minFractionPlaces, BtcFormat.boxAsList(groups));
    }

    public static BtcFormat getCoinInstance(Locale locale) {
        return BtcFormat.getInstance(0, locale, 2, new int[0]);
    }

    public static /* varargs */ BtcFormat getCoinInstance(Locale locale, int scale, int ... groups) {
        return BtcFormat.getInstance(0, locale, scale, BtcFormat.boxAsList(groups));
    }

    public static BtcFormat getMilliInstance() {
        return BtcFormat.getMilliInstance(BtcFormat.defaultLocale());
    }

    public static BtcFormat getMilliInstance(Locale locale) {
        return BtcFormat.getInstance(3, locale, 2, new int[0]);
    }

    public static /* varargs */ BtcFormat getMilliInstance(int scale, int ... groups) {
        return BtcFormat.getInstance(3, BtcFormat.defaultLocale(), scale, BtcFormat.boxAsList(groups));
    }

    public static /* varargs */ BtcFormat getMilliInstance(Locale locale, int scale, int ... groups) {
        return BtcFormat.getInstance(3, locale, scale, BtcFormat.boxAsList(groups));
    }

    public static BtcFormat getMicroInstance() {
        return BtcFormat.getMicroInstance(BtcFormat.defaultLocale());
    }

    public static BtcFormat getMicroInstance(Locale locale) {
        return BtcFormat.getInstance(6, locale);
    }

    public static /* varargs */ BtcFormat getMicroInstance(int scale, int ... groups) {
        return BtcFormat.getInstance(6, BtcFormat.defaultLocale(), scale, BtcFormat.boxAsList(groups));
    }

    public static /* varargs */ BtcFormat getMicroInstance(Locale locale, int scale, int ... groups) {
        return BtcFormat.getInstance(6, locale, scale, BtcFormat.boxAsList(groups));
    }

    public static /* varargs */ BtcFormat getInstance(int scale, int minDecimals, int ... groups) {
        return BtcFormat.getInstance(scale, BtcFormat.defaultLocale(), minDecimals, BtcFormat.boxAsList(groups));
    }

    public static BtcFormat getInstance(int scale) {
        return BtcFormat.getInstance(scale, BtcFormat.defaultLocale());
    }

    public static BtcFormat getInstance(int scale, Locale locale) {
        return BtcFormat.getInstance(scale, locale, 2, new int[0]);
    }

    public static /* varargs */ BtcFormat getInstance(int scale, Locale locale, int minDecimals, int ... groups) {
        return BtcFormat.getInstance(scale, locale, minDecimals, BtcFormat.boxAsList(groups));
    }

    public static BtcFormat getInstance(int scale, Locale locale, int minDecimals, List<Integer> groups) {
        return new BtcFixedFormat(locale, scale, minDecimals, groups);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public AttributedCharacterIterator formatToCharacterIterator(Object obj) {
        DecimalFormat decimalFormat = this.numberFormat;
        synchronized (decimalFormat) {
            DecimalFormatSymbols anteSigns = this.numberFormat.getDecimalFormatSymbols();
            BigDecimal units = this.denominateAndRound(BtcFormat.inSatoshis(obj), this.minimumFractionDigits, this.decimalGroups);
            ImmutableList<Integer> anteDigits = BtcFormat.setFormatterDigits(this.numberFormat, units.scale(), units.scale());
            AttributedCharacterIterator i = this.numberFormat.formatToCharacterIterator(units);
            this.numberFormat.setDecimalFormatSymbols(anteSigns);
            BtcFormat.setFormatterDigits(this.numberFormat, anteDigits.get(0), anteDigits.get(1));
            return i;
        }
    }

    @Override
    public StringBuffer format(Object qty, StringBuffer toAppendTo, FieldPosition pos) {
        return this.format(qty, toAppendTo, pos, this.minimumFractionDigits, this.decimalGroups);
    }

    public /* varargs */ String format(Object qty, int minDecimals, int ... fractionGroups) {
        return this.format(qty, new StringBuffer(), new FieldPosition(0), minDecimals, BtcFormat.boxAsList(fractionGroups)).toString();
    }

    public /* varargs */ StringBuffer format(Object qty, StringBuffer toAppendTo, FieldPosition pos, int minDecimals, int ... fractionGroups) {
        return this.format(qty, toAppendTo, pos, minDecimals, BtcFormat.boxAsList(fractionGroups));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private StringBuffer format(Object qty, StringBuffer toAppendTo, FieldPosition pos, int minDecimals, List<Integer> fractionGroups) {
        Preconditions.checkArgument((boolean)(minDecimals >= 0), (Object)"There can be no fewer than zero fractional decimal places");
        DecimalFormat decimalFormat = this.numberFormat;
        synchronized (decimalFormat) {
            DecimalFormatSymbols anteSigns = this.numberFormat.getDecimalFormatSymbols();
            BigDecimal denominatedUnitCount = this.denominateAndRound(BtcFormat.inSatoshis(qty), minDecimals, fractionGroups);
            ImmutableList<Integer> antePlaces = BtcFormat.setFormatterDigits(this.numberFormat, denominatedUnitCount.scale(), denominatedUnitCount.scale());
            StringBuffer s = this.numberFormat.format((Object)denominatedUnitCount, toAppendTo, pos);
            this.numberFormat.setDecimalFormatSymbols(anteSigns);
            BtcFormat.setFormatterDigits(this.numberFormat, antePlaces.get(0), antePlaces.get(1));
            return s;
        }
    }

    protected abstract int scale(BigInteger var1, int var2);

    protected abstract int scale();

    private BigDecimal denominateAndRound(BigInteger satoshis, int minDecimals, List<Integer> fractionGroups) {
        int scale = this.scale(satoshis, minDecimals);
        BigDecimal denominatedUnitCount = new BigDecimal(satoshis).movePointLeft(BtcFormat.offSatoshis(scale));
        int places = BtcFormat.calculateFractionPlaces(denominatedUnitCount, scale, minDecimals, fractionGroups);
        return denominatedUnitCount.setScale(places, RoundingMode.HALF_UP);
    }

    private static ImmutableList<Integer> setFormatterDigits(DecimalFormat formatter, int min, int max) {
        ImmutableList ante = ImmutableList.of((Object)formatter.getMinimumFractionDigits(), (Object)formatter.getMaximumFractionDigits());
        formatter.setMinimumFractionDigits(min);
        formatter.setMaximumFractionDigits(max);
        return ante;
    }

    private static int calculateFractionPlaces(BigDecimal unitCount, int scale, int minDecimals, List<Integer> fractionGroups) {
        int places = minDecimals;
        Iterator<Integer> iterator = fractionGroups.iterator();
        while (iterator.hasNext()) {
            int group = iterator.next();
            places += group;
        }
        int max = Math.min(places, BtcFormat.offSatoshis(scale));
        places = Math.min(minDecimals, max);
        Iterator<Integer> group = fractionGroups.iterator();
        while (group.hasNext()) {
            int group2 = group.next();
            if (unitCount.setScale(places, RoundingMode.HALF_UP).compareTo(unitCount.setScale(max, RoundingMode.HALF_UP)) == 0) break;
            if ((places += group2) <= max) continue;
            places = max;
        }
        return places;
    }

    private static BigInteger inSatoshis(Object qty) {
        BigInteger satoshis;
        if (qty instanceof Long || qty instanceof Integer) {
            satoshis = BigInteger.valueOf(((Number)qty).longValue());
        } else if (qty instanceof BigInteger) {
            satoshis = (BigInteger)qty;
        } else if (qty instanceof BigDecimal) {
            satoshis = ((BigDecimal)qty).movePointRight(8).setScale(0, 4).unscaledValue();
        } else if (qty instanceof Coin) {
            satoshis = BigInteger.valueOf(((Coin)qty).value);
        } else {
            throw new IllegalArgumentException("Cannot format a " + qty.getClass().getSimpleName() + " as a Bicoin value");
        }
        return satoshis;
    }

    @Override
    public final Object parseObject(String source, ParsePosition pos) {
        return this.parse(source, pos);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    ScaleMatcher[] denomMatchers() {
        ScaleMatcher[] result = this.denoms;
        if (result == null) {
            BtcFormat btcFormat = this;
            synchronized (btcFormat) {
                result = this.denoms;
                if (result == null) {
                    if (!this.coinSymbol().matches(this.ci)) {
                        this.ci = this.ci.replaceFirst("\\(", "(" + this.coinSymbol() + "|");
                    }
                    if (!this.coinCode().matches(this.ci)) {
                        this.ci = this.ci.replaceFirst("\\)", "|" + this.coinCode() + ")");
                    }
                    this.coinPattern = Pattern.compile(this.ci + "?");
                    this.denoms = new ScaleMatcher[]{new ScaleMatcher(Pattern.compile("\u00a2" + this.ci + "?|c" + this.ci), 2), new ScaleMatcher(Pattern.compile("\u20a5" + this.ci + "?|m" + this.ci), 3), new ScaleMatcher(Pattern.compile("([\u00b5u]" + this.ci + ")"), 6), new ScaleMatcher(Pattern.compile("(da" + this.ci + ")"), -1), new ScaleMatcher(Pattern.compile("(h" + this.ci + ")"), -2), new ScaleMatcher(Pattern.compile("(k" + this.ci + ")"), -3), new ScaleMatcher(Pattern.compile("(M" + this.ci + ")"), -6)};
                    result = this.denoms;
                }
            }
        }
        return result;
    }

    private static DecimalFormatSymbols setSymbolAndCode(DecimalFormat numberFormat, String sign) {
        return BtcFormat.setSymbolAndCode(numberFormat, sign, sign);
    }

    private static DecimalFormatSymbols setSymbolAndCode(DecimalFormat numberFormat, String symbol, String code) {
        Preconditions.checkState((boolean)Thread.holdsLock(numberFormat));
        DecimalFormatSymbols fs = numberFormat.getDecimalFormatSymbols();
        DecimalFormatSymbols ante = (DecimalFormatSymbols)fs.clone();
        fs.setInternationalCurrencySymbol(code);
        fs.setCurrencySymbol(symbol);
        numberFormat.setDecimalFormatSymbols(fs);
        return ante;
    }

    protected static void prefixUnitsIndicator(DecimalFormat numberFormat, int scale) {
        Preconditions.checkState((boolean)Thread.holdsLock(numberFormat));
        DecimalFormatSymbols fs = numberFormat.getDecimalFormatSymbols();
        BtcFormat.setSymbolAndCode(numberFormat, BtcFormat.prefixSymbol(fs.getCurrencySymbol(), scale), BtcFormat.prefixCode(fs.getInternationalCurrencySymbol(), scale));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public Coin parse(String source, ParsePosition pos) {
        DecimalFormatSymbols anteSigns = null;
        int parseScale = 0;
        Coin coin = null;
        DecimalFormat decimalFormat = this.numberFormat;
        synchronized (decimalFormat) {
            Number number;
            if (this.numberFormat.toPattern().contains("\u00a4")) {
                for (ScaleMatcher d : this.denomMatchers()) {
                    Matcher matcher = d.pattern.matcher(source);
                    if (!matcher.find()) continue;
                    anteSigns = BtcFormat.setSymbolAndCode(this.numberFormat, matcher.group());
                    parseScale = d.scale;
                    break;
                }
                if (parseScale == 0) {
                    Matcher matcher = this.coinPattern.matcher(source);
                    matcher.find();
                    anteSigns = BtcFormat.setSymbolAndCode(this.numberFormat, matcher.group());
                }
            } else {
                parseScale = this.scale();
            }
            if ((number = this.numberFormat.parse(source, pos)) != null) {
                try {
                    coin = Coin.valueOf(((BigDecimal)number).movePointRight(BtcFormat.offSatoshis(parseScale)).setScale(0, RoundingMode.HALF_UP).longValue());
                }
                catch (IllegalArgumentException e) {
                    pos.setIndex(0);
                }
            }
            if (anteSigns != null) {
                this.numberFormat.setDecimalFormatSymbols(anteSigns);
            }
        }
        return coin;
    }

    public Coin parse(String source) throws ParseException {
        return (Coin)this.parseObject(source);
    }

    protected static String prefixCode(String code, int scale) {
        switch (scale) {
            case 0: {
                return code;
            }
            case 1: {
                return "d" + code;
            }
            case 2: {
                return "c" + code;
            }
            case 3: {
                return "m" + code;
            }
            case 6: {
                return "\u00b5" + code;
            }
            case -1: {
                return "da" + code;
            }
            case -2: {
                return "h" + code;
            }
            case -3: {
                return "k" + code;
            }
            case -6: {
                return "M" + code;
            }
        }
        throw new IllegalStateException("No known prefix for scale " + String.valueOf(scale));
    }

    protected static String prefixSymbol(String symbol, int scale) {
        switch (scale) {
            case 0: {
                return symbol;
            }
            case 1: {
                return "d" + symbol;
            }
            case 2: {
                return "\u00a2" + symbol;
            }
            case 3: {
                return "\u20a5" + symbol;
            }
            case 6: {
                return "\u00b5" + symbol;
            }
            case -1: {
                return "da" + symbol;
            }
            case -2: {
                return "h" + symbol;
            }
            case -3: {
                return "k" + symbol;
            }
            case -6: {
                return "M" + symbol;
            }
        }
        throw new IllegalStateException("No known prefix for scale " + String.valueOf(scale));
    }

    protected static String negify(String pattern) {
        if (pattern.contains(";")) {
            return pattern;
        }
        if (pattern.contains("-")) {
            throw new IllegalStateException("Positive pattern contains negative sign");
        }
        return pattern + ";" + pattern.replaceFirst("^([^#0,.']*('[^']*')?)*", "$0-");
    }

    public static Locale[] getAvailableLocales() {
        return NumberFormat.getAvailableLocales();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public String coinSymbol() {
        DecimalFormat decimalFormat = this.numberFormat;
        synchronized (decimalFormat) {
            return this.numberFormat.getDecimalFormatSymbols().getCurrencySymbol();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public String coinCode() {
        DecimalFormat decimalFormat = this.numberFormat;
        synchronized (decimalFormat) {
            return this.numberFormat.getDecimalFormatSymbols().getInternationalCurrencySymbol();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public String pattern() {
        DecimalFormat decimalFormat = this.numberFormat;
        synchronized (decimalFormat) {
            StringBuilder groups = new StringBuilder();
            Iterator<Integer> iterator = this.decimalGroups.iterator();
            while (iterator.hasNext()) {
                int group = iterator.next();
                groups.append("(").append(Strings.repeat((String)"#", (int)group)).append(")");
            }
            DecimalFormatSymbols s = this.numberFormat.getDecimalFormatSymbols();
            String digit = String.valueOf(s.getDigit());
            String exp = s.getExponentSeparator();
            String groupSep = String.valueOf(s.getGroupingSeparator());
            String moneySep = String.valueOf(s.getMonetaryDecimalSeparator());
            String zero = String.valueOf(s.getZeroDigit());
            String boundary = String.valueOf(s.getPatternSeparator());
            String minus = String.valueOf(s.getMinusSign());
            String decSep = String.valueOf(s.getDecimalSeparator());
            String prefixAndNumber = "(^|" + boundary + ")([^" + Matcher.quoteReplacement(new StringBuilder().append(digit).append(zero).append(groupSep).append(decSep).append(moneySep).toString()) + "']*('[^']*')?)*[" + Matcher.quoteReplacement(new StringBuilder().append(digit).append(zero).append(groupSep).append(decSep).append(moneySep).append(exp).toString()) + "]+";
            return this.numberFormat.toLocalizedPattern().replaceAll(prefixAndNumber, "$0" + groups.toString()).replaceAll("\u00a4\u00a4", Matcher.quoteReplacement(this.coinCode())).replaceAll("\u00a4", Matcher.quoteReplacement(this.coinSymbol()));
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public DecimalFormatSymbols symbols() {
        DecimalFormat decimalFormat = this.numberFormat;
        synchronized (decimalFormat) {
            return this.numberFormat.getDecimalFormatSymbols();
        }
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof BtcFormat)) {
            return false;
        }
        BtcFormat other = (BtcFormat)o;
        return other.pattern().equals(this.pattern()) && other.symbols().equals(this.symbols()) && other.minimumFractionDigits == this.minimumFractionDigits;
    }

    public int hashCode() {
        return Objects.hashCode((Object[])new Object[]{this.pattern(), this.symbols(), this.minimumFractionDigits, this.decimalGroups});
    }

    static /* synthetic */ Locale access$600() {
        return BtcFormat.defaultLocale();
    }

    private class ScaleMatcher {
        public Pattern pattern;
        public int scale;

        ScaleMatcher(Pattern p, int s) {
            this.pattern = p;
            this.scale = s;
        }
    }

    public static class Builder {
        private Variant variant = Variant.UNSET;
        private Locale locale = BtcFormat.access$600();
        private int minimumFractionDigits = 2;
        private int[] fractionGroups = new int[0];
        private BtcAutoFormat.Style style = BtcAutoFormat.Style.CODE;
        private int scale = 0;
        private String symbol = "";
        private String code = "";
        private String pattern = "";
        private String localizedPattern = "";

        private Builder() {
        }

        public Builder style(BtcAutoFormat.Style val) {
            if (this.variant == Variant.FIXED) {
                throw new IllegalStateException("You cannot invoke both style() and scale()");
            }
            this.variant = Variant.AUTO;
            this.style = val;
            return this;
        }

        public Builder fractionDigits(int val) {
            return this.minimumFractionDigits(val);
        }

        public Builder scale(int val) {
            if (this.variant == Variant.AUTO) {
                throw new IllegalStateException("You cannot invoke both scale() and style()");
            }
            this.variant = Variant.FIXED;
            this.scale = val;
            return this;
        }

        public Builder minimumFractionDigits(int val) {
            this.minimumFractionDigits = val;
            return this;
        }

        public /* varargs */ Builder fractionGroups(int ... val) {
            this.fractionGroups = val;
            return this;
        }

        public Builder locale(Locale val) {
            this.locale = val;
            return this;
        }

        public Builder symbol(String val) {
            this.symbol = val;
            return this;
        }

        public Builder code(String val) {
            this.code = val;
            return this;
        }

        public Builder pattern(String val) {
            if (this.localizedPattern != "") {
                throw new IllegalStateException("You cannot invoke both pattern() and localizedPattern()");
            }
            this.pattern = val;
            return this;
        }

        public Builder localizedPattern(String val) {
            if (this.pattern != "") {
                throw new IllegalStateException("You cannot invoke both pattern() and localizedPattern().");
            }
            this.localizedPattern = val;
            return this;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        public BtcFormat build() {
            BtcFormat f = this.variant.newInstance(this);
            if (this.symbol != "" || this.code != "") {
                DecimalFormat decimalFormat = f.numberFormat;
                synchronized (decimalFormat) {
                    DecimalFormatSymbols defaultSigns = f.numberFormat.getDecimalFormatSymbols();
                    BtcFormat.setSymbolAndCode(f.numberFormat, this.symbol != "" ? this.symbol : defaultSigns.getCurrencySymbol(), this.code != "" ? this.code : defaultSigns.getInternationalCurrencySymbol());
                }
            }
            if (this.localizedPattern != "" || this.pattern != "") {
                int places = f.numberFormat.getMinimumFractionDigits();
                if (this.localizedPattern != "") {
                    f.numberFormat.applyLocalizedPattern(BtcFormat.negify(this.localizedPattern));
                } else {
                    f.numberFormat.applyPattern(BtcFormat.negify(this.pattern));
                }
                f.numberFormat.setMinimumFractionDigits(places);
                f.numberFormat.setMaximumFractionDigits(places);
            }
            return f;
        }

        private static enum Variant {
            AUTO{

                @Override
                BtcFormat newInstance(Builder b) {
                    return BtcFormat.getInstance(b.style, b.locale, b.minimumFractionDigits);
                }
            }
            ,
            FIXED,
            UNSET;
            

            private Variant() {
            }

            BtcFormat newInstance(Builder b) {
                return BtcFormat.getInstance(b.scale, b.locale, b.minimumFractionDigits, b.fractionGroups);
            }

        }

    }

}

