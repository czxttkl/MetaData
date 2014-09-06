package io.metadata.misc;

import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *  Collect urls from saved xhtmls in data/ijcgt. 
 * @author Zhengxing Chen
 */
public class IJCGTUrlCollector {

    public static void main(String[] args) throws IOException {
        // load files
        File files = new File("data/ijcgt");

        for (File file : files.listFiles()) {
            if (!file.getName().endsWith(".xhtml")) {
                continue;
            }

            String finalString = Utils.readFileAsString(file);

            // Url Pattern
            Pattern urlPattern = Pattern.compile("<a href=\"http://www.hindawi.com/journals/ijcgt(/(\\d){4}/(\\d){6}/)\">");
            Matcher urlMatcher = urlPattern.matcher(finalString);
            while (urlMatcher.find()) {
                String urlString = urlMatcher.group(1);
                System.out.println("\"" + urlString + "\",");
            }
        }
    }

}
