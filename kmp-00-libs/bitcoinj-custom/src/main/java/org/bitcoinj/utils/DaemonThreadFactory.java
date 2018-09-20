/*
 * Decompiled with CFR 0_132.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nonnull
 *  javax.annotation.Nullable
 */
package org.bitcoinj.utils;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class DaemonThreadFactory
implements ThreadFactory {
    @Nullable
    private final String name;

    public DaemonThreadFactory(@Nullable String name) {
        this.name = name;
    }

    public DaemonThreadFactory() {
        this(null);
    }

    @Override
    public Thread newThread(@Nonnull Runnable runnable) {
        Thread thread = Executors.defaultThreadFactory().newThread(runnable);
        thread.setDaemon(true);
        if (this.name != null) {
            thread.setName(this.name);
        }
        return thread;
    }
}

