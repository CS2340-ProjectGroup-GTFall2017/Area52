package area52.rat_tracking_application.controllers;

import android.content.Context;
import android.content.Intent;
import android.os.Debug;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.Date;
import java.util.Random;

import area52.rat_tracking_application.R;
import area52.rat_tracking_application.model.RatReport;
import area52.rat_tracking_application.model.RatReportLoader;
import area52.rat_tracking_application.model.RatReportManager;
import area52.rat_tracking_application.model.ReportLocation;

public class CreateRatReportActivity extends AppCompatActivity {
    private Button cancelButton;
    private Button submitButton;

    private Spinner locationType;
    private Spinner borough;

    private EditText latitude;
    private EditText longitude;
    private EditText city;
    private EditText address;
    private EditText zipCode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_rat_report);

        getRatReportFieldViews();
        setUpButtonClickListeners();
    }

    private void getRatReportFieldViews() {
        locationType = (Spinner) findViewById(R.id.spinner_locationType);
        borough = (Spinner) findViewById(R.id.spinner_borough);

        latitude = (EditText) findViewById(R.id.editText_lat);
        longitude = (EditText) findViewById(R.id.editText_longitude);
        city = (EditText) findViewById(R.id.editText_City);
        address  = (EditText) findViewById(R.id.editText_address);
        zipCode = (EditText) findViewById(R.id.editText_zipcode);
    }

    private void setUpButtonClickListeners() {
        cancelButton = (Button) findViewById(R.id.button_cancel);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                returnToRatReportList(view);
            }
        });
        submitButton = (Button) findViewById(R.id.button_submit);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createNewRatReport(view);
            }
        });
    }

    private void returnToRatReportList(View view) {
        Context context = view.getContext();
        Intent intent = new Intent(context, ReportListActivity.class);
        context.startActivity(intent);
    }

    private void createNewRatReport(View view) { //TODO: Refactor this mess
        String locType = locationType.getSelectedItem().toString();
        String bor = borough.getSelectedItem().toString();

        String addr = address.getText().toString();
        String cit = city.getText().toString();

        double lat = Double.valueOf(latitude.getText().toString());
        double lon = Double.valueOf(longitude.getText().toString());
        int zip = Integer.valueOf(zipCode.getText().toString());

        Random rand = new Random();
        long key = rand.nextLong();
        while (RatReportManager.getReportsHashMap().containsKey(key)) { //TODO: Come up with a safer, better way to get a unique key
            key = rand.nextLong();
        }

        ReportLocation reportLoc = new ReportLocation(lat, lon, locType, addr, cit, bor, zip);
        RatReport newReport = new RatReport(key, new Date(), reportLoc); //TODO: Relies on Date constructor defaulting to current time
        RatReportManager.getReportsHashMap().put(key, newReport);

        returnToRatReportList(view);
    }
}
