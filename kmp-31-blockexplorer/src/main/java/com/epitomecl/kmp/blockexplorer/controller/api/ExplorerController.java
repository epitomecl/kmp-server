package com.epitomecl.kmp.blockexplorer.controller.api;

import com.epitomecl.kmp.blockexplorer.domain.UTXO;
import com.epitomecl.kmp.blockexplorer.domain.UTXORaw;
import com.epitomecl.kmp.blockexplorer.domain.UserVO;
import com.epitomecl.kmp.blockexplorer.interfaces.api.IExplorer;
import com.epitomecl.kmp.blockexplorer.service.BlockExplorerServiceImpl;
import info.blockchain.api.data.*;
import org.bitcoinj.core.AddressFormatException;
import org.bitcoinj.core.Base58;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.crypto.DeterministicKey;
import org.bitcoinj.crypto.HDKeyDerivation;
import org.bitcoinj.params.MainNetParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongycastle.util.encoders.Hex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.lang.invoke.MethodHandles;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
public class ExplorerController implements IExplorer {
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Autowired
    private BlockExplorerServiceImpl service;

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

    public List<UTXO> getBalanceEx(
            @RequestParam("xpub") String xpub,
            @RequestParam("api_code") String apiCode,
            HttpSession session) {
        try {
            if (xpub.length() > 0) {
                final NetworkParameters netParams = NetworkParameters.testNet();

                DeterministicKey key = createMasterPubKeyFromXPub(netParams, xpub);
                DeterministicKey receiveKey = HDKeyDerivation.deriveChildKey(key, 0);
                DeterministicKey receiveAddress = HDKeyDerivation.deriveChildKey(receiveKey, 0);

                String address = receiveAddress.toAddress(NetworkParameters.testNet()).toBase58();

                List<UTXORaw> list = service.getBalanceEx(address);
                List<UTXO> result = new ArrayList<>();

                list.forEach(v -> {
                    UTXO item = new UTXO();
                    item.setHash(Hex.toHexString(v.getHash()));
                    item.setIndex(v.getIndex());
                    item.setValue(v.getValue());
                    item.setScriptBytes(Hex.toHexString(v.getScriptBytes()));
                    result.add(item);
                });

                return result;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Integer getSpendTXOCount(
            @RequestParam("address") String address,
            @RequestParam("api_code") String apiCode,
            HttpSession session) {
        try {
            if (address.length() > 0) {
                return service.getSpendTXOCount(address);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    public UserVO postLogin(
            @RequestParam("id") String id,
            @RequestParam("pw") String pw,
            HttpSession session) {
        UserVO result = new UserVO();
        result.setSession(session.getId());

        return result;
    }

    public UserVO postRegist(
            @RequestParam("id") String id,
            @RequestParam("pw") String pw,
            HttpSession session) {
        UserVO result = new UserVO();
        result.setSession(session.getId());
        return result;
    }

    private DeterministicKey createMasterPubKeyFromXPub(NetworkParameters params, String xpubstr) throws AddressFormatException {

        boolean isTestnet = !(params instanceof MainNetParams);

        byte[] xpubBytes = Base58.decodeChecked(xpubstr);

        ByteBuffer bb = ByteBuffer.wrap(xpubBytes);

        int prefix = bb.getInt();

        if (!isTestnet && prefix != 0x0488B21E) {
            throw new AddressFormatException("invalid xpub version");
        }
        if (isTestnet && prefix != 0x043587CF) {
            throw new AddressFormatException("invalid xpub version");
        }

        byte[] chain = new byte[32];
        byte[] pub = new byte[33];
        // depth:
        bb.get();
        // parent fingerprint:
        bb.getInt();
        // child no.
        bb.getInt();
        bb.get(chain);
        bb.get(pub);

        return HDKeyDerivation.createMasterPubKeyFromBytes(pub, chain);
    }
}
