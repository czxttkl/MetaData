package io.metadata.misc;

import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Collect urls from http://gamestudies.org/0902/archive.
 * @author Zhengxing Chen
 */
public class GameStudiesUrlCollector {

    /**
     * @param args
     * @throws IOException 
     */
    public static void main(String[] args) throws IOException {
        
        File file = new File("data/gamestudies/0902archive.htm");
        
        String finalString = Utils.readFileAsString(file);

        // Url Pattern before 2009
        Pattern urlPattern = Pattern.compile("<dd>URI: <a href=\"http://(www.)?gamestudies.org/((\\d){4}/(.*?))\">");
        Matcher urlMatcher = urlPattern.matcher(finalString);
        while (urlMatcher.find()) {
            String urlString = urlMatcher.group(2);
            System.out.println("\"" + urlString + "\",");
        }
    }

}
