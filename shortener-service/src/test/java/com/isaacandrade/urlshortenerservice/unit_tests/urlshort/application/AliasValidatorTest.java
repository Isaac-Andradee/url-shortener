package com.isaacandrade.urlshortenerservice.unit_tests.urlshort.application;

import com.isaacandrade.common.url.model.UrlMapping;
import com.isaacandrade.common.url.repository.UrlRepository;
import com.isaacandrade.urlshortenerservice.urlshort.application.AliasValidator;
import com.isaacandrade.urlshortenerservice.urlshort.exception.AliasNotAvailableException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AliasValidatorTest {
    @Mock
    UrlRepository urlRepository;
    AliasValidator aliasValidator;

    @BeforeEach
    void setUp() {
        aliasValidator = new AliasValidator(urlRepository);
    }

    @Test
    void validateIfExistInDb_shouldThrow_whenAliasExists() {
        String alias = "existingAlias";
        when(urlRepository.findById(alias)).thenReturn(Optional.of(new UrlMapping()));
        assertThrows(AliasNotAvailableException.class, () -> aliasValidator.validateIfExistInDb(alias));
        verify(urlRepository).findById(alias);
    }

    @Test
    void validateIfExistInDb_shouldNotThrow_whenAliasNotExists() {
        String alias = "availableAlias";
        when(urlRepository.findById(alias)).thenReturn(Optional.empty());
        assertDoesNotThrow(() -> aliasValidator.validateIfExistInDb(alias));
        verify(urlRepository).findById(alias);
    }

    @Test
    void validateIfExistInDb_shouldNotQueryRepo_whenAliasIsBlank() {
        aliasValidator.validateIfExistInDb("  ");
        verifyNoInteractions(urlRepository);
    }

    @Test
    void isAliasOnRequest_shouldReturnTrue_whenAliasIsNotBlank() {
        assertTrue(aliasValidator.isAliasOnRequest("alias123"));
    }

    @Test
    void isAliasOnRequest_shouldReturnFalse_whenAliasIsBlank() {
        assertFalse(aliasValidator.isAliasOnRequest(" "));
    }
}
