package io.metadata;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Url patterns are different before and after 2010 from AAAI.
 * 
 * @author Zhengxing Chen
 */
public class AAAILibBefore2010 extends Website {

    public static final String ARTICLE_ABSTRACT_URL_PREFIX = "http://www.aaai.org/Library/%s.php";
    public static final Pattern TITLE_PATTERN = Pattern
            .compile("Papers/([\\s\\S]*?).pdf\">([\\s\\S]*?)</a>");
    public static final Pattern YEAR_PATTERN = Pattern
            .compile("Papers/([\\s\\S]*?)(\\d{4})([\\s\\S]*?).pdf\">");
    // public static final Pattern KEYWORD_PATTERN = Pattern.compile("<p><i>Subjects: </i>(.*?)</p>");
    public static final Pattern ABSTRACT_PATTERN = Pattern
            .compile("Papers/([\\s\\S]*?).pdf\">([\\s\\S]*?)</p>([\\s\\S]*?)<p>([\\s\\S]*?)</p>");
    public static final Pattern AUTHORS_PATTERN = Pattern
            .compile("<h1><a href=\"([\\s\\S]*?)/Papers/([\\s\\S]*?).pdf\">([\\s\\S]*?)<p([\\s\\S]*?)<i>(.*?)</i></p>");

    public AAAILibBefore2010(String doi) throws IOException {
        super(doi);
    }

    @Override
    void setKeywords() {
        // AAAI papers don't have keywords. Use subjects instead.
    }

    @Override
    void setAbstract() {
        Matcher abstractMatcher = ABSTRACT_PATTERN.matcher(htmlString);
        if (abstractMatcher.find()) {
            abstractString = abstractMatcher.group(4);
        }
        abstractString.trim();
    }

    @Override
    void setTitle() {
        Matcher titleMatcher = TITLE_PATTERN.matcher(htmlString);
        if (titleMatcher.find()) {
            titleString = titleMatcher.group(2);
        }
    }

    @Override
    void setYears() {
        Matcher yearMatcher = YEAR_PATTERN.matcher(htmlString);
        if (yearMatcher.find()) {
            yearString = yearMatcher.group(2);
        }
    }

    @Override
    void setAuthors() {
        Matcher authorsMatcher = AUTHORS_PATTERN.matcher(htmlString);
//      System.out.println(htmlString);
        if (authorsMatcher.find()) {
            String wholeAuthorsString = authorsMatcher.group(5);
            authorsString = wholeAuthorsString.replaceFirst(",* and ", ",").replaceAll(", ", ",");
        }
    }

    @Override
    String constructUrlFromDoi(String doi) {
        return String.format(ARTICLE_ABSTRACT_URL_PREFIX, doi);
    }

}
