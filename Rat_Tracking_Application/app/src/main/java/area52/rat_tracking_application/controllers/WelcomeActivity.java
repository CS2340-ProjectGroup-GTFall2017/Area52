package area52.rat_tracking_application.controllers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.io.InputStream;

import area52.rat_tracking_application.R;
import area52.rat_tracking_application.model.Model;
import area52.rat_tracking_application.model.PersistenceManager;

import static area52.rat_tracking_application.model.PersistenceManager.getPersistManagerInstance;
import static area52.rat_tracking_application.model.RatReportCSVReader.getCSVReaderInstance;

public class WelcomeActivity extends Activity {

    Button goToMainButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        goToMainButton = (Button) findViewById(R.id.main_button);

        goToMainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = view.getContext();
                Intent intent = new Intent(context, MainActivity.class);
                context.startActivity(intent);
            }
        });
        loadReports();
        loadUsers();
    }

    public void loadReports() {
        File ratReportsFile = new File(this.getFilesDir(),
                PersistenceManager.RAT_REPORT_DATA_FILENAME);

        if (!getCSVReaderInstance().loadReader(ratReportsFile)) {

            InputStream csvReportFile = getResources().openRawResource(R.raw.rat_sightings);

            getCSVReaderInstance().loadRatReports(csvReportFile);

            getPersistManagerInstance()
                    .saveBinary(ratReportsFile, getCSVReaderInstance());
        }
    }

    public void loadUsers() {
        File usersFile = new File(this.getFilesDir(),
                PersistenceManager.USERS_DATA_FILENAME);
        if (!Model.getInstance().loadUsers(usersFile)) {

            Model.getInstance().loadTestData();

            Model.getInstance().syncUsersListAndHashMap();

            getPersistManagerInstance()
                    .saveBinary(usersFile, Model.getInstance());
        }
    }
}
