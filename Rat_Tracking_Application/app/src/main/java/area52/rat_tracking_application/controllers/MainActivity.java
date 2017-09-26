package area52.rat_tracking_application.controllers;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import area52.rat_tracking_application.R;
import area52.rat_tracking_application.model.Model;
import area52.rat_tracking_application.model.User;

import static area52.rat_tracking_application.R.layout.activity_main;

public class MainActivity extends Activity  {
    Button buttonOne;
    Button buttonTwo;
    Button buttonThree;
    EditText editOne;
    EditText editTwo;
    TextView textViewOne;
    int securityCounter = 10;
    User[] users = new User[5];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Model.getInstance().loadTestData();
        users = Model.getInstance().getUsers();
        super.onCreate(savedInstanceState);
        setContentView(activity_main);
        buttonOne = (Button)findViewById(R.id.button);
        buttonTwo = (Button)findViewById(R.id.button2);
        buttonThree = (Button)findViewById(R.id.button3);
        editOne = (EditText)findViewById(R.id.editText);
        editTwo = (EditText)findViewById(R.id.editText2);
        textViewOne = (TextView)findViewById(R.id.textView3);
        textViewOne.setVisibility(View.GONE);
        buttonOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i = 0; i < Model.getInstance().getUsers().length; i++) {
                    if (editOne.getText().toString().equals(Model.getInstance().getUsers()[i].getUName()) &&
                            editTwo.getText().toString().equals(Model.getInstance().getUsers()[i].getPWord())) {
                        Model.getInstance().setCurrentUser(Model.getInstance().getUsers()[i]);
                        Toast.makeText(getApplicationContext(),
                                "Welcome to the NYC Rat Tracking System!", Toast.LENGTH_LONG).show();
                    }
                }
                for (int i = 0; i < Model.getInstance().getUsers().length; i++) {
                    if (!editOne.getText().toString().equals(Model.getInstance().getUsers()[i].getUName()) ||
                            !editTwo.getText().toString().equals(Model.getInstance().getUsers()[i].getPWord())) {
                        Toast.makeText(getApplicationContext(),
                                "Incorrect Login Info", Toast.LENGTH_LONG).show();
                        textViewOne.setVisibility(View.VISIBLE);
                        textViewOne.setBackgroundColor(Color.WHITE);
                        securityCounter--;
                        if (securityCounter == 0) {
                            buttonOne.setEnabled(false);
                        }
                        textViewOne.setText(((Integer) securityCounter).toString());
                    }
                }
            }
        });
        buttonTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        buttonThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(
                    getApplicationContext(),
                    "Please register below:",
                    Toast.LENGTH_SHORT).show();
            }
        });
    }
}