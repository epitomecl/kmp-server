/*
 * Decompiled with CFR 0_132.
 */
package org.bitcoinj.crypto;

public class MnemonicException
extends Exception {
    public MnemonicException() {
    }

    public MnemonicException(String msg) {
        super(msg);
    }

    public static class MnemonicWordException
    extends MnemonicException {
        public final String badWord;

        public MnemonicWordException(String badWord) {
            this.badWord = badWord;
        }
    }

    public static class MnemonicChecksumException
    extends MnemonicException {
    }

    public static class MnemonicLengthException
    extends MnemonicException {
        public MnemonicLengthException(String msg) {
            super(msg);
        }
    }

}

