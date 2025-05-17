package com.isaacandrade.keygeneratorservice.unit_tests.keygen.application.snowflake.core;

import com.isaacandrade.keygeneratorservice.keygen.application.snowflake.core.SequenceUpdater;
import com.isaacandrade.keygeneratorservice.keygen.application.snowflake.infra.time.TimeStampProvider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.isaacandrade.keygeneratorservice.keygen.application.snowflake.utils.SnowflakeConstants.MAX_SEQUENCE;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SequenceUpdaterTests {
    @Mock
    TimeStampProvider timeStampProvider;

    @Test
    void updateSequenceWith_sameTimestamp_incrementsSequence() {
        SequenceUpdater updater = new SequenceUpdater(timeStampProvider);
        long timestamp = 1000L;
        long result = updater.updateSequenceWith(timestamp, timestamp);
        assertEquals(1L, updater.getSequence());
        assertEquals(timestamp, result);
    }

    @Test
    void updateSequenceWith_differentTimestamp_resetsSequence() {
        SequenceUpdater updater = new SequenceUpdater(timeStampProvider);
        updater.incrementSequence();
        long result = updater.updateSequenceWith(2000L, 1000L);
        assertEquals(0L, updater.getSequence());
        assertEquals(2000L, result);
    }

    @Test
    void updateSequenceWith_overflow_waitsForNextMillis() {
        SequenceUpdater updater = new SequenceUpdater(timeStampProvider);
        for (int i = 0; i < MAX_SEQUENCE; i++) {
            updater.incrementSequence();
        }
        long current = 1000L;
        long waited = 1001L;
        when(timeStampProvider.currentTimeMillis())
                .thenReturn(current, current, waited);
        long result = updater.updateSequenceWith(current, current);
        assertEquals(waited, result);
        assertEquals(0L, updater.getSequence());
    }

    @Test
    void isOverflow_shouldReturnTrueWhenSequenceIsZero() {
        SequenceUpdater updater = new SequenceUpdater(timeStampProvider);
        updater.resetSequence();
        assertTrue(updater.isOverflow());
    }

    @Test
    void isOverflow_shouldReturnFalseWhenSequenceIsNonZero() {
        SequenceUpdater updater = new SequenceUpdater(timeStampProvider);
        updater.incrementSequence();
        assertFalse(updater.isOverflow());
    }
}
