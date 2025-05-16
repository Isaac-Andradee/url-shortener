package com.isaacandrade.keygeneratorservice.keygen.application.snowflake.core;

import com.isaacandrade.keygeneratorservice.keygen.application.snowflake.infra.time.TimeStampProvider;
import org.springframework.stereotype.Component;

import static com.isaacandrade.keygeneratorservice.keygen.application.snowflake.utils.SnowflakeConstants.*;

@Component
public class SequenceUpdater {
    private final TimeStampProvider timeStampProvider;
    private long sequence = 0L;

    public SequenceUpdater(TimeStampProvider timeStampProvider) {
        this.timeStampProvider = timeStampProvider;
    }

    public long updateSequenceWith(long currentTimestamp, long lastTimestamp) {
        if (currentTimestamp == lastTimestamp) {
            incrementSequence();
            if (isOverflow()) currentTimestamp = waitNextMillis(currentTimestamp);
        } else {
            resetSequence();
        }
        return currentTimestamp;
    }

    private long waitNextMillis(long currentTimestamp) {
        long timestamp;
        do {
            timestamp = timeStampProvider.currentTimeMillis();
        } while (timestamp <= currentTimestamp);
        return timestamp;
    }

    public void incrementSequence() {
        sequence = (sequence + 1) & MAX_SEQUENCE;
    }

    public void resetSequence() {
        sequence = 0;
    }

    public boolean isOverflow() {
        return sequence == 0;
    }

    public long getSequence() {
        return sequence;
    }
}
