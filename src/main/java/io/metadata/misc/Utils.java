package io.metadata.misc;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;

import org.apache.commons.lang.StringEscapeUtils;

/**
 * Utils static functions.
 * 
 * @author Zhengxing Chen
 * 
 */
public class Utils {

    /** Generate random integer in range of min and max.     */
    public static int randInt(int min, int max) {

        // NOTE: Usually this should be a field rather than a method
        // variable so that it is not re-seeded every call.
        Random rand = new Random();

        // nextInt is normally exclusive of the top value,
        // so add 1 to make it inclusive
        int randomNum = rand.nextInt((max - min) + 1) + min;

        return randomNum;
    }
    
    /** Return true if the string is null or empty ("").     */
    public static boolean nullOrEmpty(String s) {
        if (s == null) {
            return true;
        }

        if (s.equals("")) {
            return true;
        }

        return false;
    }

    /** If the strings contained in the list are either null or empty.     */
    public static boolean nullOrEmpty(Collection<String> list) {
        if (list == null) {
            return true;
        }
        
        if (list.size() == 0) {
            return true;
        }

        if (list.size() >= 1) {
            for (String o : list) {
                if (!nullOrEmpty(o)) {
                    return false;
                }
            }
            return true;
        }

        // never return to this
        return false;
    }

    /**  Read file as String.  */
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

    /** Trim the string and remove html encodings and tags.  */
    public static String trimHtmlString(String raw) {
        // Trim the string
        raw = raw.trim();
        // Convert html encode to unicode
        raw = StringEscapeUtils.unescapeHtml(raw);
        // Remove html tags in tagcontent
        raw = raw.replaceAll("\\<.*?>", "");
        return raw;
    }

    public static String removeHttpNewLine(String raw) {
        raw = raw.replaceAll("\r", "");
        raw = raw.replaceAll("\n", "");
        return raw;
    }

    /** If the array contains the element v. */
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

    /** Sort map's keyset by values. */
    public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
        List<Map.Entry<K, V>> list = new LinkedList<>(map.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<K, V>>() {
            @Override
            public int compare(Map.Entry<K, V> o1, Map.Entry<K, V> o2) {
                return (o1.getValue()).compareTo(o2.getValue());
            }
        });

        Map<K, V> result = new LinkedHashMap<>();
        for (Map.Entry<K, V> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }
        return result;
    }
    
    /** Sort KeyCountMap's keyset by values and return a new KeyCountMap. */
    public static KeyCountMap sortByValue(KeyCountMap map) {
        List<Entry<String, MutableInt>> list = new LinkedList<>(map.entrySet());
        Collections.sort(list, new Comparator<Entry<String, MutableInt>>() {
            @Override
            public int compare(Entry<String, MutableInt> o1, Entry<String, MutableInt> o2) {
                return (-1) * (o1.getValue().get()).compareTo(o2.getValue().get());
            }
        });

        KeyCountMap result = new KeyCountMap();
        for (Entry<String, MutableInt> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }
        return result;
    }

    public static <T, E> Set<T> getKeysByValue(Map<T, E> map, E value) {
        Set<T> keys = new HashSet<T>();
        for (Entry<T, E> entry : map.entrySet()) {
            if (value.equals(entry.getValue())) {
                keys.add(entry.getKey());
            }
        }
        return keys;
    }
    
    /**
     * Key-Count map. Record counts for each key.
     * @author Zhengxing Chen
     */
    public static class KeyCountMap {

        private Map<String, MutableInt> map = new LinkedHashMap<String, MutableInt>();

        public KeyCountMap() {
            
        }
        
        @SuppressWarnings({ "unchecked", "rawtypes" })
        public KeyCountMap(Class<? extends Map> mapType) {
            try {
                map = mapType.newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        
        public void addCount(String key) {
            MutableInt count = map.get(key);
            if (count == null) {
                map.put(key, new MutableInt());
            } else {
                count.increment();
            }
        }
        
        public void addCount(Collection<String> c) {
            for (String key : c) {
                addCount(key);
            }
        }

        public void put(String key, MutableInt mi) {
            map.put(key, mi);
        }
        
        public void remove(String key) {
            map.remove(key);
        }
        
        public Integer get(String key) {
            return map.get(key).get();
        }

        public Set<String> keySet() {
            return map.keySet();
        }
        
        public Set<Entry<String, MutableInt>> entrySet() {
            return map.entrySet();
        }
        
        public boolean contains(String key) {
            return map.get(key) != null;
        }
    }

    /**
     * Class for KeyCountMap.
     * @author Zhengxing Chen
     *
     */
    public static class MutableInt {
        Integer value = 1; // note that we start at 1 since we're counting

        public MutableInt() {

        }
        
        public MutableInt(int v) {
            value = v;
        }
        
        public void increment() {
            value++;
        }

        public Integer get() {
            return value;
        }
    }
}
