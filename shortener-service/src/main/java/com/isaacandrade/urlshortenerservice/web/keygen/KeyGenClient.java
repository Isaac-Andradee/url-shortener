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
package com.isaacandrade.urlshortenerservice.web.keygen;


import com.isaacandrade.urlshortenerservice.urlshort.exception.KeygenServiceUnvailableException;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;
import java.util.Optional;

@Component
public class KeyGenClient {
    private final WebClient webClient;

    public KeyGenClient(@LoadBalanced WebClient.Builder builder) {
        this.webClient = builder.build();
    }

    public String generateKey() {
        return callKeygenService()
                .orElseThrow(() -> new KeygenServiceUnvailableException("Keygen Service Is Unavailable"));
    }

    private Optional<String> callKeygenService() {
        try {
            return Optional.ofNullable(webClient.get()
                    .uri("http://keygen-service/generate")
                    .retrieve()
                    .bodyToMono(Map.class)
                    .map(response -> (String) response.get("shortKey"))
                    .block());
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
