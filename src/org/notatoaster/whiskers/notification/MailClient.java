package org.notatoaster.whiskers.notification;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

public class MailClient {

    private final Properties properties;
    private final Authenticator authenticator;

    public MailClient(Properties properties) {
        this.properties = properties;
        final String user = properties.getProperty("mail.smtp.user", "");
        final String password = properties.getProperty("mail.smtp.password", "");

        authenticator = new Authenticator() {
            @Override
            public PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(user, password);
            }
        };
    }

    public void sendMail(String from, String to, String subject, String body) throws MessagingException {
        Session session = Session.getDefaultInstance(properties, authenticator);

        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(from));
        msg.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
        msg.setSubject(subject);
        msg.setSentDate(new Date());
        msg.setText(body);
        msg.saveChanges();
        Transport.send(msg);
    }

}
