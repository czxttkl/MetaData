package io.metadata.data;

import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.TreeMap;

import io.metadata.misc.Globals;
import io.metadata.misc.Utils;
import io.metadata.misc.Utils.KeyCountMap;
import io.metadata.misc.Utils.MutableInt;
import io.metadata.orm.MyMongoCollection;
import io.metadata.orm.Paper;

import org.jongo.MongoCursor;

/** The class lists occurences of authors for each venue in each year in order to prune editors if there is any. */
public class EditorPrune {

    public static void main(String... args) throws InstantiationException, IllegalAccessException {
        MyMongoCollection<Paper> mPapersCol = new MyMongoCollection<Paper>(Globals.MONGODB_PAPERS_CLEAN_COL);
        MongoCursor<Paper> allPapers = mPapersCol.getCollection().find().as(Paper.class);
        HashMap<String, TreeMap<Long, KeyCountMap>> venueAuthorMap = new HashMap<String, TreeMap<Long, KeyCountMap>>();

        List<String> venues = mPapersCol.getCollection().distinct("venue").as(String.class);
        System.out.println("Distinct " + venues.size() + " venues:" + venues);

        for (String venue : venues) {
            MongoCursor<Paper> papersThisVenue = mPapersCol.getCollection().find("{venue:#}", venue).as(Paper.class);
            for (Paper mPaper : papersThisVenue) {
                insertPaper(venueAuthorMap, mPaper);
            }
        }

        for (String venue : venues) {
            for (long year : venueAuthorMap.get(venue).keySet()) {
                for (Entry<String, MutableInt> entry : Utils.sortByValue(venueAuthorMap.get(venue).get(year)).entrySet()) {
                    // Only keep authors with occurrences more than 4
                    if (entry.getValue().get() > 4) {
                        System.out.println("*********************************************************************");
                        System.out.println(venue + ":" + year);
                        System.out.println("*********************************************************************");
                        System.out.println(entry.getKey() + ":" + entry.getValue().get());
                        System.out.println();
                    }
                }
            }
        }

    }

    private static void insertPaper(HashMap<String, TreeMap<Long, KeyCountMap>> venueAuthorMap, Paper mPaper) {
        if (venueAuthorMap.get(mPaper.getVenue()) == null) {
            venueAuthorMap.put(mPaper.getVenue(), new TreeMap<Long, KeyCountMap>());
        }

        if (venueAuthorMap.get(mPaper.getVenue()).get(mPaper.getYear()) == null) {
            venueAuthorMap.get(mPaper.getVenue()).put(mPaper.getYear(), new KeyCountMap());
        }

        venueAuthorMap.get(mPaper.getVenue()).get(mPaper.getYear()).addCount(mPaper.getAuthors());
    }
}
