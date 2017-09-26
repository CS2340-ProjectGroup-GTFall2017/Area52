package area52.rat_tracking_application.controllers;


import area52.rat_tracking_application.model.User;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import area52.rat_tracking_application.R;

public class RegistrationActivity extends AppCompatActivity {
    char[] newNameAsChar;
    char[] newUserNameAsChar;
    char[] newPasswordAsChar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Registering a new User", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Intent intent = new Intent(getBaseContext(), EditUserActivity.class);
                startActivity(intent);
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
        return new User(newNameAsChar.toString(), newUserNameAsChar.toString(), newPasswordAsChar.toString());
    }
}