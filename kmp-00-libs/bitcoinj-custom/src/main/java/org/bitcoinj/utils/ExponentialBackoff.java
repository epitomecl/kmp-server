/*
 * Decompiled with CFR 0_132.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Preconditions
 *  com.google.common.primitives.Longs
 */
package org.bitcoinj.utils;

import com.google.common.base.Preconditions;
import com.google.common.primitives.Longs;
import org.bitcoinj.core.Utils;

public class ExponentialBackoff
implements Comparable<ExponentialBackoff> {
    public static final int DEFAULT_INITIAL_MILLIS = 100;
    public static final float DEFAULT_MULTIPLIER = 1.1f;
    public static final int DEFAULT_MAXIMUM_MILLIS = 30000;
    private float backoff;
    private long retryTime;
    private final Params params;

    public ExponentialBackoff(Params params) {
        this.params = params;
        this.trackSuccess();
    }

    public final void trackSuccess() {
        this.backoff = this.params.initial;
        this.retryTime = Utils.currentTimeMillis();
    }

    public void trackFailure() {
        this.retryTime = Utils.currentTimeMillis() + (long)this.backoff;
        this.backoff = Math.min(this.backoff * this.params.multiplier, this.params.maximum);
    }

    public long getRetryTime() {
        return this.retryTime;
    }

    @Override
    public int compareTo(ExponentialBackoff other) {
        return Longs.compare((long)this.retryTime, (long)other.retryTime);
    }

    public String toString() {
        return "ExponentialBackoff retry=" + this.retryTime + " backoff=" + this.backoff;
    }

    public static class Params {
        private final float initial;
        private final float multiplier;
        private final float maximum;

        public Params(long initialMillis, float multiplier, long maximumMillis) {
            Preconditions.checkArgument((boolean)(multiplier > 1.0f), (Object)"multiplier must be greater than 1.0");
            Preconditions.checkArgument((boolean)(maximumMillis >= initialMillis), (Object)"maximum must not be less than initial");
            this.initial = initialMillis;
            this.multiplier = multiplier;
            this.maximum = maximumMillis;
        }

        public Params() {
            this.initial = 100.0f;
            this.multiplier = 1.1f;
            this.maximum = 30000.0f;
        }
    }

}

