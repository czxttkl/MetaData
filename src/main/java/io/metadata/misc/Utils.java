package io.metadata.misc;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.apache.commons.lang.StringEscapeUtils;

/**
 * Utils static functions.
 * @author Zhengxing Chen
 *
 */
public class Utils {
    
    /**
     * Return true if the string is null or empty ("").
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
     * Read file as String.
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
     * Trim the string and remove html encodings and tags
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
    
}
