package com.epitomecl.kmp.interfaces.spi;

import info.blockchain.wallet.api.data.FeeOptions;
import org.springframework.web.bind.annotation.GetMapping;

public interface IFee {

    @GetMapping("/mempool/fees")
    FeeOptions getFeeOptions();

    @GetMapping("/eth/fees")
    FeeOptions getEthFeeOptions();
}
