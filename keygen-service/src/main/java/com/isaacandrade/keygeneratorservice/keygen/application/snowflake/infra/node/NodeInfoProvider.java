package com.isaacandrade.keygeneratorservice.keygen.application.snowflake.infra.node;

public interface NodeInfoProvider {
    long getDatacenterId();
    long getMachineId();
}
