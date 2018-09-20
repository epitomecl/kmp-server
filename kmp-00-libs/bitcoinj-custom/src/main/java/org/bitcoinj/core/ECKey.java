/*
 * Decompiled with CFR 0_132.
 * 
 * Could not load the following classes:
 *  com.google.common.annotations.VisibleForTesting
 *  com.google.common.base.MoreObjects
 *  com.google.common.base.MoreObjects$ToStringHelper
 *  com.google.common.base.Objects
 *  com.google.common.base.Preconditions
 *  com.google.common.io.BaseEncoding
 *  com.google.common.primitives.UnsignedBytes
 *  javax.annotation.Nullable
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.spongycastle.asn1.ASN1Encodable
 *  org.spongycastle.asn1.ASN1InputStream
 *  org.spongycastle.asn1.ASN1Integer
 *  org.spongycastle.asn1.ASN1OctetString
 *  org.spongycastle.asn1.ASN1Primitive
 *  org.spongycastle.asn1.ASN1TaggedObject
 *  org.spongycastle.asn1.DERBitString
 *  org.spongycastle.asn1.DEROctetString
 *  org.spongycastle.asn1.DERSequenceGenerator
 *  org.spongycastle.asn1.DERTaggedObject
 *  org.spongycastle.asn1.DLSequence
 *  org.spongycastle.asn1.x9.X9ECParameters
 *  org.spongycastle.asn1.x9.X9IntegerConverter
 *  org.spongycastle.crypto.AsymmetricCipherKeyPair
 *  org.spongycastle.crypto.CipherParameters
 *  org.spongycastle.crypto.Digest
 *  org.spongycastle.crypto.KeyGenerationParameters
 *  org.spongycastle.crypto.digests.SHA256Digest
 *  org.spongycastle.crypto.ec.CustomNamedCurves
 *  org.spongycastle.crypto.generators.ECKeyPairGenerator
 *  org.spongycastle.crypto.params.AsymmetricKeyParameter
 *  org.spongycastle.crypto.params.ECDomainParameters
 *  org.spongycastle.crypto.params.ECKeyGenerationParameters
 *  org.spongycastle.crypto.params.ECPrivateKeyParameters
 *  org.spongycastle.crypto.params.ECPublicKeyParameters
 *  org.spongycastle.crypto.params.KeyParameter
 *  org.spongycastle.crypto.signers.DSAKCalculator
 *  org.spongycastle.crypto.signers.ECDSASigner
 *  org.spongycastle.crypto.signers.HMacDSAKCalculator
 *  org.spongycastle.math.ec.ECAlgorithms
 *  org.spongycastle.math.ec.ECCurve
 *  org.spongycastle.math.ec.ECFieldElement
 *  org.spongycastle.math.ec.ECPoint
 *  org.spongycastle.math.ec.FixedPointCombMultiplier
 *  org.spongycastle.math.ec.FixedPointUtil
 *  org.spongycastle.math.ec.custom.sec.SecP256K1Curve
 *  org.spongycastle.util.encoders.Base64
 */
