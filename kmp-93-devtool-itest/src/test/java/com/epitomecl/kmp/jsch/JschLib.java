package com.epitomecl.kmp.jsch;

import com.jcraft.jsch.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.lang.invoke.MethodHandles;

public class JschLib {
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public Session connect(String username, String privateKey, String host, int port) {
        try {
            JSch jsch = new JSch();
            jsch.addIdentity(privateKey);
            Session session = jsch.getSession(username, host, port);
            session.setUserInfo(new MyUserInfo());
            session.connect();
            return session;
        } catch (JSchException e) {
            throw new IllegalStateException(e);
        }
    }

    public void doExec(Session session, String command) {
        logger.info("{}", command);
        ChannelExec channelExec;
        try {
            channelExec = (ChannelExec) session.openChannel("exec");
        } catch (JSchException e) {
            throw new IllegalStateException(e);
        }

        channelExec.setCommand(command);
        // X Forwarding
        // channelExec.setXForwarding(true);

        //channelExec.setInputStream(System.in);
        channelExec.setInputStream(null);

        //channelExec.setOutputStream(System.out);

        //FileOutputStream fos=new FileOutputStream("/tmp/stderr");
        //channelExec.setErrStream(fos);
        channelExec.setErrStream(System.err);

        try {
            InputStream in = channelExec.getInputStream();
            channelExec.connect();

            byte[] tmp = new byte[1024];
            while (true) {
                while (in.available() > 0) {
                    int i = in.read(tmp, 0, 1024);
                    if (i < 0) break;
                    logger.info(new String(tmp, 0, i));
                }
                if (channelExec.isClosed()) {
                    if (in.available() > 0) continue;
                    logger.info("exit-status: " + channelExec.getExitStatus());
                    break;
                }
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                    logger.warn("{}", e);
                }
            }
        } catch (IOException | JSchException e) {
            e.printStackTrace();
        }

        channelExec.disconnect();
    }

    public void doScpTo(Session session, String lfilename, String rfilename) {
        logger.info("scp {} {}", lfilename, rfilename);
        FileInputStream fis = null;
        try {
            boolean ptimestamp = true;

            // exec 'scp -t rfile' remotely
            String command = "scp " + (ptimestamp ? "-p" : "") + " -t " + rfilename;
            ChannelExec channel = (ChannelExec) session.openChannel("exec");
            channel.setCommand(command);

            // get I/O streams for remote scp
            OutputStream out = channel.getOutputStream();
            InputStream in = channel.getInputStream();

            channel.connect();

            if (checkAck(in) != 0) {
                System.exit(0);
            }

            File lfile = new File(lfilename);

            if (ptimestamp) {
                command = "T" + (lfile.lastModified() / 1000) + " 0";
                // The access time should be sent here,
                // but it is not accessible with JavaAPI ;-<
                command += (" " + (lfile.lastModified() / 1000) + " 0\n");
                out.write(command.getBytes());
                out.flush();
                if (checkAck(in) != 0) {
                    System.exit(0);
                }
            }

            // send "C0644 filesize filename", where filename should not include '/'
            long filesize = lfile.length();
            command = "C0644 " + filesize + " ";
            if (lfilename.lastIndexOf('/') > 0) {
                command += lfilename.substring(lfilename.lastIndexOf('/') + 1);
            } else {
                command += lfilename;
            }
            command += "\n";
            out.write(command.getBytes());
            out.flush();
            if (checkAck(in) != 0) {
                System.exit(0);
            }

            // send a content of lfile
            fis = new FileInputStream(lfilename);
            byte[] buf = new byte[1024];
            while (true) {
                int len = fis.read(buf, 0, buf.length);
                if (len <= 0) break;
                out.write(buf, 0, len); //out.flush();
            }
            fis.close();
            fis = null;
            // send '\0'
            buf[0] = 0;
            out.write(buf, 0, 1);
            out.flush();
            if (checkAck(in) != 0) {
                System.exit(0);
            }
            out.close();

            channel.disconnect();
        } catch (Exception e) {
            logger.error("{}", e);
            try {
                if (fis != null) fis.close();
            } catch (Exception ee) {
                logger.warn("{}", ee);
            }
        }
    }

    private int checkAck(InputStream in) throws IOException {
        int b = in.read();
        // b may be 0 for success,
        //          1 for error,
        //          2 for fatal error,
        //          -1
        if (b == 0) return b;
        if (b == -1) return b;

        if (b == 1 || b == 2) {
            StringBuffer sb = new StringBuffer();
            int c;
            do {
                c = in.read();
                sb.append((char) c);
            }
            while (c != '\n');
            if (b == 1) { // error
                logger.error("{}", sb);
            }
            if (b == 2) { // fatal error
                logger.error("{}", sb);
            }
        }
        return b;
    }

}

class MyUserInfo implements UserInfo {
    private String password;

    MyUserInfo() {
    }

    MyUserInfo(String password) {
        this.password = password;
    }

    @Override
    public String getPassphrase() {
        return null;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean promptPassphrase(String arg0) {
        return true;
    }

    @Override
    public boolean promptPassword(String arg0) {
        return true;
    }

    @Override
    public boolean promptYesNo(String arg0) {
        return true;
    }

    @Override
    public void showMessage(String arg0) {
        // empty
    }
}