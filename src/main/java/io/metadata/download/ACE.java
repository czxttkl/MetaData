package io.metadata.download;

import io.metadata.MetaDataFactory;
import io.metadata.Website;

/**
 * ACE contains papers both from acm and springer.
 * @author Zhengxing Chen
 */
public class ACE {
    
    public static String[] acmDois = {
        // acm
        "1501750.1501871",
        "1690388.1690490",
        "1690388.1690490",
        "1178477.1178550",
        "1067343.1067351",
        "1690388.1690423",
        "2071423.2071508",
        "2071423.2071508",
        "1501750.1501784",
        "1501750.1501784",
        "1501750.1501805",
        "1501750.1501812",
        "1690388.1690451",
        "1690388.1690451",
        "1178477.1178505",
        "1255047.1255082",
        "1067343.1067378",
        "1178477.1178559",
        "1178477.1178518",
        "1690388.1690407",
        "2071423.2071480",
        "2071423.2071480",
        "1178823.1178871",
        "1690388.1690442",
        "1690388.1690424",
        "1690388.1690476",
        "1971630.1971669",
        "1690388.1690431",
        "1690388.1690479",
        "1690388.1690479",
        "1178823.1178852",
        "1501750.1501877",
        "1690388.1690411",
        "1690388.1690411",
        "1690388.1690403",
        "1178823.1178848",
        "1067343.1067361",
        "1501750.1501862",
        "1501750.1501862",
        "2071423.2071509",
        "2071423.2071509",
        "1178477.1178547",
        "1690388.1690419",
        "1178477.1178554",
        "1178477.1178583",
        "1178477.1178560",
        "1178823.1178905",
        "1690388.1690465",
        "2071423.2071434",
        "1501750.1501793",
        "1690388.1690415",
        "1501750.1501760",
        "1501750.1501754",
        "1690388.1690485",
        "1690388.1690485",
        "1178477.1178480",
        "1501750.1501872",
        "2071423.2071441",
        "1690388.1690456",
        "1178477.1178487",
        "1501750.1501780",
        "2071423.2071522",
        "1690388.1690500",
        "1690388.1690500",
        "1178823.1178865",
        "1690388.1690426",
        "1690388.1690426",
        "1255047.1255105",
        "1501750.1501798",
        "1501750.1501798",
        "1690388.1690499",
        "1690388.1690499",
        "1255047.1255068",
        "1178477.1178574",
        "2071423.2071458",
        "2071423.2071458",
        "1178477.1178555",
        "1501750.1501768",
        "1690388.1690488",
        "1178823.1178876",
        "1255047.1255081",
        "1690388.1690450",
        "1690388.1690450",
        "1501750.1501826",
        "1501750.1501818",
        "1971630.1971659",
        "1971630.1971659",
        "1178823.1178840",
        "1178823.1178899",
        "1255047.1255102",
        "1067343.1067357",
        "2071423.2071484",
        "2071423.2071484",
        "1067343.1067398",
        "1255047.1255049",
        "1067343.1067403",
        "1178823.1178904",
        "1178823.1178850",
        "2071423.2071436",
        "1690388.1690439",
        "2071423.2071479",
        "2071423.2071479",
        "1690388.1690427",
        "1690388.1690427",
        "1501750.1501766",
        "1501750.1501790",
        "1501750.1501807",
        "1501750.1501807",
        "2071423.2071505",
        "1971630.1971652",
        "1501750.1501837",
        "1501750.1501837",
        "1067343.1067377",
        "2071423.2071470",
        "1971630.1971641",
        "1690388.1690457",
        "2071423.2071531",
        "2071423.2071531",
        "1255047.1255111",
        "1178477.1178478",
        "1255047.1255073",
        "1178823.1178882",
        "1971630.1971634",
        "1501750.1501813",
        "1690388.1690441",
        "1178823.1178932",
        "1501750.1501775",
        "1501750.1501775",
        "1178823.1178894",
        "1690388.1690462",
        "1690388.1690462",
        "1178477.1178581",
        "1255047.1255052",
        "1690388.1690483",
        "1690388.1690483",
        "1255047.1255098",
        "2071423.2071516",
        "2071423.2071516",
        "1178477.1178571",
        "2071423.2071495",
        "2071423.2071495",
        "1255047.1255108",
        "1178477.1178526",
        "1501750.1501833",
        "1971630.1971672",
        "2071423.2071492",
        "2071423.2071507",
        "2071423.2071507",
        "1067343.1067353",
        "1178477.1178488",
        "1178477.1178536",
        "1690388.1690487",
        "1501750.1501855",
        "1501750.1501855",
        "2071423.2071502",
        "2071423.2071502",
        "1690388.1690432",
        "1690388.1690432",
        "1255047.1255124",
        "1501750.1501844",
        "1690388.1690399",
        "2071423.2071450",
        "2071423.2071450",
        "1067343.1067346",
        "1178823.1178930",
        "2071423.2071534",
        "1501750.1501765",
        "1501750.1501765",
        "2071423.2071468",
        "1501750.1501799",
        "1178823.1178879",
        "1178823.1178862",
        "1178477.1178525",
        "1178477.1178563",
        "1178477.1178508",
        "1501750.1501883",
        "2071423.2071449",
        "2071423.2071490",
        "1690388.1690484",
        "1501750.1501879",
        "1501750.1501879",
        "1690388.1690474",
        "2071423.2071519",
        "2071423.2071477",
        "1501750.1501835",
        "1501750.1501835",
        "2071423.2071432",
        "2071423.2071432",
        "1501750.1501868",
        "1690388.1690494",
        "1690388.1690494",
        "1690388.1690504",
        "1501750.1501846",
        "1971630.1971663",
        "1971630.1971663",
        "2071423.2071513",
        "2071423.2071513",
        "2071423.2071473",
        "1690388.1690497",
        "2071423.2071474",
        "1690388.1690496",
        "1690388.1690496",
        "1178823.1178945",
        "1178823.1178870",
        "1501750.1501763",
        "1501750.1501763",
        "1501750.1501859",
        "1501750.1501859",
        "1690388.1690428",
        "1690388.1690428",
        "1178823.1178944",
        "2071423.2071491",
        "2071423.2071491",
        "1178823.1178909",
        "1178823.1178859",
        "1178477.1178524",
        "1178823.1178830",
        "1501750.1501869",
        "1501750.1501811",
        "1690388.1690422",
        "1690388.1690422",
        "1178477.1178535",
        "1501750.1501878",
        "1501750.1501878",
        "1501750.1501851",
        "1501750.1501851",
        "1178477.1178491",
        "1501750.1501864",
        "1255047.1255091",
        "1255047.1255129",
        "1178477.1178482",
        "1501750.1501845",
        "1690388.1690414",
        "1501750.1501861",
        "1501750.1501861",
        "2071423.2071482",
        "1501750.1501849",
        "1971630.1971644",
        "1501750.1501827",
        "1501750.1501788",
        "1501750.1501788",
        "1178823.1178849",
        "1178477.1178516",
        "1067343.1067355",
        "2071423.2071535",
        "1690388.1690477",
        "1690388.1690477",
        "1178823.1178867",
        "1690388.1690444",
        "1690388.1690449",
        "1690388.1690449",
        "1690388.1690452",
        "2071423.2071440",
        "1178477.1178575",
        "2071423.2071457",
        "1501750.1501867",
        "1690388.1690445",
        "1690388.1690416",
        "1690388.1690416",
        "2071423.2071486",
        "1690388.1690473",
        "1690388.1690446",
        "2071423.2071532",
        "1501750.1501830",
        "1690388.1690501",
        "1501750.1501821",
        "1178823.1178834",
        "1255047.1255103",
        "1255047.1255128",
        "1067343.1067371",
        "1178477.1178511",
        "1067343.1067388",
        "1178823.1178881",
        "1178823.1178847",
        "1501750.1501876",
        "1690388.1690391",
        "1690388.1690455",
        "2071423.2071428",
        "2071423.2071428",
        "1501750.1501843",
        "1690388.1690478",
        "1690388.1690478",
        "1178823.1178920",
        "1255047.1255126",
        "1690388.1690430",
        "1690388.1690430",
        "1501750.1501783",
        "1501750.1501783",
        "1255047.1255115",
        "1178823.1178858",
        "1178477.1178479",
        "1255047.1255101",
        "1501750.1501752",
        "1690388.1690460",
        "1690388.1690481",
        "2071423.2071453",
        "1501750.1501776",
        "1971630.1971647",
        "1501750.1501853",
        "1971630.1971646",
        "1971630.1971646",
        "2071423.2071511",
        "1690388.1690410",
        "1178477.1178566",
        "1178477.1178568",
        "1690388.1690398",
        "1971630.1971642",
        "1971630.1971642",
        "1501750.1501770",
        "1501750.1501770",
        "1178477.1178573",
        "1501750.1501794",
        "1501750.1501794",
        "1690388.1690493",
        "1690388.1690493",
        "1178823.1178869",
        "1971630.1971656",
        "1501750.1501781",
        "1501750.1501781",
        "1971630.1971667",
        "1971630.1971667",
        "1178477.1178501",
        "1501750.1501841",
        "2071423.2071464",
        "2071423.2071464",
        "1178477.1178540",
        "1501750.1501764",
        "1501750.1501764",
        "1690388.1690408",
        "1690388.1690506",
        "1501750.1501758",
        "2071423.2071525",
        "1178477.1178517",
        "1255047.1255107",
        "1255047.1255117",
        "1501750.1501866",
        "1501750.1501774",
        "1501750.1501762",
        "1971630.1971654",
        "1971630.1971654",
        "1501750.1501822",
        "1501750.1501822",
        "1178823.1178900",
        "1501750.1501860",
        "1501750.1501860",
        "1971630.1971632",
        "2071423.2071503",
        "2071423.2071503",
        "1255047.1255142",
        "1971630.1971638",
        "1971630.1971638",
        "1690388.1690468",
        "1690388.1690480",
        "1690388.1690480",
        "1501750.1501874",
        "1501750.1501874",
        "2071423.2071437",
        "2071423.2071478",
        "2071423.2071478",
        "1971630.1971635",
        "1501750.1501779",
        "1501750.1501828",
        "2071423.2071442",
        "2071423.2071447",
        "2071423.2071500",
        "1690388.1690486",
        "1501750.1501789",
        "2071423.2071488",
        "1501750.1501792",
        "1501750.1501792",
        "2071423.2071430",
        "2071423.2071430",
        "1178477.1178561",
        "2071423.2071427",
        "1971630.1971660",
        "1690388.1690502",
        "1178477.1178486",
        "1255047.1255071",
        "2071423.2071459",
        "1971630.1971639",
        "1971630.1971639",
        "1690388.1690435",
        "1690388.1690503",
        "1690388.1690503",
        "1178823.1178897",
        "1255047.1255074",
        "2071423.2071527",
        "1971630.1971666",
        "1971630.1971666",
        "1255047.1255109",
        "2071423.2071514",
        "1971630.1971649",
        "1971630.1971649",
        "2071423.2071465",
        "1971630.1971664",
        "1690388.1690394",
        "1501750.1501870",
        "1501750.1501870",
        "1178477.1178493",
        "1501750.1501795",
        "1501750.1501795",
        "2071423.2071515",
        "2071423.2071524",
        "1501750.1501796",
        "1501750.1501796",
        "1690388.1690464",
        "2071423.2071530",
        "2071423.2071512",
        "2071423.2071512",
        "2071423.2071431",
        "1971630.1971657",
        "1971630.1971657",
        "1690388.1690420",
        "2071423.2071461",
        "1971630.1971671",
        "1501750.1501817",
        "1501750.1501817",
        "1501750.1501804",
        "1501750.1501804",
        "1501750.1501847",
        "1501750.1501847",
        "1971630.1971655",
        "1971630.1971655",
        "1501750.1501801",
        "1501750.1501801",
        "1067343.1067391",
        "1971630.1971661",
        "1971630.1971661",
        "1178823.1178946",
        "1067343.1067345",
        "2071423.2071526",
        "2071423.2071526",
        "1971630.1971645",
        "1501750.1501832",
        "2071423.2071501",
        "1690388.1690412",
        "2071423.2071510",
        "1178477.1178578",
        "1255047.1255106",
        "1501750.1501769",
        "1501750.1501839",
        "1690388.1690498",
        "1971630.1971633",
        "2071423.2071467",
        "2071423.2071443",
        "1690388.1690475",
        "1501750.1501785",
        "1690388.1690434",
        "1690388.1690495",
        "1690388.1690495",
        "1971630.1971673",
        "1971630.1971673",
        "2071423.2071475",
        "2071423.2071475",
        "1255047.1255131",
        "2071423.2071433",
        "1501750.1501809",
        "1501750.1501809",
        "1178477.1178567",
        "1501750.1501881",
        "1501750.1501881",
        "1178477.1178500",
        "1067343.1067356",
        "1067343.1067363",
        "1501750.1501755",
        "2071423.2071523",
        "1501750.1501810",
        "1501750.1501810",
        "2071423.2071460",
        "2071423.2071438",
        "1690388.1690436",
        "1690388.1690436",
        "1178823.1178873",
        "1178477.1178538",
        "1501750.1501865",
        "2071423.2071499",
        "2071423.2071499",
        "1971630.1971650",
        "1971630.1971650",
        "1690388.1690463",
        "1067343.1067395",
        "1178477.1178498",
        "1178823.1178837",
        "1971630.1971662",
        "1971630.1971662",
        "1067343.1067381",
        "2071423.2071448",
        "2071423.2071452",
        "1690388.1690392",
        "2071423.2071493",
        "2071423.2071493",
        "1690388.1690470",
        "2071423.2071444",
        "1690388.1690447",
        "1690388.1690447",
        "2071423.2071521",
        "2071423.2071521",
        "1971630.1971670",
        "1971630.1971670",
        "1501750.1501856",
        "2071423.2071483",
        "2071423.2071529",
        "1501750.1501753",
        "1501750.1501753",
        "1178477.1178507",
        "1067343.1067376",
        "2071423.2071496",
        "1501750.1501757",
        "1501750.1501757",
        "1178823.1178922",
        "1255047.1255058",
        "1255047.1255086",
        "1178823.1178892",
        "1501750.1501880",
        "1501750.1501880",
        "1178823.1178829",
        "1255047.1255100",
        "1501750.1501824",
        "2071423.2071463",
        "2071423.2071463",
        "2071423.2071528",
        "1971630.1971665",
        "1971630.1971665",
        "1178477.1178533",
        "1178823.1178917",
        "1690388.1690396",
        "1690388.1690396",
        "1178477.1178529",
        "1690388.1690471",
        "1690388.1690471",
        "1178823.1178907",
        "1178823.1178903",
        "1501750.1501852",
        "1971630.1971668",
        "1501750.1501873",
        "2071423.2071455",
        "1690388.1690440",
        "2071423.2071487",
        "2071423.2071487",
        "1501750.1501834",
        "1501750.1501834",
        "1501750.1501863",
        "1501750.1501863",
        "1067343.1067400",
        "1690388.1690390",
        "1501750.1501803",
        "1690388.1690454",
        "1501750.1501850",
        "1178477.1178527",
        "1178477.1178562",
        "1690388.1690507",
        "1690388.1690507",
        "1501750.1501806",
        "1501750.1501806",
        "1178477.1178585",
        "1501750.1501771",
        "1501750.1501882",
        "1067343.1067374",
        "1178823.1178863",
        "1501750.1501829",
        "1501750.1501829",
        "1067343.1067387",
        "1690388.1690437",
        "1501750.1501816",
        "1971630.1971640",
        "2071423.2071517",
        "1501750.1501759",
        "1501750.1501759",
        "1501750.1501800",
        "1501750.1501800",
        "1178823.1178838",
        "1690388.1690402",
        "1501750.1501838",
        "1501750.1501838",
        "1690388.1690406",
        "1690388.1690395",
        "1690388.1690395",
        "2071423.2071426",
        "2071423.2071426",
        "1178477.1178531",
        "2071423.2071469",
        "1690388.1690489",
        "1690388.1690489",
        "1178477.1178552",
        "1971630.1971653",
        "1501750.1501858",
        "1971630.1971637",
        "1501750.1501823",
        "1501750.1501823",
        "1255047.1255075",
        "1178823.1178833",
        "1690388.1690469",
        "1690388.1690469",
        "1690388.1690404",
        "2071423.2071506",
        "1501750.1501840",
        "1067343.1067396",
        "1255047.1255085",
        "1501750.1501814",
        "1501750.1501786",
        "1501750.1501786",
        "1690388.1690400",
        "1690388.1690400",
        "1690388.1690482",
        "2071423.2071446",
        "2071423.2071446",
        "1067343.1067393",
        "1690388.1690505",
        "1690388.1690505",
        "1255047.1255116",
        "1255047.1255141",
        "1178823.1178921",
        "2071423.2071454",
        "2071423.2071518",
        "2071423.2071518",
        "2071423.2071472",
        "2071423.2071472",
        "2071423.2071497",
        "2071423.2071497",
        "1178823.1178890",
        "1178823.1178927",
        "2071423.2071425",
        "1690388.1690459",
        "1501750.1501819",
        "1501750.1501778",
        "1690388.1690492",
        "1690388.1690492",
        "1690388.1690418",
        "2071423.2071533",
        "2071423.2071533",
        "1501750.1501773",
        "1690388.1690466",
    };
    
