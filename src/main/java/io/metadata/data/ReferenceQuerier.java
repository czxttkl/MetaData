package io.metadata.data;

import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.bson.types.ObjectId;

import io.metadata.misc.Globals;
import io.metadata.misc.Utils;
import io.metadata.orm.MyMongoCollection;
import io.metadata.orm.Paper;

import com.aliasi.spell.EditDistance;

/** Query s_references in MongoDB. */
public class ReferenceQuerier {

    public static void main(String[] args) {
        
        HashMap<String, String> idTitleMap = new HashMap<String, String>();
        MyMongoCollection<Paper> mPapersCol = new MyMongoCollection<Paper>(Globals.MONGODB_PAPERS_CLEAN_COL);
        int cnt = 0;
        for (Paper mPaper : mPapersCol.getCollection().find().as(Paper.class)) {
            idTitleMap.put(mPaper.getId(), mPaper.getTitle());
            System.out.printf("%d %s %s\n", cnt++, mPaper.getId(), mPaper.getTitle());
        }
        System.out.println("finished building idtitlemap");
        
        cnt = 0;
        EditDistance edWithoutTrans = new EditDistance(false);
        for (Paper mPaper : mPapersCol.getCollection().find().as(Paper.class)) {
            List<List<String>> refs = mPaper.getReferences();
            if (refs == null || refs.size() == 0 || refs.get(0).size() == 0) {
                continue;
            }
            
            for (List<String> ref : refs) {
                String title = ref.get(0);
                double minDist = Double.MAX_VALUE;
                String minDistId = "";
                String minDistTitle = "";
                for (Entry<String, String> candiEntry : idTitleMap.entrySet()) {
                    double dist = edWithoutTrans.distance(title, candiEntry.getValue());
                    if (dist < minDist) {
                        minDist = dist;
                        minDistId = candiEntry.getKey();
                        minDistTitle = candiEntry.getValue();
                    }
                } // traverse all candidate for a specific ref
                
                if ((int) minDist == 0) {
                    System.out.printf("title:%s \ncandi:%s \ndist:%d, cnt:%d, mPaperId:%s refId:%s\n\n", 
                            title, minDistTitle, (int) minDist , cnt, mPaper.getId(), minDistId);
                    mPaper.addIdReference(minDistId);
                }
            } // traverse all refs in mPaper
            
            if (!Utils.nullOrEmpty(mPaper.getIdReference())) {
                mPapersCol.getCollection().update(new ObjectId(mPaper.getId())).with(mPaper);
                System.out.println("updated!" + mPaper.getId());
            }
            cnt++;
        }// traverse all papers
        
    }
}
