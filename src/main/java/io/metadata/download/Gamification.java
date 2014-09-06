package io.metadata.download;

import java.io.IOException;

import io.metadata.MetaDataFactory;
import io.metadata.Website;
import io.metadata.misc.Globals;
import io.metadata.misc.Logger;
import io.metadata.orm.MyMongoCollection;
import io.metadata.orm.Paper;


/**
 * @author Zhengxing Chen
 * Download Gamification 2013
 */
public class Gamification {

    public static String[] dois = {
        // acm
        "2583008.2583009",
        "2583008.2583010",
        "2583008.2583011",
        "2583008.2583012",
        "2583008.2583013",
        "2583008.2583014",
        "2583008.2583015",
        "2583008.2583016",
        "2583008.2583017",
        "2583008.2583018",
        "2583008.2583019",
        "2583008.2583020",
        "2583008.2583021",
        "2583008.2583022",
        "2583008.2583023",
        "2583008.2583024",
        "2583008.2583025",
        "2583008.2583026",
        "2583008.2583027",
        "2583008.2583028",
        "2583008.2583029",
        "2583008.2583030",
        "2583008.2583031",
        "2583008.2583032",
        "2583008.2583033",
        "2583008.2583034",
    };
    
    public static final String VENUE = "Gamification";

    public static void main(String[] args) throws IOException, InterruptedException {
        // Initialize logger
        Logger mLogger = new Logger("logGamification", true);

        // Initialize self-wrapped mongocollection
        MyMongoCollection<Paper> mPapersCollection = new MyMongoCollection<Paper>(Globals.MONGODB_PAPERS_COLLECTION);

        MetaDataFactory mMetaDataFactory = new MetaDataFactory();
        
        for (String doi : dois) {
            try {
                Website mWebsite = mMetaDataFactory.getWebsite("io.metadata.ACM", doi);

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
