/*
 * Decompiled with CFR 0_132.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Charsets
 *  com.google.common.base.Joiner
 *  com.google.common.base.Preconditions
 *  com.google.common.collect.Lists
 *  com.google.common.collect.Ordering
 *  com.google.common.io.BaseEncoding
 *  com.google.common.io.Resources
 *  com.google.common.primitives.Ints
 *  com.google.common.primitives.UnsignedLongs
 *  com.google.common.util.concurrent.Uninterruptibles
 *  org.spongycastle.crypto.digests.RIPEMD160Digest
 */
package org.bitcoinj.core;

import com.google.common.base.Charsets;
import com.google.common.base.Joiner;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;
import com.google.common.io.BaseEncoding;
import com.google.common.io.Resources;
import com.google.common.primitives.Ints;
import com.google.common.primitives.UnsignedLongs;
import com.google.common.util.concurrent.Uninterruptibles;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import org.bitcoinj.core.AddressFormatException;
import org.bitcoinj.core.Base58;
import org.bitcoinj.core.Sha256Hash;
import org.bitcoinj.core.VarInt;
import org.spongycastle.crypto.digests.RIPEMD160Digest;

public class Utils {
    public static final String BITCOIN_SIGNED_MESSAGE_HEADER = "Bitcoin Signed Message:\n";
    public static final byte[] BITCOIN_SIGNED_MESSAGE_HEADER_BYTES = "Bitcoin Signed Message:\n".getBytes(Charsets.UTF_8);
    public static final Joiner SPACE_JOINER = Joiner.on((String)" ");
    private static BlockingQueue<Boolean> mockSleepQueue;
    public static final BaseEncoding HEX;
    public static volatile Date mockTime;
    private static final TimeZone UTC;
    private static final int[] bitMask;
    private static int isAndroid;

    public static byte[] bigIntegerToBytes(BigInteger b, int numBytes) {
        Preconditions.checkArgument((boolean)(b.signum() >= 0), (Object)"b must be positive or zero");
        Preconditions.checkArgument((boolean)(numBytes > 0), (Object)"numBytes must be positive");
        byte[] src = b.toByteArray();
        byte[] dest = new byte[numBytes];
        boolean isFirstByteOnlyForSign = src[0] == 0;
        int length = isFirstByteOnlyForSign ? src.length - 1 : src.length;
        Preconditions.checkArgument((boolean)(length <= numBytes), (Object)("The given number does not fit in " + numBytes));
        int srcPos = isFirstByteOnlyForSign ? 1 : 0;
        int destPos = numBytes - length;
        System.arraycopy(src, srcPos, dest, destPos, length);
        return dest;
    }

    public static void uint32ToByteArrayBE(long val, byte[] out, int offset) {
        out[offset] = (byte)(255L & val >> 24);
        out[offset + 1] = (byte)(255L & val >> 16);
        out[offset + 2] = (byte)(255L & val >> 8);
        out[offset + 3] = (byte)(255L & val);
    }

    public static void uint32ToByteArrayLE(long val, byte[] out, int offset) {
        out[offset] = (byte)(255L & val);
        out[offset + 1] = (byte)(255L & val >> 8);
        out[offset + 2] = (byte)(255L & val >> 16);
        out[offset + 3] = (byte)(255L & val >> 24);
    }

    public static void uint64ToByteArrayLE(long val, byte[] out, int offset) {
        out[offset] = (byte)(255L & val);
        out[offset + 1] = (byte)(255L & val >> 8);
        out[offset + 2] = (byte)(255L & val >> 16);
        out[offset + 3] = (byte)(255L & val >> 24);
        out[offset + 4] = (byte)(255L & val >> 32);
        out[offset + 5] = (byte)(255L & val >> 40);
        out[offset + 6] = (byte)(255L & val >> 48);
        out[offset + 7] = (byte)(255L & val >> 56);
    }

    public static void uint32ToByteStreamLE(long val, OutputStream stream) throws IOException {
        stream.write((int)(255L & val));
        stream.write((int)(255L & val >> 8));
        stream.write((int)(255L & val >> 16));
        stream.write((int)(255L & val >> 24));
    }