    public static String[] springerDois = {
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
            
    public static void main(String[] args) throws Exception {
        MetaDataFactory mMetaDataFactory = new MetaDataFactory();
        
        for (String doi : acmDois) {
            System.out.println(doi);
            Website mWebsite = mMetaDataFactory.getWebsite("io.metadata.ACM", doi);
            System.out.println(mWebsite.getTitle());
            System.out.println(mWebsite.getAbstract());
            // Note: some papers don't have keywords.
            System.out.println(mWebsite.getKeywords());
            System.out.println(mWebsite.getAuthors());
            System.out.println(mWebsite.getYear());
            // ACM forbids robotic process
            Thread.sleep((long) (10000 + Math.random() * 10000));
            System.out.println();
        }
        
        for (String doi : springerDois) {
            System.out.println(doi);
            Website mWebsite = mMetaDataFactory.getWebsite("io.metadata.Springer", doi);
            System.out.println(mWebsite.getTitle());
            System.out.println(mWebsite.getAbstract());
            // Note: some papers don't have keywords.
            System.out.println(mWebsite.getKeywords());
            System.out.println(mWebsite.getAuthors());
            System.out.println(mWebsite.getYear());
            Thread.sleep((long) (10000 + Math.random() * 10000));
            System.out.println();
        }
        
    }

}
