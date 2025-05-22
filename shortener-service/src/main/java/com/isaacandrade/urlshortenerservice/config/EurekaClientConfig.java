package com.isaacandrade.urlshortenerservice.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.commons.util.InetUtils;
import org.springframework.cloud.netflix.eureka.EurekaInstanceConfigBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.ConfigurableEnvironment;

import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Optional;

@Configuration
@EnableConfigurationProperties
@Profile("!test")
public class EurekaClientConfig {
    private final ConfigurableEnvironment env;

    public EurekaClientConfig(final ConfigurableEnvironment env) {
        this.env = env;
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
