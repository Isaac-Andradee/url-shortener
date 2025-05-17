package com.isaacandrade.urlshortenerservice.integration_tests.urlshort.application;

import com.isaacandrade.common.url.model.UrlMapping;
import com.isaacandrade.common.url.repository.UrlRepository;
import com.isaacandrade.urlshortenerservice.urlshort.application.AliasValidator;
import com.isaacandrade.urlshortenerservice.urlshort.exception.AliasNotAvailableException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Testcontainers
@ActiveProfiles("test")
public class AliasValidatorIntegrationTest {
    @Container
    static MongoDBContainer mongo = new MongoDBContainer("mongo:7.0").withReuse(true);

    @DynamicPropertySource
    static void mongoProps(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", mongo::getReplicaSetUrl);
    }

    @Autowired
    AliasValidator aliasValidator;

    @Autowired
    UrlRepository urlRepository;

    @BeforeEach
    void setup() {
        urlRepository.deleteAll();
    }

    @Test
    void shouldThrowWhenAliasExists() {
        urlRepository.save(new UrlMapping("alias123", "https://x.com", "alias123"));
        assertThrows(AliasNotAvailableException.class, () -> aliasValidator.validateIfExistInDb("alias123"));
    }

    @Test
    void shouldNotThrowWhenAliasIsNew() {
        assertDoesNotThrow(() -> aliasValidator.validateIfExistInDb("newAlias"));
    }

    @Test
    void shouldReturnTrueForNonBlankAlias() {
        assertTrue(aliasValidator.isAliasOnRequest("alias"));
    }

    @Test
    void shouldReturnFalseForBlankAlias() {
        assertFalse(aliasValidator.isAliasOnRequest(" "));
    }
}