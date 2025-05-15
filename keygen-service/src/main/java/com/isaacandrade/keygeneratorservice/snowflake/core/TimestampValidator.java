package com.isaacandrade.keygeneratorservice.snowflake.core;

import org.springframework.stereotype.Component;

@Component
public class TimestampValidator {
    public void validateTimestamp(long currentTimestamp, long lastTimestamp) {
        if (currentTimestamp < lastTimestamp) {
            throw new IllegalStateException("Clock moved backwards.");
        }
    }
}
