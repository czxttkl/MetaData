package io.metadata.download.undone;

import io.metadata.MetaDataFactory;
import io.metadata.Website;

/**
 * @author Zhengxing Chen
 */
public class CIG {
    
    public static long[] dois = {
        6633608,
        6633610,
        6633611,
        6633609,
        6633612,
        6633613,
        6633614,
        6633615,
        6633616,
        6633618,
        6633617,
        6633620,
        6633619,
        6633621,
        6633622,
        6633623,
        6633624,
        6633625,
        6633626,
        6633627,
        6633629,
        6633628,
        6633630,
        6633632,
        6633631,
        6633633,
        6633634,
        6633635,
        6633636,
        6633637,
        6633638,
        6633639,
        6633640,
        6633641,
        6633642,
        6633643,
        6633644,
        6633646,
        6633645,
        6633648,
        6633649,
        6633650,
        6633651,
        6633652,
        6633653,
        6633654,
        6633655,
        6633647,
        6633656,
        6633657,
        6633658,
        6633660,
        6633659,
        6633661,
        6633662,
        6633663,
        6633664,
        6633665,
        6633666,
        6633667,
        6374131,
        6374132,
        6374133,
        6374138,
        6374137,
        6374134,
        6374136,
        6374135,
        6374139,
        6374140,
        6374142,
        6374141,
        6374143,
        6374145,
        6374144,
        6374146,
        6374147,
        6374148,
        6374150,
        6374151,
        6374149,
        6374152,
        6374153,
        6374155,
        6374154,
        6374156,
        6374157,
        6374158,
        6374159,
        6374160,
        6374161,
        6374168,
        6374162,
        6374163,
        6374164,
        6374167,
        6374165,
        6374166,
        6374169,
        6374170,
        6374171,
        6374172,
        6374173,
        6374174,
        6374176,
        6374175,
        6374177,
        6374178,
        6374179,
        6374180,
        6374181,
        6374182,
        6374183,
        6374184,
        6374185,
        6374186,
        6374187,
        6374188,
        6031997,
        6031975,
        6031976,
        6031983,
        6031981,
        6031982,
        6031984,
        6031985,
        6031986,
        6031987,
        6031988,
        6031990,
        6031989,
        6031992,
        6031991,
        6031993,
        6031994,
        6031995,
        6031996,
        6031998,
        6032002,
        6032001,
        6031999,
        6032003,
        6032000,
        6032006,
        6032010,
        6032004,
        6032011,
        6032005,
        6032008,
        6032013,
        6032012,
        6032007,
        6032009,
        6032014,
        6032016,
        6032015,
        6032017,
        6032018,
        6032020,
        6032019,
        6032022,
        6032021,
        6032024,
        6032026,
        6032023,
        6032027,
        6032025,
        5593386,
        5593384,
        5593381,
        5593380,
        5593375,
        5593376,
        5593377,
        5593378,
        5593373,
        5593374,
        5593368,
        5593369,
        5593367,
        5593370,
        5593365,
        5593371,
        5593372,
        5286512,
        5286510,
        5286511,
        5286506,
        5286508,
        5286509,
        5286513,
        5286500,
        5286505,
        5286501,
        5286507,
        5286502,
        5286504,
        5286496,
        5286497,
        5286499,
        5286498,
        5035614,
        5035615,
        5035617,
        5035618,
        5035619,
        5035616,
        5035620,
        5035625,
        5035622,
        5035624,
        5035623,
        5035626,
        5035621,
        4219017,
        4219020,
        4219018,
        4219023,
        4219022,
        4219019,
        4219025,
        4219027,
        4219024,
        4219028,
        4219029,
        4219030,
        4219031,
        4219032,
        4219033,
        4219038,
        4219036,
        4219035,
        4219034,
        4219037,
        4219026,
        4100102,
        4100103,
        4100104,
        4100105,
        4100106,
        4100108,
        4100107,
        4100109,
        4100110,
        4100112,
        4100113,
        4100111,
        4100114,
        4100115,
        4100116,
        4100117,
        5593366,
        5593360,
        5593364,
        5593363,
        5593362,
        5593359,
        5593353,
        5593361,
        5593358,
        5593357,
        5593355,
        5593356,
        5593354,
        5593351,
        5593352,
        5593349,
        5593350,
        5593347,
        5593343,
        5593345,
        5593346,
        5593344,
        5593341,
        5593342,
        5593348,
        5593339,
        5593337,
        5593327,
        5593329,
        5593325,
        5593328,
        5593336,
        5593335,
        5593338,
        5593340,
        5593333,
        5593334,
        5593321,
        5593331,
        5593332,
        5593319,
        5593323,
        5593326,
        5593324,
        5593320,
        5593318,
        5286494,
        5286491,
        5286492,
        5286495,
        5286490,
        5286488,
        5286485,
        5286486,
        5286493,
        5286487,
        5286481,
        5286484,
        5286489,
        5286480,
        5286483,
        5286482,
        5286477,
        5286479,
        5286475,
        5286478,
        5286476,
        5286474,
        5286470,
        5286472,
        5286473,
        5286471,
        5286468,
        5286469,
        5286464,
        5286466,
        5286467,
        5286465,
        5286460,
        5286461,
        5286462,
        5286463,
        5286458,
        5286459,
        5286456,
        5286457,
        5286454,
        5286451,
        5286452,
        5286450,
        5286455,
        5286453,
        5035627,
        5035630,
        5035631,
        5035629,
        5035634,
        5035633,
        5035632,
        5035635,
        5035636,
        5035637,
        5035641,
        5035638,
        5035639,
        5035640,
        5035642,
        5035646,
        5035644,
        5035645,
        5035643,
        5035650,
        5035649,
        5035648,
        5035647,
        5035651,
        5035652,
        5035653,
        5035656,
        5035657,
        5035658,
        5035659,
        5035655,
        5035660,
        5035662,
        5035667,
        5035661,
        5035663,
        5035664,
        5035665,
        5035654,
        5035666,
        4219039,
        4219040,
        4219044,
        4219041,
        4219042,
        4219043,
        4219045,
        4219049,
        4219046,
        4219047,
        4219048,
        4219050,
        4219051,
        4219052,
        4219053,
        4219054,
        4219057,
        4219056,
        4219055,
        4219059,
        4219058,
        4219062,
        4219061,
        4219060,
        4219063,
        4219064,
        4219067,
        4219065,
        4219066,
        4219068,
        4219069,
        4100119,
        4100118,
        4100123,
        4100131,
        4100126,
        4100129,
        4100128,
        4100121,
        4100120,
        4100124,
        4100122,
        4100125,
        4100130,
        4100133,
        4100134,
        4100137,
        4100138,
        4100135,
        4100132,
        4100136,
        4100139,
    };
    
    public static void main(String[] args) throws Exception {
        MetaDataFactory mMetaDataFactory = new MetaDataFactory();
        for (long doi : dois) {
            System.out.println(doi);
            Website mWebsite = mMetaDataFactory.getWebsite("io.metadata.IEEEXplore", doi);
            System.out.println(mWebsite.getTitle());
            System.out.println(mWebsite.getAbstract());
            System.out.println(mWebsite.getKeywords());
            System.out.println(mWebsite.getAuthors());
            System.out.println(mWebsite.getYear());
            System.out.println();
            // Anti-robotics
            Thread.sleep((long) (10000 + Math.random() * 10000));
            System.out.println();
        }
    }

}
