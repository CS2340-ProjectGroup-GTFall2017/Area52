package area52.rat_tracking_application.controllers;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import area52.rat_tracking_application.R;
import area52.rat_tracking_application.model.RatReport;
import area52.rat_tracking_application.model.RatReportMap;
import area52.rat_tracking_application.model.ReportLocation;

import static area52.rat_tracking_application.R.id.add_button;
import static area52.rat_tracking_application.R.id.address_city_spinner;
import static area52.rat_tracking_application.R.id.borough_spinner;
import static area52.rat_tracking_application.R.id.cancel_button;
import static area52.rat_tracking_application.R.id.location_type_spinner;
import static area52.rat_tracking_application.R.id.zip_code_spinner;
import static area52.rat_tracking_application.controllers.MainActivity.getCurrentUser;
import static area52.rat_tracking_application.model.RatReportMap.cityList;
import static area52.rat_tracking_application.model.RatReportMap.locationTypes;
import static area52.rat_tracking_application.model.RatReportMap.nycBoroughs;
import static area52.rat_tracking_application.model.RatReportMap.nycZipCodes;
import static area52.rat_tracking_application.model.RatReportMap.reports;

/** *
 * An activity representing a single Rat Report Entry detail screen.
 *
 * Created by Eric on 10/24/2017.
 */

public class ReportEntryActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {


    private TextView adminGeneratedUniqueKey;
    private TextView username;
    private String reportBorough;
    private Integer reportZipCode;
    private String reportCity;
    private String reportLocationType;

    Spinner boroughSpinner;
    Spinner zipCodeSpinner;
    Spinner locationTypeSpinner;
    Spinner addressCitySpinner;

    private RatReport _report = new RatReport();

    private ReportLocation reportLocation = new ReportLocation();

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_entry);

        username = (TextView) findViewById(R.id.user_name);
        username.setText(getCurrentUser().getUName());

        adminGeneratedUniqueKey = (TextView) findViewById(R.id.unique_key_id);

        boroughSpinner = (Spinner) findViewById(borough_spinner);
        zipCodeSpinner = (Spinner) findViewById(zip_code_spinner);
        locationTypeSpinner = (Spinner) findViewById(location_type_spinner);
        addressCitySpinner = (Spinner) findViewById(address_city_spinner);

        ArrayAdapter<String> adapterBoroughs = new ArrayAdapter(
                this, android.R.layout.simple_spinner_item, nycBoroughs);
        adapterBoroughs.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        boroughSpinner.setAdapter(adapterBoroughs);

        ArrayAdapter<String> adapterZip = new ArrayAdapter(
                this, android.R.layout.simple_spinner_item, nycZipCodes);
        adapterZip.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        zipCodeSpinner.setAdapter(adapterZip);

        ArrayAdapter<String> adapterLocationType = new ArrayAdapter(
                this, android.R.layout.simple_spinner_item, locationTypes);
        adapterLocationType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        locationTypeSpinner.setAdapter(adapterLocationType);

        ArrayAdapter<String> adapterCity = new ArrayAdapter(
                this, android.R.layout.simple_spinner_item, cityList);
        adapterCity.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        addressCitySpinner.setAdapter(adapterCity);

        Button cancelButton = (Button) findViewById(cancel_button);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            /**
             * Button handler for the add new report button
             *
             * @param view the button
             */
            @Override
            public void onClick(View view) {
                Log.d("Cancel Entry", "Cancel Report Entry");
                Context reportListActivity_Context = view.getContext();
                Intent reportListActivity_Intent = new Intent(
                        reportListActivity_Context, ReportDetailActivity.class);
                reportListActivity_Context.startActivity(reportListActivity_Intent);
            }
        });

        Button addButton = (Button) findViewById(add_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            /**
             * Button handler for the add new report button
             *
             * @param view the button
             */
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {

                boroughSpinner.setSelection(
                        RatReportMap.findBoroughPosition(reportBorough));
                zipCodeSpinner.setSelection(
                        RatReportMap.findZipCodePosition(reportZipCode));
                locationTypeSpinner.setSelection(
                        RatReportMap.findLocationTypePosition(reportLocationType));
                addressCitySpinner.setSelection(
                        RatReportMap.findCityPosition(reportCity));

                Log.d("Add New Entry", "Add Report");

                reportLocation.setLatitude("0");
                reportLocation.setLongitude("0");
                reportLocation.setLocationType((String) locationTypeSpinner.getSelectedItem());
                reportLocation.setCity((String) addressCitySpinner.getSelectedItem());
                reportLocation.setBorough((String) boroughSpinner.getSelectedItem());
                reportLocation.setZipCode((String) zipCodeSpinner.getSelectedItem());

                reportLocation.setAddress(reportLocation.getBorough()
                        + reportLocation.getCity() + reportLocation.getZipCode());

                _report.setNewReportKey();
                _report.setNewReportDate();
                _report.setNewReportLocation(reportLocation);

                Log.d("New Report Entry", "New report data: " + _report);

                String key = _report.getNewReportKey();
                reports.put(key , _report);

                adminGeneratedUniqueKey.setText("" + key);

                Toast.makeText(
                        getApplicationContext(),
                        "Report has been added",
                        Toast.LENGTH_LONG).show();
                Context reportDetailActivity_Context = view.getContext();
                Intent reportDetailActivity_Intent = new Intent(
                        reportDetailActivity_Context, ReportListActivity.class);
                reportDetailActivity_Context.startActivity(reportDetailActivity_Intent);
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()){
            case borough_spinner:
                reportBorough = parent.getItemAtPosition(position).toString();
                break;
            case zip_code_spinner:
                reportZipCode = (Integer) parent.getItemAtPosition(position);
                break;
            case location_type_spinner:
                reportLocationType = parent.getItemAtPosition(position).toString();
                break;
            case address_city_spinner:
                reportCity = parent.getItemAtPosition(position).toString();
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        switch (parent.getId()){
            case borough_spinner:
                reportBorough = "NA";
                break;
            case zip_code_spinner:
                reportZipCode = 0;
                break;
            case location_type_spinner:
                reportLocationType = "NA";
                break;
            case address_city_spinner:
                reportCity = "NA";
                break;
        }
    }
}
