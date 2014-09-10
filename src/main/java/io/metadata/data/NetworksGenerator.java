package io.metadata.data;

import org.jongo.MongoCursor;

import io.metadata.misc.Globals;
import io.metadata.orm.MyMongoCollection;
import io.metadata.orm.Paper;

public class NetworksGenerator {

    /**
     * @param args
     */
    public static void main(String[] args) {
        MyMongoCollection<Paper> mPapersColCln = new MyMongoCollection<Paper>(Globals.MONGODB_PAPERS_CLEAN_COL);
        
        for (int i = 2000; i <= 2013; i++) {
            String yearQuery = "{year:" + i + "}";
            MongoCursor<Paper> allPapers = mPapersColCln.getCollection().find(yearQuery).as(Paper.class);
            for (Paper paper : allPapers) {
                System.out.println(i + ":" + paper.getTitle());
            }
        }

    
    
    
    
    }

}
