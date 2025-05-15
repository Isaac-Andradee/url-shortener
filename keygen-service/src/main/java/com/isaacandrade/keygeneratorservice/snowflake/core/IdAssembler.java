package com.isaacandrade.keygeneratorservice.snowflake.core;

import com.isaacandrade.keygeneratorservice.snowflake.infra.node.NodeInfoProvider;

import static com.isaacandrade.keygeneratorservice.snowflake.utils.SnowflakeConstants.*;

public class IdAssembler {

    private final long datacenterId;
    private final long machineId;

    public IdAssembler(NodeInfoProvider nodeInfoProvider) {
        this.datacenterId = nodeInfoProvider.getDatacenterId();
        this.machineId = nodeInfoProvider.getMachineId();
    }

    public long assemble(long timestamp, long sequence) {
        return ((timestamp - EPOCH) << TIMESTAMP_SHIFT)
                | (datacenterId << DATACENTER_ID_SHIFT)
                | (machineId << MACHINE_ID_SHIFT)
                | sequence;
    }
}
