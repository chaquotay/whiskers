package org.notatoaster.whiskers.nameservice;

import sun.net.spi.nameservice.NameService;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class ControlledNameService implements NameService {

    @Override
    public InetAddress[] lookupAllHostAddr(String s) throws UnknownHostException {
        return new InetAddress[0];
    }

    @Override
    public String getHostByAddr(byte[] bytes) throws UnknownHostException {
        return null;
    }
}
