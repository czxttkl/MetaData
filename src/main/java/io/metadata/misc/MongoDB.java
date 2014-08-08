package io.metadata.misc;

import java.net.UnknownHostException;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;

/**
 * @author Zhengxing Chen
 *
 *  Test to connect to mongodb
 */
public class MongoDB {

    /**
     * @param args
     * @throws UnknownHostException 
     */
    public static void main(String[] args) throws UnknownHostException {
        // The server is running on 129.10.76.16:27017
        MongoClient mongoClient = new MongoClient("129.10.76.16", 27017);

        // The database name is "metadata"
        DB db = mongoClient.getDB("metadata");
        
        // The collection name is "papers"
        DBCollection papersColl = db.getCollection("papers");

        // Get the cursor of "papers"
        DBCursor papersCursor = papersColl.find();
        while (papersCursor.hasNext()) {
            System.out.println(papersCursor.next());
        }
        
        // Close the cursor
        papersCursor.close();
    }

}
