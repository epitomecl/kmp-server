/*
 * Decompiled with CFR 0_132.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Joiner
 *  com.google.common.base.Preconditions
 *  com.google.common.collect.Lists
 *  com.google.common.io.BaseEncoding
 *  javax.annotation.Nullable
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.spongycastle.crypto.digests.RIPEMD160Digest
 */
package org.bitcoinj.script;

import com.google.common.base.Joiner;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.io.BaseEncoding;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import javax.annotation.Nullable;
import org.bitcoinj.core.Address;
import org.bitcoinj.core.Coin;
import org.bitcoinj.core.ECKey;
import org.bitcoinj.core.MessageSerializer;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.core.ProtocolException;
import org.bitcoinj.core.Sha256Hash;
import org.bitcoinj.core.Transaction;
import org.bitcoinj.core.TransactionInput;
import org.bitcoinj.core.UnsafeByteArrayOutputStream;
import org.bitcoinj.core.Utils;
import org.bitcoinj.crypto.TransactionSignature;
import org.bitcoinj.script.ScriptBuilder;
import org.bitcoinj.script.ScriptChunk;
import org.bitcoinj.script.ScriptError;
import org.bitcoinj.script.ScriptException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongycastle.crypto.digests.RIPEMD160Digest;

public class Script {
    public static final EnumSet<VerifyFlag> ALL_VERIFY_FLAGS = EnumSet.allOf(VerifyFlag.class);
    private static final Logger log = LoggerFactory.getLogger(Script.class);
    public static final long MAX_SCRIPT_ELEMENT_SIZE = 520L;
    private static final int MAX_OPS_PER_SCRIPT = 201;
    private static final int MAX_STACK_SIZE = 1000;
    private static final int MAX_PUBKEYS_PER_MULTISIG = 20;
    private static final int MAX_SCRIPT_SIZE = 10000;
    public static final int SIG_SIZE = 75;
    public static final int MAX_P2SH_SIGOPS = 15;
    protected List<ScriptChunk> chunks;
    protected byte[] program;
    private long creationTimeSeconds;
    private static final ScriptChunk[] STANDARD_TRANSACTION_SCRIPT_CHUNKS = new ScriptChunk[]{new ScriptChunk(118, null, 0), new ScriptChunk(169, null, 1), new ScriptChunk(136, null, 23), new ScriptChunk(172, null, 24)};

    private Script() {
        this.chunks = Lists.newArrayList();
    }

    Script(List<ScriptChunk> chunks) {
        this.chunks = Collections.unmodifiableList(new ArrayList<ScriptChunk>(chunks));
        this.creationTimeSeconds = Utils.currentTimeSeconds();
    }

    public Script(byte[] programBytes) throws ScriptException {
        this.program = programBytes;
        this.parse(programBytes);
        this.creationTimeSeconds = 0L;
    }

    public Script(byte[] programBytes, long creationTimeSeconds) throws ScriptException {
        this.program = programBytes;
        this.parse(programBytes);
        this.creationTimeSeconds = creationTimeSeconds;
    }

    public long getCreationTimeSeconds() {
        return this.creationTimeSeconds;
    }

    public void setCreationTimeSeconds(long creationTimeSeconds) {
        this.creationTimeSeconds = creationTimeSeconds;
    }

    public String toString() {
        return Utils.SPACE_JOINER.join(this.chunks);
    }

