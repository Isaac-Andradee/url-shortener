package com.isaacandrade.keygeneratorservice.snowflake.infra.node;

import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;


// TODO: Maybe find others ways to get both nodes...
// TODO: Abstract this class to allow different implementations

@Component
public class DefaultNodeInfoProvider implements NodeInfoProvider{
    @Override
    public long getDatacenterId() {
        String hostname = resolveHostname();
        return hashToRange(hostname.hashCode(), 32);
    }

    @Override
    public long getMachineId() {
        InetAddress address = resolveNonLoopbackIPv4();
        return extractLastByte(address) & 0x1F;
    }

    private String resolveHostname() {
        try {
            return InetAddress.getLocalHost().getHostName();
        } catch (Exception e) {
            return "default-host";
        }
    }

    private InetAddress resolveNonLoopbackIPv4() {
        try {
            Enumeration<NetworkInterface> nics = NetworkInterface.getNetworkInterfaces();
            while (nics.hasMoreElements()) {
                NetworkInterface nic = nics.nextElement();
                InetAddress address = getFirstValidAddress(nic);
                if (address != null) return address;
            }
            return InetAddress.getLocalHost();
        } catch (Exception e) {
            throw new RuntimeException("Unable to resolve machine IP", e);
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
