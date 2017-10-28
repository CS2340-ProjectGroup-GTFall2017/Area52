package area52.rat_tracking_application.controllers;

import android.annotation.TargetApi;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import area52.rat_tracking_application.R;
import area52.rat_tracking_application.model.Borough;
import area52.rat_tracking_application.model.RatReport;
import area52.rat_tracking_application.model.ReportLocation;

import static area52.rat_tracking_application.R.id.end;
import static area52.rat_tracking_application.controllers.RatReportLoader.createReport;
import static area52.rat_tracking_application.controllers.RatReportLoader.getNewLocation;
import static area52.rat_tracking_application.controllers.RatReportLoader.getReportDate;
import static area52.rat_tracking_application.controllers.RatReportLoader.reports;
import static area52.rat_tracking_application.controllers.RatReportLoader.setNewLocation;
import static area52.rat_tracking_application.controllers.RatReportLoader.wantedCSVColumns;
import static area52.rat_tracking_application.controllers.ReportDetailFragment.ARG_BOROUGH_ID;
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

public class ReportEntryActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {


    private TextView adminGeneratedUniqueKey;
    private TextView username;
    private Spinner boroughSpinner;
    private Spinner zipCodeSpinner;
    private Spinner addressCitySpinner;
    private Spinner locationTypeSpinner;
    private TextView location;
    private TextView creationDate;
    private String locationBorough;
    private String locationZipCode;
    private String locationCity;
    private String locationType;

    /* ***********************
           Data for user being entered.
         */
    private String report;

    private static RatReport _report;

    private static ReportLocation reportLocation;

    /* ***********************
       flag for whether this is a new report being added or an existing report being edited;
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
        setSupportActionBar(toolbar);

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
        addressCitySpinner = (Spinner) findViewById(R.id.address_city_spinner);
        adminGeneratedUniqueKey = (TextView) findViewById(R.id.unique_key_id);
        creationDate = (TextView) findViewById(R.id.creation_date);


    /*
      Set up the adapter to display the allowable indices in the spinner
       {"Unique Key", "Created Date", "Location Type",
            "Incident Zip", "Incident Address", "City", "Borough", "Latitude",
            "Longitude"};
     */

        /**
         * Manhattan.
         * Brooklyn.
         * Queens.
         * The Bronx.
         * Staten Island
         */
        ArrayAdapter<String> adapterBoroughs = new ArrayAdapter(
                this,android.R.layout.simple_spinner_item, boroughsOfResidency);
        adapterBoroughs.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        boroughSpinner.setAdapter(adapterBoroughs);


        if (getIntent().hasExtra(ARG_BOROUGH_ID)) {
            String reportBorough = getIntent().getParcelableExtra(ARG_BOROUGH_ID);
            boroughSpinner.setSelection(ReportLocation.findBoroughPosition(reportBorough));
            creating = true;
        } else {
            creating = false;
        }

        ArrayAdapter<String> adapterZip = new ArrayAdapter(
                this,android.R.layout.simple_spinner_item, nycZipCodes);
        adapterZip.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        zipCodeSpinner.setAdapter(adapterZip);


        if (getIntent().hasExtra(ReportDetailFragment.ARG_INCIDENT_ZIP_ID)) {
            String reportZipCode = getIntent().getParcelableExtra(ReportDetailFragment.ARG_INCIDENT_ZIP_ID);
            zipCodeSpinner.setSelection(ReportLocation.findZipCodePosition(reportZipCode));
            creating = true;
        } else {
            creating = false;
        }

        ArrayAdapter<String> adapterLocationType = new ArrayAdapter(
                this,android.R.layout.simple_spinner_item, locationTypes);
        adapterLocationType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        locationTypeSpinner.setAdapter(adapterLocationType);


        if (getIntent().hasExtra(ReportDetailFragment.ARG_LOCATION_TYPE_ID)) {
            String locationType = getIntent().getParcelableExtra(ReportDetailFragment.ARG_LOCATION_TYPE_ID);
            locationTypeSpinner.setSelection(ReportLocation.findLocationTypePosition(locationType));
            creating = true;
        } else {
            creating = false;
        }

        ArrayAdapter<String> adapterCity = new ArrayAdapter(
                this,android.R.layout.simple_spinner_item, cityList);
        adapterCity.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        addressCitySpinner.setAdapter(adapterCity);


        if (getIntent().hasExtra(ReportDetailFragment.ARG_CITY_ID)) {
            String reportCity = getIntent().getParcelableExtra(ReportDetailFragment.ARG_CITY_ID);
            addressCitySpinner.setSelection(ReportLocation.findCityPosition(reportCity));
            creating = true;
        } else {
            creating = false;
        }

        adminGeneratedUniqueKey.setText("" + _report.getKey());

    }

    /**
     * Button handler for the add new student button
     * @param view the button
     */
    @TargetApi(25)
    protected void onAddPressed(View view) {
        Log.d("Add New Entry", "Add Report");

        DateFormat newDateTime = new SimpleDateFormat().getDateTimeInstance();

        reportLocation.setBorough((String) boroughSpinner.getSelectedItem());
        reportLocation.setCity((String) addressCitySpinner.getSelectedItem());
        reportLocation.setZipCode((Integer) zipCodeSpinner.getSelectedItem());
        reportLocation.setLocationType((String) locationTypeSpinner.getSelectedItem());
        reportLocation.setCreationDate(newDateTime.toString());

        //report.setKey(Long ...);
        setNewLocation(reportLocation);
        Log.d("New Report Entry", "New report data: " + _report);

        _report.setKey(reports.keySet().toArray().length + 1);

        createReport((Long) _report.getKey(), getNewLocation());

        finish();
    }

    /**
     * Button handler for cancel
     *
     * @param view the button pressed
     */
    protected void onCancelPressed(View view) {
        Log.d("Cancel Entry", "Cancel Report Entry");
        finish();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        report = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        _report= null;
    }

}
