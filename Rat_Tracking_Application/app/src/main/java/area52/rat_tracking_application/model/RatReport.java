package area52.rat_tracking_application.model;

import java.util.Date;

/**
 * Created by 2016d on 10/16/2017.
 */

public class RatReport {
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
}
