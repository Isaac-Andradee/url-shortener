package com.isaacandrade.urlshortenerservice.urlshort.infra.DB;

import com.isaacandrade.common.url.model.UrlMapping;
import com.isaacandrade.common.url.repository.UrlRepository;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

@Component
public class DbCacheSaver {
    private final UrlRepository urlRepository;
    private final CacheManager cacheManager;

    public DbCacheSaver(UrlRepository urlRepository, CacheManager cacheManager) {
        this.urlRepository = urlRepository;
        this.cacheManager = cacheManager;
    }

    public void saveUrlMapping(UrlMapping urlMapping) {
        urlRepository.save(urlMapping);
        cacheManager.getCache("keys").put(urlMapping.getShortKey(), urlMapping);
    }
}
