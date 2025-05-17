package com.isaacandrade.keygeneratorservice.unit_tests.keygen.application.snowflake.core;

import com.isaacandrade.keygeneratorservice.keygen.application.snowflake.core.IdAssembler;
import com.isaacandrade.keygeneratorservice.keygen.application.snowflake.core.SequenceUpdater;
import com.isaacandrade.keygeneratorservice.keygen.application.snowflake.core.SnowflakeIdGen;
import com.isaacandrade.keygeneratorservice.keygen.application.snowflake.core.TimestampValidator;
import com.isaacandrade.keygeneratorservice.keygen.application.snowflake.infra.time.TimeStampProvider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SnowflakeIdGenTests {
    @Mock
    TimeStampProvider timeStampProvider;
    @Mock
    TimestampValidator timestampValidator;
    @Mock
    SequenceUpdater sequenceUpdater;
    @Mock
    IdAssembler idAssembler;

    @InjectMocks
    SnowflakeIdGen snowflakeIdGen;

    @Test
    void nextId_returnsAssembledId() {
        long timestamp = 1000L;
        long updatedTimestamp = 1000L;
        long sequence = 42L;
        long expectedId = 123456789L;
        when(timeStampProvider.currentTimeMillis()).thenReturn(timestamp);
        when(sequenceUpdater.updateSequenceWith(timestamp, -1L)).thenReturn(updatedTimestamp);
        when(sequenceUpdater.getSequence()).thenReturn(sequence);
        when(idAssembler.assemble(updatedTimestamp, sequence)).thenReturn(expectedId);
        long result = snowflakeIdGen.nextId();
        verify(timestampValidator).validateTimestamp(timestamp, -1L);
        assertEquals(expectedId, result);
    }
}
