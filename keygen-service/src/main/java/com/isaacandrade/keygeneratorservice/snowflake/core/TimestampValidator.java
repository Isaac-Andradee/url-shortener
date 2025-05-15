package com.isaacandrade.keygeneratorservice.snowflake.core;

import com.isaacandrade.keygeneratorservice.snowflake.exception.RegressiveClockException;
import org.springframework.stereotype.Component;

@Component
public class TimestampValidator {
    public void validateTimestamp(long currentTimestamp, long lastTimestamp) {
        if (currentTimestamp < lastTimestamp) {
            throw new RegressiveClockException("Clock Moved Backwards!");
        }
    }
}
