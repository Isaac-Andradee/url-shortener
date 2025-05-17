package com.isaacandrade.keygeneratorservice.unit_tests.keygen.application.snowflake.infra.network;

import com.isaacandrade.keygeneratorservice.keygen.application.snowflake.exception.NetworkException;
import com.isaacandrade.keygeneratorservice.keygen.application.snowflake.infra.network.DefaultNodeInfoProvider;
import com.isaacandrade.keygeneratorservice.keygen.application.snowflake.infra.network.HostnameResolver;
import com.isaacandrade.keygeneratorservice.keygen.application.snowflake.infra.network.NetworkInterfaceProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DefaultNodeInfoProviderTest {
    @Mock
    HostnameResolver hostnameResolver;

    @Mock
    NetworkInterfaceProvider networkInterfaceProvider;

    DefaultNodeInfoProvider provider;

    @BeforeEach
    void setup() {
        provider = new DefaultNodeInfoProvider(hostnameResolver, networkInterfaceProvider);
    }

    @Test
    void getDatacenterId_returnsHashedHostname() {
        when(hostnameResolver.resolve()).thenReturn("host-test");
        long datacenterId = provider.getDatacenterId();
        assertTrue(datacenterId >= 0 && datacenterId < 32);
    }

    @Test
    void getMachineId_returnsLastByteMasked() throws Exception {
        InetAddress mockAddress = InetAddress.getByAddress(new byte[]{10, 0, 0, 42});
        NetworkInterface mockInterface = mock(NetworkInterface.class);
        when(mockInterface.getInetAddresses()).thenReturn(Collections.enumeration(List.of(mockAddress)));
        when(networkInterfaceProvider.getNetworkInterfaces()).thenReturn(Collections.enumeration(List.of(mockInterface)));
        long machineId = provider.getMachineId();
        assertEquals(42 & 0x1F, machineId);
    }

    @Test
    void getMachineId_throwsException_whenNoValidIP() throws Exception {
        when(networkInterfaceProvider.getNetworkInterfaces()).thenThrow(new SocketException());
        assertThrows(NetworkException.class, () -> provider.getMachineId());
    }
}
