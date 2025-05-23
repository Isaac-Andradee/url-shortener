package com.isaacandrade.urlshortenerservice.unit_tests.urlshort.application;

import com.isaacandrade.common.url.model.UrlMapping;
import com.isaacandrade.common.url.repository.UrlRepository;
import com.isaacandrade.urlshortenerservice.urlshort.application.AliasAvailabilityValidator;
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
public class AliasAvailabilityValidatorTest {

    @Mock
    UrlRepository urlRepository;

    AliasAvailabilityValidator validator;

    @BeforeEach
    void setUp() {
        validator = new AliasAvailabilityValidator(urlRepository);
    }

    @Test
    void validate_shouldThrow_whenAliasExists() {
        String alias = "taken";
        when(urlRepository.findById(alias)).thenReturn(Optional.of(new UrlMapping()));
        assertThrows(AliasNotAvailableException.class, () -> validator.validate(alias));
        verify(urlRepository).findById(alias);
    }

    @Test
    void validate_shouldNotThrow_whenAliasEmpty() {
        validator.validate("  ");
        verifyNoInteractions(urlRepository);
    }

    @Test
    void validate_shouldNotThrow_whenAliasAvailable() {
        String alias = "free";
        when(urlRepository.findById(alias)).thenReturn(Optional.empty());
        assertDoesNotThrow(() -> validator.validate(alias));
        verify(urlRepository).findById(alias);
    }
}
