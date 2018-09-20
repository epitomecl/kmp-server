/*
 * Decompiled with CFR 0_132.
 */
package org.bitcoinj.utils;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.text.MessageFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class BriefLogFormatter
extends Formatter {
    private static final MessageFormat messageFormat = new MessageFormat("{3,date,HH:mm:ss} {0} {1}.{2}: {4}\n{5}");
    private static Logger logger;

    public static void init() {
        logger = Logger.getLogger("");
        Handler[] handlers = logger.getHandlers();
        if (handlers.length > 0) {
            handlers[0].setFormatter(new BriefLogFormatter());
        }
    }

    public static void initVerbose() {
        BriefLogFormatter.init();
        logger.setLevel(Level.ALL);
        logger.log(Level.FINE, "test");
    }

    public static void initWithSilentBitcoinJ() {
        BriefLogFormatter.init();
        Logger.getLogger("org.bitcoinj").setLevel(Level.SEVERE);
    }

    @Override
    public String format(LogRecord logRecord) {
        Object[] arguments = new Object[6];
        arguments[0] = logRecord.getThreadID();
        String fullClassName = logRecord.getSourceClassName();
        int lastDot = fullClassName.lastIndexOf(46);
        String className = fullClassName.substring(lastDot + 1);
        arguments[1] = className;
        arguments[2] = logRecord.getSourceMethodName();
        arguments[3] = new Date(logRecord.getMillis());
        arguments[4] = logRecord.getMessage();
        if (logRecord.getThrown() != null) {
            StringWriter result = new StringWriter();
            logRecord.getThrown().printStackTrace(new PrintWriter(result));
            arguments[5] = result.toString();
        } else {
            arguments[5] = "";
        }
        return messageFormat.format(arguments);
    }
}

