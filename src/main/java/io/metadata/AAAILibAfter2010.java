package io.metadata;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Url patterns are different before and after 2010 from AAAI.
 * 
 * @author Zhengxing Chen
 * 
 */
public class AAAILibAfter2010 extends Website {

    public static final String ARTICLE_ABSTRACT_URL_PREFIX = "http://www.aaai.org/ocs/index.php/";
    public static final Pattern TITLE_PATTERN = Pattern.compile("<meta name=\"citation_title\" content=\"(.*?)\"/>");
    public static final Pattern YEAR_PATTERN = Pattern
            .compile("<meta name=\"DC.Date.dateSubmitted\" scheme=\"ISO8601\" content=\"(\\d{4})-\\d{2}-\\d{2}\"/>");
    // public static final Pattern KEYWORD_PATTERN = Pattern.compile("<p><i>Subjects: </i>(.*?)</p>");
    public static final Pattern ABSTRACT_PATTERN = Pattern.compile("<meta name=\"DC.Description\" xml:lang=\"en\" content=\"(.*?)\"/>");
    public static final Pattern AUTHORS_PATTERN = Pattern.compile("<meta name=\"DC.Creator.PersonalName\" content=\"(.*?)\"/>");

    public AAAILibAfter2010(String doi) throws IOException {
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
