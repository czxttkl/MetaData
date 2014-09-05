package io.metadata.misc;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

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
}
