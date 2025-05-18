package com.isaacandrade.resolverservice.resolver.application;

import com.isaacandrade.common.url.model.UrlMapping;
import com.isaacandrade.common.url.repository.UrlRepository;
import com.isaacandrade.resolverservice.exception.KeyNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class DbLookup {
    private final UrlRepository urlRepository;

    public DbLookup(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    public UrlMapping getFromDb(String shortKey) {
        return urlRepository.findById(shortKey).
                orElseThrow(() -> new KeyNotFoundException("Key " + shortKey +" Not Found"));
    }
}