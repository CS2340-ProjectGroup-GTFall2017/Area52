package area52.rat_tracking_application.controllers;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;

import area52.rat_tracking_application.R;

public class WelcomeActivity extends Activity {
    Button goToLoginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        goToLoginButton = (Button) findViewById(R.id.go_to_login_button);

        goToLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new RatReportLoader().launchLoader();
                Context context = v.getContext();
                Intent goToMainActIntent = new Intent(context, MainActivity.class);
                context.startActivity(goToMainActIntent);
            }
        });
    }
}
