package io.metadata.orm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import io.metadata.misc.Utils;

import org.jongo.marshall.jackson.oid.Id;
import org.jongo.marshall.jackson.oid.ObjectId;

import com.mongodb.util.Hash;

/**
 * Java Object for one document in MongoDB.metadata.papers collection.
 * 
 * @author Zhengxing Chen
 */
public class Paper {
    @Id
    @ObjectId
    private String key;

    List<String> authors;
    Set<String> keywords;

    // CHECKSTYLE:OFF
    List<List<String>> s_references;
    List<String> authors_cited;
    // CHECKSTYLE:ON
    
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
        keywords = new HashSet<String>(Arrays.asList(keyword));
        return this;
    }

    public Paper setKeywords(HashSet<String> keywords) {
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

    public Set<String> getKeywords() {
        return keywords;
    }

    public List<String> getAuthors() {
        return authors;
    }
    
    public List<String> getAuthorsCited() {
        return authors_cited;
    }
    
    public long getYear() {
        return year;
    }
    
    /** Return a list of papers which cited this paper. */
    public List<List<String>> getReferences() {
        return s_references;
    }
    
    public void addKeyword(String keyword) {
        if (keywords == null) {
            keywords = new HashSet<String>();
        }
        keywords.add(keyword);
    }
    
    public void addAuthorsCited(Collection<String> authors) {
        if (authors_cited == null) {
            authors_cited = new ArrayList<String>();
        } 
        authors_cited.addAll(authors);
    }
    
    /**
     * Validate if this paper instance has enough information: 1. title 2. year 3. venue 4. authors 5. abstract or keywords. Also look at validate1().
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

    /**
     * Validate if this paper instance has enough information: 1. title 2. year 3. venue 4. authors. Also look at validate().
     * 
     * @return true if the instance has enough information
     */
    public boolean validate1() {
        if (Utils.nullOrEmpty(title) || year == 0 || Utils.nullOrEmpty(venue)
                || (authors == null ? true : (authors.get(0).trim().equals("") && authors.size() == 1))) {
            return false;
        } else {
            return true;
        }
    }

}
