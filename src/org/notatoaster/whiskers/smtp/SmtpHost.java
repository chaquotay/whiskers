package org.notatoaster.whiskers.smtp;

import org.apache.commons.net.smtp.SMTPClient;
import org.apache.commons.net.smtp.SMTPReply;
import org.notatoaster.whiskers.ProbeResult;

import java.io.IOException;
import java.net.InetAddress;

public class SmtpHost {
    private InetAddress address;
    private int port;
    private String domain;

    public SmtpHost(InetAddress address, int port, String domain) {
        this.address = address;

        this.port = port;
        this.domain = domain;
    }

    public ProbeResult send(Mail mail) throws IOException {
        SMTPClient client = new SMTPClient();
        client.connect(address, port);
        try {
            int replyCode = client.getReplyCode();
            if (!SMTPReply.isPositiveCompletion(replyCode))
                return ProbeResult.error("SMTP connect failed with reply code " + replyCode);

            replyCode = client.helo(domain);
            if (!SMTPReply.isPositiveCompletion(replyCode))
                return ProbeResult.error("SMTP HELO failed with reply code " + replyCode);

            replyCode = client.mail(mail.getFrom());
            if (!SMTPReply.isPositiveCompletion(replyCode))
                return ProbeResult.error("SMTP MAIL FROM failed with reply code " + replyCode);

            replyCode = client.rcpt(mail.getTo());
            if (!SMTPReply.isPositiveCompletion(replyCode))
                return ProbeResult.error("SMTP RCTP TO failed with reply code " + replyCode);

            client.quit();

        } finally {
            client.disconnect();
        }
        return ProbeResult.success();
    }
}
