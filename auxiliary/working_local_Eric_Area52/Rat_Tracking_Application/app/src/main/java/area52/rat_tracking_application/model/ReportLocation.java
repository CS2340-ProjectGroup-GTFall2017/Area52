package area52.rat_tracking_application.model;

public class ReportLocation {
    private double latitude;
    private double longitude;
    private String locationType; //TODO: Convert to an enum if possible
    private String address;
    private String city;
    private String borough; //TODO: Convert to an enum if possible
    private String zipCode;

    /*
     * Creates a new ReportLocation with the given data
     */
    public ReportLocation(double lat, double lng, String loc, String addr, String city,
                          String borough, String zip){
        latitude = lat;
        longitude = lng;
        locationType = loc;
        address = addr;
        this.city = city;
        this.borough = borough;
        zipCode = zip;
    }

    public double getLatitude() { return latitude; }
    public double getLongitude() { return longitude; }
    public String getLocationType() { return locationType; }
    public String getAddress() { return address; }
    public String getCity() { return city; }
    public String getBorough() { return borough; }
    public String getZipCode() { return zipCode; }

    public void setLatitude(double lat) { latitude = lat; }
    public void setLongitude(double lng) { longitude = lng; }
    public void setLocationType(String type) { locationType = type; }
    public void setAddress(String address) { this.address = address; }
    public void setCity(String city) { this.city = city; }
    public void setBorough(String borough) { this.borough = borough; }
    public void setZipCode(String zip) { zipCode = zip; }
}
