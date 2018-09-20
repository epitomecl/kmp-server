/*
 * Decompiled with CFR 0_132.
 */
package org.bitcoinj.uri;

import org.bitcoinj.uri.BitcoinURIParseException;

public class OptionalFieldValidationException
extends BitcoinURIParseException {
    public OptionalFieldValidationException(String s) {
        super(s);
    }

    public OptionalFieldValidationException(String s, Throwable throwable) {
        super(s, throwable);
    }
}

