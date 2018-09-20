/*
 * Decompiled with CFR 0_132.
 */
package org.bitcoinj.core;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.LinkedList;
import java.util.List;
import org.bitcoinj.core.UTXO;

public class TransactionOutputChanges {
    public final List<UTXO> txOutsCreated;
    public final List<UTXO> txOutsSpent;

    public TransactionOutputChanges(List<UTXO> txOutsCreated, List<UTXO> txOutsSpent) {
        this.txOutsCreated = txOutsCreated;
        this.txOutsSpent = txOutsSpent;
    }

    public TransactionOutputChanges(InputStream in) throws IOException {
        int numOutsCreated = in.read() & 255 | (in.read() & 255) << 8 | (in.read() & 255) << 16 | (in.read() & 255) << 24;
        this.txOutsCreated = new LinkedList<UTXO>();
        for (int i = 0; i < numOutsCreated; ++i) {
            this.txOutsCreated.add(new UTXO(in));
        }
        int numOutsSpent = in.read() & 255 | (in.read() & 255) << 8 | (in.read() & 255) << 16 | (in.read() & 255) << 24;
        this.txOutsSpent = new LinkedList<UTXO>();
        for (int i = 0; i < numOutsSpent; ++i) {
            this.txOutsSpent.add(new UTXO(in));
        }
    }

    public void serializeToStream(OutputStream bos) throws IOException {
        int numOutsCreated = this.txOutsCreated.size();
        bos.write(255 & numOutsCreated);
        bos.write(255 & numOutsCreated >> 8);
        bos.write(255 & numOutsCreated >> 16);
        bos.write(255 & numOutsCreated >> 24);
        for (UTXO output : this.txOutsCreated) {
            output.serializeToStream(bos);
        }
        int numOutsSpent = this.txOutsSpent.size();
        bos.write(255 & numOutsSpent);
        bos.write(255 & numOutsSpent >> 8);
        bos.write(255 & numOutsSpent >> 16);
        bos.write(255 & numOutsSpent >> 24);
        for (UTXO output : this.txOutsSpent) {
            output.serializeToStream(bos);
        }
    }
}

