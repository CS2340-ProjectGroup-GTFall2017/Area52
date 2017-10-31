package area52.rat_tracking_application.controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

import area52.rat_tracking_application.model.RatReportMap;

import static area52.rat_tracking_application.model.RatReportMap.reports;

/**
 * Created by Eric on 10/29/2017.
 */

public class RatReportCSVReader {

    static final String ARG_UNIQUE_KEY_ID = "unique_key_id";//1
    static final String ARG_CREATED_DATE_ID = "created_date_id";//2
    static final String ARG_LOCATION_TYPE_ID = "location_type_id";//3
    static final String ARG_INCIDENT_ZIP_ID = "incident_zip_id";//4
    static final String ARG_INCIDENT_ADDRESS_ID = "incident_adress_id";//5
    static final String ARG_CITY_ID = "city_id";//6
    static final String ARG_BOROUGH_ID = "borough_id";//7
    static final String ARG_LATITUDE_ID = "latitude_id";//8
    static final String ARG_LONGITUDE_ID = "longitude_id";//9

    static List<String> columnStringID = Arrays.asList(
            ARG_UNIQUE_KEY_ID, ARG_CREATED_DATE_ID, ARG_LOCATION_TYPE_ID,
            ARG_INCIDENT_ZIP_ID, ARG_INCIDENT_ADDRESS_ID, ARG_CITY_ID,
            ARG_BOROUGH_ID, ARG_LATITUDE_ID, ARG_LONGITUDE_ID);

    private static final char SEPARATION_MARKER = ',';
    private static final char _QUOTE_ = '"';
    static List<String> newList;
    public static List<Integer> wantedCSVColumnIndices = Arrays.asList(
            0, 1, 7, 8, 9, 16, 23, 49, 50);
    static String[] parsedLine;
    public static List<String> parsedLineAsList;
    static String parsedLineForViewing;

    /**
     * @param csvFile rat_sightings.csv
     */
     static void readInFile(InputStream csvFile) throws IOException {
         if (!reports.isEmpty()) {
             return;
         }
         BufferedReader csvReader = new BufferedReader(new InputStreamReader(csvFile));
         try {
             String headerLine = csvReader.readLine();
             String[] header = headerLine.split(",");
             List<String> headerRowAsList = Arrays.asList(header);

             for  (String nextLine = csvReader.readLine();
                   nextLine != null;
                   nextLine = csvReader.readLine()) {

                   parsedLine = nextLine.split(",");
                   parsedLineAsList = Arrays.asList(parsedLine);
                   parsedLineForViewing = String.valueOf("[ "
                           + ARG_UNIQUE_KEY_ID + "= " + parsedLineAsList.get(wantedCSVColumnIndices.get(0)) + ", "
                           + ARG_CREATED_DATE_ID + "= " + parsedLineAsList.get(wantedCSVColumnIndices.get(1)) + ", "
                           + ARG_LOCATION_TYPE_ID + "= " + parsedLineAsList.get(wantedCSVColumnIndices.get(2)) + ", "
                           + ARG_INCIDENT_ZIP_ID + "= " + parsedLineAsList.get(wantedCSVColumnIndices.get(3)) + ", "
                           + ARG_INCIDENT_ADDRESS_ID + "= " + parsedLineAsList.get(wantedCSVColumnIndices.get(4)) + ", "
                           + ARG_CITY_ID + "= " + parsedLineAsList.get(wantedCSVColumnIndices.get(5)) + ", "
                           + ARG_BOROUGH_ID + "= " + parsedLineAsList.get(wantedCSVColumnIndices.get(6)) + ", "
                           + ARG_LATITUDE_ID + "= " + parsedLineAsList.get(wantedCSVColumnIndices.get(7)) + ", "
                           + ARG_LONGITUDE_ID + "= " + parsedLineAsList.get(wantedCSVColumnIndices.get(8)) + " ]");
                   newList.add(parsedLineForViewing);
                   RatReportMap.addSingleReport();
             }

         } catch (IOException e) {
             e.printStackTrace();
         }
    }

    static List<String> getReportDetailActivity() {
        return parsedLineAsList;
    }
}