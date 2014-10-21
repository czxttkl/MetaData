package io.metadata.test;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.event.ListSelectionEvent;

import io.metadata.data.DataCleaner;
import io.metadata.data.HtmlStringCleaner;
import io.metadata.misc.Globals;
import io.metadata.orm.MyMongoCollection;
import io.metadata.orm.Paper;

import org.bson.types.ObjectId;
import org.jongo.MongoCursor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

/**
 * Test anything you want within the project.
 * @author Zhengxing Chen
 */
public class Test {

    public static void main(String[] args) throws FailingHttpStatusCodeException, MalformedURLException, IOException {
//        String a = "\rWarp Speed: Path Planning for Star Trek reg.: Armada";
//        a = Utils.removeNewLine(a);
//        ObjectId aId = new ObjectId("53fbf8bebc52b35959a6ca0f");
//        
//        System.out.println(aId.toString().hashCode());
     /*   WebClient webClient = new WebClient(BrowserVersion.CHROME);
        HtmlPage mySite = webClient.getPage("http://ieeexplore.ieee.org/xpls/icp.jsp?arnumber=5035662");
        System.out.println(mySite.getWebResponse().getContentAsString());*/
        
        
       /* WebDriver driver = new HtmlUnitDriver();

        driver.get("http://ieeexplore.ieee.org/xpls/icp.jsp?arnumber=5035662");
        */
        File ie = new File("IEDriverServer.exe");
        System.setProperty("webdriver.ie.driver", ie.getAbsolutePath());
        
        // The Firefox driver supports javascript 
        WebDriver driver = new InternetExplorerDriver();
        
        // Go to the Google Suggest home page
        driver.get("http://ieeexplore.ieee.org/xpls/icp.jsp?arnumber=5035662");
        
        System.out.println(driver.getPageSource());
    }

}
