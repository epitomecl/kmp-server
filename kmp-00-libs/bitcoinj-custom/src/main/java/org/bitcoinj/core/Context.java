/*
 * Decompiled with CFR 0_132.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Preconditions
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package org.bitcoinj.core;

import com.google.common.base.Preconditions;
import org.bitcoinj.core.Coin;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.core.Transaction;
import org.bitcoinj.core.TxConfidenceTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Context {
    private static final Logger log = LoggerFactory.getLogger(Context.class);
    public static final int DEFAULT_EVENT_HORIZON = 100;
    private final TxConfidenceTable confidenceTable;
    private final NetworkParameters params;
    private final int eventHorizon;
    private final boolean ensureMinRequiredFee;
    private final Coin feePerKb;
    private static volatile Context lastConstructed;
    private static boolean isStrictMode;
    private static final ThreadLocal<Context> slot;

    public Context(NetworkParameters params) {
        this(params, 100, Transaction.DEFAULT_TX_FEE, true);
    }

    public Context(NetworkParameters params, int eventHorizon, Coin feePerKb, boolean ensureMinRequiredFee) {
        log.info("Creating bitcoinj {} context.", (Object)"0.15-SNAPSHOT");
        this.confidenceTable = new TxConfidenceTable();
        this.params = params;
        this.eventHorizon = eventHorizon;
        this.ensureMinRequiredFee = ensureMinRequiredFee;
        this.feePerKb = feePerKb;
        lastConstructed = this;
        slot.set(this);
    }

    public static Context get() {
        Context tls = slot.get();
        if (tls == null) {
            if (isStrictMode) {
                log.error("Thread is missing a bitcoinj context.");
                log.error("You should use Context.propagate() or a ContextPropagatingThreadFactory.");
                throw new IllegalStateException("missing context");
            }
            if (lastConstructed == null) {
                throw new IllegalStateException("You must construct a Context object before using bitcoinj!");
            }
            slot.set(lastConstructed);
            log.error("Performing thread fixup: you are accessing bitcoinj via a thread that has not had any context set on it.");
            log.error("This error has been corrected for, but doing this makes your app less robust.");
            log.error("You should use Context.propagate() or a ContextPropagatingThreadFactory.");
            log.error("Please refer to the user guide for more information about this.");
            log.error("Thread name is {}.", (Object)Thread.currentThread().getName());
            return lastConstructed;
        }
        return tls;
    }

    public static void enableStrictMode() {
        isStrictMode = true;
    }

    public static Context getOrCreate(NetworkParameters params) {
        Context context;
        try {
            context = Context.get();
        }
        catch (IllegalStateException e) {
            log.warn("Implicitly creating context. This is a migration step and this message will eventually go away.");
            Context context2 = new Context(params);
            return context2;
        }
        if (context.getParams() != params) {
            throw new IllegalStateException("Context does not match implicit network params: " + context.getParams() + " vs " + params);
        }
        return context;
    }

    public static void propagate(Context context) {
        slot.set((Context)Preconditions.checkNotNull((Object)context));
    }

    public TxConfidenceTable getConfidenceTable() {
        return this.confidenceTable;
    }

    public NetworkParameters getParams() {
        return this.params;
    }

    public int getEventHorizon() {
        return this.eventHorizon;
    }

    public Coin getFeePerKb() {
        return this.feePerKb;
    }

    public boolean isEnsureMinRequiredFee() {
        return this.ensureMinRequiredFee;
    }

    static {
        slot = new ThreadLocal();
    }
}

