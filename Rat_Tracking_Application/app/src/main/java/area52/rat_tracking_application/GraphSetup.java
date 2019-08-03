package area52.rat_tracking_application;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
//import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;

import java.util.Date;

public class GraphSetup extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph_setup);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        Button graphButton = (Button) findViewById(R.id.button5);
        graphButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePicker datePicker = (DatePicker) findViewById(R.id.datePicker8);
                int day = datePicker.getDayOfMonth();
                int month = datePicker.getMonth() + 1;
                int year = datePicker.getYear();

                int yearOffset = 1900;
                Date startDate = new Date(year - yearOffset, month, day);

                DatePicker datePickerEnd = (DatePicker) findViewById(R.id.datePicker9);
                int dayEnd = datePickerEnd.getDayOfMonth();
                int monthEnd = datePickerEnd.getMonth() + 1;
                int yearEnd = datePickerEnd.getYear();

                Date endDate = new Date(yearEnd - yearOffset, monthEnd, dayEnd);

                Context context = view.getContext();
                Intent intent = new Intent(context, GraphActivity.class);
                intent.putExtra("start", startDate);
                intent.putExtra("end", endDate);

                Spinner spinner = (Spinner) findViewById(R.id.graph_spinner);
                String selectedGraph = (String) spinner.getSelectedItem();
                intent.putExtra("graphType", selectedGraph);

                context.startActivity(intent);
            }
        });





    }

}
