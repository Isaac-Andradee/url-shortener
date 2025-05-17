package com.isaacandrade.keygeneratorservice.unit_tests.keygen.application.snowflake.core;

import com.isaacandrade.keygeneratorservice.keygen.application.snowflake.core.TimestampValidator;
import com.isaacandrade.keygeneratorservice.keygen.application.snowflake.exception.RegressiveClockException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TimestampValidatorTests {

    private final TimestampValidator timestampValidator = new TimestampValidator();

    @Test
    void validateTimestamp_whenValid_doesNotThrow() {
        assertDoesNotThrow(() -> timestampValidator.validateTimestamp(1000L, 999L));
    }

    @Test
    void validateTimestamp_whenEqual_doesNotThrow() {
        assertDoesNotThrow(() -> timestampValidator.validateTimestamp(1000L, 1000L));
    }

    @Test
    void validateTimestamp_whenRegressive_throwsException() {
        RegressiveClockException ex = assertThrows(
                RegressiveClockException.class,
                () -> timestampValidator.validateTimestamp(999L, 1000L)
        );
        assertEquals("Clock Moved Backwards!", ex.getMessage());
    }
}
