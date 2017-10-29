package area52.rat_tracking_application.controllers;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import area52.rat_tracking_application.R;

import static area52.rat_tracking_application.controllers.RatReportLoader.launchLoader;

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
                Context welcomeContext = view.getContext();
                Intent welcomeIntent = new Intent(welcomeContext, WelcomeActivity.class);
                welcomeContext.startActivity(welcomeIntent);
                launchLoader();
            }
        });
    }
}