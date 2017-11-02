package area52.rat_tracking_application.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static area52.rat_tracking_application.controllers.RatReportCSVReader.parsedLineAsList;
import static area52.rat_tracking_application.controllers.RatReportCSVReader.wantedCSVColumnIndices;
import static area52.rat_tracking_application.model.RatReport.getReportDate;
import static area52.rat_tracking_application.model.RatReport.getReportKey;
import static area52.rat_tracking_application.model.RatReport.getReportLocation;
import static area52.rat_tracking_application.model.RatReport.setLocation;
import static area52.rat_tracking_application.model.RatReport.setReportDate;
import static area52.rat_tracking_application.model.RatReport.setReportKey;

/**
 * RatReportMap class saved loaded rat reports from csv file, and also
 * accepts new puts to its static 'reports' hash map.
 *
 * Created by Eric on 10/29/2017.
 */

public class RatReportMap extends HashMap {

    public static RatReport ratReport;

    public static HashMap<String, RatReport> reports;

    private static String[] keyCreationDate = new String[2];

    private static List<String[]> keyCreationDateList = new ArrayList<>();

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
    public static void setReportKeyCreationDate() {
        String key = parsedLineAsList.get(wantedCSVColumnIndices.get(0));
        String date = parsedLineAsList.get(wantedCSVColumnIndices.get(1));
        keyCreationDate[0] = key;
        keyCreationDate[1] = date;
        keyCreationDateList.add(keyCreationDate);
    }

    public static void setNewReportKeyCreationDate(String key, String date) {
        keyCreationDate[0] = key;
        keyCreationDate[1] = date;
        keyCreationDateList.add(keyCreationDate);
    }

    /**
     *
     * @return keyCreationDateList unique key and creation date of each rat report instance
     * in the csv file will be saved and displayed in the drop down selection menu,
     * for viewing of individual rat report details. New entries will also have their unique keys
     * and creation dates added to this list.
     */
    public static List<String[]> getReportKeysCreationDates() {
        return keyCreationDateList;
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
        setReportKey();
        setLocation();
        setReportDate();
        ratReport = new RatReport(getReportKey(), getReportLocation(), getReportDate());
        if (reports.size() > 0) {
            for (String k : reports.keySet()) {
                for (RatReport v : reports.values()) {
                    if (k.equals(getReportKey()) || v.equals(ratReport)) {
                        return;
                    }
                }
            }
            reports.put(getReportKey(), ratReport);
        }
    }
}
