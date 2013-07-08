package org.notatoaster.whiskers.probe;

import net.htmlparser.jericho.Element;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.conn.ClientConnectionOperator;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.BasicClientConnectionManager;
import org.apache.http.impl.conn.DefaultClientConnectionOperator;
import org.apache.http.impl.conn.InMemoryDnsResolver;
import org.notatoaster.whiskers.ProbeResult;

import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;

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

    public ProbeResult isFound(String url, String title)  {
        DefaultHttpClient client = new DefaultHttpClient(connectionManager);

        try {
            HttpGet get = new HttpGet(url);
            HttpResponse response = client.execute(get);

            int statusCode = response.getStatusLine().getStatusCode();

            if(statusCode!=200)
                return ProbeResult.error("unexpected status code: " + statusCode);

            BasicResponseHandler handler = new BasicResponseHandler();
            String responseBody = handler.handleResponse(response);

            String actualTitle = getTitle(responseBody);
            if(title.equals(actualTitle)) {
                return ProbeResult.success();
            } else {
                return ProbeResult.error("unexpected title. expected: " + title + ", found: " + actualTitle);
            }
        }catch (Exception ex) {
            ex.printStackTrace();
            System.out.println(ex.toString());
            return ProbeResult.error("check failed: " +  ex.toString());
        }
    }

    private String getTitle(String html) {
        net.htmlparser.jericho.Source source = new net.htmlparser.jericho.Source(html);
        for(Element element: source.getAllElements()) {
            if("title".equals(element.getName())) {
                return element.getContent().toString();
            }
        }
        return "";
    }

    public ProbeResult isRedirect(String url, String redirectedUrl) {
        DefaultHttpClient client = new DefaultHttpClient(connectionManager);
        client.getParams().setBooleanParameter(ClientPNames.HANDLE_REDIRECTS, false);

        try {
            HttpGet get = new HttpGet(url);
            HttpResponse response = client.execute(get);

            int statusCode = response.getStatusLine().getStatusCode();
            if(statusCode!=301) {
                return ProbeResult.error("expected redirect status code 301, but found " + statusCode);
            }

            String location = response.getFirstHeader("Location").getValue();
            if(location.equals(redirectedUrl)) {
                return ProbeResult.success();
            } else {
                return ProbeResult.success("expected redirect to " + redirectedUrl + ", but found redirect to " + location);
            }
        }catch (Exception ex) {
            return ProbeResult.error("checking redirect failed: " + ex.toString());
        }
    }

}
