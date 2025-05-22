package com.isaacandrade.urlshortenerservice.unit_tests.urlshort.web;

import com.isaacandrade.urlshortenerservice.urlshort.exception.KeygenServiceUnvailableException;
import com.isaacandrade.urlshortenerservice.web.keygen.KeyGenClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class KeyGenClientTest {
    private static final String URL = "http://keygen-service/generate";
    private static final String KEY = "XYZ123";

    @Mock WebClient.Builder builder;
    @Mock WebClient client;
    @Mock WebClient.RequestHeadersUriSpec uriSpec;
    @Mock WebClient.RequestHeadersSpec headersSpec;
    @Mock WebClient.ResponseSpec responseSpec;

    @InjectMocks KeyGenClient keyGenClient;

    @BeforeEach
    void init() {
        when(builder.build()).thenReturn(client);
    }

    // TODO need to find a way to make this work without eureka client
    @Test
    void generateKey_unavailable_throws() {
        when(client.get()).thenReturn(uriSpec);
        when(uriSpec.uri(eq(URL))).thenReturn(headersSpec);
        when(headersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(Map.class))
                .thenReturn(Mono.error(new KeygenServiceUnvailableException()));
        assertThrows(KeygenServiceUnvailableException.class,
                () -> keyGenClient.generateKey());
    }
}
