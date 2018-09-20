/*
 * Decompiled with CFR 0_132.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Joiner
 *  javax.annotation.Nonnull
 *  javax.annotation.Nullable
 *  org.spongycastle.asn1.ASN1Encodable
 *  org.spongycastle.asn1.ASN1ObjectIdentifier
 *  org.spongycastle.asn1.ASN1String
 *  org.spongycastle.asn1.x500.AttributeTypeAndValue
 *  org.spongycastle.asn1.x500.RDN
 *  org.spongycastle.asn1.x500.X500Name
 *  org.spongycastle.asn1.x500.style.RFC4519Style
 */
package org.bitcoinj.crypto;

import com.google.common.base.Joiner;
import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.cert.CertificateParsingException;
import java.security.cert.X509Certificate;
import java.util.Collection;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.security.auth.x500.X500Principal;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1String;
import org.spongycastle.asn1.x500.AttributeTypeAndValue;
import org.spongycastle.asn1.x500.RDN;
import org.spongycastle.asn1.x500.X500Name;
import org.spongycastle.asn1.x500.style.RFC4519Style;

public class X509Utils {
    @Nullable
    public static String getDisplayNameFromCertificate(@Nonnull X509Certificate certificate, boolean withLocation) throws CertificateParsingException {
        X500Name name = new X500Name(certificate.getSubjectX500Principal().getName());
        String commonName = null;
        String org = null;
        String location = null;
        String country = null;
        for (RDN rdn : name.getRDNs()) {
            AttributeTypeAndValue pair = rdn.getFirst();
            String val = ((ASN1String)pair.getValue()).getString();
            ASN1ObjectIdentifier type = pair.getType();
            if (type.equals((Object)RFC4519Style.cn)) {
                commonName = val;
                continue;
            }
            if (type.equals((Object)RFC4519Style.o)) {
                org = val;
                continue;
            }
            if (type.equals((Object)RFC4519Style.l)) {
                location = val;
                continue;
            }
            if (!type.equals((Object)RFC4519Style.c)) continue;
            country = val;
        }
        Collection<List<?>> subjectAlternativeNames = certificate.getSubjectAlternativeNames();
        String altName = null;
        if (subjectAlternativeNames != null) {
            for (List<?> subjectAlternativeName : subjectAlternativeNames) {
                if ((Integer)subjectAlternativeName.get(0) != 1) continue;
                altName = (String)subjectAlternativeName.get(1);
            }
        }
        if (org != null) {
            return withLocation ? Joiner.on((String)", ").skipNulls().join(org, location, new Object[]{country}) : org;
        }
        if (commonName != null) {
            return commonName;
        }
        return altName;
    }

    public static KeyStore loadKeyStore(String keystoreType, @Nullable String keystorePassword, InputStream is) throws KeyStoreException {
        try {
            KeyStore keystore = KeyStore.getInstance(keystoreType);
            keystore.load(is, keystorePassword != null ? keystorePassword.toCharArray() : null);
            KeyStore keyStore = keystore;
            return keyStore;
        }
        catch (IOException x) {
            throw new KeyStoreException(x);
        }
        catch (GeneralSecurityException x) {
            throw new KeyStoreException(x);
        }
        finally {
            try {
                is.close();
            }
            catch (IOException iOException) {}
        }
    }
}

