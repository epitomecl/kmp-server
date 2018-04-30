package com.epitomecl.kmp.cc.main;

import com.epitomecl.kmp.cc.config.SwaggerConfig;
import com.epitomecl.kmp.dc.entity.hello.Customer;
import com.epitomecl.kmp.dc.repository.hello.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.gson.GsonAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.lang.invoke.MethodHandles;
import java.text.SimpleDateFormat;
import java.util.stream.Stream;

@SpringBootApplication
@EnableAutoConfiguration(exclude = {
        GsonAutoConfiguration.class
})
@ComponentScan(basePackageClasses = {
        SwaggerConfig.class
})
public class KmpApp implements CommandLineRunner {
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public static void main(String[] args) {
        new SpringApplicationBuilder()
                .sources(KmpApp.class)
                .build()
                .run(args);
    }


    //region for test
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    @Autowired
    DataSource dataSource;

    @Autowired
    CustomerRepository customerRepository;

    @Transactional(readOnly = true)
    @Override
    public void run(String... args) {

        logger.info("DATASOURCE = " + dataSource);

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
