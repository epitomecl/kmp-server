package com.epitomecl.kmp.cc.main;

import com.epitomecl.kmp.cc.common.HomeConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

import java.lang.invoke.MethodHandles;
import java.net.InetAddress;


public class MainApp {
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public static void main(String[] args) {
        MainApp mainApp = new MainApp();
        mainApp.start(args);
    }

    private SpringApplication app;
    private ConfigurableApplicationContext ctx;

    public MainApp() {
        HomeConfigurator.changeLogConfiguration();

        app = new SpringApplicationBuilder()
                .sources(KmpApp.class)
                .build();
    }

    public void start(String[] args) {
        ctx = app.run(args);

        Environment env = ctx.getEnvironment();
        String protocol = "http";
        if (env.getProperty("server.ssl.key-store") != null) {
            protocol = "https";
        }
        String spring_application_name = ""; // env.getProperty("spring.application.name");
        String server_port = env.getProperty("server.port");
        String[] activeProfiles = env.getActiveProfiles();

        String hostAddress = "localhost";
        try {
            hostAddress = InetAddress.getLocalHost().getHostAddress();
        } catch (Exception e) {
            logger.warn("The host name could not be determined, using `localhost` as fallback");
        }
        logger.info("\n----------------------------------------------------------\n\t" +
                        "Application '{}' is running! Access URLs:\n\t" +
                        "Local: \t\t{}://localhost:{}/swagger-ui.html\n\t" +
                        "External: \t{}://{}:{}/swagger-ui.html\n\t" +
                        "Profile(s): \t{}\n----------------------------------------------------------",
                spring_application_name,
                protocol,
                server_port,
                protocol,
                hostAddress,
                server_port,
                activeProfiles);
    }

    public void stop() {
        SpringApplication.exit(ctx);
    }

}
