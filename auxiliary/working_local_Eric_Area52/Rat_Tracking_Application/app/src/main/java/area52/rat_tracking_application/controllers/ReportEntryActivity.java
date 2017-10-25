package area52.rat_tracking_application.controllers;

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
import area52.rat_tracking_application.model.RatReport;
import area52.rat_tracking_application.model.ReportLocation;
import area52.rat_tracking_application.model.User;

import static area52.rat_tracking_application.model.User.findPosition;

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
    private ReportLocation reportLocation;
    private TextView location;
    private TextView creationDate;
    private String locationBorough;
    private String locationZipCode;
    private String locationCity;
    private String locationType;

    /* ***********************
           Data for user being entered.
         */
    private RatReport _report;

    private RatReportLoader reportLoader;

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
        reportLoader = new RatReportLoader();

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
        adminGeneratedUniqueKey = (TextView) findViewById(R.id.admin_gen_unique_key);
        location = (TextView) findViewById(R.id._location_);
        creationDate = (TextView) findViewById(R.id.creation_date);

    /*
      Set up the adapter to display the allowable indices in the spinner
       {"Unique Key", "Created Date", "Location Type",
            "Incident Zip", "Incident Address", "City", "Borough", "Latitude",
            "Longitude"};
     */
        ArrayAdapter<String> adapterBoroughs = new ArrayAdapter(
                this,android.R.layout.simple_spinner_item, reportLoader.wantedCSVColumns);
        adapterBoroughs.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        boroughSpinner.setAdapter(adapterBoroughs);


        if (getIntent().hasExtra(ReportDetailFragment.ARG_BOROUGH_ID)) {
            _report = getIntent().getParcelableExtra(ReportDetailFragment.ARG_BOROUGH_ID);
            boroughSpinner.setSelection(findPosition(_report.getLocation().getBorough()));
            creating = true;
        } else {
            creating = false;
        }

        ArrayAdapter<String> adapterZip = new ArrayAdapter(
                this,android.R.layout.simple_spinner_item, reportLoader.wantedCSVColumns);
        adapterZip.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        zipCodeSpinner.setAdapter(adapterZip);


        if (getIntent().hasExtra(ReportDetailFragment.ARG_INCIDENT_ZIP_ID)) {
            _report = getIntent().getParcelableExtra(ReportDetailFragment.ARG_INCIDENT_ZIP_ID);
            zipCodeSpinner.setSelection(findPosition(_report.getLocation().getZipCode()));
            creating = true;
        } else {
            creating = false;
        }

        ArrayAdapter<String> adapterLocationType = new ArrayAdapter(
                this,android.R.layout.simple_spinner_item, reportLoader.wantedCSVColumns);
        adapterLocationType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        locationTypeSpinner.setAdapter(adapterLocationType);


        if (getIntent().hasExtra(ReportDetailFragment.ARG_LOCATION_TYPE_ID)) {
            _report = getIntent().getParcelableExtra(ReportDetailFragment.ARG_LOCATION_TYPE_ID);
            locationTypeSpinner.setSelection(findPosition(_report.getLocation().getLocationType()));
            creating = true;
        } else {
            creating = false;
        }
        ArrayAdapter<String> adapterCity = new ArrayAdapter(
                this,android.R.layout.simple_spinner_item, reportLoader.wantedCSVColumns);
        adapterCity.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        addressCitySpinner.setAdapter(adapterCity);


        if (getIntent().hasExtra(ReportDetailFragment.ARG_CITY_ID)) {
            _report = getIntent().getParcelableExtra(ReportDetailFragment.ARG_CITY_ID);
            addressCitySpinner.setSelection(findPosition(_report.getLocation().getCity()));
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
    protected void onAddPressed(View view) {
        Log.d("Add New Entry", "Add Report");

        reportLocation.setBorough((String) boroughSpinner.getSelectedItem());
        reportLocation.setCity((String) addressCitySpinner.getSelectedItem());
        reportLocation.setZipCode((String) zipCodeSpinner.getSelectedItem());
        reportLocation.setLocationType((String) locationTypeSpinner.getSelectedItem());

        Log.d("New Entry", "New report data: " + _report);

        reportLoader.addReport(_report.getKey(), _report);

        finish();
    }

    /**
     * Button handler for cancel
     *
     * @param view the button pressed
     */
    protected void onCancelPressed(View view) {
        Log.d("Edit", "Cancel Report Entry");
        finish();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String report = _report.toString();
        report = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        _report= "N/A";
    }
}
