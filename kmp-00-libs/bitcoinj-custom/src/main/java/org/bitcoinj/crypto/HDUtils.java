/*
 * Decompiled with CFR 0_132.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Joiner
 *  com.google.common.collect.ImmutableList
 *  com.google.common.collect.ImmutableList$Builder
 *  com.google.common.collect.Iterables
 *  javax.annotation.Nonnull
 *  org.spongycastle.crypto.CipherParameters
 *  org.spongycastle.crypto.Digest
 *  org.spongycastle.crypto.digests.SHA512Digest
 *  org.spongycastle.crypto.macs.HMac
 *  org.spongycastle.crypto.params.ECDomainParameters
 *  org.spongycastle.crypto.params.KeyParameter
 *  org.spongycastle.math.ec.ECCurve
 *  org.spongycastle.math.ec.ECPoint
 */
package org.bitcoinj.crypto;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.annotation.Nonnull;
import org.bitcoinj.core.ECKey;
import org.bitcoinj.crypto.ChildNumber;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.Digest;
import org.spongycastle.crypto.digests.SHA512Digest;
import org.spongycastle.crypto.macs.HMac;
import org.spongycastle.crypto.params.ECDomainParameters;
import org.spongycastle.crypto.params.KeyParameter;
import org.spongycastle.math.ec.ECCurve;
import org.spongycastle.math.ec.ECPoint;

public final class HDUtils {
    private static final Joiner PATH_JOINER = Joiner.on((String)"/");

    static HMac createHmacSha512Digest(byte[] key) {
        SHA512Digest digest = new SHA512Digest();
        HMac hMac = new HMac((Digest)digest);
        hMac.init((CipherParameters)new KeyParameter(key));
        return hMac;
    }

    static byte[] hmacSha512(HMac hmacSha512, byte[] input) {
        hmacSha512.reset();
        hmacSha512.update(input, 0, input.length);
        byte[] out = new byte[64];
        hmacSha512.doFinal(out, 0);
        return out;
    }

    public static byte[] hmacSha512(byte[] key, byte[] data) {
        return HDUtils.hmacSha512(HDUtils.createHmacSha512Digest(key), data);
    }

    static byte[] toCompressed(byte[] uncompressedPoint) {
        return ECKey.CURVE.getCurve().decodePoint(uncompressedPoint).getEncoded(true);
    }

    static byte[] longTo4ByteArray(long n) {
        byte[] bytes = Arrays.copyOfRange(ByteBuffer.allocate(8).putLong(n).array(), 4, 8);
        assert (bytes.length == 4);
        return bytes;
    }

    public static ImmutableList<ChildNumber> append(List<ChildNumber> path, ChildNumber childNumber) {
        return ImmutableList.<ChildNumber>builder().addAll(path).add(childNumber).build();
    }

    public static ImmutableList<ChildNumber> concat(List<ChildNumber> path, List<ChildNumber> path2) {
        return ImmutableList.<ChildNumber>builder().addAll(path).addAll(path2).build();
    }

    public static String formatPath(List<ChildNumber> path) {
        return PATH_JOINER.join(Iterables.concat(Collections.singleton("M"), path));
    }

    public static List<ChildNumber> parsePath(@Nonnull String path) {
        String[] parsedNodes = path.replace("M", "").split("/");
        ArrayList<ChildNumber> nodes = new ArrayList<ChildNumber>();
        for (String n : parsedNodes) {
            if ((n = n.replaceAll(" ", "")).length() == 0) continue;
            boolean isHard = n.endsWith("H");
            if (isHard) {
                n = n.substring(0, n.length() - 1);
            }
            int nodeNumber = Integer.parseInt(n);
            nodes.add(new ChildNumber(nodeNumber, isHard));
        }
        return nodes;
    }
}

