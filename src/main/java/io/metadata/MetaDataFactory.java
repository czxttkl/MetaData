package io.metadata;

import java.lang.reflect.Constructor;

/**
 * Created by Zhengxing Chen.
 */
public class MetaDataFactory {

    public Website getWebsite(String websiteType, String doi) throws Exception {
        if (websiteType == null) {
            return null;
        }
        Constructor<?> c = Class.forName(websiteType).getConstructor(String.class);
        return (Website) c.newInstance(doi);
    }

    public Website getWebsite(String websiteType, long doi) throws Exception {
        return getWebsite(websiteType, String.valueOf(doi));
    }
}
