package io.metadata.download.journal;

import io.metadata.MetaDataFactory;
import io.metadata.Website;
import io.metadata.misc.Globals;
import io.metadata.misc.Logger;
import io.metadata.orm.MyMongoCollection;
import io.metadata.orm.Paper;

import java.io.IOException;

public class GameStudies {
    public static String[] dois = {};
    
    public static final String VENUE = "GameStudies";

    public static void main(String[] args) throws IOException, InterruptedException {
        // Initialize logger
        Logger mLogger = new Logger("logGameStudies", true);

        // Initialize self-wrapped mongocollection
        MyMongoCollection<Paper> mPapersCollection = new MyMongoCollection<Paper>(Globals.MONGODB_PAPERS_COLLECTION);

        MetaDataFactory mMetaDataFactory = new MetaDataFactory();

        for (String doi : dois) {
            try {
                Website mWebsite = mMetaDataFactory.getWebsite("io.metadata.GameStudies", doi);

                mLogger.appendLines(doi, mWebsite.getTitle(), mWebsite.getAbstract(), mWebsite.getKeywords(), mWebsite.getAuthors(),
                        mWebsite.getYear(), "");

                Paper mPaper = new Paper().setTitle(mWebsite.getTitle()).setAbstraction(mWebsite.getAbstract())
                        .setKeywords(mWebsite.getKeywords()).setAuthors(mWebsite.getAuthors()).setYear(mWebsite.getYear()).setVenue(VENUE)
                        .setVenueType(Globals.VENUE_TYPE_CONFERENCE);

                if (mPaper.validate()) {
                    mPapersCollection.insert(mPaper);
                } else {
                    mLogger.appendErrMsg(mWebsite.getArticleURL());
                }

            } catch (Exception e) {
                // Catch any exception
                mLogger.appendErrMsg(e.getMessage());
            }

            // Anti-robotics
            Thread.sleep((long) (Math.random() * 60000));
            System.out.println();

        } // for

    }

}
