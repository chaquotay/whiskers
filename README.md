whiskers
========

Collection of DSL-ish helper classes which allow for writing very basic server monitor programs in Java (e.g. JUnit tests):

 ```java
 @RunWith(JUnit4.class)
 public class ExampleComTest {
 
     private final Host server;
     private final InetAddress serverAddress;
     
     public ExampleComTest() throws UnknownHostException {
         serverAddress = InetAddress.getByAddress(new byte[]{127, 0, 0, 1});
         server = Host.create(serverAddress);
     }
     
     @Test
     public void testHttpRedirect() throws IOException {
         assertThat(
             server.http().get("www.example.com", "/index.html"),
             is(redirectTo("http://example.com/index.html")));
     }
 }
 ```

Currently supported aspects are (still limited, but growing):

* HTTP (status code, header values, HTML body)
* SMTP (reply code)
* DNS (A record address)
