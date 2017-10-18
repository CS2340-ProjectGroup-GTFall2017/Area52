package area52.rat_tracking_application.controllers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import area52.rat_tracking_application.model.RatReport;
import area52.rat_tracking_application.model.Model;
import area52.rat_tracking_application.R;

/**
 * A fragment representing a single RatReport detail screen.
 *
 * This displays a list of reports that are in a particular borough
 * that was selected from the main screen.
 *
 * This fragment is either contained in a {@link ReportListActivity}
 * in two-pane mode (on tablets) or a {@link ReportDetailActivity}
 * on handsets.
 */
public class ReportDetailFragment extends Fragment {
    /**
     * The fragment arguments representing the  ID's that this fragment
     * represents.  Used to pass keys into other activities through Bundle/Intent
     */
    public static final String ARG_REPORT_ID = "report_id";
    public static final String ARG_USER_ID = "user_id";

    /**
     * The report that this detail view is for.
     */
    private RatReport mReport;
    /**
     * The adapter for the recycle view list of students
     */
    private RatReportRecyclerViewAdapter adapter;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ReportDetailFragment() {
    }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            //Check if we got a valid report passed to us
            if (getArguments().containsKey(ARG_REPORT_ID)) {
                // Get the id from the intent arguments (bundle) and
                //ask the model to give us the report object
                Model model = Model.getInstance();
                // mReport = model.getReportById(getArguments().getInt(ARG_REPORT_ID));
                mReport = model.getCurrentReport();
                Log.d("ReportDetailFragment", "Passing over report: " + mReport);
                Log.d("ReportDetailFragment", "Got reports: " + mReport.getRatReports().size());

                Activity activity = this.getActivity();

                CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
                if (appBarLayout != null) {
                    appBarLayout.setTitle(mReport.toString());
                }
            }

        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.report_detail, container, false);

            //Step 1.  Setup the recycler view by getting it from our layout in the main window
            View recyclerView = rootView.findViewById(R.id.report_list);
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

        /**
         * Set up an adapter and hook it to the provided view
         *
         * @param recyclerView  the view that needs this adapter
         */
        private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
            adapter = new RatReportRecyclerViewAdapter(Model.getInstance().getRatReports());
            Log.d("Adapter", adapter.toString());
            recyclerView.setAdapter(adapter);
        }

        /**
         * This inner class is our custom adapter.  It takes our basic model information and
         * converts it to the correct layout for this view.
         *
         * In this case, we are just mapping the toString of the Student object to a text field.
         */
        public class RatReportRecyclerViewAdapter
                extends RecyclerView.Adapter<RatReportRecyclerViewAdapter.ViewHolder> {

            /**
             * Collection of the items to be shown in this list.
             */
            private final List<Student> mValues;

            /**
             * set the items to be used by the adapter
             * @param items the list of items to be displayed in the recycler view
             */
            public SimpleStudentRecyclerViewAdapter(List<Student> items) {
                mValues = items;
            }

            @Override
            public SimpleStudentRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            /*
              This sets up the view for each individual item in the recycler display
              To edit the actual layout, we would look at: res/layout/course_list_content.xml
              If you look at the example file, you will see it currently just 2 TextView elements
             */
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.student_detail, parent, false);
                return new SimpleStudentRecyclerViewAdapter.ViewHolder(view);
            }

            @Override
            public void onBindViewHolder(final SimpleStudentRecyclerViewAdapter.ViewHolder holder, int position) {
            /*
            This is where we have to bind each data element in the list (given by position parameter)
            to an element in the view (which is one of our two TextView widgets
             */
                //start by getting the element at the correct position
                holder.mStudent = mValues.get(position);
                Log.d("Adapter", "student: " + holder.mStudent);
            /*
              Now we bind the data to the widgets.  In this case, pretty simple, put the id in one
              textview and the string rep of a course in the other.
             */
                holder.mIdView.setText("" + mValues.get(position).getId());
                holder.mContentView.setText(mValues.get(position).toString());

            /*
             * set up a listener to handle if the user clicks on this list item, what should happen?
             */
                holder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //on a phone, we need to change windows to the detail view
                        Context context = v.getContext();
                        //create our new intent with the new screen (activity)
                        Intent intent = new Intent(context, EditStudentActivity.class);
                        /*
                            pass along the selected student we can retrieve the correct data in
                            the next window
                         */
                        intent.putExtra(ReportDetailFragment.ARG_STUDENT_ID, holder.mStudent);

                        //now just display the new window
                        context.startActivity(intent);

                    }
                });
            }

            @Override
            public int getItemCount() {
                return mValues.size();
            }

            /**
             * This inner class represents a ViewHolder which provides us a way to cache information
             * about the binding between the model element (in this case a Course) and the widgets in
             * the list view (in this case the two TextView)
             */

            public class ViewHolder extends RecyclerView.ViewHolder {
                public final View mView;
                public final TextView mIdView;
                public final TextView mContentView;
                public Student mStudent;

                public ViewHolder(View view) {
                    super(view);
                    mView = view;
                    mIdView = (TextView) view.findViewById(R.id.student_id);
                    Log.d("Holder", mIdView.toString());
                    mContentView = (TextView) view.findViewById(R.id.student_details);
                }

                @Override
                public String toString() {
                    return super.toString() + " '" + mContentView.getText() + "'";
                }
            }
        }
    }
    adapter;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ReportDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Check if we got a valid course passed to us
        if (getArguments().containsKey(ARG_COURSE_ID)) {
            // Get the id from the intent arguments (bundle) and
            //ask the model to give us the course object
            Model model = Model.getInstance();
            // mCourse = model.getCourseById(getArguments().getInt(ARG_COURSE_ID));
            mCourse = model.getCurrentCourse();
            Log.d("ReportDetailFragment", "Passing over course: " + mCourse);
            Log.d("ReportDetailFragment", "Got students: " + mCourse.getStudents().size());

            Activity activity = this.getActivity();

            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(mCourse.toString());
            }
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.report_detail, container, false);

        //Step 1.  Setup the recycler view by getting it from our layout in the main window
        View recyclerView = rootView.findViewById(R.id.report_list);
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

    /**
     * Set up an adapter and hook it to the provided view
     *
     * @param recyclerView  the view that needs this adapter
     */
    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        adapter = new RatReportRecyclerViewAdapter(mReport.getRatReports());
        Log.d("Adapter", adapter.toString());
        recyclerView.setAdapter(adapter);
    }

    /**
     * This inner class is our custom adapter.  It takes our basic model information and
     * converts it to the correct layout for this view.
     *
     * In this case, we are just mapping the toString of the Student object to a text field.
     */
    public class RatReportRecyclerViewAdapter
            extends RecyclerView.Adapter<RatReportRecyclerViewAdapter.ViewHolder> {

        /**
         * Collection of the items to be shown in this list.
         */
        private final List<RatReport> mValues;

        /**
         * set the items to be used by the adapter
         * @param items the list of items to be displayed in the recycler view
         */
        public RatReportRecyclerViewAdapter(List<RatReport> items) {
            mValues = items;
        }

        @Override
        public SimpleStudentRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            /*
              This sets up the view for each individual item in the recycler display
              To edit the actual layout, we would look at: res/layout/course_list_content.xml
              If you look at the example file, you will see it currently just 2 TextView elements
             */
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.student_detail, parent, false);
            return new SimpleStudentRecyclerViewAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final SimpleStudentRecyclerViewAdapter.ViewHolder holder, int position) {
            /*
            This is where we have to bind each data element in the list (given by position parameter)
            to an element in the view (which is one of our two TextView widgets
             */
            //start by getting the element at the correct position
            holder.mStudent = mValues.get(position);
            Log.d("Adapter", "student: " + holder.mStudent);
            /*
              Now we bind the data to the widgets.  In this case, pretty simple, put the id in one
              textview and the string rep of a course in the other.
             */
            holder.mIdView.setText("" + mValues.get(position).getId());
            holder.mContentView.setText(mValues.get(position).toString());

            /*
             * set up a listener to handle if the user clicks on this list item, what should happen?
             */
            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //on a phone, we need to change windows to the detail view
                    Context context = v.getContext();
                    //create our new intent with the new screen (activity)
                    Intent intent = new Intent(context, EditStudentActivity.class);
                        /*
                            pass along the selected student we can retrieve the correct data in
                            the next window
                         */
                    intent.putExtra(CourseDetailFragment.ARG_STUDENT_ID, holder.mStudent);

                    //now just display the new window
                    context.startActivity(intent);

                }
            });
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        /**
         * This inner class represents a ViewHolder which provides us a way to cache information
         * about the binding between the model element (in this case a Course) and the widgets in
         * the list view (in this case the two TextView)
         */

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            public final TextView mIdView;
            public final TextView mContentView;
            public Student mStudent;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                mIdView = (TextView) view.findViewById(R.id.student_id);
                Log.d("Holder", mIdView.toString());
                mContentView = (TextView) view.findViewById(R.id.student_details);
            }

            @Override
            public String toString() {
                return super.toString() + " '" + mContentView.getText() + "'";
            }
        }
    }
}
