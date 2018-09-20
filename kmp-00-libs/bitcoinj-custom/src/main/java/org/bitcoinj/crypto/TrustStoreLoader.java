/*
 * Decompiled with CFR 0_132.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nonnull
 */
package org.bitcoinj.crypto;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import javax.annotation.Nonnull;
import org.bitcoinj.crypto.X509Utils;

public interface TrustStoreLoader {
    public static final String DEFAULT_KEYSTORE_TYPE = KeyStore.getDefaultType();
    public static final String DEFAULT_KEYSTORE_PASSWORD = "changeit";

    public KeyStore getKeyStore() throws FileNotFoundException, KeyStoreException;

    public static class FileTrustStoreLoader
    implements TrustStoreLoader {
        private final File path;

        public FileTrustStoreLoader(@Nonnull File path) throws FileNotFoundException {
            if (!path.exists()) {
                throw new FileNotFoundException(path.toString());
            }
            this.path = path;
        }

        @Override
        public KeyStore getKeyStore() throws FileNotFoundException, KeyStoreException {
            return X509Utils.loadKeyStore(DEFAULT_KEYSTORE_TYPE, TrustStoreLoader.DEFAULT_KEYSTORE_PASSWORD, new FileInputStream(this.path));
        }
    }

    public static class DefaultTrustStoreLoader
    implements TrustStoreLoader {
        @Override
        public KeyStore getKeyStore() throws FileNotFoundException, KeyStoreException {
            String keystorePath = null;
            String keystoreType = DEFAULT_KEYSTORE_TYPE;
            try {
                Class<?> version = Class.forName("android.os.Build$VERSION");
                if (version.getDeclaredField("SDK_INT").getInt(version) >= 14) {
                    return this.loadIcsKeyStore();
                }
                keystoreType = "BKS";
                keystorePath = System.getProperty("java.home") + "/etc/security/cacerts.bks".replace('/', File.separatorChar);
            }
            catch (ClassNotFoundException version) {
            }
            catch (NoSuchFieldException e) {
                throw new RuntimeException(e);
            }
            catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
            if (keystorePath == null) {
                keystorePath = System.getProperty("javax.net.ssl.trustStore");
            }
            if (keystorePath == null) {
                return this.loadFallbackStore();
            }
            try {
                return X509Utils.loadKeyStore(keystoreType, TrustStoreLoader.DEFAULT_KEYSTORE_PASSWORD, new FileInputStream(keystorePath));
            }
            catch (FileNotFoundException e) {
                return this.loadFallbackStore();
            }
        }

        private KeyStore loadIcsKeyStore() throws KeyStoreException {
            try {
                KeyStore keystore = KeyStore.getInstance("AndroidCAStore");
                keystore.load(null, null);
                return keystore;
            }
            catch (IOException x) {
                throw new KeyStoreException(x);
            }
            catch (GeneralSecurityException x) {
                throw new KeyStoreException(x);
            }
        }

        private KeyStore loadFallbackStore() throws FileNotFoundException, KeyStoreException {
            return X509Utils.loadKeyStore("JKS", TrustStoreLoader.DEFAULT_KEYSTORE_PASSWORD, this.getClass().getResourceAsStream("cacerts"));
        }
    }

}

