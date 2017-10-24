package area52.rat_tracking_application.controllers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import area52.rat_tracking_application.R;
import area52.rat_tracking_application.model.RatReport;

/**
 * Created by Eric on 10/23/2017.
 */
public class ReportListAdapter extends ArrayAdapter<RatReport> {

        private final Context context;
        private final ArrayList<RatReport> reportsArrayList;

        public ReportListAdapter(Context context, ArrayList<RatReport> reportsArrayList) {

            super(context, R.layout.row, reportsArrayList);

            this.context = context;
            this.reportsArrayList = reportsArrayList;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View rowView = inflater.inflate(R.layout.row, parent, false);

            TextView labelView = (TextView) rowView.findViewById(R.id.label);
            TextView valueView = (TextView) rowView.findViewById(R.id.value);

            labelView.setText(reportsArrayList.get(position).getTitle());
            valueView.setText(reportsArrayList.get(position).getDescription());


            return rowView;
        }
}
