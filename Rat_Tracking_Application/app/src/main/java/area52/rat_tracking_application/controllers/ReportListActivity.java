package area52.rat_tracking_application.controllers;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

import area52.rat_tracking_application.R;
import area52.rat_tracking_application.model.RatReport;

import static area52.rat_tracking_application.controllers.RatReportCSVReader.allReportsList;
import static area52.rat_tracking_application.model.RatReportMap.setCurrentReport;

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

    private boolean mTwoPane;

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

    /**
     * The adapter for the recycle view list of information from all rat reports
     */
    private ReportRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        for (RatReport r : allReportsList) {
            System.out.println(r.toString());
        }
        setContentView(R.layout.activity_report_list);

        View reportRecyclerView = findViewById(R.id.report_list);
        assert reportRecyclerView != null;
        setupReportRecyclerView((RecyclerView) reportRecyclerView);
    }

    @Override
    public void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }

    /**
     * Set up an adapter and hook it to the provided view
     *
     * @param reportRecyclerView  the view that needs this adapter
     */
    private void setupReportRecyclerView(@NonNull RecyclerView reportRecyclerView) {
        adapter = new ReportRecyclerViewAdapter(allReportsList);
        Log.d("Adapter", adapter.toString());
        reportRecyclerView.setAdapter(adapter);
    }

    /**
     * Inner class that represents our custom adapter.
     */
    class ReportRecyclerViewAdapter
            extends RecyclerView.Adapter<ReportRecyclerViewAdapter.ViewHolder> {

        /**
         * Rat reports to be displayed in this list.
         */
        private final List<RatReport> mReports;

        ReportRecyclerViewAdapter(List<RatReport> reportHashMapValues) {
            mReports = reportHashMapValues;
            for (RatReport r : mReports) {
                System.out.println(r.toString());
            }
        }

        @Override
        public ReportRecyclerViewAdapter.ViewHolder onCreateViewHolder(
                ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.content_report_list, parent, false);
            return new ReportRecyclerViewAdapter.ViewHolder(view);
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
        public void onBindViewHolder(final ViewHolder holder, final int position) {

            holder.mReport = mReports.get(position);

            holder.mReportContent.setText(String.format("%s", holder.mReport));

            holder.mView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    setCurrentReport(holder.mReport);
                    Context context = view.getContext();
                    Intent intent = new Intent(context, ReportDetailActivity.class);
                    context.startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return mReports.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            final View mView;
            final TextView mReportContent;
            RatReport mReport;

            ViewHolder(View view) {

                super(view);
                mView = view;
                mReportContent = (TextView) view.findViewById(R.id.report_content);
            }

            @Override
            public String toString() {

                return super.toString() + " '" + mReportContent.getText() + "'";

            }
        }
    }
}
