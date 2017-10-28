package area52.rat_tracking_application.controllers;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Bundle;
import android.icu.text.SimpleDateFormat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import area52.rat_tracking_application.R;
import area52.rat_tracking_application.model.RatReport;
import area52.rat_tracking_application.model.ReportLocation;

public class RatReportLoader extends Activity {
    private static RatReport<Long, ReportLocation> ratReport;
    static HashMap<Long, RatReport> reports;
    protected static HashMap<String, Integer> indexOfCSVColumn;
    protected static String[] wantedCSVColumns = {"Unique Key", "Created Date", "Location Type",
            "Incident Zip", "Incident Address", "City", "Borough", "Latitude",
            "Longitude"};
    private static String keyCreationDate;
    private static List<String> keyCreationDateList;
    private static ReportLocation location;
    private ReportLocation newLocation;
    private static List<ReportLocation> reportLocations = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_list);

    }

    /***RecyclerView.SmoothScroller.ScrollVectorProvider scrollingTab = (ScrollingTabContainerView) findViewById(R.id.tab_scrolling);
    scrollingTab.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view){
            Context context = view.getContext();
            Intent intent = new Intent(context, WelcomeActivity.class);
            context.startActivity(intent);
        }
    }***/


    RatReportLoader() {
        if (reports == null) {
            reports = new HashMap<>();
        }
        indexOfCSVColumn = new HashMap<>();
        loadRatReports();
    }
    public void loadRatReports() {
        InputStream csvReportFile = getResources().openRawResource(R.raw.rat_sightings);
        loadRatReportsFromCSV(csvReportFile);
    }

    /***
     * What appears in scrolling list view of rat reports
     *
     * @return keyCreationDateList from instance of rat report
     * and its overridden toString() method
     */
    protected static List<String> getReportKeysCreationDates() {
        keyCreationDate = wantedCSVColumns[0] + wantedCSVColumns[1];
        keyCreationDateList = new ArrayList<>();
        for (RatReport report : reports.values()) {
            if (keyCreationDate != null) {
                if (report.toString().equals(keyCreationDate)) {
                    keyCreationDateList.add(keyCreationDate);
                }
            }
        }
        return keyCreationDateList;
    }

    protected static List<ReportLocation> getLocations() {
        return reportLocations;
    }

    protected static void setNewLocation(ReportLocation loc) {
         ReportLocation newLocation = loc;
         location = newLocation;
    }

    protected static ReportLocation getNewLocation() {
        return location;
    }

    public static boolean addSingleReport(Long key, ReportLocation location) {

        //go through each report looking for duplicate incident   O(n^2)
        for (Long k : reports.keySet()) {
            for (RatReport v : reports.values()) {
                if (k.equals(key) || v.getLocation().equals(location)) {
                    //found duplicate key, or a duplicate <key, value> mapping
                    // don't add and return failure signal
                    return false;
                }
            }
        }
        //never found the key or <key, value> mapping, so safe to add it.

        //return the success signal
        return true;
    }

    protected static RatReport createReport(Long key, ReportLocation location) {
        RatReport<Long, ReportLocation> report = new RatReport(null, null);
        if (addSingleReport(key, location)) {
            report.put(key, location);
            reports.put(key, report);
            return ratReport;
        }
        return null;
    }

    /*
     * From the given CSV InputStream, adds all rat reports in the stream to memory
     */
    private void loadRatReportsFromCSV(InputStream csvInput) {
        if (!reports.isEmpty()) {
            return;
        }
        BufferedReader csvReader = new BufferedReader(new InputStreamReader(csvInput));

        try
        {
            int count = 0;
            String csvHeaderLine = csvReader.readLine();

            String[] headerRow = csvHeaderLine.split(",");
            setIndicesOfWantedCSVColumns(headerRow);

            String currentLine = "";

            for (currentLine = csvHeaderLine; currentLine != null; currentLine = csvReader.readLine()) {
                try
                {
                    //publishProgress((int) ((i / (float) count) * 100));

                    String[] row = currentLine.split(",");
                    convertCSVRowToRatReport(row);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private void setIndicesOfWantedCSVColumns(String[] csvHeader) {
        for (int i = 0; i < csvHeader.length; i++) {
            if (contains(wantedCSVColumns, csvHeader[i])) {
                indexOfCSVColumn.put(csvHeader[i], i);
            }
        }
    }

    private boolean contains(String[] arr, String element) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i].equals(element)) { return true; }
        }
        return false;
    }

    /**
     * HashMap<K, V> reports K
     *
     * Extracts unique key from instance of rat report, with each reports unique key
     * corresponding with a row in the provided csv file, always at column index 0.
     * Returns unique key of primitive type long if isNum(keyString) returns true,
     * & otherwise returns 0 if isNum(keyString) returns false.
     *
     * @param csvRow
     * @return
     */
    protected static long getReportKey(String[] csvRow) {
        String keyString = getCSVStringForColumn(wantedCSVColumns[0], csvRow);
        return (isNum(keyString)) ? Long.valueOf(keyString) : 0;
    }

    /**
     * HashMap<Long, RatReport> reports Value, representing a row in the
     * csv file, created by indexing the columns of each of the input streamed csv file's
     * rows, and consisting of a reports key, creation date, and reported location info.
     *
     * @param csvRow
     * @return report instance of RatReport
     */
    protected static ReportLocation convertCSVRowToRatReport(String[] csvRow) {
        /**
         * String uniqueKey = getCSVStringForColumn(wantedCSVColumns[0], csvRow);
         * String creationDate = getCSVStringForColumn(wantedCSVColumns[1], csvRow);
         * String locationType = getCSVStringForColumn(wantedCSVColumns[2], csvRow);
         * String zipCode = getCSVStringForColumn(wantedCSVColumns[3], csvRow);
         * String address = getCSVStringForColumn(wantedCSVColumns[4], csvRow);
         * String city = getCSVStringForColumn(wantedCSVColumns[5], csvRow);
         * String borough = getCSVStringForColumn(wantedCSVColumns[6], csvRow);
         * String latitude = getCSVStringForColumn(wantedCSVColumns[7], csvRow);
         * String longitude = getCSVStringForColumn(wantedCSVColumns[8], csvRow);
         */

        location = new ReportLocation(getReportDate(csvRow), getReportLatitude(
                csvRow), getReportLongitude(csvRow), getReportLocationType(
                csvRow), getReportAddress(csvRow), getReportCity(csvRow),
                getReportBorough(csvRow), getReportZip(csvRow));

        createReport(getReportKey(csvRow), location);
        return location;
    }

    protected static String getCSVStringForColumn(String wantedColumn, String[] csvRow) {
        int columnIndex = indexOfCSVColumn.get(wantedColumn);
        boolean columnIndexIsValid = columnIndex > -1 && columnIndex < csvRow.length;
        return (columnIndexIsValid) ? csvRow[columnIndex] : "";
    }

    protected static boolean isNum(String toBeChecked) {
        try {
            double d = Double.parseDouble(toBeChecked);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    @TargetApi(25)
    protected static String getReportDate(String[] csvRow) {
        String dateString = getCSVStringForColumn(wantedCSVColumns[1], csvRow);
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a");
        Date date = new Date();
        try {
            date = dateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date.toString();
    }

    protected static String getReportLocationType(String[] csvRow) {
        String locationTypeString = getCSVStringForColumn(wantedCSVColumns[2], csvRow);
        return (!isNum(locationTypeString)) ? String.valueOf(locationTypeString) : "";
    }

    protected static int getReportZip(String[] csvRow) {
        String zipString = getCSVStringForColumn(wantedCSVColumns[3], csvRow);
        return (isNum(zipString)) ? Integer.valueOf(zipString) : 0;
    }

    protected static String getReportAddress(String[] csvRow) {
        String addressString = getCSVStringForColumn(wantedCSVColumns[4], csvRow);
        return (!isNum(addressString)) ? String.valueOf(addressString) : "";
    }

    protected static String getReportCity(String[] csvRow) {
        String cityString = getCSVStringForColumn(wantedCSVColumns[5], csvRow);
        return (!isNum(cityString)) ? String.valueOf(cityString) : "";
    }
    protected static String getReportBorough(String[] csvRow) {
        String boroughString = getCSVStringForColumn(wantedCSVColumns[6], csvRow);
        return (!isNum(boroughString)) ? String.valueOf(boroughString) : "";
    }

    protected static double getReportLatitude(String[] csvRow) {
        String latitudeString = getCSVStringForColumn(wantedCSVColumns[7], csvRow);
        return (isNum(latitudeString)) ? Double.valueOf(latitudeString) : 0;
    }
    protected static double getReportLongitude(String[] csvRow) {
        String longitudeString = getCSVStringForColumn(wantedCSVColumns[8], csvRow);
        return (isNum(longitudeString)) ? Double.valueOf(longitudeString) : 0;
    }

    protected static HashMap<String, Integer> getCSVHeaderIndices() {
        return indexOfCSVColumn;
    }
}
