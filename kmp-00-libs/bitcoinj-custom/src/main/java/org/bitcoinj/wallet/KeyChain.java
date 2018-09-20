/*
 * Decompiled with CFR 0_132.
 */
package org.bitcoinj.wallet;

import java.util.List;
import org.bitcoinj.core.ECKey;

public interface KeyChain {
    public boolean hasKey(ECKey var1);

    public List<? extends ECKey> getKeys(KeyPurpose var1, int var2);

    public ECKey getKey(KeyPurpose var1);

    public int numKeys();

    public static enum KeyPurpose {
        RECEIVE_FUNDS,
        CHANGE,
        REFUND,
        AUTHENTICATION;
        

        private KeyPurpose() {
        }
    }

}

