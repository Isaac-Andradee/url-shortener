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

import com.isaacandrade.common.url.repository.UrlRepository;
import com.isaacandrade.urlshortenerservice.urlshort.exception.AliasNotAvailableException;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Component;

/** * Validates the availability of an alias for URL shortening.
 * If the alias is not blank and already exists in the repository,
 * an AliasNotAvailableException is thrown.
 * @author Isaac Andrade
 */
@Component
public class AliasAvailabilityValidator implements AliasValidator{

    private final UrlRepository urlRepository;

    public AliasAvailabilityValidator(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    @Override
    public void validate(String alias) {
        if(!Strings.isBlank(alias) && urlRepository.findById(alias).isPresent())
            throw new AliasNotAvailableException("Alias " + alias +" is not available");
    }
}
