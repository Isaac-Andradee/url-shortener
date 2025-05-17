package com.isaacandrade.keygeneratorservice.keygen.application.snowflake.infra.network;

import org.springframework.stereotype.Component;

import java.net.InetAddress;

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
