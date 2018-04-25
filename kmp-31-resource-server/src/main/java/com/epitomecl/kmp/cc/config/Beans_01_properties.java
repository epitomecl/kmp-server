package com.epitomecl.kmp.cc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Configuration
public class Beans_01_properties {

    @Bean
    public static PaprikaPropertiesFactoryBean paprikaPropertiesFactoryBean() {
        return new PaprikaPropertiesFactoryBean();
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigurer() {
        PropertySourcesPlaceholderConfigurer propertyConfigurer = new PropertySourcesPlaceholderConfigurer();
        propertyConfigurer.setProperties(paprikaPropertiesFactoryBean().getObject());
        return propertyConfigurer;
    }

}
