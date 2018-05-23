package test;

import com.codahale.shamir.Scheme;
import com.epitomecl.kmp.core.common.SharingData;
import com.epitomecl.kmp.core.wallet.CryptoType;
import com.epitomecl.kmp.core.wallet.HDWalletData;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SecretSharingTest {
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private static final String source = "hello there";

    @Test
    public void test1_SecretSharing() throws Exception {

        final Scheme scheme = Scheme.of(5, 3);
        final byte[] secret = source.getBytes(StandardCharsets.UTF_8);
        final Map<Integer, byte[]> parts = scheme.split(secret);

        HashMap<Integer, byte[]> share = new HashMap<>();
        share.put(1, parts.get(1));
        share.put(3, parts.get(3));
        share.put(4, parts.get(4));

        final byte[] recovered = scheme.join(share);
        String dest = new String(recovered, StandardCharsets.UTF_8);

        Assert.assertEquals(source, dest);
    }

    @Test
    public void test2_Wallet() {
        try {
            HDWalletData hdWallet_bitcoin = new HDWalletData(CryptoType.BITCOIN_TESTNET, "My test wallet");
            String mnemonics = String.join(" ", hdWallet_bitcoin.getMnemonic());

            SharingData sharing = new SharingData(mnemonics);

            Map<Integer, byte[]> parts = sharing.getSharingParts();

            String source = sharing.getJoinData();

            logger.info("Source data: " + source);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}