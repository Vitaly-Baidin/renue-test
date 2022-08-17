package org.renue.parse.model;

public class SearchResult implements Comparable<SearchResult> {

    private String keyWord;

    private String line;

    public SearchResult(String keyWord, String line) {
        this.keyWord = keyWord;
        this.line = line;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }

    @Override
    public String toString() {
        return keyWord + "[" + line + "]";
    }

    @Override
    public int compareTo(SearchResult o) {
        if (checkNumber(this.getKeyWord())) {
            double number1 = Double.parseDouble(this.keyWord);
            double number2 = Double.parseDouble(o.getKeyWord());
            if (number1 > number2) {
                return 1;
            } else if (number1 < number2) {
                return -1;
            }
            return 0;
        }
        return this.getKeyWord().compareTo(o.getKeyWord());
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
