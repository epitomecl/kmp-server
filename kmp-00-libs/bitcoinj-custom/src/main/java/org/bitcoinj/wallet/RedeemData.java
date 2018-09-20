/*
 * Decompiled with CFR 0_132.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Preconditions
 */
package org.bitcoinj.wallet;

import com.google.common.base.Preconditions;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.bitcoinj.core.ECKey;
import org.bitcoinj.script.Script;

public class RedeemData {
    public final Script redeemScript;
    public final List<ECKey> keys;

    private RedeemData(List<ECKey> keys, Script redeemScript) {
        this.redeemScript = redeemScript;
        ArrayList<ECKey> sortedKeys = new ArrayList<ECKey>(keys);
        Collections.sort(sortedKeys, ECKey.PUBKEY_COMPARATOR);
        this.keys = sortedKeys;
    }

    public static RedeemData of(List<ECKey> keys, Script redeemScript) {
        return new RedeemData(keys, redeemScript);
    }

    public static RedeemData of(ECKey key, Script program) {
        Preconditions.checkArgument((boolean)(program.isSentToAddress() || program.isSentToRawPubKey()));
        return key != null ? new RedeemData(Collections.singletonList(key), program) : null;
    }

    public ECKey getFullKey() {
        for (ECKey key : this.keys) {
            if (!key.hasPrivKey()) continue;
            return key;
        }
        return null;
    }
}

