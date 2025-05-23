package com.isaacandrade.urlshortenerservice.unit_tests.urlshort.application;

import com.isaacandrade.urlshortenerservice.urlshort.application.AliasFormatValidator;
import com.isaacandrade.urlshortenerservice.urlshort.exception.AliasInvalidFormatException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class AliasFormatValidatorTest {

    @InjectMocks
    AliasFormatValidator validator;

    @Test
    void validate_shouldThrow_whenInvalidFormat() {
        assertThrows(AliasInvalidFormatException.class, () -> validator.validate("--asds"));
    }

    @Test
    void validate_shouldNotThrow_whenBlank() {
        assertDoesNotThrow(() -> validator.validate("   "));
    }

    @Test
    void validate_shouldNotThrow_whenValid() {
        assertDoesNotThrow(() -> validator.validate("Valid_Alias-123"));
    }
}

