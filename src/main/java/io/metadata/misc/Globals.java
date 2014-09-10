package io.metadata.misc;

/**
 * @author Zhengxing Chen
 *
 *  Save globals variables
 */
public class Globals {

    public static final String MONGODB_DBNAME = "metadata";
    public static final String MONGODB_PAPERS_COLLECTION = "papers";
    public static final String MONGODB_PAPERS_CLEAN_COL = "papers_clean";
    
    public static final String MONGODB_SERVER_ADDR = "129.10.76.16";
    public static final int MONGODB_PORT = 27017;

    public static final String USER_AGENT_VALUE = "Mozilla/5.0 (Windows NT 6.2; WOW64) AppleWebKit/537.36 (KHTML, like Gecko)"
            + " Chrome/27.0.1453.110 Safari/537.36 CoolNovo/2.0.9.20";
    public static final String USER_AGENT = "User-Agent";
    public static final int CONNECT_TIMEOUT = 5000;
    public static final int READ_TIMEOUT = 20000;
    public static final String GET_METHOD = "GET";
    
    public static final String VENUE_TYPE_CONFERENCE = "conference";
    public static final String VENUE_TYPE_JOURNAL = "journal";

}
