package io.metadata;

import java.io.IOException;
import java.net.URL;

import org.jsoup.Jsoup;

/**
 * Created by Zhengxing Chen.
 */
public abstract class Website {

    protected String articleUrl;
    protected String htmlString;
    protected String keywordsString = "";
    protected String titleString = "";
    protected String abstractString = "";
    protected String yearString = "";
    protected String authorsString = "";

    public Website(String doi) throws IOException {
        constructUrl(doi);
        process();
    }
    
    void constructUrl(String doi) {
        articleUrl = constructUrlFromDoi(doi);
    }

    abstract String constructUrlFromDoi(String doi);
    
    public String getKeywords() throws IOException {
        return keywordsString;
    }

    public String getAbstract() {
        return abstractString;
    }

    public String getTitle() {
        return titleString;
    }

    public String getYear() {
        return yearString;
    }
    
    public String getAuthors() {
        return authorsString;
    }
    
    void process() throws IOException {
        htmlString = Downloader.toString(new URL(articleUrl));
        setKeywords();
        setAbstract();
        setTitle();
        setYears();
        setAuthors();
    }
    
    abstract void setKeywords();
    
    abstract void setAbstract();
    
    abstract void setTitle();
    
    abstract void setYears();
    
    abstract void setAuthors();
}
