package com.epitomecl.kmp.jsch;

import com.epitomecl.kmp.common.HomeConfigurator;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.invoke.MethodHandles;

public class JschTest {
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public static void main(String[] args) throws JSchException, IOException {
        HomeConfigurator.changeLogConfiguration();

        JschLib jschLib = new JschLib();
        Session session = jschLib.connect("epitome", "epitome", "epitome.iptime.org", 22);

        // 초기상태
        jschLib.doExec(session, "ls -1");

        // stop
        jschLib.doExec(session, "kmp/bin/linux/stop.sh");
        jschLib.doExec(session, "pkill -9 -ef kmp-server");

        // remove
        jschLib.doExec(session, "rm -rf kmp* epitomecl-kmp*");
        jschLib.doExec(session, "ls -1");

        // copy
        jschLib.doScpTo(session, "../kmp-devtool-release/target/epitomecl-kmp.tar.gz", "/home/epitome");
        jschLib.doExec(session, "ls -1");

        // install
        jschLib.doExec(session, "mkdir kmp");
        jschLib.doExec(session, "tar -C kmp -xzvf epitomecl-kmp.tar.gz");
        jschLib.doExec(session, "ls -1");

        // init
//        jschLib.doExec(session, "kmp/bin/linux/init.sh");

        // start
        // redirect to /dev/null for ssh with nohup
        jschLib.doExec(session, "kmp/bin/linux/start.sh > /dev/null 2>&1");
    }
}
