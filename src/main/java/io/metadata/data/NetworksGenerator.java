package io.metadata.data;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bson.types.ObjectId;
import org.jongo.MongoCursor;

import io.metadata.misc.Globals;
import io.metadata.misc.Utils;
import io.metadata.misc.Utils.KeyCountMap;
import io.metadata.orm.MyMongoCollection;
import io.metadata.orm.Paper;

/**
 * Generate Network data as input for Yizhou's code.
 * @author Zhengxing Chen
 *
 */
public class NetworksGenerator {

    public static void main(String[] args) throws IOException {
        // Connect to mongodb papers_clean
        MyMongoCollection<Paper> mPapersColCln = new MyMongoCollection<Paper>(Globals.MONGODB_PAPERS_CLEAN_COL);
        
        // create paper-id pairs
        List<ObjectId> paperIdsList = mPapersColCln.getCollection().distinct("_id").as(ObjectId.class);
        Map<String, Long> paperIdMap = constructPaperIdMap(paperIdsList);
        
        // create venue-id pairs
        List<String> venueValuesList = mPapersColCln.getCollection().distinct("venue").as(String.class);
        Map<String, Integer> venueValueIdMap = constructStringIdMap(venueValuesList);

        // create author-id pairs
        List<String> authorValuesList = mPapersColCln.getCollection().distinct("authors").as(String.class);
        Map<String, Integer> authorValueIdMap = constructStringIdMap(authorValuesList);

        // create keyword-id pairs
        List<String> keywordValuesList = mPapersColCln.getCollection().distinct("keywords").as(String.class);
        Map<String, Integer> keywordValueIdMap = constructStringIdMap(keywordValuesList);
        
        for (int i = 2000; i <= 2013; i++) {
            // create dir for network dict files in this year
            File ntwDir = new File("networks/" + i);
            if (!ntwDir.exists()) {
                ntwDir.mkdirs();
            }

            // count appearances of venues, authors and keywords
            KeyCountMap venueCountMap = new KeyCountMap();
            KeyCountMap authorCountMap = new KeyCountMap();
            KeyCountMap keywordCountMap = new KeyCountMap();

            // get all papers in a particular year
            String yearQuery = "{year:" + i + "}";
            MongoCursor<Paper> thisYearPapers = mPapersColCln.getCollection().find(yearQuery).as(Paper.class);
            
            // Prepare to write to network.tsr in year folder
            PrintWriter pwNetworkTsr = new PrintWriter("networks/" + i + "/network.tsr");

            for (Paper paper : thisYearPapers) {
                // Temporary KeyCountMap only for this paper
                // Count how many times authors and keywords appear only in this paper
                KeyCountMap authorCountMapTmp = new KeyCountMap();
                KeyCountMap keywordCountMapTmp = new KeyCountMap();
                
                // count venue for each paper
                venueCountMap.addCount(paper.getVenue());
                
                // count keyword for each paper
                for (String kw : paper.getKeywords()) {
                   keywordCountMap.addCount(kw);
                   keywordCountMapTmp.addCount(kw);
                }
                
                // count author for each paper
                for (String ath : paper.getAuthors()) {
                    authorCountMap.addCount(ath);
                    authorCountMapTmp.addCount(ath);
                }

                // print to network.tsr
                pwNetworkTsr.println(String.format("%d     0       %d      1", paperIdMap.get(paper.getId()), venueValueIdMap.get(paper.getVenue())));
                
                for (String ath : paper.getAuthors()) {
                    String line = String.format("%d     1       %d      %d", paperIdMap.get(paper.getId()), authorValueIdMap.get(ath), authorCountMapTmp.get(ath));
                    pwNetworkTsr.println(line);
                }

                for (String kw : paper.getKeywords()) {
                    String line = String.format("%d     2       %d      %d", paperIdMap.get(paper.getId()), keywordValueIdMap.get(kw), keywordCountMapTmp.get(kw));
                    pwNetworkTsr.println(line);
                }
                
            }// traverse all papers
            
            pwNetworkTsr.close();
            
            // Finish printing network.tsr
            // Now print 0.dict (venue_id-venue-times), 1.dict (author_id-author-times), 2.dict (keyword_id-keyword-times)
            
            // sort venue appearances 
            venueCountMap = Utils.sortByValue(venueCountMap);
            
            // sort author appearances 
            authorCountMap = Utils.sortByValue(authorCountMap);
            
            // sort keyword appearances 
            keywordCountMap = Utils.sortByValue(keywordCountMap);
            
            // output venue
            PrintWriter pwVenue = new PrintWriter("networks/" + i + "/0.dict");
            for (String venue : venueCountMap.keySet()) {
                Integer count = venueCountMap.get(venue);
                String line = String.format("%d      %s      %d", venueValueIdMap.get(venue), venue, count); 
                pwVenue.println(line);
            }
            pwVenue.flush();
            pwVenue.close();
            
            // output author
            PrintWriter pwAuthor = new PrintWriter("networks/" + i + "/1.dict");
            for (String author : authorCountMap.keySet()) {
                Integer count = authorCountMap.get(author);
                String line = String.format("%d      %s      %d", authorValueIdMap.get(author), author, count);
                pwAuthor.println(line);
            }
            pwAuthor.flush();
            pwAuthor.close();
            
            // output keyword
            PrintWriter pwKeyword = new PrintWriter("networks/" + i + "/2.dict");
            for (String keyword : keywordCountMap.keySet()) {
                Integer count = keywordCountMap.get(keyword);
                String line = String.format("%d      %s      %d", keywordValueIdMap.get(keyword), keyword, count);
                pwKeyword.println(line);
            }
            pwKeyword.flush();
            pwKeyword.close();
            System.out.println("year " + i + " network data generated");
        }

    }
    
    
    /** Construct String-Integer pairs map.     */
    public static Map<String, Integer> constructStringIdMap(List<String> values) {
        Map<String, Integer> valueIdMap = new HashMap<String, Integer>();
        for (int i = 0; i < values.size(); i++) {
            valueIdMap.put(values.get(i), i);
        }
        return valueIdMap;
    }

    /** Construct String-Long pairs map by ObjectId.     */
    public static Map<String, Long> constructPaperIdMap(List<ObjectId> values) {
        Map<String, Long> valueIdMap = new HashMap<String, Long>();
        for (int i = 0; i < values.size(); i++) {
            valueIdMap.put(values.get(i).toString(), values.get(i).getDate().getTime());
        }
        return valueIdMap;
    }
}
