package io.metadata;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Zhengxing Chen
 * 
 */
public class Springer extends Website {

    public static final String ARTICLE_ABSTRACT_URL_PREFIX = "http://link.springer.com/chapter/";
    public static final Pattern TITLE_PATTERN = Pattern.compile("<meta name=\"citation_title\" content=\"(.*?)\"/>");
    public static final Pattern YEAR_PATTERN = Pattern.compile("<meta name=\"citation_publication_date\" content=\"(\\d{4})/\\d{2}/"
            + "\\d{2}\"/>");
    public static final Pattern KEYWORD_PATTERN = Pattern.compile("<ul class=\"abstract-keywords\">\\s+(<li>(.*?)</li>\\s+)+</ul>");
    public static final Pattern ABSTRACT_PATTERN = Pattern.compile("<div class=\"abstract-content formatted\" itemprop=\"description"
            + "\">\\s+<p class=\"a-plus-plus\">(.*?)</p>");
    public static final Pattern AUTHORS_PATTERN = Pattern.compile("<meta name=\"citation_author\" content=\"(.*?)\"/>");

    public Springer(String doi) throws IOException {
        super(doi);
    }

    @Override
    void setKeywords() {
        Matcher keywordsMatcher = KEYWORD_PATTERN.matcher(htmlString);
        if (keywordsMatcher.find()) {
            String keywordsBlockString = keywordsMatcher.group(0);
            Matcher subKeywordsMatcher = Pattern.compile("<li>(.*?)</li>").matcher(keywordsBlockString);
            while (subKeywordsMatcher.find()) {
                keywordsString = keywordsString + "," + subKeywordsMatcher.group(1);
            }
        }
        // Remove the first comma if keywords are found.
        // In some articles there are no keywords.
        if (keywordsString.length() > 0) {
            keywordsString = keywordsString.substring(1);
        } else {
            System.err.println("No Keyword found");
        }
    }

    @Override
    void setAbstract() {
        Matcher abstractMatcher = ABSTRACT_PATTERN.matcher(htmlString);
        if (abstractMatcher.find()) {
            abstractString = abstractMatcher.group(1);
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
            authorsString = authorsString + "," + authorsMatcher.group(1);
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
