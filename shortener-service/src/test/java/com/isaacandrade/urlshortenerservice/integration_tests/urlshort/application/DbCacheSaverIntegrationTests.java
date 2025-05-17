package com.isaacandrade.urlshortenerservice.integration_tests.urlshort.application;

import com.isaacandrade.common.url.model.UrlMapping;
import com.isaacandrade.common.url.repository.UrlRepository;
import com.isaacandrade.urlshortenerservice.urlshort.infra.DB.DbCacheSaver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Testcontainers
@ActiveProfiles("test")
public class DbCacheSaverIntegrationTests {

    @Container
    static MongoDBContainer mongo = new MongoDBContainer("mongo:latest").withReuse(true);

    @DynamicPropertySource
    static void mongoProps(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", mongo::getReplicaSetUrl);
    }

    @Container
    static GenericContainer<?> redis = new GenericContainer<>("redis:latest")
            .withExposedPorts(6379);

    @DynamicPropertySource
    static void redisProps(DynamicPropertyRegistry registry) {
        registry.add("spring.data.redis.host", redis::getHost);
        registry.add("spring.data.redis.port", () -> redis.getMappedPort(6379));
    }

    @Autowired
    private UrlRepository repo;
    @Autowired
    private DbCacheSaver saver;

    @BeforeEach
    void clean() {
        repo.deleteAll();
    }

    @Test
    void shouldSaveUrlMapping() {
        UrlMapping mapping = new UrlMapping("abc123", "https://a.com", null);
        saver.saveUrlMapping(mapping);
        UrlMapping found = repo.findById("abc123").orElseThrow();
        assertEquals("https://a.com", found.getLongUrl());
    }
}