    public static void int64ToByteStreamLE(long val, OutputStream stream) throws IOException {
        stream.write((int)(255L & val));
        stream.write((int)(255L & val >> 8));
        stream.write((int)(255L & val >> 16));
        stream.write((int)(255L & val >> 24));
        stream.write((int)(255L & val >> 32));
        stream.write((int)(255L & val >> 40));
        stream.write((int)(255L & val >> 48));
        stream.write((int)(255L & val >> 56));
    }

    public static void uint64ToByteStreamLE(BigInteger val, OutputStream stream) throws IOException {
        byte[] bytes = val.toByteArray();
        if (bytes.length > 8) {
            throw new RuntimeException("Input too large to encode into a uint64");
        }
        bytes = Utils.reverseBytes(bytes);
        stream.write(bytes);
        if (bytes.length < 8) {
            for (int i = 0; i < 8 - bytes.length; ++i) {
                stream.write(0);
            }
        }
    }

    public static boolean isLessThanUnsigned(long n1, long n2) {
        return UnsignedLongs.compare((long)n1, (long)n2) < 0;
    }

    public static boolean isLessThanOrEqualToUnsigned(long n1, long n2) {
        return UnsignedLongs.compare((long)n1, (long)n2) <= 0;
    }

    public static byte[] reverseBytes(byte[] bytes) {
        byte[] buf = new byte[bytes.length];
        for (int i = 0; i < bytes.length; ++i) {
            buf[i] = bytes[bytes.length - 1 - i];
        }
        return buf;
    }

    public static byte[] reverseDwordBytes(byte[] bytes, int trimLength) {
        Preconditions.checkArgument((boolean)(bytes.length % 4 == 0));
        Preconditions.checkArgument((boolean)(trimLength < 0 || trimLength % 4 == 0));
        byte[] rev = new byte[trimLength >= 0 && bytes.length > trimLength ? trimLength : bytes.length];
        for (int i = 0; i < rev.length; i += 4) {
            System.arraycopy(bytes, i, rev, i, 4);
            for (int j = 0; j < 4; ++j) {
                rev[i + j] = bytes[i + 3 - j];
            }
        }
        return rev;
    }

    public static long readUint32(byte[] bytes, int offset) {
        return (long)bytes[offset] & 255L | ((long)bytes[offset + 1] & 255L) << 8 | ((long)bytes[offset + 2] & 255L) << 16 | ((long)bytes[offset + 3] & 255L) << 24;
    }

    public static long readInt64(byte[] bytes, int offset) {
        return (long)bytes[offset] & 255L | ((long)bytes[offset + 1] & 255L) << 8 | ((long)bytes[offset + 2] & 255L) << 16 | ((long)bytes[offset + 3] & 255L) << 24 | ((long)bytes[offset + 4] & 255L) << 32 | ((long)bytes[offset + 5] & 255L) << 40 | ((long)bytes[offset + 6] & 255L) << 48 | ((long)bytes[offset + 7] & 255L) << 56;
    }

    public static long readUint32BE(byte[] bytes, int offset) {
        return ((long)bytes[offset] & 255L) << 24 | ((long)bytes[offset + 1] & 255L) << 16 | ((long)bytes[offset + 2] & 255L) << 8 | (long)bytes[offset + 3] & 255L;
    }

    public static int readUint16BE(byte[] bytes, int offset) {
        return (bytes[offset] & 255) << 8 | bytes[offset + 1] & 255;
    }

    public static byte[] sha256hash160(byte[] input) {
        byte[] sha256 = Sha256Hash.hash(input);
        RIPEMD160Digest digest = new RIPEMD160Digest();
        digest.update(sha256, 0, sha256.length);
        byte[] out = new byte[20];
        digest.doFinal(out, 0);
        return out;
    }

    public static BigInteger decodeMPI(byte[] mpi, boolean hasLength) {
        boolean isNegative;
        byte[] buf;
        if (hasLength) {
            int length = (int)Utils.readUint32BE(mpi, 0);
            buf = new byte[length];
            System.arraycopy(mpi, 4, buf, 0, length);
        } else {
            buf = mpi;
        }
        if (buf.length == 0) {
            return BigInteger.ZERO;
        }
        boolean bl = isNegative = (buf[0] & 128) == 128;
        if (isNegative) {
            byte[] arrby = buf;
            arrby[0] = (byte)(arrby[0] & 127);
        }
        BigInteger result = new BigInteger(buf);
        return isNegative ? result.negate() : result;
    }

