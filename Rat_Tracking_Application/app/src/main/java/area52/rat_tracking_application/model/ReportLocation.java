package area52.rat_tracking_application.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ReportLocation {

    /**
     * 5 possible boroughs of residency the user may select from using corresponding report
     * entry spinner
     */
    public static List<String> nycBoroughs = Arrays.asList(
            "QUEENS", "BRONX", "BROOKLYN", "MANHATTAN", "STATEN ISLAND", "NA");
    /**
     * 6 possible location types the user may select from using corresponding report
     * entry spinner
     */
    public static List<String> locationTypes = Arrays.asList(
            "SUBW", "RESI", "COMM", "PARK", "THTR", "REST", "NA");

    /**
     * 4 possible cities (preliminary number of cities) the user may select from using
     * corresponding report entry spinner
     */
    public static List<String> cityList = Arrays.asList(
            "NYC", "ALB", "BUF", "WES", "NA");

    /** the date the report was created */
    private static String _creationDate;

    private static String _latitude;
    private static String _longitude;
    private static String _locationType; // alternative to class Enum -> List<String> locationTypes
    // CHECK alternative -> //TODO: Convert to an enum if possible
    private static String _address;
    private static String _city; // alternative to class Enum -> List<String> cityList
    // CHECK alternative -> //TODO: Convert to an enum if possible
    private static String _borough; // alternative to class Enum -> List<String> nycBoroughs
    // CHECK alternative -> //TODO: Convert to an enum if possible
    private static String _zipCode;
    public static List<String> nycZipCodes;

    /**
     * Creates a new ReportLocation with the given data
     *
     * @param latitude  the address latitude
     * @param longitude the address longitude
     * @param locationType the location type of the rat sighting being reported
     * @param address the address of the reported rat sighting
     * @param city  the city of the reported rat sighting
     * @param borough the borough of the reported rat sighting
     * @param zipCode the zip code of the reported rat sighting
     */
    public ReportLocation(String latitude, String longitude,
            String locationType, String address, String city,
            String borough, String zipCode){
        _latitude = latitude;
        _longitude = longitude;
        _locationType = locationType;
        _address = address;
        _city = city;
        _borough = borough;
        _zipCode = zipCode;
    }

    public String getLatitude() { return _latitude; }
    public String getLongitude() { return _longitude; }
    public String getLocationType() { return _locationType; }
    public String getAddress() { return _address; }
    public String getCity() { return _city; }
    public String getBorough() { return _borough; }
    public String getZipCode() { return _zipCode; }

    public void setLatitude(String latitude) { _latitude = latitude; }
    public void setLongitude(String longitude) { _longitude = longitude; }
    public void setLocationType(String locationType) { _locationType = locationType; }
    public void setAddress(String address) { _address = address; }
    public void setCity(String city) { _city = city; }
    public void setBorough(String borough) { _borough = borough; }
    public void setZipCode(String zipCode) { _zipCode = zipCode; }

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
     * Set up zip codes so that they are selectable in numerical order in the zip code spinner
     * (see ReportEntryActivity class). Index 0 corresponds with zip code 10001, and the
     * index equal to the resulting size of the nycZipCodes list will correspond with 14925.
     * These numbers represent the zip codes constraining the range of existing zip codes in NYC.
     *
     */
    public static void setZipCodePositions() {
        List<Integer> zipCodes = new ArrayList<>();
        for (int i = 10001; i <= 14925; i++) {
            zipCodes.add(i);
        }
        for (Integer i : zipCodes) {
            nycZipCodes.add(i.toString());
        }
    }

    public static int findZipCodePosition(String code) {
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
