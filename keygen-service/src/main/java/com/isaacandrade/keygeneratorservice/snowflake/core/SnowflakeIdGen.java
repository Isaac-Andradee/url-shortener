package com.isaacandrade.keygeneratorservice.snowflake.core;

import com.isaacandrade.keygeneratorservice.snowflake.infra.node.NodeInfoProvider;
import com.isaacandrade.keygeneratorservice.snowflake.infra.time.SystemTimeStampProvider;
import com.isaacandrade.keygeneratorservice.snowflake.infra.time.TimeStampProvider;
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
        long currentTimestamp = timeStampProvider.currentTimeMillis();
        timestampValidator.validateTimestamp(currentTimestamp, lastTimestamp);
        currentTimestamp =  sequenceUpdater.updateSequenceWith(currentTimestamp, lastTimestamp);
        lastTimestamp = currentTimestamp;
        return idAssembler.assemble(currentTimestamp, sequenceUpdater.getSequence());
    }
}