package io.metadata.data;

import io.metadata.misc.Globals;
import io.metadata.misc.Utils;
import io.metadata.misc.Utils.KeyCountMap;
import io.metadata.misc.Utils.MutableInt;
import io.metadata.orm.MyMongoCollection;
import io.metadata.orm.Paper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.jongo.MongoCursor;

/**
 * This class is used to extract keywords from string.
 * 
 * @author Zhengxing Chen
 *
 */
public class KeywordsExtractor {

    private static String[] stopwords = { "a", "about", "above", "above", "across", "after", "afterwards", "again", "against", "all",
            "almost", "alone", "along", "already", "also", "although", "always", "am", "among", "amongst", "amoungst", "amount", "an",
            "and", "another", "any", "anyhow", "anyone", "anything", "anyway", "anywhere", "are", "around", "as", "at", "back", "be",
            "became", "because", "become", "becomes", "becoming", "been", "before", "beforehand", "behind", "being", "below", "beside",
            "besides", "between", "beyond", "bill", "both", "bottom", "but", "by", "call", "can", "cannot", "cant", "co", "con", "could",
            "couldnt", "cry", "de", "describe", "detail", "do", "doe", "does", "done", "down", "due", "during", "each", "eg", "eight",
            "either", "eleven", "else", "elsewhere", "empty", "enough", "etc", "even", "ever", "every", "everyone", "everything",
            "everywhere", "except", "few", "fifteen", "fify", "fill", "find", "fire", "first", "five", "for", "former", "formerly",
            "forty", "found", "four", "from", "front", "full", "further", "get", "give", "go", "had", "has", "hasnt", "have", "having",
            "he", "hence", "her", "here", "hereafter", "hereby", "herein", "hereupon", "hers", "herself", "him", "himself", "his", "how",
            "however", "hundred", "ie", "if", "in", "inc", "indeed", "interest", "into", "is", "it", "its", "itself", "keep", "last",
            "latter", "latterly", "least", "less", "ltd", "made", "many", "may", "me", "meanwhile", "might", "mill", "mine", "more",
            "moreover", "most", "mostly", "move", "much", "must", "my", "myself", "name", "namely", "neither", "never", "nevertheless",
            "next", "nine", "no", "nobody", "none", "noone", "nor", "not", "nothing", "now", "nowhere", "of", "off", "often", "on", "once",
            "one", "only", "onto", "or", "other", "others", "otherwise", "our", "ours", "ourselves", "out", "over", "own", "part", "per",
            "perhaps", "please", "put", "rather", "re", "same", "see", "seem", "seemed", "seeming", "seems", "serious", "several", "she",
            "should", "show", "side", "since", "sincere", "six", "sixty", "so", "some", "somehow", "someone", "something", "sometime",
            "sometimes", "somewhere", "still", "such", "system", "take", "ten", "than", "that", "the", "their", "them", "themselves",
            "then", "thence", "there", "thereafter", "thereby", "therefore", "therein", "thereupon", "these", "they", "thickv", "thin",
            "third", "this", "those", "though", "three", "through", "throughout", "thru", "thus", "to", "together", "too", "top", "toward",
            "towards", "twelve", "twenty", "two", "un", "under", "until", "up", "upon", "us", "very", "via", "was", "we", "well", "were",
            "what", "whatever", "when", "where", "whereafter", "whereas", "whereby", "wherein", "whereupon", "wherever", "whether",
            "which", "while", "whither", "who", "whoever", "whole", "whom", "whose", "why", "will", "with", "within", "without", "would",
            "yet", "you", "your", "yours", "yourself", "yourselves", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "1.", "2.", "3.",
            "4.", "5.", "6.", "11", "7.", "8.", "9.", "12", "13", "14", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M",
            "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "terms", "CONDITIONS", "conditions", "values", "interested.",
            "care", "sure", ".", "!", "@", "#", "$", "%", "^", "&", "*", "(", ")", "{", "}", "[", "]", ":", ";", ",", "<", ".", ">", "/",
            "?", "_", "-", "+", "=", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t",
            "u", "v", "w", "x", "y", "z", "contact", "grounds", "buyers", "tried", "said,", "plan", "value", "principle.", "forces",
            "sent:", "is,", "was", "like", "discussion", "tmus", "diffrent.", "layout", "area.", "thanks", "thankyou", "hello", "bye",
            "rise", "fell", "fall", "psqft.", "http://", "km", "miles", "wa", "thi", "thu" };

