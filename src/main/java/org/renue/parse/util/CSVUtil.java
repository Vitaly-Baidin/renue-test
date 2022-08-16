package org.renue.parse.util;

import java.io.IOException;
import java.util.TreeMap;

public interface CSVUtil {

    void setFilePath(String path);

    void setStartColumn(Integer startColumn);

    void setSearchWord(String searchWord);

    TreeMap<String, String> search() throws IOException;


}
