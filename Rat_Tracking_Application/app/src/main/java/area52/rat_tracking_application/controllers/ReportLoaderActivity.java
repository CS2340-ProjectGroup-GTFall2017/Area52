package area52.rat_tracking_application.controllers;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.io.InputStream;

import area52.rat_tracking_application.R;

import static area52.rat_tracking_application.controllers.RatReportCSVReader.readInFile;

/**
 * Created by Eric on 10/29/2017.
 */

public class ReportLoaderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_loader);

        Button launchReportsButton;
        launchReportsButton = (Button) findViewById(R.id.launch_loader);
        launchReportsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = view.getContext();
                Intent intent = new Intent(context, ReportListActivity.class);
                context.startActivity(intent);
                loadRatReports();
            }
        });
    }
    public void loadRatReports() {
        InputStream csvReportFile = getResources().openRawResource(R.raw.rat_sightings);
        try {
            readInFile(csvReportFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        new RatReportLoader().launchLoader();
    }
}