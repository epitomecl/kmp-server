/*
 * Decompiled with CFR 0_132.
 * 
 * Could not load the following classes:
 *  com.google.common.annotations.VisibleForTesting
 *  com.google.common.base.Joiner
 *  com.google.common.base.Preconditions
 *  com.google.common.collect.ImmutableList
 *  javax.annotation.Nullable
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package org.bitcoinj.core;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Joiner;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.EnumSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import javax.annotation.Nullable;
import org.bitcoinj.core.Address;
import org.bitcoinj.core.Coin;
import org.bitcoinj.core.ECKey;
import org.bitcoinj.core.Message;
import org.bitcoinj.core.MessageSerializer;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.core.ProtocolException;
import org.bitcoinj.core.Sha256Hash;
import org.bitcoinj.core.Transaction;
import org.bitcoinj.core.TransactionConfidence;
import org.bitcoinj.core.TransactionInput;
import org.bitcoinj.core.TransactionOutPoint;
import org.bitcoinj.core.TransactionOutput;
import org.bitcoinj.core.UnsafeByteArrayOutputStream;
import org.bitcoinj.core.Utils;
import org.bitcoinj.core.VarInt;
import org.bitcoinj.core.VerificationException;
import org.bitcoinj.script.Script;
import org.bitcoinj.script.ScriptBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Block
extends Message {
    private static final Logger log = LoggerFactory.getLogger(Block.class);
    public static final int HEADER_SIZE = 80;
    static final long ALLOWED_TIME_DRIFT = 7200L;
    public static final int MAX_BLOCK_SIZE = 1000000;
    public static final int MAX_BLOCK_SIGOPS = 20000;
    public static final long EASIEST_DIFFICULTY_TARGET = 545259519L;
    public static final int BLOCK_HEIGHT_UNKNOWN = -1;
    public static final int BLOCK_HEIGHT_GENESIS = 0;
    public static final long BLOCK_VERSION_GENESIS = 1L;
    public static final long BLOCK_VERSION_BIP34 = 2L;
    public static final long BLOCK_VERSION_BIP66 = 3L;
    public static final long BLOCK_VERSION_BIP65 = 4L;
    private long version;
    private Sha256Hash prevBlockHash;
    private Sha256Hash merkleRoot;
    private long time;
    private long difficultyTarget;
    private long nonce;
    @Nullable
    List<Transaction> transactions;
    private Sha256Hash hash;
    protected boolean headerBytesValid;
    protected boolean transactionBytesValid;
    protected int optimalEncodingMessageSize;
    private static BigInteger LARGEST_HASH = BigInteger.ONE.shiftLeft(256);
    private static int txCounter;
    static final byte[] EMPTY_BYTES;
    private static final byte[] pubkeyForTesting;

    Block(NetworkParameters params, long setVersion) {
        super(params);
        this.version = setVersion;
        this.difficultyTarget = 487063544L;
        this.time = System.currentTimeMillis() / 1000L;
        this.prevBlockHash = Sha256Hash.ZERO_HASH;
        this.length = 80;
    }

    @Deprecated
    public Block(NetworkParameters params, byte[] payloadBytes) throws ProtocolException {
        super(params, payloadBytes, 0, params.getDefaultSerializer(), payloadBytes.length);
    }

    public Block(NetworkParameters params, byte[] payloadBytes, MessageSerializer serializer, int length) throws ProtocolException {
        super(params, payloadBytes, 0, serializer, length);
    }

    public Block(NetworkParameters params, byte[] payloadBytes, int offset, MessageSerializer serializer, int length) throws ProtocolException {
        super(params, payloadBytes, offset, serializer, length);
    }

    public Block(NetworkParameters params, byte[] payloadBytes, int offset, @Nullable Message parent, MessageSerializer serializer, int length) throws ProtocolException {
        super(params, payloadBytes, offset, serializer, length);
    }

    public Block(NetworkParameters params, long version, Sha256Hash prevBlockHash, Sha256Hash merkleRoot, long time, long difficultyTarget, long nonce, List<Transaction> transactions) {
        super(params);
        this.version = version;
        this.prevBlockHash = prevBlockHash;
        this.merkleRoot = merkleRoot;
        this.time = time;
        this.difficultyTarget = difficultyTarget;
        this.nonce = nonce;
        this.transactions = new LinkedList<Transaction>();
        this.transactions.addAll(transactions);
    }

    protected void parseTransactions(int transactionsOffset) throws ProtocolException {
        this.cursor = transactionsOffset;
        this.optimalEncodingMessageSize = 80;
        if (this.payload.length == this.cursor) {
            this.transactionBytesValid = false;
            return;
        }
        int numTransactions = (int)this.readVarInt();
        this.optimalEncodingMessageSize += VarInt.sizeOf(numTransactions);
        this.transactions = new ArrayList<Transaction>(numTransactions);
        for (int i = 0; i < numTransactions; ++i) {
            Transaction tx = new Transaction(this.params, this.payload, this.cursor, this, this.serializer, Integer.MIN_VALUE);
            tx.getConfidence().setSource(TransactionConfidence.Source.NETWORK);
            this.transactions.add(tx);
            this.cursor += tx.getMessageSize();
            this.optimalEncodingMessageSize += tx.getOptimalEncodingMessageSize();
        }
        this.transactionBytesValid = this.serializer.isParseRetainMode();
    }

    @Override
    protected void parse() throws ProtocolException {
        this.cursor = this.offset;
        this.version = this.readUint32();
        this.prevBlockHash = this.readHash();
        this.merkleRoot = this.readHash();
        this.time = this.readUint32();
        this.difficultyTarget = this.readUint32();
        this.nonce = this.readUint32();
        this.hash = Sha256Hash.wrapReversed(Sha256Hash.hashTwice(this.payload, this.offset, this.cursor - this.offset));
        this.headerBytesValid = this.serializer.isParseRetainMode();
        this.parseTransactions(this.offset + 80);
        this.length = this.cursor - this.offset;
    }

    public int getOptimalEncodingMessageSize() {
        if (this.optimalEncodingMessageSize != 0) {
            return this.optimalEncodingMessageSize;
        }
        this.optimalEncodingMessageSize = this.bitcoinSerialize().length;
        return this.optimalEncodingMessageSize;
    }

    void writeHeader(OutputStream stream) throws IOException {
        if (this.headerBytesValid && this.payload != null && this.payload.length >= this.offset + 80) {
            stream.write(this.payload, this.offset, 80);
            return;
        }
        Utils.uint32ToByteStreamLE(this.version, stream);
        stream.write(this.prevBlockHash.getReversedBytes());
        stream.write(this.getMerkleRoot().getReversedBytes());
        Utils.uint32ToByteStreamLE(this.time, stream);
        Utils.uint32ToByteStreamLE(this.difficultyTarget, stream);
        Utils.uint32ToByteStreamLE(this.nonce, stream);
    }

    private void writeTransactions(OutputStream stream) throws IOException {
        if (this.transactions == null) {
            return;
        }
        if (this.transactionBytesValid && this.payload != null && this.payload.length >= this.offset + this.length) {
            stream.write(this.payload, this.offset + 80, this.length - 80);
            return;
        }
        if (this.transactions != null) {
            stream.write(new VarInt(this.transactions.size()).encode());
            for (Transaction tx : this.transactions) {
                tx.bitcoinSerialize(stream);
            }
        }
    }

    @Override
    public byte[] bitcoinSerialize() {
        if (this.headerBytesValid && this.transactionBytesValid) {
            Preconditions.checkNotNull((Object)this.payload, (Object)"Bytes should never be null if headerBytesValid && transactionBytesValid");
            if (this.length == this.payload.length) {
                return this.payload;
            }
            byte[] buf = new byte[this.length];
            System.arraycopy(this.payload, this.offset, buf, 0, this.length);
            return buf;
        }
        UnsafeByteArrayOutputStream stream = new UnsafeByteArrayOutputStream(this.length == Integer.MIN_VALUE ? 80 + this.guessTransactionsLength() : this.length);
        try {
            this.writeHeader(stream);
            this.writeTransactions(stream);
        }
        catch (IOException iOException) {
            // empty catch block
        }
        return stream.toByteArray();
    }

    @Override
    protected void bitcoinSerializeToStream(OutputStream stream) throws IOException {
        this.writeHeader(stream);
        this.writeTransactions(stream);
    }

    private int guessTransactionsLength() {
        if (this.transactionBytesValid) {
            return this.payload.length - 80;
        }
        if (this.transactions == null) {
            return 0;
        }
        int len = VarInt.sizeOf(this.transactions.size());
        for (Transaction tx : this.transactions) {
            len += tx.length == Integer.MIN_VALUE ? 255 : tx.length;
        }
        return len;
    }

    @Override
    protected void unCache() {
        this.unCacheTransactions();
    }

    private void unCacheHeader() {
        this.headerBytesValid = false;
        if (!this.transactionBytesValid) {
            this.payload = null;
        }
        this.hash = null;
    }

    private void unCacheTransactions() {
        this.transactionBytesValid = false;
        if (!this.headerBytesValid) {
            this.payload = null;
        }
        this.unCacheHeader();
        this.merkleRoot = null;
    }

    private Sha256Hash calculateHash() {
        try {
            UnsafeByteArrayOutputStream bos = new UnsafeByteArrayOutputStream(80);
            this.writeHeader(bos);
            return Sha256Hash.wrapReversed(Sha256Hash.hashTwice(bos.toByteArray()));
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getHashAsString() {
        return this.getHash().toString();
    }

    @Override
    public Sha256Hash getHash() {
        if (this.hash == null) {
            this.hash = this.calculateHash();
        }
        return this.hash;
    }

    public BigInteger getWork() throws VerificationException {
        BigInteger target = this.getDifficultyTargetAsInteger();
        return LARGEST_HASH.divide(target.add(BigInteger.ONE));
    }

    public Block cloneAsHeader() {
        Block block = new Block(this.params, 1L);
        this.copyBitcoinHeaderTo(block);
        return block;
    }

    protected final void copyBitcoinHeaderTo(Block block) {
        block.nonce = this.nonce;
        block.prevBlockHash = this.prevBlockHash;
        block.merkleRoot = this.getMerkleRoot();
        block.version = this.version;
        block.time = this.time;
        block.difficultyTarget = this.difficultyTarget;
        block.transactions = null;
        block.hash = this.getHash();
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(" block: \n");
        s.append("   hash: ").append(this.getHashAsString()).append('\n');
        s.append("   version: ").append(this.version);
        Object[] arrobject = new Object[1];
        arrobject[0] = this.isBIP65() ? "BIP65" : null;
        String bips = Joiner.on((String)", ").skipNulls().join((Object)(this.isBIP34() ? "BIP34" : null), (Object)(this.isBIP66() ? "BIP66" : null), arrobject);
        if (!bips.isEmpty()) {
            s.append(" (").append(bips).append(')');
        }
        s.append('\n');
        s.append("   previous block: ").append(this.getPrevBlockHash()).append("\n");
        s.append("   merkle root: ").append(this.getMerkleRoot()).append("\n");
        s.append("   time: ").append(this.time).append(" (").append(Utils.dateTimeFormat(this.time * 1000L)).append(")\n");
        s.append("   difficulty target (nBits): ").append(this.difficultyTarget).append("\n");
        s.append("   nonce: ").append(this.nonce).append("\n");
        if (this.transactions != null && this.transactions.size() > 0) {
            s.append("   with ").append(this.transactions.size()).append(" transaction(s):\n");
            for (Transaction tx : this.transactions) {
                s.append(tx);
            }
        }
        return s.toString();
    }

    public void solve() {
        try {
            do {
                if (this.checkProofOfWork(false)) {
                    return;
                }
                this.setNonce(this.getNonce() + 1L);
            } while (true);
        }
        catch (VerificationException e) {
            throw new RuntimeException(e);
        }
    }

    public BigInteger getDifficultyTargetAsInteger() throws VerificationException {
        BigInteger target = Utils.decodeCompactBits(this.difficultyTarget);
        if (target.signum() <= 0 || target.compareTo(this.params.maxTarget) > 0) {
            throw new VerificationException("Difficulty target is bad: " + target.toString());
        }
        return target;
    }

    protected boolean checkProofOfWork(boolean throwException) throws VerificationException {
        BigInteger target = this.getDifficultyTargetAsInteger();
        BigInteger h = this.getHash().toBigInteger();
        if (h.compareTo(target) > 0) {
            if (throwException) {
                throw new VerificationException("Hash is higher than target: " + this.getHashAsString() + " vs " + target.toString(16));
            }
            return false;
        }
        return true;
    }

    private void checkTimestamp() throws VerificationException {
        long allowedTime = Utils.currentTimeSeconds() + 7200L;
        if (this.time > allowedTime) {
            throw new VerificationException(String.format(Locale.US, "Block too far in future: %s (%d) vs allowed %s (%d)", Utils.dateTimeFormat(this.time * 1000L), this.time, Utils.dateTimeFormat(allowedTime * 1000L), allowedTime));
        }
    }

    private void checkSigOps() throws VerificationException {
        int sigOps = 0;
        for (Transaction tx : this.transactions) {
            sigOps += tx.getSigOpCount();
        }
        if (sigOps > 20000) {
            throw new VerificationException("Block had too many Signature Operations");
        }
    }

    private void checkMerkleRoot() throws VerificationException {
        Sha256Hash calculatedRoot = this.calculateMerkleRoot();
        if (!calculatedRoot.equals(this.merkleRoot)) {
            log.error("Merkle tree did not verify");
            throw new VerificationException("Merkle hashes do not match: " + calculatedRoot + " vs " + this.merkleRoot);
        }
    }

    private Sha256Hash calculateMerkleRoot() {
        List<byte[]> tree = this.buildMerkleTree();
        return Sha256Hash.wrap(tree.get(tree.size() - 1));
    }

    private List<byte[]> buildMerkleTree() {
        ArrayList<byte[]> tree = new ArrayList<byte[]>();
        for (Transaction t : this.transactions) {
            tree.add(t.getHash().getBytes());
        }
        int levelOffset = 0;
        int levelSize = this.transactions.size();
        while (levelSize > 1) {
            for (int left = 0; left < levelSize; left += 2) {
                int right = Math.min(left + 1, levelSize - 1);
                byte[] leftBytes = Utils.reverseBytes(tree.get(levelOffset + left));
                byte[] rightBytes = Utils.reverseBytes(tree.get(levelOffset + right));
                tree.add(Utils.reverseBytes(Sha256Hash.hashTwice(leftBytes, 0, 32, rightBytes, 0, 32)));
            }
            levelOffset += levelSize;
            levelSize = (levelSize + 1) / 2;
        }
        return tree;
    }

    private void checkTransactions(int height, EnumSet<VerifyFlag> flags) throws VerificationException {
        if (!this.transactions.get(0).isCoinBase()) {
            throw new VerificationException("First tx is not coinbase");
        }
        if (flags.contains((Object)VerifyFlag.HEIGHT_IN_COINBASE) && height >= 0) {
            this.transactions.get(0).checkCoinBaseHeight(height);
        }
        for (int i = 1; i < this.transactions.size(); ++i) {
            if (!this.transactions.get(i).isCoinBase()) continue;
            throw new VerificationException("TX " + i + " is coinbase when it should not be.");
        }
    }

    public void verifyHeader() throws VerificationException {
        this.checkProofOfWork(true);
        this.checkTimestamp();
    }

    public void verifyTransactions(int height, EnumSet<VerifyFlag> flags) throws VerificationException {
        if (this.transactions.isEmpty()) {
            throw new VerificationException("Block had no transactions");
        }
        if (this.getOptimalEncodingMessageSize() > 1000000) {
            throw new VerificationException("Block larger than MAX_BLOCK_SIZE");
        }
        this.checkTransactions(height, flags);
        this.checkMerkleRoot();
        this.checkSigOps();
        for (Transaction transaction : this.transactions) {
            transaction.verify();
        }
    }

    public void verify(int height, EnumSet<VerifyFlag> flags) throws VerificationException {
        this.verifyHeader();
        this.verifyTransactions(height, flags);
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        return this.getHash().equals(((Block)o).getHash());
    }

    public int hashCode() {
        return this.getHash().hashCode();
    }

    public Sha256Hash getMerkleRoot() {
        if (this.merkleRoot == null) {
            this.unCacheHeader();
            this.merkleRoot = this.calculateMerkleRoot();
        }
        return this.merkleRoot;
    }

    void setMerkleRoot(Sha256Hash value) {
        this.unCacheHeader();
        this.merkleRoot = value;
        this.hash = null;
    }

    public void addTransaction(Transaction t) {
        this.addTransaction(t, true);
    }

    void addTransaction(Transaction t, boolean runSanityChecks) {
        this.unCacheTransactions();
        if (this.transactions == null) {
            this.transactions = new ArrayList<Transaction>();
        }
        t.setParent(this);
        if (runSanityChecks && this.transactions.size() == 0 && !t.isCoinBase()) {
            throw new RuntimeException("Attempted to add a non-coinbase transaction as the first transaction: " + t);
        }
        if (runSanityChecks && this.transactions.size() > 0 && t.isCoinBase()) {
            throw new RuntimeException("Attempted to add a coinbase transaction when there already is one: " + t);
        }
        this.transactions.add(t);
        this.adjustLength(this.transactions.size(), t.length);
        this.merkleRoot = null;
        this.hash = null;
    }

    public long getVersion() {
        return this.version;
    }

    public Sha256Hash getPrevBlockHash() {
        return this.prevBlockHash;
    }

    void setPrevBlockHash(Sha256Hash prevBlockHash) {
        this.unCacheHeader();
        this.prevBlockHash = prevBlockHash;
        this.hash = null;
    }

    public long getTimeSeconds() {
        return this.time;
    }

    public Date getTime() {
        return new Date(this.getTimeSeconds() * 1000L);
    }

    public void setTime(long time) {
        this.unCacheHeader();
        this.time = time;
        this.hash = null;
    }

    public long getDifficultyTarget() {
        return this.difficultyTarget;
    }

    public void setDifficultyTarget(long compactForm) {
        this.unCacheHeader();
        this.difficultyTarget = compactForm;
        this.hash = null;
    }

    public long getNonce() {
        return this.nonce;
    }

    public void setNonce(long nonce) {
        this.unCacheHeader();
        this.nonce = nonce;
        this.hash = null;
    }

    @Nullable
    public List<Transaction> getTransactions() {
        return this.transactions == null ? null : ImmutableList.copyOf(this.transactions);
    }

    @VisibleForTesting
    void addCoinbaseTransaction(byte[] pubKeyTo, Coin value, int height) {
        this.unCacheTransactions();
        this.transactions = new ArrayList<Transaction>();
        Transaction coinbase = new Transaction(this.params);
        ScriptBuilder inputBuilder = new ScriptBuilder();
        if (height >= 0) {
            inputBuilder.number(height);
        }
        byte[] arrby = new byte[]{(byte)txCounter, (byte)(txCounter++ >> 8)};
        inputBuilder.data(arrby);
        coinbase.addInput(new TransactionInput(this.params, coinbase, inputBuilder.build().getProgram()));
        coinbase.addOutput(new TransactionOutput(this.params, coinbase, value, ScriptBuilder.createOutputScript(ECKey.fromPublicOnly(pubKeyTo)).getProgram()));
        this.transactions.add(coinbase);
        coinbase.setParent(this);
        coinbase.length = coinbase.unsafeBitcoinSerialize().length;
        this.adjustLength(this.transactions.size(), coinbase.length);
    }

    @VisibleForTesting
    public Block createNextBlock(Address to, long version, long time, int blockHeight) {
        return this.createNextBlock(to, version, null, time, pubkeyForTesting, Coin.FIFTY_COINS, blockHeight);
    }

    Block createNextBlock(@Nullable Address to, long version, @Nullable TransactionOutPoint prevOut, long time, byte[] pubKey, Coin coinbaseValue, int height) {
        Block b = new Block(this.params, version);
        b.setDifficultyTarget(this.difficultyTarget);
        b.addCoinbaseTransaction(pubKey, coinbaseValue, height);
        if (to != null) {
            TransactionInput input;
            Transaction t = new Transaction(this.params);
            t.addOutput(new TransactionOutput(this.params, t, Coin.FIFTY_COINS, to));
            if (prevOut == null) {
                input = new TransactionInput(this.params, t, Script.createInputScript(EMPTY_BYTES, EMPTY_BYTES));
                byte[] counter = new byte[32];
                counter[0] = (byte)txCounter;
                counter[1] = (byte)(txCounter++ >> 8);
                input.getOutpoint().setHash(Sha256Hash.wrap(counter));
            } else {
                input = new TransactionInput(this.params, t, Script.createInputScript(EMPTY_BYTES, EMPTY_BYTES), prevOut);
            }
            t.addInput(input);
            b.addTransaction(t);
        }
        b.setPrevBlockHash(this.getHash());
        if (this.getTimeSeconds() >= time) {
            b.setTime(this.getTimeSeconds() + 1L);
        } else {
            b.setTime(time);
        }
        b.solve();
        try {
            b.verifyHeader();
        }
        catch (VerificationException e) {
            throw new RuntimeException(e);
        }
        if (b.getVersion() != version) {
            throw new RuntimeException();
        }
        return b;
    }

    @VisibleForTesting
    public Block createNextBlock(@Nullable Address to, TransactionOutPoint prevOut) {
        return this.createNextBlock(to, 1L, prevOut, this.getTimeSeconds() + 5L, pubkeyForTesting, Coin.FIFTY_COINS, -1);
    }

    @VisibleForTesting
    public Block createNextBlock(@Nullable Address to, Coin value) {
        return this.createNextBlock(to, 1L, null, this.getTimeSeconds() + 5L, pubkeyForTesting, value, -1);
    }

    @VisibleForTesting
    public Block createNextBlock(@Nullable Address to) {
        return this.createNextBlock(to, Coin.FIFTY_COINS);
    }

    @VisibleForTesting
    public Block createNextBlockWithCoinbase(long version, byte[] pubKey, Coin coinbaseValue, int height) {
        return this.createNextBlock(null, version, null, Utils.currentTimeSeconds(), pubKey, coinbaseValue, height);
    }

    @VisibleForTesting
    Block createNextBlockWithCoinbase(long version, byte[] pubKey, int height) {
        return this.createNextBlock(null, version, null, Utils.currentTimeSeconds(), pubKey, Coin.FIFTY_COINS, height);
    }

    @VisibleForTesting
    boolean isHeaderBytesValid() {
        return this.headerBytesValid;
    }

    @VisibleForTesting
    boolean isTransactionBytesValid() {
        return this.transactionBytesValid;
    }

    public boolean hasTransactions() {
        return !this.transactions.isEmpty();
    }

    public boolean isBIP34() {
        return this.version >= 2L;
    }

    public boolean isBIP66() {
        return this.version >= 3L;
    }

    public boolean isBIP65() {
        return this.version >= 4L;
    }

    static {
        EMPTY_BYTES = new byte[32];
        pubkeyForTesting = new ECKey().getPubKey();
    }

    public static enum VerifyFlag {
        HEIGHT_IN_COINBASE;
        

        private VerifyFlag() {
        }
    }

}

