package io.metadata.misc;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringEscapeUtils;

/**
 * Utils static functions.
 * 
 * @author Zhengxing Chen
 * 
 */
public class Utils {

    /**
     * Return true if the string is null or empty ("").
     * 
     * @param s
     * @return
     */
    public static boolean nullOrEmpty(String s) {
        if (s == null) {
            return true;
        }

        if (s.equals("")) {
            return true;
        }

        return false;
    }

    /**
     * If the strings contained in the list are either null or empty
     * 
     * @param list
     * @return
     */
    public static boolean nullOrEmpty(List<String> list) {
        if (list == null) {
            return true;
        }

        for (String str : list) {
            if (nullOrEmpty(str)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Read file as String.
     * 
     * @param filePath
     * @return
     * @throws IOException
     */
    public static String readFileAsString(File file) throws IOException {
        if (!file.exists()) {
            throw new IOException();
        } else {

        }

        StringBuilder sBuilder = new StringBuilder();
        BufferedReader bf = new BufferedReader(new FileReader(file));
        String aString;
        while ((aString = bf.readLine()) != null) {
            sBuilder.append(aString);
        }
        String finalString = sBuilder.toString();
        bf.close();
        return finalString;
    }

    /**
     * Trim the string and remove html encodings and tags.
     * 
     * @param raw
     * @return
     */
    public static String trimHtmlString(String raw) {
        // Trim the string
        raw = raw.trim();
        // Convert html encode to unicode
        raw = StringEscapeUtils.unescapeHtml(raw);
        // Remove html tags in tagcontent
        raw = raw.replaceAll("\\<.*?>", "");
        return raw;
    }

    public static String removeNewLine(String raw) {
        raw = raw.replaceAll("\r", "");
        raw = raw.replaceAll("\n", "");
        return raw;
    }

    public static <T> boolean ifContains(final T[] arr, final T v) {
        if (v == null) {
            for (final T e : arr) {
                if (e == null) {
                    return true;
                }
            }
        } else {
            for (final T e : arr) {
                if (e == v || v.equals(e)) {
                    return true;
                }
            }
        }

        return false;
    }

    public static class KeyCountMap {
        
        private Map<String, MutableInt> map = new HashMap<String, MutableInt>();
        
        public void addCount(String key) {
            MutableInt count = map.get(key);
            if (count == null) {
                map.put(key, new MutableInt());
            } else {
                count.increment();
            }
        }
        
        public Integer get(String key) {
            return map.get(key).get();
        }
        
        public Set<String> keySet() {
            return map.keySet();
        }
    }

    public static class MutableInt {
        int value = 1; // note that we start at 1 since we're counting
        
        public void increment() {
            ++value;
        }
        
        public int get() {
            return value;
        }
    }
}

