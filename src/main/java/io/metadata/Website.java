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

    void process() throws IOException;
}
