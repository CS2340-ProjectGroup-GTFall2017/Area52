package area52.rat_tracking_application.model;

public class ReportLocation {

    /** the date the report was created */
    private static String _creationDate;

    private String _latitude;
    private String _longitude;
    private String _locationType; // alternative to class Enum -> List<String> locationTypes
    // CHECK alternative -> //TODO: Convert to an enum if possible
    private String _address;
    private String _city; // alternative to class Enum -> List<String> cityList
    // CHECK alternative -> //TODO: Convert to an enum if possible
    private String _borough; // alternative to class Enum -> List<String> nycBoroughs
    // CHECK alternative -> //TODO: Convert to an enum if possible
    private String _zipCode;

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
    public ReportLocation(String locationType, String zipCode,
              String address, String city, String borough,
              String latitude, String longitude) {

        _locationType = locationType;
        _zipCode = zipCode;
        _address = address;
        _city = city;
        _borough = borough;
        _latitude = latitude;
        _longitude = longitude;

    }

    public ReportLocation(){
        this("", "", "", "", "", "", "");
    }

    public String getLocationType() { return _locationType; }
    public String getZipCode() { return _zipCode; }
    public String getAddress() { return _address; }
    public String getCity() { return _city; }
    public String getBorough() { return _borough; }
    public String getLatitude() { return _latitude; }
    public String getLongitude() { return _longitude; }

    public void setLocationType(String locationType) { _locationType = locationType; }
    public void setZipCode(String zipCode) { _zipCode = zipCode; }
    public void setAddress(String address) { _address = address; }
    public void setCity(String city) { _city = city; }
    public void setBorough(String borough) { _borough = borough; }
    public void setLatitude(String latitude) { _latitude = latitude; }
    public void setLongitude(String longitude) { _longitude = longitude; }

}
