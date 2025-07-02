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

/**
 * SnowflakeIdGen is responsible for generating unique identifiers using the Snowflake algorithm.
 * It combines a timestamp, a sequence number, and a machine ID to create a unique ID.
 * The class ensures that IDs are generated in a thread-safe manner and validates timestamps.
 *
 * @author Isaac Andrade
 */
@Component
public class SnowflakeIdGen {

    private final TimestampValidator timestampValidator;
    private final TimeStampProvider timeStampProvider;
    private final SequenceUpdater sequenceUpdater;
    private final IdAssembler idAssembler;
    private long lastTimestamp = -1L;

    public SnowflakeIdGen(
            TimeStampProvider timeStampProvider,
            TimestampValidator timestampValidator,
            SequenceUpdater sequenceUpdater,
            IdAssembler idAssembler
    ) {
        this.timeStampProvider = timeStampProvider;
        this.timestampValidator = timestampValidator;
        this.sequenceUpdater = sequenceUpdater;
        this.idAssembler = idAssembler;
    }

    public synchronized long nextId() {
        long currentTimestamp = getCurrentTimestamp();
        validateTimestamp(currentTimestamp, lastTimestamp);
        currentTimestamp = updateSequence(currentTimestamp, lastTimestamp);
        lastTimestamp = currentTimestamp;
        return snowflakeId(currentTimestamp, getSequence());
    }

    private long getCurrentTimestamp() {
        return timeStampProvider.currentTimeMillis();
    }

    private void validateTimestamp(long currentTimestamp, long lastTimestamp) {
        timestampValidator.validateTimestamp(currentTimestamp, lastTimestamp);
    }

    private long updateSequence(long currentTimestamp, long lastTimestamp) {
        return sequenceUpdater.updateSequenceWith(currentTimestamp, lastTimestamp);
    }

    private long getSequence() {
        return sequenceUpdater.getSequence();
    }

    private long snowflakeId(long currentTimestamp, long sequence) {
        return idAssembler.assemble(currentTimestamp, sequence);
    }
}