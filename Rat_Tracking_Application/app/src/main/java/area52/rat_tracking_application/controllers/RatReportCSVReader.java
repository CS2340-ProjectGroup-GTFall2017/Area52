package area52.rat_tracking_application.controllers;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import area52.rat_tracking_application.R;
import area52.rat_tracking_application.model.RatReportMap;
import area52.rat_tracking_application.model.ReportLocation;

import static area52.rat_tracking_application.model.RatReport.setReportDate;
import static area52.rat_tracking_application.model.RatReport.setReportKey;
import static area52.rat_tracking_application.model.RatReport.setReportLocation;
import static area52.rat_tracking_application.model.RatReportMap.launchMaps;
import static area52.rat_tracking_application.model.RatReportMap.reports;
import static area52.rat_tracking_application.model.RatReportMap.setReportKeyCreationDate;
/**
 * RatReportCSVReader class reads in and parses csv file of rat reports
 * using the imported BufferedReader class.
 *
 * Created by Eric on 10/29/2017.
 */

public class RatReportCSVReader extends AppCompatActivity {

    private static final String ARG_UNIQUE_KEY_ID = "Report ID";//0
    private static final String ARG_CREATED_DATE_ID = "Report Creation Date";//1
    private static final String ARG_LOCATION_TYPE_ID = "Incident Location Type";//7
    private static final String ARG_INCIDENT_ZIP_ID = "Zip Code";//8
    private static final String ARG_INCIDENT_ADDRESS_ID = "Address";//9
    private static final String ARG_CITY_ID = "City";//16
    private static final String ARG_BOROUGH_ID = "Borough";//23
    private static final String ARG_LATITUDE_ID = "Latitude";//49
    private static final String ARG_LONGITUDE_ID = "Longitude";//50

    static List<String> newList = new ArrayList<>();
    public static List<Integer> wantedCSVColumnIndices = Arrays.asList(
            0, 1, 7, 8, 9, 16, 23, 49, 50);
    static String[] parsedLine;
    public static List<String> parsedLineAsList;
    static String parsedLineForViewing;

    /**
     * @param csvFile rat_sightings.csv
     */
     static void readInFile(InputStream csvFile) throws IOException {

         parsedLine = new String[51];

         if (!reports.isEmpty()) {

             return;
         }

         BufferedReader csvReader = new BufferedReader(new InputStreamReader(csvFile));

         try {

             String headerLine = csvReader.readLine();
             String[] header = headerLine.split(",");
             List<String> headerRowAsList = Arrays.asList(header);

             for (String nextLine = csvReader.readLine();
                  nextLine != null;
                  nextLine = csvReader.readLine()) {

                 parsedLine = nextLine.split(",");

                 parsedLineAsList = Arrays.asList(parsedLine);

                 parsedLineForViewing = String.valueOf("[ " + headerRowAsList
                     + ARG_UNIQUE_KEY_ID + "= " + parsedLineAsList.get(wantedCSVColumnIndices.get(0)) + ", "
                     + ARG_CREATED_DATE_ID + "= " + parsedLineAsList.get(wantedCSVColumnIndices.get(1)) + ", "
                     + ARG_LOCATION_TYPE_ID + "= " + parsedLineAsList.get(wantedCSVColumnIndices.get(2)) + ", "
                     + ARG_INCIDENT_ZIP_ID + "= " + parsedLineAsList.get(wantedCSVColumnIndices.get(3)) + ", "
                     + ARG_INCIDENT_ADDRESS_ID + "= " + parsedLineAsList.get(wantedCSVColumnIndices.get(4)) + ", "
                     + ARG_CITY_ID + "= " + parsedLineAsList.get(wantedCSVColumnIndices.get(5)) + ", "
                     + ARG_BOROUGH_ID + "= " + parsedLineAsList.get(wantedCSVColumnIndices.get(6)) + ", "
                     + ARG_LATITUDE_ID + "= " + parsedLineAsList.get(wantedCSVColumnIndices.get(7)) + ", "
                     + ARG_LONGITUDE_ID + "= " + parsedLineAsList.get(wantedCSVColumnIndices.get(8)) + " ]");

                 newList.add(parsedLineForViewing);

                 setReportKey();

                 setReportLocation(new ReportLocation(
                         parsedLineAsList.get(wantedCSVColumnIndices.get(7)),// latitude
                         parsedLineAsList.get(wantedCSVColumnIndices.get(8)),// longitude
                         parsedLineAsList.get(wantedCSVColumnIndices.get(2)),// location type
                         parsedLineAsList.get(wantedCSVColumnIndices.get(4)),// address
                         parsedLineAsList.get(wantedCSVColumnIndices.get(5)),// city
                         parsedLineAsList.get(wantedCSVColumnIndices.get(6)),// borough
                         Integer.valueOf(parsedLineAsList.get(wantedCSVColumnIndices.get(3)))));// zip code

                 setReportDate();

                 RatReportMap.addSingleReport();

                 setReportKeyCreationDate();

             }

         } catch (IOException e) {

             e.printStackTrace();

         }
         RatReportMap.setZipCodePositions();
    }

    Button launchReportsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_report_loader);

        launchReportsButton = (Button) findViewById(R.id.launch_loader);

        launchReportsButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Context context = view.getContext();
                Intent intent = new Intent(context, ReportListActivity.class);
                context.startActivity(intent);
            }
        });

        loadRatReports();
    }

    public void loadRatReports() {

        launchMaps();

        try {

            InputStream csvReportFile = getResources().openRawResource(R.raw.rat_sightings_v2);

            readInFile(csvReportFile);

        } catch (IOException io_EXC) {

            io_EXC.printStackTrace();

        }
    }
}