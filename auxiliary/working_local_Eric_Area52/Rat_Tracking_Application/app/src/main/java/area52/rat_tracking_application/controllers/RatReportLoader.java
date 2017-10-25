package area52.rat_tracking_application.controllers;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.ScrollingTabContainerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.support.v7.app.AppCompatActivity;

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

import area52.rat_tracking_application.R;
import area52.rat_tracking_application.model.RatReport;
import area52.rat_tracking_application.model.ReportLocation;

import static java.lang.System.exit;

public class RatReportLoader extends AppCompatActivity {
    public static HashMap<Long, RatReport> reports;
    private static HashMap<String, Integer> indexOfCSVColumn;
    private static String[] wantedCSVColumns = {"Unique Key", "Created Date", "Location Type",
            "Incident Zip", "Incident Address", "City", "Borough", "Latitude",
            "Longitude"};

    private static final String TAG = "ReportListActivity";
    private Runnable url1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_list);
        new ReportLoaderExtender().execute(url1);
    }



    ScrollingTabContainerView scrollingTab = (ScrollingTabContainerView) findViewById(R.id.tab_scrolling);
    scrollingTab.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Context context = view.getContext();
            Intent intent = new Intent(context, WelcomeActivity.class);
            context.startActivity(intent);
        }
    }


    private class ReportLoaderExtender extends AsyncTask<Object, Object, ReportDetailFragment.SimpleStudentRecyclerViewAdapter> {
        private RatReportLoader reportLoader = new RatReportLoader();
        private static final String TAG = "Rat Reports Loading ";
        protected ReportDetailActivity.ReportListAdapter doInBackground(Object... urls) {
            reportLoader.loadRatReports();
            return new ReportDetailActivity.ReportListAdapter(R.layout.activity_report_list, );
        }

        protected void onProgressUpdate(Object... progress) {
            int i = 0;
            setProgressPercent(progress[i++]);
        }

        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d(TAG, "onPostExecute: parameter is " + s);
        }

        protected void onCancelled() {
            exit(0);
        }
    }

    RatReportLoader() {
        if (reports == null) {
            reports = new HashMap<>();
        }
        indexOfCSVColumn = new HashMap<>();
    }
    private void loadRatReports() {
        InputStream csvReportFile = getResources().openRawResource(R.raw.rat_sightings);
        loadRatReportsFromCSV(csvReportFile);
    }

    public static List<String> getReports() {
        String keyCreationDate = wantedCSVColumns[0] + wantedCSVColumns[1];
        List<String> keyCreationDateList = new ArrayList<>();
        int i = 0;
        for (RatReport report : reports.values()) {
            if (keyCreationDate == null) {
                keyCreationDateList.add(keyCreationDate);
            }
        }
        return keyCreationDateList;
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
            String csvHeaderLine = csvReader.readLine();

            String[] headerRow = csvHeaderLine.split(",");
            getIndicesOfWantedCSVColumns(headerRow);

            String currentLine = "";
            int count = 0;
            for (int i = 0; (currentLine = csvHeaderLine) != null; i++) {
                try
                {
                    publishProgress((int) ((i / (float) count) * 100));

                    String[] row = currentLine.split(",");
                    RatReport reportFromFile = convertCSVRowToRatReport(row);
                    reports.put(reportFromFile.getKey(), reportFromFile);
                }
                catch (IOException e)
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

    private void getIndicesOfWantedCSVColumns(String[] csvHeader) {
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
     * HashMap<Long, RatReport> reports Key
     *
     * Extracts unique key from instance of rat report, with each reports unique key
     * corresponding with a row in the provided csv file, always at column index 0.
     * Returns unique key of primitive type long if isNum(keyString) returns true,
     * & otherwise returns 0 if isNum(keyString) returns false.
     *
     * @param csvRow
     * @return
     */
    public static long getReportKey(String[] csvRow) {
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
    public static RatReport convertCSVRowToRatReport(String[] csvRow) {
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

    private static boolean isNum(String toBeChecked) {
        try {
            double d = Double.parseDouble(toBeChecked);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }
    private static Date getReportDate(String[] csvRow) {
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
    private static int getReportZip(String[] csvRow) {
        String zipString = getCSVStringForColumn(wantedCSVColumns[3], csvRow);
        return (isNum(zipString)) ? Integer.valueOf(zipString) : 0;
    }
    private static double getReportLatitude(String[] csvRow) {
        String latitudeString = getCSVStringForColumn(wantedCSVColumns[7], csvRow);
        return (isNum(latitudeString)) ? Double.valueOf(latitudeString) : 0;
    }
    private static double getReportLongitude(String[] csvRow) {
        String longitudeString = getCSVStringForColumn(wantedCSVColumns[8], csvRow);
        return (isNum(longitudeString)) ? Double.valueOf(longitudeString) : 0;
    }

    private static String getCSVStringForColumn(String wantedColumn, String[] csvRow) {
        int columnIndex = indexOfCSVColumn.get(wantedColumn);
        boolean columnIndexIsValid = columnIndex > -1 && columnIndex < csvRow.length;
        return (columnIndexIsValid) ? csvRow[columnIndex] : "";
    }
}
