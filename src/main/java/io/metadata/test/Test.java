package io.metadata.test;

import java.util.Set;

import io.metadata.data.Ngram;

/**
 * Test anything you want within the project.
 * @author Zhengxing Chen
 */
public class Test {

    public static void main(String[] args) {
        String str = "i love you babe1";
        Set<String> ngrams =Ngram.ngramSet(2, str);
        System.out.println(ngrams) ;
    }

}
