package com.isaacandrade.keygeneratorservice.snowflake.infra.node;

public interface NodeInfoProvider {
    long getDatacenterId();
    long getMachineId();
}
