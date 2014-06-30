package io.metadata;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Url patterns are different before and after 2010 from AAAI
 * @author Zhengxing Chen
 */
public class AAAILibBefore2010 extends Website {

    public static final String ARTICLE_ABSTRACT_URL_PREFIX = "http://www.aaai.org/Library/AAAI/%s.php";
    public static final Pattern TITLE_PATTERN = Pattern
            .compile("<h1><a href=\"../../../Papers/AAAI/(\\d{4})/(.*?).pdf\">(\\s)+(.*?)</a></h1>(\\s)+<p class=\"left\"><i>(.*?)</i></p>(\\s)+<p>(.*?)</p>");
    public static final Pattern YEAR_PATTERN = Pattern
            .compile("<h1><a href=\"../../../Papers/AAAI/(\\d{4})/(.*?).pdf\">(\\s)+(.*?)</a></h1>(\\s)+<p class=\"left\"><i>(.*?)</i></p>(\\s)+<p>(.*?)</p>");
//  public static final Pattern KEYWORD_PATTERN = Pattern.compile("<p><i>Subjects: </i>(.*?)</p>");
    public static final Pattern ABSTRACT_PATTERN = Pattern
            .compile("<h1><a href=\"../../../Papers/AAAI/(\\d{4})/(.*?).pdf\">(\\s)+(.*?)</a></h1>(\\s)+<p class=\"left\"><i>(.*?)</i></p>(\\s)+<p>(.*?)</p>");
    public static final Pattern AUTHORS_PATTERN = Pattern
            .compile("<h1><a href=\"../../../Papers/AAAI/(\\d{4})/(.*?).pdf\">(\\s)+(.*?)</a></h1>(\\s)+<p class=\"left\"><i>(.*?)</i></p>(\\s)+<p>(.*?)</p>");

    public AAAILibBefore2010(String doi) throws IOException {
        super(doi);
    }

    @Override
    void setKeywords() {
       // AAAI papers don't have keywords. Use subjects instead.
       /* Matcher keywordsMatcher = KEYWORD_PATTERN.matcher(htmlString);
        if (keywordsMatcher.find()) {
            String wholeKeywordsString = keywordsMatcher.group(1);
            String[] keywords = wholeKeywordsString.split(";");
            for (String keyword : keywords) {
                keywordsString = keywordsString + "," + keyword.substring(keyword.indexOf(" ") + 1);
            }
        }
        // Remove the first comma if keywords are found.
        // In some articles there are no keywords.
        if (keywordsString.length() > 0) {
            keywordsString = keywordsString.substring(1);
        }*/
    }

    @Override
    void setAbstract() {
        Matcher abstractMatcher = ABSTRACT_PATTERN.matcher(htmlString);
        if (abstractMatcher.find()) {
            abstractString = abstractMatcher.group(8);
        }
    }

    @Override
    void setTitle() {
//        System.out.println(htmlString);
        Matcher titleMatcher = TITLE_PATTERN.matcher(htmlString);
        if (titleMatcher.find()) {
            titleString = titleMatcher.group(4);
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
        if (authorsMatcher.find()) {
            String wholeAuthorsString = authorsMatcher.group(6);
            authorsString = wholeAuthorsString.replaceAll(", ", ",");
        }
    }

    @Override
    String constructUrlFromDoi(String doi) {
        return String.format(ARTICLE_ABSTRACT_URL_PREFIX, doi);
    }

}
