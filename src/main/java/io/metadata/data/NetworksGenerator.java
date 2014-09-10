package io.metadata.data;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.jongo.MongoCursor;

import io.metadata.misc.Globals;
import io.metadata.misc.Utils;
import io.metadata.misc.Utils.KeyCountMap;
import io.metadata.orm.MyMongoCollection;
import io.metadata.orm.Paper;

public class NetworksGenerator {

    /**
     * @param args
     * @throws IOException 
     */
    public static void main(String[] args) throws IOException {
        // Connect to mongodb papers_clean
        MyMongoCollection<Paper> mPapersColCln = new MyMongoCollection<Paper>(Globals.MONGODB_PAPERS_CLEAN_COL);
        
        // create paper-id pairs
        List<String> paperIdsList = mPapersColCln.getCollection().distinct("_id").as(String.class);
        Map<String, Integer> paperIdMap = constructValueIdMap(paperIdsList);
        
        // create venue-id pairs
        List<String> venueValuesList= mPapersColCln.getCollection().distinct("venue").as(String.class);
        Map<String, Integer> venueValueIdMap = constructValueIdMap(venueValuesList);

        // create author-id pairs
        List<String> authorValuesList= mPapersColCln.getCollection().distinct("authors").as(String.class);
        Map<String, Integer> authorValueIdMap = constructValueIdMap(authorValuesList);

        // create keyword-id pairs
        List<String> keywordValuesList= mPapersColCln.getCollection().distinct("keywords").as(String.class);
        Map<String, Integer> keywordValueIdMap = constructValueIdMap(keywordValuesList);
        
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
            
            
            PrintWriter pwNetworkTsr = new PrintWriter("networks/" + i + "/network.tsr");

            for (Paper paper : thisYearPapers) {
                // Temporary KeyCountMap only for this paper
                KeyCountMap authorCountMapTmp = new KeyCountMap();
                KeyCountMap keywordCountMapTmp = new KeyCountMap();
                
                // count venue for each paper
                venueCountMap.addCount(paper.getVenue());
                
                // count keyword for each paper
                for (String kw : paper.getKeywords()) {
                   keywordCountMap.addCount(kw);
                }
                
                // count author for each paper
                for (String ath : paper.getAuthors()) {
                    authorCountMap.addCount(ath);
                }

                // print for network.tsr
                pwNetworkTsr.println(String.format("%d     2       %d      1", paperIdMap.get(paper.getId()), venueValueIdMap.get(paper.getVenue())));
                
                for (String ath : paper.getKeywords()) {
                    String line = String.format("%d     2       %d      %d", paperIdMap.get(paper.getId()), authorValueIdMap.get(ath), authorCountMap.get(ath));
                    pwNetworkTsr.println(line);
                }

                for (String kw : paper.getKeywords()) {
                    String line = String.format("%d     2       %d      %d", paperIdMap.get(paper.getId()), keywordValueIdMap.get(kw), keywordCountMap.get(kw));
                    pwNetworkTsr.println(line);
                }
                
            }// traverse all papers
            
            // sort venue appearances 
            TreeMap<Integer, String> countVenueMap = new TreeMap<Integer, String>(Collections.reverseOrder());
            for (String venue : venueCountMap.keySet()) {
                countVenueMap.put(venueCountMap.get(venue), venue);
            }
            
            // sort author appearances 
            TreeMap<Integer, String> countAuthorMap = new TreeMap<Integer, String>(Collections.reverseOrder());
            for (String author : authorCountMap.keySet()) {
                countAuthorMap.put(authorCountMap.get(author), author);
            }
            
            // sort keyword appearances 
            TreeMap<Integer, String> countKeywordMap = new TreeMap<Integer, String>(Collections.reverseOrder());
            for (String keyword : keywordCountMap.keySet()) {
                countKeywordMap.put(keywordCountMap.get(keyword), keyword);
            }
            
            
            // output venue
            PrintWriter pwVenue = new PrintWriter("networks/" + i + "/0.dict");
            for (Integer count : countVenueMap.keySet()) {
                String venue = countVenueMap.get(count);
                String line = String.format("%d      %s      %d", venueValueIdMap.get(venue), venue, count); 
                pwVenue.println(line);
            }
            pwVenue.flush();
            pwVenue.close();
            
            // output author
            PrintWriter pwAuthor = new PrintWriter("networks/" + i + "/1.dict");
            for (Integer count : countAuthorMap.keySet()) {
                String author = countAuthorMap.get(count);
                String line = String.format("%d      %s      %d", authorValueIdMap.get(author), author, count);
                pwAuthor.println(line);
            }
            pwAuthor.flush();
            pwAuthor.close();
            
            // output keyword
            PrintWriter pwKeyword = new PrintWriter("networks/" + i + "/2.dict");
            for (Integer count : countKeywordMap.keySet()) {
                String keyword = countKeywordMap.get(count);
                String line = String.format("%d      %s      %d", keywordValueIdMap.get(keyword), keyword, count);
                pwKeyword.println(line);
            }
            pwKeyword.flush();
            pwKeyword.close();
            System.out.println();
        }

    }
    
    
    public static Map<String, Integer> constructValueIdMap(List<String> values) {
        Map<String, Integer> valueIdMap = new HashMap<String, Integer>();
        for (int i = 0; i < values.size(); i++) {
            valueIdMap.put(values.get(i), i);
        }
        return valueIdMap;
    }

}
