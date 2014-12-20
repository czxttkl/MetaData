package io.metadata.download.journal;

import io.metadata.MetaDataFactory;
import io.metadata.Website;
import io.metadata.misc.Globals;
import io.metadata.misc.Logger;
import io.metadata.orm.MyMongoCollection;
import io.metadata.orm.Paper;

/** Downloader for TCIAIG. */
public class TCIAIG {

    public static String[] dois = {
        "4812073",
        "4804734",
        "4804731",
        "4811953",
        "4812072",
        "4804732",
        "4907351",
        "4804730",
        "5169832",
        "4804736",
        "5067377",
        "4804733",
        "5067382",
        "5191044",
        "5306119",
        "5247069",
        "5286208",
        "5247029",
        "5288605",
        "5325797",
        "5325892",
        "5345846",
        "5308333",
        "5352259",
        "5404867",
        "5420018",
        "5345847",
        "5409565",
        "5406130",
        "5438752",
        "5453108",
        "5466096",
        "5443495",
        "5454273",
        "5460962",
        "5557763",
        "5482132",
        "5560779",
        "5535135",
        "5518405",
        "5664776",
        "5654650",
        "5523941",
        "5674075",
        "5672398",
        "5551182",
        "5599855",
        "5604665",
        "5648340",
        "5654649",
        "5672586",
        "5696747",
        "5705567",
        "5585818",
        "5765665",
        "5708170",
        "5755185",
        "5746617",
        "5752830",
        "5740585",
        "5756645",
        "5783900",
        "5975206",
        "5940995",
        "5742785",
        "5887401",
        "5763766",
        "5960781",
        "6029288",
        "6004823",
        "5936110",
        "5783334",
        "6145622",
        "6169183",
        "6129491",
        "6156425",
        "6203383",
        "6203567",
        "6185647",
        "6168229",
        "6194296",
        "6179519",
        "6269992",
        "6185648",
        "6177652",
        "6156756",
        "6266725",
        "6212341",
        "6194295",
        "6218176",
        "6266709",
        "6262464",
        "6249736",
        "6243194",
        "6263288",
        "6357232",
        "6310042",
        "6327342",
        "6307831",
        "6320688",
        "6329938",
        "6518141",
        "6418003",
        "6334432",
        "6509428",
        "6461930",
        "6401177",
        "6451193",
        "6472046",
        "6484110",
        "6509413",
        "6400237",
        "6476646",
        "6514088",
        "6409426",
        "6365249",
        "6504736",
        "6502211",
        "6373713",
        "6571199",
        "6497554",
        "6637024",
        "6468079",
        "6687219",
        "6571220",
        "6675777",
        "6632881",
        "6517274",
        "6661386",
        "6701215",
        "6732913",
        "6604407",
        "6571237",
        "6661342",
        "6648395",
        "6672047",
        "6661381",
        "6646191",
        "6657816",
        "6671428",
        "6571208",
        "6644281",
        "6623111",
        "6609034",
        "6731713",
        "6762937",
        "6003769",
    };

    public static final String VENUE = "TCIAIG";
    
    public static void main(String[] args) throws Exception {
        // Initialize logger
        Logger mLogger = new Logger("logTCIAIG", true);

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
                        .setVenueType(Globals.VENUE_TYPE_JOURNAL);

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
