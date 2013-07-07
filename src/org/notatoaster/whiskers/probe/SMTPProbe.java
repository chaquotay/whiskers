package org.notatoaster.whiskers.probe;

import org.apache.commons.net.smtp.SMTPClient;
import org.apache.commons.net.smtp.SMTPReply;
import org.notatoaster.whiskers.ProbeResult;

import java.io.IOException;

public class SMTPProbe {

    public ProbeResult probe(SMTPRequest req) throws IOException {
        SMTPClient client = new SMTPClient();
        client.connect(req.getAddress(), req.getPort());
        try {
            int replyCode = client.getReplyCode();
            if (!SMTPReply.isPositiveCompletion(replyCode))
                return ProbeResult.error("SMTP connect failed with reply code " + replyCode);

            replyCode = client.helo(req.getDomain());
            if (!SMTPReply.isPositiveCompletion(replyCode))
                return ProbeResult.error("SMTP HELO failed with reply code " + replyCode);

            replyCode = client.mail("<test@chaquotay.net>");
            if (!SMTPReply.isPositiveCompletion(replyCode))
                return ProbeResult.error("SMTP MAIL FROM failed with reply code " + replyCode);

            replyCode = client.rcpt(req.getEmailAddress());
            if (!SMTPReply.isPositiveCompletion(replyCode))
                return ProbeResult.error("SMTP RCTP TO failed with reply code " + replyCode);

            client.quit();

        } finally {
            client.disconnect();
        }
        return ProbeResult.success();
    }
}
