package org.notatoaster.whiskers.html;

import net.htmlparser.jericho.Element;
import org.apache.http.HttpResponse;
import org.apache.http.impl.client.BasicResponseHandler;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import java.io.IOException;

public class HTMLTagContentMatcher extends TypeSafeMatcher<HttpResponse>
{
    private final String tag;
    private final String content;

    public HTMLTagContentMatcher(String tag, String content) {

        this.tag = tag;
        this.content = content;
    }

    @Override
    protected boolean matchesSafely(HttpResponse response) {
        BasicResponseHandler handler = new BasicResponseHandler();
        try {
            String responseBody = handler.handleResponse(response);
            return content.equals(firstTagContent(responseBody));
        } catch (IOException e) {
            return false;
        }
    }

    private String firstTagContent(String html) {
        net.htmlparser.jericho.Source source = new net.htmlparser.jericho.Source(html);
        for(Element element: source.getAllElements()) {
            if("title".equals(element.getName())) {
                return element.getContent().toString();
            }
        }
        return "";
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("content of tag " + tag + " is " + content);
    }
}
