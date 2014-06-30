package io.metadata.misc;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Url patterns are different before and after 2010 from AAAI
 * @author Zhengxing Chen
 *
 */
public class AAAIUrlCollectorAfter2010 {

    public static void main(String[] args) throws IOException {

        // load files after 2010
//        File files = new File("data/aaai-after-2010");
        File files = new File("data/aiide-after-2009");

        for (File file : files.listFiles()) {
            if (!file.getName().endsWith(".htm"))
                continue;

            StringBuilder sBuilder = new StringBuilder();
            BufferedReader bf = new BufferedReader(new FileReader(file));
            String aString;
            while ((aString = bf.readLine()) != null) {
                sBuilder.append(aString);
            }
            String finalString = sBuilder.toString();

            // Url Pattern after 2009
//          Pattern urlPattern = Pattern.compile("(http://(www.)*aaai.org/ocs/index.php/AAAI/AAAI(\\d{2})/paper/view/\\d{3,}?)\"");
            Pattern urlPattern = Pattern.compile("(http://(www.)*aaai.org/ocs/index.php/AIIDE/AIIDE(\\d{2})/paper/view/\\d{3,}?)\"");
            Matcher urlMatcher = urlPattern.matcher(finalString);
            while (urlMatcher.find()) {
                String urlString = urlMatcher.group(1);
                System.out.println(urlString);
            }
            bf.close();
        }
    }

}
