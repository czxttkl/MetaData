package io.metadata.data;

import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.bson.types.ObjectId;

import io.metadata.misc.Globals;
import io.metadata.orm.MyMongoCollection;
import io.metadata.orm.Paper;

import com.aliasi.spell.EditDistance;

/** Query s_references in MongoDB. */
public class ReferenceQuerier {

    public static void main(String[] args) throws InterruptedException {
        
        HashMap<String, String> idTitleMap = new HashMap<String, String>();
        MyMongoCollection<Paper> mPapersCol = new MyMongoCollection<Paper>(Globals.MONGODB_PAPERS_CLEAN_COL);
        int cnt = 0;
        for (Paper mPaper : mPapersCol.getCollection().find().as(Paper.class)) {
            idTitleMap.put(mPaper.getId(), mPaper.getTitle());
            System.out.printf("%d %s %s\n", cnt++, mPaper.getId(), mPaper.getTitle());
        }
        System.out.println("finished building idtitlemap");
        
        // Count how many papers have been processed
        cnt = 0;
        EditDistance edWithoutTrans = new EditDistance(false);
        int cntThres = (int) mPapersCol.getCollection().count();
        while (cnt < cntThres) {
            try {
                mPapersCol = new MyMongoCollection<Paper>(Globals.MONGODB_PAPERS_CLEAN_COL);
                for (Paper mPaper : mPapersCol.getCollection().find().skip(cnt).as(Paper.class)) {
                    cnt++;
                    // filter out papers with no references
                    List<List<String>> refs = mPaper.getReferences();
                    if (refs == null || refs.size() == 0 || refs.get(0).size() == 0) {
                        continue;
                    }

                    for (List<String> ref : refs) {
                        String title = ref.get(0);
                        for (Entry<String, String> candiEntry : idTitleMap.entrySet()) {
                            double dist = edWithoutTrans.distance(title, candiEntry.getValue());
                            // find match if dist == 0
                            if (dist <= 2) {
                                String cite_id = candiEntry.getKey();
                                String cite_title = candiEntry.getValue();
                                System.out.printf("title:%s \ncandi:%s \ndist:%d, cnt:%d, mPaperId:%s refId:%s\n\n", title, cite_title, 0,
                                        cnt, mPaper.getId(), cite_id);
                                Paper citePaper = mPapersCol.getCollection().findOne(new ObjectId(cite_id)).as(Paper.class);
                                citePaper.addAuthorsCited(mPaper.getAuthors());
                                mPapersCol.getCollection().update(new ObjectId(cite_id)).with(citePaper);
                                break;
                            }
                        } // traverse all candidate for a specific ref
                    } // traverse all refs in mPaper
                }// traverse all papers
            } catch (Exception e) {
                System.out.println("Exception!!!!!!!!\n\n");
                Thread.sleep(5000);
            }
        } // while
        
    }// main
}
