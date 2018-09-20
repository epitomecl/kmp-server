/*
 * Decompiled with CFR 0_132.
 * 
 * Could not load the following classes:
 *  com.google.common.base.MoreObjects
 *  com.google.common.base.MoreObjects$ToStringHelper
 *  com.google.common.base.Preconditions
 *  org.spongycastle.crypto.params.KeyParameter
 */
package org.bitcoinj.wallet;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import java.math.BigInteger;
import java.util.Date;
import org.bitcoin.protocols.payments.Protos;
import org.bitcoinj.core.Address;
import org.bitcoinj.core.Coin;
import org.bitcoinj.core.Context;
import org.bitcoinj.core.ECKey;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.core.Transaction;
import org.bitcoinj.core.TransactionOutput;
import org.bitcoinj.script.Script;
import org.bitcoinj.script.ScriptBuilder;
import org.bitcoinj.utils.ExchangeRate;
import org.bitcoinj.wallet.CoinSelector;
import org.spongycastle.crypto.params.KeyParameter;

public class SendRequest {
    public Transaction tx;
    public boolean emptyWallet = false;
    public Address changeAddress = null;
    public Coin feePerKb = Context.get().getFeePerKb();
    public boolean ensureMinRequiredFee = Context.get().isEnsureMinRequiredFee();
    public boolean signInputs = true;
    public KeyParameter aesKey = null;
    public CoinSelector coinSelector = null;
    public boolean shuffleOutputs = true;
    public ExchangeRate exchangeRate = null;
    public String memo = null;
    public boolean recipientsPayFees = false;
    boolean completed;

    private SendRequest() {
    }

    public static SendRequest to(Address destination, Coin value) {
        SendRequest req = new SendRequest();
        NetworkParameters parameters = destination.getParameters();
        Preconditions.checkNotNull((Object)parameters, (Object)"Address is for an unknown network");
        req.tx = new Transaction(parameters);
        req.tx.addOutput(value, destination);
        return req;
    }

    public static SendRequest to(NetworkParameters params, ECKey destination, Coin value) {
        SendRequest req = new SendRequest();
        req.tx = new Transaction(params);
        req.tx.addOutput(value, destination);
        return req;
    }

    public static SendRequest forTx(Transaction tx) {
        SendRequest req = new SendRequest();
        req.tx = tx;
        return req;
    }

    public static SendRequest emptyWallet(Address destination) {
        SendRequest req = new SendRequest();
        NetworkParameters parameters = destination.getParameters();
        Preconditions.checkNotNull((Object)parameters, (Object)"Address is for an unknown network");
        req.tx = new Transaction(parameters);
        req.tx.addOutput(Coin.ZERO, destination);
        req.emptyWallet = true;
        return req;
    }

    public static SendRequest toCLTVPaymentChannel(NetworkParameters params, Date releaseTime, ECKey from, ECKey to, Coin value) {
        long time = releaseTime.getTime() / 1000L;
        Preconditions.checkArgument((boolean)(time >= 500000000L), (Object)"Release time was too small");
        return SendRequest.toCLTVPaymentChannel(params, BigInteger.valueOf(time), from, to, value);
    }

    public static SendRequest toCLTVPaymentChannel(NetworkParameters params, int releaseBlock, ECKey from, ECKey to, Coin value) {
        Preconditions.checkArgument((boolean)(0 <= releaseBlock && releaseBlock < 500000000), (Object)"Block number was too large");
        return SendRequest.toCLTVPaymentChannel(params, BigInteger.valueOf(releaseBlock), from, to, value);
    }

    public static SendRequest toCLTVPaymentChannel(NetworkParameters params, BigInteger time, ECKey from, ECKey to, Coin value) {
        SendRequest req = new SendRequest();
        Script output = ScriptBuilder.createCLTVPaymentChannelOutput(time, from, to);
        req.tx = new Transaction(params);
        req.tx.addOutput(value, output);
        return req;
    }

    public SendRequest fromPaymentDetails(Protos.PaymentDetails paymentDetails) {
        if (paymentDetails.hasMemo()) {
            this.memo = paymentDetails.getMemo();
        }
        return this;
    }

    public String toString() {
        MoreObjects.ToStringHelper helper = MoreObjects.toStringHelper((Object)this).omitNullValues();
        helper.add("emptyWallet", this.emptyWallet);
        helper.add("changeAddress", (Object)this.changeAddress);
        helper.add("feePerKb", (Object)this.feePerKb);
        helper.add("ensureMinRequiredFee", this.ensureMinRequiredFee);
        helper.add("signInputs", this.signInputs);
        helper.add("aesKey", (Object)(this.aesKey != null ? "set" : null));
        helper.add("coinSelector", (Object)this.coinSelector);
        helper.add("shuffleOutputs", this.shuffleOutputs);
        helper.add("recipientsPayFees", this.recipientsPayFees);
        return helper.toString();
    }
}

