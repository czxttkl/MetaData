package io.metadata.download;

import java.io.IOException;

import io.metadata.MetaDataFactory;
import io.metadata.Website;

/**
 * Created by Zhengxing Chen.
 */
public class CGames {

    public static long[] dois = {
        // 2013
        6632597,
        6632599,
        6632601,
        6632600,
        6632602,
        6632604,
        6632598,
        6632605,
        6632606,
        6632608,
        6632609,
        6632610,
        6632611,
        6632613,
        6632615,
        6632614,
        6632616,
        6632617,
        6632618,
        6632619,
        6632621,
        6632622,
        6632623,
        6632624,
        6632625,
        6632626,
        6632628,
        6632629,
        6632630,
        6632631,
        6632634,
        6632632,
        6632633,
        6632639,
        6632635,
        6632636,
        6632637,
               
        // 2012,
        6314544,
        6314545,
        6314546,
        6314547,
        6314548,
        6314549,
        6314550,
        6314551,
        6314552,
        6314553,
        6314554,
        6314555,
        6314556,
        6314557,
        6314558,
        6314559,
        6314560,
        6314561,
        6314562,
        6314563,
        6314564,
        6314565,
        6314566,
        6314567,
        6314568,
        6314569,
        6314570,
        6314571,
        6314572,
        6314573,
        6314574,
        6314575,
        6314576,
        6314577,
        6314578,
        6314579,
        6314580,
        6314581,
        6314582,
        6314583,
        6314584,
               
        // 2011,
        6000346,
        6000347,
        6000348,
        6000349,
        6000350,
        6000351,
        6000353,
        6000354,
        6000355,
        6000319,
        6000330,
        6000341,
        6000352,
        6000357,
        6000358,
        6000359,
        6000360,
        6000361,
        6000320,
        6000321,
        6000322,
        6000323,
        6000324,
        6000325,
        6000326,
        6000327,
        6000328,
        6000329,
        6000331,
        6000332,
        6000333,
        6000334,
        6000335,
        6000336,
        6000337,
        6000338,
        6000339,
        6000340,
        6000342,
        6000343,
        6000344,
        6000345,
        6000356,
    };
    
    public static void main(String[] args) throws IOException {
        MetaDataFactory mMetaDataFactory = new MetaDataFactory();
        for (long doi : dois) {
            System.out.println(doi);
            Website mWebsite = mMetaDataFactory.getWebsite("IEEEXplore", doi);
            System.out.println(mWebsite.getTitle());
            System.out.println(mWebsite.getAbstract());
            System.out.println(mWebsite.getKeywords());
            System.out.println();
        }
    }
}
