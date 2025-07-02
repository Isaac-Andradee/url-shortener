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

import com.isaacandrade.keygeneratorservice.keygen.application.snowflake.infra.network.NodeInfoProvider;
import org.springframework.stereotype.Component;

import static com.isaacandrade.keygeneratorservice.keygen.application.snowflake.utils.SnowflakeConstants.*;

/**
 * Assembles a unique ID using the Snowflake algorithm.
 * This class combines the timestamp, datacenter ID, machine ID, and sequence number into a single long value.
 * It is used to generate unique identifiers in a distributed system.
 *
 * @author Isaac Andrade
 */
@Component
public class IdAssembler {

    private final long datacenterId;
    private final long machineId;

    public IdAssembler(NodeInfoProvider nodeInfoProvider) {
        this.datacenterId = nodeInfoProvider.getDatacenterId();
        this.machineId = nodeInfoProvider.getMachineId();
    }

    public long assemble(long timestamp, long sequence) {
        return ((timestamp - EPOCH) << TIMESTAMP_SHIFT)
                | (datacenterId << DATACENTER_ID_SHIFT)
                | (machineId << MACHINE_ID_SHIFT)
                | sequence;
    }
}
