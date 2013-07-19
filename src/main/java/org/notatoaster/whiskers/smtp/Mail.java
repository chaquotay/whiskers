package org.notatoaster.whiskers.smtp;


public class Mail {

    private final String from;
    private final String to;

    public Mail(String from, String to) {

        this.from = from;
        this.to = to;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }
}
