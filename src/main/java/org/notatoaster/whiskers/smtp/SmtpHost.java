package org.notatoaster.whiskers.smtp;

import org.apache.commons.net.smtp.SMTPClient;
import org.apache.commons.net.smtp.SMTPReply;

import java.io.IOException;
import java.net.InetAddress;

public class SmtpHost {
    private InetAddress address;
    private int port;

    public SmtpHost(InetAddress address, int port) {
        this.address = address;
        this.port = port;
    }

    public SmtpResponse send(String domain, Mail mail) throws IOException {
        SMTPClient client = new SMTPClient();
        client.connect(address, port);
        try {
            int replyCode = client.getReplyCode();
            if (!SMTPReply.isPositiveCompletion(replyCode))
                return new SmtpResponse(replyCode);
                //return error("SMTP connect failed with reply code " + replyCode);

            replyCode = client.helo(domain);
            if (!SMTPReply.isPositiveCompletion(replyCode))
                return new SmtpResponse(replyCode);
                //return error("SMTP HELO failed with reply code " + replyCode);

            replyCode = client.mail(mail.getFrom());
            if (!SMTPReply.isPositiveCompletion(replyCode))
                return new SmtpResponse(replyCode);
                //return error("SMTP MAIL FROM failed with reply code " + replyCode);

            replyCode = client.rcpt(mail.getTo());
            if (!SMTPReply.isPositiveCompletion(replyCode))
                return new SmtpResponse(replyCode);
                //return error("SMTP RCTP TO failed with reply code " + replyCode);

            client.quit();

        } finally {
            client.disconnect();
        }

        return new SmtpResponse(SMTPReply.SERVICE_CLOSING_TRANSMISSION_CHANNEL);
    }
}
