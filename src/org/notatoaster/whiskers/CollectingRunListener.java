package org.notatoaster.whiskers;

import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;

import java.util.ArrayList;
import java.util.Collection;

public class CollectingRunListener extends RunListener {

    private ArrayList<Failure> failures = new ArrayList<Failure>();

    @Override
    public void testFailure(Failure f) throws Exception {
        failures.add(f);
    }

    public Collection<Failure> getFailures() {
        return failures;
    }

}
