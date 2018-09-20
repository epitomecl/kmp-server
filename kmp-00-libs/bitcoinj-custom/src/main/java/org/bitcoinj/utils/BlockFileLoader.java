/*
 * Decompiled with CFR 0_132.
 */
package org.bitcoinj.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.NoSuchElementException;
import org.bitcoinj.core.Block;
import org.bitcoinj.core.MessageSerializer;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.core.ProtocolException;
import org.bitcoinj.core.Utils;

public class BlockFileLoader
implements Iterable<Block>,
Iterator<Block> {
    private Iterator<File> fileIt;
    private FileInputStream currentFileStream = null;
    private Block nextBlock = null;
    private NetworkParameters params;

    public static List<File> getReferenceClientBlockFileList() {
        File file;
        String OS = System.getProperty("os.name").toLowerCase();
        String defaultDataDir = OS.indexOf("win") >= 0 ? System.getenv("APPDATA") + "\\.bitcoin\\blocks\\" : (OS.indexOf("mac") >= 0 || OS.indexOf("darwin") >= 0 ? System.getProperty("user.home") + "/Library/Application Support/Bitcoin/blocks/" : System.getProperty("user.home") + "/.bitcoin/blocks/");
        LinkedList<File> list = new LinkedList<File>();
        int i = 0;
        while ((file = new File(defaultDataDir + String.format(Locale.US, "blk%05d.dat", i))).exists()) {
            list.add(file);
            ++i;
        }
        return list;
    }

    public BlockFileLoader(NetworkParameters params, List<File> files) {
        this.fileIt = files.iterator();
        this.params = params;
    }

    @Override
    public boolean hasNext() {
        if (this.nextBlock == null) {
            this.loadNextBlock();
        }
        return this.nextBlock != null;
    }

    @Override
    public Block next() throws NoSuchElementException {
        if (!this.hasNext()) {
            throw new NoSuchElementException();
        }
        Block next = this.nextBlock;
        this.nextBlock = null;
        return next;
    }

    private void loadNextBlock() {
        do {
            block17 : {
                try {
                    if (this.fileIt.hasNext()) break block17;
                    if (this.currentFileStream == null) break;
                    if (this.currentFileStream.available() < 1) {
                    }
                    break block17;
                }
                catch (IOException e) {
                    this.currentFileStream = null;
                    if (this.fileIt.hasNext()) break block17;
                }
                break;
            }
            do {
                try {
                    if (this.currentFileStream != null && this.currentFileStream.available() > 0) {
                        break;
                    }
                }
                catch (IOException e1) {
                    this.currentFileStream = null;
                }
                if (!this.fileIt.hasNext()) {
                    this.nextBlock = null;
                    this.currentFileStream = null;
                    return;
                }
                try {
                    this.currentFileStream = new FileInputStream(this.fileIt.next());
                }
                catch (FileNotFoundException e) {
                    this.currentFileStream = null;
                }
            } while (true);
            try {
                int nextChar = this.currentFileStream.read();
                while (nextChar != -1) {
                    if ((long)nextChar != (this.params.getPacketMagic() >>> 24 & 255L)) {
                        nextChar = this.currentFileStream.read();
                        continue;
                    }
                    nextChar = this.currentFileStream.read();
                    if ((long)nextChar != (this.params.getPacketMagic() >>> 16 & 255L) || (long)(nextChar = this.currentFileStream.read()) != (this.params.getPacketMagic() >>> 8 & 255L) || (long)(nextChar = this.currentFileStream.read()) != (this.params.getPacketMagic() & 255L)) continue;
                }
                byte[] bytes = new byte[4];
                this.currentFileStream.read(bytes, 0, 4);
                long size = Utils.readUint32BE(Utils.reverseBytes(bytes), 0);
                if (size > 2000000L || size <= 0L) continue;
                bytes = new byte[(int)size];
                this.currentFileStream.read(bytes, 0, (int)size);
                try {
                    this.nextBlock = this.params.getDefaultSerializer().makeBlock(bytes);
                    break;
                }
                catch (ProtocolException e) {
                    this.nextBlock = null;
                }
            }
            catch (IOException e) {
                this.currentFileStream = null;
            }
        } while (true);
    }

    @Override
    public void remove() throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<Block> iterator() {
        return this;
    }
}

