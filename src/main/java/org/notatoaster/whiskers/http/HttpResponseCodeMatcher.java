package org.notatoaster.whiskers.http;

import org.apache.http.HttpResponse;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

class HttpResponseCodeMatcher extends TypeSafeMatcher<HttpResponse> {

    private int code;

    public HttpResponseCodeMatcher(int code) {
        this.code = code;
    }

    @Override
    protected boolean matchesSafely(HttpResponse response) {
        return response.getStatusLine().getStatusCode()==code;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("has result code " + code);
    }
}
