package com.isaacandrade.keygeneratorservice.config;

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
@Profile("!test")
public class EurekaClientConfig {
    private final ConfigurableEnvironment env;

    public EurekaClientConfig(final ConfigurableEnvironment env) {
        this.env = env;
    }

    @Bean
    @Primary
    public EurekaInstanceConfigBean eurekaInstanceConfigBean(final InetUtils inetUtils) throws IOException {
        final String hostName = Optional.ofNullable(System.getenv("HOSTNAME"))
                .orElse(InetAddress.getLocalHost().getHostName());
        String hostAddress = get(hostName);

        int nonSecurePort = Integer.parseInt(env.getProperty("server.port", env.getProperty("port", "8081")));

        final EurekaInstanceConfigBean instance = new EurekaInstanceConfigBean(inetUtils);
        instance.setHostname(hostName);
        instance.setIpAddress(hostAddress);
        instance.setNonSecurePort(nonSecurePort);
        return instance;
    }

    private static String get(String hostName) throws SocketException, UnknownHostException {
        String hostAddress = null;

        final Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
        for (NetworkInterface netInt : Collections.list(networkInterfaces)) {
            for (InetAddress inetAddress : Collections.list(netInt.getInetAddresses())) {
                if (hostName.equals(inetAddress.getHostName())) {
                    hostAddress = inetAddress.getHostAddress();
                }
            }
        }
        if (hostAddress == null) {
            throw new UnknownHostException("Cannot find ip address for hostname: " + hostName);
        }
        return hostAddress;
    }
}
