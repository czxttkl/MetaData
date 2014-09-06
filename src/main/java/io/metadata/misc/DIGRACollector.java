package io.metadata.misc;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Collect 70 pages of DIGRA LIB.
 * @author Zhengxing Chen
 *
 */
public class DIGRACollector {

    public static void main(String[] args) throws MalformedURLException, IOException, InterruptedException {

        PrintWriter pw = new PrintWriter("digraddd");

        String baseUrl = "http://www.digra.org/digital-library/publications/page/";
        Pattern p = Pattern
                .compile("<h2><a href=\"http://www.digra.org/digital-library/publications/(.*?)\" rel=\"bookmark\" title=\"Permanent Link ");

        for (int i = 1; i <= 70; i++) {
            pw.println("//" + i);
            
            String pageUrl = baseUrl + i;
            String pageString = Downloader.toString(new URL(pageUrl));
            Matcher urlMatcher = p.matcher(pageString);
            
            while (urlMatcher.find()) {
                String doi = "\"" + urlMatcher.group(1) + "\",";
                System.out.println(doi);
                pw.println(doi);
                pw.flush();
            }

            // Anti-robotics
            Thread.sleep((long) (10000 + Math.random() * 10000));
            System.out.println();
        }
        
        pw.close();

    }
}
