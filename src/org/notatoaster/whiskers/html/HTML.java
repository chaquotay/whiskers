package org.notatoaster.whiskers.html;

import org.apache.http.HttpResponse;
import org.hamcrest.Matcher;

public class HTML {

    public static Matcher<HttpResponse> hasTitle(String title) {
        return new HTMLTagContentMatcher("title", title);
    }

}
