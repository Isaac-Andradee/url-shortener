/*
 * Copyright 2025 the original author.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.isaacandrade.keygeneratorservice.keygen.application;

import com.isaacandrade.keygeneratorservice.keygen.application.base62.core.Base62Encoder;
import com.isaacandrade.keygeneratorservice.keygen.application.snowflake.core.SnowflakeIdGen;
import org.springframework.stereotype.Service;

/**
 * Use case for generating unique keys using Snowflake ID generation and Base62 encoding.
 * This service encapsulates the logic for generating a unique key that can be used in various applications.
 *
 * @author Isaac Andrade
 */
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
