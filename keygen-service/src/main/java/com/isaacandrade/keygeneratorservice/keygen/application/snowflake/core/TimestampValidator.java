package com.isaacandrade.keygeneratorservice.keygen.application.snowflake.core;

import com.isaacandrade.keygeneratorservice.keygen.application.snowflake.exception.RegressiveClockException;
import org.springframework.stereotype.Component;

@Component
public class TimestampValidator {
    public void validateTimestamp(long currentTimestamp, long lastTimestamp) {
        if (currentTimestamp < lastTimestamp) {
            throw new RegressiveClockException("Clock Moved Backwards!");
        }
    }
}