    private static String[] commonwords = { "2000", "2001", "2002", "2003", "2004", "2005", "2006", "2007", "2008", "2009", "2010", "2011",
            "2012", "2013", "2014", "1990", "1991", "1992", "1993", "1994", "1995", "1996", "1997", "1998", "1999", "acm", "aaai",
            "approach", "approache", "al", "algorithm", "article", "according", "addresse", "address", "addressing",
            "academic", "accept", "accepted",
            "acceptance", "achieve", "achievement", "achieving", "achieved", "available", "adopt", "adoption", "adopted", "assume",
            "assumption", "approximately", "approximate", "appropriate", "apply", "applying", "applied", "aim", "aimed", "aiming",
            "alternate", "alternative", "alternatively", "allow", "allowing", "allowed", "add", "added", "adding", "actual", "accurate",
            "accuracy", "accurately", "abstract", "author", "automatically", "average", "aware", "avoid", "avoided", "avoiding",
            "acquire", "acquired", "acquiring",

            "big", "bigger", "biggest", "base", "based", "better", "best", "basic", "basically", "basis", "brief", "briefly", "begin", 
            "build", "built", "building", "broad", "broader", "broardly", "begun", "began", "beginning", "break", "breaking",

            "create", "creating", "created", "correct", "correctly", "common", "commonly", "current", "currently", "combining", "case",
            "computer science", "call", "called", "challenge", "challenging", "carefully", "contain", "contained", "containing",
            "consider", "considering", "considered", "certain", "certainly", "conclude", "concluded", "cause", "caused", "causing",
            "changing", "century", "choose", "choosing", "chosen", "compare", 

            "day", "define", "defined", "defining", "discus", "discussed", "directly", "describe", "demonstrate", "difficult",
            "difficulty", "difficultie", "different", "data set", "data sets", "design", "designed", "designing", 
            "develop", "developed", "developing", "development", "dramatic", "dramatically", "drastically", "decrease", "decreased", "decreasing",

            "exact", "experiment", "experiments", "experimental", "et", "existing", "efficient", "efficiently", "enable", "enabled",
            "enabling", "encourage", "encouraging", "extremely", "example", "evaluate", "evaluating", "evaluation", "essential",
            "essentially", "especial", "especially", "eqaully", "entire", "entirely", "enhance", "enhanced", "enhancing", "effective",
            "easy", "easily", "easier", "early", "explained", "explanation", "explain", "exploit", "exploitation", "explored", "explore",
            "exploring", "exploration", "explosive", "explosion", "explosive", "exponential", "exponentially", "error",

            "future", "fully", "follow", "following", "finding", "finally", "find", "final", "fast", "faster", "facilitate",
            "facilitating", "facilitated", "facilitation", "furthermore", "good", "great", "greatly", "greater", "goal", "grow", "growing",
            "grew", "giving", "given", "generated", "generating", "generate", "general", "generally", "fail", "failed", "failure",

            "high performance", "higher", "highest", "high quality", "highly", "hopefully", "help",
            
            "introduce", "introducing", "introduced", "introductory", "intuitive", "intuititively", "intuition", "issue",
            "involve", "involving", "involved",
            "indicate", "improve", "improvement", "improved", "improving", "investigate", "include", "including", "included", "increase", "increasing",
            "increased", "increasingly", "initial", "initially", "important", "improv", "implie", "implied", "instead", "implementation", "implemented",
            "implement", "instance", "immediate", "immediately", "illustrate", "illustration",

            "just",
            "key", "knowledge", "known", "lack",
            
            "large", "larger", "long", "longer", "longest", "look", "lead", "leading", "likely", "led", "left", "little",
            
            "method", "month", "model", "new", "novel", "number", "main", "maintain", "maintaining", "maintained", "making", "major",
            "meaning", "measure", "measured", "measuring", "measurement", "metric",
            
            "nearly", "necessary", "necessarily", "need", "newly", "naturally",
            
            "old", "obtained", "original", "observed", "observation", "observing", "ongoing",
            "offer", "offered", "offering", "overcome", "outcome", "outperform", "outperformed", "overall",
            
            "paper", "present", "proposed", "project", "purpose", 
            "propose", "previou", "previously", "provide", "provided", "providing", "purely",
            "participant", "past", "people", "particular", "particularly", "perform",
            "performed", "performing", "performance", "possible", "possibly", "problem", "plot", "potential", "potentially",
            "present", "presenting", "presented", "presentation",

            "quickly", "quite",
            
            "real world", "result", "results", "resulting", "recent", "recently", "research", "researcher", "researche", 
            "relative", "relatively", "review", "related", "relate", "relating", "right", "represent", "representing", "represented", 
            "rapid", "rapidly", "raise", "record", "recording", "recorded", "reduce", "reducing", "reduced",
            "relevant", "rely", "remain", "remained", "rendering", "require", "required", "requirement", "requiring",
            "revisit", "revisiting", "rich", "richer", "reach", 
            
            "success", "successful", "small", "study", "studie", "studies", "suggest", "suggesting", "suggested",
            "significant", "significantly", "show", "showed",
            "showing", "surprise", "surprising", "surprisingly", "support", "supporting", "supported", "summary", 
            "satisfy", "satisfying", "satisfied", "satisfie", "simple", "simply", "solve", "solving", "solved",
            "special", "specific", "specially", "strong", "stronger", "studied", 

            "tiny", "typical", "typically", "task", "traditional", "traditionally",
            
            "use", "using", "used", "useful", "usual", "usually", "unlike", "unknown", "unnecessary", "undergoe", "undergo",
            "understand", "understanding", "underlying", "ultimate", "ultimately", "unexpected", "undertake", "unique",

            "variou", "viewing", "view", "viewed", "varying", "vary", "vast",
            
            "world", "widely", "work", "wide", "workshop", "worst", "www", "way", "week", 
            "yield", "year", 
    };

