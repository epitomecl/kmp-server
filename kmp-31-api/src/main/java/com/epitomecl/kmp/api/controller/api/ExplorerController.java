package com.epitomecl.kmp.api.controller.api;

import com.epitomecl.kmp.api.domain.ActiveAddress;
import com.epitomecl.kmp.core.wallet.IAPIManager;
import com.epitomecl.kmp.core.wallet.UTXO;
import com.epitomecl.kmp.api.domain.UTXORaw;
import com.epitomecl.kmp.api.interfaces.api.IExplorer;
import com.epitomecl.kmp.api.service.BlockExplorerServiceImpl;
import com.epitomecl.kmp.core.wallet.AccountKeyDerivation;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.wallet.KeyChain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongycastle.util.encoders.Hex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
public class ExplorerController implements IExplorer, IAPIManager {
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Autowired
    private BlockExplorerServiceImpl service;

    public ExplorerController() {

    }

//    public AddressFull getAddress(
//            @PathVariable("addr") String address,
//            @RequestParam("limit") Integer limit,
//            @RequestParam("offset") Integer offset,
//            @RequestParam("filter") Integer filter,
//            @RequestParam("format") String format,
//            @RequestParam("api_code") String apiCode) {
//        return new AddressFull();
//    }
//
//    public HashMap<String, Balance> getBalance(
//            @RequestParam("active") String active,
//            @RequestParam("filter") Integer filter,
//            @RequestParam("api_code") String apiCode) {
//        return new HashMap<String, Balance>();
//    }
//
//    public MultiAddress getMultiAddress(
//            @RequestParam("active") String active,
//            @RequestParam("n") Integer limit,
//            @RequestParam("offset") Integer offset,
//            @RequestParam("filter") Integer filter,
//            @RequestParam("onlyShow") String context,
//            @RequestParam("api_code") String apiCode) {
//        return new MultiAddress();
//    }
//
//    public String pushTx(
//            @RequestParam("tx") String hash,
//            @RequestParam("api_code") String apiCode) {
//        return "ok";
//    }
//
//    public String pushTxWithSecret(
//            @RequestParam("tx") String hash,
//            @RequestParam("lock_secret") String lockSecret,
//            @RequestParam("api_code") String apiCode) {
//        return "ok";
//    }
//
//    public Transaction getTransaction(
//            @PathVariable("hash") String hash,
//            @RequestParam("format") String format,
//            @RequestParam("api_code") String apiCode) {
//        return new Transaction();
//    }
//
//    public UnspentOutputs getUnspent(
//            @RequestParam("active") String active,
//            @RequestParam("confirmations") Integer confirmations,
//            @RequestParam("limit") Integer limit,
//            @RequestParam("api_code") String apiCode) {
//        return new UnspentOutputs();
//    }
//
//    public RawBlock getBlockDetails(
//            @PathVariable("block_hash") String blockHash,
//            @RequestParam("api_code") String apiCode) {
//        return new RawBlock();
//    }
//
//    public RawBlock getBlockDetails(
//            @PathVariable("block_index") long blockIndex,
//            @RequestParam("api_code") String apiCode) {
//        return new RawBlock();
//    }
//
//    public Transaction getTransactionDetails(
//            @PathVariable("tx_index") long txIndex,
//            @RequestParam("api_code") String apiCode) {
//        return new Transaction();
//    }
//
//    public Transaction getTransactionDetails(
//            @PathVariable("tx_hash") String txHash,
//            @RequestParam("api_code") String apiCode) {
//        return new Transaction();
//    }
//
//    public RawBlocks getBlocksAtHeight(
//            @PathVariable("block-height") long blockHeight,
//            @RequestParam("format") String format,
//            @RequestParam("api_code") String apiCode) {
//        return new RawBlocks();
//    }
//
//    public Block getLatestBlock(@RequestParam("api_code") String apiCode) {
//        return new Block();
//    }
//
//    public Transactions getUnconfirmedTransactions(
//            @RequestParam("format") String format,
//            @RequestParam("api_code") String apiCode) {
//        return new Transactions();
//    }
//
//    public RawBlocks getBlocksAtTime(
//            @PathVariable("time_in_milliseconds") long time,
//            @RequestParam("format") String format,
//            @RequestParam("api_code") String apiCode) {
//        return new RawBlocks();
//    }
//
//    public RawBlocks getBlocksFromPool(
//            @PathVariable("pool_name") String pool,
//            @RequestParam("format") String format,
//            @RequestParam("api_code") String apiCode) {
//        return new RawBlocks();
//    }
//
//    public List<ExportHistory> getExportHistory(
//            @RequestParam("active") String active,
//            @RequestParam("start") String start,
//            @RequestParam("end") String end,
//            @RequestParam("currency") String currency,
//            @RequestParam("api_code") String apiCode) {
//        return new ArrayList<ExportHistory>();
//    }