package org.bitcoinj.core;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.io.BaseEncoding;
import com.google.common.primitives.UnsignedBytes;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.security.SecureRandom;
import java.security.SignatureException;
import java.util.Arrays;
import java.util.Comparator;
import javax.annotation.Nullable;
import org.bitcoin.NativeSecp256k1;
import org.bitcoin.NativeSecp256k1Util;
import org.bitcoin.Secp256k1Context;
import org.bitcoinj.core.Address;
import org.bitcoinj.core.DumpedPrivateKey;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.core.Sha256Hash;
import org.bitcoinj.core.Utils;
import org.bitcoinj.crypto.LazyECPoint;
import org.bitcoinj.crypto.LinuxSecureRandom;
import org.bitcoinj.crypto.TransactionSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1InputStream;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.DERBitString;
import org.spongycastle.asn1.DEROctetString;
import org.spongycastle.asn1.DERSequenceGenerator;
import org.spongycastle.asn1.DERTaggedObject;
import org.spongycastle.asn1.DLSequence;
import org.spongycastle.asn1.x9.X9ECParameters;
import org.spongycastle.asn1.x9.X9IntegerConverter;
import org.spongycastle.crypto.AsymmetricCipherKeyPair;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.Digest;
import org.spongycastle.crypto.KeyGenerationParameters;
import org.spongycastle.crypto.digests.SHA256Digest;
import org.spongycastle.crypto.ec.CustomNamedCurves;
import org.spongycastle.crypto.generators.ECKeyPairGenerator;
import org.spongycastle.crypto.params.AsymmetricKeyParameter;
import org.spongycastle.crypto.params.ECDomainParameters;
import org.spongycastle.crypto.params.ECKeyGenerationParameters;
import org.spongycastle.crypto.params.ECPrivateKeyParameters;
import org.spongycastle.crypto.params.ECPublicKeyParameters;
import org.spongycastle.crypto.params.KeyParameter;
import org.spongycastle.crypto.signers.DSAKCalculator;
import org.spongycastle.crypto.signers.ECDSASigner;
import org.spongycastle.crypto.signers.HMacDSAKCalculator;
import org.spongycastle.math.ec.ECAlgorithms;
import org.spongycastle.math.ec.ECCurve;
import org.spongycastle.math.ec.ECFieldElement;
import org.spongycastle.math.ec.ECPoint;
import org.spongycastle.math.ec.FixedPointCombMultiplier;
import org.spongycastle.math.ec.FixedPointUtil;
import org.spongycastle.math.ec.custom.sec.SecP256K1Curve;
import org.spongycastle.util.encoders.Base64;

public class ECKey {
    private static final Logger log = LoggerFactory.getLogger(ECKey.class);
    public static final Comparator<ECKey> AGE_COMPARATOR = new Comparator<ECKey>(){

        @Override
        public int compare(ECKey k1, ECKey k2) {
            if (k1.creationTimeSeconds == k2.creationTimeSeconds) {
                return 0;
            }
            return k1.creationTimeSeconds > k2.creationTimeSeconds ? 1 : -1;
        }
    };
    public static final Comparator<ECKey> PUBKEY_COMPARATOR = new Comparator<ECKey>(){
        private Comparator<byte[]> comparator = UnsignedBytes.lexicographicalComparator();

        @Override
        public int compare(ECKey k1, ECKey k2) {
            return this.comparator.compare(k1.getPubKey(), k2.getPubKey());
        }
    };
    private static final X9ECParameters CURVE_PARAMS = CustomNamedCurves.getByName((String)"secp256k1");
    public static final ECDomainParameters CURVE;
    public static final BigInteger HALF_CURVE_ORDER;
    private static final SecureRandom secureRandom;
    protected final BigInteger priv;
    protected final LazyECPoint pub;
    protected long creationTimeSeconds;
    private byte[] pubKeyHash;
    @VisibleForTesting
    public static boolean FAKE_SIGNATURES;

    public ECKey() {
        this(secureRandom);
    }

    public ECKey(SecureRandom secureRandom) {
        ECKeyPairGenerator generator = new ECKeyPairGenerator();
        ECKeyGenerationParameters keygenParams = new ECKeyGenerationParameters(CURVE, secureRandom);
        generator.init((KeyGenerationParameters)keygenParams);
        AsymmetricCipherKeyPair keypair = generator.generateKeyPair();
        ECPrivateKeyParameters privParams = (ECPrivateKeyParameters)keypair.getPrivate();
        ECPublicKeyParameters pubParams = (ECPublicKeyParameters)keypair.getPublic();
        this.priv = privParams.getD();
        this.pub = new LazyECPoint(CURVE.getCurve(), pubParams.getQ().getEncoded(true));
        this.creationTimeSeconds = Utils.currentTimeSeconds();
    }

