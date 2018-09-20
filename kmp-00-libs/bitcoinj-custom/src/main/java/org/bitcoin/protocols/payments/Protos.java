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
 *  com.google.protobuf.SingleFieldBuilder
 *  com.google.protobuf.UnknownFieldSet
 *  com.google.protobuf.UnknownFieldSet$Builder
 */
package org.bitcoin.protocols.payments;

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
import com.google.protobuf.SingleFieldBuilder;
import com.google.protobuf.UnknownFieldSet;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectStreamException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public final class Protos {
    private static final Descriptors.Descriptor internal_static_payments_Output_descriptor;
    private static GeneratedMessage.FieldAccessorTable internal_static_payments_Output_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_payments_PaymentDetails_descriptor;
    private static GeneratedMessage.FieldAccessorTable internal_static_payments_PaymentDetails_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_payments_PaymentRequest_descriptor;
    private static GeneratedMessage.FieldAccessorTable internal_static_payments_PaymentRequest_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_payments_X509Certificates_descriptor;
    private static GeneratedMessage.FieldAccessorTable internal_static_payments_X509Certificates_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_payments_Payment_descriptor;
    private static GeneratedMessage.FieldAccessorTable internal_static_payments_Payment_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_payments_PaymentACK_descriptor;
    private static GeneratedMessage.FieldAccessorTable internal_static_payments_PaymentACK_fieldAccessorTable;
    private static Descriptors.FileDescriptor descriptor;

    private Protos() {
    }

    public static void registerAllExtensions(ExtensionRegistry registry) {
    }

    public static Descriptors.FileDescriptor getDescriptor() {
        return descriptor;
    }

    static {
        String[] descriptorData = new String[]{"\n\u0014paymentrequest.proto\u0012\bpayments\"+\n\u0006Output\u0012\u0011\n\u0006amount\u0018\u0001 \u0001(\u0004:\u00010\u0012\u000e\n\u0006script\u0018\u0002 \u0002(\f\"\u00a3\u0001\n\u000ePaymentDetails\u0012\u0015\n\u0007network\u0018\u0001 \u0001(\t:\u0004main\u0012!\n\u0007outputs\u0018\u0002 \u0003(\u000b2\u0010.payments.Output\u0012\f\n\u0004time\u0018\u0003 \u0002(\u0004\u0012\u000f\n\u0007expires\u0018\u0004 \u0001(\u0004\u0012\f\n\u0004memo\u0018\u0005 \u0001(\t\u0012\u0013\n\u000bpayment_url\u0018\u0006 \u0001(\t\u0012\u0015\n\rmerchant_data\u0018\u0007 \u0001(\f\"\u0095\u0001\n\u000ePaymentRequest\u0012\"\n\u0017payment_details_version\u0018\u0001 \u0001(\r:\u00011\u0012\u0016\n\bpki_type\u0018\u0002 \u0001(\t:\u0004none\u0012\u0010\n\bpki_data\u0018\u0003 \u0001(\f\u0012\"\n\u001aserialized_payment_details\u0018\u0004 \u0002(\f\u0012\u0011\n\tsignature\u0018\u0005 \u0001(\f\"'\n\u0010X", "509Certificates\u0012\u0013\n\u000bcertificate\u0018\u0001 \u0003(\f\"i\n\u0007Payment\u0012\u0015\n\rmerchant_data\u0018\u0001 \u0001(\f\u0012\u0014\n\ftransactions\u0018\u0002 \u0003(\f\u0012#\n\trefund_to\u0018\u0003 \u0003(\u000b2\u0010.payments.Output\u0012\f\n\u0004memo\u0018\u0004 \u0001(\t\">\n\nPaymentACK\u0012\"\n\u0007payment\u0018\u0001 \u0002(\u000b2\u0011.payments.Payment\u0012\f\n\u0004memo\u0018\u0002 \u0001(\tB(\n\u001eorg.bitcoin.protocols.paymentsB\u0006Protos"};
        Descriptors.FileDescriptor.InternalDescriptorAssigner assigner = new Descriptors.FileDescriptor.InternalDescriptorAssigner(){

            public ExtensionRegistry assignDescriptors(Descriptors.FileDescriptor root) {
                descriptor = root;
                return null;
            }
        };
        Descriptors.FileDescriptor.internalBuildGeneratedFileFrom((String[])descriptorData, (Descriptors.FileDescriptor[])new Descriptors.FileDescriptor[0], (Descriptors.FileDescriptor.InternalDescriptorAssigner)assigner);
        internal_static_payments_Output_descriptor = (Descriptors.Descriptor)Protos.getDescriptor().getMessageTypes().get(0);
        internal_static_payments_Output_fieldAccessorTable = new GeneratedMessage.FieldAccessorTable(internal_static_payments_Output_descriptor, new String[]{"Amount", "Script"});
        internal_static_payments_PaymentDetails_descriptor = (Descriptors.Descriptor)Protos.getDescriptor().getMessageTypes().get(1);
        internal_static_payments_PaymentDetails_fieldAccessorTable = new GeneratedMessage.FieldAccessorTable(internal_static_payments_PaymentDetails_descriptor, new String[]{"Network", "Outputs", "Time", "Expires", "Memo", "PaymentUrl", "MerchantData"});
        internal_static_payments_PaymentRequest_descriptor = (Descriptors.Descriptor)Protos.getDescriptor().getMessageTypes().get(2);
        internal_static_payments_PaymentRequest_fieldAccessorTable = new GeneratedMessage.FieldAccessorTable(internal_static_payments_PaymentRequest_descriptor, new String[]{"PaymentDetailsVersion", "PkiType", "PkiData", "SerializedPaymentDetails", "Signature"});
        internal_static_payments_X509Certificates_descriptor = (Descriptors.Descriptor)Protos.getDescriptor().getMessageTypes().get(3);
        internal_static_payments_X509Certificates_fieldAccessorTable = new GeneratedMessage.FieldAccessorTable(internal_static_payments_X509Certificates_descriptor, new String[]{"Certificate"});
        internal_static_payments_Payment_descriptor = (Descriptors.Descriptor)Protos.getDescriptor().getMessageTypes().get(4);
        internal_static_payments_Payment_fieldAccessorTable = new GeneratedMessage.FieldAccessorTable(internal_static_payments_Payment_descriptor, new String[]{"MerchantData", "Transactions", "RefundTo", "Memo"});
        internal_static_payments_PaymentACK_descriptor = (Descriptors.Descriptor)Protos.getDescriptor().getMessageTypes().get(5);
        internal_static_payments_PaymentACK_fieldAccessorTable = new GeneratedMessage.FieldAccessorTable(internal_static_payments_PaymentACK_descriptor, new String[]{"Payment", "Memo"});
    }

    public static final class PaymentACK
    extends GeneratedMessage
    implements PaymentACKOrBuilder {
        private static final PaymentACK defaultInstance;
        private final UnknownFieldSet unknownFields;
        public static Parser<PaymentACK> PARSER;
        private int bitField0_;
        public static final int PAYMENT_FIELD_NUMBER = 1;
        private Payment payment_;
        public static final int MEMO_FIELD_NUMBER = 2;
        private Object memo_;
        private byte memoizedIsInitialized;
        private int memoizedSerializedSize;
        private static final long serialVersionUID = 0L;

        private PaymentACK(GeneratedMessage.Builder<?> builder) {
            super(builder);
            this.memoizedIsInitialized = (byte)-1;
            this.memoizedSerializedSize = -1;
            this.unknownFields = builder.getUnknownFields();
        }

        private PaymentACK(boolean noInit) {
            this.memoizedIsInitialized = (byte)-1;
            this.memoizedSerializedSize = -1;
            this.unknownFields = UnknownFieldSet.getDefaultInstance();
        }

        public static PaymentACK getDefaultInstance() {
            return defaultInstance;
        }

        public PaymentACK getDefaultInstanceForType() {
            return defaultInstance;
        }

        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private PaymentACK(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                            Payment.Builder subBuilder = null;
                            if ((this.bitField0_ & 1) == 1) {
                                subBuilder = this.payment_.toBuilder();
                            }
                            this.payment_ = (Payment)input.readMessage(Payment.PARSER, extensionRegistry);
                            if (subBuilder != null) {
                                subBuilder.mergeFrom(this.payment_);
                                this.payment_ = subBuilder.buildPartial();
                            }
                            this.bitField0_ |= 1;
                            continue block11;
                        }
                        case 18: 
                    }
                    ByteString bs = input.readBytes();
                    this.bitField0_ |= 2;
                    this.memo_ = bs;
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
            return internal_static_payments_PaymentACK_descriptor;
        }

        protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
            return internal_static_payments_PaymentACK_fieldAccessorTable.ensureFieldAccessorsInitialized(PaymentACK.class, Builder.class);
        }

        public Parser<PaymentACK> getParserForType() {
            return PARSER;
        }

        @Override
        public boolean hasPayment() {
            return (this.bitField0_ & 1) == 1;
        }

        @Override
        public Payment getPayment() {
            return this.payment_;
        }

        @Override
        public PaymentOrBuilder getPaymentOrBuilder() {
            return this.payment_;
        }

        @Override
        public boolean hasMemo() {
            return (this.bitField0_ & 2) == 2;
        }

        @Override
        public String getMemo() {
            Object ref = this.memo_;
            if (ref instanceof String) {
                return (String)ref;
            }
            ByteString bs = (ByteString)ref;
            String s = bs.toStringUtf8();
            if (bs.isValidUtf8()) {
                this.memo_ = s;
            }
            return s;
        }

        @Override
        public ByteString getMemoBytes() {
            Object ref = this.memo_;
            if (ref instanceof String) {
                ByteString b = ByteString.copyFromUtf8((String)((String)ref));
                this.memo_ = b;
                return b;
            }
            return (ByteString)ref;
        }

        private void initFields() {
            this.payment_ = Payment.getDefaultInstance();
            this.memo_ = "";
        }

        public final boolean isInitialized() {
            byte isInitialized = this.memoizedIsInitialized;
            if (isInitialized == 1) {
                return true;
            }
            if (isInitialized == 0) {
                return false;
            }
            if (!this.hasPayment()) {
                this.memoizedIsInitialized = 0;
                return false;
            }
            if (!this.getPayment().isInitialized()) {
                this.memoizedIsInitialized = 0;
                return false;
            }
            this.memoizedIsInitialized = 1;
            return true;
        }

        public void writeTo(CodedOutputStream output) throws IOException {
            this.getSerializedSize();
            if ((this.bitField0_ & 1) == 1) {
                output.writeMessage(1, (MessageLite)this.payment_);
            }
            if ((this.bitField0_ & 2) == 2) {
                output.writeBytes(2, this.getMemoBytes());
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
                size += CodedOutputStream.computeMessageSize((int)1, (MessageLite)this.payment_);
            }
            if ((this.bitField0_ & 2) == 2) {
                size += CodedOutputStream.computeBytesSize((int)2, (ByteString)this.getMemoBytes());
            }
            this.memoizedSerializedSize = size += this.getUnknownFields().getSerializedSize();
            return size;
        }

        protected Object writeReplace() throws ObjectStreamException {
            return super.writeReplace();
        }

        public static PaymentACK parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (PaymentACK)PARSER.parseFrom(data);
        }

        public static PaymentACK parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (PaymentACK)PARSER.parseFrom(data, extensionRegistry);
        }

        public static PaymentACK parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (PaymentACK)PARSER.parseFrom(data);
        }

        public static PaymentACK parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (PaymentACK)PARSER.parseFrom(data, extensionRegistry);
        }

        public static PaymentACK parseFrom(InputStream input) throws IOException {
            return (PaymentACK)PARSER.parseFrom(input);
        }

        public static PaymentACK parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (PaymentACK)PARSER.parseFrom(input, extensionRegistry);
        }

        public static PaymentACK parseDelimitedFrom(InputStream input) throws IOException {
            return (PaymentACK)PARSER.parseDelimitedFrom(input);
        }

        public static PaymentACK parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (PaymentACK)PARSER.parseDelimitedFrom(input, extensionRegistry);
        }

        public static PaymentACK parseFrom(CodedInputStream input) throws IOException {
            return (PaymentACK)PARSER.parseFrom(input);
        }

        public static PaymentACK parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (PaymentACK)PARSER.parseFrom(input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return Builder.create();
        }

        public Builder newBuilderForType() {
            return PaymentACK.newBuilder();
        }

        public static Builder newBuilder(PaymentACK prototype) {
            return PaymentACK.newBuilder().mergeFrom(prototype);
        }

        public Builder toBuilder() {
            return PaymentACK.newBuilder(this);
        }

        protected Builder newBuilderForType(GeneratedMessage.BuilderParent parent) {
            Builder builder = new Builder(parent);
            return builder;
        }

        static {
            PARSER = new AbstractParser<PaymentACK>(){

                public PaymentACK parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                    return new PaymentACK(input, extensionRegistry);
                }
            };
            defaultInstance = new PaymentACK(true);
            defaultInstance.initFields();
        }

        public static final class Builder
        extends GeneratedMessage.Builder<Builder>
        implements PaymentACKOrBuilder {
            private int bitField0_;
            private Payment payment_ = Payment.getDefaultInstance();
            private SingleFieldBuilder<Payment, Payment.Builder, PaymentOrBuilder> paymentBuilder_;
            private Object memo_ = "";

            public static final Descriptors.Descriptor getDescriptor() {
                return internal_static_payments_PaymentACK_descriptor;
            }

            protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
                return internal_static_payments_PaymentACK_fieldAccessorTable.ensureFieldAccessorsInitialized(PaymentACK.class, Builder.class);
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
                    this.getPaymentFieldBuilder();
                }
            }

            private static Builder create() {
                return new Builder();
            }

            public Builder clear() {
                super.clear();
                if (this.paymentBuilder_ == null) {
                    this.payment_ = Payment.getDefaultInstance();
                } else {
                    this.paymentBuilder_.clear();
                }
                this.bitField0_ &= -2;
                this.memo_ = "";
                this.bitField0_ &= -3;
                return this;
            }

            public Builder clone() {
                return Builder.create().mergeFrom(this.buildPartial());
            }

            public Descriptors.Descriptor getDescriptorForType() {
                return internal_static_payments_PaymentACK_descriptor;
            }

            public PaymentACK getDefaultInstanceForType() {
                return PaymentACK.getDefaultInstance();
            }

            public PaymentACK build() {
                PaymentACK result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw Builder.newUninitializedMessageException((Message)result);
                }
                return result;
            }

            public PaymentACK buildPartial() {
                PaymentACK result = new PaymentACK(this);
                int from_bitField0_ = this.bitField0_;
                int to_bitField0_ = 0;
                if ((from_bitField0_ & 1) == 1) {
                    to_bitField0_ |= true;
                }
                if (this.paymentBuilder_ == null) {
                    result.payment_ = this.payment_;
                } else {
                    result.payment_ = (Payment)this.paymentBuilder_.build();
                }
                if ((from_bitField0_ & 2) == 2) {
                    to_bitField0_ |= 2;
                }
                result.memo_ = this.memo_;
                result.bitField0_ = to_bitField0_;
                this.onBuilt();
                return result;
            }

            public Builder mergeFrom(Message other) {
                if (other instanceof PaymentACK) {
                    return this.mergeFrom((PaymentACK)other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(PaymentACK other) {
                if (other == PaymentACK.getDefaultInstance()) {
                    return this;
                }
                if (other.hasPayment()) {
                    this.mergePayment(other.getPayment());
                }
                if (other.hasMemo()) {
                    this.bitField0_ |= 2;
                    this.memo_ = other.memo_;
                    this.onChanged();
                }
                this.mergeUnknownFields(other.getUnknownFields());
                return this;
            }

            public final boolean isInitialized() {
                if (!this.hasPayment()) {
                    return false;
                }
                if (!this.getPayment().isInitialized()) {
                    return false;
                }
                return true;
            }

            public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                PaymentACK parsedMessage = null;
                try {
                    parsedMessage = (PaymentACK)PaymentACK.PARSER.parsePartialFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e) {
                    parsedMessage = (PaymentACK)e.getUnfinishedMessage();
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
            public boolean hasPayment() {
                return (this.bitField0_ & 1) == 1;
            }

            @Override
            public Payment getPayment() {
                if (this.paymentBuilder_ == null) {
                    return this.payment_;
                }
                return (Payment)this.paymentBuilder_.getMessage();
            }

            public Builder setPayment(Payment value) {
                if (this.paymentBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.payment_ = value;
                    this.onChanged();
                } else {
                    this.paymentBuilder_.setMessage((GeneratedMessage)value);
                }
                this.bitField0_ |= 1;
                return this;
            }

            public Builder setPayment(Payment.Builder builderForValue) {
                if (this.paymentBuilder_ == null) {
                    this.payment_ = builderForValue.build();
                    this.onChanged();
                } else {
                    this.paymentBuilder_.setMessage((GeneratedMessage)builderForValue.build());
                }
                this.bitField0_ |= 1;
                return this;
            }

            public Builder mergePayment(Payment value) {
                if (this.paymentBuilder_ == null) {
                    this.payment_ = (this.bitField0_ & 1) == 1 && this.payment_ != Payment.getDefaultInstance() ? Payment.newBuilder(this.payment_).mergeFrom(value).buildPartial() : value;
                    this.onChanged();
                } else {
                    this.paymentBuilder_.mergeFrom((GeneratedMessage)value);
                }
                this.bitField0_ |= 1;
                return this;
            }

            public Builder clearPayment() {
                if (this.paymentBuilder_ == null) {
                    this.payment_ = Payment.getDefaultInstance();
                    this.onChanged();
                } else {
                    this.paymentBuilder_.clear();
                }
                this.bitField0_ &= -2;
                return this;
            }

            public Payment.Builder getPaymentBuilder() {
                this.bitField0_ |= 1;
                this.onChanged();
                return (Payment.Builder)this.getPaymentFieldBuilder().getBuilder();
            }

            @Override
            public PaymentOrBuilder getPaymentOrBuilder() {
                if (this.paymentBuilder_ != null) {
                    return (PaymentOrBuilder)this.paymentBuilder_.getMessageOrBuilder();
                }
                return this.payment_;
            }

            private SingleFieldBuilder<Payment, Payment.Builder, PaymentOrBuilder> getPaymentFieldBuilder() {
                if (this.paymentBuilder_ == null) {
                    this.paymentBuilder_ = new SingleFieldBuilder((GeneratedMessage)this.getPayment(), this.getParentForChildren(), this.isClean());
                    this.payment_ = null;
                }
                return this.paymentBuilder_;
            }

            @Override
            public boolean hasMemo() {
                return (this.bitField0_ & 2) == 2;
            }

            @Override
            public String getMemo() {
                Object ref = this.memo_;
                if (!(ref instanceof String)) {
                    ByteString bs = (ByteString)ref;
                    String s = bs.toStringUtf8();
                    if (bs.isValidUtf8()) {
                        this.memo_ = s;
                    }
                    return s;
                }
                return (String)ref;
            }

            @Override
            public ByteString getMemoBytes() {
                Object ref = this.memo_;
                if (ref instanceof String) {
                    ByteString b = ByteString.copyFromUtf8((String)((String)ref));
                    this.memo_ = b;
                    return b;
                }
                return (ByteString)ref;
            }

            public Builder setMemo(String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 2;
                this.memo_ = value;
                this.onChanged();
                return this;
            }

            public Builder clearMemo() {
                this.bitField0_ &= -3;
                this.memo_ = PaymentACK.getDefaultInstance().getMemo();
                this.onChanged();
                return this;
            }

            public Builder setMemoBytes(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 2;
                this.memo_ = value;
                this.onChanged();
                return this;
            }
        }

    }

    public static interface PaymentACKOrBuilder
    extends MessageOrBuilder {
        public boolean hasPayment();

        public Payment getPayment();

        public PaymentOrBuilder getPaymentOrBuilder();

        public boolean hasMemo();

        public String getMemo();

        public ByteString getMemoBytes();
    }

    public static final class Payment
    extends GeneratedMessage
    implements PaymentOrBuilder {
        private static final Payment defaultInstance;
        private final UnknownFieldSet unknownFields;
        public static Parser<Payment> PARSER;
        private int bitField0_;
        public static final int MERCHANT_DATA_FIELD_NUMBER = 1;
        private ByteString merchantData_;
        public static final int TRANSACTIONS_FIELD_NUMBER = 2;
        private List<ByteString> transactions_;
        public static final int REFUND_TO_FIELD_NUMBER = 3;
        private List<Output> refundTo_;
        public static final int MEMO_FIELD_NUMBER = 4;
        private Object memo_;
        private byte memoizedIsInitialized;
        private int memoizedSerializedSize;
        private static final long serialVersionUID = 0L;

        private Payment(GeneratedMessage.Builder<?> builder) {
            super(builder);
            this.memoizedIsInitialized = (byte)-1;
            this.memoizedSerializedSize = -1;
            this.unknownFields = builder.getUnknownFields();
        }

        private Payment(boolean noInit) {
            this.memoizedIsInitialized = (byte)-1;
            this.memoizedSerializedSize = -1;
            this.unknownFields = UnknownFieldSet.getDefaultInstance();
        }

        public static Payment getDefaultInstance() {
            return defaultInstance;
        }

        public Payment getDefaultInstanceForType() {
            return defaultInstance;
        }

        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private Payment(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            this.memoizedIsInitialized = (byte)-1;
            this.memoizedSerializedSize = -1;
            this.initFields();
            int mutable_bitField0_ = 0;
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
                            this.merchantData_ = input.readBytes();
                            continue block13;
                        }
                        case 18: {
                            if ((mutable_bitField0_ & 2) != 2) {
                                this.transactions_ = new ArrayList<ByteString>();
                                mutable_bitField0_ |= 2;
                            }
                            this.transactions_.add(input.readBytes());
                            continue block13;
                        }
                        case 26: {
                            if ((mutable_bitField0_ & 4) != 4) {
                                this.refundTo_ = new ArrayList<Output>();
                                mutable_bitField0_ |= 4;
                            }
                            this.refundTo_.add((Output)input.readMessage(Output.PARSER, extensionRegistry));
                            continue block13;
                        }
                        case 34: 
                    }
                    ByteString bs = input.readBytes();
                    this.bitField0_ |= 2;
                    this.memo_ = bs;
                }
            }
            catch (InvalidProtocolBufferException e) {
                throw e.setUnfinishedMessage((MessageLite)this);
            }
            catch (IOException e) {
                throw new InvalidProtocolBufferException(e.getMessage()).setUnfinishedMessage((MessageLite)this);
            }
            finally {
                if ((mutable_bitField0_ & 2) == 2) {
                    this.transactions_ = Collections.unmodifiableList(this.transactions_);
                }
                if ((mutable_bitField0_ & 4) == 4) {
                    this.refundTo_ = Collections.unmodifiableList(this.refundTo_);
                }
                this.unknownFields = unknownFields.build();
                this.makeExtensionsImmutable();
            }
        }

        public static final Descriptors.Descriptor getDescriptor() {
            return internal_static_payments_Payment_descriptor;
        }

        protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
            return internal_static_payments_Payment_fieldAccessorTable.ensureFieldAccessorsInitialized(Payment.class, Builder.class);
        }

        public Parser<Payment> getParserForType() {
            return PARSER;
        }

        @Override
        public boolean hasMerchantData() {
            return (this.bitField0_ & 1) == 1;
        }

        @Override
        public ByteString getMerchantData() {
            return this.merchantData_;
        }

        @Override
        public List<ByteString> getTransactionsList() {
            return this.transactions_;
        }

        @Override
        public int getTransactionsCount() {
            return this.transactions_.size();
        }

        @Override
        public ByteString getTransactions(int index) {
            return this.transactions_.get(index);
        }

        @Override
        public List<Output> getRefundToList() {
            return this.refundTo_;
        }

        @Override
        public List<? extends OutputOrBuilder> getRefundToOrBuilderList() {
            return this.refundTo_;
        }

        @Override
        public int getRefundToCount() {
            return this.refundTo_.size();
        }

        @Override
        public Output getRefundTo(int index) {
            return this.refundTo_.get(index);
        }

        @Override
        public OutputOrBuilder getRefundToOrBuilder(int index) {
            return this.refundTo_.get(index);
        }

        @Override
        public boolean hasMemo() {
            return (this.bitField0_ & 2) == 2;
        }

        @Override
        public String getMemo() {
            Object ref = this.memo_;
            if (ref instanceof String) {
                return (String)ref;
            }
            ByteString bs = (ByteString)ref;
            String s = bs.toStringUtf8();
            if (bs.isValidUtf8()) {
                this.memo_ = s;
            }
            return s;
        }

        @Override
        public ByteString getMemoBytes() {
            Object ref = this.memo_;
            if (ref instanceof String) {
                ByteString b = ByteString.copyFromUtf8((String)((String)ref));
                this.memo_ = b;
                return b;
            }
            return (ByteString)ref;
        }

        private void initFields() {
            this.merchantData_ = ByteString.EMPTY;
            this.transactions_ = Collections.emptyList();
            this.refundTo_ = Collections.emptyList();
            this.memo_ = "";
        }

        public final boolean isInitialized() {
            byte isInitialized = this.memoizedIsInitialized;
            if (isInitialized == 1) {
                return true;
            }
            if (isInitialized == 0) {
                return false;
            }
            for (int i = 0; i < this.getRefundToCount(); ++i) {
                if (this.getRefundTo(i).isInitialized()) continue;
                this.memoizedIsInitialized = 0;
                return false;
            }
            this.memoizedIsInitialized = 1;
            return true;
        }

        public void writeTo(CodedOutputStream output) throws IOException {
            int i;
            this.getSerializedSize();
            if ((this.bitField0_ & 1) == 1) {
                output.writeBytes(1, this.merchantData_);
            }
            for (i = 0; i < this.transactions_.size(); ++i) {
                output.writeBytes(2, this.transactions_.get(i));
            }
            for (i = 0; i < this.refundTo_.size(); ++i) {
                output.writeMessage(3, (MessageLite)this.refundTo_.get(i));
            }
            if ((this.bitField0_ & 2) == 2) {
                output.writeBytes(4, this.getMemoBytes());
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
                size += CodedOutputStream.computeBytesSize((int)1, (ByteString)this.merchantData_);
            }
            int dataSize = 0;
            for (int i = 0; i < this.transactions_.size(); ++i) {
                dataSize += CodedOutputStream.computeBytesSizeNoTag((ByteString)this.transactions_.get(i));
            }
            size += dataSize;
            size += 1 * this.getTransactionsList().size();
            for (int i = 0; i < this.refundTo_.size(); ++i) {
                size += CodedOutputStream.computeMessageSize((int)3, (MessageLite)((MessageLite)this.refundTo_.get(i)));
            }
            if ((this.bitField0_ & 2) == 2) {
                size += CodedOutputStream.computeBytesSize((int)4, (ByteString)this.getMemoBytes());
            }
            this.memoizedSerializedSize = size += this.getUnknownFields().getSerializedSize();
            return size;
        }

        protected Object writeReplace() throws ObjectStreamException {
            return super.writeReplace();
        }

        public static Payment parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (Payment)PARSER.parseFrom(data);
        }

        public static Payment parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (Payment)PARSER.parseFrom(data, extensionRegistry);
        }

        public static Payment parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (Payment)PARSER.parseFrom(data);
        }

        public static Payment parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (Payment)PARSER.parseFrom(data, extensionRegistry);
        }

        public static Payment parseFrom(InputStream input) throws IOException {
            return (Payment)PARSER.parseFrom(input);
        }

        public static Payment parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (Payment)PARSER.parseFrom(input, extensionRegistry);
        }

        public static Payment parseDelimitedFrom(InputStream input) throws IOException {
            return (Payment)PARSER.parseDelimitedFrom(input);
        }

        public static Payment parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (Payment)PARSER.parseDelimitedFrom(input, extensionRegistry);
        }

        public static Payment parseFrom(CodedInputStream input) throws IOException {
            return (Payment)PARSER.parseFrom(input);
        }

        public static Payment parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (Payment)PARSER.parseFrom(input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return Builder.create();
        }

        public Builder newBuilderForType() {
            return Payment.newBuilder();
        }

        public static Builder newBuilder(Payment prototype) {
            return Payment.newBuilder().mergeFrom(prototype);
        }

        public Builder toBuilder() {
            return Payment.newBuilder(this);
        }

        protected Builder newBuilderForType(GeneratedMessage.BuilderParent parent) {
            Builder builder = new Builder(parent);
            return builder;
        }

        static {
            PARSER = new AbstractParser<Payment>(){

                public Payment parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                    return new Payment(input, extensionRegistry);
                }
            };
            defaultInstance = new Payment(true);
            defaultInstance.initFields();
        }

        public static final class Builder
        extends GeneratedMessage.Builder<Builder>
        implements PaymentOrBuilder {
            private int bitField0_;
            private ByteString merchantData_ = ByteString.EMPTY;
            private List<ByteString> transactions_ = Collections.emptyList();
            private List<Output> refundTo_ = Collections.emptyList();
            private RepeatedFieldBuilder<Output, Output.Builder, OutputOrBuilder> refundToBuilder_;
            private Object memo_ = "";

            public static final Descriptors.Descriptor getDescriptor() {
                return internal_static_payments_Payment_descriptor;
            }

            protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
                return internal_static_payments_Payment_fieldAccessorTable.ensureFieldAccessorsInitialized(Payment.class, Builder.class);
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
                    this.getRefundToFieldBuilder();
                }
            }

            private static Builder create() {
                return new Builder();
            }

            public Builder clear() {
                super.clear();
                this.merchantData_ = ByteString.EMPTY;
                this.bitField0_ &= -2;
                this.transactions_ = Collections.emptyList();
                this.bitField0_ &= -3;
                if (this.refundToBuilder_ == null) {
                    this.refundTo_ = Collections.emptyList();
                    this.bitField0_ &= -5;
                } else {
                    this.refundToBuilder_.clear();
                }
                this.memo_ = "";
                this.bitField0_ &= -9;
                return this;
            }

            public Builder clone() {
                return Builder.create().mergeFrom(this.buildPartial());
            }

            public Descriptors.Descriptor getDescriptorForType() {
                return internal_static_payments_Payment_descriptor;
            }

            public Payment getDefaultInstanceForType() {
                return Payment.getDefaultInstance();
            }

            public Payment build() {
                Payment result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw Builder.newUninitializedMessageException((Message)result);
                }
                return result;
            }

            public Payment buildPartial() {
                Payment result = new Payment(this);
                int from_bitField0_ = this.bitField0_;
                int to_bitField0_ = 0;
                if ((from_bitField0_ & 1) == 1) {
                    to_bitField0_ |= true;
                }
                result.merchantData_ = this.merchantData_;
                if ((this.bitField0_ & 2) == 2) {
                    this.transactions_ = Collections.unmodifiableList(this.transactions_);
                    this.bitField0_ &= -3;
                }
                result.transactions_ = this.transactions_;
                if (this.refundToBuilder_ == null) {
                    if ((this.bitField0_ & 4) == 4) {
                        this.refundTo_ = Collections.unmodifiableList(this.refundTo_);
                        this.bitField0_ &= -5;
                    }
                    result.refundTo_ = this.refundTo_;
                } else {
                    result.refundTo_ = this.refundToBuilder_.build();
                }
                if ((from_bitField0_ & 8) == 8) {
                    to_bitField0_ |= 2;
                }
                result.memo_ = this.memo_;
                result.bitField0_ = to_bitField0_;
                this.onBuilt();
                return result;
            }

            public Builder mergeFrom(Message other) {
                if (other instanceof Payment) {
                    return this.mergeFrom((Payment)other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(Payment other) {
                if (other == Payment.getDefaultInstance()) {
                    return this;
                }
                if (other.hasMerchantData()) {
                    this.setMerchantData(other.getMerchantData());
                }
                if (!other.transactions_.isEmpty()) {
                    if (this.transactions_.isEmpty()) {
                        this.transactions_ = other.transactions_;
                        this.bitField0_ &= -3;
                    } else {
                        this.ensureTransactionsIsMutable();
                        this.transactions_.addAll(other.transactions_);
                    }
                    this.onChanged();
                }
                if (this.refundToBuilder_ == null) {
                    if (!other.refundTo_.isEmpty()) {
                        if (this.refundTo_.isEmpty()) {
                            this.refundTo_ = other.refundTo_;
                            this.bitField0_ &= -5;
                        } else {
                            this.ensureRefundToIsMutable();
                            this.refundTo_.addAll(other.refundTo_);
                        }
                        this.onChanged();
                    }
                } else if (!other.refundTo_.isEmpty()) {
                    if (this.refundToBuilder_.isEmpty()) {
                        this.refundToBuilder_.dispose();
                        this.refundToBuilder_ = null;
                        this.refundTo_ = other.refundTo_;
                        this.bitField0_ &= -5;
                        this.refundToBuilder_ = alwaysUseFieldBuilders ? this.getRefundToFieldBuilder() : null;
                    } else {
                        this.refundToBuilder_.addAllMessages((Iterable)other.refundTo_);
                    }
                }
                if (other.hasMemo()) {
                    this.bitField0_ |= 8;
                    this.memo_ = other.memo_;
                    this.onChanged();
                }
                this.mergeUnknownFields(other.getUnknownFields());
                return this;
            }

            public final boolean isInitialized() {
                for (int i = 0; i < this.getRefundToCount(); ++i) {
                    if (this.getRefundTo(i).isInitialized()) continue;
                    return false;
                }
                return true;
            }

            public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                Payment parsedMessage = null;
                try {
                    parsedMessage = (Payment)Payment.PARSER.parsePartialFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e) {
                    parsedMessage = (Payment)e.getUnfinishedMessage();
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
            public boolean hasMerchantData() {
                return (this.bitField0_ & 1) == 1;
            }

            @Override
            public ByteString getMerchantData() {
                return this.merchantData_;
            }

            public Builder setMerchantData(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 1;
                this.merchantData_ = value;
                this.onChanged();
                return this;
            }

            public Builder clearMerchantData() {
                this.bitField0_ &= -2;
                this.merchantData_ = Payment.getDefaultInstance().getMerchantData();
                this.onChanged();
                return this;
            }

            private void ensureTransactionsIsMutable() {
                if ((this.bitField0_ & 2) != 2) {
                    this.transactions_ = new ArrayList<ByteString>(this.transactions_);
                    this.bitField0_ |= 2;
                }
            }

            @Override
            public List<ByteString> getTransactionsList() {
                return Collections.unmodifiableList(this.transactions_);
            }

            @Override
            public int getTransactionsCount() {
                return this.transactions_.size();
            }

            @Override
            public ByteString getTransactions(int index) {
                return this.transactions_.get(index);
            }

            public Builder setTransactions(int index, ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.ensureTransactionsIsMutable();
                this.transactions_.set(index, value);
                this.onChanged();
                return this;
            }

            public Builder addTransactions(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.ensureTransactionsIsMutable();
                this.transactions_.add(value);
                this.onChanged();
                return this;
            }

            public Builder addAllTransactions(Iterable<? extends ByteString> values) {
                this.ensureTransactionsIsMutable();
                AbstractMessageLite.Builder.addAll(values, this.transactions_);
                this.onChanged();
                return this;
            }

            public Builder clearTransactions() {
                this.transactions_ = Collections.emptyList();
                this.bitField0_ &= -3;
                this.onChanged();
                return this;
            }

            private void ensureRefundToIsMutable() {
                if ((this.bitField0_ & 4) != 4) {
                    this.refundTo_ = new ArrayList<Output>(this.refundTo_);
                    this.bitField0_ |= 4;
                }
            }

            @Override
            public List<Output> getRefundToList() {
                if (this.refundToBuilder_ == null) {
                    return Collections.unmodifiableList(this.refundTo_);
                }
                return this.refundToBuilder_.getMessageList();
            }

            @Override
            public int getRefundToCount() {
                if (this.refundToBuilder_ == null) {
                    return this.refundTo_.size();
                }
                return this.refundToBuilder_.getCount();
            }

            @Override
            public Output getRefundTo(int index) {
                if (this.refundToBuilder_ == null) {
                    return this.refundTo_.get(index);
                }
                return (Output)this.refundToBuilder_.getMessage(index);
            }

            public Builder setRefundTo(int index, Output value) {
                if (this.refundToBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.ensureRefundToIsMutable();
                    this.refundTo_.set(index, value);
                    this.onChanged();
                } else {
                    this.refundToBuilder_.setMessage(index, (GeneratedMessage)value);
                }
                return this;
            }

            public Builder setRefundTo(int index, Output.Builder builderForValue) {
                if (this.refundToBuilder_ == null) {
                    this.ensureRefundToIsMutable();
                    this.refundTo_.set(index, builderForValue.build());
                    this.onChanged();
                } else {
                    this.refundToBuilder_.setMessage(index, (GeneratedMessage)builderForValue.build());
                }
                return this;
            }

            public Builder addRefundTo(Output value) {
                if (this.refundToBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.ensureRefundToIsMutable();
                    this.refundTo_.add(value);
                    this.onChanged();
                } else {
                    this.refundToBuilder_.addMessage((GeneratedMessage)value);
                }
                return this;
            }

            public Builder addRefundTo(int index, Output value) {
                if (this.refundToBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.ensureRefundToIsMutable();
                    this.refundTo_.add(index, value);
                    this.onChanged();
                } else {
                    this.refundToBuilder_.addMessage(index, (GeneratedMessage)value);
                }
                return this;
            }

            public Builder addRefundTo(Output.Builder builderForValue) {
                if (this.refundToBuilder_ == null) {
                    this.ensureRefundToIsMutable();
                    this.refundTo_.add(builderForValue.build());
                    this.onChanged();
                } else {
                    this.refundToBuilder_.addMessage((GeneratedMessage)builderForValue.build());
                }
                return this;
            }

            public Builder addRefundTo(int index, Output.Builder builderForValue) {
                if (this.refundToBuilder_ == null) {
                    this.ensureRefundToIsMutable();
                    this.refundTo_.add(index, builderForValue.build());
                    this.onChanged();
                } else {
                    this.refundToBuilder_.addMessage(index, (GeneratedMessage)builderForValue.build());
                }
                return this;
            }

            public Builder addAllRefundTo(Iterable<? extends Output> values) {
                if (this.refundToBuilder_ == null) {
                    this.ensureRefundToIsMutable();
                    AbstractMessageLite.Builder.addAll(values, this.refundTo_);
                    this.onChanged();
                } else {
                    this.refundToBuilder_.addAllMessages(values);
                }
                return this;
            }

            public Builder clearRefundTo() {
                if (this.refundToBuilder_ == null) {
                    this.refundTo_ = Collections.emptyList();
                    this.bitField0_ &= -5;
                    this.onChanged();
                } else {
                    this.refundToBuilder_.clear();
                }
                return this;
            }

            public Builder removeRefundTo(int index) {
                if (this.refundToBuilder_ == null) {
                    this.ensureRefundToIsMutable();
                    this.refundTo_.remove(index);
                    this.onChanged();
                } else {
                    this.refundToBuilder_.remove(index);
                }
                return this;
            }

            public Output.Builder getRefundToBuilder(int index) {
                return (Output.Builder)this.getRefundToFieldBuilder().getBuilder(index);
            }

            @Override
            public OutputOrBuilder getRefundToOrBuilder(int index) {
                if (this.refundToBuilder_ == null) {
                    return this.refundTo_.get(index);
                }
                return (OutputOrBuilder)this.refundToBuilder_.getMessageOrBuilder(index);
            }

            @Override
            public List<? extends OutputOrBuilder> getRefundToOrBuilderList() {
                if (this.refundToBuilder_ != null) {
                    return this.refundToBuilder_.getMessageOrBuilderList();
                }
                return Collections.unmodifiableList(this.refundTo_);
            }

            public Output.Builder addRefundToBuilder() {
                return (Output.Builder)this.getRefundToFieldBuilder().addBuilder((GeneratedMessage)Output.getDefaultInstance());
            }

            public Output.Builder addRefundToBuilder(int index) {
                return (Output.Builder)this.getRefundToFieldBuilder().addBuilder(index, (GeneratedMessage)Output.getDefaultInstance());
            }

            public List<Output.Builder> getRefundToBuilderList() {
                return this.getRefundToFieldBuilder().getBuilderList();
            }

            private RepeatedFieldBuilder<Output, Output.Builder, OutputOrBuilder> getRefundToFieldBuilder() {
                if (this.refundToBuilder_ == null) {
                    this.refundToBuilder_ = new RepeatedFieldBuilder(this.refundTo_, (this.bitField0_ & 4) == 4, this.getParentForChildren(), this.isClean());
                    this.refundTo_ = null;
                }
                return this.refundToBuilder_;
            }

            @Override
            public boolean hasMemo() {
                return (this.bitField0_ & 8) == 8;
            }

            @Override
            public String getMemo() {
                Object ref = this.memo_;
                if (!(ref instanceof String)) {
                    ByteString bs = (ByteString)ref;
                    String s = bs.toStringUtf8();
                    if (bs.isValidUtf8()) {
                        this.memo_ = s;
                    }
                    return s;
                }
                return (String)ref;
            }

            @Override
            public ByteString getMemoBytes() {
                Object ref = this.memo_;
                if (ref instanceof String) {
                    ByteString b = ByteString.copyFromUtf8((String)((String)ref));
                    this.memo_ = b;
                    return b;
                }
                return (ByteString)ref;
            }

            public Builder setMemo(String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 8;
                this.memo_ = value;
                this.onChanged();
                return this;
            }

            public Builder clearMemo() {
                this.bitField0_ &= -9;
                this.memo_ = Payment.getDefaultInstance().getMemo();
                this.onChanged();
                return this;
            }

            public Builder setMemoBytes(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 8;
                this.memo_ = value;
                this.onChanged();
                return this;
            }
        }

    }

    public static interface PaymentOrBuilder
    extends MessageOrBuilder {
        public boolean hasMerchantData();

        public ByteString getMerchantData();

        public List<ByteString> getTransactionsList();

        public int getTransactionsCount();

        public ByteString getTransactions(int var1);

        public List<Output> getRefundToList();

        public Output getRefundTo(int var1);

        public int getRefundToCount();

        public List<? extends OutputOrBuilder> getRefundToOrBuilderList();

        public OutputOrBuilder getRefundToOrBuilder(int var1);

        public boolean hasMemo();

        public String getMemo();

        public ByteString getMemoBytes();
    }

    public static final class X509Certificates
    extends GeneratedMessage
    implements X509CertificatesOrBuilder {
        private static final X509Certificates defaultInstance;
        private final UnknownFieldSet unknownFields;
        public static Parser<X509Certificates> PARSER;
        public static final int CERTIFICATE_FIELD_NUMBER = 1;
        private List<ByteString> certificate_;
        private byte memoizedIsInitialized;
        private int memoizedSerializedSize;
        private static final long serialVersionUID = 0L;

        private X509Certificates(GeneratedMessage.Builder<?> builder) {
            super(builder);
            this.memoizedIsInitialized = (byte)-1;
            this.memoizedSerializedSize = -1;
            this.unknownFields = builder.getUnknownFields();
        }

        private X509Certificates(boolean noInit) {
            this.memoizedIsInitialized = (byte)-1;
            this.memoizedSerializedSize = -1;
            this.unknownFields = UnknownFieldSet.getDefaultInstance();
        }

        public static X509Certificates getDefaultInstance() {
            return defaultInstance;
        }

        public X509Certificates getDefaultInstanceForType() {
            return defaultInstance;
        }

        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private X509Certificates(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                    if (!(mutable_bitField0_ & true)) {
                        this.certificate_ = new ArrayList<ByteString>();
                        mutable_bitField0_ |= true;
                    }
                    this.certificate_.add(input.readBytes());
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
                    this.certificate_ = Collections.unmodifiableList(this.certificate_);
                }
                this.unknownFields = unknownFields.build();
                this.makeExtensionsImmutable();
            }
        }

        public static final Descriptors.Descriptor getDescriptor() {
            return internal_static_payments_X509Certificates_descriptor;
        }

        protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
            return internal_static_payments_X509Certificates_fieldAccessorTable.ensureFieldAccessorsInitialized(X509Certificates.class, Builder.class);
        }

        public Parser<X509Certificates> getParserForType() {
            return PARSER;
        }

        @Override
        public List<ByteString> getCertificateList() {
            return this.certificate_;
        }

        @Override
        public int getCertificateCount() {
            return this.certificate_.size();
        }

        @Override
        public ByteString getCertificate(int index) {
            return this.certificate_.get(index);
        }

        private void initFields() {
            this.certificate_ = Collections.emptyList();
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
            for (int i = 0; i < this.certificate_.size(); ++i) {
                output.writeBytes(1, this.certificate_.get(i));
            }
            this.getUnknownFields().writeTo(output);
        }

        public int getSerializedSize() {
            int size = this.memoizedSerializedSize;
            if (size != -1) {
                return size;
            }
            size = 0;
            int dataSize = 0;
            for (int i = 0; i < this.certificate_.size(); ++i) {
                dataSize += CodedOutputStream.computeBytesSizeNoTag((ByteString)this.certificate_.get(i));
            }
            size += dataSize;
            size += 1 * this.getCertificateList().size();
            this.memoizedSerializedSize = size += this.getUnknownFields().getSerializedSize();
            return size;
        }

        protected Object writeReplace() throws ObjectStreamException {
            return super.writeReplace();
        }

        public static X509Certificates parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (X509Certificates)PARSER.parseFrom(data);
        }

        public static X509Certificates parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (X509Certificates)PARSER.parseFrom(data, extensionRegistry);
        }

        public static X509Certificates parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (X509Certificates)PARSER.parseFrom(data);
        }

        public static X509Certificates parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (X509Certificates)PARSER.parseFrom(data, extensionRegistry);
        }

        public static X509Certificates parseFrom(InputStream input) throws IOException {
            return (X509Certificates)PARSER.parseFrom(input);
        }

        public static X509Certificates parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (X509Certificates)PARSER.parseFrom(input, extensionRegistry);
        }

        public static X509Certificates parseDelimitedFrom(InputStream input) throws IOException {
            return (X509Certificates)PARSER.parseDelimitedFrom(input);
        }

        public static X509Certificates parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (X509Certificates)PARSER.parseDelimitedFrom(input, extensionRegistry);
        }

        public static X509Certificates parseFrom(CodedInputStream input) throws IOException {
            return (X509Certificates)PARSER.parseFrom(input);
        }

        public static X509Certificates parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (X509Certificates)PARSER.parseFrom(input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return Builder.create();
        }

        public Builder newBuilderForType() {
            return X509Certificates.newBuilder();
        }

        public static Builder newBuilder(X509Certificates prototype) {
            return X509Certificates.newBuilder().mergeFrom(prototype);
        }

        public Builder toBuilder() {
            return X509Certificates.newBuilder(this);
        }

        protected Builder newBuilderForType(GeneratedMessage.BuilderParent parent) {
            Builder builder = new Builder(parent);
            return builder;
        }

        static {
            PARSER = new AbstractParser<X509Certificates>(){

                public X509Certificates parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                    return new X509Certificates(input, extensionRegistry);
                }
            };
            defaultInstance = new X509Certificates(true);
            defaultInstance.initFields();
        }

        public static final class Builder
        extends GeneratedMessage.Builder<Builder>
        implements X509CertificatesOrBuilder {
            private int bitField0_;
            private List<ByteString> certificate_ = Collections.emptyList();

            public static final Descriptors.Descriptor getDescriptor() {
                return internal_static_payments_X509Certificates_descriptor;
            }

            protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
                return internal_static_payments_X509Certificates_fieldAccessorTable.ensureFieldAccessorsInitialized(X509Certificates.class, Builder.class);
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
                this.certificate_ = Collections.emptyList();
                this.bitField0_ &= -2;
                return this;
            }

            public Builder clone() {
                return Builder.create().mergeFrom(this.buildPartial());
            }

            public Descriptors.Descriptor getDescriptorForType() {
                return internal_static_payments_X509Certificates_descriptor;
            }

            public X509Certificates getDefaultInstanceForType() {
                return X509Certificates.getDefaultInstance();
            }

            public X509Certificates build() {
                X509Certificates result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw Builder.newUninitializedMessageException((Message)result);
                }
                return result;
            }

            public X509Certificates buildPartial() {
                X509Certificates result = new X509Certificates(this);
                int from_bitField0_ = this.bitField0_;
                if ((this.bitField0_ & 1) == 1) {
                    this.certificate_ = Collections.unmodifiableList(this.certificate_);
                    this.bitField0_ &= -2;
                }
                result.certificate_ = this.certificate_;
                this.onBuilt();
                return result;
            }

            public Builder mergeFrom(Message other) {
                if (other instanceof X509Certificates) {
                    return this.mergeFrom((X509Certificates)other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(X509Certificates other) {
                if (other == X509Certificates.getDefaultInstance()) {
                    return this;
                }
                if (!other.certificate_.isEmpty()) {
                    if (this.certificate_.isEmpty()) {
                        this.certificate_ = other.certificate_;
                        this.bitField0_ &= -2;
                    } else {
                        this.ensureCertificateIsMutable();
                        this.certificate_.addAll(other.certificate_);
                    }
                    this.onChanged();
                }
                this.mergeUnknownFields(other.getUnknownFields());
                return this;
            }

            public final boolean isInitialized() {
                return true;
            }

            public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                X509Certificates parsedMessage = null;
                try {
                    parsedMessage = (X509Certificates)X509Certificates.PARSER.parsePartialFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e) {
                    parsedMessage = (X509Certificates)e.getUnfinishedMessage();
                    throw e;
                }
                finally {
                    if (parsedMessage != null) {
                        this.mergeFrom(parsedMessage);
                    }
                }
                return this;
            }

            private void ensureCertificateIsMutable() {
                if ((this.bitField0_ & 1) != 1) {
                    this.certificate_ = new ArrayList<ByteString>(this.certificate_);
                    this.bitField0_ |= 1;
                }
            }

            @Override
            public List<ByteString> getCertificateList() {
                return Collections.unmodifiableList(this.certificate_);
            }

            @Override
            public int getCertificateCount() {
                return this.certificate_.size();
            }

            @Override
            public ByteString getCertificate(int index) {
                return this.certificate_.get(index);
            }

            public Builder setCertificate(int index, ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.ensureCertificateIsMutable();
                this.certificate_.set(index, value);
                this.onChanged();
                return this;
            }

            public Builder addCertificate(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.ensureCertificateIsMutable();
                this.certificate_.add(value);
                this.onChanged();
                return this;
            }

            public Builder addAllCertificate(Iterable<? extends ByteString> values) {
                this.ensureCertificateIsMutable();
                AbstractMessageLite.Builder.addAll(values, this.certificate_);
                this.onChanged();
                return this;
            }

            public Builder clearCertificate() {
                this.certificate_ = Collections.emptyList();
                this.bitField0_ &= -2;
                this.onChanged();
                return this;
            }
        }

    }

    public static interface X509CertificatesOrBuilder
    extends MessageOrBuilder {
        public List<ByteString> getCertificateList();

        public int getCertificateCount();

        public ByteString getCertificate(int var1);
    }

    public static final class PaymentRequest
    extends GeneratedMessage
    implements PaymentRequestOrBuilder {
        private static final PaymentRequest defaultInstance;
        private final UnknownFieldSet unknownFields;
        public static Parser<PaymentRequest> PARSER;
        private int bitField0_;
        public static final int PAYMENT_DETAILS_VERSION_FIELD_NUMBER = 1;
        private int paymentDetailsVersion_;
        public static final int PKI_TYPE_FIELD_NUMBER = 2;
        private Object pkiType_;
        public static final int PKI_DATA_FIELD_NUMBER = 3;
        private ByteString pkiData_;
        public static final int SERIALIZED_PAYMENT_DETAILS_FIELD_NUMBER = 4;
        private ByteString serializedPaymentDetails_;
        public static final int SIGNATURE_FIELD_NUMBER = 5;
        private ByteString signature_;
        private byte memoizedIsInitialized;
        private int memoizedSerializedSize;
        private static final long serialVersionUID = 0L;

        private PaymentRequest(GeneratedMessage.Builder<?> builder) {
            super(builder);
            this.memoizedIsInitialized = (byte)-1;
            this.memoizedSerializedSize = -1;
            this.unknownFields = builder.getUnknownFields();
        }

        private PaymentRequest(boolean noInit) {
            this.memoizedIsInitialized = (byte)-1;
            this.memoizedSerializedSize = -1;
            this.unknownFields = UnknownFieldSet.getDefaultInstance();
        }

        public static PaymentRequest getDefaultInstance() {
            return defaultInstance;
        }

        public PaymentRequest getDefaultInstanceForType() {
            return defaultInstance;
        }

        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private PaymentRequest(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            this.memoizedIsInitialized = (byte)-1;
            this.memoizedSerializedSize = -1;
            this.initFields();
            boolean mutable_bitField0_ = false;
            UnknownFieldSet.Builder unknownFields = UnknownFieldSet.newBuilder();
            try {
                boolean done = false;
                block14 : while (!done) {
                    int tag = input.readTag();
                    switch (tag) {
                        case 0: {
                            done = true;
                            continue block14;
                        }
                        default: {
                            if (this.parseUnknownField(input, unknownFields, extensionRegistry, tag)) continue block14;
                            done = true;
                            continue block14;
                        }
                        case 8: {
                            this.bitField0_ |= 1;
                            this.paymentDetailsVersion_ = input.readUInt32();
                            continue block14;
                        }
                        case 18: {
                            ByteString bs = input.readBytes();
                            this.bitField0_ |= 2;
                            this.pkiType_ = bs;
                            continue block14;
                        }
                        case 26: {
                            this.bitField0_ |= 4;
                            this.pkiData_ = input.readBytes();
                            continue block14;
                        }
                        case 34: {
                            this.bitField0_ |= 8;
                            this.serializedPaymentDetails_ = input.readBytes();
                            continue block14;
                        }
                        case 42: 
                    }
                    this.bitField0_ |= 16;
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
            return internal_static_payments_PaymentRequest_descriptor;
        }

        protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
            return internal_static_payments_PaymentRequest_fieldAccessorTable.ensureFieldAccessorsInitialized(PaymentRequest.class, Builder.class);
        }

        public Parser<PaymentRequest> getParserForType() {
            return PARSER;
        }

        @Override
        public boolean hasPaymentDetailsVersion() {
            return (this.bitField0_ & 1) == 1;
        }

        @Override
        public int getPaymentDetailsVersion() {
            return this.paymentDetailsVersion_;
        }

        @Override
        public boolean hasPkiType() {
            return (this.bitField0_ & 2) == 2;
        }

        @Override
        public String getPkiType() {
            Object ref = this.pkiType_;
            if (ref instanceof String) {
                return (String)ref;
            }
            ByteString bs = (ByteString)ref;
            String s = bs.toStringUtf8();
            if (bs.isValidUtf8()) {
                this.pkiType_ = s;
            }
            return s;
        }

        @Override
        public ByteString getPkiTypeBytes() {
            Object ref = this.pkiType_;
            if (ref instanceof String) {
                ByteString b = ByteString.copyFromUtf8((String)((String)ref));
                this.pkiType_ = b;
                return b;
            }
            return (ByteString)ref;
        }

        @Override
        public boolean hasPkiData() {
            return (this.bitField0_ & 4) == 4;
        }

        @Override
        public ByteString getPkiData() {
            return this.pkiData_;
        }

        @Override
        public boolean hasSerializedPaymentDetails() {
            return (this.bitField0_ & 8) == 8;
        }

        @Override
        public ByteString getSerializedPaymentDetails() {
            return this.serializedPaymentDetails_;
        }

        @Override
        public boolean hasSignature() {
            return (this.bitField0_ & 16) == 16;
        }

        @Override
        public ByteString getSignature() {
            return this.signature_;
        }

        private void initFields() {
            this.paymentDetailsVersion_ = 1;
            this.pkiType_ = "none";
            this.pkiData_ = ByteString.EMPTY;
            this.serializedPaymentDetails_ = ByteString.EMPTY;
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
            if (!this.hasSerializedPaymentDetails()) {
                this.memoizedIsInitialized = 0;
                return false;
            }
            this.memoizedIsInitialized = 1;
            return true;
        }

        public void writeTo(CodedOutputStream output) throws IOException {
            this.getSerializedSize();
            if ((this.bitField0_ & 1) == 1) {
                output.writeUInt32(1, this.paymentDetailsVersion_);
            }
            if ((this.bitField0_ & 2) == 2) {
                output.writeBytes(2, this.getPkiTypeBytes());
            }
            if ((this.bitField0_ & 4) == 4) {
                output.writeBytes(3, this.pkiData_);
            }
            if ((this.bitField0_ & 8) == 8) {
                output.writeBytes(4, this.serializedPaymentDetails_);
            }
            if ((this.bitField0_ & 16) == 16) {
                output.writeBytes(5, this.signature_);
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
                size += CodedOutputStream.computeUInt32Size((int)1, (int)this.paymentDetailsVersion_);
            }
            if ((this.bitField0_ & 2) == 2) {
                size += CodedOutputStream.computeBytesSize((int)2, (ByteString)this.getPkiTypeBytes());
            }
            if ((this.bitField0_ & 4) == 4) {
                size += CodedOutputStream.computeBytesSize((int)3, (ByteString)this.pkiData_);
            }
            if ((this.bitField0_ & 8) == 8) {
                size += CodedOutputStream.computeBytesSize((int)4, (ByteString)this.serializedPaymentDetails_);
            }
            if ((this.bitField0_ & 16) == 16) {
                size += CodedOutputStream.computeBytesSize((int)5, (ByteString)this.signature_);
            }
            this.memoizedSerializedSize = size += this.getUnknownFields().getSerializedSize();
            return size;
        }

        protected Object writeReplace() throws ObjectStreamException {
            return super.writeReplace();
        }

        public static PaymentRequest parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (PaymentRequest)PARSER.parseFrom(data);
        }

        public static PaymentRequest parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (PaymentRequest)PARSER.parseFrom(data, extensionRegistry);
        }

        public static PaymentRequest parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (PaymentRequest)PARSER.parseFrom(data);
        }

        public static PaymentRequest parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (PaymentRequest)PARSER.parseFrom(data, extensionRegistry);
        }

        public static PaymentRequest parseFrom(InputStream input) throws IOException {
            return (PaymentRequest)PARSER.parseFrom(input);
        }

        public static PaymentRequest parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (PaymentRequest)PARSER.parseFrom(input, extensionRegistry);
        }

        public static PaymentRequest parseDelimitedFrom(InputStream input) throws IOException {
            return (PaymentRequest)PARSER.parseDelimitedFrom(input);
        }

        public static PaymentRequest parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (PaymentRequest)PARSER.parseDelimitedFrom(input, extensionRegistry);
        }

        public static PaymentRequest parseFrom(CodedInputStream input) throws IOException {
            return (PaymentRequest)PARSER.parseFrom(input);
        }

        public static PaymentRequest parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (PaymentRequest)PARSER.parseFrom(input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return Builder.create();
        }

        public Builder newBuilderForType() {
            return PaymentRequest.newBuilder();
        }

        public static Builder newBuilder(PaymentRequest prototype) {
            return PaymentRequest.newBuilder().mergeFrom(prototype);
        }

        public Builder toBuilder() {
            return PaymentRequest.newBuilder(this);
        }

        protected Builder newBuilderForType(GeneratedMessage.BuilderParent parent) {
            Builder builder = new Builder(parent);
            return builder;
        }

        static {
            PARSER = new AbstractParser<PaymentRequest>(){

                public PaymentRequest parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                    return new PaymentRequest(input, extensionRegistry);
                }
            };
            defaultInstance = new PaymentRequest(true);
            defaultInstance.initFields();
        }

        public static final class Builder
        extends GeneratedMessage.Builder<Builder>
        implements PaymentRequestOrBuilder {
            private int bitField0_;
            private int paymentDetailsVersion_ = 1;
            private Object pkiType_ = "none";
            private ByteString pkiData_ = ByteString.EMPTY;
            private ByteString serializedPaymentDetails_ = ByteString.EMPTY;
            private ByteString signature_ = ByteString.EMPTY;

            public static final Descriptors.Descriptor getDescriptor() {
                return internal_static_payments_PaymentRequest_descriptor;
            }

            protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
                return internal_static_payments_PaymentRequest_fieldAccessorTable.ensureFieldAccessorsInitialized(PaymentRequest.class, Builder.class);
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
                this.paymentDetailsVersion_ = 1;
                this.bitField0_ &= -2;
                this.pkiType_ = "none";
                this.bitField0_ &= -3;
                this.pkiData_ = ByteString.EMPTY;
                this.bitField0_ &= -5;
                this.serializedPaymentDetails_ = ByteString.EMPTY;
                this.bitField0_ &= -9;
                this.signature_ = ByteString.EMPTY;
                this.bitField0_ &= -17;
                return this;
            }

            public Builder clone() {
                return Builder.create().mergeFrom(this.buildPartial());
            }

            public Descriptors.Descriptor getDescriptorForType() {
                return internal_static_payments_PaymentRequest_descriptor;
            }

            public PaymentRequest getDefaultInstanceForType() {
                return PaymentRequest.getDefaultInstance();
            }

            public PaymentRequest build() {
                PaymentRequest result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw Builder.newUninitializedMessageException((Message)result);
                }
                return result;
            }

            public PaymentRequest buildPartial() {
                PaymentRequest result = new PaymentRequest(this);
                int from_bitField0_ = this.bitField0_;
                int to_bitField0_ = 0;
                if ((from_bitField0_ & 1) == 1) {
                    to_bitField0_ |= true;
                }
                result.paymentDetailsVersion_ = this.paymentDetailsVersion_;
                if ((from_bitField0_ & 2) == 2) {
                    to_bitField0_ |= 2;
                }
                result.pkiType_ = this.pkiType_;
                if ((from_bitField0_ & 4) == 4) {
                    to_bitField0_ |= 4;
                }
                result.pkiData_ = this.pkiData_;
                if ((from_bitField0_ & 8) == 8) {
                    to_bitField0_ |= 8;
                }
                result.serializedPaymentDetails_ = this.serializedPaymentDetails_;
                if ((from_bitField0_ & 16) == 16) {
                    to_bitField0_ |= 16;
                }
                result.signature_ = this.signature_;
                result.bitField0_ = to_bitField0_;
                this.onBuilt();
                return result;
            }

            public Builder mergeFrom(Message other) {
                if (other instanceof PaymentRequest) {
                    return this.mergeFrom((PaymentRequest)other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(PaymentRequest other) {
                if (other == PaymentRequest.getDefaultInstance()) {
                    return this;
                }
                if (other.hasPaymentDetailsVersion()) {
                    this.setPaymentDetailsVersion(other.getPaymentDetailsVersion());
                }
                if (other.hasPkiType()) {
                    this.bitField0_ |= 2;
                    this.pkiType_ = other.pkiType_;
                    this.onChanged();
                }
                if (other.hasPkiData()) {
                    this.setPkiData(other.getPkiData());
                }
                if (other.hasSerializedPaymentDetails()) {
                    this.setSerializedPaymentDetails(other.getSerializedPaymentDetails());
                }
                if (other.hasSignature()) {
                    this.setSignature(other.getSignature());
                }
                this.mergeUnknownFields(other.getUnknownFields());
                return this;
            }

            public final boolean isInitialized() {
                if (!this.hasSerializedPaymentDetails()) {
                    return false;
                }
                return true;
            }

            public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                PaymentRequest parsedMessage = null;
                try {
                    parsedMessage = (PaymentRequest)PaymentRequest.PARSER.parsePartialFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e) {
                    parsedMessage = (PaymentRequest)e.getUnfinishedMessage();
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
            public boolean hasPaymentDetailsVersion() {
                return (this.bitField0_ & 1) == 1;
            }

            @Override
            public int getPaymentDetailsVersion() {
                return this.paymentDetailsVersion_;
            }

            public Builder setPaymentDetailsVersion(int value) {
                this.bitField0_ |= 1;
                this.paymentDetailsVersion_ = value;
                this.onChanged();
                return this;
            }

            public Builder clearPaymentDetailsVersion() {
                this.bitField0_ &= -2;
                this.paymentDetailsVersion_ = 1;
                this.onChanged();
                return this;
            }

            @Override
            public boolean hasPkiType() {
                return (this.bitField0_ & 2) == 2;
            }

            @Override
            public String getPkiType() {
                Object ref = this.pkiType_;
                if (!(ref instanceof String)) {
                    ByteString bs = (ByteString)ref;
                    String s = bs.toStringUtf8();
                    if (bs.isValidUtf8()) {
                        this.pkiType_ = s;
                    }
                    return s;
                }
                return (String)ref;
            }

            @Override
            public ByteString getPkiTypeBytes() {
                Object ref = this.pkiType_;
                if (ref instanceof String) {
                    ByteString b = ByteString.copyFromUtf8((String)((String)ref));
                    this.pkiType_ = b;
                    return b;
                }
                return (ByteString)ref;
            }

            public Builder setPkiType(String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 2;
                this.pkiType_ = value;
                this.onChanged();
                return this;
            }

            public Builder clearPkiType() {
                this.bitField0_ &= -3;
                this.pkiType_ = PaymentRequest.getDefaultInstance().getPkiType();
                this.onChanged();
                return this;
            }

            public Builder setPkiTypeBytes(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 2;
                this.pkiType_ = value;
                this.onChanged();
                return this;
            }

            @Override
            public boolean hasPkiData() {
                return (this.bitField0_ & 4) == 4;
            }

            @Override
            public ByteString getPkiData() {
                return this.pkiData_;
            }

            public Builder setPkiData(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 4;
                this.pkiData_ = value;
                this.onChanged();
                return this;
            }

            public Builder clearPkiData() {
                this.bitField0_ &= -5;
                this.pkiData_ = PaymentRequest.getDefaultInstance().getPkiData();
                this.onChanged();
                return this;
            }

            @Override
            public boolean hasSerializedPaymentDetails() {
                return (this.bitField0_ & 8) == 8;
            }

            @Override
            public ByteString getSerializedPaymentDetails() {
                return this.serializedPaymentDetails_;
            }

            public Builder setSerializedPaymentDetails(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 8;
                this.serializedPaymentDetails_ = value;
                this.onChanged();
                return this;
            }

            public Builder clearSerializedPaymentDetails() {
                this.bitField0_ &= -9;
                this.serializedPaymentDetails_ = PaymentRequest.getDefaultInstance().getSerializedPaymentDetails();
                this.onChanged();
                return this;
            }

            @Override
            public boolean hasSignature() {
                return (this.bitField0_ & 16) == 16;
            }

            @Override
            public ByteString getSignature() {
                return this.signature_;
            }

            public Builder setSignature(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 16;
                this.signature_ = value;
                this.onChanged();
                return this;
            }

            public Builder clearSignature() {
                this.bitField0_ &= -17;
                this.signature_ = PaymentRequest.getDefaultInstance().getSignature();
                this.onChanged();
                return this;
            }
        }

    }

    public static interface PaymentRequestOrBuilder
    extends MessageOrBuilder {
        public boolean hasPaymentDetailsVersion();

        public int getPaymentDetailsVersion();

        public boolean hasPkiType();

        public String getPkiType();

        public ByteString getPkiTypeBytes();

        public boolean hasPkiData();

        public ByteString getPkiData();

        public boolean hasSerializedPaymentDetails();

        public ByteString getSerializedPaymentDetails();

        public boolean hasSignature();

        public ByteString getSignature();
    }

    public static final class PaymentDetails
    extends GeneratedMessage
    implements PaymentDetailsOrBuilder {
        private static final PaymentDetails defaultInstance;
        private final UnknownFieldSet unknownFields;
        public static Parser<PaymentDetails> PARSER;
        private int bitField0_;
        public static final int NETWORK_FIELD_NUMBER = 1;
        private Object network_;
        public static final int OUTPUTS_FIELD_NUMBER = 2;
        private List<Output> outputs_;
        public static final int TIME_FIELD_NUMBER = 3;
        private long time_;
        public static final int EXPIRES_FIELD_NUMBER = 4;
        private long expires_;
        public static final int MEMO_FIELD_NUMBER = 5;
        private Object memo_;
        public static final int PAYMENT_URL_FIELD_NUMBER = 6;
        private Object paymentUrl_;
        public static final int MERCHANT_DATA_FIELD_NUMBER = 7;
        private ByteString merchantData_;
        private byte memoizedIsInitialized;
        private int memoizedSerializedSize;
        private static final long serialVersionUID = 0L;

        private PaymentDetails(GeneratedMessage.Builder<?> builder) {
            super(builder);
            this.memoizedIsInitialized = (byte)-1;
            this.memoizedSerializedSize = -1;
            this.unknownFields = builder.getUnknownFields();
        }

        private PaymentDetails(boolean noInit) {
            this.memoizedIsInitialized = (byte)-1;
            this.memoizedSerializedSize = -1;
            this.unknownFields = UnknownFieldSet.getDefaultInstance();
        }

        public static PaymentDetails getDefaultInstance() {
            return defaultInstance;
        }

        public PaymentDetails getDefaultInstanceForType() {
            return defaultInstance;
        }

        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private PaymentDetails(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            this.memoizedIsInitialized = (byte)-1;
            this.memoizedSerializedSize = -1;
            this.initFields();
            int mutable_bitField0_ = 0;
            UnknownFieldSet.Builder unknownFields = UnknownFieldSet.newBuilder();
            try {
                boolean done = false;
                block16 : while (!done) {
                    int tag = input.readTag();
                    switch (tag) {
                        ByteString bs;
                        case 0: {
                            done = true;
                            continue block16;
                        }
                        default: {
                            if (this.parseUnknownField(input, unknownFields, extensionRegistry, tag)) continue block16;
                            done = true;
                            continue block16;
                        }
                        case 10: {
                            bs = input.readBytes();
                            this.bitField0_ |= 1;
                            this.network_ = bs;
                            continue block16;
                        }
                        case 18: {
                            if ((mutable_bitField0_ & 2) != 2) {
                                this.outputs_ = new ArrayList<Output>();
                                mutable_bitField0_ |= 2;
                            }
                            this.outputs_.add((Output)input.readMessage(Output.PARSER, extensionRegistry));
                            continue block16;
                        }
                        case 24: {
                            this.bitField0_ |= 2;
                            this.time_ = input.readUInt64();
                            continue block16;
                        }
                        case 32: {
                            this.bitField0_ |= 4;
                            this.expires_ = input.readUInt64();
                            continue block16;
                        }
                        case 42: {
                            bs = input.readBytes();
                            this.bitField0_ |= 8;
                            this.memo_ = bs;
                            continue block16;
                        }
                        case 50: {
                            bs = input.readBytes();
                            this.bitField0_ |= 16;
                            this.paymentUrl_ = bs;
                            continue block16;
                        }
                        case 58: 
                    }
                    this.bitField0_ |= 32;
                    this.merchantData_ = input.readBytes();
                }
            }
            catch (InvalidProtocolBufferException e) {
                throw e.setUnfinishedMessage((MessageLite)this);
            }
            catch (IOException e) {
                throw new InvalidProtocolBufferException(e.getMessage()).setUnfinishedMessage((MessageLite)this);
            }
            finally {
                if ((mutable_bitField0_ & 2) == 2) {
                    this.outputs_ = Collections.unmodifiableList(this.outputs_);
                }
                this.unknownFields = unknownFields.build();
                this.makeExtensionsImmutable();
            }
        }

        public static final Descriptors.Descriptor getDescriptor() {
            return internal_static_payments_PaymentDetails_descriptor;
        }

        protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
            return internal_static_payments_PaymentDetails_fieldAccessorTable.ensureFieldAccessorsInitialized(PaymentDetails.class, Builder.class);
        }

        public Parser<PaymentDetails> getParserForType() {
            return PARSER;
        }

        @Override
        public boolean hasNetwork() {
            return (this.bitField0_ & 1) == 1;
        }

        @Override
        public String getNetwork() {
            Object ref = this.network_;
            if (ref instanceof String) {
                return (String)ref;
            }
            ByteString bs = (ByteString)ref;
            String s = bs.toStringUtf8();
            if (bs.isValidUtf8()) {
                this.network_ = s;
            }
            return s;
        }

        @Override
        public ByteString getNetworkBytes() {
            Object ref = this.network_;
            if (ref instanceof String) {
                ByteString b = ByteString.copyFromUtf8((String)((String)ref));
                this.network_ = b;
                return b;
            }
            return (ByteString)ref;
        }

        @Override
        public List<Output> getOutputsList() {
            return this.outputs_;
        }

        @Override
        public List<? extends OutputOrBuilder> getOutputsOrBuilderList() {
            return this.outputs_;
        }

        @Override
        public int getOutputsCount() {
            return this.outputs_.size();
        }

        @Override
        public Output getOutputs(int index) {
            return this.outputs_.get(index);
        }

        @Override
        public OutputOrBuilder getOutputsOrBuilder(int index) {
            return this.outputs_.get(index);
        }

        @Override
        public boolean hasTime() {
            return (this.bitField0_ & 2) == 2;
        }

        @Override
        public long getTime() {
            return this.time_;
        }

        @Override
        public boolean hasExpires() {
            return (this.bitField0_ & 4) == 4;
        }

        @Override
        public long getExpires() {
            return this.expires_;
        }

        @Override
        public boolean hasMemo() {
            return (this.bitField0_ & 8) == 8;
        }

        @Override
        public String getMemo() {
            Object ref = this.memo_;
            if (ref instanceof String) {
                return (String)ref;
            }
            ByteString bs = (ByteString)ref;
            String s = bs.toStringUtf8();
            if (bs.isValidUtf8()) {
                this.memo_ = s;
            }
            return s;
        }

        @Override
        public ByteString getMemoBytes() {
            Object ref = this.memo_;
            if (ref instanceof String) {
                ByteString b = ByteString.copyFromUtf8((String)((String)ref));
                this.memo_ = b;
                return b;
            }
            return (ByteString)ref;
        }

        @Override
        public boolean hasPaymentUrl() {
            return (this.bitField0_ & 16) == 16;
        }

        @Override
        public String getPaymentUrl() {
            Object ref = this.paymentUrl_;
            if (ref instanceof String) {
                return (String)ref;
            }
            ByteString bs = (ByteString)ref;
            String s = bs.toStringUtf8();
            if (bs.isValidUtf8()) {
                this.paymentUrl_ = s;
            }
            return s;
        }

        @Override
        public ByteString getPaymentUrlBytes() {
            Object ref = this.paymentUrl_;
            if (ref instanceof String) {
                ByteString b = ByteString.copyFromUtf8((String)((String)ref));
                this.paymentUrl_ = b;
                return b;
            }
            return (ByteString)ref;
        }

        @Override
        public boolean hasMerchantData() {
            return (this.bitField0_ & 32) == 32;
        }

        @Override
        public ByteString getMerchantData() {
            return this.merchantData_;
        }

        private void initFields() {
            this.network_ = "main";
            this.outputs_ = Collections.emptyList();
            this.time_ = 0L;
            this.expires_ = 0L;
            this.memo_ = "";
            this.paymentUrl_ = "";
            this.merchantData_ = ByteString.EMPTY;
        }

        public final boolean isInitialized() {
            byte isInitialized = this.memoizedIsInitialized;
            if (isInitialized == 1) {
                return true;
            }
            if (isInitialized == 0) {
                return false;
            }
            if (!this.hasTime()) {
                this.memoizedIsInitialized = 0;
                return false;
            }
            for (int i = 0; i < this.getOutputsCount(); ++i) {
                if (this.getOutputs(i).isInitialized()) continue;
                this.memoizedIsInitialized = 0;
                return false;
            }
            this.memoizedIsInitialized = 1;
            return true;
        }

        public void writeTo(CodedOutputStream output) throws IOException {
            this.getSerializedSize();
            if ((this.bitField0_ & 1) == 1) {
                output.writeBytes(1, this.getNetworkBytes());
            }
            for (int i = 0; i < this.outputs_.size(); ++i) {
                output.writeMessage(2, (MessageLite)this.outputs_.get(i));
            }
            if ((this.bitField0_ & 2) == 2) {
                output.writeUInt64(3, this.time_);
            }
            if ((this.bitField0_ & 4) == 4) {
                output.writeUInt64(4, this.expires_);
            }
            if ((this.bitField0_ & 8) == 8) {
                output.writeBytes(5, this.getMemoBytes());
            }
            if ((this.bitField0_ & 16) == 16) {
                output.writeBytes(6, this.getPaymentUrlBytes());
            }
            if ((this.bitField0_ & 32) == 32) {
                output.writeBytes(7, this.merchantData_);
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
                size += CodedOutputStream.computeBytesSize((int)1, (ByteString)this.getNetworkBytes());
            }
            for (int i = 0; i < this.outputs_.size(); ++i) {
                size += CodedOutputStream.computeMessageSize((int)2, (MessageLite)((MessageLite)this.outputs_.get(i)));
            }
            if ((this.bitField0_ & 2) == 2) {
                size += CodedOutputStream.computeUInt64Size((int)3, (long)this.time_);
            }
            if ((this.bitField0_ & 4) == 4) {
                size += CodedOutputStream.computeUInt64Size((int)4, (long)this.expires_);
            }
            if ((this.bitField0_ & 8) == 8) {
                size += CodedOutputStream.computeBytesSize((int)5, (ByteString)this.getMemoBytes());
            }
            if ((this.bitField0_ & 16) == 16) {
                size += CodedOutputStream.computeBytesSize((int)6, (ByteString)this.getPaymentUrlBytes());
            }
            if ((this.bitField0_ & 32) == 32) {
                size += CodedOutputStream.computeBytesSize((int)7, (ByteString)this.merchantData_);
            }
            this.memoizedSerializedSize = size += this.getUnknownFields().getSerializedSize();
            return size;
        }

        protected Object writeReplace() throws ObjectStreamException {
            return super.writeReplace();
        }

        public static PaymentDetails parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (PaymentDetails)PARSER.parseFrom(data);
        }

        public static PaymentDetails parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (PaymentDetails)PARSER.parseFrom(data, extensionRegistry);
        }

        public static PaymentDetails parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (PaymentDetails)PARSER.parseFrom(data);
        }

        public static PaymentDetails parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (PaymentDetails)PARSER.parseFrom(data, extensionRegistry);
        }

        public static PaymentDetails parseFrom(InputStream input) throws IOException {
            return (PaymentDetails)PARSER.parseFrom(input);
        }

        public static PaymentDetails parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (PaymentDetails)PARSER.parseFrom(input, extensionRegistry);
        }

        public static PaymentDetails parseDelimitedFrom(InputStream input) throws IOException {
            return (PaymentDetails)PARSER.parseDelimitedFrom(input);
        }

        public static PaymentDetails parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (PaymentDetails)PARSER.parseDelimitedFrom(input, extensionRegistry);
        }

        public static PaymentDetails parseFrom(CodedInputStream input) throws IOException {
            return (PaymentDetails)PARSER.parseFrom(input);
        }

        public static PaymentDetails parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (PaymentDetails)PARSER.parseFrom(input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return Builder.create();
        }

        public Builder newBuilderForType() {
            return PaymentDetails.newBuilder();
        }

        public static Builder newBuilder(PaymentDetails prototype) {
            return PaymentDetails.newBuilder().mergeFrom(prototype);
        }

        public Builder toBuilder() {
            return PaymentDetails.newBuilder(this);
        }

        protected Builder newBuilderForType(GeneratedMessage.BuilderParent parent) {
            Builder builder = new Builder(parent);
            return builder;
        }

        static {
            PARSER = new AbstractParser<PaymentDetails>(){

                public PaymentDetails parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                    return new PaymentDetails(input, extensionRegistry);
                }
            };
            defaultInstance = new PaymentDetails(true);
            defaultInstance.initFields();
        }

        public static final class Builder
        extends GeneratedMessage.Builder<Builder>
        implements PaymentDetailsOrBuilder {
            private int bitField0_;
            private Object network_ = "main";
            private List<Output> outputs_ = Collections.emptyList();
            private RepeatedFieldBuilder<Output, Output.Builder, OutputOrBuilder> outputsBuilder_;
            private long time_;
            private long expires_;
            private Object memo_ = "";
            private Object paymentUrl_ = "";
            private ByteString merchantData_ = ByteString.EMPTY;

            public static final Descriptors.Descriptor getDescriptor() {
                return internal_static_payments_PaymentDetails_descriptor;
            }

            protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
                return internal_static_payments_PaymentDetails_fieldAccessorTable.ensureFieldAccessorsInitialized(PaymentDetails.class, Builder.class);
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
                    this.getOutputsFieldBuilder();
                }
            }

            private static Builder create() {
                return new Builder();
            }

            public Builder clear() {
                super.clear();
                this.network_ = "main";
                this.bitField0_ &= -2;
                if (this.outputsBuilder_ == null) {
                    this.outputs_ = Collections.emptyList();
                    this.bitField0_ &= -3;
                } else {
                    this.outputsBuilder_.clear();
                }
                this.time_ = 0L;
                this.bitField0_ &= -5;
                this.expires_ = 0L;
                this.bitField0_ &= -9;
                this.memo_ = "";
                this.bitField0_ &= -17;
                this.paymentUrl_ = "";
                this.bitField0_ &= -33;
                this.merchantData_ = ByteString.EMPTY;
                this.bitField0_ &= -65;
                return this;
            }

            public Builder clone() {
                return Builder.create().mergeFrom(this.buildPartial());
            }

            public Descriptors.Descriptor getDescriptorForType() {
                return internal_static_payments_PaymentDetails_descriptor;
            }

            public PaymentDetails getDefaultInstanceForType() {
                return PaymentDetails.getDefaultInstance();
            }

            public PaymentDetails build() {
                PaymentDetails result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw Builder.newUninitializedMessageException((Message)result);
                }
                return result;
            }

            public PaymentDetails buildPartial() {
                PaymentDetails result = new PaymentDetails(this);
                int from_bitField0_ = this.bitField0_;
                int to_bitField0_ = 0;
                if ((from_bitField0_ & 1) == 1) {
                    to_bitField0_ |= true;
                }
                result.network_ = this.network_;
                if (this.outputsBuilder_ == null) {
                    if ((this.bitField0_ & 2) == 2) {
                        this.outputs_ = Collections.unmodifiableList(this.outputs_);
                        this.bitField0_ &= -3;
                    }
                    result.outputs_ = this.outputs_;
                } else {
                    result.outputs_ = this.outputsBuilder_.build();
                }
                if ((from_bitField0_ & 4) == 4) {
                    to_bitField0_ |= 2;
                }
                result.time_ = this.time_;
                if ((from_bitField0_ & 8) == 8) {
                    to_bitField0_ |= 4;
                }
                result.expires_ = this.expires_;
                if ((from_bitField0_ & 16) == 16) {
                    to_bitField0_ |= 8;
                }
                result.memo_ = this.memo_;
                if ((from_bitField0_ & 32) == 32) {
                    to_bitField0_ |= 16;
                }
                result.paymentUrl_ = this.paymentUrl_;
                if ((from_bitField0_ & 64) == 64) {
                    to_bitField0_ |= 32;
                }
                result.merchantData_ = this.merchantData_;
                result.bitField0_ = to_bitField0_;
                this.onBuilt();
                return result;
            }

            public Builder mergeFrom(Message other) {
                if (other instanceof PaymentDetails) {
                    return this.mergeFrom((PaymentDetails)other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(PaymentDetails other) {
                if (other == PaymentDetails.getDefaultInstance()) {
                    return this;
                }
                if (other.hasNetwork()) {
                    this.bitField0_ |= 1;
                    this.network_ = other.network_;
                    this.onChanged();
                }
                if (this.outputsBuilder_ == null) {
                    if (!other.outputs_.isEmpty()) {
                        if (this.outputs_.isEmpty()) {
                            this.outputs_ = other.outputs_;
                            this.bitField0_ &= -3;
                        } else {
                            this.ensureOutputsIsMutable();
                            this.outputs_.addAll(other.outputs_);
                        }
                        this.onChanged();
                    }
                } else if (!other.outputs_.isEmpty()) {
                    if (this.outputsBuilder_.isEmpty()) {
                        this.outputsBuilder_.dispose();
                        this.outputsBuilder_ = null;
                        this.outputs_ = other.outputs_;
                        this.bitField0_ &= -3;
                        this.outputsBuilder_ = alwaysUseFieldBuilders ? this.getOutputsFieldBuilder() : null;
                    } else {
                        this.outputsBuilder_.addAllMessages((Iterable)other.outputs_);
                    }
                }
                if (other.hasTime()) {
                    this.setTime(other.getTime());
                }
                if (other.hasExpires()) {
                    this.setExpires(other.getExpires());
                }
                if (other.hasMemo()) {
                    this.bitField0_ |= 16;
                    this.memo_ = other.memo_;
                    this.onChanged();
                }
                if (other.hasPaymentUrl()) {
                    this.bitField0_ |= 32;
                    this.paymentUrl_ = other.paymentUrl_;
                    this.onChanged();
                }
                if (other.hasMerchantData()) {
                    this.setMerchantData(other.getMerchantData());
                }
                this.mergeUnknownFields(other.getUnknownFields());
                return this;
            }

            public final boolean isInitialized() {
                if (!this.hasTime()) {
                    return false;
                }
                for (int i = 0; i < this.getOutputsCount(); ++i) {
                    if (this.getOutputs(i).isInitialized()) continue;
                    return false;
                }
                return true;
            }

            public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                PaymentDetails parsedMessage = null;
                try {
                    parsedMessage = (PaymentDetails)PaymentDetails.PARSER.parsePartialFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e) {
                    parsedMessage = (PaymentDetails)e.getUnfinishedMessage();
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
            public boolean hasNetwork() {
                return (this.bitField0_ & 1) == 1;
            }

            @Override
            public String getNetwork() {
                Object ref = this.network_;
                if (!(ref instanceof String)) {
                    ByteString bs = (ByteString)ref;
                    String s = bs.toStringUtf8();
                    if (bs.isValidUtf8()) {
                        this.network_ = s;
                    }
                    return s;
                }
                return (String)ref;
            }

            @Override
            public ByteString getNetworkBytes() {
                Object ref = this.network_;
                if (ref instanceof String) {
                    ByteString b = ByteString.copyFromUtf8((String)((String)ref));
                    this.network_ = b;
                    return b;
                }
                return (ByteString)ref;
            }

            public Builder setNetwork(String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 1;
                this.network_ = value;
                this.onChanged();
                return this;
            }

            public Builder clearNetwork() {
                this.bitField0_ &= -2;
                this.network_ = PaymentDetails.getDefaultInstance().getNetwork();
                this.onChanged();
                return this;
            }

            public Builder setNetworkBytes(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 1;
                this.network_ = value;
                this.onChanged();
                return this;
            }

            private void ensureOutputsIsMutable() {
                if ((this.bitField0_ & 2) != 2) {
                    this.outputs_ = new ArrayList<Output>(this.outputs_);
                    this.bitField0_ |= 2;
                }
            }

            @Override
            public List<Output> getOutputsList() {
                if (this.outputsBuilder_ == null) {
                    return Collections.unmodifiableList(this.outputs_);
                }
                return this.outputsBuilder_.getMessageList();
            }

            @Override
            public int getOutputsCount() {
                if (this.outputsBuilder_ == null) {
                    return this.outputs_.size();
                }
                return this.outputsBuilder_.getCount();
            }

            @Override
            public Output getOutputs(int index) {
                if (this.outputsBuilder_ == null) {
                    return this.outputs_.get(index);
                }
                return (Output)this.outputsBuilder_.getMessage(index);
            }

            public Builder setOutputs(int index, Output value) {
                if (this.outputsBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.ensureOutputsIsMutable();
                    this.outputs_.set(index, value);
                    this.onChanged();
                } else {
                    this.outputsBuilder_.setMessage(index, (GeneratedMessage)value);
                }
                return this;
            }

            public Builder setOutputs(int index, Output.Builder builderForValue) {
                if (this.outputsBuilder_ == null) {
                    this.ensureOutputsIsMutable();
                    this.outputs_.set(index, builderForValue.build());
                    this.onChanged();
                } else {
                    this.outputsBuilder_.setMessage(index, (GeneratedMessage)builderForValue.build());
                }
                return this;
            }

            public Builder addOutputs(Output value) {
                if (this.outputsBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.ensureOutputsIsMutable();
                    this.outputs_.add(value);
                    this.onChanged();
                } else {
                    this.outputsBuilder_.addMessage((GeneratedMessage)value);
                }
                return this;
            }

            public Builder addOutputs(int index, Output value) {
                if (this.outputsBuilder_ == null) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.ensureOutputsIsMutable();
                    this.outputs_.add(index, value);
                    this.onChanged();
                } else {
                    this.outputsBuilder_.addMessage(index, (GeneratedMessage)value);
                }
                return this;
            }

            public Builder addOutputs(Output.Builder builderForValue) {
                if (this.outputsBuilder_ == null) {
                    this.ensureOutputsIsMutable();
                    this.outputs_.add(builderForValue.build());
                    this.onChanged();
                } else {
                    this.outputsBuilder_.addMessage((GeneratedMessage)builderForValue.build());
                }
                return this;
            }

            public Builder addOutputs(int index, Output.Builder builderForValue) {
                if (this.outputsBuilder_ == null) {
                    this.ensureOutputsIsMutable();
                    this.outputs_.add(index, builderForValue.build());
                    this.onChanged();
                } else {
                    this.outputsBuilder_.addMessage(index, (GeneratedMessage)builderForValue.build());
                }
                return this;
            }

            public Builder addAllOutputs(Iterable<? extends Output> values) {
                if (this.outputsBuilder_ == null) {
                    this.ensureOutputsIsMutable();
                    AbstractMessageLite.Builder.addAll(values, this.outputs_);
                    this.onChanged();
                } else {
                    this.outputsBuilder_.addAllMessages(values);
                }
                return this;
            }

            public Builder clearOutputs() {
                if (this.outputsBuilder_ == null) {
                    this.outputs_ = Collections.emptyList();
                    this.bitField0_ &= -3;
                    this.onChanged();
                } else {
                    this.outputsBuilder_.clear();
                }
                return this;
            }

            public Builder removeOutputs(int index) {
                if (this.outputsBuilder_ == null) {
                    this.ensureOutputsIsMutable();
                    this.outputs_.remove(index);
                    this.onChanged();
                } else {
                    this.outputsBuilder_.remove(index);
                }
                return this;
            }

            public Output.Builder getOutputsBuilder(int index) {
                return (Output.Builder)this.getOutputsFieldBuilder().getBuilder(index);
            }

            @Override
            public OutputOrBuilder getOutputsOrBuilder(int index) {
                if (this.outputsBuilder_ == null) {
                    return this.outputs_.get(index);
                }
                return (OutputOrBuilder)this.outputsBuilder_.getMessageOrBuilder(index);
            }

            @Override
            public List<? extends OutputOrBuilder> getOutputsOrBuilderList() {
                if (this.outputsBuilder_ != null) {
                    return this.outputsBuilder_.getMessageOrBuilderList();
                }
                return Collections.unmodifiableList(this.outputs_);
            }

            public Output.Builder addOutputsBuilder() {
                return (Output.Builder)this.getOutputsFieldBuilder().addBuilder((GeneratedMessage)Output.getDefaultInstance());
            }

            public Output.Builder addOutputsBuilder(int index) {
                return (Output.Builder)this.getOutputsFieldBuilder().addBuilder(index, (GeneratedMessage)Output.getDefaultInstance());
            }

            public List<Output.Builder> getOutputsBuilderList() {
                return this.getOutputsFieldBuilder().getBuilderList();
            }

            private RepeatedFieldBuilder<Output, Output.Builder, OutputOrBuilder> getOutputsFieldBuilder() {
                if (this.outputsBuilder_ == null) {
                    this.outputsBuilder_ = new RepeatedFieldBuilder(this.outputs_, (this.bitField0_ & 2) == 2, this.getParentForChildren(), this.isClean());
                    this.outputs_ = null;
                }
                return this.outputsBuilder_;
            }

            @Override
            public boolean hasTime() {
                return (this.bitField0_ & 4) == 4;
            }

            @Override
            public long getTime() {
                return this.time_;
            }

            public Builder setTime(long value) {
                this.bitField0_ |= 4;
                this.time_ = value;
                this.onChanged();
                return this;
            }

            public Builder clearTime() {
                this.bitField0_ &= -5;
                this.time_ = 0L;
                this.onChanged();
                return this;
            }

            @Override
            public boolean hasExpires() {
                return (this.bitField0_ & 8) == 8;
            }

            @Override
            public long getExpires() {
                return this.expires_;
            }

            public Builder setExpires(long value) {
                this.bitField0_ |= 8;
                this.expires_ = value;
                this.onChanged();
                return this;
            }

            public Builder clearExpires() {
                this.bitField0_ &= -9;
                this.expires_ = 0L;
                this.onChanged();
                return this;
            }

            @Override
            public boolean hasMemo() {
                return (this.bitField0_ & 16) == 16;
            }

            @Override
            public String getMemo() {
                Object ref = this.memo_;
                if (!(ref instanceof String)) {
                    ByteString bs = (ByteString)ref;
                    String s = bs.toStringUtf8();
                    if (bs.isValidUtf8()) {
                        this.memo_ = s;
                    }
                    return s;
                }
                return (String)ref;
            }

            @Override
            public ByteString getMemoBytes() {
                Object ref = this.memo_;
                if (ref instanceof String) {
                    ByteString b = ByteString.copyFromUtf8((String)((String)ref));
                    this.memo_ = b;
                    return b;
                }
                return (ByteString)ref;
            }

            public Builder setMemo(String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 16;
                this.memo_ = value;
                this.onChanged();
                return this;
            }

            public Builder clearMemo() {
                this.bitField0_ &= -17;
                this.memo_ = PaymentDetails.getDefaultInstance().getMemo();
                this.onChanged();
                return this;
            }

            public Builder setMemoBytes(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 16;
                this.memo_ = value;
                this.onChanged();
                return this;
            }

            @Override
            public boolean hasPaymentUrl() {
                return (this.bitField0_ & 32) == 32;
            }

            @Override
            public String getPaymentUrl() {
                Object ref = this.paymentUrl_;
                if (!(ref instanceof String)) {
                    ByteString bs = (ByteString)ref;
                    String s = bs.toStringUtf8();
                    if (bs.isValidUtf8()) {
                        this.paymentUrl_ = s;
                    }
                    return s;
                }
                return (String)ref;
            }

            @Override
            public ByteString getPaymentUrlBytes() {
                Object ref = this.paymentUrl_;
                if (ref instanceof String) {
                    ByteString b = ByteString.copyFromUtf8((String)((String)ref));
                    this.paymentUrl_ = b;
                    return b;
                }
                return (ByteString)ref;
            }

            public Builder setPaymentUrl(String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 32;
                this.paymentUrl_ = value;
                this.onChanged();
                return this;
            }

            public Builder clearPaymentUrl() {
                this.bitField0_ &= -33;
                this.paymentUrl_ = PaymentDetails.getDefaultInstance().getPaymentUrl();
                this.onChanged();
                return this;
            }

            public Builder setPaymentUrlBytes(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 32;
                this.paymentUrl_ = value;
                this.onChanged();
                return this;
            }

            @Override
            public boolean hasMerchantData() {
                return (this.bitField0_ & 64) == 64;
            }

            @Override
            public ByteString getMerchantData() {
                return this.merchantData_;
            }

            public Builder setMerchantData(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 64;
                this.merchantData_ = value;
                this.onChanged();
                return this;
            }

            public Builder clearMerchantData() {
                this.bitField0_ &= -65;
                this.merchantData_ = PaymentDetails.getDefaultInstance().getMerchantData();
                this.onChanged();
                return this;
            }
        }

    }

    public static interface PaymentDetailsOrBuilder
    extends MessageOrBuilder {
        public boolean hasNetwork();

        public String getNetwork();

        public ByteString getNetworkBytes();

        public List<Output> getOutputsList();

        public Output getOutputs(int var1);

        public int getOutputsCount();

        public List<? extends OutputOrBuilder> getOutputsOrBuilderList();

        public OutputOrBuilder getOutputsOrBuilder(int var1);

        public boolean hasTime();

        public long getTime();

        public boolean hasExpires();

        public long getExpires();

        public boolean hasMemo();

        public String getMemo();

        public ByteString getMemoBytes();

        public boolean hasPaymentUrl();

        public String getPaymentUrl();

        public ByteString getPaymentUrlBytes();

        public boolean hasMerchantData();

        public ByteString getMerchantData();
    }

    public static final class Output
    extends GeneratedMessage
    implements OutputOrBuilder {
        private static final Output defaultInstance;
        private final UnknownFieldSet unknownFields;
        public static Parser<Output> PARSER;
        private int bitField0_;
        public static final int AMOUNT_FIELD_NUMBER = 1;
        private long amount_;
        public static final int SCRIPT_FIELD_NUMBER = 2;
        private ByteString script_;
        private byte memoizedIsInitialized;
        private int memoizedSerializedSize;
        private static final long serialVersionUID = 0L;

        private Output(GeneratedMessage.Builder<?> builder) {
            super(builder);
            this.memoizedIsInitialized = (byte)-1;
            this.memoizedSerializedSize = -1;
            this.unknownFields = builder.getUnknownFields();
        }

        private Output(boolean noInit) {
            this.memoizedIsInitialized = (byte)-1;
            this.memoizedSerializedSize = -1;
            this.unknownFields = UnknownFieldSet.getDefaultInstance();
        }

        public static Output getDefaultInstance() {
            return defaultInstance;
        }

        public Output getDefaultInstanceForType() {
            return defaultInstance;
        }

        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private Output(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
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
                            this.amount_ = input.readUInt64();
                            continue block11;
                        }
                        case 18: 
                    }
                    this.bitField0_ |= 2;
                    this.script_ = input.readBytes();
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
            return internal_static_payments_Output_descriptor;
        }

        protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
            return internal_static_payments_Output_fieldAccessorTable.ensureFieldAccessorsInitialized(Output.class, Builder.class);
        }

        public Parser<Output> getParserForType() {
            return PARSER;
        }

        @Override
        public boolean hasAmount() {
            return (this.bitField0_ & 1) == 1;
        }

        @Override
        public long getAmount() {
            return this.amount_;
        }

        @Override
        public boolean hasScript() {
            return (this.bitField0_ & 2) == 2;
        }

        @Override
        public ByteString getScript() {
            return this.script_;
        }

        private void initFields() {
            this.amount_ = 0L;
            this.script_ = ByteString.EMPTY;
        }

        public final boolean isInitialized() {
            byte isInitialized = this.memoizedIsInitialized;
            if (isInitialized == 1) {
                return true;
            }
            if (isInitialized == 0) {
                return false;
            }
            if (!this.hasScript()) {
                this.memoizedIsInitialized = 0;
                return false;
            }
            this.memoizedIsInitialized = 1;
            return true;
        }

        public void writeTo(CodedOutputStream output) throws IOException {
            this.getSerializedSize();
            if ((this.bitField0_ & 1) == 1) {
                output.writeUInt64(1, this.amount_);
            }
            if ((this.bitField0_ & 2) == 2) {
                output.writeBytes(2, this.script_);
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
                size += CodedOutputStream.computeUInt64Size((int)1, (long)this.amount_);
            }
            if ((this.bitField0_ & 2) == 2) {
                size += CodedOutputStream.computeBytesSize((int)2, (ByteString)this.script_);
            }
            this.memoizedSerializedSize = size += this.getUnknownFields().getSerializedSize();
            return size;
        }

        protected Object writeReplace() throws ObjectStreamException {
            return super.writeReplace();
        }

        public static Output parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (Output)PARSER.parseFrom(data);
        }

        public static Output parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (Output)PARSER.parseFrom(data, extensionRegistry);
        }

        public static Output parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (Output)PARSER.parseFrom(data);
        }

        public static Output parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (Output)PARSER.parseFrom(data, extensionRegistry);
        }

        public static Output parseFrom(InputStream input) throws IOException {
            return (Output)PARSER.parseFrom(input);
        }

        public static Output parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (Output)PARSER.parseFrom(input, extensionRegistry);
        }

        public static Output parseDelimitedFrom(InputStream input) throws IOException {
            return (Output)PARSER.parseDelimitedFrom(input);
        }

        public static Output parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (Output)PARSER.parseDelimitedFrom(input, extensionRegistry);
        }

        public static Output parseFrom(CodedInputStream input) throws IOException {
            return (Output)PARSER.parseFrom(input);
        }

        public static Output parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (Output)PARSER.parseFrom(input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return Builder.create();
        }

        public Builder newBuilderForType() {
            return Output.newBuilder();
        }

        public static Builder newBuilder(Output prototype) {
            return Output.newBuilder().mergeFrom(prototype);
        }

        public Builder toBuilder() {
            return Output.newBuilder(this);
        }

        protected Builder newBuilderForType(GeneratedMessage.BuilderParent parent) {
            Builder builder = new Builder(parent);
            return builder;
        }

        static {
            PARSER = new AbstractParser<Output>(){

                public Output parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                    return new Output(input, extensionRegistry);
                }
            };
            defaultInstance = new Output(true);
            defaultInstance.initFields();
        }

        public static final class Builder
        extends GeneratedMessage.Builder<Builder>
        implements OutputOrBuilder {
            private int bitField0_;
            private long amount_;
            private ByteString script_ = ByteString.EMPTY;

            public static final Descriptors.Descriptor getDescriptor() {
                return internal_static_payments_Output_descriptor;
            }

            protected GeneratedMessage.FieldAccessorTable internalGetFieldAccessorTable() {
                return internal_static_payments_Output_fieldAccessorTable.ensureFieldAccessorsInitialized(Output.class, Builder.class);
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
                this.amount_ = 0L;
                this.bitField0_ &= -2;
                this.script_ = ByteString.EMPTY;
                this.bitField0_ &= -3;
                return this;
            }

            public Builder clone() {
                return Builder.create().mergeFrom(this.buildPartial());
            }

            public Descriptors.Descriptor getDescriptorForType() {
                return internal_static_payments_Output_descriptor;
            }

            public Output getDefaultInstanceForType() {
                return Output.getDefaultInstance();
            }

            public Output build() {
                Output result = this.buildPartial();
                if (!result.isInitialized()) {
                    throw Builder.newUninitializedMessageException((Message)result);
                }
                return result;
            }

            public Output buildPartial() {
                Output result = new Output(this);
                int from_bitField0_ = this.bitField0_;
                int to_bitField0_ = 0;
                if ((from_bitField0_ & 1) == 1) {
                    to_bitField0_ |= true;
                }
                result.amount_ = this.amount_;
                if ((from_bitField0_ & 2) == 2) {
                    to_bitField0_ |= 2;
                }
                result.script_ = this.script_;
                result.bitField0_ = to_bitField0_;
                this.onBuilt();
                return result;
            }

            public Builder mergeFrom(Message other) {
                if (other instanceof Output) {
                    return this.mergeFrom((Output)other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(Output other) {
                if (other == Output.getDefaultInstance()) {
                    return this;
                }
                if (other.hasAmount()) {
                    this.setAmount(other.getAmount());
                }
                if (other.hasScript()) {
                    this.setScript(other.getScript());
                }
                this.mergeUnknownFields(other.getUnknownFields());
                return this;
            }

            public final boolean isInitialized() {
                if (!this.hasScript()) {
                    return false;
                }
                return true;
            }

            public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                Output parsedMessage = null;
                try {
                    parsedMessage = (Output)Output.PARSER.parsePartialFrom(input, extensionRegistry);
                }
                catch (InvalidProtocolBufferException e) {
                    parsedMessage = (Output)e.getUnfinishedMessage();
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
            public boolean hasAmount() {
                return (this.bitField0_ & 1) == 1;
            }

            @Override
            public long getAmount() {
                return this.amount_;
            }

            public Builder setAmount(long value) {
                this.bitField0_ |= 1;
                this.amount_ = value;
                this.onChanged();
                return this;
            }

            public Builder clearAmount() {
                this.bitField0_ &= -2;
                this.amount_ = 0L;
                this.onChanged();
                return this;
            }

            @Override
            public boolean hasScript() {
                return (this.bitField0_ & 2) == 2;
            }

            @Override
            public ByteString getScript() {
                return this.script_;
            }

            public Builder setScript(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 2;
                this.script_ = value;
                this.onChanged();
                return this;
            }

            public Builder clearScript() {
                this.bitField0_ &= -3;
                this.script_ = Output.getDefaultInstance().getScript();
                this.onChanged();
                return this;
            }
        }

    }

    public static interface OutputOrBuilder
    extends MessageOrBuilder {
        public boolean hasAmount();

        public long getAmount();

        public boolean hasScript();

        public ByteString getScript();
    }

}

