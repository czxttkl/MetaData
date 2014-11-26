package io.metadata.data;

import io.metadata.misc.Globals;
import io.metadata.misc.Utils;
import io.metadata.misc.Utils.MutableInt;
import io.metadata.orm.MyMongoCollection;
import io.metadata.orm.Paper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.jongo.MongoCursor;

/**
 * This class is used to extract keywords from string.
 * @author Zhengxing Chen
 *
 */
public class KeywordsExtractor {

    private static String[] stopwords = { "a", "about", "above", "above", "across", "after", "afterwards", "again", "against", "all", "almost", "alone",
            "along", "already", "also", "although", "always", "am", "among", "amongst", "amoungst", "amount", "an", "and", "another",
            "any", "anyhow", "anyone", "anything", "anyway", "anywhere", "are", "around", "as", "at", "back", "be", "became", "because",
            "become", "becomes", "becoming", "been", "before", "beforehand", "behind", "being", "below", "beside", "besides", "between",
            "beyond", "bill", "both", "bottom", "but", "by", "call", "can", "cannot", "cant", "co", "con", "could", "couldnt", "cry", "de",
            "describe", "detail", "do", "done", "down", "due", "during", "each", "eg", "eight", "either", "eleven", "else", "elsewhere",
            "empty", "enough", "etc", "even", "ever", "every", "everyone", "everything", "everywhere", "except", "few", "fifteen", "fify",
            "fill", "find", "fire", "first", "five", "for", "former", "formerly", "forty", "found", "four", "from", "front", "full",
            "further", "get", "give", "go", "had", "has", "hasnt", "have", "he", "hence", "her", "here", "hereafter", "hereby", "herein",
            "hereupon", "hers", "herself", "him", "himself", "his", "how", "however", "hundred", "ie", "if", "in", "inc", "indeed",
            "interest", "into", "is", "it", "its", "itself", "keep", "last", "latter", "latterly", "least", "less", "ltd", "made", "many",
            "may", "me", "meanwhile", "might", "mill", "mine", "more", "moreover", "most", "mostly", "move", "much", "must", "my",
            "myself", "name", "namely", "neither", "never", "nevertheless", "next", "nine", "no", "nobody", "none", "noone", "nor", "not",
            "nothing", "now", "nowhere", "of", "off", "often", "on", "once", "one", "only", "onto", "or", "other", "others", "otherwise",
            "our", "ours", "ourselves", "out", "over", "own", "part", "per", "perhaps", "please", "put", "rather", "re", "same", "see",
            "seem", "seemed", "seeming", "seems", "serious", "several", "she", "should", "show", "side", "since", "sincere", "six",
            "sixty", "so", "some", "somehow", "someone", "something", "sometime", "sometimes", "somewhere", "still", "such", "system",
            "take", "ten", "than", "that", "the", "their", "them", "themselves", "then", "thence", "there", "thereafter", "thereby",
            "therefore", "therein", "thereupon", "these", "they", "thickv", "thin", "third", "this", "those", "though", "three", "through",
            "throughout", "thru", "thus", "to", "together", "too", "top", "toward", "towards", "twelve", "twenty", "two", "un", "under",
            "until", "up", "upon", "us", "very", "via", "was", "we", "well", "were", "what", "whatever", "when", "whence", "whenever",
            "where", "whereafter", "whereas", "whereby", "wherein", "whereupon", "wherever", "whether", "which", "while", "whither", "who",
            "whoever", "whole", "whom", "whose", "why", "will", "with", "within", "without", "would", "yet", "you", "your", "yours",
            "yourself", "yourselves", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "1.", "2.", "3.", "4.", "5.", "6.", "11", "7.",
            "8.", "9.", "12", "13", "14", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S",
            "T", "U", "V", "W", "X", "Y", "Z", "terms", "CONDITIONS", "conditions", "values", "interested.", "care", "sure", ".", "!", "@",
            "#", "$", "%", "^", "&", "*", "(", ")", "{", "}", "[", "]", ":", ";", ",", "<", ".", ">", "/", "?", "_", "-", "+", "=", "a",
            "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z",
            "contact", "grounds", "buyers", "tried", "said,", "plan", "value", "principle.", "forces", "sent:", "is,", "was", "like",
            "discussion", "tmus", "diffrent.", "layout", "area.", "thanks", "thankyou", "hello", "bye", "rise", "fell", "fall", "psqft.",
            "http://", "km", "miles" };

