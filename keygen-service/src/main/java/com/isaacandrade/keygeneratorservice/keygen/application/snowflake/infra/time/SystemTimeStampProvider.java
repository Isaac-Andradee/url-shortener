package com.isaacandrade.keygeneratorservice.keygen.application.snowflake.infra.time;

import org.springframework.stereotype.Component;

@Component
public class SystemTimeStampProvider implements TimeStampProvider {

    @Override
    public long currentTimeMillis() {
        return System.currentTimeMillis();
    }
}
