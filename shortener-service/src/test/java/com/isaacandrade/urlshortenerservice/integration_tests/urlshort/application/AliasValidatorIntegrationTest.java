package com.isaacandrade.urlshortenerservice.integration_tests.urlshort.application;

import com.isaacandrade.common.url.model.UrlMapping;
import com.isaacandrade.common.url.repository.UrlRepository;
import com.isaacandrade.urlshortenerservice.integration_tests.urlshort.config.BaseIntegrationTest;
import com.isaacandrade.urlshortenerservice.urlshort.application.AliasValidator;
import com.isaacandrade.urlshortenerservice.urlshort.exception.AliasNotAvailableException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
public class AliasValidatorIntegrationTest extends BaseIntegrationTest {

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
