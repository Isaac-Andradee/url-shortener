package com.isaacandrade.keygeneratorservice.keygen.application.snowflake.infra.network;

import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public interface NetworkInterfaceProvider {
    Enumeration<NetworkInterface> getNetworkInterfaces() throws SocketException;
}
