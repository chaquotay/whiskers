package org.notatoaster.whiskers;

import org.junit.runner.Description;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;

import java.util.ArrayList;
import java.util.Collection;

public class CollectingRunListener extends RunListener {

    private ArrayList<Failure> failures = new ArrayList<Failure>();
    private int run;

    @Override
    public void testFailure(Failure f) throws Exception {
        failures.add(f);
    }

    @Override
    public void testFinished(Description description) throws Exception {
        run++;
    }

    public Collection<Failure> getFailures() {
        return failures;
    }

    public int getRun() {
        return run;
    }
}
