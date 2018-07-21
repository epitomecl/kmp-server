package com.epitomecl.kmp.blockexplorer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages = "com.epitomecl.kmp.blockexplorer")
public class BlockexplorerApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlockexplorerApplication.class, args);
    }
}
