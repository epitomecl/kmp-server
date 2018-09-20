/*
 * Decompiled with CFR 0_132.
 */
package org.bitcoinj.core;

import java.math.BigInteger;
import java.util.Arrays;
import org.bitcoinj.core.AddressFormatException;
import org.bitcoinj.core.Sha256Hash;

public class Base58 {
    public static final char[] ALPHABET = "123456789ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnopqrstuvwxyz".toCharArray();
    private static final char ENCODED_ZERO = ALPHABET[0];
    private static final int[] INDEXES = new int[128];

    public static String encode(byte[] input) {
        int zeros;
        if (input.length == 0) {
            return "";
        }
        for (zeros = 0; zeros < input.length && input[zeros] == 0; ++zeros) {
        }
        input = Arrays.copyOf(input, input.length);
        char[] encoded = new char[input.length * 2];
        int outputStart = encoded.length;
        int inputStart = zeros;
        while (inputStart < input.length) {
            encoded[--outputStart] = ALPHABET[Base58.divmod(input, inputStart, 256, 58)];
            if (input[inputStart] != 0) continue;
            ++inputStart;
        }
        while (outputStart < encoded.length && encoded[outputStart] == ENCODED_ZERO) {
            ++outputStart;
        }
        while (--zeros >= 0) {
            encoded[--outputStart] = ENCODED_ZERO;
        }
        return new String(encoded, outputStart, encoded.length - outputStart);
    }

    public static byte[] decode(String input) throws AddressFormatException {
        int zeros;
        if (input.length() == 0) {
            return new byte[0];
        }
        byte[] input58 = new byte[input.length()];
        for (int i = 0; i < input.length(); ++i) {
            int digit;
            char c = input.charAt(i);
            int n = digit = c < '?' ? INDEXES[c] : -1;
            if (digit < 0) {
                throw new AddressFormatException("Illegal character " + c + " at position " + i);
            }
            input58[i] = (byte)digit;
        }
        for (zeros = 0; zeros < input58.length && input58[zeros] == 0; ++zeros) {
        }
        byte[] decoded = new byte[input.length()];
        int outputStart = decoded.length;
        int inputStart = zeros;
        while (inputStart < input58.length) {
            decoded[--outputStart] = Base58.divmod(input58, inputStart, 58, 256);
            if (input58[inputStart] != 0) continue;
            ++inputStart;
        }
        while (outputStart < decoded.length && decoded[outputStart] == 0) {
            ++outputStart;
        }
        return Arrays.copyOfRange(decoded, outputStart - zeros, decoded.length);
    }

    public static BigInteger decodeToBigInteger(String input) throws AddressFormatException {
        return new BigInteger(1, Base58.decode(input));
    }

    public static byte[] decodeChecked(String input) throws AddressFormatException {
        byte[] actualChecksum;
        byte[] decoded = Base58.decode(input);
        if (decoded.length < 4) {
            throw new AddressFormatException("Input too short");
        }
        byte[] data = Arrays.copyOfRange(decoded, 0, decoded.length - 4);
        byte[] checksum = Arrays.copyOfRange(decoded, decoded.length - 4, decoded.length);
        if (!Arrays.equals(checksum, actualChecksum = Arrays.copyOfRange(Sha256Hash.hashTwice(data), 0, 4))) {
            throw new AddressFormatException("Checksum does not validate");
        }
        return data;
    }

    private static byte divmod(byte[] number, int firstDigit, int base, int divisor) {
        int remainder = 0;
        for (int i = firstDigit; i < number.length; ++i) {
            int digit = number[i] & 255;
            int temp = remainder * base + digit;
            number[i] = (byte)(temp / divisor);
            remainder = temp % divisor;
        }
        return (byte)remainder;
    }

    static {
        Arrays.fill(INDEXES, -1);
        int i = 0;
        while (i < ALPHABET.length) {
            Base58.INDEXES[Base58.ALPHABET[i]] = i++;
        }
    }
}

