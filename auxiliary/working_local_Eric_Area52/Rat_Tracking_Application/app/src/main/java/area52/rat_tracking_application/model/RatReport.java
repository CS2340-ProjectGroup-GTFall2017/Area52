package area52.rat_tracking_application.model;

import java.util.HashMap;

import area52.rat_tracking_application.controllers.RatReportLoader;

/**
 * INFORMATION HOLDER Represents a single report in the model
 *
 *
 * Information Holder and Structurer -> Manages the report information selected by the user and
 * saved in the application's server
 */

public class RatReport<Long, ReportLocation> extends HashMap{
    /** allow us to assign unique number to the report */
    private static int nextKey = 1;
    /** unique number as key */
    private Long _key;
    /** the instance of report location*/
    private ReportLocation _location;

    /**
     * Makes a new Report
     * @param key  the unique key auto-generated for the report
     * @param location the location of the rat sighting being reported
     */
    public RatReport(Long key, ReportLocation location) {
        _key = key;
        _location = location;
    }

    @Override
    public boolean equals(Object o) {
        RatReport r = (RatReport) o;
        return (r.get(_key).equals(_location) && r.getKey().equals(_key)
                && r.getLocation().equals(_location));
    }

    /* *****************************************
     * All the property setters and getters
     * */

    public Long getKey() { return _key; }

    public void setKey(Long key) { _key = key; }

    public ReportLocation getLocation() { return _location; }

    public void setLocation(ReportLocation newLocation) { _location = newLocation; }
}
