/*
 * Decompiled with CFR 0_132.
 */
package org.bitcoinj.core;

import org.bitcoinj.script.Script;

public interface TransactionBag {
    public boolean isPubKeyHashMine(byte[] var1);

    public boolean isWatchedScript(Script var1);

    public boolean isPubKeyMine(byte[] var1);

    public boolean isPayToScriptHashMine(byte[] var1);
}

