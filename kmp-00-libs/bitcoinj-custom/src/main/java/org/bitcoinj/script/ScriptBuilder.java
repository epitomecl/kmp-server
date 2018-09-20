/*
 * Decompiled with CFR 0_132.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Preconditions
 *  com.google.common.collect.Lists
 *  javax.annotation.Nullable
 */
package org.bitcoinj.script;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;
import javax.annotation.Nullable;
import org.bitcoinj.core.Address;
import org.bitcoinj.core.ECKey;
import org.bitcoinj.core.Utils;
import org.bitcoinj.crypto.TransactionSignature;
import org.bitcoinj.script.Script;
import org.bitcoinj.script.ScriptChunk;

public class ScriptBuilder {
    private List<ScriptChunk> chunks;

    public ScriptBuilder() {
        this.chunks = Lists.newLinkedList();
    }

    public ScriptBuilder(Script template) {
        this.chunks = new ArrayList<ScriptChunk>(template.getChunks());
    }

    public ScriptBuilder addChunk(ScriptChunk chunk) {
        return this.addChunk(this.chunks.size(), chunk);
    }

    public ScriptBuilder addChunk(int index, ScriptChunk chunk) {
        this.chunks.add(index, chunk);
        return this;
    }

    public ScriptBuilder op(int opcode) {
        return this.op(this.chunks.size(), opcode);
    }

    public ScriptBuilder op(int index, int opcode) {
        Preconditions.checkArgument((boolean)(opcode > 78));
        return this.addChunk(index, new ScriptChunk(opcode, null));
    }

    public ScriptBuilder data(byte[] data) {
        if (data.length == 0) {
            return this.smallNum(0);
        }
        return this.data(this.chunks.size(), data);
    }

    public ScriptBuilder data(int index, byte[] data) {
        int opcode;
        byte[] copy = Arrays.copyOf(data, data.length);
        if (data.length == 0) {
            opcode = 0;
        } else if (data.length == 1) {
            byte b = data[0];
            opcode = b >= 1 && b <= 16 ? Script.encodeToOpN(b) : 1;
        } else if (data.length < 76) {
            opcode = data.length;
        } else if (data.length < 256) {
            opcode = 76;
        } else if (data.length < 65536) {
            opcode = 77;
        } else {
            throw new RuntimeException("Unimplemented");
        }
        return this.addChunk(index, new ScriptChunk(opcode, copy));
    }

    public ScriptBuilder number(long num) {
        return this.number(this.chunks.size(), num);
    }

    public ScriptBuilder number(int index, long num) {
        if (num == -1L) {
            return this.op(index, 79);
        }
        if (num >= 0L && num <= 16L) {
            return this.smallNum(index, (int)num);
        }
        return this.bigNum(index, num);
    }

    public ScriptBuilder smallNum(int num) {
        return this.smallNum(this.chunks.size(), num);
    }

    protected ScriptBuilder bigNum(long num) {
        return this.bigNum(this.chunks.size(), num);
    }

    public ScriptBuilder smallNum(int index, int num) {
        Preconditions.checkArgument((boolean)(num >= 0), (Object)"Cannot encode negative numbers with smallNum");
        Preconditions.checkArgument((boolean)(num <= 16), (Object)"Cannot encode numbers larger than 16 with smallNum");
        return this.addChunk(index, new ScriptChunk(Script.encodeToOpN(num), null));
    }

    protected ScriptBuilder bigNum(int index, long num) {
        byte[] data;
        if (num == 0L) {
            data = new byte[]{};
        } else {
            Stack<Byte> result = new Stack<Byte>();
            boolean neg = num < 0L;
            for (long absvalue = java.lang.Math.abs((long)num); absvalue != 0L; absvalue >>= 8) {
                result.push((byte)(absvalue & 255L));
            }
            if (((Byte)result.peek() & 128) != 0) {
                result.push((byte)(neg ? 128 : 0));
            } else if (neg) {
                result.push((byte)((Byte)result.pop() | 128));
            }
            data = new byte[result.size()];
            for (int byteIdx = 0; byteIdx < data.length; ++byteIdx) {
                data[byteIdx] = (Byte)result.get(byteIdx);
            }
        }
        return this.addChunk(index, new ScriptChunk(data.length, data));
    }

