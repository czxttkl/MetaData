package io.metadata.misc;

import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Url patterns are different before and after 2009 for AIIDE.
 * 
 * @author Zhengxing Chen
 * 
 */
public class AIIDEUrlCollectorAfter2009 {

    public static void main(String[] args) throws IOException {

        // load files after 2009
        File files = new File("data/aiide-after-2009");

        for (File file : files.listFiles()) {
            if (!file.getName().endsWith(".htm")) {
                continue;
            }

            String finalString = Utils.readFileAsString(file);

            // Url Pattern after 2009
            Pattern urlPattern = Pattern.compile("(http://(www.)*aaai.org/ocs/index.php/AIIDE/AIIDE(\\d{2})/paper/view/\\d{3,}?)\"");
            Matcher urlMatcher = urlPattern.matcher(finalString);
            while (urlMatcher.find()) {
                String urlString = urlMatcher.group(1);
                System.out.println(urlString);
            }
        }
    }

}
