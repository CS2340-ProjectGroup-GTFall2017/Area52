package area52.rat_tracking_application.controllers;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import area52.rat_tracking_application.R;
import area52.rat_tracking_application.model.RatReport;

import static area52.rat_tracking_application.R.layout.report_list;
import static area52.rat_tracking_application.controllers.RatReportCSVReader.ARG_UNIQUE_KEY_ID;
import static area52.rat_tracking_application.model.RatReportMap.getReportKeysCreationDates;
import static area52.rat_tracking_application.model.RatReportMap.reports;
import static area52.rat_tracking_application.model.ReportLocation.setZipCodePositions;

/**
 * THIS IS OUR TOP_LEVEL WINDOW THAT THE USER FIRST SEES IN THE APPLICATION!
 *
 * An activity representing a list of Courses. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link ReportDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 *
 * This is using a RecyclerView, which is the preferred standard for displaying
 * lists of things like our courses.
 */
public class ReportListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_list);

        View recyclerView = findViewById(R.id.report_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);

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
                setZipCodePositions();
                Context reportEntryContext = view.getContext();
                Intent reportEntryIntent = new Intent(reportEntryContext, ReportEntryActivity.class);
                reportEntryContext.startActivity(reportEntryIntent);
            }
        });
    }

    /**
     *
     * @param recyclerView  the view that needs this adapter
     */
    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(new SimpleReportRecyclerViewAdapter(getReportKeysCreationDates()));
    }

    /**
     * This inner class is our custom adapter.  It takes our basic model information and
     * converts it to the correct layout for this view.
     *
     * In this case, we are just mapping the toString of the Report object to a text field.
     */
    class SimpleReportRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleReportRecyclerViewAdapter.ViewHolder> {


            /**
         * Collection of the items to be shown in this list.
         */
        private final List<String[]> mReports;

        /**
         * set the items to be used by the adapter
         * @param items the list of items to be displayed in the recycler view
         */
        SimpleReportRecyclerViewAdapter(List<String[]> items) {
            mReports = items;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(parent.getContext())
                    .inflate(report_list, parent, false);
            return new ViewHolder(view);
        }


        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            String[] reportKeyCreationDate = mReports.get(position);
            holder.keyV = reportKeyCreationDate[0];
            holder.keyView.setText(holder.keyV);
            holder.mFullReport = reports.get(reportKeyCreationDate[0]);
            holder.mContentView.setText(holder.mFullReport.toString());

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                        Context context = view.getContext();
                        Intent intent = new Intent(context, ReportDetailActivity.class);
                        intent.putExtra(ARG_UNIQUE_KEY_ID, holder.mContentView.toString());

                        context.startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return mReports.size();
        }

        /**
         * This inner class represents a ViewHolder which provides us a way to cache information
         * about the binding between the model element (in this case a Course) and the widgets in
         * the list view (in this case the two TextView)
         */

        class ViewHolder extends RecyclerView.ViewHolder {
            final View mView;
            final TextView keyView;
            final TextView mContentView;
            String keyV;
            RatReport mFullReport;

            ViewHolder(View view) {
                super(view);
                mView = view;
                keyView = (TextView) mView.findViewById(R.id.key_view);
                mContentView = (TextView) mView.findViewById(R.id.content_view);
            }

            @Override
            public String toString() {
                return super.toString() + " '" + mContentView.getText() + "'";
            }
        }
    }
}
