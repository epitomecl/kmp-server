package test.rpc;

import org.json.simple.JSONObject;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;

public class RPCClientTest {
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Test
    public void test() {
        RPCClient rpcClient = new RPCClient();
        JSONObject jsonObject = rpcClient.getInfo();
        logger.info("{}", jsonObject);
    }
}
