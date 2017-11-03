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

    private static List<Integer> wantedCSVColumnIndices = Arrays.asList(
            0, 1, 7, 8, 9, 16, 23, 49, 50);
    private static List<java.lang.String> wantedCSVColumns = Arrays.asList(
            "Unique Key", "Created Date", "Location Type",
            "Incident Zip", "Incident Address", "City", "Borough", "Latitude",
            "Longitude");

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

    public RatReport() {
        this("", null, "");
    }

    public static void setReportKey() {
        _key = parsedLineAsList.get(wantedCSVColumnIndices.get(0));
    }

    /**
     * A RatReport instance's unique key
     *
     * Extracts unique key from instance of rat report, with each reports unique key
     * corresponding with a row in the provided csv file, always at column index 0.     *
     *
     * @return unique key or 0
     */
    static String getReportKey() {
        return _key;
    }

    public void setNewReportKey() {
        Long key = Long.valueOf(reports.keySet().toArray().length + 1);
        _key = key.toString();
    }

    public String getNewReportKey() {
        return _key;
    }

    @TargetApi(25)
    public static void setReportDate() {
        currentDate = new SimpleDateFormat(
                    parsedLineAsList.get(wantedCSVColumnIndices.get(1)));
    }

    static String getReportDate() {
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

    public static void setReportLocation(ReportLocation singleReportLocation) {
        _location = singleReportLocation;
    }

    /**
     *
     * @return ReportLocation for instance of RatReport
     */
    static ReportLocation getReportLocation() {
        return _location;
    }

    public void setNewReportLocation(ReportLocation location) {
        _location = location;
    }

    /**
     *
     * @return ReportLocation for instance of new entry instance of RatReport,
     * input via the ReportEntryActivity class in the controllers package.
     */
    public ReportLocation getNewReportLocation() {
        return _location;
    }
}
