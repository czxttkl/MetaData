package io.metadata.misc;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * AAAI Intelligent Narrative Technologies 2011-2013.
 * @author Zhengxing Chen
 *
 */
public class AAAIINTWorkshopUrlCollector {

    public static void main(String[] args) throws IOException {
        // load files
        File files = new File("data/aaai-int-workshop");

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

            // Url Pattern
            Pattern urlPattern = Pattern.compile("<p class=\"left\"><a href=\"http://(www.)?aaai.org/ocs/index.php/(AIIDE/AIIDE((\\d){2})(.*?)/paper/view/((\\d){4}))");
            Matcher urlMatcher = urlPattern.matcher(finalString);
            while (urlMatcher.find()) {
                String urlString = "\"" + urlMatcher.group(2) + "\",";
                System.out.println(urlString);
            }
            bf.close();
        }
    }
}
