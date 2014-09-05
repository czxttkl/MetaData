package io.metadata.misc;

import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CIEUrlCollector {

    public static void main(String[] args) throws IOException {

        // load cie webpages
        File files = new File("data/cie");

        for (File file : files.listFiles()) {
            if (!file.getName().endsWith(".htm")) {
                continue;
            }

            String finalString = Utils.readFileAsString(file);

            // Url Pattern after 2010
            Pattern urlPattern = Pattern.compile("(http://dx.doi.org/(.*?))\"");
            Matcher urlMatcher = urlPattern.matcher(finalString);
            while (urlMatcher.find()) {
                String urlString = urlMatcher.group(1);
                System.out.println(urlString);
            }
        }
    }

}
