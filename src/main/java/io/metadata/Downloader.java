package io.metadata;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class Downloader {

    public static final String USER_AGENT_VALUE = "Mozilla/5.0 (Windows NT 6.2; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/27.0.1453.110 Safari/537.36 CoolNovo/2.0.9.20";
    public static final String USER_AGENT = "USER_AGENT";
    public static final int CONNECT_TIMEOUT = 5000;
    public static final int READ_TIMEOUT = 20000;

    public static String toString(URL mURL) throws IOException {
        InputStream is = getInputStreamFromUrl(mURL, 0);
        try (Scanner s = new Scanner(is)) {
            return s.useDelimiter("\\A").hasNext() ? s.next() : "";
        }
    }

    private static InputStream getInputStreamFromUrl(URL mUrl, long startPos) throws IOException {
        HttpURLConnection huc = (HttpURLConnection) mUrl.openConnection();
        huc.setConnectTimeout(5000);
        huc.setReadTimeout(20000);
        huc.setRequestMethod("GET");
        huc.setRequestProperty(USER_AGENT, USER_AGENT_VALUE);
        return huc.getInputStream();
    }

}
