package area52.rat_tracking_application.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static area52.rat_tracking_application.controllers.RatReportCSVReader.parsedLineAsList;
import static area52.rat_tracking_application.controllers.RatReportCSVReader.wantedCSVColumnIndices;
import static area52.rat_tracking_application.model.RatReport.getReportDate;
import static area52.rat_tracking_application.model.RatReport.getReportKey;
import static area52.rat_tracking_application.model.RatReport.getReportLocation;

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

    public static List<Integer> nycZipCodes;

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

    public static void launchMaps() {
        if (reports == null) {
            reports = new HashMap<>();
            setZipCodePositions();
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

    /**
     * Set up zip codes so that upon their return when the getter is called,
     * they are selectable in numerical order in the zip code spinner
     * (see ReportEntryActivity class). Index 0 corresponds with zip code 10001, and the
     * index equal to the resulting size of the nycZipCodes list will correspond with 14925.
     * These numbers represent the zip codes constraining the range of existing zip codes in NYC.
     */
    public static void setZipCodePositions() {
        nycZipCodes = new ArrayList<>();
        for (int i = 10001; i <= 14925; i++) {
            nycZipCodes.add(i);
        }
    }

    public static List<Integer> getZipCodePositions() {
        return nycZipCodes;
    }

    /**
     *
     * @return zip code user's zip code selection within the corresponding spinner item.
     */

    public static int findZipCodePosition(Integer code) {
        int j = 0;
        while (j < nycZipCodes.size()) {
            if (code.equals(nycZipCodes.get(j))) {
                return j;
            }
            ++j;
        }
        return 0;
    }

    /**
     * Lookup a location based on its code.  Returns the position of that
     * location in the array
     *
     * @param locationCode the location type to find
     *
     * @return the index of the array that corresponds to the submitted location type
     */
    public static int findLocationTypePosition(String locationCode) {
        int i = 0;
        while (i < locationTypes.size()) {
            if (locationCode.equals(locationTypes.get(i))) return i;
            ++i;
        }
        return 0;
    }

    /**
     * Lookup a borough based on its code String.  Returns the position of that
     * borough in the corresponding list of boroughs.
     *
     * @param code the borough to find.
     *
     * @return the index of the list that corresponds with the submitted borough.
     */
    public static int findBoroughPosition(String code) {
        int i = 0;
        while (i < nycBoroughs.size()) {
            if (code.equals(nycBoroughs.get(i))) {
                return i;
            }
            ++i;
        }
        return 0;
    }
    /**
     * Lookup a city based on its code String.  Returns the position of that
     * city in the corresponding list of cities.
     *
     * @param code the city to find.
     *
     * @return the index of the list that corresponds with the submitted city.
     */
    public static int findCityPosition(String code) {
        int i = 0;
        while (i < cityList.size()) {
            if (code.equals(cityList.get(i))) {
                return i;
            }
            ++i;
        }
        return 0;
    }

}
