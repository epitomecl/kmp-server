/*
 * Decompiled with CFR 0_132.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package org.bitcoinj.wallet;

import javax.annotation.Nullable;
import org.bitcoinj.core.ECKey;
import org.bitcoinj.wallet.RedeemData;

public interface KeyBag {
    @Nullable
    public ECKey findKeyFromPubHash(byte[] var1);

    @Nullable
    public ECKey findKeyFromPubKey(byte[] var1);

    @Nullable
    public RedeemData findRedeemDataFromScriptHash(byte[] var1);
}

