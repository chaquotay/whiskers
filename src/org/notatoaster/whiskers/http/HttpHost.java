package org.notatoaster.whiskers.http;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.conn.ClientConnectionOperator;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.BasicClientConnectionManager;
import org.apache.http.impl.conn.DefaultClientConnectionOperator;
import org.apache.http.impl.conn.InMemoryDnsResolver;
import org.notatoaster.whiskers.Host;

import java.net.InetAddress;

public class HttpHost {

    private final MyConnectionManager connectionManager;
    private final InetAddress address;
    private final int port;

    public class MyConnectionManager extends BasicClientConnectionManager {

        private InMemoryDnsResolver resolver;

        public MyConnectionManager() {
            //resolver = new InMemoryDnsResolver();
        }

        public void addHost(String host, InetAddress address) {
            resolver.add(host, address);
        }

        @Override
        protected ClientConnectionOperator createConnectionOperator(SchemeRegistry schemeRegistry) {
            resolver = new InMemoryDnsResolver();
            DefaultClientConnectionOperator op = new DefaultClientConnectionOperator(schemeRegistry, resolver);
            return op;
        }
    }

    public HttpHost(InetAddress address, int port) {
        this.address = address;
        this.port = port;
        connectionManager = new MyConnectionManager();
    }

    public HttpResponse get(String host, String url) {
        connectionManager.addHost(host, address);
        DefaultHttpClient client = new DefaultHttpClient(connectionManager);
        client.getParams().setBooleanParameter(ClientPNames.HANDLE_REDIRECTS, false);

        try {
            HttpGet get = new HttpGet("http://"+host+url);
            HttpResponse response = client.execute(get);
            return response;
        }catch (Exception ex) {
            ex.printStackTrace();
            System.out.println(ex.toString());
            return null;
        }
    }

}
