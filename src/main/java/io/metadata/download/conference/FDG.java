package io.metadata.download.conference;

import io.metadata.MetaDataFactory;
import io.metadata.Website;
import io.metadata.misc.Globals;
import io.metadata.misc.Logger;
import io.metadata.orm.MyMongoCollection;
import io.metadata.orm.Paper;

/**
 * @author Zhengxing Chen
 *
 */
public class FDG {

    public static String[] dois = {
        "1536513.1536529",
        "1536513.1536534",
        "1822348.1822359",
        "2159365.2159394",
        "1822348.1822365",
        "2159365.2159387",
        "1822348.1822384",
        "2159365.2159376",
        "1822348.1822370",
        "1536513.1536522",
        "2282338.2282361",
        "1536513.1536542",
        "2159365.2159403",
        "2282338.2282364",
        "1536513.1536557",
        "2282338.2282339",
        "2282338.2282390",
        "2282338.2282392",
        "2282338.2282343",
        "1536513.1536580",
        "1822348.1822357",
        "2282338.2282394",
        "1536513.1536515",
        "1536513.1536527",
        "2282338.2282350",
        "1822348.1822352",
        "2282338.2282404",
        "2282338.2282382",
        "1536513.1536547",
        "1536513.1536525",
        "2159365.2159382",
        "1822348.1822355",
        "2282338.2282393",
        "2159365.2159377",
        "2159365.2159408",
        "2282338.2282352",
        "1822348.1822366",
        "1536513.1536578",
        "1536513.1536523",
        "1536513.1536531",
        "1536513.1536533",
        "2282338.2282389",
        "1536513.1536576",
        "2159365.2159383",
        "2282338.2282346",
        "2282338.2282366",
        "1822348.1822381",
        "2282338.2282370",
        "2282338.2282363",
        "2159365.2159404",
        "2159365.2159409",
        "1822348.1822371",
        "1536513.1536551",
        "2159365.2159391",
        "1822348.1822387",
        "2159365.2159372",
        "1822348.1822380",
        "1536513.1536572",
        "1536513.1536559",
        "1822348.1822374",
        "2282338.2282388",
        "2282338.2282359",
        "1536513.1536517",
        "1536513.1536564",
        "2282338.2282374",
        "1536513.1536550",
        "2159365.2159425",
        "2159365.2159400",
        "2282338.2282383",
        "1536513.1536581",
        "2282338.2282395",
        "1822348.1822368",
        "1536513.1536554",
        "1536513.1536539",
        "1536513.1536567",
        "2282338.2282386",
        "2159365.2159421",
        "1536513.1536543",
        "2282338.2282356",
        "2159365.2159393",
        "1536513.1536530",
        "2282338.2282348",
        "2282338.2282384",
        "2159365.2159399",
        "2282338.2282351",
        "1822348.1822375",
        "1822348.1822385",
        "2282338.2282380",
        "1822348.1822377",
        "2159365.2159422",
        "2159365.2159379",
        "1536513.1536565",
        "1822348.1822363",
        "2282338.2282347",
        "2159365.2159420",
        "1536513.1536574",
        "2159365.2159371",
        "1822348.1822373",
        "2159365.2159415",
        "2159365.2159419",
        "2282338.2282403",
        "2159365.2159370",
        "1822348.1822367",
        "2159365.2159390",
        "2159365.2159384",
        "2159365.2159424",
        "2159365.2159427",
        "2159365.2159407",
        "1822348.1822379",
        "1822348.1822358",
        "2282338.2282368",
        "2282338.2282345",
        "2282338.2282371",
        "1536513.1536545",
        "2159365.2159392",
        "1536513.1536528",
        "2282338.2282355",
        "2159365.2159374",
        "1822348.1822354",
        "1536513.1536540",
        "2282338.2282360",
        "1536513.1536577",
        "2159365.2159366",
        "2159365.2159423",
        "1536513.1536537",
        "2159365.2159386",
        "2282338.2282387",
        "1536513.1536526",
        "2159365.2159418",
        "1536513.1536538",
        "1822348.1822378",
        "1536513.1536520",
        "2282338.2282357",
        "1822348.1822386",
        "1822348.1822361",
        "2159365.2159397",
        "1822348.1822383",
        "2282338.2282397",
        "1822348.1822364",
        "2159365.2159398",
        "2159365.2159378",
        "2159365.2159368",
        "1536513.1536558",
        "1536513.1536544",
        "2159365.2159429",
        "1536513.1536569",
        "1536513.1536536",
        "1822348.1822391",
        "2282338.2282401",
        "2159365.2159413",
        "2282338.2282379",
        "1536513.1536560",
        "2159365.2159410",
        "2159365.2159402",
        "1536513.1536556",
        "2282338.2282400",
        "1822348.1822356",
        "2282338.2282365",
        "2282338.2282396",
        "1536513.1536566",
        "1822348.1822389",
        "1822348.1822390",
        "2282338.2282385",
        "1536513.1536541",
        "2159365.2159388",
        "2159365.2159396",
        "2159365.2159412",
        "1536513.1536552",
        "2282338.2282373",
        "1536513.1536562",
        "2282338.2282378",
        "2282338.2282340",
        "2282338.2282353",
        "1536513.1536563",
        "2282338.2282398",
        "1536513.1536575",
        "2282338.2282406",
        "1536513.1536532",
        "1822348.1822349",
        "1822348.1822350",
        "2159365.2159375",
        "1536513.1536549",
        "2159365.2159417",
        "2159365.2159428",
        "1536513.1536561",
        "1536513.1536521",
        "1822348.1822388",
        "2159365.2159406",
        "1822348.1822351",
        "1536513.1536516",
        "2282338.2282375",
        "2159365.2159416",
        "2159365.2159381",
        "2282338.2282405",
        "1822348.1822382",
        "1536513.1536570",
        "1536513.1536546",
        "1822348.1822369",
        "2159365.2159380",
        "1822348.1822372",
        "2159365.2159426",
        "1536513.1536579",
        "2282338.2282376",
        "1536513.1536518",
        "1536513.1536548",
        "2282338.2282342",
        "2282338.2282402",
        "1536513.1536535",
        "2159365.2159385",
        "2159365.2159369",
        "2159365.2159395",
        "1822348.1822360",
        "1536513.1536571",
        "2159365.2159373",
        "2159365.2159389",
        "2282338.2282369",
        "2159365.2159401",
        "1822348.1822353",
        "1536513.1536553",
        "2159365.2159414",
        "1822348.1822376",
        "2159365.2159405",
        "1822348.1822362",
        "2159365.2159367",
        "2159365.2159411",
        
        // 2014
        //http://www.fdg2014.org/proceedings.html
        
        // 2013
        /*http://www.fdg2013.org/program/doctoral/dc01_barik.pdf
          http://www.fdg2013.org/program/papers/paper16_sturn_etal.pdf
          http://www.fdg2013.org/program/papers/paper34_barendregt_vonfeilitzen.pdf
          http://www.fdg2013.org/program/papers/paper38_fua_etal.pdf
          http://www.fdg2013.org/program/papers/paper24_zhao.pdf
          http://www.fdg2013.org/program/papers/paper30_tremblay_verbrugge.pdf
          http://www.fdg2013.org/program/posters/poster21_yu_riedl.pdf
          http://www.fdg2013.org/program/doctoral/dc05_karhulahti.pdf
          http://www.fdg2013.org/program/papers/short01_font_etal.pdf
          http://www.fdg2013.org/program/papers/paper28_liapis_etal.pdf
          http://www.fdg2013.org/program/papers/paper37_campos_etal.pdf
          http://www.fdg2013.org/program/preface.pdf
          http://www.fdg2013.org/program/papers/paper22_tekofsky_etal.pdf
          http://www.fdg2013.org/program/festival/gamesforhealth.pdf
          http://www.fdg2013.org/program/doctoral/dc03_carter.pdf
          http://www.fdg2013.org/program/papers/paper10_marczak_etal.pdf
          http://www.fdg2013.org/program/papers/paper44_shannon_etal.pdf
          http://www.fdg2013.org/program/posters/poster20_warpefelt_straat.pdf
          http://www.fdg2013.org/program/papers/paper26_gaudl_etal.pdf
          http://www.fdg2013.org/program/papers/paper46_zorn_etal.pdf
          http://www.fdg2013.org/program/papers/short06_eagle_etal.pdf
          http://www.fdg2013.org/program/posters/poster07_ksuz_etal.pdf
          http://www.fdg2013.org/program/papers/paper39_greenspan_whitson.pdf
          http://www.fdg2013.org/program/posters/poster13_reer_kraemer.pdf
          http://www.fdg2013.org/program/papers/paper20_emmerich_masuch.pdf
          http://www.fdg2013.org/program/posters/poster01_christinaki_etal.pdf
          http://www.fdg2013.org/program/papers/paper19_ekman.pdf
          http://www.fdg2013.org/program/papers/paper42_molnar_kostkova.pdf
          http://www.fdg2013.org/program/papers/paper17_toprak_etal.pdf
          http://www.fdg2013.org/program/papers/paper33_wallner.pdf
          http://www.fdg2013.org/program/doctoral/dc09_newon.pdf
          http://www.fdg2013.org/program/doctoral/dc08_murray.pdf
          http://www.fdg2013.org/program/papers/paper14_milam_etal.pdf
          http://www.fdg2013.org/program/doctoral/dc04_holmgard.pdf
          http://www.fdg2013.org/program/posters/poster02_degens_etal.pdf
          http://www.fdg2013.org/program/papers/paper41_maung_etal.pdf
          http://www.fdg2013.org/program/papers/paper13_mccoy_etal.pdf
          http://www.fdg2013.org/program/doctoral/dc14_toprak.pdf
          http://www.fdg2013.org/program/posters/poster15_ruggiero.pdf
          http://www.fdg2013.org/program/papers/paper36_waern.pdf
          http://www.fdg2013.org/program/papers/paper04_myers.pdf
          http://www.fdg2013.org/program/papers/paper01_bjork.pdf
          http://www.fdg2013.org/program/posters/poster03_cabezas_thompson.pdf
          http://www.fdg2013.org/program/papers/paper03_moering.pdf
          http://www.fdg2013.org/program/posters/poster09_linssen_degroot.pdf
          http://www.fdg2013.org/program/papers/paper31_caplar_etal.pdf
          http://www.fdg2013.org/program/posters/poster11_nielsen.pdf
          http://www.fdg2013.org/program/papers/paper06_zagal_etal.pdf
          http://www.fdg2013.org/program/papers/paper05_vella.pdf
          http://www.fdg2013.org/program/festival/openrelativity.pdf
          http://www.fdg2013.org/program/papers/paper40_marklund_etal.pdf
          http://www.fdg2013.org/program/posters/poster14_rijnboutt_etal.pdf
          http://www.fdg2013.org/program/festival/sightlence.pdf
          http://www.fdg2013.org/program/festival/simula.pdf
          http://www.fdg2013.org/program/doctoral/dc15_warpefelt.pdf
          http://www.fdg2013.org/program/papers/paper02_calleja.pdf
          http://www.fdg2013.org/program/papers/paper35_ryan.pdf
          http://www.fdg2013.org/program/doctoral/dc07_milik.pdf
          http://www.fdg2013.org/program/posters/poster16_shepherd_etal.pdf
          http://www.fdg2013.org/program/posters/poster06_knight_etal.pdf
          http://www.fdg2013.org/program/posters/poster10_lopes_etal.pdf
          http://www.fdg2013.org/program/festival/movmote.pdf
          http://www.fdg2013.org/program/doctoral/dc11_rentschler.pdf
          http://www.fdg2013.org/program/papers/paper09_eklund_bergmark.pdf
          http://www.fdg2013.org/program/papers/short07_schoenaufog_etal.pdf
          http://www.fdg2013.org/program/papers/paper43_ryan_charsky.pdf
          http://www.fdg2013.org/program/papers/paper27_koutnik_etal.pdf
          http://www.fdg2013.org/program/papers/paper11_voulgari_komis.pdf
          http://www.fdg2013.org/program/papers/short02_herrewijn_etal.pdf
          http://www.fdg2013.org/program/posters/poster12_pace_thompson.pdf
          http://www.fdg2013.org/program/doctoral/dc06_marklund.pdf
          http://www.fdg2013.org/program/doctoral/dc13_serra.pdf
          http://www.fdg2013.org/program/festival/networknightmares.pdf
          http://www.fdg2013.org/program/papers/paper08_consalvo_paul.pdf
          http://www.fdg2013.org/program/papers/paper21_orourke_etal.pdf
          http://www.fdg2013.org/program/posters/poster19_vlachoukonchylaki_vassos.pdf
          http://www.fdg2013.org/program/posters/poster04_janssen_etal.pdf
          http://www.fdg2013.org/program/posters/poster17_stiegler_livingstone.pdf
          http://www.fdg2013.org/program/papers/paper29_smith_etal.pdf
          http://www.fdg2013.org/program/posters/poster05_khaled_yannakakis.pdf
          http://www.fdg2013.org/program/papers/paper23_vandenabeele_etal.pdf
          http://www.fdg2013.org/program/festival/beadloom.pdf
          http://www.fdg2013.org/program/papers/paper18_burelli.pdf
          http://www.fdg2013.org/program/papers/short04_koutsouras_cairns.pdf
          http://www.fdg2013.org/program/doctoral/dc02_battaglino.pdf
          http://www.fdg2013.org/program/doctoral/dc10_rapp.pdf
          http://www.fdg2013.org/program/papers/paper32_drachen_etal.pdf
          http://www.fdg2013.org/program/doctoral/dc12_reuter.pdf
          http://www.fdg2013.org/program/papers/paper25_bauer_etal.pdf
          http://www.fdg2013.org/program/posters/poster08_li_etal.pdf
          http://www.fdg2013.org/program/festival/anygammon.pdf
          http://www.fdg2013.org/program/papers/short03_james_etal.pdf
          http://www.fdg2013.org/program/papers/paper15_shi_crawfis.pdf
          http://www.fdg2013.org/program/papers/short05_llanso_etal.pdf
          http://www.fdg2013.org/program/festival/moversandshakers.pdf
          http://www.fdg2013.org/program/posters/poster18_togelius_friberger.pdf
          http://www.fdg2013.org/program/papers/paper45_wagner_wernbacher.pdf
          http://www.fdg2013.org/program/papers/paper12_zagal_tomuro.pdf
          http://www.fdg2013.org/program/papers/paper07_carter_gibbs.pdf
*/
    };
    
    public static final String VENUE = "FDG";
   
    public static void main(String[] args) throws Exception {
        // Initialize logger
        Logger mLogger = new Logger("logFDG", true);

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

        } // for
    }

}
