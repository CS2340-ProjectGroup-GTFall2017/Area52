package area52.rat_tracking_application.controllers;

import area52.rat_tracking_application.model.Model;
import area52.rat_tracking_application.model.User;
import area52.rat_tracking_application.R;
import static area52.rat_tracking_application.R.layout.activity_main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

public class MainActivity extends Activity  {
    Button buttonOne;
    Button buttonTwo;
    Button buttonThree;
    EditText editOne;
    EditText editTwo;
    TextView textViewOne;
    int securityCounter = 3;
    HashMap<String, User> users;
    boolean match = false;

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
                for (int i = 0; i < getRegistrationActivity().getRegisteredUserMap().size(); i++) {
                    if (getRegistrationActivity(
                            ).getRegisteredUserMap(
                                    ).containsKey(editOne.getText().toString())
                            && getRegistrationActivity(
                                    ).getRegisteredUserMap(
                                            ).get(editOne.getText().toString())
                            == editTwo.getText().toString()) {
                        Toast.makeText(getApplicationContext(),
                                "Welcome to the NYC Rat Tracking System!",
                                Toast.LENGTH_LONG).show();
                        match = true;
                        Context context = view.getContext();
                        Intent intent = new Intent(context, LogoutActivity.class);
                        context.startActivity(intent);
                    }
                    if (match) {
                        return;
                    } else {
                        if (i < getRegistrationActivity().getRegisteredUserMap().size()

                                && (!editOne.getText().toString().equals(
                                Model.getInstance().getUserMap()[i].getUName())
                                    || !editTwo.getText().toString().equals(
                                            Model.getInstance().getUserMap()[i].getPWord()))) {
                            Toast.makeText(getApplicationContext(),
                                    "Incorrect Login Info",
                                    Toast.LENGTH_SHORT.show());
                            textViewOne.setVisibility(View.VISIBLE);
                            textViewOne.setBackgroundColor(Color.WHITE);
                            securityCounter--;
                            textViewOne.setText(((Integer) securityCounter).toString());
                            if (securityCounter <= 0) {
                                buttonOne.setEnabled(false);
                            }
                        }
                    }
                }
            }
        });

        buttonTwo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Context context = view.getContext();
                Intent intent = new Intent(context, WelcomeActivity.class);
                context.startActivity(intent);
            }
        });

        buttonThree.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Toast.makeText(
                    getApplicationContext(),
                    "Please register on next screen",
                    Toast.LENGTH_LONG).show();
                Context context = view.getContext();
                Intent intent = new Intent(context, RegistrationActivity.class);
                context.startActivity(intent);
            }
        });
    }
    public static RegistrationActivity getRegistrationActivity() {
        return new RegistrationActivity<>();
    }
}