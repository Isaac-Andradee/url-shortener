package com.isaacandrade.urlshortenerservice.unit_tests.urlshort.application;

import com.isaacandrade.urlshortenerservice.urlshort.application.AliasValidationComposite;
import com.isaacandrade.urlshortenerservice.urlshort.application.AliasValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AliasValidationCompositeTest {

    @Mock
    AliasValidator v1, v2;

    AliasValidationComposite composite;

    @BeforeEach
    void setUp() {
        composite = new AliasValidationComposite(List.of(v1, v2));
    }

    @Test
    void validate_shouldInvokeAllValidators() {
        String alias = "any";
        composite.validate(alias);
        verify(v1).validate(alias);
        verify(v2).validate(alias);
    }

    @Test
    void validate_shouldAbortOnException() {
        String alias = "x";
        doThrow(new RuntimeException()).when(v1).validate(alias);
        assertThrows(RuntimeException.class, () -> composite.validate(alias));
        verify(v1).validate(alias);
        verifyNoMoreInteractions(v2);
    }
}

