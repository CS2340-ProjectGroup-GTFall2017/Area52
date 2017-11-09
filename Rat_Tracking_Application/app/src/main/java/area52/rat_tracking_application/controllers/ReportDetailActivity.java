package area52.rat_tracking_application.controllers;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import area52.rat_tracking_application.R;
import area52.rat_tracking_application.model.Model;
import area52.rat_tracking_application.model.RatReport;

/**
 * Displays the details of a single report, selected on the ReportListActivity
 * class instance's corresponding screen.
 *
 * Created by Eric on 10/23/2017.
 */
public class ReportDetailActivity extends AppCompatActivity {

    private RatReport report;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_detail);
        setupButtonsOnStartup();
        final Model model = Model.getInstance();
        report = model.getCurrentReport();
        assert report != null;
        TextView reportContentView = (TextView) findViewById(R.id.report_content_view);
        setupTextView(reportContentView);
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

        Button goBackToReportListScreen;
        goBackToReportListScreen = (Button) findViewById(R.id.go_to_report_list_screen_button);
        goBackToReportListScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context reportListContext = view.getContext();
                Intent reportListIntent = new Intent(reportListContext, ReportListActivity.class);
                reportListContext.startActivity(reportListIntent);
            }
        });
    }

    /**
     *
     * @param textView  the view of the rat report selected from the ReportListActivity screen's
     * recycler view.
     */
    private void setupTextView(@NonNull TextView textView) {
        textView.setText(report.toString());

    }
}
