/*
 * Decompiled with CFR 0_132.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Objects
 *  com.google.common.base.Preconditions
 *  com.google.common.io.BaseEncoding
 *  javax.annotation.Nullable
 */
package org.bitcoinj.script;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.io.BaseEncoding;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import javax.annotation.Nullable;
import org.bitcoinj.core.Utils;
import org.bitcoinj.script.Script;
import org.bitcoinj.script.ScriptOpCodes;

public class ScriptChunk {
    public final int opcode;
    @Nullable
    public final byte[] data;
    private int startLocationInProgram;

    public ScriptChunk(int opcode, byte[] data) {
        this(opcode, data, -1);
    }

    public ScriptChunk(int opcode, byte[] data, int startLocationInProgram) {
        this.opcode = opcode;
        this.data = data;
        this.startLocationInProgram = startLocationInProgram;
    }

    public boolean equalsOpCode(int opcode) {
        return opcode == this.opcode;
    }

    public boolean isOpCode() {
        return this.opcode > 78;
    }

    public boolean isPushData() {
        return this.opcode <= 96;
    }

    public int getStartLocationInProgram() {
        Preconditions.checkState((boolean)(this.startLocationInProgram >= 0));
        return this.startLocationInProgram;
    }

    public int decodeOpN() {
        Preconditions.checkState((boolean)this.isOpCode());
        return Script.decodeFromOpN(this.opcode);
    }

    public boolean isShortestPossiblePushData() {
        Preconditions.checkState((boolean)this.isPushData());
        if (this.data == null) {
            return true;
        }
        if (this.data.length == 0) {
            return this.opcode == 0;
        }
        if (this.data.length == 1) {
            byte b = this.data[0];
            if (b >= 1 && b <= 16) {
                return this.opcode == 81 + b - 1;
            }
            if ((b & 255) == 129) {
                return this.opcode == 79;
            }
        }
        if (this.data.length < 76) {
            return this.opcode == this.data.length;
        }
        if (this.data.length < 256) {
            return this.opcode == 76;
        }
        if (this.data.length < 65536) {
            return this.opcode == 77;
        }
        return this.opcode == 78;
    }

    public void write(OutputStream stream) throws IOException {
        if (this.isOpCode()) {
            Preconditions.checkState((boolean)(this.data == null));
            stream.write(this.opcode);
        } else if (this.data != null) {
            if (this.opcode < 76) {
                Preconditions.checkState((boolean)(this.data.length == this.opcode));
                stream.write(this.opcode);
            } else if (this.opcode == 76) {
                Preconditions.checkState((boolean)(this.data.length <= 255));
                stream.write(76);
                stream.write(this.data.length);
            } else if (this.opcode == 77) {
                Preconditions.checkState((boolean)(this.data.length <= 65535));
                stream.write(77);
                stream.write(255 & this.data.length);
                stream.write(255 & this.data.length >> 8);
            } else if (this.opcode == 78) {
                Preconditions.checkState((boolean)((long)this.data.length <= 520L));
                stream.write(78);
                Utils.uint32ToByteStreamLE(this.data.length, stream);
            } else {
                throw new RuntimeException("Unimplemented");
            }
            stream.write(this.data);
        } else {
            stream.write(this.opcode);
        }
    }

    public String toString() {
        StringBuilder buf = new StringBuilder();
        if (this.isOpCode()) {
            buf.append(ScriptOpCodes.getOpCodeName(this.opcode));
        } else if (this.data != null) {
            buf.append(ScriptOpCodes.getPushDataName(this.opcode)).append("[").append(Utils.HEX.encode(this.data)).append("]");
        } else {
            buf.append(Script.decodeFromOpN(this.opcode));
        }
        return buf.toString();
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        ScriptChunk other = (ScriptChunk)o;
        return this.opcode == other.opcode && this.startLocationInProgram == other.startLocationInProgram && Arrays.equals(this.data, other.data);
    }

    public int hashCode() {
        return Objects.hashCode((Object[])new Object[]{this.opcode, this.startLocationInProgram, Arrays.hashCode(this.data)});
    }
}

