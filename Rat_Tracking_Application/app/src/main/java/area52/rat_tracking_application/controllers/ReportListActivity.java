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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import area52.rat_tracking_application.R;
import area52.rat_tracking_application.model.RatReport;

import static area52.rat_tracking_application.R.id.report_list;
import static area52.rat_tracking_application.R.layout.activity_report_list;
import static area52.rat_tracking_application.R.layout.content_report_list;
import static area52.rat_tracking_application.model.Model.setCurrentReport;
import static area52.rat_tracking_application.model.RatReportMap.getReportKeysCreationDates;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_report_list);

        setupButtonsOnStartup();

        View recyclerView = findViewById(report_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);
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
     * @param recyclerView  the view that requires this adapter
     */
    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(new SimpleReportRecyclerViewAdapter(getReportKeysCreationDates()));
    }

    /**
     * Inner class that represents our custom adapter.
     */
    class SimpleReportRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleReportRecyclerViewAdapter.ViewHolder> {

        HashMap<String, RatReport> mapReports = reports;

        List<String[]> mReports = new ArrayList<>();

        /**
         * set items to be used by the adapter
         *
         * @param items the list of items to be displayed in the recycler view
         */
        SimpleReportRecyclerViewAdapter(List<String[]> items) {
            mReports = items;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {/////
            View view = LayoutInflater.from(parent.getContext())///////////
                    .inflate(content_report_list, parent, false);////
            return new ViewHolder(view);////
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

            holder.reportKeyCreationDate = mReports.get(position);

            holder.ratExpandedReport = mapReports.get(holder.reportKeyCreationDate[0]);

            holder.mView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {

                        Context context = view.getContext();
                        Intent intent = new Intent(context, ReportDetailActivity.class);
                        setCurrentReport(holder.ratExpandedReport);
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
            String[] reportKeyCreationDate;
            RatReport ratExpandedReport;
            String keyCreationDate;

            ViewHolder(View view) {
                super(view);
                mView = view;
                keyCreationDate = String.valueOf(view.findViewById(R.id.key_view));
            }

            @Override
            public String toString() {
                return super.toString() + " '" + keyCreationDate + "'";
            }
        }
    }
}
