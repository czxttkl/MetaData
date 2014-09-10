package io.metadata.test;

import io.metadata.misc.Utils;

public class Test {

    /**
     * @param args
     */
    public static void main(String[] args) {
        String a = "\rWarp Speed: Path Planning for Star Trek reg.: Armada";
        a = Utils.removeNewLine(a);
        System.out.println(a);

    }

}
