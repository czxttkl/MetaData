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

    public static final String USER_AGENT_VALUE = "Mozilla/5.0 (Windows NT 6.2; WOW64) AppleWebKit/537.36 (KHTML, like Gecko)" +
            " Chrome/27.0.1453.110 Safari/537.36 CoolNovo/2.0.9.20";
    public static final String USER_AGENT = "User-Agent";
    public static final int CONNECT_TIMEOUT = 5000;
    public static final int READ_TIMEOUT = 20000;
    public static final String GET_METHOD = "GET";

    public static String toString(URL mURL) throws IOException {
        InputStream is = getInputStreamFromUrl(mURL);
        try (Scanner s = new Scanner(is, "UTF-8")) {
            return s.useDelimiter("\\A").hasNext() ? s.next() : "";
//            return StringEscapeUtils.unescapeHtml(htmlString);
        }
    }

    private static InputStream getInputStreamFromUrl(URL mUrl) throws IOException {
        HttpURLConnection huc = (HttpURLConnection) mUrl.openConnection();
        huc.setConnectTimeout(CONNECT_TIMEOUT);
        huc.setReadTimeout(READ_TIMEOUT);
        huc.setRequestMethod(GET_METHOD);
        huc.setRequestProperty(USER_AGENT, USER_AGENT_VALUE);
        return huc.getInputStream();
    }

}