    public static byte[] encodeMPI(BigInteger value, boolean includeLength) {
        byte[] result;
        boolean isNegative;
        if (value.equals(BigInteger.ZERO)) {
            if (!includeLength) {
                return new byte[0];
            }
            return new byte[]{0, 0, 0, 0};
        }
        boolean bl = isNegative = value.signum() < 0;
        if (isNegative) {
            value = value.negate();
        }
        byte[] array = value.toByteArray();
        int length = array.length;
        if ((array[0] & 128) == 128) {
            ++length;
        }
        if (includeLength) {
            byte[] result2 = new byte[length + 4];
            System.arraycopy(array, 0, result2, length - array.length + 3, array.length);
            Utils.uint32ToByteArrayBE(length, result2, 0);
            if (isNegative) {
                byte[] arrby = result2;
                arrby[4] = (byte)(arrby[4] | 128);
            }
            return result2;
        }
        if (length != array.length) {
            result = new byte[length];
            System.arraycopy(array, 0, result, 1, array.length);
        } else {
            result = array;
        }
        if (isNegative) {
            byte[] arrby = result;
            arrby[0] = (byte)(arrby[0] | 128);
        }
        return result;
    }

    public static BigInteger decodeCompactBits(long compact) {
        int size = (int)(compact >> 24) & 255;
        byte[] bytes = new byte[4 + size];
        bytes[3] = (byte)size;
        if (size >= 1) {
            bytes[4] = (byte)(compact >> 16 & 255L);
        }
        if (size >= 2) {
            bytes[5] = (byte)(compact >> 8 & 255L);
        }
        if (size >= 3) {
            bytes[6] = (byte)(compact & 255L);
        }
        return Utils.decodeMPI(bytes, true);
    }

    public static long encodeCompactBits(BigInteger value) {
        int size = value.toByteArray().length;
        long result = size <= 3 ? value.longValue() << 8 * (3 - size) : value.shiftRight(8 * (size - 3)).longValue();
        if ((result & 0x800000L) != 0L) {
            result >>= 8;
            ++size;
        }
        result |= (long)(size << 24);
        return result |= value.signum() == -1 ? 0x800000L : 0L;
    }

    public static Date rollMockClock(int seconds) {
        return Utils.rollMockClockMillis(seconds * 1000);
    }

    public static Date rollMockClockMillis(long millis) {
        if (mockTime == null) {
            throw new IllegalStateException("You need to use setMockClock() first.");
        }
        mockTime = new Date(mockTime.getTime() + millis);
        return mockTime;
    }

    public static void setMockClock() {
        mockTime = new Date();
    }

    public static void setMockClock(long mockClockSeconds) {
        mockTime = new Date(mockClockSeconds * 1000L);
    }

    public static Date now() {
        return mockTime != null ? mockTime : new Date();
    }

    public static long currentTimeMillis() {
        return mockTime != null ? mockTime.getTime() : System.currentTimeMillis();
    }

    public static long currentTimeSeconds() {
        return Utils.currentTimeMillis() / 1000L;
    }

