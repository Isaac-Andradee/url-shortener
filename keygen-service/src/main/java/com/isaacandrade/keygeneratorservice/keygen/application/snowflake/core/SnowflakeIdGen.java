package com.isaacandrade.keygeneratorservice.keygen.application.snowflake.core;

import com.isaacandrade.keygeneratorservice.keygen.application.snowflake.infra.time.TimeStampProvider;
import org.springframework.stereotype.Component;

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