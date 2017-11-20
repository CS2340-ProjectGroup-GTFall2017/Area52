package area52.rat_tracking_application.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class RatReportLoader {
    /**
     * Singleton pattern, the most original, clever, not-at-all-lazy piece of design appropriate for
     * this situation
     */
    private static RatReportLoader instance = new RatReportLoader();

    private HashMap<String, Integer> indexOfCSVColumn;
    private String[] wantedCSVColumns = {"Unique Key", "Created Date", "Location Type",
            "Incident Zip", "Incident Address", "City", "Borough", "Latitude",
            "Longitude"};

    public RatReportLoader() {
        indexOfCSVColumn = new HashMap<>();
    }

    public static RatReportLoader getInstance() { return instance; }

    /*
     * From the given CSV InputStream, adds all rat reports in the stream to memory
     */
    public List<RatReport> getRatReportsFromCSV(InputStream csvInput) {
        ArrayList<RatReport> csvRatReports = new ArrayList<>();
        BufferedReader csvReader = new BufferedReader(new InputStreamReader(csvInput));

        try {
            String csvHeaderLine = csvReader.readLine();
            String[] headerRow = csvHeaderLine.split(",");
            getIndicesOfWantedCSVColumns(headerRow);

            String currentLine;
            while ((currentLine = csvReader.readLine()) != null) {
                String[] row = currentLine.split(",");
                RatReport reportFromFile = convertCSVRowToRatReport(row);

                csvRatReports.add(reportFromFile);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return csvRatReports;
    }

    private void getIndicesOfWantedCSVColumns(String[] csvHeader) {
        for (int i = 0; i < csvHeader.length; i++) {
            if (contains(wantedCSVColumns, csvHeader[i])) {
                indexOfCSVColumn.put(csvHeader[i], i);
            }
        }
    }

    public boolean contains(String[] arr, String element) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i].equals(element)) { return true; }
        }
        return false;
    }

    private RatReport convertCSVRowToRatReport(String[] csvRow) {
        long key = getReportKey(csvRow);
        Date creationDate = getReportDate(csvRow);
        String locationType = getCSVStringForColumn(wantedCSVColumns[2], csvRow);
        int zip = getReportZip(csvRow);
        String address = getCSVStringForColumn(wantedCSVColumns[4], csvRow);
        String city = getCSVStringForColumn(wantedCSVColumns[5], csvRow);
        String borough = getCSVStringForColumn(wantedCSVColumns[6], csvRow);
        double lat = getReportLatitude(csvRow);
        double lng = getReportLongitude(csvRow);

        ReportLocation location = new ReportLocation(lat, lng, locationType, address,
                city, borough, zip);
        RatReport report = new RatReport(key, creationDate, location);

        return report;
    }

    private long getReportKey(String[] csvRow) {
        String keyString = getCSVStringForColumn(wantedCSVColumns[0], csvRow);
        return (isNum(keyString)) ? Long.valueOf(keyString) : 0;
    }
    public boolean isNum(String toBeChecked) {  //TODO: Move this to an interface-style class
        try {
            double d = Double.parseDouble(toBeChecked);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }
    private Date getReportDate(String[] csvRow) {
        String dateString = getCSVStringForColumn(wantedCSVColumns[1], csvRow);
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a");
        Date date = new Date();
        try {
            date = dateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
    private int getReportZip(String[] csvRow) {
        String zipString = getCSVStringForColumn(wantedCSVColumns[3], csvRow);
        return (isNum(zipString)) ? Integer.valueOf(zipString) : 0;
    }
    private double getReportLatitude(String[] csvRow) {
        String latitudeString = getCSVStringForColumn(wantedCSVColumns[7], csvRow);
        return (isNum(latitudeString)) ? Double.valueOf(latitudeString) : 0;
    }
    private double getReportLongitude(String[] csvRow) {
        String longitudeString = getCSVStringForColumn(wantedCSVColumns[8], csvRow);
        return (isNum(longitudeString)) ? Double.valueOf(longitudeString) : 0;
    }

    private String getCSVStringForColumn(String wantedColumn, String[] csvRow) {
        int columnIndex = indexOfCSVColumn.get(wantedColumn);
        boolean columnIndexIsValid = columnIndex > -1 && columnIndex < csvRow.length;
        return (columnIndexIsValid) ? csvRow[columnIndex] : "";
    }
}
