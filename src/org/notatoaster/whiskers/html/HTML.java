package org.notatoaster.whiskers.html;

import org.apache.http.HttpResponse;
import org.hamcrest.Matcher;

/**
 * Matchers for HTML documents inside HTTP responses.
 */
public class HTML {

    public static Matcher<HttpResponse> hasTitle(String title) {
        return new HTMLTagContentMatcher("title", title);
    }

}
