package com.isaacandrade.keygeneratorservice.integration_tests.keygen.application;

import com.isaacandrade.keygeneratorservice.keygen.application.KeyGenUseCase;
import com.isaacandrade.keygeneratorservice.keygen.application.snowflake.infra.time.TimeStampProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class KeyGenUseCaseIntegrationTest {
    @MockitoBean
    private TimeStampProvider timeStampProvider;

    @Autowired
    private KeyGenUseCase keyGenUseCase;

    private long currentTime = System.currentTimeMillis();

    @BeforeEach
    void setup() {
        when(timeStampProvider.currentTimeMillis()).thenAnswer(invocation -> currentTime++);
    }

    @Test
    void generateUniqueKey_returnsBase62EncodedUniqueKey() {
        String key1 = keyGenUseCase.generateUniqueKey();
        String key2 = keyGenUseCase.generateUniqueKey();
        assertNotNull(key1);
        assertNotNull(key2);
        assertNotEquals(key1, key2);
        assertEquals(7, key1.length());
        assertEquals(7, key2.length());
    }
}
