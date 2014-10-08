package io.metadata;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * Parser for GameStudies.
 * @author Zhengxing Chen
 *
 */
public class GameStudiesLib extends Website {

    public static final String ARTICLE_URL_PREFIX = "http://www.gamestudies.org/%s";
    public static final Pattern TITLE_PATTERN = Pattern.compile("<h(1|2)>([\\s\\S]*?)</h(1|2)>");
    //public static final Pattern YEAR_PATTERN = Pattern.compile(">volume (\\d)+, issue (\\d)+<br([\\s\\S]*?)>([\\s\\S]*?)((\\d){4})</a>");
    public static final Pattern YEAR_PATTERN = Pattern.compile("<span class=\"date\">(.*?)((\\d){4})</span>");
    
    // To check if there is abstract or introduction paragraph.
    // If not, pick the first paragraph as abstract
    public static final Pattern ABSTRACT_PATTERN = Pattern
            .compile("(?s)<h3>([\\s\\S]*?)Abstract([\\s\\S]*?)</h3>(.*?)((</p>)|(<p/>)|(<br>))");
    public static final Pattern INTRODUCTION_PATTERN = Pattern
            .compile("<h3>([\\s\\S]*?)Introduction([\\s\\S]*?)</h3>([\\s\\S]*?)<p>([\\s\\S]*?)</p>");
    public static final Pattern FIRST_PARA_PATTERN = Pattern.compile("(?s)<div id=\"article\">(.*?)<p>(.*?)<(.*?)p(.*?)>");

    public static final Pattern AUTHORS_PATTERN = Pattern.compile("<em>by</em>([\\s\\S]*?)</");
    public static final Pattern KEYWORDS_PATTERN = Pattern.compile("(?s)<b>Keywords(:)?</b>(:)?(.*?)<");

    public GameStudiesLib(String doi) throws IOException {
        super(doi);
    }

    @Override
    void setKeywords() {
        Matcher keywordMatcher = KEYWORDS_PATTERN.matcher(htmlString);
        if (keywordMatcher.find()) {
            String keywordsStringWhole = keywordMatcher.group(3);
            String[] keywords = keywordsStringWhole.split(",");
            for (String keyword : keywords) {
                keywordsString = keywordsString + "," + keyword.trim();
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
        // System.out.println(htmlString);
        // Finding the abstract paragraph
        Matcher abstractMatcher = ABSTRACT_PATTERN.matcher(htmlString);
        if (abstractMatcher.find()) {
            abstractString = abstractMatcher.group(3);
        } else {
            // Finding the introduction paragraph
            Matcher introMatcher = INTRODUCTION_PATTERN.matcher(htmlString);
            if (introMatcher.find()) {
                abstractString = introMatcher.group(4);
            } else {
                Matcher firstParaMatcher = FIRST_PARA_PATTERN.matcher(htmlString);
                if (firstParaMatcher.find()) {
                    abstractString = firstParaMatcher.group(2);
                }
            }
        }
        // System.out.println(abstractString);
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
        // System.out.println(htmlString);
        Matcher yearMatcher = YEAR_PATTERN.matcher(htmlString);
        if (yearMatcher.find()) {
            yearString = yearMatcher.group(2);
        }
    }

    @Override
    void setAuthors() {
        Matcher authorsMatcher = AUTHORS_PATTERN.matcher(htmlString);
        if (authorsMatcher.find()) {
            String wholeAuthorsString = authorsMatcher.group(1);

            String[] authorsStringArr = wholeAuthorsString.split(", ");
            if (authorsStringArr.length == 1) {
                authorsString = authorsStringArr[0].trim();
                return;
            }
            for (int i = 0; i < authorsStringArr.length - 1; i++) {
                authorsString = authorsString + "," + authorsStringArr[i].trim();
            }

            String[] lastTwoAuthors = authorsStringArr[authorsStringArr.length - 1].split(" and ");
            if (lastTwoAuthors.length == 2) {
                authorsString = authorsString + "," + lastTwoAuthors[0].trim();
                authorsString = authorsString + "," + lastTwoAuthors[1].trim();
            }

            // Remove the first comma if authors are found.
            if (authorsString.length() > 0) {
                authorsString = authorsString.substring(1);
            }
        }
    }

    @Override
    String constructUrlFromDoi(String doi) {
        return String.format(ARTICLE_URL_PREFIX, doi);
    }

}
