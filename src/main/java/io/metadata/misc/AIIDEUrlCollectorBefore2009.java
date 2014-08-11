package io.metadata.misc;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Url patterns are different before and after 2009 for AIIDE.
 * @author Zhengxing Chen
 */
public class AIIDEUrlCollectorBefore2009 {

    public static void main(String[] args) throws IOException {

        // load files before 2009
        File files = new File("data/aiide-before-2009");

        for (File file : files.listFiles()) {
            if (!file.getName().endsWith(".htm")) {
                continue;
            }

            StringBuilder sBuilder = new StringBuilder();
            BufferedReader bf = new BufferedReader(new FileReader(file));
            String aString;
            while ((aString = bf.readLine()) != null) {
                sBuilder.append(aString);
            }
            String finalString = sBuilder.toString();

            // Url Pattern before 2009
            Pattern urlPattern = Pattern.compile("http://www.aaai.org/Library/AIIDE/(\\d){4}/(.*?).php");
            Matcher urlMatcher = urlPattern.matcher(finalString);
            while (urlMatcher.find()) {
                String urlString = urlMatcher.group();
                System.out.println(urlString);
            }
            bf.close();
        }
    }
}
