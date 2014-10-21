package io.metadata.misc;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import org.apache.commons.lang.StringEscapeUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

/**
 * 
 * Downloader that supports different levels of html rendering 
 * @author Zhengxing Chen
 * 
 */
public class Downloader {

    /** Basic html source downloader. JS scripts may not e rendered */ 
    public static String toString(URL mURL) throws IOException {
        InputStream is = getInputStreamFromUrl(mURL);
        try (Scanner s = new Scanner(is, "UTF-8")) {
            String htmlString = s.useDelimiter("\\A").hasNext() ? s.next() : "";
            return StringEscapeUtils.unescapeHtml(htmlString);
        }
    }

    /** More advanced way to render html pages and download sources. It is still possible that some js scripts are not be rendered well. */
    public static String toStringAdv1(URL mURL) {
        WebDriver driver = new HtmlUnitDriver();
        driver.get(mURL.toString());
        String html = driver.getPageSource();
        driver.close();
        return html;
    }
    
    /** The most advanced way to render html pages and download sources. It will open IE explorer browser for rendering. */
    public static String toStringAdv2(URL mURL) {
        File ie = new File("IEDriverServer.exe");
        System.setProperty("webdriver.ie.driver", ie.getAbsolutePath());
        WebDriver driver = new InternetExplorerDriver(); 
        driver.get(mURL.toString());
        String html = driver.getPageSource();
        driver.close();
        return html;
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