    protected ECKey(@Nullable BigInteger priv, ECPoint pub) {
        this(priv, new LazyECPoint((ECPoint)Preconditions.checkNotNull((Object)pub)));
    }

    protected ECKey(@Nullable BigInteger priv, LazyECPoint pub) {
        if (priv != null) {
            Preconditions.checkArgument((boolean)(priv.bitLength() <= 256), (String)"private key exceeds 32 bytes: %s bits", (int)priv.bitLength());
            Preconditions.checkArgument((boolean)(!priv.equals(BigInteger.ZERO)));
            Preconditions.checkArgument((boolean)(!priv.equals(BigInteger.ONE)));
        }
        this.priv = priv;
        this.pub = (LazyECPoint)Preconditions.checkNotNull((Object)pub);
    }

    public static ECPoint compressPoint(ECPoint point) {
        return ECKey.getPointWithCompression(point, true);
    }

    public static LazyECPoint compressPoint(LazyECPoint point) {
        return point.isCompressed() ? point : new LazyECPoint(ECKey.compressPoint(point.get()));
    }

    public static ECPoint decompressPoint(ECPoint point) {
        return ECKey.getPointWithCompression(point, false);
    }

    public static LazyECPoint decompressPoint(LazyECPoint point) {
        return !point.isCompressed() ? point : new LazyECPoint(ECKey.decompressPoint(point.get()));
    }

    private static ECPoint getPointWithCompression(ECPoint point, boolean compressed) {
        if (point.isCompressed() == compressed) {
            return point;
        }
        point = point.normalize();
        BigInteger x = point.getAffineXCoord().toBigInteger();
        BigInteger y = point.getAffineYCoord().toBigInteger();
        return CURVE.getCurve().createPoint(x, y, compressed);
    }

    public static ECKey fromASN1(byte[] asn1privkey) {
        return ECKey.extractKeyFromASN1(asn1privkey);
    }

    public static ECKey fromPrivate(BigInteger privKey) {
        return ECKey.fromPrivate(privKey, true);
    }

    public static ECKey fromPrivate(BigInteger privKey, boolean compressed) {
        ECPoint point = ECKey.publicPointFromPrivate(privKey);
        return new ECKey(privKey, ECKey.getPointWithCompression(point, compressed));
    }

    public static ECKey fromPrivate(byte[] privKeyBytes) {
        return ECKey.fromPrivate(new BigInteger(1, privKeyBytes));
    }

    public static ECKey fromPrivate(byte[] privKeyBytes, boolean compressed) {
        return ECKey.fromPrivate(new BigInteger(1, privKeyBytes), compressed);
    }

    public static ECKey fromPrivateAndPrecalculatedPublic(BigInteger priv, ECPoint pub) {
        return new ECKey(priv, pub);
    }

    public static ECKey fromPrivateAndPrecalculatedPublic(byte[] priv, byte[] pub) {
        Preconditions.checkNotNull((Object)priv);
        Preconditions.checkNotNull((Object)pub);
        return new ECKey(new BigInteger(1, priv), CURVE.getCurve().decodePoint(pub));
    }

    public static ECKey fromPublicOnly(ECPoint pub) {
        return new ECKey(null, pub);
    }

    public static ECKey fromPublicOnly(byte[] pub) {
        return new ECKey(null, CURVE.getCurve().decodePoint(pub));
    }

    public ECKey decompress() {
        if (!this.pub.isCompressed()) {
            return this;
        }
        return new ECKey(this.priv, ECKey.decompressPoint(this.pub.get()));
    }

    @Deprecated
    public ECKey(@Nullable byte[] privKeyBytes, @Nullable byte[] pubKey) {
        this(privKeyBytes == null ? null : new BigInteger(1, privKeyBytes), pubKey);
    }

