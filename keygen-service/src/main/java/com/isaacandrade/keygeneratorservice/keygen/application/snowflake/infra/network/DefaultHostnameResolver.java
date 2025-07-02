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
package com.isaacandrade.keygeneratorservice.keygen.application.snowflake.infra.network;

import org.springframework.stereotype.Component;

import java.net.InetAddress;

/**
 * Default implementation of the HostnameResolver interface.
 * This class resolves the hostname of the local machine using InetAddress.
 * If an error occurs during resolution, it returns a default hostname.
 *
 * @author Isaac Andrade
 */
@Component
public class DefaultHostnameResolver implements HostnameResolver{
    @Override
    public String resolve() {
        try {
            return InetAddress.getLocalHost().getHostName();
        } catch (Exception e) {
            return "default-host";
        }
    }
}
