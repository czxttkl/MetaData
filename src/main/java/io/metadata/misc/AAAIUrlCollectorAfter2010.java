package io.metadata.misc;

import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Url patterns are different before and after 2010 from AAAI.
 * @author Zhengxing Chen
 *
 */
public class AAAIUrlCollectorAfter2010 {

    public static void main(String[] args) throws IOException {

        // load files after 2010
        File files = new File("data/aaai-after-2010");

        for (File file : files.listFiles()) {
            if (!file.getName().endsWith(".htm")) {
                continue;
            }

            String finalString = Utils.readFileAsString(file);

            // Url Pattern after 2010
            Pattern urlPattern = Pattern.compile("(http://(www.)*aaai.org/ocs/index.php/AAAI/AAAI(\\d{2})/paper/view/\\d{3,}?)\"");
            Matcher urlMatcher = urlPattern.matcher(finalString);
            while (urlMatcher.find()) {
                String urlString = urlMatcher.group(1);
                System.out.println(urlString);
            }
        }
    }

}
