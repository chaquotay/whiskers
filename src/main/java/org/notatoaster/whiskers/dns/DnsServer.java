package org.notatoaster.whiskers.dns;

import org.xbill.DNS.*;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class DnsServer {

    private final InetAddress address;

    public DnsServer(InetAddress address) {

        this.address = address;
    }

    public Record resolve(String domain, int recordType) throws TextParseException, UnknownHostException {
        Lookup lookup = new Lookup(domain, recordType);

        // Use empty cache for every lookup, so that a lookup with the specified resolver is actually performed
        // and not answered from the cache.
        Cache cache = new Cache();
        lookup.setCache(cache);

        String hostname = address.getHostAddress();
        lookup.setResolver(new SimpleResolver(hostname));
        Record[] records = lookup.run();
        if(records!=null && records.length==1)
            return records[0];

        return null;
    }

    @Override
    public String toString() {
        return "DNS @ "+address.toString();
    }
}
