package area52.rat_tracking_application.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static area52.rat_tracking_application.model.PersistenceManager.getPersistManagerInstance;

/**
 * RatReportCSVReader class reads in and parses csv file of rat reports
 * using the imported BufferedReader class.
 *
 * Created by Eric on 10/29/2017.
 */
public class RatReportCSVReader implements Serializable {

    private static RatReportCSVReader readerInstance = new RatReportCSVReader();

    private List<Integer> wantedCSVColumnIndices = Arrays.asList(
            0, 1, 7, 8, 9, 16, 23, 49, 50);

    private List<RatReport> allReportsList = new ArrayList<>();
    private List<String[]> newList = new ArrayList<>();

    private RatReport ratReport;

    static HashMap<String, RatReport> reports;

    /**
     * 5 possible boroughs of residency the user may select from using corresponding report
     * entry spinner
     */
    public static List<String> nycBoroughs = Arrays.asList(
            "QUEENS", "THE BRONX", "BROOKLYN", "MANHATTAN", "STATEN ISLAND", "NA");
    /**
     * 6 possible location types the user may select from using corresponding report
     * entry spinner
     */
    public static List<String> locationTypes = Arrays.asList(
            "SUBWAY", "RESIDENCE", "COMMERCIAL", "PARK", "THEATER", "RESTAURANT", "NA");

    /**
     * (Currently) 5 possible cities (preliminary number of cities) the user may select from using
     * corresponding report entry spinner
     */
    public static List<String> cityList = Arrays.asList(
            "NYC", "ALBANY", "BUFFALO", "LONG ISLAND", "SYRACUSE", "NA");

    /**
     * @param csvFile rat_sightings.csv
     */
    private void readInFile(InputStream csvFile) throws IOException {
        String key;
        String latitude;
        String longitude;
        String locationType;
        String address;
        String city;
        String borough;
        String zipCode;
        String date;

        if (!reports.isEmpty()) {

            return;
        }
        try {

            BufferedReader csvReader = new BufferedReader(new InputStreamReader(csvFile));
            String nextLine;
            while ((nextLine = csvReader.readLine()) != null) {
                String[] next = nextLine.split(",");
                newList.add(next);
            }
            String[] newLine = new String[51];
            for (String[] rowToParse : newList) {
                int i = 0;
                List<String> rowList = Arrays.asList(rowToParse);
                for (String itemInRow : rowList) {
                    for (Integer idx : wantedCSVColumnIndices) {
                        if (itemInRow != null) {
                            if (rowList.indexOf(itemInRow) == idx) {
                                newLine[i++] = itemInRow;
                            }
                        }
                    }
                }

                RatReport csvExtractReport = new RatReport();

                key = newLine[0];//original index = 0

                csvExtractReport.setReportKey(key);

                date = newLine[1];//original index = 1

                csvExtractReport.setReportDate(date);

                locationType = newLine[2];//original index = 7

                zipCode = newLine[3];//original index = 8

                address = newLine[4];//original index = 9

                city = newLine[5];//original index = 16

                borough = newLine[6];//original index = 23

                latitude = newLine[7];//original index = 49

                longitude = newLine[8];//original index = 50

                csvExtractReport.setReportLocation(new ReportLocation(
                        locationType, zipCode, address, city, borough,
                        String.format("%s", latitude), String.format("%s", longitude)));

                addSingleReport(csvExtractReport);

                allReportsList.add(csvExtractReport);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static RatReportCSVReader getCSVReaderInstance() {
        return readerInstance;
    }

    public void loadRatReports(InputStream csvReportFile) {

        launchMaps();

        try {

            readInFile(csvReportFile);

        } catch (IOException io_EXC) {

            io_EXC.printStackTrace();

        }
    }

    /**
     * Method added by Jake Deerin on 10/31/2017
     *
     * Gets the rat report data from a binary file and loads it into the manager
     *
     * @param ratReportBinary The binary file that contains the rat report data
     * @return whether or not the rat reports were retrieved successfully
     */
    public boolean loadReader(File ratReportBinary) {
        boolean success = false;

        Object fromBinary = getPersistManagerInstance().loadBinary(ratReportBinary);

        if (fromBinary != null) {
            readerInstance = (RatReportCSVReader) fromBinary;
            copyAllReportsListToHashMap();
            success = true;
        }
        return success;
    }

    /**
     * Puts all ratReports in internalRatReports into the "reports" HashMap
     */
    private void copyAllReportsListToHashMap() {
        for (RatReport report : allReportsList) {
            reports.put(report.getReportKey(), report);
            for (int i = 0; i < 50; i++) {
                System.out.println(report.getReportKey() + ": " + report);
            }
        }
    }

    public List<RatReport> getRatReportList() {
        return allReportsList;
    }

    private HashMap<String, RatReport> getReportsHashMap() { return reports; }

    private void launchMaps() {
        if (reports == null) {
            reports = new HashMap<>();
        }
    }

    public void setCurrentReport(RatReport rReport) {
        ratReport = rReport;
    }

    public RatReport getCurrentReport() {
        return ratReport;
    }

    /**
     * Check to verify existence of duplicate key, or duplicate
     * <key, value> mapping
     *
     * If the check fails and returns false, the method won't add and return,
     * and a failure signal is provided to the user by the ui.
     *
     * If it's determined that a safe add of the report can be performed, then the
     * <key, value> pair is added to the map for this report instance, and a success
     * signal is provided to the user by the ui.
     *
     * @param report the single report to add
     */
    public void addSingleReport(RatReport report) {
        ratReport = report;
        if (reports.size() > 0) {
            for (String k : reports.keySet()) {
                for (RatReport v : reports.values()) {
                    if (k.equals(ratReport.getReportKey()) || v.equals(ratReport)) {
                        return;
                    }
                }
            }
            allReportsList.add(ratReport);
            getReportsHashMap().put(ratReport.getReportKey(), ratReport);
        }
    }
}
