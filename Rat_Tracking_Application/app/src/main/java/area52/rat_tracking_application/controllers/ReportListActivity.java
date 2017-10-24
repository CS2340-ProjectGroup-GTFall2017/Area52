package area52.rat_tracking_application.controllers;

import android.app.ExpandableListActivity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;

import java.net.URL;

import area52.rat_tracking_application.R;

import static area52.rat_tracking_application.R.layout.activity_report_list;

/**
 * Created by Eric on 10/23/2017.
 */

public class ReportListActivity extends ExpandableListActivity {
    public ReportListActivity() {
        super();
    }



    private static final String TAG = "ReportListActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_report_list);

        Log.d(TAG, "onCreate: starting Asynctask");
        ReportLoaderExtender loader = new ReportLoaderExtender();
        loader.onPreExecute("URL goes here");
        Log.d(TAG, "onCreate: done");
    }


    private class ReportLoaderExtender extends AsyncTask<URL, Integer, Long> {
        private static final String TAG = "Rat Reports Loading ";
        protected Long doInBackground(URL... urls) {
            int count = urls.length;
            long totalSize = 0;
            for (int i = 0; i < count; i++) {
                totalSize += Downloader.downloadFile(urls[i]);
                publishProgress((int) ((i / (float) count) * 100));
                if (isCancelled()) break;
            }
            return totalSize;
        }

        protected void onProgressUpdate(Integer... progress) {
            setProgressPercent(progress[0]);
        }

        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d(TAG, "onPostExecute: parameter is " + s);
        }

        ReportListAdapter reportListAdapter = new ReportListAdapter(this, generateData());

        ListView listView = (ListView) findViewById(R.id.listview);

        listView.setAdapter(reportListAdapter);

    }
}