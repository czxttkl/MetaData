package io.metadata;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;

public class DIGRALib implements Website {

        public static final String ARTICLE_ABSTRACT_URL_PREFIX = "http://www.digra.org/digital-library/publications/";
        public static final Pattern KEYWORD_PATTERN = Pattern.compile("<a href=\"http://www.digra.org/digital-library/keywords/(.*?)/\">(.*?)</a>");
        public static final Pattern TITLE_PATTERN = Pattern.compile("<h1 class=\"entry-title\">(.*)</h1>");
        public static final Pattern ABSTRACT_PATTERN = Pattern.compile("<label class=\"diglib_item\">Abstract: </label>\\s+<br />\\s+<p>(.*)</p>");
        public static final Pattern YEAR_PATTERN = Pattern.compile("http://www.digra.org/digital-library/forums/digra((\\d){4})");
        public static final Pattern AUTHORS_PATTERN = Pattern.compile("<a href=\"http://www.digra.org/digital-library/authors/(.*?)/\">(.*?)</a>");
        
        private final String articleUrl;

        private String keywordsString = "";
        private String titleString = "";
        private String abstractString = "";
        private String yearString = "";
        private String authorsString = "";

        public DIGRALib(String doi) throws IOException{
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
            
            // Set Years
            Matcher yearMatcher = YEAR_PATTERN.matcher(aString);
            if (yearMatcher.find()) {
                yearString = yearMatcher.group(1);
            }
            
            // Set keywords
            Matcher keywordsMatcher = KEYWORD_PATTERN.matcher(aString);
            while (keywordsMatcher.find()) {
                keywordsString = keywordsString + "," + keywordsMatcher.group(2);
            }
            // Remove the first comma if keywords are found. 
            // In some articles there are no keywords.
            if (keywordsString.length() > 0) {
                keywordsString = keywordsString.substring(1);
            }

            // Set authors
            Matcher authorsMatcher = AUTHORS_PATTERN.matcher(aString);
            while (authorsMatcher.find()) {
                authorsString = authorsString + "," + authorsMatcher.group(2);
            }
            // Remove the first comma if keywords are found. 
            // In some articles there are no keywords.
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
        }

}
