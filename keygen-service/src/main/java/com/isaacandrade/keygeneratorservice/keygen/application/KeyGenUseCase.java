package com.isaacandrade.keygeneratorservice.keygen.application;

import com.isaacandrade.keygeneratorservice.base62.core.Base62Encoder;
import com.isaacandrade.keygeneratorservice.snowflake.core.SnowflakeIdGen;
import org.springframework.stereotype.Service;

@Service
public class KeyGenUseCase {

    private final SnowflakeIdGen generator;

    public KeyGenUseCase(SnowflakeIdGen generator) {
        this.generator = generator;
    }

    public String generateUniqueKey() {
        long id = generator.nextId();
        return Base62Encoder.encode(id);
    }
}
