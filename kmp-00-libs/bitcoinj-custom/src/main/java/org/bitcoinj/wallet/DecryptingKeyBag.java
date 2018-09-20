/*
 * Decompiled with CFR 0_132.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Preconditions
 *  javax.annotation.Nullable
 */
package org.bitcoinj.wallet;

import com.google.common.base.Preconditions;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;
import org.bitcoinj.core.ECKey;
import org.bitcoinj.script.Script;
import org.bitcoinj.wallet.KeyBag;
import org.bitcoinj.wallet.RedeemData;

public class DecryptingKeyBag
implements KeyBag {
    protected final KeyBag target;

    public DecryptingKeyBag(KeyBag target) {
        this.target = (KeyBag)Preconditions.checkNotNull((Object)target);
    }

    @Nullable
    private ECKey maybeDecrypt(ECKey key) {
        if (key == null) {
            return null;
        }
        return key;
    }

    private RedeemData maybeDecrypt(RedeemData redeemData) {
        ArrayList<ECKey> decryptedKeys = new ArrayList<ECKey>();
        for (ECKey key : redeemData.keys) {
            decryptedKeys.add(this.maybeDecrypt(key));
        }
        return RedeemData.of(decryptedKeys, redeemData.redeemScript);
    }

    @Nullable
    @Override
    public ECKey findKeyFromPubHash(byte[] pubkeyHash) {
        return this.maybeDecrypt(this.target.findKeyFromPubHash(pubkeyHash));
    }

    @Nullable
    @Override
    public ECKey findKeyFromPubKey(byte[] pubkey) {
        return this.maybeDecrypt(this.target.findKeyFromPubKey(pubkey));
    }

    @Nullable
    @Override
    public RedeemData findRedeemDataFromScriptHash(byte[] scriptHash) {
        return this.maybeDecrypt(this.target.findRedeemDataFromScriptHash(scriptHash));
    }
}

