package io.metadata.data;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
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

    private static final String NETWORK_INPUT_FOLDER = "networks_input/";
    
    public static void main(String[] args) throws IOException {
        // Connect to mongodb papers_clean
        MyMongoCollection<Paper> mPapersColCln = new MyMongoCollection<Paper>(Globals.MONGODB_PAPERS_CLEAN_COL);
        
        // create paper-id pairs
        List<ObjectId> paperIdsList = mPapersColCln.getCollection().distinct("_id").as(ObjectId.class);
        Map<String, Integer> paperIdMap = constructPaperIdMap(paperIdsList);
        
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
            File ntwDir = new File(NETWORK_INPUT_FOLDER + i);
            if (!ntwDir.exists()) {
                ntwDir.mkdirs();
            }

            // count appearances of venues, authors and keywords in this year
            KeyCountMap venueCountMap = new KeyCountMap();
            KeyCountMap authorCountMap = new KeyCountMap();
            KeyCountMap keywordCountMap = new KeyCountMap();
            KeyCountMap authorCitedCountMap = new KeyCountMap();

            // get all papers in a particular year
            String yearQuery = "{year:" + i + "}";
            MongoCursor<Paper> thisYearPapers = mPapersColCln.getCollection().find(yearQuery).as(Paper.class);
            
            // Prepare to write to network.tsr in year folder
            PrintWriter pwNetworkTsr = new PrintWriter(new OutputStreamWriter(new FileOutputStream(NETWORK_INPUT_FOLDER + i + "/network.tsr"), "UTF-8"));

            for (Paper paper : thisYearPapers) {
                // Temporary KeyCountMap only for this paper
                // Count how many times authors, authors_cited and keywords appear in this paper
                KeyCountMap authorCountMapTmp = new KeyCountMap();
                KeyCountMap keywordCountMapTmp = new KeyCountMap();
                KeyCountMap authorCitedCountMapTmp = new KeyCountMap();
                
                // increment the count of the venue for this year
                venueCountMap.addCount(paper.getVenue());
                
                for (String kw : paper.getKeywords()) {
                   // increment the count of the keyword for this year
                   keywordCountMap.addCount(kw);
                   // increment the count of the keyword for this paper 
                   keywordCountMapTmp.addCount(kw);
                }
                
                for (String ath : paper.getAuthors()) {
                    // increment the count of the author for this year
                    authorCountMap.addCount(ath);
                     // increment the count of the author for this paper
                    authorCountMapTmp.addCount(ath);
                }
                
                if (!Utils.nullOrEmpty(paper.getAuthorsCited())) {
                    for (String ath_cited : paper.getAuthorsCited()) {
                        // increment the count of the author_cited for this year
                        authorCitedCountMap.addCount(ath_cited);
                        // increment the count of the author_cited for this paper
                        authorCitedCountMapTmp.addCount(ath_cited);
                    }
                }

                // print to network.tsr
                for (String ath : paper.getAuthors()) {
                    String line = String.format("%d\t0\t%d\t%d", paperIdMap.get(paper.getId()), authorValueIdMap.get(ath), authorCountMapTmp.get(ath));
                    pwNetworkTsr.println(line);
                }

                for (String kw : paper.getKeywords()) {
                    String line = String.format("%d\t1\t%d\t%d", paperIdMap.get(paper.getId()), keywordValueIdMap.get(kw), keywordCountMapTmp.get(kw));
                    pwNetworkTsr.println(line);
                }
                
                if (!Utils.nullOrEmpty(paper.getAuthorsCited())) {
                    for (String ath_cited : paper.getAuthorsCited()) {
                        String line = String.format("%d\t2\t%d\t%d", paperIdMap.get(paper.getId()), authorValueIdMap.get(ath_cited), authorCitedCountMapTmp.get(ath_cited));
                        pwNetworkTsr.println(line);
                    }
                }
                
            }// traverse all papers
            
            pwNetworkTsr.close();
            
            // Finish printing network.tsr
            // Now print 0.dict (venue_id-venue-times), 1.dict (author_id-author-times), 
            // 2.dict (keyword_id-keyword-times), 3.dict (author_cited_id-author_cited_times) 
            
            // sort by venue appearances
            venueCountMap = Utils.sortByValue(venueCountMap);
            
            // sort by author appearances 
            authorCountMap = Utils.sortByValue(authorCountMap);
            
            // sort by keyword appearances 
            keywordCountMap = Utils.sortByValue(keywordCountMap);
            
            // sort by author_cited appearances
            authorCitedCountMap = Utils.sortByValue(authorCitedCountMap);
            
            // output author
            PrintWriter pwAuthor = new PrintWriter(new OutputStreamWriter(new FileOutputStream(NETWORK_INPUT_FOLDER + i + "/0.dict"), "UTF-8"));
            for (String author : authorCountMap.keySet()) {
                Integer count = authorCountMap.get(author);
                String line = String.format("%d\t%s\t%d", authorValueIdMap.get(author), author, count);
                pwAuthor.println(line);
            }
            pwAuthor.flush();
            pwAuthor.close();
            
            // output keyword
            PrintWriter pwKeyword = new PrintWriter(new OutputStreamWriter(new FileOutputStream(NETWORK_INPUT_FOLDER + i + "/1.dict"), "UTF-8"));
            for (String keyword : keywordCountMap.keySet()) {
                Integer count = keywordCountMap.get(keyword);
                String line = String.format("%d\t%s\t%d", keywordValueIdMap.get(keyword), keyword, count);
                pwKeyword.println(line);
            }
            pwKeyword.flush();
            pwKeyword.close();
            
            // output author_cited
            PrintWriter pwAuthorCited = new PrintWriter(new OutputStreamWriter(new FileOutputStream(NETWORK_INPUT_FOLDER + i + "/2.dict"), "UTF-8"));
            for (String author_cited : authorCitedCountMap.keySet()) {
                Integer count = authorCitedCountMap.get(author_cited);
                String line = String.format("%d\t%s\t%d", authorValueIdMap.get(author_cited), author_cited, count);
                pwAuthorCited.println(line);
            }
            pwAuthorCited.flush();
            pwAuthorCited.close();
            
            System.out.println("year " + i + " network data generated");
        }

        PrintWriter pwPaperId = new PrintWriter(new File(NETWORK_INPUT_FOLDER + "paperIdMap.txt"));
        printMapToFile(pwPaperId, paperIdMap);
        PrintWriter pwVenueId = new PrintWriter(new File(NETWORK_INPUT_FOLDER + "venueValueIdMap.txt"));
        printMapToFile(pwVenueId, venueValueIdMap);
        PrintWriter pwAuthorId = new PrintWriter(new File(NETWORK_INPUT_FOLDER + "authorValueIdMap.txt"));
        printMapToFile(pwAuthorId, authorValueIdMap);
        PrintWriter pwKeywordId = new PrintWriter(new File(NETWORK_INPUT_FOLDER + "keywordValueIdMap.txt"));
        printMapToFile(pwKeywordId, keywordValueIdMap);

    } // main
    
    
    private static void printMapToFile(PrintWriter pw, Map<String, Integer> keywordValueIdMap) {
        for (String keyword : keywordValueIdMap.keySet()) {
            pw.println(keywordValueIdMap.get(keyword) + ":" + keyword);
        }
        pw.flush();
        pw.close();
    }


    /** Construct String-Integer pairs map. The key is String value, the value is the index of the string value in List values. */
    public static Map<String, Integer> constructStringIdMap(List<String> values) {
        Map<String, Integer> valueIdMap = new HashMap<String, Integer>();
        for (int i = 0; i < values.size(); i++) {
            valueIdMap.put(values.get(i), i);
        }
        return valueIdMap;
    }

    /** Construct String-Integer pairs map by ObjectId. Since Yizhou's code only accepts paper id as Integer, 
     *  we have to map ObjectId String to Integer.     */
    public static Map<String, Integer> constructPaperIdMap(List<ObjectId> values) {
        Map<String, Integer> valueIdMap = new HashMap<String, Integer>();
        for (int i = 0; i < values.size(); i++) {
            int value = values.get(i).toString().hashCode();
            if (value < 0) {
                value = 0 - value + Utils.randInt(0, 100);
            }
            valueIdMap.put(values.get(i).toString(), value);
        }
        return valueIdMap;
    }
}
