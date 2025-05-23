package com.isaacandrade.resolverservice.unit_tests.resolver.application;

import com.isaacandrade.common.url.model.UrlMapping;
import com.isaacandrade.resolverservice.exception.KeyNotFoundException;
import com.isaacandrade.resolverservice.resolver.application.CacheLookup;
import com.isaacandrade.resolverservice.resolver.application.DbLookup;
import com.isaacandrade.resolverservice.resolver.application.ResolverUseCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ResolverUseCaseTests {

    @Mock
    private CacheLookup cacheLookup;

    @Mock
    private DbLookup dbLookup;

    @InjectMocks
    private ResolverUseCase resolverUseCase;

    @Test
    void shouldReturnUrlFromCache_whenCacheHit() {
        UrlMapping mapping = new UrlMapping("abc", "https://site.com", "abc");
        when(cacheLookup.get("abc")).thenReturn(mapping);
        String result = resolverUseCase.resolve("abc");
        assertEquals("https://site.com", result);
        verify(dbLookup, never()).getFromDb(anyString());
        verify(cacheLookup, never()).save(anyString(), any());
    }

    @Test
    void shouldReturnUrlFromDbAndCacheIt_whenCacheMiss() {
        UrlMapping mapping = new UrlMapping("abc", "https://site.com", "abc");
        when(cacheLookup.get("abc")).thenReturn(null);
        when(dbLookup.getFromDb("abc")).thenReturn(mapping);
        String result = resolverUseCase.resolve("abc");
        assertEquals("https://site.com", result);
        verify(dbLookup).getFromDb("abc");
        verify(cacheLookup).save("abc", mapping);
    }

    @Test
    void shouldThrow_whenNotFoundAnywhere() {
        when(cacheLookup.get("abc")).thenReturn(null);
        when(dbLookup.getFromDb("abc")).thenThrow(new KeyNotFoundException());
        assertThrows(KeyNotFoundException.class, () -> resolverUseCase.resolve("abc"));
        verify(cacheLookup).get("abc");
        verify(dbLookup).getFromDb("abc");
        verify(cacheLookup, never()).save(anyString(), any());
    }
}