    public static String dateTimeFormat(Date dateTime) {
        SimpleDateFormat iso8601 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US);
        iso8601.setTimeZone(UTC);
        return iso8601.format(dateTime);
    }

    public static String dateTimeFormat(long dateTime) {
        SimpleDateFormat iso8601 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US);
        iso8601.setTimeZone(UTC);
        return iso8601.format(dateTime);
    }

    public static byte[] copyOf(byte[] in, int length) {
        byte[] out = new byte[length];
        System.arraycopy(in, 0, out, 0, Math.min(length, in.length));
        return out;
    }

    public static byte[] appendByte(byte[] bytes, byte b) {
        byte[] result = Arrays.copyOf(bytes, bytes.length + 1);
        result[result.length - 1] = b;
        return result;
    }

    public static String toString(byte[] bytes, String charsetName) {
        try {
            return new String(bytes, charsetName);
        }
        catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    public static byte[] toBytes(CharSequence str, String charsetName) {
        try {
            return str.toString().getBytes(charsetName);
        }
        catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    public static byte[] parseAsHexOrBase58(String data) {
        try {
            return HEX.decode((CharSequence)data);
        }
        catch (Exception e) {
            try {
                return Base58.decodeChecked(data);
            }
            catch (AddressFormatException e1) {
                return null;
            }
        }
    }

    public static boolean isWindows() {
        return System.getProperty("os.name").toLowerCase().contains("win");
    }

    public static byte[] formatMessageForSigning(String message) {
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bos.write(BITCOIN_SIGNED_MESSAGE_HEADER_BYTES.length);
            bos.write(BITCOIN_SIGNED_MESSAGE_HEADER_BYTES);
            byte[] messageBytes = message.getBytes(Charsets.UTF_8);
            VarInt size = new VarInt(messageBytes.length);
            bos.write(size.encode());
            bos.write(messageBytes);
            return bos.toByteArray();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean checkBitLE(byte[] data, int index) {
        return (data[index >>> 3] & bitMask[7 & index]) != 0;
    }

    public static void setBitLE(byte[] data, int index) {
        byte[] arrby = data;
        int n = index >>> 3;
        arrby[n] = (byte)(arrby[n] | bitMask[7 & index]);
    }

    public static void sleep(long millis) {
        if (mockSleepQueue == null) {
            Uninterruptibles.sleepUninterruptibly((long)millis, (TimeUnit)TimeUnit.MILLISECONDS);
        } else {
            try {
                boolean isMultiPass = mockSleepQueue.take();
                Utils.rollMockClockMillis(millis);
                if (isMultiPass) {
                    mockSleepQueue.offer(true);
                }
            }
            catch (InterruptedException isMultiPass) {
                // empty catch block
            }
        }
    }

    public static void setMockSleep(boolean isEnable) {
        if (isEnable) {
            mockSleepQueue = new ArrayBlockingQueue<Boolean>(1);
            mockTime = new Date(System.currentTimeMillis());
        } else {
            mockSleepQueue = null;
        }
    }

    public static void passMockSleep() {
        mockSleepQueue.offer(false);
    }

    public static void finishMockSleep() {
        if (mockSleepQueue != null) {
            mockSleepQueue.offer(true);
        }
    }

    public static boolean isAndroidRuntime() {
        if (isAndroid == -1) {
            String runtime = System.getProperty("java.runtime.name");
            isAndroid = runtime != null && runtime.equals("Android Runtime") ? 1 : 0;
        }
        return isAndroid == 1;
    }

    public static /* varargs */ int maxOfMostFreq(int ... items) {
        ArrayList<Integer> list = new ArrayList<Integer>(items.length);
        for (int item : items) {
            list.add(item);
        }
        return Utils.maxOfMostFreq(list);
    }

    public static int maxOfMostFreq(List<Integer> items) {
        if (items.isEmpty()) {
            return 0;
        }
        items = Ordering.natural().reverse().sortedCopy(items);
        LinkedList<Pair> pairs = Lists.newLinkedList();
        pairs.add(new Pair(items.get(0), 0));
        Iterator iterator = items.iterator();
        while (iterator.hasNext()) {
            int item = (Integer)iterator.next();
            Pair pair = pairs.getLast();
            if (pair.item != item) {
                pair = new Pair(item, 0);
                pairs.add(pair);
            }
            ++pair.count;
        }
        Collections.sort(pairs);
        int maxCount = (pairs.getFirst()).count;
        int maxItem = (pairs.getFirst()).item;
        for (Pair pair : pairs) {
            if (pair.count != maxCount) break;
            maxItem = Math.max(maxItem, pair.item);
        }
        return maxItem;
    }

    public static String getResourceAsString(URL url) throws IOException {
        List lines = Resources.readLines((URL)url, (Charset)Charsets.UTF_8);
        return Joiner.on((char)'\n').join((Iterable)lines);
    }

    public static InputStream closeUnchecked(InputStream stream) {
        try {
            stream.close();
            return stream;
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static OutputStream closeUnchecked(OutputStream stream) {
        try {
            stream.close();
            return stream;
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    static {
        HEX = BaseEncoding.base16().lowerCase();
        UTC = TimeZone.getTimeZone("UTC");
        bitMask = new int[]{1, 2, 4, 8, 16, 32, 64, 128};
        isAndroid = -1;
    }

    private static class Pair
    implements Comparable<Pair> {
        int item;
        int count;

        public Pair(int item, int count) {
            this.count = count;
            this.item = item;
        }

        @Override
        public int compareTo(Pair o) {
            return - Ints.compare((int)this.count, (int)o.count);
        }
    }

}

