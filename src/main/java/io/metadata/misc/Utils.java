package io.metadata.misc;

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

}
