package info.blockchain.wallet.metadata;

public final class MetadataUrls {

    private MetadataUrls() {
        throw new UnsupportedOperationException("You can't implement this class");
    }

    /* Base endpoint for Contacts/Shared Metadata/CryptoMatrix */
    private static final String IWCS = "iwcs";

    /* Base endpoint for generic Metadata */
    public static final String METADATA = "metadata";

    /* Complete paths */
    public static final String AUTH = IWCS + "/auth";
    public static final String TRUSTED = IWCS + "/trusted";
    public static final String MESSAGE = IWCS + "/message";
    public static final String MESSAGES = IWCS + "/messages";
    public static final String SHARE = IWCS + "/share";

}
