package area52.rat_tracking_application;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import area52.rat_tracking_application.model.RatReport;
import area52.rat_tracking_application.model.RatReportManager;

import static area52.rat_tracking_application.R.layout.activity_graphs;

public class GraphActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_graphs);

        Date start = (Date) getIntent().getSerializableExtra("start");
        Date end = (Date) getIntent().getSerializableExtra("end");
        List<RatReport> rawReports = (ArrayList<RatReport>) RatReportManager.getInstance().getRatReportList();


//making a list(map) for all range of months chosen
        Map<String, int> hist = new HashMap<String, int>();

        int startYear = start.getYear();
        int endYear = end.getYear();
        int startMonth = start.getMonth() + 1;
        int endMonth = end.getMonth() + 1;

        for (int j = startMonth; j < 13; j++) {
            hist.add(j + "-" + startYear);
        }

        for (int i = startYear + 1; i < endYear; i++) {
            for (int j = 1; j < 13; j++) {
                hist.add(j + "-" + i);
            }
        }

        for (int j = 1; j < endMonth + 1; j++) {
            hist.add(j + "-" + endYear);
        }

//for loop for reports
        int month = aReport.getCreationDate().getMonth();
        int year = aReport.getCreationDate().getYear();



        for (int i = 0; i < rawReports.size(); i++) {
            Date creationDate = rawReports.get(i).getCreationDate();
            if (creationDate.after(start)
                    && creationDate.before(end)) {

            }
        }


        GraphView graph = (GraphView) findViewById(R.id.graph);
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[] {
                new DataPoint(0,1),
                new DataPoint(1,4),
                new DataPoint(2,3),
                new DataPoint(3, 8),
                new DataPoint(4,1)
        });
        graph.addSeries(series);

        // bar graph
//        GraphView graph1 = (GraphView) findViewById(R.id.graph);
//        BarGraphSeries<DataPoint> series1 = new BarGraphSeries<>(new DataPoint[] {
//                new DataPoint(0, 1),
//                new DataPoint(1, 2)
//        });
//
//        series1.setValueDependentColor(new ValueDependentColor<DataPoint>() {
//            @Override
//            public int get(DataPoint data) {
//                return Color.rgb((int) data.getX()*255/4, (int) Math.abs(data.getY()*255/6), 100);
//            }
//        });
//
//        series1.setSpacing(50);
//
//        series1.setDrawValuesOnTop(true);
//        series1.setValuesOnTopColor(Color.RED);
        // graph1.addSeries(series1);
    }

}
