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
package com.isaacandrade.resolverservice.infra.router;

import com.isaacandrade.resolverservice.resolver.annotations.ResolverUrlOperation;
import com.isaacandrade.resolverservice.resolver.application.ResolverUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

/** * Controller for handling URL resolution requests.
 * This controller maps incoming requests to the resolver use case, which resolves short keys to long URLs.
 * It returns a 307 redirect response with the resolved long URL.
 *
 * @author Isaac Andrade
 */
@RestController
@RequestMapping("/")
public class ResolverController {

    private final ResolverUseCase resolverUseCase;

    public ResolverController(ResolverUseCase resolverUseCase) {
        this.resolverUseCase = resolverUseCase;
    }

    @GetMapping("/{shortKey}")
    @ResolverUrlOperation
    public ResponseEntity<Void> resolve(@PathVariable String shortKey) {
        String longUrl = resolverUseCase.resolve(shortKey);
        return ResponseEntity.status(307)
                .location(URI.create(longUrl))
                .build();
    }
}
