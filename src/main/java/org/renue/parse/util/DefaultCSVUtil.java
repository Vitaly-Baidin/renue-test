package org.renue.parse.util;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class DefaultCSVUtil implements CSVUtil {

    private String filePath;

    private int startColumn = 1;

    private String searchWord;

    @Override
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public void setStartColumn(Integer startColumn) {
        this.startColumn = startColumn;
    }

    @Override
    public void setSearchWord(String searchWord) {
        this.searchWord = searchWord;
    }

    @Override
    public TreeMap<String, String> search() throws IOException {
        return parseCSV();
    }

    private TreeMap<String, String> parseCSV() throws IOException {
        Map<String, String> result;

        try (InputStream resource = DefaultCSVUtil.class.getClassLoader().getResourceAsStream(filePath)) {
            if (resource == null) throw new FileNotFoundException(filePath + " not found in resources folder");

            BufferedReader br = new BufferedReader(new InputStreamReader(resource, StandardCharsets.UTF_8));

            result = br.lines().filter(line -> !returnSubstring(line).isEmpty())
                    .collect(Collectors.toMap(
                            this::returnSubstring,
                            v -> "[" + v + "]"
                    ));
        }

        if (result.isEmpty()) {
            return new TreeMap<>();
        }



        return sortMap(result);
    }

    private String returnSubstring(String line) {
        int currentIndex = 0;

        int startIndex = 0;
        int endIndex = 0;

        if (startColumn == 1) {
            if (checkLine(line)) {
                return line.substring(0, line.indexOf(","));
            }
        }

        for (int i = 2; i <= startColumn; i++) {
            startIndex = line.indexOf(",", currentIndex);

            if (line.charAt(startIndex + 1) == '\"') {
                endIndex = line.indexOf("\",", startIndex);
            } else {
                endIndex = line.indexOf(",", startIndex);
            }

            currentIndex += endIndex;
        }

        if (startIndex == -1 || endIndex == -1) {
            return "";
        }

        String result = line.substring(startIndex + 1, endIndex + 1);

        if (checkLine(result)) {
            return result;
        }

        return "";
    }

    private boolean checkLine(String line) {
        Pattern pattern = Pattern.compile("^\\\"?" + searchWord, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(line);
        return matcher.find();
    }

    private TreeMap<String, String> sortMap(Map<String, String> map) {
        TreeMap<String, String> sortResult = new TreeMap<>((o1, o2) -> {

            if (checkNumber(o1)) {
                double number1 = Double.parseDouble(o1);
                double number2 = Double.parseDouble(o2);
                if (number1 > number2) {
                    return 1;
                } else if (number1 < number2) {
                    return -1;
                }
                return 0;
            }
            return o1.compareTo(o2);
        });

        sortResult.putAll(map);

        return sortResult;
    }

    private boolean checkNumber(String line) {
        if (line == null) {
            return false;
        }
        try {
            Double.parseDouble(line);
        } catch (NumberFormatException ex) {
            return false;
        }
        return true;
    }
}
