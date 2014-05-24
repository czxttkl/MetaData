package io.metadata.download;

import java.io.IOException;

import io.metadata.MetaDataFactory;
import io.metadata.Website;

/**
 * Created by Zhengxing Chen.
 */
public class CGames {

    public static void main(String[] args) throws IOException {
        MetaDataFactory mMetaDataFactory = new MetaDataFactory();
        Website mWebsite = mMetaDataFactory.getWebsite("IEEEXplore", "6632597");
        System.out.println(mWebsite.getTitle());
        System.out.println(mWebsite.getAbstract());
        System.out.println(mWebsite.getKeywords());
    }
}
