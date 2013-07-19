package org.notatoaster.whiskers.notification;

import javax.mail.MessagingException;

public class MailNotifier implements Notifier
{
    private final MailClient mailClient;
    private final String from;
    private final String to;

    public MailNotifier(MailClient mailClient, String from, String to) {
        this.mailClient = mailClient;
        this.from = from;
        this.to = to;
    }

    @Override
    public void send(Notification notification) {

        try {
            mailClient.sendMail(from, to, "WSKRS notification", notification.getMessage());
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
