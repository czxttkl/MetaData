package io.metadata;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Zhengxing Chen.
 */
public class IEEEXplore extends Website {

    public static final String ARTICLE_ABSTRACT_URL_PREFIX = "http://ieeexplore.ieee.org/xpl/articleDetails.jsp?arnumber=";
    public static final Pattern AUTHOR_KEYWORDS_PATTERN = Pattern.compile("<h2>AUTHOR KEYWORDS</h2>\\s+<ul>\\s+((<li>.+</li>\\s+)+)</ul>");
    public static final Pattern KEYWORD_PATTERN = Pattern.compile("a data-keyword=\"(.*)\" role");
    public static final Pattern TITLE_PATTERN = Pattern.compile("<div class=\"title\">\\s+<h1>\\s+(.*)\\s+</h1>");
    public static final Pattern ABSTRACT_PATTERN = Pattern.compile("<div class=\"article\">\\s+<p>(.*)</p>\\s+</div>");
    public static final Pattern AUTHORS_PATTERN = Pattern.compile("<meta name=\"citation_author\" content=\"(.*?)\" />");
    public static final Pattern YEAR_PATTERN = Pattern.compile("<meta name=\"citation_doi\" content=\"(.*?)/\\w*?.((\\d){4})");
    public static final Pattern CONTROLLED_INDEXING_PATTERN = Pattern
            .compile("<h2>INSPEC: CONTROLLED INDEXING</h2>\\s+<ul>\\s+((<li>.+</li>\\s+)+)</ul>");

    public IEEEXplore(String doi) throws IOException {
        super(doi);
    }

    @Override
    String constructUrlFromDoi(String doi) {
        return ARTICLE_ABSTRACT_URL_PREFIX + doi;
    }

    @Override
    void setKeywords() {
        // Set keywords
        Matcher authorKeywordsMatcher = AUTHOR_KEYWORDS_PATTERN.matcher(htmlString);
        String keywordsList = "";
        if (authorKeywordsMatcher.find()) {
            keywordsList = authorKeywordsMatcher.group(1);
        } else {
            // Some papers don't have author strings. Instead, we crawl controlled indexing keywords.
            System.err.println("No author keywords found. Use Controlled Indexing instead.");
            Matcher controlIndexingMatcher = CONTROLLED_INDEXING_PATTERN.matcher(htmlString);
            if (controlIndexingMatcher.find()) {
                keywordsList = controlIndexingMatcher.group(1);
            }
        }
        Matcher keywordMatcher = KEYWORD_PATTERN.matcher(keywordsList);
        while (keywordMatcher.find()) {
            keywordsString = keywordsString + "," + keywordMatcher.group(1);
        }
        // Remove the first comma if keywords are found.
        if (keywordsString.length() > 0) {
            keywordsString = keywordsString.substring(1);
        }
    }

    @Override
    void setAbstract() {
        // Set abstract
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
            yearString = yearMatcher.group(2);
        }
    }

    @Override
    void setAuthors() {
        System.out.println(htmlString);
        Matcher authorsMatcher = AUTHORS_PATTERN.matcher(htmlString);
        while (authorsMatcher.find()) {
            authorsString = authorsString + "," + authorsMatcher.group(1).replace(",", "");
        }
        // Remove the first comma if authors are found.
        if (authorsString.length() > 0) {
            authorsString = authorsString.substring(1);
        }
    }

}
