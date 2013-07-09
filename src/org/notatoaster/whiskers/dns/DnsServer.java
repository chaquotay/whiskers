package org.notatoaster.whiskers.dns;

import org.xbill.DNS.Lookup;
import org.xbill.DNS.Record;
import org.xbill.DNS.SimpleResolver;
import org.xbill.DNS.TextParseException;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class DnsServer {

    private final InetAddress address;

    public DnsServer(InetAddress address) {

        this.address = address;
    }

    public Record resolve(String domain, int recordType) throws TextParseException, UnknownHostException {
        Lookup lookup = new Lookup(domain, recordType);
        String hostname = address.getHostAddress();
        lookup.setResolver(new SimpleResolver(hostname));
        Record[] records = lookup.run();
        if(records!=null && records.length==1)
            return records[0];

        return null;
    }

}
