package org.notatoaster.whiskers.dns;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.xbill.DNS.ARecord;
import org.xbill.DNS.Record;

import java.net.InetAddress;

public class DNS {

    public static Matcher<Record> address(InetAddress address) {
        return new DnsARecordMatcher(address);
    }

    private static class DnsARecordMatcher extends TypeSafeMatcher<Record> {

        private InetAddress address;

        public DnsARecordMatcher(InetAddress address) {

            this.address = address;
        }

        @Override
        protected boolean matchesSafely(Record record) {
            if(record==null || !(record instanceof ARecord))
                return false;

            ARecord arec = (ARecord)record;
            return arec.getAddress().equals(address);
        }

        @Override
        public void describeTo(Description description) {
            description.appendText("address " + address.getHostAddress());
        }
    }
}
