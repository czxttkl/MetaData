package io.metadata.orm;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.WriteResult;

import io.metadata.misc.Globals;

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
        new MyMongoCollection<T>(collectionName, Globals.MONGODB_DBNAME);
    }
    
    public MyMongoCollection(String collectionName, String dbName) {
        try {
            db = new MongoClient(Globals.MONGODB_SERVER_ADDR, Globals.MONGODB_PORT).getDB(dbName);
            Jongo jongo = new Jongo(db);
            mCollection = jongo.getCollection(collectionName);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public WriteResult insert(T obj) {
        return mCollection.insert(obj);
    }
    
    public WriteResult insert(T[] objs) {
        return mCollection.insert(objs);
    }
    
    public MongoCollection getCollection() {
        return mCollection;
    }
    
    @SuppressWarnings("deprecation")
    public void close() {
        db.cleanCursors(true);
    }
}
