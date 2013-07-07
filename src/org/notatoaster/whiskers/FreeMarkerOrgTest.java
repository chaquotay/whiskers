package org.notatoaster.whiskers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.notatoaster.whiskers.probe.DNSProbe;
import org.notatoaster.whiskers.probe.HttpProbe;
import org.notatoaster.whiskers.probe.SMTPProbe;
import org.notatoaster.whiskers.probe.SMTPRequest;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.notatoaster.whiskers.ProbeResultAssert.assertError;
import static org.notatoaster.whiskers.ProbeResultAssert.assertSuccess;

@RunWith(JUnit4.class)
public class FreeMarkerOrgTest {

    private final InetAddress realAddress;
    private final InetAddress localhostAddress = InetAddress.getByAddress(new byte[]{127, 0, 0, 1});

    public FreeMarkerOrgTest() throws UnknownHostException {
        realAddress = InetAddress.getByAddress(new byte[]{46, 4, 106, 76});
    }

    @Test
    public void testDns4() throws UnknownHostException {
        DNSProbe probe = new DNSProbe();
        ProbeResult result = probe.resolvesTo("freemarker.org", realAddress);
        assertSuccess(result);
    }

    @Test
    public void testDns4WWW() throws UnknownHostException {
        DNSProbe probe = new DNSProbe();
        ProbeResult result = probe.resolvesTo("www.freemarker.org", realAddress);
        assertSuccess(result);
    }

    @Test
    public void testIndexHtmlIsFoundWithCorrectTitle() throws IOException {
        HttpProbe httpProbe = new HttpProbe();
        httpProbe.addHost("freemarker.org", realAddress);
        ProbeResult result = httpProbe.isFound("http://freemarker.org/index.html","FreeMarker Java Template Engine - Overview");
        assertSuccess(result);
    }

    @Test
    public void testIndexHtmlIsFoundWithWWWRedirect() throws IOException {
        HttpProbe httpProbe = new HttpProbe();
        httpProbe.addHost("www.freemarker.org", realAddress);
        ProbeResult result = httpProbe.isRedirect("http://www.freemarker.org/index.html", "http://freemarker.org/index.html");
        assertSuccess(result);
    }

    @Test
    public void testIndexHtmlIsNotFoundWithWrongHost() throws IOException {
        HttpProbe httpProbe = new HttpProbe();
        httpProbe.addHost("notatoaster.org", realAddress);
        ProbeResult result = httpProbe.isFound("http://notatoaster.org/index.html","FreeMarker: Java Template Engine Library - Overview");
        assertError(result);
    }

    @Test
    public void testIndexHtmlIsNotFoundWithWrongTitle() throws IOException {
        HttpProbe httpProbe = new HttpProbe();
        httpProbe.addHost("freemarker.org", realAddress);
        ProbeResult result = httpProbe.isFound("http://freemarker.org/index.html","Something different");
        assertError(result);
    }

    @Test
    public void testNothingIsFoundOnWrongServer() throws IOException {
        HttpProbe httpProbe = new HttpProbe();
        httpProbe.addHost("freemarker.org", localhostAddress);
        ProbeResult result = httpProbe.isFound("http://freemarker.org/index.html","FreeMarker: Java Template Engine Library - Overview");
        assertError(result);
    }

    @Test
    public void testSmtpReceivesMail() throws IOException {
        SMTPRequest req = new SMTPRequest(realAddress, 25,"freemarker.org","abuse@freemarker.org");
        SMTPProbe probe = new SMTPProbe();
        ProbeResult result = probe.probe(req);
        assertSuccess(result);
    }
}
