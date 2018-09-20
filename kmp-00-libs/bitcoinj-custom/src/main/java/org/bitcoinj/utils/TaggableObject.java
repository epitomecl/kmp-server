/*
 * Decompiled with CFR 0_132.
 * 
 * Could not load the following classes:
 *  com.google.protobuf.ByteString
 *  javax.annotation.Nullable
 */
package org.bitcoinj.utils;

import com.google.protobuf.ByteString;
import java.util.Map;
import javax.annotation.Nullable;

public interface TaggableObject {
    @Nullable
    public ByteString maybeGetTag(String var1);

    public ByteString getTag(String var1);

    public void setTag(String var1, ByteString var2);

    public Map<String, ByteString> getTags();
}

