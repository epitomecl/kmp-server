package com.epitomecl.kmp.api.interfaces.api;

import com.epitomecl.kmp.api.domain.ActiveAddress;
import com.epitomecl.kmp.core.wallet.UTXO;
import info.blockchain.api.data.*;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;

/**
 * source from - info.blockchain.api.interfaces.ExplorerInterface
 */
public interface IExplorer {

//    @GetMapping("/address/{addr}")
//    AddressFull getAddress(
//            @PathVariable("addr") String address,
//            @RequestParam("limit") Integer limit,
//            @RequestParam("offset") Integer offset,
//            @RequestParam("filter") Integer filter,
//            @RequestParam("format") String format,
//            @RequestParam("api_code") String apiCode);
//
//    @Deprecated
//    @PostMapping("/balance")
//    HashMap<String, Balance> getBalance(
//            @RequestParam("active") String active,
//            @RequestParam("filter") Integer filter,
//            @RequestParam("api_code") String apiCode);
//
//    @Deprecated
//    @PostMapping("/multiaddr")
//    MultiAddress getMultiAddress(
//            @RequestParam("active") String active,
//            @RequestParam("n") Integer limit,
//            @RequestParam("offset") Integer offset,
//            @RequestParam("filter") Integer filter,
//            @RequestParam("onlyShow") String context,
//            @RequestParam("api_code") String apiCode);
//
////    @Deprecated
////    @PostMapping("/pushtx")
////    String pushTx(
////            @RequestParam("tx") String hash,
////            @RequestParam("api_code") String apiCode);
//
//    @PostMapping("/pushtx")
//    String pushTxWithSecret(
//            @RequestParam("tx") String hash,
//            @RequestParam("lock_secret") String lockSecret,
//            @RequestParam("api_code") String apiCode);
//
//    @GetMapping("/tx/{hash}")
//    Transaction getTransaction(
//            @PathVariable("hash") String hash,
//            @RequestParam("format") String format,
//            @RequestParam("api_code") String apiCode);
//
//    @Deprecated
//    @GetMapping("/unspent")
//    UnspentOutputs getUnspent(
//            @RequestParam("active") String active,
//            @RequestParam("confirmations") Integer confirmations,
//            @RequestParam("limit") Integer limit,
//            @RequestParam("api_code") String apiCode);
//
//    @GetMapping("/rawblock/{block_hash}")
//    RawBlock getBlockDetails(
//            @PathVariable("block_hash") String blockHash,
//            @RequestParam("api_code") String apiCode);
//
//    @GetMapping("/rawblock/{block_index}")
//    RawBlock getBlockDetails(
//            @PathVariable("block_index") long blockIndex,
//            @RequestParam("api_code") String apiCode);
//
//    @GetMapping("/rawtx/{tx_index}")
//    Transaction getTransactionDetails(
//            @PathVariable("tx_index") long txIndex,
//            @RequestParam("api_code") String apiCode);
//
//    @GetMapping("/rawtx/{tx_hash}")
//    Transaction getTransactionDetails(
//            @PathVariable("tx_hash") String txHash,
//            @RequestParam("api_code") String apiCode);
//
//    @GetMapping("/block-height/{block-height}")
//    RawBlocks getBlocksAtHeight(
//            @PathVariable("block-height") long blockHeight,
//            @RequestParam("format") String format,
//            @RequestParam("api_code") String apiCode);
//
//    @GetMapping("/latestblock")
//    Block getLatestBlock(@RequestParam("api_code") String apiCode);
//
//    @GetMapping("/unconfirmed-transactions")
//    Transactions getUnconfirmedTransactions(
//            @RequestParam("format") String format,
//            @RequestParam("api_code") String apiCode);
//
//    @GetMapping("/blocks/{time_in_milliseconds}")
//    RawBlocks getBlocksAtTime(
//            @PathVariable("time_in_milliseconds") long time,
//            @RequestParam("format") String format,
//            @RequestParam("api_code") String apiCode);
//
//    @GetMapping("/blocks/{pool_name}")
//    RawBlocks getBlocksFromPool(
//            @PathVariable("pool_name") String pool,
//            @RequestParam("format") String format,
//            @RequestParam("api_code") String apiCode);
//
//    @GetMapping("/v2/export-history")
//    List<ExportHistory> getExportHistory(
//            @RequestParam("active") String active,
//            @RequestParam("start") String start,
//            @RequestParam("end") String end,
//            @RequestParam("currency") String currency,
//            @RequestParam("api_code") String apiCode);


    //region custom extension
    @PostMapping("/balance-ex")
    List<UTXO> getBalanceEx(
            @RequestParam("xpub") String xpub,
            @RequestParam("api_code") String apiCode,
            HttpSession session);

    @PostMapping("/activereceiveaddress")
    ActiveAddress getActiveReceiveAddress(
            @RequestParam("xpub") String xpub,
            @RequestParam("api_code") String apiCode,
            HttpSession session);

    @PostMapping("/activechangeaddress")
    ActiveAddress getActiveChangeAddress(
            @RequestParam("xpub") String xpub,
            @RequestParam("api_code") String apiCode,
            HttpSession session);

    @PostMapping("/spendtxo-count")
    Integer getSpendTXOCount(
            @RequestParam("address") String address,
            @RequestParam("api_code") String apiCode,
            HttpSession session);

    @PostMapping("/checktx")
    List<UTXO> checkTX(
            @RequestParam("txid") String txid,
            @RequestParam("api_code") String apiCode,
            HttpSession session);

    //endregion
}
