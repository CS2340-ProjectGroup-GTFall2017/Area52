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

public class RatReport<K, V> extends HashMap{
    /** allow us to assign unique number to the report */
    private static int nextKey = 1;
    /** unique number as key */
    private K _key;
    /** the instance of report location*/
    private V _location;

    /**
     * Makes a new Report
     * @param key  the unique key auto-generated for the report
     * @param location the location of the rat sighting being reported
     */
    public RatReport(K key, V location) {
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

    public K getKey() { return _key; }

    public void setKey(K key) { _key = key; }

    public V getLocation() { return _location; }

    public void setLocation(V newLocation) { _location = newLocation; }

    @Override
    public String toString() {
        return "" + _key + "| " + ReportLocation.getCreationDate();
    }
}
