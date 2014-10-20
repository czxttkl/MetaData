package io.metadata.data;

import java.text.Normalizer;

import org.jsoup.Jsoup;

/**
 * Different methods to clean html entities in Strings.
 * @author Zhengxing Chen
 *
 */
public class HtmlStringCleaner {

    /**
     * Use Jsoup library to remove html entities.
     * http://stackoverflow.com/questions/240546/remove-html-tags-from-a-string.
     * It will convert htmlencoding (e.g. &amp) to utf (e.g. &), remove tags such as <\n> and make multiple new lines or spaces into one.
     */
    public static String cleanByJsoup(String raw) {
        return Jsoup.parse(raw).text();
    }
    
    public static String cleanByNormalizer(String raw) {
        String str = Normalizer.normalize(raw, Normalizer.Form.NFD);
        str = str.replaceAll("\\p{M}", "");
        return str;
    }
}
