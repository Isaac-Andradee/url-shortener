package com.isaacandrade.keygeneratorservice.integration_tests.keygen.application.snowflake.core;

import com.isaacandrade.keygeneratorservice.keygen.application.snowflake.core.SequenceUpdater;
import com.isaacandrade.keygeneratorservice.keygen.application.snowflake.infra.time.TimeStampProvider;
import com.isaacandrade.keygeneratorservice.keygen.application.snowflake.utils.SnowflakeConstants;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ActiveProfiles("test")
public class SequenceUpdaterIntegrationTest {
    @Autowired
    private SequenceUpdater sequenceUpdater;

    @Autowired
    private TimeStampProvider timeStampProvider;

    @Test
    void updateSequenceWith_sameTimestamp_incrementsSequence() {
        long timestamp = timeStampProvider.currentTimeMillis();
        long lastTimestamp = timestamp;
        long updatedTimestamp = sequenceUpdater.updateSequenceWith(timestamp, lastTimestamp);
        assertEquals(timestamp, updatedTimestamp);
        assertEquals(1, sequenceUpdater.getSequence());
    }

    @Test
    void updateSequenceWith_differentTimestamp_resetsSequence() {
        long lastTimestamp = timeStampProvider.currentTimeMillis();
        long newTimestamp = lastTimestamp + 1;
        long updatedTimestamp = sequenceUpdater.updateSequenceWith(newTimestamp, lastTimestamp);
        assertEquals(newTimestamp, updatedTimestamp);
        assertEquals(0, sequenceUpdater.getSequence());
    }

    @Test
    void updateSequenceWith_overflow_waitsForNextMillis() {
        ReflectionTestUtils.setField(sequenceUpdater, "sequence", SnowflakeConstants.MAX_SEQUENCE);
        long timestamp = timeStampProvider.currentTimeMillis();
        long updatedTimestamp = sequenceUpdater.updateSequenceWith(timestamp, timestamp);
        assertTrue(updatedTimestamp > timestamp);
        assertEquals(0, sequenceUpdater.getSequence());
    }
}
