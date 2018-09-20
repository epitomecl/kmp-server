/*
 * Decompiled with CFR 0_132.
 */
package org.bitcoinj.uri;

import org.bitcoinj.uri.BitcoinURIParseException;

public class RequiredFieldValidationException
extends BitcoinURIParseException {
    public RequiredFieldValidationException(String s) {
        super(s);
    }

    public RequiredFieldValidationException(String s, Throwable throwable) {
        super(s, throwable);
    }
}

