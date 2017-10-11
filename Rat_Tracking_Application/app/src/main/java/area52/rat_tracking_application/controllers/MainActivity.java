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

/**
 * Temp representation of login screen
 * and branching to other activities in controllers
 * package
 */

public class MainActivity extends Activity {
    Button buttonOne;
    Button buttonTwo;
    Button buttonThree;
    EditText editOne;
    EditText editTwo;
    TextView textViewOne;
    private int securityCounter = 3;
    boolean match = false;

    /**
     * the currently logged in user
     */
    private User _currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
                if (Model.getInstance().getUserMap().containsKey(
                        editOne.getText().toString())) {
                    if (editTwo.getText().toString().equals(
                            Model.getInstance().getUserMap().get(
                                    editOne.getText().toString()).getPWord())) {
                        setCurrentUser(Model.getInstance().getUserMap().get(
                                editOne.getText().toString()));
                        Toast.makeText(getApplicationContext(),
                                "Welcome to the NYC Rat Tracking System, "
                                        + getCurrentUser() + " !",
                                Toast.LENGTH_LONG).show();
                        match = true;
                        Context context = view.getContext();
                        Intent intent = new Intent(context, LogoutActivity.class);
                        context.startActivity(intent);
                    }
                } else {
                    if (!match) {
                        Toast.makeText(getApplicationContext(),
                                "Incorrect Login Info",
                                Toast.LENGTH_SHORT).show();
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

    /**
     * Gets the currently logged in user
     *
     * @return the currently logged in user
     */
    public User getCurrentUser() {
        return _currentUser;
    }

    /**
     * Sets the currently logged in user
     *
     * @param user the currently logged in user
     */
    public void setCurrentUser(User user) {
        _currentUser = user;
    }
}