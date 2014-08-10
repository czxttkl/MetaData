package io.metadata;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Zhengxing Chen
 */
public class DIGRALib extends Website {

    public static final String ARTICLE_ABSTRACT_URL_PREFIX = "http://www.digra.org/digital-library/publications/";
    public static final Pattern TITLE_PATTERN = Pattern.compile("<h1 class=\"entry-title\">(.*)</h1>");
    public static final Pattern YEAR_PATTERN = Pattern.compile("http://www.digra.org/digital-library/forums/digra((\\d){4})");
    public static final Pattern KEYWORD_PATTERN = Pattern
            .compile("<a href=\"http://www.digra.org/digital-library/keywords/(.*?)>(.*?)</a>");
    public static final Pattern ABSTRACT_PATTERN = Pattern
            .compile("<label class=\"diglib_item\">Abstract: </label>([\\s\\S]*?)<p>([\\s\\S]*?)</p>");
    public static final Pattern AUTHORS_PATTERN = Pattern
            .compile("<a href=\"http://www.digra.org/digital-library/authors/(.*?)>(.*?)</a>");

    public DIGRALib(String doi) throws IOException {
        super(doi);
    }

    @Override
    void setKeywords() {
        Matcher keywordsMatcher = KEYWORD_PATTERN.matcher(htmlString);
        while (keywordsMatcher.find()) {
            keywordsString = keywordsString + "," + keywordsMatcher.group(2);
        }
        // Remove the first comma if keywords are found.
        // In some articles there are no keywords.
        if (keywordsString.length() > 0) {
            keywordsString = keywordsString.substring(1);
        }
    }

    @Override
    void setAbstract() {
        Matcher abstractMatcher = ABSTRACT_PATTERN.matcher(htmlString);
        if (abstractMatcher.find()) {
            abstractString = abstractMatcher.group(2);
        }
    }

    @Override
    void setTitle() {
        Matcher titleMatcher = TITLE_PATTERN.matcher(htmlString);
        if (titleMatcher.find()) {
            titleString = titleMatcher.group(1);
        }
    }

    @Override
    void setYears() {
        Matcher yearMatcher = YEAR_PATTERN.matcher(htmlString);
        if (yearMatcher.find()) {
            yearString = yearMatcher.group(1);
        }
    }

    @Override
    void setAuthors() {
        Matcher authorsMatcher = AUTHORS_PATTERN.matcher(htmlString);
        while (authorsMatcher.find()) {
            authorsString = authorsString + "," + authorsMatcher.group(2);
        }
        // Remove the first comma if keywords are found.
        // In some articles there are no keywords.
        if (authorsString.length() > 0) {
            authorsString = authorsString.substring(1);
        }
    }

    @Override
    String constructUrlFromDoi(String doi) {
        return ARTICLE_ABSTRACT_URL_PREFIX + doi;
    }

}
