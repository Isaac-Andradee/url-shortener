package com.isaacandrade.urlshortenerservice.unit_tests.urlshort.application;

import com.isaacandrade.urlshortenerservice.urlshort.application.AliasValidator;
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
    AliasValidator aliasValidator;
    @Mock
    KeyGenClient keyGenClient;
    KeyGenResolver keyGenResolver;

    @BeforeEach
    void setUp() {
        keyGenResolver = new KeyGenResolver(aliasValidator, keyGenClient);
    }

    @Test
    void resolveShortKey_shouldReturnAlias_whenAliasIsPresent() {
        String alias = "customAlias";
        when(aliasValidator.isAliasOnRequest(alias)).thenReturn(true);
        String result = keyGenResolver.resolveShortKey(alias);
        assertEquals(alias, result);
        verify(aliasValidator).isAliasOnRequest(alias);
        verifyNoInteractions(keyGenClient);
    }

    @Test
    void resolveShortKey_shouldCallKeyGen_whenAliasIsNull() {
        String generated = "gen123";
        when(aliasValidator.isAliasOnRequest(null)).thenReturn(false);
        when(keyGenClient.generateKey()).thenReturn(generated);
        String result = keyGenResolver.resolveShortKey(null);
        assertEquals(generated, result);
        verify(aliasValidator).isAliasOnRequest(null);
        verify(keyGenClient).generateKey();
    }
}
