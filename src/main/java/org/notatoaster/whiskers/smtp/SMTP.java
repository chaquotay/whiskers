package org.notatoaster.whiskers.smtp;

import org.apache.commons.net.smtp.SMTPReply;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

/**
 * Matchers for SMTP response, specifically their reply codes.
 */
public class SMTP {

    public static Matcher<SmtpResponse> noError() {
        return new ReplyCodeMatcher(SMTPReply.SERVICE_CLOSING_TRANSMISSION_CHANNEL);
    }

    private static class ReplyCodeMatcher extends TypeSafeMatcher<SmtpResponse> {

        private final int expectedReplyCode;

        public ReplyCodeMatcher(int expectedReplyCode) {
            this.expectedReplyCode = expectedReplyCode;
        }

        @Override
        protected boolean matchesSafely(SmtpResponse smtpResponse) {
            return smtpResponse.getReplyCode()==expectedReplyCode;
        }

        @Override
        public void describeTo(Description description) {
            description.appendText("reply code " + expectedReplyCode);
        }
    }
}
