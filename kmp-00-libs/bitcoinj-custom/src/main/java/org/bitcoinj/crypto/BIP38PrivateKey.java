/*
 * Decompiled with CFR 0_132.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Charsets
 *  com.google.common.base.Objects
 *  com.google.common.base.Preconditions
 *  com.google.common.primitives.Bytes
 *  com.lambdaworks.crypto.SCrypt
 *  org.spongycastle.crypto.params.ECDomainParameters
 */
package org.bitcoinj.crypto;

import com.google.common.base.Charsets;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.primitives.Bytes;
import com.lambdaworks.crypto.SCrypt;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.security.GeneralSecurityException;
import java.security.Key;
import java.text.Normalizer;
import java.util.Arrays;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import org.bitcoinj.core.Address;
import org.bitcoinj.core.AddressFormatException;
import org.bitcoinj.core.ECKey;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.core.Sha256Hash;
import org.bitcoinj.core.VersionedChecksummedBytes;
import org.bitcoinj.crypto.DRMWorkaround;
import org.spongycastle.crypto.params.ECDomainParameters;

public class BIP38PrivateKey
extends VersionedChecksummedBytes {
    public transient NetworkParameters params;
    public final boolean ecMultiply;
    public final boolean compressed;
    public final boolean hasLotAndSequence;
    public final byte[] addressHash;
    public final byte[] content;

    public static BIP38PrivateKey fromBase58(NetworkParameters params, String base58) throws AddressFormatException {
        return new BIP38PrivateKey(params, base58);
    }

    @Deprecated
    public BIP38PrivateKey(NetworkParameters params, String encoded) throws AddressFormatException {
        super(encoded);
        this.params = (NetworkParameters)Preconditions.checkNotNull((Object)params);
        if (this.version != 1) {
            throw new AddressFormatException("Mismatched version number: " + this.version);
        }
        if (this.bytes.length != 38) {
            throw new AddressFormatException("Wrong number of bytes, excluding version byte: " + this.bytes.length);
        }
        this.hasLotAndSequence = (this.bytes[1] & 4) != 0;
        boolean bl = this.compressed = (this.bytes[1] & 32) != 0;
        if ((this.bytes[1] & 1) != 0) {
            throw new AddressFormatException("Bit 0x01 reserved for future use.");
        }
        if ((this.bytes[1] & 2) != 0) {
            throw new AddressFormatException("Bit 0x02 reserved for future use.");
        }
        if ((this.bytes[1] & 8) != 0) {
            throw new AddressFormatException("Bit 0x08 reserved for future use.");
        }
        if ((this.bytes[1] & 16) != 0) {
            throw new AddressFormatException("Bit 0x10 reserved for future use.");
        }
        int byte0 = this.bytes[0] & 255;
        if (byte0 == 66) {
            if ((this.bytes[1] & 192) != 192) {
                throw new AddressFormatException("Bits 0x40 and 0x80 must be set for non-EC-multiplied keys.");
            }
            this.ecMultiply = false;
            if (this.hasLotAndSequence) {
                throw new AddressFormatException("Non-EC-multiplied keys cannot have lot/sequence.");
            }
        } else if (byte0 == 67) {
            if ((this.bytes[1] & 192) != 0) {
                throw new AddressFormatException("Bits 0x40 and 0x80 must be cleared for EC-multiplied keys.");
            }
            this.ecMultiply = true;
        } else {
            throw new AddressFormatException("Second byte must by 0x42 or 0x43.");
        }
        this.addressHash = Arrays.copyOfRange(this.bytes, 2, 6);
        this.content = Arrays.copyOfRange(this.bytes, 6, 38);
    }

    public ECKey decrypt(String passphrase) throws BadPassphraseException {
        String normalizedPassphrase = Normalizer.normalize(passphrase, Normalizer.Form.NFC);
        ECKey key = this.ecMultiply ? this.decryptEC(normalizedPassphrase) : this.decryptNoEC(normalizedPassphrase);
        Sha256Hash hash = Sha256Hash.twiceOf(key.toAddress(this.params).toString().getBytes(Charsets.US_ASCII));
        byte[] actualAddressHash = Arrays.copyOfRange(hash.getBytes(), 0, 4);
        if (!Arrays.equals(actualAddressHash, this.addressHash)) {
            throw new BadPassphraseException();
        }
        return key;
    }

    private ECKey decryptNoEC(String normalizedPassphrase) {
        try {
            byte[] derived = SCrypt.scrypt((byte[])normalizedPassphrase.getBytes(Charsets.UTF_8), (byte[])this.addressHash, (int)16384, (int)8, (int)8, (int)64);
            byte[] key = Arrays.copyOfRange(derived, 32, 64);
            SecretKeySpec keyspec = new SecretKeySpec(key, "AES");
            DRMWorkaround.maybeDisableExportControls();
            Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");
            cipher.init(2, keyspec);
            byte[] decrypted = cipher.doFinal(this.content, 0, 32);
            for (int i = 0; i < 32; ++i) {
                byte[] arrby = decrypted;
                int n = i;
                arrby[n] = (byte)(arrby[n] ^ derived[i]);
            }
            return ECKey.fromPrivate(decrypted, this.compressed);
        }
        catch (GeneralSecurityException x) {
            throw new RuntimeException(x);
        }
    }

    private ECKey decryptEC(String normalizedPassphrase) {
        try {
            byte[] ownerEntropy = Arrays.copyOfRange(this.content, 0, 8);
            byte[] ownerSalt = this.hasLotAndSequence ? Arrays.copyOfRange(ownerEntropy, 0, 4) : ownerEntropy;
            byte[] passFactorBytes = SCrypt.scrypt((byte[])normalizedPassphrase.getBytes(Charsets.UTF_8), (byte[])ownerSalt, (int)16384, (int)8, (int)8, (int)32);
            if (this.hasLotAndSequence) {
                byte[] hashBytes = Bytes.concat((byte[][])new byte[][]{passFactorBytes, ownerEntropy});
                Preconditions.checkState((boolean)(hashBytes.length == 40));
                passFactorBytes = Sha256Hash.hashTwice(hashBytes);
            }
            BigInteger passFactor = new BigInteger(1, passFactorBytes);
            ECKey k = ECKey.fromPrivate(passFactor, true);
            byte[] salt = Bytes.concat((byte[][])new byte[][]{this.addressHash, ownerEntropy});
            Preconditions.checkState((boolean)(salt.length == 12));
            byte[] derived = SCrypt.scrypt((byte[])k.getPubKey(), (byte[])salt, (int)1024, (int)1, (int)1, (int)64);
            byte[] aeskey = Arrays.copyOfRange(derived, 32, 64);
            SecretKeySpec keyspec = new SecretKeySpec(aeskey, "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");
            cipher.init(2, keyspec);
            byte[] encrypted2 = Arrays.copyOfRange(this.content, 16, 32);
            byte[] decrypted2 = cipher.doFinal(encrypted2);
            Preconditions.checkState((boolean)(decrypted2.length == 16));
            for (int i = 0; i < 16; ++i) {
                byte[] arrby = decrypted2;
                int n = i;
                arrby[n] = (byte)(arrby[n] ^ derived[i + 16]);
            }
            byte[] encrypted1 = Bytes.concat((byte[][])new byte[][]{Arrays.copyOfRange(this.content, 8, 16), Arrays.copyOfRange(decrypted2, 0, 8)});
            byte[] decrypted1 = cipher.doFinal(encrypted1);
            Preconditions.checkState((boolean)(decrypted1.length == 16));
            for (int i = 0; i < 16; ++i) {
                byte[] arrby = decrypted1;
                int n = i;
                arrby[n] = (byte)(arrby[n] ^ derived[i]);
            }
            byte[] seed = Bytes.concat((byte[][])new byte[][]{decrypted1, Arrays.copyOfRange(decrypted2, 8, 16)});
            Preconditions.checkState((boolean)(seed.length == 24));
            BigInteger seedFactor = new BigInteger(1, Sha256Hash.hashTwice(seed));
            Preconditions.checkState((boolean)(passFactor.signum() >= 0));
            Preconditions.checkState((boolean)(seedFactor.signum() >= 0));
            BigInteger priv = passFactor.multiply(seedFactor).mod(ECKey.CURVE.getN());
            return ECKey.fromPrivate(priv, this.compressed);
        }
        catch (GeneralSecurityException x) {
            throw new RuntimeException(x);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        BIP38PrivateKey other = (BIP38PrivateKey)o;
        return super.equals(other) && Objects.equal((Object)this.params, (Object)other.params);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode((Object[])new Object[]{super.hashCode(), this.params});
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
        out.writeUTF(this.params.getId());
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        this.params = (NetworkParameters)Preconditions.checkNotNull((Object)NetworkParameters.fromID(in.readUTF()));
    }

    public static final class BadPassphraseException
    extends Exception {
    }

}

