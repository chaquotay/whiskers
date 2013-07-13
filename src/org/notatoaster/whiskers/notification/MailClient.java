package org.notatoaster.whiskers.notification;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
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

    public static MailClient createFromFile(Path propertiesFileName) throws IOException {
        Properties props = new Properties();
        //props.put("mail.smtp.host", "localhost");
        //props.put("mail.smtp.port", "25");587
        props.put("mail.transport.protocol","smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.tls", "true");

        loadPropertiesFile(propertiesFileName, props);

        return new MailClient(props);
    }

    private static void loadPropertiesFile(Path propertiesFileName, Properties props) {
        if(Files.exists(propertiesFileName)) {
            BufferedReader reader = null;
            try {
                reader = Files.newBufferedReader(propertiesFileName, Charset.forName("UTF-8"));
                props.load(reader);
            } catch (Exception ex) {
                if(reader!=null)
                {
                    try {
                        reader.close();
                    } catch (IOException ignored) {

                    }
                }
            }
        }
    }

}
