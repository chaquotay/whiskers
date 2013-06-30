package org.notatoaster.monitor;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.notatoaster.monitor.probe.HttpProbe;
import org.notatoaster.monitor.probe.SMTPProbe;
import org.notatoaster.monitor.probe.SMTPRequest;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(JUnit4.class)
public class FreeMarkerOrgTest {

    private final InetAddress realAddress;
    private final InetAddress localhostAddress = InetAddress.getByAddress(new byte[]{127, 0, 0, 1});

    public FreeMarkerOrgTest() throws UnknownHostException {
        realAddress = InetAddress.getByAddress(new byte[]{46, 4, 106, 76});
    }

    @Test
    public void testIndexHtmlIsFoundWithCorrectTitle() throws IOException {
        HttpProbe httpProbe = new HttpProbe();
        httpProbe.addHost("freemarker.org", realAddress);
        boolean found = httpProbe.isFound("http://freemarker.org/index.html","FreeMarker: Java Template Engine Library - Overview");
        assertTrue(found);
    }

    @Test
    public void testIndexHtmlIsFoundWithWWWRedirect() throws IOException {
        HttpProbe httpProbe = new HttpProbe();
        httpProbe.addHost("www.freemarker.org", realAddress);
        boolean isRedirected = httpProbe.isRedirect("http://www.freemarker.org/index.html", "http://freemarker.org/index.html");
        assertTrue(isRedirected);
    }

    @Test
    public void testIndexHtmlIsNotFoundWithWrongHost() throws IOException {
        HttpProbe httpProbe = new HttpProbe();
        httpProbe.addHost("notatoaster.org", realAddress);
        boolean found = httpProbe.isFound("http://notatoaster.org/index.html","FreeMarker: Java Template Engine Library - Overview");
        assertFalse(found);
    }

    @Test
    public void testIndexHtmlIsNotFoundWithWrongTitle() throws IOException {
        HttpProbe httpProbe = new HttpProbe();
        httpProbe.addHost("freemarker.org", realAddress);
        boolean found = httpProbe.isFound("http://freemarker.org/index.html","Something different");
        assertFalse(found);
    }

    @Test
    public void testNothingIsFoundOnWrongServer() throws IOException {
        HttpProbe httpProbe = new HttpProbe();
        httpProbe.addHost("freemarker.org", localhostAddress);
        boolean found = httpProbe.isFound("http://freemarker.org/index.html","FreeMarker: Java Template Engine Library - Overview");
        assertFalse(found);
    }

    @Test
    public void testSmtpReceivesMail() throws IOException {
        SMTPRequest req = new SMTPRequest(realAddress, 25,"freemarker.org","abuse@freemarker.org");
        SMTPProbe probe = new SMTPProbe();
        int result = probe.probe(req);
        assertEquals(0, result);
    }
}
