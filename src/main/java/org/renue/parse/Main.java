package org.renue.parse;

import org.renue.parse.model.SearchResult;
import org.renue.parse.util.CSVUtil;
import org.renue.parse.util.DefaultCSVUtil;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {

        CSVUtil defaultCsvUtil = new DefaultCSVUtil();

        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите строку: ");
        String input = scanner.nextLine();

        int startColumn = createArg(args);

        defaultCsvUtil.setFilePath("airports.dat");
        defaultCsvUtil.setStartColumn(startColumn);

        while (!input.equals("!quit")) {
            defaultCsvUtil.setSearchWord(input);


            try {
                long before = System.currentTimeMillis();
                List<SearchResult> result = defaultCsvUtil.search();
                long after = System.currentTimeMillis() - before;

                for (SearchResult searchResult : result) {
                    System.out.println(searchResult.toString());
                }

                System.out.print("Количество найденных строк: " + result.size() +
                        "\tЗатраченное время на поиск: " + after);

            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }




            System.out.print("\nВведите строку: ");
            input = scanner.nextLine();
        }
    }

    private static int createArg(String[] args) {
        try {
            return Integer.parseInt(args[0]);
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException ex) {
            return 1;
        }
    }
}
