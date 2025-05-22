package com.isaacandrade.urlshortenerservice.unit_tests.urlshort.infra.DB;

import com.isaacandrade.common.url.model.UrlMapping;
import com.isaacandrade.common.url.repository.UrlRepository;
import com.isaacandrade.urlshortenerservice.urlshort.infra.DB.DbCacheSaver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DbCacheSaverTest {

    @Mock
    private UrlRepository urlRepository;

    @Mock
    private CacheManager cacheManager;

    @Mock
    private Cache cache;

    private DbCacheSaver dbCacheSaver;

    @BeforeEach
    void setUp() {
        when(cacheManager.getCache("keys")).thenReturn(cache);
        dbCacheSaver = new DbCacheSaver(urlRepository, cacheManager);
    }

    @Test
    void shouldSaveUrlMappingToDatabaseAndCache() {
        UrlMapping urlMapping = new UrlMapping("abc123", "https://example.com", "exemplo");
        dbCacheSaver.saveUrlMapping(urlMapping);
        ArgumentCaptor<UrlMapping> urlMappingCaptor = ArgumentCaptor.forClass(UrlMapping.class);
        verify(urlRepository).save(urlMappingCaptor.capture());
        UrlMapping savedMapping = urlMappingCaptor.getValue();
        assertEquals("abc123", savedMapping.getShortKey());
        assertEquals("https://example.com", savedMapping.getLongUrl());
        assertEquals("exemplo", savedMapping.getAlias());
        verify(cache).put("abc123", urlMapping);
    }
} 