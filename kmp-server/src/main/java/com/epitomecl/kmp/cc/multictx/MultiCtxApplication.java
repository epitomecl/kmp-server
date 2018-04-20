package com.epitomecl.kmp.cc.multictx;

import com.epitomecl.kmp.cc.multictx.child.first.ChildFirstCtxConfig;
import com.epitomecl.kmp.cc.multictx.child.second.ChildSecondCtxConfig;
import com.epitomecl.kmp.cc.multictx.parent.ParentCtxConfig;
import org.springframework.boot.builder.SpringApplicationBuilder;

public class MultiCtxApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder()
                .sources(ParentCtxConfig.class)
                .child(ChildFirstCtxConfig.class)
                .sibling(ChildSecondCtxConfig.class)
                .run(args);
    }
}
