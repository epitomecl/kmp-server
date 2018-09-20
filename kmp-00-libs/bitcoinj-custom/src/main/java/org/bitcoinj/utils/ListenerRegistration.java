/*
 * Decompiled with CFR 0_132.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Preconditions
 */
package org.bitcoinj.utils;

import com.google.common.base.Preconditions;
import java.util.List;
import java.util.concurrent.Executor;

public class ListenerRegistration<T> {
    public final T listener;
    public final Executor executor;

    public ListenerRegistration(T listener, Executor executor) {
        this.listener = Preconditions.checkNotNull(listener);
        this.executor = (Executor)Preconditions.checkNotNull((Object)executor);
    }

    public static <T> boolean removeFromList(T listener, List<? extends ListenerRegistration<T>> list) {
        Preconditions.checkNotNull(listener);
        ListenerRegistration<T> item = null;
        for (ListenerRegistration<T> registration : list) {
            if (registration.listener != listener) continue;
            item = registration;
            break;
        }
        return item != null && list.remove(item);
    }
}

