package io.metadata.orm;

import io.metadata.misc.Globals;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.WriteResult;

import org.jongo.Jongo;
import org.jongo.MongoCollection;

/**
 * @author Zhengxing Chen
 * 
 * @param <T>
 
 * Self-wrapped MongoCollection
 */
public class MyMongoCollection<T> {

    private static MongoCollection mCollection;
    private static DB db;
    
    @SuppressWarnings("unused")
    private MyMongoCollection() {
        // prevent from creating with no-arg
    }

    public MyMongoCollection(String collectionName) {
        try {
            db = new MongoClient(Globals.MONGODB_SERVER_ADDR, Globals.MONGODB_PORT).getDB(Globals.MONGODB_DBNAME);
            Jongo jongo = new Jongo(db);
            mCollection = jongo.getCollection(collectionName);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public WriteResult insert(T obj) {
        return mCollection.insert(obj);
    }
    
    @SuppressWarnings("deprecation")
    public void close() {
        db.cleanCursors(true);
    }
}
