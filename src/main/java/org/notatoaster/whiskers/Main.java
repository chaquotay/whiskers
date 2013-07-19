package org.notatoaster.whiskers;

import org.apache.commons.cli.*;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.JUnit4;
import org.junit.runners.Parameterized;
import org.junit.runners.model.InitializationError;
import org.notatoaster.whiskers.notification.MailClient;
import org.notatoaster.whiskers.notification.MailNotifier;
import org.notatoaster.whiskers.notification.Notification;
import org.notatoaster.whiskers.notification.Notifier;
import org.notatoaster.whiskers.util.PropertiesUtil;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.Properties;

public class Main {

    public static void main(String[] args) throws Throwable {
        CommandLine cl = parseCommandLine(args);
        String configFile = cl.getOptionValue('f', "");

        if ("".equals(configFile)) {
            System.err.println("No config file specified!");
            return;
        }

        boolean notifySuccess = cl.hasOption('s');

        Path path = FileSystems.getDefault().getPath(configFile);
        System.out.println("Using config file " + path);
        Properties props = PropertiesUtil.loadPropertiesFile(path);

        RunNotifier notifier = new RunNotifier();
        CollectingRunListener clr = new CollectingRunListener();
        notifier.addListener(clr);

        runTest(notifier, FreeMarkerOrgTest.class);
        runParameterizedTest(notifier, FreeMarkerOrgDnsTest.class);

        int failureCount = clr.getFailures().size();
        if(failureCount!=0 || notifySuccess) {
            StringBuilder msg = new StringBuilder();
            msg.append(failureCount);
            msg.append(" failures occurred!\r\n");
            for(Failure f: clr.getFailures()) {
                msg.append("- ");
                msg.append(f.getDescription());
                msg.append("\r\n");
            }
            System.out.println(msg.toString());
            Notifier n = createNotifier(props);
            Notification x = new Notification(msg.toString());
            n.send(x);
        } else {
            System.out.println("No failures! (" + clr.getRun() + ")");
        }
    }

    private static void runTest(RunNotifier notifier, Class<?> klass) throws InitializationError {
        JUnit4 runner = new JUnit4(klass);
        runner.run(notifier);
    }

    private static void runParameterizedTest(RunNotifier notifier, Class<?> klass) throws Throwable {
        Parameterized runner = new Parameterized(klass);
        runner.run(notifier);
    }

    private static CommandLine parseCommandLine(String[] args) throws ParseException {
        CommandLineParser parser = new BasicParser();
        return parser.parse(createOptions(), args);
    }

    private static Options createOptions() {
        Options opts = new Options();
        opts.addOption("f", "file", true, "configuration file");
        opts.addOption("s", "success", false, "also notify about success");
        return opts;
    }

    private static Notifier createNotifier(Properties properties) throws IOException {
        MailClient mailClient = new MailClient(properties);
        String from = properties.getProperty("notify.mail.from");
        String to = properties.getProperty("notify.mail.to");
        return new MailNotifier(mailClient,from,to);
    }
}
