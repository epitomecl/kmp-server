/*
 * Decompiled with CFR 0_132.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Preconditions
 *  javax.annotation.Nullable
 */
package org.bitcoinj.uri;

import com.google.common.base.Preconditions;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import javax.annotation.Nullable;
import org.bitcoinj.core.Address;
import org.bitcoinj.core.AddressFormatException;
import org.bitcoinj.core.Coin;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.uri.BitcoinURIParseException;
import org.bitcoinj.uri.OptionalFieldValidationException;
import org.bitcoinj.uri.RequiredFieldValidationException;

public class BitcoinURI {
    public static final String FIELD_MESSAGE = "message";
    public static final String FIELD_LABEL = "label";
    public static final String FIELD_AMOUNT = "amount";
    public static final String FIELD_ADDRESS = "address";
    public static final String FIELD_PAYMENT_REQUEST_URL = "r";
    private static final String ENCODED_SPACE_CHARACTER = "%20";
    private static final String AMPERSAND_SEPARATOR = "&";
    private static final String QUESTION_MARK_SEPARATOR = "?";
    private final Map<String, Object> parameterMap = new LinkedHashMap<String, Object>();

    public BitcoinURI(String uri) throws BitcoinURIParseException {
        this(null, uri);
    }

    public BitcoinURI(@Nullable NetworkParameters params, String input) throws BitcoinURIParseException {
        String schemeSpecificPart;
        URI uri;
        Preconditions.checkNotNull((Object)input);
        String scheme = null == params ? "bitcoin" : params.getUriScheme();
        try {
            uri = new URI(input);
        }
        catch (URISyntaxException e) {
            throw new BitcoinURIParseException("Bad URI syntax", e);
        }
        String blockchainInfoScheme = scheme + "://";
        String correctScheme = scheme + ":";
        if (input.startsWith(blockchainInfoScheme)) {
            schemeSpecificPart = input.substring(blockchainInfoScheme.length());
        } else if (input.startsWith(correctScheme)) {
            schemeSpecificPart = input.substring(correctScheme.length());
        } else {
            throw new BitcoinURIParseException("Unsupported URI scheme: " + uri.getScheme());
        }
        String[] addressSplitTokens = schemeSpecificPart.split("\\?", 2);
        if (addressSplitTokens.length == 0) {
            throw new BitcoinURIParseException("No data found after the bitcoin: prefix");
        }
        String addressToken = addressSplitTokens[0];
        String[] nameValuePairTokens = addressSplitTokens.length == 1 ? new String[]{} : addressSplitTokens[1].split(AMPERSAND_SEPARATOR);
        this.parseParameters(params, addressToken, nameValuePairTokens);
        if (!addressToken.isEmpty()) {
            try {
                Address address = Address.fromBase58(params, addressToken);
                this.putWithValidation(FIELD_ADDRESS, address);
            }
            catch (AddressFormatException e) {
                throw new BitcoinURIParseException("Bad address", e);
            }
        }
        if (addressToken.isEmpty() && this.getPaymentRequestUrl() == null) {
            throw new BitcoinURIParseException("No address and no r= parameter found");
        }
    }

