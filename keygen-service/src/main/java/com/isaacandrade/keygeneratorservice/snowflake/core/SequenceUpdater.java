package com.isaacandrade.keygeneratorservice.snowflake.core;

import com.isaacandrade.keygeneratorservice.snowflake.infra.time.TimeStampProvider;

import static com.isaacandrade.keygeneratorservice.snowflake.utils.SnowflakeConstants.*;

public class SequenceUpdater {
    private final TimeStampProvider timeStampProvider;

    public SequenceUpdater(TimeStampProvider timeStampProvider) {
        this.timeStampProvider = timeStampProvider;
    }

    public void updateSequenceWith(long currentTimestamp) {
        if (currentTimestamp == lastTimestamp) {
            incrementSequence();
            if (isOverflow()) waitNextMillis(currentTimestamp);
        } else {
            reset();
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

    public void reset() {
        sequence = 0;
    }

    public boolean isOverflow() {
        return sequence == 0;
    }

    public long getSequence() {
        return sequence;
    }
}
