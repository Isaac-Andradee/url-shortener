package com.isaacandrade.keygeneratorservice.snowflake.core;

import com.isaacandrade.keygeneratorservice.snowflake.infra.node.NodeInfoProvider;
import com.isaacandrade.keygeneratorservice.snowflake.infra.time.SystemTimeStampProvider;
import com.isaacandrade.keygeneratorservice.snowflake.infra.time.TimeStampProvider;
import org.springframework.stereotype.Component;
import static com.isaacandrade.keygeneratorservice.snowflake.utils.SnowflakeConstants.lastTimestamp;

@Component
public class SnowflakeIdGen {

    private final TimestampValidator timestampValidator;
    private final TimeStampProvider timeStampProvider;
    private final SequenceUpdater sequenceUpdater;
    private final IdAssembler idAssembler;

    public SnowflakeIdGen(NodeInfoProvider nodeInfoProvider) {
        this.timeStampProvider = new SystemTimeStampProvider();
        this.timestampValidator = new TimestampValidator();
        this.sequenceUpdater = new SequenceUpdater(timeStampProvider);
        this.idAssembler = new IdAssembler(nodeInfoProvider);
    }

    public synchronized long nextId() {
        long currentTimestamp = timeStampProvider.currentTimeMillis();
        timestampValidator.validateTimestamp(currentTimestamp, lastTimestamp);
        sequenceUpdater.updateSequenceWith(currentTimestamp);
        lastTimestamp = currentTimestamp;
        return idAssembler.assemble(currentTimestamp, sequenceUpdater.getSequence());
    }
}