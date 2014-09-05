package io.metadata.download;

import java.io.IOException;

import io.metadata.MetaDataFactory;
import io.metadata.Website;
import io.metadata.misc.Globals;
import io.metadata.misc.Logger;
import io.metadata.orm.MyMongoCollection;
import io.metadata.orm.Paper;

/**
 * Intetain from Springer.
 * 
 * @author Truong-Huy Nguyen
 */
public class IntetainSpringer {

    // Online DOI
    public static String[] bookDOIs = { "11590323", // 2005

            "978-3-642-02315-6", // 2009
            "978-3-642-30214-5", // 2011
            "978-3-319-03892-6", // 2013
            "978-3-319-08189-2", // 2014
    };

    public static int[] numberOfChapters = { 48, // 2005 - first

            29, // 2009 - 3rd
            37, // 2011 - 4th
            23, // 2013 - 5th
            22, // 2014 - 6th
    };

    public static final String VENUE = "Intetain";

    public static void main(String[] args) throws IOException, InterruptedException {
        // Initialize logger
        Logger mLogger = new Logger("logIntetain", true);

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

                    mLogger.appendLine(doi);
                    mLogger.appendLine(mWebsite.getTitle());
                    mLogger.appendLine(mWebsite.getAbstract());
                    mLogger.appendLine(mWebsite.getKeywords());
                    mLogger.appendLine(mWebsite.getAuthors());
                    mLogger.appendLine(mWebsite.getYear());
                    mLogger.appendLine("");

                    Paper mPaper = new Paper().setTitle(mWebsite.getTitle()).setAbstraction(mWebsite.getAbstract())
                            .setKeywords(mWebsite.getKeywords()).setAuthors(mWebsite.getAuthors()).setYear(mWebsite.getYear())
                            .setVenue(VENUE).setVenueType("conference");
                    if (mPaper.validate()) {
                        mPapersCollection.insert(mPaper);
                    } else {
                        mLogger.appendLine("///////////////////////////////////////////////////////////////////////");
                        mLogger.appendLine(mWebsite.getArticleURL());
                        mLogger.appendLine("///////////////////////////////////////////////////////////////////////");
                    }
                } catch (Exception e) {
                    // Catch any exception
                    mLogger.appendLine("\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\");
                    mLogger.appendLine(e.getMessage());
                }

                // Anti-robot
                Thread.sleep((long) (Math.random() * 60000));
                System.out.println();
            }
        }

    }
}
