package area52.rat_tracking_application.model;

import android.annotation.TargetApi;
import android.icu.text.DateTimePatternGenerator;
import android.os.Build;
import android.support.annotation.RequiresApi;

import java.io.Serializable;
import java.util.Set;

import static area52.rat_tracking_application.model.RatReportCSVReader.reports;
import static java.lang.String.valueOf;

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
    private RatReport(String key, ReportLocation location, String date) {
        _key = key;
        _location = location;
        currentDate = date;
    }

    public RatReport() {
        this("", null, null);
    }

    void setReportKey(String key) {
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
    String getReportKey() {
        return _key;
    }

    public void setNewReportKey() {
        Set<String> reportKeys = reports.keySet();
        Long _key_ = Long.valueOf(String.format("%s", reportKeys.toArray()[reports.keySet().size() - 1])) + 1;
        _key = valueOf(_key_);
    }

    public String getNewReportKey() {
        return _key;
    }

    @TargetApi(25)
    void setReportDate(String date) {
        currentDate = date;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void setNewReportDate() {
        DateTimePatternGenerator dateTime = DateTimePatternGenerator.getInstance();
        currentDate = String.format("%s", dateTime);
    }

    void setReportLocation(ReportLocation singleReportLocation) {
        _location = singleReportLocation;
    }

    ReportLocation getLocation() {
        return _location;
    }

    /**
     *
     * Set new entry's report location, for instance of RatReport entered by the user.
     */
    public void setNewReportLocation(ReportLocation location) {
        _location = location;
    }

    @Override
    public String toString() {
        return "" + _key + "| " + currentDate;
    }
}
