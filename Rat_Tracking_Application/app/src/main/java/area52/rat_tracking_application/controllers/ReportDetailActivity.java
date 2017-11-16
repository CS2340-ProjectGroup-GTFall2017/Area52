package area52.rat_tracking_application.controllers;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import area52.rat_tracking_application.R;

import static area52.rat_tracking_application.model.RatReportCSVReader.getCSVReaderInstance;

/**
 * Displays the details of a single report, selected on the ReportListActivity
 * class instance's corresponding screen.
 *
 * Created by Eric on 10/23/2017.
 */
public class ReportDetailActivity extends AppCompatActivity {

    TextView reportContentView;

    Button backToReportsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_detail);

        reportContentView = (TextView) findViewById(R.id.single_report_content);
        reportContentView.setText(String.format("%s", getCSVReaderInstance().getCurrentReport()));

        backToReportsButton = (Button) findViewById(R.id.back_to_reports_button);

        backToReportsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Context context = view.getContext();
                Intent intent = new Intent(context, ReportListActivity.class);
                context.startActivity(intent);
            }
        });
    }
}