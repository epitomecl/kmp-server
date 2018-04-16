package com.epitomecl.kmp.jsch;

import com.epitomecl.kmp.common.HomeConfigurator;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.invoke.MethodHandles;

public class JschTest {
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Test
    public void test() throws JSchException, IOException {
        HomeConfigurator.changeLogConfiguration();

        JschLib jschLib = new JschLib();
        Session session = jschLib.connect("epitome", "epitome", "epitome.iptime.org", 22);

        // 초기상태
        jschLib.doExec(session, "ls -1");

        // stop
        jschLib.doExec(session, "coinvest/bin/linux/stop.sh");
        jschLib.doExec(session, "ps -ef | grep java | grep coinvest | grep -v grep | awk '{print $2}' | xargs kill -9");

        // remove
        jschLib.doExec(session, "rm -rf coinvest* paprika*");
        jschLib.doExec(session, "ls -1");

        // copy
        jschLib.doScpTo(session, "../worker-92-devtool-release/target/paprika.tar.gz", "/home/jdlee");
        jschLib.doExec(session, "ls -1");

        // install
        jschLib.doExec(session, "mkdir coinvest");
        jschLib.doExec(session, "tar -C coinvest -xzvf paprika.tar.gz");
        jschLib.doExec(session, "ls -1");

        // init
        jschLib.doExec(session, "coinvest/bin/linux/init.sh");
        jschLib.doExec(session, "coinvest/bin/linux/keytool.sh");

        // start
        // redirect to /dev/null for ssh with nohup
        jschLib.doExec(session, "coinvest/bin/linux/start.sh > /dev/null 2>&1");
    }
}
