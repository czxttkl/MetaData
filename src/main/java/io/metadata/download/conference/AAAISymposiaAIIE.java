package io.metadata.download.conference;

import java.io.IOException;

import io.metadata.MetaDataFactory;
import io.metadata.Website;
import io.metadata.misc.Globals;
import io.metadata.misc.Logger;
import io.metadata.orm.MyMongoCollection;
import io.metadata.orm.Paper;


/**
 * Download AAAI Symposia. 
 * @author Zhengxing Chen
 *
 */
public class AAAISymposiaAIIE {

    public static String[] dois = {
        "Symposia/Spring/2001/ss01-02-001",
        "Symposia/Spring/2001/ss01-02-002",
        "Symposia/Spring/2001/ss01-02-003",
        "Symposia/Spring/2001/ss01-02-004",
        "Symposia/Spring/2001/ss01-02-005",
        "Symposia/Spring/2001/ss01-02-006",
        "Symposia/Spring/2001/ss01-02-007",
        "Symposia/Spring/2001/ss01-02-008",
        "Symposia/Spring/2001/ss01-02-009",
        "Symposia/Spring/2001/ss01-02-010",
        "Symposia/Spring/2001/ss01-02-011",
        "Symposia/Spring/2001/ss01-02-012",
        "Symposia/Spring/2001/ss01-02-013",
        "Symposia/Spring/2001/ss01-02-014",
        "Symposia/Spring/2001/ss01-02-015",
        "Symposia/Spring/2001/ss01-02-016",
        "Symposia/Spring/2001/ss01-02-017",
        "Symposia/Spring/2001/ss01-02-018",
        "Symposia/Spring/2002/ss02-01-001",
        "Symposia/Spring/2002/ss02-01-002",
        "Symposia/Spring/2002/ss02-01-003",
        "Symposia/Spring/2002/ss02-01-004",
        "Symposia/Spring/2002/ss02-01-005",
        "Symposia/Spring/2002/ss02-01-006",
        "Symposia/Spring/2002/ss02-01-007",
        "Symposia/Spring/2002/ss02-01-008",
        "Symposia/Spring/2002/ss02-01-009",
        "Symposia/Spring/2002/ss02-01-010",
        "Symposia/Spring/2002/ss02-01-011",
        "Symposia/Spring/2002/ss02-01-012",
        "Symposia/Spring/2002/ss02-01-013",
        "Symposia/Spring/2002/ss02-01-014",
        "Symposia/Spring/2002/ss02-01-015",
        "Symposia/Spring/2002/ss02-01-016",
        "Symposia/Spring/2002/ss02-01-017",
        "Symposia/Spring/2002/ss02-01-018",
        "Symposia/Spring/2002/ss02-01-019",
        "Symposia/Spring/2002/ss02-01-020",
        "Symposia/Spring/2000/ss00-02-001",
        "Symposia/Spring/2000/ss00-02-002",
        "Symposia/Spring/2000/ss00-02-003",
        "Symposia/Spring/2000/ss00-02-004",
        "Symposia/Spring/2000/ss00-02-005",
        "Symposia/Spring/2000/ss00-02-006",
        "Symposia/Spring/2000/ss00-02-007",
        "Symposia/Spring/2000/ss00-02-008",
        "Symposia/Spring/2000/ss00-02-009",
        "Symposia/Spring/2000/ss00-02-010",
        "Symposia/Spring/2000/ss00-02-011",
        "Symposia/Spring/2000/ss00-02-012",
        "Symposia/Spring/2000/ss00-02-013",
        "Symposia/Spring/2000/ss00-02-014",
        "Symposia/Spring/2000/ss00-02-015",
        "Symposia/Spring/2000/ss00-02-016",
    };
    
    public static final String VENUE = "AAAISAIIE";

    public static void main(String[] args) throws IOException, InterruptedException {
        // Initialize logger
        Logger mLogger = new Logger("logAAAISAIIE", true);

        // Initialize self-wrapped mongocollection
        MyMongoCollection<Paper> mPapersCollection = new MyMongoCollection<Paper>(Globals.MONGODB_PAPERS_COLLECTION);

        MetaDataFactory mMetaDataFactory = new MetaDataFactory();

        for (String doi : dois) {
            try {
                Website mWebsite = mMetaDataFactory.getWebsite("io.metadata.AAAILibBefore2010", doi);

                // Note: AAAI papers don't have keywords.
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
            Thread.sleep((long) (10000 + Math.random() * 10000));
            System.out.println();
        }
    }

}
