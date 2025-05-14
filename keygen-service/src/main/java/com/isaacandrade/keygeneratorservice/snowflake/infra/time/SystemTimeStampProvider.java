package com.isaacandrade.keygeneratorservice.snowflake.core;

import org.springframework.stereotype.Component;

@Component
public class SystemTimeStampProvider implements TimeStampProvider{

    @Override
    public long currentTimeMillis() {
        return System.currentTimeMillis();
    }
}
