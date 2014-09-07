package io.metadata.download.journal;

import io.metadata.MetaDataFactory;
import io.metadata.Website;
import io.metadata.misc.Globals;
import io.metadata.misc.Logger;
import io.metadata.orm.MyMongoCollection;
import io.metadata.orm.Paper;

import java.io.IOException;

/**
 * Crawl data for International Journal of Computer Game Technology.
 * @author Zhengxing Chen
 *
 */
public class IJCGT {

    public static String[] dois = {
        "/2014/787968/",
        "/2014/781950/",
        "/2014/817167/",
        "/2014/485019/",
        "/2014/138596/",
        "/2014/382396/",
        "/2014/249721/",
        "/2014/463489/",
        "/2014/602403/",
        "/2014/623809/",
        "/2013/371374/",
        "/2013/363160/",
        "/2013/904154/",
        "/2013/674848/",
        "/2013/891431/",
        "/2013/851287/",
        "/2013/578269/",
        "/2013/603604/",
        "/2013/170914/",
        "/2012/640725/",
        "/2012/103710/",
        "/2012/494232/",
        "/2012/969785/",
        "/2012/929814/",
        "/2012/625476/",
        "/2012/724917/",
        "/2012/178578/",
        "/2012/596953/",
        "/2011/612535/",
        "/2011/706893/",
        "/2011/819746/",
        "/2011/282345/",
        "/2011/857371/",
        "/2011/164949/",
        "/2011/920873/",
        "/2011/834026/",
        "/2011/570210/",
        "/2011/107160/",
        "/2011/495437/",
        "/2010/897217/",
        "/2010/512563/",
        "/2010/613931/",
        "/2010/624817/",
        "/2010/979235/",
        "/2010/578784/",
        "/2010/360458/",
        "/2010/536480/",
        "/2009/530367/",
        "/2009/375905/",
        "/2009/713584/",
        "/2009/323095/",
        "/2009/726496/",
        "/2009/318505/",
        "/2009/397938/",
        "/2009/456763/",
        "/2009/627109/",
        "/2009/970617/",
        "/2009/609350/",
        "/2009/231863/",
        "/2009/573924/",
        "/2009/730894/",
        "/2009/521020/",
        "/2009/572030/",
        "/2009/693267/",
        "/2009/470590/",
        "/2009/952524/",
        "/2009/890917/",
        "/2009/462315/",
        "/2009/251652/",
        "/2009/837095/",
        "/2009/472672/",
        "/2009/125714/",
        "/2009/162450/",
        "/2009/129075/",
        "/2009/745219/",
        "/2009/670459/",
        "/2009/456169/",
        "/2009/910819/",
        "/2009/436732/",
        "/2008/186941/",
        "/2008/928712/",
        "/2008/594313/",
        "/2008/906931/",
        "/2008/623725/",
        "/2008/327387/",
        "/2008/192153/",
        "/2008/642314/",
        "/2008/873913/",
        "/2008/739041/",
        "/2008/371872/",
        "/2008/243107/",
        "/2008/783231/",
        "/2008/316790/",
        "/2008/619108/",
        "/2008/470350/",
        "/2008/378485/",
        "/2008/834616/",
        "/2008/321708/",
        "/2008/542918/",
        "/2008/176056/",
        "/2008/751268/",
        "/2008/412056/",
        "/2008/753584/",
        "/2008/216784/",
        "/2008/720280/",
        "/2008/432365/",
        "/2008/539078/",
        "/2008/135398/",
        "/2008/281959/",
        "/2008/178923/",
    };
    
    public static final String VENUE = "IJCGT";

    public static void main(String[] args) throws IOException, InterruptedException {
        // Initialize logger
        Logger mLogger = new Logger("logIJCGT", true);

        // Initialize self-wrapped mongocollection
        MyMongoCollection<Paper> mPapersCollection = new MyMongoCollection<Paper>(Globals.MONGODB_PAPERS_COLLECTION);

        MetaDataFactory mMetaDataFactory = new MetaDataFactory();

        for (String doi : dois) {
            try {
                Website mWebsite = mMetaDataFactory.getWebsite("io.metadata.IJCGTLib", doi);

                mLogger.appendLines(doi, mWebsite.getTitle(), mWebsite.getAbstract(), mWebsite.getKeywords(), mWebsite.getAuthors(),
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
            Thread.sleep((long) (Math.random() * 60000));
            System.out.println();

        } // for

    }
}