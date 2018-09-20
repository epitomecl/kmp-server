/*
 * Decompiled with CFR 0_132.
 * 
 * Could not load the following classes:
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package org.bitcoin;

import java.security.AccessControlException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Secp256k1Context {
    private static final boolean enabled;
    private static final long context;
    private static final Logger log;

    public static boolean isEnabled() {
        return enabled;
    }

    public static long getContext() {
        if (!enabled) {
            return -1L;
        }
        return context;
    }

    private static native long secp256k1_init_context();

    static {
        log = LoggerFactory.getLogger(Secp256k1Context.class);
        boolean isEnabled = true;
        long contextRef = -1L;
        try {
            System.loadLibrary("secp256k1");
            contextRef = Secp256k1Context.secp256k1_init_context();
        }
        catch (UnsatisfiedLinkError e) {
            log.info(e.toString());
            isEnabled = false;
        }
        catch (AccessControlException e) {
            log.debug(e.toString());
            isEnabled = false;
        }
        enabled = isEnabled;
        context = contextRef;
    }
}

