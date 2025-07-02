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

import com.isaacandrade.urlshortenerservice.web.keygen.KeyGenClient;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * Resolver for generating or resolving short keys.
 * This component uses a KeyGenClient to generate a new key if the provided alias is empty.
 * It is used to ensure that every URL has a valid short key.
 *
 * @author Isaac Andrade
 */
@Component
public class KeyGenResolver {
    private final KeyGenClient keyGenClient;

    public KeyGenResolver(KeyGenClient keyGenClient) {
        this.keyGenClient = keyGenClient;
    }
    public String resolveShortKey(String alias) {
        return StringUtils.hasText(alias) ? alias : keyGenClient.generateKey();
    }
}
