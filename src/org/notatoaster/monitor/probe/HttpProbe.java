package org.notatoaster.monitor.probe;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.conn.ClientConnectionOperator;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.BasicClientConnectionManager;
import org.apache.http.impl.conn.DefaultClientConnectionOperator;
import org.apache.http.impl.conn.InMemoryDnsResolver;

import java.net.InetAddress;

public class HttpProbe {

    private final MyConnectionManager connectionManager;

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

    public HttpProbe() {
        connectionManager = new MyConnectionManager();
    }

    public void addHost(String host, InetAddress address) {
        connectionManager.addHost(host, address);
    }

    public boolean isFound(String url, String title)  {
        DefaultHttpClient client = new DefaultHttpClient(connectionManager);

        try {
            HttpGet get = new HttpGet(url);
            HttpResponse response = client.execute(get);

            int statusCode = response.getStatusLine().getStatusCode();

            BasicResponseHandler handler = new BasicResponseHandler();
            String responseBody = handler.handleResponse(response);

            return statusCode==200 && responseBody.contains("<title>"+title+"</title>"); // evil check
        }catch (Exception ex) {
            System.out.println(ex.toString());
            return false;
        }
    }

    public boolean isRedirect(String url, String redirectedUrl) {
        DefaultHttpClient client = new DefaultHttpClient(connectionManager);
        client.getParams().setBooleanParameter(ClientPNames.HANDLE_REDIRECTS, false);

        try {
            HttpGet get = new HttpGet(url);
            HttpResponse response = client.execute(get);

            int statusCode = response.getStatusLine().getStatusCode();
            String location = response.getFirstHeader("Location").getValue();
            return statusCode==301 && location.equals(redirectedUrl);
        }catch (Exception ex) {
            System.out.println(ex.toString());
            return false;
        }
    }

}
