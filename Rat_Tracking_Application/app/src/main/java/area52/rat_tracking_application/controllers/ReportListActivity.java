package area52.rat_tracking_application.controllers;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import area52.rat_tracking_application.R;
import area52.rat_tracking_application.model.RatReport;
import area52.rat_tracking_application.model.RatReportLoader;

public class ReportListActivity extends AppCompatActivity {

    private RecyclerView ratReportRecyclerView;
    private RecyclerView.Adapter reportAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_list);

        createRatReportRecyclerView();
    }

    private void createRatReportRecyclerView() {
        ratReportRecyclerView = (RecyclerView) findViewById(R.id.reportListRecyclerView);
        ratReportRecyclerView.setHasFixedSize(true);
        ratReportRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        reportAdapter = new RatReportAdapter(this);
        ratReportRecyclerView.setAdapter(reportAdapter);
    }
}

class RatReportAdapter extends RecyclerView.Adapter<RatReportAdapter.ViewHolder>{
    private List<RatReport> ratReports;
    private Context context;

    RatReportAdapter(Context context) {
        this.context = context;
        ratReports  = new ArrayList<>(RatReportLoader.reports.values());
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View reportListItem = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.report_list_item, parent, false);
        return new ViewHolder(reportListItem);
    }

    /**
     * Called after creating a new ViewHolder, binds the data to the ViewHolder
     * @param holder the ViewHolder representing one report item in our RecyclerView
     * @param position the position of the Report the ViewHolder needs the data for
     */
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final RatReport report = ratReports.get(position);

        holder.ratReportListItemText.setText(report.toString());
        holder.reportLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, report.detailedReportString(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return ratReports.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView ratReportListItemText;
        public LinearLayout reportLayout;

        public ViewHolder(View itemView) {
            super(itemView);

            ratReportListItemText = (TextView) itemView.findViewById(R.id.report_list_item_text);
            reportLayout = (LinearLayout) itemView.findViewById(R.id.report_item_linLayout);
        }
    }
}
