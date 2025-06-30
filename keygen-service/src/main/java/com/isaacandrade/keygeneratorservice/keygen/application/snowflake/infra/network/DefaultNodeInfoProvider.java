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

import com.isaacandrade.keygeneratorservice.keygen.application.snowflake.exception.NetworkException;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;


// TODO: Maybe find others ways to get both nodes...
// TODO: Abstract this class to allow different implementations

@Component
public class DefaultNodeInfoProvider implements NodeInfoProvider{
    private final HostnameResolver hostnameResolver;
    private final NetworkInterfaceProvider networkInterfaceProvider;

    public DefaultNodeInfoProvider(HostnameResolver hostnameResolver,
                                   NetworkInterfaceProvider networkInterfaceProvider) {
        this.hostnameResolver = hostnameResolver;
        this.networkInterfaceProvider = networkInterfaceProvider;
    }

    @Override
    public long getDatacenterId() {
        String hostname = hostnameResolver.resolve();
        return hashToRange(hostname.hashCode(), 32);
    }

    @Override
    public long getMachineId() {
        InetAddress address = resolveNonLoopbackIPv4();
        return extractLastByte(address) & 0x1F;
    }

    private InetAddress resolveNonLoopbackIPv4() {
        try {
            Enumeration<NetworkInterface> nics = networkInterfaceProvider.getNetworkInterfaces();
            while (nics.hasMoreElements()) {
                NetworkInterface nic = nics.nextElement();
                InetAddress address = getFirstValidAddress(nic);
                if (address != null) return address;
            }
            return InetAddress.getLocalHost();
        } catch (Exception e) {
            throw new NetworkException("Unable to resolve machine IP");
        }
    }

    private InetAddress getFirstValidAddress(NetworkInterface nic) {
        Enumeration<InetAddress> addresses = nic.getInetAddresses();
        while (addresses.hasMoreElements()) {
            InetAddress addr = addresses.nextElement();
            if (isValidIPv4(addr)) return addr;
        }
        return null;
    }

    private boolean isValidIPv4(InetAddress address) {
        return !address.isLoopbackAddress() && address.getHostAddress().indexOf(':') == -1;
    }

    private int extractLastByte(InetAddress address) {
        byte[] bytes = address.getAddress();
        return bytes[bytes.length - 1];
    }

    private long hashToRange(int hash, int range) {
        return Math.abs(hash) % range;
    }
}
