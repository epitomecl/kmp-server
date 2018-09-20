/*
 * Decompiled with CFR 0_132.
 * 
 * Could not load the following classes:
 *  com.google.protobuf.AbstractMessage
 *  com.google.protobuf.AbstractMessage$Builder
 *  com.google.protobuf.AbstractMessageLite
 *  com.google.protobuf.AbstractMessageLite$Builder
 *  com.google.protobuf.AbstractParser
 *  com.google.protobuf.ByteString
 *  com.google.protobuf.CodedInputStream
 *  com.google.protobuf.CodedOutputStream
 *  com.google.protobuf.Descriptors
 *  com.google.protobuf.Descriptors$Descriptor
 *  com.google.protobuf.Descriptors$FileDescriptor
 *  com.google.protobuf.Descriptors$FileDescriptor$InternalDescriptorAssigner
 *  com.google.protobuf.ExtensionRegistry
 *  com.google.protobuf.ExtensionRegistryLite
 *  com.google.protobuf.GeneratedMessage
 *  com.google.protobuf.GeneratedMessage$Builder
 *  com.google.protobuf.GeneratedMessage$BuilderParent
 *  com.google.protobuf.GeneratedMessage$FieldAccessorTable
 *  com.google.protobuf.InvalidProtocolBufferException
 *  com.google.protobuf.Message
 *  com.google.protobuf.Message$Builder
 *  com.google.protobuf.MessageLite
 *  com.google.protobuf.MessageLite$Builder
 *  com.google.protobuf.MessageOrBuilder
 *  com.google.protobuf.Parser
 *  com.google.protobuf.RepeatedFieldBuilder
 *  com.google.protobuf.UnknownFieldSet
 *  com.google.protobuf.UnknownFieldSet$Builder
 */
package org.bitcoin.crawler;

