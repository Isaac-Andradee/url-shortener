package com.isaacandrade.urlshortenerservice.web.keygen;


import com.isaacandrade.urlshortenerservice.urlshort.exception.KeygenServiceUnvailableException;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

@Component
public class KeyGenClient {
    private final WebClient webClient;

    public KeyGenClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public String generateKey() {
        return callKeygenService("/generate")
                .orElseThrow(() -> new KeygenServiceUnvailableException("Keygen Service Is Unavailable"));
    }

    private Optional<String> callKeygenService(String url) {
        return Optional.ofNullable(webClient.get()
                .uri(url)
                .retrieve()
                .bodyToMono(Map.class)
                .map(response -> (String) response.get("shortKey"))
                .block());
    }
}