    public List<UTXO> getBalanceEx(
            @RequestParam("xpub") String xpub,
            @RequestParam("api_code") String apiCode,
            HttpSession session) {
        try {
            if (xpub.length() > 0) {
                final NetworkParameters netParams = NetworkParameters.testNet();

                AccountKeyDerivation deriver = new AccountKeyDerivation(netParams, xpub);
                List<UTXORaw> receive_balances = getAddressBalance(deriver, KeyChain.KeyPurpose.RECEIVE_FUNDS);
                List<UTXORaw> change_balances = getAddressBalance(deriver, KeyChain.KeyPurpose.CHANGE);

                List<UTXORaw> balances = new ArrayList<>(receive_balances);
                balances.addAll(change_balances);

                List<UTXO> result = new ArrayList<>();

                balances.forEach(v -> {
                    UTXO item = new UTXO();
                    item.setHash(Hex.toHexString(v.getHash()));
                    item.setIndex(v.getIndex());
                    item.setValue(Long.valueOf(v.getValue().toString()));
                    item.setScriptBytes(Hex.toHexString(v.getScriptBytes()));
                    item.setToAddress(v.getToAddress());
                    result.add(item);
                });

                return result;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private List<UTXORaw> getAddressBalance(AccountKeyDerivation deriver, KeyChain.KeyPurpose purpose) {
        String address = deriver.getAddresses(purpose);
        List<UTXORaw> balances = service.getBalanceEx(address);
        int spentTxoCount = service.getSpendTXOCount(address);

        //if the result has size then check next derive address recursively
        //https://blog.blockonomics.co/bitcoin-what-is-this-gap-limit-4f098e52d7e1
        //BIP44 wallet has gap limit. When you genetate 20 receiving address that didn't receive any funds.
        //From the 21st recipient address cannot find funds.
        //But if you have seed key then you can get fund from derived receive address.

        AccountKeyDerivation.ChildKeyNode childKeyNode = deriver.getChildKeyNode(purpose);
        if((balances.size() == 0) && (spentTxoCount == 0)) {
            childKeyNode.addGap();
            if(childKeyNode.getGap() >= 20) {
                return balances;
            }
        }
        else {
            childKeyNode.resetGap();
        }

        List<UTXORaw> balances_next = getAddressBalance(deriver, purpose);

        List<UTXORaw> result = new ArrayList<>(balances);
        result.addAll(balances_next);

        return result;
    }

    public ActiveAddress getActiveReceiveAddress(
            @RequestParam("xpub") String xpub,
            @RequestParam("api_code") String apiCode,
            HttpSession session) {
        ActiveAddress result = new ActiveAddress();

        try {
            if (xpub.length() > 0) {
                final NetworkParameters netParams = NetworkParameters.testNet();
                AccountKeyDerivation deriver = new AccountKeyDerivation(netParams, xpub);

                String receiveAddress = findActiveReceiveAddress(deriver, KeyChain.KeyPurpose.RECEIVE_FUNDS);
                result.setAddress(receiveAddress);
                return result;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    private String findActiveReceiveAddress(AccountKeyDerivation deriver, KeyChain.KeyPurpose purpose) {
        String address = deriver.getAddresses(purpose);

        if(!service.isSpendAddress(address)) {
            return address;
        }

        String address_next = findActiveReceiveAddress(deriver, purpose);
        return address_next;
    }

    public ActiveAddress getActiveChangeAddress(
            @RequestParam("xpub") String xpub,
            @RequestParam("api_code") String apiCode,
            HttpSession session) {
        ActiveAddress result = new ActiveAddress();

        try {
            if (xpub.length() > 0) {
                final NetworkParameters netParams = NetworkParameters.testNet();
                AccountKeyDerivation deriver = new AccountKeyDerivation(netParams, xpub);

                String changeAddress = findActiveChangeAddress(deriver, KeyChain.KeyPurpose.CHANGE);
                result.setAddress(changeAddress);
                return result;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    private String findActiveChangeAddress(AccountKeyDerivation deriver, KeyChain.KeyPurpose purpose) {
        String address = deriver.getAddresses(purpose);
        if(!service.isUsedAddress(address) && !service.isSpendAddress(address)) {
            return address;
        }

        String address_next = findActiveChangeAddress(deriver, purpose);
        return address_next;
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

    @Override
    public int spendTXOCount(String address) {
        return getSpendTXOCount(address, "api_code", null);
    }

    public List<UTXO> checkTX(
            @RequestParam("txid") String txid,
            @RequestParam("api_code") String apiCode,
            HttpSession session) {
        List<UTXO> result = new ArrayList<>();

        try {
            byte[] hash = Hex.decode(txid);
            List<UTXORaw> utxoRawList = service.getTX(hash);

            utxoRawList.forEach(v -> {
                UTXO item = new UTXO();
                item.setHash(Hex.toHexString(v.getHash()));
                item.setIndex(v.getIndex());
                item.setValue(Long.valueOf(v.getValue().toString()));
                item.setScriptBytes(Hex.toHexString(v.getScriptBytes()));
                item.setToAddress(v.getToAddress());

                if(!item.getHash().equals(txid)) {
                    try {
                        throw new Exception(String.format("Transaction id not match: source[%s], found[%s]", txid, item.getHash()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                result.add(item);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

}
