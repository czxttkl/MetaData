package io.metadata.download;

import io.metadata.MetaDataFactory;
import io.metadata.Website;
import io.metadata.misc.Globals;
import io.metadata.misc.Logger;
import io.metadata.orm.MyMongoCollection;
import io.metadata.orm.Paper;

/**
 * @author Zhengxing Chen
 * Download AIIDE after 2010.
 *
 */
public class AIIDEAfter2010 {

    public static String[] dois = {
        // aiide
        "AIIDE/AIIDE10/paper/viewPaper/2119",
        "AIIDE/AIIDE10/paper/viewPaper/2143",
        "AIIDE/AIIDE10/paper/viewPaper/2110",
        "AIIDE/AIIDE10/paper/viewPaper/2128",
        "AIIDE/AIIDE10/paper/viewPaper/2130",
        "AIIDE/AIIDE10/paper/viewPaper/2122",
        "AIIDE/AIIDE10/paper/viewPaper/2131",
        "AIIDE/AIIDE10/paper/viewPaper/2125",
        "AIIDE/AIIDE10/paper/viewPaper/2116",
        "AIIDE/AIIDE10/paper/viewPaper/2149",
        "AIIDE/AIIDE10/paper/viewPaper/2135",
        "AIIDE/AIIDE10/paper/viewPaper/2121",
        "AIIDE/AIIDE10/paper/viewPaper/2139",
        "AIIDE/AIIDE10/paper/viewPaper/2111",
        "AIIDE/AIIDE10/paper/viewPaper/2112",
        "AIIDE/AIIDE10/paper/viewPaper/2133",
        "AIIDE/AIIDE10/paper/viewPaper/2142",
        "AIIDE/AIIDE10/paper/viewPaper/2129",
        "AIIDE/AIIDE10/paper/viewPaper/2132",
        "AIIDE/AIIDE10/paper/viewPaper/2145",
        "AIIDE/AIIDE10/paper/viewPaper/2113",
        "AIIDE/AIIDE10/paper/viewPaper/2144",
        "AIIDE/AIIDE10/paper/viewPaper/2150",
        "AIIDE/AIIDE10/paper/viewPaper/2153",
        "AIIDE/AIIDE10/paper/viewPaper/2147",
        "AIIDE/AIIDE10/paper/viewPaper/2137",
        "AIIDE/AIIDE10/paper/viewPaper/2115",
        "AIIDE/AIIDE10/paper/viewPaper/2114",
        "AIIDE/AIIDE10/paper/viewPaper/2151",
        "AIIDE/AIIDE10/paper/viewPaper/2124",
        "AIIDE/AIIDE10/paper/viewPaper/2117",
        "AIIDE/AIIDE10/paper/viewPaper/2152",
        "AIIDE/AIIDE10/paper/viewPaper/2138",
        "AIIDE/AIIDE10/paper/viewPaper/2120",
        "AIIDE/AIIDE10/paper/viewPaper/2136",
        
        // Notice: can't access to AIIDE 2009
        /*"AIIDE/AIIDE09/paper/viewPaper/698",
        "AIIDE/AIIDE09/paper/viewPaper/813",
        "AIIDE/AIIDE09/paper/viewPaper/802",
        "AIIDE/AIIDE09/paper/viewPaper/776",
        "AIIDE/AIIDE09/paper/viewPaper/838",
        "AIIDE/AIIDE09/paper/viewPaper/793",
        "AIIDE/AIIDE09/paper/viewPaper/792",
        "AIIDE/AIIDE09/paper/viewPaper/811",
        "AIIDE/AIIDE09/paper/viewPaper/808",
        "AIIDE/AIIDE09/paper/viewPaper/689",
        "AIIDE/AIIDE09/paper/viewPaper/801",
        "AIIDE/AIIDE09/paper/viewPaper/791",
        "AIIDE/AIIDE09/paper/viewPaper/780",
        "AIIDE/AIIDE09/paper/viewPaper/817",
        "AIIDE/AIIDE09/paper/viewPaper/812",
        "AIIDE/AIIDE09/paper/viewPaper/781",
        "AIIDE/AIIDE09/paper/viewPaper/805",
        "AIIDE/AIIDE09/paper/viewPaper/809",
        "AIIDE/AIIDE09/paper/viewPaper/804",
        "AIIDE/AIIDE09/paper/viewPaper/782",
        "AIIDE/AIIDE09/paper/viewPaper/800",
        "AIIDE/AIIDE09/paper/viewPaper/778",
        "AIIDE/AIIDE09/paper/viewPaper/816",
        "AIIDE/AIIDE09/paper/viewPaper/810",
        "AIIDE/AIIDE09/paper/viewPaper/789",
        "AIIDE/AIIDE09/paper/viewPaper/783",
        "AIIDE/AIIDE09/paper/viewPaper/797",
        "AIIDE/AIIDE09/paper/viewPaper/690",
        "AIIDE/AIIDE09/paper/viewPaper/799",
        "AIIDE/AIIDE09/paper/viewPaper/775",*/
        
        
        "AIIDE/AIIDE11/paper/viewPaper/4046",
        "AIIDE/AIIDE11/paper/viewPaper/4086",
        "AIIDE/AIIDE11/paper/viewPaper/4078",
        "AIIDE/AIIDE11/paper/viewPaper/4073",
        "AIIDE/AIIDE11/paper/viewPaper/4057",
        "AIIDE/AIIDE11/paper/viewPaper/4077",
        "AIIDE/AIIDE11/paper/viewPaper/4081",
        "AIIDE/AIIDE11/paper/viewPaper/4065",
        "AIIDE/AIIDE11/paper/viewPaper/4071",
        "AIIDE/AIIDE11/paper/viewPaper/4052",
        "AIIDE/AIIDE11/paper/viewPaper/4054",
        "AIIDE/AIIDE11/paper/viewPaper/4085",
        "AIIDE/AIIDE11/paper/viewPaper/4062",
        "AIIDE/AIIDE11/paper/viewPaper/4063",
        "AIIDE/AIIDE11/paper/viewPaper/4083",
        "AIIDE/AIIDE11/paper/viewPaper/4058",
        "AIIDE/AIIDE11/paper/viewPaper/4051",
        "AIIDE/AIIDE11/paper/viewPaper/4048",
        "AIIDE/AIIDE11/paper/viewPaper/4045",
        "AIIDE/AIIDE11/paper/viewPaper/4050",
        "AIIDE/AIIDE11/paper/viewPaper/4084",
        "AIIDE/AIIDE11/paper/viewPaper/4059",
        "AIIDE/AIIDE11/paper/viewPaper/4072",
        "AIIDE/AIIDE11/paper/viewPaper/4082",
        "AIIDE/AIIDE11/paper/viewPaper/4053",
        "AIIDE/AIIDE11/paper/viewPaper/4080",
        "AIIDE/AIIDE11/paper/viewPaper/4069",
        "AIIDE/AIIDE11/paper/viewPaper/4066",
        "AIIDE/AIIDE11/paper/viewPaper/4079",
        "AIIDE/AIIDE11/paper/viewPaper/4056",
        "AIIDE/AIIDE11/paper/viewPaper/4068",
        "AIIDE/AIIDE11/paper/viewPaper/4067",
        "AIIDE/AIIDE11/paper/viewPaper/4055",
        "AIIDE/AIIDE11/paper/viewPaper/4049",
        "AIIDE/AIIDE12/paper/viewPaper/5459",
        "AIIDE/AIIDE12/paper/viewPaper/5467",
        "AIIDE/AIIDE12/paper/viewPaper/5446",
        "AIIDE/AIIDE12/paper/viewPaper/5466",
        "AIIDE/AIIDE12/paper/viewPaper/5470",
        "AIIDE/AIIDE12/paper/viewPaper/5452",
        "AIIDE/AIIDE12/paper/viewPaper/5461",
        "AIIDE/AIIDE12/paper/viewPaper/5458",
        "AIIDE/AIIDE12/paper/viewPaper/5445",
        "AIIDE/AIIDE12/paper/viewPaper/5457",
        "AIIDE/AIIDE12/paper/viewPaper/5449",
        "AIIDE/AIIDE12/paper/viewPaper/5442",
        "AIIDE/AIIDE12/paper/viewPaper/5448",
        "AIIDE/AIIDE12/paper/viewPaper/5456",
        "AIIDE/AIIDE12/paper/viewPaper/5450",
        "AIIDE/AIIDE12/paper/viewPaper/5451",
        "AIIDE/AIIDE12/paper/viewPaper/5444",
        "AIIDE/AIIDE12/paper/viewPaper/5447",
        "AIIDE/AIIDE12/paper/viewPaper/5469",
        "AIIDE/AIIDE12/paper/viewPaper/5454",
        "AIIDE/AIIDE12/paper/viewPaper/5463",
        "AIIDE/AIIDE12/paper/viewPaper/5436",
        "AIIDE/AIIDE12/paper/viewPaper/5438",
        "AIIDE/AIIDE12/paper/viewPaper/5464",
        "AIIDE/AIIDE12/paper/viewPaper/5460",
        "AIIDE/AIIDE12/paper/viewPaper/5441",
        "AIIDE/AIIDE12/paper/viewPaper/5465",
        "AIIDE/AIIDE12/paper/viewPaper/5443",
        "AIIDE/AIIDE12/paper/viewPaper/5455",
        "AIIDE/AIIDE12/paper/viewPaper/5462",
        "AIIDE/AIIDE12/paper/viewPaper/5471",
        "AIIDE/AIIDE12/paper/viewPaper/5472",
        "AIIDE/AIIDE13/paper/viewPaper/7378",
        "AIIDE/AIIDE13/paper/viewPaper/7395",
        "AIIDE/AIIDE13/paper/viewPaper/7398",
        "AIIDE/AIIDE13/paper/viewPaper/7387",
        "AIIDE/AIIDE13/paper/viewPaper/7374",
        "AIIDE/AIIDE13/paper/viewPaper/7386",
        "AIIDE/AIIDE13/paper/viewPaper/7390",
        "AIIDE/AIIDE13/paper/viewPaper/7367",
        "AIIDE/AIIDE13/paper/viewPaper/7377",
        "AIIDE/AIIDE13/paper/viewPaper/7382",
        "AIIDE/AIIDE13/paper/viewPaper/7403",
        "AIIDE/AIIDE13/paper/viewPaper/7408",
        "AIIDE/AIIDE13/paper/viewPaper/7381",
        "AIIDE/AIIDE13/paper/viewPaper/7411",
        "AIIDE/AIIDE13/paper/viewPaper/7369",
        "AIIDE/AIIDE13/paper/viewPaper/7409",
        "AIIDE/AIIDE13/paper/viewPaper/7383",
        "AIIDE/AIIDE13/paper/viewPaper/7373",
        "AIIDE/AIIDE13/paper/viewPaper/7394",
        "AIIDE/AIIDE13/paper/viewPaper/7410",
        "AIIDE/AIIDE13/paper/viewPaper/7391",
        "AIIDE/AIIDE13/paper/viewPaper/7380",
        "AIIDE/AIIDE13/paper/viewPaper/7407",
        "AIIDE/AIIDE13/paper/viewPaper/7389",
        "AIIDE/AIIDE13/paper/viewPaper/7397",
        "AIIDE/AIIDE13/paper/viewPaper/7400",
        "AIIDE/AIIDE13/paper/viewPaper/7370",
        "AIIDE/AIIDE13/paper/viewPaper/7371",
        "AIIDE/AIIDE13/paper/viewPaper/7401",
    };
    
    public static final String VENUE = "AIIDE";

    public static void main(String[] args) throws Exception {
        // Initialize logger
        Logger mLogger = new Logger("logAIIDE", true);

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
