package com.isaacandrade.url_shortener.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.CacheManager;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class CacheService {
    private final CacheManager cacheManager;

    public CacheService(@Qualifier("cacheManager")CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    public void save(String key, String value) {
        cacheManager.getCache("urlCache").put(key, value);
    }

    public String get(String key) {
        return cacheManager.getCache("urlCache").get(key, String.class);
    }

    public void remove(String key) {
        cacheManager.getCache("urlCache").evict(key);
    }
}
