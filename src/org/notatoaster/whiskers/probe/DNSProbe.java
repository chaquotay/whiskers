package org.notatoaster.whiskers.probe;


import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class DNSProbe {

    public boolean resolvesTo(String hostname, InetAddress expectedAddress) throws UnknownHostException {
        InetAddress actualAddress = Inet4Address.getByName(hostname);

        if(expectedAddress==null) {
            return actualAddress==null;
        } else {
            return expectedAddress.equals(actualAddress);
        }
    }

}
