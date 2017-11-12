package area52.rat_tracking_application.controllers;

import android.annotation.SuppressLint;
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

import area52.rat_tracking_application.R;
import area52.rat_tracking_application.model.Model;
import area52.rat_tracking_application.model.User;

import static area52.rat_tracking_application.R.layout.activity_main;

/**
 * Temp representation of login screen
 * and branching to other activities in controllers
 * package
 */

public class MainActivity extends Activity {

    Button loginButton;
    Button cancelButton;
    Button registrationButton;
    EditText usernameEntry;
    EditText passwordEntry;
    TextView textViewOne;
    private int securityCounter = 3;
    boolean match = false;

    /**
     * the currently logged in user
     */
    private static User _currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_main);
        setupButtonsOnStartup();
    }

    /**
     * Gets the currently logged in user
     *
     * @return the currently logged in user
     */
    public static User getCurrentUser() {
        return _currentUser;
    }

    /**
     * Sets the currently logged in user
     *
     * @param user the currently logged in user
     */
    public static void setCurrentUser(User user) {
        _currentUser = user;
    }

    public void setupButtonsOnStartup() {
        loginButton = (Button) findViewById(R.id.login_button);
        cancelButton = (Button) findViewById(R.id.cancel_button);
        registrationButton = (Button) findViewById(R.id.registration_button);
        usernameEntry = (EditText) findViewById(R.id.enter_username);
        passwordEntry = (EditText) findViewById(R.id.enter_password);
        textViewOne = (TextView) findViewById(R.id.textView3);
        textViewOne.setVisibility(View.GONE);

        loginButton.setOnClickListener(new View.OnClickListener() {

            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                if (Model.getInstance().getUserMap().containsKey(
                        usernameEntry.getText().toString())) {
                    if (passwordEntry.getText().toString().equals(
                            Model.getInstance().getUserMap().get(
                                    usernameEntry.getText().toString()).getPWord())) {
                        setCurrentUser(Model.getInstance().getUserMap().get(
                                usernameEntry.getText().toString()));
                        Toast.makeText(getApplicationContext(),
                                "Welcome to the NYC Rat Tracking System, "
                                        + getCurrentUser() + " !",
                                Toast.LENGTH_LONG).show();
                        match = true;
                        Context CSVReaderContext = view.getContext();
                        Intent CSVReaderIntent = new Intent(
                                CSVReaderContext, RatReportCSVReader.class);
                        CSVReaderContext.startActivity(CSVReaderIntent);
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
                            loginButton.setEnabled(false);
                        }
                    }
                }
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Context context = view.getContext();
                Intent intent = new Intent(context, WelcomeActivity.class);
                context.startActivity(intent);
            }
        });

        registrationButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Toast.makeText(
                        getApplicationContext(),
                        "Please register on next screen",
                        Toast.LENGTH_LONG).show();
                Context registrationContext = view.getContext();
                Intent registrationIntent = new Intent(registrationContext, RegistrationActivity.class);
                registrationContext.startActivity(registrationIntent);
            }
        });
    }
}