    public byte[] getProgram() {
        try {
            if (this.program != null) {
                return Arrays.copyOf(this.program, this.program.length);
            }
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            for (ScriptChunk chunk : this.chunks) {
                chunk.write(bos);
            }
            this.program = bos.toByteArray();
            return this.program;
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<ScriptChunk> getChunks() {
        return Collections.unmodifiableList(this.chunks);
    }

    private void parse(byte[] program) throws ScriptException {
        this.chunks = new ArrayList<ScriptChunk>(5);
        ByteArrayInputStream bis = new ByteArrayInputStream(program);
        int initialSize = bis.available();
        while (bis.available() > 0) {
            ScriptChunk chunk;
            int startLocationInProgram = initialSize - bis.available();
            int opcode = bis.read();
            long dataToRead = -1L;
            if (opcode >= 0 && opcode < 76) {
                dataToRead = opcode;
            } else if (opcode == 76) {
                if (bis.available() < 1) {
                    throw new ScriptException(ScriptError.SCRIPT_ERR_UNKNOWN_ERROR, "Unexpected end of script");
                }
                dataToRead = bis.read();
            } else if (opcode == 77) {
                if (bis.available() < 2) {
                    throw new ScriptException(ScriptError.SCRIPT_ERR_UNKNOWN_ERROR, "Unexpected end of script");
                }
                dataToRead = bis.read() | bis.read() << 8;
            } else if (opcode == 78) {
                if (bis.available() < 4) {
                    throw new ScriptException(ScriptError.SCRIPT_ERR_UNKNOWN_ERROR, "Unexpected end of script");
                }
                dataToRead = (long)bis.read() | (long)bis.read() << 8 | (long)bis.read() << 16 | (long)bis.read() << 24;
            }
            if (dataToRead == -1L) {
                chunk = new ScriptChunk(opcode, null, startLocationInProgram);
            } else {
                if (dataToRead > (long)bis.available()) {
                    throw new ScriptException(ScriptError.SCRIPT_ERR_BAD_OPCODE, "Push of data element that is larger than remaining data");
                }
                //ScriptChunk[] data = new byte[(int)dataToRead];
                byte[] data = new byte[(int)dataToRead];
                Preconditions.checkState((boolean)(dataToRead == 0L || (long)bis.read((byte[])data, 0, (int)dataToRead) == dataToRead));
                chunk = new ScriptChunk(opcode, (byte[])data, startLocationInProgram);
            }
            for (ScriptChunk c : STANDARD_TRANSACTION_SCRIPT_CHUNKS) {
                if (!c.equals(chunk)) continue;
                chunk = c;
            }
            this.chunks.add(chunk);
        }
    }

    public boolean isSentToRawPubKey() {
        return this.chunks.size() == 2 && this.chunks.get(1).equalsOpCode(172) && !this.chunks.get(0).isOpCode() && this.chunks.get((int)0).data.length > 1;
    }

    public boolean isSentToAddress() {
        return this.chunks.size() == 5 && this.chunks.get(0).equalsOpCode(118) && this.chunks.get(1).equalsOpCode(169) && this.chunks.get((int)2).data.length == 20 && this.chunks.get(3).equalsOpCode(136) && this.chunks.get(4).equalsOpCode(172);
    }

    @Deprecated
    public boolean isSentToP2SH() {
        return this.isPayToScriptHash();
    }

    public byte[] getPubKeyHash() throws ScriptException {
        if (this.isSentToAddress()) {
            return this.chunks.get((int)2).data;
        }
        if (this.isPayToScriptHash()) {
            return this.chunks.get((int)1).data;
        }
        throw new ScriptException(ScriptError.SCRIPT_ERR_UNKNOWN_ERROR, "Script not in the standard scriptPubKey form");
    }

    public byte[] getPubKey() throws ScriptException {
        if (this.chunks.size() != 2) {
            throw new ScriptException(ScriptError.SCRIPT_ERR_UNKNOWN_ERROR, "Script not of right size, expecting 2 but got " + this.chunks.size());
        }
        ScriptChunk chunk0 = this.chunks.get(0);
        byte[] chunk0data = chunk0.data;
        ScriptChunk chunk1 = this.chunks.get(1);
        byte[] chunk1data = chunk1.data;
        if (chunk0data != null && chunk0data.length > 2 && chunk1data != null && chunk1data.length > 2) {
            return chunk1data;
        }
        if (chunk1.equalsOpCode(172) && chunk0data != null && chunk0data.length > 2) {
            return chunk0data;
        }
        throw new ScriptException(ScriptError.SCRIPT_ERR_UNKNOWN_ERROR, "Script did not match expected form: " + this);
    }

    public byte[] getCLTVPaymentChannelSenderPubKey() throws ScriptException {
        if (!this.isSentToCLTVPaymentChannel()) {
            throw new ScriptException(ScriptError.SCRIPT_ERR_UNKNOWN_ERROR, "Script not a standard CHECKLOCKTIMVERIFY transaction: " + this);
        }
        return this.chunks.get((int)8).data;
    }

    public byte[] getCLTVPaymentChannelRecipientPubKey() throws ScriptException {
        if (!this.isSentToCLTVPaymentChannel()) {
            throw new ScriptException(ScriptError.SCRIPT_ERR_UNKNOWN_ERROR, "Script not a standard CHECKLOCKTIMVERIFY transaction: " + this);
        }
        return this.chunks.get((int)1).data;
    }

    public BigInteger getCLTVPaymentChannelExpiry() {
        if (!this.isSentToCLTVPaymentChannel()) {
            throw new ScriptException(ScriptError.SCRIPT_ERR_UNKNOWN_ERROR, "Script not a standard CHECKLOCKTIMEVERIFY transaction: " + this);
        }
        return Script.castToBigInteger(this.chunks.get((int)4).data, 5, false);
    }

    @Deprecated
    public Address getFromAddress(NetworkParameters params) throws ScriptException {
        return new Address(params, Utils.sha256hash160(this.getPubKey()));
    }

    public Address getToAddress(NetworkParameters params) throws ScriptException {
        return this.getToAddress(params, false);
    }

    public Address getToAddress(NetworkParameters params, boolean forcePayToPubKey) throws ScriptException {
        if (this.isSentToAddress()) {
            return new Address(params, this.getPubKeyHash());
        }
        if (this.isPayToScriptHash()) {
            return Address.fromP2SHScript(params, this);
        }
        if (forcePayToPubKey && this.isSentToRawPubKey()) {
            return ECKey.fromPublicOnly(this.getPubKey()).toAddress(params);
        }
        throw new ScriptException(ScriptError.SCRIPT_ERR_UNKNOWN_ERROR, "Cannot cast this script to a pay-to-address type");
    }

    public static void writeBytes(OutputStream os, byte[] buf) throws IOException {
        if (buf.length < 76) {
            os.write(buf.length);
            os.write(buf);
        } else if (buf.length < 256) {
            os.write(76);
            os.write(buf.length);
            os.write(buf);
        } else if (buf.length < 65536) {
            os.write(77);
            os.write(255 & buf.length);
            os.write(255 & buf.length >> 8);
            os.write(buf);
        } else {
            throw new RuntimeException("Unimplemented");
        }
    }

    public static byte[] createMultiSigOutputScript(int threshold, List<ECKey> pubkeys) {
        Preconditions.checkArgument((boolean)(threshold > 0));
        Preconditions.checkArgument((boolean)(threshold <= pubkeys.size()));
        Preconditions.checkArgument((boolean)(pubkeys.size() <= 16));
        if (pubkeys.size() > 3) {
            log.warn("Creating a multi-signature output that is non-standard: {} pubkeys, should be <= 3", (Object)pubkeys.size());
        }
        try {
            ByteArrayOutputStream bits = new ByteArrayOutputStream();
            bits.write(Script.encodeToOpN(threshold));
            for (ECKey key : pubkeys) {
                Script.writeBytes(bits, key.getPubKey());
            }
            bits.write(Script.encodeToOpN(pubkeys.size()));
            bits.write(174);
            return bits.toByteArray();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static byte[] createInputScript(byte[] signature, byte[] pubkey) {
        try {
            UnsafeByteArrayOutputStream bits = new UnsafeByteArrayOutputStream(signature.length + pubkey.length + 2);
            Script.writeBytes(bits, signature);
            Script.writeBytes(bits, pubkey);
            return bits.toByteArray();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static byte[] createInputScript(byte[] signature) {
        try {
            UnsafeByteArrayOutputStream bits = new UnsafeByteArrayOutputStream(signature.length + 2);
            Script.writeBytes(bits, signature);
            return bits.toByteArray();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Script createEmptyInputScript(@Nullable ECKey key, @Nullable Script redeemScript) {
        if (this.isSentToAddress()) {
            Preconditions.checkArgument((boolean)(key != null), (Object)"Key required to create pay-to-address input script");
            return ScriptBuilder.createInputScript(null, key);
        }
        if (this.isSentToRawPubKey()) {
            return ScriptBuilder.createInputScript(null);
        }
        if (this.isPayToScriptHash()) {
            Preconditions.checkArgument((boolean)(redeemScript != null), (Object)"Redeem script required to create P2SH input script");
            return ScriptBuilder.createP2SHMultiSigInputScript(null, redeemScript);
        }
        throw new ScriptException(ScriptError.SCRIPT_ERR_UNKNOWN_ERROR, "Do not understand script type: " + this);
    }

    public Script getScriptSigWithSignature(Script scriptSig, byte[] sigBytes, int index) {
        int sigsPrefixCount = 0;
        int sigsSuffixCount = 0;
        if (this.isPayToScriptHash()) {
            sigsPrefixCount = 1;
            sigsSuffixCount = 1;
        } else if (this.isSentToMultiSig()) {
            sigsPrefixCount = 1;
        } else if (this.isSentToAddress()) {
            sigsSuffixCount = 1;
        }
        return ScriptBuilder.updateScriptWithSignature(scriptSig, sigBytes, index, sigsPrefixCount, sigsSuffixCount);
    }

    public int getSigInsertionIndex(Sha256Hash hash, ECKey signingKey) {
        List<ScriptChunk> existingChunks = this.chunks.subList(1, this.chunks.size() - 1);
        ScriptChunk redeemScriptChunk = this.chunks.get(this.chunks.size() - 1);
        Preconditions.checkNotNull((Object)redeemScriptChunk.data);
        Script redeemScript = new Script(redeemScriptChunk.data);
        int sigCount = 0;
        int myIndex = redeemScript.findKeyInRedeem(signingKey);
        for (ScriptChunk chunk : existingChunks) {
            if (chunk.opcode == 0) continue;
            Preconditions.checkNotNull((Object)chunk.data);
            if (myIndex < redeemScript.findSigInRedeem(chunk.data, hash)) {
                return sigCount;
            }
            ++sigCount;
        }
        return sigCount;
    }

    private int findKeyInRedeem(ECKey key) {
        Preconditions.checkArgument((boolean)this.chunks.get(0).isOpCode());
        int numKeys = Script.decodeFromOpN(this.chunks.get((int)(this.chunks.size() - 2)).opcode);
        for (int i = 0; i < numKeys; ++i) {
            if (!Arrays.equals(this.chunks.get((int)(1 + i)).data, key.getPubKey())) continue;
            return i;
        }
        throw new IllegalStateException("Could not find matching key " + key.toString() + " in script " + this);
    }

    public List<ECKey> getPubKeys() {
        if (!this.isSentToMultiSig()) {
            throw new ScriptException(ScriptError.SCRIPT_ERR_UNKNOWN_ERROR, "Only usable for multisig scripts.");
        }
        ArrayList result = Lists.newArrayList();
        int numKeys = Script.decodeFromOpN(this.chunks.get((int)(this.chunks.size() - 2)).opcode);
        for (int i = 0; i < numKeys; ++i) {
            result.add(ECKey.fromPublicOnly(this.chunks.get((int)(1 + i)).data));
        }
        return result;
    }

    private int findSigInRedeem(byte[] signatureBytes, Sha256Hash hash) {
        Preconditions.checkArgument((boolean)this.chunks.get(0).isOpCode());
        int numKeys = Script.decodeFromOpN(this.chunks.get((int)(this.chunks.size() - 2)).opcode);
        TransactionSignature signature = TransactionSignature.decodeFromBitcoin(signatureBytes, true);
        for (int i = 0; i < numKeys; ++i) {
            if (!ECKey.fromPublicOnly(this.chunks.get((int)(i + 1)).data).verify(hash, signature)) continue;
            return i;
        }
        throw new IllegalStateException("Could not find matching key for signature on " + hash.toString() + " sig " + Utils.HEX.encode(signatureBytes));
    }

    private static int getSigOpCount(List<ScriptChunk> chunks, boolean accurate) throws ScriptException {
        int sigOps = 0;
        int lastOpCode = 255;
        for (ScriptChunk chunk : chunks) {
            if (!chunk.isOpCode()) continue;
            switch (chunk.opcode) {
                case 172: 
                case 173: {
                    ++sigOps;
                    break;
                }
                case 174: 
                case 175: {
                    if (accurate && lastOpCode >= 81 && lastOpCode <= 96) {
                        sigOps += Script.decodeFromOpN(lastOpCode);
                        break;
                    }
                    sigOps += 20;
                    break;
                }
            }
            lastOpCode = chunk.opcode;
        }
        return sigOps;
    }

    static int decodeFromOpN(int opcode) {
        Preconditions.checkArgument((boolean)(opcode == 0 || opcode == 79 || opcode >= 81 && opcode <= 96), (Object)"decodeFromOpN called on non OP_N opcode");
        if (opcode == 0) {
            return 0;
        }
        if (opcode == 79) {
            return -1;
        }
        return opcode + 1 - 81;
    }

    static int encodeToOpN(int value) {
        Preconditions.checkArgument((boolean)(value >= -1 && value <= 16), (Object)("encodeToOpN called for " + value + " which we cannot encode in an opcode."));
        if (value == 0) {
            return 0;
        }
        if (value == -1) {
            return 79;
        }
        return value - 1 + 81;
    }

    public static int getSigOpCount(byte[] program) throws ScriptException {
        Script script = new Script();
        try {
            script.parse(program);
        }
        catch (ScriptException scriptException) {
            // empty catch block
        }
        return Script.getSigOpCount(script.chunks, false);
    }

    public static long getP2SHSigOpCount(byte[] scriptSig) throws ScriptException {
        Script script = new Script();
        try {
            script.parse(scriptSig);
        }
        catch (ScriptException scriptException) {
            // empty catch block
        }
        for (int i = script.chunks.size() - 1; i >= 0; --i) {
            if (script.chunks.get(i).isOpCode()) continue;
            Script subScript = new Script();
            subScript.parse(script.chunks.get((int)i).data);
            return Script.getSigOpCount(subScript.chunks, true);
        }
        return 0L;
    }

    public int getNumberOfSignaturesRequiredToSpend() {
        if (this.isSentToMultiSig()) {
            ScriptChunk nChunk = this.chunks.get(0);
            return Script.decodeFromOpN(nChunk.opcode);
        }
        if (this.isSentToAddress() || this.isSentToRawPubKey()) {
            return 1;
        }
        if (this.isPayToScriptHash()) {
            throw new IllegalStateException("For P2SH number of signatures depends on redeem script");
        }
        throw new IllegalStateException("Unsupported script type");
    }

    public int getNumberOfBytesRequiredToSpend(@Nullable ECKey pubKey, @Nullable Script redeemScript) {
        if (this.isPayToScriptHash()) {
            Preconditions.checkArgument((boolean)(redeemScript != null), (Object)"P2SH script requires redeemScript to be spent");
            return redeemScript.getNumberOfSignaturesRequiredToSpend() * 75 + redeemScript.getProgram().length;
        }
        if (this.isSentToMultiSig()) {
            return this.getNumberOfSignaturesRequiredToSpend() * 75 + 1;
        }
        if (this.isSentToRawPubKey()) {
            return 75;
        }
        if (this.isSentToAddress()) {
            int uncompressedPubKeySize = 65;
            return 75 + (pubKey != null ? pubKey.getPubKey().length : uncompressedPubKeySize);
        }
        throw new IllegalStateException("Unsupported script type");
    }

    public boolean isPayToScriptHash() {
        byte[] program = this.getProgram();
        return program.length == 23 && (program[0] & 255) == 169 && (program[1] & 255) == 20 && (program[22] & 255) == 135;
    }

    public boolean isSentToMultiSig() {
        if (this.chunks.size() < 4) {
            return false;
        }
        ScriptChunk chunk = this.chunks.get(this.chunks.size() - 1);
        if (!chunk.isOpCode()) {
            return false;
        }
        if (!chunk.equalsOpCode(174) && !chunk.equalsOpCode(175)) {
            return false;
        }
        try {
            ScriptChunk m = this.chunks.get(this.chunks.size() - 2);
            if (!m.isOpCode()) {
                return false;
            }
            int numKeys = Script.decodeFromOpN(m.opcode);
            if (numKeys < 1 || this.chunks.size() != 3 + numKeys) {
                return false;
            }
            for (int i = 1; i < this.chunks.size() - 2; ++i) {
                if (!this.chunks.get(i).isOpCode()) continue;
                return false;
            }
            if (Script.decodeFromOpN(this.chunks.get((int)0).opcode) < 1) {
                return false;
            }
        }
        catch (IllegalArgumentException e) {
            return false;
        }
        return true;
    }

    public boolean isSentToCLTVPaymentChannel() {
        if (this.chunks.size() != 10) {
            return false;
        }
        if (!this.chunks.get(0).equalsOpCode(99)) {
            return false;
        }
        if (!this.chunks.get(2).equalsOpCode(173)) {
            return false;
        }
        if (!this.chunks.get(3).equalsOpCode(103)) {
            return false;
        }
        if (!this.chunks.get(5).equalsOpCode(177)) {
            return false;
        }
        if (!this.chunks.get(6).equalsOpCode(117)) {
            return false;
        }
        if (!this.chunks.get(7).equalsOpCode(104)) {
            return false;
        }
        if (!this.chunks.get(9).equalsOpCode(172)) {
            return false;
        }
        return true;
    }

    private static boolean equalsRange(byte[] a, int start, byte[] b) {
        if (start + b.length > a.length) {
            return false;
        }
        for (int i = 0; i < b.length; ++i) {
            if (a[i + start] == b[i]) continue;
            return false;
        }
        return true;
    }

    public static byte[] removeAllInstancesOf(byte[] inputScript, byte[] chunkToRemove) {
        int additionalBytes;
        UnsafeByteArrayOutputStream bos = new UnsafeByteArrayOutputStream(inputScript.length);
        for (int cursor = 0; cursor < inputScript.length; cursor += additionalBytes) {
            boolean skip = Script.equalsRange(inputScript, cursor, chunkToRemove);
            int opcode = inputScript[cursor++] & 255;
            additionalBytes = 0;
            if (opcode >= 0 && opcode < 76) {
                additionalBytes = opcode;
            } else if (opcode == 76) {
                additionalBytes = (255 & inputScript[cursor]) + 1;
            } else if (opcode == 77) {
                additionalBytes = (255 & inputScript[cursor] | (255 & inputScript[cursor + 1]) << 8) + 2;
            } else if (opcode == 78) {
                additionalBytes = (255 & inputScript[cursor] | (255 & inputScript[cursor + 1]) << 8 | (255 & inputScript[cursor + 1]) << 16 | (255 & inputScript[cursor + 1]) << 24) + 4;
            }
            if (skip) continue;
            try {
                bos.write(opcode);
                bos.write(Arrays.copyOfRange(inputScript, cursor, cursor + additionalBytes));
                continue;
            }
            catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return bos.toByteArray();
    }

    public static byte[] removeAllInstancesOfOp(byte[] inputScript, int opCode) {
        return Script.removeAllInstancesOf(inputScript, new byte[]{(byte)opCode});
    }

    private static boolean castToBool(byte[] data) {
        for (int i = 0; i < data.length; ++i) {
            if (data[i] == 0) continue;
            return i != data.length - 1 || (data[i] & 255) != 128;
        }
        return false;
    }

    private static BigInteger castToBigInteger(byte[] chunk, boolean requireMinimal) throws ScriptException {
        return Script.castToBigInteger(chunk, 4, requireMinimal);
    }

    private static BigInteger castToBigInteger(byte[] chunk, int maxLength, boolean requireMinimal) throws ScriptException {
        if (chunk.length > maxLength) {
            throw new ScriptException(ScriptError.SCRIPT_ERR_UNKNOWN_ERROR, "Script attempted to use an integer larger than " + maxLength + " bytes");
        }
        if (requireMinimal && chunk.length > 0 && (chunk[chunk.length - 1] & 127) == 0 && (chunk.length <= 1 || (chunk[chunk.length - 2] & 128) == 0)) {
            throw new ScriptException(ScriptError.SCRIPT_ERR_UNKNOWN_ERROR, "non-minimally encoded script number");
        }
        return Utils.decodeMPI(Utils.reverseBytes(chunk), false);
    }

    public boolean isOpReturn() {
        return this.chunks.size() > 0 && this.chunks.get(0).equalsOpCode(106);
    }

    @Deprecated
    public static void executeScript(@Nullable Transaction txContainingThis, long index, Script script, LinkedList<byte[]> stack, boolean enforceNullDummy) throws ScriptException {
        EnumSet<VerifyFlag> flags = enforceNullDummy ? EnumSet.of(VerifyFlag.NULLDUMMY) : EnumSet.noneOf(VerifyFlag.class);
        Script.executeScript(txContainingThis, index, script, stack, Coin.ZERO, flags);
    }

    @Deprecated
    public static void executeScript(@Nullable Transaction txContainingThis, long index, Script script, LinkedList<byte[]> stack, Set<VerifyFlag> verifyFlags) throws ScriptException {
        Script.executeScript(txContainingThis, index, script, stack, Coin.ZERO, verifyFlags);
    }

    public static void executeScript(@Nullable Transaction txContainingThis, long index, Script script, LinkedList<byte[]> stack, Coin value, Set<VerifyFlag> verifyFlags) throws ScriptException {
        int opCount = 0;
        int lastCodeSepLocation = 0;
        LinkedList<byte[]> altstack = new LinkedList<byte[]>();
        LinkedList<Boolean> ifStack = new LinkedList<Boolean>();
        block70 : for (ScriptChunk chunk : script.chunks) {
            boolean shouldExecute = !ifStack.contains(false);
            int opcode = chunk.opcode;
            if (chunk.data != null && (long)chunk.data.length > 520L) {
                throw new ScriptException(ScriptError.SCRIPT_ERR_PUSH_SIZE, "Attempted to push a data string larger than 520 bytes");
            }
            if (opcode > 96 && ++opCount > 201) {
                throw new ScriptException(ScriptError.SCRIPT_ERR_OP_COUNT, "More script operations than is allowed");
            }
            if (opcode == 126 || opcode == 127 || opcode == 128 || opcode == 129 || opcode == 131 || opcode == 132 || opcode == 133 || opcode == 134 || opcode == 141 || opcode == 142 || opcode == 149 || opcode == 150 || opcode == 151 || opcode == 152 || opcode == 153) {
                throw new ScriptException(ScriptError.SCRIPT_ERR_DISABLED_OPCODE, "Script included a disabled Script Op.");
            }
            if (shouldExecute && 0 <= opcode && opcode <= 78) {
                if (verifyFlags.contains((Object)VerifyFlag.MINIMALDATA) && !chunk.isShortestPossiblePushData()) {
                    throw new ScriptException(ScriptError.SCRIPT_ERR_MINIMALDATA, "Script included a not minimal push operation.");
                }
                if (opcode == 0) {
                    stack.add(new byte[0]);
                } else {
                    stack.add(chunk.data);
                }
            } else if (shouldExecute || 99 <= opcode && opcode <= 104) {
                switch (opcode) {
                    case 99: {
                        if (!shouldExecute) {
                            ifStack.add(false);
                            continue block70;
                        }
                        if (stack.size() < 1) {
                            throw new ScriptException(ScriptError.SCRIPT_ERR_UNBALANCED_CONDITIONAL, "Attempted OP_IF on an empty stack");
                        }
                        ifStack.add(Script.castToBool(stack.pollLast()));
                        continue block70;
                    }
                    case 100: {
                        if (!shouldExecute) {
                            ifStack.add(false);
                            continue block70;
                        }
                        if (stack.size() < 1) {
                            throw new ScriptException(ScriptError.SCRIPT_ERR_UNBALANCED_CONDITIONAL, "Attempted OP_NOTIF on an empty stack");
                        }
                        ifStack.add(!Script.castToBool(stack.pollLast()));
                        continue block70;
                    }
                    case 103: {
                        if (ifStack.isEmpty()) {
                            throw new ScriptException(ScriptError.SCRIPT_ERR_UNBALANCED_CONDITIONAL, "Attempted OP_ELSE without OP_IF/NOTIF");
                        }
                        ifStack.add((Boolean)ifStack.pollLast() == false);
                        continue block70;
                    }
                    case 104: {
                        if (ifStack.isEmpty()) {
                            throw new ScriptException(ScriptError.SCRIPT_ERR_UNBALANCED_CONDITIONAL, "Attempted OP_ENDIF without OP_IF/NOTIF");
                        }
                        ifStack.pollLast();
                        continue block70;
                    }
                    case 79: {
                        stack.add(Utils.reverseBytes(Utils.encodeMPI(BigInteger.ONE.negate(), false)));
                        break;
                    }
                    case 81: 
                    case 82: 
                    case 83: 
                    case 84: 
                    case 85: 
                    case 86: 
                    case 87: 
                    case 88: 
                    case 89: 
                    case 90: 
                    case 91: 
                    case 92: 
                    case 93: 
                    case 94: 
                    case 95: 
                    case 96: {
                        stack.add(Utils.reverseBytes(Utils.encodeMPI(BigInteger.valueOf(Script.decodeFromOpN(opcode)), false)));
                        break;
                    }
                    case 97: {
                        break;
                    }
                    case 105: {
                        if (stack.size() < 1) {
                            throw new ScriptException(ScriptError.SCRIPT_ERR_INVALID_STACK_OPERATION, "Attempted OP_VERIFY on an empty stack");
                        }
                        if (Script.castToBool(stack.pollLast())) break;
                        throw new ScriptException(ScriptError.SCRIPT_ERR_VERIFY, "OP_VERIFY failed");
                    }
                    case 106: {
                        throw new ScriptException(ScriptError.SCRIPT_ERR_OP_RETURN, "Script called OP_RETURN");
                    }
                    case 107: {
                        if (stack.size() < 1) {
                            throw new ScriptException(ScriptError.SCRIPT_ERR_INVALID_STACK_OPERATION, "Attempted OP_TOALTSTACK on an empty stack");
                        }
                        altstack.add(stack.pollLast());
                        break;
                    }
                    case 108: {
                        if (altstack.size() < 1) {
                            throw new ScriptException(ScriptError.SCRIPT_ERR_INVALID_ALTSTACK_OPERATION, "Attempted OP_FROMALTSTACK on an empty altstack");
                        }
                        stack.add((byte[])altstack.pollLast());
                        break;
                    }
                    case 109: {
                        if (stack.size() < 2) {
                            throw new ScriptException(ScriptError.SCRIPT_ERR_INVALID_STACK_OPERATION, "Attempted OP_2DROP on a stack with size < 2");
                        }
                        stack.pollLast();
                        stack.pollLast();
                        break;
                    }
                    case 110: {
                        if (stack.size() < 2) {
                            throw new ScriptException(ScriptError.SCRIPT_ERR_INVALID_STACK_OPERATION, "Attempted OP_2DUP on a stack with size < 2");
                        }
                        Iterator<byte[]> it2DUP = stack.descendingIterator();
                        byte[] OP2DUPtmpChunk2 = it2DUP.next();
                        stack.add(it2DUP.next());
                        stack.add(OP2DUPtmpChunk2);
                        break;
                    }
                    case 111: {
                        if (stack.size() < 3) {
                            throw new ScriptException(ScriptError.SCRIPT_ERR_INVALID_STACK_OPERATION, "Attempted OP_3DUP on a stack with size < 3");
                        }
                        Iterator<byte[]> it3DUP = stack.descendingIterator();
                        byte[] OP3DUPtmpChunk3 = it3DUP.next();
                        byte[] OP3DUPtmpChunk2 = it3DUP.next();
                        stack.add(it3DUP.next());
                        stack.add(OP3DUPtmpChunk2);
                        stack.add(OP3DUPtmpChunk3);
                        break;
                    }
                    case 112: {
                        if (stack.size() < 4) {
                            throw new ScriptException(ScriptError.SCRIPT_ERR_INVALID_STACK_OPERATION, "Attempted OP_2OVER on a stack with size < 4");
                        }
                        Iterator<byte[]> it2OVER = stack.descendingIterator();
                        it2OVER.next();
                        it2OVER.next();
                        byte[] OP2OVERtmpChunk2 = it2OVER.next();
                        stack.add(it2OVER.next());
                        stack.add(OP2OVERtmpChunk2);
                        break;
                    }
                    case 113: {
                        if (stack.size() < 6) {
                            throw new ScriptException(ScriptError.SCRIPT_ERR_INVALID_STACK_OPERATION, "Attempted OP_2ROT on a stack with size < 6");
                        }
                        byte[] OP2ROTtmpChunk6 = stack.pollLast();
                        byte[] OP2ROTtmpChunk5 = stack.pollLast();
                        byte[] OP2ROTtmpChunk4 = stack.pollLast();
                        byte[] OP2ROTtmpChunk3 = stack.pollLast();
                        byte[] OP2ROTtmpChunk2 = stack.pollLast();
                        byte[] OP2ROTtmpChunk1 = stack.pollLast();
                        stack.add(OP2ROTtmpChunk3);
                        stack.add(OP2ROTtmpChunk4);
                        stack.add(OP2ROTtmpChunk5);
                        stack.add(OP2ROTtmpChunk6);
                        stack.add(OP2ROTtmpChunk1);
                        stack.add(OP2ROTtmpChunk2);
                        break;
                    }
                    case 114: {
                        if (stack.size() < 4) {
                            throw new ScriptException(ScriptError.SCRIPT_ERR_INVALID_STACK_OPERATION, "Attempted OP_2SWAP on a stack with size < 4");
                        }
                        byte[] OP2SWAPtmpChunk4 = stack.pollLast();
                        byte[] OP2SWAPtmpChunk3 = stack.pollLast();
                        byte[] OP2SWAPtmpChunk2 = stack.pollLast();
                        byte[] OP2SWAPtmpChunk1 = stack.pollLast();
                        stack.add(OP2SWAPtmpChunk3);
                        stack.add(OP2SWAPtmpChunk4);
                        stack.add(OP2SWAPtmpChunk1);
                        stack.add(OP2SWAPtmpChunk2);
                        break;
                    }
                    case 115: {
                        if (stack.size() < 1) {
                            throw new ScriptException(ScriptError.SCRIPT_ERR_INVALID_STACK_OPERATION, "Attempted OP_IFDUP on an empty stack");
                        }
                        if (!Script.castToBool(stack.getLast())) break;
                        stack.add(stack.getLast());
                        break;
                    }
                    case 116: {
                        stack.add(Utils.reverseBytes(Utils.encodeMPI(BigInteger.valueOf(stack.size()), false)));
                        break;
                    }
                    case 117: {
                        if (stack.size() < 1) {
                            throw new ScriptException(ScriptError.SCRIPT_ERR_INVALID_STACK_OPERATION, "Attempted OP_DROP on an empty stack");
                        }
                        stack.pollLast();
                        break;
                    }
                    case 118: {
                        if (stack.size() < 1) {
                            throw new ScriptException(ScriptError.SCRIPT_ERR_INVALID_STACK_OPERATION, "Attempted OP_DUP on an empty stack");
                        }
                        stack.add(stack.getLast());
                        break;
                    }
                    case 119: {
                        if (stack.size() < 2) {
                            throw new ScriptException(ScriptError.SCRIPT_ERR_INVALID_STACK_OPERATION, "Attempted OP_NIP on a stack with size < 2");
                        }
                        byte[] OPNIPtmpChunk = stack.pollLast();
                        stack.pollLast();
                        stack.add(OPNIPtmpChunk);
                        break;
                    }
                    case 120: {
                        if (stack.size() < 2) {
                            throw new ScriptException(ScriptError.SCRIPT_ERR_INVALID_STACK_OPERATION, "Attempted OP_OVER on a stack with size < 2");
                        }
                        Iterator<byte[]> itOVER = stack.descendingIterator();
                        itOVER.next();
                        stack.add(itOVER.next());
                        break;
                    }
                    case 121: 
                    case 122: {
                        if (stack.size() < 1) {
                            throw new ScriptException(ScriptError.SCRIPT_ERR_INVALID_STACK_OPERATION, "Attempted OP_PICK/OP_ROLL on an empty stack");
                        }
                        long val = Script.castToBigInteger(stack.pollLast(), verifyFlags.contains((Object)VerifyFlag.MINIMALDATA)).longValue();
                        if (val < 0L || val >= (long)stack.size()) {
                            throw new ScriptException(ScriptError.SCRIPT_ERR_INVALID_STACK_OPERATION, "OP_PICK/OP_ROLL attempted to get data deeper than stack size");
                        }
                        Iterator<byte[]> itPICK = stack.descendingIterator();
                        for (long i = 0L; i < val; ++i) {
                            itPICK.next();
                        }
                        byte[] OPROLLtmpChunk = itPICK.next();
                        if (opcode == 122) {
                            itPICK.remove();
                        }
                        stack.add(OPROLLtmpChunk);
                        break;
                    }
                    case 123: {
                        if (stack.size() < 3) {
                            throw new ScriptException(ScriptError.SCRIPT_ERR_INVALID_STACK_OPERATION, "Attempted OP_ROT on a stack with size < 3");
                        }
                        byte[] OPROTtmpChunk3 = stack.pollLast();
                        byte[] OPROTtmpChunk2 = stack.pollLast();
                        byte[] OPROTtmpChunk1 = stack.pollLast();
                        stack.add(OPROTtmpChunk2);
                        stack.add(OPROTtmpChunk3);
                        stack.add(OPROTtmpChunk1);
                        break;
                    }
                    case 124: 
                    case 125: {
                        if (stack.size() < 2) {
                            throw new ScriptException(ScriptError.SCRIPT_ERR_INVALID_STACK_OPERATION, "Attempted OP_SWAP on a stack with size < 2");
                        }
                        byte[] OPSWAPtmpChunk2 = stack.pollLast();
                        byte[] OPSWAPtmpChunk1 = stack.pollLast();
                        stack.add(OPSWAPtmpChunk2);
                        stack.add(OPSWAPtmpChunk1);
                        if (opcode != 125) break;
                        stack.add(OPSWAPtmpChunk2);
                        break;
                    }
                    case 130: {
                        if (stack.size() < 1) {
                            throw new ScriptException(ScriptError.SCRIPT_ERR_INVALID_STACK_OPERATION, "Attempted OP_SIZE on an empty stack");
                        }
                        stack.add(Utils.reverseBytes(Utils.encodeMPI(BigInteger.valueOf(stack.getLast().length), false)));
                        break;
                    }
                    case 135: {
                        byte[] arrby;
                        if (stack.size() < 2) {
                            throw new ScriptException(ScriptError.SCRIPT_ERR_INVALID_STACK_OPERATION, "Attempted OP_EQUAL on a stack with size < 2");
                        }
                        if (Arrays.equals(stack.pollLast(), stack.pollLast())) {
                            byte[] arrby2 = new byte[1];
                            arrby = arrby2;
                            arrby2[0] = 1;
                        } else {
                            arrby = new byte[]{};
                        }
                        stack.add(arrby);
                        break;
                    }
                    case 136: {
                        if (stack.size() < 2) {
                            throw new ScriptException(ScriptError.SCRIPT_ERR_INVALID_STACK_OPERATION, "Attempted OP_EQUALVERIFY on a stack with size < 2");
                        }
                        if (Arrays.equals(stack.pollLast(), stack.pollLast())) break;
                        throw new ScriptException(ScriptError.SCRIPT_ERR_EQUALVERIFY, "OP_EQUALVERIFY: non-equal data");
                    }
                    case 139: 
                    case 140: 
                    case 143: 
                    case 144: 
                    case 145: 
                    case 146: {
                        if (stack.size() < 1) {
                            throw new ScriptException(ScriptError.SCRIPT_ERR_INVALID_STACK_OPERATION, "Attempted a numeric op on an empty stack");
                        }
                        BigInteger numericOPnum = Script.castToBigInteger(stack.pollLast(), verifyFlags.contains((Object)VerifyFlag.MINIMALDATA));
                        switch (opcode) {
                            case 139: {
                                numericOPnum = numericOPnum.add(BigInteger.ONE);
                                break;
                            }
                            case 140: {
                                numericOPnum = numericOPnum.subtract(BigInteger.ONE);
                                break;
                            }
                            case 143: {
                                numericOPnum = numericOPnum.negate();
                                break;
                            }
                            case 144: {
                                if (numericOPnum.signum() >= 0) break;
                                numericOPnum = numericOPnum.negate();
                                break;
                            }
                            case 145: {
                                if (numericOPnum.equals(BigInteger.ZERO)) {
                                    numericOPnum = BigInteger.ONE;
                                    break;
                                }
                                numericOPnum = BigInteger.ZERO;
                                break;
                            }
                            case 146: {
                                if (numericOPnum.equals(BigInteger.ZERO)) {
                                    numericOPnum = BigInteger.ZERO;
                                    break;
                                }
                                numericOPnum = BigInteger.ONE;
                                break;
                            }
                            default: {
                                throw new AssertionError((Object)"Unreachable");
                            }
                        }
                        stack.add(Utils.reverseBytes(Utils.encodeMPI(numericOPnum, false)));
                        break;
                    }
                    case 147: 
                    case 148: 
                    case 154: 
                    case 155: 
                    case 156: 
                    case 158: 
                    case 159: 
                    case 160: 
                    case 161: 
                    case 162: 
                    case 163: 
                    case 164: {
                        BigInteger numericOPresult;
                        if (stack.size() < 2) {
                            throw new ScriptException(ScriptError.SCRIPT_ERR_INVALID_STACK_OPERATION, "Attempted a numeric op on a stack with size < 2");
                        }
                        BigInteger numericOPnum2 = Script.castToBigInteger(stack.pollLast(), verifyFlags.contains((Object)VerifyFlag.MINIMALDATA));
                        BigInteger numericOPnum1 = Script.castToBigInteger(stack.pollLast(), verifyFlags.contains((Object)VerifyFlag.MINIMALDATA));
                        switch (opcode) {
                            case 147: {
                                numericOPresult = numericOPnum1.add(numericOPnum2);
                                break;
                            }
                            case 148: {
                                numericOPresult = numericOPnum1.subtract(numericOPnum2);
                                break;
                            }
                            case 154: {
                                if (!numericOPnum1.equals(BigInteger.ZERO) && !numericOPnum2.equals(BigInteger.ZERO)) {
                                    numericOPresult = BigInteger.ONE;
                                    break;
                                }
                                numericOPresult = BigInteger.ZERO;
                                break;
                            }
                            case 155: {
                                if (!numericOPnum1.equals(BigInteger.ZERO) || !numericOPnum2.equals(BigInteger.ZERO)) {
                                    numericOPresult = BigInteger.ONE;
                                    break;
                                }
                                numericOPresult = BigInteger.ZERO;
                                break;
                            }
                            case 156: {
                                if (numericOPnum1.equals(numericOPnum2)) {
                                    numericOPresult = BigInteger.ONE;
                                    break;
                                }
                                numericOPresult = BigInteger.ZERO;
                                break;
                            }
                            case 158: {
                                if (!numericOPnum1.equals(numericOPnum2)) {
                                    numericOPresult = BigInteger.ONE;
                                    break;
                                }
                                numericOPresult = BigInteger.ZERO;
                                break;
                            }
                            case 159: {
                                if (numericOPnum1.compareTo(numericOPnum2) < 0) {
                                    numericOPresult = BigInteger.ONE;
                                    break;
                                }
                                numericOPresult = BigInteger.ZERO;
                                break;
                            }
                            case 160: {
                                if (numericOPnum1.compareTo(numericOPnum2) > 0) {
                                    numericOPresult = BigInteger.ONE;
                                    break;
                                }
                                numericOPresult = BigInteger.ZERO;
                                break;
                            }
                            case 161: {
                                if (numericOPnum1.compareTo(numericOPnum2) <= 0) {
                                    numericOPresult = BigInteger.ONE;
                                    break;
                                }
                                numericOPresult = BigInteger.ZERO;
                                break;
                            }
                            case 162: {
                                if (numericOPnum1.compareTo(numericOPnum2) >= 0) {
                                    numericOPresult = BigInteger.ONE;
                                    break;
                                }
                                numericOPresult = BigInteger.ZERO;
                                break;
                            }
                            case 163: {
                                if (numericOPnum1.compareTo(numericOPnum2) < 0) {
                                    numericOPresult = numericOPnum1;
                                    break;
                                }
                                numericOPresult = numericOPnum2;
                                break;
                            }
                            case 164: {
                                if (numericOPnum1.compareTo(numericOPnum2) > 0) {
                                    numericOPresult = numericOPnum1;
                                    break;
                                }
                                numericOPresult = numericOPnum2;
                                break;
                            }
                            default: {
                                throw new RuntimeException("Opcode switched at runtime?");
                            }
                        }
                        stack.add(Utils.reverseBytes(Utils.encodeMPI(numericOPresult, false)));
                        break;
                    }
                    case 157: {
                        if (stack.size() < 2) {
                            throw new ScriptException(ScriptError.SCRIPT_ERR_INVALID_STACK_OPERATION, "Attempted OP_NUMEQUALVERIFY on a stack with size < 2");
                        }
                        BigInteger OPNUMEQUALVERIFYnum2 = Script.castToBigInteger(stack.pollLast(), verifyFlags.contains((Object)VerifyFlag.MINIMALDATA));
                        BigInteger OPNUMEQUALVERIFYnum1 = Script.castToBigInteger(stack.pollLast(), verifyFlags.contains((Object)VerifyFlag.MINIMALDATA));
                        if (OPNUMEQUALVERIFYnum1.equals(OPNUMEQUALVERIFYnum2)) break;
                        throw new ScriptException(ScriptError.SCRIPT_ERR_NUMEQUALVERIFY, "OP_NUMEQUALVERIFY failed");
                    }
                    case 165: {
                        BigInteger OPWITHINnum1;
                        if (stack.size() < 3) {
                            throw new ScriptException(ScriptError.SCRIPT_ERR_INVALID_STACK_OPERATION, "Attempted OP_WITHIN on a stack with size < 3");
                        }
                        BigInteger OPWITHINnum3 = Script.castToBigInteger(stack.pollLast(), verifyFlags.contains((Object)VerifyFlag.MINIMALDATA));
                        BigInteger OPWITHINnum2 = Script.castToBigInteger(stack.pollLast(), verifyFlags.contains((Object)VerifyFlag.MINIMALDATA));
                        if (OPWITHINnum2.compareTo(OPWITHINnum1 = Script.castToBigInteger(stack.pollLast(), verifyFlags.contains((Object)VerifyFlag.MINIMALDATA))) <= 0 && OPWITHINnum1.compareTo(OPWITHINnum3) < 0) {
                            stack.add(Utils.reverseBytes(Utils.encodeMPI(BigInteger.ONE, false)));
                            break;
                        }
                        stack.add(Utils.reverseBytes(Utils.encodeMPI(BigInteger.ZERO, false)));
                        break;
                    }
                    case 166: {
                        if (stack.size() < 1) {
                            throw new ScriptException(ScriptError.SCRIPT_ERR_INVALID_STACK_OPERATION, "Attempted OP_RIPEMD160 on an empty stack");
                        }
                        RIPEMD160Digest digest = new RIPEMD160Digest();
                        byte[] dataToHash = stack.pollLast();
                        digest.update(dataToHash, 0, dataToHash.length);
                        byte[] ripmemdHash = new byte[20];
                        digest.doFinal(ripmemdHash, 0);
                        stack.add(ripmemdHash);
                        break;
                    }
                    case 167: {
                        if (stack.size() < 1) {
                            throw new ScriptException(ScriptError.SCRIPT_ERR_INVALID_STACK_OPERATION, "Attempted OP_SHA1 on an empty stack");
                        }
                        try {
                            stack.add(MessageDigest.getInstance("SHA-1").digest(stack.pollLast()));
                            break;
                        }
                        catch (NoSuchAlgorithmException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    case 168: {
                        if (stack.size() < 1) {
                            throw new ScriptException(ScriptError.SCRIPT_ERR_INVALID_STACK_OPERATION, "Attempted OP_SHA256 on an empty stack");
                        }
                        stack.add(Sha256Hash.hash(stack.pollLast()));
                        break;
                    }
                    case 169: {
                        if (stack.size() < 1) {
                            throw new ScriptException(ScriptError.SCRIPT_ERR_INVALID_STACK_OPERATION, "Attempted OP_HASH160 on an empty stack");
                        }
                        stack.add(Utils.sha256hash160(stack.pollLast()));
                        break;
                    }
                    case 170: {
                        if (stack.size() < 1) {
                            throw new ScriptException(ScriptError.SCRIPT_ERR_INVALID_STACK_OPERATION, "Attempted OP_SHA256 on an empty stack");
                        }
                        stack.add(Sha256Hash.hashTwice(stack.pollLast()));
                        break;
                    }
                    case 171: {
                        lastCodeSepLocation = chunk.getStartLocationInProgram() + 1;
                        break;
                    }
                    case 172: 
                    case 173: {
                        if (txContainingThis == null) {
                            throw new IllegalStateException("Script attempted signature check but no tx was provided");
                        }
                        Script.executeCheckSig(txContainingThis, (int)index, script, stack, lastCodeSepLocation, opcode, value, verifyFlags);
                        break;
                    }
                    case 174: 
                    case 175: {
                        if (txContainingThis == null) {
                            throw new IllegalStateException("Script attempted signature check but no tx was provided");
                        }
                        opCount = Script.executeMultiSig(txContainingThis, (int)index, script, stack, opCount, lastCodeSepLocation, opcode, value, verifyFlags);
                        break;
                    }
                    case 177: {
                        if (!verifyFlags.contains((Object)VerifyFlag.CHECKLOCKTIMEVERIFY)) {
                            if (!verifyFlags.contains((Object)VerifyFlag.DISCOURAGE_UPGRADABLE_NOPS)) break;
                            throw new ScriptException(ScriptError.SCRIPT_ERR_DISCOURAGE_UPGRADABLE_NOPS, "Script used a reserved opcode " + opcode);
                        }
                        Script.executeCheckLockTimeVerify(txContainingThis, (int)index, stack, verifyFlags);
                        break;
                    }
                    case 178: {
                        if (!verifyFlags.contains((Object)VerifyFlag.CHECKSEQUENCEVERIFY)) {
                            if (!verifyFlags.contains((Object)VerifyFlag.DISCOURAGE_UPGRADABLE_NOPS)) break;
                            throw new ScriptException(ScriptError.SCRIPT_ERR_DISCOURAGE_UPGRADABLE_NOPS, "Script used a reserved opcode " + opcode);
                        }
                        Script.executeCheckSequenceVerify(txContainingThis, (int)index, stack, verifyFlags);
                        break;
                    }
                    case 176: 
                    case 179: 
                    case 180: 
                    case 181: 
                    case 182: 
                    case 183: 
                    case 184: 
                    case 185: {
                        if (!verifyFlags.contains((Object)VerifyFlag.DISCOURAGE_UPGRADABLE_NOPS)) break;
                        throw new ScriptException(ScriptError.SCRIPT_ERR_DISCOURAGE_UPGRADABLE_NOPS, "Script used a reserved opcode " + opcode);
                    }
                    default: {
                        throw new ScriptException(ScriptError.SCRIPT_ERR_BAD_OPCODE, "Script used a reserved or disabled opcode: " + opcode);
                    }
                }
            }
            if (stack.size() + altstack.size() <= 1000 && stack.size() + altstack.size() >= 0) continue;
            throw new ScriptException(ScriptError.SCRIPT_ERR_STACK_SIZE, "Stack size exceeded range");
        }
        if (!ifStack.isEmpty()) {
            throw new ScriptException(ScriptError.SCRIPT_ERR_UNBALANCED_CONDITIONAL, "OP_IF/OP_NOTIF without OP_ENDIF");
        }
    }

    private static void executeCheckLockTimeVerify(Transaction txContainingThis, int index, LinkedList<byte[]> stack, Set<VerifyFlag> verifyFlags) throws ScriptException {
        if (stack.size() < 1) {
            throw new ScriptException(ScriptError.SCRIPT_ERR_INVALID_STACK_OPERATION, "Attempted OP_CHECKLOCKTIMEVERIFY on a stack with size < 1");
        }
        BigInteger nLockTime = Script.castToBigInteger(stack.getLast(), 5, verifyFlags.contains((Object)VerifyFlag.MINIMALDATA));
        if (nLockTime.compareTo(BigInteger.ZERO) < 0) {
            throw new ScriptException(ScriptError.SCRIPT_ERR_NEGATIVE_LOCKTIME, "Negative locktime");
        }
        if (!(txContainingThis.getLockTime() < 500000000L && nLockTime.compareTo(Transaction.LOCKTIME_THRESHOLD_BIG) < 0 || txContainingThis.getLockTime() >= 500000000L && nLockTime.compareTo(Transaction.LOCKTIME_THRESHOLD_BIG) >= 0)) {
            throw new ScriptException(ScriptError.SCRIPT_ERR_UNSATISFIED_LOCKTIME, "Locktime requirement type mismatch");
        }
        if (nLockTime.compareTo(BigInteger.valueOf(txContainingThis.getLockTime())) > 0) {
            throw new ScriptException(ScriptError.SCRIPT_ERR_UNSATISFIED_LOCKTIME, "Locktime requirement not satisfied");
        }
        if (!txContainingThis.getInput(index).hasSequence()) {
            throw new ScriptException(ScriptError.SCRIPT_ERR_UNSATISFIED_LOCKTIME, "Transaction contains a final transaction input for a CHECKLOCKTIMEVERIFY script.");
        }
    }

    private static void executeCheckSequenceVerify(Transaction txContainingThis, int index, LinkedList<byte[]> stack, Set<VerifyFlag> verifyFlags) throws ScriptException {
        if (stack.size() < 1) {
            throw new ScriptException(ScriptError.SCRIPT_ERR_INVALID_STACK_OPERATION, "Attempted OP_CHECKSEQUENCEVERIFY on a stack with size < 1");
        }
        long nSequence = Script.castToBigInteger(stack.getLast(), 5, verifyFlags.contains((Object)VerifyFlag.MINIMALDATA)).longValue();
        if (nSequence < 0L) {
            throw new ScriptException(ScriptError.SCRIPT_ERR_NEGATIVE_LOCKTIME, "Negative sequence");
        }
        if ((nSequence & 0x80000000L) != 0L) {
            return;
        }
        if (!Script.checkSequence(nSequence, txContainingThis, index)) {
            throw new ScriptException(ScriptError.SCRIPT_ERR_UNSATISFIED_LOCKTIME, "Unsatisfied CHECKLOCKTIMEVERIFY lock time");
        }
    }

    private static boolean checkSequence(long nSequence, Transaction txContainingThis, int index) {
        long txToSequence = txContainingThis.getInput(index).getSequenceNumber();
        if (txContainingThis.getVersion() < 2L) {
            return false;
        }
        if ((txToSequence & 0x80000000L) != 0L) {
            return false;
        }
        long nLockTimeMask = 4259839L;
        long txToSequenceMasked = txToSequence & nLockTimeMask;
        long nSequenceMasked = nSequence & nLockTimeMask;
        if (!(txToSequenceMasked < 0x400000L && nSequenceMasked < 0x400000L || txToSequenceMasked >= 0x400000L && nSequenceMasked >= 0x400000L)) {
            return false;
        }
        if (nSequenceMasked > txToSequenceMasked) {
            return false;
        }
        return true;
    }

    private static void executeCheckSig(Transaction txContainingThis, int index, Script script, LinkedList<byte[]> stack, int lastCodeSepLocation, int opcode, Coin value, Set<VerifyFlag> verifyFlags) throws ScriptException {
        boolean sigValid;
        block10 : {
            boolean requireCanonical;
            boolean bl = requireCanonical = verifyFlags.contains((Object)VerifyFlag.STRICTENC) || verifyFlags.contains((Object)VerifyFlag.DERSIG) || verifyFlags.contains((Object)VerifyFlag.LOW_S);
            if (stack.size() < 2) {
                throw new ScriptException(ScriptError.SCRIPT_ERR_INVALID_STACK_OPERATION, "Attempted OP_CHECKSIG(VERIFY) on a stack with size < 2");
            }
            byte[] pubKey = stack.pollLast();
            byte[] sigBytes = stack.pollLast();
            byte[] prog = script.getProgram();
            byte[] connectedScript = Arrays.copyOfRange(prog, lastCodeSepLocation, prog.length);
            UnsafeByteArrayOutputStream outStream = new UnsafeByteArrayOutputStream(sigBytes.length + 1);
            try {
                Script.writeBytes(outStream, sigBytes);
            }
            catch (IOException e) {
                throw new RuntimeException(e);
            }
            connectedScript = Script.removeAllInstancesOf(connectedScript, outStream.toByteArray());
            sigValid = false;
            try {
                TransactionSignature sig = TransactionSignature.decodeFromBitcoin(sigBytes, requireCanonical, verifyFlags.contains((Object)VerifyFlag.LOW_S));
                Sha256Hash hash = sig.useForkId() ? txContainingThis.hashForSignatureWitness(index, connectedScript, value, sig.sigHashMode(), sig.anyoneCanPay()) : txContainingThis.hashForSignature(index, connectedScript, (byte)sig.sighashFlags);
                sigValid = ECKey.verify(hash.getBytes(), sig, pubKey);
            }
            catch (Exception e1) {
                if (e1.getMessage() == null || e1.getMessage().contains("Reached past end of ASN.1 stream")) break block10;
                log.warn("Signature checking failed!", (Throwable)e1);
            }
        }
        if (opcode == 172) {
            byte[] arrby;
            if (sigValid) {
                byte[] arrby2 = new byte[1];
                arrby = arrby2;
                arrby2[0] = 1;
            } else {
                arrby = new byte[]{};
            }
            stack.add(arrby);
        } else if (opcode == 173 && !sigValid) {
            throw new ScriptException(ScriptError.SCRIPT_ERR_CHECKSIGVERIFY, "Script failed OP_CHECKSIGVERIFY");
        }
    }

    private static int executeMultiSig(Transaction txContainingThis, int index, Script script, LinkedList<byte[]> stack, int opCount, int lastCodeSepLocation, int opcode, Coin value, Set<VerifyFlag> verifyFlags) throws ScriptException {
        boolean requireCanonical;
        boolean bl = requireCanonical = verifyFlags.contains((Object)VerifyFlag.STRICTENC) || verifyFlags.contains((Object)VerifyFlag.DERSIG) || verifyFlags.contains((Object)VerifyFlag.LOW_S);
        if (stack.size() < 1) {
            throw new ScriptException(ScriptError.SCRIPT_ERR_INVALID_STACK_OPERATION, "Attempted OP_CHECKMULTISIG(VERIFY) on a stack with size < 2");
        }
        int pubKeyCount = Script.castToBigInteger(stack.pollLast(), verifyFlags.contains((Object)VerifyFlag.MINIMALDATA)).intValue();
        if (pubKeyCount < 0 || pubKeyCount > 20) {
            throw new ScriptException(ScriptError.SCRIPT_ERR_PUBKEY_COUNT, "OP_CHECKMULTISIG(VERIFY) with pubkey count out of range");
        }
        if ((opCount += pubKeyCount) > 201) {
            throw new ScriptException(ScriptError.SCRIPT_ERR_OP_COUNT, "Total op count > 201 during OP_CHECKMULTISIG(VERIFY)");
        }
        if (stack.size() < pubKeyCount + 1) {
            throw new ScriptException(ScriptError.SCRIPT_ERR_INVALID_STACK_OPERATION, "Attempted OP_CHECKMULTISIG(VERIFY) on a stack with size < num_of_pubkeys + 2");
        }
        LinkedList<byte[]> pubkeys = new LinkedList<byte[]>();
        for (int i = 0; i < pubKeyCount; ++i) {
            byte[] pubKey = stack.pollLast();
            pubkeys.add(pubKey);
        }
        int sigCount = Script.castToBigInteger(stack.pollLast(), verifyFlags.contains((Object)VerifyFlag.MINIMALDATA)).intValue();
        if (sigCount < 0 || sigCount > pubKeyCount) {
            throw new ScriptException(ScriptError.SCRIPT_ERR_SIG_COUNT, "OP_CHECKMULTISIG(VERIFY) with sig count out of range");
        }
        if (stack.size() < sigCount + 1) {
            throw new ScriptException(ScriptError.SCRIPT_ERR_INVALID_STACK_OPERATION, "Attempted OP_CHECKMULTISIG(VERIFY) on a stack with size < num_of_pubkeys + num_of_signatures + 3");
        }
        LinkedList<byte[]> sigs = new LinkedList<byte[]>();
        for (int i = 0; i < sigCount; ++i) {
            byte[] sig = stack.pollLast();
            sigs.add(sig);
        }
        byte[] prog = script.getProgram();
        byte[] connectedScript = Arrays.copyOfRange(prog, lastCodeSepLocation, prog.length);
        for (byte[] sig : sigs) {
            UnsafeByteArrayOutputStream outStream = new UnsafeByteArrayOutputStream(sig.length + 1);
            try {
                Script.writeBytes(outStream, sig);
            }
            catch (IOException e) {
                throw new RuntimeException(e);
            }
            connectedScript = Script.removeAllInstancesOf(connectedScript, outStream.toByteArray());
        }
        boolean valid = true;
        while (sigs.size() > 0) {
            byte[] pubKey = (byte[])pubkeys.pollFirst();
            try {
                Sha256Hash hash;
                TransactionSignature sig = TransactionSignature.decodeFromBitcoin((byte[])sigs.getFirst(), requireCanonical);
                Sha256Hash sha256Hash = hash = sig.useForkId() ? txContainingThis.hashForSignatureWitness(index, connectedScript, value, sig.sigHashMode(), sig.anyoneCanPay()) : txContainingThis.hashForSignature(index, connectedScript, (byte)sig.sighashFlags);
                if (ECKey.verify(hash.getBytes(), sig, pubKey)) {
                    sigs.pollFirst();
                }
            }
            catch (Exception sig) {
                // empty catch block
            }
            if (sigs.size() <= pubkeys.size()) continue;
            valid = false;
            break;
        }
        byte[] nullDummy = stack.pollLast();
        if (verifyFlags.contains((Object)VerifyFlag.NULLDUMMY) && nullDummy.length > 0) {
            throw new ScriptException(ScriptError.SCRIPT_ERR_SIG_NULLFAIL, "OP_CHECKMULTISIG(VERIFY) with non-null nulldummy: " + Arrays.toString(nullDummy));
        }
        if (opcode == 174) {
            byte[] arrby;
            if (valid) {
                byte[] arrby2 = new byte[1];
                arrby = arrby2;
                arrby2[0] = 1;
            } else {
                arrby = new byte[]{};
            }
            stack.add(arrby);
        } else if (opcode == 175 && !valid) {
            throw new ScriptException(ScriptError.SCRIPT_ERR_SIG_NULLFAIL, "Script failed OP_CHECKMULTISIGVERIFY");
        }
        return opCount;
    }

    @Deprecated
    public void correctlySpends(Transaction txContainingThis, long scriptSigIndex, Script scriptPubKey) throws ScriptException {
        this.correctlySpends(txContainingThis, scriptSigIndex, scriptPubKey, Coin.ZERO, ALL_VERIFY_FLAGS);
    }

    @Deprecated
    public void correctlySpends(Transaction txContainingThis, long scriptSigIndex, Script scriptPubKey, Set<VerifyFlag> verifyFlags) throws ScriptException {
        this.correctlySpends(txContainingThis, scriptSigIndex, scriptPubKey, Coin.ZERO, verifyFlags);
    }

    public void correctlySpends(Transaction txContainingThis, long scriptSigIndex, Script scriptPubKey, Coin value, Set<VerifyFlag> verifyFlags) throws ScriptException {
        try {
            txContainingThis = txContainingThis.getParams().getDefaultSerializer().makeTransaction(txContainingThis.bitcoinSerialize());
        }
        catch (ProtocolException e) {
            throw new RuntimeException(e);
        }
        if (this.getProgram().length > 10000 || scriptPubKey.getProgram().length > 10000) {
            throw new ScriptException(ScriptError.SCRIPT_ERR_SCRIPT_SIZE, "Script larger than 10,000 bytes");
        }
        LinkedList<byte[]> stack = new LinkedList<byte[]>();
        LinkedList<byte[]> p2shStack = null;
        Script.executeScript(txContainingThis, scriptSigIndex, this, stack, value, verifyFlags);
        if (verifyFlags.contains((Object)VerifyFlag.P2SH)) {
            p2shStack = new LinkedList<byte[]>(stack);
        }
        Script.executeScript(txContainingThis, scriptSigIndex, scriptPubKey, stack, value, verifyFlags);
        if (stack.size() == 0) {
            throw new ScriptException(ScriptError.SCRIPT_ERR_EVAL_FALSE, "Stack empty at end of script execution.");
        }
        if (!Script.castToBool(stack.pollLast())) {
            throw new ScriptException(ScriptError.SCRIPT_ERR_EVAL_FALSE, "Script resulted in a non-true stack: " + stack);
        }
        if (verifyFlags.contains((Object)VerifyFlag.P2SH) && scriptPubKey.isPayToScriptHash()) {
            for (ScriptChunk chunk : this.chunks) {
                if (!chunk.isOpCode() || chunk.opcode <= 96) continue;
                throw new ScriptException(ScriptError.SCRIPT_ERR_SIG_PUSHONLY, "Attempted to spend a P2SH scriptPubKey with a script that contained script ops");
            }
            byte[] scriptPubKeyBytes = p2shStack.pollLast();
            Script scriptPubKeyP2SH = new Script(scriptPubKeyBytes);
            Script.executeScript(txContainingThis, scriptSigIndex, scriptPubKeyP2SH, p2shStack, value, verifyFlags);
            if (p2shStack.size() == 0) {
                throw new ScriptException(ScriptError.SCRIPT_ERR_EVAL_FALSE, "P2SH stack empty at end of script execution.");
            }
            if (!Script.castToBool(p2shStack.pollLast())) {
                throw new ScriptException(ScriptError.SCRIPT_ERR_EVAL_FALSE, "P2SH script execution resulted in a non-true stack");
            }
        }
    }

    private byte[] getQuickProgram() {
        if (this.program != null) {
            return this.program;
        }
        return this.getProgram();
    }

    public ScriptType getScriptType() {
        ScriptType type = ScriptType.NO_TYPE;
        if (this.isSentToAddress()) {
            type = ScriptType.P2PKH;
        } else if (this.isSentToRawPubKey()) {
            type = ScriptType.PUB_KEY;
        } else if (this.isPayToScriptHash()) {
            type = ScriptType.P2SH;
        }
        return type;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        return Arrays.equals(this.getQuickProgram(), ((Script)o).getQuickProgram());
    }

    public int hashCode() {
        return Arrays.hashCode(this.getQuickProgram());
    }

    public static enum VerifyFlag {
        P2SH,
        STRICTENC,
        DERSIG,
        LOW_S,
        NULLDUMMY,
        SIGPUSHONLY,
        MINIMALDATA,
        DISCOURAGE_UPGRADABLE_NOPS,
        CLEANSTACK,
        CHECKLOCKTIMEVERIFY,
        CHECKSEQUENCEVERIFY,
        ENABLESIGHASHFORKID;
        

        private VerifyFlag() {
        }
    }

    public static enum ScriptType {
        NO_TYPE,
        P2PKH,
        PUB_KEY,
        P2SH;
        

        private ScriptType() {
        }
    }

}

