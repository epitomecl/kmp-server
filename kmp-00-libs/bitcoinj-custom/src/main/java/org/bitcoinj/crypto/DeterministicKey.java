/*
 * Decompiled with CFR 0_132.
 * 
 * Could not load the following classes:
 *  com.google.common.base.MoreObjects
 *  com.google.common.base.MoreObjects$ToStringHelper
 *  com.google.common.base.Objects
 *  com.google.common.base.Preconditions
 *  com.google.common.collect.ImmutableList
 *  com.google.common.io.BaseEncoding
 *  javax.annotation.Nullable
 *  org.spongycastle.crypto.params.ECDomainParameters
 *  org.spongycastle.math.ec.ECCurve
 *  org.spongycastle.math.ec.ECPoint
 */
package org.bitcoinj.crypto;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.io.BaseEncoding;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Comparator;
import javax.annotation.Nullable;
import org.bitcoinj.core.Address;
import org.bitcoinj.core.Base58;
import org.bitcoinj.core.ECKey;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.core.Sha256Hash;
import org.bitcoinj.core.Utils;
import org.bitcoinj.crypto.ChildNumber;
import org.bitcoinj.crypto.HDKeyDerivation;
import org.bitcoinj.crypto.HDUtils;
import org.bitcoinj.crypto.KeyCrypterException;
import org.bitcoinj.crypto.LazyECPoint;
import org.spongycastle.crypto.params.ECDomainParameters;
import org.spongycastle.math.ec.ECCurve;
import org.spongycastle.math.ec.ECPoint;

