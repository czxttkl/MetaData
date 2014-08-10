package io.metadata.orm;

import java.util.Arrays;
import java.util.List;

import org.jongo.marshall.jackson.oid.Id;
import org.jongo.marshall.jackson.oid.ObjectId;

/**
 * @author Zhengxing Chen
 * Java Object for one document in MongoDB.metadata.papers collection
 */
public class Paper {
    @Id @ObjectId
    private String key;

    List<String> authors;
    List<String> keywords;

    String title;
    String abstraction;
    long year;
    String venue;

    /*public Paper(String titleString, String abstractString, String keywordsString, String authorsString, String yearString,
            String venueString) {

    }*/
    
    public Paper setTitle(String titleString) {
        title = titleString;
        return this;
    }
    
    public Paper setAbstraction(String abstractString) {
        abstraction = abstractString;
        return this;
    }
    
    public Paper setAuthors(String authorsString) {
        String[] author = authorsString.split(",");
        authors = Arrays.asList(author);
        return this;
    }
    
    public Paper setKeywords(String keywordsString) {
        String[] keyword = keywordsString.split(",");
        keywords = Arrays.asList(keyword);
        return this;
    }
    
    public Paper setYear(String yearString) {
        year = Long.valueOf(yearString);
        return this;
    }
    
    public Paper setVenue(String venueString) {
        venue = venueString;
        return this;
    }
}
