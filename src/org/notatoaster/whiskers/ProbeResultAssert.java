package org.notatoaster.whiskers;

import static org.junit.Assert.assertTrue;

public class ProbeResultAssert {

    public static void assertSuccess(ProbeResult probeResult) {
        assertTrue(probeResult.getMessage(), probeResult.isSuccess());
    }

    public static void assertError(ProbeResult probeResult) {
        assertTrue(probeResult.getMessage(), probeResult.isError());
    }

}
