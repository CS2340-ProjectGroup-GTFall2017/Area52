package area52.rat_tracking_application.model;

import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static area52.rat_tracking_application.controllers.RatReportCSVReader.parsedLineAsList;
import static area52.rat_tracking_application.controllers.RatReportCSVReader.wantedCSVColumnIndices;

/**
 * Created by Eric on 10/29/2017.
 */

public class RatReportMap extends HashMap {
    private static RatReport ratReport;
    public static HashMap<String, RatReport> reports;

    private static List<String> keyCreationDateList = new ArrayList<>();
    private static ReportLocation location;

    private static String key;

    public static void launchMaps() {
        if (reports == null) {
            reports = new HashMap<>();
        }
    }

    /**
     * setCreationDate from instance of rat report to add to
     * keyCreationDateList
     *
     */
    protected static void setReportKeysCreationDates() {
        String key = parsedLineAsList.get(wantedCSVColumnIndices.get(0));
        String date = parsedLineAsList.get(wantedCSVColumnIndices.get(1));
        String keyCreationDate = key + date;
        keyCreationDateList.add(keyCreationDate);
    }

    /**
     *
     * @return keyCreationDateList unique key and creation date of each rat report instance
     * in the csv file will be saved and displayed in the drop down selection menu,
     * for viewing of individual rat report details. New entries will also have their unique keys
     * and creation dates added to this list.
     */
    public static List<String> getReportKeysCreationDates() {
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
     * @return boolean true if item can be added safely (i.e. no duplicate keys
     * or <key, value> mappings), and otherwise return false.
     */
    public static void addSingleReport() {
        ratReport.setReportKey();
        ratReport.setLocation();
        ratReport.setReportDate();
        key = ratReport.getReportKey();
        location = ratReport.getReportLocation();
        if (reports.size() > 0) {
            for (String k : reports.keySet()) {
                for (RatReport v : reports.values()) {
                    if (k.equals(key) || (k.equals(key) && v.getReportLocation().equals(location))) {
                        return;
                    }
                }
            }

            reports.put(key, ratReport);
        }
    }

    public boolean addNewSingleReport(String key, ReportLocation location) {
        key = ratReport.getReportKey();
        location = ratReport.getReportLocation();
        if (reports.size() > 0) {
            for (String k : reports.keySet()) {
                for (RatReport v : reports.values()) {
                    if (k.equals(key) || (k.equals(key) && v.getReportLocation().equals(location))) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    @Nullable
    RatReport createNewReport(String key, ReportLocation location) {
        RatReport report = null;
        if (addNewSingleReport(key, location)) {
            report.setReportKey();
            report.setReportDate();
            report.setLocation();
            reports.put(key, report);
            return ratReport;
        }
        return null;
    }
}
