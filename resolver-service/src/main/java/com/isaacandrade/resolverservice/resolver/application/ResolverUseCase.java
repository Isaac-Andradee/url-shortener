/*
 * Copyright 2025 the original author.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.isaacandrade.resolverservice.resolver.application;

import com.isaacandrade.common.url.model.UrlMapping;
import org.springframework.stereotype.Service;

/**
 * ResolverUseCase is responsible for resolving short URLs to their long counterparts.
 * It first checks the cache for the mapping and, if not found, queries the database.
 * The resolved URL is then saved back into the cache for future requests.
 *
 * @author Isaac Andrade
 */
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