package org.notatoaster.whiskers;

import org.apache.http.HttpResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.notatoaster.whiskers.html.HTML;
import org.notatoaster.whiskers.http.HTTP;
import org.notatoaster.whiskers.smtp.Mail;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.notatoaster.whiskers.http.HTTP.redirectTo;


@RunWith(JUnit4.class)
public class FreeMarkerOrgTest2 {


    private final Host server;

    public FreeMarkerOrgTest2() throws UnknownHostException {
        InetAddress realAddress = InetAddress.getByAddress(new byte[]{46, 4, 106, 76});
        server = Host.create(realAddress);
    }

    @Test
    public void testIndexHtmlIsFoundWithCorrectTitle() throws IOException {
        HttpResponse response = server.http().get("freemarker.org", "/index.html");
        assertThat(response, is(HTTP.success()));
        assertThat(response, HTML.hasTitle("FreeMarker Java Template Engine - Overview"));
    }

    @Test
    public void testIndexHtmlIsFoundWithCorrectTitle2() throws IOException {
        assertThat(
                server.http().get("freemarker.org", "/index.html"),
                allOf(
                        is(HTTP.success()),
                        HTML.hasTitle("FreeMarker Java Template Engine - Overview")
                )
        );
    }

    @Test
     public void testRedirect() throws IOException {
        assertThat(
                server.http().get("www.freemarker.org", "/index.html"),
                is(redirectTo("http://freemarker.org/index.html")));
    }

    @Test
    public void testSmtp() throws IOException {
        ProbeResult result = server.smtp("freemarker.org").send(new Mail("<test@chaquotay.net>","abuse@freemarker.org"));
        //assertThat(result, is(notNullValue()));
        assertThat(result.isSuccess(), is(true));
    }
}
