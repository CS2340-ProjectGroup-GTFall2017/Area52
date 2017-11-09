package area52.rat_tracking_application.controllers;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import area52.rat_tracking_application.R;
import area52.rat_tracking_application.model.RatReport;
import area52.rat_tracking_application.model.RatReportMap;
import area52.rat_tracking_application.model.ReportLocation;

import static area52.rat_tracking_application.model.RatReportMap.reports;

/**
 * RatReportCSVReader class reads in and parses csv file of rat reports
 * using the imported BufferedReader class.
 *
 * Created by Eric on 10/29/2017.
 */
public class RatReportCSVReader extends AppCompatActivity {



    public static List<Integer> wantedCSVColumnIndices = Arrays.asList(
            0, 1, 7, 8, 9, 16, 23, 49, 50);

    static List<String[]> preParsedList = new ArrayList<>();
    static List<String[]> newList = new ArrayList<>();
    public static List<String[]> newListString = new ArrayList<>();
    private File csvFile = null;

    private String[] headerLine = new String[9];

   public static RatReport csvExtractReport;
    RatReportCSVReader getNonStaticInstance() {
        return this;
    }

    /**
     * @param csvFile rat_sightings.csv
     */
    void readInFile(InputStream csvFile) throws IOException {
        String key;
        String latitude;
        String longitude;
        String locationType;
        String address;
        String city;
        String borough;
        String zipCode;
        String date;

        List<String> rowAsList;

        List<String> headerLineAsList;

        if (!reports.isEmpty()) {

            return;
        }
        try {

            BufferedReader csvReader = new BufferedReader(new InputStreamReader(csvFile));
            String nextLine;
            while ((nextLine = csvReader.readLine()) != null) {
                String[] next = nextLine.split(",");
                newList.add(next);
            }
            String[] newLine = new String[51];
            for (String[] rowToParse : newList) {
                int i = 0;
                List<String> rowList = Arrays.asList(rowToParse);
                for (String itemInRow : rowList) {
                    for (Integer idx : wantedCSVColumnIndices) {
                        if (rowList.indexOf(itemInRow) == idx) {
                            newLine[i++] = itemInRow;
                        }
                    }
                }

                RatReport csvExtractReport = new RatReport();
                System.out.println(Arrays.asList(newLine));

                newListString.add(newLine);

                key = newLine[0];//original index = 0
                csvExtractReport.setReportKey(key);

                date = newLine[1];//original index = 1
                csvExtractReport.setReportDate(date);

                locationType = newLine[2];//original index = 7

                zipCode = newLine[3];//original index = 8

                address = newLine[4];//original index = 9

                city = newLine[5];//original index = 16

                borough = newLine[6];//original index = 23

                latitude = newLine[7];//original index = 49

                longitude = newLine[8];//original index = 50
                csvExtractReport.setReportLocation(new ReportLocation(
                        locationType, zipCode, address, city, borough,
                        latitude, longitude));
                RatReportMap.addSingleReport(csvExtractReport);
            }
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "We're experiencing an issue with the specified input file",
                    Toast.LENGTH_SHORT).show();
        }
        newList = newListString;
        RatReportMap.setZipCodePositions();
    }

    Button launchReportsButton;

    void setCSVReference() {
        String csvFileString = this.getApplicationInfo().dataDir
                + File.separatorChar + "newRatFile.csv";
        csvFile = new File(csvFileString);
    }

    File getCSVReference() {
        return csvFile;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_report_loader);

        launchReportsButton = (Button) findViewById(R.id.launch_loader);

        launchReportsButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                loadRatReports();
                setCSVReference();
                setContextWithIntent(view);
            }

                void setContextWithIntent(View view) {
                Context context = view.getContext();
                Intent intent = new Intent(context, ReportListActivity.class);
                context.startActivity(intent);
            }
        });

    }

    public void loadRatReports() {

        RatReportMap reportMap = new RatReportMap();

        reportMap.launchMaps();

        try {

            InputStream csvReportFile = getResources().openRawResource(R.raw.rat_sightings_v2);

            readInFile(csvReportFile);

        } catch (IOException io_EXC) {

            io_EXC.printStackTrace();

        }
        String[] params = {"Unique Key", "Created Date", "Closed Date" , "Agency" , "Agency Name",
                "Complaint Type", "Descriptor" , "Location Type", "Incident Zip",
                "Incident Address", "Street Name", "Cross Street 1", "Cross Street 2",
                "Intersection Street 1", "Intersection Street 2", "Address Type", "City",
                "Landmark", "Facility Type", "Status", "Due Date", "Resolution Action Updated Date",
                "Community Board", "Borough", "X Coordinate (State Plane)",
                "Y Coordinate (State Plane)", "Park Facility Name", "Park Borough", "School Name",
                "School Number", "School Region", "School Code", "School Phone Number",
                "School Address", "School City", "School State", "School Zip",
                "School Not Found", "School or Citywide Complaint", "Vehicle Type",
                "Taxi Company Borough", "Taxi Pick Up Location", "Bridge Highway Name",
                "Bridge Highway Direction", "Road Ramp", "Bridge Highway Segment",
                "Garage Lot Name", "Ferry Direction", "Ferry Terminal Name",
                "Latitude", "Longitude", "Location"};

        /***List<Integer> wantedCSVColumnIndices = Arrays.asList(
                0, 1, 7, 8, 9, 16, 23, 49, 50);***/
    }
}