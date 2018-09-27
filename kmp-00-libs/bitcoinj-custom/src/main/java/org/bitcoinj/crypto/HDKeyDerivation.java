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
import org.bitcoinj.core.ECKey;
import org.bitcoinj.core.Utils;
import org.spongycastle.math.ec.ECPoint;

public final class HDKeyDerivation {
    private static final BigInteger RAND_INT;
    public static final int MAX_CHILD_DERIVATION_ATTEMPTS = 100;

    private HDKeyDerivation() {
    }

    public static DeterministicKey createMasterPrivateKey(byte[] seed) throws HDDerivationException {
        Preconditions.checkArgument(seed.length > 8, "Seed is too short and could be brute forced");
        byte[] i = HDUtils.hmacSha512(HDUtils.createHmacSha512Digest("Bitcoin seed".getBytes()), seed);
        Preconditions.checkState(i.length == 64, i.length);
        byte[] il = Arrays.copyOfRange(i, 0, 32);
        byte[] ir = Arrays.copyOfRange(i, 32, 64);
        Arrays.fill(i, (byte)0);
        DeterministicKey masterPrivKey = createMasterPrivKeyFromBytes(il, ir);
        Arrays.fill(il, (byte)0);
        Arrays.fill(ir, (byte)0);
        masterPrivKey.setCreationTimeSeconds(Utils.currentTimeSeconds());
        return masterPrivKey;
    }

    public static DeterministicKey createMasterPrivKeyFromBytes(byte[] privKeyBytes, byte[] chainCode) throws HDDerivationException {
        return createMasterPrivKeyFromBytes(privKeyBytes, chainCode, ImmutableList.of());
    }

    public static DeterministicKey createMasterPrivKeyFromBytes(byte[] privKeyBytes, byte[] chainCode, ImmutableList<ChildNumber> childNumberPath) throws HDDerivationException {
        BigInteger priv = new BigInteger(1, privKeyBytes);
        assertNonZero(priv, "Generated master key is invalid.");
        assertLessThanN(priv, "Generated master key is invalid.");
        return new DeterministicKey(childNumberPath, chainCode, priv, (DeterministicKey)null);
    }

    public static DeterministicKey createMasterPubKeyFromBytes(byte[] pubKeyBytes, byte[] chainCode) {
        return new DeterministicKey(ImmutableList.of(), chainCode, new LazyECPoint(ECKey.CURVE.getCurve(), pubKeyBytes), (BigInteger)null, (DeterministicKey)null);
    }

    public static DeterministicKey deriveChildKey(DeterministicKey parent, int childNumber) {
        return deriveChildKey(parent, new ChildNumber(childNumber));
    }

    public static DeterministicKey deriveThisOrNextChildKey(DeterministicKey parent, int childNumber) {
        int nAttempts = 0;
        ChildNumber child = new ChildNumber(childNumber);
        boolean isHardened = child.isHardened();

        while(nAttempts < 100) {
            try {
                child = new ChildNumber(child.num() + nAttempts, isHardened);
                return deriveChildKey(parent, child);
            } catch (HDDerivationException var6) {
                ++nAttempts;
            }
        }

        throw new HDDerivationException("Maximum number of child derivation attempts reached, this is probably an indication of a bug.");
    }

    public static DeterministicKey deriveChildKey(DeterministicKey parent, ChildNumber childNumber) throws HDDerivationException {
        HDKeyDerivation.RawKeyBytes rawKey;
        if (!parent.hasPrivKey()) {
            rawKey = deriveChildKeyBytesFromPublic(parent, childNumber, HDKeyDerivation.PublicDeriveMode.NORMAL);
            return new DeterministicKey(HDUtils.append(parent.getPath(), childNumber), rawKey.chainCode, new LazyECPoint(ECKey.CURVE.getCurve(), rawKey.keyBytes), (BigInteger)null, parent);
        } else {
            rawKey = deriveChildKeyBytesFromPrivate(parent, childNumber);
            return new DeterministicKey(HDUtils.append(parent.getPath(), childNumber), rawKey.chainCode, new BigInteger(1, rawKey.keyBytes), parent);
        }
    }

    public static HDKeyDerivation.RawKeyBytes deriveChildKeyBytesFromPrivate(DeterministicKey parent, ChildNumber childNumber) throws HDDerivationException {
        Preconditions.checkArgument(parent.hasPrivKey(), "Parent key must have private key bytes for this method.");
        byte[] parentPublicKey = parent.getPubKeyPoint().getEncoded(true);
        Preconditions.checkState(parentPublicKey.length == 33, "Parent pubkey must be 33 bytes, but is " + parentPublicKey.length);
        ByteBuffer data = ByteBuffer.allocate(37);
        if (childNumber.isHardened()) {
            data.put(parent.getPrivKeyBytes33());
        } else {
            data.put(parentPublicKey);
        }

        data.putInt(childNumber.i());
        byte[] i = HDUtils.hmacSha512(parent.getChainCode(), data.array());
        Preconditions.checkState(i.length == 64, i.length);
        byte[] il = Arrays.copyOfRange(i, 0, 32);
        byte[] chainCode = Arrays.copyOfRange(i, 32, 64);
        BigInteger ilInt = new BigInteger(1, il);
        assertLessThanN(ilInt, "Illegal derived key: I_L >= n");
        BigInteger priv = parent.getPrivKey();
        BigInteger ki = priv.add(ilInt).mod(ECKey.CURVE.getN());
        assertNonZero(ki, "Illegal derived key: derived private key equals 0.");
        return new HDKeyDerivation.RawKeyBytes(ki.toByteArray(), chainCode);
    }

    public static HDKeyDerivation.RawKeyBytes deriveChildKeyBytesFromPublic(DeterministicKey parent, ChildNumber childNumber, HDKeyDerivation.PublicDeriveMode mode) throws HDDerivationException {
        Preconditions.checkArgument(!childNumber.isHardened(), "Can't use private derivation with public keys only.");
        byte[] parentPublicKey = parent.getPubKeyPoint().getEncoded(true);
        Preconditions.checkState(parentPublicKey.length == 33, "Parent pubkey must be 33 bytes, but is " + parentPublicKey.length);
        ByteBuffer data = ByteBuffer.allocate(37);
        data.put(parentPublicKey);
        data.putInt(childNumber.i());
        byte[] i = HDUtils.hmacSha512(parent.getChainCode(), data.array());
        Preconditions.checkState(i.length == 64, i.length);
        byte[] il = Arrays.copyOfRange(i, 0, 32);
        byte[] chainCode = Arrays.copyOfRange(i, 32, 64);
        BigInteger ilInt = new BigInteger(1, il);
        assertLessThanN(ilInt, "Illegal derived key: I_L >= n");
        BigInteger N = ECKey.CURVE.getN();
        ECPoint Ki;
        switch(mode) {
            case NORMAL:
                Ki = ECKey.publicPointFromPrivate(ilInt).add(parent.getPubKeyPoint());
                break;
            case WITH_INVERSION:
                Ki = ECKey.publicPointFromPrivate(ilInt.add(RAND_INT).mod(N));
                BigInteger additiveInverse = RAND_INT.negate().mod(N);
                Ki = Ki.add(ECKey.publicPointFromPrivate(additiveInverse));
                Ki = Ki.add(parent.getPubKeyPoint());
                break;
            default:
                throw new AssertionError();
        }

        assertNonInfinity(Ki, "Illegal derived key: derived public key equals infinity.");
        return new HDKeyDerivation.RawKeyBytes(Ki.getEncoded(true), chainCode);
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
