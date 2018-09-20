/*
 * Decompiled with CFR 0_132.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Preconditions
 *  com.google.common.collect.ImmutableList
 *  org.spongycastle.crypto.params.ECDomainParameters
 *  org.spongycastle.math.ec.ECCurve
 *  org.spongycastle.math.ec.ECPoint
 */
package org.bitcoinj.crypto;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Random;
import org.bitcoinj.core.ECKey;
import org.bitcoinj.core.Utils;
import org.bitcoinj.crypto.ChildNumber;
import org.bitcoinj.crypto.DeterministicKey;
import org.bitcoinj.crypto.HDDerivationException;
import org.bitcoinj.crypto.HDUtils;
import org.bitcoinj.crypto.LazyECPoint;
import org.bitcoinj.crypto.LinuxSecureRandom;
import org.spongycastle.crypto.params.ECDomainParameters;
import org.spongycastle.math.ec.ECCurve;
import org.spongycastle.math.ec.ECPoint;

public final class HDKeyDerivation {
    private static final BigInteger RAND_INT;
    public static final int MAX_CHILD_DERIVATION_ATTEMPTS = 100;

    private HDKeyDerivation() {
    }

    public static DeterministicKey createMasterPrivateKey(byte[] seed) throws HDDerivationException {
        Preconditions.checkArgument((boolean)(seed.length > 8), (Object)"Seed is too short and could be brute forced");
        byte[] i = HDUtils.hmacSha512(HDUtils.createHmacSha512Digest("Bitcoin seed".getBytes()), seed);
        Preconditions.checkState((boolean)(i.length == 64), (Object)i.length);
        byte[] il = Arrays.copyOfRange(i, 0, 32);
        byte[] ir = Arrays.copyOfRange(i, 32, 64);
        Arrays.fill(i, (byte)0);
        DeterministicKey masterPrivKey = HDKeyDerivation.createMasterPrivKeyFromBytes(il, ir);
        Arrays.fill(il, (byte)0);
        Arrays.fill(ir, (byte)0);
        masterPrivKey.setCreationTimeSeconds(Utils.currentTimeSeconds());
        return masterPrivKey;
    }

    public static DeterministicKey createMasterPrivKeyFromBytes(byte[] privKeyBytes, byte[] chainCode) throws HDDerivationException {
        return HDKeyDerivation.createMasterPrivKeyFromBytes(privKeyBytes, chainCode, (ImmutableList<ChildNumber>)ImmutableList.of());
    }

    public static DeterministicKey createMasterPrivKeyFromBytes(byte[] privKeyBytes, byte[] chainCode, ImmutableList<ChildNumber> childNumberPath) throws HDDerivationException {
        BigInteger priv = new BigInteger(1, privKeyBytes);
        HDKeyDerivation.assertNonZero(priv, "Generated master key is invalid.");
        HDKeyDerivation.assertLessThanN(priv, "Generated master key is invalid.");
        return new DeterministicKey(childNumberPath, chainCode, priv, null);
    }

    public static DeterministicKey createMasterPubKeyFromBytes(byte[] pubKeyBytes, byte[] chainCode) {
        return new DeterministicKey((ImmutableList<ChildNumber>)ImmutableList.of(), chainCode, new LazyECPoint(ECKey.CURVE.getCurve(), pubKeyBytes), null, null);
    }

    public static DeterministicKey deriveChildKey(DeterministicKey parent, int childNumber) {
        return HDKeyDerivation.deriveChildKey(parent, new ChildNumber(childNumber));
    }

    public static DeterministicKey deriveThisOrNextChildKey(DeterministicKey parent, int childNumber) {
        ChildNumber child = new ChildNumber(childNumber);
        boolean isHardened = child.isHardened();
        for (int nAttempts = 0; nAttempts < 100; ++nAttempts) {
            try {
                child = new ChildNumber(child.num() + nAttempts, isHardened);
                return HDKeyDerivation.deriveChildKey(parent, child);
            }
            catch (HDDerivationException hDDerivationException) {
                continue;
            }
        }
        throw new HDDerivationException("Maximum number of child derivation attempts reached, this is probably an indication of a bug.");
    }

    public static DeterministicKey deriveChildKey(DeterministicKey parent, ChildNumber childNumber) throws HDDerivationException {
        if (!parent.hasPrivKey()) {
            RawKeyBytes rawKey = HDKeyDerivation.deriveChildKeyBytesFromPublic(parent, childNumber, PublicDeriveMode.NORMAL);
            return new DeterministicKey(HDUtils.append(parent.getPath(), childNumber), rawKey.chainCode, new LazyECPoint(ECKey.CURVE.getCurve(), rawKey.keyBytes), null, parent);
        }
        RawKeyBytes rawKey = HDKeyDerivation.deriveChildKeyBytesFromPrivate(parent, childNumber);
        return new DeterministicKey(HDUtils.append(parent.getPath(), childNumber), rawKey.chainCode, new BigInteger(1, rawKey.keyBytes), parent);
    }

