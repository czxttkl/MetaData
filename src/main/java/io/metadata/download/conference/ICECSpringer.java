package io.metadata.download.conference;

import java.io.IOException;

import io.metadata.MetaDataFactory;
import io.metadata.Website;
import io.metadata.misc.Globals;
import io.metadata.misc.Logger;
import io.metadata.orm.MyMongoCollection;
import io.metadata.orm.Paper;

/**
 * ICEC from Springer.
 * 
 * @author Zhengxing Chen
 */
public class ICECSpringer {

    // Online DOI
    public static String[] bookDOIs = {
            // "978-3-540-28643-1", // 2004
//            "11558651", // 2005
//            "11872320", // 2006
//            "978-3-540-74873-1", // 2007
//            "978-3-540-89222-9", // 2008
//            "978-3-642-04052-8", // 2009
//            "978-3-642-15399-0", // 2010
            "978-3-642-24500-8", // 2011
            "978-3-642-33542-6", // 2012
            "978-3-642-41106-9", // 2013
    };

    public static int[] numberOfChapters = {
            // 82, // 2004
//            56, // 2005
//            62, // 2006
//            64, // 2007
//            31, // 2008
//            62, // 2009
//            77, // 2010
            70, // 2011
            81, // 2012
            30, // 2013
    };

    public static final String VENUE = "ICEC";

    public static void main(String[] args) throws IOException, InterruptedException {
        // Initialize logger
        Logger mLogger = new Logger("logICEC", true);

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
