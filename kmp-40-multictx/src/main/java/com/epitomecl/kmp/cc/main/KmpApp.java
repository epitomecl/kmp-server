package com.epitomecl.kmp.cc.main;

import com.epitomecl.kmp.blockexplorer.config.PrimaryDbConfig;
import com.epitomecl.kmp.blockexplorer.config.SwaggerConfig;
import com.epitomecl.kmp.blockexplorer.dao.BlockExplorerDAOImpl;
import com.epitomecl.kmp.blockexplorer.service.BlockExplorerServiceImpl;
import com.epitomecl.kmp.cc.config.Beans_01_properties;
import com.epitomecl.kmp.dc.primary.entity.Customer;
import com.epitomecl.kmp.dc.primary.repository.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.gson.GsonAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.lang.invoke.MethodHandles;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.stream.Stream;

@SpringBootApplication
@EnableAutoConfiguration(exclude = {
        GsonAutoConfiguration.class
})
@ComponentScan(basePackageClasses = {
        Beans_01_properties.class,
        PrimaryDbConfig.class,
        SwaggerConfig.class,
        CustomerRepository.class,
        BlockExplorerDAOImpl.class,
        BlockExplorerServiceImpl.class
})
public class KmpApp implements CommandLineRunner {
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public static void main(String[] args) {
        KmpApp kmpApp = new KmpApp();
        kmpApp.start(args);
    }

    private SpringApplication app;
    private ConfigurableApplicationContext ctx;

    public KmpApp() {
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


    //region for test
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    @Autowired
    DataSource defaultDataSource;

    @Autowired
    CustomerRepository customerRepository;

    @Transactional(readOnly = true)
    @Override
    public void run(String... args) {

        logger.info("DATASOURCE = " + defaultDataSource);

        logger.info("1.findAll()...");
        for (Customer customer : customerRepository.findAll()) {
            logger.info("{}", customer);
        }

        logger.info("2.findByEmail(String email)...");
        for (Customer customer : customerRepository.findByEmail("222@yahoo.com")) {
            logger.info("{}", customer);
        }

        // For Stream, need @Transactional
        logger.info("3.findByEmailReturnStream(@Param(\"email\") String email)...");
        try (Stream<Customer> stream = customerRepository.findByEmailReturnStream("333@yahoo.com")) {
            stream.forEach(x -> logger.info("{}", x));
        }

        logger.info("Done!");
    }
    //end-region

}
