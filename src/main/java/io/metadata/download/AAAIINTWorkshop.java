package io.metadata.download;

import java.io.IOException;

import io.metadata.MetaDataFactory;
import io.metadata.Website;
import io.metadata.misc.Globals;
import io.metadata.misc.Logger;
import io.metadata.orm.MyMongoCollection;
import io.metadata.orm.Paper;


/**
 * Download AAAI Intelligent Narrative Technologies Workshop from 2011 - 2013.
 * @author Zhengxing Chen
 *
 */
public class AAAIINTWorkshop {

    public static String[] dois = {
        "AIIDE/AIIDE13/paper/viewPaper/7470",
        "AIIDE/AIIDE13/paper/viewPaper/7427",
        "AIIDE/AIIDE13/paper/viewPaper/7417",
        "AIIDE/AIIDE13/paper/viewPaper/7419",
        "AIIDE/AIIDE13/paper/viewPaper/7434",
        "AIIDE/AIIDE13/paper/viewPaper/7437",
        "AIIDE/AIIDE13/paper/viewPaper/7433",
        "AIIDE/AIIDE13/paper/viewPaper/7463",
        "AIIDE/AIIDE13/paper/viewPaper/7472",
        "AIIDE/AIIDE13/paper/viewPaper/7454",
        "AIIDE/AIIDE13/paper/viewPaper/7442",
        "AIIDE/AIIDE13/paper/viewPaper/7460",
        "AIIDE/AIIDE13/paper/viewPaper/7468",
        "AIIDE/AIIDE13/paper/viewPaper/7458",
        "AIIDE/AIIDE13/paper/viewPaper/7464",
        "AIIDE/AIIDE13/paper/viewPaper/7438",
        "AIIDE/AIIDE13/paper/viewPaper/7461",
        "AIIDE/AIIDE13/paper/viewPaper/7439",
        "AIIDE/AIIDE13/paper/viewPaper/7446",
        "AIIDE/AIIDE13/paper/viewPaper/7424",
        "AIIDE/AIIDE11WS/paper/viewPaper/4108",
        "AIIDE/AIIDE11WS/paper/viewPaper/4096",
        "AIIDE/AIIDE11WS/paper/viewPaper/4092",
        "AIIDE/AIIDE11WS/paper/viewPaper/4091",
        "AIIDE/AIIDE11WS/paper/viewPaper/4113",
        "AIIDE/AIIDE11WS/paper/viewPaper/4101",
        "AIIDE/AIIDE11WS/paper/viewPaper/4107",
        "AIIDE/AIIDE11WS/paper/viewPaper/4102",
        "AIIDE/AIIDE11WS/paper/viewPaper/4110",
        "AIIDE/AIIDE11WS/paper/viewPaper/4098",
        "AIIDE/AIIDE11WS/paper/viewPaper/4089",
        "AIIDE/AIIDE11WS/paper/viewPaper/4111",
        "AIIDE/AIIDE11WS/paper/viewPaper/4109",
        "AIIDE/AIIDE11WS/paper/viewPaper/4106",
        "AIIDE/AIIDE11WS/paper/viewPaper/4094",
        "AIIDE/AIIDE11WS/paper/viewPaper/4115",
        "AIIDE/AIIDE11WS/paper/viewPaper/4104",
        "AIIDE/AIIDE11WS/paper/viewPaper/4099",
        "AIIDE/AIIDE11WS/paper/viewPaper/4103",
        "AIIDE/AIIDE12/paper/viewPaper/5491",
        "AIIDE/AIIDE12/paper/viewPaper/5490",
        "AIIDE/AIIDE12/paper/viewPaper/5481",
        "AIIDE/AIIDE12/paper/viewPaper/5485",
        "AIIDE/AIIDE12/paper/viewPaper/5532",
        "AIIDE/AIIDE12/paper/viewPaper/5509",
        "AIIDE/AIIDE12/paper/viewPaper/5550",
        "AIIDE/AIIDE12/paper/viewPaper/5494",
        "AIIDE/AIIDE12/paper/viewPaper/5536",
        "AIIDE/AIIDE12/paper/viewPaper/5511",
        "AIIDE/AIIDE12/paper/viewPaper/5488",
        "AIIDE/AIIDE12/paper/viewPaper/5499",
    };
    
    public static final String VENUE = "AIIIINTWorkshop";

    public static void main(String[] args) throws IOException, InterruptedException {
        // Initialize logger
        Logger mLogger = new Logger("logAIIIINTWorkshop", true);

        // Initialize self-wrapped mongocollection
        MyMongoCollection<Paper> mPapersCollection = new MyMongoCollection<Paper>(Globals.MONGODB_PAPERS_COLLECTION);

        MetaDataFactory mMetaDataFactory = new MetaDataFactory();

        for (String doi : dois) {
            try {
                Website mWebsite = mMetaDataFactory.getWebsite("io.metadata.AAAILibAfter2010", doi);

                // Note: AAAI papers don't have keywords.
                mLogger.appendLine(doi);
                mLogger.appendLine(mWebsite.getTitle());
                mLogger.appendLine(mWebsite.getAbstract());
                mLogger.appendLine(mWebsite.getKeywords());
                mLogger.appendLine(mWebsite.getAuthors());
                mLogger.appendLine(mWebsite.getYear());
                mLogger.appendLine("");

                mPapersCollection.insert(new Paper().setTitle(mWebsite.getTitle()).setAbstraction(mWebsite.getAbstract())
                        .setKeywords(mWebsite.getKeywords()).setAuthors(mWebsite.getAuthors()).setYear(mWebsite.getYear()).setVenue(VENUE));
           
            } catch (Exception e) {
                // Catch any exception
                mLogger.appendLine("\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\");
                mLogger.appendLine(e.getMessage());
            }

            // Anti-robotics
            Thread.sleep((long) (10000 + Math.random() * 10000));
            System.out.println();
        }
    }


}
