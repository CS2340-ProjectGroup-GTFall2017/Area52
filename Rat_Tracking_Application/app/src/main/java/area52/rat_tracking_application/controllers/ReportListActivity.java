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
import static area52.rat_tracking_application.model.RatReportMap.getReportKeysCreationDates;
import static area52.rat_tracking_application.model.RatReportMap.reports;
import static area52.rat_tracking_application.model.ReportLocation.setZipCodePositions;

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

        private final List<String[]> mReports;

        /**
         * set items to be used by the adapter
         *
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

                        Context context = holder.mContentView.getContext();
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
