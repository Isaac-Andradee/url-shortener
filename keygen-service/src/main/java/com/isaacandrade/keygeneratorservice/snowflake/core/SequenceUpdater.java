package com.isaacandrade.keygeneratorservice.snowflake.core;

import com.isaacandrade.keygeneratorservice.snowflake.infra.time.TimeStampProvider;

import static com.isaacandrade.keygeneratorservice.snowflake.utils.SnowflakeConstants.*;

public class SequenceUpdater {
    private final TimeStampProvider timeStampProvider;
    private long sequence = 0L;

    public SequenceUpdater(TimeStampProvider timeStampProvider) {
        this.timeStampProvider = timeStampProvider;
    }

    public void updateSequenceWith(long currentTimestamp, long lastTimestamp) {
        if (currentTimestamp == lastTimestamp) {
            incrementSequence();
            if (isOverflow()) waitNextMillis(currentTimestamp);
        } else {
            resetSequence();
        }
    }

    private void waitNextMillis(long currentTimestamp) {
        long timestamp;
        do {
            timestamp = timeStampProvider.currentTimeMillis();
        } while (timestamp <= currentTimestamp);
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
