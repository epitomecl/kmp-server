package com.epitomecl.kmp.cc.multictx.child.first;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import java.io.IOException;
import java.util.Properties;

@SpringBootApplication
public class ChildFirstCtxConfig {

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigurer() throws IOException {
        PropertySourcesPlaceholderConfigurer propertyConfigurer = new PropertySourcesPlaceholderConfigurer();
        propertyConfigurer.setProperties(new Properties() {{
            setProperty("custom.property.first", "prop_first");
        }});
        return propertyConfigurer;
    }

    @Bean
    public WebServerFactoryCustomizer<ConfigurableServletWebServerFactory>
    webServerFactoryCustomizer() {
        return factory -> {
            factory.setPort(8080);
            factory.setContextPath("/first");
        };
    }

    @Bean(name = "child_first_bean")
    public String getChildFirstBean() {
        return "child_first_bean";
    }
}