    public static RawKeyBytes deriveChildKeyBytesFromPrivate(DeterministicKey parent, ChildNumber childNumber) throws HDDerivationException {
        Preconditions.checkArgument((boolean)parent.hasPrivKey(), (Object)"Parent key must have private key bytes for this method.");
        byte[] parentPublicKey = parent.getPubKeyPoint().getEncoded(true);
        Preconditions.checkState((boolean)(parentPublicKey.length == 33), (Object)("Parent pubkey must be 33 bytes, but is " + parentPublicKey.length));
        ByteBuffer data = ByteBuffer.allocate(37);
        if (childNumber.isHardened()) {
            data.put(parent.getPrivKeyBytes33());
        } else {
            data.put(parentPublicKey);
        }
        data.putInt(childNumber.i());
        byte[] i = HDUtils.hmacSha512(parent.getChainCode(), data.array());
        Preconditions.checkState((boolean)(i.length == 64), (Object)i.length);
        byte[] il = Arrays.copyOfRange(i, 0, 32);
        byte[] chainCode = Arrays.copyOfRange(i, 32, 64);
        BigInteger ilInt = new BigInteger(1, il);
        HDKeyDerivation.assertLessThanN(ilInt, "Illegal derived key: I_L >= n");
        BigInteger priv = parent.getPrivKey();
        BigInteger ki = priv.add(ilInt).mod(ECKey.CURVE.getN());
        HDKeyDerivation.assertNonZero(ki, "Illegal derived key: derived private key equals 0.");
        return new RawKeyBytes(ki.toByteArray(), chainCode);
    }

    public static RawKeyBytes deriveChildKeyBytesFromPublic(DeterministicKey parent, ChildNumber childNumber, PublicDeriveMode mode) throws HDDerivationException {
        ECPoint Ki;
        Preconditions.checkArgument((boolean)(!childNumber.isHardened()), (Object)"Can't use private derivation with public keys only.");
        byte[] parentPublicKey = parent.getPubKeyPoint().getEncoded(true);
        Preconditions.checkState((boolean)(parentPublicKey.length == 33), (Object)("Parent pubkey must be 33 bytes, but is " + parentPublicKey.length));
        ByteBuffer data = ByteBuffer.allocate(37);
        data.put(parentPublicKey);
        data.putInt(childNumber.i());
        byte[] i = HDUtils.hmacSha512(parent.getChainCode(), data.array());
        Preconditions.checkState((boolean)(i.length == 64), (Object)i.length);
        byte[] il = Arrays.copyOfRange(i, 0, 32);
        byte[] chainCode = Arrays.copyOfRange(i, 32, 64);
        BigInteger ilInt = new BigInteger(1, il);
        HDKeyDerivation.assertLessThanN(ilInt, "Illegal derived key: I_L >= n");
        BigInteger N = ECKey.CURVE.getN();
        switch (mode) {
            case NORMAL: {
                Ki = ECKey.publicPointFromPrivate(ilInt).add(parent.getPubKeyPoint());
                break;
            }
            case WITH_INVERSION: {
                Ki = ECKey.publicPointFromPrivate(ilInt.add(RAND_INT).mod(N));
                BigInteger additiveInverse = RAND_INT.negate().mod(N);
                Ki = Ki.add(ECKey.publicPointFromPrivate(additiveInverse));
                Ki = Ki.add(parent.getPubKeyPoint());
                break;
            }
            default: {
                throw new AssertionError();
            }
        }
        HDKeyDerivation.assertNonInfinity(Ki, "Illegal derived key: derived public key equals infinity.");
        return new RawKeyBytes(Ki.getEncoded(true), chainCode);
    }

    private static void assertNonZero(BigInteger integer, String errorMessage) {
        if (integer.equals(BigInteger.ZERO)) {
            throw new HDDerivationException(errorMessage);
        }
    }

    private static void assertNonInfinity(ECPoint point, String errorMessage) {
        if (point.equals(ECKey.CURVE.getCurve().getInfinity())) {
            throw new HDDerivationException(errorMessage);
        }
    }

    private static void assertLessThanN(BigInteger integer, String errorMessage) {
        if (integer.compareTo(ECKey.CURVE.getN()) > 0) {
            throw new HDDerivationException(errorMessage);
        }
    }

    static {
        if (Utils.isAndroidRuntime()) {
            new LinuxSecureRandom();
        }
        RAND_INT = new BigInteger(256, new SecureRandom());
    }

    public static class RawKeyBytes {
        public final byte[] keyBytes;
        public final byte[] chainCode;

        public RawKeyBytes(byte[] keyBytes, byte[] chainCode) {
            this.keyBytes = keyBytes;
            this.chainCode = chainCode;
        }
    }

    public static enum PublicDeriveMode {
        NORMAL,
        WITH_INVERSION;
        

        private PublicDeriveMode() {
        }
    }

}

