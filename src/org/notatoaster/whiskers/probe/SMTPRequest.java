package org.notatoaster.whiskers.probe;

import java.net.InetAddress;

public class SMTPRequest {

    private final InetAddress address;
    private final int port;
    private final String domain;
    private final String emailAddress;

    public SMTPRequest(InetAddress address, int port, String domain, String emailAddress) {

        this.address = address;
        this.port = port;
        this.domain = domain;
        this.emailAddress = emailAddress;
    }

    public InetAddress getAddress() {
        return address;
    }

    public int getPort() {
        return port;
    }

    public String getDomain() {
        return domain;
    }

    public String getEmailAddress() {
        return emailAddress;
    }
}