    public Script build() {
        return new Script(this.chunks);
    }

    public static Script createOutputScript(Address to) {
        if (to.isP2SHAddress()) {
            return new ScriptBuilder().op(169).data(to.getHash160()).op(135).build();
        }
        return new ScriptBuilder().op(118).op(169).data(to.getHash160()).op(136).op(172).build();
    }

    public static Script createOutputScript(ECKey key) {
        return new ScriptBuilder().data(key.getPubKey()).op(172).build();
    }

    public static Script createInputScript(@Nullable TransactionSignature signature, ECKey pubKey) {
        byte[] pubkeyBytes = pubKey.getPubKey();
        byte[] sigBytes = signature != null ? signature.encodeToBitcoin() : new byte[]{};
        return new ScriptBuilder().data(sigBytes).data(pubkeyBytes).build();
    }

    public static Script createInputScript(@Nullable TransactionSignature signature) {
        byte[] sigBytes = signature != null ? signature.encodeToBitcoin() : new byte[]{};
        return new ScriptBuilder().data(sigBytes).build();
    }

    public static Script createMultiSigOutputScript(int threshold, List<ECKey> pubkeys) {
        Preconditions.checkArgument((boolean)(threshold > 0));
        Preconditions.checkArgument((boolean)(threshold <= pubkeys.size()));
        Preconditions.checkArgument((boolean)(pubkeys.size() <= 16));
        ScriptBuilder builder = new ScriptBuilder();
        builder.smallNum(threshold);
        for (ECKey key : pubkeys) {
            builder.data(key.getPubKey());
        }
        builder.smallNum(pubkeys.size());
        builder.op(174);
        return builder.build();
    }

    public static Script createMultiSigInputScript(List<TransactionSignature> signatures) {
        ArrayList<byte[]> sigs = new ArrayList<byte[]>(signatures.size());
        for (TransactionSignature signature : signatures) {
            sigs.add(signature.encodeToBitcoin());
        }
        return ScriptBuilder.createMultiSigInputScriptBytes(sigs, null);
    }

    public static /* varargs */ Script createMultiSigInputScript(TransactionSignature ... signatures) {
        return ScriptBuilder.createMultiSigInputScript(Arrays.asList(signatures));
    }

    public static Script createMultiSigInputScriptBytes(List<byte[]> signatures) {
        return ScriptBuilder.createMultiSigInputScriptBytes(signatures, null);
    }

    public static Script createP2SHMultiSigInputScript(@Nullable List<TransactionSignature> signatures, Script multisigProgram) {
        ArrayList<byte[]> sigs;
        sigs = new ArrayList<byte[]>();
        if (signatures == null) {
            int numSigs = multisigProgram.getNumberOfSignaturesRequiredToSpend();
            for (int i = 0; i < numSigs; ++i) {
                sigs.add(new byte[0]);
            }
        } else {
            for (TransactionSignature signature : signatures) {
                sigs.add(signature.encodeToBitcoin());
            }
        }
        return ScriptBuilder.createMultiSigInputScriptBytes(sigs, multisigProgram.getProgram());
    }

    public static Script createMultiSigInputScriptBytes(List<byte[]> signatures, @Nullable byte[] multisigProgramBytes) {
        Preconditions.checkArgument((boolean)(signatures.size() <= 16));
        ScriptBuilder builder = new ScriptBuilder();
        builder.smallNum(0);
        for (byte[] signature : signatures) {
            builder.data(signature);
        }
        if (multisigProgramBytes != null) {
            builder.data(multisigProgramBytes);
        }
        return builder.build();
    }

