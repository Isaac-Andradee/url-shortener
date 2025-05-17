package com.isaacandrade.keygeneratorservice.unit_tests.keygen.application;

import com.isaacandrade.keygeneratorservice.keygen.application.KeyGenUseCase;
import com.isaacandrade.keygeneratorservice.keygen.application.base62.core.Base62Encoder;
import com.isaacandrade.keygeneratorservice.keygen.application.snowflake.core.SnowflakeIdGen;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class KeygenUseCaseTest {
    @Mock
    SnowflakeIdGen generator;

    KeyGenUseCase keyGenUseCase;

    @BeforeEach
    void setUp() {
        keyGenUseCase = new KeyGenUseCase(generator);
    }

    @Test
    void generateUniqueKey_returnsEncodedId() {
        long id = 12345L;
        when(generator.nextId()).thenReturn(id);
        String result = keyGenUseCase.generateUniqueKey();
        assertNotNull(result);
        verify(generator).nextId();
        assertEquals(Base62Encoder.encode(id), result);
    }
}
