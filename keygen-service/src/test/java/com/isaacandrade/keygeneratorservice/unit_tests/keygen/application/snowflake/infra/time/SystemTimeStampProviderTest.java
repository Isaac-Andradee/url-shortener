package com.isaacandrade.keygeneratorservice.unit_tests.keygen.application.snowflake.infra.time;

import com.isaacandrade.keygeneratorservice.keygen.application.snowflake.infra.time.SystemTimeStampProvider;
import com.isaacandrade.keygeneratorservice.keygen.application.snowflake.infra.time.TimeStampProvider;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class SystemTimeStampProviderTest {
    @Test
    void currentTimeMillis_returnsSystemTime() {
        TimeStampProvider provider = new SystemTimeStampProvider();
        long before = System.currentTimeMillis();
        long result = provider.currentTimeMillis();
        long after = System.currentTimeMillis();
        assertTrue(result >= before && result <= after);
    }
}
