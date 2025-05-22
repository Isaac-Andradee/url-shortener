package com.isaacandrade.urlshortenerservice.integration_tests.urlshort.application;

import com.isaacandrade.common.url.model.UrlMapping;
import com.isaacandrade.common.url.repository.UrlRepository;
import com.isaacandrade.urlshortenerservice.integration_tests.urlshort.config.BaseIntegrationTest;
import com.isaacandrade.urlshortenerservice.integration_tests.urlshort.config.EmbeddedRedisClusterConfig;
import com.isaacandrade.urlshortenerservice.urlshort.infra.DB.DbCacheSaver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles("test")
@Import(EmbeddedRedisClusterConfig.class)
public class DbCacheSaverIntegrationTests extends BaseIntegrationTest {

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
