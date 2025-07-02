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
package com.isaacandrade.urlshortenerservice.urlshort.application;

import com.isaacandrade.common.url.model.UrlMapping;
import com.isaacandrade.urlshortenerservice.config.ShortenerProperties;
import com.isaacandrade.common.url.model.dto.ShortenResponse;
import com.isaacandrade.urlshortenerservice.urlshort.infra.DB.DbCacheSaver;
import com.isaacandrade.common.url.model.dto.ShortenRequest;
import org.springframework.stereotype.Service;

/**
 * Use case for shortening URLs.
 * This service handles the logic for creating a short URL from a long URL and an optional alias.
 * It validates the alias, generates a short key, builds a URL mapping, and persists it in the database.
 *
 * @author Isaac Andrade
 */
@Service
public class ShortenerUseCase {

    private final ShortenerProperties domainProperties;
    private final AliasValidationComposite aliasValidation;
    private final KeyGenResolver keyGenResolver;
    private final DbCacheSaver dbCacheSaver;

    public ShortenerUseCase(ShortenerProperties domainProperties, AliasValidationComposite aliasValidation, KeyGenResolver keyGenResolver, DbCacheSaver dbCacheSaver) {
        this.domainProperties = domainProperties;
        this.aliasValidation = aliasValidation;
        this.keyGenResolver = keyGenResolver;
        this.dbCacheSaver = dbCacheSaver;
    }

    public ShortenResponse shorten(ShortenRequest request) {
        validateAlias(request.alias());
        String shortKey = generateShortKey(request.alias());
        UrlMapping urlMapping = buildMapping(shortKey, request);
        persist(urlMapping);
        return buildShortUrl(shortKey);
    }

    private void validateAlias(String alias) {
        aliasValidation.validate(alias);
    }

    private String generateShortKey(String alias) {
        return keyGenResolver.resolveShortKey(alias);
    }

    private UrlMapping buildMapping(String shortKey, ShortenRequest request) {
        return new UrlMapping(shortKey, request.longUrl(), request.alias());
    }

    private void persist(UrlMapping urlMapping) {
        dbCacheSaver.saveUrlMapping(urlMapping);
    }

    private ShortenResponse buildShortUrl(String shortKey) {
        return new ShortenResponse( domainProperties + shortKey);
    }
}
