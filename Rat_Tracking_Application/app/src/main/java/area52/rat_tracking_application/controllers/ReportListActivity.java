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

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import area52.rat_tracking_application.R;
import area52.rat_tracking_application.model.Model;
import area52.rat_tracking_application.model.RatReport;

import static area52.rat_tracking_application.model.RatReportMap.reports;

/**
 * An activity representing a list of RatReport instances.
 * The activity presents a list of items, which when touched,
 * lead to a {@link ReportDetailActivity} representing
 * report item details.
 *
 * This activity implements use of a RecyclerView, defined within
 * an inner class in ReportListActivity
 */
public class ReportListActivity extends AppCompatActivity {

    private List<String[]> ratReportList;

    private RecyclerView reportRecyclerView;

    View report;

    private static final String ARG_UNIQUE_KEY_ID = "Report ID";//0
    private static final String ARG_CREATED_DATE_ID = "Report Creation Date";//1
    private static final String ARG_LOCATION_TYPE_ID = "Incident Location Type";//7
    private static final String ARG_INCIDENT_ZIP_ID = "Zip Code";//8
    private static final String ARG_INCIDENT_ADDRESS_ID = "Address";//9
    private static final String ARG_CITY_ID = "City";//16
    private static final String ARG_BOROUGH_ID = "Borough";//23
    private static final String ARG_LATITUDE_ID = "Latitude";//49
    private static final String ARG_LONGITUDE_ID = "Longitude";//50

    static List<String> reportParams = Arrays.asList(ARG_UNIQUE_KEY_ID, ARG_CREATED_DATE_ID,
            ARG_LOCATION_TYPE_ID, ARG_INCIDENT_ZIP_ID, ARG_INCIDENT_ADDRESS_ID,
            ARG_CITY_ID, ARG_BOROUGH_ID, ARG_LATITUDE_ID, ARG_LONGITUDE_ID);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_list);
        setupButtonsOnStartup();

        //String header_ListScreen = reportParams.get(0) + reportParams.get(1);
        reportRecyclerView = (RecyclerView) findViewById(R.id.report_list);
        assert reportRecyclerView != null;
        setupRecyclerView(reportRecyclerView);
    }

    protected void setupButtonsOnStartup() {
        Button logout_Button = (Button) findViewById(R.id.log_out_button);
        logout_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context logoutContext = view.getContext();
                Intent logoutIntent = new Intent(logoutContext, WelcomeActivity.class);
                logoutContext.startActivity(logoutIntent);
            }
        });

        Button goToReportEntryScreen = (Button) findViewById(R.id.go_to_report_entry_screen_button);
        goToReportEntryScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context reportEntryContext = view.getContext();
                Intent reportEntryIntent = new Intent(reportEntryContext, ReportEntryActivity.class);
                reportEntryContext.startActivity(reportEntryIntent);
            }
        });
    }

    /**
     *
     * @param reportRecyclerView  the view that requires this adapter
     */
    private void setupRecyclerView(@NonNull RecyclerView reportRecyclerView) {
        reportRecyclerView.setAdapter(new ReportRecyclerViewAdapter((Collection<RatReport>) reports.values()));
    }

    /**
     * Inner class that represents our custom adapter.
     */
    class ReportRecyclerViewAdapter
            extends RecyclerView.Adapter<ReportRecyclerViewAdapter.ViewHolder> {

        /**
         * Rat reports to be displayed in this list.
         */
        private final Collection<RatReport> mReports;

        ReportRecyclerViewAdapter(Collection<RatReport> fullReportListView) {
            mReports = fullReportListView;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.content_report_list, parent, false);
            return new ViewHolder(view);
        }

        /**
         * As per Prof. Bob Waters - Georgia Tech - [CS-2340] Objects & Design - Fall 2017
         *
         * "This is where we have to bind each data element in the list (given by position parameter)
         * to an element in the view (which is one of our two TextView widgets).
         * Start by getting the element at the correct position."
         *
         * @param holder view container
         * @param position index in list view
         */
        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {

            final Model model = Model.getInstance();

            holder.mKey = String.valueOf(reportRecyclerView.getChildAt(position));
            holder.mIdView.setText(holder.mKey);
            holder.mReport = reports.get(holder.mKey);
            holder.mContentView.setText(holder.mReport.toString());
            holder.mView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    Context context = view.getContext();
                    Intent intent = new Intent(context, ReportDetailActivity.class);
                    model.setCurrentReport(holder.mReport);
                    context.startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return reports.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            final Button lockSelection;
            final View mView;
            final TextView mIdView;
            final TextView mContentView;
            RatReport mReport;
            String mKey;

            ViewHolder(View view) {

                super(view);
                mView = view;
                mIdView = (TextView) view.findViewById(R.id.key_view);
                mContentView = (TextView) view.findViewById(R.id.report_content_view);
                lockSelection = (Button) view.findViewById(R.id.lock_selection);
            }

            @Override
            public String toString() {

                return super.toString() + " '" + mContentView.getText() + "'";

            }
        }
    }
}
