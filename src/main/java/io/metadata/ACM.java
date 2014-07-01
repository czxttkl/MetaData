package io.metadata;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Zhengxing Chen
 *
 */
public class ACM extends Website {

    public static final String ARTICLE_ABSTRACT_URL_PREFIX = "http://dl.acm.org/citation.cfm?doid=%s&preflayout=flat";
    public static final Pattern TITLE_PATTERN = Pattern.compile("<meta name=\"citation_title\" content=\"(.*?)\">");
    public static final Pattern YEAR_PATTERN = Pattern.compile("<meta name=\"citation_date\" content=\"(\\d){2}/(\\d){2}/((\\d){4})\">");
    public static final Pattern KEYWORD_PATTERN = Pattern.compile("<meta name=\"citation_keywords\" content=\"(.*?)\">");
    public static final Pattern ABSTRACT_PATTERN = Pattern.compile("ABSTRACT</A></h1>\\s+(.*?)\\s+(.*?)<div style=\"display:inline\">" +
            "(<p>)*(.*?)(</p>)*</div>");
    public static final Pattern AUTHORS_PATTERN = Pattern.compile("<meta name=\"citation_authors\" content=\"(.*?)\">");

    public ACM(String doi) throws IOException {
        super(doi);
    }

    @Override
    void setKeywords() {
        Matcher keywordsMatcher = KEYWORD_PATTERN.matcher(htmlString);
        if (keywordsMatcher.find()) {
            String wholeKeywordsStrings = keywordsMatcher.group(1);
            String[] keywordsStrings = wholeKeywordsStrings.split(";");
            for (String keywordString : keywordsStrings) {
                keywordsString = keywordsString + "," + keywordString.trim();
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
            abstractString = abstractMatcher.group(4);
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
            yearString = yearMatcher.group(3);
        }
    }

    @Override
    void setAuthors() {
        Matcher authorsMatcher = AUTHORS_PATTERN.matcher(htmlString);
        if (authorsMatcher.find()) {
            String wholeAuthorsString = authorsMatcher.group(1);
            String[] authorsStringArr = wholeAuthorsString.split(";");
            for (String authorString : authorsStringArr) {
                String[] names = authorString.split(",");
                String lastName = names[0].trim();
                String firstName = names[1].trim();
                authorsString = authorsString + "," + firstName + " " + lastName;
            }
        }
        // Remove the first comma if keywords are found.
        // In some articles there are no keywords.
        if (authorsString.length() > 0) {
            authorsString = authorsString.substring(1);
        }
    }

    @Override
    String constructUrlFromDoi(String doi) {
        return String.format(ARTICLE_ABSTRACT_URL_PREFIX, doi);
    }

}
