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
    List<List<String>> cited_by_papers;
    List<String> cited_authors;
    List<String> cited_papers;
    Set<String> title_keywords;
    
    String title;
    String abstraction;
    long year = 0;
    String venue;
    String venue_type = "conference";
    Integer label = null;
    // CHECKSTYLE:ON
    
    
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
        venue_type = venueTypeString;
        return this;
    }

    public Integer getLabel() {
        return label;
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
    
    public List<String> getCitedAuthors() {
        return cited_authors;
    }
    
    public List<String> getCitedPapers() {
        return cited_papers;
    }
    
    public long getYear() {
        return year;
    }
    
    /** Return a list of papers which cited this paper. */
    public List<List<String>> getCitedByPapers() {
        return cited_by_papers;
    }
    
    public void addKeyword(String keyword) {
        if (keywords == null) {
            keywords = new HashSet<String>();
        }
        keywords.add(keyword);
    }
    
    public Paper addCitedPapers(String title) {
        if (cited_papers == null) {
            cited_papers = new ArrayList<String>();
        }
        cited_papers.add(title);
        return this;
    }
    
    public Paper addCitedAuthors(Collection<String> authors) {
        if (cited_authors == null) {
            cited_authors = new ArrayList<String>();
        } 
        cited_authors.addAll(authors);
        return this;
    }
    
    public Paper addTitleKeyword(String keyword) {
        if (title_keywords == null) {
            title_keywords = new HashSet<String>();
        }
        title_keywords.add(keyword);
        return this;
    }
    
    public Paper addTitleKeywords(Collection<String> keywords) {
        if (title_keywords == null) {
            title_keywords = new HashSet<String>();
        }
        title_keywords.addAll(keywords);
        return this;
    }
    
    public Set<String> getTitleKeywords() {
        return title_keywords;
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
