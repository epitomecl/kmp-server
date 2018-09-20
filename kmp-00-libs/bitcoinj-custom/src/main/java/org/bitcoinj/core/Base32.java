/*
 * Decompiled with CFR 0_132.
 * 
 * Could not load the following classes:
 *  com.google.common.primitives.Bytes
 */
package org.bitcoinj.core;

import com.google.common.primitives.Bytes;
import java.math.BigInteger;
import java.util.ArrayList;
import org.bitcoinj.core.AddressFormatException;

public class Base32 {
    private static final String CHARSET = "qpzry9x8gf2tvdw0s3jn54khce6mua7l";
    private static final int CSLEN = 8;
    public String prefix;
    public byte[] words;

    public Base32(String prefix, byte[] words) {
        this.prefix = prefix;
        this.words = words;
    }

    private static BigInteger polymod(BigInteger pre) {
        BigInteger b = pre.shiftRight(35);
        BigInteger mask = new BigInteger("07ffffffff", 16);
        BigInteger v = pre.and(mask).shiftLeft(5);
        if (b.and(BigInteger.valueOf(1L)).intValue() > 0) {
            v = v.xor(new BigInteger("98f2bc8e61", 16));
        }
        if (b.and(BigInteger.valueOf(2L)).intValue() > 0) {
            v = v.xor(new BigInteger("79b76d99e2", 16));
        }
        if (b.and(BigInteger.valueOf(4L)).intValue() > 0) {
            v = v.xor(new BigInteger("f33e5fb3c4", 16));
        }
        if (b.and(BigInteger.valueOf(8L)).intValue() > 0) {
            v = v.xor(new BigInteger("ae2eabe2a8", 16));
        }
        if (b.and(BigInteger.valueOf(16L)).intValue() > 0) {
            v = v.xor(new BigInteger("1e4f43e470", 16));
        }
        return v;
    }

    public static Base32 decode(String str) {
        if (str.length() < 8) {
            throw new AddressFormatException("bech32 input too short");
        }
        if (str.length() > 90) {
            throw new AddressFormatException("bech32 input too long");
        }
        String lowered = str.toLowerCase();
        String uppered = str.toUpperCase();
        if (!str.equals(lowered) && !str.equals(uppered)) {
            throw new AddressFormatException("bech32 cannot mix upper and lower case");
        }
        str = lowered;
        int split = str.lastIndexOf(":");
        if (split < 1) {
            throw new AddressFormatException("bech32 missing separator");
        }
        if (split == 0) {
            throw new AddressFormatException("bech32 missing prefix");
        }
        String prefix = str.substring(0, split);
        String wordChars = str.substring(split + 1);
        if (wordChars.length() < 6) {
            throw new AddressFormatException("bech32 data too short");
        }
        BigInteger chk = Base32.prefixChk(prefix);
        ArrayList<BigInteger> words = new ArrayList<BigInteger>();
        for (int i = 0; i < wordChars.length(); ++i) {
            char c = wordChars.charAt(i);
            byte v = (byte)CHARSET.indexOf(c);
            if (v > wordChars.length() - 1 || CHARSET.indexOf(wordChars.charAt(v)) == -1) {
                throw new AddressFormatException("bech32 characters  out of range");
            }
            chk = Base32.polymod(chk).xor(BigInteger.valueOf(v));
            if (i + 8 >= wordChars.length()) continue;
            words.add(BigInteger.valueOf(v));
        }
        if (chk.intValue() != 1) {
            throw new AddressFormatException("invalid bech32 checksum");
        }
        return new Base32(prefix, Bytes.toArray(words));
    }

    public static String encode(String prefix, byte[] words) {
        int i;
        if (prefix.length() + 8 + 1 + words.length > 90) {
            throw new AddressFormatException("Exceeds Base32 maximum length");
        }
        prefix = prefix.toLowerCase();
        BigInteger chk = Base32.prefixChk(prefix);
        String result = prefix + ":";
        for (i = 0; i < words.length; ++i) {
            byte x = words[i];
            if (x >> 5 != 0) {
                throw new AddressFormatException("Non 5-bit word");
            }
            chk = Base32.polymod(chk).xor(BigInteger.valueOf(x));
            result = result + CHARSET.charAt(x);
        }
        for (i = 0; i < 8; ++i) {
            chk = Base32.polymod(chk);
        }
        chk = chk.xor(BigInteger.valueOf(1L));
        for (i = 0; i < 8; ++i) {
            int pos = 5 * (7 - i);
            BigInteger v2 = chk.shiftRight(pos).and(new BigInteger("1f", 16));
            result = result + CHARSET.charAt(v2.intValue());
        }
        return result;
    }

    private static BigInteger prefixChk(String prefix) {
        BigInteger chk = BigInteger.valueOf(1L);
        for (int i = 0; i < prefix.length(); ++i) {
            BigInteger c = BigInteger.valueOf(Character.codePointAt(prefix, i));
            BigInteger mixwith = c.and(new BigInteger("1f", 16));
            chk = Base32.polymod(chk).xor(mixwith);
        }
        chk = Base32.polymod(chk);
        return chk;
    }

    public static byte[] convert(byte[] data, int inBits, int outBits, boolean pad) {
        BigInteger value = BigInteger.valueOf(0L);
        int bits = 0;
        BigInteger maxV = BigInteger.valueOf((1 << outBits) - 1);
        ArrayList<Byte> result = new ArrayList<Byte>();
        for (int i = 0; i < data.length; ++i) {
            int unsigned = data[i] & 255;
            value = value.shiftLeft(inBits).or(BigInteger.valueOf(unsigned));
            bits += inBits;
            while (bits >= outBits) {
                result.add(value.shiftRight(bits -= outBits).and(maxV).byteValue());
            }
        }
        if (pad) {
            if (bits > 0) {
                result.add(value.shiftLeft(outBits - bits).and(maxV).byteValue());
            }
        } else {
            if (bits >= inBits) {
                throw new AddressFormatException("Excess padding");
            }
            if (value.shiftLeft(outBits - bits).and(maxV).intValue() > 0) {
                throw new AddressFormatException("Non-zero padding");
            }
        }
        return Bytes.toArray(result);
    }

    public static byte[] toWords(byte[] bytes) {
        return Base32.convert(bytes, 8, 5, true);
    }

    public static byte[] fromWords(byte[] words) {
        return Base32.convert(words, 5, 8, false);
    }
}

