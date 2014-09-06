package io.metadata.download;

import java.io.IOException;

import io.metadata.MetaDataFactory;
import io.metadata.Website;
import io.metadata.misc.Globals;
import io.metadata.misc.Logger;
import io.metadata.orm.MyMongoCollection;
import io.metadata.orm.Paper;

/**
 * ISAGA from Springer.
 * 
 * @author Zhengxing Chen
 */
public class ISAGASpringer {

    // Online DOI
    public static String[] bookDOIs = { "978-3-319-04954-0", // 2013
    };

    public static int[] numberOfChapters = { 30, // 2013
    };

    public static final String VENUE = "ISAGA";

    public static void main(String[] args) throws IOException, InterruptedException {
        // Initialize logger
        Logger mLogger = new Logger("logISAGA", true);

        // Initialize self-wrapped mongocollection
        MyMongoCollection<Paper> mPapersCollection = new MyMongoCollection<Paper>(Globals.MONGODB_PAPERS_COLLECTION);

        MetaDataFactory mMetaDataFactory = new MetaDataFactory();

        // Each book
        String doi;
        for (int i = 0; i < bookDOIs.length; i++) {

            // each chapter in book
            for (int j = 1; j <= numberOfChapters[i]; j++) {
                doi = "10.1007/" + bookDOIs[i] + "_" + j;

                try {
                    Website mWebsite = mMetaDataFactory.getWebsite("io.metadata.Springer", doi);

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
            }
        }

    }
}
