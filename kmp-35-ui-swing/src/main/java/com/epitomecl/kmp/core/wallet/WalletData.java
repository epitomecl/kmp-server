package com.epitomecl.kmp.core.wallet;

import info.blockchain.wallet.bip44.HDWallet;

public class WalletData {
    private CryptoType coinType;
    private HDWallet wallet;

    public WalletData(CryptoType coinType, HDWallet wallet) {
        this.coinType = coinType;
        this.wallet = wallet;
    }

    public CryptoType getCoinType() {
        return coinType;
    }

    public HDWallet getWallet() {
        return wallet;
    }
}