    public static Script updateScriptWithSignature(Script scriptSig, byte[] signature, int targetIndex, int sigsPrefixCount, int sigsSuffixCount) {
        ScriptBuilder builder = new ScriptBuilder();
        List<ScriptChunk> inputChunks = scriptSig.getChunks();
        int totalChunks = inputChunks.size();
        boolean hasMissingSigs = inputChunks.get(totalChunks - sigsSuffixCount - 1).equalsOpCode(0);
        Preconditions.checkArgument((boolean)hasMissingSigs, (Object)"ScriptSig is already filled with signatures");
        for (ScriptChunk chunk : inputChunks.subList(0, sigsPrefixCount)) {
            builder.addChunk(chunk);
        }
        int pos = 0;
        boolean inserted = false;
        for (ScriptChunk chunk : inputChunks.subList(sigsPrefixCount, totalChunks - sigsSuffixCount)) {
            if (pos == targetIndex) {
                inserted = true;
                builder.data(signature);
                ++pos;
            }
            if (chunk.equalsOpCode(0)) continue;
            builder.addChunk(chunk);
            ++pos;
        }
        while (pos < totalChunks - sigsPrefixCount - sigsSuffixCount) {
            if (pos == targetIndex) {
                inserted = true;
                builder.data(signature);
            } else {
                builder.addChunk(new ScriptChunk(0, null));
            }
            ++pos;
        }
        for (ScriptChunk chunk : inputChunks.subList(totalChunks - sigsSuffixCount, totalChunks)) {
            builder.addChunk(chunk);
        }
        Preconditions.checkState((boolean)inserted);
        return builder.build();
    }

    public static Script createP2SHOutputScript(byte[] hash) {
        Preconditions.checkArgument((boolean)(hash.length == 20));
        return new ScriptBuilder().op(169).data(hash).op(135).build();
    }

    public static Script createP2SHOutputScript(Script redeemScript) {
        byte[] hash = Utils.sha256hash160(redeemScript.getProgram());
        return ScriptBuilder.createP2SHOutputScript(hash);
    }

    public static Script createP2SHOutputScript(int threshold, List<ECKey> pubkeys) {
        Script redeemScript = ScriptBuilder.createRedeemScript(threshold, pubkeys);
        return ScriptBuilder.createP2SHOutputScript(redeemScript);
    }

    public static Script createRedeemScript(int threshold, List<ECKey> pubkeys) {
        pubkeys = new ArrayList<ECKey>(pubkeys);
        Collections.sort(pubkeys, ECKey.PUBKEY_COMPARATOR);
        return ScriptBuilder.createMultiSigOutputScript(threshold, pubkeys);
    }

    public static Script createOpReturnScript(byte[] data) {
        Preconditions.checkArgument((boolean)(data.length <= 80));
        return new ScriptBuilder().op(106).data(data).build();
    }

    public static Script createCLTVPaymentChannelOutput(BigInteger time, ECKey from, ECKey to) {
        byte[] timeBytes = Utils.reverseBytes(Utils.encodeMPI(time, false));
        if (timeBytes.length > 5) {
            throw new RuntimeException("Time too large to encode as 5-byte int");
        }
        return new ScriptBuilder().op(99).data(to.getPubKey()).op(173).op(103).data(timeBytes).op(177).op(117).op(104).data(from.getPubKey()).op(172).build();
    }

    public static Script createCLTVPaymentChannelRefund(TransactionSignature signature) {
        ScriptBuilder builder = new ScriptBuilder();
        builder.data(signature.encodeToBitcoin());
        builder.data(new byte[]{0});
        return builder.build();
    }

    public static Script createCLTVPaymentChannelP2SHRefund(TransactionSignature signature, Script redeemScript) {
        ScriptBuilder builder = new ScriptBuilder();
        builder.data(signature.encodeToBitcoin());
        builder.data(new byte[]{0});
        builder.data(redeemScript.getProgram());
        return builder.build();
    }

    public static Script createCLTVPaymentChannelP2SHInput(byte[] from, byte[] to, Script redeemScript) {
        ScriptBuilder builder = new ScriptBuilder();
        builder.data(from);
        builder.data(to);
        builder.smallNum(1);
        builder.data(redeemScript.getProgram());
        return builder.build();
    }

    public static Script createCLTVPaymentChannelInput(TransactionSignature from, TransactionSignature to) {
        return ScriptBuilder.createCLTVPaymentChannelInput(from.encodeToBitcoin(), to.encodeToBitcoin());
    }

    public static Script createCLTVPaymentChannelInput(byte[] from, byte[] to) {
        ScriptBuilder builder = new ScriptBuilder();
        builder.data(from);
        builder.data(to);
        builder.smallNum(1);
        return builder.build();
    }
}

