/*
 * Decompiled with CFR 0_132.
 */
package org.bitcoinj.core;

import java.util.List;
import org.bitcoinj.core.Address;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.core.UTXO;
import org.bitcoinj.core.UTXOProviderException;

public interface UTXOProvider {
    public List<UTXO> getOpenTransactionOutputs(List<Address> var1) throws UTXOProviderException;

    public int getChainHeadHeight() throws UTXOProviderException;

    public NetworkParameters getParams();
}

