package io.metadata;

import java.io.IOException;
import java.net.URL;
import io.metadata.misc.Downloader;

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

    final void constructUrl(String doi) {
        articleUrl = constructUrlFromDoi(doi);
    }

    abstract String constructUrlFromDoi(String doi);

    public final String getKeywords() {
        return keywordsString;
    }

    public final String getAbstract() {
        return abstractString;
    }

    public final String getTitle() {
        return titleString;
    }

    public final String getYear() {
        return yearString;
    }

    public final String getAuthors() {
        return authorsString;
    }

    final void process() throws IOException {
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
