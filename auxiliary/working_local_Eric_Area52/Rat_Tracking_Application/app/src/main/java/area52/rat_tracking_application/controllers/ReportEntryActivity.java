package area52.rat_tracking_application.controllers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.icu.text.DateFormat;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import area52.rat_tracking_application.R;
import area52.rat_tracking_application.model.Borough;
import area52.rat_tracking_application.model.RatReport;
import area52.rat_tracking_application.model.ReportLocation;

import static android.icu.text.DateFormat.getDateTimeInstance;
import static area52.rat_tracking_application.controllers.RatReportLoader.createReport;
import static area52.rat_tracking_application.controllers.RatReportLoader.getNewLocation;
import static area52.rat_tracking_application.controllers.RatReportLoader.reports;
import static area52.rat_tracking_application.controllers.RatReportLoader.setNewLocation;
import static area52.rat_tracking_application.model.ReportLocation.boroughsOfResidency;
import static area52.rat_tracking_application.model.ReportLocation.cityList;
import static area52.rat_tracking_application.model.ReportLocation.locationTypes;
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

public class ReportEntryActivity extends Activity implements AdapterView.OnItemSelectedListener {


    private TextView adminGeneratedUniqueKey;
    private TextView username;
    private Spinner boroughSpinner;
    private Spinner zipCodeSpinner;
    private Spinner addressCitySpinner;
    private Spinner locationTypeSpinner;
    private TextView creationDate;
    private String reportBorough;
    private String reportZipCode;
    private String reportCity;
    private String reportLocationType;

    /* ***********************
           Data for report being entered.
         */
    private String report;

    private static RatReport _report;

    private static ReportLocation reportLocation;

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
                Snackbar.make(view, "TBD", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        /**
         * Grab the dialog widgets so we can get info for later
         */
        username = (TextView) findViewById(R.id.user_name);
        boroughSpinner = (Spinner) findViewById(R.id.borough_spinner);
        zipCodeSpinner = (Spinner) findViewById(R.id.zip_code_spinner);
        locationTypeSpinner = (Spinner) findViewById(R.id.location_type_spinner);
        addressCitySpinner = (Spinner) findViewById(R.id.address_city_spinner);
        adminGeneratedUniqueKey = (TextView) findViewById(R.id.unique_key_id);
        creationDate = (TextView) findViewById(R.id.creation_date);

        DateFormat newDateTime = getDateTimeInstance();
        reportLocation.setCreationDate(newDateTime.toString());
        creationDate.setText(reportLocation.getCreationDate());
        username.setText((CharSequence) MainActivity.getCurrentUser());

        /**
         * Set up each adapter to display the following column indices in four
         * simple drop down spinners:
         *
         * {"Borough", "Incident Zip", "Location Type", "City"}
         */
        ArrayAdapter<String> adapterBoroughs = new ArrayAdapter(
                this, android.R.layout.simple_spinner_item, boroughsOfResidency);
        adapterBoroughs.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        boroughSpinner.setAdapter(adapterBoroughs);


        if (getIntent().hasExtra(ReportDetailFragment.ARG_BOROUGH_ID)) {
            reportBorough = getIntent().getParcelableExtra(ReportDetailFragment.ARG_BOROUGH_ID);
            boroughSpinner.setSelection(ReportLocation.findBoroughPosition(reportBorough));
            creating = true;
        } else {
            creating = false;
        }

        ArrayAdapter<String> adapterZip = new ArrayAdapter(
                this, android.R.layout.simple_spinner_item, nycZipCodes);
        adapterZip.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        zipCodeSpinner.setAdapter(adapterZip);


        if (getIntent().hasExtra(ReportDetailFragment.ARG_INCIDENT_ZIP_ID)) {
            reportZipCode = getIntent().getParcelableExtra(ReportDetailFragment.ARG_INCIDENT_ZIP_ID);
            zipCodeSpinner.setSelection(ReportLocation.findZipCodePosition(reportZipCode));
            creating = true;
        } else {
            creating = false;
        }

        ArrayAdapter<String> adapterLocationType = new ArrayAdapter(
                this, android.R.layout.simple_spinner_item, locationTypes);
        adapterLocationType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        locationTypeSpinner.setAdapter(adapterLocationType);


        if (getIntent().hasExtra(ReportDetailFragment.ARG_LOCATION_TYPE_ID)) {
            reportLocationType = getIntent().getParcelableExtra(ReportDetailFragment.ARG_LOCATION_TYPE_ID);
            locationTypeSpinner.setSelection(ReportLocation.findLocationTypePosition(reportLocationType));
            creating = true;
        } else {
            creating = false;
        }

        ArrayAdapter<String> adapterCity = new ArrayAdapter(
                this, android.R.layout.simple_spinner_item, cityList);
        adapterCity.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        addressCitySpinner.setAdapter(adapterCity);


        if (getIntent().hasExtra(ReportDetailFragment.ARG_CITY_ID)) {
            reportCity = getIntent().getParcelableExtra(ReportDetailFragment.ARG_CITY_ID);
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
            @Override
            public void onClick(View view) {
                if (creating) {
                    Log.d("Add New Entry", "Add Report");

                    reportLocation.setBorough((String) boroughSpinner.getSelectedItem());
                    reportLocation.setZipCode((Integer) zipCodeSpinner.getSelectedItem());
                    reportLocation.setLocationType((String) locationTypeSpinner.getSelectedItem());
                    reportLocation.setCity((String) addressCitySpinner.getSelectedItem());

                    _report.setKey(reports.keySet().toArray().length + 1);
                    adminGeneratedUniqueKey.setText("" + _report.getKey());

                    setNewLocation(reportLocation);

                    Log.d("New Report Entry", "New report data: " + _report);

                    createReport((Long) _report.getKey(), getNewLocation());

                    Toast.makeText(
                            getApplicationContext(),
                            "Report has been added",
                            Toast.LENGTH_LONG).show();
                    Context reportDetailFragmentContext = view.getContext();
                    Intent reportDetailFragmentIntent = new Intent(
                            reportDetailFragmentContext, ReportDetailFragment.class);
                    reportDetailFragmentContext.startActivity(reportDetailFragmentIntent);
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
                Context reportDetailActivityContext = view.getContext();
                Intent reportDetailActivityIntent = new Intent(
                        reportDetailActivityContext, ReportDetailActivity.class);
                reportDetailActivityContext.startActivity(reportDetailActivityIntent);
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
        reportBorough = "NA";
        reportZipCode = "NA";
        reportLocationType = "NA";
        reportCity = "NA";
    }
}
