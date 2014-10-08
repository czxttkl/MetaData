package io.metadata.download.journal;

import io.metadata.MetaDataFactory;
import io.metadata.Website;
import io.metadata.misc.Globals;
import io.metadata.misc.Logger;
import io.metadata.orm.MyMongoCollection;
import io.metadata.orm.Paper;

import java.io.IOException;

/**
 * Crawl Data for GameStudies.
 * @author Zhengxing Chen
 *
 */
public class GameStudies {
    
    public static String[] dois = {
        
        // The commented-out block has irregular formats thus difficult to crawl.
       /* "0202/wright/",
        "0101/bringsjord/",
        "0501/burke/",
        "0301/carr/",
        "0302/castronova/",
        "0501/davis_steury_pagulayan/",
        "0501/ermi_mayra/",
        "0101/eskelinen/",
        "0101/frasca/",
        "0302/frasca/",
        "0301/fromme/",
        "0401/galloway/",
        "0501/gingold/",
        "0501/gruenvogel/",
        "0102/jarvinen/",
        "0401/jarvinen/",
        "0101/juul-review/",
        "0202/kennedy/",
        "0401/kolo/",
        "0301/kucklich/",
        "0302/lee/",
        "0501/lindley/",
        "0202/lugo/",
        "0301/manninen/",
        "0501/manninen_kujanpaa/",
        "0102/mortensen/",
        "0501/nakamura_wirman/",
        "0102/newman/",
        "0102/pearce/",
        "0202/pearce/",
        "0301/pearce/",
        "0501/pearce/",
        "0401/rau/",
        "0101/ryan/",
        "0202/lugo/",
        "0202/smith/",
        "0102/squire/",
        "0302/taylor/",
        "0302/vanlooy/",
        "0301/walther/",
        "0401/whalen/",
        "0401/woods/",
        "0202/wright/",*/
        
        "1302/articles/eaarseth",
        "1001/articles/bryant_akerman_drell",
        "1401/articles/arjoranta",
        "0601/articles/arnseth",
        "0801/articles/hall_baird",
        "1001/articles/bartle",
        "0801/articles/barton",
        "1101/articles/begy_consalvo",
        "1102/articles/bjork",
        "1202/articles/bjork_book_review",
        "1201/articles/bogost_book_review",
        "0802/articles/zagal_bruckman",
        "1202/articles/the_algorithmic_experience",
        "0601/articles/consalvo_dutton",
        "0601/articles/cover",
        "1301/articles/depaoli_automatic_play",
        "1102/articles/deen",
        "1401/articles/dor",
        "0601/articles/dormans",
        "1101/articles/williams_nesbitt_eidels_elliott",
        "0902/articles/moeller_esplin_conway",
        "1102/articles/galbraith",
        "1101/articles/gazzard_alison",
        "1302/articles/agazzard",
        "1201/articles/tuur_ghys",
        "1103/articles/gibbons",
        "0601/articles/griebel",
        "1302/articles/guins_book_review",
        "1001/articles/ham",
        "0701/articles/harpold",
        "1001/articles/haynes",
        "0901/articles/interview_lena",
        "1202/articles/ui_mod_in_wow",
        "1103/articles/michael_hitchens",
        "1301/articles/hjorth_raising_the_stakes_review",
        "0901/articles/humphreys",
        "0801/articles/hutch",
        "0801/articles/hoeglund",
        "1401/articles/jhoeglund",
        "1202/articles/in_the_double_grip_of_the_game",
        "1101/articles/editorial_game_reward_systems",
        "1101/articles/jakobsson",
        "0802/articles/jorgensen",
        "1401/articles/kjorgensen",
        "1301/articles/karhulahti_kinesthetic_theory_of_the_videogame",
        "0801/articles/karlsen",
        "1201/articles/kirkpatrick",
        "1102/articles/klabbers_book_review",
        "0901/articles/klastrup",
        "0701/articles/elnasr_niedenthal_knez_almeida_zupko",
        "1201/articles/carly_kocurek",
        "1102/articles/konzack",
        "1102/articles/koskima",
        "0901/articles/lastowka",
        "1001/articles/lehdonvirta",
        "1102/articles/leino",
        "1202/articles/death_loop_as_a_feature",
        "1401/articles/lizardi",
        "0701/articles/malliet",
        "1103/articles/martin",
        "1101/articles/medler",
        "0801/articles/miller",
        "0601/articles/montfort",
        "1101/articles/moore",
        "1201/articles/gibbs_martin",
        "1202/articles/mortensen_book_review",
        "1202/articles/myers_book_review",
        "1001/articles/mayra",
        "0601/articles/ng",
        "1302/articles/nooney",
        "1301/articles/oldenburg_sonic_mechanics",
        "0801/articles/ouellette_m",
        "1302/articles/parisi",
        "1102/articles/paul",
        "0601/articles/paulk",
        "1001/articles/pearce_celia",
        "1302/articles/picard",
        "1102/articles/geoffrey_rockwell_kevin_kee",
        "0601/articles/rodriges",
        "0902/articles/ruch",
        "0802/articles/sample",
        "0902/articles/schulzke",
        "1401/articles/scullyblaker",
        "0802/articles/sicart",
        "1103/articles/sicart_ap",
        "0901/articles/simon_boudreau_silverman",
        "1102/articles/simon",
        "0701/articles/simons",
        "0601/articles/heide_smith",
        "0701/articles/smith",
        "0601/articles/parikka_suominen",
        "1301/articles/zagal_book_review",
        "1302/articles/trammell",
        "1001/articles/tronstad",
        "0802/articles/tyler",
        "1201/articles/veale",
        "0902/articles/voorhees",
        "1401/articles/gvoorhees",
        "0701/articles/wallin",
        "1001/articles/wanenchak",
        "0901/articles/hayot_wesp_space",
        "1102/articles/wharton_collins",
        "1101/articles/wilson",
        "1401/articles/wirman",
        "1102/articles/woods",
        "0901/articles/yee",
        "1102/articles/zagal",
        "1302/articles/svelch",

    };
    
    public static final String VENUE = "GameStudies";

    public static void main(String[] args) throws IOException, InterruptedException {
        // Initialize logger
        Logger mLogger = new Logger("logGameStudies", true);

        // Initialize self-wrapped mongocollection
        MyMongoCollection<Paper> mPapersCollection = new MyMongoCollection<Paper>(Globals.MONGODB_PAPERS_COLLECTION);

        MetaDataFactory mMetaDataFactory = new MetaDataFactory();

        for (String doi : dois) {
            try {
                Website mWebsite = mMetaDataFactory.getWebsite("io.metadata.GameStudiesLib", doi);

                mLogger.appendLines(doi, mWebsite.getTitle(), mWebsite.getAbstract(), mWebsite.getKeywords(), mWebsite.getAuthors(),
                        mWebsite.getYear(), "");

                Paper mPaper = new Paper().setTitle(mWebsite.getTitle()).setAbstraction(mWebsite.getAbstract())
                        .setKeywords(mWebsite.getKeywords()).setAuthors(mWebsite.getAuthors()).setYear(mWebsite.getYear()).setVenue(VENUE)
                        .setVenueType(Globals.VENUE_TYPE_CONFERENCE);

                // Bad formats at GameStudies. Need to modify documents manually.
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
            Thread.sleep((long) (Math.random() * 5000));
            System.out.println();

        } // for

    }

}
