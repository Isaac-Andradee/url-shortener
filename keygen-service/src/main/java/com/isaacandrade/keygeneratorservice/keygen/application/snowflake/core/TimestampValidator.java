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
package com.isaacandrade.keygeneratorservice.keygen.application.snowflake.core;

import com.isaacandrade.keygeneratorservice.keygen.application.snowflake.exception.RegressiveClockException;
import org.springframework.stereotype.Component;

@Component
public class TimestampValidator {
    public void validateTimestamp(long currentTimestamp, long lastTimestamp) {
        if (currentTimestamp < lastTimestamp) {
            throw new RegressiveClockException("Clock Moved Backwards!");
        }
    }
}
