package io.metadata.misc;

import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * AAAI symposia artificial intelligence and interactive environment 2000 - 2002.
 * @author Zhengxing Chen
 *
 */
public class AAAISYMPOSIAUrlCollector {

    public static void main(String[] args) throws IOException {
        // load files
        File files = new File("data/aaai-symposia");

        for (File file : files.listFiles()) {
            if (!file.getName().endsWith(".htm")) {
                continue;
            }

            String finalString = Utils.readFileAsString(file);

            // Url Pattern
            Pattern urlPattern = Pattern.compile("http://www.aaai.org/Library/Symposia/Spring/(\\d){4}/(.*?).php");
            Matcher urlMatcher = urlPattern.matcher(finalString);
            while (urlMatcher.find()) {
                String urlString = urlMatcher.group();
                System.out.println(urlString);
            }
        }
    }
}
