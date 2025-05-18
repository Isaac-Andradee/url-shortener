package com.isaacandrade.urlshortenerservice.integration_tests.urlshort.web;

import com.isaacandrade.urlshortenerservice.web.keygen.KeyGenClient;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.reactive.function.client.WebClient;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Testcontainers
@SpringBootTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class KeyGenClientIntegrationTest {

    private KeyGenClient client;

    @BeforeAll
    void setUp() {
        String host = "localhost";
        Integer port = 8085;
        WebClient clientBuilder = WebClient.builder()
                .baseUrl("http://" + host + ":" + port)
                .build();
        client = new KeyGenClient(clientBuilder);
    }

    @Test
    void shouldCallKeyGenAndReturnKey() {
        String key = client.generateKey();
        assertNotNull(key);
        assertFalse(key.isBlank());
    }
}
