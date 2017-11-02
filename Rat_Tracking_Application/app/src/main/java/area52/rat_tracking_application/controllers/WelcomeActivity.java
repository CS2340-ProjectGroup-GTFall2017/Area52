package area52.rat_tracking_application.controllers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import area52.rat_tracking_application.R;

public class WelcomeActivity extends Activity {

    Button goToMainButton;
    Button launchReportsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        goToMainButton = (Button) findViewById(R.id.main_button);

        goToMainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View vMain) {
                Context contextMain = vMain.getContext();
                Intent goToMainIntent = new Intent(contextMain, MainActivity.class);
                contextMain.startActivity(goToMainIntent);
            }
        });
    }
}