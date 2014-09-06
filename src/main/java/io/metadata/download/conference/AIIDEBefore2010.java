package io.metadata.download.conference;

import java.io.IOException;

import io.metadata.MetaDataFactory;
import io.metadata.Website;
import io.metadata.misc.Globals;
import io.metadata.misc.Logger;
import io.metadata.orm.MyMongoCollection;
import io.metadata.orm.Paper;

/**
 * @author Zhengxing Chen
 * Download AIIDE before 2010
 */
public class AIIDEBefore2010 {

    public static String[] dois = {
            // aiide
            "AIIDE/2005/aiide05-001", "AIIDE/2005/aiide05-002", "AIIDE/2005/aiide05-003", "AIIDE/2005/aiide05-004",
            "AIIDE/2005/aiide05-005", "AIIDE/2005/aiide05-006", "AIIDE/2005/aiide05-007", "AIIDE/2005/aiide05-008",
            "AIIDE/2005/aiide05-009", "AIIDE/2005/aiide05-010", "AIIDE/2005/aiide05-011", "AIIDE/2005/aiide05-012",
            "AIIDE/2005/aiide05-013", "AIIDE/2005/aiide05-014", "AIIDE/2005/aiide05-015", "AIIDE/2005/aiide05-016",
            "AIIDE/2005/aiide05-017", "AIIDE/2005/aiide05-018", "AIIDE/2005/aiide05-019", "AIIDE/2005/aiide05-020",
            "AIIDE/2005/aiide05-021", "AIIDE/2005/aiide05-022", "AIIDE/2005/aiide05-023", "AIIDE/2005/aiide05-024",
            "AIIDE/2006/aiide06-005", "AIIDE/2006/aiide06-006", "AIIDE/2006/aiide06-007", "AIIDE/2006/aiide06-008",
            "AIIDE/2006/aiide06-009", "AIIDE/2006/aiide06-010", "AIIDE/2006/aiide06-011", "AIIDE/2006/aiide06-012",
            "AIIDE/2006/aiide06-013", "AIIDE/2006/aiide06-014", "AIIDE/2006/aiide06-015", "AIIDE/2006/aiide06-016",
            "AIIDE/2006/aiide06-017", "AIIDE/2006/aiide06-018", "AIIDE/2006/aiide06-019", "AIIDE/2006/aiide06-020",
            "AIIDE/2006/aiide06-021", "AIIDE/2006/aiide06-022", "AIIDE/2006/aiide06-023", "AIIDE/2006/aiide06-024",
            "AIIDE/2006/aiide06-025", "AIIDE/2006/aiide06-026", "AIIDE/2006/aiide06-027", "AIIDE/2006/aiide06-028",
            "AIIDE/2006/aiide06-029", "AIIDE/2006/aiide06-030", "AIIDE/2007/aiide07-001", "AIIDE/2007/aiide07-002",
            "AIIDE/2007/aiide07-003", "AIIDE/2007/aiide07-004", "AIIDE/2007/aiide07-005", "AIIDE/2007/aiide07-006",
            "AIIDE/2007/aiide07-007", "AIIDE/2007/aiide07-008", "AIIDE/2007/aiide07-009", "AIIDE/2007/aiide07-010",
            "AIIDE/2007/aiide07-011", "AIIDE/2007/aiide07-012", "AIIDE/2007/aiide07-013", "AIIDE/2007/aiide07-014",
            "AIIDE/2007/aiide07-015", "AIIDE/2007/aiide07-016", "AIIDE/2007/aiide07-017", "AIIDE/2007/aiide07-018",
            "AIIDE/2007/aiide07-019", "AIIDE/2007/aiide07-020", "AIIDE/2007/aiide07-021", "AIIDE/2007/aiide07-022",
            "AIIDE/2008/aiide08-001", "AIIDE/2008/aiide08-002", "AIIDE/2008/aiide08-003", "AIIDE/2008/aiide08-004",
            "AIIDE/2008/aiide08-005", "AIIDE/2008/aiide08-006", "AIIDE/2008/aiide08-007", "AIIDE/2008/aiide08-008",
            "AIIDE/2008/aiide08-009", "AIIDE/2008/aiide08-010", "AIIDE/2008/aiide08-011", "AIIDE/2008/aiide08-012",
            "AIIDE/2008/aiide08-013", "AIIDE/2008/aiide08-014", "AIIDE/2008/aiide08-015", "AIIDE/2008/aiide08-016",
            "AIIDE/2008/aiide08-017", "AIIDE/2008/aiide08-018", "AIIDE/2008/aiide08-019", "AIIDE/2008/aiide08-020",
            "AIIDE/2008/aiide08-021", "AIIDE/2008/aiide08-022", "AIIDE/2008/aiide08-023", "AIIDE/2008/aiide08-024",
            "AIIDE/2008/aiide08-025", "AIIDE/2008/aiide08-026", "AIIDE/2008/aiide08-027", "AIIDE/2008/aiide08-028",
            "AIIDE/2008/aiide08-029", "AIIDE/2008/aiide08-030", "AIIDE/2008/aiide08-031", "AIIDE/2008/aiide08-032",
            "AIIDE/2008/aiide08-033", "AIIDE/2008/aiide08-034", "AIIDE/2008/aiide08-035", };

    public static final String VENUE = "AIIDE";

    public static void main(String[] args) throws IOException, InterruptedException {
        // Initialize logger
        Logger mLogger = new Logger("logaiidebefore2010", true);

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
