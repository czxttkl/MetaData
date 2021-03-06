package io.metadata.download.conference;

import java.io.IOException;

import io.metadata.MetaDataFactory;
import io.metadata.Website;
import io.metadata.misc.Globals;
import io.metadata.misc.Logger;
import io.metadata.orm.MyMongoCollection;
import io.metadata.orm.Paper;

/**
 * ACE from Springer.
 * @author Zhengxing Chen
 */
public class ACESpringer {

    public static String[] dois = {
        "10.1007/978-3-642-34292-9_58",
        "10.1007/978-3-319-03161-3_37",
        "10.1007/978-3-319-03161-3_10",
        "10.1007/978-3-642-34292-9_30",
        "10.1007/978-3-319-03161-3_74",
        "10.1007/978-3-319-03161-3_58",
        "10.1007/978-3-642-34292-9_23",
        "10.1007/978-3-319-03161-3_45",
        "10.1007/978-3-319-03161-3_16",
        "10.1007/978-3-642-34292-9_65",
        "10.1007/978-3-319-03161-3_5",
        "10.1007/978-3-642-34292-9_41",
        "10.1007/978-3-319-03161-3_75",
        "10.1007/978-3-319-03161-3_62",
        "10.1007/978-3-642-34292-9_55",
        "10.1007/978-3-319-03161-3_7",
        "10.1007/978-3-319-03161-3_61",
        "10.1007/978-3-642-34292-9_7",
        "10.1007/978-3-642-34292-9_56",
        "10.1007/978-3-319-03161-3_1",
        "10.1007/978-3-642-34292-9_13",
        "10.1007/978-3-319-03161-3_39",
        "10.1007/978-3-319-03161-3_6",
        "10.1007/978-3-319-03161-3_54",
        "10.1007/978-3-642-34292-9_5",
        "10.1007/978-3-642-34292-9_54",
        "10.1007/978-3-319-03161-3_32",
        "10.1007/978-3-642-34292-9_40",
        "10.1007/978-3-319-03161-3_70",
        "10.1007/978-3-642-34292-9_19",
        "10.1007/978-3-319-03161-3_13",
        "10.1007/978-3-642-34292-9_27",
        "10.1007/978-3-319-03161-3_22",
        "10.1007/978-3-319-03161-3_47",
        "10.1007/978-3-319-03161-3_50",
        "10.1007/978-3-319-03161-3_67",
        "10.1007/978-3-319-03161-3_55",
        "10.1007/978-3-642-34292-9_67",
        "10.1007/978-3-642-34292-9_16",
        "10.1007/978-3-319-03161-3_44",
        "10.1007/978-3-642-34292-9_59",
        "10.1007/978-3-642-34292-9_51",
        "10.1007/978-3-319-03161-3_64",
        "10.1007/978-3-642-34292-9_3",
        "10.1007/978-3-319-03161-3_27",
        "10.1007/978-3-319-03161-3_46",
        "10.1007/978-3-319-03161-3_41",
        "10.1007/978-3-642-34292-9_39",
        "10.1007/978-3-642-34292-9_42",
        "10.1007/978-3-642-34292-9_66",
        "10.1007/978-3-319-03161-3_38",
        "10.1007/978-3-319-03161-3_4",
        "10.1007/978-3-319-03161-3_72",
        "10.1007/978-3-319-03161-3_65",
        "10.1007/978-3-642-34292-9_33",
        "10.1007/978-3-642-34292-9_25",
        "10.1007/978-3-642-34292-9_57",
        "10.1007/978-3-642-34292-9_53",
        "10.1007/978-3-642-34292-9_43",
        "10.1007/978-3-642-34292-9_14",
        "10.1007/978-3-319-03161-3_53",
        "10.1007/978-3-642-34292-9_15",
        "10.1007/978-3-319-03161-3_77",
        "10.1007/978-3-642-34292-9_61",
        "10.1007/978-3-642-34292-9_47",
        "10.1007/978-3-319-03161-3_8",
        "10.1007/978-3-319-03161-3_73",
        "10.1007/978-3-642-34292-9_46",
        "10.1007/978-3-642-34292-9_8",
        "10.1007/978-3-319-03161-3_31",
        "10.1007/978-3-642-34292-9_64",
        "10.1007/978-3-642-34292-9_6",
        "10.1007/978-3-642-34292-9_12",
        "10.1007/978-3-642-34292-9_20",
        "10.1007/978-3-319-03161-3_36",
        "10.1007/978-3-319-03161-3_49",
        "10.1007/978-3-319-03161-3_18",
        "10.1007/978-3-319-03161-3_20",
        "10.1007/978-3-642-34292-9_21",
        "10.1007/978-3-642-34292-9_32",
        "10.1007/978-3-642-34292-9_2",
        "10.1007/978-3-642-34292-9_18",
        "10.1007/978-3-319-03161-3_2",
        "10.1007/978-3-319-03161-3_19",
        "10.1007/978-3-319-03161-3_52",
        "10.1007/978-3-319-03161-3_76",
        "10.1007/978-3-319-03161-3_29",
        "10.1007/978-3-319-03161-3_40",
        "10.1007/978-3-319-03161-3_11",
        "10.1007/978-3-319-03161-3_30",
        "10.1007/978-3-319-03161-3_28",
        "10.1007/978-3-642-34292-9_38",
        "10.1007/978-3-319-03161-3_33",
        "10.1007/978-3-319-03161-3_34",
        "10.1007/978-3-642-34292-9_36",
        "10.1007/978-3-642-34292-9_62",
        "10.1007/978-3-642-34292-9_10",
        "10.1007/978-3-319-03161-3_26",
        "10.1007/978-3-319-03161-3_21",
        "10.1007/978-3-319-03161-3_35",
        "10.1007/978-3-642-34292-9_50",
        "10.1007/978-3-642-34292-9_49",
        "10.1007/978-3-642-34292-9_34",
        "10.1007/978-3-642-34292-9_24",
        "10.1007/978-3-319-03161-3_25",
        "10.1007/978-3-319-03161-3_71",
        "10.1007/978-3-642-34292-9_37",
        "10.1007/978-3-319-03161-3_12",
        "10.1007/978-3-642-34292-9_9",
        "10.1007/978-3-319-03161-3_48",
        "10.1007/978-3-642-34292-9_1",
        "10.1007/978-3-319-03161-3_68",
        "10.1007/978-3-319-03161-3_3",
        "10.1007/978-3-319-03161-3_60",
        "10.1007/978-3-319-03161-3_17",
        "10.1007/978-3-642-34292-9_28",
        "10.1007/978-3-319-03161-3_63",
        "10.1007/978-3-319-03161-3_66",
        "10.1007/978-3-319-03161-3_69",
        "10.1007/978-3-642-34292-9_31",
        "10.1007/978-3-642-34292-9_48",
        "10.1007/978-3-642-34292-9_44",
        "10.1007/978-3-642-34292-9_29",
        "10.1007/978-3-319-03161-3_57",
        "10.1007/978-3-642-34292-9_4",
        "10.1007/978-3-319-03161-3_51",
        "10.1007/978-3-319-03161-3_15",
        "10.1007/978-3-319-03161-3_14",
        "10.1007/978-3-319-03161-3_59",
        "10.1007/978-3-319-03161-3_24",
        "10.1007/978-3-642-34292-9_11",
        "10.1007/978-3-319-03161-3_23",
        "10.1007/978-3-642-34292-9_60",
        "10.1007/978-3-642-34292-9_63",
        "10.1007/978-3-319-03161-3_56",
        "10.1007/978-3-642-34292-9_26",
        "10.1007/978-3-319-03161-3_9",
        "10.1007/978-3-642-34292-9_52",
        "10.1007/978-3-642-34292-9_35",
        "10.1007/978-3-642-34292-9_22",
        "10.1007/978-3-642-34292-9_17",
        "10.1007/978-3-319-03161-3_43",
        "10.1007/978-3-319-03161-3_42",
        "10.1007/978-3-642-34292-9_45",
    };
    
    public static final String VENUE = "ACE";

    public static void main(String[] args) throws IOException, InterruptedException {
        // Initialize logger
        Logger mLogger = new Logger("logACE", true);

        // Initialize self-wrapped mongocollection
        MyMongoCollection<Paper> mPapersCollection = new MyMongoCollection<Paper>(Globals.MONGODB_PAPERS_COLLECTION);

        MetaDataFactory mMetaDataFactory = new MetaDataFactory();

        for (String doi : dois) {
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
