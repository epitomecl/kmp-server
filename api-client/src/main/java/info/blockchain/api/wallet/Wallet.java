package info.blockchain.api.wallet;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import info.blockchain.api.data.*;
import info.blockchain.api.interfaces.ServiceWalletInterface;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit.Builder;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

/**
 * Created by riaanvos on 26/01/2017.
 */
public class Wallet {

    private ServiceWalletInterface serviceInterface;

    private String identifier;
    private String password;
    private String secondPassword;
    private String apiCode;

    /**
     * @param serviceURL URL to an instance of service-my-wallet-v3 (with trailing slash)
     * @param apiCode    API Code See <a href="https://api.blockchain.info/v2/apikey/request/">Request API key</a>
     * @param identifier Wallet identifier (GUID)
     * @param password   Decryption password
     */
    public Wallet(String serviceURL, String apiCode, String identifier, String password) {
        this(serviceURL, apiCode, identifier, password, null);
    }

    /**
     * @param serviceURL     URL to an instance of service-my-wallet-v3 (with trailing slash)
     * @param apiCode        API Code See <a href="https://api.blockchain.info/v2/apikey/request/">Request API key</a>
     * @param identifier     Wallet identifier (GUID)
     * @param password       Decryption password
     * @param secondPassword Second password
     */
    public Wallet(String serviceURL, String apiCode, String identifier, String password, String secondPassword) {
        this(serviceURL, apiCode, identifier, password, secondPassword, null);
    }

    /**
     * @param serviceURL     URL to an instance of service-my-wallet-v3 (with trailing slash)
     * @param apiCode        API Code. See <a href="https://api.blockchain.info/v2/apikey/request/">Request API key</a>
     * @param identifier     Wallet identifier (GUID)
     * @param password       Decryption password
     * @param secondPassword Second password
     */
    public Wallet(String serviceURL, String apiCode, String identifier, String password, String secondPassword, OkHttpClient okHttpClient) {
        this.apiCode = apiCode;
        this.identifier = identifier;
        this.password = password;
        this.secondPassword = secondPassword;
        this.serviceInterface = getServiceInterface(okHttpClient, serviceURL);
    }

    /**
     * The create_wallet method can be used to create a new blockchain.info bitcoin wallet.
     *
     * @param serviceURL URL to an instance of service-my-wallet-v3 (with trailing slash)
     * @param password   The password for the new wallet. Must be at least 10 characters in length.
     * @param apiCode    An API code with create wallets permission.
     * @param priv       Nullable. A private key to add to the wallet (Wallet import format preferred). (Optional)
     * @param label      Nullable. A label to set for the first address in the wallet. Alphanumeric only. (Optional)
     * @param email      Nullable. An email to associate with the new wallet i.e. the email address of the user you are creating this wallet on behalf of. (Optional)
     * @return {@link Call} object which can be executed synchronously or asynchronously to return a
     * response object
     */
    public static Call<CreateWalletResponse> createWallet(String serviceURL, String password, String apiCode, String priv, String label, String email,
                                                          OkHttpClient httpClient) {
        return getServiceInterface(httpClient, serviceURL).createWallet(password, apiCode, priv, label, email);
    }

    public static Call<CreateWalletResponse> createWallet(String serviceURL, String password, String apiCode, String priv, String label, String email) {
        return getServiceInterface(null, serviceURL).createWallet(password, apiCode, priv, label, email);
    }

    private static OkHttpClient getDefaultOkHttpClient() {
        return new OkHttpClient.Builder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .retryOnConnectionFailure(false)
                .build();
    }

    private static ServiceWalletInterface getServiceInterface(OkHttpClient okHttpClient, String serviceURL) {

        if (okHttpClient == null) {
            okHttpClient = getDefaultOkHttpClient();
        }

        Builder builder = new Builder();
        builder.baseUrl(serviceURL);
        builder.client(okHttpClient);
        builder.addConverterFactory(JacksonConverterFactory.create());

        return builder.build().create(ServiceWalletInterface.class);
    }

    /**
     * Fetches the wallet balance. Includes unconfirmed transactions and
     * possibly double spends.
     *
     * @return {@link Call} object which can be executed synchronously or asynchronously to return a
     * response object
     */
    public Call<WalletBalance> getBalance() {
        return serviceInterface.getBalance(identifier, password, apiCode);
    }

