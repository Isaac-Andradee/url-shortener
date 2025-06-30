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
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

import static com.isaacandrade.resolverservice.resolver.utils.ResolverConstants.CACHE_NAME;

@Component
public class CacheLookup {
    private final CacheManager cacheManager;

    public CacheLookup(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    public UrlMapping get(String shortKey) {
        Cache.ValueWrapper cachedKey = cacheManager.getCache(CACHE_NAME).get(shortKey);
        return cachedKey != null ? (UrlMapping) cachedKey.get() : null;
    }

    public void save(String shortKey, UrlMapping urlMapping) {
        cacheManager.getCache(CACHE_NAME).put(shortKey, urlMapping);
    }
}
