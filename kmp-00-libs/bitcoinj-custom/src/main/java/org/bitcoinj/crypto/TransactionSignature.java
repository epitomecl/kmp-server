/*
 * Decompiled with CFR 0_132.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Preconditions
 */
package org.bitcoinj.crypto;

import com.google.common.base.Preconditions;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import org.bitcoinj.core.ECKey;
import org.bitcoinj.core.Transaction;
import org.bitcoinj.core.VerificationException;

public class TransactionSignature
extends ECKey.ECDSASignature {
    public final int sighashFlags;

    public TransactionSignature(BigInteger r, BigInteger s) {
        this(r, s, Transaction.SigHash.ALL.value);
    }

    public TransactionSignature(BigInteger r, BigInteger s, int sighashFlags) {
        super(r, s);
        this.sighashFlags = sighashFlags;
    }

    public TransactionSignature(ECKey.ECDSASignature signature, Transaction.SigHash mode, boolean anyoneCanPay) {
        super(signature.r, signature.s);
        this.sighashFlags = TransactionSignature.calcSigHashValue(mode, anyoneCanPay);
    }

    public TransactionSignature(ECKey.ECDSASignature signature, Transaction.SigHash mode, boolean anyoneCanPay, boolean useForkId) {
        super(signature.r, signature.s);
        this.sighashFlags = TransactionSignature.calcSigHashValue(mode, anyoneCanPay, useForkId);
    }

    public static TransactionSignature dummy() {
        BigInteger val = ECKey.HALF_CURVE_ORDER;
        return new TransactionSignature(val, val);
    }

    public static int calcSigHashValue(Transaction.SigHash mode, boolean anyoneCanPay) {
        Preconditions.checkArgument((boolean)(Transaction.SigHash.ALL == mode || Transaction.SigHash.NONE == mode || Transaction.SigHash.SINGLE == mode));
        int sighashFlags = mode.value;
        if (anyoneCanPay) {
            sighashFlags |= Transaction.SigHash.ANYONECANPAY.value;
        }
        return sighashFlags;
    }

    public static int calcSigHashValue(Transaction.SigHash mode, boolean anyoneCanPay, boolean useForkId) {
        Preconditions.checkArgument((boolean)(Transaction.SigHash.ALL == mode || Transaction.SigHash.NONE == mode || Transaction.SigHash.SINGLE == mode));
        int sighashFlags = mode.value;
        if (anyoneCanPay) {
            sighashFlags |= Transaction.SigHash.ANYONECANPAY.value;
        }
        if (useForkId) {
            sighashFlags |= Transaction.SigHash.FORKID.value;
        }
        return sighashFlags;
    }

    public static boolean isEncodingCanonical(byte[] signature) {
        if (signature.length < 9 || signature.length > 73) {
            return false;
        }
        int hashType = signature[signature.length - 1] & 255 & ~ (Transaction.SigHash.ANYONECANPAY.value | Transaction.SigHash.FORKID.value);
        if (hashType < Transaction.SigHash.ALL.value || hashType > Transaction.SigHash.SINGLE.value) {
            return false;
        }
        if ((signature[0] & 255) != 48 || (signature[1] & 255) != signature.length - 3) {
            return false;
        }
        int lenR = signature[3] & 255;
        if (5 + lenR >= signature.length || lenR == 0) {
            return false;
        }
        int lenS = signature[5 + lenR] & 255;
        if (lenR + lenS + 7 != signature.length || lenS == 0) {
            return false;
        }
        if (signature[2] != 2 || (signature[4] & 128) == 128) {
            return false;
        }
        if (lenR > 1 && signature[4] == 0 && (signature[5] & 128) != 128) {
            return false;
        }
        if (signature[6 + lenR - 2] != 2 || (signature[6 + lenR] & 128) == 128) {
            return false;
        }
        if (lenS > 1 && signature[6 + lenR] == 0 && (signature[6 + lenR + 1] & 128) != 128) {
            return false;
        }
        return true;
    }

    public static boolean hasForkId(byte[] signature) {
        int forkId = signature[signature.length - 1] & 255 & Transaction.SigHash.FORKID.value;
        return forkId == Transaction.SigHash.FORKID.value;
    }

    public boolean anyoneCanPay() {
        return (this.sighashFlags & Transaction.SigHash.ANYONECANPAY.value) != 0;
    }

    public boolean useForkId() {
        return (this.sighashFlags & Transaction.SigHash.FORKID.value) != 0;
    }

    public Transaction.SigHash sigHashMode() {
        int mode = this.sighashFlags & 31;
        if (mode == Transaction.SigHash.NONE.value) {
            return Transaction.SigHash.NONE;
        }
        if (mode == Transaction.SigHash.SINGLE.value) {
            return Transaction.SigHash.SINGLE;
        }
        return Transaction.SigHash.ALL;
    }

    public byte[] encodeToBitcoin() {
        try {
            ByteArrayOutputStream bos = this.derByteStream();
            bos.write(this.sighashFlags);
            return bos.toByteArray();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ECKey.ECDSASignature toCanonicalised() {
        return new TransactionSignature(super.toCanonicalised(), this.sigHashMode(), this.anyoneCanPay(), this.useForkId());
    }

    @Deprecated
    public static TransactionSignature decodeFromBitcoin(byte[] bytes, boolean requireCanonicalEncoding) throws VerificationException {
        return TransactionSignature.decodeFromBitcoin(bytes, requireCanonicalEncoding, false);
    }

    public static TransactionSignature decodeFromBitcoin(byte[] bytes, boolean requireCanonicalEncoding, boolean requireCanonicalSValue) throws VerificationException {
        ECKey.ECDSASignature sig;
        if (requireCanonicalEncoding && !TransactionSignature.isEncodingCanonical(bytes)) {
            throw new VerificationException("Signature encoding is not canonical.");
        }
        try {
            sig = ECKey.ECDSASignature.decodeFromDER(bytes);
        }
        catch (IllegalArgumentException e) {
            throw new VerificationException("Could not decode DER", e);
        }
        if (requireCanonicalSValue && !sig.isCanonical()) {
            throw new VerificationException("S-value is not canonical.");
        }
        return new TransactionSignature(sig.r, sig.s, bytes[bytes.length - 1]);
    }
}

