package area52.rat_tracking_application.controllers;

import android.annotation.TargetApi;
import android.app.Activity;
import android.icu.text.SimpleDateFormat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import area52.rat_tracking_application.R;
import area52.rat_tracking_application.model.RatReport;
import area52.rat_tracking_application.model.ReportLocation;

public class RatReportLoader extends Activity {
    private static RatReport<Long, ReportLocation> ratReport;
    static HashMap<Long, RatReport> reports;
    protected static HashMap<String, Integer> indexOfCSVColumn;
    protected static List<String> wantedCSVColumns = Arrays.asList("Unique Key", "Created Date", "Location Type",
            "Incident Zip", "Incident Address", "City", "Borough", "Latitude",
            "Longitude");
    private static String keyCreationDate;
    private static List<String> keyCreationDateList = new ArrayList<>();
    private static ReportLocation location;
    private static List<ReportLocation> reportLocations = new ArrayList<>();

    void launchLoader() {
        if (reports == null) {
            reports = new HashMap<>();
        }
        indexOfCSVColumn = new HashMap<>();
        loadRatReports();
    }

    public void loadRatReports() {
        InputStream csvReportFile = getResources().openRawResource(R.raw.rat_sightings);
        loadRatReportsFromCSV(csvReportFile);
    }

    /**
     * setCreationDate from instance of rat report to add to
     * keyCreationDateList
     *
     * @param a row index of rat report instance's unique key.
     * @param b row index of rat report instance's creation date.
     */
    protected static void setReportKeysCreationDates(int a, int b) {
        keyCreationDate = wantedCSVColumns.get(a) + wantedCSVColumns.get(b);
        if (keyCreationDate != null) {
            keyCreationDateList.add(keyCreationDate);
        }
    }

    /**
     *
     * @return keyCreationDateList unique key and creation date of each rat report instance
     * in the csv file will be saved and displayed in the drop down selection menu,
     * for viewing of individual rat report details. New entries will also have their unique keys
     * and creation dates added to this list.
     */
    protected static List<String> getReportKeysCreationDates() {
        return keyCreationDateList;
    }

    /**
     * sets a new rat report location during a new report entry activity
     *
     * @param newLocation a new rat report instance's location. Accessed during a
     * report entry activity
     */
    protected static void setNewLocation(ReportLocation newLocation) {
        location = newLocation;
    }

    /**
     *
     * @return location the newly entered rat report instance's location.
     */
    protected static ReportLocation getNewLocation() {
        return location;
    }

    /**
     * boolean check to verify existence of duplicate key, or duplicate
     * <key, value> mapping
     *
     * If the check fails and returns false, the method won't add and return,
     * and a failure signal is provided to the user by the ui.
     *
     * If it's determined that a safe add of the report can be performed, then the
     * <key, value> pair is added to the map for this report instance, and a success
     * signal is provided to the user by the ui.
     *
     * @param key report instance's unique key.
     * @param location report instance's reported location.
     * @return boolean true if item can be added safely (i.e. no duplicate keys
     * or <key, value> mappings), and otherwise return false.
     */
    public static boolean addSingleReport(Long key, ReportLocation location) {
        for (Long k : reports.keySet()) {
            for (RatReport v : reports.values()) {
                if (k.equals(key) || v.getLocation().equals(location)) {
                    return false;
                }
            }
        }
        return true;
    }

    protected static RatReport createReport(Long key, ReportLocation location) {
        RatReport<Long, ReportLocation> report = new RatReport(null, null);
        if (addSingleReport(key, location)) {
            report.put(key, location);
            reports.put(key, report);
            setReportKeysCreationDates(0, 1);
            return ratReport;
        }
        return null;
    }

    /*
     * From the given CSV InputStream, adds all rat reports in the stream to memory
     */

