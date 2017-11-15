package area52.rat_tracking_application;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.ValueDependentColor;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        Map<Date, Integer> reportsByMonths = reportsByMonths(start, end, rawReports);

        int i = 0;
        DataPoint[] dp = new DataPoint[reportsByMonths.size()];
        for (Date d : reportsByMonths.keySet()) {
            dp[i++] = new DataPoint(d, reportsByMonths.get(d));
        }

        String graphType = (String) getIntent().getSerializableExtra("graphType");

        if (graphType.equals("Line Graph")) {
            GraphView graph = (GraphView) findViewById(R.id.graph);
            LineGraphSeries<DataPoint> series = new LineGraphSeries<>(dp);
            graph.addSeries(series);

        } else if (graphType.equals("Histogram")) {
             //bar graph
        GraphView graph1 = (GraphView) findViewById(R.id.graph);
        BarGraphSeries<DataPoint> series1 = new BarGraphSeries<>(dp);
        series1.setValueDependentColor(new ValueDependentColor<DataPoint>() {
            @Override
            public int get(DataPoint data) {
                return Color.rgb((int) data.getX()*255/4, (int) Math.abs(data.getY()*255/6), 100);
            }
        });
        series1.setSpacing(50);
        series1.setDrawValuesOnTop(true);
        series1.setValuesOnTopColor(Color.RED);
             graph1.addSeries(series1);
        }



    }

    private Map<Date, Integer> reportsByMonths(Date start, Date end, List<RatReport> rawReports) {
        //making a list(map) for all range of months chosen
        Map<Date, Integer> numOfReports = new HashMap<Date, Integer>();

        int startYear = start.getYear();
        int endYear = end.getYear();
        int startMonth = start.getMonth();
        int endMonth = end.getMonth();

        int x = startMonth;
        for (int y = startYear; y < endYear + 1; y++) {
            if (y == endYear) {
                while (x < endMonth + 1) {
                    numOfReports.put(new Date(endYear, x, 0), 0);
                    x++;
                }
            } else {
                while (x < 12) {
                    numOfReports.put(new Date(y, x, 0), 0);
                    x++;
                }
            }
            x = 0;
        }
        for (RatReport report : rawReports) {
            Date creationDate = report.getCreationDate();
            int month = report.getCreationDate().getMonth();
            int year = report.getCreationDate().getYear();
            if (creationDate.after(start)
                    && creationDate.before(end)) {
                for (Date d : numOfReports.keySet()) { //would we have more efficient way?
                    if (d.getMonth() == month
                            && d.getYear() == year) {
                        numOfReports.put(d, numOfReports.get(d) + 1);
                    }
                }

            }
        }
        return numOfReports;
    }


}
