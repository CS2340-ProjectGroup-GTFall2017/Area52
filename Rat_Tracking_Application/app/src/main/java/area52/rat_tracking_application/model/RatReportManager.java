package area52.rat_tracking_application.model;

import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by 2016d on 10/31/2017.
 */

public class RatReportManager implements Serializable {
    private static HashMap<Long, RatReport> reports;
    private List<RatReport> internalRatReports;

    /**
     * Singleton Pattern
     */
    private static RatReportManager instance = new RatReportManager();

    public RatReportManager() { //TODO: Include loading persistent data by default
        internalRatReports = new ArrayList<>();
        reports = new HashMap<>();
    }

    /**
     * Adds all rat reports in the CSV file to the list of current rat reports
     * @param csvInput InputStream containing the CSV file data
     */
    public void loadRatReports(InputStream csvInput) {
        internalRatReports = RatReportLoader.getInstance().getRatReportsFromCSV(csvInput);
        copyInternalReportsToHashMap();
    }

    /**
     * Puts all ratReports in internalRatReports into the "reports" HashMap
     */
    private void copyInternalReportsToHashMap() {
        for (RatReport report : internalRatReports) {
            reports.put(report.getKey(), report);
        }
    }

    public static RatReportManager getInstance() { return instance; }
    public static HashMap<Long, RatReport> getReportsHashMap() { return reports; }
}
