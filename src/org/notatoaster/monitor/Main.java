package org.notatoaster.monitor;

import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.JUnit4;
import org.junit.runners.model.InitializationError;

public class Main {

    public static void main(String[] args) throws InitializationError {
        JUnit4 runner = new JUnit4(FreeMarkerOrgTest.class);
        RunNotifier notifier = new RunNotifier();
        CollectingRunListener clr = new CollectingRunListener();
        notifier.addListener(clr);
        runner.run(notifier);
        int failureCount = clr.getFailures().size();
        if(failureCount!=0) {
            StringBuilder msg = new StringBuilder();
            msg.append(failureCount);
            msg.append(" failures occurred!\r\n");
            for(Failure f: clr.getFailures()) {
                msg.append("- ");
                msg.append(f.getDescription());
                msg.append("\r\n");
            }
            System.out.println(msg.toString());
        } else {
            System.out.println("No failures!");
        }
    }
}
