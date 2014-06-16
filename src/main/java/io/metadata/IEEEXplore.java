package io.metadata;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;

/**
 * Created by Zhengxing Chen.
 */
public class IEEEXplore implements Website {

    public static final String ARTICLE_ABSTRACT_URL_PREFIX = "http://ieeexplore.ieee.org/xpl/articleDetails.jsp?arnumber=";
    public static final Pattern AUTHOR_KEYWORDS_PATTERN =
            Pattern.compile("<h2>AUTHOR KEYWORDS</h2>\\s+<ul>\\s+((<li>.+</li>\\s+)+)</ul>");
    public static final Pattern CONTROLLED_INDEXING_PATTERN =
            Pattern.compile("<h2>INSPEC: CONTROLLED INDEXING</h2>\\s+<ul>\\s+((<li>.+</li>\\s+)+)</ul>");
    public static final Pattern KEYWORD_PATTERN = Pattern.compile("a data-keyword=\"(.*)\" role");
    public static final Pattern TITLE_PATTERN = Pattern.compile("<div class=\"title\">\\s+<h1>\\s+(.*)\\s+</h1>");
    public static final Pattern ABSTRACT_PATTERN = Pattern.compile("<div class=\"article\">\\s+<p>(.*)</p>\\s+</div>");
    public static final Pattern AUTHORS_PATTERN = Pattern.compile("<meta name=\"citation_author\" content=\"(.*?)\" />");
    public static final Pattern YEAR_PATTERN = Pattern.compile("<meta name=\"citation_doi\" content=\"(.*?)CGames.((\\d){4})");

    private final String articleUrl;

    private String keywordsString = "";
    private String titleString = "";
    private String abstractString = "";
    private String yearString = "";
    private String authorsString = "";

    public IEEEXplore(String doi) throws IOException{
        articleUrl = ARTICLE_ABSTRACT_URL_PREFIX + doi;
        process();
    }

    @Override
    public String getKeywords() throws IOException {
        return keywordsString;
    }

    @Override
    public String getAbstract() {
        return abstractString;
    }

    @Override
    public String getTitle() {
        return titleString;
    }

    @Override
    public String getYear() {
        return yearString;
    }
    
    @Override
    public String getAuthors() {
        return authorsString;
    }

    @Override
    public void process() throws IOException {
        String aString = Jsoup.connect(articleUrl).get().html();

        // Set keywords
        Matcher authorKeywordsMatcher = AUTHOR_KEYWORDS_PATTERN.matcher(aString);
        String keywordsList = "";
        if (authorKeywordsMatcher.find()) {
            keywordsList = authorKeywordsMatcher.group(1);
        } else {
            // Some papers don't have author strings. Instead, we crawl controlled indexing keywords.
            System.err.println("No author keywords found. Use Controlled Indexing instead.");
            Matcher controlIndexingMatcher = CONTROLLED_INDEXING_PATTERN.matcher(aString);
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

        // Set authors
        Matcher authorsMatcher = AUTHORS_PATTERN.matcher(aString);
        while (authorsMatcher.find()) {
            authorsString = authorsString + "," + authorsMatcher.group(1).replace(",","");
        }
        // Remove the first comma if authors are found. 
        if (authorsString.length() > 0) {
            authorsString = authorsString.substring(1);
        }
        
        // Set title
        Matcher titleMatcher = TITLE_PATTERN.matcher(aString);
        if (titleMatcher.find()) {
            titleString = titleMatcher.group(1);
        }
        
        // Set abstract
        Matcher abstractMatcher = ABSTRACT_PATTERN.matcher(aString);
        if (abstractMatcher.find()) {
            abstractString = abstractMatcher.group(1);
        }
        
        // Set year
        Matcher yearMatcher = YEAR_PATTERN.matcher(aString);
        if (yearMatcher.find()) {
            yearString = yearMatcher.group(2);
        }
    }

}
