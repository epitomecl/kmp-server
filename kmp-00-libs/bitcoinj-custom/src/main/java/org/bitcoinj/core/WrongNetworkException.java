/*
 * Decompiled with CFR 0_132.
 */
package org.bitcoinj.core;

import java.util.Arrays;
import org.bitcoinj.core.AddressFormatException;

public class WrongNetworkException
extends AddressFormatException {
    public int verCode;
    public int[] acceptableVersions;

    public WrongNetworkException(int verCode, int[] acceptableVersions) {
        super("Version code of address did not match acceptable versions for network: " + verCode + " not in " + Arrays.toString(acceptableVersions));
        this.verCode = verCode;
        this.acceptableVersions = acceptableVersions;
    }
}

