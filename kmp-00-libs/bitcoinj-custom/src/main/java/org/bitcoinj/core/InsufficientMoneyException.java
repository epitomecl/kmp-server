/*
 * Decompiled with CFR 0_132.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Preconditions
 *  javax.annotation.Nullable
 */
package org.bitcoinj.core;

import com.google.common.base.Preconditions;
import javax.annotation.Nullable;
import org.bitcoinj.core.Coin;

public class InsufficientMoneyException
extends Exception {
    @Nullable
    public final Coin missing;

    protected InsufficientMoneyException() {
        this.missing = null;
    }

    public InsufficientMoneyException(Coin missing) {
        this(missing, "Insufficient money,  missing " + missing.toFriendlyString());
    }

    public InsufficientMoneyException(Coin missing, String message) {
        super(message);
        this.missing = (Coin)Preconditions.checkNotNull((Object)missing);
    }
}

