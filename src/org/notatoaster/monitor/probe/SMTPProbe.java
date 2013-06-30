package org.notatoaster.monitor.probe;

import org.apache.commons.net.smtp.SMTPClient;
import org.apache.commons.net.smtp.SMTPReply;

import java.io.IOException;

public class SMTPProbe {

    public int probe(SMTPRequest req) throws IOException {
        SMTPClient client = new SMTPClient();
        client.connect(req.getAddress(), req.getPort());
        try {
            int replyCode = client.getReplyCode();
            if (!SMTPReply.isPositiveCompletion(replyCode))
                return 1;

            replyCode = client.helo(req.getDomain());
            if (!SMTPReply.isPositiveCompletion(replyCode))
                return 2;

            replyCode = client.mail("<test@chaquotay.net>");
            if (!SMTPReply.isPositiveCompletion(replyCode))
                return replyCode;

            replyCode = client.rcpt(req.getEmailAddress());
            if (!SMTPReply.isPositiveCompletion(replyCode))
                return 4;

            client.quit();

        } finally {
            client.disconnect();
        }
        return 0;
    }
}
