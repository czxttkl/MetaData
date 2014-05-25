package io.metadata;

import java.io.IOException;

/**
 * Created by Zhengxing Chen.
 */
public class MetaDataFactory {
    
    public Website getWebsite(String websiteType, String doi) throws IOException {
        if (websiteType == null) {
            return null;
        }

        if (websiteType.equals("IEEEXplore")) {
            return new IEEEXplore(doi);
        }
        return null;
    }
    
    public Website getWebsite(String websiteType, long doi) throws IOException {
        return getWebsite(websiteType, String.valueOf(doi));
    }
}
