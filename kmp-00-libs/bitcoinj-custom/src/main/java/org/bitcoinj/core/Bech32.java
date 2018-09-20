/*
 * Decompiled with CFR 0_132.
 */
package org.bitcoinj.core;

import java.util.Locale;
import org.bitcoinj.core.AddressFormatException;
import org.bitcoinj.core.NetworkParameters;

public class Bech32 {
    private static final String CHARSET = "qpzry9x8gf2tvdw0s3jn54khce6mua7l";

    public static String encode(NetworkParameters params, byte[] data) throws AddressFormatException {
        String prefix = params.getBech32AddressPrefix();
        byte separator = params.getBech32AddressSeparator();
        byte[] checksum = Bech32.createChecksum(prefix.getBytes(), data);
        byte[] combined = new byte[checksum.length + data.length];
        System.arraycopy(data, 0, combined, 0, data.length);
        System.arraycopy(checksum, 0, combined, data.length, checksum.length);
        byte[] xlat = new byte[combined.length];
        for (int i = 0; i < combined.length; ++i) {
            xlat[i] = (byte)CHARSET.charAt(combined[i]);
        }
        byte[] ret = new byte[prefix.getBytes().length + xlat.length + 1];
        System.arraycopy(prefix.getBytes(), 0, ret, 0, prefix.getBytes().length);
        System.arraycopy(new byte[]{separator}, 0, ret, prefix.getBytes().length, 1);
        System.arraycopy(xlat, 0, ret, prefix.getBytes().length + 1, xlat.length);
        return new String(ret);
    }

    public static Bech32Parts decode(NetworkParameters params, String bech) throws AddressFormatException {
        byte[] buffer;
        for (byte b : buffer = bech.getBytes()) {
            if (b >= 33 && b <= 126) continue;
            throw new AddressFormatException("bech32 characters out of range");
        }
        if (!bech.equals(bech.toLowerCase(Locale.ROOT)) && !bech.equals(bech.toUpperCase(Locale.ROOT))) {
            throw new AddressFormatException("bech32 cannot mix upper and lower case");
        }
        int pos = (bech = bech.toLowerCase()).lastIndexOf(params.getBech32AddressSeparator());
        if (pos < 1) {
            throw new AddressFormatException("bech32 missing separator");
        }
        if (pos + 7 > bech.length()) {
            throw new AddressFormatException("bech32 separator misplaced");
        }
        if (bech.length() < 8) {
            throw new AddressFormatException("bech32 input too short");
        }
        if (bech.length() > 90) {
            throw new AddressFormatException("bech32 input too long");
        }
        String s = bech.substring(pos + 1);
        for (int i = 0; i < s.length(); ++i) {
            if (CHARSET.indexOf(s.charAt(i)) != -1) continue;
            throw new AddressFormatException("bech32 characters  out of range");
        }
        byte[] prefix = bech.substring(0, pos).getBytes();
        byte[] data = new byte[bech.length() - pos - 1];
        int j = 0;
        int i = pos + 1;
        while (i < bech.length()) {
            data[j] = (byte)CHARSET.indexOf(bech.charAt(i));
            ++i;
            ++j;
        }
        if (!Bech32.verifyChecksum(prefix, data)) {
            throw new AddressFormatException("invalid bech32 checksum");
        }
        byte[] ret = new byte[data.length - 6];
        System.arraycopy(data, 0, ret, 0, data.length - 6);
        return new Bech32Parts(new String(prefix), ret);
    }

    private static int polymod(byte[] values) {
        int[] GENERATORS = new int[]{996825010, 642813549, 513874426, 1027748829, 705979059};
        int chk = 1;
        for (byte b : values) {
            byte top = (byte)(chk >> 25);
            chk = b ^ (chk & 33554431) << 5;
            for (int i = 0; i < 5; ++i) {
                chk ^= (top >> i & 1) == 1 ? GENERATORS[i] : 0;
            }
        }
        return chk;
    }

    private static byte[] hrpExpand(byte[] hrp) {
        int i;
        byte[] buf1 = new byte[hrp.length];
        byte[] buf2 = new byte[hrp.length];
        byte[] mid = new byte[1];
        for (i = 0; i < hrp.length; ++i) {
            buf1[i] = (byte)(hrp[i] >> 5);
        }
        mid[0] = 0;
        for (i = 0; i < hrp.length; ++i) {
            buf2[i] = (byte)(hrp[i] & 31);
        }
        byte[] ret = new byte[hrp.length * 2 + 1];
        System.arraycopy(buf1, 0, ret, 0, buf1.length);
        System.arraycopy(mid, 0, ret, buf1.length, mid.length);
        System.arraycopy(buf2, 0, ret, buf1.length + mid.length, buf2.length);
        return ret;
    }

    private static boolean verifyChecksum(byte[] hrp, byte[] data) {
        byte[] exp = Bech32.hrpExpand(hrp);
        byte[] values = new byte[exp.length + data.length];
        System.arraycopy(exp, 0, values, 0, exp.length);
        System.arraycopy(data, 0, values, exp.length, data.length);
        return 1 == Bech32.polymod(values);
    }

    private static byte[] createChecksum(byte[] hrp, byte[] data) {
        byte[] zeroes = new byte[]{0, 0, 0, 0, 0, 0};
        byte[] expanded = Bech32.hrpExpand(hrp);
        byte[] values = new byte[zeroes.length + expanded.length + data.length];
        System.arraycopy(expanded, 0, values, 0, expanded.length);
        System.arraycopy(data, 0, values, expanded.length, data.length);
        System.arraycopy(zeroes, 0, values, expanded.length + data.length, zeroes.length);
        int polymod = Bech32.polymod(values) ^ 1;
        byte[] ret = new byte[6];
        for (int i = 0; i < ret.length; ++i) {
            ret[i] = (byte)(polymod >> 5 * (5 - i) & 31);
        }
        return ret;
    }

    public static class Bech32Parts {
        private String prefix;
        private byte[] data;

        public Bech32Parts(String prefix, byte[] data) {
            this.prefix = prefix;
            this.data = data;
        }

        public String getPrefix() {
            return this.prefix;
        }

        public byte[] getData() {
            return this.data;
        }
    }

}

