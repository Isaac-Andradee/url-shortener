package com.isaacandrade.urlshortenerservice.web.keygen;


import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@Component
public class KeyGenClient {
    private final WebClient webClient;

    public KeyGenClient(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:8085").build();
    }

    public String generateKey() {
        return webClient.get()
                .uri("/generate")
                .retrieve()
                .bodyToMono(Map.class)
                .map(response -> (String) response.get("shortKey"))
                .block();
    }
}
