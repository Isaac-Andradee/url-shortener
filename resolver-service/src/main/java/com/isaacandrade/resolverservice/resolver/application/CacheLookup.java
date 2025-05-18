package com.isaacandrade.resolverservice.resolver.application;

import com.isaacandrade.common.url.model.UrlMapping;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

@Component
public class CacheLookup {
    private final CacheManager cacheManager;

    public CacheLookup(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    public UrlMapping get(String shortKey) {
        Cache.ValueWrapper cachedKey = cacheManager.getCache("keys").get(shortKey);
        return cachedKey != null ? (UrlMapping) cachedKey.get() : null;
    }

    public void save(String shortKey, UrlMapping urlMapping) {
        cacheManager.getCache("keys").put(shortKey, urlMapping);
    }
}
