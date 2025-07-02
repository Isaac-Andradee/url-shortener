/*
 * Copyright 2025 the original author.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.isaacandrade.keygeneratorservice.keygen.application.snowflake.core;

import com.isaacandrade.keygeneratorservice.keygen.application.snowflake.infra.time.TimeStampProvider;
import org.springframework.stereotype.Component;

import static com.isaacandrade.keygeneratorservice.keygen.application.snowflake.utils.SnowflakeConstants.*;

/**
 * SequenceUpdater is responsible for managing the sequence number in the Snowflake ID generation process.
 * It ensures that the sequence is incremented correctly and handles overflow by waiting for the next millisecond.
 * This component is crucial for generating unique IDs in a distributed system.
 *
 * @author Isaac Andrade
 */
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
