package org.notatoaster.whiskers.smtp;

/**
 * Wrapper for SMTP reply code, along the lines of HttpResponse
 */
public class SmtpResponse {
    private final int replyCode;

    public SmtpResponse(int replyCode) {

        this.replyCode = replyCode;
    }

    public int getReplyCode() {
        return replyCode;
    }

    @Override
    public String toString() {
        return "replyCode: " + replyCode;
    }
}