    private void loadRatReportsFromCSV(InputStream csvInput) {
        BufferedReader csvReader = null;
        if (!reports.isEmpty()) {
            return;
        }
        try {
            csvReader = new BufferedReader(new InputStreamReader(csvInput));
            String csvHeaderLine = csvReader.readLine();
            String[] headerRow = csvHeaderLine.split(",");
            setIndicesOfWantedCSVColumns(headerRow);
            String currentLine;
            while ((currentLine = csvReader.readLine()) != null) {
                String[] row = currentLine.split(",");
                convertCSVRowToRatReport(row);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                csvReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void setIndicesOfWantedCSVColumns(String[] csvHeader) {
        for (int i = 0; i < csvHeader.length; i++) {
            if (contains(wantedCSVColumns, csvHeader[i])) {
                indexOfCSVColumn.put(csvHeader[i], i);
            }
        }
    }

    private boolean contains(List<String> columns, String element) {
        for (int i = 0; i < columns.size(); i++) {
            if (columns.get(i).equals(element)) { return true; }
        }
        return false;
    }

    /**
     * HashMap<K, V> reports K
     *
     * Extracts unique key from instance of rat report, with each reports unique key
     * corresponding with a row in the provided csv file, always at column index 0.
     * Returns unique key of primitive type long if isNum(keyString) returns true,
     * & otherwise returns 0 if isNum(keyString) returns false.
     *
     * @param csvRow
     * @return
     */
    protected static long getReportKey(String[] csvRow) {
        String keyString = getCSVStringForColumn(wantedCSVColumns.get(0), csvRow);
        return (isNum(keyString)) ? Long.valueOf(keyString) : 0;
    }

    /**
     * HashMap<Long, RatReport> reports Value, representing a row in the
     * csv file, created by indexing the columns of each of the input streamed csv file's
     * rows, and consisting of a reports key, creation date, and reported location info.
     *
     * @param csvRow
     * @return reportLocation instance of RatReport
     */
    protected static void convertCSVRowToRatReport(String[] csvRow) {

        location = new ReportLocation(getReportDate(csvRow), getReportLatitude(
                csvRow), getReportLongitude(csvRow), getReportLocationType(
                csvRow), getReportAddress(csvRow), getReportCity(csvRow),
                getReportBorough(csvRow), getReportZip(csvRow));

        createReport(getReportKey(csvRow), location);
    }

    protected static String getCSVStringForColumn(String wantedColumn, String[] csvRow) {
        int columnIndex = indexOfCSVColumn.get(wantedColumn);
        boolean columnIndexIsValid = columnIndex > -1 && columnIndex < csvRow.length;
        return (columnIndexIsValid) ? csvRow[columnIndex] : "";
    }

    protected static boolean isNum(String toBeChecked) {
        try {
            double d = Double.parseDouble(toBeChecked);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    @TargetApi(25)
    protected static String getReportDate(String[] csvRow) {
        String dateString = getCSVStringForColumn(wantedCSVColumns.get(1), csvRow);
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a");
        Date date = new Date();
        try {
            date = dateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date.toString();
    }

    protected static String getReportLocationType(String[] csvRow) {
        String locationTypeString = getCSVStringForColumn(wantedCSVColumns.get(2), csvRow);
        return (!isNum(locationTypeString)) ? String.valueOf(locationTypeString) : "";
    }

    protected static int getReportZip(String[] csvRow) {
        String zipString = getCSVStringForColumn(wantedCSVColumns.get(3), csvRow);
        return (isNum(zipString)) ? Integer.valueOf(zipString) : 0;
    }

    protected static String getReportAddress(String[] csvRow) {
        String addressString = getCSVStringForColumn(wantedCSVColumns.get(4), csvRow);
        return (!isNum(addressString)) ? String.valueOf(addressString) : "";
    }

    protected static String getReportCity(String[] csvRow) {
        String cityString = getCSVStringForColumn(wantedCSVColumns.get(5), csvRow);
        return (!isNum(cityString)) ? String.valueOf(cityString) : "";
    }
    protected static String getReportBorough(String[] csvRow) {
        String boroughString = getCSVStringForColumn(wantedCSVColumns.get(6), csvRow);
        return (!isNum(boroughString)) ? String.valueOf(boroughString) : "";
    }

    protected static double getReportLatitude(String[] csvRow) {
        String latitudeString = getCSVStringForColumn(wantedCSVColumns.get(7), csvRow);
        return (isNum(latitudeString)) ? Double.valueOf(latitudeString) : 0;
    }
    protected static double getReportLongitude(String[] csvRow) {
        String longitudeString = getCSVStringForColumn(wantedCSVColumns.get(8), csvRow);
        return (isNum(longitudeString)) ? Double.valueOf(longitudeString) : 0;
    }

    protected static HashMap<String, Integer> getCSVHeaderIndices() {
        return indexOfCSVColumn;
    }
}
