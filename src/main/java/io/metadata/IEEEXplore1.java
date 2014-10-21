package io.metadata;

import io.metadata.misc.Downloader;

import java.io.IOException;
import java.net.URL;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Crawl papers on IEEE. 
 * Created by Zhengxing Chen.
 */
public class IEEEXplore1 extends Website {

    public static final String ARTICLE_ABSTRACT_URL_PREFIX = "http://ieeexplore.ieee.org/xpls/icp.jsp?arnumber=";
    public static final Pattern KEYWORD_PATTERN = Pattern.compile("searchWithin=Search_Index_Terms([\\s\\S]*?)>([\\s\\S]*?)</a>");
    public static final Pattern TITLE_PATTERN = Pattern.compile("<div class=\"text\">([\\s\\S]*?)<h1>([\\s\\S]*?)</h1>");
    public static final Pattern AUTHORS_PATTERN = Pattern.compile("searchWithin=p_Authors([\\s\\S]*?)>([\\s\\S]*?)</a>");
    public static final Pattern YEAR_PATTERN = Pattern.compile("<dd><span class=\"dt_date\" id=\"dt_conf_date\"([\\s\\S]*?)((\\d){4})</a></span></dd>");

    public IEEEXplore1(String doi) throws IOException {
        super(doi);
    }

    @Override
    void process() throws IOException {
        htmlString = Downloader.toStringAdv2(new URL(articleUrl));
        setKeywords();
        setAbstract();
        setTitle();
        setYears();
        setAuthors();
    }

    @Override
    String constructUrlFromDoi(String doi) {
        return ARTICLE_ABSTRACT_URL_PREFIX + doi;
    }

    @Override
    void setKeywords() {
        HashSet<String> keywordsSet = new HashSet<String>();
        // Set keywords
        Matcher keywordsMatcher = KEYWORD_PATTERN.matcher(htmlString);
        while (keywordsMatcher.find()) {
            keywordsSet.add(keywordsMatcher.group(2));
        } 
        for (String keyword : keywordsSet) {
            keywordsString = keywordsString + "," + keyword;
        }
        // Remove the first comma if keywords are found.
        if (keywordsString.length() > 0) {
            keywordsString = keywordsString.substring(1);
        }
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
//        System.out.println(htmlString);
        String firstAuthor = "";
        while (authorsMatcher.find()) {
            String author = authorsMatcher.group(2).trim();
            if (firstAuthor.equals("")) {
                firstAuthor = author;
                authorsString = authorsString + "," + author;
                continue;
            }
            if (firstAuthor.equals(author)) {
                break;
            }
            authorsString = authorsString + "," + author; 
        }
        // Remove the first comma if authors are found.
        if (authorsString.length() > 0) {
            authorsString = authorsString.substring(1);
        }
    }

    @Override
    void setAbstract() {
       // Don't need to implement setAbstract() for IEEE.
    }

}
