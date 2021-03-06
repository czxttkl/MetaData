package io.metadata.data;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Return ngrams from string str.
 * 
 * @author Zhengxing Chen
 *
 */
public class Ngram {

    /** use " " as default delimiter. */
    public static Set<String> ngramSet(int n, String str) {
        return ngramSet(n, str, " ");
    }

    public static Set<String> ngramSet(int n, String str, String delimiter) {
        Set<String> ngrams = new HashSet<String>();
        
        List<String> words = new LinkedList<String>(Arrays.asList(str.split(delimiter)));
        
        for (int i = 0; i < words.size() - n + 1; i++) {
            ngrams.add(concat(words, i, i + n));
        }
        return ngrams;
    }
    
    private static String concat(List<String> words, int start, int end) {
        StringBuilder sb = new StringBuilder();
        for (int i = start; i < end; i++) {
            sb.append((i > start ? " " : "") + words.get(i));
        }
        return sb.toString();
    }
}
