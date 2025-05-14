package com.isaacandrade.urlshortenerservice.url.application;

import com.isaacandrade.common.url.model.UrlMapping;
import com.isaacandrade.common.url.repository.UrlRepository;
import com.isaacandrade.urlshortenerservice.url.application.dto.ShortenRequest;
import com.isaacandrade.urlshortenerservice.web.keygen.KeyGenClient;
import org.apache.logging.log4j.util.Strings;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class ShortenerUseCase {

    private final UrlRepository urlRepository;
    private final CacheManager cacheManager;
    private final KeyGenClient keyGenClient;

    public ShortenerUseCase(UrlRepository urlRepository, CacheManager cacheManager, KeyGenClient keyGenClient) {
        this.urlRepository = urlRepository;
        this.cacheManager = cacheManager;
        this.keyGenClient = keyGenClient;
    }


    public String shorten(ShortenRequest request) {
        // alias validate
        String alias = request.alias();
        if(!Strings.isBlank(alias) && Objects.requireNonNull(cacheManager.getCache("keys")).get(alias) != null) {
            throw new IllegalArgumentException("Alias Not Available");
        }
        // make keygen
        String shortKey;
        if(Strings.isBlank(alias)) {
           shortKey = keyGenClient.generateKey();
        } else {
            shortKey = alias;
        }
        // save to db and cache
        UrlMapping urlMapping = new UrlMapping(shortKey, request.longUrl());
        urlRepository.save(urlMapping);
        cacheManager.getCache("keys").put(shortKey, urlMapping);

        // returns shortKey
        return shortKey;
    }
}
