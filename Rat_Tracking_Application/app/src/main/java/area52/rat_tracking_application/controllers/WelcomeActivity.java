package area52.rat_tracking_application.controllers;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.io.InputStream;

import area52.rat_tracking_application.R;
import area52.rat_tracking_application.model.Model;
import area52.rat_tracking_application.model.PersistenceManager;
import area52.rat_tracking_application.model.RatReportLoader;
import area52.rat_tracking_application.model.RatReportManager;

public class WelcomeActivity extends Activity {
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent(context, MainActivity.class);
                context.startActivity(intent);
            }
        });
        loadRatReports();
    }

    public void loadRatReports() {
        File ratReportsFile = new File(this.getFilesDir(),
                PersistenceManager.RAT_REPORT_DATA_FILENAME);

        if (!RatReportManager.getInstance().loadRatReports(ratReportsFile)) {
            InputStream csvReportFile = getResources().openRawResource(R.raw.rat_sightings);
            RatReportManager.getInstance().loadRatReports(csvReportFile);

            PersistenceManager.getInstance()
                    .saveBinary(ratReportsFile, RatReportManager.getInstance());
        }
    }

    public void loadUsers() {
        File usersFile = new File(this.getFilesDir(),
                PersistenceManager.USERS_DATA_FILENAME);
        Model.getInstance().loadUsers(usersFile);
    }
}
