package area52.rat_tracking_application.controllers;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import area52.rat_tracking_application.R;

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

    private void createNewRatReport(View view) {

        returnToRatReportList(view);
    }
}
