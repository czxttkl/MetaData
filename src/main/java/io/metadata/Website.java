package io.metadata;

import java.io.IOException;
import java.util.List;

/**
 * Created by Zhengxing Chen.
 */
public interface Website {
    String getKeywords() throws IOException;

    String getAbstract();

    String getTitle();

    String getYear();
    
    void process() throws IOException;
}
