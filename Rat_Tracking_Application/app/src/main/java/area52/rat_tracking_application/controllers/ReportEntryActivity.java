package area52.rat_tracking_application.controllers;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import area52.rat_tracking_application.R;
import area52.rat_tracking_application.model.RatReport;
import area52.rat_tracking_application.model.ReportLocation;

import static area52.rat_tracking_application.controllers.RatReportCSVReader.ARG_BOROUGH_ID;
import static area52.rat_tracking_application.controllers.RatReportCSVReader.ARG_CITY_ID;
import static area52.rat_tracking_application.controllers.RatReportCSVReader.ARG_INCIDENT_ZIP_ID;
import static area52.rat_tracking_application.controllers.RatReportCSVReader.ARG_LOCATION_TYPE_ID;
import static area52.rat_tracking_application.controllers.RatReportCSVReader.ARG_UNIQUE_KEY_ID;
import static area52.rat_tracking_application.controllers.RatReportCSVReader.parsedLineAsList;
import static area52.rat_tracking_application.controllers.RatReportCSVReader.wantedCSVColumnIndices;
import static area52.rat_tracking_application.model.RatReportMap.reports;
import static area52.rat_tracking_application.model.ReportLocation.cityList;
import static area52.rat_tracking_application.model.ReportLocation.locationTypes;
import static area52.rat_tracking_application.model.ReportLocation.nycBoroughs;
import static area52.rat_tracking_application.model.ReportLocation.nycZipCodes;

/**
 * Acknowledgements:
 *
 * Prof. Bob Waters ([Template provided by Prof. Waters] for Android
 * project guidance [GaTech - Fall 2017 - cs2340 - Objects & Design]).
 * Classes, methods, method params, instance and local variables named
 * to reflect our [class final project] --> [Rat Tracking App]:
 *
 * An activity representing a single Rat Report Entry detail screen.
 *
 * Created by Eric on 10/24/2017.
 */

public class ReportEntryActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {


    private TextView adminGeneratedUniqueKey;
    private String reportBorough;
    private String reportZipCode;
    private String reportCity;
    private String reportLocationType;
    private Long key;
    /* ***********************
           Data for report being entered.
         */
    private String report;

    private RatReport _report;

    private ReportLocation reportLocation;

    /* ***********************
       flag for whether this is a new report being added (currently unused);
     */
    private boolean creating;

    public ReportEntryActivity() {
        super();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_entry);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Report a Rat Sighting", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        /**
         * Grab the dialog widgets so we can get info for later
         */
        TextView username = (TextView) findViewById(R.id.user_name);
        Spinner boroughSpinner = (Spinner) findViewById(R.id.borough_spinner);
        Spinner zipCodeSpinner = (Spinner) findViewById(R.id.zip_code_spinner);
        Spinner locationTypeSpinner = (Spinner) findViewById(R.id.location_type_spinner);
        Spinner addressCitySpinner = (Spinner) findViewById(R.id.address_city_spinner);
        adminGeneratedUniqueKey = (TextView) findViewById(R.id.unique_key_id);
        TextView creationDate = (TextView) findViewById(R.id.creation_date);
        username.setText((CharSequence) MainActivity.getCurrentUser());

        /**
         * Set up each adapter to display the following column indices in four
         * simple drop down spinners:
         *
         * {"Borough", "Incident Zip", "Location Type", "City"}
         */
        ArrayAdapter<String> adapterBoroughs = new ArrayAdapter(
                this, android.R.layout.simple_spinner_item, nycBoroughs);
        adapterBoroughs.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        boroughSpinner.setAdapter(adapterBoroughs);


        if (getIntent().hasExtra(ARG_UNIQUE_KEY_ID)) {
            reportBorough = getIntent().getParcelableExtra(ARG_BOROUGH_ID);
            boroughSpinner.setSelection(ReportLocation.findBoroughPosition(reportBorough));
            creating = true;
        } else {
            creating = false;
        }

        ArrayAdapter<String> adapterZip = new ArrayAdapter(
                this, android.R.layout.simple_spinner_item, nycZipCodes);
        adapterZip.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        zipCodeSpinner.setAdapter(adapterZip);