public class DeterministicKey
extends ECKey {
    public static final Comparator<ECKey> CHILDNUM_ORDER = new Comparator<ECKey>(){

        @Override
        public int compare(ECKey k1, ECKey k2) {
            ChildNumber cn1 = ((DeterministicKey)k1).getChildNumber();
            ChildNumber cn2 = ((DeterministicKey)k2).getChildNumber();
            return cn1.compareTo(cn2);
        }
    };
    private final DeterministicKey parent;
    private final ImmutableList<ChildNumber> childNumberPath;
    private final int depth;
    private int parentFingerprint;
    private final byte[] chainCode;

    public DeterministicKey(ImmutableList<ChildNumber> childNumberPath, byte[] chainCode, LazyECPoint publicAsPoint, @Nullable BigInteger priv, @Nullable DeterministicKey parent) {
        super(priv, DeterministicKey.compressPoint((LazyECPoint)Preconditions.checkNotNull((Object)publicAsPoint)));
        Preconditions.checkArgument((boolean)(chainCode.length == 32));
        this.parent = parent;
        this.childNumberPath = (ImmutableList)Preconditions.checkNotNull(childNumberPath);
        this.chainCode = Arrays.copyOf(chainCode, chainCode.length);
        this.depth = parent == null ? 0 : parent.depth + 1;
        this.parentFingerprint = parent != null ? parent.getFingerprint() : 0;
    }

    public DeterministicKey(ImmutableList<ChildNumber> childNumberPath, byte[] chainCode, ECPoint publicAsPoint, @Nullable BigInteger priv, @Nullable DeterministicKey parent) {
        this(childNumberPath, chainCode, new LazyECPoint(publicAsPoint), priv, parent);
    }

    public DeterministicKey(ImmutableList<ChildNumber> childNumberPath, byte[] chainCode, BigInteger priv, @Nullable DeterministicKey parent) {
        super(priv, DeterministicKey.compressPoint(ECKey.publicPointFromPrivate(priv)));
        Preconditions.checkArgument((boolean)(chainCode.length == 32));
        this.parent = parent;
        this.childNumberPath = (ImmutableList)Preconditions.checkNotNull(childNumberPath);
        this.chainCode = Arrays.copyOf(chainCode, chainCode.length);
        this.depth = parent == null ? 0 : parent.depth + 1;
        this.parentFingerprint = parent != null ? parent.getFingerprint() : 0;
    }

    private int ascertainParentFingerprint(DeterministicKey parentKey, int parentFingerprint) throws IllegalArgumentException {
        if (parentFingerprint != 0) {
            if (this.parent != null) {
                Preconditions.checkArgument((boolean)(this.parent.getFingerprint() == parentFingerprint), (String)"parent fingerprint mismatch", (Object)Integer.toHexString(this.parent.getFingerprint()), (Object)Integer.toHexString(parentFingerprint));
            }
            return parentFingerprint;
        }
        return 0;
    }

    public DeterministicKey(ImmutableList<ChildNumber> childNumberPath, byte[] chainCode, LazyECPoint publicAsPoint, @Nullable DeterministicKey parent, int depth, int parentFingerprint) {
        super(null, DeterministicKey.compressPoint((LazyECPoint)Preconditions.checkNotNull((Object)publicAsPoint)));
        Preconditions.checkArgument((boolean)(chainCode.length == 32));
        this.parent = parent;
        this.childNumberPath = (ImmutableList)Preconditions.checkNotNull(childNumberPath);
        this.chainCode = Arrays.copyOf(chainCode, chainCode.length);
        this.depth = depth;
        this.parentFingerprint = this.ascertainParentFingerprint(parent, parentFingerprint);
    }

    public DeterministicKey(ImmutableList<ChildNumber> childNumberPath, byte[] chainCode, BigInteger priv, @Nullable DeterministicKey parent, int depth, int parentFingerprint) {
        super(priv, DeterministicKey.compressPoint(ECKey.publicPointFromPrivate(priv)));
        Preconditions.checkArgument((boolean)(chainCode.length == 32));
        this.parent = parent;
        this.childNumberPath = (ImmutableList)Preconditions.checkNotNull(childNumberPath);
        this.chainCode = Arrays.copyOf(chainCode, chainCode.length);
        this.depth = depth;
        this.parentFingerprint = this.ascertainParentFingerprint(parent, parentFingerprint);
    }

    public DeterministicKey(DeterministicKey keyToClone, DeterministicKey newParent) {
        super(keyToClone.priv, keyToClone.pub.get());
        this.parent = newParent;
        this.childNumberPath = keyToClone.childNumberPath;
        this.chainCode = keyToClone.chainCode;
        this.depth = this.childNumberPath.size();
        this.parentFingerprint = this.parent.getFingerprint();
    }

    public ImmutableList<ChildNumber> getPath() {
        return this.childNumberPath;
    }

    public String getPathAsString() {
        return HDUtils.formatPath(this.getPath());
    }

    public int getDepth() {
        return this.depth;
    }

    public ChildNumber getChildNumber() {
        return this.childNumberPath.size() == 0 ? ChildNumber.ZERO : (ChildNumber)this.childNumberPath.get(this.childNumberPath.size() - 1);
    }

    public byte[] getChainCode() {
        return this.chainCode;
    }

    public byte[] getIdentifier() {
        return Utils.sha256hash160(this.getPubKey());
    }

    public int getFingerprint() {
        return ByteBuffer.wrap(Arrays.copyOfRange(this.getIdentifier(), 0, 4)).getInt();
    }

    @Nullable
    public DeterministicKey getParent() {
        return this.parent;
    }

    public int getParentFingerprint() {
        return this.parentFingerprint;
    }

    public byte[] getPrivKeyBytes33() {
        byte[] bytes33 = new byte[33];
        byte[] priv = this.getPrivKeyBytes();
        System.arraycopy(priv, 0, bytes33, 33 - priv.length, priv.length);
        return bytes33;
    }

    public DeterministicKey dropPrivateBytes() {
        if (this.isPubKeyOnly()) {
            return this;
        }
        return new DeterministicKey(this.getPath(), this.getChainCode(), this.pub, null, this.parent);
    }

    public DeterministicKey dropParent() {
        DeterministicKey key = new DeterministicKey(this.getPath(), this.getChainCode(), this.pub, this.priv, null);
        key.parentFingerprint = this.parentFingerprint;
        return key;
    }

    static byte[] addChecksum(byte[] input) {
        int inputLength = input.length;
        byte[] checksummed = new byte[inputLength + 4];
        System.arraycopy(input, 0, checksummed, 0, inputLength);
        byte[] checksum = Sha256Hash.hashTwice(input);
        System.arraycopy(checksum, 0, checksummed, inputLength, 4);
        return checksummed;
    }

    @Override
    public boolean isPubKeyOnly() {
        return super.isPubKeyOnly() && (this.parent == null || this.parent.isPubKeyOnly());
    }

    @Override
    public boolean hasPrivKey() {
        return this.findParentWithPrivKey() != null;
    }

    private DeterministicKey findParentWithPrivKey() {
        DeterministicKey cursor = this;
        while (cursor != null && cursor.priv == null) {
            cursor = cursor.parent;
        }
        return cursor;
    }

    @Nullable
    private BigInteger findOrDerivePrivateKey() {
        DeterministicKey cursor = this.findParentWithPrivKey();
        if (cursor == null) {
            return null;
        }
        return this.derivePrivateKeyDownwards(cursor, cursor.priv.toByteArray());
    }

    private BigInteger derivePrivateKeyDownwards(DeterministicKey cursor, byte[] parentalPrivateKeyBytes) {
        DeterministicKey downCursor = new DeterministicKey(cursor.childNumberPath, cursor.chainCode, cursor.pub, new BigInteger(1, parentalPrivateKeyBytes), cursor.parent);
        ImmutableList path = this.childNumberPath.subList(cursor.getPath().size(), this.childNumberPath.size());
        for (ChildNumber num : path) {
            downCursor = HDKeyDerivation.deriveChildKey(downCursor, num);
        }
        if (!downCursor.pub.equals(this.pub)) {
            throw new KeyCrypterException("Could not decrypt bytes");
        }
        return (BigInteger)Preconditions.checkNotNull((Object)downCursor.priv);
    }

    public DeterministicKey derive(int child) {
        return HDKeyDerivation.deriveChildKey(this, new ChildNumber(child, true));
    }

    @Override
    public BigInteger getPrivKey() {
        BigInteger key = this.findOrDerivePrivateKey();
        Preconditions.checkState((boolean)(key != null), (Object)"Private key bytes not available");
        return key;
    }

    public byte[] serializePublic(NetworkParameters params) {
        return this.serialize(params, true);
    }

    public byte[] serializePrivate(NetworkParameters params) {
        return this.serialize(params, false);
    }

    private byte[] serialize(NetworkParameters params, boolean pub) {
        ByteBuffer ser = ByteBuffer.allocate(78);
        ser.putInt(pub ? params.getBip32HeaderPub() : params.getBip32HeaderPriv());
        ser.put((byte)this.getDepth());
        ser.putInt(this.getParentFingerprint());
        ser.putInt(this.getChildNumber().i());
        ser.put(this.getChainCode());
        ser.put(pub ? this.getPubKey() : this.getPrivKeyBytes33());
        Preconditions.checkState((boolean)(ser.position() == 78));
        return ser.array();
    }

    public String serializePubB58(NetworkParameters params) {
        return DeterministicKey.toBase58(this.serialize(params, true));
    }

    public String serializePrivB58(NetworkParameters params) {
        return DeterministicKey.toBase58(this.serialize(params, false));
    }

    static String toBase58(byte[] ser) {
        return Base58.encode(DeterministicKey.addChecksum(ser));
    }

    public static DeterministicKey deserializeB58(String base58, NetworkParameters params) {
        return DeterministicKey.deserializeB58(null, base58, params);
    }

    public static DeterministicKey deserializeB58(@Nullable DeterministicKey parent, String base58, NetworkParameters params) {
        return DeterministicKey.deserialize(params, Base58.decodeChecked(base58), parent);
    }

    public static DeterministicKey deserialize(NetworkParameters params, byte[] serializedKey) {
        return DeterministicKey.deserialize(params, serializedKey, null);
    }

    public static DeterministicKey deserialize(NetworkParameters params, byte[] serializedKey, @Nullable DeterministicKey parent) {
        Object path;
        ByteBuffer buffer = ByteBuffer.wrap(serializedKey);
        int header = buffer.getInt();
        if (header != params.getBip32HeaderPriv() && header != params.getBip32HeaderPub()) {
            throw new IllegalArgumentException("Unknown header bytes: " + DeterministicKey.toBase58(serializedKey).substring(0, 4));
        }
        boolean pub = header == params.getBip32HeaderPub();
        int depth = buffer.get() & 255;
        int parentFingerprint = buffer.getInt();
        int i = buffer.getInt();
        ChildNumber childNumber = new ChildNumber(i);
        if (parent != null) {
            if (parentFingerprint == 0) {
                throw new IllegalArgumentException("Parent was provided but this key doesn't have one");
            }
            if (parent.getFingerprint() != parentFingerprint) {
                throw new IllegalArgumentException("Parent fingerprints don't match");
            }
            path = HDUtils.append(parent.getPath(), childNumber);
            if (path.size() != depth) {
                throw new IllegalArgumentException("Depth does not match");
            }
        } else {
            path = depth >= 1 ? ImmutableList.of((Object)childNumber) : ImmutableList.of();
        }
        byte[] chainCode = new byte[32];
        buffer.get(chainCode);
        byte[] data = new byte[33];
        buffer.get(data);
        Preconditions.checkArgument((boolean)(!buffer.hasRemaining()), (Object)"Found unexpected data in key");
        if (pub) {
            return new DeterministicKey((ImmutableList<ChildNumber>)path, chainCode, new LazyECPoint(ECKey.CURVE.getCurve(), data), parent, depth, parentFingerprint);
        }
        return new DeterministicKey((ImmutableList<ChildNumber>)path, chainCode, new BigInteger(1, data), parent, depth, parentFingerprint);
    }

    @Override
    public void setCreationTimeSeconds(long newCreationTimeSeconds) {
        if (this.parent != null) {
            throw new IllegalStateException("Creation time can only be set on root keys.");
        }
        super.setCreationTimeSeconds(newCreationTimeSeconds);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        DeterministicKey other = (DeterministicKey)o;
        return super.equals(other) && Arrays.equals(this.chainCode, other.chainCode) && Objects.equal(this.childNumberPath, other.childNumberPath);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode((Object[])new Object[]{super.hashCode(), Arrays.hashCode(this.chainCode), this.childNumberPath});
    }

    @Override
    public String toString() {
        MoreObjects.ToStringHelper helper = MoreObjects.toStringHelper((Object)this).omitNullValues();
        helper.add("pub", (Object)Utils.HEX.encode(this.pub.getEncoded()));
        helper.add("chainCode", (Object)Utils.HEX.encode(this.chainCode));
        helper.add("path", (Object)this.getPathAsString());
        if (this.creationTimeSeconds > 0L) {
            helper.add("creationTimeSeconds", this.creationTimeSeconds);
        }
        helper.add("isPubKeyOnly", this.isPubKeyOnly());
        return helper.toString();
    }

    @Override
    public void formatKeyWithAddress(StringBuilder builder, NetworkParameters params) {
        Address address = this.toAddress(params);
        builder.append("  addr:").append(address);
        builder.append("  hash160:").append(Utils.HEX.encode(this.getPubKeyHash()));
        builder.append("  (").append(this.getPathAsString()).append(")\n");
    }

}

