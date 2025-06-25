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
