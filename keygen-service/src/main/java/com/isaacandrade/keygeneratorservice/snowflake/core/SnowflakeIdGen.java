package com.isaacandrade.keygeneratorservice.keygen.application;

import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import static com.isaacandrade.keygeneratorservice.keygen.utils.SnowflakeConstants.*;

@Component
public class SnowflakeIdGen {

    private final long datacenterId;
    private final long machineId;


    public SnowflakeIdGen() {
        this.datacenterId = getDatacenterId();
        this.machineId = getMachineId();
    }

    public synchronized long nextId() {
        long timestamp = currentTime();

        if (timestamp < lastTimestamp) {
            throw new IllegalStateException("Clock moved backwards.");
        }

        if (timestamp == lastTimestamp) {
            sequence = (sequence + 1) & MAX_SEQUENCE;
            if (sequence == 0) {
                timestamp = waitNextMillis(timestamp);
            }
        } else {
            sequence = 0;
        }

        lastTimestamp = timestamp;

        return ((timestamp - EPOCH) << TIMESTAMP_SHIFT)
                | (datacenterId << DATACENTER_ID_SHIFT)
                | (machineId << MACHINE_ID_SHIFT)
                | sequence;
    }

    private long currentTime() {
        return System.currentTimeMillis();
    }

    private long waitNextMillis(long lastTimestamp) {
        long timestamp = currentTime();
        while (timestamp <= lastTimestamp) {
            timestamp = currentTime();
        }
        return timestamp;
    }

    private long getDatacenterId() {
        try {
            String hostname = InetAddress.getLocalHost().getHostName();
            return Math.abs(hostname.hashCode()) % 32;
        } catch (Exception e) {
            return 1L;
        }
    }

    private long getMachineId() {
        try {
            InetAddress ip = getLocalAddress();
            byte[] addr = ip.getAddress();
            return addr[addr.length - 1] & 0x1F;
        } catch (Exception e) {
            return 1L;
        }
    }

    private InetAddress getLocalAddress() throws Exception {
        Enumeration<NetworkInterface> nics = NetworkInterface.getNetworkInterfaces();
        while (nics.hasMoreElements()) {
            NetworkInterface nic = nics.nextElement();
            Enumeration<InetAddress> addresses = nic.getInetAddresses();
            while (addresses.hasMoreElements()) {
                InetAddress addr = addresses.nextElement();
                if (!addr.isLoopbackAddress() && addr.getHostAddress().indexOf(':') == -1) {
                    return addr;
                }
            }
        }
        return InetAddress.getLocalHost();
    }
}
