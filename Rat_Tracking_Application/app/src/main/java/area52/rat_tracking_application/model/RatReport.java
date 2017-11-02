package area52.rat_tracking_application.model;

import android.annotation.TargetApi;
import android.icu.text.DateTimePatternGenerator;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.support.annotation.RequiresApi;

import java.util.Arrays;
import java.util.List;

import static area52.rat_tracking_application.controllers.RatReportCSVReader.parsedLineAsList;
import static area52.rat_tracking_application.model.RatReportMap.reports;

/**
 * INFORMATION HOLDER Represents a single report in the model
 *
 *
 * Information Holder and Structurer -> Manages the report information selected by the user and
 * saved in the application's server
 */

public class RatReport {

    private static String _key;
    /** the instance of report location*/
    private static ReportLocation _location;

    private static SimpleDateFormat currentDate;

    private static String _date;

    private Long key;

    private static List<Integer> wantedCSVColumnIndices = Arrays.asList(
            0, 1, 7, 8, 9, 16, 23, 49, 50);
    private static List<java.lang.String> wantedCSVColumns = Arrays.asList(
            "Unique Key", "Created Date", "Location Type",
            "Incident Zip", "Incident Address", "City", "Borough", "Latitude",
            "Longitude");

    private static List<String> singleReportLocation;

    /**
     * Makes a new Report
     * @param key  the unique key auto-generated for the report
     * @param location the location of the rat sighting being reported
     */
    public RatReport(String key, ReportLocation location, String date) {
        _key = key;
        _location = location;
        _date = date;
    }

    /**
     * HashMap<K, V> reports K
     *
     * Extracts unique key from instance of rat report, with each reports unique key
     * corresponding with a row in the provided csv file, always at column index 0.
     * Returns unique key of primitive type long if isNum(keyString) returns true,
     * & otherwise returns 0 if isNum(keyString) returns false.
     *
     *
     * @return unique key or 0
     */
    public static void setReportKey() {
        _key = parsedLineAsList.get(wantedCSVColumnIndices.get(0));
    }

    public static String getReportKey() {
        return _key;
    }

    public void setNewReportKey() {
        key = Long.valueOf(reports.keySet().toArray().length + 1);
        _key = key.toString();
    }

    public String getNewReportKey() {
        return _key;
    }

    @TargetApi(25)
    static void setReportDate() {
        currentDate = new SimpleDateFormat(
                    parsedLineAsList.get(wantedCSVColumnIndices.get(1)));
    }

    public static String getReportDate() {
        _date = currentDate.toString();
        return _date;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void setNewReportDate() {
        DateTimePatternGenerator date = DateTimePatternGenerator.getInstance();
        currentDate = new SimpleDateFormat(date.toString());
    }

    public String getNewReportDate() {
        return currentDate.toString();
    }

    /**
     * HashMap<Long, RatReport> reports Value, representing a row in the
     * csv file, created by indexing the columns of each of the input streamed csv file's
     * rows, and consisting of a reports key, creation date, and reported location info.
     *
     * @return reportLocation instance of RatReport
     */
    static void setLocation() {
        String[] parsedAsArray = null;
        for (Integer i : wantedCSVColumnIndices) {
            parsedAsArray[i] = parsedLineAsList.get(wantedCSVColumnIndices.get(i));
        }
        singleReportLocation = Arrays.asList(parsedAsArray);
        _location = (ReportLocation) singleReportLocation;
    }

    static ReportLocation getReportLocation() {
        return _location;
    }

    public void setNewReportLocation(ReportLocation location) {
        _location = location;
    }

    public ReportLocation getNewReportLocation() {
        return _location;
    }
}
