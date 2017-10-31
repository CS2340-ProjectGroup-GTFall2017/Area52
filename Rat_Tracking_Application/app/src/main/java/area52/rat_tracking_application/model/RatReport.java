package area52.rat_tracking_application.model;

import java.io.Serializable;
import java.util.Date;

public class RatReport implements Serializable {
    private long key;
    private Date creationDate;
    private ReportLocation location;

    public RatReport(long key, Date creation, ReportLocation location) {
        this.key = key;
        this.creationDate = creation;
        this.location = location;
    }

    public long getKey() { return key; }
    public Date getCreationDate() { return creationDate; }
    public ReportLocation getLocation() { return location; }

    public void setCreationDate(Date newDate) { creationDate = newDate; }
    public void setLocation(ReportLocation newLocation) { location = newLocation; }

    public String detailedReportString() {
        String reportDetails = "ID: " + key + "\n";
        reportDetails += "Date: " + creationDate + "\n";
        reportDetails += "City: " + location.getCity() + "\n";
        reportDetails += "Zip Code: " + location.getZipCode() + "\n";
        reportDetails += "Borough: " + location.getBorough() + "\n";
        reportDetails += "Address: " + location.getAddress() + "\n";
        reportDetails += "Location: " + location.getLocationType() + "\n";
        reportDetails += "Lat: " + location.getLatitude() + "\n";
        reportDetails += "Lng: " + location.getLongitude() + "\n";
        return reportDetails;
    }
    @Override
    public String toString() {
        return "" + key + "| " + creationDate;
    }
}
