package io.metadata.test;

import org.bson.types.ObjectId;

/**
 * Test anything you want within the project.
 * @author Zhengxing Chen
 */
public class Test {

    public static void main(String[] args) {
//        String a = "\rWarp Speed: Path Planning for Star Trek reg.: Armada";
//        a = Utils.removeNewLine(a);
        ObjectId aId = new ObjectId("53fbf8bebc52b35959a6ca0f");
        
        System.out.println(aId.toString().hashCode());
    }

}
