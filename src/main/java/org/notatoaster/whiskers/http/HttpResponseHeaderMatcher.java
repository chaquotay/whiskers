package org.notatoaster.whiskers.http;

import org.apache.http.HttpResponse;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

class HttpResponseHeaderMatcher extends TypeSafeMatcher<HttpResponse> {

    private final String header;
    private final String value;

    public HttpResponseHeaderMatcher(String header, String value) {

        this.header = header;
        this.value = value;
    }

    @Override
    protected boolean matchesSafely(HttpResponse response) {
        String headerValue = response.getFirstHeader(header).getValue();
        return value.equals(headerValue);
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("header " + header + " is " + value);
    }
}
