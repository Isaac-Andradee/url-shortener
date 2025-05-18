package com.isaacandrade.urlshortenerservice.integration_tests.urlshort.application;

import com.isaacandrade.urlshortenerservice.urlshort.application.AliasValidator;
import com.isaacandrade.urlshortenerservice.urlshort.application.KeyGenResolver;
import com.isaacandrade.urlshortenerservice.web.keygen.KeyGenClient;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.web.reactive.function.client.WebClient;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
@Testcontainers
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class KeygenResolverIntegrationTests {
    @Container
    static GenericContainer<?> keyGenService = new GenericContainer<>("isaacandra/keygen-service:latest")
            .withExposedPorts(8080)
            .withEnv("KEYGEN_SERVER_PORT", "8080");

    private static KeyGenResolver resolver;

    @BeforeAll
    public static void setUp() {
        String host = keyGenService.getHost();
        Integer port = keyGenService.getMappedPort(8080);
        WebClient webClient = WebClient.builder()
                .baseUrl("http://" + host + ":" + port)
                .build();
        KeyGenClient client = new KeyGenClient(webClient);
        AliasValidator aliasValidator = mock(AliasValidator.class);
        when(aliasValidator.isAliasOnRequest(any())).thenCallRealMethod();
        resolver = new KeyGenResolver(aliasValidator, client);
    }

    @Test
    public void shouldReturnAliasIfPresent() {
        String result = resolver.resolveShortKey("customAlias");
        assertEquals("customAlias", result);
    }

    @Test
    public void shouldGenerateKeyIfAliasNull() {
        String result = resolver.resolveShortKey(null);
        assertNotNull(result);
        assertFalse(result.isBlank());
    }

    @Test
    public void shouldGenerateKeyIfAliasEmpty() {
        String result = resolver.resolveShortKey("");
        assertNotNull(result);
        assertFalse(result.isBlank());
    }
}
