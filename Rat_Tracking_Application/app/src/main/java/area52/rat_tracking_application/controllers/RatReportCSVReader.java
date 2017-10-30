package area52.rat_tracking_application.controllers;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Eric on 10/29/2017.
 */

class RatReportCSVReader {

    private static final char DEFAULT_SEPARATOR = ',';
    private static final char DEFAULT_QUOTE = '"';
    private static List<String> line;
    private static List<String> parsedLineList;

    static void readInFile(InputStream csvFile) throws Exception {

        parsedLineList = new ArrayList<>();
        String parsedLine;

        Scanner scanner = new Scanner(csvFile);
        while (scanner.hasNext()) {
            line = parseLine(scanner.nextLine());
            parsedLine= line.toString();
            parsedLineList.add(parsedLine);
            ///for (String s : line) {

            /***}
            System.out.println(
                    "RatReport [unique_id = " + line.get(0) + ", " +
                            "creation_date = " + line.get(1) + ", " +
                            "name=" + line.get(2) + "]");***/
        }
        scanner.close();
    }

    static List<String> getParsedFile() {
        return parsedLineList;
    }

    static List<String> parseLine(String csvLine) {
        return parseLine(csvLine, DEFAULT_SEPARATOR, DEFAULT_QUOTE);
    }

    static List<String> parseLine(String csvLine, char separators) {
        return parseLine(csvLine, separators, DEFAULT_QUOTE);
    }

    static List<String> parseLine(String csvLine, char separationMarkers, char customized) {

        List<String> reportList = new ArrayList<>();

        if (csvLine == null && csvLine.isEmpty()) {
            return reportList;
        }

        if (customized == ' ') {
            customized = DEFAULT_QUOTE;
        }

        if (separationMarkers == ' ') {
            separationMarkers = DEFAULT_SEPARATOR;
        }

        StringBuffer current = new StringBuffer();
        boolean inQuo = false;
        boolean beginCharCollection = false;
        boolean doubleQuoInCol = false;

        char[] chars = csvLine.toCharArray();

        for (char c : chars) {

            if (inQuo) {
                beginCharCollection = true;
                if (c == customized) {
                    inQuo = false;
                    doubleQuoInCol = false;
                } else {

                    //Fixed : allow "" in custom quote enclosed
                    if (c == '\"') {
                        if (!doubleQuoInCol) {
                            current.append(c);
                            doubleQuoInCol = true;
                        }
                    } else {
                        current.append(c);
                    }

                }
            } else {
                if (c == customized) {
                    inQuo = true;
                    if (chars[0] != '"' && customized == '\"') {
                        current.append('"');
                    }
                    if (beginCharCollection) {
                        current.append('"');
                    }
                } else if (c == separationMarkers) {

                    reportList.add(current.toString());
                    current = new StringBuffer();
                    beginCharCollection = false;

                } else if (c == '\r') {
                    continue;
                } else if (c == '\n') {
                    break;
                } else {
                    current.append(c);
                }
            }
        }

        reportList.add(current.toString());

        return reportList;
    }

}
