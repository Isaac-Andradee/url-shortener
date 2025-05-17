package com.isaacandrade.keygeneratorservice.integration_tests.keygen.application.snowflake.core;

import com.isaacandrade.keygeneratorservice.keygen.application.snowflake.core.SnowflakeIdGen;
import com.isaacandrade.keygeneratorservice.keygen.application.snowflake.exception.RegressiveClockException;
import com.isaacandrade.keygeneratorservice.keygen.application.snowflake.infra.time.TimeStampProvider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.ReflectionTestUtils.setField;

@SpringBootTest
@ActiveProfiles("test")
public class SnowflakeIdGenIntegrationTest {
    @Autowired
    private SnowflakeIdGen snowflakeIdGen;

    @Autowired
    private TimeStampProvider timeStampProvider;

    @Test
    void nextId_generatesUniqueIdsInSequence() {
        long id1 = snowflakeIdGen.nextId();
        long id2 = snowflakeIdGen.nextId();
        assertTrue(id2 > id1);
    }

    @Test
    void nextId_throwsExceptionIfClockMovesBackwards() {
        TimeStampProvider spyProvider = spy(timeStampProvider);
        setField(snowflakeIdGen, "timeStampProvider", spyProvider);
        long current = System.currentTimeMillis();
        long last = current + 1000;
        setField(snowflakeIdGen, "lastTimestamp", last);
        when(spyProvider.currentTimeMillis()).thenReturn(current);
        assertThrows(RegressiveClockException.class, () -> {
            snowflakeIdGen.nextId();
        });
    }
}
