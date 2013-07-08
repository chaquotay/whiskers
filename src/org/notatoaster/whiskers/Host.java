package org.notatoaster.whiskers;

import org.notatoaster.whiskers.http.HttpHost;
import org.notatoaster.whiskers.smtp.SmtpHost;

import java.net.InetAddress;

public class Host {

    private InetAddress address;

    public Host(InetAddress address) {

        this.address = address;
    }

    public HttpHost http() {
        return http(80);
    }

    public HttpHost http(int port) {
        return new HttpHost(address, port);
    }

    public SmtpHost smtp(String domain) {
        return smtp(domain, 25);
    }

    public SmtpHost smtp(String domain, int port) {
        return new SmtpHost(address, port, domain);
    }

    public static Host create(InetAddress address) {
        return new Host(address);
    }
}
