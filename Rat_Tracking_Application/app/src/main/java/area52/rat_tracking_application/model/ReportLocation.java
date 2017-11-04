package area52.rat_tracking_application.model;

public class ReportLocation {

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
    private static Integer _zipCode;

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
                          String borough, Integer zipCode){
        _latitude = latitude;
        _longitude = longitude;
        _locationType = locationType;
        _address = address;
        _city = city;
        _borough = borough;
        _zipCode = zipCode;
    }

    public ReportLocation(){
        this("", "", "", "", "", "", 0);
    }

    public String getLatitude() { return _latitude; }
    public String getLongitude() { return _longitude; }
    public String getLocationType() { return _locationType; }
    public String getAddress() { return _address; }
    public String getCity() { return _city; }
    public String getBorough() { return _borough; }
    public Integer getZipCode() { return _zipCode; }

    public void setLatitude(String latitude) { _latitude = latitude; }
    public void setLongitude(String longitude) { _longitude = longitude; }
    public void setLocationType(String locationType) { _locationType = locationType; }
    public void setAddress(String address) { _address = address; }
    public void setCity(String city) { _city = city; }
    public void setBorough(String borough) { _borough = borough; }
    public void setZipCode(Integer zipCode) { _zipCode = zipCode; }

}
