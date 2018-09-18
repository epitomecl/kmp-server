package com.epitomecl.kmp.api.interfaces.spi;

import info.blockchain.wallet.ethereum.EthUrls;
import info.blockchain.wallet.ethereum.data.EthAddressResponseMap;
import info.blockchain.wallet.ethereum.data.EthLatestBlock;
import info.blockchain.wallet.ethereum.data.EthPushTxRequest;
import info.blockchain.wallet.ethereum.data.EthTxDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashMap;

interface IEth {

    @GetMapping("/" + EthUrls.ACCOUNT + "/{address}")
    EthAddressResponseMap getEthAccount(@PathVariable("address") String address);

    @GetMapping("/" + EthUrls.ACCOUNT + "/{address}" + EthUrls.IS_CONTRACT)
    HashMap<String, Boolean> getIfContract(@PathVariable("address") String address);

    @PostMapping("/" + EthUrls.PUSH_TX)
    HashMap<String, String> pushTx(@RequestBody EthPushTxRequest ethPushTxRequest);

    @GetMapping("/" + EthUrls.LATEST_BLOCK)
    EthLatestBlock getLatestBlock();

    @GetMapping("/" + EthUrls.TX + "/{hash}")
    EthTxDetails getTransaction(@PathVariable("hash") String txHash);

}
