package io.metadata.misc;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import org.apache.commons.lang.StringEscapeUtils;

/**
 * @author Zhengxing Chen
 * 
 */
public class Downloader {

    public static String toString(URL mURL) throws IOException {
        InputStream is = getInputStreamFromUrl(mURL);
        try (Scanner s = new Scanner(is, "UTF-8")) {
            String htmlString = s.useDelimiter("\\A").hasNext() ? s.next() : "";
            return StringEscapeUtils.unescapeHtml(htmlString);
        }
    }

    private static InputStream getInputStreamFromUrl(URL mUrl) throws IOException {
        HttpURLConnection huc = (HttpURLConnection) mUrl.openConnection();
        huc.setConnectTimeout(Globals.CONNECT_TIMEOUT);
        huc.setReadTimeout(Globals.READ_TIMEOUT);
        huc.setRequestMethod(Globals.GET_METHOD);
        huc.setRequestProperty(Globals.USER_AGENT, Globals.USER_AGENT_VALUE);
        return huc.getInputStream();
    }

}
