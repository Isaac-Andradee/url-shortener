package com.isaacandrade.urlshortenerservice.unit_tests.urlshort.application;

import com.isaacandrade.urlshortenerservice.urlshort.application.KeyGenResolver;
import com.isaacandrade.urlshortenerservice.web.keygen.KeyGenClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class KeyGenResolverTest {
    @Mock
    KeyGenClient keyGenClient;

    KeyGenResolver keyGenResolver;

    @BeforeEach
    void setUp() {
        keyGenResolver = new KeyGenResolver(keyGenClient);
    }

    @Test
    void resolveShortKey_shouldReturnAlias_whenAliasIsPresent() {
        String alias = "customAlias";
        String result = keyGenResolver.resolveShortKey(alias);
        assertEquals(alias, result);
        verifyNoInteractions(keyGenClient);
    }

    @Test
    void resolveShortKey_shouldCallKeyGen_whenAliasIsNull() {
        String generated = "gen123";
        when(keyGenClient.generateKey()).thenReturn(generated);
        String result = keyGenResolver.resolveShortKey(null);
        assertEquals(generated, result);
        verify(keyGenClient).generateKey();
    }

    @Test
    void resolveShortKey_shouldCallKeyGen_whenAliasIsBlank() {
        String generated = "gen123";
        when(keyGenClient.generateKey()).thenReturn(generated);
        String result = keyGenResolver.resolveShortKey(" ");
        assertEquals(generated, result);
        verify(keyGenClient).generateKey();
    }
}
