/*
 * Decompiled with CFR 0_132.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Preconditions
 *  javax.annotation.Nullable
 */
package org.bitcoinj.core;

import com.google.common.base.Preconditions;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import javax.annotation.Nullable;
import org.bitcoinj.core.AddressFormatException;
import org.bitcoinj.core.CashAddress;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.core.VersionedChecksummedBytes;
import org.bitcoinj.core.WrongNetworkException;
import org.bitcoinj.params.Networks;
import org.bitcoinj.script.Script;

public class Address
extends VersionedChecksummedBytes {
    public static final int LENGTH = 20;
    private transient NetworkParameters params;

    public Address(NetworkParameters params, int version, byte[] hash160) throws WrongNetworkException {
        super(version, hash160);
        Preconditions.checkNotNull((Object)params);
        Preconditions.checkArgument((boolean)(hash160.length == 20), (Object)"Addresses are 160-bit hashes, so you must provide 20 bytes");
        if (!Address.isAcceptableVersion(params, version)) {
            throw new WrongNetworkException(version, params.getAcceptableAddressCodes());
        }
        this.params = params;
    }

    public static Address fromP2SHHash(NetworkParameters params, byte[] hash160) {
        try {
            return new Address(params, params.getP2SHHeader(), hash160);
        }
        catch (WrongNetworkException e) {
            throw new RuntimeException(e);
        }
    }

    public static Address fromP2SHScript(NetworkParameters params, Script scriptPubKey) {
        Preconditions.checkArgument((boolean)scriptPubKey.isPayToScriptHash(), (Object)"Not a P2SH script");
        return Address.fromP2SHHash(params, scriptPubKey.getPubKeyHash());
    }

    public static Address fromBase58(@Nullable NetworkParameters params, String base58) throws AddressFormatException {
        return new Address(params, base58);
    }

    public Address(NetworkParameters params, byte[] hash160) {
        super(params.getAddressHeader(), hash160);
        Preconditions.checkArgument((boolean)(hash160.length == 20), (Object)"Addresses are 160-bit hashes, so you must provide 20 bytes");
        this.params = params;
    }

    @Deprecated
    public Address(@Nullable NetworkParameters params, String address) throws AddressFormatException {
        super(address);
        if (params != null) {
            if (!Address.isAcceptableVersion(params, this.version)) {
                throw new WrongNetworkException(this.version, params.getAcceptableAddressCodes());
            }
            this.params = params;
        } else {
            NetworkParameters paramsFound = null;
            for (NetworkParameters p : Networks.get()) {
                if (!Address.isAcceptableVersion(p, this.version)) continue;
                paramsFound = p;
                break;
            }
            if (paramsFound == null) {
                throw new AddressFormatException("No network found for " + address);
            }
            this.params = paramsFound;
        }
    }

    public byte[] getHash160() {
        return this.bytes;
    }

    public boolean isP2SHAddress() {
        NetworkParameters parameters = this.getParameters();
        return parameters != null && this.version == parameters.p2shHeader;
    }

    public NetworkParameters getParameters() {
        return this.params;
    }

    public static NetworkParameters getParametersFromAddress(String address) throws AddressFormatException {
        try {
            return Address.fromBase58(null, address).getParameters();
        }
        catch (WrongNetworkException e) {
            throw new RuntimeException(e);
        }
    }

    private static boolean isAcceptableVersion(NetworkParameters params, int version) {
        for (int v : params.getAcceptableAddressCodes()) {
            if (version != v) continue;
            return true;
        }
        return false;
    }

    @Override
    public Address clone() throws CloneNotSupportedException {
        return (Address)super.clone();
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
        out.writeUTF(this.params.id);
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        this.params = NetworkParameters.fromID(in.readUTF());
    }

    public String toCashAddress() {
        if (this.isP2SHAddress()) {
            return CashAddress.encode(this.params.bech32Prefix, "scripthash", this.bytes);
        }
        return CashAddress.encode(this.params.bech32Prefix, "pubkeyhash", this.bytes);
    }
}

