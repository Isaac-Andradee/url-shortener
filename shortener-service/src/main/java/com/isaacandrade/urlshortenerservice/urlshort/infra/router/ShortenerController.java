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
package com.isaacandrade.urlshortenerservice.urlshort.infra.router;

import com.isaacandrade.urlshortenerservice.urlshort.annotations.ShortenUrlOperation;
import com.isaacandrade.urlshortenerservice.urlshort.application.ShortenerUseCase;
import com.isaacandrade.common.url.model.dto.ShortenRequest;
import com.isaacandrade.common.url.model.dto.ShortenResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** * Controller for handling URL shortening requests.
 * This controller processes incoming requests to shorten URLs and returns the shortened URL in the response.
 *
 * @author Isaac Andrade
 */
@RestController
@RequestMapping("/shorten")
public class ShortenerController {

    private final ShortenerUseCase shortenerUseCase;

    public ShortenerController(ShortenerUseCase shortenerUseCase) {
        this.shortenerUseCase = shortenerUseCase;
    }

    @PostMapping
    @ShortenUrlOperation
    public ResponseEntity<ShortenResponse> shortenUrl(@RequestBody ShortenRequest request) {
        ShortenResponse shortUrl = shortenerUseCase.shorten(request);
        return ResponseEntity.ok(shortUrl);
    }
}
