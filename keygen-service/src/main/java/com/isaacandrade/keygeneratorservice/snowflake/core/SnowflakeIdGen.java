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

    public SnowflakeIdGen(NodeInfoProvider nodeInfoProvider) {
        this.timeStampProvider = new SystemTimeStampProvider();
        this.timestampValidator = new TimestampValidator();
        this.sequenceUpdater = new SequenceUpdater(timeStampProvider);
        this.idAssembler = new IdAssembler(nodeInfoProvider);
    }

    public synchronized long nextId() {
        long currentTimestamp = timeStampProvider.currentTimeMillis();
        timestampValidator.validateTimestamp(currentTimestamp, lastTimestamp);
        sequenceUpdater.updateSequenceWith(currentTimestamp, lastTimestamp);
        lastTimestamp = currentTimestamp;
        return idAssembler.assemble(currentTimestamp, sequenceUpdater.getSequence());
    }
}