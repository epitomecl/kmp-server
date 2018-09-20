/*
 * Decompiled with CFR 0_132.
 */
package org.bitcoinj.script;

import org.bitcoinj.core.VerificationException;
import org.bitcoinj.script.ScriptError;

public class ScriptException
extends VerificationException {
    private final ScriptError err;

    public ScriptException(ScriptError err, String msg) {
        super(msg);
        this.err = err;
    }

    public ScriptException(ScriptError err, String msg, Exception e) {
        super(msg, e);
        this.err = err;
    }

    public ScriptError getError() {
        return this.err;
    }
}

