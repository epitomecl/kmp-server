package test.rpc;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import wf.bitcoin.javabitcoindrpcclient.BitcoinJSONRPCClient;

import java.lang.invoke.MethodHandles;
import java.net.MalformedURLException;
import java.net.URL;

public class BitcoinJSONRPCClientTest {
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private static final String host = "epitome.iptime.org";
    private static int port = 18332;
    private static final String user = "username";
    private static final String password = "password";


    @Test
    public void test() {
        try {
            URL url = new URL("http://" + user + ':' + password + "@" + host + ":" + port + "/");

            BitcoinJSONRPCClient bitcoinClient = new BitcoinJSONRPCClient(url);
//            logger.info("{}", bitcoinClient.getInfo());

            logger.info("{}", bitcoinClient.getBestBlockHash());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
