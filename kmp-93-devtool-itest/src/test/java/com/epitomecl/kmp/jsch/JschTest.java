package com.epitomecl.kmp.jsch;

import com.epitomecl.kmp.core.common.HomeConfigurator;
import com.jcraft.jsch.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;

public class JschTest {
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public static void main(String[] args) {
        HomeConfigurator.changeLogConfiguration();

        String download_dir = "/data/ubuntu/kmp-server";

        String username = "ubuntu";
        String privateKey = System.getProperty("user.home") + "/.ssh/id_rsa";
        String host = "kmp.epitomecl.com";
        int port = 22;

        // session connect
        JschLib jschLib = new JschLib();
        Session session = jschLib.connect(username, privateKey, host, port);

        // 초기상태
        jschLib.doExec(session, "ls -1");

        // stop
//        jschLib.doExec(session, "kmp/bin/linux/stop.sh");
        jschLib.doExec(session, "pkill -9 -ef kmp-41-jh-2.0.0.war");

        // remove
        jschLib.doExec(session, "rm -rf " + download_dir);
        jschLib.doExec(session, "ls -1");
        jschLib.doExec(session, "mkdir -p " + download_dir);

        // copy
        jschLib.doScpTo(session, "./kmp-92-devtool-release/target/epitomecl-kmp.tar.gz", download_dir);
        jschLib.doExec(session, "ls -1");

        // install
        jschLib.doExec(session, "tar -C " + download_dir + " -xzvf " + download_dir + "/epitomecl-kmp.tar.gz");
        jschLib.doExec(session, "ls -1");

        // init
//        jschLib.doExec(session, "kmp/bin/linux/init.sh");

        // start
        // redirect to /dev/null for ssh with nohup
        jschLib.doExec(session, download_dir + "/bin/linux/start.sh > /dev/null 2>&1");

        // session disconnect
        session.disconnect();
    }
}
