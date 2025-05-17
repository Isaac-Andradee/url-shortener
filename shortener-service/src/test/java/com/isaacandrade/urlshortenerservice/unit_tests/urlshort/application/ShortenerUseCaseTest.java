package com.isaacandrade.urlshortenerservice.unit_tests.urlshort.application;

import com.isaacandrade.urlshortenerservice.config.ShortenerProperties;
import com.isaacandrade.urlshortenerservice.urlshort.application.AliasValidator;
import com.isaacandrade.urlshortenerservice.urlshort.application.KeyGenResolver;
import com.isaacandrade.urlshortenerservice.urlshort.application.ShortenerUseCase;
import com.isaacandrade.urlshortenerservice.urlshort.application.dto.ShortenRequest;
import com.isaacandrade.urlshortenerservice.urlshort.application.dto.ShortenResponse;
import com.isaacandrade.urlshortenerservice.urlshort.exception.AliasNotAvailableException;
import com.isaacandrade.urlshortenerservice.urlshort.infra.DB.DbCacheSaver;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ShortenerUseCaseTest {
    @Mock
    ShortenerProperties shortenerProperties;
    @Mock
    AliasValidator aliasValidator;
    @Mock
    KeyGenResolver keyGenResolver;
    @Mock
    DbCacheSaver dbCacheSaver;
    @InjectMocks
    ShortenerUseCase shortenerUseCase;

    @Test
    public void shorten_shouldReturnShortenResponse_whenAliasProvided() {
        String usingAlias = "google";
        ShortenRequest request = new ShortenRequest("https://www.google.com", usingAlias);
        String baseUrl = "https://shorting.com/";
        when(keyGenResolver.resolveShortKey(usingAlias)).thenReturn(usingAlias);
        when(shortenerProperties.toString()).thenReturn(baseUrl);
        ShortenResponse response = shortenerUseCase.shorten(request);
        assertEquals(baseUrl + usingAlias, response.shortUrl());
    }

    @Test
    public void shorten_shouldThrowException_whenAliasAlreadyExists() {
        ShortenRequest request = new ShortenRequest("https://www.google.com", "alreadyExists");
        doThrow(new AliasNotAvailableException())
                .when(aliasValidator).validateIfExistInDb("alreadyExists");
        assertThrows(AliasNotAvailableException.class, () -> {
            shortenerUseCase.shorten(request);
        });
        verify(aliasValidator).validateIfExistInDb("alreadyExists");
        verifyNoInteractions(keyGenResolver);
        verifyNoInteractions(dbCacheSaver);
    }

    @Test
    public void shorten_shouldGenerateKey_whenAliasIsNull() {
        ShortenRequest request = new ShortenRequest("https://www.google.com", null);
        String generatedKey = "base62key";
        String baseUrl = "https://shorting.com/";
        when(keyGenResolver.resolveShortKey(null)).thenReturn(generatedKey);
        when(shortenerProperties.toString()).thenReturn(baseUrl);
        ShortenResponse response = shortenerUseCase.shorten(request);
        assertEquals(baseUrl + generatedKey, response.shortUrl());
    }
}
