package area52.rat_tracking_application.controllers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import area52.rat_tracking_application.model.RatReport;
import area52.rat_tracking_application.R;
import area52.rat_tracking_application.model.ReportLocation;

import static area52.rat_tracking_application.controllers.RatReportLoader.getCSVHeaderIndices;
import static area52.rat_tracking_application.controllers.RatReportLoader.getReportKeysCreationDates;
import static area52.rat_tracking_application.controllers.RatReportLoader.reports;
import static area52.rat_tracking_application.model.ReportLocation.setZipCodePositions;

/**
* Acknowledgements:
*
* Prof. Bob Waters ([Template provided by Prof. Waters] for Android
* project guidance [GaTech - Fall 2017 - cs2340 - Objects & Design]).
* Classes, methods, method params, instance and local variables named
* to reflect our [class final project] --> [Rat Tracking App]:
*
* A fragment representing a single Rat Report detail screen.
*
* This displays a report that are was selected from the expandable list view
* on the Report detail activity screen.
*
* This fragment is either contained in a {@link ReportListActivity}
* in two-pane mode (on tablets) or a {@link ReportDetailActivity}
* on handsets.
*/
public class ReportDetailFragment extends Fragment {
    /**
     * Per Prof. Bob Waters:     *
     * "The fragment arguments representing the ID's that this fragment
     * represents. Used to pass keys into other activities through Bundle/Intent"
     */
    public static final String ARG_UNIQUE_KEY_ID = "unique_key_id";//1
    public static final String ARG_CREATED_DATE_ID = "created_date_id";//2
    public static final String ARG_LOCATION_TYPE_ID = "location_type_id";//3
    public static final String ARG_INCIDENT_ZIP_ID = "incident_zip_id";//4
    public static final String ARG_INCIDENT_ADDRESS_ID = "incident_adress_id";//5
    public static final String ARG_CITY_ID = "city_id";//6
    public static final String ARG_BOROUGH_ID = "borough_id";//7
    public static final String ARG_LATITUDE_ID = "latitude_id";//8
    public static final String ARG_LONGITUDE_ID = "longitude_id";//9




    /**
     * The adapter for the recycle view list of reports
     */
    private SimpleReportRecyclerViewAdapter adapter;
    private String ratReportKeyCreateDate;
    /***private ReportLoaderExtender loaderExtender;***/

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * instance of the ReportDetailFragment.
     */
    public ReportDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Check if we got a valid report passed to us
        if (getArguments().containsKey(ARG_UNIQUE_KEY_ID)) {
            // Get the id from the intent arguments (bundle) and
            // ask the RatReportLoader to give us the rat report object
            RatReport<Long, ReportLocation> ratDataSingleReport = reports.get(Long.valueOf(ARG_UNIQUE_KEY_ID));

            /**
             * scrolling adapter list view will only contain the key and creation date of each report
             * this will change to a tree format with an expandable list view allowing the user to make a
             * selection by setting report location instance parameter constraints using spinner widgets,
             * but for now only the key/creation date combo will be visible
             * and selectable.
             *
             * Upon making a selection, the user will be taken to a report details screen
             * which currently only appears as a list of the relevant data for that single report
             */
            List<String> ratReportKeyCreateDate = getReportKeysCreationDates();
            Log.d("ReportDetailFragment", "Retrieving the report with the following report id and creation date: "
                    + ratDataSingleReport.getKey().toString() + " " + ratDataSingleReport.getLocation().getCreationDate());
            Log.d("ReportDetailFragment", "Got report: " + ratDataSingleReport.toString());

            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = (
                    CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(getCSVHeaderIndices().toString());
            }
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.content_report_detail, container, false);