import com.google.protobuf.AbstractMessage;
import com.google.protobuf.AbstractMessageLite;
import com.google.protobuf.AbstractParser;
import com.google.protobuf.ByteString;
import com.google.protobuf.CodedInputStream;
import com.google.protobuf.CodedOutputStream;
import com.google.protobuf.Descriptors;
import com.google.protobuf.ExtensionRegistry;
import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.GeneratedMessage;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Message;
import com.google.protobuf.MessageLite;
import com.google.protobuf.MessageOrBuilder;
import com.google.protobuf.Parser;
import com.google.protobuf.RepeatedFieldBuilder;
import com.google.protobuf.UnknownFieldSet;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectStreamException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public final class PeerSeedProtos {
    private static final Descriptors.Descriptor internal_static_org_bitcoin_crawler_PeerSeedData_descriptor;
    private static GeneratedMessage.FieldAccessorTable internal_static_org_bitcoin_crawler_PeerSeedData_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_org_bitcoin_crawler_PeerSeeds_descriptor;
    private static GeneratedMessage.FieldAccessorTable internal_static_org_bitcoin_crawler_PeerSeeds_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_org_bitcoin_crawler_SignedPeerSeeds_descriptor;
    private static GeneratedMessage.FieldAccessorTable internal_static_org_bitcoin_crawler_SignedPeerSeeds_fieldAccessorTable;
    private static Descriptors.FileDescriptor descriptor;

    private PeerSeedProtos() {
    }

    public static void registerAllExtensions(ExtensionRegistry registry) {
    }

    public static Descriptors.FileDescriptor getDescriptor() {
        return descriptor;
    }

    static {
        String[] descriptorData = new String[]{"\n\u000fpeerseeds.proto\u0012\u0013org.bitcoin.crawler\"B\n\fPeerSeedData\u0012\u0012\n\nip_address\u0018\u0001 \u0002(\t\u0012\f\n\u0004port\u0018\u0002 \u0002(\r\u0012\u0010\n\bservices\u0018\u0003 \u0002(\r\"\\\n\tPeerSeeds\u0012/\n\u0004seed\u0018\u0001 \u0003(\u000b2!.org.bitcoin.crawler.PeerSeedData\u0012\u0011\n\ttimestamp\u0018\u0002 \u0002(\u0004\u0012\u000b\n\u0003net\u0018\u0003 \u0002(\t\"H\n\u000fSignedPeerSeeds\u0012\u0012\n\npeer_seeds\u0018\u0001 \u0002(\f\u0012\u0011\n\tsignature\u0018\u0002 \u0002(\f\u0012\u000e\n\u0006pubkey\u0018\u0003 \u0002(\fB%\n\u0013org.bitcoin.crawlerB\u000ePeerSeedProtos"};
        Descriptors.FileDescriptor.InternalDescriptorAssigner assigner = new Descriptors.FileDescriptor.InternalDescriptorAssigner(){

            public ExtensionRegistry assignDescriptors(Descriptors.FileDescriptor root) {
                descriptor = root;
                return null;
            }
        };
        Descriptors.FileDescriptor.internalBuildGeneratedFileFrom((String[])descriptorData, (Descriptors.FileDescriptor[])new Descriptors.FileDescriptor[0], (Descriptors.FileDescriptor.InternalDescriptorAssigner)assigner);
        internal_static_org_bitcoin_crawler_PeerSeedData_descriptor = (Descriptors.Descriptor)PeerSeedProtos.getDescriptor().getMessageTypes().get(0);
        internal_static_org_bitcoin_crawler_PeerSeedData_fieldAccessorTable = new GeneratedMessage.FieldAccessorTable(internal_static_org_bitcoin_crawler_PeerSeedData_descriptor, new String[]{"IpAddress", "Port", "Services"});
        internal_static_org_bitcoin_crawler_PeerSeeds_descriptor = (Descriptors.Descriptor)PeerSeedProtos.getDescriptor().getMessageTypes().get(1);
        internal_static_org_bitcoin_crawler_PeerSeeds_fieldAccessorTable = new GeneratedMessage.FieldAccessorTable(internal_static_org_bitcoin_crawler_PeerSeeds_descriptor, new String[]{"Seed", "Timestamp", "Net"});
        internal_static_org_bitcoin_crawler_SignedPeerSeeds_descriptor = (Descriptors.Descriptor)PeerSeedProtos.getDescriptor().getMessageTypes().get(2);
        internal_static_org_bitcoin_crawler_SignedPeerSeeds_fieldAccessorTable = new GeneratedMessage.FieldAccessorTable(internal_static_org_bitcoin_crawler_SignedPeerSeeds_descriptor, new String[]{"PeerSeeds", "Signature", "Pubkey"});
    }

    public static final class SignedPeerSeeds
    extends GeneratedMessage
    implements SignedPeerSeedsOrBuilder {
        private static final SignedPeerSeeds defaultInstance;
        private final UnknownFieldSet unknownFields;
        public static Parser<SignedPeerSeeds> PARSER;
        private int bitField0_;
        public static final int PEER_SEEDS_FIELD_NUMBER = 1;
        private ByteString peerSeeds_;
        public static final int SIGNATURE_FIELD_NUMBER = 2;
        private ByteString signature_;
        public static final int PUBKEY_FIELD_NUMBER = 3;
        private ByteString pubkey_;
        private byte memoizedIsInitialized;
        private int memoizedSerializedSize;
        private static final long serialVersionUID = 0L;

        private SignedPeerSeeds(GeneratedMessage.Builder<?> builder) {
            super(builder);
            this.memoizedIsInitialized = (byte)-1;
            this.memoizedSerializedSize = -1;
            this.unknownFields = builder.getUnknownFields();
        }

        private SignedPeerSeeds(boolean noInit) {
            this.memoizedIsInitialized = (byte)-1;
            this.memoizedSerializedSize = -1;
            this.unknownFields = UnknownFieldSet.getDefaultInstance();
        }

        public static SignedPeerSeeds getDefaultInstance() {
            return defaultInstance;
        }

        public SignedPeerSeeds getDefaultInstanceForType() {
            return defaultInstance;
        }

        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private SignedPeerSeeds(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            this.memoizedIsInitialized = (byte)-1;
            this.memoizedSerializedSize = -1;
            this.initFields();
            boolean mutable_bitField0_ = false;
            UnknownFieldSet.Builder unknownFields = UnknownFieldSet.newBuilder();
            try {
                boolean done = false;
                block12 : while (!done) {
                    int tag = input.readTag();
                    switch (tag) {
                        case 0: {
                            done = true;
                            continue block12;
                        }
                        default: {
                            if (this.parseUnknownField(input, unknownFields, extensionRegistry, tag)) continue block12;
                            done = true;
                            continue block12;
                        }
                        case 10: {
                            this.bitField0_ |= 1;
                            this.peerSeeds_ = input.readBytes();
                            continue block12;
                        }
                        case 18: {
                            this.bitField0_ |= 2;
                            this.signature_ = input.readBytes();
                            continue block12;
                        }
                        case 26: 
                    }
                    this.bitField0_ |= 4;
                    this.pubkey_ = input.readBytes();
                }
            }
            catch (InvalidProtocolBufferException e) {
                throw e.setUnfinishedMessage((MessageLite)this);
            }
            catch (IOException e) {
                throw new InvalidProtocolBufferException(e.getMessage()).setUnfinishedMessage((MessageLite)this);
            }
            finally {
                this.unknownFields = unknownFields.build();
                this.makeExtensionsImmutable();
            }
        }

        public static final Descriptors.Descriptor getDescriptor() {
            return internal_static_org_bitcoin_crawler_SignedPeerSeeds_descriptor;
        }

        protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
            return internal_static_org_bitcoin_crawler_SignedPeerSeeds_fieldAccessorTable.ensureFieldAccessorsInitialized(SignedPeerSeeds.class, Builder.class);
        }

        public Parser<SignedPeerSeeds> getParserForType() {
            return PARSER;
        }

        @Override
        public boolean hasPeerSeeds() {
            return (this.bitField0_ & 1) == 1;
        }

        @Override
        public ByteString getPeerSeeds() {
            return this.peerSeeds_;
        }

        @Override
        public boolean hasSignature() {
            return (this.bitField0_ & 2) == 2;
        }

        @Override
        public ByteString getSignature() {
            return this.signature_;
        }

        @Override
        public boolean hasPubkey() {
            return (this.bitField0_ & 4) == 4;
        }

        @Override
        public ByteString getPubkey() {
            return this.pubkey_;
        }

        private void initFields() {
            this.peerSeeds_ = ByteString.EMPTY;
            this.signature_ = ByteString.EMPTY;
            this.pubkey_ = ByteString.EMPTY;
        }

        public final boolean isInitialized() {
            byte isInitialized = this.memoizedIsInitialized;
            if (isInitialized == 1) {
                return true;
            }
            if (isInitialized == 0) {
                return false;
            }
            if (!this.hasPeerSeeds()) {
                this.memoizedIsInitialized = 0;
                return false;
            }
            if (!this.hasSignature()) {
                this.memoizedIsInitialized = 0;
                return false;
            }
            if (!this.hasPubkey()) {
                this.memoizedIsInitialized = 0;
                return false;
            }
            this.memoizedIsInitialized = 1;
            return true;
        }

        public void writeTo(CodedOutputStream output) throws IOException {
            this.getSerializedSize();
            if ((this.bitField0_ & 1) == 1) {
                output.writeBytes(1, this.peerSeeds_);
            }
            if ((this.bitField0_ & 2) == 2) {
                output.writeBytes(2, this.signature_);
            }
            if ((this.bitField0_ & 4) == 4) {
                output.writeBytes(3, this.pubkey_);
            }
            this.getUnknownFields().writeTo(output);
        }

        public int getSerializedSize() {
            int size = this.memoizedSerializedSize;
            if (size != -1) {
                return size;
            }
            size = 0;
            if ((this.bitField0_ & 1) == 1) {
                size += CodedOutputStream.computeBytesSize((int)1, (ByteString)this.peerSeeds_);
            }
            if ((this.bitField0_ & 2) == 2) {
                size += CodedOutputStream.computeBytesSize((int)2, (ByteString)this.signature_);
            }
            if ((this.bitField0_ & 4) == 4) {
                size += CodedOutputStream.computeBytesSize((int)3, (ByteString)this.pubkey_);
            }
            this.memoizedSerializedSize = size += this.getUnknownFields().getSerializedSize();
            return size;
        }

        protected Object writeReplace() throws ObjectStreamException {
            return super.writeReplace();
        }

        public static SignedPeerSeeds parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (SignedPeerSeeds)PARSER.parseFrom(data);
        }

        public static SignedPeerSeeds parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (SignedPeerSeeds)PARSER.parseFrom(data, extensionRegistry);
        }

        public static SignedPeerSeeds parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (SignedPeerSeeds)PARSER.parseFrom(data);
        }

        public static SignedPeerSeeds parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (SignedPeerSeeds)PARSER.parseFrom(data, extensionRegistry);
        }

        public static SignedPeerSeeds parseFrom(InputStream input) throws IOException {
            return (SignedPeerSeeds)PARSER.parseFrom(input);
        }

        public static SignedPeerSeeds parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (SignedPeerSeeds)PARSER.parseFrom(input, extensionRegistry);
        }

        public static SignedPeerSeeds parseDelimitedFrom(InputStream input) throws IOException {
            return (SignedPeerSeeds)PARSER.parseDelimitedFrom(input);
        }

        public static SignedPeerSeeds parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (SignedPeerSeeds)PARSER.parseDelimitedFrom(input, extensionRegistry);
        }

        public static SignedPeerSeeds parseFrom(CodedInputStream input) throws IOException {
            return (SignedPeerSeeds)PARSER.parseFrom(input);
        }

        public static SignedPeerSeeds parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (SignedPeerSeeds)PARSER.parseFrom(input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return Builder.create();
        }

        public Builder newBuilderForType() {
            return SignedPeerSeeds.newBuilder();
        }

        public static Builder newBuilder(SignedPeerSeeds prototype) {
            return SignedPeerSeeds.newBuilder().mergeFrom(prototype);
        }

        public Builder toBuilder() {
            return SignedPeerSeeds.newBuilder(this);
        }

        protected Builder newBuilderForType(GeneratedMessage.BuilderParent parent) {
            Builder builder = new Builder(parent);
            return builder;
        }

        static {
            PARSER = new AbstractParser<SignedPeerSeeds>(){

                public SignedPeerSeeds parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                    return new SignedPeerSeeds(input, extensionRegistry);
                }
            };
            defaultInstance = new SignedPeerSeeds(true);
            defaultInstance.initFields();
        }

        public static final class Builder
        extends GeneratedMessage.Builder<Builder>
        implements SignedPeerSeedsOrBuilder {
            private int bitField0_;
            private ByteString peerSeeds_ = ByteString.EMPTY;
            private ByteString signature_ = ByteString.EMPTY;
            private ByteString pubkey_ = ByteString.EMPTY;

            public static final Descriptors.Descriptor getDescriptor() {
                return internal_static_org_bitcoin_crawler_SignedPeerSeeds_descriptor;
            }

            protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
                return internal_static_org_bitcoin_crawler_SignedPeerSeeds_fieldAccessorTable.ensureFieldAccessorsInitialized(SignedPeerSeeds.class, Builder.class);
            }

            private Builder() {
                this.maybeForceBuilderInitialization();
            }

            private Builder(GeneratedMessage.BuilderParent parent) {
                super(parent);
                this.maybeForceBuilderInitialization();
            }

            private void maybeForceBuilderInitialization() {
                if (alwaysUseFieldBuilders) {
                    // empty if block
                }
            }

            private static Builder create() {
                return new Builder();
            }

            public Builder clear() {
                super.clear();
                this.peerSeeds_ = ByteString.EMPTY;
                this.bitField0_ &= -2;
                this.signature_ = ByteString.EMPTY;
                this.bitField0_ &= -3;
                this.pubkey_ = ByteString.EMPTY;
                this.bitField0_ &= -5;
                return this;
            }

            public Builder clone() {
                return Builder.create().mergeFrom(this.buildPartial());
            }

            public Descriptors.Descriptor getDescriptorForType() {
                return internal_static_org_bitcoin_crawler_SignedPeerSeeds_descriptor;
            }

            public SignedPeerSeeds getDefaultInstanceForType() {
                return SignedPeerSeeds.getDefaultInstance();
            }

            public SignedPeerSeeds build() {
                SignedPeerSeeds result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw Builder.newUninitializedMessageException((Message)result);
                }
                return result;
            }

            public SignedPeerSeeds buildPartial() {
                SignedPeerSeeds result = new SignedPeerSeeds(this);
                int from_bitField0_ = this.bitField0_;
                int to_bitField0_ = 0;
                if ((from_bitField0_ & 1) == 1) {
                    to_bitField0_ |= true;
                }
                result.peerSeeds_ = this.peerSeeds_;
                if ((from_bitField0_ & 2) == 2) {
                    to_bitField0_ |= 2;
                }
                result.signature_ = this.signature_;
                if ((from_bitField0_ & 4) == 4) {
                    to_bitField0_ |= 4;
                }
                result.pubkey_ = this.pubkey_;
                result.bitField0_ = to_bitField0_;
                this.onBuilt();
                return result;
            }

            public Builder mergeFrom(Message other) {
                if (other instanceof SignedPeerSeeds) {
                    return this.mergeFrom((SignedPeerSeeds)other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(SignedPeerSeeds other) {
                if (other == SignedPeerSeeds.getDefaultInstance()) {
                    return this;
                }
                if (other.hasPeerSeeds()) {
                    this.setPeerSeeds(other.getPeerSeeds());
                }
                if (other.hasSignature()) {
                    this.setSignature(other.getSignature());
                }
                if (other.hasPubkey()) {
                    this.setPubkey(other.getPubkey());
                }
                this.mergeUnknownFields(other.getUnknownFields());
                return this;
            }

            public final boolean isInitialized() {
                if (!this.hasPeerSeeds()) {
                    return false;
                }
                if (!this.hasSignature()) {
                    return false;
                }
                if (!this.hasPubkey()) {
                    return false;
                }
                return true;
            }

            public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                SignedPeerSeeds parsedMessage = null;
                try {
                    parsedMessage = (SignedPeerSeeds)SignedPeerSeeds.PARSER.parsePartialFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e) {
                    parsedMessage = (SignedPeerSeeds)e.getUnfinishedMessage();
                    throw e;
                }
                finally {
                    if (parsedMessage != null) {
                        this.mergeFrom(parsedMessage);
                    }
                }
                return this;
            }

            @Override
            public boolean hasPeerSeeds() {
                return (this.bitField0_ & 1) == 1;
            }

            @Override
            public ByteString getPeerSeeds() {
                return this.peerSeeds_;
            }

            public Builder setPeerSeeds(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 1;
                this.peerSeeds_ = value;
                this.onChanged();
                return this;
            }

            public Builder clearPeerSeeds() {
                this.bitField0_ &= -2;
                this.peerSeeds_ = SignedPeerSeeds.getDefaultInstance().getPeerSeeds();
                this.onChanged();
                return this;
            }

            @Override
            public boolean hasSignature() {
                return (this.bitField0_ & 2) == 2;
            }

            @Override
            public ByteString getSignature() {
                return this.signature_;
            }

            public Builder setSignature(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 2;
                this.signature_ = value;
                this.onChanged();
                return this;
            }

            public Builder clearSignature() {
                this.bitField0_ &= -3;
                this.signature_ = SignedPeerSeeds.getDefaultInstance().getSignature();
                this.onChanged();
                return this;
            }

            @Override
            public boolean hasPubkey() {
                return (this.bitField0_ & 4) == 4;
            }

            @Override
            public ByteString getPubkey() {
                return this.pubkey_;
            }

            public Builder setPubkey(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 4;
                this.pubkey_ = value;
                this.onChanged();
                return this;
            }

            public Builder clearPubkey() {
                this.bitField0_ &= -5;
                this.pubkey_ = SignedPeerSeeds.getDefaultInstance().getPubkey();
                this.onChanged();
                return this;
            }
        }

    }

    public static interface SignedPeerSeedsOrBuilder
    extends MessageOrBuilder {
        public boolean hasPeerSeeds();

        public ByteString getPeerSeeds();

        public boolean hasSignature();

        public ByteString getSignature();

        public boolean hasPubkey();

        public ByteString getPubkey();
    }

    public static final class PeerSeeds
    extends GeneratedMessage
    implements PeerSeedsOrBuilder {
        private static final PeerSeeds defaultInstance;
        private final UnknownFieldSet unknownFields;
        public static Parser<PeerSeeds> PARSER;
        private int bitField0_;
        public static final int SEED_FIELD_NUMBER = 1;
        private List<PeerSeedData> seed_;
        public static final int TIMESTAMP_FIELD_NUMBER = 2;
        private long timestamp_;
        public static final int NET_FIELD_NUMBER = 3;
        private Object net_;
        private byte memoizedIsInitialized;
        private int memoizedSerializedSize;
        private static final long serialVersionUID = 0L;

        private PeerSeeds(GeneratedMessage.Builder<?> builder) {
            super(builder);
            this.memoizedIsInitialized = (byte)-1;
            this.memoizedSerializedSize = -1;
            this.unknownFields = builder.getUnknownFields();
        }

        private PeerSeeds(boolean noInit) {
            this.memoizedIsInitialized = (byte)-1;
            this.memoizedSerializedSize = -1;
            this.unknownFields = UnknownFieldSet.getDefaultInstance();
        }

        public static PeerSeeds getDefaultInstance() {
            return defaultInstance;
        }

        public PeerSeeds getDefaultInstanceForType() {
            return defaultInstance;
        }

        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private PeerSeeds(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            this.memoizedIsInitialized = (byte)-1;
            this.memoizedSerializedSize = -1;
            this.initFields();
            boolean mutable_bitField0_ = false;
            UnknownFieldSet.Builder unknownFields = UnknownFieldSet.newBuilder();
            try {
                boolean done = false;
                block12 : while (!done) {
                    int tag = input.readTag();
                    switch (tag) {
                        case 0: {
                            done = true;
                            continue block12;
                        }
                        default: {
                            if (this.parseUnknownField(input, unknownFields, extensionRegistry, tag)) continue block12;
                            done = true;
                            continue block12;
                        }
                        case 10: {
                            if (!(mutable_bitField0_ & true)) {
                                this.seed_ = new ArrayList<PeerSeedData>();
                                mutable_bitField0_ |= true;
                            }
                            this.seed_.add((PeerSeedData)input.readMessage(PeerSeedData.PARSER, extensionRegistry));
                            continue block12;
                        }
                        case 16: {
                            this.bitField0_ |= 1;
                            this.timestamp_ = input.readUInt64();
                            continue block12;
                        }
                        case 26: 
                    }
                    ByteString bs = input.readBytes();
                    this.bitField0_ |= 2;
                    this.net_ = bs;
                }
            }
            catch (InvalidProtocolBufferException e) {
                throw e.setUnfinishedMessage((MessageLite)this);
            }
            catch (IOException e) {
                throw new InvalidProtocolBufferException(e.getMessage()).setUnfinishedMessage((MessageLite)this);
            }
            finally {
                if (mutable_bitField0_ & true) {
                    this.seed_ = Collections.unmodifiableList(this.seed_);
                }
                this.unknownFields = unknownFields.build();
                this.makeExtensionsImmutable();
            }
        }

        public static final Descriptors.Descriptor getDescriptor() {
            return internal_static_org_bitcoin_crawler_PeerSeeds_descriptor;
        }

        protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
            return internal_static_org_bitcoin_crawler_PeerSeeds_fieldAccessorTable.ensureFieldAccessorsInitialized(PeerSeeds.class, Builder.class);
        }

        public Parser<PeerSeeds> getParserForType() {
            return PARSER;
        }

        @Override
        public List<PeerSeedData> getSeedList() {
            return this.seed_;
        }

        @Override
        public List<? extends PeerSeedDataOrBuilder> getSeedOrBuilderList() {
            return this.seed_;
        }

        @Override
        public int getSeedCount() {
            return this.seed_.size();
        }

        @Override
        public PeerSeedData getSeed(int index) {
            return this.seed_.get(index);
        }

        @Override
        public PeerSeedDataOrBuilder getSeedOrBuilder(int index) {
            return this.seed_.get(index);
        }

        @Override
        public boolean hasTimestamp() {
            return (this.bitField0_ & 1) == 1;
        }

        @Override
        public long getTimestamp() {
            return this.timestamp_;
        }

        @Override
        public boolean hasNet() {
            return (this.bitField0_ & 2) == 2;
        }

        @Override
        public String getNet() {
            Object ref = this.net_;
            if (ref instanceof String) {
                return (String)ref;
            }
            ByteString bs = (ByteString)ref;
            String s = bs.toStringUtf8();
            if (bs.isValidUtf8()) {
                this.net_ = s;
            }
            return s;
        }

        @Override
        public ByteString getNetBytes() {
            Object ref = this.net_;
            if (ref instanceof String) {
                ByteString b = ByteString.copyFromUtf8((String)((String)ref));
                this.net_ = b;
                return b;
            }
            return (ByteString)ref;
        }

        private void initFields() {
            this.seed_ = Collections.emptyList();
            this.timestamp_ = 0L;
            this.net_ = "";
        }

        public final boolean isInitialized() {
            byte isInitialized = this.memoizedIsInitialized;
            if (isInitialized == 1) {
                return true;
            }
            if (isInitialized == 0) {
                return false;
            }
            if (!this.hasTimestamp()) {
                this.memoizedIsInitialized = 0;
                return false;
            }
            if (!this.hasNet()) {
                this.memoizedIsInitialized = 0;
                return false;
            }
            for (int i = 0; i < this.getSeedCount(); ++i) {
                if (this.getSeed(i).isInitialized()) continue;
                this.memoizedIsInitialized = 0;
                return false;
            }
            this.memoizedIsInitialized = 1;
            return true;
        }

        public void writeTo(CodedOutputStream output) throws IOException {
            this.getSerializedSize();
            for (int i = 0; i < this.seed_.size(); ++i) {
                output.writeMessage(1, (MessageLite)this.seed_.get(i));
            }
            if ((this.bitField0_ & 1) == 1) {
                output.writeUInt64(2, this.timestamp_);
            }
            if ((this.bitField0_ & 2) == 2) {
                output.writeBytes(3, this.getNetBytes());
            }
            this.getUnknownFields().writeTo(output);
        }

        public int getSerializedSize() {
            int size = this.memoizedSerializedSize;
            if (size != -1) {
                return size;
            }
            size = 0;
            for (int i = 0; i < this.seed_.size(); ++i) {
                size += CodedOutputStream.computeMessageSize((int)1, (MessageLite)((MessageLite)this.seed_.get(i)));
            }
            if ((this.bitField0_ & 1) == 1) {
                size += CodedOutputStream.computeUInt64Size((int)2, (long)this.timestamp_);
            }
            if ((this.bitField0_ & 2) == 2) {
                size += CodedOutputStream.computeBytesSize((int)3, (ByteString)this.getNetBytes());
            }
            this.memoizedSerializedSize = size += this.getUnknownFields().getSerializedSize();
            return size;
        }

        protected Object writeReplace() throws ObjectStreamException {
            return super.writeReplace();
        }

        public static PeerSeeds parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (PeerSeeds)PARSER.parseFrom(data);
        }

        public static PeerSeeds parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (PeerSeeds)PARSER.parseFrom(data, extensionRegistry);
        }

        public static PeerSeeds parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (PeerSeeds)PARSER.parseFrom(data);
        }

        public static PeerSeeds parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (PeerSeeds)PARSER.parseFrom(data, extensionRegistry);
        }

        public static PeerSeeds parseFrom(InputStream input) throws IOException {
            return (PeerSeeds)PARSER.parseFrom(input);
        }

        public static PeerSeeds parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (PeerSeeds)PARSER.parseFrom(input, extensionRegistry);
        }

        public static PeerSeeds parseDelimitedFrom(InputStream input) throws IOException {
            return (PeerSeeds)PARSER.parseDelimitedFrom(input);
        }

        public static PeerSeeds parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (PeerSeeds)PARSER.parseDelimitedFrom(input, extensionRegistry);
        }

        public static PeerSeeds parseFrom(CodedInputStream input) throws IOException {
            return (PeerSeeds)PARSER.parseFrom(input);
        }

        public static PeerSeeds parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (PeerSeeds)PARSER.parseFrom(input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return Builder.create();
        }

        public Builder newBuilderForType() {
            return PeerSeeds.newBuilder();
        }

        public static Builder newBuilder(PeerSeeds prototype) {
            return PeerSeeds.newBuilder().mergeFrom(prototype);
        }

        public Builder toBuilder() {
            return PeerSeeds.newBuilder(this);
        }

        protected Builder newBuilderForType(GeneratedMessage.BuilderParent parent) {
            Builder builder = new Builder(parent);
            return builder;
        }

        static {
            PARSER = new AbstractParser<PeerSeeds>(){

                public PeerSeeds parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                    return new PeerSeeds(input, extensionRegistry);
                }
            };
            defaultInstance = new PeerSeeds(true);
            defaultInstance.initFields();
        }

        public static final class Builder
        extends GeneratedMessage.Builder<Builder>
        implements PeerSeedsOrBuilder {
            private int bitField0_;
            private List<PeerSeedData> seed_ = Collections.emptyList();
            private RepeatedFieldBuilder<PeerSeedData, PeerSeedData.Builder, PeerSeedDataOrBuilder> seedBuilder_;
            private long timestamp_;
            private Object net_ = "";

            public static final Descriptors.Descriptor getDescriptor() {
                return internal_static_org_bitcoin_crawler_PeerSeeds_descriptor;
            }

            protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
                return internal_static_org_bitcoin_crawler_PeerSeeds_fieldAccessorTable.ensureFieldAccessorsInitialized(PeerSeeds.class, Builder.class);
            }

            private Builder() {
                this.maybeForceBuilderInitialization();
            }

            private Builder(GeneratedMessage.BuilderParent parent) {
                super(parent);
                this.maybeForceBuilderInitialization();
            }

            private void maybeForceBuilderInitialization() {
                if (alwaysUseFieldBuilders) {
                    this.getSeedFieldBuilder();
                }
            }

            private static Builder create() {
                return new Builder();
            }

            public Builder clear() {
                super.clear();
                if (this.seedBuilder_ == null) {
                    this.seed_ = Collections.emptyList();
                    this.bitField0_ &= -2;
                } else {
                    this.seedBuilder_.clear();
                }
                this.timestamp_ = 0L;
                this.bitField0_ &= -3;
                this.net_ = "";
                this.bitField0_ &= -5;
                return this;
            }

            public Builder clone() {
                return Builder.create().mergeFrom(this.buildPartial());
            }

            public Descriptors.Descriptor getDescriptorForType() {
                return internal_static_org_bitcoin_crawler_PeerSeeds_descriptor;
            }

            public PeerSeeds getDefaultInstanceForType() {
                return PeerSeeds.getDefaultInstance();
            }

            public PeerSeeds build() {
                PeerSeeds result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw Builder.newUninitializedMessageException((Message)result);
                }
                return result;
            }

            public PeerSeeds buildPartial() {
                PeerSeeds result = new PeerSeeds(this);
                int from_bitField0_ = this.bitField0_;
                int to_bitField0_ = 0;
                if (this.seedBuilder_ == null) {
                    if ((this.bitField0_ & 1) == 1) {
                        this.seed_ = Collections.unmodifiableList(this.seed_);
                        this.bitField0_ &= -2;
                    }
                    result.seed_ = this.seed_;
                } else {
                    result.seed_ = this.seedBuilder_.build();
                }
                if ((from_bitField0_ & 2) == 2) {
                    to_bitField0_ |= true;
                }
                result.timestamp_ = this.timestamp_;
                if ((from_bitField0_ & 4) == 4) {
                    to_bitField0_ |= 2;
                }
                result.net_ = this.net_;
                result.bitField0_ = to_bitField0_;
                this.onBuilt();
                return result;
            }

            public Builder mergeFrom(Message other) {
                if (other instanceof PeerSeeds) {
                    return this.mergeFrom((PeerSeeds)other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(PeerSeeds other) {
                if (other == PeerSeeds.getDefaultInstance()) {
                    return this;
                }
                if (this.seedBuilder_ == null) {
                    if (!other.seed_.isEmpty()) {
                        if (this.seed_.isEmpty()) {
                            this.seed_ = other.seed_;
                            this.bitField0_ &= -2;
                        } else {
                            this.ensureSeedIsMutable();
                            this.seed_.addAll(other.seed_);
                        }
                        this.onChanged();
                    }
                } else if (!other.seed_.isEmpty()) {
                    if (this.seedBuilder_.isEmpty()) {
                        this.seedBuilder_.dispose();
                        this.seedBuilder_ = null;
                        this.seed_ = other.seed_;
                        this.bitField0_ &= -2;
                        this.seedBuilder_ = alwaysUseFieldBuilders ? this.getSeedFieldBuilder() : null;
                    } else {
                        this.seedBuilder_.addAllMessages((Iterable)other.seed_);
                    }
                }
                if (other.hasTimestamp()) {
                    this.setTimestamp(other.getTimestamp());
                }
                if (other.hasNet()) {
                    this.bitField0_ |= 4;
                    this.net_ = other.net_;
                    this.onChanged();
                }
                this.mergeUnknownFields(other.getUnknownFields());
                return this;
            }

            public final boolean isInitialized() {
                if (!this.hasTimestamp()) {
                    return false;
                }
                if (!this.hasNet()) {
                    return false;
                }
                for (int i = 0; i < this.getSeedCount(); ++i) {
                    if (this.getSeed(i).isInitialized()) continue;
                    return false;
                }
                return true;
            }

            public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                PeerSeeds parsedMessage = null;
                try {
                    parsedMessage = (PeerSeeds)PeerSeeds.PARSER.parsePartialFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e) {
                    parsedMessage = (PeerSeeds)e.getUnfinishedMessage();
                    throw e;
                }
                finally {
                    if (parsedMessage != null) {
                        this.mergeFrom(parsedMessage);
                    }
                }
                return this;
            }

            private void ensureSeedIsMutable() {
                if ((this.bitField0_ & 1) != 1) {
                    this.seed_ = new ArrayList<PeerSeedData>(this.seed_);
                    this.bitField0_ |= 1;
                }
            }

            @Override
            public List<PeerSeedData> getSeedList() {
                if (this.seedBuilder_ == null) {
                    return Collections.unmodifiableList(this.seed_);
                }
                return this.seedBuilder_.getMessageList();
            }

            @Override
            public int getSeedCount() {
                if (this.seedBuilder_ == null) {
                    return this.seed_.size();
                }
                return this.seedBuilder_.getCount();
            }

            @Override
            public PeerSeedData getSeed(int index) {
                if (this.seedBuilder_ == null) {
                    return this.seed_.get(index);
                }
                return (PeerSeedData)this.seedBuilder_.getMessage(index);
            }

            public Builder setSeed(int index, PeerSeedData value) {
                if (this.seedBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.ensureSeedIsMutable();
                    this.seed_.set(index, value);
                    this.onChanged();
                } else {
                    this.seedBuilder_.setMessage(index, (GeneratedMessage)value);
                }
                return this;
            }

            public Builder setSeed(int index, PeerSeedData.Builder builderForValue) {
                if (this.seedBuilder_ == null) {
                    this.ensureSeedIsMutable();
                    this.seed_.set(index, builderForValue.build());
                    this.onChanged();
                } else {
                    this.seedBuilder_.setMessage(index, (GeneratedMessage)builderForValue.build());
                }
                return this;
            }

            public Builder addSeed(PeerSeedData value) {
                if (this.seedBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.ensureSeedIsMutable();
                    this.seed_.add(value);
                    this.onChanged();
                } else {
                    this.seedBuilder_.addMessage((GeneratedMessage)value);
                }
                return this;
            }

            public Builder addSeed(int index, PeerSeedData value) {
                if (this.seedBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.ensureSeedIsMutable();
                    this.seed_.add(index, value);
                    this.onChanged();
                } else {
                    this.seedBuilder_.addMessage(index, (GeneratedMessage)value);
                }
                return this;
            }

            public Builder addSeed(PeerSeedData.Builder builderForValue) {
                if (this.seedBuilder_ == null) {
                    this.ensureSeedIsMutable();
                    this.seed_.add(builderForValue.build());
                    this.onChanged();
                } else {
                    this.seedBuilder_.addMessage((GeneratedMessage)builderForValue.build());
                }
                return this;
            }

            public Builder addSeed(int index, PeerSeedData.Builder builderForValue) {
                if (this.seedBuilder_ == null) {
                    this.ensureSeedIsMutable();
                    this.seed_.add(index, builderForValue.build());
                    this.onChanged();
                } else {
                    this.seedBuilder_.addMessage(index, (GeneratedMessage)builderForValue.build());
                }
                return this;
            }

            public Builder addAllSeed(Iterable<? extends PeerSeedData> values) {
                if (this.seedBuilder_ == null) {
                    this.ensureSeedIsMutable();
                    AbstractMessageLite.Builder.addAll(values, this.seed_);
                    this.onChanged();
                } else {
                    this.seedBuilder_.addAllMessages(values);
                }
                return this;
            }

            public Builder clearSeed() {
                if (this.seedBuilder_ == null) {
                    this.seed_ = Collections.emptyList();
                    this.bitField0_ &= -2;
                    this.onChanged();
                } else {
                    this.seedBuilder_.clear();
                }
                return this;
            }

            public Builder removeSeed(int index) {
                if (this.seedBuilder_ == null) {
                    this.ensureSeedIsMutable();
                    this.seed_.remove(index);
                    this.onChanged();
                } else {
                    this.seedBuilder_.remove(index);
                }
                return this;
            }

            public PeerSeedData.Builder getSeedBuilder(int index) {
                return (PeerSeedData.Builder)this.getSeedFieldBuilder().getBuilder(index);
            }

            @Override
            public PeerSeedDataOrBuilder getSeedOrBuilder(int index) {
                if (this.seedBuilder_ == null) {
                    return this.seed_.get(index);
                }
                return (PeerSeedDataOrBuilder)this.seedBuilder_.getMessageOrBuilder(index);
            }

            @Override
            public List<? extends PeerSeedDataOrBuilder> getSeedOrBuilderList() {
                if (this.seedBuilder_ != null) {
                    return this.seedBuilder_.getMessageOrBuilderList();
                }
                return Collections.unmodifiableList(this.seed_);
            }

            public PeerSeedData.Builder addSeedBuilder() {
                return (PeerSeedData.Builder)this.getSeedFieldBuilder().addBuilder((GeneratedMessage)PeerSeedData.getDefaultInstance());
            }

            public PeerSeedData.Builder addSeedBuilder(int index) {
                return (PeerSeedData.Builder)this.getSeedFieldBuilder().addBuilder(index, (GeneratedMessage)PeerSeedData.getDefaultInstance());
            }

            public List<PeerSeedData.Builder> getSeedBuilderList() {
                return this.getSeedFieldBuilder().getBuilderList();
            }

            private RepeatedFieldBuilder<PeerSeedData, PeerSeedData.Builder, PeerSeedDataOrBuilder> getSeedFieldBuilder() {
                if (this.seedBuilder_ == null) {
                    this.seedBuilder_ = new RepeatedFieldBuilder(this.seed_, (this.bitField0_ & 1) == 1, this.getParentForChildren(), this.isClean());
                    this.seed_ = null;
                }
                return this.seedBuilder_;
            }

            @Override
            public boolean hasTimestamp() {
                return (this.bitField0_ & 2) == 2;
            }

            @Override
            public long getTimestamp() {
                return this.timestamp_;
            }

            public Builder setTimestamp(long value) {
                this.bitField0_ |= 2;
                this.timestamp_ = value;
                this.onChanged();
                return this;
            }

            public Builder clearTimestamp() {
                this.bitField0_ &= -3;
                this.timestamp_ = 0L;
                this.onChanged();
                return this;
            }

            @Override
            public boolean hasNet() {
                return (this.bitField0_ & 4) == 4;
            }

            @Override
            public String getNet() {
                Object ref = this.net_;
                if (!(ref instanceof String)) {
                    ByteString bs = (ByteString)ref;
                    String s = bs.toStringUtf8();
                    if (bs.isValidUtf8()) {
                        this.net_ = s;
                    }
                    return s;
                }
                return (String)ref;
            }

            @Override
            public ByteString getNetBytes() {
                Object ref = this.net_;
                if (ref instanceof String) {
                    ByteString b = ByteString.copyFromUtf8((String)((String)ref));
                    this.net_ = b;
                    return b;
                }
                return (ByteString)ref;
            }

            public Builder setNet(String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 4;
                this.net_ = value;
                this.onChanged();
                return this;
            }

            public Builder clearNet() {
                this.bitField0_ &= -5;
                this.net_ = PeerSeeds.getDefaultInstance().getNet();
                this.onChanged();
                return this;
            }

            public Builder setNetBytes(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 4;
                this.net_ = value;
                this.onChanged();
                return this;
            }
        }

    }

    public static interface PeerSeedsOrBuilder
    extends MessageOrBuilder {
        public List<PeerSeedData> getSeedList();

        public PeerSeedData getSeed(int var1);

        public int getSeedCount();

        public List<? extends PeerSeedDataOrBuilder> getSeedOrBuilderList();

        public PeerSeedDataOrBuilder getSeedOrBuilder(int var1);

        public boolean hasTimestamp();

        public long getTimestamp();

        public boolean hasNet();

        public String getNet();

        public ByteString getNetBytes();
    }

    public static final class PeerSeedData
    extends GeneratedMessage
    implements PeerSeedDataOrBuilder {
        private static final PeerSeedData defaultInstance;
        private final UnknownFieldSet unknownFields;
        public static Parser<PeerSeedData> PARSER;
        private int bitField0_;
        public static final int IP_ADDRESS_FIELD_NUMBER = 1;
        private Object ipAddress_;
        public static final int PORT_FIELD_NUMBER = 2;
        private int port_;
        public static final int SERVICES_FIELD_NUMBER = 3;
        private int services_;
        private byte memoizedIsInitialized;
        private int memoizedSerializedSize;
        private static final long serialVersionUID = 0L;

        private PeerSeedData(GeneratedMessage.Builder<?> builder) {
            super(builder);
            this.memoizedIsInitialized = (byte)-1;
            this.memoizedSerializedSize = -1;
            this.unknownFields = builder.getUnknownFields();
        }

        private PeerSeedData(boolean noInit) {
            this.memoizedIsInitialized = (byte)-1;
            this.memoizedSerializedSize = -1;
            this.unknownFields = UnknownFieldSet.getDefaultInstance();
        }

        public static PeerSeedData getDefaultInstance() {
            return defaultInstance;
        }

        public PeerSeedData getDefaultInstanceForType() {
            return defaultInstance;
        }

        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private PeerSeedData(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            this.memoizedIsInitialized = (byte)-1;
            this.memoizedSerializedSize = -1;
            this.initFields();
            boolean mutable_bitField0_ = false;
            UnknownFieldSet.Builder unknownFields = UnknownFieldSet.newBuilder();
            try {
                boolean done = false;
                block12 : while (!done) {
                    int tag = input.readTag();
                    switch (tag) {
                        case 0: {
                            done = true;
                            continue block12;
                        }
                        default: {
                            if (this.parseUnknownField(input, unknownFields, extensionRegistry, tag)) continue block12;
                            done = true;
                            continue block12;
                        }
                        case 10: {
                            ByteString bs = input.readBytes();
                            this.bitField0_ |= 1;
                            this.ipAddress_ = bs;
                            continue block12;
                        }
                        case 16: {
                            this.bitField0_ |= 2;
                            this.port_ = input.readUInt32();
                            continue block12;
                        }
                        case 24: 
                    }
                    this.bitField0_ |= 4;
                    this.services_ = input.readUInt32();
                }
            }
            catch (InvalidProtocolBufferException e) {
                throw e.setUnfinishedMessage((MessageLite)this);
            }
            catch (IOException e) {
                throw new InvalidProtocolBufferException(e.getMessage()).setUnfinishedMessage((MessageLite)this);
            }
            finally {
                this.unknownFields = unknownFields.build();
                this.makeExtensionsImmutable();
            }
        }

        public static final Descriptors.Descriptor getDescriptor() {
            return internal_static_org_bitcoin_crawler_PeerSeedData_descriptor;
        }

        protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
            return internal_static_org_bitcoin_crawler_PeerSeedData_fieldAccessorTable.ensureFieldAccessorsInitialized(PeerSeedData.class, Builder.class);
        }

        public Parser<PeerSeedData> getParserForType() {
            return PARSER;
        }

        @Override
        public boolean hasIpAddress() {
            return (this.bitField0_ & 1) == 1;
        }

        @Override
        public String getIpAddress() {
            Object ref = this.ipAddress_;
            if (ref instanceof String) {
                return (String)ref;
            }
            ByteString bs = (ByteString)ref;
            String s = bs.toStringUtf8();
            if (bs.isValidUtf8()) {
                this.ipAddress_ = s;
            }
            return s;
        }

        @Override
        public ByteString getIpAddressBytes() {
            Object ref = this.ipAddress_;
            if (ref instanceof String) {
                ByteString b = ByteString.copyFromUtf8((String)((String)ref));
                this.ipAddress_ = b;
                return b;
            }
            return (ByteString)ref;
        }

        @Override
        public boolean hasPort() {
            return (this.bitField0_ & 2) == 2;
        }

        @Override
        public int getPort() {
            return this.port_;
        }

        @Override
        public boolean hasServices() {
            return (this.bitField0_ & 4) == 4;
        }

        @Override
        public int getServices() {
            return this.services_;
        }

        private void initFields() {
            this.ipAddress_ = "";
            this.port_ = 0;
            this.services_ = 0;
        }

        public final boolean isInitialized() {
            byte isInitialized = this.memoizedIsInitialized;
            if (isInitialized == 1) {
                return true;
            }
            if (isInitialized == 0) {
                return false;
            }
            if (!this.hasIpAddress()) {
                this.memoizedIsInitialized = 0;
                return false;
            }
            if (!this.hasPort()) {
                this.memoizedIsInitialized = 0;
                return false;
            }
            if (!this.hasServices()) {
                this.memoizedIsInitialized = 0;
                return false;
            }
            this.memoizedIsInitialized = 1;
            return true;
        }

        public void writeTo(CodedOutputStream output) throws IOException {
            this.getSerializedSize();
            if ((this.bitField0_ & 1) == 1) {
                output.writeBytes(1, this.getIpAddressBytes());
            }
            if ((this.bitField0_ & 2) == 2) {
                output.writeUInt32(2, this.port_);
            }
            if ((this.bitField0_ & 4) == 4) {
                output.writeUInt32(3, this.services_);
            }
            this.getUnknownFields().writeTo(output);
        }

        public int getSerializedSize() {
            int size = this.memoizedSerializedSize;
            if (size != -1) {
                return size;
            }
            size = 0;
            if ((this.bitField0_ & 1) == 1) {
                size += CodedOutputStream.computeBytesSize((int)1, (ByteString)this.getIpAddressBytes());
            }
            if ((this.bitField0_ & 2) == 2) {
                size += CodedOutputStream.computeUInt32Size((int)2, (int)this.port_);
            }
            if ((this.bitField0_ & 4) == 4) {
                size += CodedOutputStream.computeUInt32Size((int)3, (int)this.services_);
            }
            this.memoizedSerializedSize = size += this.getUnknownFields().getSerializedSize();
            return size;
        }

        protected Object writeReplace() throws ObjectStreamException {
            return super.writeReplace();
        }

        public static PeerSeedData parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (PeerSeedData)PARSER.parseFrom(data);
        }

        public static PeerSeedData parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (PeerSeedData)PARSER.parseFrom(data, extensionRegistry);
        }

        public static PeerSeedData parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (PeerSeedData)PARSER.parseFrom(data);
        }

        public static PeerSeedData parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (PeerSeedData)PARSER.parseFrom(data, extensionRegistry);
        }

        public static PeerSeedData parseFrom(InputStream input) throws IOException {
            return (PeerSeedData)PARSER.parseFrom(input);
        }

        public static PeerSeedData parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (PeerSeedData)PARSER.parseFrom(input, extensionRegistry);
        }

        public static PeerSeedData parseDelimitedFrom(InputStream input) throws IOException {
            return (PeerSeedData)PARSER.parseDelimitedFrom(input);
        }

        public static PeerSeedData parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (PeerSeedData)PARSER.parseDelimitedFrom(input, extensionRegistry);
        }

        public static PeerSeedData parseFrom(CodedInputStream input) throws IOException {
            return (PeerSeedData)PARSER.parseFrom(input);
        }

        public static PeerSeedData parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (PeerSeedData)PARSER.parseFrom(input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return Builder.create();
        }

        public Builder newBuilderForType() {
            return PeerSeedData.newBuilder();
        }

        public static Builder newBuilder(PeerSeedData prototype) {
            return PeerSeedData.newBuilder().mergeFrom(prototype);
        }

        public Builder toBuilder() {
            return PeerSeedData.newBuilder(this);
        }

        protected Builder newBuilderForType(GeneratedMessage.BuilderParent parent) {
            Builder builder = new Builder(parent);
            return builder;
        }

        static {
            PARSER = new AbstractParser<PeerSeedData>(){

                public PeerSeedData parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                    return new PeerSeedData(input, extensionRegistry);
                }
            };
            defaultInstance = new PeerSeedData(true);
            defaultInstance.initFields();
        }

        public static final class Builder
        extends GeneratedMessage.Builder<Builder>
        implements PeerSeedDataOrBuilder {
            private int bitField0_;
            private Object ipAddress_ = "";
            private int port_;
            private int services_;

            public static final Descriptors.Descriptor getDescriptor() {
                return internal_static_org_bitcoin_crawler_PeerSeedData_descriptor;
            }

            protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
                return internal_static_org_bitcoin_crawler_PeerSeedData_fieldAccessorTable.ensureFieldAccessorsInitialized(PeerSeedData.class, Builder.class);
            }

            private Builder() {
                this.maybeForceBuilderInitialization();
            }

            private Builder(GeneratedMessage.BuilderParent parent) {
                super(parent);
                this.maybeForceBuilderInitialization();
            }

            private void maybeForceBuilderInitialization() {
                if (alwaysUseFieldBuilders) {
                    // empty if block
                }
            }

            private static Builder create() {
                return new Builder();
            }

            public Builder clear() {
                super.clear();
                this.ipAddress_ = "";
                this.bitField0_ &= -2;
                this.port_ = 0;
                this.bitField0_ &= -3;
                this.services_ = 0;
                this.bitField0_ &= -5;
                return this;
            }

            public Builder clone() {
                return Builder.create().mergeFrom(this.buildPartial());
            }

            public Descriptors.Descriptor getDescriptorForType() {
                return internal_static_org_bitcoin_crawler_PeerSeedData_descriptor;
            }

            public PeerSeedData getDefaultInstanceForType() {
                return PeerSeedData.getDefaultInstance();
            }

            public PeerSeedData build() {
                PeerSeedData result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw Builder.newUninitializedMessageException((Message)result);
                }
                return result;
            }

            public PeerSeedData buildPartial() {
                PeerSeedData result = new PeerSeedData(this);
                int from_bitField0_ = this.bitField0_;
                int to_bitField0_ = 0;
                if ((from_bitField0_ & 1) == 1) {
                    to_bitField0_ |= true;
                }
                result.ipAddress_ = this.ipAddress_;
                if ((from_bitField0_ & 2) == 2) {
                    to_bitField0_ |= 2;
                }
                result.port_ = this.port_;
                if ((from_bitField0_ & 4) == 4) {
                    to_bitField0_ |= 4;
                }
                result.services_ = this.services_;
                result.bitField0_ = to_bitField0_;
                this.onBuilt();
                return result;
            }

            public Builder mergeFrom(Message other) {
                if (other instanceof PeerSeedData) {
                    return this.mergeFrom((PeerSeedData)other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(PeerSeedData other) {
                if (other == PeerSeedData.getDefaultInstance()) {
                    return this;
                }
                if (other.hasIpAddress()) {
                    this.bitField0_ |= 1;
                    this.ipAddress_ = other.ipAddress_;
                    this.onChanged();
                }
                if (other.hasPort()) {
                    this.setPort(other.getPort());
                }
                if (other.hasServices()) {
                    this.setServices(other.getServices());
                }
                this.mergeUnknownFields(other.getUnknownFields());
                return this;
            }

            public final boolean isInitialized() {
                if (!this.hasIpAddress()) {
                    return false;
                }
                if (!this.hasPort()) {
                    return false;
                }
                if (!this.hasServices()) {
                    return false;
                }
                return true;
            }

            public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                PeerSeedData parsedMessage = null;
                try {
                    parsedMessage = (PeerSeedData)PeerSeedData.PARSER.parsePartialFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e) {
                    parsedMessage = (PeerSeedData)e.getUnfinishedMessage();
                    throw e;
                }
                finally {
                    if (parsedMessage != null) {
                        this.mergeFrom(parsedMessage);
                    }
                }
                return this;
            }

            @Override
            public boolean hasIpAddress() {
                return (this.bitField0_ & 1) == 1;
            }

            @Override
            public String getIpAddress() {
                Object ref = this.ipAddress_;
                if (!(ref instanceof String)) {
                    ByteString bs = (ByteString)ref;
                    String s = bs.toStringUtf8();
                    if (bs.isValidUtf8()) {
                        this.ipAddress_ = s;
                    }
                    return s;
                }
                return (String)ref;
            }

            @Override
            public ByteString getIpAddressBytes() {
                Object ref = this.ipAddress_;
                if (ref instanceof String) {
                    ByteString b = ByteString.copyFromUtf8((String)((String)ref));
                    this.ipAddress_ = b;
                    return b;
                }
                return (ByteString)ref;
            }

            public Builder setIpAddress(String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 1;
                this.ipAddress_ = value;
                this.onChanged();
                return this;
            }

            public Builder clearIpAddress() {
                this.bitField0_ &= -2;
                this.ipAddress_ = PeerSeedData.getDefaultInstance().getIpAddress();
                this.onChanged();
                return this;
            }

            public Builder setIpAddressBytes(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 1;
                this.ipAddress_ = value;
                this.onChanged();
                return this;
            }

            @Override
            public boolean hasPort() {
                return (this.bitField0_ & 2) == 2;
            }

            @Override
            public int getPort() {
                return this.port_;
            }

            public Builder setPort(int value) {
                this.bitField0_ |= 2;
                this.port_ = value;
                this.onChanged();
                return this;
            }

            public Builder clearPort() {
                this.bitField0_ &= -3;
                this.port_ = 0;
                this.onChanged();
                return this;
            }

            @Override
            public boolean hasServices() {
                return (this.bitField0_ & 4) == 4;
            }

            @Override
            public int getServices() {
                return this.services_;
            }

            public Builder setServices(int value) {
                this.bitField0_ |= 4;
                this.services_ = value;
                this.onChanged();
                return this;
            }

            public Builder clearServices() {
                this.bitField0_ &= -5;
                this.services_ = 0;
                this.onChanged();
                return this;
            }
        }

    }

    public static interface PeerSeedDataOrBuilder
    extends MessageOrBuilder {
        public boolean hasIpAddress();

        public String getIpAddress();

        public ByteString getIpAddressBytes();

        public boolean hasPort();

        public int getPort();

        public boolean hasServices();

        public int getServices();
    }

}

