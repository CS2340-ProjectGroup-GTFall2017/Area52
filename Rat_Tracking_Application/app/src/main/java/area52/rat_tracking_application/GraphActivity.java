package area52.rat_tracking_application;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import static area52.rat_tracking_application.R.layout.activity_graphs;

/**
 * Created by eunjikang on 11/8/17.
 */

public class GraphActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_graphs);

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