    @Deprecated
    public ECKey(@Nullable BigInteger privKey, @Nullable byte[] pubKey, boolean compressed) {
        if (privKey == null && pubKey == null) {
            throw new IllegalArgumentException("ECKey requires at least private or public key");
        }
        this.priv = privKey;
        if (pubKey == null) {
            ECPoint point = ECKey.publicPointFromPrivate(privKey);
            point = ECKey.getPointWithCompression(point, compressed);
            this.pub = new LazyECPoint(point);
        } else {
            this.pub = new LazyECPoint(CURVE.getCurve(), pubKey);
        }
    }

    @Deprecated
    private ECKey(@Nullable BigInteger privKey, @Nullable byte[] pubKey) {
        this(privKey, pubKey, false);
    }

    public boolean isPubKeyOnly() {
        return this.priv == null;
    }

    public boolean hasPrivKey() {
        return this.priv != null;
    }

    public boolean isWatching() {
        return this.isPubKeyOnly();
    }

    public byte[] toASN1() {
        try {
            byte[] privKeyBytes = this.getPrivKeyBytes();
            ByteArrayOutputStream baos = new ByteArrayOutputStream(400);
            DERSequenceGenerator seq = new DERSequenceGenerator((OutputStream)baos);
            seq.addObject((ASN1Encodable)new ASN1Integer(1L));
            seq.addObject((ASN1Encodable)new DEROctetString(privKeyBytes));
            seq.addObject((ASN1Encodable)new DERTaggedObject(0, (ASN1Encodable)CURVE_PARAMS.toASN1Primitive()));
            seq.addObject((ASN1Encodable)new DERTaggedObject(1, (ASN1Encodable)new DERBitString(this.getPubKey())));
            seq.close();
            return baos.toByteArray();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static byte[] publicKeyFromPrivate(BigInteger privKey, boolean compressed) {
        ECPoint point = ECKey.publicPointFromPrivate(privKey);
        return point.getEncoded(compressed);
    }

    public static ECPoint publicPointFromPrivate(BigInteger privKey) {
        if (privKey.bitLength() > CURVE.getN().bitLength()) {
            privKey = privKey.mod(CURVE.getN());
        }
        return new FixedPointCombMultiplier().multiply(CURVE.getG(), privKey);
    }

    public byte[] getPubKeyHash() {
        if (this.pubKeyHash == null) {
            this.pubKeyHash = Utils.sha256hash160(this.pub.getEncoded());
        }
        return this.pubKeyHash;
    }

    public byte[] getPubKey() {
        return this.pub.getEncoded();
    }

    public ECPoint getPubKeyPoint() {
        return this.pub.get();
    }

    public BigInteger getPrivKey() {
        if (this.priv == null) {
            throw new MissingPrivateKeyException();
        }
        return this.priv;
    }

    public boolean isCompressed() {
        return this.pub.isCompressed();
    }

    public Address toAddress(NetworkParameters params) {
        return new Address(params, this.getPubKeyHash());
    }

    public ECDSASignature sign(Sha256Hash input) {
        if (this.priv == null) {
            throw new MissingPrivateKeyException();
        }
        return this.doSign(input, this.priv);
    }

    protected ECDSASignature doSign(Sha256Hash input, BigInteger privateKeyForSigning) {
        if (Secp256k1Context.isEnabled()) {
            try {
                byte[] signature = NativeSecp256k1.sign(input.getBytes(), Utils.bigIntegerToBytes(privateKeyForSigning, 32));
                return ECDSASignature.decodeFromDER(signature);
            }
            catch (NativeSecp256k1Util.AssertFailException e) {
                log.error("Caught AssertFailException inside secp256k1", (Throwable)e);
                throw new RuntimeException(e);
            }
        }
        if (FAKE_SIGNATURES) {
            return TransactionSignature.dummy();
        }
        Preconditions.checkNotNull((Object)privateKeyForSigning);
        ECDSASigner signer = new ECDSASigner((DSAKCalculator)new HMacDSAKCalculator((Digest)new SHA256Digest()));
        ECPrivateKeyParameters privKey = new ECPrivateKeyParameters(privateKeyForSigning, CURVE);
        signer.init(true, (CipherParameters)privKey);
        BigInteger[] components = signer.generateSignature(input.getBytes());
        return new ECDSASignature(components[0], components[1]).toCanonicalised();
    }

    public static boolean verify(byte[] data, ECDSASignature signature, byte[] pub) {
        if (FAKE_SIGNATURES) {
            return true;
        }
        if (Secp256k1Context.isEnabled()) {
            try {
                return NativeSecp256k1.verify(data, signature.encodeToDER(), pub);
            }
            catch (NativeSecp256k1Util.AssertFailException e) {
                log.error("Caught AssertFailException inside secp256k1", (Throwable)e);
                return false;
            }
        }
        ECDSASigner signer = new ECDSASigner();
        ECPublicKeyParameters params = new ECPublicKeyParameters(CURVE.getCurve().decodePoint(pub), CURVE);
        signer.init(false, (CipherParameters)params);
        try {
            return signer.verifySignature(data, signature.r, signature.s);
        }
        catch (NullPointerException e) {
            log.error("Caught NPE inside bouncy castle", (Throwable)e);
            return false;
        }
    }

    public static boolean verify(byte[] data, byte[] signature, byte[] pub) {
        if (Secp256k1Context.isEnabled()) {
            try {
                return NativeSecp256k1.verify(data, signature, pub);
            }
            catch (NativeSecp256k1Util.AssertFailException e) {
                log.error("Caught AssertFailException inside secp256k1", (Throwable)e);
                return false;
            }
        }
        return ECKey.verify(data, ECDSASignature.decodeFromDER(signature), pub);
    }

    public boolean verify(byte[] hash, byte[] signature) {
        return ECKey.verify(hash, signature, this.getPubKey());
    }

    public boolean verify(Sha256Hash sigHash, ECDSASignature signature) {
        return ECKey.verify(sigHash.getBytes(), signature, this.getPubKey());
    }

    public void verifyOrThrow(byte[] hash, byte[] signature) throws SignatureException {
        if (!this.verify(hash, signature)) {
            throw new SignatureException();
        }
    }

    public void verifyOrThrow(Sha256Hash sigHash, ECDSASignature signature) throws SignatureException {
        if (!ECKey.verify(sigHash.getBytes(), signature, this.getPubKey())) {
            throw new SignatureException();
        }
    }

    public static boolean isPubKeyCanonical(byte[] pubkey) {
        if (pubkey.length < 33) {
            return false;
        }
        if (pubkey[0] == 4) {
            if (pubkey.length != 65) {
                return false;
            }
        } else if (pubkey[0] == 2 || pubkey[0] == 3) {
            if (pubkey.length != 33) {
                return false;
            }
        } else {
            return false;
        }
        return true;
    }

    private static ECKey extractKeyFromASN1(byte[] asn1privkey) {
        try {
            ASN1InputStream decoder = new ASN1InputStream(asn1privkey);
            DLSequence seq = (DLSequence)decoder.readObject();
            Preconditions.checkArgument((boolean)(decoder.readObject() == null), (Object)"Input contains extra bytes");
            decoder.close();
            Preconditions.checkArgument((boolean)(seq.size() == 4), (Object)"Input does not appear to be an ASN.1 OpenSSL EC private key");
            Preconditions.checkArgument((boolean)((ASN1Integer)seq.getObjectAt(0)).getValue().equals(BigInteger.ONE), (Object)"Input is of wrong version");
            byte[] privbits = ((ASN1OctetString)seq.getObjectAt(1)).getOctets();
            BigInteger privkey = new BigInteger(1, privbits);
            ASN1TaggedObject pubkey = (ASN1TaggedObject)seq.getObjectAt(3);
            Preconditions.checkArgument((boolean)(pubkey.getTagNo() == 1), (Object)"Input has 'publicKey' with bad tag number");
            byte[] pubbits = ((DERBitString)pubkey.getObject()).getBytes();
            Preconditions.checkArgument((boolean)(pubbits.length == 33 || pubbits.length == 65), (Object)"Input has 'publicKey' with invalid length");
            int encoding = pubbits[0] & 255;
            Preconditions.checkArgument((boolean)(encoding >= 2 && encoding <= 4), (Object)"Input has 'publicKey' with invalid encoding");
            boolean compressed = pubbits.length == 33;
            ECKey key = new ECKey(privkey, null, compressed);
            if (!Arrays.equals(key.getPubKey(), pubbits)) {
                throw new IllegalArgumentException("Public key in ASN.1 structure does not match private key.");
            }
            return key;
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String signMessage(String message) {
        byte[] data = Utils.formatMessageForSigning(message);
        Sha256Hash hash = Sha256Hash.twiceOf(data);
        ECDSASignature sig = this.sign(hash);
        int recId = -1;
        for (int i = 0; i < 4; ++i) {
            ECKey k = ECKey.recoverFromSignature(i, sig, hash, this.isCompressed());
            if (k == null || !k.pub.equals(this.pub)) continue;
            recId = i;
            break;
        }
        if (recId == -1) {
            throw new RuntimeException("Could not construct a recoverable key. This should never happen.");
        }
        int headerByte = recId + 27 + (this.isCompressed() ? 4 : 0);
        byte[] sigData = new byte[65];
        sigData[0] = (byte)headerByte;
        System.arraycopy(Utils.bigIntegerToBytes(sig.r, 32), 0, sigData, 1, 32);
        System.arraycopy(Utils.bigIntegerToBytes(sig.s, 32), 0, sigData, 33, 32);
        return new String(Base64.encode((byte[])sigData), Charset.forName("UTF-8"));
    }

    public static ECKey signedMessageToKey(String message, String signatureBase64) throws SignatureException {
        ECKey key;
        byte[] signatureEncoded;
        int recId;
        try {
            signatureEncoded = Base64.decode((String)signatureBase64);
        }
        catch (RuntimeException e) {
            throw new SignatureException("Could not decode base64", e);
        }
        if (signatureEncoded.length < 65) {
            throw new SignatureException("Signature truncated, expected 65 bytes and got " + signatureEncoded.length);
        }
        int header = signatureEncoded[0] & 255;
        if (header < 27 || header > 34) {
            throw new SignatureException("Header byte out of range: " + header);
        }
        BigInteger r = new BigInteger(1, Arrays.copyOfRange(signatureEncoded, 1, 33));
        BigInteger s = new BigInteger(1, Arrays.copyOfRange(signatureEncoded, 33, 65));
        ECDSASignature sig = new ECDSASignature(r, s);
        byte[] messageBytes = Utils.formatMessageForSigning(message);
        Sha256Hash messageHash = Sha256Hash.twiceOf(messageBytes);
        boolean compressed = false;
        if (header >= 31) {
            compressed = true;
            header -= 4;
        }
        if ((key = ECKey.recoverFromSignature(recId = header - 27, sig, messageHash, compressed)) == null) {
            throw new SignatureException("Could not recover public key from signature");
        }
        return key;
    }

    public void verifyMessage(String message, String signatureBase64) throws SignatureException {
        ECKey key = ECKey.signedMessageToKey(message, signatureBase64);
        if (!key.pub.equals(this.pub)) {
            throw new SignatureException("Signature did not match for message");
        }
    }

    @Nullable
    public static ECKey recoverFromSignature(int recId, ECDSASignature sig, Sha256Hash message, boolean compressed) {
        Preconditions.checkArgument((boolean)(recId >= 0), (Object)"recId must be positive");
        Preconditions.checkArgument((boolean)(sig.r.signum() >= 0), (Object)"r must be positive");
        Preconditions.checkArgument((boolean)(sig.s.signum() >= 0), (Object)"s must be positive");
        Preconditions.checkNotNull((Object)message);
        BigInteger n = CURVE.getN();
        BigInteger i = BigInteger.valueOf((long)recId / 2L);
        BigInteger x = sig.r.add(i.multiply(n));
        BigInteger prime = SecP256K1Curve.q;
        if (x.compareTo(prime) >= 0) {
            return null;
        }
        ECPoint R = ECKey.decompressKey(x, (recId & 1) == 1);
        if (!R.multiply(n).isInfinity()) {
            return null;
        }
        BigInteger e = message.toBigInteger();
        BigInteger eInv = BigInteger.ZERO.subtract(e).mod(n);
        BigInteger rInv = sig.r.modInverse(n);
        BigInteger srInv = rInv.multiply(sig.s).mod(n);
        BigInteger eInvrInv = rInv.multiply(eInv).mod(n);
        ECPoint q = ECAlgorithms.sumOfTwoMultiplies((ECPoint)CURVE.getG(), (BigInteger)eInvrInv, (ECPoint)R, (BigInteger)srInv);
        return ECKey.fromPublicOnly(q.getEncoded(compressed));
    }

    private static ECPoint decompressKey(BigInteger xBN, boolean yBit) {
        X9IntegerConverter x9 = new X9IntegerConverter();
        byte[] compEnc = x9.integerToBytes(xBN, 1 + x9.getByteLength(CURVE.getCurve()));
        compEnc[0] = (byte)(yBit ? 3 : 2);
        return CURVE.getCurve().decodePoint(compEnc);
    }

    public byte[] getPrivKeyBytes() {
        return Utils.bigIntegerToBytes(this.getPrivKey(), 32);
    }

    public DumpedPrivateKey getPrivateKeyEncoded(NetworkParameters params) {
        return new DumpedPrivateKey(params, this.getPrivKeyBytes(), this.isCompressed());
    }

    public long getCreationTimeSeconds() {
        return this.creationTimeSeconds;
    }

    public void setCreationTimeSeconds(long newCreationTimeSeconds) {
        if (newCreationTimeSeconds < 0L) {
            throw new IllegalArgumentException("Cannot set creation time to negative value: " + newCreationTimeSeconds);
        }
        this.creationTimeSeconds = newCreationTimeSeconds;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || !(o instanceof ECKey)) {
            return false;
        }
        ECKey other = (ECKey)o;
        return Objects.equal((Object)this.priv, (Object)other.priv) && Objects.equal((Object)this.pub, (Object)other.pub) && Objects.equal((Object)this.creationTimeSeconds, (Object)other.creationTimeSeconds);
    }

    public int hashCode() {
        return this.pub.hashCode();
    }

    public String toString() {
        return this.toString(false, null, null);
    }

    public String getPrivateKeyAsHex() {
        return Utils.HEX.encode(this.getPrivKeyBytes());
    }

    public String getPublicKeyAsHex() {
        return Utils.HEX.encode(this.pub.getEncoded());
    }

    public String getPrivateKeyAsWiF(NetworkParameters params) {
        return this.getPrivateKeyEncoded(params).toString();
    }

    private String toString(boolean includePrivate, @Nullable KeyParameter aesKey, NetworkParameters params) {
        MoreObjects.ToStringHelper helper = MoreObjects.toStringHelper((Object)this).omitNullValues();
        helper.add("pub HEX", (Object)this.getPublicKeyAsHex());
        if (includePrivate) {
            ECKey decryptedKey = this;
            try {
                helper.add("priv HEX", (Object)decryptedKey.getPrivateKeyAsHex());
                helper.add("priv WIF", (Object)decryptedKey.getPrivateKeyAsWiF(params));
            }
            catch (IllegalStateException illegalStateException) {
            }
            catch (Exception e) {
                String message = e.getMessage();
                helper.add("priv EXCEPTION", (Object)(e.getClass().getName() + (message != null ? new StringBuilder().append(": ").append(message).toString() : "")));
            }
        }
        if (this.creationTimeSeconds > 0L) {
            helper.add("creationTimeSeconds", this.creationTimeSeconds);
        }
        helper.add("isPubKeyOnly", this.isPubKeyOnly());
        return helper.toString();
    }

    public void formatKeyWithAddress(StringBuilder builder, NetworkParameters params) {
        Address address = this.toAddress(params);
        builder.append("  addr:");
        builder.append(address.toString());
        builder.append("  hash160:");
        builder.append(Utils.HEX.encode(this.getPubKeyHash()));
        if (this.creationTimeSeconds > 0L) {
            builder.append("  creationTimeSeconds:").append(this.creationTimeSeconds);
        }
        builder.append("\n");
    }

    static {
        if (Utils.isAndroidRuntime()) {
            new LinuxSecureRandom();
        }
        FixedPointUtil.precompute((ECPoint)CURVE_PARAMS.getG(), (int)12);
        CURVE = new ECDomainParameters(CURVE_PARAMS.getCurve(), CURVE_PARAMS.getG(), CURVE_PARAMS.getN(), CURVE_PARAMS.getH());
        HALF_CURVE_ORDER = CURVE_PARAMS.getN().shiftRight(1);
        secureRandom = new SecureRandom();
        FAKE_SIGNATURES = false;
    }

    public static class KeyIsEncryptedException
    extends MissingPrivateKeyException {
    }

    public static class MissingPrivateKeyException
    extends RuntimeException {
    }

    public static class ECDSASignature {
        public final BigInteger r;
        public final BigInteger s;

        public ECDSASignature(BigInteger r, BigInteger s) {
            this.r = r;
            this.s = s;
        }

        public boolean isCanonical() {
            return this.s.compareTo(ECKey.HALF_CURVE_ORDER) <= 0;
        }

        public ECDSASignature toCanonicalised() {
            if (!this.isCanonical()) {
                return new ECDSASignature(this.r, ECKey.CURVE.getN().subtract(this.s));
            }
            return this;
        }

        public byte[] encodeToDER() {
            try {
                return this.derByteStream().toByteArray();
            }
            catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        public static ECDSASignature decodeFromDER(byte[] bytes) throws IllegalArgumentException {
            ASN1InputStream decoder = null;
            try {
                ASN1Integer s;
                ASN1Integer r;
                decoder = new ASN1InputStream(bytes);
                ASN1Primitive seqObj = decoder.readObject();
                if (seqObj == null) {
                    throw new IllegalArgumentException("Reached past end of ASN.1 stream.");
                }
                if (!(seqObj instanceof DLSequence)) {
                    throw new IllegalArgumentException("Read unexpected class: " + seqObj.getClass().getName());
                }
                DLSequence seq = (DLSequence)seqObj;
                try {
                    r = (ASN1Integer)seq.getObjectAt(0);
                    s = (ASN1Integer)seq.getObjectAt(1);
                }
                catch (ClassCastException e2) {
                    throw new IllegalArgumentException(e2);
                }
                ECDSASignature e2 = new ECDSASignature(r.getPositiveValue(), s.getPositiveValue());
                return e2;
            }
            catch (IOException e) {
                throw new IllegalArgumentException(e);
            }
            finally {
                if (decoder != null) {
                    try {
                        decoder.close();
                    }
                    catch (IOException iOException) {}
                }
            }
        }

        protected ByteArrayOutputStream derByteStream() throws IOException {
            ByteArrayOutputStream bos = new ByteArrayOutputStream(72);
            DERSequenceGenerator seq = new DERSequenceGenerator((OutputStream)bos);
            seq.addObject((ASN1Encodable)new ASN1Integer(this.r));
            seq.addObject((ASN1Encodable)new ASN1Integer(this.s));
            seq.close();
            return bos;
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || this.getClass() != o.getClass()) {
                return false;
            }
            ECDSASignature other = (ECDSASignature)o;
            return this.r.equals(other.r) && this.s.equals(other.s);
        }

        public int hashCode() {
            return Objects.hashCode((Object[])new Object[]{this.r, this.s});
        }
    }

}

