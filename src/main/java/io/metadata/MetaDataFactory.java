package io.metadata;


import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by Zhengxing Chen.
 */
public class MetaDataFactory {
    
    public Website getWebsite(String websiteType, String doi) throws IOException, NoSuchMethodException, SecurityException, ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (websiteType == null) {
            return null;
        }
        Constructor<?> c = Class.forName(websiteType).getConstructor(String.class);
        return (Website) c.newInstance(doi);
    }
    
    public Website getWebsite(String websiteType, long doi) throws IOException, NoSuchMethodException, SecurityException, ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        return getWebsite(websiteType, String.valueOf(doi));
    }
}
