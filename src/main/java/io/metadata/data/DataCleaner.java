package io.metadata.data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jongo.MongoCursor;

import io.metadata.misc.Globals;
import io.metadata.misc.Utils;
import io.metadata.orm.MyMongoCollection;
import io.metadata.orm.Paper;

/**
 * Process data from papers collection and save to papers_clean collecton.
 * 1. Remove new lines (\r or \n) in title or abstract
 * 2. Extract keywords if missing
 * @author Zhengxing Chen
 *
 */
public class DataCleaner {

    public static void main(String[] args) {
        List<Paper> cleanedPapers = new ArrayList<Paper>();

        MyMongoCollection<Paper> mPapersColOrig = new MyMongoCollection<Paper>(Globals.MONGODB_PAPERS_COLLECTION);
        
        MongoCursor<Paper> allPapers = mPapersColOrig.getCollection().find().as(Paper.class);
        
        for (Paper paper : allPapers) {
            if (!paper.validate1()) {
                // Ignore papers which are not valid.
                /*System.out.println(paper.getId());
                System.out.println(paper.getTitle());
                System.out.println(paper.getAbstraction());
                System.out.println(paper.getVenue());
                System.out.println();*/
            } else {
                // Remove http entities and accent letters in title, keywords, abstract and author string 
                paper.setTitle(cleanHtmlString(paper.getTitle()));
                
                if (!Utils.nullOrEmpty(paper.getAbstraction())) {
                    paper.setAbstraction(cleanHtmlString(paper.getAbstraction()));
                }
                
                for (int i = 0; i < paper.getAuthors().size(); i++) {
                    String author = paper.getAuthors().get(i);
                    paper.getAuthors().set(i, cleanHtmlString(author));
                }
                
                // Also clean keyword list
                Set<String> kwList = paper.getKeywords();
                if (!Utils.nullOrEmpty(kwList)) {
                    HashSet<String> keywordSet = new HashSet<String>();
                    for (String keywordStr : kwList) {
                        String cleanedKeyword = cleanHtmlString(keywordStr);
                        keywordSet.add(cleanedKeyword);
                    }
                    paper.setKeywords(keywordSet);
                }
                
                // Clean reference
                List<List<String>> refs = paper.getCitedByPapers();
                if (refs != null && refs.size() > 0 && refs.get(0).size() > 0) {
                    for (List<String> ref : refs) {
                        ref.set(0, cleanHtmlString(ref.get(0)));
                    }
                }
                
                // Add to the cleanedPaper list
                System.out.println("Insert paper:" + paper.getId());
                cleanedPapers.add(paper);
            }
        }
        
        System.out.println("out");
        // batch insert to clean version collection
        MyMongoCollection<Paper> mPapersColCln = new MyMongoCollection<Paper>(Globals.MONGODB_PAPERS_CLEAN_COL);
        Paper[] cleanedPapersArr = new Paper[cleanedPapers.size()];
        cleanedPapers.toArray(cleanedPapersArr);
        mPapersColCln.insert(cleanedPapersArr);
    }
    
    /** Integrate several ways to clean html string. */
    public static String cleanHtmlString(String raw) {
        //Remove http entities
        String p = HtmlStringCleaner.cleanByJsoup(raw);
        // Remove accent letters
        p = HtmlStringCleaner.cleanByNormalizer(p);
        // Convert to lower cases
        p = p.toLowerCase();
        // Remove punctuations
        p = p.replaceAll("\\p{P}", " ");
        // Final trim()
        p = p.trim().replaceAll(" +", " ");
        return p;
    }
}
