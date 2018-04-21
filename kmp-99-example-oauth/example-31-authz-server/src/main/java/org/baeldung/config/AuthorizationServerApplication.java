package org.baeldung.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AuthorizationServerApplication extends SpringBootServletInitializer {

    @Bean
    public WebServerFactoryCustomizer<ConfigurableServletWebServerFactory>
    webServerFactoryCustomizer() {
        return factory -> {
            factory.setPort(8081);
            factory.setContextPath("/spring-security-oauth-server");
        };
    }

    public static void main(String[] args) {
        SpringApplication.run(AuthorizationServerApplication.class, args);
    }

}