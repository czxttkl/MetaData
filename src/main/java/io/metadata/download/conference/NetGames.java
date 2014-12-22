package io.metadata.download.conference;

import io.metadata.MetaDataFactory;
import io.metadata.Website;
import io.metadata.misc.Globals;
import io.metadata.misc.Logger;
import io.metadata.orm.MyMongoCollection;
import io.metadata.orm.Paper;


/** Downloader for NetGames. */
public class NetGames {

    public static String[] dois = {
        "5446226",
        "5446235",
        "5446236",
        "5446237",
        "5446230",
        "5446233",
        "5446221",
        "5446231",
        "5446227",
        "5446220",
        "5446232",
        "5446234",
        "5446228",
        "5446229",
        "5446224",
        "5446225",
        "5446222",
        "5446223",
        "5679537",
        "5679538",
        "5679571",
        "5679572",
        "5679578",
        "5680187",
        "5679650",
        "5679527",
        "5680282",
        "5680283",
        "5680284",
        "5679542",
        "5679543",
        "5679544",
        "5679654",
        "5679655",
        "5679660",
        "5679667",
        "5679669",
        "5680275",
        "6080977",
        "6080978",
        "6080979",
        "6080980",
        "6080981",
        "6080982",
        "6080983",
        "6080984",
        "6080985",
        "6080986",
        "6080987",
        "6080988",
        "6080989",
        "6080990",
        "6080991",
        "6080992",
        "6080993",
        "6080994",
        "6080995",
        "6404013",
        "6404024",
        "6404025",
        "6404026",
        "6404027",
        "6404028",
        "6404029",
        "6404030",
        "6404031",
        "6404014",
        "6404015",
        "6404016",
        "6404017",
        "6404018", 
        "6404019",
        "6404020",
        "6404021",
        "6404022",
        "6404023",
        "6820601",
        "6820602",
        "6820603",
        "6820604",
        "6820605",
        "6820606",
        "6820607",
        "6820608",
        "6820609",
        "6820610",
        "6820611",
        "6820612",
        "6820613",
        "6820614",
        "6820615",
        "6820616",
        "6820617",
    };
    
    public static final String VENUE = "NetGames";
    
    public static void main(String[] args) throws Exception {
        // Initialize logger
        Logger mLogger = new Logger("logNetGames", true);

        // Initialize self-wrapped mongocollection
        MyMongoCollection<Paper> mPapersCollection = new MyMongoCollection<Paper>(Globals.MONGODB_PAPERS_COLLECTION);

        MetaDataFactory mMetaDataFactory = new MetaDataFactory();

        for (String doi : dois) {
            try {
                Website mWebsite = mMetaDataFactory.getWebsite("io.metadata.IEEEXplore1", doi);

                mLogger.appendLines("" + doi, mWebsite.getTitle(), mWebsite.getAbstract(), mWebsite.getKeywords(), mWebsite.getAuthors(),
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
            Thread.sleep((long) (Math.random() * 80000));
            System.out.println();
        }
    }

}