        //Step 1.  Setup the recycler view by getting it from our layout in the main window
        View recyclerView = rootView.findViewById(R.id.report_list_container);
        assert recyclerView != null;
        //Step 2.  Hook up the adapter to the view
        setupRecyclerView((RecyclerView) recyclerView);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }

    /***public ReportLoaderExtender getLoader() {
        loaderExtender = new ReportLoaderExtender();
        return loaderExtender;
    }***/



    /***protected class ReportLoaderExtender extends AsyncTask<Object, Object, Object> {
        private static final String TAG = "Rat Reports Loading ";


        protected Object doInBackground(Object... objects) {
            new RatReportLoader().loadRatReports();
            setZipCodePositions();
            /**
             * only key and creation date will appear in the scrolling adapter.
             */
        /***adapter = new SimpleReportRecyclerViewAdapter((ListView) getReportKeysCreationDates());
            return adapter;
        }

        protected void onPostExecute(Object adapter) {
            super.onPostExecute(adapter);
            Log.d(TAG, " ");
        }

        protected void onCancelled() {
            return;
        }
    }***/

    /**
     * Set up an adapter and hook it to the provided view
     *
     * @param recyclerView the view that needs this adapter
     */
    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        /**
         * ratDataSingleReport -> represents a single rat report as a row within the report,
         * returned as a String upon conversion, performed by an instance of RatReportLoader
         */

        Log.d("Adapter", adapter.toString());
        recyclerView.setAdapter(adapter);
    }

    /**
     * This inner class is our custom adapter.  It takes our basic model information and
     * converts it to the correct layout for this view.
     * <p>
     * In this case, we are just mapping the toString of the Student object to a text field.
     */
    public class SimpleReportRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleReportRecyclerViewAdapter.ViewHolder> {

        private final ListView mValues;

        /**
         * Collection of the items to be shown in this expandable list view.
         */
        public SimpleReportRecyclerViewAdapter(ListView items) {
            mValues = items;
        }

        @Override
        public SimpleReportRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            /*
              This sets up the view for each individual item in the recycler display
              To edit the actual layout, we would look at: res/layout/course_list_content.xml
              If you look at the example file, you will see it currently just 2 TextView elements
             */
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.report_detail, parent, false);
            return new SimpleReportRecyclerViewAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final SimpleReportRecyclerViewAdapter.ViewHolder holder, int position) {
            /*
            This is where we have to bind each data element in the list (given by position parameter)
            to an element in the view (which is one of our two TextView widgets
             */
            //start by getting the element at the correct position
            holder.reportView = mValues.getChildAt(position);
            Log.d("Adapter", "rat report: " + holder.reportView);
            /*
              Now we bind the data to the widgets.  In this case, pretty simple, put the id in one
              textview and the string rep of a course in the other.
             */
            holder.mIdView.setText("" + mValues.getChildAt(position).getId());
            holder.mContentView.setText(mValues.getChildAt(position).toString());

            holder.reportView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //on a phone, we need to change windows to the detail view
                    Context context = v.getContext();
                    //create our new intent with the new screen (activity)
                    Intent intent = new Intent(context, ReportEntryActivity.class);
                        /*
                            pass along the selected report we can retrieve the correct data in
                            the next window
                         */
                    //intent.putExtra(ARG_UNIQUE_KEY_ID + ARG_CREATED_DATE_ID);

                    //now just display the new window
                    context.startActivity(intent);

                }
            });
        }

        @Override
        public int getItemCount() {
            int i = 0;
            for (Object report : (List) mValues) {
                i++;
            }
            return i;
        }

        /**
         * This inner class represents a ViewHolder which provides us a way to cache information
         * about the binding between the model element (in this case a Course) and the widgets in
         * the list view (in this case the two TextView)
         */

        public class ViewHolder extends RecyclerView.ViewHolder {
            public View reportView;
            public TextView mIdView;
            public TextView mContentView;
            public RatReport ratReport;

            public ViewHolder(View view) {
                super(view);
                reportView = view;
                mIdView = (TextView) view.findViewById(R.id.report_list);
                Log.d("Holder", mIdView.toString());
                mContentView = (TextView) view.findViewById(R.id.report_detail_container);
            }

            @Override
            public String toString() {
                return super.toString() + " '" + mContentView.getText() + "'";
            }
        }


    }
}