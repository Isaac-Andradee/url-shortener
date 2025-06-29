/*
 * Copyright 2024 the original author.
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
package com.isaacandrade.urlshortenerservice.config;

import com.netflix.appinfo.InstanceInfo;
import org.springframework.boot.actuate.health.HealthEndpoint;
import org.springframework.boot.actuate.health.Status;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.commons.util.InetUtils;
import org.springframework.cloud.netflix.eureka.EurekaHealthCheckHandler;
import org.springframework.cloud.netflix.eureka.EurekaInstanceConfigBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.ConfigurableEnvironment;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.Optional;

/**
 * Configuration class for Eureka client in the shortener service.
 *
 * @author Isaac Andrade
 */
@Configuration
@EnableConfigurationProperties
@Profile("!test")
public class EurekaClientConfig {
    private final ConfigurableEnvironment env;

    public EurekaClientConfig(final ConfigurableEnvironment env) {
        this.env = env;
    }

    @Bean
    public EurekaHealthCheckHandler healthCheckHandler(HealthEndpoint healthEndpoint) {
        return new EurekaHealthCheckHandler(statuses -> healthEndpoint.health().getStatus()) {
            @Override
            public InstanceInfo.InstanceStatus getStatus(InstanceInfo.InstanceStatus currentStatus) {
                Status status = healthEndpoint.health().getStatus();
                return status.equals(Status.UP) ? InstanceInfo.InstanceStatus.UP : InstanceInfo.InstanceStatus.DOWN;
            }
        };
    }

    @Bean
    @Primary
    public EurekaInstanceConfigBean eurekaInstanceConfigBean(final InetUtils inetUtils) {
        final String hostName = Optional.ofNullable(System.getenv("HOSTNAME"))
                .filter(s -> !s.isBlank())
                .orElseGet(() -> {
                    try {
                        return InetAddress.getLocalHost().getHostName();
                    } catch (UnknownHostException e) {
                        return "localhost";
                    }
                });
        String hostAddress = get(hostName);

        int nonSecurePort = Integer.parseInt(env.getProperty("server.port", env.getProperty("port", "8080")));

        final EurekaInstanceConfigBean instance = new EurekaInstanceConfigBean(inetUtils);
        instance.setHostname(hostName);
        instance.setIpAddress(hostAddress);
        instance.setNonSecurePort(nonSecurePort);
        return instance;
    }

    private static String get(String hostName) {
        try {
            for (NetworkInterface ni : Collections.list(NetworkInterface.getNetworkInterfaces())) {
                for (InetAddress ia : Collections.list(ni.getInetAddresses())) {
                    if (hostName.equalsIgnoreCase(ia.getHostName())) {
                        return ia.getHostAddress();
                    }
                }
            }
        } catch (SocketException ignored) { }
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            return "127.0.0.1";
        }
    }
}
