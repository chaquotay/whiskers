package org.notatoaster.whiskers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.notatoaster.whiskers.dns.DnsServer;
import org.xbill.DNS.TextParseException;
import org.xbill.DNS.Type;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Collection;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.notatoaster.whiskers.dns.DNS.address;

@RunWith(Parameterized.class)
public class FreeMarkerOrgDnsTest {

    private final DnsServer dnsServer;
    private final InetAddress serverAddress = InetAddress.getByAddress(new byte[]{46, 4, 106, 76});

    @Parameterized.Parameters(name = "{0}")
    public static Collection<Object[]> dnsServers() throws UnknownHostException {
        final DnsServer googleDNS = new DnsServer(InetAddress.getByAddress(new byte[]{8, 8, 8, 8}));
        final DnsServer mainDNS1 = new DnsServer(InetAddress.getByName("NS2198.DNS.DYN.COM"));
        final DnsServer mainDNS2 = new DnsServer(InetAddress.getByName("NS1144.DNS.DYN.COM"));
        final DnsServer mainDNS3 = new DnsServer(InetAddress.getByName("NS4192.DNS.DYN.COM"));
        final DnsServer mainDNS4 = new DnsServer(InetAddress.getByName("NS3157.DNS.DYN.COM"));

        return Arrays.asList(new Object[][]{
                {googleDNS},
                {mainDNS1},
                {mainDNS2},
                {mainDNS3},
                {mainDNS4}
        });
    }

    public FreeMarkerOrgDnsTest(DnsServer dnsServer) throws UnknownHostException {
        this.dnsServer = dnsServer;
    }

    @Test
    public void testDnsA() throws UnknownHostException, TextParseException {
        assertThat(
                dnsServer.resolve("freemarker.org", Type.A),
                is(address(serverAddress))
        );
    }

    @Test
    public void testDnsAWww() throws UnknownHostException, TextParseException {
        assertThat(
                dnsServer.resolve("www.freemarker.org", Type.A),
                is(address(serverAddress))
        );
    }

}
