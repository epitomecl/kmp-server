/*
 * Decompiled with CFR 0_132.
 */
package org.bitcoinj.core;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import org.bitcoinj.core.AddressFormatException;
import org.bitcoinj.core.Bech32;
import org.bitcoinj.core.NetworkParameters;

public class SegwitAddress {
    public static SegwitAddressParts decode(NetworkParameters params, String addr) {
        String hrp = params.getBech32AddressPrefix();
        Bech32.Bech32Parts p = Bech32.decode(params, addr);
        String hrpgotStr = p.getPrefix();
        if (hrpgotStr == null) {
            throw new AddressFormatException("invalid segwit human readable part");
        }
        if (!hrpgotStr.equalsIgnoreCase(hrp)) {
            throw new AddressFormatException("invalid segwit human readable part");
        }
        byte[] data = p.getData();
        ArrayList<Byte> progBytes = new ArrayList<Byte>();
        for (int i = 1; i < data.length; ++i) {
            progBytes.add(data[i]);
        }
        byte[] decoded = SegwitAddress.convertBits(progBytes, 5, 8, false);
        if (decoded.length < 2 || decoded.length > 40) {
            throw new AddressFormatException("invalid decoded data length");
        }
        byte witnessVersion = data[0];
        if (witnessVersion > 16) {
            throw new AddressFormatException("invalid decoded witness version");
        }
        if (witnessVersion == 0 && decoded.length != 20 && decoded.length != 32) {
            throw new AddressFormatException("decoded witness version 0 with unknown length");
        }
        return new SegwitAddressParts(witnessVersion, decoded);
    }

    public static String encode(NetworkParameters params, byte witnessVersion, byte[] witnessProgram) {
        ArrayList<Byte> progBytes = new ArrayList<Byte>();
        for (int i = 0; i < witnessProgram.length; ++i) {
            progBytes.add(witnessProgram[i]);
        }
        byte[] prog = SegwitAddress.convertBits(progBytes, 8, 5, true);
        byte[] data = new byte[1 + prog.length];
        System.arraycopy(new byte[]{witnessVersion}, 0, data, 0, 1);
        System.arraycopy(prog, 0, data, 1, prog.length);
        return Bech32.encode(params, data);
    }

    public static byte[] convertBits(List<Byte> data, int fromBits, int toBits, boolean pad) {
        int acc = 0;
        int bits = 0;
        int maxv = (1 << toBits) - 1;
        ArrayList<Byte> ret = new ArrayList<Byte>();
        for (Byte value : data) {
            short b = (short)(value & 255);
            if (b < 0) {
                throw new AddressFormatException();
            }
            if (b >> fromBits > 0) {
                throw new AddressFormatException();
            }
            acc = acc << fromBits | b;
            bits += fromBits;
            while (bits >= toBits) {
                ret.add((byte)(acc >> (bits -= toBits) & maxv));
            }
        }
        if (pad && bits > 0) {
            ret.add((byte)(acc << toBits - bits & maxv));
        } else if (bits >= fromBits || (byte)(acc << toBits - bits & maxv) != 0) {
            return null;
        }
        byte[] buf = new byte[ret.size()];
        for (int i = 0; i < ret.size(); ++i) {
            buf[i] = (Byte)ret.get(i);
        }
        return buf;
    }

    public static byte[] convertBits(byte[] data, int fromBits, int toBits, boolean pad) {
        int acc = 0;
        int bits = 0;
        int maxv = (1 << toBits) - 1;
        ArrayList<Byte> ret = new ArrayList<Byte>();
        for (byte value : data) {
            short b = (short)(value & 255);
            System.out.println("b: " + b);
            if (b < 0) {
                throw new AddressFormatException();
            }
            if (b >> fromBits > 0) {
                throw new AddressFormatException();
            }
            acc = acc << fromBits | b;
            bits += fromBits;
            while (bits >= toBits) {
                ret.add((byte)(acc >> (bits -= toBits) & maxv));
            }
        }
        if (pad && bits > 0) {
            ret.add((byte)(acc << toBits - bits & maxv));
        } else if (bits >= fromBits || (byte)(acc << toBits - bits & maxv) != 0) {
            System.out.println("fok");
            return null;
        }
        byte[] buf = new byte[ret.size()];
        for (int i = 0; i < ret.size(); ++i) {
            buf[i] = (Byte)ret.get(i);
        }
        return buf;
    }

    public static byte[] getScriptPubkey(byte witver, byte[] witprog) {
        byte v = witver > 0 ? (byte)(witver + 80) : (byte)0;
        byte[] ver = new byte[]{v, (byte)witprog.length};
        byte[] ret = new byte[witprog.length + ver.length];
        System.arraycopy(ver, 0, ret, 0, ver.length);
        System.arraycopy(witprog, 0, ret, ver.length, witprog.length);
        return ret;
    }

    public static boolean isValidVersion(byte version) {
        return version >= 0 && version <= 16;
    }

    public static class SegwitAddressParts {
        private byte version;
        private byte[] program;

        public SegwitAddressParts(byte version, byte[] program) {
            this.version = version;
            this.program = program;
        }

        public byte getVersion() {
            return this.version;
        }

        public byte[] getProgram() {
            return this.program;
        }
    }

}

