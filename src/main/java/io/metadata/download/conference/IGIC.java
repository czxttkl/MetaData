package io.metadata.download.conference;

import io.metadata.MetaDataFactory;
import io.metadata.Website;
import io.metadata.misc.Globals;
import io.metadata.misc.Logger;
import io.metadata.orm.MyMongoCollection;
import io.metadata.orm.Paper;

/**
 * Downloader for Games Innovation Conference.
 * @author Zhengxing Chen
 *
 */
public class IGIC {

    public static String[] dois = {
            "6115116",
            "6115019",
            "6115106",
            "6115107",
            "6115108",
            "6115109",
            "6115110",
            "6115111",
            "6115115",
            "6115112",
            "6115113",
            "6115114",
            "6115117",
            "6115118",
            "6115119",
            "6115120",
            "6115121",
            "6115122",
            "6115123",
            "6115124",
            "6115125",
            "6115126",
            "6114852",
            "6115127",
            "6115128",
            "6115129",
            "6115133",
            "6115130",
            "6115131",
            "6115132",
            "6115134",
            "6115135",
            "6115136",
            "6114950",
            "6114952",
            "6114951",
            "6114953",
            "6114954",
            "6659124",
            "6659125",
            "6659126",
            "6659127",
            "6659128",
            "6659129",
            "6659130",
            "6659131",
            "6659132",
            "6659134",
            "6659133",
            "6659135",
            "6659136",
            "6659137",
            "6659138",
            "6659139",
            "6659140",
            "6659141",
            "6659142",
            "6659143",
            "6659144",
            "6659145",
            "6659146",
            "6659147",
            "6659148",
            "6659149",
            "6659150",
            "6659151",
            "6659152",
            "6659153",
            "6659154",
            "6659155",
            "6659156",
            "6659157",
            "6659158",
            "6659159",
            "6659160",
            "6659161",
            "6659162",
            "6659163",
            "6659164",
            "6659165",
            "6659166",
            "6659167",
            "6659168",
            "6659169",
            "6659170",
            "6659171",
            "6659172",
            "6659173",
            "6329824",
            "6329825",
            "6329826",
            "6329827",
            "6329828",
            "6329829",
            "6329830",
            "6329831",
            "6329832",
            "6329834",
            "6329833",
            "6329835",
            "6329836",
            "6329837",
            "6329838",
            "6329839",
            "6329840",
            "6329841",
            "6329842",
            "6329843",
            "6329844",
            "6329845",
            "6329846",
            "6329847",
            "6329848",
            "6329849",
            "6329850",
            "6329851",
            "6329852",
            "6329853",
            "6329854",
            "6329855",
            "6329856",
            "6329857",
            "6329858",
            "6329859",
            "6329860",
            "6329861",
            "6329862",
            "6329863",
 
    };

    public static final String VENUE = "IGIC";
    
    public static void main(String[] args) throws Exception {
        // Initialize logger
        Logger mLogger = new Logger("logIGIC", true);

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
            Thread.sleep((long) (Math.random() * 20000));
            System.out.println();
        }
    }

}
