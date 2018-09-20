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
 *  com.google.protobuf.Descriptors$EnumDescriptor
 *  com.google.protobuf.Descriptors$EnumValueDescriptor
 *  com.google.protobuf.Descriptors$FileDescriptor
 *  com.google.protobuf.Descriptors$FileDescriptor$InternalDescriptorAssigner
 *  com.google.protobuf.ExtensionRegistry
 *  com.google.protobuf.ExtensionRegistryLite
 *  com.google.protobuf.GeneratedMessage
 *  com.google.protobuf.GeneratedMessage$Builder
 *  com.google.protobuf.GeneratedMessage$BuilderParent
 *  com.google.protobuf.GeneratedMessage$FieldAccessorTable
 *  com.google.protobuf.Internal
 *  com.google.protobuf.Internal$EnumLite
 *  com.google.protobuf.Internal$EnumLiteMap
 *  com.google.protobuf.InvalidProtocolBufferException
 *  com.google.protobuf.Message
 *  com.google.protobuf.Message$Builder
 *  com.google.protobuf.MessageLite
 *  com.google.protobuf.MessageLite$Builder
 *  com.google.protobuf.MessageOrBuilder
 *  com.google.protobuf.Parser
 *  com.google.protobuf.ProtocolMessageEnum
 *  com.google.protobuf.SingleFieldBuilder
 *  com.google.protobuf.UnknownFieldSet
 *  com.google.protobuf.UnknownFieldSet$Builder
 */
package org.bitcoin.paymentchannel;

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
import com.google.protobuf.Internal;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Message;
import com.google.protobuf.MessageLite;
import com.google.protobuf.MessageOrBuilder;
import com.google.protobuf.Parser;
import com.google.protobuf.ProtocolMessageEnum;
import com.google.protobuf.SingleFieldBuilder;
import com.google.protobuf.UnknownFieldSet;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectStreamException;
import java.util.List;

public final class Protos {
    private static final Descriptors.Descriptor internal_static_paymentchannels_TwoWayChannelMessage_descriptor;
    private static GeneratedMessage.FieldAccessorTable internal_static_paymentchannels_TwoWayChannelMessage_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_paymentchannels_ClientVersion_descriptor;
    private static GeneratedMessage.FieldAccessorTable internal_static_paymentchannels_ClientVersion_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_paymentchannels_ServerVersion_descriptor;
    private static GeneratedMessage.FieldAccessorTable internal_static_paymentchannels_ServerVersion_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_paymentchannels_Initiate_descriptor;
    private static GeneratedMessage.FieldAccessorTable internal_static_paymentchannels_Initiate_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_paymentchannels_ProvideRefund_descriptor;
    private static GeneratedMessage.FieldAccessorTable internal_static_paymentchannels_ProvideRefund_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_paymentchannels_ReturnRefund_descriptor;
    private static GeneratedMessage.FieldAccessorTable internal_static_paymentchannels_ReturnRefund_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_paymentchannels_ProvideContract_descriptor;
    private static GeneratedMessage.FieldAccessorTable internal_static_paymentchannels_ProvideContract_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_paymentchannels_UpdatePayment_descriptor;
    private static GeneratedMessage.FieldAccessorTable internal_static_paymentchannels_UpdatePayment_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_paymentchannels_PaymentAck_descriptor;
    private static GeneratedMessage.FieldAccessorTable internal_static_paymentchannels_PaymentAck_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_paymentchannels_Settlement_descriptor;
    private static GeneratedMessage.FieldAccessorTable internal_static_paymentchannels_Settlement_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_paymentchannels_Error_descriptor;
    private static GeneratedMessage.FieldAccessorTable internal_static_paymentchannels_Error_fieldAccessorTable;
    private static Descriptors.FileDescriptor descriptor;

    private Protos() {
    }

    public static void registerAllExtensions(ExtensionRegistry registry) {
    }

    public static Descriptors.FileDescriptor getDescriptor() {
        return descriptor;
    }

    static {
        String[] descriptorData = new String[]{"\n\u0014paymentchannel.proto\u0012\u000fpaymentchannels\"\u00b0\u0006\n\u0014TwoWayChannelMessage\u0012?\n\u0004type\u0018\u0001 \u0002(\u000e21.paymentchannels.TwoWayChannelMessage.MessageType\u00126\n\u000eclient_version\u0018\u0002 \u0001(\u000b2\u001e.paymentchannels.ClientVersion\u00126\n\u000eserver_version\u0018\u0003 \u0001(\u000b2\u001e.paymentchannels.ServerVersion\u0012+\n\binitiate\u0018\u0004 \u0001(\u000b2\u0019.paymentchannels.Initiate\u00126\n\u000eprovide_refund\u0018\u0005 \u0001(\u000b2\u001e.paymentchannels.ProvideRefund\u00124\n\rreturn_refund\u0018\u0006 \u0001(\u000b2\u001d.paymentchannels.ReturnRefund\u0012:\n\u0010", "provide_contract\u0018\u0007 \u0001(\u000b2 .paymentchannels.ProvideContract\u00126\n\u000eupdate_payment\u0018\b \u0001(\u000b2\u001e.paymentchannels.UpdatePayment\u00120\n\u000bpayment_ack\u0018\u000b \u0001(\u000b2\u001b.paymentchannels.PaymentAck\u0012/\n\nsettlement\u0018\t \u0001(\u000b2\u001b.paymentchannels.Settlement\u0012%\n\u0005error\u0018\n \u0001(\u000b2\u0016.paymentchannels.Error\"\u00cd\u0001\n\u000bMessageType\u0012\u0012\n\u000eCLIENT_VERSION\u0010\u0001\u0012\u0012\n\u000eSERVER_VERSION\u0010\u0002\u0012\f\n\bINITIATE\u0010\u0003\u0012\u0012\n\u000ePROVIDE_REFUND\u0010\u0004\u0012\u0011\n\rRETURN_REFUND\u0010\u0005\u0012\u0014\n\u0010PROVIDE_CONTRACT\u0010\u0006\u0012\u0010\n\fCHANNEL_OPEN\u0010\u0007\u0012", "\u0012\n\u000eUPDATE_PAYMENT\u0010\b\u0012\u000f\n\u000bPAYMENT_ACK\u0010\u000b\u0012\t\n\u0005CLOSE\u0010\t\u0012\t\n\u0005ERROR\u0010\n\"y\n\rClientVersion\u0012\r\n\u0005major\u0018\u0001 \u0002(\u0005\u0012\u0010\n\u0005minor\u0018\u0002 \u0001(\u0005:\u00010\u0012&\n\u001eprevious_channel_contract_hash\u0018\u0003 \u0001(\f\u0012\u001f\n\u0010time_window_secs\u0018\u0004 \u0001(\u0004:\u000586340\"0\n\rServerVersion\u0012\r\n\u0005major\u0018\u0001 \u0002(\u0005\u0012\u0010\n\u0005minor\u0018\u0002 \u0001(\u0005:\u00010\"r\n\bInitiate\u0012\u0014\n\fmultisig_key\u0018\u0001 \u0002(\f\u0012!\n\u0019min_accepted_channel_size\u0018\u0002 \u0002(\u0004\u0012\u0018\n\u0010expire_time_secs\u0018\u0003 \u0002(\u0004\u0012\u0013\n\u000bmin_payment\u0018\u0004 \u0002(\u0004\"1\n\rProvideRefund\u0012\u0014\n\fmultisig_key\u0018\u0001 \u0002(\f\u0012\n\n\u0002tx\u0018\u0002 \u0002(\f\"!", "\n\fReturnRefund\u0012\u0011\n\tsignature\u0018\u0001 \u0002(\f\"j\n\u000fProvideContract\u0012\n\n\u0002tx\u0018\u0001 \u0002(\f\u00127\n\u000finitial_payment\u0018\u0002 \u0002(\u000b2\u001e.paymentchannels.UpdatePayment\u0012\u0012\n\nclient_key\u0018\u0003 \u0001(\f\"M\n\rUpdatePayment\u0012\u001b\n\u0013client_change_value\u0018\u0001 \u0002(\u0004\u0012\u0011\n\tsignature\u0018\u0002 \u0002(\f\u0012\f\n\u0004info\u0018\u0003 \u0001(\f\"\u001a\n\nPaymentAck\u0012\f\n\u0004info\u0018\u0001 \u0001(\f\"\u0018\n\nSettlement\u0012\n\n\u0002tx\u0018\u0003 \u0002(\f\"\u00a9\u0002\n\u0005Error\u00125\n\u0004code\u0018\u0001 \u0001(\u000e2 .paymentchannels.Error.ErrorCode:\u0005OTHER\u0012\u0013\n\u000bexplanation\u0018\u0002 \u0001(\t\u0012\u0016\n\u000eexpected_value\u0018\u0003 \u0001(\u0004\"\u00bb\u0001\n\tErrorCode\u0012\u000b", "\n\u0007TIMEOUT\u0010\u0001\u0012\u0010\n\fSYNTAX_ERROR\u0010\u0002\u0012\u0019\n\u0015NO_ACCEPTABLE_VERSION\u0010\u0003\u0012\u0013\n\u000fBAD_TRANSACTION\u0010\u0004\u0012\u001c\n\u0018TIME_WINDOW_UNACCEPTABLE\u0010\u0005\u0012\u001b\n\u0017CHANNEL_VALUE_TOO_LARGE\u0010\u0006\u0012\u0019\n\u0015MIN_PAYMENT_TOO_LARGE\u0010\u0007\u0012\t\n\u0005OTHER\u0010\bB$\n\u001aorg.bitcoin.paymentchannelB\u0006Protos"};
        Descriptors.FileDescriptor.InternalDescriptorAssigner assigner = new Descriptors.FileDescriptor.InternalDescriptorAssigner(){

            public ExtensionRegistry assignDescriptors(Descriptors.FileDescriptor root) {
                descriptor = root;
                return null;
            }
        };
        Descriptors.FileDescriptor.internalBuildGeneratedFileFrom((String[])descriptorData, (Descriptors.FileDescriptor[])new Descriptors.FileDescriptor[0], (Descriptors.FileDescriptor.InternalDescriptorAssigner)assigner);
        internal_static_paymentchannels_TwoWayChannelMessage_descriptor = (Descriptors.Descriptor)Protos.getDescriptor().getMessageTypes().get(0);
        internal_static_paymentchannels_TwoWayChannelMessage_fieldAccessorTable = new GeneratedMessage.FieldAccessorTable(internal_static_paymentchannels_TwoWayChannelMessage_descriptor, new String[]{"Type", "ClientVersion", "ServerVersion", "Initiate", "ProvideRefund", "ReturnRefund", "ProvideContract", "UpdatePayment", "PaymentAck", "Settlement", "Error"});
        internal_static_paymentchannels_ClientVersion_descriptor = (Descriptors.Descriptor)Protos.getDescriptor().getMessageTypes().get(1);
        internal_static_paymentchannels_ClientVersion_fieldAccessorTable = new GeneratedMessage.FieldAccessorTable(internal_static_paymentchannels_ClientVersion_descriptor, new String[]{"Major", "Minor", "PreviousChannelContractHash", "TimeWindowSecs"});
        internal_static_paymentchannels_ServerVersion_descriptor = (Descriptors.Descriptor)Protos.getDescriptor().getMessageTypes().get(2);
        internal_static_paymentchannels_ServerVersion_fieldAccessorTable = new GeneratedMessage.FieldAccessorTable(internal_static_paymentchannels_ServerVersion_descriptor, new String[]{"Major", "Minor"});
        internal_static_paymentchannels_Initiate_descriptor = (Descriptors.Descriptor)Protos.getDescriptor().getMessageTypes().get(3);
        internal_static_paymentchannels_Initiate_fieldAccessorTable = new GeneratedMessage.FieldAccessorTable(internal_static_paymentchannels_Initiate_descriptor, new String[]{"MultisigKey", "MinAcceptedChannelSize", "ExpireTimeSecs", "MinPayment"});
        internal_static_paymentchannels_ProvideRefund_descriptor = (Descriptors.Descriptor)Protos.getDescriptor().getMessageTypes().get(4);
        internal_static_paymentchannels_ProvideRefund_fieldAccessorTable = new GeneratedMessage.FieldAccessorTable(internal_static_paymentchannels_ProvideRefund_descriptor, new String[]{"MultisigKey", "Tx"});
        internal_static_paymentchannels_ReturnRefund_descriptor = (Descriptors.Descriptor)Protos.getDescriptor().getMessageTypes().get(5);
        internal_static_paymentchannels_ReturnRefund_fieldAccessorTable = new GeneratedMessage.FieldAccessorTable(internal_static_paymentchannels_ReturnRefund_descriptor, new String[]{"Signature"});
        internal_static_paymentchannels_ProvideContract_descriptor = (Descriptors.Descriptor)Protos.getDescriptor().getMessageTypes().get(6);
        internal_static_paymentchannels_ProvideContract_fieldAccessorTable = new GeneratedMessage.FieldAccessorTable(internal_static_paymentchannels_ProvideContract_descriptor, new String[]{"Tx", "InitialPayment", "ClientKey"});
        internal_static_paymentchannels_UpdatePayment_descriptor = (Descriptors.Descriptor)Protos.getDescriptor().getMessageTypes().get(7);
        internal_static_paymentchannels_UpdatePayment_fieldAccessorTable = new GeneratedMessage.FieldAccessorTable(internal_static_paymentchannels_UpdatePayment_descriptor, new String[]{"ClientChangeValue", "Signature", "Info"});
        internal_static_paymentchannels_PaymentAck_descriptor = (Descriptors.Descriptor)Protos.getDescriptor().getMessageTypes().get(8);
        internal_static_paymentchannels_PaymentAck_fieldAccessorTable = new GeneratedMessage.FieldAccessorTable(internal_static_paymentchannels_PaymentAck_descriptor, new String[]{"Info"});
        internal_static_paymentchannels_Settlement_descriptor = (Descriptors.Descriptor)Protos.getDescriptor().getMessageTypes().get(9);
        internal_static_paymentchannels_Settlement_fieldAccessorTable = new GeneratedMessage.FieldAccessorTable(internal_static_paymentchannels_Settlement_descriptor, new String[]{"Tx"});
        internal_static_paymentchannels_Error_descriptor = (Descriptors.Descriptor)Protos.getDescriptor().getMessageTypes().get(10);
        internal_static_paymentchannels_Error_fieldAccessorTable = new GeneratedMessage.FieldAccessorTable(internal_static_paymentchannels_Error_descriptor, new String[]{"Code", "Explanation", "ExpectedValue"});
    }

    public static final class Error
    extends GeneratedMessage
    implements ErrorOrBuilder {
        private static final Error defaultInstance;
        private final UnknownFieldSet unknownFields;
        public static Parser<Error> PARSER;
        private int bitField0_;
        public static final int CODE_FIELD_NUMBER = 1;
        private ErrorCode code_;
        public static final int EXPLANATION_FIELD_NUMBER = 2;
        private Object explanation_;
        public static final int EXPECTED_VALUE_FIELD_NUMBER = 3;
        private long expectedValue_;
        private byte memoizedIsInitialized;
        private int memoizedSerializedSize;
        private static final long serialVersionUID = 0L;

        private Error(GeneratedMessage.Builder<?> builder) {
            super(builder);
            this.memoizedIsInitialized = (byte)-1;
            this.memoizedSerializedSize = -1;
            this.unknownFields = builder.getUnknownFields();
        }

        private Error(boolean noInit) {
            this.memoizedIsInitialized = (byte)-1;
            this.memoizedSerializedSize = -1;
            this.unknownFields = UnknownFieldSet.getDefaultInstance();
        }

        public static Error getDefaultInstance() {
            return defaultInstance;
        }

        public Error getDefaultInstanceForType() {
            return defaultInstance;
        }

        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private Error(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                        case 8: {
                            int rawValue = input.readEnum();
                            ErrorCode value = ErrorCode.valueOf(rawValue);
                            if (value == null) {
                                unknownFields.mergeVarintField(1, rawValue);
                                continue block12;
                            }
                            this.bitField0_ |= 1;
                            this.code_ = value;
                            continue block12;
                        }
                        case 18: {
                            ByteString bs = input.readBytes();
                            this.bitField0_ |= 2;
                            this.explanation_ = bs;
                            continue block12;
                        }
                        case 24: 
                    }
                    this.bitField0_ |= 4;
                    this.expectedValue_ = input.readUInt64();
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
            return internal_static_paymentchannels_Error_descriptor;
        }

        protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
            return internal_static_paymentchannels_Error_fieldAccessorTable.ensureFieldAccessorsInitialized(Error.class, Builder.class);
        }

        public Parser<Error> getParserForType() {
            return PARSER;
        }

        @Override
        public boolean hasCode() {
            return (this.bitField0_ & 1) == 1;
        }

        @Override
        public ErrorCode getCode() {
            return this.code_;
        }

        @Override
        public boolean hasExplanation() {
            return (this.bitField0_ & 2) == 2;
        }

        @Override
        public String getExplanation() {
            Object ref = this.explanation_;
            if (ref instanceof String) {
                return (String)ref;
            }
            ByteString bs = (ByteString)ref;
            String s = bs.toStringUtf8();
            if (bs.isValidUtf8()) {
                this.explanation_ = s;
            }
            return s;
        }

        @Override
        public ByteString getExplanationBytes() {
            Object ref = this.explanation_;
            if (ref instanceof String) {
                ByteString b = ByteString.copyFromUtf8((String)((String)ref));
                this.explanation_ = b;
                return b;
            }
            return (ByteString)ref;
        }

        @Override
        public boolean hasExpectedValue() {
            return (this.bitField0_ & 4) == 4;
        }

        @Override
        public long getExpectedValue() {
            return this.expectedValue_;
        }

        private void initFields() {
            this.code_ = ErrorCode.OTHER;
            this.explanation_ = "";
            this.expectedValue_ = 0L;
        }

        public final boolean isInitialized() {
            byte isInitialized = this.memoizedIsInitialized;
            if (isInitialized == 1) {
                return true;
            }
            if (isInitialized == 0) {
                return false;
            }
            this.memoizedIsInitialized = 1;
            return true;
        }

        public void writeTo(CodedOutputStream output) throws IOException {
            this.getSerializedSize();
            if ((this.bitField0_ & 1) == 1) {
                output.writeEnum(1, this.code_.getNumber());
            }
            if ((this.bitField0_ & 2) == 2) {
                output.writeBytes(2, this.getExplanationBytes());
            }
            if ((this.bitField0_ & 4) == 4) {
                output.writeUInt64(3, this.expectedValue_);
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
                size += CodedOutputStream.computeEnumSize((int)1, (int)this.code_.getNumber());
            }
            if ((this.bitField0_ & 2) == 2) {
                size += CodedOutputStream.computeBytesSize((int)2, (ByteString)this.getExplanationBytes());
            }
            if ((this.bitField0_ & 4) == 4) {
                size += CodedOutputStream.computeUInt64Size((int)3, (long)this.expectedValue_);
            }
            this.memoizedSerializedSize = size += this.getUnknownFields().getSerializedSize();
            return size;
        }

        protected Object writeReplace() throws ObjectStreamException {
            return super.writeReplace();
        }

        public static Error parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (Error)PARSER.parseFrom(data);
        }

        public static Error parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (Error)PARSER.parseFrom(data, extensionRegistry);
        }

        public static Error parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (Error)PARSER.parseFrom(data);
        }

        public static Error parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (Error)PARSER.parseFrom(data, extensionRegistry);
        }

        public static Error parseFrom(InputStream input) throws IOException {
            return (Error)PARSER.parseFrom(input);
        }

        public static Error parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (Error)PARSER.parseFrom(input, extensionRegistry);
        }

        public static Error parseDelimitedFrom(InputStream input) throws IOException {
            return (Error)PARSER.parseDelimitedFrom(input);
        }

        public static Error parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (Error)PARSER.parseDelimitedFrom(input, extensionRegistry);
        }

        public static Error parseFrom(CodedInputStream input) throws IOException {
            return (Error)PARSER.parseFrom(input);
        }

        public static Error parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (Error)PARSER.parseFrom(input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return Builder.create();
        }

        public Builder newBuilderForType() {
            return Error.newBuilder();
        }

        public static Builder newBuilder(Error prototype) {
            return Error.newBuilder().mergeFrom(prototype);
        }

        public Builder toBuilder() {
            return Error.newBuilder(this);
        }

        protected Builder newBuilderForType(GeneratedMessage.BuilderParent parent) {
            Builder builder = new Builder(parent);
            return builder;
        }

        static {
            PARSER = new AbstractParser<Error>(){

                public Error parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                    return new Error(input, extensionRegistry);
                }
            };
            defaultInstance = new Error(true);
            defaultInstance.initFields();
        }

        public static final class Builder
        extends GeneratedMessage.Builder<Builder>
        implements ErrorOrBuilder {
            private int bitField0_;
            private ErrorCode code_ = ErrorCode.OTHER;
            private Object explanation_ = "";
            private long expectedValue_;

            public static final Descriptors.Descriptor getDescriptor() {
                return internal_static_paymentchannels_Error_descriptor;
            }

            protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
                return internal_static_paymentchannels_Error_fieldAccessorTable.ensureFieldAccessorsInitialized(Error.class, Builder.class);
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
                this.code_ = ErrorCode.OTHER;
                this.bitField0_ &= -2;
                this.explanation_ = "";
                this.bitField0_ &= -3;
                this.expectedValue_ = 0L;
                this.bitField0_ &= -5;
                return this;
            }

            public Builder clone() {
                return Builder.create().mergeFrom(this.buildPartial());
            }

            public Descriptors.Descriptor getDescriptorForType() {
                return internal_static_paymentchannels_Error_descriptor;
            }

            public Error getDefaultInstanceForType() {
                return Error.getDefaultInstance();
            }

            public Error build() {
                Error result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw Builder.newUninitializedMessageException((Message)result);
                }
                return result;
            }

            public Error buildPartial() {
                Error result = new Error(this);
                int from_bitField0_ = this.bitField0_;
                int to_bitField0_ = 0;
                if ((from_bitField0_ & 1) == 1) {
                    to_bitField0_ |= true;
                }
                result.code_ = this.code_;
                if ((from_bitField0_ & 2) == 2) {
                    to_bitField0_ |= 2;
                }
                result.explanation_ = this.explanation_;
                if ((from_bitField0_ & 4) == 4) {
                    to_bitField0_ |= 4;
                }
                result.expectedValue_ = this.expectedValue_;
                result.bitField0_ = to_bitField0_;
                this.onBuilt();
                return result;
            }

            public Builder mergeFrom(Message other) {
                if (other instanceof Error) {
                    return this.mergeFrom((Error)other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(Error other) {
                if (other == Error.getDefaultInstance()) {
                    return this;
                }
                if (other.hasCode()) {
                    this.setCode(other.getCode());
                }
                if (other.hasExplanation()) {
                    this.bitField0_ |= 2;
                    this.explanation_ = other.explanation_;
                    this.onChanged();
                }
                if (other.hasExpectedValue()) {
                    this.setExpectedValue(other.getExpectedValue());
                }
                this.mergeUnknownFields(other.getUnknownFields());
                return this;
            }

            public final boolean isInitialized() {
                return true;
            }

            public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                Error parsedMessage = null;
                try {
                    parsedMessage = (Error)Error.PARSER.parsePartialFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e) {
                    parsedMessage = (Error)e.getUnfinishedMessage();
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
            public boolean hasCode() {
                return (this.bitField0_ & 1) == 1;
            }

            @Override
            public ErrorCode getCode() {
                return this.code_;
            }

            public Builder setCode(ErrorCode value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 1;
                this.code_ = value;
                this.onChanged();
                return this;
            }

            public Builder clearCode() {
                this.bitField0_ &= -2;
                this.code_ = ErrorCode.OTHER;
                this.onChanged();
                return this;
            }

            @Override
            public boolean hasExplanation() {
                return (this.bitField0_ & 2) == 2;
            }

            @Override
            public String getExplanation() {
                Object ref = this.explanation_;
                if (!(ref instanceof String)) {
                    ByteString bs = (ByteString)ref;
                    String s = bs.toStringUtf8();
                    if (bs.isValidUtf8()) {
                        this.explanation_ = s;
                    }
                    return s;
                }
                return (String)ref;
            }

            @Override
            public ByteString getExplanationBytes() {
                Object ref = this.explanation_;
                if (ref instanceof String) {
                    ByteString b = ByteString.copyFromUtf8((String)((String)ref));
                    this.explanation_ = b;
                    return b;
                }
                return (ByteString)ref;
            }

            public Builder setExplanation(String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 2;
                this.explanation_ = value;
                this.onChanged();
                return this;
            }

            public Builder clearExplanation() {
                this.bitField0_ &= -3;
                this.explanation_ = Error.getDefaultInstance().getExplanation();
                this.onChanged();
                return this;
            }

            public Builder setExplanationBytes(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 2;
                this.explanation_ = value;
                this.onChanged();
                return this;
            }

            @Override
            public boolean hasExpectedValue() {
                return (this.bitField0_ & 4) == 4;
            }

            @Override
            public long getExpectedValue() {
                return this.expectedValue_;
            }

            public Builder setExpectedValue(long value) {
                this.bitField0_ |= 4;
                this.expectedValue_ = value;
                this.onChanged();
                return this;
            }

            public Builder clearExpectedValue() {
                this.bitField0_ &= -5;
                this.expectedValue_ = 0L;
                this.onChanged();
                return this;
            }
        }

        public static enum ErrorCode implements ProtocolMessageEnum
        {
            TIMEOUT(0, 1),
            SYNTAX_ERROR(1, 2),
            NO_ACCEPTABLE_VERSION(2, 3),
            BAD_TRANSACTION(3, 4),
            TIME_WINDOW_UNACCEPTABLE(4, 5),
            CHANNEL_VALUE_TOO_LARGE(5, 6),
            MIN_PAYMENT_TOO_LARGE(6, 7),
            OTHER(7, 8);
            
            public static final int TIMEOUT_VALUE = 1;
            public static final int SYNTAX_ERROR_VALUE = 2;
            public static final int NO_ACCEPTABLE_VERSION_VALUE = 3;
            public static final int BAD_TRANSACTION_VALUE = 4;
            public static final int TIME_WINDOW_UNACCEPTABLE_VALUE = 5;
            public static final int CHANNEL_VALUE_TOO_LARGE_VALUE = 6;
            public static final int MIN_PAYMENT_TOO_LARGE_VALUE = 7;
            public static final int OTHER_VALUE = 8;
            private static Internal.EnumLiteMap<ErrorCode> internalValueMap;
            private static final ErrorCode[] VALUES;
            private final int index;
            private final int value;

            public final int getNumber() {
                return this.value;
            }

            public static ErrorCode valueOf(int value) {
                switch (value) {
                    case 1: {
                        return TIMEOUT;
                    }
                    case 2: {
                        return SYNTAX_ERROR;
                    }
                    case 3: {
                        return NO_ACCEPTABLE_VERSION;
                    }
                    case 4: {
                        return BAD_TRANSACTION;
                    }
                    case 5: {
                        return TIME_WINDOW_UNACCEPTABLE;
                    }
                    case 6: {
                        return CHANNEL_VALUE_TOO_LARGE;
                    }
                    case 7: {
                        return MIN_PAYMENT_TOO_LARGE;
                    }
                    case 8: {
                        return OTHER;
                    }
                }
                return null;
            }

            public static Internal.EnumLiteMap<ErrorCode> internalGetValueMap() {
                return internalValueMap;
            }

            public final Descriptors.EnumValueDescriptor getValueDescriptor() {
                return (Descriptors.EnumValueDescriptor)ErrorCode.getDescriptor().getValues().get(this.index);
            }

            public final Descriptors.EnumDescriptor getDescriptorForType() {
                return ErrorCode.getDescriptor();
            }

            public static final Descriptors.EnumDescriptor getDescriptor() {
                return (Descriptors.EnumDescriptor)Error.getDescriptor().getEnumTypes().get(0);
            }

            public static ErrorCode valueOf(Descriptors.EnumValueDescriptor desc) {
                if (desc.getType() != ErrorCode.getDescriptor()) {
                    throw new IllegalArgumentException("EnumValueDescriptor is not for this type.");
                }
                return VALUES[desc.getIndex()];
            }

            private ErrorCode(int index, int value) {
                this.index = index;
                this.value = value;
            }

            static {
                internalValueMap = new Internal.EnumLiteMap<ErrorCode>(){

                    public ErrorCode findValueByNumber(int number) {
                        return ErrorCode.valueOf(number);
                    }
                };
                VALUES = ErrorCode.values();
            }

        }

    }

    public static interface ErrorOrBuilder
    extends MessageOrBuilder {
        public boolean hasCode();

        public Error.ErrorCode getCode();

        public boolean hasExplanation();

        public String getExplanation();

        public ByteString getExplanationBytes();

        public boolean hasExpectedValue();

        public long getExpectedValue();
    }

    public static final class Settlement
    extends GeneratedMessage
    implements SettlementOrBuilder {
        private static final Settlement defaultInstance;
        private final UnknownFieldSet unknownFields;
        public static Parser<Settlement> PARSER;
        private int bitField0_;
        public static final int TX_FIELD_NUMBER = 3;
        private ByteString tx_;
        private byte memoizedIsInitialized;
        private int memoizedSerializedSize;
        private static final long serialVersionUID = 0L;

        private Settlement(GeneratedMessage.Builder<?> builder) {
            super(builder);
            this.memoizedIsInitialized = (byte)-1;
            this.memoizedSerializedSize = -1;
            this.unknownFields = builder.getUnknownFields();
        }

        private Settlement(boolean noInit) {
            this.memoizedIsInitialized = (byte)-1;
            this.memoizedSerializedSize = -1;
            this.unknownFields = UnknownFieldSet.getDefaultInstance();
        }

        public static Settlement getDefaultInstance() {
            return defaultInstance;
        }

        public Settlement getDefaultInstanceForType() {
            return defaultInstance;
        }

        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private Settlement(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            this.memoizedIsInitialized = (byte)-1;
            this.memoizedSerializedSize = -1;
            this.initFields();
            boolean mutable_bitField0_ = false;
            UnknownFieldSet.Builder unknownFields = UnknownFieldSet.newBuilder();
            try {
                boolean done = false;
                block10 : while (!done) {
                    int tag = input.readTag();
                    switch (tag) {
                        case 0: {
                            done = true;
                            continue block10;
                        }
                        default: {
                            if (this.parseUnknownField(input, unknownFields, extensionRegistry, tag)) continue block10;
                            done = true;
                            continue block10;
                        }
                        case 26: 
                    }
                    this.bitField0_ |= 1;
                    this.tx_ = input.readBytes();
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
            return internal_static_paymentchannels_Settlement_descriptor;
        }

        protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
            return internal_static_paymentchannels_Settlement_fieldAccessorTable.ensureFieldAccessorsInitialized(Settlement.class, Builder.class);
        }

        public Parser<Settlement> getParserForType() {
            return PARSER;
        }

        @Override
        public boolean hasTx() {
            return (this.bitField0_ & 1) == 1;
        }

        @Override
        public ByteString getTx() {
            return this.tx_;
        }

        private void initFields() {
            this.tx_ = ByteString.EMPTY;
        }

        public final boolean isInitialized() {
            byte isInitialized = this.memoizedIsInitialized;
            if (isInitialized == 1) {
                return true;
            }
            if (isInitialized == 0) {
                return false;
            }
            if (!this.hasTx()) {
                this.memoizedIsInitialized = 0;
                return false;
            }
            this.memoizedIsInitialized = 1;
            return true;
        }

        public void writeTo(CodedOutputStream output) throws IOException {
            this.getSerializedSize();
            if ((this.bitField0_ & 1) == 1) {
                output.writeBytes(3, this.tx_);
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
                size += CodedOutputStream.computeBytesSize((int)3, (ByteString)this.tx_);
            }
            this.memoizedSerializedSize = size += this.getUnknownFields().getSerializedSize();
            return size;
        }

        protected Object writeReplace() throws ObjectStreamException {
            return super.writeReplace();
        }

        public static Settlement parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (Settlement)PARSER.parseFrom(data);
        }

        public static Settlement parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (Settlement)PARSER.parseFrom(data, extensionRegistry);
        }

        public static Settlement parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (Settlement)PARSER.parseFrom(data);
        }

        public static Settlement parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (Settlement)PARSER.parseFrom(data, extensionRegistry);
        }

        public static Settlement parseFrom(InputStream input) throws IOException {
            return (Settlement)PARSER.parseFrom(input);
        }

        public static Settlement parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (Settlement)PARSER.parseFrom(input, extensionRegistry);
        }

        public static Settlement parseDelimitedFrom(InputStream input) throws IOException {
            return (Settlement)PARSER.parseDelimitedFrom(input);
        }

        public static Settlement parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (Settlement)PARSER.parseDelimitedFrom(input, extensionRegistry);
        }

        public static Settlement parseFrom(CodedInputStream input) throws IOException {
            return (Settlement)PARSER.parseFrom(input);
        }

        public static Settlement parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (Settlement)PARSER.parseFrom(input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return Builder.create();
        }

        public Builder newBuilderForType() {
            return Settlement.newBuilder();
        }

        public static Builder newBuilder(Settlement prototype) {
            return Settlement.newBuilder().mergeFrom(prototype);
        }

        public Builder toBuilder() {
            return Settlement.newBuilder(this);
        }

        protected Builder newBuilderForType(GeneratedMessage.BuilderParent parent) {
            Builder builder = new Builder(parent);
            return builder;
        }

        static {
            PARSER = new AbstractParser<Settlement>(){

                public Settlement parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                    return new Settlement(input, extensionRegistry);
                }
            };
            defaultInstance = new Settlement(true);
            defaultInstance.initFields();
        }

        public static final class Builder
        extends GeneratedMessage.Builder<Builder>
        implements SettlementOrBuilder {
            private int bitField0_;
            private ByteString tx_ = ByteString.EMPTY;

            public static final Descriptors.Descriptor getDescriptor() {
                return internal_static_paymentchannels_Settlement_descriptor;
            }

            protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
                return internal_static_paymentchannels_Settlement_fieldAccessorTable.ensureFieldAccessorsInitialized(Settlement.class, Builder.class);
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
                this.tx_ = ByteString.EMPTY;
                this.bitField0_ &= -2;
                return this;
            }

            public Builder clone() {
                return Builder.create().mergeFrom(this.buildPartial());
            }

            public Descriptors.Descriptor getDescriptorForType() {
                return internal_static_paymentchannels_Settlement_descriptor;
            }

            public Settlement getDefaultInstanceForType() {
                return Settlement.getDefaultInstance();
            }

            public Settlement build() {
                Settlement result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw Builder.newUninitializedMessageException((Message)result);
                }
                return result;
            }

            public Settlement buildPartial() {
                Settlement result = new Settlement(this);
                int from_bitField0_ = this.bitField0_;
                int to_bitField0_ = 0;
                if ((from_bitField0_ & 1) == 1) {
                    to_bitField0_ |= true;
                }
                result.tx_ = this.tx_;
                result.bitField0_ = to_bitField0_;
                this.onBuilt();
                return result;
            }

            public Builder mergeFrom(Message other) {
                if (other instanceof Settlement) {
                    return this.mergeFrom((Settlement)other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(Settlement other) {
                if (other == Settlement.getDefaultInstance()) {
                    return this;
                }
                if (other.hasTx()) {
                    this.setTx(other.getTx());
                }
                this.mergeUnknownFields(other.getUnknownFields());
                return this;
            }

            public final boolean isInitialized() {
                if (!this.hasTx()) {
                    return false;
                }
                return true;
            }

            public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                Settlement parsedMessage = null;
                try {
                    parsedMessage = (Settlement)Settlement.PARSER.parsePartialFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e) {
                    parsedMessage = (Settlement)e.getUnfinishedMessage();
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
            public boolean hasTx() {
                return (this.bitField0_ & 1) == 1;
            }

            @Override
            public ByteString getTx() {
                return this.tx_;
            }

            public Builder setTx(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 1;
                this.tx_ = value;
                this.onChanged();
                return this;
            }

            public Builder clearTx() {
                this.bitField0_ &= -2;
                this.tx_ = Settlement.getDefaultInstance().getTx();
                this.onChanged();
                return this;
            }
        }

    }

    public static interface SettlementOrBuilder
    extends MessageOrBuilder {
        public boolean hasTx();

        public ByteString getTx();
    }

    public static final class PaymentAck
    extends GeneratedMessage
    implements PaymentAckOrBuilder {
        private static final PaymentAck defaultInstance;
        private final UnknownFieldSet unknownFields;
        public static Parser<PaymentAck> PARSER;
        private int bitField0_;
        public static final int INFO_FIELD_NUMBER = 1;
        private ByteString info_;
        private byte memoizedIsInitialized;
        private int memoizedSerializedSize;
        private static final long serialVersionUID = 0L;

        private PaymentAck(GeneratedMessage.Builder<?> builder) {
            super(builder);
            this.memoizedIsInitialized = (byte)-1;
            this.memoizedSerializedSize = -1;
            this.unknownFields = builder.getUnknownFields();
        }

        private PaymentAck(boolean noInit) {
            this.memoizedIsInitialized = (byte)-1;
            this.memoizedSerializedSize = -1;
            this.unknownFields = UnknownFieldSet.getDefaultInstance();
        }

        public static PaymentAck getDefaultInstance() {
            return defaultInstance;
        }

        public PaymentAck getDefaultInstanceForType() {
            return defaultInstance;
        }

        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private PaymentAck(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            this.memoizedIsInitialized = (byte)-1;
            this.memoizedSerializedSize = -1;
            this.initFields();
            boolean mutable_bitField0_ = false;
            UnknownFieldSet.Builder unknownFields = UnknownFieldSet.newBuilder();
            try {
                boolean done = false;
                block10 : while (!done) {
                    int tag = input.readTag();
                    switch (tag) {
                        case 0: {
                            done = true;
                            continue block10;
                        }
                        default: {
                            if (this.parseUnknownField(input, unknownFields, extensionRegistry, tag)) continue block10;
                            done = true;
                            continue block10;
                        }
                        case 10: 
                    }
                    this.bitField0_ |= 1;
                    this.info_ = input.readBytes();
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
            return internal_static_paymentchannels_PaymentAck_descriptor;
        }

        protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
            return internal_static_paymentchannels_PaymentAck_fieldAccessorTable.ensureFieldAccessorsInitialized(PaymentAck.class, Builder.class);
        }

        public Parser<PaymentAck> getParserForType() {
            return PARSER;
        }

        @Override
        public boolean hasInfo() {
            return (this.bitField0_ & 1) == 1;
        }

        @Override
        public ByteString getInfo() {
            return this.info_;
        }

        private void initFields() {
            this.info_ = ByteString.EMPTY;
        }

        public final boolean isInitialized() {
            byte isInitialized = this.memoizedIsInitialized;
            if (isInitialized == 1) {
                return true;
            }
            if (isInitialized == 0) {
                return false;
            }
            this.memoizedIsInitialized = 1;
            return true;
        }

        public void writeTo(CodedOutputStream output) throws IOException {
            this.getSerializedSize();
            if ((this.bitField0_ & 1) == 1) {
                output.writeBytes(1, this.info_);
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
                size += CodedOutputStream.computeBytesSize((int)1, (ByteString)this.info_);
            }
            this.memoizedSerializedSize = size += this.getUnknownFields().getSerializedSize();
            return size;
        }

        protected Object writeReplace() throws ObjectStreamException {
            return super.writeReplace();
        }

        public static PaymentAck parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (PaymentAck)PARSER.parseFrom(data);
        }

        public static PaymentAck parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (PaymentAck)PARSER.parseFrom(data, extensionRegistry);
        }

        public static PaymentAck parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (PaymentAck)PARSER.parseFrom(data);
        }

        public static PaymentAck parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (PaymentAck)PARSER.parseFrom(data, extensionRegistry);
        }

        public static PaymentAck parseFrom(InputStream input) throws IOException {
            return (PaymentAck)PARSER.parseFrom(input);
        }

        public static PaymentAck parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (PaymentAck)PARSER.parseFrom(input, extensionRegistry);
        }

        public static PaymentAck parseDelimitedFrom(InputStream input) throws IOException {
            return (PaymentAck)PARSER.parseDelimitedFrom(input);
        }

        public static PaymentAck parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (PaymentAck)PARSER.parseDelimitedFrom(input, extensionRegistry);
        }

        public static PaymentAck parseFrom(CodedInputStream input) throws IOException {
            return (PaymentAck)PARSER.parseFrom(input);
        }

        public static PaymentAck parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (PaymentAck)PARSER.parseFrom(input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return Builder.create();
        }

        public Builder newBuilderForType() {
            return PaymentAck.newBuilder();
        }

        public static Builder newBuilder(PaymentAck prototype) {
            return PaymentAck.newBuilder().mergeFrom(prototype);
        }

        public Builder toBuilder() {
            return PaymentAck.newBuilder(this);
        }

        protected Builder newBuilderForType(GeneratedMessage.BuilderParent parent) {
            Builder builder = new Builder(parent);
            return builder;
        }

        static {
            PARSER = new AbstractParser<PaymentAck>(){

                public PaymentAck parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                    return new PaymentAck(input, extensionRegistry);
                }
            };
            defaultInstance = new PaymentAck(true);
            defaultInstance.initFields();
        }

        public static final class Builder
        extends GeneratedMessage.Builder<Builder>
        implements PaymentAckOrBuilder {
            private int bitField0_;
            private ByteString info_ = ByteString.EMPTY;

            public static final Descriptors.Descriptor getDescriptor() {
                return internal_static_paymentchannels_PaymentAck_descriptor;
            }

            protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
                return internal_static_paymentchannels_PaymentAck_fieldAccessorTable.ensureFieldAccessorsInitialized(PaymentAck.class, Builder.class);
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
                this.info_ = ByteString.EMPTY;
                this.bitField0_ &= -2;
                return this;
            }

            public Builder clone() {
                return Builder.create().mergeFrom(this.buildPartial());
            }

            public Descriptors.Descriptor getDescriptorForType() {
                return internal_static_paymentchannels_PaymentAck_descriptor;
            }

            public PaymentAck getDefaultInstanceForType() {
                return PaymentAck.getDefaultInstance();
            }

            public PaymentAck build() {
                PaymentAck result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw Builder.newUninitializedMessageException((Message)result);
                }
                return result;
            }

            public PaymentAck buildPartial() {
                PaymentAck result = new PaymentAck(this);
                int from_bitField0_ = this.bitField0_;
                int to_bitField0_ = 0;
                if ((from_bitField0_ & 1) == 1) {
                    to_bitField0_ |= true;
                }
                result.info_ = this.info_;
                result.bitField0_ = to_bitField0_;
                this.onBuilt();
                return result;
            }

            public Builder mergeFrom(Message other) {
                if (other instanceof PaymentAck) {
                    return this.mergeFrom((PaymentAck)other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(PaymentAck other) {
                if (other == PaymentAck.getDefaultInstance()) {
                    return this;
                }
                if (other.hasInfo()) {
                    this.setInfo(other.getInfo());
                }
                this.mergeUnknownFields(other.getUnknownFields());
                return this;
            }

            public final boolean isInitialized() {
                return true;
            }

            public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                PaymentAck parsedMessage = null;
                try {
                    parsedMessage = (PaymentAck)PaymentAck.PARSER.parsePartialFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e) {
                    parsedMessage = (PaymentAck)e.getUnfinishedMessage();
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
            public boolean hasInfo() {
                return (this.bitField0_ & 1) == 1;
            }

            @Override
            public ByteString getInfo() {
                return this.info_;
            }

            public Builder setInfo(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 1;
                this.info_ = value;
                this.onChanged();
                return this;
            }

            public Builder clearInfo() {
                this.bitField0_ &= -2;
                this.info_ = PaymentAck.getDefaultInstance().getInfo();
                this.onChanged();
                return this;
            }
        }

    }

    public static interface PaymentAckOrBuilder
    extends MessageOrBuilder {
        public boolean hasInfo();

        public ByteString getInfo();
    }

    public static final class UpdatePayment
    extends GeneratedMessage
    implements UpdatePaymentOrBuilder {
        private static final UpdatePayment defaultInstance;
        private final UnknownFieldSet unknownFields;
        public static Parser<UpdatePayment> PARSER;
        private int bitField0_;
        public static final int CLIENT_CHANGE_VALUE_FIELD_NUMBER = 1;
        private long clientChangeValue_;
        public static final int SIGNATURE_FIELD_NUMBER = 2;
        private ByteString signature_;
        public static final int INFO_FIELD_NUMBER = 3;
        private ByteString info_;
        private byte memoizedIsInitialized;
        private int memoizedSerializedSize;
        private static final long serialVersionUID = 0L;

        private UpdatePayment(GeneratedMessage.Builder<?> builder) {
            super(builder);
            this.memoizedIsInitialized = (byte)-1;
            this.memoizedSerializedSize = -1;
            this.unknownFields = builder.getUnknownFields();
        }

        private UpdatePayment(boolean noInit) {
            this.memoizedIsInitialized = (byte)-1;
            this.memoizedSerializedSize = -1;
            this.unknownFields = UnknownFieldSet.getDefaultInstance();
        }

        public static UpdatePayment getDefaultInstance() {
            return defaultInstance;
        }

        public UpdatePayment getDefaultInstanceForType() {
            return defaultInstance;
        }

        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private UpdatePayment(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                        case 8: {
                            this.bitField0_ |= 1;
                            this.clientChangeValue_ = input.readUInt64();
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
                    this.info_ = input.readBytes();
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
            return internal_static_paymentchannels_UpdatePayment_descriptor;
        }

        protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
            return internal_static_paymentchannels_UpdatePayment_fieldAccessorTable.ensureFieldAccessorsInitialized(UpdatePayment.class, Builder.class);
        }

        public Parser<UpdatePayment> getParserForType() {
            return PARSER;
        }

        @Override
        public boolean hasClientChangeValue() {
            return (this.bitField0_ & 1) == 1;
        }

        @Override
        public long getClientChangeValue() {
            return this.clientChangeValue_;
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
        public boolean hasInfo() {
            return (this.bitField0_ & 4) == 4;
        }

        @Override
        public ByteString getInfo() {
            return this.info_;
        }

        private void initFields() {
            this.clientChangeValue_ = 0L;
            this.signature_ = ByteString.EMPTY;
            this.info_ = ByteString.EMPTY;
        }

        public final boolean isInitialized() {
            byte isInitialized = this.memoizedIsInitialized;
            if (isInitialized == 1) {
                return true;
            }
            if (isInitialized == 0) {
                return false;
            }
            if (!this.hasClientChangeValue()) {
                this.memoizedIsInitialized = 0;
                return false;
            }
            if (!this.hasSignature()) {
                this.memoizedIsInitialized = 0;
                return false;
            }
            this.memoizedIsInitialized = 1;
            return true;
        }

        public void writeTo(CodedOutputStream output) throws IOException {
            this.getSerializedSize();
            if ((this.bitField0_ & 1) == 1) {
                output.writeUInt64(1, this.clientChangeValue_);
            }
            if ((this.bitField0_ & 2) == 2) {
                output.writeBytes(2, this.signature_);
            }
            if ((this.bitField0_ & 4) == 4) {
                output.writeBytes(3, this.info_);
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
                size += CodedOutputStream.computeUInt64Size((int)1, (long)this.clientChangeValue_);
            }
            if ((this.bitField0_ & 2) == 2) {
                size += CodedOutputStream.computeBytesSize((int)2, (ByteString)this.signature_);
            }
            if ((this.bitField0_ & 4) == 4) {
                size += CodedOutputStream.computeBytesSize((int)3, (ByteString)this.info_);
            }
            this.memoizedSerializedSize = size += this.getUnknownFields().getSerializedSize();
            return size;
        }

        protected Object writeReplace() throws ObjectStreamException {
            return super.writeReplace();
        }

        public static UpdatePayment parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (UpdatePayment)PARSER.parseFrom(data);
        }

        public static UpdatePayment parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (UpdatePayment)PARSER.parseFrom(data, extensionRegistry);
        }

        public static UpdatePayment parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (UpdatePayment)PARSER.parseFrom(data);
        }

        public static UpdatePayment parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (UpdatePayment)PARSER.parseFrom(data, extensionRegistry);
        }

        public static UpdatePayment parseFrom(InputStream input) throws IOException {
            return (UpdatePayment)PARSER.parseFrom(input);
        }

        public static UpdatePayment parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (UpdatePayment)PARSER.parseFrom(input, extensionRegistry);
        }

        public static UpdatePayment parseDelimitedFrom(InputStream input) throws IOException {
            return (UpdatePayment)PARSER.parseDelimitedFrom(input);
        }

        public static UpdatePayment parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (UpdatePayment)PARSER.parseDelimitedFrom(input, extensionRegistry);
        }

        public static UpdatePayment parseFrom(CodedInputStream input) throws IOException {
            return (UpdatePayment)PARSER.parseFrom(input);
        }

        public static UpdatePayment parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (UpdatePayment)PARSER.parseFrom(input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return Builder.create();
        }

        public Builder newBuilderForType() {
            return UpdatePayment.newBuilder();
        }

        public static Builder newBuilder(UpdatePayment prototype) {
            return UpdatePayment.newBuilder().mergeFrom(prototype);
        }

        public Builder toBuilder() {
            return UpdatePayment.newBuilder(this);
        }

        protected Builder newBuilderForType(GeneratedMessage.BuilderParent parent) {
            Builder builder = new Builder(parent);
            return builder;
        }

        static {
            PARSER = new AbstractParser<UpdatePayment>(){

                public UpdatePayment parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                    return new UpdatePayment(input, extensionRegistry);
                }
            };
            defaultInstance = new UpdatePayment(true);
            defaultInstance.initFields();
        }

        public static final class Builder
        extends GeneratedMessage.Builder<Builder>
        implements UpdatePaymentOrBuilder {
            private int bitField0_;
            private long clientChangeValue_;
            private ByteString signature_ = ByteString.EMPTY;
            private ByteString info_ = ByteString.EMPTY;

            public static final Descriptors.Descriptor getDescriptor() {
                return internal_static_paymentchannels_UpdatePayment_descriptor;
            }

            protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
                return internal_static_paymentchannels_UpdatePayment_fieldAccessorTable.ensureFieldAccessorsInitialized(UpdatePayment.class, Builder.class);
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
                this.clientChangeValue_ = 0L;
                this.bitField0_ &= -2;
                this.signature_ = ByteString.EMPTY;
                this.bitField0_ &= -3;
                this.info_ = ByteString.EMPTY;
                this.bitField0_ &= -5;
                return this;
            }

            public Builder clone() {
                return Builder.create().mergeFrom(this.buildPartial());
            }

            public Descriptors.Descriptor getDescriptorForType() {
                return internal_static_paymentchannels_UpdatePayment_descriptor;
            }

            public UpdatePayment getDefaultInstanceForType() {
                return UpdatePayment.getDefaultInstance();
            }

            public UpdatePayment build() {
                UpdatePayment result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw Builder.newUninitializedMessageException((Message)result);
                }
                return result;
            }

            public UpdatePayment buildPartial() {
                UpdatePayment result = new UpdatePayment(this);
                int from_bitField0_ = this.bitField0_;
                int to_bitField0_ = 0;
                if ((from_bitField0_ & 1) == 1) {
                    to_bitField0_ |= true;
                }
                result.clientChangeValue_ = this.clientChangeValue_;
                if ((from_bitField0_ & 2) == 2) {
                    to_bitField0_ |= 2;
                }
                result.signature_ = this.signature_;
                if ((from_bitField0_ & 4) == 4) {
                    to_bitField0_ |= 4;
                }
                result.info_ = this.info_;
                result.bitField0_ = to_bitField0_;
                this.onBuilt();
                return result;
            }

            public Builder mergeFrom(Message other) {
                if (other instanceof UpdatePayment) {
                    return this.mergeFrom((UpdatePayment)other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(UpdatePayment other) {
                if (other == UpdatePayment.getDefaultInstance()) {
                    return this;
                }
                if (other.hasClientChangeValue()) {
                    this.setClientChangeValue(other.getClientChangeValue());
                }
                if (other.hasSignature()) {
                    this.setSignature(other.getSignature());
                }
                if (other.hasInfo()) {
                    this.setInfo(other.getInfo());
                }
                this.mergeUnknownFields(other.getUnknownFields());
                return this;
            }

            public final boolean isInitialized() {
                if (!this.hasClientChangeValue()) {
                    return false;
                }
                if (!this.hasSignature()) {
                    return false;
                }
                return true;
            }

            public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                UpdatePayment parsedMessage = null;
                try {
                    parsedMessage = (UpdatePayment)UpdatePayment.PARSER.parsePartialFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e) {
                    parsedMessage = (UpdatePayment)e.getUnfinishedMessage();
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
            public boolean hasClientChangeValue() {
                return (this.bitField0_ & 1) == 1;
            }

            @Override
            public long getClientChangeValue() {
                return this.clientChangeValue_;
            }

            public Builder setClientChangeValue(long value) {
                this.bitField0_ |= 1;
                this.clientChangeValue_ = value;
                this.onChanged();
                return this;
            }

            public Builder clearClientChangeValue() {
                this.bitField0_ &= -2;
                this.clientChangeValue_ = 0L;
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
                this.signature_ = UpdatePayment.getDefaultInstance().getSignature();
                this.onChanged();
                return this;
            }

            @Override
            public boolean hasInfo() {
                return (this.bitField0_ & 4) == 4;
            }

            @Override
            public ByteString getInfo() {
                return this.info_;
            }

            public Builder setInfo(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 4;
                this.info_ = value;
                this.onChanged();
                return this;
            }

            public Builder clearInfo() {
                this.bitField0_ &= -5;
                this.info_ = UpdatePayment.getDefaultInstance().getInfo();
                this.onChanged();
                return this;
            }
        }

    }

    public static interface UpdatePaymentOrBuilder
    extends MessageOrBuilder {
        public boolean hasClientChangeValue();

        public long getClientChangeValue();

        public boolean hasSignature();

        public ByteString getSignature();

        public boolean hasInfo();

        public ByteString getInfo();
    }

    public static final class ProvideContract
    extends GeneratedMessage
    implements ProvideContractOrBuilder {
        private static final ProvideContract defaultInstance;
        private final UnknownFieldSet unknownFields;
        public static Parser<ProvideContract> PARSER;
        private int bitField0_;
        public static final int TX_FIELD_NUMBER = 1;
        private ByteString tx_;
        public static final int INITIAL_PAYMENT_FIELD_NUMBER = 2;
        private UpdatePayment initialPayment_;
        public static final int CLIENT_KEY_FIELD_NUMBER = 3;
        private ByteString clientKey_;
        private byte memoizedIsInitialized;
        private int memoizedSerializedSize;
        private static final long serialVersionUID = 0L;

        private ProvideContract(GeneratedMessage.Builder<?> builder) {
            super(builder);
            this.memoizedIsInitialized = (byte)-1;
            this.memoizedSerializedSize = -1;
            this.unknownFields = builder.getUnknownFields();
        }

        private ProvideContract(boolean noInit) {
            this.memoizedIsInitialized = (byte)-1;
            this.memoizedSerializedSize = -1;
            this.unknownFields = UnknownFieldSet.getDefaultInstance();
        }

        public static ProvideContract getDefaultInstance() {
            return defaultInstance;
        }

        public ProvideContract getDefaultInstanceForType() {
            return defaultInstance;
        }

        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private ProvideContract(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                            this.tx_ = input.readBytes();
                            continue block12;
                        }
                        case 18: {
                            UpdatePayment.Builder subBuilder = null;
                            if ((this.bitField0_ & 2) == 2) {
                                subBuilder = this.initialPayment_.toBuilder();
                            }
                            this.initialPayment_ = (UpdatePayment)input.readMessage(UpdatePayment.PARSER, extensionRegistry);
                            if (subBuilder != null) {
                                subBuilder.mergeFrom(this.initialPayment_);
                                this.initialPayment_ = subBuilder.buildPartial();
                            }
                            this.bitField0_ |= 2;
                            continue block12;
                        }
                        case 26: 
                    }
                    this.bitField0_ |= 4;
                    this.clientKey_ = input.readBytes();
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
            return internal_static_paymentchannels_ProvideContract_descriptor;
        }

        protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
            return internal_static_paymentchannels_ProvideContract_fieldAccessorTable.ensureFieldAccessorsInitialized(ProvideContract.class, Builder.class);
        }

        public Parser<ProvideContract> getParserForType() {
            return PARSER;
        }

        @Override
        public boolean hasTx() {
            return (this.bitField0_ & 1) == 1;
        }

        @Override
        public ByteString getTx() {
            return this.tx_;
        }

        @Override
        public boolean hasInitialPayment() {
            return (this.bitField0_ & 2) == 2;
        }

        @Override
        public UpdatePayment getInitialPayment() {
            return this.initialPayment_;
        }

        @Override
        public UpdatePaymentOrBuilder getInitialPaymentOrBuilder() {
            return this.initialPayment_;
        }

        @Override
        public boolean hasClientKey() {
            return (this.bitField0_ & 4) == 4;
        }

        @Override
        public ByteString getClientKey() {
            return this.clientKey_;
        }

        private void initFields() {
            this.tx_ = ByteString.EMPTY;
            this.initialPayment_ = UpdatePayment.getDefaultInstance();
            this.clientKey_ = ByteString.EMPTY;
        }

        public final boolean isInitialized() {
            byte isInitialized = this.memoizedIsInitialized;
            if (isInitialized == 1) {
                return true;
            }
            if (isInitialized == 0) {
                return false;
            }
            if (!this.hasTx()) {
                this.memoizedIsInitialized = 0;
                return false;
            }
            if (!this.hasInitialPayment()) {
                this.memoizedIsInitialized = 0;
                return false;
            }
            if (!this.getInitialPayment().isInitialized()) {
                this.memoizedIsInitialized = 0;
                return false;
            }
            this.memoizedIsInitialized = 1;
            return true;
        }

        public void writeTo(CodedOutputStream output) throws IOException {
            this.getSerializedSize();
            if ((this.bitField0_ & 1) == 1) {
                output.writeBytes(1, this.tx_);
            }
            if ((this.bitField0_ & 2) == 2) {
                output.writeMessage(2, (MessageLite)this.initialPayment_);
            }
            if ((this.bitField0_ & 4) == 4) {
                output.writeBytes(3, this.clientKey_);
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
                size += CodedOutputStream.computeBytesSize((int)1, (ByteString)this.tx_);
            }
            if ((this.bitField0_ & 2) == 2) {
                size += CodedOutputStream.computeMessageSize((int)2, (MessageLite)this.initialPayment_);
            }
            if ((this.bitField0_ & 4) == 4) {
                size += CodedOutputStream.computeBytesSize((int)3, (ByteString)this.clientKey_);
            }
            this.memoizedSerializedSize = size += this.getUnknownFields().getSerializedSize();
            return size;
        }

        protected Object writeReplace() throws ObjectStreamException {
            return super.writeReplace();
        }

        public static ProvideContract parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (ProvideContract)PARSER.parseFrom(data);
        }

        public static ProvideContract parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (ProvideContract)PARSER.parseFrom(data, extensionRegistry);
        }

        public static ProvideContract parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (ProvideContract)PARSER.parseFrom(data);
        }

        public static ProvideContract parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (ProvideContract)PARSER.parseFrom(data, extensionRegistry);
        }

        public static ProvideContract parseFrom(InputStream input) throws IOException {
            return (ProvideContract)PARSER.parseFrom(input);
        }

        public static ProvideContract parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (ProvideContract)PARSER.parseFrom(input, extensionRegistry);
        }

        public static ProvideContract parseDelimitedFrom(InputStream input) throws IOException {
            return (ProvideContract)PARSER.parseDelimitedFrom(input);
        }

        public static ProvideContract parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (ProvideContract)PARSER.parseDelimitedFrom(input, extensionRegistry);
        }

        public static ProvideContract parseFrom(CodedInputStream input) throws IOException {
            return (ProvideContract)PARSER.parseFrom(input);
        }

        public static ProvideContract parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (ProvideContract)PARSER.parseFrom(input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return Builder.create();
        }

        public Builder newBuilderForType() {
            return ProvideContract.newBuilder();
        }

        public static Builder newBuilder(ProvideContract prototype) {
            return ProvideContract.newBuilder().mergeFrom(prototype);
        }

        public Builder toBuilder() {
            return ProvideContract.newBuilder(this);
        }

        protected Builder newBuilderForType(GeneratedMessage.BuilderParent parent) {
            Builder builder = new Builder(parent);
            return builder;
        }

        static {
            PARSER = new AbstractParser<ProvideContract>(){

                public ProvideContract parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                    return new ProvideContract(input, extensionRegistry);
                }
            };
            defaultInstance = new ProvideContract(true);
            defaultInstance.initFields();
        }

        public static final class Builder
        extends GeneratedMessage.Builder<Builder>
        implements ProvideContractOrBuilder {
            private int bitField0_;
            private ByteString tx_ = ByteString.EMPTY;
            private UpdatePayment initialPayment_ = UpdatePayment.getDefaultInstance();
            private SingleFieldBuilder<UpdatePayment, UpdatePayment.Builder, UpdatePaymentOrBuilder> initialPaymentBuilder_;
            private ByteString clientKey_ = ByteString.EMPTY;

            public static final Descriptors.Descriptor getDescriptor() {
                return internal_static_paymentchannels_ProvideContract_descriptor;
            }

            protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
                return internal_static_paymentchannels_ProvideContract_fieldAccessorTable.ensureFieldAccessorsInitialized(ProvideContract.class, Builder.class);
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
                    this.getInitialPaymentFieldBuilder();
                }
            }

            private static Builder create() {
                return new Builder();
            }

            public Builder clear() {
                super.clear();
                this.tx_ = ByteString.EMPTY;
                this.bitField0_ &= -2;
                if (this.initialPaymentBuilder_ == null) {
                    this.initialPayment_ = UpdatePayment.getDefaultInstance();
                } else {
                    this.initialPaymentBuilder_.clear();
                }
                this.bitField0_ &= -3;
                this.clientKey_ = ByteString.EMPTY;
                this.bitField0_ &= -5;
                return this;
            }

            public Builder clone() {
                return Builder.create().mergeFrom(this.buildPartial());
            }

            public Descriptors.Descriptor getDescriptorForType() {
                return internal_static_paymentchannels_ProvideContract_descriptor;
            }

            public ProvideContract getDefaultInstanceForType() {
                return ProvideContract.getDefaultInstance();
            }

            public ProvideContract build() {
                ProvideContract result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw Builder.newUninitializedMessageException((Message)result);
                }
                return result;
            }

            public ProvideContract buildPartial() {
                ProvideContract result = new ProvideContract(this);
                int from_bitField0_ = this.bitField0_;
                int to_bitField0_ = 0;
                if ((from_bitField0_ & 1) == 1) {
                    to_bitField0_ |= true;
                }
                result.tx_ = this.tx_;
                if ((from_bitField0_ & 2) == 2) {
                    to_bitField0_ |= 2;
                }
                if (this.initialPaymentBuilder_ == null) {
                    result.initialPayment_ = this.initialPayment_;
                } else {
                    result.initialPayment_ = (UpdatePayment)this.initialPaymentBuilder_.build();
                }
                if ((from_bitField0_ & 4) == 4) {
                    to_bitField0_ |= 4;
                }
                result.clientKey_ = this.clientKey_;
                result.bitField0_ = to_bitField0_;
                this.onBuilt();
                return result;
            }

            public Builder mergeFrom(Message other) {
                if (other instanceof ProvideContract) {
                    return this.mergeFrom((ProvideContract)other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(ProvideContract other) {
                if (other == ProvideContract.getDefaultInstance()) {
                    return this;
                }
                if (other.hasTx()) {
                    this.setTx(other.getTx());
                }
                if (other.hasInitialPayment()) {
                    this.mergeInitialPayment(other.getInitialPayment());
                }
                if (other.hasClientKey()) {
                    this.setClientKey(other.getClientKey());
                }
                this.mergeUnknownFields(other.getUnknownFields());
                return this;
            }

            public final boolean isInitialized() {
                if (!this.hasTx()) {
                    return false;
                }
                if (!this.hasInitialPayment()) {
                    return false;
                }
                if (!this.getInitialPayment().isInitialized()) {
                    return false;
                }
                return true;
            }

            public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                ProvideContract parsedMessage = null;
                try {
                    parsedMessage = (ProvideContract)ProvideContract.PARSER.parsePartialFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e) {
                    parsedMessage = (ProvideContract)e.getUnfinishedMessage();
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
            public boolean hasTx() {
                return (this.bitField0_ & 1) == 1;
            }

            @Override
            public ByteString getTx() {
                return this.tx_;
            }

            public Builder setTx(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 1;
                this.tx_ = value;
                this.onChanged();
                return this;
            }

            public Builder clearTx() {
                this.bitField0_ &= -2;
                this.tx_ = ProvideContract.getDefaultInstance().getTx();
                this.onChanged();
                return this;
            }

            @Override
            public boolean hasInitialPayment() {
                return (this.bitField0_ & 2) == 2;
            }

            @Override
            public UpdatePayment getInitialPayment() {
                if (this.initialPaymentBuilder_ == null) {
                    return this.initialPayment_;
                }
                return (UpdatePayment)this.initialPaymentBuilder_.getMessage();
            }

            public Builder setInitialPayment(UpdatePayment value) {
                if (this.initialPaymentBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.initialPayment_ = value;
                    this.onChanged();
                } else {
                    this.initialPaymentBuilder_.setMessage((GeneratedMessage)value);
                }
                this.bitField0_ |= 2;
                return this;
            }

            public Builder setInitialPayment(UpdatePayment.Builder builderForValue) {
                if (this.initialPaymentBuilder_ == null) {
                    this.initialPayment_ = builderForValue.build();
                    this.onChanged();
                } else {
                    this.initialPaymentBuilder_.setMessage((GeneratedMessage)builderForValue.build());
                }
                this.bitField0_ |= 2;
                return this;
            }

            public Builder mergeInitialPayment(UpdatePayment value) {
                if (this.initialPaymentBuilder_ == null) {
                    this.initialPayment_ = (this.bitField0_ & 2) == 2 && this.initialPayment_ != UpdatePayment.getDefaultInstance() ? UpdatePayment.newBuilder(this.initialPayment_).mergeFrom(value).buildPartial() : value;
                    this.onChanged();
                } else {
                    this.initialPaymentBuilder_.mergeFrom((GeneratedMessage)value);
                }
                this.bitField0_ |= 2;
                return this;
            }

            public Builder clearInitialPayment() {
                if (this.initialPaymentBuilder_ == null) {
                    this.initialPayment_ = UpdatePayment.getDefaultInstance();
                    this.onChanged();
                } else {
                    this.initialPaymentBuilder_.clear();
                }
                this.bitField0_ &= -3;
                return this;
            }

            public UpdatePayment.Builder getInitialPaymentBuilder() {
                this.bitField0_ |= 2;
                this.onChanged();
                return (UpdatePayment.Builder)this.getInitialPaymentFieldBuilder().getBuilder();
            }

            @Override
            public UpdatePaymentOrBuilder getInitialPaymentOrBuilder() {
                if (this.initialPaymentBuilder_ != null) {
                    return (UpdatePaymentOrBuilder)this.initialPaymentBuilder_.getMessageOrBuilder();
                }
                return this.initialPayment_;
            }

            private SingleFieldBuilder<UpdatePayment, UpdatePayment.Builder, UpdatePaymentOrBuilder> getInitialPaymentFieldBuilder() {
                if (this.initialPaymentBuilder_ == null) {
                    this.initialPaymentBuilder_ = new SingleFieldBuilder((GeneratedMessage)this.getInitialPayment(), this.getParentForChildren(), this.isClean());
                    this.initialPayment_ = null;
                }
                return this.initialPaymentBuilder_;
            }

            @Override
            public boolean hasClientKey() {
                return (this.bitField0_ & 4) == 4;
            }

            @Override
            public ByteString getClientKey() {
                return this.clientKey_;
            }

            public Builder setClientKey(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 4;
                this.clientKey_ = value;
                this.onChanged();
                return this;
            }

            public Builder clearClientKey() {
                this.bitField0_ &= -5;
                this.clientKey_ = ProvideContract.getDefaultInstance().getClientKey();
                this.onChanged();
                return this;
            }
        }

    }

    public static interface ProvideContractOrBuilder
    extends MessageOrBuilder {
        public boolean hasTx();

        public ByteString getTx();

        public boolean hasInitialPayment();

        public UpdatePayment getInitialPayment();

        public UpdatePaymentOrBuilder getInitialPaymentOrBuilder();

        public boolean hasClientKey();

        public ByteString getClientKey();
    }

    public static final class ReturnRefund
    extends GeneratedMessage
    implements ReturnRefundOrBuilder {
        private static final ReturnRefund defaultInstance;
        private final UnknownFieldSet unknownFields;
        public static Parser<ReturnRefund> PARSER;
        private int bitField0_;
        public static final int SIGNATURE_FIELD_NUMBER = 1;
        private ByteString signature_;
        private byte memoizedIsInitialized;
        private int memoizedSerializedSize;
        private static final long serialVersionUID = 0L;

        private ReturnRefund(GeneratedMessage.Builder<?> builder) {
            super(builder);
            this.memoizedIsInitialized = (byte)-1;
            this.memoizedSerializedSize = -1;
            this.unknownFields = builder.getUnknownFields();
        }

        private ReturnRefund(boolean noInit) {
            this.memoizedIsInitialized = (byte)-1;
            this.memoizedSerializedSize = -1;
            this.unknownFields = UnknownFieldSet.getDefaultInstance();
        }

        public static ReturnRefund getDefaultInstance() {
            return defaultInstance;
        }

        public ReturnRefund getDefaultInstanceForType() {
            return defaultInstance;
        }

        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private ReturnRefund(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            this.memoizedIsInitialized = (byte)-1;
            this.memoizedSerializedSize = -1;
            this.initFields();
            boolean mutable_bitField0_ = false;
            UnknownFieldSet.Builder unknownFields = UnknownFieldSet.newBuilder();
            try {
                boolean done = false;
                block10 : while (!done) {
                    int tag = input.readTag();
                    switch (tag) {
                        case 0: {
                            done = true;
                            continue block10;
                        }
                        default: {
                            if (this.parseUnknownField(input, unknownFields, extensionRegistry, tag)) continue block10;
                            done = true;
                            continue block10;
                        }
                        case 10: 
                    }
                    this.bitField0_ |= 1;
                    this.signature_ = input.readBytes();
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
            return internal_static_paymentchannels_ReturnRefund_descriptor;
        }

        protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
            return internal_static_paymentchannels_ReturnRefund_fieldAccessorTable.ensureFieldAccessorsInitialized(ReturnRefund.class, Builder.class);
        }

        public Parser<ReturnRefund> getParserForType() {
            return PARSER;
        }

        @Override
        public boolean hasSignature() {
            return (this.bitField0_ & 1) == 1;
        }

        @Override
        public ByteString getSignature() {
            return this.signature_;
        }

        private void initFields() {
            this.signature_ = ByteString.EMPTY;
        }

        public final boolean isInitialized() {
            byte isInitialized = this.memoizedIsInitialized;
            if (isInitialized == 1) {
                return true;
            }
            if (isInitialized == 0) {
                return false;
            }
            if (!this.hasSignature()) {
                this.memoizedIsInitialized = 0;
                return false;
            }
            this.memoizedIsInitialized = 1;
            return true;
        }

        public void writeTo(CodedOutputStream output) throws IOException {
            this.getSerializedSize();
            if ((this.bitField0_ & 1) == 1) {
                output.writeBytes(1, this.signature_);
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
                size += CodedOutputStream.computeBytesSize((int)1, (ByteString)this.signature_);
            }
            this.memoizedSerializedSize = size += this.getUnknownFields().getSerializedSize();
            return size;
        }

        protected Object writeReplace() throws ObjectStreamException {
            return super.writeReplace();
        }

        public static ReturnRefund parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (ReturnRefund)PARSER.parseFrom(data);
        }

        public static ReturnRefund parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (ReturnRefund)PARSER.parseFrom(data, extensionRegistry);
        }

        public static ReturnRefund parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (ReturnRefund)PARSER.parseFrom(data);
        }

        public static ReturnRefund parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (ReturnRefund)PARSER.parseFrom(data, extensionRegistry);
        }

        public static ReturnRefund parseFrom(InputStream input) throws IOException {
            return (ReturnRefund)PARSER.parseFrom(input);
        }

        public static ReturnRefund parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (ReturnRefund)PARSER.parseFrom(input, extensionRegistry);
        }

        public static ReturnRefund parseDelimitedFrom(InputStream input) throws IOException {
            return (ReturnRefund)PARSER.parseDelimitedFrom(input);
        }

        public static ReturnRefund parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (ReturnRefund)PARSER.parseDelimitedFrom(input, extensionRegistry);
        }

        public static ReturnRefund parseFrom(CodedInputStream input) throws IOException {
            return (ReturnRefund)PARSER.parseFrom(input);
        }

        public static ReturnRefund parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (ReturnRefund)PARSER.parseFrom(input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return Builder.create();
        }

        public Builder newBuilderForType() {
            return ReturnRefund.newBuilder();
        }

        public static Builder newBuilder(ReturnRefund prototype) {
            return ReturnRefund.newBuilder().mergeFrom(prototype);
        }

        public Builder toBuilder() {
            return ReturnRefund.newBuilder(this);
        }

        protected Builder newBuilderForType(GeneratedMessage.BuilderParent parent) {
            Builder builder = new Builder(parent);
            return builder;
        }

        static {
            PARSER = new AbstractParser<ReturnRefund>(){

                public ReturnRefund parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                    return new ReturnRefund(input, extensionRegistry);
                }
            };
            defaultInstance = new ReturnRefund(true);
            defaultInstance.initFields();
        }

        public static final class Builder
        extends GeneratedMessage.Builder<Builder>
        implements ReturnRefundOrBuilder {
            private int bitField0_;
            private ByteString signature_ = ByteString.EMPTY;

            public static final Descriptors.Descriptor getDescriptor() {
                return internal_static_paymentchannels_ReturnRefund_descriptor;
            }

            protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
                return internal_static_paymentchannels_ReturnRefund_fieldAccessorTable.ensureFieldAccessorsInitialized(ReturnRefund.class, Builder.class);
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
                this.signature_ = ByteString.EMPTY;
                this.bitField0_ &= -2;
                return this;
            }

            public Builder clone() {
                return Builder.create().mergeFrom(this.buildPartial());
            }

            public Descriptors.Descriptor getDescriptorForType() {
                return internal_static_paymentchannels_ReturnRefund_descriptor;
            }

            public ReturnRefund getDefaultInstanceForType() {
                return ReturnRefund.getDefaultInstance();
            }

            public ReturnRefund build() {
                ReturnRefund result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw Builder.newUninitializedMessageException((Message)result);
                }
                return result;
            }

            public ReturnRefund buildPartial() {
                ReturnRefund result = new ReturnRefund(this);
                int from_bitField0_ = this.bitField0_;
                int to_bitField0_ = 0;
                if ((from_bitField0_ & 1) == 1) {
                    to_bitField0_ |= true;
                }
                result.signature_ = this.signature_;
                result.bitField0_ = to_bitField0_;
                this.onBuilt();
                return result;
            }

            public Builder mergeFrom(Message other) {
                if (other instanceof ReturnRefund) {
                    return this.mergeFrom((ReturnRefund)other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(ReturnRefund other) {
                if (other == ReturnRefund.getDefaultInstance()) {
                    return this;
                }
                if (other.hasSignature()) {
                    this.setSignature(other.getSignature());
                }
                this.mergeUnknownFields(other.getUnknownFields());
                return this;
            }

            public final boolean isInitialized() {
                if (!this.hasSignature()) {
                    return false;
                }
                return true;
            }

            public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                ReturnRefund parsedMessage = null;
                try {
                    parsedMessage = (ReturnRefund)ReturnRefund.PARSER.parsePartialFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e) {
                    parsedMessage = (ReturnRefund)e.getUnfinishedMessage();
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
            public boolean hasSignature() {
                return (this.bitField0_ & 1) == 1;
            }

            @Override
            public ByteString getSignature() {
                return this.signature_;
            }

            public Builder setSignature(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 1;
                this.signature_ = value;
                this.onChanged();
                return this;
            }

            public Builder clearSignature() {
                this.bitField0_ &= -2;
                this.signature_ = ReturnRefund.getDefaultInstance().getSignature();
                this.onChanged();
                return this;
            }
        }

    }

    public static interface ReturnRefundOrBuilder
    extends MessageOrBuilder {
        public boolean hasSignature();

        public ByteString getSignature();
    }

    public static final class ProvideRefund
    extends GeneratedMessage
    implements ProvideRefundOrBuilder {
        private static final ProvideRefund defaultInstance;
        private final UnknownFieldSet unknownFields;
        public static Parser<ProvideRefund> PARSER;
        private int bitField0_;
        public static final int MULTISIG_KEY_FIELD_NUMBER = 1;
        private ByteString multisigKey_;
        public static final int TX_FIELD_NUMBER = 2;
        private ByteString tx_;
        private byte memoizedIsInitialized;
        private int memoizedSerializedSize;
        private static final long serialVersionUID = 0L;

        private ProvideRefund(GeneratedMessage.Builder<?> builder) {
            super(builder);
            this.memoizedIsInitialized = (byte)-1;
            this.memoizedSerializedSize = -1;
            this.unknownFields = builder.getUnknownFields();
        }

        private ProvideRefund(boolean noInit) {
            this.memoizedIsInitialized = (byte)-1;
            this.memoizedSerializedSize = -1;
            this.unknownFields = UnknownFieldSet.getDefaultInstance();
        }

        public static ProvideRefund getDefaultInstance() {
            return defaultInstance;
        }

        public ProvideRefund getDefaultInstanceForType() {
            return defaultInstance;
        }

        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private ProvideRefund(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            this.memoizedIsInitialized = (byte)-1;
            this.memoizedSerializedSize = -1;
            this.initFields();
            boolean mutable_bitField0_ = false;
            UnknownFieldSet.Builder unknownFields = UnknownFieldSet.newBuilder();
            try {
                boolean done = false;
                block11 : while (!done) {
                    int tag = input.readTag();
                    switch (tag) {
                        case 0: {
                            done = true;
                            continue block11;
                        }
                        default: {
                            if (this.parseUnknownField(input, unknownFields, extensionRegistry, tag)) continue block11;
                            done = true;
                            continue block11;
                        }
                        case 10: {
                            this.bitField0_ |= 1;
                            this.multisigKey_ = input.readBytes();
                            continue block11;
                        }
                        case 18: 
                    }
                    this.bitField0_ |= 2;
                    this.tx_ = input.readBytes();
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
            return internal_static_paymentchannels_ProvideRefund_descriptor;
        }

        protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
            return internal_static_paymentchannels_ProvideRefund_fieldAccessorTable.ensureFieldAccessorsInitialized(ProvideRefund.class, Builder.class);
        }

        public Parser<ProvideRefund> getParserForType() {
            return PARSER;
        }

        @Override
        public boolean hasMultisigKey() {
            return (this.bitField0_ & 1) == 1;
        }

        @Override
        public ByteString getMultisigKey() {
            return this.multisigKey_;
        }

        @Override
        public boolean hasTx() {
            return (this.bitField0_ & 2) == 2;
        }

        @Override
        public ByteString getTx() {
            return this.tx_;
        }

        private void initFields() {
            this.multisigKey_ = ByteString.EMPTY;
            this.tx_ = ByteString.EMPTY;
        }

        public final boolean isInitialized() {
            byte isInitialized = this.memoizedIsInitialized;
            if (isInitialized == 1) {
                return true;
            }
            if (isInitialized == 0) {
                return false;
            }
            if (!this.hasMultisigKey()) {
                this.memoizedIsInitialized = 0;
                return false;
            }
            if (!this.hasTx()) {
                this.memoizedIsInitialized = 0;
                return false;
            }
            this.memoizedIsInitialized = 1;
            return true;
        }

        public void writeTo(CodedOutputStream output) throws IOException {
            this.getSerializedSize();
            if ((this.bitField0_ & 1) == 1) {
                output.writeBytes(1, this.multisigKey_);
            }
            if ((this.bitField0_ & 2) == 2) {
                output.writeBytes(2, this.tx_);
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
                size += CodedOutputStream.computeBytesSize((int)1, (ByteString)this.multisigKey_);
            }
            if ((this.bitField0_ & 2) == 2) {
                size += CodedOutputStream.computeBytesSize((int)2, (ByteString)this.tx_);
            }
            this.memoizedSerializedSize = size += this.getUnknownFields().getSerializedSize();
            return size;
        }

        protected Object writeReplace() throws ObjectStreamException {
            return super.writeReplace();
        }

        public static ProvideRefund parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (ProvideRefund)PARSER.parseFrom(data);
        }

        public static ProvideRefund parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (ProvideRefund)PARSER.parseFrom(data, extensionRegistry);
        }

        public static ProvideRefund parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (ProvideRefund)PARSER.parseFrom(data);
        }

        public static ProvideRefund parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (ProvideRefund)PARSER.parseFrom(data, extensionRegistry);
        }

        public static ProvideRefund parseFrom(InputStream input) throws IOException {
            return (ProvideRefund)PARSER.parseFrom(input);
        }

        public static ProvideRefund parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (ProvideRefund)PARSER.parseFrom(input, extensionRegistry);
        }

        public static ProvideRefund parseDelimitedFrom(InputStream input) throws IOException {
            return (ProvideRefund)PARSER.parseDelimitedFrom(input);
        }

        public static ProvideRefund parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (ProvideRefund)PARSER.parseDelimitedFrom(input, extensionRegistry);
        }

        public static ProvideRefund parseFrom(CodedInputStream input) throws IOException {
            return (ProvideRefund)PARSER.parseFrom(input);
        }

        public static ProvideRefund parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (ProvideRefund)PARSER.parseFrom(input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return Builder.create();
        }

        public Builder newBuilderForType() {
            return ProvideRefund.newBuilder();
        }

        public static Builder newBuilder(ProvideRefund prototype) {
            return ProvideRefund.newBuilder().mergeFrom(prototype);
        }

        public Builder toBuilder() {
            return ProvideRefund.newBuilder(this);
        }

        protected Builder newBuilderForType(GeneratedMessage.BuilderParent parent) {
            Builder builder = new Builder(parent);
            return builder;
        }

        static {
            PARSER = new AbstractParser<ProvideRefund>(){

                public ProvideRefund parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                    return new ProvideRefund(input, extensionRegistry);
                }
            };
            defaultInstance = new ProvideRefund(true);
            defaultInstance.initFields();
        }

        public static final class Builder
        extends GeneratedMessage.Builder<Builder>
        implements ProvideRefundOrBuilder {
            private int bitField0_;
            private ByteString multisigKey_ = ByteString.EMPTY;
            private ByteString tx_ = ByteString.EMPTY;

            public static final Descriptors.Descriptor getDescriptor() {
                return internal_static_paymentchannels_ProvideRefund_descriptor;
            }

            protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
                return internal_static_paymentchannels_ProvideRefund_fieldAccessorTable.ensureFieldAccessorsInitialized(ProvideRefund.class, Builder.class);
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
                this.multisigKey_ = ByteString.EMPTY;
                this.bitField0_ &= -2;
                this.tx_ = ByteString.EMPTY;
                this.bitField0_ &= -3;
                return this;
            }

            public Builder clone() {
                return Builder.create().mergeFrom(this.buildPartial());
            }

            public Descriptors.Descriptor getDescriptorForType() {
                return internal_static_paymentchannels_ProvideRefund_descriptor;
            }

            public ProvideRefund getDefaultInstanceForType() {
                return ProvideRefund.getDefaultInstance();
            }

            public ProvideRefund build() {
                ProvideRefund result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw Builder.newUninitializedMessageException((Message)result);
                }
                return result;
            }

            public ProvideRefund buildPartial() {
                ProvideRefund result = new ProvideRefund(this);
                int from_bitField0_ = this.bitField0_;
                int to_bitField0_ = 0;
                if ((from_bitField0_ & 1) == 1) {
                    to_bitField0_ |= true;
                }
                result.multisigKey_ = this.multisigKey_;
                if ((from_bitField0_ & 2) == 2) {
                    to_bitField0_ |= 2;
                }
                result.tx_ = this.tx_;
                result.bitField0_ = to_bitField0_;
                this.onBuilt();
                return result;
            }

            public Builder mergeFrom(Message other) {
                if (other instanceof ProvideRefund) {
                    return this.mergeFrom((ProvideRefund)other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(ProvideRefund other) {
                if (other == ProvideRefund.getDefaultInstance()) {
                    return this;
                }
                if (other.hasMultisigKey()) {
                    this.setMultisigKey(other.getMultisigKey());
                }
                if (other.hasTx()) {
                    this.setTx(other.getTx());
                }
                this.mergeUnknownFields(other.getUnknownFields());
                return this;
            }

            public final boolean isInitialized() {
                if (!this.hasMultisigKey()) {
                    return false;
                }
                if (!this.hasTx()) {
                    return false;
                }
                return true;
            }

            public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                ProvideRefund parsedMessage = null;
                try {
                    parsedMessage = (ProvideRefund)ProvideRefund.PARSER.parsePartialFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e) {
                    parsedMessage = (ProvideRefund)e.getUnfinishedMessage();
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
            public boolean hasMultisigKey() {
                return (this.bitField0_ & 1) == 1;
            }

            @Override
            public ByteString getMultisigKey() {
                return this.multisigKey_;
            }

            public Builder setMultisigKey(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 1;
                this.multisigKey_ = value;
                this.onChanged();
                return this;
            }

            public Builder clearMultisigKey() {
                this.bitField0_ &= -2;
                this.multisigKey_ = ProvideRefund.getDefaultInstance().getMultisigKey();
                this.onChanged();
                return this;
            }

            @Override
            public boolean hasTx() {
                return (this.bitField0_ & 2) == 2;
            }

            @Override
            public ByteString getTx() {
                return this.tx_;
            }

            public Builder setTx(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 2;
                this.tx_ = value;
                this.onChanged();
                return this;
            }

            public Builder clearTx() {
                this.bitField0_ &= -3;
                this.tx_ = ProvideRefund.getDefaultInstance().getTx();
                this.onChanged();
                return this;
            }
        }

    }

    public static interface ProvideRefundOrBuilder
    extends MessageOrBuilder {
        public boolean hasMultisigKey();

        public ByteString getMultisigKey();

        public boolean hasTx();

        public ByteString getTx();
    }

    public static final class Initiate
    extends GeneratedMessage
    implements InitiateOrBuilder {
        private static final Initiate defaultInstance;
        private final UnknownFieldSet unknownFields;
        public static Parser<Initiate> PARSER;
        private int bitField0_;
        public static final int MULTISIG_KEY_FIELD_NUMBER = 1;
        private ByteString multisigKey_;
        public static final int MIN_ACCEPTED_CHANNEL_SIZE_FIELD_NUMBER = 2;
        private long minAcceptedChannelSize_;
        public static final int EXPIRE_TIME_SECS_FIELD_NUMBER = 3;
        private long expireTimeSecs_;
        public static final int MIN_PAYMENT_FIELD_NUMBER = 4;
        private long minPayment_;
        private byte memoizedIsInitialized;
        private int memoizedSerializedSize;
        private static final long serialVersionUID = 0L;

        private Initiate(GeneratedMessage.Builder<?> builder) {
            super(builder);
            this.memoizedIsInitialized = (byte)-1;
            this.memoizedSerializedSize = -1;
            this.unknownFields = builder.getUnknownFields();
        }

        private Initiate(boolean noInit) {
            this.memoizedIsInitialized = (byte)-1;
            this.memoizedSerializedSize = -1;
            this.unknownFields = UnknownFieldSet.getDefaultInstance();
        }

        public static Initiate getDefaultInstance() {
            return defaultInstance;
        }

        public Initiate getDefaultInstanceForType() {
            return defaultInstance;
        }

        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private Initiate(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            this.memoizedIsInitialized = (byte)-1;
            this.memoizedSerializedSize = -1;
            this.initFields();
            boolean mutable_bitField0_ = false;
            UnknownFieldSet.Builder unknownFields = UnknownFieldSet.newBuilder();
            try {
                boolean done = false;
                block13 : while (!done) {
                    int tag = input.readTag();
                    switch (tag) {
                        case 0: {
                            done = true;
                            continue block13;
                        }
                        default: {
                            if (this.parseUnknownField(input, unknownFields, extensionRegistry, tag)) continue block13;
                            done = true;
                            continue block13;
                        }
                        case 10: {
                            this.bitField0_ |= 1;
                            this.multisigKey_ = input.readBytes();
                            continue block13;
                        }
                        case 16: {
                            this.bitField0_ |= 2;
                            this.minAcceptedChannelSize_ = input.readUInt64();
                            continue block13;
                        }
                        case 24: {
                            this.bitField0_ |= 4;
                            this.expireTimeSecs_ = input.readUInt64();
                            continue block13;
                        }
                        case 32: 
                    }
                    this.bitField0_ |= 8;
                    this.minPayment_ = input.readUInt64();
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
            return internal_static_paymentchannels_Initiate_descriptor;
        }

        protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
            return internal_static_paymentchannels_Initiate_fieldAccessorTable.ensureFieldAccessorsInitialized(Initiate.class, Builder.class);
        }

        public Parser<Initiate> getParserForType() {
            return PARSER;
        }

        @Override
        public boolean hasMultisigKey() {
            return (this.bitField0_ & 1) == 1;
        }

        @Override
        public ByteString getMultisigKey() {
            return this.multisigKey_;
        }

        @Override
        public boolean hasMinAcceptedChannelSize() {
            return (this.bitField0_ & 2) == 2;
        }

        @Override
        public long getMinAcceptedChannelSize() {
            return this.minAcceptedChannelSize_;
        }

        @Override
        public boolean hasExpireTimeSecs() {
            return (this.bitField0_ & 4) == 4;
        }

        @Override
        public long getExpireTimeSecs() {
            return this.expireTimeSecs_;
        }

        @Override
        public boolean hasMinPayment() {
            return (this.bitField0_ & 8) == 8;
        }

        @Override
        public long getMinPayment() {
            return this.minPayment_;
        }

        private void initFields() {
            this.multisigKey_ = ByteString.EMPTY;
            this.minAcceptedChannelSize_ = 0L;
            this.expireTimeSecs_ = 0L;
            this.minPayment_ = 0L;
        }

        public final boolean isInitialized() {
            byte isInitialized = this.memoizedIsInitialized;
            if (isInitialized == 1) {
                return true;
            }
            if (isInitialized == 0) {
                return false;
            }
            if (!this.hasMultisigKey()) {
                this.memoizedIsInitialized = 0;
                return false;
            }
            if (!this.hasMinAcceptedChannelSize()) {
                this.memoizedIsInitialized = 0;
                return false;
            }
            if (!this.hasExpireTimeSecs()) {
                this.memoizedIsInitialized = 0;
                return false;
            }
            if (!this.hasMinPayment()) {
                this.memoizedIsInitialized = 0;
                return false;
            }
            this.memoizedIsInitialized = 1;
            return true;
        }

        public void writeTo(CodedOutputStream output) throws IOException {
            this.getSerializedSize();
            if ((this.bitField0_ & 1) == 1) {
                output.writeBytes(1, this.multisigKey_);
            }
            if ((this.bitField0_ & 2) == 2) {
                output.writeUInt64(2, this.minAcceptedChannelSize_);
            }
            if ((this.bitField0_ & 4) == 4) {
                output.writeUInt64(3, this.expireTimeSecs_);
            }
            if ((this.bitField0_ & 8) == 8) {
                output.writeUInt64(4, this.minPayment_);
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
                size += CodedOutputStream.computeBytesSize((int)1, (ByteString)this.multisigKey_);
            }
            if ((this.bitField0_ & 2) == 2) {
                size += CodedOutputStream.computeUInt64Size((int)2, (long)this.minAcceptedChannelSize_);
            }
            if ((this.bitField0_ & 4) == 4) {
                size += CodedOutputStream.computeUInt64Size((int)3, (long)this.expireTimeSecs_);
            }
            if ((this.bitField0_ & 8) == 8) {
                size += CodedOutputStream.computeUInt64Size((int)4, (long)this.minPayment_);
            }
            this.memoizedSerializedSize = size += this.getUnknownFields().getSerializedSize();
            return size;
        }

        protected Object writeReplace() throws ObjectStreamException {
            return super.writeReplace();
        }

        public static Initiate parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (Initiate)PARSER.parseFrom(data);
        }

        public static Initiate parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (Initiate)PARSER.parseFrom(data, extensionRegistry);
        }

        public static Initiate parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (Initiate)PARSER.parseFrom(data);
        }

        public static Initiate parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (Initiate)PARSER.parseFrom(data, extensionRegistry);
        }

        public static Initiate parseFrom(InputStream input) throws IOException {
            return (Initiate)PARSER.parseFrom(input);
        }

        public static Initiate parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (Initiate)PARSER.parseFrom(input, extensionRegistry);
        }

        public static Initiate parseDelimitedFrom(InputStream input) throws IOException {
            return (Initiate)PARSER.parseDelimitedFrom(input);
        }

        public static Initiate parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (Initiate)PARSER.parseDelimitedFrom(input, extensionRegistry);
        }

        public static Initiate parseFrom(CodedInputStream input) throws IOException {
            return (Initiate)PARSER.parseFrom(input);
        }

        public static Initiate parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (Initiate)PARSER.parseFrom(input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return Builder.create();
        }

        public Builder newBuilderForType() {
            return Initiate.newBuilder();
        }

        public static Builder newBuilder(Initiate prototype) {
            return Initiate.newBuilder().mergeFrom(prototype);
        }

        public Builder toBuilder() {
            return Initiate.newBuilder(this);
        }

        protected Builder newBuilderForType(GeneratedMessage.BuilderParent parent) {
            Builder builder = new Builder(parent);
            return builder;
        }

        static {
            PARSER = new AbstractParser<Initiate>(){

                public Initiate parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                    return new Initiate(input, extensionRegistry);
                }
            };
            defaultInstance = new Initiate(true);
            defaultInstance.initFields();
        }

        public static final class Builder
        extends GeneratedMessage.Builder<Builder>
        implements InitiateOrBuilder {
            private int bitField0_;
            private ByteString multisigKey_ = ByteString.EMPTY;
            private long minAcceptedChannelSize_;
            private long expireTimeSecs_;
            private long minPayment_;

            public static final Descriptors.Descriptor getDescriptor() {
                return internal_static_paymentchannels_Initiate_descriptor;
            }

            protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
                return internal_static_paymentchannels_Initiate_fieldAccessorTable.ensureFieldAccessorsInitialized(Initiate.class, Builder.class);
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
                this.multisigKey_ = ByteString.EMPTY;
                this.bitField0_ &= -2;
                this.minAcceptedChannelSize_ = 0L;
                this.bitField0_ &= -3;
                this.expireTimeSecs_ = 0L;
                this.bitField0_ &= -5;
                this.minPayment_ = 0L;
                this.bitField0_ &= -9;
                return this;
            }

            public Builder clone() {
                return Builder.create().mergeFrom(this.buildPartial());
            }

            public Descriptors.Descriptor getDescriptorForType() {
                return internal_static_paymentchannels_Initiate_descriptor;
            }

            public Initiate getDefaultInstanceForType() {
                return Initiate.getDefaultInstance();
            }

            public Initiate build() {
                Initiate result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw Builder.newUninitializedMessageException((Message)result);
                }
                return result;
            }

            public Initiate buildPartial() {
                Initiate result = new Initiate(this);
                int from_bitField0_ = this.bitField0_;
                int to_bitField0_ = 0;
                if ((from_bitField0_ & 1) == 1) {
                    to_bitField0_ |= true;
                }
                result.multisigKey_ = this.multisigKey_;
                if ((from_bitField0_ & 2) == 2) {
                    to_bitField0_ |= 2;
                }
                result.minAcceptedChannelSize_ = this.minAcceptedChannelSize_;
                if ((from_bitField0_ & 4) == 4) {
                    to_bitField0_ |= 4;
                }
                result.expireTimeSecs_ = this.expireTimeSecs_;
                if ((from_bitField0_ & 8) == 8) {
                    to_bitField0_ |= 8;
                }
                result.minPayment_ = this.minPayment_;
                result.bitField0_ = to_bitField0_;
                this.onBuilt();
                return result;
            }

            public Builder mergeFrom(Message other) {
                if (other instanceof Initiate) {
                    return this.mergeFrom((Initiate)other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(Initiate other) {
                if (other == Initiate.getDefaultInstance()) {
                    return this;
                }
                if (other.hasMultisigKey()) {
                    this.setMultisigKey(other.getMultisigKey());
                }
                if (other.hasMinAcceptedChannelSize()) {
                    this.setMinAcceptedChannelSize(other.getMinAcceptedChannelSize());
                }
                if (other.hasExpireTimeSecs()) {
                    this.setExpireTimeSecs(other.getExpireTimeSecs());
                }
                if (other.hasMinPayment()) {
                    this.setMinPayment(other.getMinPayment());
                }
                this.mergeUnknownFields(other.getUnknownFields());
                return this;
            }

            public final boolean isInitialized() {
                if (!this.hasMultisigKey()) {
                    return false;
                }
                if (!this.hasMinAcceptedChannelSize()) {
                    return false;
                }
                if (!this.hasExpireTimeSecs()) {
                    return false;
                }
                if (!this.hasMinPayment()) {
                    return false;
                }
                return true;
            }

            public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                Initiate parsedMessage = null;
                try {
                    parsedMessage = (Initiate)Initiate.PARSER.parsePartialFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e) {
                    parsedMessage = (Initiate)e.getUnfinishedMessage();
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
            public boolean hasMultisigKey() {
                return (this.bitField0_ & 1) == 1;
            }

            @Override
            public ByteString getMultisigKey() {
                return this.multisigKey_;
            }

            public Builder setMultisigKey(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 1;
                this.multisigKey_ = value;
                this.onChanged();
                return this;
            }

            public Builder clearMultisigKey() {
                this.bitField0_ &= -2;
                this.multisigKey_ = Initiate.getDefaultInstance().getMultisigKey();
                this.onChanged();
                return this;
            }

            @Override
            public boolean hasMinAcceptedChannelSize() {
                return (this.bitField0_ & 2) == 2;
            }

            @Override
            public long getMinAcceptedChannelSize() {
                return this.minAcceptedChannelSize_;
            }

            public Builder setMinAcceptedChannelSize(long value) {
                this.bitField0_ |= 2;
                this.minAcceptedChannelSize_ = value;
                this.onChanged();
                return this;
            }

            public Builder clearMinAcceptedChannelSize() {
                this.bitField0_ &= -3;
                this.minAcceptedChannelSize_ = 0L;
                this.onChanged();
                return this;
            }

            @Override
            public boolean hasExpireTimeSecs() {
                return (this.bitField0_ & 4) == 4;
            }

            @Override
            public long getExpireTimeSecs() {
                return this.expireTimeSecs_;
            }

            public Builder setExpireTimeSecs(long value) {
                this.bitField0_ |= 4;
                this.expireTimeSecs_ = value;
                this.onChanged();
                return this;
            }

            public Builder clearExpireTimeSecs() {
                this.bitField0_ &= -5;
                this.expireTimeSecs_ = 0L;
                this.onChanged();
                return this;
            }

            @Override
            public boolean hasMinPayment() {
                return (this.bitField0_ & 8) == 8;
            }

            @Override
            public long getMinPayment() {
                return this.minPayment_;
            }

            public Builder setMinPayment(long value) {
                this.bitField0_ |= 8;
                this.minPayment_ = value;
                this.onChanged();
                return this;
            }

            public Builder clearMinPayment() {
                this.bitField0_ &= -9;
                this.minPayment_ = 0L;
                this.onChanged();
                return this;
            }
        }

    }

    public static interface InitiateOrBuilder
    extends MessageOrBuilder {
        public boolean hasMultisigKey();

        public ByteString getMultisigKey();

        public boolean hasMinAcceptedChannelSize();

        public long getMinAcceptedChannelSize();

        public boolean hasExpireTimeSecs();

        public long getExpireTimeSecs();

        public boolean hasMinPayment();

        public long getMinPayment();
    }

    public static final class ServerVersion
    extends GeneratedMessage
    implements ServerVersionOrBuilder {
        private static final ServerVersion defaultInstance;
        private final UnknownFieldSet unknownFields;
        public static Parser<ServerVersion> PARSER;
        private int bitField0_;
        public static final int MAJOR_FIELD_NUMBER = 1;
        private int major_;
        public static final int MINOR_FIELD_NUMBER = 2;
        private int minor_;
        private byte memoizedIsInitialized;
        private int memoizedSerializedSize;
        private static final long serialVersionUID = 0L;

        private ServerVersion(GeneratedMessage.Builder<?> builder) {
            super(builder);
            this.memoizedIsInitialized = (byte)-1;
            this.memoizedSerializedSize = -1;
            this.unknownFields = builder.getUnknownFields();
        }

        private ServerVersion(boolean noInit) {
            this.memoizedIsInitialized = (byte)-1;
            this.memoizedSerializedSize = -1;
            this.unknownFields = UnknownFieldSet.getDefaultInstance();
        }

        public static ServerVersion getDefaultInstance() {
            return defaultInstance;
        }

        public ServerVersion getDefaultInstanceForType() {
            return defaultInstance;
        }

        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private ServerVersion(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            this.memoizedIsInitialized = (byte)-1;
            this.memoizedSerializedSize = -1;
            this.initFields();
            boolean mutable_bitField0_ = false;
            UnknownFieldSet.Builder unknownFields = UnknownFieldSet.newBuilder();
            try {
                boolean done = false;
                block11 : while (!done) {
                    int tag = input.readTag();
                    switch (tag) {
                        case 0: {
                            done = true;
                            continue block11;
                        }
                        default: {
                            if (this.parseUnknownField(input, unknownFields, extensionRegistry, tag)) continue block11;
                            done = true;
                            continue block11;
                        }
                        case 8: {
                            this.bitField0_ |= 1;
                            this.major_ = input.readInt32();
                            continue block11;
                        }
                        case 16: 
                    }
                    this.bitField0_ |= 2;
                    this.minor_ = input.readInt32();
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
            return internal_static_paymentchannels_ServerVersion_descriptor;
        }

        protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
            return internal_static_paymentchannels_ServerVersion_fieldAccessorTable.ensureFieldAccessorsInitialized(ServerVersion.class, Builder.class);
        }

        public Parser<ServerVersion> getParserForType() {
            return PARSER;
        }

        @Override
        public boolean hasMajor() {
            return (this.bitField0_ & 1) == 1;
        }

        @Override
        public int getMajor() {
            return this.major_;
        }

        @Override
        public boolean hasMinor() {
            return (this.bitField0_ & 2) == 2;
        }

        @Override
        public int getMinor() {
            return this.minor_;
        }

        private void initFields() {
            this.major_ = 0;
            this.minor_ = 0;
        }

        public final boolean isInitialized() {
            byte isInitialized = this.memoizedIsInitialized;
            if (isInitialized == 1) {
                return true;
            }
            if (isInitialized == 0) {
                return false;
            }
            if (!this.hasMajor()) {
                this.memoizedIsInitialized = 0;
                return false;
            }
            this.memoizedIsInitialized = 1;
            return true;
        }

        public void writeTo(CodedOutputStream output) throws IOException {
            this.getSerializedSize();
            if ((this.bitField0_ & 1) == 1) {
                output.writeInt32(1, this.major_);
            }
            if ((this.bitField0_ & 2) == 2) {
                output.writeInt32(2, this.minor_);
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
                size += CodedOutputStream.computeInt32Size((int)1, (int)this.major_);
            }
            if ((this.bitField0_ & 2) == 2) {
                size += CodedOutputStream.computeInt32Size((int)2, (int)this.minor_);
            }
            this.memoizedSerializedSize = size += this.getUnknownFields().getSerializedSize();
            return size;
        }

        protected Object writeReplace() throws ObjectStreamException {
            return super.writeReplace();
        }

        public static ServerVersion parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (ServerVersion)PARSER.parseFrom(data);
        }

        public static ServerVersion parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (ServerVersion)PARSER.parseFrom(data, extensionRegistry);
        }

        public static ServerVersion parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (ServerVersion)PARSER.parseFrom(data);
        }

        public static ServerVersion parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (ServerVersion)PARSER.parseFrom(data, extensionRegistry);
        }

        public static ServerVersion parseFrom(InputStream input) throws IOException {
            return (ServerVersion)PARSER.parseFrom(input);
        }

        public static ServerVersion parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (ServerVersion)PARSER.parseFrom(input, extensionRegistry);
        }

        public static ServerVersion parseDelimitedFrom(InputStream input) throws IOException {
            return (ServerVersion)PARSER.parseDelimitedFrom(input);
        }

        public static ServerVersion parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (ServerVersion)PARSER.parseDelimitedFrom(input, extensionRegistry);
        }

        public static ServerVersion parseFrom(CodedInputStream input) throws IOException {
            return (ServerVersion)PARSER.parseFrom(input);
        }

        public static ServerVersion parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (ServerVersion)PARSER.parseFrom(input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return Builder.create();
        }

        public Builder newBuilderForType() {
            return ServerVersion.newBuilder();
        }

        public static Builder newBuilder(ServerVersion prototype) {
            return ServerVersion.newBuilder().mergeFrom(prototype);
        }

        public Builder toBuilder() {
            return ServerVersion.newBuilder(this);
        }

        protected Builder newBuilderForType(GeneratedMessage.BuilderParent parent) {
            Builder builder = new Builder(parent);
            return builder;
        }

        static {
            PARSER = new AbstractParser<ServerVersion>(){

                public ServerVersion parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                    return new ServerVersion(input, extensionRegistry);
                }
            };
            defaultInstance = new ServerVersion(true);
            defaultInstance.initFields();
        }

        public static final class Builder
        extends GeneratedMessage.Builder<Builder>
        implements ServerVersionOrBuilder {
            private int bitField0_;
            private int major_;
            private int minor_;

            public static final Descriptors.Descriptor getDescriptor() {
                return internal_static_paymentchannels_ServerVersion_descriptor;
            }

            protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
                return internal_static_paymentchannels_ServerVersion_fieldAccessorTable.ensureFieldAccessorsInitialized(ServerVersion.class, Builder.class);
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
                this.major_ = 0;
                this.bitField0_ &= -2;
                this.minor_ = 0;
                this.bitField0_ &= -3;
                return this;
            }

            public Builder clone() {
                return Builder.create().mergeFrom(this.buildPartial());
            }

            public Descriptors.Descriptor getDescriptorForType() {
                return internal_static_paymentchannels_ServerVersion_descriptor;
            }

            public ServerVersion getDefaultInstanceForType() {
                return ServerVersion.getDefaultInstance();
            }

            public ServerVersion build() {
                ServerVersion result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw Builder.newUninitializedMessageException((Message)result);
                }
                return result;
            }

            public ServerVersion buildPartial() {
                ServerVersion result = new ServerVersion(this);
                int from_bitField0_ = this.bitField0_;
                int to_bitField0_ = 0;
                if ((from_bitField0_ & 1) == 1) {
                    to_bitField0_ |= true;
                }
                result.major_ = this.major_;
                if ((from_bitField0_ & 2) == 2) {
                    to_bitField0_ |= 2;
                }
                result.minor_ = this.minor_;
                result.bitField0_ = to_bitField0_;
                this.onBuilt();
                return result;
            }

            public Builder mergeFrom(Message other) {
                if (other instanceof ServerVersion) {
                    return this.mergeFrom((ServerVersion)other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(ServerVersion other) {
                if (other == ServerVersion.getDefaultInstance()) {
                    return this;
                }
                if (other.hasMajor()) {
                    this.setMajor(other.getMajor());
                }
                if (other.hasMinor()) {
                    this.setMinor(other.getMinor());
                }
                this.mergeUnknownFields(other.getUnknownFields());
                return this;
            }

            public final boolean isInitialized() {
                if (!this.hasMajor()) {
                    return false;
                }
                return true;
            }

            public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                ServerVersion parsedMessage = null;
                try {
                    parsedMessage = (ServerVersion)ServerVersion.PARSER.parsePartialFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e) {
                    parsedMessage = (ServerVersion)e.getUnfinishedMessage();
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
            public boolean hasMajor() {
                return (this.bitField0_ & 1) == 1;
            }

            @Override
            public int getMajor() {
                return this.major_;
            }

            public Builder setMajor(int value) {
                this.bitField0_ |= 1;
                this.major_ = value;
                this.onChanged();
                return this;
            }

            public Builder clearMajor() {
                this.bitField0_ &= -2;
                this.major_ = 0;
                this.onChanged();
                return this;
            }

            @Override
            public boolean hasMinor() {
                return (this.bitField0_ & 2) == 2;
            }

            @Override
            public int getMinor() {
                return this.minor_;
            }

            public Builder setMinor(int value) {
                this.bitField0_ |= 2;
                this.minor_ = value;
                this.onChanged();
                return this;
            }

            public Builder clearMinor() {
                this.bitField0_ &= -3;
                this.minor_ = 0;
                this.onChanged();
                return this;
            }
        }

    }

    public static interface ServerVersionOrBuilder
    extends MessageOrBuilder {
        public boolean hasMajor();

        public int getMajor();

        public boolean hasMinor();

        public int getMinor();
    }

    public static final class ClientVersion
    extends GeneratedMessage
    implements ClientVersionOrBuilder {
        private static final ClientVersion defaultInstance;
        private final UnknownFieldSet unknownFields;
        public static Parser<ClientVersion> PARSER;
        private int bitField0_;
        public static final int MAJOR_FIELD_NUMBER = 1;
        private int major_;
        public static final int MINOR_FIELD_NUMBER = 2;
        private int minor_;
        public static final int PREVIOUS_CHANNEL_CONTRACT_HASH_FIELD_NUMBER = 3;
        private ByteString previousChannelContractHash_;
        public static final int TIME_WINDOW_SECS_FIELD_NUMBER = 4;
        private long timeWindowSecs_;
        private byte memoizedIsInitialized;
        private int memoizedSerializedSize;
        private static final long serialVersionUID = 0L;

        private ClientVersion(GeneratedMessage.Builder<?> builder) {
            super(builder);
            this.memoizedIsInitialized = (byte)-1;
            this.memoizedSerializedSize = -1;
            this.unknownFields = builder.getUnknownFields();
        }

        private ClientVersion(boolean noInit) {
            this.memoizedIsInitialized = (byte)-1;
            this.memoizedSerializedSize = -1;
            this.unknownFields = UnknownFieldSet.getDefaultInstance();
        }

        public static ClientVersion getDefaultInstance() {
            return defaultInstance;
        }

        public ClientVersion getDefaultInstanceForType() {
            return defaultInstance;
        }

        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private ClientVersion(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            this.memoizedIsInitialized = (byte)-1;
            this.memoizedSerializedSize = -1;
            this.initFields();
            boolean mutable_bitField0_ = false;
            UnknownFieldSet.Builder unknownFields = UnknownFieldSet.newBuilder();
            try {
                boolean done = false;
                block13 : while (!done) {
                    int tag = input.readTag();
                    switch (tag) {
                        case 0: {
                            done = true;
                            continue block13;
                        }
                        default: {
                            if (this.parseUnknownField(input, unknownFields, extensionRegistry, tag)) continue block13;
                            done = true;
                            continue block13;
                        }
                        case 8: {
                            this.bitField0_ |= 1;
                            this.major_ = input.readInt32();
                            continue block13;
                        }
                        case 16: {
                            this.bitField0_ |= 2;
                            this.minor_ = input.readInt32();
                            continue block13;
                        }
                        case 26: {
                            this.bitField0_ |= 4;
                            this.previousChannelContractHash_ = input.readBytes();
                            continue block13;
                        }
                        case 32: 
                    }
                    this.bitField0_ |= 8;
                    this.timeWindowSecs_ = input.readUInt64();
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
            return internal_static_paymentchannels_ClientVersion_descriptor;
        }

        protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
            return internal_static_paymentchannels_ClientVersion_fieldAccessorTable.ensureFieldAccessorsInitialized(ClientVersion.class, Builder.class);
        }

        public Parser<ClientVersion> getParserForType() {
            return PARSER;
        }

        @Override
        public boolean hasMajor() {
            return (this.bitField0_ & 1) == 1;
        }

        @Override
        public int getMajor() {
            return this.major_;
        }

        @Override
        public boolean hasMinor() {
            return (this.bitField0_ & 2) == 2;
        }

        @Override
        public int getMinor() {
            return this.minor_;
        }

        @Override
        public boolean hasPreviousChannelContractHash() {
            return (this.bitField0_ & 4) == 4;
        }

        @Override
        public ByteString getPreviousChannelContractHash() {
            return this.previousChannelContractHash_;
        }

        @Override
        public boolean hasTimeWindowSecs() {
            return (this.bitField0_ & 8) == 8;
        }

        @Override
        public long getTimeWindowSecs() {
            return this.timeWindowSecs_;
        }

        private void initFields() {
            this.major_ = 0;
            this.minor_ = 0;
            this.previousChannelContractHash_ = ByteString.EMPTY;
            this.timeWindowSecs_ = 86340L;
        }

        public final boolean isInitialized() {
            byte isInitialized = this.memoizedIsInitialized;
            if (isInitialized == 1) {
                return true;
            }
            if (isInitialized == 0) {
                return false;
            }
            if (!this.hasMajor()) {
                this.memoizedIsInitialized = 0;
                return false;
            }
            this.memoizedIsInitialized = 1;
            return true;
        }

        public void writeTo(CodedOutputStream output) throws IOException {
            this.getSerializedSize();
            if ((this.bitField0_ & 1) == 1) {
                output.writeInt32(1, this.major_);
            }
            if ((this.bitField0_ & 2) == 2) {
                output.writeInt32(2, this.minor_);
            }
            if ((this.bitField0_ & 4) == 4) {
                output.writeBytes(3, this.previousChannelContractHash_);
            }
            if ((this.bitField0_ & 8) == 8) {
                output.writeUInt64(4, this.timeWindowSecs_);
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
                size += CodedOutputStream.computeInt32Size((int)1, (int)this.major_);
            }
            if ((this.bitField0_ & 2) == 2) {
                size += CodedOutputStream.computeInt32Size((int)2, (int)this.minor_);
            }
            if ((this.bitField0_ & 4) == 4) {
                size += CodedOutputStream.computeBytesSize((int)3, (ByteString)this.previousChannelContractHash_);
            }
            if ((this.bitField0_ & 8) == 8) {
                size += CodedOutputStream.computeUInt64Size((int)4, (long)this.timeWindowSecs_);
            }
            this.memoizedSerializedSize = size += this.getUnknownFields().getSerializedSize();
            return size;
        }

        protected Object writeReplace() throws ObjectStreamException {
            return super.writeReplace();
        }

        public static ClientVersion parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (ClientVersion)PARSER.parseFrom(data);
        }

        public static ClientVersion parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (ClientVersion)PARSER.parseFrom(data, extensionRegistry);
        }

        public static ClientVersion parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (ClientVersion)PARSER.parseFrom(data);
        }

        public static ClientVersion parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (ClientVersion)PARSER.parseFrom(data, extensionRegistry);
        }

        public static ClientVersion parseFrom(InputStream input) throws IOException {
            return (ClientVersion)PARSER.parseFrom(input);
        }

        public static ClientVersion parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (ClientVersion)PARSER.parseFrom(input, extensionRegistry);
        }

        public static ClientVersion parseDelimitedFrom(InputStream input) throws IOException {
            return (ClientVersion)PARSER.parseDelimitedFrom(input);
        }

        public static ClientVersion parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (ClientVersion)PARSER.parseDelimitedFrom(input, extensionRegistry);
        }

        public static ClientVersion parseFrom(CodedInputStream input) throws IOException {
            return (ClientVersion)PARSER.parseFrom(input);
        }

        public static ClientVersion parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (ClientVersion)PARSER.parseFrom(input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return Builder.create();
        }

        public Builder newBuilderForType() {
            return ClientVersion.newBuilder();
        }

        public static Builder newBuilder(ClientVersion prototype) {
            return ClientVersion.newBuilder().mergeFrom(prototype);
        }

        public Builder toBuilder() {
            return ClientVersion.newBuilder(this);
        }

        protected Builder newBuilderForType(GeneratedMessage.BuilderParent parent) {
            Builder builder = new Builder(parent);
            return builder;
        }

        static {
            PARSER = new AbstractParser<ClientVersion>(){

                public ClientVersion parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                    return new ClientVersion(input, extensionRegistry);
                }
            };
            defaultInstance = new ClientVersion(true);
            defaultInstance.initFields();
        }

        public static final class Builder
        extends GeneratedMessage.Builder<Builder>
        implements ClientVersionOrBuilder {
            private int bitField0_;
            private int major_;
            private int minor_;
            private ByteString previousChannelContractHash_ = ByteString.EMPTY;
            private long timeWindowSecs_ = 86340L;

            public static final Descriptors.Descriptor getDescriptor() {
                return internal_static_paymentchannels_ClientVersion_descriptor;
            }

            protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
                return internal_static_paymentchannels_ClientVersion_fieldAccessorTable.ensureFieldAccessorsInitialized(ClientVersion.class, Builder.class);
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
                this.major_ = 0;
                this.bitField0_ &= -2;
                this.minor_ = 0;
                this.bitField0_ &= -3;
                this.previousChannelContractHash_ = ByteString.EMPTY;
                this.bitField0_ &= -5;
                this.timeWindowSecs_ = 86340L;
                this.bitField0_ &= -9;
                return this;
            }

            public Builder clone() {
                return Builder.create().mergeFrom(this.buildPartial());
            }

            public Descriptors.Descriptor getDescriptorForType() {
                return internal_static_paymentchannels_ClientVersion_descriptor;
            }

            public ClientVersion getDefaultInstanceForType() {
                return ClientVersion.getDefaultInstance();
            }

            public ClientVersion build() {
                ClientVersion result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw Builder.newUninitializedMessageException((Message)result);
                }
                return result;
            }

            public ClientVersion buildPartial() {
                ClientVersion result = new ClientVersion(this);
                int from_bitField0_ = this.bitField0_;
                int to_bitField0_ = 0;
                if ((from_bitField0_ & 1) == 1) {
                    to_bitField0_ |= true;
                }
                result.major_ = this.major_;
                if ((from_bitField0_ & 2) == 2) {
                    to_bitField0_ |= 2;
                }
                result.minor_ = this.minor_;
                if ((from_bitField0_ & 4) == 4) {
                    to_bitField0_ |= 4;
                }
                result.previousChannelContractHash_ = this.previousChannelContractHash_;
                if ((from_bitField0_ & 8) == 8) {
                    to_bitField0_ |= 8;
                }
                result.timeWindowSecs_ = this.timeWindowSecs_;
                result.bitField0_ = to_bitField0_;
                this.onBuilt();
                return result;
            }

            public Builder mergeFrom(Message other) {
                if (other instanceof ClientVersion) {
                    return this.mergeFrom((ClientVersion)other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(ClientVersion other) {
                if (other == ClientVersion.getDefaultInstance()) {
                    return this;
                }
                if (other.hasMajor()) {
                    this.setMajor(other.getMajor());
                }
                if (other.hasMinor()) {
                    this.setMinor(other.getMinor());
                }
                if (other.hasPreviousChannelContractHash()) {
                    this.setPreviousChannelContractHash(other.getPreviousChannelContractHash());
                }
                if (other.hasTimeWindowSecs()) {
                    this.setTimeWindowSecs(other.getTimeWindowSecs());
                }
                this.mergeUnknownFields(other.getUnknownFields());
                return this;
            }

            public final boolean isInitialized() {
                if (!this.hasMajor()) {
                    return false;
                }
                return true;
            }

            public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                ClientVersion parsedMessage = null;
                try {
                    parsedMessage = (ClientVersion)ClientVersion.PARSER.parsePartialFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e) {
                    parsedMessage = (ClientVersion)e.getUnfinishedMessage();
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
            public boolean hasMajor() {
                return (this.bitField0_ & 1) == 1;
            }

            @Override
            public int getMajor() {
                return this.major_;
            }

            public Builder setMajor(int value) {
                this.bitField0_ |= 1;
                this.major_ = value;
                this.onChanged();
                return this;
            }

            public Builder clearMajor() {
                this.bitField0_ &= -2;
                this.major_ = 0;
                this.onChanged();
                return this;
            }

            @Override
            public boolean hasMinor() {
                return (this.bitField0_ & 2) == 2;
            }

            @Override
            public int getMinor() {
                return this.minor_;
            }

            public Builder setMinor(int value) {
                this.bitField0_ |= 2;
                this.minor_ = value;
                this.onChanged();
                return this;
            }

            public Builder clearMinor() {
                this.bitField0_ &= -3;
                this.minor_ = 0;
                this.onChanged();
                return this;
            }

            @Override
            public boolean hasPreviousChannelContractHash() {
                return (this.bitField0_ & 4) == 4;
            }

            @Override
            public ByteString getPreviousChannelContractHash() {
                return this.previousChannelContractHash_;
            }

            public Builder setPreviousChannelContractHash(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 4;
                this.previousChannelContractHash_ = value;
                this.onChanged();
                return this;
            }

            public Builder clearPreviousChannelContractHash() {
                this.bitField0_ &= -5;
                this.previousChannelContractHash_ = ClientVersion.getDefaultInstance().getPreviousChannelContractHash();
                this.onChanged();
                return this;
            }

            @Override
            public boolean hasTimeWindowSecs() {
                return (this.bitField0_ & 8) == 8;
            }

            @Override
            public long getTimeWindowSecs() {
                return this.timeWindowSecs_;
            }

            public Builder setTimeWindowSecs(long value) {
                this.bitField0_ |= 8;
                this.timeWindowSecs_ = value;
                this.onChanged();
                return this;
            }

            public Builder clearTimeWindowSecs() {
                this.bitField0_ &= -9;
                this.timeWindowSecs_ = 86340L;
                this.onChanged();
                return this;
            }
        }

    }

    public static interface ClientVersionOrBuilder
    extends MessageOrBuilder {
        public boolean hasMajor();

        public int getMajor();

        public boolean hasMinor();

        public int getMinor();

        public boolean hasPreviousChannelContractHash();

        public ByteString getPreviousChannelContractHash();

        public boolean hasTimeWindowSecs();

        public long getTimeWindowSecs();
    }

    public static final class TwoWayChannelMessage
    extends GeneratedMessage
    implements TwoWayChannelMessageOrBuilder {
        private static final TwoWayChannelMessage defaultInstance;
        private final UnknownFieldSet unknownFields;
        public static Parser<TwoWayChannelMessage> PARSER;
        private int bitField0_;
        public static final int TYPE_FIELD_NUMBER = 1;
        private MessageType type_;
        public static final int CLIENT_VERSION_FIELD_NUMBER = 2;
        private ClientVersion clientVersion_;
        public static final int SERVER_VERSION_FIELD_NUMBER = 3;
        private ServerVersion serverVersion_;
        public static final int INITIATE_FIELD_NUMBER = 4;
        private Initiate initiate_;
        public static final int PROVIDE_REFUND_FIELD_NUMBER = 5;
        private ProvideRefund provideRefund_;
        public static final int RETURN_REFUND_FIELD_NUMBER = 6;
        private ReturnRefund returnRefund_;
        public static final int PROVIDE_CONTRACT_FIELD_NUMBER = 7;
        private ProvideContract provideContract_;
        public static final int UPDATE_PAYMENT_FIELD_NUMBER = 8;
        private UpdatePayment updatePayment_;
        public static final int PAYMENT_ACK_FIELD_NUMBER = 11;
        private PaymentAck paymentAck_;
        public static final int SETTLEMENT_FIELD_NUMBER = 9;
        private Settlement settlement_;
        public static final int ERROR_FIELD_NUMBER = 10;
        private Error error_;
        private byte memoizedIsInitialized;
        private int memoizedSerializedSize;
        private static final long serialVersionUID = 0L;

        private TwoWayChannelMessage(GeneratedMessage.Builder<?> builder) {
            super(builder);
            this.memoizedIsInitialized = (byte)-1;
            this.memoizedSerializedSize = -1;
            this.unknownFields = builder.getUnknownFields();
        }

        private TwoWayChannelMessage(boolean noInit) {
            this.memoizedIsInitialized = (byte)-1;
            this.memoizedSerializedSize = -1;
            this.unknownFields = UnknownFieldSet.getDefaultInstance();
        }

        public static TwoWayChannelMessage getDefaultInstance() {
            return defaultInstance;
        }

        public TwoWayChannelMessage getDefaultInstanceForType() {
            return defaultInstance;
        }

        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private TwoWayChannelMessage(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            this.memoizedIsInitialized = (byte)-1;
            this.memoizedSerializedSize = -1;
            this.initFields();
            boolean mutable_bitField0_ = false;
            UnknownFieldSet.Builder unknownFields = UnknownFieldSet.newBuilder();
            try {
                boolean done = false;
                block20 : while (!done) {
                    int tag = input.readTag();
                    switch (tag) {
                        case 0: {
                            done = true;
                            continue block20;
                        }
                        default: {
                            if (this.parseUnknownField(input, unknownFields, extensionRegistry, tag)) continue block20;
                            done = true;
                            continue block20;
                        }
                        case 8: {
                            int rawValue = input.readEnum();
                            MessageType value = MessageType.valueOf(rawValue);
                            if (value == null) {
                                unknownFields.mergeVarintField(1, rawValue);
                                continue block20;
                            }
                            this.bitField0_ |= 1;
                            this.type_ = value;
                            continue block20;
                        }
                        case 18: {
                            ClientVersion.Builder subBuilder = null;
                            if ((this.bitField0_ & 2) == 2) {
                                subBuilder = this.clientVersion_.toBuilder();
                            }
                            this.clientVersion_ = (ClientVersion)input.readMessage(ClientVersion.PARSER, extensionRegistry);
                            if (subBuilder != null) {
                                subBuilder.mergeFrom(this.clientVersion_);
                                this.clientVersion_ = subBuilder.buildPartial();
                            }
                            this.bitField0_ |= 2;
                            continue block20;
                        }
                        case 26: {
                            ServerVersion.Builder subBuilder = null;
                            if ((this.bitField0_ & 4) == 4) {
                                subBuilder = this.serverVersion_.toBuilder();
                            }
                            this.serverVersion_ = (ServerVersion)input.readMessage(ServerVersion.PARSER, extensionRegistry);
                            if (subBuilder != null) {
                                subBuilder.mergeFrom(this.serverVersion_);
                                this.serverVersion_ = subBuilder.buildPartial();
                            }
                            this.bitField0_ |= 4;
                            continue block20;
                        }
                        case 34: {
                            Initiate.Builder subBuilder = null;
                            if ((this.bitField0_ & 8) == 8) {
                                subBuilder = this.initiate_.toBuilder();
                            }
                            this.initiate_ = (Initiate)input.readMessage(Initiate.PARSER, extensionRegistry);
                            if (subBuilder != null) {
                                subBuilder.mergeFrom(this.initiate_);
                                this.initiate_ = subBuilder.buildPartial();
                            }
                            this.bitField0_ |= 8;
                            continue block20;
                        }
                        case 42: {
                            ProvideRefund.Builder subBuilder = null;
                            if ((this.bitField0_ & 16) == 16) {
                                subBuilder = this.provideRefund_.toBuilder();
                            }
                            this.provideRefund_ = (ProvideRefund)input.readMessage(ProvideRefund.PARSER, extensionRegistry);
                            if (subBuilder != null) {
                                subBuilder.mergeFrom(this.provideRefund_);
                                this.provideRefund_ = subBuilder.buildPartial();
                            }
                            this.bitField0_ |= 16;
                            continue block20;
                        }
                        case 50: {
                            ReturnRefund.Builder subBuilder = null;
                            if ((this.bitField0_ & 32) == 32) {
                                subBuilder = this.returnRefund_.toBuilder();
                            }
                            this.returnRefund_ = (ReturnRefund)input.readMessage(ReturnRefund.PARSER, extensionRegistry);
                            if (subBuilder != null) {
                                subBuilder.mergeFrom(this.returnRefund_);
                                this.returnRefund_ = subBuilder.buildPartial();
                            }
                            this.bitField0_ |= 32;
                            continue block20;
                        }
                        case 58: {
                            ProvideContract.Builder subBuilder = null;
                            if ((this.bitField0_ & 64) == 64) {
                                subBuilder = this.provideContract_.toBuilder();
                            }
                            this.provideContract_ = (ProvideContract)input.readMessage(ProvideContract.PARSER, extensionRegistry);
                            if (subBuilder != null) {
                                subBuilder.mergeFrom(this.provideContract_);
                                this.provideContract_ = subBuilder.buildPartial();
                            }
                            this.bitField0_ |= 64;
                            continue block20;
                        }
                        case 66: {
                            UpdatePayment.Builder subBuilder = null;
                            if ((this.bitField0_ & 128) == 128) {
                                subBuilder = this.updatePayment_.toBuilder();
                            }
                            this.updatePayment_ = (UpdatePayment)input.readMessage(UpdatePayment.PARSER, extensionRegistry);
                            if (subBuilder != null) {
                                subBuilder.mergeFrom(this.updatePayment_);
                                this.updatePayment_ = subBuilder.buildPartial();
                            }
                            this.bitField0_ |= 128;
                            continue block20;
                        }
                        case 74: {
                            Settlement.Builder subBuilder = null;
                            if ((this.bitField0_ & 512) == 512) {
                                subBuilder = this.settlement_.toBuilder();
                            }
                            this.settlement_ = (Settlement)input.readMessage(Settlement.PARSER, extensionRegistry);
                            if (subBuilder != null) {
                                subBuilder.mergeFrom(this.settlement_);
                                this.settlement_ = subBuilder.buildPartial();
                            }
                            this.bitField0_ |= 512;
                            continue block20;
                        }
                        case 82: {
                            Error.Builder subBuilder = null;
                            if ((this.bitField0_ & 1024) == 1024) {
                                subBuilder = this.error_.toBuilder();
                            }
                            this.error_ = (Error)input.readMessage(Error.PARSER, extensionRegistry);
                            if (subBuilder != null) {
                                subBuilder.mergeFrom(this.error_);
                                this.error_ = subBuilder.buildPartial();
                            }
                            this.bitField0_ |= 1024;
                            continue block20;
                        }
                        case 90: 
                    }
                    PaymentAck.Builder subBuilder = null;
                    if ((this.bitField0_ & 256) == 256) {
                        subBuilder = this.paymentAck_.toBuilder();
                    }
                    this.paymentAck_ = (PaymentAck)input.readMessage(PaymentAck.PARSER, extensionRegistry);
                    if (subBuilder != null) {
                        subBuilder.mergeFrom(this.paymentAck_);
                        this.paymentAck_ = subBuilder.buildPartial();
                    }
                    this.bitField0_ |= 256;
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
            return internal_static_paymentchannels_TwoWayChannelMessage_descriptor;
        }

        protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
            return internal_static_paymentchannels_TwoWayChannelMessage_fieldAccessorTable.ensureFieldAccessorsInitialized(TwoWayChannelMessage.class, Builder.class);
        }

        public Parser<TwoWayChannelMessage> getParserForType() {
            return PARSER;
        }

        @Override
        public boolean hasType() {
            return (this.bitField0_ & 1) == 1;
        }

        @Override
        public MessageType getType() {
            return this.type_;
        }

        @Override
        public boolean hasClientVersion() {
            return (this.bitField0_ & 2) == 2;
        }

        @Override
        public ClientVersion getClientVersion() {
            return this.clientVersion_;
        }

        @Override
        public ClientVersionOrBuilder getClientVersionOrBuilder() {
            return this.clientVersion_;
        }

        @Override
        public boolean hasServerVersion() {
            return (this.bitField0_ & 4) == 4;
        }

        @Override
        public ServerVersion getServerVersion() {
            return this.serverVersion_;
        }

        @Override
        public ServerVersionOrBuilder getServerVersionOrBuilder() {
            return this.serverVersion_;
        }

        @Override
        public boolean hasInitiate() {
            return (this.bitField0_ & 8) == 8;
        }

        @Override
        public Initiate getInitiate() {
            return this.initiate_;
        }

        @Override
        public InitiateOrBuilder getInitiateOrBuilder() {
            return this.initiate_;
        }

        @Override
        public boolean hasProvideRefund() {
            return (this.bitField0_ & 16) == 16;
        }

        @Override
        public ProvideRefund getProvideRefund() {
            return this.provideRefund_;
        }

        @Override
        public ProvideRefundOrBuilder getProvideRefundOrBuilder() {
            return this.provideRefund_;
        }

        @Override
        public boolean hasReturnRefund() {
            return (this.bitField0_ & 32) == 32;
        }

        @Override
        public ReturnRefund getReturnRefund() {
            return this.returnRefund_;
        }

        @Override
        public ReturnRefundOrBuilder getReturnRefundOrBuilder() {
            return this.returnRefund_;
        }

        @Override
        public boolean hasProvideContract() {
            return (this.bitField0_ & 64) == 64;
        }

        @Override
        public ProvideContract getProvideContract() {
            return this.provideContract_;
        }

        @Override
        public ProvideContractOrBuilder getProvideContractOrBuilder() {
            return this.provideContract_;
        }

        @Override
        public boolean hasUpdatePayment() {
            return (this.bitField0_ & 128) == 128;
        }

        @Override
        public UpdatePayment getUpdatePayment() {
            return this.updatePayment_;
        }

        @Override
        public UpdatePaymentOrBuilder getUpdatePaymentOrBuilder() {
            return this.updatePayment_;
        }

        @Override
        public boolean hasPaymentAck() {
            return (this.bitField0_ & 256) == 256;
        }

        @Override
        public PaymentAck getPaymentAck() {
            return this.paymentAck_;
        }

        @Override
        public PaymentAckOrBuilder getPaymentAckOrBuilder() {
            return this.paymentAck_;
        }

        @Override
        public boolean hasSettlement() {
            return (this.bitField0_ & 512) == 512;
        }

        @Override
        public Settlement getSettlement() {
            return this.settlement_;
        }

        @Override
        public SettlementOrBuilder getSettlementOrBuilder() {
            return this.settlement_;
        }

        @Override
        public boolean hasError() {
            return (this.bitField0_ & 1024) == 1024;
        }

        @Override
        public Error getError() {
            return this.error_;
        }

        @Override
        public ErrorOrBuilder getErrorOrBuilder() {
            return this.error_;
        }

        private void initFields() {
            this.type_ = MessageType.CLIENT_VERSION;
            this.clientVersion_ = ClientVersion.getDefaultInstance();
            this.serverVersion_ = ServerVersion.getDefaultInstance();
            this.initiate_ = Initiate.getDefaultInstance();
            this.provideRefund_ = ProvideRefund.getDefaultInstance();
            this.returnRefund_ = ReturnRefund.getDefaultInstance();
            this.provideContract_ = ProvideContract.getDefaultInstance();
            this.updatePayment_ = UpdatePayment.getDefaultInstance();
            this.paymentAck_ = PaymentAck.getDefaultInstance();
            this.settlement_ = Settlement.getDefaultInstance();
            this.error_ = Error.getDefaultInstance();
        }

        public final boolean isInitialized() {
            byte isInitialized = this.memoizedIsInitialized;
            if (isInitialized == 1) {
                return true;
            }
            if (isInitialized == 0) {
                return false;
            }
            if (!this.hasType()) {
                this.memoizedIsInitialized = 0;
                return false;
            }
            if (this.hasClientVersion() && !this.getClientVersion().isInitialized()) {
                this.memoizedIsInitialized = 0;
                return false;
            }
            if (this.hasServerVersion() && !this.getServerVersion().isInitialized()) {
                this.memoizedIsInitialized = 0;
                return false;
            }
            if (this.hasInitiate() && !this.getInitiate().isInitialized()) {
                this.memoizedIsInitialized = 0;
                return false;
            }
            if (this.hasProvideRefund() && !this.getProvideRefund().isInitialized()) {
                this.memoizedIsInitialized = 0;
                return false;
            }
            if (this.hasReturnRefund() && !this.getReturnRefund().isInitialized()) {
                this.memoizedIsInitialized = 0;
                return false;
            }
            if (this.hasProvideContract() && !this.getProvideContract().isInitialized()) {
                this.memoizedIsInitialized = 0;
                return false;
            }
            if (this.hasUpdatePayment() && !this.getUpdatePayment().isInitialized()) {
                this.memoizedIsInitialized = 0;
                return false;
            }
            if (this.hasSettlement() && !this.getSettlement().isInitialized()) {
                this.memoizedIsInitialized = 0;
                return false;
            }
            this.memoizedIsInitialized = 1;
            return true;
        }

        public void writeTo(CodedOutputStream output) throws IOException {
            this.getSerializedSize();
            if ((this.bitField0_ & 1) == 1) {
                output.writeEnum(1, this.type_.getNumber());
            }
            if ((this.bitField0_ & 2) == 2) {
                output.writeMessage(2, (MessageLite)this.clientVersion_);
            }
            if ((this.bitField0_ & 4) == 4) {
                output.writeMessage(3, (MessageLite)this.serverVersion_);
            }
            if ((this.bitField0_ & 8) == 8) {
                output.writeMessage(4, (MessageLite)this.initiate_);
            }
            if ((this.bitField0_ & 16) == 16) {
                output.writeMessage(5, (MessageLite)this.provideRefund_);
            }
            if ((this.bitField0_ & 32) == 32) {
                output.writeMessage(6, (MessageLite)this.returnRefund_);
            }
            if ((this.bitField0_ & 64) == 64) {
                output.writeMessage(7, (MessageLite)this.provideContract_);
            }
            if ((this.bitField0_ & 128) == 128) {
                output.writeMessage(8, (MessageLite)this.updatePayment_);
            }
            if ((this.bitField0_ & 512) == 512) {
                output.writeMessage(9, (MessageLite)this.settlement_);
            }
            if ((this.bitField0_ & 1024) == 1024) {
                output.writeMessage(10, (MessageLite)this.error_);
            }
            if ((this.bitField0_ & 256) == 256) {
                output.writeMessage(11, (MessageLite)this.paymentAck_);
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
                size += CodedOutputStream.computeEnumSize((int)1, (int)this.type_.getNumber());
            }
            if ((this.bitField0_ & 2) == 2) {
                size += CodedOutputStream.computeMessageSize((int)2, (MessageLite)this.clientVersion_);
            }
            if ((this.bitField0_ & 4) == 4) {
                size += CodedOutputStream.computeMessageSize((int)3, (MessageLite)this.serverVersion_);
            }
            if ((this.bitField0_ & 8) == 8) {
                size += CodedOutputStream.computeMessageSize((int)4, (MessageLite)this.initiate_);
            }
            if ((this.bitField0_ & 16) == 16) {
                size += CodedOutputStream.computeMessageSize((int)5, (MessageLite)this.provideRefund_);
            }
            if ((this.bitField0_ & 32) == 32) {
                size += CodedOutputStream.computeMessageSize((int)6, (MessageLite)this.returnRefund_);
            }
            if ((this.bitField0_ & 64) == 64) {
                size += CodedOutputStream.computeMessageSize((int)7, (MessageLite)this.provideContract_);
            }
            if ((this.bitField0_ & 128) == 128) {
                size += CodedOutputStream.computeMessageSize((int)8, (MessageLite)this.updatePayment_);
            }
            if ((this.bitField0_ & 512) == 512) {
                size += CodedOutputStream.computeMessageSize((int)9, (MessageLite)this.settlement_);
            }
            if ((this.bitField0_ & 1024) == 1024) {
                size += CodedOutputStream.computeMessageSize((int)10, (MessageLite)this.error_);
            }
            if ((this.bitField0_ & 256) == 256) {
                size += CodedOutputStream.computeMessageSize((int)11, (MessageLite)this.paymentAck_);
            }
            this.memoizedSerializedSize = size += this.getUnknownFields().getSerializedSize();
            return size;
        }

        protected Object writeReplace() throws ObjectStreamException {
            return super.writeReplace();
        }

        public static TwoWayChannelMessage parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (TwoWayChannelMessage)PARSER.parseFrom(data);
        }

        public static TwoWayChannelMessage parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (TwoWayChannelMessage)PARSER.parseFrom(data, extensionRegistry);
        }

        public static TwoWayChannelMessage parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (TwoWayChannelMessage)PARSER.parseFrom(data);
        }

        public static TwoWayChannelMessage parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (TwoWayChannelMessage)PARSER.parseFrom(data, extensionRegistry);
        }

        public static TwoWayChannelMessage parseFrom(InputStream input) throws IOException {
            return (TwoWayChannelMessage)PARSER.parseFrom(input);
        }

        public static TwoWayChannelMessage parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (TwoWayChannelMessage)PARSER.parseFrom(input, extensionRegistry);
        }

        public static TwoWayChannelMessage parseDelimitedFrom(InputStream input) throws IOException {
            return (TwoWayChannelMessage)PARSER.parseDelimitedFrom(input);
        }

        public static TwoWayChannelMessage parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (TwoWayChannelMessage)PARSER.parseDelimitedFrom(input, extensionRegistry);
        }

        public static TwoWayChannelMessage parseFrom(CodedInputStream input) throws IOException {
            return (TwoWayChannelMessage)PARSER.parseFrom(input);
        }

        public static TwoWayChannelMessage parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (TwoWayChannelMessage)PARSER.parseFrom(input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return Builder.create();
        }

        public Builder newBuilderForType() {
            return TwoWayChannelMessage.newBuilder();
        }

        public static Builder newBuilder(TwoWayChannelMessage prototype) {
            return TwoWayChannelMessage.newBuilder().mergeFrom(prototype);
        }

        public Builder toBuilder() {
            return TwoWayChannelMessage.newBuilder(this);
        }

        protected Builder newBuilderForType(GeneratedMessage.BuilderParent parent) {
            Builder builder = new Builder(parent);
            return builder;
        }

        static {
            PARSER = new AbstractParser<TwoWayChannelMessage>(){

                public TwoWayChannelMessage parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                    return new TwoWayChannelMessage(input, extensionRegistry);
                }
            };
            defaultInstance = new TwoWayChannelMessage(true);
            defaultInstance.initFields();
        }

        public static final class Builder
        extends GeneratedMessage.Builder<Builder>
        implements TwoWayChannelMessageOrBuilder {
            private int bitField0_;
            private MessageType type_ = MessageType.CLIENT_VERSION;
            private ClientVersion clientVersion_ = ClientVersion.getDefaultInstance();
            private SingleFieldBuilder<ClientVersion, ClientVersion.Builder, ClientVersionOrBuilder> clientVersionBuilder_;
            private ServerVersion serverVersion_ = ServerVersion.getDefaultInstance();
            private SingleFieldBuilder<ServerVersion, ServerVersion.Builder, ServerVersionOrBuilder> serverVersionBuilder_;
            private Initiate initiate_ = Initiate.getDefaultInstance();
            private SingleFieldBuilder<Initiate, Initiate.Builder, InitiateOrBuilder> initiateBuilder_;
            private ProvideRefund provideRefund_ = ProvideRefund.getDefaultInstance();
            private SingleFieldBuilder<ProvideRefund, ProvideRefund.Builder, ProvideRefundOrBuilder> provideRefundBuilder_;
            private ReturnRefund returnRefund_ = ReturnRefund.getDefaultInstance();
            private SingleFieldBuilder<ReturnRefund, ReturnRefund.Builder, ReturnRefundOrBuilder> returnRefundBuilder_;
            private ProvideContract provideContract_ = ProvideContract.getDefaultInstance();
            private SingleFieldBuilder<ProvideContract, ProvideContract.Builder, ProvideContractOrBuilder> provideContractBuilder_;
            private UpdatePayment updatePayment_ = UpdatePayment.getDefaultInstance();
            private SingleFieldBuilder<UpdatePayment, UpdatePayment.Builder, UpdatePaymentOrBuilder> updatePaymentBuilder_;
            private PaymentAck paymentAck_ = PaymentAck.getDefaultInstance();
            private SingleFieldBuilder<PaymentAck, PaymentAck.Builder, PaymentAckOrBuilder> paymentAckBuilder_;
            private Settlement settlement_ = Settlement.getDefaultInstance();
            private SingleFieldBuilder<Settlement, Settlement.Builder, SettlementOrBuilder> settlementBuilder_;
            private Error error_ = Error.getDefaultInstance();
            private SingleFieldBuilder<Error, Error.Builder, ErrorOrBuilder> errorBuilder_;

            public static final Descriptors.Descriptor getDescriptor() {
                return internal_static_paymentchannels_TwoWayChannelMessage_descriptor;
            }

            protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
                return internal_static_paymentchannels_TwoWayChannelMessage_fieldAccessorTable.ensureFieldAccessorsInitialized(TwoWayChannelMessage.class, Builder.class);
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
                    this.getClientVersionFieldBuilder();
                    this.getServerVersionFieldBuilder();
                    this.getInitiateFieldBuilder();
                    this.getProvideRefundFieldBuilder();
                    this.getReturnRefundFieldBuilder();
                    this.getProvideContractFieldBuilder();
                    this.getUpdatePaymentFieldBuilder();
                    this.getPaymentAckFieldBuilder();
                    this.getSettlementFieldBuilder();
                    this.getErrorFieldBuilder();
                }
            }

            private static Builder create() {
                return new Builder();
            }

            public Builder clear() {
                super.clear();
                this.type_ = MessageType.CLIENT_VERSION;
                this.bitField0_ &= -2;
                if (this.clientVersionBuilder_ == null) {
                    this.clientVersion_ = ClientVersion.getDefaultInstance();
                } else {
                    this.clientVersionBuilder_.clear();
                }
                this.bitField0_ &= -3;
                if (this.serverVersionBuilder_ == null) {
                    this.serverVersion_ = ServerVersion.getDefaultInstance();
                } else {
                    this.serverVersionBuilder_.clear();
                }
                this.bitField0_ &= -5;
                if (this.initiateBuilder_ == null) {
                    this.initiate_ = Initiate.getDefaultInstance();
                } else {
                    this.initiateBuilder_.clear();
                }
                this.bitField0_ &= -9;
                if (this.provideRefundBuilder_ == null) {
                    this.provideRefund_ = ProvideRefund.getDefaultInstance();
                } else {
                    this.provideRefundBuilder_.clear();
                }
                this.bitField0_ &= -17;
                if (this.returnRefundBuilder_ == null) {
                    this.returnRefund_ = ReturnRefund.getDefaultInstance();
                } else {
                    this.returnRefundBuilder_.clear();
                }
                this.bitField0_ &= -33;
                if (this.provideContractBuilder_ == null) {
                    this.provideContract_ = ProvideContract.getDefaultInstance();
                } else {
                    this.provideContractBuilder_.clear();
                }
                this.bitField0_ &= -65;
                if (this.updatePaymentBuilder_ == null) {
                    this.updatePayment_ = UpdatePayment.getDefaultInstance();
                } else {
                    this.updatePaymentBuilder_.clear();
                }
                this.bitField0_ &= -129;
                if (this.paymentAckBuilder_ == null) {
                    this.paymentAck_ = PaymentAck.getDefaultInstance();
                } else {
                    this.paymentAckBuilder_.clear();
                }
                this.bitField0_ &= -257;
                if (this.settlementBuilder_ == null) {
                    this.settlement_ = Settlement.getDefaultInstance();
                } else {
                    this.settlementBuilder_.clear();
                }
                this.bitField0_ &= -513;
                if (this.errorBuilder_ == null) {
                    this.error_ = Error.getDefaultInstance();
                } else {
                    this.errorBuilder_.clear();
                }
                this.bitField0_ &= -1025;
                return this;
            }

            public Builder clone() {
                return Builder.create().mergeFrom(this.buildPartial());
            }

            public Descriptors.Descriptor getDescriptorForType() {
                return internal_static_paymentchannels_TwoWayChannelMessage_descriptor;
            }

            public TwoWayChannelMessage getDefaultInstanceForType() {
                return TwoWayChannelMessage.getDefaultInstance();
            }

            public TwoWayChannelMessage build() {
                TwoWayChannelMessage result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw Builder.newUninitializedMessageException((Message)result);
                }
                return result;
            }

            public TwoWayChannelMessage buildPartial() {
                TwoWayChannelMessage result = new TwoWayChannelMessage(this);
                int from_bitField0_ = this.bitField0_;
                int to_bitField0_ = 0;
                if ((from_bitField0_ & 1) == 1) {
                    to_bitField0_ |= true;
                }
                result.type_ = this.type_;
                if ((from_bitField0_ & 2) == 2) {
                    to_bitField0_ |= 2;
                }
                if (this.clientVersionBuilder_ == null) {
                    result.clientVersion_ = this.clientVersion_;
                } else {
                    result.clientVersion_ = (ClientVersion)this.clientVersionBuilder_.build();
                }
                if ((from_bitField0_ & 4) == 4) {
                    to_bitField0_ |= 4;
                }
                if (this.serverVersionBuilder_ == null) {
                    result.serverVersion_ = this.serverVersion_;
                } else {
                    result.serverVersion_ = (ServerVersion)this.serverVersionBuilder_.build();
                }
                if ((from_bitField0_ & 8) == 8) {
                    to_bitField0_ |= 8;
                }
                if (this.initiateBuilder_ == null) {
                    result.initiate_ = this.initiate_;
                } else {
                    result.initiate_ = (Initiate)this.initiateBuilder_.build();
                }
                if ((from_bitField0_ & 16) == 16) {
                    to_bitField0_ |= 16;
                }
                if (this.provideRefundBuilder_ == null) {
                    result.provideRefund_ = this.provideRefund_;
                } else {
                    result.provideRefund_ = (ProvideRefund)this.provideRefundBuilder_.build();
                }
                if ((from_bitField0_ & 32) == 32) {
                    to_bitField0_ |= 32;
                }
                if (this.returnRefundBuilder_ == null) {
                    result.returnRefund_ = this.returnRefund_;
                } else {
                    result.returnRefund_ = (ReturnRefund)this.returnRefundBuilder_.build();
                }
                if ((from_bitField0_ & 64) == 64) {
                    to_bitField0_ |= 64;
                }
                if (this.provideContractBuilder_ == null) {
                    result.provideContract_ = this.provideContract_;
                } else {
                    result.provideContract_ = (ProvideContract)this.provideContractBuilder_.build();
                }
                if ((from_bitField0_ & 128) == 128) {
                    to_bitField0_ |= 128;
                }
                if (this.updatePaymentBuilder_ == null) {
                    result.updatePayment_ = this.updatePayment_;
                } else {
                    result.updatePayment_ = (UpdatePayment)this.updatePaymentBuilder_.build();
                }
                if ((from_bitField0_ & 256) == 256) {
                    to_bitField0_ |= 256;
                }
                if (this.paymentAckBuilder_ == null) {
                    result.paymentAck_ = this.paymentAck_;
                } else {
                    result.paymentAck_ = (PaymentAck)this.paymentAckBuilder_.build();
                }
                if ((from_bitField0_ & 512) == 512) {
                    to_bitField0_ |= 512;
                }
                if (this.settlementBuilder_ == null) {
                    result.settlement_ = this.settlement_;
                } else {
                    result.settlement_ = (Settlement)this.settlementBuilder_.build();
                }
                if ((from_bitField0_ & 1024) == 1024) {
                    to_bitField0_ |= 1024;
                }
                if (this.errorBuilder_ == null) {
                    result.error_ = this.error_;
                } else {
                    result.error_ = (Error)this.errorBuilder_.build();
                }
                result.bitField0_ = to_bitField0_;
                this.onBuilt();
                return result;
            }

            public Builder mergeFrom(Message other) {
                if (other instanceof TwoWayChannelMessage) {
                    return this.mergeFrom((TwoWayChannelMessage)other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(TwoWayChannelMessage other) {
                if (other == TwoWayChannelMessage.getDefaultInstance()) {
                    return this;
                }
                if (other.hasType()) {
                    this.setType(other.getType());
                }
                if (other.hasClientVersion()) {
                    this.mergeClientVersion(other.getClientVersion());
                }
                if (other.hasServerVersion()) {
                    this.mergeServerVersion(other.getServerVersion());
                }
                if (other.hasInitiate()) {
                    this.mergeInitiate(other.getInitiate());
                }
                if (other.hasProvideRefund()) {
                    this.mergeProvideRefund(other.getProvideRefund());
                }
                if (other.hasReturnRefund()) {
                    this.mergeReturnRefund(other.getReturnRefund());
                }
                if (other.hasProvideContract()) {
                    this.mergeProvideContract(other.getProvideContract());
                }
                if (other.hasUpdatePayment()) {
                    this.mergeUpdatePayment(other.getUpdatePayment());
                }
                if (other.hasPaymentAck()) {
                    this.mergePaymentAck(other.getPaymentAck());
                }
                if (other.hasSettlement()) {
                    this.mergeSettlement(other.getSettlement());
                }
                if (other.hasError()) {
                    this.mergeError(other.getError());
                }
                this.mergeUnknownFields(other.getUnknownFields());
                return this;
            }

            public final boolean isInitialized() {
                if (!this.hasType()) {
                    return false;
                }
                if (this.hasClientVersion() && !this.getClientVersion().isInitialized()) {
                    return false;
                }
                if (this.hasServerVersion() && !this.getServerVersion().isInitialized()) {
                    return false;
                }
                if (this.hasInitiate() && !this.getInitiate().isInitialized()) {
                    return false;
                }
                if (this.hasProvideRefund() && !this.getProvideRefund().isInitialized()) {
                    return false;
                }
                if (this.hasReturnRefund() && !this.getReturnRefund().isInitialized()) {
                    return false;
                }
                if (this.hasProvideContract() && !this.getProvideContract().isInitialized()) {
                    return false;
                }
                if (this.hasUpdatePayment() && !this.getUpdatePayment().isInitialized()) {
                    return false;
                }
                if (this.hasSettlement() && !this.getSettlement().isInitialized()) {
                    return false;
                }
                return true;
            }

            public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                TwoWayChannelMessage parsedMessage = null;
                try {
                    parsedMessage = (TwoWayChannelMessage)TwoWayChannelMessage.PARSER.parsePartialFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e) {
                    parsedMessage = (TwoWayChannelMessage)e.getUnfinishedMessage();
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
            public boolean hasType() {
                return (this.bitField0_ & 1) == 1;
            }

            @Override
            public MessageType getType() {
                return this.type_;
            }

            public Builder setType(MessageType value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 1;
                this.type_ = value;
                this.onChanged();
                return this;
            }

            public Builder clearType() {
                this.bitField0_ &= -2;
                this.type_ = MessageType.CLIENT_VERSION;
                this.onChanged();
                return this;
            }

            @Override
            public boolean hasClientVersion() {
                return (this.bitField0_ & 2) == 2;
            }

            @Override
            public ClientVersion getClientVersion() {
                if (this.clientVersionBuilder_ == null) {
                    return this.clientVersion_;
                }
                return (ClientVersion)this.clientVersionBuilder_.getMessage();
            }

            public Builder setClientVersion(ClientVersion value) {
                if (this.clientVersionBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.clientVersion_ = value;
                    this.onChanged();
                } else {
                    this.clientVersionBuilder_.setMessage((GeneratedMessage)value);
                }
                this.bitField0_ |= 2;
                return this;
            }

            public Builder setClientVersion(ClientVersion.Builder builderForValue) {
                if (this.clientVersionBuilder_ == null) {
                    this.clientVersion_ = builderForValue.build();
                    this.onChanged();
                } else {
                    this.clientVersionBuilder_.setMessage((GeneratedMessage)builderForValue.build());
                }
                this.bitField0_ |= 2;
                return this;
            }

            public Builder mergeClientVersion(ClientVersion value) {
                if (this.clientVersionBuilder_ == null) {
                    this.clientVersion_ = (this.bitField0_ & 2) == 2 && this.clientVersion_ != ClientVersion.getDefaultInstance() ? ClientVersion.newBuilder(this.clientVersion_).mergeFrom(value).buildPartial() : value;
                    this.onChanged();
                } else {
                    this.clientVersionBuilder_.mergeFrom((GeneratedMessage)value);
                }
                this.bitField0_ |= 2;
                return this;
            }

            public Builder clearClientVersion() {
                if (this.clientVersionBuilder_ == null) {
                    this.clientVersion_ = ClientVersion.getDefaultInstance();
                    this.onChanged();
                } else {
                    this.clientVersionBuilder_.clear();
                }
                this.bitField0_ &= -3;
                return this;
            }

            public ClientVersion.Builder getClientVersionBuilder() {
                this.bitField0_ |= 2;
                this.onChanged();
                return (ClientVersion.Builder)this.getClientVersionFieldBuilder().getBuilder();
            }

            @Override
            public ClientVersionOrBuilder getClientVersionOrBuilder() {
                if (this.clientVersionBuilder_ != null) {
                    return (ClientVersionOrBuilder)this.clientVersionBuilder_.getMessageOrBuilder();
                }
                return this.clientVersion_;
            }

            private SingleFieldBuilder<ClientVersion, ClientVersion.Builder, ClientVersionOrBuilder> getClientVersionFieldBuilder() {
                if (this.clientVersionBuilder_ == null) {
                    this.clientVersionBuilder_ = new SingleFieldBuilder((GeneratedMessage)this.getClientVersion(), this.getParentForChildren(), this.isClean());
                    this.clientVersion_ = null;
                }
                return this.clientVersionBuilder_;
            }

            @Override
            public boolean hasServerVersion() {
                return (this.bitField0_ & 4) == 4;
            }

            @Override
            public ServerVersion getServerVersion() {
                if (this.serverVersionBuilder_ == null) {
                    return this.serverVersion_;
                }
                return (ServerVersion)this.serverVersionBuilder_.getMessage();
            }

            public Builder setServerVersion(ServerVersion value) {
                if (this.serverVersionBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.serverVersion_ = value;
                    this.onChanged();
                } else {
                    this.serverVersionBuilder_.setMessage((GeneratedMessage)value);
                }
                this.bitField0_ |= 4;
                return this;
            }

            public Builder setServerVersion(ServerVersion.Builder builderForValue) {
                if (this.serverVersionBuilder_ == null) {
                    this.serverVersion_ = builderForValue.build();
                    this.onChanged();
                } else {
                    this.serverVersionBuilder_.setMessage((GeneratedMessage)builderForValue.build());
                }
                this.bitField0_ |= 4;
                return this;
            }

            public Builder mergeServerVersion(ServerVersion value) {
                if (this.serverVersionBuilder_ == null) {
                    this.serverVersion_ = (this.bitField0_ & 4) == 4 && this.serverVersion_ != ServerVersion.getDefaultInstance() ? ServerVersion.newBuilder(this.serverVersion_).mergeFrom(value).buildPartial() : value;
                    this.onChanged();
                } else {
                    this.serverVersionBuilder_.mergeFrom((GeneratedMessage)value);
                }
                this.bitField0_ |= 4;
                return this;
            }

            public Builder clearServerVersion() {
                if (this.serverVersionBuilder_ == null) {
                    this.serverVersion_ = ServerVersion.getDefaultInstance();
                    this.onChanged();
                } else {
                    this.serverVersionBuilder_.clear();
                }
                this.bitField0_ &= -5;
                return this;
            }

            public ServerVersion.Builder getServerVersionBuilder() {
                this.bitField0_ |= 4;
                this.onChanged();
                return (ServerVersion.Builder)this.getServerVersionFieldBuilder().getBuilder();
            }

            @Override
            public ServerVersionOrBuilder getServerVersionOrBuilder() {
                if (this.serverVersionBuilder_ != null) {
                    return (ServerVersionOrBuilder)this.serverVersionBuilder_.getMessageOrBuilder();
                }
                return this.serverVersion_;
            }

            private SingleFieldBuilder<ServerVersion, ServerVersion.Builder, ServerVersionOrBuilder> getServerVersionFieldBuilder() {
                if (this.serverVersionBuilder_ == null) {
                    this.serverVersionBuilder_ = new SingleFieldBuilder((GeneratedMessage)this.getServerVersion(), this.getParentForChildren(), this.isClean());
                    this.serverVersion_ = null;
                }
                return this.serverVersionBuilder_;
            }

            @Override
            public boolean hasInitiate() {
                return (this.bitField0_ & 8) == 8;
            }

            @Override
            public Initiate getInitiate() {
                if (this.initiateBuilder_ == null) {
                    return this.initiate_;
                }
                return (Initiate)this.initiateBuilder_.getMessage();
            }

            public Builder setInitiate(Initiate value) {
                if (this.initiateBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.initiate_ = value;
                    this.onChanged();
                } else {
                    this.initiateBuilder_.setMessage((GeneratedMessage)value);
                }
                this.bitField0_ |= 8;
                return this;
            }

            public Builder setInitiate(Initiate.Builder builderForValue) {
                if (this.initiateBuilder_ == null) {
                    this.initiate_ = builderForValue.build();
                    this.onChanged();
                } else {
                    this.initiateBuilder_.setMessage((GeneratedMessage)builderForValue.build());
                }
                this.bitField0_ |= 8;
                return this;
            }

            public Builder mergeInitiate(Initiate value) {
                if (this.initiateBuilder_ == null) {
                    this.initiate_ = (this.bitField0_ & 8) == 8 && this.initiate_ != Initiate.getDefaultInstance() ? Initiate.newBuilder(this.initiate_).mergeFrom(value).buildPartial() : value;
                    this.onChanged();
                } else {
                    this.initiateBuilder_.mergeFrom((GeneratedMessage)value);
                }
                this.bitField0_ |= 8;
                return this;
            }

            public Builder clearInitiate() {
                if (this.initiateBuilder_ == null) {
                    this.initiate_ = Initiate.getDefaultInstance();
                    this.onChanged();
                } else {
                    this.initiateBuilder_.clear();
                }
                this.bitField0_ &= -9;
                return this;
            }

            public Initiate.Builder getInitiateBuilder() {
                this.bitField0_ |= 8;
                this.onChanged();
                return (Initiate.Builder)this.getInitiateFieldBuilder().getBuilder();
            }

            @Override
            public InitiateOrBuilder getInitiateOrBuilder() {
                if (this.initiateBuilder_ != null) {
                    return (InitiateOrBuilder)this.initiateBuilder_.getMessageOrBuilder();
                }
                return this.initiate_;
            }

            private SingleFieldBuilder<Initiate, Initiate.Builder, InitiateOrBuilder> getInitiateFieldBuilder() {
                if (this.initiateBuilder_ == null) {
                    this.initiateBuilder_ = new SingleFieldBuilder((GeneratedMessage)this.getInitiate(), this.getParentForChildren(), this.isClean());
                    this.initiate_ = null;
                }
                return this.initiateBuilder_;
            }

            @Override
            public boolean hasProvideRefund() {
                return (this.bitField0_ & 16) == 16;
            }

            @Override
            public ProvideRefund getProvideRefund() {
                if (this.provideRefundBuilder_ == null) {
                    return this.provideRefund_;
                }
                return (ProvideRefund)this.provideRefundBuilder_.getMessage();
            }

            public Builder setProvideRefund(ProvideRefund value) {
                if (this.provideRefundBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.provideRefund_ = value;
                    this.onChanged();
                } else {
                    this.provideRefundBuilder_.setMessage((GeneratedMessage)value);
                }
                this.bitField0_ |= 16;
                return this;
            }

            public Builder setProvideRefund(ProvideRefund.Builder builderForValue) {
                if (this.provideRefundBuilder_ == null) {
                    this.provideRefund_ = builderForValue.build();
                    this.onChanged();
                } else {
                    this.provideRefundBuilder_.setMessage((GeneratedMessage)builderForValue.build());
                }
                this.bitField0_ |= 16;
                return this;
            }

            public Builder mergeProvideRefund(ProvideRefund value) {
                if (this.provideRefundBuilder_ == null) {
                    this.provideRefund_ = (this.bitField0_ & 16) == 16 && this.provideRefund_ != ProvideRefund.getDefaultInstance() ? ProvideRefund.newBuilder(this.provideRefund_).mergeFrom(value).buildPartial() : value;
                    this.onChanged();
                } else {
                    this.provideRefundBuilder_.mergeFrom((GeneratedMessage)value);
                }
                this.bitField0_ |= 16;
                return this;
            }

            public Builder clearProvideRefund() {
                if (this.provideRefundBuilder_ == null) {
                    this.provideRefund_ = ProvideRefund.getDefaultInstance();
                    this.onChanged();
                } else {
                    this.provideRefundBuilder_.clear();
                }
                this.bitField0_ &= -17;
                return this;
            }

            public ProvideRefund.Builder getProvideRefundBuilder() {
                this.bitField0_ |= 16;
                this.onChanged();
                return (ProvideRefund.Builder)this.getProvideRefundFieldBuilder().getBuilder();
            }

            @Override
            public ProvideRefundOrBuilder getProvideRefundOrBuilder() {
                if (this.provideRefundBuilder_ != null) {
                    return (ProvideRefundOrBuilder)this.provideRefundBuilder_.getMessageOrBuilder();
                }
                return this.provideRefund_;
            }

            private SingleFieldBuilder<ProvideRefund, ProvideRefund.Builder, ProvideRefundOrBuilder> getProvideRefundFieldBuilder() {
                if (this.provideRefundBuilder_ == null) {
                    this.provideRefundBuilder_ = new SingleFieldBuilder((GeneratedMessage)this.getProvideRefund(), this.getParentForChildren(), this.isClean());
                    this.provideRefund_ = null;
                }
                return this.provideRefundBuilder_;
            }

            @Override
            public boolean hasReturnRefund() {
                return (this.bitField0_ & 32) == 32;
            }

            @Override
            public ReturnRefund getReturnRefund() {
                if (this.returnRefundBuilder_ == null) {
                    return this.returnRefund_;
                }
                return (ReturnRefund)this.returnRefundBuilder_.getMessage();
            }

            public Builder setReturnRefund(ReturnRefund value) {
                if (this.returnRefundBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.returnRefund_ = value;
                    this.onChanged();
                } else {
                    this.returnRefundBuilder_.setMessage((GeneratedMessage)value);
                }
                this.bitField0_ |= 32;
                return this;
            }

            public Builder setReturnRefund(ReturnRefund.Builder builderForValue) {
                if (this.returnRefundBuilder_ == null) {
                    this.returnRefund_ = builderForValue.build();
                    this.onChanged();
                } else {
                    this.returnRefundBuilder_.setMessage((GeneratedMessage)builderForValue.build());
                }
                this.bitField0_ |= 32;
                return this;
            }

            public Builder mergeReturnRefund(ReturnRefund value) {
                if (this.returnRefundBuilder_ == null) {
                    this.returnRefund_ = (this.bitField0_ & 32) == 32 && this.returnRefund_ != ReturnRefund.getDefaultInstance() ? ReturnRefund.newBuilder(this.returnRefund_).mergeFrom(value).buildPartial() : value;
                    this.onChanged();
                } else {
                    this.returnRefundBuilder_.mergeFrom((GeneratedMessage)value);
                }
                this.bitField0_ |= 32;
                return this;
            }

            public Builder clearReturnRefund() {
                if (this.returnRefundBuilder_ == null) {
                    this.returnRefund_ = ReturnRefund.getDefaultInstance();
                    this.onChanged();
                } else {
                    this.returnRefundBuilder_.clear();
                }
                this.bitField0_ &= -33;
                return this;
            }

            public ReturnRefund.Builder getReturnRefundBuilder() {
                this.bitField0_ |= 32;
                this.onChanged();
                return (ReturnRefund.Builder)this.getReturnRefundFieldBuilder().getBuilder();
            }

            @Override
            public ReturnRefundOrBuilder getReturnRefundOrBuilder() {
                if (this.returnRefundBuilder_ != null) {
                    return (ReturnRefundOrBuilder)this.returnRefundBuilder_.getMessageOrBuilder();
                }
                return this.returnRefund_;
            }

            private SingleFieldBuilder<ReturnRefund, ReturnRefund.Builder, ReturnRefundOrBuilder> getReturnRefundFieldBuilder() {
                if (this.returnRefundBuilder_ == null) {
                    this.returnRefundBuilder_ = new SingleFieldBuilder((GeneratedMessage)this.getReturnRefund(), this.getParentForChildren(), this.isClean());
                    this.returnRefund_ = null;
                }
                return this.returnRefundBuilder_;
            }

            @Override
            public boolean hasProvideContract() {
                return (this.bitField0_ & 64) == 64;
            }

            @Override
            public ProvideContract getProvideContract() {
                if (this.provideContractBuilder_ == null) {
                    return this.provideContract_;
                }
                return (ProvideContract)this.provideContractBuilder_.getMessage();
            }

            public Builder setProvideContract(ProvideContract value) {
                if (this.provideContractBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.provideContract_ = value;
                    this.onChanged();
                } else {
                    this.provideContractBuilder_.setMessage((GeneratedMessage)value);
                }
                this.bitField0_ |= 64;
                return this;
            }

            public Builder setProvideContract(ProvideContract.Builder builderForValue) {
                if (this.provideContractBuilder_ == null) {
                    this.provideContract_ = builderForValue.build();
                    this.onChanged();
                } else {
                    this.provideContractBuilder_.setMessage((GeneratedMessage)builderForValue.build());
                }
                this.bitField0_ |= 64;
                return this;
            }

            public Builder mergeProvideContract(ProvideContract value) {
                if (this.provideContractBuilder_ == null) {
                    this.provideContract_ = (this.bitField0_ & 64) == 64 && this.provideContract_ != ProvideContract.getDefaultInstance() ? ProvideContract.newBuilder(this.provideContract_).mergeFrom(value).buildPartial() : value;
                    this.onChanged();
                } else {
                    this.provideContractBuilder_.mergeFrom((GeneratedMessage)value);
                }
                this.bitField0_ |= 64;
                return this;
            }

            public Builder clearProvideContract() {
                if (this.provideContractBuilder_ == null) {
                    this.provideContract_ = ProvideContract.getDefaultInstance();
                    this.onChanged();
                } else {
                    this.provideContractBuilder_.clear();
                }
                this.bitField0_ &= -65;
                return this;
            }

            public ProvideContract.Builder getProvideContractBuilder() {
                this.bitField0_ |= 64;
                this.onChanged();
                return (ProvideContract.Builder)this.getProvideContractFieldBuilder().getBuilder();
            }

            @Override
            public ProvideContractOrBuilder getProvideContractOrBuilder() {
                if (this.provideContractBuilder_ != null) {
                    return (ProvideContractOrBuilder)this.provideContractBuilder_.getMessageOrBuilder();
                }
                return this.provideContract_;
            }

            private SingleFieldBuilder<ProvideContract, ProvideContract.Builder, ProvideContractOrBuilder> getProvideContractFieldBuilder() {
                if (this.provideContractBuilder_ == null) {
                    this.provideContractBuilder_ = new SingleFieldBuilder((GeneratedMessage)this.getProvideContract(), this.getParentForChildren(), this.isClean());
                    this.provideContract_ = null;
                }
                return this.provideContractBuilder_;
            }

            @Override
            public boolean hasUpdatePayment() {
                return (this.bitField0_ & 128) == 128;
            }

            @Override
            public UpdatePayment getUpdatePayment() {
                if (this.updatePaymentBuilder_ == null) {
                    return this.updatePayment_;
                }
                return (UpdatePayment)this.updatePaymentBuilder_.getMessage();
            }

            public Builder setUpdatePayment(UpdatePayment value) {
                if (this.updatePaymentBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.updatePayment_ = value;
                    this.onChanged();
                } else {
                    this.updatePaymentBuilder_.setMessage((GeneratedMessage)value);
                }
                this.bitField0_ |= 128;
                return this;
            }

            public Builder setUpdatePayment(UpdatePayment.Builder builderForValue) {
                if (this.updatePaymentBuilder_ == null) {
                    this.updatePayment_ = builderForValue.build();
                    this.onChanged();
                } else {
                    this.updatePaymentBuilder_.setMessage((GeneratedMessage)builderForValue.build());
                }
                this.bitField0_ |= 128;
                return this;
            }

            public Builder mergeUpdatePayment(UpdatePayment value) {
                if (this.updatePaymentBuilder_ == null) {
                    this.updatePayment_ = (this.bitField0_ & 128) == 128 && this.updatePayment_ != UpdatePayment.getDefaultInstance() ? UpdatePayment.newBuilder(this.updatePayment_).mergeFrom(value).buildPartial() : value;
                    this.onChanged();
                } else {
                    this.updatePaymentBuilder_.mergeFrom((GeneratedMessage)value);
                }
                this.bitField0_ |= 128;
                return this;
            }

            public Builder clearUpdatePayment() {
                if (this.updatePaymentBuilder_ == null) {
                    this.updatePayment_ = UpdatePayment.getDefaultInstance();
                    this.onChanged();
                } else {
                    this.updatePaymentBuilder_.clear();
                }
                this.bitField0_ &= -129;
                return this;
            }

            public UpdatePayment.Builder getUpdatePaymentBuilder() {
                this.bitField0_ |= 128;
                this.onChanged();
                return (UpdatePayment.Builder)this.getUpdatePaymentFieldBuilder().getBuilder();
            }

            @Override
            public UpdatePaymentOrBuilder getUpdatePaymentOrBuilder() {
                if (this.updatePaymentBuilder_ != null) {
                    return (UpdatePaymentOrBuilder)this.updatePaymentBuilder_.getMessageOrBuilder();
                }
                return this.updatePayment_;
            }

            private SingleFieldBuilder<UpdatePayment, UpdatePayment.Builder, UpdatePaymentOrBuilder> getUpdatePaymentFieldBuilder() {
                if (this.updatePaymentBuilder_ == null) {
                    this.updatePaymentBuilder_ = new SingleFieldBuilder((GeneratedMessage)this.getUpdatePayment(), this.getParentForChildren(), this.isClean());
                    this.updatePayment_ = null;
                }
                return this.updatePaymentBuilder_;
            }

            @Override
            public boolean hasPaymentAck() {
                return (this.bitField0_ & 256) == 256;
            }

            @Override
            public PaymentAck getPaymentAck() {
                if (this.paymentAckBuilder_ == null) {
                    return this.paymentAck_;
                }
                return (PaymentAck)this.paymentAckBuilder_.getMessage();
            }

            public Builder setPaymentAck(PaymentAck value) {
                if (this.paymentAckBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.paymentAck_ = value;
                    this.onChanged();
                } else {
                    this.paymentAckBuilder_.setMessage((GeneratedMessage)value);
                }
                this.bitField0_ |= 256;
                return this;
            }

            public Builder setPaymentAck(PaymentAck.Builder builderForValue) {
                if (this.paymentAckBuilder_ == null) {
                    this.paymentAck_ = builderForValue.build();
                    this.onChanged();
                } else {
                    this.paymentAckBuilder_.setMessage((GeneratedMessage)builderForValue.build());
                }
                this.bitField0_ |= 256;
                return this;
            }

            public Builder mergePaymentAck(PaymentAck value) {
                if (this.paymentAckBuilder_ == null) {
                    this.paymentAck_ = (this.bitField0_ & 256) == 256 && this.paymentAck_ != PaymentAck.getDefaultInstance() ? PaymentAck.newBuilder(this.paymentAck_).mergeFrom(value).buildPartial() : value;
                    this.onChanged();
                } else {
                    this.paymentAckBuilder_.mergeFrom((GeneratedMessage)value);
                }
                this.bitField0_ |= 256;
                return this;
            }

            public Builder clearPaymentAck() {
                if (this.paymentAckBuilder_ == null) {
                    this.paymentAck_ = PaymentAck.getDefaultInstance();
                    this.onChanged();
                } else {
                    this.paymentAckBuilder_.clear();
                }
                this.bitField0_ &= -257;
                return this;
            }

            public PaymentAck.Builder getPaymentAckBuilder() {
                this.bitField0_ |= 256;
                this.onChanged();
                return (PaymentAck.Builder)this.getPaymentAckFieldBuilder().getBuilder();
            }

            @Override
            public PaymentAckOrBuilder getPaymentAckOrBuilder() {
                if (this.paymentAckBuilder_ != null) {
                    return (PaymentAckOrBuilder)this.paymentAckBuilder_.getMessageOrBuilder();
                }
                return this.paymentAck_;
            }

            private SingleFieldBuilder<PaymentAck, PaymentAck.Builder, PaymentAckOrBuilder> getPaymentAckFieldBuilder() {
                if (this.paymentAckBuilder_ == null) {
                    this.paymentAckBuilder_ = new SingleFieldBuilder((GeneratedMessage)this.getPaymentAck(), this.getParentForChildren(), this.isClean());
                    this.paymentAck_ = null;
                }
                return this.paymentAckBuilder_;
            }

            @Override
            public boolean hasSettlement() {
                return (this.bitField0_ & 512) == 512;
            }

            @Override
            public Settlement getSettlement() {
                if (this.settlementBuilder_ == null) {
                    return this.settlement_;
                }
                return (Settlement)this.settlementBuilder_.getMessage();
            }

            public Builder setSettlement(Settlement value) {
                if (this.settlementBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.settlement_ = value;
                    this.onChanged();
                } else {
                    this.settlementBuilder_.setMessage((GeneratedMessage)value);
                }
                this.bitField0_ |= 512;
                return this;
            }

            public Builder setSettlement(Settlement.Builder builderForValue) {
                if (this.settlementBuilder_ == null) {
                    this.settlement_ = builderForValue.build();
                    this.onChanged();
                } else {
                    this.settlementBuilder_.setMessage((GeneratedMessage)builderForValue.build());
                }
                this.bitField0_ |= 512;
                return this;
            }

            public Builder mergeSettlement(Settlement value) {
                if (this.settlementBuilder_ == null) {
                    this.settlement_ = (this.bitField0_ & 512) == 512 && this.settlement_ != Settlement.getDefaultInstance() ? Settlement.newBuilder(this.settlement_).mergeFrom(value).buildPartial() : value;
                    this.onChanged();
                } else {
                    this.settlementBuilder_.mergeFrom((GeneratedMessage)value);
                }
                this.bitField0_ |= 512;
                return this;
            }

            public Builder clearSettlement() {
                if (this.settlementBuilder_ == null) {
                    this.settlement_ = Settlement.getDefaultInstance();
                    this.onChanged();
                } else {
                    this.settlementBuilder_.clear();
                }
                this.bitField0_ &= -513;
                return this;
            }

            public Settlement.Builder getSettlementBuilder() {
                this.bitField0_ |= 512;
                this.onChanged();
                return (Settlement.Builder)this.getSettlementFieldBuilder().getBuilder();
            }

            @Override
            public SettlementOrBuilder getSettlementOrBuilder() {
                if (this.settlementBuilder_ != null) {
                    return (SettlementOrBuilder)this.settlementBuilder_.getMessageOrBuilder();
                }
                return this.settlement_;
            }

            private SingleFieldBuilder<Settlement, Settlement.Builder, SettlementOrBuilder> getSettlementFieldBuilder() {
                if (this.settlementBuilder_ == null) {
                    this.settlementBuilder_ = new SingleFieldBuilder((GeneratedMessage)this.getSettlement(), this.getParentForChildren(), this.isClean());
                    this.settlement_ = null;
                }
                return this.settlementBuilder_;
            }

            @Override
            public boolean hasError() {
                return (this.bitField0_ & 1024) == 1024;
            }

            @Override
            public Error getError() {
                if (this.errorBuilder_ == null) {
                    return this.error_;
                }
                return (Error)this.errorBuilder_.getMessage();
            }

            public Builder setError(Error value) {
                if (this.errorBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.error_ = value;
                    this.onChanged();
                } else {
                    this.errorBuilder_.setMessage((GeneratedMessage)value);
                }
                this.bitField0_ |= 1024;
                return this;
            }

            public Builder setError(Error.Builder builderForValue) {
                if (this.errorBuilder_ == null) {
                    this.error_ = builderForValue.build();
                    this.onChanged();
                } else {
                    this.errorBuilder_.setMessage((GeneratedMessage)builderForValue.build());
                }
                this.bitField0_ |= 1024;
                return this;
            }

            public Builder mergeError(Error value) {
                if (this.errorBuilder_ == null) {
                    this.error_ = (this.bitField0_ & 1024) == 1024 && this.error_ != Error.getDefaultInstance() ? Error.newBuilder(this.error_).mergeFrom(value).buildPartial() : value;
                    this.onChanged();
                } else {
                    this.errorBuilder_.mergeFrom((GeneratedMessage)value);
                }
                this.bitField0_ |= 1024;
                return this;
            }

            public Builder clearError() {
                if (this.errorBuilder_ == null) {
                    this.error_ = Error.getDefaultInstance();
                    this.onChanged();
                } else {
                    this.errorBuilder_.clear();
                }
                this.bitField0_ &= -1025;
                return this;
            }

            public Error.Builder getErrorBuilder() {
                this.bitField0_ |= 1024;
                this.onChanged();
                return (Error.Builder)this.getErrorFieldBuilder().getBuilder();
            }

            @Override
            public ErrorOrBuilder getErrorOrBuilder() {
                if (this.errorBuilder_ != null) {
                    return (ErrorOrBuilder)this.errorBuilder_.getMessageOrBuilder();
                }
                return this.error_;
            }

            private SingleFieldBuilder<Error, Error.Builder, ErrorOrBuilder> getErrorFieldBuilder() {
                if (this.errorBuilder_ == null) {
                    this.errorBuilder_ = new SingleFieldBuilder((GeneratedMessage)this.getError(), this.getParentForChildren(), this.isClean());
                    this.error_ = null;
                }
                return this.errorBuilder_;
            }
        }

        public static enum MessageType implements ProtocolMessageEnum
        {
            CLIENT_VERSION(0, 1),
            SERVER_VERSION(1, 2),
            INITIATE(2, 3),
            PROVIDE_REFUND(3, 4),
            RETURN_REFUND(4, 5),
            PROVIDE_CONTRACT(5, 6),
            CHANNEL_OPEN(6, 7),
            UPDATE_PAYMENT(7, 8),
            PAYMENT_ACK(8, 11),
            CLOSE(9, 9),
            ERROR(10, 10);
            
            public static final int CLIENT_VERSION_VALUE = 1;
            public static final int SERVER_VERSION_VALUE = 2;
            public static final int INITIATE_VALUE = 3;
            public static final int PROVIDE_REFUND_VALUE = 4;
            public static final int RETURN_REFUND_VALUE = 5;
            public static final int PROVIDE_CONTRACT_VALUE = 6;
            public static final int CHANNEL_OPEN_VALUE = 7;
            public static final int UPDATE_PAYMENT_VALUE = 8;
            public static final int PAYMENT_ACK_VALUE = 11;
            public static final int CLOSE_VALUE = 9;
            public static final int ERROR_VALUE = 10;
            private static Internal.EnumLiteMap<MessageType> internalValueMap;
            private static final MessageType[] VALUES;
            private final int index;
            private final int value;

            public final int getNumber() {
                return this.value;
            }

            public static MessageType valueOf(int value) {
                switch (value) {
                    case 1: {
                        return CLIENT_VERSION;
                    }
                    case 2: {
                        return SERVER_VERSION;
                    }
                    case 3: {
                        return INITIATE;
                    }
                    case 4: {
                        return PROVIDE_REFUND;
                    }
                    case 5: {
                        return RETURN_REFUND;
                    }
                    case 6: {
                        return PROVIDE_CONTRACT;
                    }
                    case 7: {
                        return CHANNEL_OPEN;
                    }
                    case 8: {
                        return UPDATE_PAYMENT;
                    }
                    case 11: {
                        return PAYMENT_ACK;
                    }
                    case 9: {
                        return CLOSE;
                    }
                    case 10: {
                        return ERROR;
                    }
                }
                return null;
            }

            public static Internal.EnumLiteMap<MessageType> internalGetValueMap() {
                return internalValueMap;
            }

            public final Descriptors.EnumValueDescriptor getValueDescriptor() {
                return (Descriptors.EnumValueDescriptor)MessageType.getDescriptor().getValues().get(this.index);
            }

            public final Descriptors.EnumDescriptor getDescriptorForType() {
                return MessageType.getDescriptor();
            }

            public static final Descriptors.EnumDescriptor getDescriptor() {
                return (Descriptors.EnumDescriptor)TwoWayChannelMessage.getDescriptor().getEnumTypes().get(0);
            }

            public static MessageType valueOf(Descriptors.EnumValueDescriptor desc) {
                if (desc.getType() != MessageType.getDescriptor()) {
                    throw new IllegalArgumentException("EnumValueDescriptor is not for this type.");
                }
                return VALUES[desc.getIndex()];
            }

            private MessageType(int index, int value) {
                this.index = index;
                this.value = value;
            }

            static {
                internalValueMap = new Internal.EnumLiteMap<MessageType>(){

                    public MessageType findValueByNumber(int number) {
                        return MessageType.valueOf(number);
                    }
                };
                VALUES = MessageType.values();
            }

        }

    }

    public static interface TwoWayChannelMessageOrBuilder
    extends MessageOrBuilder {
        public boolean hasType();

        public TwoWayChannelMessage.MessageType getType();

        public boolean hasClientVersion();

        public ClientVersion getClientVersion();

        public ClientVersionOrBuilder getClientVersionOrBuilder();

        public boolean hasServerVersion();

        public ServerVersion getServerVersion();

        public ServerVersionOrBuilder getServerVersionOrBuilder();

        public boolean hasInitiate();

        public Initiate getInitiate();

        public InitiateOrBuilder getInitiateOrBuilder();

        public boolean hasProvideRefund();

        public ProvideRefund getProvideRefund();

        public ProvideRefundOrBuilder getProvideRefundOrBuilder();

        public boolean hasReturnRefund();

        public ReturnRefund getReturnRefund();

        public ReturnRefundOrBuilder getReturnRefundOrBuilder();

        public boolean hasProvideContract();

        public ProvideContract getProvideContract();

        public ProvideContractOrBuilder getProvideContractOrBuilder();

        public boolean hasUpdatePayment();

        public UpdatePayment getUpdatePayment();

        public UpdatePaymentOrBuilder getUpdatePaymentOrBuilder();

        public boolean hasPaymentAck();

        public PaymentAck getPaymentAck();

        public PaymentAckOrBuilder getPaymentAckOrBuilder();

        public boolean hasSettlement();

        public Settlement getSettlement();

        public SettlementOrBuilder getSettlementOrBuilder();

        public boolean hasError();

        public Error getError();

        public ErrorOrBuilder getErrorOrBuilder();
    }

}

