package area52.rat_tracking_application.controllers;


import area52.rat_tracking_application.model.User;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import area52.rat_tracking_application.R;

import static area52.rat_tracking_application.R.id.button3;

public class RegistrationActivity extends AppCompatActivity {
    String newName;
    String newUserName;
    String newPassword;
    Button buttonThree;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "TBD", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    public boolean validUsername() {
        return true;//////set parameters for appropriate userName selection
    }

    public boolean validPassword() {
         return true;///////////set parameters for appropriate password selection
    }

    public User addNewUser() {
        return new User(newName, newUserName, newPassword);
    }
}