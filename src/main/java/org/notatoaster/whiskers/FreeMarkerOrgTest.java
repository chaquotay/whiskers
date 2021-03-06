package org.notatoaster.whiskers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.notatoaster.whiskers.html.HTML;
import org.notatoaster.whiskers.smtp.Mail;
import org.notatoaster.whiskers.smtp.SMTP;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.notatoaster.whiskers.http.HTTP.*;


@RunWith(JUnit4.class)
public class FreeMarkerOrgTest {

    private final Host server;
    private final InetAddress serverAddress;

    public FreeMarkerOrgTest() throws UnknownHostException {
        serverAddress = InetAddress.getByAddress(new byte[]{46, 4, 106, 76});
        server = Host.create(serverAddress);
    }

    @Test
    public void testIndexHtmlHasExpectedTitle() throws IOException {
        assertThat(
                server.http().get("freemarker.org", "/index.html"),
                allOf(
                        is(success()),
                        HTML.hasTitle("FreeMarker Java Template Engine - Overview")
                )
        );
    }

    @Test
    public void testHttpNotFound() throws IOException {
        assertThat(
                server.http().get("freemarker.org", "/test-not-existing-foo-file-20130709.html"),
                is(notFound())
        );
    }

    @Test
     public void testHttpRedirect() throws IOException {
        assertThat(
                server.http().get("www.freemarker.org", "/index.html"),
                is(redirectTo("http://freemarker.org/index.html"))
        );
    }

    @Test
    public void testSmtp() throws IOException {
        assertThat(
                server.smtp().send("freemarker.org", new Mail("<test@chaquotay.net>","abuse@freemarker.org")),
                is(SMTP.noError())
        );
    }
}