    /**
     * Sends bitcoin from your wallet to a single address.
     *
     * @param toAddress   Recipient bitcoin address
     * @param amount      Amount to send (in satoshi)
     * @param fromAddress Specific address to send from (optional, nullable)
     * @param fee         Transaction fee in satoshi. Must be greater than the default fee (optional, nullable).
     * @param note        Public note to include with the transaction (optional, nullable)
     * @return {@link Call} object which can be executed synchronously or asynchronously to return a
     * response object
     */
    public Call<WalletPaymentResponse> send(String toAddress, long amount, String fromAddress, Long fee, String note)
            throws JsonProcessingException {

        Map<String, Long> recipient = new HashMap<String, Long>();
        recipient.put(toAddress, amount);

        return sendMany(recipient, fromAddress, fee, note);
    }

    /**
     * Sends bitcoin from your wallet to multiple addresses.
     *
     * @param fromAddress  Specific address to send from (optional, nullable)
     * @param recipientmap Map with the structure of 'address':amount in satoshi (String:long)
     * @param fee          Transaction fee in satoshi. Must be greater than the default fee (optional, nullable).
     * @param note         Public note to include with the transaction (optional, nullable)
     * @return {@link Call} object which can be executed synchronously or asynchronously to return a
     * response object
     */
    public Call<WalletPaymentResponse> sendMany(Map<String, Long> recipientmap, String fromAddress, Long fee, String note)
            throws JsonProcessingException {

        String method = null;
        String to = null;
        String amount = null;
        String recipients = null;

        if (recipientmap.size() == 1) {
            method = "payment";
            Entry<String, Long> item = recipientmap.entrySet().iterator().next();
            to = item.getKey();
            amount = item.getValue().toString();
        } else {
            method = "sendmany";
            recipients = new ObjectMapper().writeValueAsString(recipientmap);
        }

        return serviceInterface.send(identifier, method,
                password, secondPassword, apiCode,
                to, amount, recipients, fromAddress,
                fee, note);
    }

    /**
     * Lists all active addresses in the wallet.
     *
     * @return {@link Call} object which can be executed synchronously or asynchronously to return a
     * response object
     */
    public Call<WalletAddressList> listAddresses() {
        return serviceInterface.listAddresses(identifier, password, apiCode);
    }

    /**
     * Retrieves an address from the wallet.
     *
     * @param address Address in the wallet to look up
     * @return {@link Call} object which can be executed synchronously or asynchronously to return a
     * response object
     */
    public Call<WalletAddress> getAddress(String address) {
        return serviceInterface.getAddress(identifier, password, apiCode, address);
    }

    /**
     * Generates a new address and adds it to the wallet.
     *
     * @param label Label to attach to this address (optional, nullable)
     * @return {@link Call} object which can be executed synchronously or asynchronously to return a
     * response object
     */
    public Call<WalletAddress> newAddress(String label) {
        return serviceInterface.newAddress(identifier, password, secondPassword, apiCode, label);
    }

    /**
     * Archives an address.
     *
     * @param address Address to archive
     * @return {@link Call} object which can be executed synchronously or asynchronously to return a
     * response object
     */
    public Call<WalletAddressArchiveResponse> archiveAddress(String address) {
        return serviceInterface.archiveAddress(identifier, password, apiCode, address);
    }

    /**
     * Unarchives an address.
     *
     * @param address Address to unarchive
     * @return {@link Call} object which can be executed synchronously or asynchronously to return a
     * response object
     */
    public Call<WalletAddressUnarchiveResponse> unarchiveAddress(String address) {
        return serviceInterface.unarchiveAddress(identifier, password, apiCode, address);
    }

    /**
     * Lists all active addresses in the wallet.
     *
     * @param confirmations Minimum number of confirmations transactions
     *                      must have before being included in the balance of addresses (can be 0)
     * @return {@link Call} object which can be executed synchronously or asynchronously to return a
     * response object
     * @deprecated As of 0.2.0, replaced by {@link #listAddresses()}
     */
    @Deprecated
    public Call<WalletAddressList> listAddresses(int confirmations) {
        return serviceInterface.listAddresses(identifier, password, apiCode);
    }

    /**
     * Retrieves an address from the wallet.
     *
     * @param address       Address in the wallet to look up
     * @param confirmations Minimum number of confirmations transactions
     *                      must have before being included in the balance of an addresses (can be 0)
     * @return {@link Call} object which can be executed synchronously or asynchronously to return a
     * response object
     * @deprecated As of 0.2.0, replaced by {@link #getAddress(String)}
     */
    @Deprecated
    public Call<WalletAddress> getAddress(String address, int confirmations) {
        return serviceInterface.getAddress(identifier, password, apiCode, address);
    }
}
