package com.isaacandrade.keygeneratorservice.keygen.application.snowflake.infra.network;

import org.springframework.stereotype.Component;

import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

@Component
public class DefaultNetworkInterfaceProvider implements NetworkInterfaceProvider{
    @Override
    public Enumeration<NetworkInterface> getNetworkInterfaces() throws SocketException {
        return NetworkInterface.getNetworkInterfaces();
    }
}
