package io.metadata.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.jongo.MongoCursor;

import io.metadata.misc.Globals;
import io.metadata.misc.Utils;
import io.metadata.orm.MyMongoCollection;
import io.metadata.orm.Paper;

/**
 * Process data from papers collection and save to papers_clean collecton.
 * 1. Remove new lines (\t or \n) in title or abstract
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
            if (!paper.validate()) {
                /*System.out.println(paper.getId());
                System.out.println(paper.getTitle());
                System.out.println(paper.getAbstraction());
                System.out.println(paper.getVenue());
                System.out.println();*/
            } else {
                // Clean new lines (\r or \n) in title string and abstract string 
                paper.setTitle(Utils.removeNewLine(paper.getTitle()));
                if (!Utils.nullOrEmpty(paper.getAbstraction())) {
                    paper.setAbstraction(Utils.removeNewLine(paper.getAbstraction()));
                }
                
                // Extract keywords simply from paper titles
                if (Utils.nullOrEmpty(paper.getKeywords())) {
                    paper.setKeywords(KeywordsExtractor.simpleExtract(paper.getTitle()));
                }
                
                System.out.println("Insert paper:" + paper.getId() + "  keywords:" + Arrays.toString(paper.getKeywords().toArray()));
                
                // Add to the cleanedPaper list
                cleanedPapers.add(paper);
            }
        }
        
        // batch insert to clean version collection
        MyMongoCollection<Paper> mPapersColCln = new MyMongoCollection<Paper>(Globals.MONGODB_PAPERS_CLEAN_COL);
        Paper[] cleanedPapersArr = new Paper[cleanedPapers.size()];
        cleanedPapers.toArray(cleanedPapersArr);
        mPapersColCln.insert(cleanedPapersArr);
    }
    

}