        if (getIntent().hasExtra(ARG_UNIQUE_KEY_ID)) {
            reportZipCode = getIntent().getParcelableExtra(ARG_INCIDENT_ZIP_ID);
            zipCodeSpinner.setSelection(ReportLocation.findZipCodePosition(reportZipCode));
            creating = true;
        } else {
            creating = false;
        }

        ArrayAdapter<String> adapterLocationType = new ArrayAdapter(
                this, android.R.layout.simple_spinner_item, locationTypes);
        adapterLocationType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        locationTypeSpinner.setAdapter(adapterLocationType);


        if (getIntent().hasExtra(ARG_UNIQUE_KEY_ID)) {
            reportLocationType = getIntent().getParcelableExtra(ARG_LOCATION_TYPE_ID);
            locationTypeSpinner.setSelection(ReportLocation.findLocationTypePosition(reportLocationType));
            creating = true;
        } else {
            creating = false;
        }

        ArrayAdapter<String> adapterCity = new ArrayAdapter(
                this, android.R.layout.simple_spinner_item, cityList);
        adapterCity.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        addressCitySpinner.setAdapter(adapterCity);


        if (getIntent().hasExtra(ARG_UNIQUE_KEY_ID)) {
            reportCity = getIntent().getParcelableExtra(ARG_CITY_ID);
            addressCitySpinner.setSelection(ReportLocation.findCityPosition(reportCity));
            creating = true;
        } else {
            creating = false;
        }

        Button addButton = (Button) findViewById(R.id.add_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            /**
             * Button handler for the add new report button
             *
             * @param view the button
             */
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                if (creating) {
                    Log.d("Add New Entry", "Add Report");

                    reportLocation.setBorough(reportBorough);
                    reportLocation.setZipCode(reportZipCode);
                    reportLocation.setLocationType(reportLocationType);
                    reportLocation.setCity(reportCity);

                    _report.setNewReportKey();
                    _report.setNewReportDate();
                    _report.setNewReportLocation(reportLocation);


                    Log.d("New Report Entry", "New report data: " + _report);

                    reports.put(_report.getReportKey() , _report);

                    adminGeneratedUniqueKey.setText("" + parsedLineAsList.get(wantedCSVColumnIndices.get(0)));

                    Toast.makeText(
                            getApplicationContext(),
                            "Report has been added",
                            Toast.LENGTH_LONG).show();
                    Context reportListActivity_Context = view.getContext();
                    Intent reportListActivity_Intent = new Intent(
                            reportListActivity_Context, ReportListActivity.class);
                    reportListActivity_Context.startActivity(reportListActivity_Intent);
                } else {
                    finish();
                }
            }
        });



        Button cancelButton = (Button) findViewById(R.id.cancel_button);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            /**
             * Button handler for the add new report button
             *
             * @param view the button
             */
            @Override
            public void onClick(View view) {
                Log.d("Cancel Entry", "Cancel Report Entry");
                Context reportDetailActivity_Context = view.getContext();
                Intent reportDetailActivity_Intent = new Intent(
                        reportDetailActivity_Context, ReportDetailActivity.class);
                reportDetailActivity_Context.startActivity(reportDetailActivity_Intent);
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()){
            case R.id.borough_spinner:
                reportBorough = parent.getItemAtPosition(position).toString();
                break;
            case R.id.zip_code_spinner:
                reportZipCode = parent.getItemAtPosition(position).toString();
                break;
            case R.id.location_type_spinner:
                reportLocationType = parent.getItemAtPosition(position).toString();
                break;
            case R.id.address_city_spinner:
                reportCity = parent.getItemAtPosition(position).toString();
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        switch (parent.getId()){
            case R.id.borough_spinner:
                reportBorough = "NA";
                break;
            case R.id.zip_code_spinner:
                reportZipCode = "NA";
                break;
            case R.id.location_type_spinner:
                reportLocationType = "NA";
                break;
            case R.id.address_city_spinner:
                reportCity = "NA";
                break;
        }
    }
}
