package com.epitomecl.kmp.cc.controller.api;

import com.epitomecl.kmp.cc.interfaces.api.IExplorer;
import info.blockchain.api.data.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
public class ExplorerController implements IExplorer {
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public ExplorerController() {

    }

    public AddressFull getAddress(
            @PathVariable("addr") String address,
            @RequestParam("limit") Integer limit,
            @RequestParam("offset") Integer offset,
            @RequestParam("filter") Integer filter,
            @RequestParam("format") String format,
            @RequestParam("api_code") String apiCode) {
        return new AddressFull();
    }

    public HashMap<String, Balance> getBalance(
            @RequestParam("active") String active,
            @RequestParam("filter") Integer filter,
            @RequestParam("api_code") String apiCode) {
        return new HashMap<String, Balance>();
    }

    public MultiAddress getMultiAddress(
            @RequestParam("active") String active,
            @RequestParam("n") Integer limit,
            @RequestParam("offset") Integer offset,
            @RequestParam("filter") Integer filter,
            @RequestParam("onlyShow") String context,
            @RequestParam("api_code") String apiCode) {
        return new MultiAddress();
    }

    public String pushTx(
            @RequestParam("tx") String hash,
            @RequestParam("api_code") String apiCode) {
        return "ok";
    }

    public String pushTxWithSecret(
            @RequestParam("tx") String hash,
            @RequestParam("lock_secret") String lockSecret,
            @RequestParam("api_code") String apiCode) {
        return "ok";
    }

    public Transaction getTransaction(
            @PathVariable("hash") String hash,
            @RequestParam("format") String format,
            @RequestParam("api_code") String apiCode) {
        return new Transaction();
    }

    public UnspentOutputs getUnspent(
            @RequestParam("active") String active,
            @RequestParam("confirmations") Integer confirmations,
            @RequestParam("limit") Integer limit,
            @RequestParam("api_code") String apiCode) {
        return new UnspentOutputs();
    }

    public RawBlock getBlockDetails(
            @PathVariable("block_hash") String blockHash,
            @RequestParam("api_code") String apiCode) {
        return new RawBlock();
    }

    public RawBlock getBlockDetails(
            @PathVariable("block_index") long blockIndex,
            @RequestParam("api_code") String apiCode) {
        return new RawBlock();
    }

    public Transaction getTransactionDetails(
            @PathVariable("tx_index") long txIndex,
            @RequestParam("api_code") String apiCode) {
        return new Transaction();
    }

    public Transaction getTransactionDetails(
            @PathVariable("tx_hash") String txHash,
            @RequestParam("api_code") String apiCode) {
        return new Transaction();
    }

    public RawBlocks getBlocksAtHeight(
            @PathVariable("block-height") long blockHeight,
            @RequestParam("format") String format,
            @RequestParam("api_code") String apiCode) {
        return new RawBlocks();
    }

    public Block getLatestBlock(@RequestParam("api_code") String apiCode) {
        return new Block();
    }

    public Transactions getUnconfirmedTransactions(
            @RequestParam("format") String format,
            @RequestParam("api_code") String apiCode) {
        return new Transactions();
    }

    public RawBlocks getBlocksAtTime(
            @PathVariable("time_in_milliseconds") long time,
            @RequestParam("format") String format,
            @RequestParam("api_code") String apiCode) {
        return new RawBlocks();
    }

    public RawBlocks getBlocksFromPool(
            @PathVariable("pool_name") String pool,
            @RequestParam("format") String format,
            @RequestParam("api_code") String apiCode) {
        return new RawBlocks();
    }

    public List<ExportHistory> getExportHistory(
            @RequestParam("active") String active,
            @RequestParam("start") String start,
            @RequestParam("end") String end,
            @RequestParam("currency") String currency,
            @RequestParam("api_code") String apiCode) {
        return new ArrayList<ExportHistory>();
    }
}
