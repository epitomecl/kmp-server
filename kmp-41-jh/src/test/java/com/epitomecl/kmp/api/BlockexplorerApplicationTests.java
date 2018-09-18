package com.epitomecl.kmp.api;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.invoke.MethodHandles;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BlockexplorerApplicationTests {
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void test_01_wallet_create() {
        logger.info("test_01_wallet_create");
        String body = this.restTemplate.getForObject("/", String.class);
        assertThat(body).isEqualTo("Hello World");
    }

    @Test
    public void test_02_wallet_read() {
        logger.info("test_02_wallet_read");
    }

    @Test
    public void test_03_wallet_update() {
        logger.info("test_03_wallet_update");
    }

    @Test
    public void test_04_wallet_delete() {
        logger.info("test_04_wallet_delete");
    }

    @Test
    public void test_05_seed_backup() {
        logger.info("test_05_seed_backup");
    }

    @Test
    public void test_06_seed_restore() {
        logger.info("test_06_seed_restore");
    }

}
