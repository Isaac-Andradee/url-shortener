package com.isaacandrade.keygeneratorservice.unit_tests.keygen.application.snowflake.core;

import com.isaacandrade.keygeneratorservice.keygen.application.snowflake.core.IdAssembler;
import com.isaacandrade.keygeneratorservice.keygen.application.snowflake.infra.network.NodeInfoProvider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.isaacandrade.keygeneratorservice.keygen.application.snowflake.utils.SnowflakeConstants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class IdAssemblerTests {
    @Mock
    NodeInfoProvider nodeInfoProvider;

    @Test
    void assemble_shouldCombineFieldsCorrectly() {
        long datacenterId = 3L;
        long machineId = 5L;
        long timestamp = 1609459200123L;
        long sequence = 42L;
        when(nodeInfoProvider.getDatacenterId()).thenReturn(datacenterId);
        when(nodeInfoProvider.getMachineId()).thenReturn(machineId);
        IdAssembler assembler = new IdAssembler(nodeInfoProvider);
        long expected = ((timestamp - EPOCH) << TIMESTAMP_SHIFT)
                | (datacenterId << DATACENTER_ID_SHIFT)
                | (machineId << MACHINE_ID_SHIFT)
                | sequence;
        long result = assembler.assemble(timestamp, sequence);
        assertEquals(expected, result);
    }
}