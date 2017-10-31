package area52.rat_tracking_application.controllers;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import area52.rat_tracking_application.R;

/**
 * Acknowledgements:
 *
 * Prof. Bob Waters ([Template provided by Prof. Waters] for Android
 * project guidance [GaTech - Fall 2017 - cs2340 - Objects & Design]).
 * Classes, methods, method params, instance and local variables named
 * to reflect our [class final project] --> [Rat Tracking App]:
 *
 * "An activity representing a single Report detail screen. This
 * activity is only used in narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link ReportListActivity}.
 *
 * Here we need to display a list of students.  Our view will be pretty similar
 * since we are displaying a list of students in the course.  We are going to use a
 * recycler view again."
 *
 * Created by Eric on 10/23/2017.
 */
public class ReportDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_detail);
        setupButtonsOnStartup();
    }

    protected void setupButtonsOnStartup() {
        Button logoutButton;
        logoutButton = (Button) findViewById(R.id.logout_button);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context logoutContext = view.getContext();
                Intent logoutIntent = new Intent(logoutContext, WelcomeActivity.class);
                logoutContext.startActivity(logoutIntent);
            }
        });

        Button goToReportEntryScreen;
        goToReportEntryScreen = (Button) findViewById(R.id.go_to_report_entry_screen_button);
        goToReportEntryScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context reportEntryContext = view.getContext();
                Intent reportEntryIntent = new Intent(reportEntryContext, ReportEntryActivity.class);
                reportEntryContext.startActivity(reportEntryIntent);
            }
        });

        // RatReportCSVReader.getReportDetailActivity()
    }
}
