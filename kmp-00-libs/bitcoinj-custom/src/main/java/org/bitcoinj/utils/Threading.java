/*
 * Decompiled with CFR 0_132.
 * 
 * Could not load the following classes:
 *  com.google.common.util.concurrent.CycleDetectingLockFactory
 *  com.google.common.util.concurrent.CycleDetectingLockFactory$Policies
 *  com.google.common.util.concurrent.CycleDetectingLockFactory$Policy
 *  com.google.common.util.concurrent.ListeningExecutorService
 *  com.google.common.util.concurrent.MoreExecutors
 *  com.google.common.util.concurrent.Uninterruptibles
 *  javax.annotation.Nonnull
 *  javax.annotation.Nullable
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package org.bitcoinj.utils;

import com.google.common.util.concurrent.CycleDetectingLockFactory;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.common.util.concurrent.Uninterruptibles;
import com.google.common.util.concurrent.CycleDetectingLockFactory.Policies;
import com.google.common.util.concurrent.CycleDetectingLockFactory.Policy;
import java.lang.Thread.UncaughtExceptionHandler;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.locks.ReentrantLock;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.bitcoinj.core.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Threading {
    public static Executor USER_THREAD;
    public static final Executor SAME_THREAD;
    @Nullable
    public static volatile UncaughtExceptionHandler uncaughtExceptionHandler;
    private static Policy policy;
    public static CycleDetectingLockFactory factory;
    public static ListeningExecutorService THREAD_POOL;

    public Threading() {
    }

    public static void waitForUserCode() {
        final CountDownLatch latch = new CountDownLatch(1);
        USER_THREAD.execute(new Runnable() {
            public void run() {
                latch.countDown();
            }
        });
        Uninterruptibles.awaitUninterruptibly(latch);
    }

    public static ReentrantLock lock(String name) {
        return Utils.isAndroidRuntime() ? new ReentrantLock(true) : factory.newReentrantLock(name);
    }

    public static void warnOnLockCycles() {
        setPolicy(Policies.WARN);
    }

    public static void throwOnLockCycles() {
        setPolicy(Policies.THROW);
    }

    public static void ignoreLockCycles() {
        setPolicy(Policies.DISABLED);
    }

    public static void setPolicy(Policy policy) {
        policy = policy;
        factory = CycleDetectingLockFactory.newInstance(policy);
    }

    public static Policy getPolicy() {
        return policy;
    }

    static {
        throwOnLockCycles();
        USER_THREAD = new Threading.UserThread();
        SAME_THREAD = new Executor() {
            public void execute(@Nonnull Runnable runnable) {
                runnable.run();
            }
        };
        THREAD_POOL = MoreExecutors.listeningDecorator(Executors.newCachedThreadPool(new ThreadFactory() {
            public Thread newThread(Runnable r) {
                Thread t = new Thread(r);
                t.setName("Threading.THREAD_POOL worker");
                t.setDaemon(true);
                return t;
            }
        }));
    }

    public static class UserThread extends Thread implements Executor {
        private static final Logger log = LoggerFactory.getLogger(Threading.UserThread.class);
        public static int WARNING_THRESHOLD = 10000;
        private LinkedBlockingQueue<Runnable> tasks;

        public UserThread() {
            super("bitcoinj user thread");
            this.setDaemon(true);
            this.tasks = new LinkedBlockingQueue();
            this.start();
        }

        public void run() {
            while(true) {
                Runnable task = (Runnable)Uninterruptibles.takeUninterruptibly(this.tasks);

                try {
                    task.run();
                } catch (Throwable var4) {
                    log.warn("Exception in user thread", var4);
                    UncaughtExceptionHandler handler = Threading.uncaughtExceptionHandler;
                    if (handler != null) {
                        handler.uncaughtException(this, var4);
                    }
                }
            }
        }

        public void execute(Runnable command) {
            int size = this.tasks.size();
            if (size == WARNING_THRESHOLD) {
                log.warn("User thread has {} pending tasks, memory exhaustion may occur.\nIf you see this message, check your memory consumption and see if it's problematic or excessively spikey.\nIf it is, check for deadlocked or slow event handlers. If it isn't, try adjusting the constant \nThreading.UserThread.WARNING_THRESHOLD upwards until it's a suitable level for your app, or Integer.MAX_VALUE to disable.", size);
            }

            Uninterruptibles.putUninterruptibly(this.tasks, command);
        }
    }
}
