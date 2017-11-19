package area52.rat_tracking_application.controllers;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

import area52.rat_tracking_application.R;
import area52.rat_tracking_application.model.PersistenceManager;
import area52.rat_tracking_application.model.RatReport;
import area52.rat_tracking_application.model.ReportLocation;

import static area52.rat_tracking_application.controllers.MainActivity.getCurrentUser;
import static area52.rat_tracking_application.model.PersistenceManager.getPersistManagerInstance;
import static area52.rat_tracking_application.model.RatReportCSVReader.cityList;
import static area52.rat_tracking_application.model.RatReportCSVReader.getCSVReaderInstance;
import static area52.rat_tracking_application.model.RatReportCSVReader.locationTypes;
import static area52.rat_tracking_application.model.RatReportCSVReader.nycBoroughs;

/** *
 * An activity representing a single Rat Report Entry detail screen.
 *
 * Created by Eric on 10/24/2017.
 */

public class ReportEntryActivity extends AppCompatActivity {

    private Spinner boroughSpinner;
    private Spinner locationTypeSpinner;
    private Spinner addressCity;

    private TextView adminGenUniqueKey;

    private TextView latitude;
    private TextView longitude;
    private EditText zipCode;

    private TextView address;

    private RatReport _report = new RatReport();

    private ReportLocation reportLocation = new ReportLocation();

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_entry);

        getReportEntryViewWidgets();
        setListeners();
    }

    private void getReportEntryViewWidgets() {

        latitude = (TextView) findViewById(R.id.latitude);
        longitude = (TextView) findViewById(R.id.longitude);
        address  = (TextView) findViewById(R.id.address);
        zipCode = (EditText) findViewById(R.id.zip_code);

        TextView username = (TextView) findViewById(R.id.user_name);

        adminGenUniqueKey = (TextView) findViewById(R.id.unique_key_id);

        boroughSpinner = (Spinner) findViewById(R.id.borough_spinner);
        locationTypeSpinner = (Spinner) findViewById(R.id.location_type_spinner);
        addressCity = (Spinner) findViewById(R.id.address_city_spinner);

        /*
          Set up the adapter to display the allowable boroughs in the spinner
         */
        ArrayAdapter adapterBoroughs = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, nycBoroughs);
        adapterBoroughs.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        boroughSpinner.setAdapter(adapterBoroughs);

        /*
          Set up the adapter to display the allowable location types in the spinner
         */
        ArrayAdapter<String> adapterLocationType = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, locationTypes);
        adapterLocationType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        locationTypeSpinner.setAdapter(adapterLocationType);

        /*
          Set up the adapter to display the allowable cities in the spinner
         */
        ArrayAdapter<String> adapterCity = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, cityList);
        adapterCity.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        addressCity.setAdapter(adapterCity);

        username.setText(getCurrentUser().getUName());
    }

    private void setListeners() {
        Button cancelButton = (Button) findViewById(R.id.cancel_button);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                returnToRatReportList(view);
            }
        });
        Button submitButton = (Button) findViewById(R.id.button_submit);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addReportToCurrentList(view);
            }
        });
    }




    /**
     * Button handler for the add new rat report button
     * @param view the button
     */
    @TargetApi(Build.VERSION_CODES.N)
    private void addReportToCurrentList(View view) {
        Log.d("Edit Existing Report", "Add New Report");

        String lat = latitude.getText().toString();
        String lon = longitude.getText().toString();

        reportLocation.setLatitude(lat);
        reportLocation.setLongitude(lon);
        reportLocation.setLocationType((String) locationTypeSpinner.getSelectedItem());
        reportLocation.setCity((String) addressCity.getSelectedItem());
        reportLocation.setBorough((String) boroughSpinner.getSelectedItem());
        String zip = String.format("%s", zipCode.getText());
        reportLocation.setZipCode(zip);

        reportLocation.setAddress(reportLocation.getBorough()
                + reportLocation.getCity() + reportLocation.getZipCode());

        address.setText(reportLocation.getAddress());

        _report.setNewReportKey();
        String key = _report.getNewReportKey();
        adminGenUniqueKey.setText(String.format("%s", key));

        _report.setNewReportDate();

        _report.setNewReportLocation(reportLocation);

        Log.d("New Report Entry", "New report data: " + _report);

        getCSVReaderInstance().addSingleReport(_report);

        Toast.makeText(
                getApplicationContext(),
                "Report has been added",
                Toast.LENGTH_LONG).show();

        saveRatReports(view);
    }

    /**
     * Button handler for cancel
     *
     * @param view the button pressed
     */
    private void returnToRatReportList(View view) {
        Log.d("Return to Report List", "Cancel Report Entry/Edit");
        Context context = view.getContext();
        Intent intent = new Intent(
                context, ReportListActivity.class);
        context.startActivity(intent);
    }

    /**
     * Save rat reports as a binary file
     *
     * Should be changed to onClose() saveRatReports() && onLogout() saveRatReports()
     */
    private void saveRatReports(View view) {
        File ratReportsFile = new File(
                this.getFilesDir(), PersistenceManager.RAT_REPORT_DATA_FILENAME);
        getPersistManagerInstance().saveBinary(ratReportsFile, getCSVReaderInstance());
        returnToRatReports(view);
    }

    private void returnToRatReports(View view) {
        Log.d("Return to Report List", "New report entry successfully saved");
        Context context = view.getContext();
        Intent intent = new Intent(
                context, ReportListActivity.class);
        context.startActivity(intent);
    }
}
