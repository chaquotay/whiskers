package org.notatoaster.whiskers.http;

import org.apache.http.HttpResponse;
import org.hamcrest.Matcher;

import static org.hamcrest.CoreMatchers.allOf;

/**
 * Matchers for HTTP responses
 */
public class HTTP {

    public static Matcher<HttpResponse> redirectTo(String url) {
        return allOf(hasResponseCode(301), hasHeader("Location", url));
    }

    public static Matcher<HttpResponse> success() {
        return hasResponseCode(200);
    }

    public static Matcher<HttpResponse> notFound() {
        return hasResponseCode(404);
    }

    public static Matcher<HttpResponse> hasHeader(String header, String value) {
        return new HttpResponseHeaderMatcher(header, value);
    }

    public static Matcher<HttpResponse> hasResponseCode(int code) {
        return new HttpResponseCodeMatcher(code);
    }

}
