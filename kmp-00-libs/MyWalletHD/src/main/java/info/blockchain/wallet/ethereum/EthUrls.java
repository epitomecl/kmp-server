package info.blockchain.wallet.ethereum;

public final class EthUrls {

    private EthUrls() {
        throw new UnsupportedOperationException("You can't implement this class");
    }

    /* Base endpoint for all ETH operations */
    private static final String ETH = "eth";

    /* Additional paths for certain queries */
    public static final String IS_CONTRACT = "/isContract";
    static final String BALANCE = "/balance";

    /* Complete paths */
    public static final String ACCOUNT = ETH + "/account";
    public static final String PUSH_TX = ETH + "/pushtx";
    public static final String LATEST_BLOCK = ETH + "/latestblock";
    public static final String TX = ETH + "/tx";
    static final String FEES = ETH + "/fees";

}
