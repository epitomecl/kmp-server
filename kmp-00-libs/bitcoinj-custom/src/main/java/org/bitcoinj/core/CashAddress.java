/*
 * Decompiled with CFR 0_132.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableBiMap
 *  com.google.common.collect.ImmutableBiMap$Builder
 *  org.spongycastle.util.encoders.Hex
 */
package org.bitcoinj.core;

import com.google.common.collect.ImmutableBiMap;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import org.bitcoinj.core.Address;
import org.bitcoinj.core.AddressFormatException;
import org.bitcoinj.core.Base32;
import org.bitcoinj.core.NetworkParameters;
import org.spongycastle.util.encoders.Hex;

public class CashAddress {
    public static final String P2SH = "scripthash";
    public static final String P2PKH = "pubkeyhash";
    private static final ImmutableBiMap<Integer, BigInteger> hashBitMap = new ImmutableBiMap.Builder().put((Object)160, (Object)BigInteger.valueOf(0L)).put((Object)192, (Object)BigInteger.valueOf(1L)).put((Object)224, (Object)BigInteger.valueOf(2L)).put((Object)256, (Object)BigInteger.valueOf(3L)).put((Object)320, (Object)BigInteger.valueOf(4L)).put((Object)384, (Object)BigInteger.valueOf(5L)).put((Object)448, (Object)BigInteger.valueOf(6L)).put((Object)512, (Object)BigInteger.valueOf(7L)).build();
    private static final ImmutableBiMap<String, BigInteger> versionBitMap = new ImmutableBiMap.Builder().put((Object)"pubkeyhash", (Object)BigInteger.valueOf(0L)).put((Object)"scripthash", (Object)BigInteger.valueOf(1L)).build();
    public String scriptType;
    public String prefix;
    public String hash;

    public CashAddress(String scriptType, String prefix, String hash) {
        this.scriptType = scriptType;
        this.prefix = prefix;
        this.hash = hash;
    }

    private static BigInteger createVersion(String scriptType, int hashLengthBits) {
        if ((scriptType.equals(P2PKH) || scriptType.equals(P2SH)) && hashLengthBits != 160) {
            throw new AddressFormatException("Invalid hash length for scriptType");
        }
        return ((BigInteger)versionBitMap.get((Object)scriptType)).shiftLeft(3).or((BigInteger)hashBitMap.get((Object)hashLengthBits));
    }

    private static byte[] encodePayload(String scriptType, byte[] hash) {
        int hashLength = hash.length;
        BigInteger version = CashAddress.createVersion(scriptType, hashLength * 8);
        byte[] payload = new byte[hash.length + 1];
        payload[0] = version.byteValue();
        System.arraycopy(hash, 0, payload, 1, payload.length - 1);
        return payload;
    }

    private static VersionPayload decodeVersion(BigInteger version) {
        BigInteger last = version.shiftRight(7);
        if (last.and(BigInteger.ONE).intValue() > 0 || last.compareTo(BigInteger.ZERO) == 1) {
            throw new AddressFormatException("Invalid version, most significant bit is reserved");
        }
        BigInteger versionValue = version.shiftRight(3).and(new BigInteger("0f", 16));
        String scriptType = (String)versionBitMap.inverse().get((Object)versionValue);
        if (scriptType == null) {
            throw new AddressFormatException("Invalid script type");
        }
        BigInteger sizeBit = version.and(new BigInteger("07", 16));
        Integer hashSize = (Integer)hashBitMap.inverse().get((Object)sizeBit);
        if ((scriptType.equals(P2PKH) || scriptType.equals(P2SH)) && hashSize != 160) {
            throw new AddressFormatException("Mismatch between script type and hash length");
        }
        return new VersionPayload(scriptType, hashSize);
    }

    public static String encode(String prefix, String scriptType, byte[] hash) {
        if (!versionBitMap.containsKey((Object)scriptType)) {
            throw new AddressFormatException("Unsupported script type");
        }
        byte[] words = Base32.toWords(CashAddress.encodePayload(scriptType, hash));
        return Base32.encode(prefix, words);
    }

    public static CashAddress decode(String address) {
        Base32 result = Base32.decode(address);
        byte[] data = Base32.fromWords(result.words);
        if (data.length < 1) {
            throw new AddressFormatException("Empty payload in address");
        }
        VersionPayload versionInfo = CashAddress.decodeVersion(BigInteger.valueOf(data[0]));
        if (1 + versionInfo.hashSize / 8 != data.length) {
            throw new AddressFormatException("Hash length does not match version");
        }
        byte[] hash = new byte[data.length - 1];
        System.arraycopy(data, 1, hash, 0, hash.length - 1);
        return new CashAddress(versionInfo.scriptType, result.prefix, Hex.toHexString((byte[])data));
    }

    public static String toLegacy(NetworkParameters networkParameters, String address) {
        CashAddress cashAddress = CashAddress.decode(address);
        int versionByte = cashAddress.scriptType == P2PKH ? networkParameters.addressHeader : networkParameters.p2shHeader;
        byte[] cashAddressBuffer = Hex.decode((String)cashAddress.hash);
        byte[] data = new byte[cashAddressBuffer.length - 1];
        System.arraycopy(cashAddressBuffer, 1, data, 0, cashAddressBuffer.length - 1);
        ByteBuffer bb = ByteBuffer.allocate(cashAddressBuffer.length);
        bb.put((byte)versionByte);
        bb.put(data);
        return new Address(networkParameters, versionByte, data).toBase58();
    }

    public static class VersionPayload {
        public String scriptType;
        public int hashSize;

        public VersionPayload(String scriptType, int hashSize) {
            this.scriptType = scriptType;
            this.hashSize = hashSize;
        }
    }

}

