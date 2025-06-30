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
