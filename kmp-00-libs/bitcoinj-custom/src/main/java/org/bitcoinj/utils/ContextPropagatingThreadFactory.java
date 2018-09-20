/*
 * Decompiled with CFR 0_132.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Throwables
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package org.bitcoinj.utils;

import com.google.common.base.Throwables;
import java.util.concurrent.ThreadFactory;
import org.bitcoinj.core.Context;
import org.bitcoinj.utils.Threading;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ContextPropagatingThreadFactory
implements ThreadFactory {
    private static final Logger log = LoggerFactory.getLogger(ContextPropagatingThreadFactory.class);
    private final String name;
    private final int priority;

    public ContextPropagatingThreadFactory(String name, int priority) {
        this.name = name;
        this.priority = priority;
    }

    public ContextPropagatingThreadFactory(String name) {
        this(name, 5);
    }

    @Override
    public Thread newThread(final Runnable r) {
        final Context context = Context.get();
        Thread thread = new Thread(new Runnable(){

            @Override
            public void run() {
                try {
                    Context.propagate(context);
                    r.run();
                }
                catch (Exception e) {
                    log.error("Exception in thread", (Throwable)e);
                    Throwables.propagate((Throwable)e);
                }
            }
        }, this.name);
        thread.setPriority(this.priority);
        thread.setDaemon(true);
        Thread.UncaughtExceptionHandler handler = Threading.uncaughtExceptionHandler;
        if (handler != null) {
            thread.setUncaughtExceptionHandler(handler);
        }
        return thread;
    }

}

