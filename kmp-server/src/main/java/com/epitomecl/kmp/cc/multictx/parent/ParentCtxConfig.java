package com.epitomecl.kmp.cc.multictx.parent;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ParentCtxConfig {

    @Bean(name = "parent_bean")
    public String getParentBean() {
        return "parent_bean";
    }
}
