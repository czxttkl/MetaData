package io.metadata.data;

import java.util.HashSet;
import java.util.Set;

public class Ngram {

    public static Set<String> ngramSet(int n, String str) {
        Set<String> ngrams = new HashSet<String>();
        String[] words = str.split(" ");
        for (int i = 0; i < words.length - n + 1; i++)
            ngrams.add(concat(words, i, i+n));
        return ngrams;
    }
    
    private static String concat(String[] words, int start, int end) {
        StringBuilder sb = new StringBuilder();
        for (int i = start; i < end; i++)
            sb.append((i > start ? " " : "") + words[i]);
        return sb.toString();
    }
}
