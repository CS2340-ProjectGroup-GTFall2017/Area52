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
import java.util.Arrays;
import java.util.List;

import area52.rat_tracking_application.R;
import area52.rat_tracking_application.model.RatReportMap;

import static area52.rat_tracking_application.model.RatReportMap.launchMaps;
import static area52.rat_tracking_application.model.RatReportMap.reports;
import static area52.rat_tracking_application.model.RatReportMap.setReportKeyCreationDate;
import static area52.rat_tracking_application.model.ReportLocation.setZipCodePositions;

/**
 * RatReportCSVReader class reads in and parses csv file of rat reports
 * using the imported BufferedReader class.
 *
 * Created by Eric on 10/29/2017.
 */

public class RatReportCSVReader extends AppCompatActivity {

    static final String ARG_UNIQUE_KEY_ID = "unique_key_id";//1
    static final String ARG_CREATED_DATE_ID = "created_date_id";//2
    static final String ARG_LOCATION_TYPE_ID = "location_type_id";//3
    static final String ARG_INCIDENT_ZIP_ID = "incident_zip_id";//4
    static final String ARG_INCIDENT_ADDRESS_ID = "incident_adress_id";//5
    static final String ARG_CITY_ID = "city_id";//6
    static final String ARG_BOROUGH_ID = "borough_id";//7
    static final String ARG_LATITUDE_ID = "latitude_id";//8
    static final String ARG_LONGITUDE_ID = "longitude_id";//9

    static List<String> newList;
    public static List<Integer> wantedCSVColumnIndices = Arrays.asList(
            0, 1, 7, 8, 9, 16, 23, 49, 50);
    static String[] parsedLine;
    public static List<String> parsedLineAsList;
    static String parsedLineForViewing;

    /**
     * @param csvFile rat_sightings.csv
     */
     static void readInFile(InputStream csvFile) throws IOException {

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

                 RatReportMap.addSingleReport();

                 setReportKeyCreationDate();

             }

         } catch (IOException e) {

             e.printStackTrace();

         }
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

        InputStream ratReportFile = getResources().openRawResource(R.raw.rat_sightings);

        try {

            readInFile(ratReportFile);

            setZipCodePositions();

        } catch (Exception e) {

            e.printStackTrace();

        }
    }
}