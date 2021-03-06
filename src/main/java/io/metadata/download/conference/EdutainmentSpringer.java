package io.metadata.download.conference;

import java.io.IOException;

import io.metadata.MetaDataFactory;
import io.metadata.Website;
import io.metadata.misc.Globals;
import io.metadata.misc.Logger;
import io.metadata.orm.MyMongoCollection;
import io.metadata.orm.Paper;

/**
 * Edutainment from Springer.
 * 
 * @author Truong-Huy Nguyen
 */
public class EdutainmentSpringer {

    // Online DOI
    public static String[] bookDOIs = {
        "11736639", // 2006
        "978-3-540-73011-8", // 2007
        "978-3-540-69736-7", // 2008
//        "978-3-642-03364-3", // 2009
//        "978-3-642-14533-9", // 2010
//        "978-3-642-23456-9", // 2011
    };

    public static int[] numberOfChapters = { 
        173, // 2006
        99, // 2007
        83, // 2008
//        71, // 2009 - 4th
//        63, // 2010
//        99, // 2011
    };
    
    public static int[] startingChapter = {2, 3, 1};

    public static final String VENUE = "Edutainment";

    public static void main(String[] args) throws IOException, InterruptedException {
        // Initialize logger
        Logger mLogger = new Logger("logEdutainment", true);

        // Initialize self-wrapped mongocollection
        MyMongoCollection<Paper> mPapersCollection = new MyMongoCollection<Paper>(Globals.MONGODB_PAPERS_COLLECTION);

        MetaDataFactory mMetaDataFactory = new MetaDataFactory();

        // Each book
        String doi;
        for (int i = 0; i < bookDOIs.length; i++) {

            // each chapter in book
            for (int j = startingChapter[i]; j <= numberOfChapters[i]; j++) {
                doi = "10.1007/" + bookDOIs[i] + "_" + j;
                System.out.println(doi);
                try {
                    Website mWebsite = mMetaDataFactory.getWebsite("io.metadata.Springer", doi);
                    System.out.println(mWebsite.getArticleURL());

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

                // Anti-robot
                Thread.sleep((long) (Math.random() * 10000));
                System.out.println();
            }
        }

    }
}
