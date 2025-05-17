package com.isaacandrade.keygeneratorservice.keygen.application.snowflake.infra.network;

public interface NodeInfoProvider {
    long getDatacenterId();
    long getMachineId();
}
