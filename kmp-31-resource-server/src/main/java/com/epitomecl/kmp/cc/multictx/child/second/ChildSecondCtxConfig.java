package com.epitomecl.kmp.cc.multictx.child.second;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import java.io.IOException;
import java.util.Properties;

@SpringBootApplication
public class ChildSecondCtxConfig {

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigurer() throws IOException {
        PropertySourcesPlaceholderConfigurer propertyConfigurer = new PropertySourcesPlaceholderConfigurer();
        propertyConfigurer.setProperties(new Properties() {{
            setProperty("custom.property.second", "prop_second");
        }});
        return propertyConfigurer;
    }

    @Bean
    public WebServerFactoryCustomizer<ConfigurableServletWebServerFactory>
    webServerFactoryCustomizer() {
        return factory -> {
            factory.setPort(8081);
            factory.setContextPath("/second");
        };
    }

    @Bean(name = "child_second_bean")
    public String getChildSecondBean() {
        return "child_second_bean";
    }
}
