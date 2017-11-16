package area52.rat_tracking_application.model;

import android.annotation.TargetApi;
import android.icu.text.DateTimePatternGenerator;
import android.os.Build;
import android.support.annotation.RequiresApi;

import java.io.Serializable;
import java.util.Date;

import static area52.rat_tracking_application.model.RatReportCSVReader.reports;

/**
 * INFORMATION HOLDER Represents a single report in the model
 *
 *
 * Information Holder and Structurer -> Manages the report information selected by the user and
 * saved in the application's server
 */

public class RatReport implements Serializable {

    private String _key;
    /** the instance of report location*/

    private ReportLocation _location;

    private String currentDate;

    /**
     * Makes a new Report
     * @param key  the unique key auto-generated for the report
     * @param location the location of the rat sighting being reported
     */
    RatReport(String key, ReportLocation location, String date) {
        _key = key;
        _location = location;
        currentDate = date;
    }

    public RatReport() {
        this("", null, null);
    }

    public void setReportKey(String key) {
        _key = key;
    }

    /**
     * A RatReport instance's unique key
     *
     * Extracts unique key from instance of rat report, with each reports unique key
     * corresponding with a row in the provided csv file, always at column index 0.     *
     *
     * @return unique key or 0
     */
    public String getReportKey() {
        return _key;
    }

    public void setNewReportKey() {
        Long[] reportKeys = (Long[]) reports.keySet().toArray();
        _key = String.valueOf(reportKeys[reports.keySet().size() - 1] + 1);
    }

    public String getNewReportKey() {
        return _key;
    }

    @TargetApi(25)
    public void setReportDate(String date) {
        currentDate = date;
    }

    public String getReportDate() {
        return currentDate;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void setNewReportDate() {
        DateTimePatternGenerator dateTime = DateTimePatternGenerator.getInstance();
        Date newDate = new Date(dateTime.toString());
        currentDate = newDate.toString();
    }

    void setReportLocation(ReportLocation singleReportLocation) {
        _location = singleReportLocation;
    }

    /**
     *
     * @return ReportLocation for instance of RatReport
     */
    public ReportLocation getReportLocation() {
        return _location;
    }

    public void setNewReportLocation(ReportLocation location) {
        _location = location;
    }

    @Override
    public String toString() {
        return "" + _key + "| " + currentDate;
    }

}
