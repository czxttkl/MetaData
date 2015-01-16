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
import java.util.List;
import java.util.Set;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.bson.types.ObjectId;
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
            "couldnt", "cry", "de", "describe", "despite", "detail", "do", "did", "doe", "does", "done", "down", "due", "during", "each", "eg", "eight",
            "either", "eleven", "else", "elsewhere", "empty", "enough", "etc", "even", "ever", "every", "everyone", "everything",
            "everywhere", "except", "few", "fifteen", "fify", "fill", "find", "fire", "first", "five", "for", "former", "formerly",
            "forty", "found", "four", "from", "front", "full", "further", "get", "give", "go", "had", "has", "hasnt", "have", "having",
            "he", "hence", "her", "here", "hereafter", "hereby", "herein", "hereupon", "hers", "herself", "him", "himself", "his", "hitherto", "how",
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

    /** Common words that shouldn't appear in 1grams. */
    private static String[] commonword1Gram = {
        "analysi", "application", "assessment", "association", "affect", "architecture", "appropriation",
        "agent", "agency",
        "benefit", "book", "balance", "busines",
        "cost", "control", "computer", "competition", "conference", "change", "character", "complexity",
        "content", "context", "cooperation", "culture",
        "definition", "digital", "display", "diversity", "death",
        "enjoyment", "entertainment", "environment", "engineering", "experience",
        
        "framework", "fan", "fun", 
        "game", "gaming", "gamer", "gameplay", "guideline", "generation", "global",
        "high", "human",
        "installation", "industry", "information", "interaction", "interactive", "internet",
        "low", "language", "learning",
        "media", "material", "modelling", "modeling", "mapping", "magic",
        "network",
        "play", "proces", "player", "pleasure", "power", "protocal", "pattern", "practice", "probability",
        "perspective", "production", "philosophy", "prototype",
        "questionnaire",
        
        "representation", "relationship", "reading", "role", "rule", "road",
        "scale", "survey", "standard", "space", "software", "skill", "sense", "shape", "schema",
        "story",
        "science",
        "sound", "search",
        "technique", "time", "tool", "theory", "technology", "taeching", "text",
        "uncertainty", "vector",
        "video", "virtual",
        "writing",
    };
    
    /** Common words that shouldn't appear in 2grams as well as in 1grams. */
    private static String[] commonword2Gram = {
            "2000", "2001", "2002", "2003", "2004", "2005", "2006", "2007", "2008", "2009", "2010", "2011",
            "2012", "2013", "2014", "1990", "1991", "1992", "1993", "1994", "1995", "1996", "1997", "1998", "1999", 
            "acm", "aaai", "approach", "approache", "al", "algorithm", "article", "according", "addresse", "address", "addressing",
            "academic", "accept", "accepted",
            "acceptance", "achieve", "achievement", "achieving", "achieved", "available", "adopt", "adoption", "adopted", "assume",
            "assumption", "approximately", "approximate", "appropriate", "apply", "applying", "applied", "aim", "aimed", "aiming",
            "alternate", "alternative", "alternatively", "allow", "allowing", "allowed", "add", "added", "adding", 
            "actual", "actually", "accurate", "accuracy", "accurately", "abstract", "author", "automatically", "average", "aware", 
            "avoid", "avoided", "avoiding",
            "acquire", "acquired", "acquiring", "additional", "additionally",

            "big", "bigger", "biggest", "base", "based", "better", "best", "basic", "basically", "brief", "briefly", "begin", 
            "build", "built", "building", "broad", "broader", "broardly", "begun", "began", "beginning", "break", "breaking",

            "create", "creating", "created", "correct", "correctly", "common", "commonly", "current", "currently", "combining", "case",
            "computer science", "call", "called", "challenge", "challenging", "carefully", "contain", "contained", "containing",
            "consider", "considering", "considered", "certain", "certainly", "conclude", "concluded", "cause", "caused", "causing",
            "changing", "century", "choose", "choosing", "chosen", "compare", "computer games",

            "day", "define", "defined", "defining", "discus", "discussed", "directly", "describe", "demonstrate", "difficult",
            "difficulty", "difficultie", "different", "data set", "data sets", "design", "designed", "designing", "doctoral",
            "develop", "developed", "developing", "development", "dramatic", "dramatically", "drastically", "decrease", "decreased", "decreasing",
            
            "exact", "experiment", "experiments", "experimental", "et", "existing", "efficient", "efficiently", "enable", "enabled",
            "enabling", "encourage", "encouraging", "extremely", "example", "evaluate", "evaluating", "evaluation", "essential",
            "essentially", "especial", "especially", "eqaully", "entire", "entirely", "enhance", "enhanced", "enhancing", "effective",
            "easy", "easily", "easier", "early", "explained", "explanation", "explain", "exploit", "exploitation", "explored", "explore",
            "exploring", "exploration", "explosive", "explosion", "explosive", "exponential", "exponentially", "error", 
            "employ", "employed",

            "future", "fully", "follow", "following", "finding", "finally", "find", "final", "fast", "faster", "facilitate",
            "fail", "failed", "failure", "formal framework",
            "facilitating", "facilitated", "facilitation", "furthermore", 
            
            "good", "great", "greatly", "greater", "goal", "grow", "growing",
            "grew", "giving", "given", "generated", "generating", "generate", "general", "generally", 

            "high performance", "higher", "highest", "high quality", "highly", "hopefully", "help", "helped", "helping",
            
            "introduce", "introducing", "introduced", "introductory", "intuitive", "intuititively", "intuition", "issue",
            "involve", "involving", "involved",
            "indicate", "improve", "improvement", "improved", "improving", "investigate", "include", "including", "included", "increase", "increasing",
            "increased", "increasingly", "initial", "initially", "important", "improv", "implie", "implied", "instead", "implementation", "implemented",
            "implement", "instance", "immediate", "immediately", "illustrate", "illustration",

            "just",
            "key", "knowledge", "known", "lack",
            
            "large", "larger", "long", "longer", "longest", "look", "lead", "leading", "likely", "led", "left", "little",
            
            "method", "month", "model", "new", "novel", "number", "main", "mainly", 
            "maintain", "maintaining", "maintained", "making", "major",
            "meaning", "measure", "measured", "measuring", "measurement", "metric",
            
            "nearly", "necessary", "necessarily", "need", "newly", "naturally",
            
            "old", "obtained", "original", "observed", "observation", "observing", "ongoing",
            "offer", "offered", "offering", "overcome", "outcome", "outperform", "outperformed", "overall",
            
            "paper", "present", "proposed", "project", "purpose", "played", "predicted",
            "propose", "previou", "previously", "provide", "provided", "providing", "purely",
            "participant", "past", "people", "particular", "particularly", "perform",
            "performed", "performing", "performance", "possible", "possibly", "problem", "plot", "potentially",
            "present", "presenting", "presented", "presentation", "peer reviewed", "peer review",

            "quickly", "quite",
            
            "real world", "result", "results", "resulting", "recent", "recently", "research", "researcher", "researche", 
            "relative", "relatively", "review", "related", "relate", "relating", "right", 
            "represent", "representing", "represented",  
            "rapid", "rapidly", "raise", "record", "recording", "recorded", "reduce", "reducing", "reduced",
            "relevant", "rely", "relied", "relie", "remain", "remained", "rendering", "require", "required", "requirement", "requiring",
            "revisit", "revisiting", "rich", "richer", "reach", 
            
            "success", "successful", "small", "study", "studie", "studies", "suggest", "suggesting", "suggested",
            "significant", "significantly", "show", "showed", "setting",
            "showing", "surprise", "surprising", "surprisingly", "support", "supporting", "supported", "summary", 
            "satisfy", "satisfying", "satisfied", "satisfie", "simple", "simply", "solve", "solving", "solved",
            "special", "specific", "specially", "strong", "stronger", "studied", 

            "tiny", "typical", "typically", "task", "traditional", "traditionally", "training data",
            
            "use", "using", "used", "useful", "usual", "usually", "unlike", "unknown", "unnecessary", "undergoe", "undergo",
            "understand", "understanding", "underlying", "ultimate", "ultimately", "unexpected", "undertake", "unique",

            "variou", "viewing", "view", "viewed", "varying", "vary", "vast", "vice versa", "video games",
            
            "world", "widely", "wider", "wide", "work", "workshop", "worst", "www", "way", "week", 
            "yield", "year", "york",
    };
    
    private static HashSet<String> stopwordSet = new HashSet<String>(Arrays.asList(stopwords));
    private static HashSet<String> commonword2GramSet = new HashSet<String>(Arrays.asList(commonword2Gram));
    private static HashSet<String> commonword1GramSet = new HashSet<String>(Arrays.asList(commonword1Gram));
    
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

    /** skip 2-gram candidates appearing less than OCCURRENCE_THRES times. */
    public static final int OCCURRENCE_THRES = 3;
    /** keywords with size than KEYWORD_SIZE_THRES is enough. */
    public static final int KEYWORD_SIZE_THRES = 3;
    public static KeyCountMap twoGramKeywordCntMap = new KeyCountMap(TreeMap.class);
    public static KeyCountMap existingKeywordCntMap = new KeyCountMap(TreeMap.class);
    
    /** Test for ngrams extraction.  */
    public static void main(String... args) throws FileNotFoundException, InstantiationException, IllegalAccessException {
        MyMongoCollection<Paper> mPapersClnCol = new MyMongoCollection<Paper>(Globals.MONGODB_PAPERS_CLEAN_COL);
        MongoCursor<Paper> mPapers = mPapersClnCol.getCollection().find().as(Paper.class);
        
        for (Paper mPaper : mPapers) {
            // Add existing keywords
            if (!Utils.nullOrEmpty(mPaper.getKeywords())) {
                for (String keyword : mPaper.getKeywords()) {
                    addToExstWordPool(keyword);
                }
            }
            // Add new 2gram paperKeywords to keywordCntMap
            Iterator<String> iterator = gen2GramFromPaper(mPaper).iterator();
            while (iterator.hasNext()) {
                String paperKeyword = iterator.next();
                addTo2GramPool(paperKeyword);
            }
        } // traverse all papers

        // Save n-grams(n>=2) with appearance larger than OCCURRENCE_THRES to the file.
        saveKeyCntMapToFile("2gram_pool.txt", twoGramKeywordCntMap);
        
        // Save 1grams with appearance larger than OCCURRENCE_THRES to the file.
        saveKeyCntMapToFile("existing_keyword_pool.txt", existingKeywordCntMap);

        // check how many papers can't be labelled keyword.
        mPapers = mPapersClnCol.getCollection().find().as(Paper.class);
        for (Paper mPaper : mPapers) {
            if (mPaper.getId().equals("53e8de76d837748b0acd25ee")) {
                System.out.println("..");
            }
            // skip the papers which already have keywords.
            if (!Utils.nullOrEmpty(mPaper.getKeywords())) {
                continue;
            }
            // if the paper has less than 3 keywords, extract 2 grams from its title and abstract.
            set2GramForPpr(mPaper);
//            // the paper is done if it has more than three existing keywords
//            if (mPaper.getKeywords().size() < KEYWORD_SIZE_THRES ) {
//                // Set existing keywords for papers without keywords originally.
//                setExstWrdForPpr(mPaper);
//            }

            if (Utils.nullOrEmpty(mPaper.getKeywords())) {
                System.err.println(mPaper.getId() + " Title:" + mPaper.getTitle() + "\nVenue:" + mPaper.getVenue() + "  \nAbstract:"
                        + mPaper.getAbstraction() + "\n\n");
//                mPapersClnCol.getCollection().remove(new ObjectId(mPaper.getId()));
            } else {
                mPapersClnCol.getCollection().update(new ObjectId(mPaper.getId())).with(mPaper);
                System.out.println("updated:" + mPaper.getId());
            }
        } //traverse all papers

    } // main

    private static void set2GramForPpr(Paper mPaper) {
        Set<String> keywordCandidates = gen2GramFromPaper(mPaper);
        for (String kw : keywordCandidates) {
            if (twoGramKeywordCntMap.contains(kw) && twoGramKeywordCntMap.get(kw) > OCCURRENCE_THRES) {
                mPaper.getKeywords().add(kw);
            }
        }
    }

    private static void setExstWrdForPpr(Paper mPaper) {
        for (String exstWord : existingKeywordCntMap.keySet()) {
            if (existingKeywordCntMap.get(exstWord) <= OCCURRENCE_THRES) {
                continue;
            }
            exstWord = " " + exstWord + " ";
            // check if title has existing keywords
            if (mPaper.getTitle().contains(exstWord)) {
                mPaper.addKeyword(exstWord);
            }
            // check if abstract has existing keywords
            if (!Utils.nullOrEmpty(mPaper.getAbstraction())) {
                if (mPaper.getAbstraction().contains(exstWord)) {
                    mPaper.addKeyword(exstWord);
                }
            }
        }
    }

    private static void addToExstWordPool(String keyword) {
        keyword = processWord(keyword);    
        if (keyword != null) {
            existingKeywordCntMap.addCount(keyword);
        }
    }
    
    private static void addTo2GramPool(String keyword) {
        keyword = processWord(keyword);
        if (keyword != null) {
            twoGramKeywordCntMap.addCount(keyword);
        }
    }

    /** Generate 2grams from paper's title and abstract (if it is not null). */
    private static Set<String> gen2GramFromPaper(Paper mPaper) {
        Set<String> paperKeywords = new HashSet<String>();
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
        return paperKeywords;
    }

    /** Save keycountmap to file.  */
    private static void saveKeyCntMapToFile(String path, KeyCountMap keywordCntMap) throws FileNotFoundException {
        PrintWriter pw = new PrintWriter(new File(path));
        for (Entry<String, MutableInt> entry : keywordCntMap.entrySet()) {
            // if a keyword appears only one time or contains stopword in it, skip saving it.
            if (entry.getValue().get() <= OCCURRENCE_THRES) {
                continue;
            }
            pw.println(entry.getKey() + ":" + entry.getValue().get());
        }
        pw.flush();
        pw.close();
    }

    /** Remove stopword and plurals. Return null for those invalid keyword*/
    private static String processWord(String paperKeyword) {
        String[] keywords = paperKeyword.split("[^a-zA-Z0-9]");
        switch (keywords.length) {
        case 0:
            paperKeyword = null;
            break;
        case 1:
            // 1 gram
            paperKeyword = process1Gram(paperKeyword);
            break;
        case 2:
            // 2 grams
            paperKeyword = process2Gram(paperKeyword);
            break;
        default:
            paperKeyword = processNGram(paperKeyword);
            break;
        }
        return paperKeyword;
    }
    
    // for now we do nothing for n-gram (n>2)
    private static String processNGram(String paperKeyword) {
        return paperKeyword;
    }

    private static String process2Gram(String paperKeyword) {
        String[] keywords = paperKeyword.split("[^a-zA-Z0-9]");
        String cleanPaperKeyword = "";
        if (stopwordSet.contains(keywords[0]) || stopwordSet.contains(keywords[1])) {
            return null;
        }
        // roughly remove plurals
        if (keywords[1].endsWith("s")) {
            keywords[1] = keywords[1].substring(0, keywords[1].length() - 1);
        }

        if (keywords[0].matches("\\d+") || keywords[1].matches("\\d+")) {
            return null;
        }
        if (commonword2GramSet.contains(keywords[0]) || commonword2GramSet.contains(keywords[1])) {
            return null;
        }

        cleanPaperKeyword = keywords[0] + " " + keywords[1];
        if (commonword2GramSet.contains(cleanPaperKeyword)) {
            return null;
        }
        
        return cleanPaperKeyword;
    }

    private static String process1Gram(String paperKeyword) {
        paperKeyword = paperKeyword.trim();
        if (stopwordSet.contains(paperKeyword)) {
            return null;
        }

        if (paperKeyword.endsWith("s")) {
            paperKeyword = paperKeyword.substring(0, paperKeyword.length() - 1);
            if (paperKeyword.length() <= 1) {
                return null;
            }
        }

        if (commonword1GramSet.contains(paperKeyword) || commonword2GramSet.contains(paperKeyword)) {
            return null;
        }
        return paperKeyword;
    }
    
}
