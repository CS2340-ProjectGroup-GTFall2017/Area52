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
    final int GRAPH_MIN_X = 0;
    final int GRAPH_MAX_X = 12;
    final int BAR_GRAPH_SPACING = 50;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_graphs);

        Date start = (Date) getIntent().getSerializableExtra("start");
        Date end = (Date) getIntent().getSerializableExtra("end");
        List<RatReport> rawReports = (ArrayList<RatReport>) RatReportManager.getInstance().getRatReportList();
        Map<Date, Integer> reportsByMonths = reportsByMonths(monthsRange(start, end), rawReports, start, end);

        int i = 0;
        DataPoint[] dp = new DataPoint[reportsByMonths.size()];
        for (Date d : reportsByMonths.keySet()) {
            dp[i++] = new DataPoint(i, reportsByMonths.get(d));
        }

//        for (int j = 0; j < dp.length; j++) {
//            Log.d("Graph", j + " " + );
//        }

        String graphType = (String) getIntent().getSerializableExtra("graphType");
        GraphView graph = (GraphView) findViewById(R.id.graph);

        if (graphType.equals("Line Graph")) {
            LineGraphSeries<DataPoint> series = new LineGraphSeries<>(dp);
            graph.addSeries(series);
            graph.getViewport().setMinX(GRAPH_MIN_X);
            graph.getViewport().setMaxX(GRAPH_MAX_X);


        } else if (graphType.equals("Histogram")) {
             //bar graph
            BarGraphSeries<DataPoint> series1 = new BarGraphSeries<>(dp);
            series1.setValueDependentColor(new ValueDependentColor<DataPoint>() {
                @Override
                public int get(DataPoint data) {
                    int red = 255/4;
                    int green = 255/6;
                    int blue = 100;
                    return Color.rgb((int) data.getX()*red, (int) Math.abs(data.getY()*green), blue);
                }
            });
            series1.setSpacing(BAR_GRAPH_SPACING);
            series1.setDrawValuesOnTop(true);
            series1.setValuesOnTopColor(Color.RED);
             graph.addSeries(series1);
        }

        //graph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(getActivity()));
        graph.getGridLabelRenderer().setNumHorizontalLabels(reportsByMonths.size());

        graph.getGridLabelRenderer().setHumanRounding(false);
    }

    /**
     * A method to get a map which has dates as key, and the number of reports as the value.
     *
     *
     */
    private Map<Date, Integer> monthsRange(Date start, Date end) {
        //making a list(map) for all range of months chosen
        Map<Date, Integer> monthRange = new HashMap<Date, Integer>();

        int startYear = start.getYear(); //2016
        int endYear = end.getYear(); // 2017
        int startMonth = start.getMonth(); // 10
        int endMonth = end.getMonth(); // 11

        int x = startMonth; // 10
        for (int y = startYear; y < endYear + 1; y++) { // y = 2017, while y < 2018, y++
            if (y == endYear) {
                while (x < endMonth + 1) { // x = 0, x < 11 + 1
                    monthRange.put(new Date(endYear, x, 0), 0); // 2017, 0, 0; 2017, 1, 0; 2017, 2, 0; ...
                    x++;
                }
            } else {
                while (x < GRAPH_MAX_X) {
                    monthRange.put(new Date(y, x, 0), 0); // 2016, 10, 0; 2016, 11, 0;
                    x++;
                }
            }
            x = 0;
        }
        return monthRange;
    }

    private Map<Date, Integer> reportsByMonths(Map<Date, Integer> months, List<RatReport> rawReports,
                                               Date start, Date end) {
        for (RatReport report : rawReports) {
            Date creationDate = report.getCreationDate();
            int month = report.getCreationDate().getMonth();
            int year = report.getCreationDate().getYear();
            if (creationDate.after(start)
                    && creationDate.before(end)) {
                for (Date d : months.keySet()) { //would we have more efficient way?
                    if (d.getMonth() == month
                            && d.getYear() == year) {
                        months.put(d,months.get(d) + 1);
                    }
                }

            }
        }
        return months;
    }
}
