package com.isaacandrade.keygeneratorservice.keygen.application.snowflake.utils;

import org.springframework.stereotype.Component;

@Component
public class SnowflakeConstants {

    public static final long EPOCH =1747267200000L;
    public static final long DATACENTER_ID_BITS = 5L;
    public static final long MACHINE_ID_BITS = 5L;
    public static final long SEQUENCE_BITS = 12L;

    public static final long MAX_SEQUENCE = ~(-1L << SEQUENCE_BITS);
    public static final long MACHINE_ID_SHIFT = SEQUENCE_BITS;
    public static final long DATACENTER_ID_SHIFT = MACHINE_ID_SHIFT + MACHINE_ID_BITS;
    public static final long TIMESTAMP_SHIFT = DATACENTER_ID_SHIFT + DATACENTER_ID_BITS;

}
