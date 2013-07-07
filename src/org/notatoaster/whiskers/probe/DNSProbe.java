package org.notatoaster.whiskers.probe;


import org.notatoaster.whiskers.ProbeResult;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class DNSProbe {

    public ProbeResult resolvesTo(String hostname, InetAddress expectedAddress) throws UnknownHostException {
        InetAddress actualAddress = Inet4Address.getByName(hostname);

        if(expectedAddress==null) {
            if(actualAddress==null) {
                return ProbeResult.success();
            } else {
                return ProbeResult.error("Expected no successful resolution, but hostname " + hostname + " resolved to " + actualAddress);
            }
        } else {
            if(expectedAddress.equals(actualAddress)) {
                return ProbeResult.success();
            } else {
                return ProbeResult.error("Expected resolution of hostname " + hostname + " to " + expectedAddress + ", but instead it resolved to " + actualAddress);
            }
        }
    }

}
