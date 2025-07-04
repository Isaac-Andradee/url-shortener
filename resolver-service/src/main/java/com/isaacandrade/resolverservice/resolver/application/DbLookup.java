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
import com.isaacandrade.common.url.repository.UrlRepository;
import com.isaacandrade.resolverservice.exception.KeyNotFoundException;
import org.springframework.stereotype.Component;

/**
 * DbLookup is responsible for retrieving URL mappings from the database.
 * It uses a UrlRepository to access the database and fetch the mapping
 * associated with a given short key.
 *
 * @author Isaac Andrade
 */
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