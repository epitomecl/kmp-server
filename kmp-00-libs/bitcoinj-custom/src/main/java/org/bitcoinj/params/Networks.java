/*
 * Decompiled with CFR 0_132.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableSet
 *  com.google.common.collect.ImmutableSet$Builder
 *  com.google.common.collect.Lists
 */
package org.bitcoinj.params;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import java.util.Collection;
import java.util.Set;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.params.BitcoinCashMainNetParams;
import org.bitcoinj.params.BitcoinCashTestNet3Params;
import org.bitcoinj.params.BitcoinMainNetParams;
import org.bitcoinj.params.BitcoinTestNet3Params;

public class Networks {
    private static Set<? extends NetworkParameters> networks = ImmutableSet.of((Object)BitcoinTestNet3Params.get(), (Object)BitcoinMainNetParams.get(), (Object)BitcoinCashTestNet3Params.get(), (Object)BitcoinCashMainNetParams.get());

    public static Set<? extends NetworkParameters> get() {
        return networks;
    }

    public static void register(NetworkParameters network) {
        Networks.register(Lists.newArrayList((Object[])new NetworkParameters[]{network}));
    }

    public static void register(Collection<? extends NetworkParameters> networks) {
        ImmutableSet.Builder builder = ImmutableSet.builder();
        builder.addAll(Networks.networks);
        builder.addAll(networks);
        Networks.networks = builder.build();
    }

    public static void unregister(NetworkParameters network) {
        if (networks.contains(network)) {
            ImmutableSet.Builder builder = ImmutableSet.builder();
            for (NetworkParameters parameters : networks) {
                if (parameters.equals(network)) continue;
                builder.add((Object)parameters);
            }
            networks = builder.build();
        }
    }
}

