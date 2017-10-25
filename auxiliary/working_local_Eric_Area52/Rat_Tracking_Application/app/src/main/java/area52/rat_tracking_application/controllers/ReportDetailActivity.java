package area52.rat_tracking_application.controllers;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import area52.rat_tracking_application.R;

/**
 * Created by Eric on 10/23/2017.
 */

public class ReportDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Creating a new Report", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Intent reportEntryIntent = new Intent(getBaseContext(), ReportEntryActivity.class);
                startActivity(reportEntryIntent);
            }
        });

        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // Per Prof. Bob Waters [GaTech - Fall 2017 - cs2340 - Objects & Design]
        // "savedInstanceState is non-null when there is fragment state
        // saved from previous configurations of this activity
        // (e.g. when rotating the screen from portrait to landscape).
        // In this case, the fragment will automatically be re-added
        // to its container so we don't need to manually add it.
        // For more information, see the Fragments API guide at:
        //
        // http://developer.android.com/guide/components/fragments.html"
        //
        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.  Pass the course info to
            //the fragment
            Bundle arguments = new Bundle();
            arguments.putInt(ReportDetailFragment.ARG_UNIQUE_KEY_ID,
                    getIntent().getIntExtra(ReportDetailFragment.ARG_UNIQUE_KEY_ID, 0));

            ReportDetailFragment fragment = new ReportDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.report_detail_container, fragment)
                    .commit();
        }

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


    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
            navigateUpTo(new Intent(this, ReportListActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
