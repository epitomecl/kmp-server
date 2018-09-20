/*
 * Decompiled with CFR 0_132.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Preconditions
 *  com.google.common.collect.Maps
 *  com.google.protobuf.ByteString
 *  javax.annotation.Nullable
 */
package org.bitcoinj.utils;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import com.google.protobuf.ByteString;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Nullable;
import org.bitcoinj.utils.TaggableObject;

public class BaseTaggableObject
implements TaggableObject {
    @Nullable
    protected Map<String, ByteString> tags;

    @Nullable
    @Override
    public synchronized ByteString maybeGetTag(String tag) {
        if (this.tags == null) {
            return null;
        }
        return this.tags.get(tag);
    }

    @Override
    public ByteString getTag(String tag) {
        ByteString b = this.maybeGetTag(tag);
        if (b == null) {
            throw new IllegalArgumentException("Unknown tag " + tag);
        }
        return b;
    }

    @Override
    public synchronized void setTag(String tag, ByteString value) {
        Preconditions.checkNotNull((Object)tag);
        Preconditions.checkNotNull((Object)value);
        if (this.tags == null) {
            this.tags = new HashMap<String, ByteString>();
        }
        this.tags.put(tag, value);
    }

    @Override
    public synchronized Map<String, ByteString> getTags() {
        if (this.tags != null) {
            return Maps.newHashMap(this.tags);
        }
        return Maps.newHashMap();
    }
}