    private void parseParameters(@Nullable NetworkParameters params, String addressToken, String[] nameValuePairTokens) throws BitcoinURIParseException {
        for (String nameValuePairToken : nameValuePairTokens) {
            int sepIndex = nameValuePairToken.indexOf(61);
            if (sepIndex == -1) {
                throw new BitcoinURIParseException("Malformed Bitcoin URI - no separator in '" + nameValuePairToken + "'");
            }
            if (sepIndex == 0) {
                throw new BitcoinURIParseException("Malformed Bitcoin URI - empty name '" + nameValuePairToken + "'");
            }
            String nameToken = nameValuePairToken.substring(0, sepIndex).toLowerCase(Locale.ENGLISH);
            String valueToken = nameValuePairToken.substring(sepIndex + 1);
            if (FIELD_AMOUNT.equals(nameToken)) {
                try {
                    Coin amount = Coin.parseCoin(valueToken);
                    if (params != null && amount.isGreaterThan(params.getMaxMoney())) {
                        throw new BitcoinURIParseException("Max number of coins exceeded");
                    }
                    if (amount.signum() < 0) {
                        throw new ArithmeticException("Negative coins specified");
                    }
                    this.putWithValidation(FIELD_AMOUNT, amount);
                    continue;
                }
                catch (IllegalArgumentException e) {
                    throw new OptionalFieldValidationException(String.format(Locale.US, "'%s' is not a valid amount", valueToken), e);
                }
                catch (ArithmeticException e) {
                    throw new OptionalFieldValidationException(String.format(Locale.US, "'%s' has too many decimal places", valueToken), e);
                }
            }
            if (nameToken.startsWith("req-")) {
                throw new RequiredFieldValidationException("'" + nameToken + "' is required but not known, this URI is not valid");
            }
            try {
                if (valueToken.length() <= 0) continue;
                this.putWithValidation(nameToken, URLDecoder.decode(valueToken, "UTF-8"));
            }
            catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void putWithValidation(String key, Object value) throws BitcoinURIParseException {
        if (this.parameterMap.containsKey(key)) {
            throw new BitcoinURIParseException(String.format(Locale.US, "'%s' is duplicated, URI is invalid", key));
        }
        this.parameterMap.put(key, value);
    }

    @Nullable
    public Address getAddress() {
        return (Address)this.parameterMap.get(FIELD_ADDRESS);
    }

    public Coin getAmount() {
        return (Coin)this.parameterMap.get(FIELD_AMOUNT);
    }

    public String getLabel() {
        return (String)this.parameterMap.get(FIELD_LABEL);
    }

    public String getMessage() {
        return (String)this.parameterMap.get(FIELD_MESSAGE);
    }

    public final String getPaymentRequestUrl() {
        return (String)this.parameterMap.get(FIELD_PAYMENT_REQUEST_URL);
    }

    public List<String> getPaymentRequestUrls() {
        String url;
        String paramName;
        int i;
        ArrayList<String> urls = new ArrayList<String>();
        while ((url = (String)this.parameterMap.get(paramName = FIELD_PAYMENT_REQUEST_URL + ((i = urls.size()) > 0 ? Integer.toString(i) : ""))) != null) {
            urls.add(url);
        }
        Collections.reverse(urls);
        return urls;
    }

    public Object getParameterByName(String name) {
        return this.parameterMap.get(name);
    }

    public String toString() {
        StringBuilder builder = new StringBuilder("BitcoinURI[");
        boolean first = true;
        for (Map.Entry<String, Object> entry : this.parameterMap.entrySet()) {
            if (first) {
                first = false;
            } else {
                builder.append(",");
            }
            builder.append("'").append(entry.getKey()).append("'=").append("'").append(entry.getValue()).append("'");
        }
        builder.append("]");
        return builder.toString();
    }

    public static String convertToBitcoinURI(Address address, Coin amount, String label, String message) {
        return BitcoinURI.convertToBitcoinURI(address.getParameters(), address.toString(), amount, label, message);
    }

    public static String convertToBitcoinURI(NetworkParameters params, String address, @Nullable Coin amount, @Nullable String label, @Nullable String message) {
        Preconditions.checkNotNull((Object)params);
        Preconditions.checkNotNull((Object)address);
        if (amount != null && amount.signum() < 0) {
            throw new IllegalArgumentException("Coin must be positive");
        }
        StringBuilder builder = new StringBuilder();
        String scheme = params.getUriScheme();
        builder.append(scheme).append(":").append(address);
        boolean questionMarkHasBeenOutput = false;
        if (amount != null) {
            builder.append(QUESTION_MARK_SEPARATOR).append(FIELD_AMOUNT).append("=");
            builder.append(amount.toPlainString());
            questionMarkHasBeenOutput = true;
        }
        if (label != null && !"".equals(label)) {
            if (questionMarkHasBeenOutput) {
                builder.append(AMPERSAND_SEPARATOR);
            } else {
                builder.append(QUESTION_MARK_SEPARATOR);
                questionMarkHasBeenOutput = true;
            }
            builder.append(FIELD_LABEL).append("=").append(BitcoinURI.encodeURLString(label));
        }
        if (message != null && !"".equals(message)) {
            if (questionMarkHasBeenOutput) {
                builder.append(AMPERSAND_SEPARATOR);
            } else {
                builder.append(QUESTION_MARK_SEPARATOR);
            }
            builder.append(FIELD_MESSAGE).append("=").append(BitcoinURI.encodeURLString(message));
        }
        return builder.toString();
    }

    static String encodeURLString(String stringToEncode) {
        try {
            return URLEncoder.encode(stringToEncode, "UTF-8").replace("+", ENCODED_SPACE_CHARACTER);
        }
        catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
}

