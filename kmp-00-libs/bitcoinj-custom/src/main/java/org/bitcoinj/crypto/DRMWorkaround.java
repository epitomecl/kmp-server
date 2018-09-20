/*
 * Decompiled with CFR 0_132.
 * 
 * Could not load the following classes:
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package org.bitcoinj.crypto;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.security.Permission;
import org.bitcoinj.core.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DRMWorkaround {
    private static Logger log = LoggerFactory.getLogger(DRMWorkaround.class);
    private static boolean done = false;

    public static void maybeDisableExportControls() {
        if (done) {
            return;
        }
        done = true;
        if (Utils.isAndroidRuntime()) {
            return;
        }
        try {
            Field gate = Class.forName("javax.crypto.JceSecurity").getDeclaredField("isRestricted");
            gate.setAccessible(true);
            gate.setBoolean(null, false);
            Field allPerm = Class.forName("javax.crypto.CryptoAllPermission").getDeclaredField("INSTANCE");
            allPerm.setAccessible(true);
            Object accessAllAreasCard = allPerm.get(null);
            Constructor<?> constructor = Class.forName("javax.crypto.CryptoPermissions").getDeclaredConstructor(new Class[0]);
            constructor.setAccessible(true);
            Object coll = constructor.newInstance(new Object[0]);
            Method addPerm = Class.forName("javax.crypto.CryptoPermissions").getDeclaredMethod("add", Permission.class);
            addPerm.setAccessible(true);
            addPerm.invoke(coll, accessAllAreasCard);
            Field defaultPolicy = Class.forName("javax.crypto.JceSecurity").getDeclaredField("defaultPolicy");
            defaultPolicy.setAccessible(true);
            defaultPolicy.set(null, coll);
        }
        catch (Exception e) {
            log.warn("Failed to deactivate AES-256 barrier logic, Tor mode/BIP38 decryption may crash if this JVM requires it: " + e.getMessage());
        }
    }
}

