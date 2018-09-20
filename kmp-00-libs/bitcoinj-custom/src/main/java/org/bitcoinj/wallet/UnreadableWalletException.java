/*
 * Decompiled with CFR 0_132.
 */
package org.bitcoinj.wallet;

public class UnreadableWalletException
extends Exception {
    public UnreadableWalletException(String s) {
        super(s);
    }

    public UnreadableWalletException(String s, Throwable t) {
        super(s, t);
    }

    public static class WrongNetwork
    extends UnreadableWalletException {
        public WrongNetwork() {
            super("Mismatched network ID");
        }
    }

    public static class FutureVersion
    extends UnreadableWalletException {
        public FutureVersion() {
            super("Unknown wallet version from the future.");
        }
    }

    public static class BadPassword
    extends UnreadableWalletException {
        public BadPassword() {
            super("Password incorrect");
        }
    }

}

