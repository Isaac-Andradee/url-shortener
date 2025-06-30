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

import com.isaacandrade.urlshortenerservice.urlshort.exception.AliasInvalidFormatException;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class AliasFormatValidator implements AliasValidator{
    private static final Pattern PATTERN =  Pattern.compile("^[A-Za-z0-9](?!.*[_-]{2})[A-Za-z0-9_-]{1,18}[A-Za-z0-9]$");

    @Override
    public void validate(String alias) {
        if(!Strings.isBlank(alias) && !PATTERN.matcher(alias).matches())
            throw new AliasInvalidFormatException("Invalid Alias Format");
    }
}