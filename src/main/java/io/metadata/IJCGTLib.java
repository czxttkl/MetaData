package io.metadata;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Zhengxing Chen
 */
public class IJCGTLib extends Website {

    public static final String ARTICLE_ABSTRACT_URL_PREFIX = "http://www.hindawi.com/journals/ijcgt";
    public static final Pattern TITLE_PATTERN = Pattern.compile("<meta name=\"citation_title\" content=\"([\\s\\S]*?)\"([\\s\\S]*?)/>");
    public static final Pattern YEAR_PATTERN = Pattern.compile("<meta name=\"citation_year\" content=\"((\\d){4})\"([\\s\\S]*?)>");
    public static final Pattern ABSTRACT_PATTERN = Pattern.compile("<meta name=\"citation_abstract\" content=\"([\\s\\S]*?)\"([\\s\\S]*?)/>");
    public static final Pattern AUTHORS_PATTERN = Pattern.compile("<meta name=\"citation_authors\" content=\"([\\s\\S]*?)\"([\\s\\S]*?)/>");

    public IJCGTLib(String doi) throws IOException {
        super(doi);
    }

    @Override
    void setKeywords() {
        // IJCGT journals don't have keywords.
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
        if (authorsMatcher.find()) {
            String authors = authorsMatcher.group(1);
            String[] authorsArr = authors.split(";");
            for (String author : authorsArr) {
                String[] names = author.split(",");
                String authorName = "";
                for (int i = names.length - 1; i >= 0; i--) {
                    authorName = authorName + names[i].trim() + " ";
                }
                authorsString = authorsString + "," + authorName.trim();
            }
        }
        // Remove the first comma if authors are found.
        if (authorsString.length() > 0) {
            authorsString = authorsString.substring(1);
        }
    }

    @Override
    String constructUrlFromDoi(String doi) {
        return ARTICLE_ABSTRACT_URL_PREFIX + doi;
    }

}