    /** Only split by \\W and keep words which are not stopwords. */
    public static List<String> simpleExtract(String raw) {
        HashSet<String> keywords = new HashSet<String>();
        String[] words = raw.split("[^a-zA-Z]+");
        for (String w : words) {
            // remove space
            w = w.trim();

            if (Utils.ifContains(stopwords, w)) {
                continue;
            } else {
                keywords.add(w);
            }
        }
        
        return new ArrayList<String>(keywords);
    }
    
    /** Test for ngrams extraction. 
     * @throws FileNotFoundException 
     * @throws IllegalAccessException 
     * @throws InstantiationException */
    public static void main(String... args) throws FileNotFoundException, InstantiationException, IllegalAccessException {
        MyMongoCollection<Paper> mPapersColOrig = new MyMongoCollection<Paper>(Globals.MONGODB_PAPERS_CLEAN_COL);
        MongoCursor<Paper> mPapers = mPapersColOrig.getCollection().find().as(Paper.class);
        Utils.KeyCountMap keywordCntMap = new Utils.KeyCountMap(TreeMap.class);
        
        for (Paper mPaper : mPapers) {
            // A keyword only counts once within one paper.
            Set<String> paperKeywords = new HashSet<String>();
            
            // Add existing keywords
            if (!Utils.nullOrEmpty(mPaper.getKeywords())) {
                for (String keyword : mPaper.getKeywords()) {
                    paperKeywords.add(keyword);
                }
            }

            // Add 2grams in titles
            for (String twoGram : Ngram.ngramSet(2, mPaper.getTitle(), "[^a-zA-Z0-9]+")) {
                paperKeywords.add(twoGram);
            }
            
            // Add 2grams in abstract
            if (!Utils.nullOrEmpty(mPaper.getAbstraction())) {
                for (String twoGram : Ngram.ngramSet(2, mPaper.getAbstraction(), "[^a-zA-Z0-9]+")) {
                    paperKeywords.add(twoGram);
                }
            }

            keywordCntMap.addCount(paperKeywords);
        }
        
        // Save to the file.
        PrintWriter pw = new PrintWriter(new File("keywords_raw.txt"));
        for (Entry<String, MutableInt> entry : keywordCntMap.entrySet()) {
            // if a keyword contains at least one stopword, skip saving it.
            String[] keywords = entry.getKey().split("[^a-zA-Z0-9]");
            for (String keyword : keywords) {
                if (Utils.ifContains(stopwords, keyword)) {
                    keywordCntMap.put(entry.getKey(), new MutableInt());
                    break;
                }
            }
            
            // if a keyword appears only one time or contains stopword in it, skip saving it.
            if (entry.getValue().get() <= 1) {
                continue;
            }
            
            pw.println(entry.getKey() + ":" + entry.getValue().get());
        }
        pw.flush();
        pw.close();
        
        // check how many papers can't be labelled keyword.
        mPapers = mPapersColOrig.getCollection().find().as(Paper.class);
        for (Paper mPaper : mPapers) {
            // skip the papers which already have keywords.
            if (!Utils.nullOrEmpty(mPaper.getKeywords())) {
                continue;
            }
            
            mPaper.setKeywords(new ArrayList<String>());
            for (String kw : Ngram.ngramSet(2, mPaper.getTitle(), "[^a-zA-Z0-9]+")) {
                if (keywordCntMap.contains(kw) && keywordCntMap.get(kw) > 1) {
                    mPaper.getKeywords().add(kw);
                }
            }

            if (!Utils.nullOrEmpty(mPaper.getAbstraction())) {
                for (String kw : Ngram.ngramSet(2, mPaper.getAbstraction(), "[^a-zA-Z0-9]+")) {
                    if (keywordCntMap.contains(kw) && keywordCntMap.get(kw) > 1) {
                        mPaper.getKeywords().add(kw);
                    }
                }
            }
            
            if (Utils.nullOrEmpty(mPaper.getKeywords())) {
                System.err.println(mPaper.getId() + ":" + mPaper.getTitle());
            } else {
//                System.out.println(mPaper.getId() + ":" + mPaper.getKeywords());
            }
            
        }
        
        
        
    }
    
}
