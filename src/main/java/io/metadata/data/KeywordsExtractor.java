package io.metadata.data;

import io.metadata.misc.Globals;
import io.metadata.misc.Utils;
import io.metadata.orm.MyMongoCollection;
import io.metadata.orm.Paper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    
    /** Test for ngrams extraction. */
    public static void main(String... args) {
        MyMongoCollection<Paper> mPapersColOrig = new MyMongoCollection<Paper>(Globals.MONGODB_PAPERS_CLEAN_COL);
        MongoCursor<Paper> mPapers = mPapersColOrig.getCollection().find().as(Paper.class);
//        Set<String> oneGrams = new HashSet<String>();
        Set<String> twoGrams = new HashSet<String>();
        
        for (Paper mPaper : mPapers) {
//            oneGrams.addAll(Ngram.ngramSet(1, mPaper.getTitle()));
            twoGrams.addAll(Ngram.ngramSet(2, mPaper.getTitle()));
            /*if (!Utils.nullOrEmpty(mPaper.getAbstraction())) {
                oneGrams.addAll(Ngram.ngramSet(1, mPaper.getAbstraction()));
                twoGrams.addAll(Ngram.ngramSet(2, mPaper.getAbstraction()));
            }*/
        }
        
//      System.out.println(oneGrams);
        System.out.println(twoGrams.size());
        System.out.println(twoGrams);
        
        
    }
    
}
