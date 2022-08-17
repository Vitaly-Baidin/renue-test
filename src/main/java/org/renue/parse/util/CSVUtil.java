package org.renue.parse.util;

import org.renue.parse.model.SearchResult;

import java.io.IOException;
import java.util.List;

public interface CSVUtil {

    void setFilePath(String path);

    void setStartColumn(Integer startColumn);

    void setSearchWord(String searchWord);

    List<SearchResult> search() throws IOException;


}
