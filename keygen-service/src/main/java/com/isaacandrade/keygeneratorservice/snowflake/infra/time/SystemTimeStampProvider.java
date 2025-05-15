package com.isaacandrade.keygeneratorservice.snowflake.infra.time;

import org.springframework.stereotype.Component;

@Component
public class SystemTimeStampProvider implements TimeStampProvider {

    @Override
    public long currentTimeMillis() {
        return System.currentTimeMillis();
    }
}
