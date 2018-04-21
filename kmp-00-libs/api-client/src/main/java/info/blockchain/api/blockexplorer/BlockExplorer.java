package info.blockchain.api.blockexplorer;

import info.blockchain.api.BaseApiClient;
import info.blockchain.api.data.*;
import org.apache.commons.lang3.StringUtils;
import retrofit2.Call;
import retrofit2.Retrofit;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class BlockExplorer extends BaseApiClient {

    /**
     * Default constructor for production environment.
     * Uses default OkHttpClient with blockchain.info certificate pinning for secure host verification.
     */
    public BlockExplorer() {
        super();
    }

    /**
     * Default constructor for production environment with an API key.
     * Uses default OkHttpClient with blockchain.info certificate pinning for secure host verification.
     */
    public BlockExplorer(String apiCode) {
        super(apiCode);
    }

    /**
     * Constructor that allows you to pass in retrofit instances.
     *
     * @param retrofitExplorerRoot
     * @param apiCode              Nullable - API code for platform Android, iOS, Web statistics
     * @throws IOException
     */
    public BlockExplorer(Retrofit retrofitExplorerRoot, Retrofit retrofitApiRoot, String apiCode) {
        super(retrofitExplorerRoot, retrofitApiRoot, apiCode);
    }

    /**
     * Fetches address information by hash160(hex) or base58 address.
     *
     * @param address a hash160 or a base58 address
     * @param filter  the filter for transactions selection, use null to indicate default
     * @param limit   an integer to limit number of transactions to display, use null to indicate default
     * @param offset  an integer to set number of transactions to skip when fetch
     * @return {@link Call} object which can be executed synchronously or asynchronously to return a
     * response object
     */
    public Call<AddressFull> getAddress(String address, FilterType filter, Integer limit, Integer offset) {
        return getExplorerInterface().getAddress(address, limit, offset, filter == null ? null : filter.getFilterInt(), "json", apiCode);
    }

    /**
     * Returns the address balance summary for each address provided
     *
     * @param addressList base58 or xpub addresses
     * @param filter      the filter for transactions selection, use null to indicate default
     * @return {@link Call} object which can be executed synchronously or asynchronously to return a
     * response object
     * @deprecated All calls to balance must now use the API
     */
    @Deprecated
    public Call<HashMap<String, Balance>> getBalance(List<String> addressList, FilterType filter) {

        String pipedAddresses = StringUtils.join(addressList, "|");
        return getExplorerInterface().getBalance(pipedAddresses, filter == null ? null : filter.getFilterInt(), apiCode);
    }

    /**
     * Returns the address balance summary for each address provided
     *
     * @param coin        The code of the coin to be returned, ie "btc" or "bch"
     * @param addressList base58 or xpub addresses
     * @param filter      the filter for transactions selection, use null to indicate default
     * @return {@link Call} object which can be executed synchronously or asynchronously to return a
     * response object
     */
    public Call<HashMap<String, Balance>> getBalance(String coin, List<String> addressList, FilterType filter) {

        String pipedAddresses = StringUtils.join(addressList, "|");
        return getApiInterface().getBalance(coin, pipedAddresses, filter == null ? null : filter.getFilterInt(), apiCode);
    }

    /**
     * Returns an aggregated summary on all addresses provided.
     *
     * @param addressList a list of Base58 or xpub addresses
     * @param filter      the filter for transactions selection, use null to indicate default
     * @param limit       an integer to limit number of transactions to display, use null to indicate default
     * @param offset      an integer to set number of transactions to skip when fetch
     * @return {@link Call} object which can be executed synchronously or asynchronously to return a
     * response object
     * @deprecated All calls to multiaddress must now use the API
     */
    @Deprecated
    public Call<MultiAddress> getMultiAddress(List<String> addressList, FilterType filter, Integer limit, Integer offset) {
        String pipedAddresses = StringUtils.join(addressList, "|");
        return getExplorerInterface().getMultiAddress(pipedAddresses, limit, offset, filter == null ? null : filter.getFilterInt(), null, apiCode);
    }

    /**
     * Returns an aggregated summary on all addresses provided.
     *
     * @param coin        The code of the coin to be returned, ie "btc" or "bch"
     * @param addressList a list of Base58 or xpub addresses
     * @param filter      the filter for transactions selection, use null to indicate default
     * @param limit       an integer to limit number of transactions to display, use null to indicate default
     * @param offset      an integer to set number of transactions to skip when fetch
     * @param context     A context for the results
     * @return {@link Call} object which can be executed synchronously or asynchronously to return a
     * response object
     */
    public Call<MultiAddress> getMultiAddress(String coin, List<String> addressList, String context, FilterType filter, Integer limit, Integer offset) {
        String pipedAddresses = StringUtils.join(addressList, "|");
        return getApiInterface().getMultiAddress(coin, pipedAddresses, limit, offset, filter == null ? null : filter.getFilterInt(), context, apiCode);
    }

    /**
     * Returns list of unspent outputs.
     *
     * @param addressList a list of Base58 or xpub addresses
     * @param confirms    an integer for minimum confirms of the outputs, use null to indicate default
     * @param limit       an integer to limit number of transactions to display, use null to indicate default
     * @return {@link Call} object which can be executed synchronously or asynchronously to return a
     * response object
     * @deprecated All calls to unspent must now use the API
     */
    @Deprecated
    public Call<UnspentOutputs> getUnspentOutputs(List<String> addressList, Integer confirms, Integer limit) {
        String pipedAddresses = StringUtils.join(addressList, "|");
        return getExplorerInterface().getUnspent(pipedAddresses, confirms, limit, apiCode);
    }

    /**
     * Returns list of unspent outputs.
     *
     * @param coin        The code of the coin to be returned, ie "btc" or "bch"
     * @param addressList a list of Base58 or xpub addresses
     * @param confirms    an integer for minimum confirms of the outputs, use null to indicate default
     * @param limit       an integer to limit number of transactions to display, use null to indicate default
     * @return {@link Call} object which can be executed synchronously or asynchronously to return a
     * response object
     */
    public Call<UnspentOutputs> getUnspentOutputs(String coin, List<String> addressList, Integer confirms, Integer limit) {
        String pipedAddresses = StringUtils.join(addressList, "|");
        return getApiInterface().getUnspent(coin, pipedAddresses, confirms, limit, apiCode);
    }

    /**
     * Returns block details.
     *
     * @param blockHash a block hash
     * @return {@link Call} object which can be executed synchronously or asynchronously to return a
     * response object
     */
    public Call<RawBlock> getBlockDetails(String blockHash) {
        return getExplorerInterface().getBlockDetails(blockHash, apiCode);
    }

    /**
     * Returns transaction details.
     *
     * @param txHash a transaction hash
     * @return {@link Call} object which can be executed synchronously or asynchronously to return a
     * response object
     */
    public Call<Transaction> getTransactionDetails(String txHash) {
        return getExplorerInterface().getTransactionDetails(txHash, apiCode);
    }

    /**
     * Returns list of block details at a certain height.
     *
     * @param height the block height
     * @return {@link Call} object which can be executed synchronously or asynchronously to return a
     * response object
     */
    public Call<RawBlocks> getBlocksAtHeight(long height) {
        return getExplorerInterface().getBlocksAtHeight(height, "json", apiCode);
    }

    /**
     * Returns the latest block.
     *
     * @return {@link Call} object which can be executed synchronously or asynchronously to return a
     * response object
     */
    public Call<Block> getLatestBlock() {
        return getExplorerInterface().getLatestBlock(apiCode);
    }

    /**
     * Returns list of the latest 10 unconfirmed transactions.
     *
     * @return {@link Call} object which can be executed synchronously or asynchronously to return a
     * response object
     */
    public Call<Transactions> getUnconfirmedTransactions() {
        return getExplorerInterface().getUnconfirmedTransactions("json", apiCode);
    }

    /**
     * Get all blocks of a day which the time stamp falls into.
     *
     * @param timeInMilliseconds the time stamp
     * @return {@link Call} object which can be executed synchronously or asynchronously to return a
     * response object
     */
    public Call<RawBlocks> getBlocksAtTime(long timeInMilliseconds) {
        return getExplorerInterface().getBlocksAtTime(timeInMilliseconds, "json", apiCode);
    }

    /**
     * Get the latest 100 blocks mined by a specific pool.
     *
     * @param pool the name of the pool(example: "AntPool")
     * @return {@link Call} object which can be executed synchronously or asynchronously to return a
     * response object
     */
    public Call<RawBlocks> getBlocksFromPool(String pool) {
        return getExplorerInterface().getBlocksFromPool(pool, "json", apiCode);
    }

    /**
     * Returns xpub summary on a xpub provided, with its overall balance and its transactions.
     *
     * @param xpub   a xpub address
     * @param filter the filter for transactions selection, use null to indicate default
     * @param limit  an integer to limit number of transactions to display, use null to indicate default
     * @param offset an integer to set number of transactions to skip when fetch
     * @return {@link XpubFull} an object to represent the xpub summary
     */
    public XpubFull getXpub(String xpub, FilterType filter, Integer limit, Integer offset) {
        Call<MultiAddress> xpubMultiAddressCall = getMultiAddress(Collections.singletonList(xpub), filter, limit, offset);
        MultiAddress xpubMultiAddress = null;
        try {
            xpubMultiAddress = xpubMultiAddressCall.execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new XpubFull(xpubMultiAddress.getAddresses().get(0), xpubMultiAddress.getTxs());
    }

    /**
     * Returns a list of exportable address history.
     *
     * @param addressList List of addresses to include in result
     * @param start       Start date (example: "05/05/2016")
     * @param end         End date (example: "05/05/2016")
     * @param currency    (example: "USD")
     * @return {@link Call} object which can be executed synchronously or asynchronously to return a
     * response object
     * @deprecated As of 0.2.0, will be removed in future releases
     */
    @Deprecated
    public Call<List<ExportHistory>> getExportHistory(List<String> addressList, String start,
                                                      String end, String currency) {
        String pipedAddresses = StringUtils.join(addressList, "|");
        return getExplorerInterface().getExportHistory(pipedAddresses, start, end, currency, apiCode);
    }

    /**
     * Fetches address information by base58 address
     *
     * @param address
     * @param limit   Txs limit
     * @param offset  Offset for txs
     * @return {@link Call} object which can be executed synchronously or asynchronously to return a
     * response object
     * @deprecated As of 0.2.0, replaced by {@link #getAddress(String, FilterType, Integer, Integer)}
     */
    @Deprecated
    public Call<AddressFull> getAddress(String address, int limit, int offset) {
        return getExplorerInterface().getAddress(address, limit, offset, null, "json", apiCode);
    }

    /**
     * Returns address transaction details and a list of transactions.
     *
     * @param addressList
     * @param filter      Sent = 1, Received = 2, Moved = 3, All = 4, Confirmed only = 5,
     *                    Remove unspent = 6, Unconfirmed only = 7
     * @param limit       Txs limit
     * @param offset      Offset for txs
     * @return {@link Call} object which can be executed synchronously or asynchronously to return a
     * response object
     * @deprecated As of 0.2.0, replaced by {@link #getMultiAddress(List, FilterType, Integer, Integer)}
     */
    @Deprecated
    public Call<MultiAddress> getMultiAddress(List<String> addressList, String context, int filter, int limit,
                                              int offset) {
        String pipedAddresses = StringUtils.join(addressList, "|");
        return getExplorerInterface()
                .getMultiAddress(pipedAddresses, limit, offset, filter, context, apiCode);
    }

    /**
     * Returns transaction details.
     *
     * @param hash
     * @return {@link Call} object which can be executed synchronously or asynchronously to return a
     * response object
     * @deprecated As of 0.2.0, replaced by {@link #getTransactionDetails(String)}
     */
    @Deprecated
    public Call<Transaction> getTransaction(String hash) {
        return getExplorerInterface().getTransaction(hash, "json", apiCode);
    }

    /**
     * Returns transaction details.
     *
     * @param txIndex
     * @return {@link Call} object which can be executed synchronously or asynchronously to return a
     * response object
     * @deprecated As of 0.2.0, will be removed in future releases
     */
    @Deprecated
    public Call<Transaction> getTransactionDetails(long txIndex) {
        return getExplorerInterface().getTransactionDetails(txIndex, apiCode);
    }

    /**
     * Returns block details.
     *
     * @param blockIndex
     * @return {@link Call} object which can be executed synchronously or asynchronously to return a
     * response object
     * @deprecated As of 0.2.0, will be removed in future releases
     */
    @Deprecated
    public Call<RawBlock> getBlockDetails(long blockIndex) {
        return getExplorerInterface().getBlockDetails(blockIndex, apiCode);
    }

    /**
     * Returns list of unspent outputs.
     *
     * @param addressList
     * @return {@link Call} object which can be executed synchronously or asynchronously to return a
     * response object
     * @deprecated As of 0.2.0, replaced by {@link #getUnspentOutputs(List, Integer, Integer)}
     */
    @Deprecated
    public Call<UnspentOutputs> getUnspentOutputs(List<String> addressList) {
        String pipedAddresses = StringUtils.join(addressList, "|");
        return getExplorerInterface().getUnspent(pipedAddresses, null, null, apiCode);
    }
}
