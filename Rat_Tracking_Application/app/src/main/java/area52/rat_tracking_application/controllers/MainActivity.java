package area52.rat_tracking_application.controllers;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import area52.rat_tracking_application.R;
import area52.rat_tracking_application.model.Model;
import area52.rat_tracking_application.model.RatReport;
import area52.rat_tracking_application.model.ReportLocation;
import area52.rat_tracking_application.model.User;

import static area52.rat_tracking_application.R.layout.activity_main;
import static area52.rat_tracking_application.model.ReportLocation.setZipCodePositions;

/**
 * Temp representation of login screen
 * and branching to other activities in controllers
 * package
 */

public class MainActivity extends Activity {
    Button loginButton;
    Button cancelButton;
    Button registrationButton;
    EditText usernameEntry;
    EditText passwordEntry;
    TextView textViewOne;
    private int securityCounter = 3;
    boolean match = false;

    /**
     * the currently logged in user
     */
    private static User _currentUser;

    private static List<String> reportList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_main);
        setZipCodePositions();
        setupButtonsOnStartup();
    }

    /**
     * Gets the currently logged in user
     *
     * @return the currently logged in user
     */
    public static User getCurrentUser() {
        return _currentUser;
    }

    /**
     * Sets the currently logged in user
     *
     * @param user the currently logged in user
     */
    public static void setCurrentUser(User user) {
        _currentUser = user;
    }

    public void setupButtonsOnStartup() {
        loginButton = (Button)findViewById(R.id.button);
        cancelButton = (Button)findViewById(R.id.button2);
        registrationButton = (Button)findViewById(R.id.button3);
        usernameEntry = (EditText)findViewById(R.id.editText);
        passwordEntry = (EditText)findViewById(R.id.editText2);
        textViewOne = (TextView)findViewById(R.id.textView3);
        textViewOne.setVisibility(View.GONE);

        loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (Model.getInstance().getUserMap().containsKey(
                        usernameEntry.getText().toString())) {
                    if (passwordEntry.getText().toString().equals(
                            Model.getInstance().getUserMap().get(
                                    usernameEntry.getText().toString()).getPWord())) {
                        setCurrentUser(Model.getInstance().getUserMap().get(
                                usernameEntry.getText().toString()));
                        Toast.makeText(getApplicationContext(),
                                "Welcome to the NYC Rat Tracking System, "
                                        + getCurrentUser() + " !",
                                Toast.LENGTH_LONG).show();
                        match = true;
                        Context reportDetailContext = view.getContext();
                        Intent reportDetailIntent = new Intent(
                                reportDetailContext, ReportDetailActivity.class);
                        reportDetailContext.startActivity(reportDetailIntent);
                    }
                } else {
                    if (!match) {
                        Toast.makeText(getApplicationContext(),
                                "Incorrect Login Info",
                                Toast.LENGTH_SHORT).show();
                        textViewOne.setVisibility(View.VISIBLE);
                        textViewOne.setBackgroundColor(Color.WHITE);
                        securityCounter--;
                        textViewOne.setText(((Integer) securityCounter).toString());
                        if (securityCounter <= 0) {
                            loginButton.setEnabled(false);
                        }
                    }
                }
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Context context = view.getContext();
                Intent intent = new Intent(context, WelcomeActivity.class);
                context.startActivity(intent);
            }
        });

        registrationButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Toast.makeText(
                        getApplicationContext(),
                        "Please register on next screen",
                        Toast.LENGTH_LONG).show();
                Context registrationContext = view.getContext();
                Intent registrationIntent = new Intent(registrationContext, RegistrationActivity.class);
                registrationContext.startActivity(registrationIntent);
            }
        });


    }
    static class RatReportLoader extends AppCompatActivity {
        private static RatReport<Long, ReportLocation> ratReport;
        static HashMap<Long, RatReport> reports;
        protected static HashMap<String, Integer> indexOfCSVColumn;
        protected static List<String> wantedCSVColumns = Arrays.asList("Unique Key", "Created Date", "Location Type",
                "Incident Zip", "Incident Address", "City", "Borough", "Latitude",
                "Longitude");
        private static String keyCreationDate;
        private static List<String> keyCreationDateList = new ArrayList<>();
        private static ReportLocation location;
        private static List<ReportLocation> reportLocations = new ArrayList<>();

        static void launchLoader() {
            if (reports == null) {
                reports = new HashMap<>();
            }
            indexOfCSVColumn = new HashMap<>();
            loadRatReportsFromCSV();
        }

        /**
         * setCreationDate from instance of rat report to add to
         * keyCreationDateList
         *
         * @param a row index of rat report instance's unique key.
         * @param b row index of rat report instance's creation date.
         */
        protected static void setReportKeysCreationDates(int a, int b) {
            keyCreationDate = wantedCSVColumns.get(a) + wantedCSVColumns.get(b);
            if (keyCreationDate != null) {
                keyCreationDateList.add(keyCreationDate);
            }
        }

        /**
         *
         * @return keyCreationDateList unique key and creation date of each rat report instance
         * in the csv file will be saved and displayed in the drop down selection menu,
         * for viewing of individual rat report details. New entries will also have their unique keys
         * and creation dates added to this list.
         */
        protected static List<String> getReportKeysCreationDates() {
            return keyCreationDateList;
        }

        /**
         * sets a new rat report location during a new report entry activity
         *
         * @param newLocation a new rat report instance's location. Accessed during a
         * report entry activity
         */
        protected static void setNewLocation(ReportLocation newLocation) {
            location = newLocation;
        }

        /**
         *
         * @return location the newly entered rat report instance's location.
         */
        protected static ReportLocation getNewLocation() {
            return location;
        }

        /**
         * boolean check to verify existence of duplicate key, or duplicate
         * <key, value> mapping
         *
         * If the check fails and returns false, the method won't add and return,
         * and a failure signal is provided to the user by the ui.
         *
         * If it's determined that a safe add of the report can be performed, then the
         * <key, value> pair is added to the map for this report instance, and a success
         * signal is provided to the user by the ui.
         *
         * @param key report instance's unique key.
         * @param location report instance's reported location.
         * @return boolean true if item can be added safely (i.e. no duplicate keys
         * or <key, value> mappings), and otherwise return false.
         */
        public static boolean addSingleReport(Long key, ReportLocation location) {
            for (Long k : reports.keySet()) {
                for (RatReport v : reports.values()) {
                    if (k.equals(key) || v.getLocation().equals(location)) {
                        return false;
                    }
                }
            }
            return true;
        }

        static RatReport createReport(Long key, ReportLocation location) {
            RatReport<Long, ReportLocation> report = new RatReport(null, null);
            if (addSingleReport(key, location)) {
                report.put(key, location);
                reports.put(key, report);
                setReportKeysCreationDates(0, 1);
                return ratReport;
            }
            return null;
        }

        /**
         * From the given CSV InputStream, adds all rat reports in the stream to memory,
         * with each row of the streamed in CSV being partitioned into string arrays.         *
         *
         */
        static void loadRatReportsFromCSV() {
            try {
                setUpToPartitionCSV();
                reportList = getToPartitionCSV();
                String csvHeaderLine = reportList.get(0);
                String[] headerRow = csvHeaderLine.split(",");
                setIndicesOfWantedCSVColumns(headerRow);
                reportList.remove(0);
                for (String row : reportList) {
                    String[] stringPartitionedRow = row.split(",");
                    setIndicesOfWantedCSVColumns(stringPartitionedRow);
                    convertCSVRowToRatReport(stringPartitionedRow);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private static List<String> result;

        static void setUpToPartitionCSV() throws java.io.IOException {
            URL ratSightingsGitSourcedCSV = new URL("https://media.githubusercontent.com/media/" +
                    "CS2340-ProjectGroup-GTFall2017/Area52/working/Rat_Tracking_Application/app/" +
                    "src/main/res/raw/rat_sightings.csv");
            BufferedReader csvReader = new BufferedReader(
                    new InputStreamReader(ratSightingsGitSourcedCSV.openStream()));

            List<String> csvStringList = new ArrayList<>();

            int i = 0;
            String csvLine;
            while ((csvLine = csvReader.readLine()) != null) {
                if (i < 100) {
                    csvStringList.add(csvLine);
                }
                i++;
            }
            csvReader.close();
            System.out.println(csvStringList);
            result = csvStringList;
        }

        private static List<String> getToPartitionCSV() {
            return result;
        }

        private static void setIndicesOfWantedCSVColumns(String[] csvHeader) {
            for (int i = 0; i < csvHeader.length; i++) {
                if (contains(wantedCSVColumns, csvHeader[i])) {
                    indexOfCSVColumn.put(csvHeader[i], i);
                }
            }
        }

        private static boolean contains(List<String> columns, String element) {
            for (int i = 0; i < columns.size(); i++) {
                if (columns.get(i).equals(element)) { return true; }
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
            String keyString = getCSVStringForColumn(wantedCSVColumns.get(0), csvRow);
            return (isNum(keyString)) ? Long.valueOf(keyString) : 0;
        }

        /**
         * HashMap<Long, RatReport> reports Value, representing a row in the
         * csv file, created by indexing the columns of each of the input streamed csv file's
         * rows, and consisting of a reports key, creation date, and reported location info.
         *
         * @param csvRow
         * @return reportLocation instance of RatReport
         */
        protected static void convertCSVRowToRatReport(String[] csvRow) {

            location = new ReportLocation(getReportDate(csvRow), getReportLatitude(
                    csvRow), getReportLongitude(csvRow), getReportLocationType(
                    csvRow), getReportAddress(csvRow), getReportCity(csvRow),
                    getReportBorough(csvRow), getReportZip(csvRow));

            createReport(getReportKey(csvRow), location);
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
            String dateString = getCSVStringForColumn(wantedCSVColumns.get(1), csvRow);
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
            String locationTypeString = getCSVStringForColumn(wantedCSVColumns.get(2), csvRow);
            return (!isNum(locationTypeString)) ? String.valueOf(locationTypeString) : "";
        }

        protected static int getReportZip(String[] csvRow) {
            String zipString = getCSVStringForColumn(wantedCSVColumns.get(3), csvRow);
            return (isNum(zipString)) ? Integer.valueOf(zipString) : 0;
        }

        protected static String getReportAddress(String[] csvRow) {
            String addressString = getCSVStringForColumn(wantedCSVColumns.get(4), csvRow);
            return (!isNum(addressString)) ? String.valueOf(addressString) : "";
        }

        protected static String getReportCity(String[] csvRow) {
            String cityString = getCSVStringForColumn(wantedCSVColumns.get(5), csvRow);
            return (!isNum(cityString)) ? String.valueOf(cityString) : "";
        }
        protected static String getReportBorough(String[] csvRow) {
            String boroughString = getCSVStringForColumn(wantedCSVColumns.get(6), csvRow);
            return (!isNum(boroughString)) ? String.valueOf(boroughString) : "";
        }

        protected static double getReportLatitude(String[] csvRow) {
            String latitudeString = getCSVStringForColumn(wantedCSVColumns.get(7), csvRow);
            return (isNum(latitudeString)) ? Double.valueOf(latitudeString) : 0;
        }
        protected static double getReportLongitude(String[] csvRow) {
            String longitudeString = getCSVStringForColumn(wantedCSVColumns.get(8), csvRow);
            return (isNum(longitudeString)) ? Double.valueOf(longitudeString) : 0;
        }

        protected static HashMap<String, Integer> getCSVHeaderIndices() {
            return indexOfCSVColumn;
        }
    }

}

