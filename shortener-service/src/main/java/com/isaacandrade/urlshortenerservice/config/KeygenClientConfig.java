package com.isaacandrade.urlshortenerservice.config;

import com.isaacandrade.urlshortenerservice.web.keygen.KeyGenClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class KeygenClientConfig {
    @Value("${keygen.base_url}")
    private String baseUrl;

    @Bean
    public WebClient keygenWebClient(WebClient.Builder builder) {
        return builder.baseUrl(baseUrl).build();
    }

    @Bean
    public KeyGenClient keyGenClient(WebClient webClient) {
        return new KeyGenClient(webClient);
    }
}
