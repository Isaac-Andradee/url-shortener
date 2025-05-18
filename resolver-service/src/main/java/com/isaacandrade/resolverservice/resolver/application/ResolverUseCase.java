package com.isaacandrade.resolverservice.resolver.application;

import com.isaacandrade.common.url.model.UrlMapping;
import org.springframework.stereotype.Service;

@Service
public class ResolverUseCase {

   private final CacheLookup cacheLookup;
   private final DbLookup dbLookup;

    public ResolverUseCase(CacheLookup cacheLookup, DbLookup dbLookup) {
        this.cacheLookup = cacheLookup;
        this.dbLookup = dbLookup;
    }

    public String resolve(String shortKey) {
        UrlMapping longUrl = getFromCache(shortKey);
        if (longUrl == null) {
            longUrl = getFromDb(shortKey);
            saveInCache(shortKey, longUrl);
        }
        return longUrl.getLongUrl();
    }

    private UrlMapping getFromCache(String shortKey) {
        return cacheLookup.get(shortKey);
    }

    private UrlMapping getFromDb(String shortKey) {
        return dbLookup.getFromDb(shortKey);
    }

    private void saveInCache(String shortKey, UrlMapping urlMapping) {
        cacheLookup.save(shortKey, urlMapping);
    }
}