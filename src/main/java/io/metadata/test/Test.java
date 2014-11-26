package io.metadata.test;

import java.util.Set;

import io.metadata.data.Ngram;
import io.metadata.data.PorterStemmer;

/**
 * Test anything you want within the project.
 * @author Zhengxing Chen
 */
public class Test {

    public static void main(String[] args) {
        String kw = "symbolic interaction";
        PorterStemmer ps = new PorterStemmer();
        ps.add(kw.toCharArray(), kw.length());
        ps.stem();
        System.out.println(ps.toString());
    }

}
