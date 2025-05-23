package com.isaacandrade.urlshortenerservice.unit_tests.urlshort.application;

import com.isaacandrade.urlshortenerservice.config.ShortenerProperties;
import com.isaacandrade.urlshortenerservice.urlshort.application.AliasValidationComposite;
import com.isaacandrade.urlshortenerservice.urlshort.application.KeyGenResolver;
import com.isaacandrade.urlshortenerservice.urlshort.application.ShortenerUseCase;
import com.isaacandrade.common.url.model.dto.ShortenRequest;
import com.isaacandrade.common.url.model.dto.ShortenResponse;
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
    private ShortenerProperties domainProperties;

    @Mock
    private AliasValidationComposite aliasValidation;

    @Mock
    private KeyGenResolver keyGenResolver;

    @Mock
    private DbCacheSaver dbCacheSaver;

    @InjectMocks
    private ShortenerUseCase useCase;

    @Test
    void shorten_withValidAlias_returnsShortUrl() {
        // arrange
        String alias = "myAlias";
        String longUrl = "https://example.com";
        ShortenRequest req = new ShortenRequest(longUrl, alias);
        when(keyGenResolver.resolveShortKey(alias)).thenReturn(alias);
        when(domainProperties.toString()).thenReturn("https://short.ly/");

        // act
        ShortenResponse resp = useCase.shorten(req);

        // assert
        assertEquals("https://short.ly/" + alias, resp.shortUrl());
        verify(aliasValidation).validate(alias);
        verify(keyGenResolver).resolveShortKey(alias);
        verify(dbCacheSaver).saveUrlMapping(
                argThat(mapping ->
                        mapping.getShortKey().equals(alias) &&
                                mapping.getLongUrl().equals(longUrl) &&
                                mapping.getAlias().equals(alias)
                )
        );
    }

    @Test
    void shorten_whenAliasInvalid_throwsAndSkipsGeneration() {
        // arrange
        String alias = "bad!";
        ShortenRequest req = new ShortenRequest("https://x.com", alias);
        doThrow(new IllegalArgumentException("Invalid"))
                .when(aliasValidation).validate(alias);

        // act & assert
        assertThrows(IllegalArgumentException.class, () -> useCase.shorten(req));
        verify(aliasValidation).validate(alias);
        verifyNoInteractions(keyGenResolver);
        verifyNoInteractions(dbCacheSaver);
    }

    @Test
    void shorten_withoutAlias_generatesKeyAndSaves() {
        // arrange
        String generated = "abc123";
        ShortenRequest req = new ShortenRequest("https://foo.com", null);
        when(keyGenResolver.resolveShortKey(null)).thenReturn(generated);
        when(domainProperties.toString()).thenReturn("https://short.ly/");

        // act
        ShortenResponse resp = useCase.shorten(req);

        // assert
        assertEquals("https://short.ly/" + generated, resp.shortUrl());
        verify(aliasValidation).validate(null);
        verify(keyGenResolver).resolveShortKey(null);
        verify(dbCacheSaver).saveUrlMapping(
                argThat(mapping ->
                        mapping.getShortKey().equals(generated) &&
                                mapping.getLongUrl().equals("https://foo.com") &&
                                mapping.getAlias() == null
                )
        );
    }
}
