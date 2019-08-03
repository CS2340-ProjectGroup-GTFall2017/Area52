package area52.rat_tracking_application.controllers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import area52.rat_tracking_application.DateRange;
import area52.rat_tracking_application.GraphSetup;
import area52.rat_tracking_application.R;

/**
 * Logout activity class represents logout screen
 */
public class LogoutActivity extends Activity {
    Button button;
    Button reportListButton;
    Button mapButton;
    Button graphButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logout);
        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = view.getContext();
                Intent intent = new Intent(context, WelcomeActivity.class);
                context.startActivity(intent);
            }
        });

        reportListButton = (Button) findViewById(R.id.button_reportList);
        reportListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = view.getContext();
                Intent intent = new Intent(context, ReportListActivity.class);
                context.startActivity(intent);
            }
        });

        mapButton = (Button) findViewById(R.id.button_reportMap);
        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = view.getContext();
                Intent intent = new Intent(context, DateRange.class);
                context.startActivity(intent);
            }
        });

        graphButton = (Button) findViewById(R.id.button_reportGraph);
        graphButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = view.getContext();
                Intent intent = new Intent(context, GraphSetup.class);
                context.startActivity(intent);
            }
        });


    }

}