    private static HashSet<String> stopwordSet = new HashSet<String>(Arrays.asList(stopwords));
    private static HashSet<String> commonwordSet = new HashSet<String>(Arrays.asList(commonwords));

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

    /**
     * Test for ngrams extraction.
     * 
     * @throws FileNotFoundException
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public static void main(String... args) throws FileNotFoundException, InstantiationException, IllegalAccessException {
        MyMongoCollection<Paper> mPapersColOrig = new MyMongoCollection<Paper>(Globals.MONGODB_PAPERS_COLLECTION);
        MongoCursor<Paper> mPapers = mPapersColOrig.getCollection().find().as(Paper.class);
        Utils.KeyCountMap keywordCntMap = new Utils.KeyCountMap(TreeMap.class);
        Utils.KeyCountMap existingKeywordCntMap = new Utils.KeyCountMap(TreeMap.class);
        
        for (Paper mPaper : mPapers) {
            // A keyword only counts once within one paper.
            Set<String> paperKeywords = new HashSet<String>();
            
            // Add existing keywords
/*            if (!Utils.nullOrEmpty(mPaper.getKeywords())) {
                for (String keyword : mPaper.getKeywords()) {
                    paperKeywords.add(keyword);
                    processWord(keyword, existingKeywordCntMap);
                }
            }
*/
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

            // Add paperKeywords to keywordCntMap
            Iterator<String> iterator = paperKeywords.iterator();
            while (iterator.hasNext()) {
                String paperKeyword = iterator.next();
                processWord(paperKeyword, keywordCntMap);
            }
        }

        // Save to the file.
        PrintWriter pw = new PrintWriter(new File("keywords_raw.txt"));
        for (Entry<String, MutableInt> entry : keywordCntMap.entrySet()) {
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

            mPaper.setKeywords(new HashSet<String>());
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

            for (String singleword : (mPaper.getTitle() + " " + mPaper.getAbstraction()).split("[^a-zA-Z0-9]+")) {
                if (stopwordSet.contains(singleword)) {
                    continue;
                }
                if (singleword.endsWith("s")) {
                    singleword = singleword.substring(0, singleword.length() - 1);
                    if (singleword.length() <=1) {
                        continue;
                    }
                }

                if (keywordCntMap.contains(singleword) && keywordCntMap.get(singleword) > 1) {
                    mPaper.getKeywords().add(singleword);
                }
            }

            if (Utils.nullOrEmpty(mPaper.getKeywords())) {
                System.err.println(/* mPaper.getId() + */"Title:" + mPaper.getTitle() + "\nVenue:" + mPaper.getVenue() + "  \nAbstract:"
                        + mPaper.getAbstraction() + "\n\n");
                // System.err.println(Ngram.ngramSet(2, mPaper.getTitle(), "[^a-zA-Z0-9]+"));
            } else {
                System.out.println("Title:" + mPaper.getTitle() + "\nAbstract:" + mPaper.getAbstraction());
                System.out.println(Arrays.toString(mPaper.getKeywords().toArray()));
                System.out.println();
            }

           
        }

    }

    /** Remove stopword and plurals. */
    private static void processWord(String paperKeyword, KeyCountMap keywordCntMap) {
        String[] keywords = paperKeyword.split("[^a-zA-Z0-9]");
        
        // 1 gram
        if (keywords.length <= 1) {
            paperKeyword = paperKeyword.trim();
            if (stopwordSet.contains(paperKeyword)) {
                return;
            }
            if (paperKeyword.endsWith("s")) {
                paperKeyword = paperKeyword.substring(0, paperKeyword.length() - 1);
                if (paperKeyword.length() <=1) {
                    return;
                }
            }
            keywordCntMap.addCount(paperKeyword);
            return;
        }

        // 2 grams
        String nexPaperKeyword = "";
        if (stopwordSet.contains(keywords[0]) || stopwordSet.contains(keywords[1])) {
            return;
        }

        if (keywords[0].endsWith("s")) {
            keywords[0] = keywords[0].substring(0, keywords[0].length() - 1);
        }
        if (keywords[1].endsWith("s")) {
            keywords[1] = keywords[1].substring(0, keywords[1].length() - 1);
        }
        if (keywords[0].matches("\\d+") || keywords[1].matches("\\d+")) {
            return;
        }
        
        
        if (commonwordSet.contains(keywords[0]) || commonwordSet.contains(keywords[1]) 
                || stopwordSet.contains(keywords[0]) || stopwordSet.contains(keywords[1])) {
            return;
        }

        nexPaperKeyword = keywords[0] + " " + keywords[1];
        if (commonwordSet.contains(nexPaperKeyword)) {
            return;
        }
        keywordCntMap.addCount(nexPaperKeyword);
    }
    
}
