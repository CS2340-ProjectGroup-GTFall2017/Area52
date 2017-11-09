package area52.rat_tracking_application;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

import java.util.Date;

public class DateRange extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_range);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button mapButton = (Button) findViewById(R.id.button4);
        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePicker datePicker = (DatePicker) findViewById(R.id.datePicker8);
                int day = datePicker.getDayOfMonth();
                int month = datePicker.getMonth() + 1;
                int year = datePicker.getYear();

                //Date startDate = new Date();
                //startDate.setYear(year);
                //startDate.setMonth(month);
                //startDate.setDate(day);
                Date startDate = new Date(year - 1900, month, day);

                DatePicker datePickerEnd = (DatePicker) findViewById(R.id.datePicker9);
                int dayEnd = datePickerEnd.getDayOfMonth();
                int monthEnd = datePickerEnd.getMonth() + 1;
                int yearEnd = datePickerEnd.getYear();

                //Date endDate = new Date();
                //endDate.setMonth(monthEnd);
                //endDate.setYear(yearEnd);
                //endDate.setDate(dayEnd);
                Date endDate = new Date(yearEnd - 1900, monthEnd, dayEnd);

                Context context = view.getContext();
                Intent intent = new Intent(context, MapsActivity.class);
                intent.putExtra("start", startDate);
                intent.putExtra("end", endDate);
                context.startActivity(intent);
            }
        });





    }

}