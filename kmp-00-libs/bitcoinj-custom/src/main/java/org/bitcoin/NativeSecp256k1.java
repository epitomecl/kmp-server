/*
 * Decompiled with CFR 0_132.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Preconditions
 */
package org.bitcoin;

import com.google.common.base.Preconditions;
import java.math.BigInteger;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import org.bitcoin.NativeSecp256k1Util;
import org.bitcoin.Secp256k1Context;

public class NativeSecp256k1 {
    private static final ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
    private static final Lock r = rwl.readLock();
    private static final Lock w = rwl.writeLock();
    private static ThreadLocal<ByteBuffer> nativeECDSABuffer = new ThreadLocal<T>();

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static boolean verify(byte[] data, byte[] signature, byte[] pub) throws NativeSecp256k1Util.AssertFailException {
        Preconditions.checkArgument((boolean)(data.length == 32 && signature.length <= 520 && pub.length <= 520));
        ByteBuffer byteBuff = nativeECDSABuffer.get();
        if (byteBuff == null || byteBuff.capacity() < 520) {
            byteBuff = ByteBuffer.allocateDirect(520);
            byteBuff.order(ByteOrder.nativeOrder());
            nativeECDSABuffer.set(byteBuff);
        }
        byteBuff.rewind();
        byteBuff.put(data);
        byteBuff.put(signature);
        byteBuff.put(pub);
        r.lock();
        try {
            boolean bl = NativeSecp256k1.secp256k1_ecdsa_verify(byteBuff, Secp256k1Context.getContext(), signature.length, pub.length) == 1;
            return bl;
        }
        finally {
            r.unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static byte[] sign(byte[] data, byte[] sec) throws NativeSecp256k1Util.AssertFailException {
        byte[][] retByteArray;
        Preconditions.checkArgument((boolean)(data.length == 32 && sec.length <= 32));
        ByteBuffer byteBuff = nativeECDSABuffer.get();
        if (byteBuff == null || byteBuff.capacity() < 64) {
            byteBuff = ByteBuffer.allocateDirect(64);
            byteBuff.order(ByteOrder.nativeOrder());
            nativeECDSABuffer.set(byteBuff);
        }
        byteBuff.rewind();
        byteBuff.put(data);
        byteBuff.put(sec);
        r.lock();
        try {
            retByteArray = NativeSecp256k1.secp256k1_ecdsa_sign(byteBuff, Secp256k1Context.getContext());
        }
        finally {
            r.unlock();
        }
        byte[] sigArr = retByteArray[0];
        int sigLen = new BigInteger(new byte[]{retByteArray[1][0]}).intValue();
        int retVal = new BigInteger(new byte[]{retByteArray[1][1]}).intValue();
        NativeSecp256k1Util.assertEquals(sigArr.length, sigLen, "Got bad signature length.");
        return retVal == 0 ? new byte[]{} : sigArr;
    }

    public static boolean secKeyVerify(byte[] seckey) {
        Preconditions.checkArgument((boolean)(seckey.length == 32));
        ByteBuffer byteBuff = nativeECDSABuffer.get();
        if (byteBuff == null || byteBuff.capacity() < seckey.length) {
            byteBuff = ByteBuffer.allocateDirect(seckey.length);
            byteBuff.order(ByteOrder.nativeOrder());
            nativeECDSABuffer.set(byteBuff);
        }
        byteBuff.rewind();
        byteBuff.put(seckey);
        r.lock();
        try {
            boolean bl = NativeSecp256k1.secp256k1_ec_seckey_verify(byteBuff, Secp256k1Context.getContext()) == 1;
            return bl;
        }
        finally {
            r.unlock();
        }
    }

    public static byte[] computePubkey(byte[] seckey) throws NativeSecp256k1Util.AssertFailException {
        byte[][] retByteArray;
        Preconditions.checkArgument((boolean)(seckey.length == 32));
        ByteBuffer byteBuff = nativeECDSABuffer.get();
        if (byteBuff == null || byteBuff.capacity() < seckey.length) {
            byteBuff = ByteBuffer.allocateDirect(seckey.length);
            byteBuff.order(ByteOrder.nativeOrder());
            nativeECDSABuffer.set(byteBuff);
        }
        byteBuff.rewind();
        byteBuff.put(seckey);
        r.lock();
        try {
            retByteArray = NativeSecp256k1.secp256k1_ec_pubkey_create(byteBuff, Secp256k1Context.getContext());
        }
        finally {
            r.unlock();
        }
        byte[] pubArr = retByteArray[0];
        int pubLen = new BigInteger(new byte[]{retByteArray[1][0]}).intValue();
        int retVal = new BigInteger(new byte[]{retByteArray[1][1]}).intValue();
        NativeSecp256k1Util.assertEquals(pubArr.length, pubLen, "Got bad pubkey length.");
        return retVal == 0 ? new byte[]{} : pubArr;
    }

    public static synchronized void cleanup() {
        w.lock();
        try {
            NativeSecp256k1.secp256k1_destroy_context(Secp256k1Context.getContext());
        }
        finally {
            w.unlock();
        }
    }

    public static long cloneContext() {
        r.lock();
        try {
            long l = NativeSecp256k1.secp256k1_ctx_clone(Secp256k1Context.getContext());
            return l;
        }
        finally {
            r.unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static byte[] privKeyTweakMul(byte[] privkey, byte[] tweak) throws NativeSecp256k1Util.AssertFailException {
        byte[][] retByteArray;
        Preconditions.checkArgument((boolean)(privkey.length == 32));
        ByteBuffer byteBuff = nativeECDSABuffer.get();
        if (byteBuff == null || byteBuff.capacity() < privkey.length + tweak.length) {
            byteBuff = ByteBuffer.allocateDirect(privkey.length + tweak.length);
            byteBuff.order(ByteOrder.nativeOrder());
            nativeECDSABuffer.set(byteBuff);
        }
        byteBuff.rewind();
        byteBuff.put(privkey);
        byteBuff.put(tweak);
        r.lock();
        try {
            retByteArray = NativeSecp256k1.secp256k1_privkey_tweak_mul(byteBuff, Secp256k1Context.getContext());
        }
        finally {
            r.unlock();
        }
        byte[] privArr = retByteArray[0];
        int privLen = (byte)new BigInteger(new byte[]{retByteArray[1][0]}).intValue() & 255;
        int retVal = new BigInteger(new byte[]{retByteArray[1][1]}).intValue();
        NativeSecp256k1Util.assertEquals(privArr.length, privLen, "Got bad pubkey length.");
        NativeSecp256k1Util.assertEquals(retVal, 1, "Failed return value check.");
        return privArr;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static byte[] privKeyTweakAdd(byte[] privkey, byte[] tweak) throws NativeSecp256k1Util.AssertFailException {
        byte[][] retByteArray;
        Preconditions.checkArgument((boolean)(privkey.length == 32));
        ByteBuffer byteBuff = nativeECDSABuffer.get();
        if (byteBuff == null || byteBuff.capacity() < privkey.length + tweak.length) {
            byteBuff = ByteBuffer.allocateDirect(privkey.length + tweak.length);
            byteBuff.order(ByteOrder.nativeOrder());
            nativeECDSABuffer.set(byteBuff);
        }
        byteBuff.rewind();
        byteBuff.put(privkey);
        byteBuff.put(tweak);
        r.lock();
        try {
            retByteArray = NativeSecp256k1.secp256k1_privkey_tweak_add(byteBuff, Secp256k1Context.getContext());
        }
        finally {
            r.unlock();
        }
        byte[] privArr = retByteArray[0];
        int privLen = (byte)new BigInteger(new byte[]{retByteArray[1][0]}).intValue() & 255;
        int retVal = new BigInteger(new byte[]{retByteArray[1][1]}).intValue();
        NativeSecp256k1Util.assertEquals(privArr.length, privLen, "Got bad pubkey length.");
        NativeSecp256k1Util.assertEquals(retVal, 1, "Failed return value check.");
        return privArr;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static byte[] pubKeyTweakAdd(byte[] pubkey, byte[] tweak) throws NativeSecp256k1Util.AssertFailException {
        byte[][] retByteArray;
        Preconditions.checkArgument((boolean)(pubkey.length == 33 || pubkey.length == 65));
        ByteBuffer byteBuff = nativeECDSABuffer.get();
        if (byteBuff == null || byteBuff.capacity() < pubkey.length + tweak.length) {
            byteBuff = ByteBuffer.allocateDirect(pubkey.length + tweak.length);
            byteBuff.order(ByteOrder.nativeOrder());
            nativeECDSABuffer.set(byteBuff);
        }
        byteBuff.rewind();
        byteBuff.put(pubkey);
        byteBuff.put(tweak);
        r.lock();
        try {
            retByteArray = NativeSecp256k1.secp256k1_pubkey_tweak_add(byteBuff, Secp256k1Context.getContext(), pubkey.length);
        }
        finally {
            r.unlock();
        }
        byte[] pubArr = retByteArray[0];
        int pubLen = (byte)new BigInteger(new byte[]{retByteArray[1][0]}).intValue() & 255;
        int retVal = new BigInteger(new byte[]{retByteArray[1][1]}).intValue();
        NativeSecp256k1Util.assertEquals(pubArr.length, pubLen, "Got bad pubkey length.");
        NativeSecp256k1Util.assertEquals(retVal, 1, "Failed return value check.");
        return pubArr;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static byte[] pubKeyTweakMul(byte[] pubkey, byte[] tweak) throws NativeSecp256k1Util.AssertFailException {
        byte[][] retByteArray;
        Preconditions.checkArgument((boolean)(pubkey.length == 33 || pubkey.length == 65));
        ByteBuffer byteBuff = nativeECDSABuffer.get();
        if (byteBuff == null || byteBuff.capacity() < pubkey.length + tweak.length) {
            byteBuff = ByteBuffer.allocateDirect(pubkey.length + tweak.length);
            byteBuff.order(ByteOrder.nativeOrder());
            nativeECDSABuffer.set(byteBuff);
        }
        byteBuff.rewind();
        byteBuff.put(pubkey);
        byteBuff.put(tweak);
        r.lock();
        try {
            retByteArray = NativeSecp256k1.secp256k1_pubkey_tweak_mul(byteBuff, Secp256k1Context.getContext(), pubkey.length);
        }
        finally {
            r.unlock();
        }
        byte[] pubArr = retByteArray[0];
        int pubLen = (byte)new BigInteger(new byte[]{retByteArray[1][0]}).intValue() & 255;
        int retVal = new BigInteger(new byte[]{retByteArray[1][1]}).intValue();
        NativeSecp256k1Util.assertEquals(pubArr.length, pubLen, "Got bad pubkey length.");
        NativeSecp256k1Util.assertEquals(retVal, 1, "Failed return value check.");
        return pubArr;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static byte[] createECDHSecret(byte[] seckey, byte[] pubkey) throws NativeSecp256k1Util.AssertFailException {
        byte[][] retByteArray;
        Preconditions.checkArgument((boolean)(seckey.length <= 32 && pubkey.length <= 65));
        ByteBuffer byteBuff = nativeECDSABuffer.get();
        if (byteBuff == null || byteBuff.capacity() < 32 + pubkey.length) {
            byteBuff = ByteBuffer.allocateDirect(32 + pubkey.length);
            byteBuff.order(ByteOrder.nativeOrder());
            nativeECDSABuffer.set(byteBuff);
        }
        byteBuff.rewind();
        byteBuff.put(seckey);
        byteBuff.put(pubkey);
        r.lock();
        try {
            retByteArray = NativeSecp256k1.secp256k1_ecdh(byteBuff, Secp256k1Context.getContext(), pubkey.length);
        }
        finally {
            r.unlock();
        }
        byte[] resArr = retByteArray[0];
        int retVal = new BigInteger(new byte[]{retByteArray[1][0]}).intValue();
        NativeSecp256k1Util.assertEquals(resArr.length, 32, "Got bad result length.");
        NativeSecp256k1Util.assertEquals(retVal, 1, "Failed return value check.");
        return resArr;
    }

    public static synchronized boolean randomize(byte[] seed) throws NativeSecp256k1Util.AssertFailException {
        Preconditions.checkArgument((boolean)(seed.length == 32 || seed == null));
        ByteBuffer byteBuff = nativeECDSABuffer.get();
        if (byteBuff == null || byteBuff.capacity() < seed.length) {
            byteBuff = ByteBuffer.allocateDirect(seed.length);
            byteBuff.order(ByteOrder.nativeOrder());
            nativeECDSABuffer.set(byteBuff);
        }
        byteBuff.rewind();
        byteBuff.put(seed);
        w.lock();
        try {
            boolean bl = NativeSecp256k1.secp256k1_context_randomize(byteBuff, Secp256k1Context.getContext()) == 1;
            return bl;
        }
        finally {
            w.unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static byte[] schnorrSign(byte[] data, byte[] sec) throws NativeSecp256k1Util.AssertFailException {
        byte[][] retByteArray;
        Preconditions.checkArgument((boolean)(data.length == 32 && sec.length <= 32));
        ByteBuffer byteBuff = nativeECDSABuffer.get();
        if (byteBuff == null) {
            byteBuff = ByteBuffer.allocateDirect(64);
            byteBuff.order(ByteOrder.nativeOrder());
            nativeECDSABuffer.set(byteBuff);
        }
        byteBuff.rewind();
        byteBuff.put(data);
        byteBuff.put(sec);
        r.lock();
        try {
            retByteArray = NativeSecp256k1.secp256k1_schnorr_sign(byteBuff, Secp256k1Context.getContext());
        }
        finally {
            r.unlock();
        }
        byte[] sigArr = retByteArray[0];
        int retVal = new BigInteger(new byte[]{retByteArray[1][0]}).intValue();
        NativeSecp256k1Util.assertEquals(sigArr.length, 64, "Got bad signature length.");
        return retVal == 0 ? new byte[]{} : sigArr;
    }

    private static native long secp256k1_ctx_clone(long var0);

    private static native int secp256k1_context_randomize(ByteBuffer var0, long var1);

    private static native byte[][] secp256k1_privkey_tweak_add(ByteBuffer var0, long var1);

    private static native byte[][] secp256k1_privkey_tweak_mul(ByteBuffer var0, long var1);

    private static native byte[][] secp256k1_pubkey_tweak_add(ByteBuffer var0, long var1, int var3);

    private static native byte[][] secp256k1_pubkey_tweak_mul(ByteBuffer var0, long var1, int var3);

    private static native void secp256k1_destroy_context(long var0);

    private static native int secp256k1_ecdsa_verify(ByteBuffer var0, long var1, int var3, int var4);

    private static native byte[][] secp256k1_ecdsa_sign(ByteBuffer var0, long var1);

    private static native int secp256k1_ec_seckey_verify(ByteBuffer var0, long var1);

    private static native byte[][] secp256k1_ec_pubkey_create(ByteBuffer var0, long var1);

    private static native byte[][] secp256k1_ec_pubkey_parse(ByteBuffer var0, long var1, int var3);

    private static native byte[][] secp256k1_schnorr_sign(ByteBuffer var0, long var1);

    private static native byte[][] secp256k1_ecdh(ByteBuffer var0, long var1, int var3);
}

