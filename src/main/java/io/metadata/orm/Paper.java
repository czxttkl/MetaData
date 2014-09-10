package io.metadata.orm;

import java.util.Arrays;
import java.util.List;

import io.metadata.misc.Utils;

import org.jongo.marshall.jackson.oid.Id;
import org.jongo.marshall.jackson.oid.ObjectId;

/**
 * Java Object for one document in MongoDB.metadata.papers collection.
 * @author Zhengxing Chen 
 */
public class Paper {
    @Id
    @ObjectId
    private String key;

    List<String> authors;
    List<String> keywords;

    String title;
    String abstraction;
    long year = 0;
    String venue;
    String venuetype = "conference";

    /*
     * public Paper(String titleString, String abstractString, String keywordsString, String authorsString, String yearString, String venueString) {
     * 
     * }
     */

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
    
    public Paper setKeywords(List<String> keywords) {
        this.keywords = keywords;
        return this;
    }

    public Paper setYear(String yearString) {
        year = Long.valueOf(yearString);
        return this;
    }

    public Paper setYear(long yearLong) {
        year = yearLong;
        return this;
    }

    public Paper setVenue(String venueString) {
        venue = venueString;
        return this;
    }

    public Paper setVenueType(String venueTypeString) {
        venuetype = venueTypeString;
        return this;
    }

    public String getAbstraction() {
        return abstraction;
    }

    public String getTitle() {
        return title;
    }

    public String getVenue() {
        return venue;
    }

    public String getId() {
        return key;
    }
    
    public List<String> getKeywords() {
        return keywords;
    }
    
    public List<String> getAuthors() {
        return authors;
    }
    
    /**
     * Validate if this paper instance has enough information: 1. title 2. year 3. venue 4. authors 5. abstract or keywords
     * 
     * @return true if the instance has enough information
     */
    public boolean validate() {
        if (Utils.nullOrEmpty(title) || year == 0 || Utils.nullOrEmpty(venue)
                || (authors == null ? true : (authors.get(0).trim().equals("") && authors.size() == 1))
                || (Utils.nullOrEmpty(abstraction) && Utils.nullOrEmpty(keywords))) {
            return false;
        } else {
            return true;
        }
    }
    
    

}
