/*
 * Decompiled with CFR 0_132.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Preconditions
 *  com.google.common.io.BaseEncoding
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package org.bitcoinj.core;

import com.google.common.base.Preconditions;
import com.google.common.io.BaseEncoding;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.util.Arrays;
import org.bitcoinj.core.DummySerializer;
import org.bitcoinj.core.MessageSerializer;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.core.ProtocolException;
import org.bitcoinj.core.Sha256Hash;
import org.bitcoinj.core.UnsafeByteArrayOutputStream;
import org.bitcoinj.core.Utils;
import org.bitcoinj.core.VarInt;
import org.bitcoinj.core.VersionMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class Message {
    private static final Logger log = LoggerFactory.getLogger(Message.class);
    public static final int MAX_SIZE = 33554432;
    public static final int UNKNOWN_LENGTH = Integer.MIN_VALUE;
    private static final boolean SELF_CHECK = false;
    protected int offset;
    protected int cursor;
    protected int length = Integer.MIN_VALUE;
    protected byte[] payload;
    protected boolean recached = false;
    protected MessageSerializer serializer;
    protected int protocolVersion;
    protected NetworkParameters params;

    protected Message() {
        this.serializer = DummySerializer.DEFAULT;
    }

    protected Message(NetworkParameters params) {
        this.params = params;
        this.serializer = params.getDefaultSerializer();
    }

    protected Message(NetworkParameters params, byte[] payload, int offset, int protocolVersion) throws ProtocolException {
        this(params, payload, offset, protocolVersion, params.getDefaultSerializer(), Integer.MIN_VALUE);
    }

    protected Message(NetworkParameters params, byte[] payload, int offset, int protocolVersion, MessageSerializer serializer, int length) throws ProtocolException {
        this.serializer = serializer;
        this.protocolVersion = protocolVersion;
        this.params = params;
        this.payload = payload;
        this.cursor = this.offset = offset;
        this.length = length;
        this.parse();
        if (this.length == Integer.MIN_VALUE) {
            Preconditions.checkState((boolean)false, (String)"Length field has not been set in constructor for %s after parse.", (Object)this.getClass().getSimpleName());
        }
        if (!serializer.isParseRetainMode()) {
            this.payload = null;
        }
    }

    private void selfCheck(byte[] payload, int offset) {
        if (!(this instanceof VersionMessage)) {
            byte[] payloadBytes = new byte[this.cursor - offset];
            System.arraycopy(payload, offset, payloadBytes, 0, this.cursor - offset);
            byte[] reserialized = this.bitcoinSerialize();
            if (!Arrays.equals(reserialized, payloadBytes)) {
                throw new RuntimeException("Serialization is wrong: \n" + Utils.HEX.encode(reserialized) + " vs \n" + Utils.HEX.encode(payloadBytes));
            }
        }
    }

    protected Message(NetworkParameters params, byte[] payload, int offset) throws ProtocolException {
        this(params, payload, offset, params.getProtocolVersionNum(NetworkParameters.ProtocolVersion.CURRENT), params.getDefaultSerializer(), Integer.MIN_VALUE);
    }

    protected Message(NetworkParameters params, byte[] payload, int offset, MessageSerializer serializer, int length) throws ProtocolException {
        this(params, payload, offset, params.getProtocolVersionNum(NetworkParameters.ProtocolVersion.CURRENT), serializer, length);
    }

    protected abstract void parse() throws ProtocolException;

    protected void unCache() {
        this.payload = null;
        this.recached = false;
    }

    protected void adjustLength(int newArraySize, int adjustment) {
        if (this.length == Integer.MIN_VALUE) {
            return;
        }
        if (adjustment == Integer.MIN_VALUE) {
            this.length = Integer.MIN_VALUE;
            return;
        }
        this.length += adjustment;
        if (newArraySize == 1) {
            ++this.length;
        } else if (newArraySize != 0) {
            this.length += VarInt.sizeOf(newArraySize) - VarInt.sizeOf(newArraySize - 1);
        }
    }

    public boolean isCached() {
        return this.payload != null;
    }

    public boolean isRecached() {
        return this.recached;
    }

    public byte[] bitcoinSerialize() {
        byte[] bytes = this.unsafeBitcoinSerialize();
        byte[] copy = new byte[bytes.length];
        System.arraycopy(bytes, 0, copy, 0, bytes.length);
        return copy;
    }

    public byte[] unsafeBitcoinSerialize() {
        if (this.payload != null) {
            if (this.offset == 0 && this.length == this.payload.length) {
                return this.payload;
            }
            byte[] buf = new byte[this.length];
            System.arraycopy(this.payload, this.offset, buf, 0, this.length);
            return buf;
        }
        UnsafeByteArrayOutputStream stream = new UnsafeByteArrayOutputStream(this.length < 32 ? 32 : this.length + 32);
        try {
            this.bitcoinSerializeToStream(stream);
        }
        catch (IOException iOException) {
            // empty catch block
        }
        if (this.serializer.isParseRetainMode()) {
            this.payload = stream.toByteArray();
            this.cursor -= this.offset;
            this.offset = 0;
            this.recached = true;
            this.length = this.payload.length;
            return this.payload;
        }
        byte[] buf = stream.toByteArray();
        this.length = buf.length;
        return buf;
    }

    public final void bitcoinSerialize(OutputStream stream) throws IOException {
        if (this.payload != null && this.length != Integer.MIN_VALUE) {
            stream.write(this.payload, this.offset, this.length);
            return;
        }
        this.bitcoinSerializeToStream(stream);
    }

    protected void bitcoinSerializeToStream(OutputStream stream) throws IOException {
        log.error("Error: {} class has not implemented bitcoinSerializeToStream method.  Generating message with no payload", this.getClass());
    }

    public Sha256Hash getHash() {
        throw new UnsupportedOperationException();
    }

    public final int getMessageSize() {
        if (this.length == Integer.MIN_VALUE) {
            Preconditions.checkState((boolean)false, (String)"Length field has not been set in %s.", (Object)this.getClass().getSimpleName());
        }
        return this.length;
    }

    protected long readUint32() throws ProtocolException {
        try {
            long u = Utils.readUint32(this.payload, this.cursor);
            this.cursor += 4;
            return u;
        }
        catch (ArrayIndexOutOfBoundsException e) {
            throw new ProtocolException(e);
        }
    }

    protected long readInt64() throws ProtocolException {
        try {
            long u = Utils.readInt64(this.payload, this.cursor);
            this.cursor += 8;
            return u;
        }
        catch (ArrayIndexOutOfBoundsException e) {
            throw new ProtocolException(e);
        }
    }

    protected BigInteger readUint64() throws ProtocolException {
        return new BigInteger(Utils.reverseBytes(this.readBytes(8)));
    }

    protected long readVarInt() throws ProtocolException {
        return this.readVarInt(0);
    }

    protected long readVarInt(int offset) throws ProtocolException {
        try {
            VarInt varint = new VarInt(this.payload, this.cursor + offset);
            this.cursor += offset + varint.getOriginalSizeInBytes();
            return varint.value;
        }
        catch (ArrayIndexOutOfBoundsException e) {
            throw new ProtocolException(e);
        }
    }

    protected byte[] readBytes(int length) throws ProtocolException {
        if (length > 33554432) {
            throw new ProtocolException("Claimed value length too large: " + length);
        }
        try {
            byte[] b = new byte[length];
            System.arraycopy(this.payload, this.cursor, b, 0, length);
            this.cursor += length;
            return b;
        }
        catch (IndexOutOfBoundsException e) {
            throw new ProtocolException(e);
        }
    }

    protected byte[] readByteArray() throws ProtocolException {
        long len = this.readVarInt();
        return this.readBytes((int)len);
    }

    protected String readStr() throws ProtocolException {
        long length = this.readVarInt();
        return length == 0L ? "" : Utils.toString(this.readBytes((int)length), "UTF-8");
    }

    protected Sha256Hash readHash() throws ProtocolException {
        return Sha256Hash.wrapReversed(this.readBytes(32));
    }

    protected boolean hasMoreBytes() {
        return this.cursor < this.payload.length;
    }

    public NetworkParameters getParams() {
        return this.params;
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        if (null != this.params) {
            this.serializer = this.params.getDefaultSerializer();
        }
    }
}

