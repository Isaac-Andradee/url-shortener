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
package com.isaacandrade.common.url.model.dto;

import java.io.Serializable;

/**
 * Represents a request to shorten a URL.
 * This record encapsulates the long URL to be shortened and an optional alias for the shortened URL.
 *
 * @param longUrl The original long URL that needs to be shortened.
 * @param alias   An optional alias for the shortened URL, which can be used instead of a generated key.
 * @author Isaac Andrade
 */
public record ShortenRequest(
        String longUrl,
        String alias
) implements Serializable {
}
